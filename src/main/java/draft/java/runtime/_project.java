package draft.java.runtime;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import draft.DraftException;
import draft.java.*;
import draft.java.file.*;
import draft.java.macro._macro;

import javax.annotation.processing.Processor;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaFileObject;
import java.io.InputStream;
import java.io.Writer;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Dynamically compiled project (one or more class files) loaded at runtime
 * that maintains it's own {@link _classLoader} containing the {@link _type}s
 * that were compiled.
 */
public final class _project {

    private _classLoader _cl;

    public _project(){
        this( new _classLoader());
    }

    public _project(_classLoader _cl ) {
        this._cl = _cl;
    }

    public static _project of(){
        return new _project();
    }

    public static _project of( _classLoader classLoader, _type...types ){
        return of(classLoader, null, null, _javaFiles.of( types), null, null);
    }

    public static _project of( Class...classes ){
        //List<_type> ts = new ArrayList<>();
        _javaFiles _jfs = _javaFiles.of( );
        for(int i=0;i<classes.length;i++){
            //System.out.println(" adding "+ classes[i]);
            _type _t = _type.of(classes[i]);
            _jfs.add( _t );
        }
        //Arrays.stream(classes).forEach(c-> _jfs.add( $$.to(c) ) );
        return of((_classLoader)null,null,null,_jfs,null,null);
    }

    /**
     * {@link _type}s into .class files & getAt the .class files
     * into a new _classLoader and return it.
     *
     * @param _types
     * @return a populated _classLoader with the compiled Classes
     */
    public static _project of(_type... _types) {
        return of((_classLoader)null,null,null,_javaFiles.of(_types),null,null);
    }

    /**
     * Compile the _project into .class files & of the .class files
     * into a new _classLoader and return it.
     *
     * @param compilerOpts compiler options
     * @param _types types to be compiled
     * @return a populated _classLoader with the compiled Classes
     */
    public static _project of(_javacOptions compilerOpts, _type... _types) {
        return _project.of( compilerOpts, _javaFiles.of( _types ) );
    }

    /**
     * Build and return a compiled project given the _project, using the
     * javacOpts (and potentially building off of classes from the parent)
     *
     * @param compilerOpts compiler options
     * @param parent a parent project to _1_build off of( may contain dependent
     * classes)
     * @param _types types to be compiled and loaded into the _project
     * @return a new project containing the compiled classes and artifacts as
     * output
     * from the compiler
     */
    public static _project of(_project parent, _javacOptions compilerOpts, _type... _types) {

        return _project.of( parent,
                compilerOpts,
                null,
                _javaFiles.of( _types ),
                null,
                null );
    }

    /**
     *
     * @param parent a parent project to _1_build off of( may contain dependent
     * classes)
     * @param _types the types to _1_build
     * @return compiled classes and artifacts as output from the compiler
     */
    public static _project of(_project parent, _type... _types) {
        return _project.of(
                parent,
                null,
                null,
                _javaFiles.of( _types ),
                null,
                null );
    }

    /**
     *
     * @param _types a .java _sourceFile s to be compiled
     * @param annotationProcessors annotation Processors to use by the compiler
     * @return compiled classes and artifacts as output from the compiler
     */
    public static _project of(_type._hasTypes _types, Processor... annotationProcessors) {

        List<Processor> annProc = new ArrayList<>();
        for( int i = 0; i < annotationProcessors.length; i++ ) {
            annProc.add( annotationProcessors[ i ] );
        }
        return _project.of( (_classLoader)null, null, annProc, _types, null, null );
    }

    public static _project of(Processor annotationProcessor, _type... types) {
        _javaFiles javaFiles = _javaFiles.of( types );
        List<Processor> annProc = new ArrayList<Processor>();
        annProc.add( annotationProcessor );
        return _project.of( (_classLoader)null, null, annProc, javaFiles, null, null );
    }

    /**
     *
     * @param parent a parent project to _1_build off of( may contain dependent classes)
     * @param _types .java _sourceFile s to be compiled
     * @param annotationProcessors annotation Processors to use by the compiler
     * @return
     */
    public static _project of(_project parent, _type._hasTypes _types, Processor... annotationProcessors) {

        List<Processor> annProc = new ArrayList<Processor>();
        for( int i = 0; i < annotationProcessors.length; i++ ) {
            annProc.add( annotationProcessors[ i ] );
        }
        return of( parent, null, annProc, _javaFiles.of().add(_types.list()), null, null );
    }

    /**
     * Compile the java source files and return a populated _classLoader
     *
     * @param compilerOpts
     * @param _types .java _sourceFile s to be compiled
     * @param annotationProcessors annotation Processors to use by the compiler
     * @return
     */
    public static _project of(_javacOptions compilerOpts, _type._hasTypes _types, Processor... annotationProcessors) {

        List<Processor> annProc = new ArrayList<Processor>();
        for( int i = 0; i < annotationProcessors.length; i++ ) {
            annProc.add( annotationProcessors[ i ] );
        }
        return _project.of( (_classLoader)null, compilerOpts, annProc, _types, null, null );
    }

    /**
     * Compiles Loads and returns a new _classLoader containing the newly
     * compiled AND LOADED classes (preloaded in the new _classLoader)
     *
     * @param compilerOpts compiler compilerOpts
     * @param parent a parent project to _1_build off of( may contain dependent
     * classes)
     * @param annotationProcessors annotation Processors to use by the compiler
     * @param _ts types to be compiled
     * @return API containing the compiled classes
     */
    static _project of(_project parent, _javacOptions compilerOpts, List<Processor> annotationProcessors, _type... _ts){

        return of( parent, compilerOpts, annotationProcessors, _javaFiles.of( _ts ), null, null );
    }

    public static _project of( _classLoader _parentClassLoader,
                               _javacOptions compilerOpts,
                               List<Processor> annotationProcessors,
                               _type._hasTypes _types,
                               DiagnosticCollector<JavaFileObject> diagnostics,
                               Writer errOutput) {//optional where to write Javac errors (defaults to System.err)

        if( _types.isEmpty() ) {
            if( _parentClassLoader == null ){
                return new _project( new _classLoader() );
            }
            return new _project( _parentClassLoader );
        }
        _javaFiles javaFiles = null;
        if( _types instanceof _javaFiles ){
            javaFiles = (_javaFiles)_types;
        } else{
            javaFiles = _javaFiles.of().add(_types.list());
        }
        _classLoader _cl = null;
        if( _parentClassLoader != null ) {
            _cl = _parentClassLoader;
        }

        _classLoader _targetClassLoader = _javac.of(
                _cl,
                compilerOpts,
                javaFiles,
                annotationProcessors,
                diagnostics,
                errOutput
        );

        //_targetClassLoader.of( javaFiles.listAt() );
        try {
            // this is where the rubber hits the road... linkAllClasses() will
            // associate the compiled classes and auto generated packages available
            // to the Java Runtime (after the links have been made, they cannot be
            // changed
            _targetClassLoader.linkAllClasses();

            //open the classLoader to accept the recently compiled java source files
            _targetClassLoader._fileMan.open();
            _targetClassLoader._fileMan.sourceJavaFiles.add( javaFiles.list() );
            _targetClassLoader._fileMan.close();
            return new _project( _targetClassLoader );
        }
        catch( Exception e ) {
            throw new DraftException( "Unable to load all Compiled Classes", e );
        }
    }

    /**
     * Compiles Loads and returns a new _classLoader containing the newly
     * compiled AND LOADED classes (preloaded in the new _classLoader)
     *
     * @param compilerOpts compiler compilerOpts
     * @param parent a parent project to _1_build off of( may contain dependent
     * classes)
     * @param javaFiles a directory containing the .java _sourceFile s to be
     * compiled
     * @param annotationProcessors annotation Processors to use by the compiler
     * @param diagnostics collector of diagnostics while the compiler is running
     * @param errOutput where to write error output to
     * @return API containing the compiled classes
     */
    public static _project of(_project parent,
                              _javacOptions compilerOpts,
                              List<Processor> annotationProcessors,
                              _javaFiles javaFiles,
                              DiagnosticCollector<JavaFileObject> diagnostics,
                              Writer errOutput) {//optional where to write Javac errors (defaults to System.err)

        //we might be creating a project that doesnt have any files to compile
        // it might just be configuration files

        if( parent != null ) {
            _classLoader _parentClassLoader = parent.getClassLoader();
            return of( _parentClassLoader, compilerOpts, annotationProcessors, javaFiles, diagnostics, errOutput );
        }
        return of( (_classLoader)null, compilerOpts, annotationProcessors, javaFiles, diagnostics, errOutput );
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("_project :").append(System.lineSeparator());
        this.listClassNames().forEach( s -> sb.append("    ").append(s).append(System.lineSeparator()));
        return sb.toString();
    }

    public <E extends Enum<E>> Class<E> getEnumClass(_enum _e){
        return (Class<E>) getClass( _e.getFullName() );
    }

    public Class<? extends Enum> getEnumClass(String className){
        return (Class<? extends Enum>) getClass( className );
    }

    public Class<? extends Annotation> getAnnotationClass(_annotation _at){
        return (Class<? extends Annotation>) getClass( _at.getFullName() );
    }

    public Class<? extends Annotation> getAnnotationClass(String className){
        return (Class<? extends Annotation>) getClass( className );
    }

    public List<Enum> _enumConstants( _enum _eType ){
        Class clazz = getClass( _eType );
        if( clazz.isEnum() ) {
            Object[] consts = clazz.getEnumConstants();
            List<Enum> les = new ArrayList<>();
            Arrays.stream( consts).forEach(c -> les.add( (Enum)c ) );
            return les;
        }
        throw new DraftException( clazz + " is not an enum " );
    }
    public Enum _enumConstant( _enum _eType, String constantName ) {
        Class clazz = getClass( _eType );
        if( clazz.isEnum() ) {
            return Enum.valueOf( clazz, constantName );
        }
        throw new DraftException( clazz + " is not an enum " );
    }

    public Enum _enumConstant( String fullEnumName, String constantName ) {
        Class clazz = getClass( fullEnumName );
        if( clazz.isEnum() ) {
            return Enum.valueOf( clazz, constantName );
        }
        throw new DraftException( clazz + " is not an enum " );
    }

    /**
     * Create and return a new instance of the _class _c (ASSUMING _c was in
     * the project or parent projects)
     * @param _c the _class model to create
     * @param ctorArgs the constructor args
     * @return the new instance
     */
    public Object _new( _class _c, Object...ctorArgs ){
        return _new(_c.getFullName(), ctorArgs);
    }

    /**
     * Construct and return a new instance of an Class by NAME using
     * ctorArgs
     *
     * @param fullClassName the full NAME of the class
     * @param ctorArgs constructor ARGUMENTS
     * @return a newly constructed _proxy of the Class
     * @throws DraftException if the class is not found or the constructor cannot be called
     */
    public Object _new( String fullClassName, Object... ctorArgs ) {
        Class c = getClass( fullClassName );
        if( c == null ) {
            throw new DraftException( "Could not find class \"" + fullClassName + "\"" );
        }
        if( ctorArgs.length == 0 ) {
            try {
                Constructor ctor = c.getConstructor( new Class[ 0 ] );
                return _new.construct( ctor, ctorArgs );
            }
            catch( NoSuchMethodException ex ) {
                throw new DraftException(
                        "Could not find no arg constructor for " + fullClassName, ex );
            }
            catch( SecurityException ex ) {
                throw new DraftException(
                        "Could not find no arg constructor for " + fullClassName, ex );
            }
        }
        return _new.tryAllCtors( c.getConstructors(), ctorArgs );
    }

    /**
     * Construct and return a new _proxy instance for the _class _c given the optional constructor args
     * @param _c the _class to be constructed (must be in this _project or a parent _project)
     * @param ctorArgs the constructor ARGUMENTS
     * @return the new proxy instance
     */
    public _proxy _proxy( _class _c, Object...ctorArgs){
        return _proxy(_c.getFullName(), ctorArgs);
    }

    /**
     * Construct and return an {@link _proxy} wrapper around a new _proxy
     * of an Object in the _project
     *
     * @param fullClassName fully qualified class NAME (i.e.
     * "com.mypkg.MyClass")
     * @param ctorArgs ARGUMENTS to pass to constructor
     * @return an _proxy wrapper around a constructed _new
     * @throws DraftException if unable to locate the Class of call the
     * constructor
     */
    public _proxy _proxy( String fullClassName, Object... ctorArgs ) {
        return new _proxy( _new( fullClassName, ctorArgs ) );
    }

    public _proxy _proxy( Class clazz, Object...ctorsArgs ){
        return _proxy( clazz.getCanonicalName(), ctorsArgs );
    }

    /**
     * find the first _type that contains a public static main(String[]) method
     * and run it
     */
    public void main(){
        //find the first TYPE with a main method and run it
        List<_type> mainTypes = this.list_types( t-> t.hasMain() );
        if( mainTypes.size() > 0  ){
            main( mainTypes.get(0).getFullName() );
        }
    }

    /**
     * i.e.
     * call("java.util.UUID.generateRandomUUID");
     * call("java.util.UUID.generateRandomUUID()");
     *
     * call("java.lang.System.setProperty(k, v);", "key", "val");
     *
     * @param callSignature
     * @param args
     * @return
     */
    public Object call( String callSignature, Object... args ){
        callSignature = callSignature.trim();
        if( callSignature.endsWith(";")){
            callSignature = callSignature.substring(0, callSignature.length() -1 ).trim();
        }
        if( !callSignature.endsWith(")")){
            callSignature = callSignature +"()";
        }
        Expression ex = Ast.expr(callSignature);
        if( ex.isMethodCallExpr() ){
            MethodCallExpr mce = ex.asMethodCallExpr();
            if( !mce.getScope().isPresent() ){
                throw new DraftException("Must provide class NAME for static method call ");
            }
            String scope = mce.getScope().get().toString();
            //System.out.println( "scope "+scope );
            Class targetClass = getClass( scope );
            //System.out.println( "class "+targetClass );
            String methodName = mce.getName().asString();
            //System.out.println( "NAME "+methodName );

            List<Method> ms = Arrays.stream( targetClass.getMethods())
                    .filter(m -> Modifier.isPublic( m.getModifiers() ) && Modifier.isStatic( m.getModifiers() ) &&
                            m.getName().equals(methodName) && m.getParameterCount() == args.length ).collect(Collectors.toList());

            if( ms.size() == 1 ){ //hopefully, there is only 1 exactly matching
                return _new.invokeStatic(targetClass, ms.get(0), args );
            }
            //System.out.println( "Couldnt find exact... checking VARARGS");
            //now I need to check for varags
            ms = Arrays.stream( targetClass.getMethods())
                    .filter(m -> Modifier.isPublic( m.getModifiers() ) && Modifier.isStatic( m.getModifiers() ) &&
                            m.getName().equals(methodName) && m.isVarArgs() ).collect(Collectors.toList());
            if( ms.size() == 1 ){
                return _new.invokeStatic(targetClass, ms.get(0), args );
            }

            //ok, we find multiple vararg METHODS, try each of 'em
            DraftException dex = null;
            for(int i=0;i<ms.size(); i++){
                try{
                    return _new.invokeStatic(targetClass, ms.get(i), args);
                }catch(DraftException de){
                    dex = de;
                }
            }
            throw dex;
        }
        //System.out.println( "Not a method call");
        return null;
    }

    public Object call(String className, String methodName, Object... args ) {
        if( methodName.equals("main") ){
            if( args.length == 0 ) {
                main(className,new String[0]);
            }
            else{
                String[] strs = new String[ args.length ];
                for(int i=0;i<args.length;i++){
                    strs[i] = args[i].toString();
                }
                main(className,strs);
            }
            return null;
        }
        Class clazz = getClass( className );

        return _new.invokeStatic(clazz, methodName, args );
    }

    public void main( String className, String... args ) {
        Class clazz = getClass( className );
        if( clazz != null ) {
            try {
                Method main = clazz.getMethod( "main", String[].class );
                if( args.length == 0 ) {
                    main.invoke( null, (Object)new String[ 0 ] );
                }
                else {
                    main.invoke( null, (Object)args );
                }
                return;
            }
            catch( NoSuchMethodException ex ) {
                throw new DraftException( "no main method for " + clazz );
            }
            catch( SecurityException ex ) {
                throw new DraftException( "security exception for main method on " + clazz, ex );
            }
            catch( IllegalAccessException ex ) {
                throw new DraftException( "illegal acces for main method for " + clazz, ex );
            }
            catch( IllegalArgumentException ex ) {
                throw new DraftException( "illegal arg for  " + clazz, ex );
            }
            catch( InvocationTargetException ex ) {
                throw new DraftException( "invocation target exception for " + clazz, ex.getCause() );
            }
        }
        throw new DraftException( "No class for \"" + className + "\"" );
    }

    public _project addResourceFile(String filePath, String relativeName, String... linesOfText ) {

        this._cl._fileMan.getResourceFiles().add( filePath, relativeName, linesOfText );
        return this;
    }

    public _project addResourceFile(String filePath, String relativeName, byte[] data ) {

        this._cl._fileMan.getResourceFiles().add( filePath, relativeName, data );
        return this;
    }

    public URL getResourceUrl(String name ) {
        return this._cl.getResource( name );
    }

    public URL getResourceUrl( String filePath, String relativeName ) {
        return this._cl.getResource( filePath, relativeName );
    }

    public InputStream getResourceAsStream(String name ) {
        return this._cl.getResourceAsStream( name );
    }

    public _classLoader getClassLoader() {
        return this._cl;
    }

    public Class getClass( _type _t ){
        return getClass(_t.getFullName());
    }
    public Class getClass( String fullClassName ) {
        return this._cl.getClass( fullClassName );
    }

    public _type get_type(String fullClassName ) {
        return this._cl.get_type( fullClassName );
    }

    public _type get_type(Predicate<? super _type> _typeMatchFn){
        return get_type( _typeMatchFn, false );
    }

    public _type get_type( Predicate<? super _type> _typeMatchFn, boolean includeAncestorProjects ){
        Optional<_type> ot =
                list_types(includeAncestorProjects).stream().filter( _typeMatchFn ).findFirst();
        return ot.orElse(null);
    }

    public Object get( _type _t, String fieldName ){
        Class clazz = getClass( _t.getFullName() );
        try {
            Field f = clazz.getField(fieldName);
            return f.get(null);
        } catch(NoSuchFieldException | IllegalAccessException e){
            throw new DraftException("Could not access field \""+ fieldName+"\" on class \""+clazz+"\"");
        }
    }


    public boolean isEmpty() {
        return this._cl.isEmpty();
    }

    public _classFiles classFiles(){
        return this._cl._fileMan.classFiles;
    }

    /**
     * return a {@link _classFiles} container from the _project potentially
     * including _classFiles that were compiled from parent {@link _classLoader}
     * (includes all dynamically compiled {@link _classFile}s from all
     * {@link _classLoader}s)
     * @param includeAncestorClassLoaders
     * @return
     */
    public _classFiles classFiles( boolean includeAncestorClassLoaders ){
        return _classFiles.of( this._cl.list_classFiles( includeAncestorClassLoaders ));
    }

    public List<Class> listClasses() {
        return this._cl.listClasses();
    }

    public List<Class> listClasses( boolean includeAncestorProjects ) {
        return this._cl.listClasses( includeAncestorProjects );
    }

    public List<Class> listClasses(Predicate<Class> classMatchFn) {
        return this._cl.listClasses(classMatchFn);
    }

    public List<Class> listClasses(
            Predicate<Class> classMatchFn, boolean includeAncestorProjects){
        return this._cl.listClasses( includeAncestorProjects ).stream()
                .filter(classMatchFn).collect(Collectors.toList());
    }

    public List<_classFile> list_classFiles() {
        return this._cl.list_classFiles( );
    }

    public List<_classFile> list_classFiles(boolean includeAncestorProjects) {
        return this._cl.list_classFiles(includeAncestorProjects);
    }

    public List<_classFile> list_classFiles(Predicate<_classFile> _classFileMatchFn) {
        return this._cl.list_classFiles(_classFileMatchFn);
    }

    public List<_classFile> list_classFiles(
            Predicate<_classFile> _classFileMatchFn, boolean includeAncestorProjects) {
        return this._cl.list_classFiles(_classFileMatchFn, includeAncestorProjects);
    }

    public List<String> listClassNames() {
        return this._cl.listClassNames();
    }

    public List<String> listClassNames( boolean includeAncestorProjects ) {
        return this._cl.listClassNames( includeAncestorProjects );
    }

    public List<Package> listPackages() {
        return this._cl.listPackages();
    }

    public List<Package> listPackages( boolean includeAncestorProjects ) {
        return this._cl.listPackages( includeAncestorProjects );
    }

    public List<Package> listPackages(Predicate<Package> packageMatchFn) {
        return this._cl.listPackages(packageMatchFn);
    }

    public List<Package> listPackages(
            Predicate<Package> packageMatchFn, boolean includeAncestorProjects) {
        return this._cl.listPackages(packageMatchFn, includeAncestorProjects);
    }

    public List<_type> list_types() {
        return this._cl.list_types();
    }

    public List<_type> list_types(boolean includeAncestorProjects ) {
        return this._cl.list_types( includeAncestorProjects );
    }


    public List<_type> list_types(Predicate<? super _type> _typeMatchFn ) {
        return this._cl.list_types( _typeMatchFn );
    }

    public List<_type> list_types(
            Predicate<? super _type> _typeMatchFn,
            boolean includeAncestorProjects ) {

        return this._cl.list_types( _typeMatchFn, includeAncestorProjects );
    }
}