package draft.java.file;

import com.github.javaparser.ParseResult;
import com.github.javaparser.ast.CompilationUnit;
import draft.DraftException;
import draft.java.Ast;
import draft.java._type;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import static java.nio.file.FileVisitResult.CONTINUE;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import draft.java.io._ioException;
import java.util.function.Consumer;
import java.util.function.Predicate;
import draft.java._code;
import draft.java._code.*;
import java.nio.file.Paths;

/**
 * Given a root directory, (and optional Predicate) will recursively read in a 
 * the files in this directory and all subdirectories) into memory so they can 
 * be operated on (and potentially written out)
 * 
 * @author Eric
 */
public class _batch {

    /** the path for where files are read from*/
    public Path rootPath;

    /** timestamp when the _batch first started */
    public long startTimestamp;

    /** timestamp when the _batch completed */
    public long endTimestamp;

    /**
     * the paths of the files read in //public List<Path> sourceFilePaths;
     * (i.e. .java file, .yml
     * file, .pom file)
     */     
    public _files files;

    /**
     * Optional predicate used to skip files from being read into the batch 
     * (by default skips NO files)... Note we use the Stringified path
     * i.e. "C:\\dev\\project\\aaa\\"... And the paths to files that were 
     * skipped are stored in the {@link #filesSkipped} member variable
     
    public Predicate<String> skipFilesPathPredicate = p-> false;
    */
    
    /** 
     * Collect the paths to all files that were skipped based on the 
     * {@link #skipFilesPathPredicate} 
     */
    public List<Path> filesSkipped;
    
    /** it's a private constructor, use one of the static of() */
    private _batch (){ }
    
    /**
     * 
     * @param rootPath
     * @return 
     */
    public static _batch of( String rootPath ){
        return of(Paths.get(rootPath));
    }
    
    /**
     * Builds a _batch of in-memory files starting at the rootPath (including all files)
     * @param rootPath the root Path where to read from
     * @return the batch containing all files
     */
    public static _batch of(Path rootPath) {
        /** the default exclude for walking the project*/
        Predicate<String> skipFiles = f-> false;                
        return of(rootPath, skipFiles);
    }

    /**
     * 
     * @param rootPath
     * @param skipFiles
     * @return 
     */
    public static _batch of( String rootPath, Predicate<String> skipFiles) {
        return of(Paths.get(rootPath), skipFiles);
    }
    
    /**
     * Reads in all files into (accept those who match the {@link skipFiles} 
     * predicate) into a _batch
     *
     * @param rootPath the root path
     * @param skipFiles a filter to decide which files to skip based on the 
     * String version of the path
     * @return _batch containing the files read, and the paths read from
     */
    public static _batch of(Path rootPath, Predicate<String> skipFiles) {
        _batch batch = new _batch();
        batch.rootPath = rootPath;

        if (rootPath.toFile().isDirectory()) { //bulk read in a directory
            _fileReader fileReader = new _fileReader(rootPath, skipFiles);
            try {
                batch.startTimestamp = System.currentTimeMillis();
                Files.walkFileTree(batch.rootPath, fileReader);
                batch.endTimestamp = System.currentTimeMillis();
                batch.filesSkipped = fileReader.filesSkipped;
            } catch (IOException ioe) {
                throw new _ioException("unable to list files in " + rootPath, ioe);
            }
            batch.files = fileReader.filesRead;
            return batch;
        }
        throw new _ioException("Failure: expected rootPath to be a directory");
    }
    
    /**
     * build & return the Java code model (_type, _packageInfo, _moduleInfo)
     * 
     * @param f
     * @return 
     */
    private static _code buildJavaCode( _file f){
        
        ParseResult<CompilationUnit> pr = 
            Ast.JAVAPARSER.parse(f.getCharContent(true).toString());
        
        CompilationUnit  astCompUnit = null;
        if( !pr.getResult().isPresent() ){
            throw new DraftException("Unable to parse " + f.filePath + System.lineSeparator() + pr.getProblems() );        
        }
        astCompUnit = pr.getResult().get();
        astCompUnit.setStorage(f.filePath);
        if( f.filePath.endsWith("package-info.java") ){
            return _packageInfo.of(astCompUnit);
        } 
        else if (f.filePath.endsWith("module-info.java") ){
            return _moduleInfo.of(pr.getResult().get());
        } 
        return _type.of(astCompUnit);                
    }
    
    public _batch forFiles( Consumer<_file> _fileActionFn ){
        this.files.forEach(_fileActionFn);
        return this;
    }
    
    /**
     * 
     * @param _codeActionFn
     * @return 
     */
    public List<_code> forJavaCode(Consumer<_code> _codeActionFn ){
        return _batch.this.forJavaCode(t-> true, _codeActionFn);
    }
    
    /**
     * Apply a consumer to all code units of the batch
     * (_codeUnits are {@draft.java._type}s, like: 
     * <UL>
     *   <LI>{@link draft.java._type} 
     *   <UL>
     *     <LI>{@link draft.java._class} 
     *     <LI>{@link draft.java._interface}
     *     <LI>{@link draft.java._enum} 
     *     <LI>{@link draft.java._annotation}
     *   <UL> 
     *   <LI>{@link draft.java._java._packageInfo} 
     *   <LI>{@link draft.java._java._moduleInfo}
     * </UL>
     * 
     * @param _codeMatchFn
     * @see #forTypes(java.util.function.Consumer) 
     * @param _codeActionFn
     * @return 
     */
    public List<_code> forJavaCode(Predicate<_code> _codeMatchFn, Consumer<_code> _codeActionFn ){
        List<_code> _cus = new ArrayList<>();
        this.files.forEach(f -> {
            if(f.getName().endsWith(".java") ){                          
                _code _cu = buildJavaCode(f);    
                if( _codeMatchFn.test(_cu ) ){
                    int beforeHash = _cu.hashCode();
                    _codeActionFn.accept(_cu);  
                    int afterHash = _cu.hashCode();
                    if( beforeHash != afterHash ){
                        //since this will reformat the code, only do it if there were changes
                        f.data = _cu.astCompilationUnit().toString().getBytes();
                    }
                    _cus.add( _cu);
                }
            } 
        });
        return _cus;                
    }
    
    public <T extends _type> _batch forJavaTypes( Consumer<_type> _typeActionFn){
        return forJavaTypes(_type.class, t->true, _typeActionFn);
    }
    
    public <T extends _type> _batch forJavaTypes( Class<T> classType, Predicate<T> _typeMatchFn, Consumer<_type> _typeActionFn){
        this.files.forEach(f -> {
            if(f.getName().endsWith(".java") && !f.filePath.endsWith("package-info.java") && !f.filePath.endsWith("module-info.java")){                          
                CompilationUnit cu = Ast.compilationUnit( new String (f.data) );
                cu.setStorage(f.filePath);
                _type _t = _type.of( cu );
                if( classType.isAssignableFrom( _t.getClass() ) ){
                    T t = (T)_t;
                    if( _typeMatchFn.test(t) ){
                        int beforeHash = t.hashCode();
                        _typeActionFn.accept(t);  
                        int afterHash = t.hashCode();
                        if( beforeHash != afterHash ){
                            //since this will reformat the code, only do it if there were changes
                            f.data = t.astCompilationUnit().toString().getBytes();
                        }
                    }
                }
            } 
        });
        return this;       
    }
    
    /**
     * @see #forCodeUnits(java.util.function.Consumer) to include {@link draft.java._packageInfo} and {@link draft.java._moduleInfo}
     * 
     * @param _typeMatchFn
     * @param _typeActionFn     
     * @return 
     */ 
    public _batch forJavaTypes( Predicate<_type> _typeMatchFn, Consumer<_type> _typeActionFn ) {
        return _batch.this.forJavaTypes(_type.class, _typeMatchFn, _typeActionFn);        
    }
     
    
    /**
     * Reads and stores the Files read as bytes in memory as FileData (the Path
     * & contents of the data in bytes)
     *
     */
    public static class _fileReader extends SimpleFileVisitor<Path> {

        public Path basePath;
        public _files filesRead = new _files();
        public List<Path> filesSkipped = new ArrayList<>();

        public Predicate<String> skipFiles;

        public _fileReader(Path basePath) {
            this.basePath = basePath;
        }

        public _fileReader(Path basePath, Predicate<String> pathExclude) {
            this.basePath = basePath;
            this.skipFiles = pathExclude;
        }

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attr) {
            String pathString = path.toString();
            if (skipFiles.test(pathString)) {
                filesSkipped.add(path);
                return CONTINUE;
            }
            try {
                filesRead.add(path, Files.readAllBytes(path) );
            } catch (IOException e) {
                throw new _ioException("Unable to read file " + e);
            }
            return CONTINUE;
        }

        /**
         * Invoked for a file that could not be visited.
         *
         * <p>
         * Unless overridden, this method re-throws the I/O exception that
         * prevented the file from being visited.
         *
         * @param file
         * @param exc
         * @return
         */
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) {
            Objects.requireNonNull(file);
            throw new _ioException("unable to visit file " + file, exc);
        }
    }

    /*
    public static class _sourceUnit {

        public Path filePath;
        public byte[] data;

        public _sourceUnit(Path filePath, byte[] data) {
            this.filePath = filePath;
            this.data = data;
        }

        public boolean isJavaCodeUnit() {
            return filePath.endsWith(".java");
        }
        
        public _codeUnit asJavaCodeUnit() {
            CompilationUnit cu = StaticJavaParser.parse(new ByteArrayInputStream(data) );
            cu.setStorage(filePath); //set the path
            if (filePath.endsWith("package-info.java")) {
                return _packageInfo.of(cu);            
            } else if (filePath.endsWith("module-info.java")) {
                return _moduleInfo.of(cu);
            } else if( filePath.endsWith(".java")) {
                return _type.of(cu); //1) parse the String to _type                            
            }
            throw new _ioException("file at path "+ filePath+" is not recognized as a Java code unit");
        }
        
        public _sourceUnit replaceInData( String original, String transformed){
            String str = new String(data);
            int beforeHash = Objects.hashCode(str);
            str = str.replace(original, transformed);
            int afterHash = Objects.hashCode(str);
            if( beforeHash != afterHash ){
                if( !filePath.toString().endsWith(".java")){
                    System.out.println( "Modified source for "+filePath+" from "+original+" to "+transformed );
                }
            }
            data = str.getBytes();
            return this;
        }
    }
     */
}
