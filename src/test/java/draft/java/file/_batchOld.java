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
import draft.java._java;
import draft.java.file._classFile;
import draft.java.file._file;
import draft.java.file._javaFile;
import draft.java.file._memoryFile;
import java.nio.file.Paths;
import java.util.Set;

/**
 * Given a root directory, (and optional Predicate) will recursively read in a 
 * the files in this directory and all subdirectories) into memory so they can 
 * be operated on (and potentially written out)
 * 
 * @author Eric
 */
public class _batchOld {
    
    /** the path for where files are read from*/
    public Path rootPath;

    /**
     * the paths of the files read in //public List<Path> sourceFilePaths;
     * (i.e. .java file, .yml
     * file, .pom file)
     */     
    public List<_memoryFile> files;
    
    /** 
     * Collect the paths to all files that were skipped based on the 
     * {@link #skipFilesPathPredicate} 
     */
    public List<Path> filesSkipped;
    
    /** it's a private constructor, use one of the static of() */
    private _batchOld (){ }
    
    /**
     * 
     * @param rootPath
     * @return 
     */
    public static _batchOld of( String rootPath ){
        return of(Paths.get(rootPath));
    }
    
    /**
     * Builds a _batch of in-memory files starting at the rootPath (including all files)
     * @param rootPath the root Path where to read from
     * @return the batch containing all files
     */
    public static _batchOld of(Path rootPath) {
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
    public static _batchOld of( String rootPath, Predicate<String> skipFiles) {
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
    public static _batchOld of(Path rootPath, Predicate<String> skipFiles) {
        _batchOld batch = new _batchOld();
        batch.rootPath = rootPath;

        if (rootPath.toFile().isDirectory()) { //bulk read in a directory
            _fileReader fileReader = new _fileReader(rootPath, skipFiles);
            try {
                Files.walkFileTree(batch.rootPath, fileReader);
                batch.filesSkipped = fileReader.filesSkipped;
            } catch (IOException ioe) {
                throw new _ioException("unable to list files in " + rootPath, ioe);
            }
            batch.files = fileReader.filesRead;
            return batch;
        }
        throw new _ioException("Failure: expected rootPath to be a directory");
    }
    
    
    
    public _batchOld forFilesNamed( Predicate<String>fileNameMatchFn, Consumer<_memoryFile> _fileActionFn ){
        this.files.stream().filter(f-> fileNameMatchFn.test(f.getName())).forEach(_fileActionFn);
        return this;
    }
            
    /**
     * 
     * @param _fileActionFn
     * @return 
     */
    public _batchOld forFiles( Consumer<_memoryFile> _fileActionFn ){
        this.files.forEach(_fileActionFn);
        return this;
    }
    
    /**
     * 
     * @param _fileMatchFn
     * @param _fileActionFn
     * @return 
     */
    public _batchOld forFiles(Predicate<_memoryFile> _fileMatchFn, Consumer<_memoryFile> _fileActionFn){
        this.files.stream().filter(_fileMatchFn).forEach(_fileActionFn);
        return this;
    }
    
    /**
     * 
     * @param astActionFn
     * @return 
     */
    public List<CompilationUnit> forAstCompilationUnits( Consumer<CompilationUnit> astActionFn){
        return forAstCompilationUnits(t->true, astActionFn);
    }
    
    /**
     * 
     * @param astMatchFn
     * @param astActionFn
     * @return 
     */
    public List<CompilationUnit> forAstCompilationUnits( Predicate<CompilationUnit> astMatchFn, Consumer<CompilationUnit> astActionFn){
        List<CompilationUnit> asts = new ArrayList<>();
        this.files.forEach(f -> {
            if(f.getName().endsWith(".java") ){                          
                if( f instanceof _javaFile ){
                    _javaFile _jf = (_javaFile)f;
                    CompilationUnit ast = _jf.type.astCompilationUnit();
                    if( astMatchFn.test(ast)){
                        astActionFn.accept(ast);
                        asts.add(ast);
                    }
                }
                else{
                    _file _f = (_file)f;
                    CompilationUnit ast = Ast.compilationUnit( new String(_f.data));
                    ast.setStorage( Paths.get( f.toUri() ));
                    if( astMatchFn.test(ast)){
                        astActionFn.accept(ast);
                        asts.add(ast);
                    }
                }
            }
        });
        return asts;
    }
    
    /**
     * 
     * @param _codeActionFn
     * @return 
     */
    public List<_java._code> forJavaCode(Consumer<_java._code> _codeActionFn ){
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
    public List<_java._code> forJavaCode(Predicate<_java._code> _codeMatchFn, Consumer<_java._code> _codeActionFn ){
        List<_java._code> _cus = new ArrayList<>();
        this.files.forEach(f -> {
            if(f.getName().endsWith(".java") ){                          
                if( f instanceof _javaFile ){
                    _type _t = ((_javaFile)f).get_type();
                    if(_codeMatchFn.test(_t)){
                        _codeActionFn.accept(_t);
                        _cus.add( _t);
                    }
                } else {
                    //module-info, package-info
                    _java._code _c = null;
                    try{
                        _c = (_java._code)_java.of( Ast.compilationUnit( f.getCharContent(true).toString()) );
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
    public <T extends _type> _batchOld forJavaTypes( Consumer<_type> _typeActionFn){
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
    public <T extends _type> _batchOld forJavaTypes( Class<T> classType, Predicate<T> _typeMatchFn, Consumer<_type> _typeActionFn){
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
     * 
     * @param _classFileActionFn
     * @return 
     */
    public _batchOld forClassFiles( Consumer<_classFile> _classFileActionFn){
        return forClassFiles(f->true, _classFileActionFn);
    }
    
    /**
     * 
     * @param _classFileMatchFn
     * @param _classFileActionFn
     * @return 
     */
    public _batchOld forClassFiles( Predicate<_classFile> _classFileMatchFn, Consumer<_classFile> _classFileActionFn){
        this.files.forEach(f -> {
            if( f instanceof _classFile ){
                _classFile _cf = (_classFile)f;                
                if( _classFileMatchFn.test(_cf)){
                    _classFileActionFn.accept(_cf);                  
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
    public _batchOld forJavaTypes( Predicate<_type> _typeMatchFn, Consumer<_type> _typeActionFn ) {
        return forJavaTypes(_type.class, _typeMatchFn, _typeActionFn);        
    }
    
    /**
     * Reads and stores the Files read as bytes in memory as FileData (the Path
     * & contents of the data in bytes)
     *
     */
    public static class _fileReader extends SimpleFileVisitor<Path> {

        public Path basePath;
        public List<_memoryFile> filesRead = new ArrayList<>();
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
                if( pathString.endsWith(".java") && bytes.length > 0 ){
                    if( path.endsWith("package-info.java")  ){
                        filesRead.add(_file.of( path, "", new String(bytes, "utf8")));
                    } 
                    else if (path.endsWith("module-info.java") ){                        
                        filesRead.add(_file.of( path, "", new String(bytes, "utf8")));
                    } 
                    else{
                        CompilationUnit cu = null;
                        try{
                            cu = Ast.compilationUnit(new String(bytes, "utf8"));
                        }catch(Exception e){                            
                            throw new _ioException("Failed parsing : "+ path, e);
                        }
                        cu.setStorage(path);
                        _type _t = null;
                        try{
                            _t = _type.of(cu);
                            filesRead.add(_javaFile.of( basePath, _t));
                        }catch(Exception e){
                            throw new _ioException("Unable to resolve _type from parsed "+path, e);
                        }
                        //filesRead.add(_javaFile.of( basePath, _type.of(cu) ));
                    }                    
                }
                else if( pathString.endsWith(".class")){
                    
                    //relPath will be like "java\\util\\Map.class"
                    String fullyQualifiedClassName = basePath.relativize(path).toString(); 
                    
                    //remove .class
                    fullyQualifiedClassName = fullyQualifiedClassName.substring(0, fullyQualifiedClassName.lastIndexOf(".class") );
                    //replace "\\" with "."
                    fullyQualifiedClassName = fullyQualifiedClassName.replace("\\", ".");
                    filesRead.add(_classFile.of(basePath, fullyQualifiedClassName, bytes) );
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
