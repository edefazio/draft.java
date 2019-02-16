package draft.java.runtime;

import draft.DraftException;
import draft.java.*;
import draft.java.file.*;
import draft.java.io._in_classLoader;

import javax.tools.*;
import java.io.*;
import java.net.URL;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * A implementation of a {@link ClassLoader}
 * _1_build for compiling and loading draft classes at runtime.
 *
 * An implementation of  {@link _in_classLoader._typeCacheClassLoader}
 * because it maintains a _fileManager reference to the {@link _type}s
 * Models (i.e. the source of the Classes that were generated in this
 * _classLoader)
 *
 * @author Eric
 */
public final class _classLoader
        extends ClassLoader
        implements _in_classLoader._typeCacheClassLoader {

    public final _fileManager _fileMan;

    public static _classLoader of( _classFiles _cfs ){
        return new _classLoader( _cfs );
    }

    public _classLoader() {
        this._fileMan = new _fileManager( this );
    }

    public _classLoader( _classFiles _cfs ){
        this._fileMan = new _fileManager( this );
        this._fileMan.classFiles.add( _cfs );
    }

    public _classFiles classFiles(){
        return this._fileMan.classFiles;
    }
    /**
     * Create a child _classLoader of the _parentCl _classLoader
     *
     * @param _parentCl
     */
    public _classLoader( _classLoader _parentCl ) {
        super( _parentCl );
        this._fileMan = new _fileManager( _parentCl._fileMan.getFileManager(), this );
    }

    /**
     * @return a listAt of names for all Resources in THIS classLoader
     * (not ancestor classLoaders)
     */
    public List<String> listResourceFileNames() {
        return listResourceFileNames( false );
    }

    /**
     * Returns a listAt of names for all Resources in ancestor classLoaders)
     * @param includeAncestorClassLoaders
     * @return
     */
    public List<String> listResourceFileNames( boolean includeAncestorClassLoaders ) {
        if( includeAncestorClassLoaders ) {
            Set<String> names = new HashSet<String>();
            List<_classLoader> ancestry = listAncestry( this );
            ancestry.forEach( cl -> names.addAll( cl._fileMan.resourceFiles.listNames() ) );
            List<String> rnames = new ArrayList<String>();
            rnames.addAll( names );
            return rnames;
        }
        return this._fileMan.resourceFiles.listNames();
    }

    public List<_file> listResourceFiles() {
        return listResourceFiles( false );
    }

    public List<_file> listResourceFiles(boolean includeAncestorClassLoaders ) {
        if( includeAncestorClassLoaders ) {
            List<_classLoader> _ancestry = listAncestry( this );
            Map<URL, _file> resources = new HashMap<URL, _file>();
            for( int i = 0; i < _ancestry.size(); i++ ) {
                resources.putAll(
                        _ancestry.get( i ).mapURLToResource_file(false ) );
            }
            List<_file> allResources = new ArrayList<_file>();
            allResources.addAll( resources.values() );
            return allResources;
        }
        return this._fileMan.resourceFiles.list();
    }

    public List<_file> listResourceFiles(
            Predicate<? super _file> _fileMatchFn,
            boolean includeAncestorClassLoaders ) {

        List<_file> rfs = listResourceFiles( includeAncestorClassLoaders );
        return rfs.stream().filter( _fileMatchFn ).collect( Collectors.toList() );
    }

    /**
     * Lists localTypes top level types declared in THIS classLoader
     * (NOT {@link _type}s from parent/ancestor {@link _classLoader}s)
     *
     * @return localTypes _project declared in this classLoader
     */
    public List<_type> list_types() {
        return list_types( false );
    }

    /**
     * Gets the "origin" (application) {@link ClassLoader} that is the parent
     * {@link ClassLoader} to localTypes child {@link _classLoader} instances
     *
     * @param _cl the current _classLoader to check
     * @return the Origin classLoader (or the parent classLoader of the first
     * _classLoader in the _classLoader hierarchy)
     */
    protected static ClassLoader getOriginClassLoader( _classLoader _cl ) {
        if( _cl.getParent() instanceof _classLoader) {
            return getOriginClassLoader((_classLoader)_cl.getParent() );
        }
        return _cl.getParent();
    }

    public List<JavaFileObject> list(
            JavaFileManager.Location location,
            String packageName,
            Set<JavaFileObject.Kind> kinds,
            boolean recurse )
            throws IOException {

        return this._fileMan.list( location, packageName, kinds, recurse );
    }

    /**
     * listAt the ancestry of this _classLoader (other parent _classLoaders)
     *
     * @param _cl
     * @return
     */
    public static List<_classLoader> listAncestry(_classLoader _cl ) {
        List<_classLoader> collected = new ArrayList<_classLoader>();
        return listAncestry( collected, _cl );
    }

    /**
     * Returns a List of localTypes parent _classLoader instances highest level
     * first i.e.
     * (_grandparentCL, parentCL, childCL)
     *
     * @param collected the List of collected {@link _classLoader}s
     * @param cl the classLoader to test
     * @return listAt of _classLoaders in order (grandparent, parent, child)
     */
    public static List<_classLoader> listAncestry( List<_classLoader> collected, ClassLoader cl ) {
        if( cl instanceof _classLoader) {
            listAncestry( collected, cl.getParent() );
            collected.add((_classLoader)cl );
        }
        return collected;
    }

    public int count(){
        return count( false );
    }

    public int count(boolean includeAncestorClassLoader){
        return this.listClasses(includeAncestorClassLoader).size();
    }

    /**
     * lists localTypes top level {@link _type}s defined in this classLoader
     * (and optionally localTypes {@link _type}s in localTypes ancestor
     * {@link _classLoader}s
     *
     * @param includeAncestorClassLoaders whether we want to look at localTypes
     * _classLoader instances
     * in the ancestry( parent ClassLoaders that are instances of _classLoader)
     * for example:
     * _classLoader _parentCL = _load.of( _class.of("A"));
     * _classLoader _childCL = _load.of(_parent, _class.of("B"));
     *
     * List<_type> _project = _childCL.listAt(true); //contains "A" and "B"
     * List<_type> _project = _childCL.listAt(false); //contains "B"
     *
     * List<_type> _project = _parentCL.listAt(true); //contains "A"
     *
     * @return the {@link _type}s from this classLoader alone or localTypes
     * {@link _type}s
     * from
     */
    public List<_type> list_types(boolean includeAncestorClassLoaders ) {
        if( includeAncestorClassLoaders ) {
            // we need to do this because of hierarchial classLoader
            // namespace rules, so basically is a parent _classLoader
            // has a version of _class "A" and a child classLoader
            // has a version of _class "A" then we need to ensure
            // that when we list_types the _project in the hierarchy, we include
            // the child _classLoader version of "A" (but also any _project
            //that are in the parent  _classLoader that may
            //not exist in the child _classLoader
            Map<String, _type> nameToType = mapNameTo_type( true );
            List<_type> allTypesList = new ArrayList<_type>();
            allTypesList.addAll( nameToType.values() );
            return allTypesList;
        }
        List<_type> all = new ArrayList<_type>();
        all.addAll( _fileMan.sourceJavaFiles.list() );
        all.addAll( _fileMan.generatedJavaFiles.list() );
        return all;
    }

    /**
     * Lists local top level {@link _type}s declared in THIS classLoader
     * (NOT {@link _type}s from parent/ancestor {@link _classLoader}s)
     * that pass the predicate
     *
     * i.e.
     * //to return only interfaces
     * List<_type> _interfaces = _cl.listAt(t-> t instanceof _interface);
     *
     * //listAt only enums
     * List<_type> _enums = _cl.listAt(t-> t instanceof _enum);
     *
     * //all types in the root directory
     * List<_type> inRootDirectory = _cl.listAt(t-> t.getPackage().isEmpty());
     *
     * @param _typeMatchFn the predicate filter to to to _project
     * @return
     */
    public List<_type> list_types(Predicate<? super _type> _typeMatchFn ) {
        return list_types( _typeMatchFn, false );
    }

    /**
     * Lists top level {@link _type}s declared in THIS classLoader
     * (and optionally ancestor classLoaders)
     * that pass the predicate
     *
     * i.e.
     * //to return only interfaces
     * List<_type> _interfaces = _cl.listAt(t-> t instanceof _interface);
     *
     * //listAt only enums
     * List<_type> _enums = _cl.listAt(t-> t instanceof _enum);
     *
     * //all types in the root directory
     * List<_type> inRootDirectory = _cl.listAt(t-> t.getPackage().isEmpty());
     *
     * @param _typeMatchFn the predicate filter to _project
     * @param includeAncestorClassLoaders ancestor _classLoaders
     * @return
     */
    public List<_type> list_types(
            Predicate<? super _type> _typeMatchFn, boolean includeAncestorClassLoaders ) {

        if( includeAncestorClassLoaders ) {
            List<_type> allTypes = list_types( true );
            return allTypes.stream().filter( _typeMatchFn ).collect( Collectors.toList() );
        }
        List<_type> localTypes = list_types();
        return localTypes.stream().filter( _typeMatchFn ).collect( Collectors.toList() );
    }

    public List<_classFile> list_classFiles(){
        return this._fileMan.classFiles.list();
    }

    public List<_classFile> list_classFiles(boolean includeAncestorProjects){

        Map<String, _classFile>nameTo_classFile = new HashMap<>();

        //we ALWAYS recursively put() classes from the parent _classLoader first
        // then (as wind back down the recursion) we add child _classLoader _classFiles, that
        // when the NAME is the same, will override the previously added ancestor
        // version of the _classFile
        if( includeAncestorProjects && this.getParent() instanceof _classLoader ) {
            _classLoader _parent = (_classLoader)this.getParent();
            nameTo_classFile.putAll( _parent._fileMan.classFiles.mapNameTo_classFile() );
        }
        nameTo_classFile.putAll(_fileMan.classFiles.mapNameTo_classFile());

        List<_classFile>cfs = new ArrayList<_classFile>();
        cfs.addAll(nameTo_classFile.values());
        return cfs;
    }

    public List<_classFile> list_classFiles( Predicate<_classFile> _classFileMatchFn ){
        return list_classFiles().stream().filter(_classFileMatchFn).collect(Collectors.toList());
    }


    public List<_classFile> list_classFiles(
            Predicate<_classFile> _classFileMatchFn, boolean includeAncestorClassLoaders ){
        return list_classFiles(includeAncestorClassLoaders).stream()
                .filter(_classFileMatchFn).collect(Collectors.toList());
    }

    public List<Class> listClasses() {
        return listClasses( false );
    }


    public List<Class>listClasses(Predicate<Class>classMatchFn ){
        return this.listClasses().stream().filter(classMatchFn).collect(Collectors.toList());
    }

    public List<Class>listClasses(
            Predicate<Class>classMatchFn, boolean includeAncestorClassLoaders ){
        return this.listClasses(includeAncestorClassLoaders)
                .stream().filter(classMatchFn).collect(Collectors.toList());
    }

    public List<Class> listClasses( boolean includeAncestorClassLoaders ) {
        List<Class> classes = new ArrayList<Class>();
        List<_type> _types;
        if( includeAncestorClassLoaders ) {
            _types = list_types( true );
        }
        else {
            _types = list_types( false );
        }
        for( int i = 0; i < _types.size(); i++ ) {
            classes.add( this.getClass( _types.get( i ).getFullName() ) );
        }
        return classes;
    }

    protected Map<URL, _file> mapURLToResource_file(boolean includeAncestorClassLoaders ) {

        Map<URL, _file> nameToResource = new HashMap<URL, _file>();
        if( includeAncestorClassLoaders ) {
            List<_classLoader> ancestry = listAncestry( this );
            for( int i = 0; i < ancestry.size(); i++ ) {
                nameToResource.putAll(
                        ancestry.get( i ).mapURLToResource_file( false ) );
            }
            return nameToResource;
        }
        this._fileMan.resourceFiles.files.forEach( r -> nameToResource.put( r.getURL(), r ) );
        return nameToResource;
    }

    /**
     * Returns a Map of names associated wioth types (NOTE only copies are
     * returned)
     *
     * @param includeAncestorClassLoaders look thorough parent classLoaders
     * @return a compose of names and types
     */
    protected Map<String, _type> mapNameTo_type( boolean includeAncestorClassLoaders ) {
        Map<String, _type> nameToType = new HashMap<String, _type>();
        if( includeAncestorClassLoaders ) {
            List<_classLoader> ancestry = _classLoader.listAncestry( this );
            for( int i = 0; i < ancestry.size(); i++ ) {
                List<_type> types = ancestry.get( i ).list_types();
                types.forEach( t -> nameToType.put( t.getFullName(), t.copy() ) );
            }
            return nameToType;
        }
        List<_type> types = list_types();
        types.forEach( t -> nameToType.put( t.getFullName(), t.copy() ) );
        return nameToType;
    }

    /**
     * Lists the fully qualified names for locally declared {@link _type}s
     * declared in this _classLoader (not it's parent or other ancestor
     * {@link _classLoader}s)
     *
     * @return a List of TYPE names that exist in this _classLoader
     */
    public List<String> listClassNames() {
        return listClassNames( false );
    }

    /**
     * listAt the names of all types in this _classLoader and (optionally in
     * ancestor classLoaders
     *
     * @param includeAncestorClassLoaders
     * @return
     */
    public List<String> listClassNames( boolean includeAncestorClassLoaders ) {
        List<String> names = new ArrayList<String>();
        if( includeAncestorClassLoaders ) {
            List<_classLoader> _ancestry = listAncestry( this );
            for( int i = 0; i < _ancestry.size(); i++ ) {
                names.addAll( _ancestry.get( i )._fileMan.sourceJavaFiles.listTypeNames() );
                names.addAll( _ancestry.get( i )._fileMan.generatedJavaFiles.listTypeNames() );
            }
        }
        names.addAll( _fileMan.sourceJavaFiles.listTypeNames() );
        names.addAll( _fileMan.generatedJavaFiles.listTypeNames() );
        return names;
    }

    public _type get_type( Class clazz ){
        return this._fileMan.sourceJavaFiles.get(clazz);
    }

    /**
     * Gets the _type given the fully qualified className (i.e.
     * <PRE>
     * _type _t = getAt("org.example.MyClass");
     * _type _nest = getAt("org.example.MyClass$NestedClass");
     * </PRE>
     *
     * uses this strategy:
     * 1) check source Java Files for exact fullClassName (org.example.MyClass)
     * 2) check source Java Files for simple NAME (MyClass)
     * 3) check generated Java Files for exact fullClassName (org.example.MyClass)
     * 4) check generated Java Files for simple NAME(MyClass)
     *
     * @param fullClassName
     * @return
     */
    public _type get_type(String fullClassName ) {
        //try the "local classes" first
        _type _t = this._fileMan.sourceJavaFiles.get( fullClassName );
        if( _t != null ){
            return _t;
        }
        _t = this._fileMan.generatedJavaFiles.get( fullClassName );
        if( _t != null ){
            return _t;
        }
        //now try super classLoaders
        if( this.getParent() instanceof _classLoader){
            _classLoader _parent = (_classLoader)getParent();
            return _parent.get_type( fullClassName );
        }
        return null;
    }


    public _type get_type( Predicate<? super _type> _typeMatchFn ) {
        //try the "local classes" first
        _type _t = this._fileMan.sourceJavaFiles.get( _typeMatchFn );
        if( _t != null ){
            return _t;
        }
        _t = this._fileMan.generatedJavaFiles.get( _typeMatchFn );
        if( _t != null ){
            return _t;
        }
        //now try super classLoaders
        if( this.getParent() instanceof _classLoader){
            _classLoader _parent = (_classLoader)getParent();
            return _parent.get_type( _typeMatchFn );
        }
        return null;
    }


    public boolean isEmpty() {
        return this._fileMan.isEmpty();
    }

    public _fileManager getFileManager(){
        return this._fileMan;
    }

    @Override
    public Package getPackage( String packageName ) {
        //ALWAYS use . notation
        packageName = packageName.replace( "/", "." );
        Package p = super.getPackage( packageName );
        if( p != null ) {
            return p;
        }
        ClassLoader parent = getParent();
        if( parent instanceof _classLoader) {
            return ((_classLoader)parent).getPackage( packageName );
        }
        return this._fileMan.classFiles.getPackage( packageName );
    }

    /**
     * Gets All packages (including those defined in parent classLoaders)
     *
     * @return
     */
    @Override
    public Package[] getPackages() {
        Package[] ancestorPackages = super.getPackages();
        Package[] localPackages = _fileMan.classFiles.listPackages().toArray( new Package[ 0 ] );
        Package[] both = new Package[ ancestorPackages.length + localPackages.length ];
        System.arraycopy( ancestorPackages, 0, both, 0, ancestorPackages.length );
        System.arraycopy( localPackages, 0, both, ancestorPackages.length, localPackages.length );
        return both;
    }

    /**
     * Returns a listAt of "local" (to this _classLoader) Packages
     *
     * @return the Packages synthesized by this _classLoader
     */
    public List<Package> listPackages() {
        return listPackages( false );
    }

    public List<Package> listPackages( Predicate<Package> packageMatchFn ){
        return listPackages().stream().filter(packageMatchFn).collect(Collectors.toList());
    }

    public List<Package> listPackages(
            Predicate<Package> packageMatchFn, boolean includeParentClassLoaders ){
        return listPackages(includeParentClassLoaders).stream()
                .filter(packageMatchFn).collect(Collectors.toList());
    }
    /**
     * Gets the resource UTL for the filePath and relative NAME of resource
     *
     * @param filePath path to the resource
     * @param relativeName the relative NAME within the path
     * @return the URL of the resource of null if not found
     */
    public URL getResource( String filePath, String relativeName ) {
        //System.out.println( "Getting resource "+ filePath+ relativeName );
        List<_classLoader> ancestry = listAncestry( this );
        for( int i = 0; i < ancestry.size(); i++ ) {
            _file _f = ancestry.get( i )._fileMan.resourceFiles.getFile( filePath, relativeName );
            if( _f != null ) {
                return _f.getURL();
            }
        }
        return null;
    }

    @Override
    public URL getResource( String name ) {
        //FIRST try local resources
        _file _f = _fileMan.resourceFiles.get( name );
        if( _f != null ) {
            return _f.getURL();
        }
        //THEN RECURSIVELY TRY parent _classLoaders
        if( getParent() instanceof _classLoader) {
            _classLoader parent = (_classLoader)getParent();
            return parent.getResource( name );
        }
        //THEN try the base getResource (i.e. existing FileManager)
        return super.getResource( name );
    }

    @Override
    public Enumeration<URL> findResources( String name ) throws IOException {
        List<_classLoader> ancestry = listAncestry( this );
        ArrayList<URL> found = new ArrayList<URL>();
        for( int i = 0; i < ancestry.size(); i++ ) {
            _file _resourceFile
                    = ancestry.get( i )._fileMan.resourceFiles.get( name );
            if( _resourceFile != null ) {
                found.add( _resourceFile.url );
            }
        }
        return Collections.enumeration( found );
    }

    @Override
    public InputStream getResourceAsStream( String name ) {
        //FIRST try local resources
        _file _f = this._fileMan.resourceFiles.get( name );
        if( _f != null ) {
            return  new ByteArrayInputStream( _f.data );
        }
        //THEN RECURSIVELY TRY parent _classLoaders
        if( getParent() instanceof _classLoader) {
            _classLoader parent = (_classLoader)getParent();
            return parent.getResourceAsStream( name );
        }
        //THEN try the base getResource (i.e. existing FileManager)
        return super.getResourceAsStream( name );
    }

    public List<Package> listPackages( boolean includeAncestorClassLoaders ) {
        List<Package> allPackages = new ArrayList<Package>();
        if( includeAncestorClassLoaders ) {
            List<_classLoader> ancestry = listAncestry( this );
            for( int i = 0; i < ancestry.size(); i++ ) {
                allPackages.addAll( ancestry.get( i ).listPackages() );
            }
            return allPackages;
        }
        return _fileMan.classFiles.listPackages();
    }

    /**
     * Loads the class by NAME (in this of <B>or the loader of</B>)
     * (Unlike getClass(), throws an UNCHECKED ClassNotFoundException and
     * not a CheckedException if the class is not found)
     *
     * @param qualifiedClassName the fully qualified class NAME to in (i.e.
     * "ex.varcode.MyAuthored")
     * @return the class (Or null if the class is not found in this of
     * or the loader ClassLoader
     * @throws ClassNotFoundException
     */
    @Override
    public Class<?> findClass( String qualifiedClassName )
            throws ClassNotFoundException {


        qualifiedClassName = qualifiedClassName.replace( '/', '.' );

        //System.out.println("finding class "+qualifiedClassName );

        //Why am I checking this first??? -- BECAUSE overhead of loading
        Class loadedClass = this.findLoadedClass( qualifiedClassName );
        if( loadedClass != null ) {
            return loadedClass;
        }
        //if(this.getParent() != null && this.parent){

        //}
        //Now ACTUALLY Link the class and return the Class
        Class linked = linkClass( qualifiedClassName );
        if( linked == null ){
            throw new ClassNotFoundException("No class named \""+qualifiedClassName+"\"");
        }
        return linked;
    }

    /**
     * Link the bytecode Class to this ClassLoader at runtime (make the class
     * known and usable by the VMM)...
     *
     * even though the method is called "defineClass" the class
     * is technically already "defined" in the bytecode, the important thing
     * here is that the class becomes "useable" by the JVM since the linkage
     * association between the
     * VM -> classLoader -> Class and the
     * VM -> classLoader -> Package are made
     *
     * @param fullClassName the fully qualified className
     * @return
     * @throws ClassNotFoundException if there is no
     */
    private Class<?> linkClass( String fullClassName ) throws ClassNotFoundException {

        //first check that I have the compiled class (bytecode)
        _classFile _classByteCode
                = _fileMan.classFiles.get( fullClassName );

        //we dont have a local _new of this class, check parent classLoaders
        if( _classByteCode == null ) {
            //if I dont have the compiled class, maybe I just want
            // a non-local class (i.e. in parent ClassLoader)
            //In this case... no "linkage" occurs, but rather an existing
            //(already linked) Class _new is returned
            return super.findClass( fullClassName );
        }

        // Here I know I have an unlinked compiled class (the bytecode)
        // Before I of the class to make it available in the JVM,
        // I MIGHT have to of a new Package
        // (this MUST BE DONE BEFORE I of the class since the linkages
        //  from classLoader -> Package and classLoader -> Class is stored at
        // the virtual machine level and cannot be changed after linkage occurs
        //
        // i.e. There MUST be a Package "linked" before the first class in the
        // package can be "linked"
        // NOTE in Java 9, 10, 11 this causes nasty messages
        // linkPackage( fullClassName );

        //NOW of the class (AFTER we made sure the package has been defined)
        byte[] byteCode = _classByteCode.getBytes();

        //defineClass SHOULD be CALLED linkClass, as it's already been defined
        //in the bytecodes, here it is being linked and made accessible
        // in the JVM
        return defineClass( fullClassName, byteCode, 0, byteCode.length );
    }

    /**
     * NOT A USER LEVEL API (INTERNALLY USED BY the _project class
     *
     * Makes the compiled Classes available to the Java runtime.
     *
     * This is a final "LifeCycle" method for the Classes in the _classLoader,
     * when called, it will
     * 1) auto-generate any of the Packages instances necessary to "house" the
     * _class files (it will linkAllClasses the Packages to the Runtime and
     * _classLoader
     * to make them available to the Java Runtime)
     * 2) will call "defineClass" which will associate all of the compiled
     * {@link _type}s
     *
     * @return the Classes that have been linked
     */
    public List<Class> linkAllClasses() {
        List<_type> _classNames = list_types( false );

        List<Class> linkedClasses = new ArrayList<Class>();
        for( int i = 0; i < _classNames.size(); i++ ) {
            try {
                linkedClasses.add(
                        this.linkClass( _classNames.get( i ).getFullName() ) );
            }
            catch( ClassNotFoundException e ) {
                throw new DraftException("Unable to load class "
                        + _classNames.get( i ).getFullName(), e );
            }
        }
        return linkedClasses;
    }

    /**
     * IF we are generated a new Class at runtime that resides in a package
     * that does not yet exist, we have to CREATE/LINK a new Package _new
     * to the classLoader BEFORE we of the first class that resides in this
     * package. Therefore before we of the class we verify that either a
     * Package already exists for the class to be associated with or we generate
     * a new Package and of it (Packages, like classes are linked to ClassLoader
     * instances in the VM).
     *
     * OTHERWISE, if you generate a class at runtime and not the package first
     * when you reflectively getAt the package from the generated class, it will
     * return null (potential NPE).
     * This way, we automatically generate a Package _new and of it
     *
     * @param fullClassName the fully qualified class NAME
     */
    private void linkPackage( String fullClassName ) {
        try {
            //** OK FINAL STEP BEFORE WE DEFINE THE CLASS,
            // we need to ensure we create a Package to "house" the class
            int dotIndex = fullClassName.lastIndexOf('.');

            //_id._qualifiedName _qual = _id._qualifiedName.of( fullClassName );
            if( dotIndex > 0 ){
                Package autoPkg = _packageLinker.of(this,
                        _packageLinker._versionSpecification.of( fullClassName.substring(0, dotIndex) ) );
                _fileMan.classFiles.addPackage( autoPkg );
            }
        }
        catch( DraftException de ) {
            throw de;
        }
    }

    public Class getClass( _type _t){
        return getClass( _t.getFullName() );
    }

    public Class getClass( String className ) {
        try {
            return findClass( className );
        }
        catch( ClassNotFoundException cnfe ) {
            throw new DraftException( "No class \"" + className + "\"", cnfe );
        }
    }

    public _class get_class( Class clazz ){
        return (_class) get_type(clazz);
    }

    public _enum get_enum( Class clazz ){
        return (_enum) get_type(clazz);
    }

    public _interface get_interface( Class clazz ){
        return (_interface) get_type(clazz);
    }

    public _annotation get_annotation( Class clazz ){
        return (_annotation) get_type(clazz);
    }

    /**
     * Gets class by NAME:
     * 1: checks the
     *
     *
     * @param className
     * @return
     */
    public _class get_class(String className ) {
        _type _t = get_type(className);
        if( _t instanceof _class){
            return (_class)_t;
        }
        if( _t != null ){
            throw new DraftException("found _type "+ className+" of "
                    + _t.getClass().getCanonicalName() );
        }
        return null;
    }

    public _interface get_interface(String interfaceName ) {
        _type _t = get_type(interfaceName);
        if( _t instanceof _interface){
            return (_interface)_t;
        }
        if( _t != null ){
            throw new DraftException(
                    "found _type "+ interfaceName+" of "+ _t.getClass().getCanonicalName() );
        }
        return null;
    }

    public _annotation get_annotation(String annotationName ) {
        _type _t = get_type(annotationName);
        if( _t instanceof _annotation){
            return (_annotation)_t;
        }
        if( _t != null ){
            throw new DraftException("found _type "+ annotationName+" of "
                    + _t.getClass().getCanonicalName() );
        }
        return null;
    }

    public _enum get_enum(String enumName ) {
        _type _t = get_type(enumName);
        if( _t instanceof _enum){
            return (_enum)_t;
        }
        if( _t != null ){
            throw new DraftException("found _type "+ enumName+" of "
                    + _t.getClass().getCanonicalName() );
        }
        return null;
    }
}
