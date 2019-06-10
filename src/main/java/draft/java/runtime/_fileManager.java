package draft.java.runtime;

import draft.DraftException;
import draft.Text;
import draft.java.file.*;

import javax.tools.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

/**
 * Implementation of a and {@link JavaFileManager}
 *
 * Used by the runtime javac compiler to manage the (in memory) files
 * like the {@link _javaFiles} {@link _classFiles} and the {@link _files} files
 * used and created during javac compilation
 *
 * Also "forwards" to the StandardJavaFileManager containing the existing runtime
 * (i.e. when running in an editor, consists of all Classes within the editors
 * current context, (library jars like Log4J, JUnit, and all project dependencies
 * will be present)
 *
 * @author Eric
 */
public final class _fileManager
        extends ForwardingJavaFileManager<StandardJavaFileManager> {

    protected final _classLoader cl;

    public _fileManager( _classLoader cl ) {
        this( getStandardJavaFileManager(), cl );
    }

    public _fileManager( StandardJavaFileManager fileMan ) {
        this( fileMan, new _classLoader());
    }
    public _fileManager( StandardJavaFileManager fileMan, _classLoader _cl ) {
        super( fileMan );
        this.cl = _cl;
    }

    public StandardJavaFileManager getFileManager(){
        return this.fileManager;
    }

    /**
     * Since _classLoader is an _proxy for JavaFileManager this method is
     * required (NOT a user level API)
     *
     * @throws IOException
     */
    public void flush() throws IOException {
        fileManager.flush();
    }

    public void open(){
        this.classFiles.open();
        this.generatedJavaFiles.open();
        this.sourceJavaFiles.open();
    }

    /**
     * Make it so the files (source files and class files) cannot be modified...
     *
     * NOTE: when you getAt(...) or listAt(...) the _javaFile source files or _project, if CLOSED
     * a COPY is returned, if OPEN the original is returned
     *
     * Since _classLoader is an _proxy for JavaFileManager this method is
     * required (NOT a user level API)
     *
     * @throws IOException
     */
    public void close() throws IOException {

        fileManager.close();

        this.classFiles.close();
        this.generatedJavaFiles.close();
        this.sourceJavaFiles.close();

        //we should be able to keep resourceFiles
        //this.resourceFiles.close();
        //now I need to accept closing to the local _classFiles, _javaFiles, & _files
    }

    /**
     * stores physical _classFiles (bytecode)
     * to be <B> written / loaded / stored</B>
     */
    public final _classFiles classFiles = new _classFiles();

    /**
     * (_class, _interface)...models to be <B>read</B> as input files
     * and compiled by javac (if necessary)
     *
     * TRADITIONALLY this might be used to put source code that only
     * gets read/compiled/used in the event it is referenced from code
     * that is being compiled. (similar to a library path)
     */
    public _javaFiles sourceJavaFiles = new _javaFiles(
            StandardLocation.SOURCE_PATH.getName(), false );

    /**
     * Corresponds with the StandardLocation.SOURCE_OUTPUT
     *
     * (_class, _interface)...{@link draft.java._type}s that are generated during
     * javac.
     *
     * TRADITIONALLY this is where a Java annotation processor will place
     * generated source files as a result of annotation Processing
     */
    public _javaFiles generatedJavaFiles
            = new _javaFiles( StandardLocation.SOURCE_OUTPUT.getName(), true );

    /**
     * If resource artifacts
     * (other than ".java" source code and ".class" files)
     * are generated during compilation, they are to be written here
     * (i.e. json, xml, etc)
     */
    public _files resourceFiles = new _files();

    /**
     * At the moment, I just create a new one every time.
     *
     * BEWARE of using a cached _new if you want to create and getAt adHoc
     * instances at runtime..
     * Basically the standard file manager is LIKE a single _new
     *
     * @return
     */
    public static StandardJavaFileManager getStandardJavaFileManager() {
        return ToolProvider.getSystemJavaCompiler()
                .getStandardFileManager(
                        null, //use default DiagnosticListener
                        null, //use default Locale
                        null ); //use default CharSet
    }

    /**
     * @throws IllegalArgumentException {@inheritDoc}
     * @throws IllegalStateException {@inheritDoc}
     */
    @Override
    public boolean handleOption( String current, Iterator<String> remaining ) {
        return fileManager.handleOption( current, remaining );
    }

    /*
    public Iterable<Set<Location>> listLocationsForModules(Location location) throws IOException {
        System.out.println("No Modules");
        return Collections.EMPTY_LIST;
    }
    */

    @Override
    public int isSupportedOption( String option ) {
        return fileManager.isSupportedOption( option );
    }

    /**
     * StandardLocation#ANNOTATION_PROCESSOR_PATH
     *
     * @param location
     * @return
     */
    @Override
    public _classLoader getClassLoader( Location location ) {
        return this.cl;
    }

    /**
     * @param location
     * @param file
     * @return 
     * @throws IllegalStateException {@inheritDoc}
     */
    @Override
    public String inferBinaryName( Location location, JavaFileObject file ) {

        //StackTraceElement[] ste = Thread.currentThread().getStackTrace();
        //if( file.getName().equals("/Base.java") ){
            //System.out.println( "Inferring binary NAME for "+ file.getName() );
            //System.out.println( "TYPE" + file.getClass() );
            //System.out.println( "Caller : " + ste[2] );
        //    _javaFile jf = (_javaFile)file;
            //System.out.println ( jf.get_type() );
        //    for(int i=0;i< ste.length; i++){
        //        System.err.println(ste[i]);
        //    }
        //}
        if( file instanceof _javaFile) {
            //System.out.println( "Inferring binary NAME for _source.file "+ file.getName() );
            return "." + ((_javaFile)file).type.getFullName();
            //return ((_javaFile)file).getBinaryName();
        }
        if( file instanceof _classFile) {
            _classFile _cf = (_classFile)file;
            //System.out.println( "**** Inferring binary NAME for _classByteCode to : " + _classFile.getFullName());
            return _cf.getFullName();
        }
        else {
            return  fileManager.inferBinaryName( location, file );
            //System.out.println( ">>>> Inferring binary NAME for other to : " + binName);
            //return binName;
        }
    }

    @Override
    public boolean hasLocation( Location location ) {
        //System.out.println( "*** HAS LOCATION "+ location );
        if( location == StandardLocation.SOURCE_PATH ) {
            return true; //we have  the sourceJavaFiles, it may be empty, but we have it
        }
        if( location == StandardLocation.CLASS_OUTPUT ) {
            return true; //we write classes to the _classLoader
        }
        if( location == StandardLocation.SOURCE_OUTPUT ) {
            return true; //we can write source during compilation, (to generatedJavaFiles)
        }
        if( location == StandardLocation.NATIVE_HEADER_OUTPUT ) {
            return true;
        }
        return  fileManager.hasLocation( location );
        //System.out.println( "HAS LOCATION " + _classFiles + hl );
        //return hl;
    }

    public boolean isEmpty() {
        return classFiles.isEmpty()
                && generatedJavaFiles.isEmpty()
                && sourceJavaFiles.isEmpty()
                && resourceFiles.isEmpty();
    }

    /** @return Java source files (passed in to be compiled)*/
    public _javaFiles getJavaSourceFiles() {
        return this.sourceJavaFiles;
    }

    public _files getResourceFiles(){
        return this.resourceFiles;
    }

    /** @return Java source files (generated by annotationProcessing)*/
    public _javaFiles getJavaGeneratedFiles() {
        return this.generatedJavaFiles;
    }

    public _fileManager addResourceFile(String filePath, String... linesOfText ) {

        //resourceFiles.add( _file.of( filePath, relativeName, linesOfText ) );
//        resourceFiles.add( _file.of( Paths.get(filePath), linesOfText ) );
        resourceFiles.add( new _file( Paths.get(filePath), Text.combine(linesOfText).getBytes() ) );
        return this;
    }

    //public _fileManager addResourceFile(
    //        String filePath, String relativeName, byte[] data ) {
    public _fileManager addResourceFile(String filePath, byte[] data ) {    

        //resourceFiles.add( _file.of( filePath, relativeName, data ) );
        //resourceFiles.add( _file.of( Paths.get( filePath), relativeName, data ) );
        resourceFiles.add( new _file( Paths.get(filePath), data ) );
        return this;
    }

    /*
    public _fileManager addResourceFile(
        String filePath, String relativeName, String... linesOfText ) {

        //resourceFiles.add( _file.of( filePath, relativeName, linesOfText ) );
        resourceFiles.add( _file.of( Paths.get(filePath), relativeName, linesOfText ) );
        return this;
    }

    public _fileManager addResourceFile(
            String filePath, String relativeName, byte[] data ) {

        //resourceFiles.add( _file.of( filePath, relativeName, data ) );
        resourceFiles.add( _file.of( Paths.get( filePath), relativeName, data ) );
        return this;
    }
    */

    public _fileManager setLocation(Location loc, Iterable<? extends File> path )
            throws IOException {
        this.fileManager.setLocation( loc, path );
        return this;
    }

    /**
     * @param a
     * @param b
     * @return 
     * @throws IllegalArgumentException {@inheritDoc}
     */
    @Override
    public boolean isSameFile( FileObject a, FileObject b ) {
        //System.out.println( "is same file "+ a +" "+ b );
        if( a instanceof _javaFile) {
            _javaFile _sfa = (_javaFile)a;
            return _sfa.equals( b );
        }
        if( a instanceof _classFile) {
            _classFile _cfa = (_classFile)a;
            return _cfa.equals( b );
        }
        if( a instanceof _file) {
            return  a.equals( b );
        }
        return fileManager.isSameFile( a, b );
    }

    @Override
    public List<JavaFileObject> list(
            Location location,
            String packageName,
            Set<JavaFileObject.Kind> kinds,
            boolean recurse )
            throws IOException {

        //LOG.info( "Listing " + location + " " + PACKAGE_NAME + " " + recurse );
        List<JavaFileObject> files = new ArrayList<JavaFileObject>();

        if( kinds.contains( JavaFileObject.Kind.CLASS ) ) {
            //System.out.println( "LISTING _CLASSLOADER classes for " + location +" ");
            List<_classFile> _cfs = this.classFiles.list( packageName, recurse );
            //System.out.println( "--->> FOUND "+ _classFiles );
            files.addAll( _cfs );
        }
        if( location == StandardLocation.SOURCE_PATH
                && kinds.contains( JavaFileObject.Kind.SOURCE ) ) {
            //System.out.println( "Getting source files from sourceJavaFiles");
            files.addAll( sourceJavaFiles.listFiles( packageName, recurse ) );
        }
        if( location == StandardLocation.SOURCE_OUTPUT
                && kinds.contains( JavaFileObject.Kind.SOURCE ) ) {
            //System.out.println( "Getting source files from generatedJavaFiles");
            files.addAll( generatedJavaFiles.listFiles( packageName, recurse ) );
        }

        //now getAt localTypes of the files from the "traditional" fileManager
        Iterable<JavaFileObject> parentFiles
                = fileManager.list( location, packageName, kinds, recurse );

        Iterator<JavaFileObject> it = parentFiles.iterator();
        while( it.hasNext() ) {
            files.add( it.next() );
        }
        ClassLoader parent = this.cl.getParent();
        if( parent instanceof _classLoader ) {
            _classLoader _parentCL = (_classLoader)parent;
            List<JavaFileObject> parentCLFiles
                    = _parentCL.list( location, packageName, kinds, recurse );

            for( int i = 0; i < parentCLFiles.size(); i++ ) {
                JavaFileObject fo = parentCLFiles.get( i );
                boolean shouldAdd = true;
                for( int j = 0; j < files.size(); j++ ) {
                    //dont accept parent files that conflict with child files
                    if( files.get( j ).isNameCompatible( fo.getName(), fo.getKind() ) ) {
                        shouldAdd = false;
                        break;
                    }
                }
                if( shouldAdd ) {
                    files.add( fo );
                }
            }
        }
        return files;
    }

    @Override
    public FileObject getFileForInput(
            Location location,
            String packageName,
            String relativeName )
            throws IOException {

        //System.out.println( "getFileForInput " + location + " " + PACKAGE_NAME + " " + relativeName );
        if( location.getName().equals( this.sourceJavaFiles.getName() ) ) {
            return this.sourceJavaFiles.getFile( packageName, relativeName );
        }
        if( location.getName().equals( this.generatedJavaFiles.getName() ) ) {
            return this.generatedJavaFiles.getFile( packageName, relativeName );
        }
        if( location.getName().equals( this.resourceFiles.getName() ) ) {
            return this.resourceFiles.getFile( packageName, relativeName );
        }
        return fileManager.getFileForInput( location, packageName, relativeName );
    }

    @Override
    public JavaFileObject getJavaFileForInput(
            Location location,
            String className,
            JavaFileObject.Kind kind )
            throws IOException {

        //System.out.println( "Checking CP for "+ className);
        //first try getting it from the Local classPath
        if( location == StandardLocation.CLASS_PATH
                && kind == JavaFileObject.Kind.CLASS ) {
            _classFile _cf = classFiles.get( className );
            if( _cf != null ) {
                return _cf;
            }
        }
        //System.out.println( "Checking SP for "+ className);
        if( location == StandardLocation.SOURCE_PATH
                && kind == JavaFileObject.Kind.SOURCE ) {
            _javaFile _sf = this.sourceJavaFiles.getFile( className );
            if( _sf != null ) {
                return _sf;
            }
        }
        //System.out.println( "Checking SOP for "+ className);
        if( location == StandardLocation.SOURCE_OUTPUT
                && kind == JavaFileObject.Kind.SOURCE ) {
            _javaFile _sf = this.generatedJavaFiles.getFile( className );
            if( _sf != null ) {
                return _sf;
            }
        }
        //System.out.println( "Checking Parent for "+ className);
        //2) check the parent _classLoader for
        ClassLoader parent = this.cl.getParent();
        if( parent instanceof _classLoader ) {
            _classLoader _parentCl = (_classLoader)parent;
            return _parentCl._fileMan.getJavaFileForInput( location, className, kind );
        }
        //System.out.println( "Checking Default for "+ className);
        //3) now check the "default" fileManager... when running
        // inside the editor
        return fileManager.getJavaFileForInput( location, className, kind );
    }

    @Override
    public JavaFileObject getJavaFileForOutput(
            Location location,
            String className,
            JavaFileObject.Kind kind,
            FileObject sibling ) {

        // the fileManager/compiler wants to generate a ".java" source file...
        // probably during an annotation processing operation
        if( location.getName().equals( this.generatedJavaFiles.getName() )
                && kind == JavaFileObject.Kind.SOURCE ) {
            //return this.generatedJavaFiles.getOrCreateFile( className );
            return this.generatedJavaFiles.getOrCreateFile( className );
        }
        // the compiler wants to write a new source file in the sourcePath
        if( location.getName().equals( this.sourceJavaFiles.getName() )
                && kind == JavaFileObject.Kind.SOURCE ) {
            return this.sourceJavaFiles.getOrCreateFile( className );
        }

        //any class files are written to the
        if( kind == JavaFileObject.Kind.CLASS ) {
            return this.reserveClassFile( className );
        }
        throw new DraftException(
                "Cant getAt Java File for " + className + " of " + kind + " in " + location );
    }

    /**
     * Reserves a {@link _classFile} for the _classLoader to write compiled
     * bytecode to based on the className. (not a user-level API but rather
     * an API
     *
     * @param className
     * @return the _classByteCode
     */
    protected JavaFileObject reserveClassFile( String className ) {
        _classFile _cf = classFiles.get( className );

        if( _cf != null ) { // return the already-loaded class from ad hoc class loader
            return _cf;
        }
        //reserve a new to
        return classFiles.reserve( className );
    }

    @Override
    public FileObject getFileForOutput(
            Location location,
            String packageName,
            String relativeName,
            FileObject sibling )
            throws IOException {

        //the compiler wants to write new resource file in the
        //in memory resourceOutput
        if( location == null ) {
            return this.resourceFiles.reserveFile( packageName, relativeName );
        }
        if( location.equals( _files.LOCATION ) ) {
            return this.resourceFiles.reserveFile( packageName, relativeName );
        }
        return fileManager.getFileForOutput(location, packageName, relativeName, sibling );
    }
}
