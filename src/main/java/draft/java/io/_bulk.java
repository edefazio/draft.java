package draft.java.io;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.utils.SourceZip;
import draft.DraftException;
import draft.java._type;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;
import java.util.function.Function;

import static java.nio.file.FileVisitResult.CONTINUE;
import java.util.function.Consumer;

/**
 * Bulk read in .java files and optionally transform or inspect the {@link _type}s created
 *
 * ASSUMPTION: YOU HAVE MEMORY TO SPARE, WE READ ALL OF THE FILES INTO MEMORY BEFORE PROCESSING
 * NOT A GREAT ASSUMPTION FORM 1,000,0000s of files or in a memory constrained environment.
 *
 * 1) Reads in .java files from a root directory (as Strings)
 * 2) In parallel worker threads  :
 *      a) parses each String into {@link _type}
 *      b) (optionally) performs one or more Consumer<_type> transforms on the {@link _type}
 *
 */
public enum _bulk {

    ;

    /**
     * Reads in all .java files into {@link _type}s that are within the rootPath
     * and returns a receipt containing
     * @param rootPath
     * @return Receipt containing the files read in, and the paths read from
     */
    public static _load read(Path rootPath ){
        return fn( rootPath );
    }

    /**
     * read all .java files from the root path (or a .zip or .jar)
     * and all subdirectories and return the
     * _load
     * @param rootPath the root path to .java files or path to a .jar or .zip
     * @return the _load (containing the files as Strings)
     */
    public static _load read( String rootPath ){
        return _bulk.fn( rootPath );
    }

    /**
     * 1) Read in the individual .java files, 
     * 2) convert them to _type
     * 3) call all _typeConsumers on each _type
     * 4) return the receipt (i.e. the _type(s) are not stored)
     * 
     * @param rootPath
     * @param _typeConsumers
     * @return 
     */
    public static _receipt consume(String rootPath, Consumer<_type>..._typeConsumers ){
        return consume(Paths.get(rootPath), _typeConsumers);
    }
    
    /**
     * 1) Read in the individual .java files, 
     * 2) convert them to _type
     * 3) call all _typeConsumers on each _type
     * 4) return the receipt (i.e. the _type(s) are not stored)
     * 
     * @param rootPath
     * @param _typeConsumers
     * @return 
     */
    public static _receipt consume(Path rootPath, Consumer<_type>..._typeConsumers ){
        _receipt receipt = new _receipt();
        receipt.baseReadPath = rootPath;
        if( rootPath.toFile().isDirectory() ) { //bulk read in a directory
            _bulkReadJavaFiles javaFileReader = new _bulkReadJavaFiles();
            try {
                receipt.startTimestamp = System.currentTimeMillis();
                Files.walkFileTree(rootPath, javaFileReader);
                receipt.readFilesTimestamp = System.currentTimeMillis();
                receipt.paths = javaFileReader.listPaths();
            } catch (IOException ioe) {
                throw new _ioException("unable to list files in " + rootPath, ioe);
            }
            /** Use parallel worker threads for parsing and transforming */
            javaFileReader.filesRead.parallelStream().forEach( fr-> {
                _type _t = _type.of( fr.fileContents ); //1) parse the String to _type
                
                //3) apply transforms
                for(int i=0;i<_typeConsumers.length; i++){
                    _typeConsumers[i].accept(_t);
                }                
            } );
            receipt.processedTimestamp = System.currentTimeMillis();
            return receipt;
        }
        /* its a file ( a .zip or .jar file ) */
        SourceZip sz = new SourceZip(rootPath);
        receipt.startTimestamp = System.currentTimeMillis();
        receipt.readFilesTimestamp = System.currentTimeMillis();
        try {
            sz.parse( (Path relativeZipEntryPath, ParseResult<CompilationUnit> result) -> {
                if( result.isSuccessful() ){
                    receipt.paths.add(relativeZipEntryPath);
                    _type _t = _type.of(result.getResult().get());
                    for(int i=0;i<_typeConsumers.length; i++){
                        _typeConsumers[i].accept(_t);
                    }
                } else{
                    throw new DraftException("Unable to parse File in zip/jar at: "+ relativeZipEntryPath+System.lineSeparator()+
                            "problems:"+result.getProblems() );
                }
            });
            receipt.processedTimestamp = System.currentTimeMillis();
            return receipt;
        } catch(IOException ioe){
            throw new DraftException("Unable to read from .jar or .zip file at "+rootPath);
        } 
    }
    
    /**
     * loads all of the .java files into {@link _type} s that are located within 
     * the rootPath(recursively) (or the files within a .zip or .jar file) 
     * and runs the _type functions and stores the original _read in _type and 
     * (if the _type changed after applying the functions) the modified
     * _type is also saved in the _load.
     * 
     * @param rootPath
     * @param _typeFns
     * @return
     */
    public static _load fn(String rootPath, Function<_type,_type>... _typeFns){
        return fn( Paths.get(rootPath), _typeFns);
    }
   
    /**
     * Will read in bulk, if the rootPath is a .zip or a .jar file, will delegate 
     * to reading these in if rootPath is a Directory, will read
     * @param rootPath
     * @param _typeFns
     * @return
     */
    public static _load fn(Path rootPath, Function<_type,_type>... _typeFns){

        _load receipt = new _load();
        receipt.baseReadPath = rootPath;

        if( rootPath.toFile().isDirectory() ) { //bulk read in a directory
            _bulkReadJavaFiles javaFileReader = new _bulkReadJavaFiles();
            try {
                receipt.startTimestamp = System.currentTimeMillis();
                Files.walkFileTree(rootPath, javaFileReader);
                receipt.readFilesTimestamp = System.currentTimeMillis();
                receipt.paths = javaFileReader.listPaths();
            } catch (IOException ioe) {
                throw new _ioException("unable to list files in " + rootPath, ioe);
            }
            /** Use parallel worker threads for parsing and transforming */
            javaFileReader.filesRead.parallelStream().forEach( fr-> {
                _type _t = _type.of( fr.fileContents ); //1) parse the String to _type
                _type _orig = _t.copy();
                int beforeHash = _t.hashCode();         //2) store the before hash
                receipt.types.add(_orig); //store the _type in the receipt
                //3) apply transforms
                for(int i=0;i<_typeFns.length; i++){
                    _t = _typeFns[i].apply(_t);
                }
                int afterHash = _t.hashCode();
                if( beforeHash != afterHash ) { //check if after hash is different
                    receipt.addTransformedType(_orig, _t);
                }                
            } );
            receipt.processedTimestamp = System.currentTimeMillis();
            return receipt;
        }
        /* its a file ( a .zip or .jar file ) */
        SourceZip sz = new SourceZip(rootPath);
        receipt.startTimestamp = System.currentTimeMillis();
        receipt.readFilesTimestamp = System.currentTimeMillis();
        try {
            sz.parse( (Path relativeZipEntryPath, ParseResult<CompilationUnit> result) -> {
                if( result.isSuccessful() ){
                    _type _t = _type.of(result.getResult().get());
                    _type _orig = _t.copy();
                    receipt.types.add(_orig); //store the clean version
                    //System.out.println( "Type "+ _t.getFullName());
                    int beforeHash = _t.hashCode();         //2) store the before hash
                    //3) apply transforms
                    //if( _typeFns.length > 0 ){
                    //    _orig = _t.copy(); //clone BEFORE trying to apply annos
                    // }
                    for(int i=0;i<_typeFns.length; i++){
                        _t = _typeFns[i].apply(_t);
                    }
                    int afterHash = _t.hashCode();
                    if( beforeHash != afterHash ) { //check if after hash is different
                        receipt.addTransformedType(_orig, _t);
                    }
                    //receipt.types.add(_t); //store the _type in the receipt
                } else{
                    throw new DraftException("Unable to parse File in zip/jar at: "+ relativeZipEntryPath+System.lineSeparator()+
                        "problems:"+result.getProblems() );
                }
            });
            receipt.processedTimestamp = System.currentTimeMillis();
            return receipt;
        } catch(IOException ioe){
            throw new DraftException("Unable to read from .jar or .zip file at "+rootPath);
        } 
    }

    /**
     * If transformations are applied to types, you can keep track of
     * the hashcodes of the types (before and after)
     */
    public static class _transformed {
        _type original;
        _type transformed;

        public _transformed(_type original, _type transformed ){
            this.original = original;
            this.transformed = transformed;
        }
    }

    /**
     * Receipt for a consumer, i.e. bulk read and operate on
     * (don't keep the _types in memory)
     */
    public static class _receipt {
        /** where the .java files were read from*/
        public Path baseReadPath;
        //public List<String> typeNames = new ArrayList<String>();
        public List<Path>paths = new ArrayList<>();
        public long startTimestamp;
        public long readFilesTimestamp;
        public long processedTimestamp;
        
         public long timeToRead(){
            return readFilesTimestamp - startTimestamp;
        }

        public long timeToProcess(){
            return processedTimestamp - readFilesTimestamp;
        }
        
        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append( "Read and Processed ("+paths.size()+") .java files from \""+ baseReadPath +"\" in : "+(processedTimestamp - startTimestamp)+"ms"+System.lineSeparator());
            
            paths.forEach(p -> sb.append("    ").append(p).append(System.lineSeparator()) );
            return sb.toString();
        }
    }
    
    /**
     * Maintains the details as well as the bulk payload of
     * _types that have been read in from the directory
     *
     */
    public static class _load implements _type._hasTypes {
        /** where the .java files were read from*/
        public Path baseReadPath;

        public List<Path>paths = new ArrayList<>();
        public long startTimestamp;
        public long readFilesTimestamp;
        public long processedTimestamp;
        public List<_type> types = new ArrayList<>();
        public List<_transformed> transformedTypes = new ArrayList<>();

        public _load addTransformedType(_type original, _type transformed){
            transformedTypes.add( new _transformed( original, transformed) );
            return this;
        }

        public List<_type> list(){
            return types;
        }
        public boolean isEmpty(){
            return this.types.isEmpty();
        }
        public _type[] typesArray(){
            _type[] arr= new _type[ this.types.size() ];
            for(int i=0;i<arr.length;i++){
                arr[i] = types.get(i);
            }
            return arr;
        }
        public long timeToRead(){
            return readFilesTimestamp - startTimestamp;
        }

        public long timeToProcess(){
            return processedTimestamp - readFilesTimestamp;
        }

        public List<Path> listFilePaths(){
            return paths;
        }

        public _type getOriginal( Class clazz ){
            return getOriginal(clazz.getCanonicalName());
        }

        public _type getOriginal( String className ){
            Optional<_transformed> ot =
                    this.transformedTypes.stream().filter(tt -> tt.original.getFullName().equals(className)).findFirst();
            if( ot.isPresent() ) {
                return ot.get().original;
            }
            return null;
        }

        /**
         * @param transformed
         * @return
         */
        public _type getOriginal( _type transformed ){
            Optional<_transformed> ot =
                    this.transformedTypes.stream().filter(tt -> tt.transformed.equals(transformed)).findFirst();
            if( ot.isPresent() ) {
                return ot.get().original;
            }
            return null;
        }

        public boolean isTransformed(String className){
            return this.transformedTypes.stream().filter(tt -> tt.original.getFullName().equals(className)).findFirst().isPresent();
        }

        public boolean isTransformed(Class clazz){
            return this.transformedTypes.stream().filter(tt -> tt.original.getFullName().equals(clazz.getCanonicalName())).findFirst().isPresent();
        }

        public List<_type> listTypes(){
            return types;
        }

        public _type getType(Class typeClass){
            if(typeClass.isMemberClass() ){
                Class declClass = typeClass.getDeclaringClass();
                _type _t = getType( declClass );
                _t.getNest(typeClass.getSimpleName());
            }
            Optional<_type> ot =
                    this.types.stream().filter( t -> t.getFullName().equals(typeClass.getCanonicalName())).findFirst();
            if( ot.isPresent() ){
                return ot.get();
            }
            return null;
        }

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append( "Read and Processed ("+types.size()+") .java files from \""+ baseReadPath +"\" in : "+(processedTimestamp - startTimestamp)+"ms"+System.lineSeparator());
            if( this.transformedTypes.size() > 0){
                sb.append("--("+this.transformedTypes.size()+") transformations applied to:").append(System.lineSeparator());
                this.transformedTypes.forEach( tt-> sb.append("   ").append( tt.transformed.getFullName() ).append(System.lineSeparator()) );
                sb.append("----------------------------").append(System.lineSeparator());
            }
            paths.forEach(p -> sb.append("    ").append(p).append(System.lineSeparator()) );
            return sb.toString();
        }
    }

    /**
     * Reads and Stores all Java files found within a directory
     * into a List of Strings in memory.
     *
     * NOTE: this CAN be a memory hog if the directory contains MANY .java files
     * but it does this to allow parallel threads to parse the Strings (which is more
     * CPU intensive operation)
     *
     * Consider an alternative if you have 1000s of .java files to be read in
     */
    private static class _bulkReadJavaFiles extends SimpleFileVisitor<Path> {
        List<FileContents> filesRead = new ArrayList<>();

        public static class FileContents{
            public final Path filePath;
            public final String fileContents;

            public FileContents( Path filePath, String fileContents ){
                this.filePath = filePath;
                this.fileContents = fileContents;
            }
        }

        public List<Path> listPaths(){
            List<Path> paths = new ArrayList<>();
            filesRead.forEach( fr -> paths.add( fr.filePath));
            return paths;
        }

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attr) {
            if(attr.isRegularFile()) {
                String pathString = path.toString();
                if( pathString.endsWith(".java") ){
                    filesRead.add( new FileContents(path, _inFilePath.readFile(path, Charset.defaultCharset())));
                }
            }
            return CONTINUE;
        }

        /**
         * Invoked for a file that could not be visited.
         *
         * <p> Unless overridden, this method re-throws the I/O exception that prevented
         * the file from being visited.
         */
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            Objects.requireNonNull(file);
            throw new _ioException("unable to visit file "+file, exc);
        }
    }
}
