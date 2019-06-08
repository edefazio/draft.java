package draft.java.file;

import com.github.javaparser.ast.CompilationUnit;
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
import draft.java._java;
import java.nio.file.Paths;
import javax.tools.FileObject;

/**
 * Given a root directory, (and optional Predicate) will recursively read in a 
 * the files in this directory and all subdirectories) into memory so they can 
 * be operated on (and potentially written out)
 * 
 * @author Eric
 */
public class _batch {

    interface pathFile {
        
    }
    
    /** the path for where files are read from*/
    public Path rootPath;

    /**
     * the paths of the files read in //public List<Path> sourceFilePaths;
     * (i.e. .java file, .yml
     * file, .pom file)
     */     
    public List<FileObject> files;
    
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
                //batch.startTimestamp = System.currentTimeMillis();
                Files.walkFileTree(batch.rootPath, fileReader);
                //batch.endTimestamp = System.currentTimeMillis();
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
     * 
     * @param _fileActionFn
     * @return 
     */
    public _batch forFiles( Consumer<FileObject> _fileActionFn ){
        this.files.forEach(_fileActionFn);
        return this;
    }
    
    /**
     * 
     * @param _codeActionFn
     * @return 
     */
    public List<_code> forJavaCode(Consumer<_code> _codeActionFn ){
        return forJavaCode( t-> true, _codeActionFn );
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
            //System.out.println("testing file : >>> "+ f.getName()+System.lineSeparator()+"<<");
            if(f.getName().endsWith(".java") ){                          
                //System.out.println( "NAME ENDS WITH .java"+ f.getName());
                if( f instanceof _javaFile ){
                    //System.out.println( "its a java file");
                    _type _t = ((_javaFile)f).get_type();
                    if(_codeMatchFn.test(_t)){
                        _codeActionFn.accept(_t);
                        _cus.add( _t);
                    }
                } else {
                    //System.out.println( "Mod Info Pkg Info "+f.getName());
                    //module-info, package-info
                    _code _c = null;
                    try{
                        _c = (_code)_java.of( Ast.compilationUnit( f.getCharContent(true).toString()) );
                        _c.astCompilationUnit().setStorage( Paths.get( f.toUri() ));
                    }catch(IOException ioe){
                        throw new _ioException("unable to read file \""+ f.getName()+"\"");
                    }                    
                    if(_codeMatchFn.test(_c)){
                        
                        _codeActionFn.accept(_c);
                    }
                    try{
                        f.openOutputStream().write(_c.toString().getBytes());                    
                        _cus.add( _c);
                    } catch(Exception e){
                        throw new _ioException("unable to save code back to file \""+ f.getName()+"\"");
                    }
                }                
            } 
        });
        return _cus;                
    }
    
    /**
     * 
     * @param <T>
     * @param _typeActionFn
     * @return 
     */
    public <T extends _type> _batch forJavaTypes( Consumer<_type> _typeActionFn){
        return forJavaTypes(_type.class, t->true, _typeActionFn);
    }
    
    /**
     * 
     * @param <T>
     * @param classType
     * @param _typeMatchFn
     * @param _typeActionFn
     * @return 
     */
    public <T extends _type> _batch forJavaTypes( Class<T> classType, Predicate<T> _typeMatchFn, Consumer<_type> _typeActionFn){
        this.files.forEach(f -> {
            if( f instanceof _javaFile ){
                _javaFile _jf = (_javaFile)f;
                _type _t = _jf.get_type();
                _typeActionFn.accept(_t);                  
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
        return forJavaTypes(_type.class, _typeMatchFn, _typeActionFn);        
    }
    
    /**
     * Reads and stores the Files read as bytes in memory as FileData (the Path
     * & contents of the data in bytes)
     *
     */
    public static class _fileReader extends SimpleFileVisitor<Path> {

        public Path basePath;
        public List<FileObject> filesRead = new ArrayList<>();
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
                byte[] bytes = Files.readAllBytes(path);
                if( pathString.endsWith(".java")){
                    if( path.endsWith("package-info.java") ){
                        filesRead.add(_file.of( path, "", new String(bytes)));
                    } 
                    else if (path.endsWith("module-info.java") ){                        
                        filesRead.add(_file.of( path, "", new String(bytes)));
                    } 
                    else{
                        CompilationUnit cu = Ast.compilationUnit(new String(bytes));
                        cu.setStorage(path);
                        filesRead.add(_javaFile.of( _type.of(cu) ));
                    }                    
                }
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
}
