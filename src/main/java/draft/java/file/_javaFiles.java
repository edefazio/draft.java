package draft.java.file;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import draft.DraftException;
import draft.java.*;

import javax.tools.JavaFileManager;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * An implementation of : {@link JavaFileManager.Location} storing
 * in memory {@link _type}s that can be converted (on demand) to
 * {@link _javaFile}s (for in memory javac compilation):
 * <UL>
 * <LI>{@link draft.java._class}
 * <LI>{@link draft.java._interface}
 * <LI>{@link draft.java._enum}
 * <LI>{@link draft.java._annotation}
 * </UL>
 * NOTE:
 * we only create the _sourceFile to house the (_class, _interface, _enum...)
 * when requested (on demand) we internally store the models that can be
 * directly manipulated at compile time.
 *
 * By convention there is a sourcePath _sourceDierctory which contains Java
 * source files that have NOT been compiled (i.e. .java files) that are being
 * passed into the
 */
public final class _javaFiles
        implements JavaFileManager.Location, Iterable<_javaFile>, _type._hasTypes {

    public static final String DEFAULT_NAME = "SOURCE_DIR";

    /**
     * Open _javaFiles accept {@link _javaFile}s to be added, and returns
     * references to the original _javaFile / _type.
     * When CLOSED
     */
    private final AtomicBoolean isOpen;

    /**
     * Name of the Location ("SOURCE_OUPUT", "SOURCE_INPUT") are both
     * standard locations understood
     */
    public final String locationName;
    public final boolean isOutputLocation;

    private final List<_javaFile> javaFiles = new ArrayList<>();

    public static _javaFiles of(  ){
        return new _javaFiles( DEFAULT_NAME, true );
    }

    public static _javaFiles of(_type...types ){
        _javaFiles _sd = new _javaFiles( DEFAULT_NAME, true );
        _sd.add( types );
        return _sd;
    }

    public static _javaFiles of(String name ){
        return of( name, true );
    }

    public static _javaFiles of(String name, boolean isOutputDirectory ){
        return new _javaFiles( name, isOutputDirectory );
    }

    public _javaFiles( String name, boolean isOutputLocation ) {
        this.locationName = name;
        this.isOutputLocation = isOutputLocation;
        this.isOpen = new AtomicBoolean( true );
    }

    public _javaFiles( _javaFiles prototype ) {
        this.locationName = prototype.locationName;
        this.isOutputLocation = prototype.isOutputLocation;
        for( int i = 0; i < prototype.size(); i++ ) {
            this.javaFiles.add( new _javaFile( prototype.javaFiles.get( i )) );
            //clone all of the types in the workspace to avoid modification
        }
        this.isOpen = new AtomicBoolean( true );
    }

    /**
     *
     * @param _typeMatchFn for matching which _project to removeAll
     * @return the _project removed)
     */
    public List<_type> remove(Predicate<_type> _typeMatchFn ){
        if( this.isOpen.get() ){
            throw new DraftException("CLOSED cannot removeAll");
        }
        List<_type>typesRemoved = list(_typeMatchFn, false );

        this.javaFiles.removeIf( f -> typesRemoved.contains( f.get_type() ) );
        return typesRemoved;
    }

    public int size() {
        return javaFiles.size();
    }

    public boolean isOpen(){
        return this.isOpen.get();
    }

    public _javaFiles open(){
        this.isOpen.set( true );
        return this;
    }

    public _javaFiles close(){
        this.isOpen.set(false );
        return this;
    }

    @Override
    public boolean isEmpty() {
        return size() == 0;
    }

    @Override
    public String getName() {
        return this.locationName;
    }

    @Override
    public boolean isOutputLocation() {
        return this.isOutputLocation && this.isOpen.get();
    }

    @Override
    public String toString() {
        return "_javaFiles " + this.getName() + " output " + this.isOutputLocation
                + "\n" + listTypeNames();
    }

    /**
     * Gets the _java file base on the pkgName and the relativeName.
     *
     * We need to do this (separate pkgName of relativeName)
     * because the "models" can be nested
     * (i.e. a Class Nested inside another class within the same physical file)
     *
     * @param pkgName the package NAME
     * @param relativeName the relative NAME of the class...
     * NOTE: this can be a compound NAME (i.e. "TopClass.NestedClass")
     * for nested classes
     * @return a _javaFile representing the source
     */
    public _javaFile getFile( String pkgName, String relativeName ) {

        if( !relativeName.contains(".")) { //TOP LEVEL CLASS
            Optional<_javaFile> ojf =
                    javaFiles.stream().filter(jf -> pkgName.equals(jf.type.getPackage())
                            && relativeName.equals(jf.type.getName())).findFirst();
            if( ojf.isPresent() ){
                return ojf.get();
            }
            return null;
        }
        //nested class, first get the Top Level Class, then get the NESTS
        String topClass = relativeName.substring(0, relativeName.indexOf('.'));
        return getFile( pkgName, topClass);
    }

    /**
     * gets an existing JavaFileObject for a source file with the className
     * className.
     *
     * @param className the fully qualified class NAME (i.e. "java.util.Map")
     * @return an existing _javaFile (containing a _type) or new _javaFile to write a _type to
     */
    public _javaFile getOrCreateFile( String className ){
        //FIRST check if I already have this file
        _javaFile existingJavaFile = getFile( className );
        if( existingJavaFile != null ){
            if( this.isOpen.get() ){
                return existingJavaFile;
            }
            return existingJavaFile.copy();
        }
        if( !this.isOpen.get() ){
            throw new DraftException("CLOSED cannot create new file "+ className );
        }
        _javaFile javaFile = new _javaFile( className );
        this.javaFiles.add( javaFile );
        return javaFile;
    }

    /**
     * Gets the TYPE given the pkgName and the relativeName
     *
     * @param pkgName the package of the class (i.e. "my.pkg")
     * @param relativeName the relative NAME (i.e. "MyClass", or "MyClass$Nested")
     * @return the _type or null if not found
     */
    public _type get(String pkgName, String relativeName ) {
        _javaFile javaFile = getFile( pkgName, relativeName );
        if( javaFile != null && javaFile.get_type() != null){
            if( javaFile.type.getName().equals(relativeName) ) { //top level TYPE
                _type _t = javaFile.get_type();
                if (this.isOpen.get()) {
                    return _t;
                }
                return _t.copy(); //not open, return a mutable  copy
            } //nested TYPE
            String nestName = relativeName.substring(relativeName.indexOf('.')+1);

            _type _t = javaFile.type.getNest(nestName);
            if (this.isOpen.get()) {
                return _t;
            }
            return _t.copy(); //not open, return a mutable  copy
        }
        return null;
    }

    /**
     * Adds (and overwrites if necessary) the new TOP LEVEL
     * {@link _type}s to the directory
     *
     * @param _typesToAdd the top-level types to accept (or load)
     * @return the modified sourceDirectory
     */
    public _javaFiles add( List<_type> _typesToAdd ) {

        for( int i = 0; i < _typesToAdd.size(); i++ ) {
            _type _td = _typesToAdd.get( i );
            _javaFile _toReplace = getFile( _td.getFullName() );
            if( _toReplace != null ){
                this.javaFiles.remove( _toReplace );
            }
            this.javaFiles.add(_javaFile.of( _td ) );
        }
        return this;
    }

    /**
     * 
     * @param _types
     * @return 
     */
    public _javaFiles add(_type... _types ) {
        if( !this.isOpen.get() ){
            throw new DraftException("CLOSED, could not accept types");
        }
        List<_type> ts = new ArrayList<>();
        for(int i=0;i<_types.length;i++){
            _javaFile _toReplace = getFile( _types[i].getFullName() );
            if( _toReplace != null ){
                this.javaFiles.remove( _toReplace );
            }
            this.javaFiles.add( _javaFile.of( _types[i] ) );
        }
        return this;
    }

    /**
     * 
     * @param className
     * @return 
     */
    public _javaFile getFile( String className ) {
        for(int i=0;i<this.javaFiles.size();i++){
            _javaFile javaFile = javaFiles.get( i );
            if( javaFile.type.getFullName().equals( className ) ){
                if( this.isOpen.get() ){
                    return javaFile;
                }
                return javaFile.copy(); //it's closed, return a copy
            }
        }
        return null;
    }

    public String describe(){
        return _javaFiles.class.getCanonicalName()+" : " + System.lineSeparator() + listTypeNames(false);
    }

    public _type resolve(String sourceId ){
        return get( sourceId );
    }

    public _type resolve(Class clazz ){
        return get( clazz );
    }

    public _type get(Class clazz ){
        if( clazz.isMemberClass() ){
            _type _dc = get( clazz.getDeclaringClass() );
            List<TypeDeclaration> tds = Walk.list(_dc,
                    TypeDeclaration.class,
                    td-> td.getNameAsString().equals(clazz.getSimpleName()) );
            if( tds.size() == 1 ){
                return _class.of( (ClassOrInterfaceDeclaration)tds.get(0));
            }
        }
        if( clazz.isLocalClass() ){
            _type _dc = get( clazz.getDeclaringClass() );
            List<LocalClassDeclarationStmt> tds = Walk.list(_dc,
                    LocalClassDeclarationStmt.class,
                    lcd-> lcd.getClassDeclaration().getNameAsString().equals(clazz.getSimpleName()) );
            if( tds.size() == 1 ){
                return _class.of( tds.get(0).getClassDeclaration() );
            }
        }
        if( clazz.isAnonymousClass() ){
            _type _dc = get( clazz.getDeclaringClass() );
            List<ObjectCreationExpr> tds = Walk.list(_dc,
                    ObjectCreationExpr.class,
                    oce-> oce.getAnonymousClassBody().isPresent() && oce.getType().getName().asString().equals(clazz.getSimpleName()));
            if( tds.size() == 1 ){
                _class _c = _class.of( "ANONYMOUS");
                tds.get(0).getAnonymousClassBody().get().forEach( e -> _c.ast().addMember( e ) );
            }
        }
        return get( clazz.getName() );
    }

    /* */
    public _type get(Predicate<? super _type> _typeMatchFn ){
        Optional<_javaFile> _found = this.javaFiles.stream()
                .filter( _jf -> _typeMatchFn.test(_jf.get_type()))
                .findFirst();
        if( _found.isPresent() ){
            if( this.isOpen.get() ){
                return _found.get().get_type(); //return the mutable original
            }
            return _found.get().get_type().copy(); //return a copy
        }
        return null;
    }

    /**
     * gets the _type
     *
     * @param className the fully qualified class NAME (i.e. "java.util.Map")
     * @return the _java._file
     */
    public _type get(String className ) {
        for( int i = 0; i < this.javaFiles.size(); i++ ) {
            _type _t = javaFiles.get( i ).get_type();
            if( _t != null ){
                _type _at = _t.getType( className );
                if( _at != null ){
                    if( this.isOpen.get() ){
                        return _at;
                    }
                    return _at.copy();
                }
            }
        }
        return null;
    }

    /**
     * @return List of all "top level" (non-nested) {@link _type}s`
     */
    @Override
    public List<_type> list() {
        return list(false);
    }

    /**
     * return a listAt of all "Top-level" types (and optionally nested types)
     * of <T> where <T> is one of ( _class, _enum, _interface, _annotation)
     *
     * i.e.
     * <PRE>
     * _javaFiles _jfs = _javaFiles.of(
     *    _class.of("A").$(_interface.of("B")), _class.of("C") );
     * _jfs.listAt
     * </PRE>
     * @param <T> the specific _type (_class, _interface, _enum, _annotation)
     * @param _typeClass
     * @param _typeMatchFn matching function
     * @param includeNestedTypes whether to matching function tests nested types
     * (not just the Top level classes)
     * @return List of <T>( _class, _enum, _interface, _annotation)
     */
    public <T extends _type> List<T> list(
            Class<T> _typeClass, Predicate<? super T> _typeMatchFn, boolean includeNestedTypes ){

        List<T> _types = new ArrayList<>();
        for(int i=0;i<this.javaFiles.size();i++){
            _type _t = javaFiles.get( i ).type;
            if( _t != null ){
                if( includeNestedTypes ){
                    if( this.isOpen.get( )){
                        _types.addAll( Walk.list(_t, (Class<T>)_typeClass, (Predicate<T>)_typeMatchFn) );
                    }
                    else{ //not open, return copies
                        Walk.list(_t, (Class<T>)_typeClass, (Predicate<T>)_typeMatchFn)
                            .forEach( t -> _types.add( _typeClass.cast( ((_type)t).copy()) ) );
                    }
                }
                else{
                    if( _t.getClass().isAssignableFrom( _typeClass )){
                        if( _typeMatchFn.test( _typeClass.cast( _t ) ) ){
                            if( this.isOpen.get() ){
                                _types.add( _typeClass.cast( _t ) );
                            }
                            else{
                                _types.add( _typeClass.cast( _t.copy() ) );
                            }
                        }
                    }
                }
            }
        }
        return _types;
    }

    /**
     *
     * @param includeNestedTypes will return references to all types
     * (including nested types)
     * @return listAt of all "top-level" _type declarations
     */
    public List<_type> list(boolean includeNestedTypes ){
        List<_type> _types = new ArrayList<>();
        for(int i=0;i<this.javaFiles.size();i++){
            if( javaFiles.get( i ).type != null ){
                if( includeNestedTypes ){
                    if( this.isOpen.get() ){
                        _types.addAll( Walk.list(javaFiles.get( i ).type, _type.class ) );
                    }
                    else{
                        Walk.list( javaFiles.get( i ).type, _type.class )
                            .forEach( t -> _types.add( ((_type)t).copy()));
                    }
                }
                else{
                    if( this.isOpen.get() ){
                        _types.add( javaFiles.get( i ).type );
                    }
                    else{
                        _types.add( javaFiles.get( i ).type.copy() );
                    }
                }
            }
        }
        return _types;
    }

    /**
     * List the _project in the _sourceLocation based on predicate
     * @param _typeMatchFn matches which types to be included
     * @param includeNestedTypes should nested types be added or only top level types?
     * @return
     */
    public List<_type> list( Predicate<_type>_typeMatchFn, boolean includeNestedTypes ){

        return list( _type.class, _typeMatchFn, includeNestedTypes);
    }

    public _javaFiles forEach( Consumer<? super _type>_typeActionFn, boolean includeNestedTypes ){

        //since listAt will return (either copies or
        list( includeNestedTypes ).forEach( _typeActionFn );
        return this;
    }

    public _javaFiles forEach( Predicate<_type>_typeMatchFn, Consumer<_type>_typeActionFn, boolean includeNestedTypes ){
        list( _typeMatchFn, includeNestedTypes ).forEach( _typeActionFn );
        return this;
    }

    public List<String> listTypeNames() {
        return listTypeNames( true );
    }

    public List<String> listTypeNames(boolean includeNestedClasses) {
        List<String> names = new ArrayList<>();
        for(int i=0;i<this.javaFiles.size();i++){
            if( javaFiles.get( i ).type != null ){
                if( includeNestedClasses ){
                    names.addAll( javaFiles.get( i ).type.listTypeNames() );
                } else{
                    names.add( javaFiles.get( i ).type.getFullName() );
                }
            }
        }
        return names;
    }

    public List<_type> list(String pkgName ) {
        return list(pkgName, false, false );
    }

    public List<_type> list(String pkgName, boolean includeSubPkgs ) {

        return list( pkgName, includeSubPkgs, false );
    }

    public List<_type> list(String pkgName, boolean includeSubPkgs, boolean includeNestedTypes ) {

        List<_type> _types = new ArrayList<>();

        for(int i=0;i<this.javaFiles.size();i++){
            _type _t = javaFiles.get( i ).type;
            if( _t != null ){
                if( _t.getPackage().equals( pkgName)
                        || (_t.isInPackage( pkgName ) && includeSubPkgs) ) {

                    if( includeNestedTypes ){
                        if( this.isOpen.get( )){
                            Walk.list(_t, _type.class).forEach( t -> _types.add( (_type)t) );
                            _types.add( _t );
                        }
                        else{ //not open, return copies
                            Walk.list(_t, _type.class).forEach( t -> _types.add( ((_type)t).copy() ) );
                        }
                    }
                    else{
                        if( this.isOpen.get( )){
                            _types.add( _t );
                        }
                        else{ //not open, return copies
                            _types.add( _t.copy() );
                        }
                    }
                }
            }
        }
        return _types;
    }

    public List<_javaFile> listFiles(){
        return listFiles(null, true);
    }

    public List<_javaFile> listFiles( Predicate<? super _type>_typeMatchFn ){
        return listFiles().stream().filter( jf -> _typeMatchFn.test( jf.get_type()))
                .collect(Collectors.toList());
    }

    public List<_javaFile> listFiles( String pkgName, boolean includeSubPkgs ) {

        List<_javaFile> _javaFilesByPackage = new ArrayList<>();

        _type[] _types = list().toArray(new _type[ 0 ] );
        for( int i = 0; i < _types.length; i++ ) {
            if( pkgName == null || pkgName.length() == 0 ) {
                if( includeSubPkgs ) { //they want all class files
                    if( this.isOpen.get() ){
                        _javaFilesByPackage.add(_javaFile.of( _types[ i ] ) );
                    }
                    else{
                        _javaFilesByPackage.add(_javaFile.of( _types[ i ] ).copy() );
                    }
                }
                else if( _types[ i ].getPackage() == null
                        || _types[ i ].getPackage().length() == 0 ) {

                    if(this.isOpen.get() ){
                        _javaFilesByPackage.add(_javaFile.of(_types[ i ]));
                    }
                    else{
                        _javaFilesByPackage.add(_javaFile.of(_types[i]).copy());
                    }
                }
            }
            else if( _types[ i ].getPackage().equals(pkgName )
                    || (includeSubPkgs
                    && _types[ i ].isInPackage( pkgName )) ) {

                if( this.isOpen.get() ){
                    _javaFilesByPackage.add(_javaFile.of( _types[ i ] ) );
                }
                else{
                    _javaFilesByPackage.add(_javaFile.of( _types[ i ] ).copy() );
                }
            }
        }
        return _javaFilesByPackage;
    }

    @Override
    public Iterator<_javaFile> iterator() {
        return listFiles(null, true).iterator();
    }
}
