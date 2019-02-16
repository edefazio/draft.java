package draft.java;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.nodeTypes.NodeWithName;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import draft.DraftException;
import draft.Text;
import draft.java._model.*;
import draft.java.io._in;
import draft.java.io._io;
import draft.java.macro._remove;

import java.io.InputStream;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * <PRE>
 * <H2>Traditional Programming Workflow</H2>:
 * 1) write code as text within an editor to create .java files
 * 2) call the javac compiler to convert the .java files to an AST then into
 * Java .class files (ByteCode)
 * 3) run the program in a Java VM to (convert the ByteCode into IR/Machine code)

 editor           compiler         VM runtime
  _________      _____________      _____________
 /         \    /             \    /             \
 .java files -> AST -> ByteCode -> IR Machine Code


 * <H2>Draft Programming Workflow</H2>:
 * 1) Build draft objects (instead of .java files)
 * 2) call the javac compiler to convert the draft objects (which ARE ASTs) into
 * Java .class files (ByteCode)
 * 3) run the program in a Java VM to convert the ByteCode into IR/Machine code
 compiler           VM runtime
  _____________      _____________
 /             \    /             \
 AST -> ByteCode -> IR Machine Code
 ^
 |
 draft
 _model
 objects
 </PRE>
 *
 * Examples:
 * //compile and return the (unloaded) ByteCode within
 * _classFiles _cfs = _javac.of(_class.of("C").FIELDS("String firstName, lastName;"));
 *
 * //create, compile and load a new {@link Class} into a new {@link ClassLoader}:
 * Class draft = _load.of(_class.of("C").FIELDS("String firstName, lastName;"));
 *
 * //create / compile/ load a new {@link Class} into a new {@link ClassLoader}
 * //& create a new instance:
 * Object o = _new.of( _class.of("C").FIELDS("String firstName, lastName;"));
 *
 * @author Eric
 * @param <AST> the Ast {@link TypeDeclaration} ({@link ClassOrInterfaceDeclaration},
 * {@link AnnotationDeclaration}, {@link EnumDeclaration}) that stores the state
 * and maintains the Bi-Directional AST Tree implementation
 * @param <T> the _type entity that provides logical access to manipulating the
 * AST
 */
public interface _type<AST extends TypeDeclaration, T extends _type>
        extends  _javadoc._hasJavadoc<T>, _anno._hasAnnos<T>, _modifiers._hasModifiers<T>,
        _field._hasFields<T>, _member<Node, T> {

    static _type of( InputStream is ){
        return of(JavaParser.parse(is));
    }

    /**
     *
     * @param clazz
     * @return
     */
    static _type of(Class clazz){
        return of( clazz, _io.IN_DEFAULT );
    }

    /**
     * given a Class, return the draft model of the source
     * @param clazz
     * @param resolver
     * @return
     */
    static _type of( Class clazz, _in._resolver resolver ){
        Node n = Ast.type( clazz, resolver );
        TypeDeclaration td = null;
        if( n instanceof CompilationUnit) { //top level TYPE
            CompilationUnit cu = (CompilationUnit) n;
            if (cu.getTypes().size() == 1) {
                td = cu.getType(0);
            } else {
                td = cu.getPrimaryType().get();
            }
        }else {
            td = (TypeDeclaration) n;
        }
        if( td instanceof ClassOrInterfaceDeclaration ){
            ClassOrInterfaceDeclaration coid = (ClassOrInterfaceDeclaration)td;
            if( coid.isInterface() ){
                return _interface.of(coid);
            }
            return _class.of(coid);
        }else if( td instanceof EnumDeclaration){
            return _enum.of( (EnumDeclaration)td);
        }
        return _annotation.of( (AnnotationDeclaration)td);
    }

    /**
     * Gets the comment (i.e. the copyright, etc.)
     * at the top of the CompilationUnit
     * (NOTE: returns null if there are no header comments or
     * this type is not a top level type)
     *
     * @return the Comment a JavaDoc comment or BlockComment or null
     */
    default Comment getHeaderComment(){
        if( isTopClass() && ast().getComment().isPresent()){
            return ast().getComment().get();
        }
        return null;
    }

    /**
     * Sets the header comment as the block comment provided
     * (Assuming the _type is a top level type... (i.e. not a nested type)
     * (this is for setting /resetting the copywrite, etc.)
     * @param blockComment the comment (i.e. the copyright)
     * @return the modified T
     */
    default T setHeaderComment( BlockComment blockComment ){
        if( isTopClass() ){
            if( ast().getComment().isPresent()){
                ast().removeComment();
            }
            ast().setComment(blockComment);
        }
        return (T)this;
    }


    default T setHeaderComment( String...commentLines ){
        return setHeaderComment( new BlockComment( Text.combine(commentLines )) );
    }


    /**
     * add one or more _member(s) (_field, _method, _constructor, enum._constant, etc.) to the BODY of the _type
     * and return the modified _type
     * @param _members
     * @return
     */
    default T add( _model._member..._members ){
        Arrays.stream(_members).forEach( _m -> {
            if(_m instanceof _field){
                this.astType().addMember( ((_field)_m).getFieldDeclaration() );
            } else{
                this.astType().addMember( (BodyDeclaration)_m.ast() );
            }
        }  );
        return (T)this;
    }

    /**
     * remove one or more members (_field, _method, _constructor) from the _type and return the modified _type
     * @param _members members to be removed
     * @return the modified T
     */
    default T remove( _model._member..._members ){
        Arrays.stream(_members).forEach( _m -> this.astType().remove( _m.ast() ) );
        return (T)this;
    }
    /**
     * Apply each of the Macros and return the modified T
     * @param typeFn all of the macros to execute
     * @return the modified T after applying the macros
     */
    default T apply( Function<_type, _type>...typeFn ){
        for(int i=0; i < typeFn.length; i++){
            typeFn[i].apply(this);
        }
        return (T)this;
    }

    /**
     * List the members (_fields, _methods, _constructors,...) of the _type
     * @return
     */
    List<_member> listMembers();

    /**
     * list all the members that match the predicate
     *
     * @param memberMatchFn
     * @return matching members
     */
    default List<_member> listMembers( Predicate<_member> memberMatchFn ){
        return listMembers().stream().filter(memberMatchFn).collect(Collectors.toList());
    }

    /**
     * Is this TYPE the top level class TYPE within a (i.e. a separate top level file /compilation Unit)?
     * @return true if the _type is a top level TYPE, false otherwise
     */
    boolean isTopClass();

    /**
     * Resolve the Compilation Unit that contains this _type,
     * either this TYPE is:
     * <UL>
     * <LI>a top-level class
     * <LI>a nested/member class
     * <LI>an orphan class (a class built separately without linkage to a CompilationUnit
     * (in which case this method returns a null)
     * </UL>
     * @return the top level CompilationUnit, or null if this _type is "orphaned"
     * (created without linking to a CompilationUnit)
     */
    CompilationUnit findCompilationUnit();

    /**
     * @return the AST Node instance being manipulated this will return a {@link CompilationUnit} if the
     * _type is a top level TYPE, or a {@link TypeDeclaration} instance
     */
    default Node ast(){
        if( this.isTopClass() ){
            return this.findCompilationUnit();
        }
        return astType();
    }

    /**
     * Returns the Ast {@link TypeDeclaration} ({@link ClassOrInterfaceDeclaration},
     * {@link AnnotationDeclaration}, {@link EnumDeclaration}
     * @return the TypeDeclaration wrapped by this {@link _type}
     */
    AST astType();

    /**
     * Does this _type have a {@link PackageDeclaration} Node set?
     * @return true if the TYPE is a top level TYPE AND has a declared package
     */
    default boolean hasPackage(){
        return getPackage() != null;
    }

    /**
     * Builds the full NAME for the TYPE based on it's nest position within a class
     * (and its package)
     *
     * @return the full NAME of the TYPE (separated by '.'s)
     */
    default String getFullName(){
        return getFullName( astType() );
    }

    /**
     * Builds the full NAME for the TYPE based on it's nest position within a class
     * (and its package)
     *
     * @return the full NAME of the TYPE (separated by '.'s)
     */
    static String getFullName(TypeDeclaration td){
        if( td.isTopLevelType() ){
            if( td.findCompilationUnit().get().getPackageDeclaration().isPresent()) {
                String pkgName = td.findCompilationUnit().get().getPackageDeclaration().get().getNameAsString();
                return pkgName +"."+ td.getNameAsString();
            }
            return td.getNameAsString(); //top level TYPE but no package declaration
        }
        if(!td.getParentNode().isPresent()){
            return td.getNameAsString();
        }
        //prefix back to parents
        String name = td.getNameAsString();
        Node n = td.getParentNode().get();
        if( n instanceof TypeDeclaration ){
            return getFullName( (TypeDeclaration)n)+"."+name;
        }
        if( n instanceof LocalClassDeclarationStmt ){
          LocalClassDeclarationStmt lc = (LocalClassDeclarationStmt) n;
          return lc.getClassDeclaration().getNameAsString();
        } else {
            NodeWithName nwn = (NodeWithName) n;
            name = nwn.getNameAsString() + "." + name;
            if (n.getParentNode().isPresent()) {
                return getFullName((TypeDeclaration) n) + "." + name;
            }
        }
        return name;
    }

    /**
     * Remove the package declaration and return the modified TYPE
     * @return
     */
    default T removePackage(){
        CompilationUnit cu = findCompilationUnit();
        if( cu == null ){
            return (T)this;
        }
        if( cu.getPackageDeclaration().isPresent() ){
            cu.removePackageDeclaration();
        }
        return (T)this;
    }

    /**
     * Determines the package this class is in
     * @return
     */
    default String getPackage(){
        CompilationUnit cu = findCompilationUnit();
        if( cu == null ){
            return null;
        }
        if( cu.getPackageDeclaration().isPresent() ){
            return cu.getPackageDeclaration( ).get().getNameAsString();
        }
        return null;
    }

    /**
     * Sets the package this TYPE is in
     * @param packageName
     * @return the modified TYPE
     */
    default T setPackage( String packageName ){
        if( !this.isTopClass() ){
            CompilationUnit astRoot = new CompilationUnit();
            astRoot.setPackageDeclaration(packageName);
            astRoot.addType(astType());
            return (T) this;
        }
        CompilationUnit cu = findCompilationUnit();
        cu.setPackageDeclaration( packageName );
        return (T)this;
    }

    default boolean isInPackage( String packageName){
        String pn  = getPackage();
        if( pn == null){
            return packageName == null || packageName.length() == 0;
        }
        if( Objects.equals(pn, packageName) ){
            return true;
        }
        if( packageName !=null ){
            return packageName.indexOf(pn) == 0;
        }
        return false;
    }

    default T copy(){
        if( this.isTopClass()){
            if( this instanceof _class ) {
                return (T)_class.of(this.findCompilationUnit().clone());
            }
            if( this instanceof _enum ) {
                return (T)_enum.of(this.findCompilationUnit().clone());
            }
            if( this instanceof _interface ) {
                return (T)_interface.of(this.findCompilationUnit().clone());
            }
            return (T)_annotation.of(this.findCompilationUnit().clone());
        }
        if( this instanceof _class ) {
            return (T)_class.of( ((_class) this).astType().clone());
        }
        if( this instanceof _enum ) {
            return (T)_enum.of( ((_enum) this).astType().clone());
        }
        if( this instanceof _interface ) {
            return (T)_interface.of( ((_interface) this).astType().clone());
        }
        return (T)_annotation.of( ((_annotation) this).astType().clone());
    }

    /**
     * Remove imports based on a predicate
     * @param importMatchFn filter for deciding which imports to removeIn
     * @return the modified TYPE
     */
    default T removeImports( Predicate<ImportDeclaration> importMatchFn ){
        removeImports( listImports( importMatchFn ));
        return (T)this;
    }

    /**
     * removeIn imports by classes
     * @param clazzes
     * @return
     */
    default T removeImports( Class...clazzes ){
        ImportDeclaration[] ids = new ImportDeclaration[clazzes.length];
        for(int i=0;i<clazzes.length;i++){
            ids[i] = new ImportDeclaration(clazzes[i].getCanonicalName(), false, false);
        }
        return removeImports( ids );
    }

    /**
     *
     * @param toRemove the ImportDeclarations to removeIn
     * @return the modified _type
     */
    default T removeImports( ImportDeclaration...toRemove ){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            for(int i=0;i<toRemove.length;i++){
                cu.getImports().remove( toRemove[i] );
            }
        }
        return (T)this;
    }

    default T removeImports( List<ImportDeclaration> toRemove ){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            for(int i=0;i<toRemove.size();i++){
                cu.getImports().remove( toRemove.get(i) );
            }
        }
        return (T)this;
    }

    default List<ImportDeclaration> listImports(){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            return cu.getImports();
        }
        return new ArrayList<>();
    }

    default boolean isImported( _type _t ){
        return isImported( _t.getFullName() );
    }

    default boolean isImported(String className){
        int dotIndex = className.lastIndexOf( '.' );
        if( dotIndex > 1 ){
            String packageName = className.substring( 0, dotIndex );
            //System.out.println( "Looking for package NAME "+ PACKAGE_NAME);
            String simpleClassName =  className.substring( dotIndex + 1 );
            if( !listImports( i-> i.getNameAsString().equals( packageName ) && i.isAsterisk() ).isEmpty() ){
                return true;
            }
            //check exact import
            if( !listImports( i-> i.getNameAsString().equals( className ) ).isEmpty() ){
                return true;
            }
        }
        return false;
    }

    default boolean isImported(Class clazz ){
        if( clazz.isMemberClass() ){
            //check exact import
            if( !listImports( i-> i.getNameAsString().equals( clazz.getCanonicalName() ) ).isEmpty() ){
                return true;
            }
            return false;
        }else{
            if( clazz.getPackage() != null ) {
                String packageName = clazz.getPackage().getName();
                List<ImportDeclaration> li = listImports();
                //check star import
                if (listImports(i -> i.getNameAsString().equals(packageName) && i.isAsterisk()).isEmpty()) {
                    return true;
                }
                //check exact import
                if (!listImports(i -> i.getNameAsString().equals(clazz.getCanonicalName())).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    default List<ImportDeclaration> listImports( Predicate<ImportDeclaration> importMatchFn){
        List<ImportDeclaration> is = listImports();
        return is.stream().filter( importMatchFn ).collect(Collectors.toList());
    }

    /**
     * Creates Wildcard static imports
     * @param wildcardStaticImports a list of classes that will WildcardImports
     * @return the T
     */
    default T importStatic( Class ...wildcardStaticImports ){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            Arrays.stream(wildcardStaticImports).forEach( i -> {
                if( i.isArray() ){
                    cu.addImport(new ImportDeclaration((i.getComponentType().getCanonicalName()), true, true));
                } else {
                    cu.addImport(new ImportDeclaration(i.getCanonicalName(), true, true));
                }
            });
        }
        return (T)this;
    }

    /**
     *
     * @param staticImports
     * @return
     */
    default T importStatic( String...staticImports){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            Arrays.stream(staticImports).forEach( i -> {
                if( i.endsWith(".*")) {
                    cu.addImport(new ImportDeclaration(i.substring(0, i.length() - 2), true, true));
                } else{
                    cu.addImport(new ImportDeclaration(i, true, false));
                }
            });
        }
        return (T)this;
    }

    /**
     * Statically import all of the
     * @param wildcardTypeStaticImport
     * @return
     */
    default T importStatic( _type...wildcardTypeStaticImport){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            Arrays.stream(wildcardTypeStaticImport).forEach( i -> {
                cu.addImport(new ImportDeclaration(i.getFullName(), true, true));
            });
        }
        return (T)this;
    }

    /**
     * Create wildcard imports for the packages of all Classes
     * for instance:
     * <PRE>
     * _class.of("aaaa.C").importPackages( Map.class, IOException.class );
     * //represents
     * package aaa;
     *
     * import java.util.*;
     * import java.io.*;
     *
     * public class C{
     *
     * }
     * </PRE>
     * @param clazzes classes whos packahes will be imported
     * @return the modified T
     */
    default T importPackages( Class...clazzes){
        Arrays.stream(clazzes).forEach( c-> {
            if( c.getPackage() != null ) {
                importPackages(c.getPackage().getName());
            }
            });
        return (T)this;
    }

    /**
     * Wildcard import the classeses in the Packages provided
     * @param packageNames
     * @return
     */
    default T importPackages( String...packageNames ){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            Arrays.stream(packageNames).forEach( p -> {
                cu.addImport(new ImportDeclaration(p, false, true));
            });
        }
        return (T)this;
    }

    /**
     *
     * @param _ts
     * @return
     */
    default T imports( _type... _ts ){
        Arrays.stream(_ts).forEach( _t -> imports(_t.getFullName()));
        return (T)this;
    }

    /**
     * Regularly import a class
     * @param classesToImport
     * @return
     */
    default T imports( Class...classesToImport ){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            //Arrays.stream( classesToImport ).forEach( c-> cu.addImport( c ) );
            for(int i=0;i<classesToImport.length; i++){
                //System.out.println( "Adding "+ classesToImport[i]);

                //fix a minor bug in JavaParser API where anything in "java.lang.**.*" is not imported
                // so java.lang.annotation.* classes are not imported when they should be
                if( classesToImport[i].getPackage() != Integer.class.getPackage()
                        && classesToImport[i].getCanonicalName().startsWith("java") ) {
                    //System.out.println( "manually adding "+classesToImport[i].getCanonicalName() );
                    if( classesToImport[i].isArray() ){
                        String s = classesToImport[i].getCanonicalName();
                        cu.addImport(s.substring(0, s.indexOf('[')));
                    } else {
                        cu.addImport(classesToImport[i].getCanonicalName());
                    }
                } else {
                    if( classesToImport[i].isArray() ) {
                        String s = classesToImport[i].getCanonicalName();
                        cu.addImport(s.substring(0, s.indexOf('[')));
                    }
                    else {
                        cu.addImport(classesToImport[i]);
                    }
                }
                //System.out.println( cu.getImports() );
            }
            //System.out.println( "AFTER ADD "+ this);
            return (T)this;
        }
        throw new DraftException("No CompilationUnit of TYPE "+ ((T)this).getName()+" to add imports");
    }

    default T imports( ImportDeclaration...importDecls){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            Arrays.stream( importDecls ).forEach( c-> cu.addImport( c ) );
            return (T)this;
        }
        throw new DraftException("No CompilationUnit of class "+ getName()+" to add imports");
    }

    default T imports( String...importStatements ){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            Arrays.stream( importStatements ).forEach( c-> cu.addImport( Ast.importDeclaration( c ) ) );
            return (T)this;
        }
        throw new DraftException("No CompilationUnit of "+ getName()+" to add imports");
    }

    @Override
    default T javadoc( String...content ){
        astType().setJavadocComment( Text.combine(content));
        return (T)this;
    }

    @Override
    default T removeJavadoc(){
        astType().removeJavaDocComment();
        return (T) this;
    }

    @Override
    default boolean hasJavadoc(){
        return astType().getJavadocComment().isPresent();
    }

    @Override
    default _javadoc getJavadoc() {
        return _javadoc.of(this.astType());
    }

    @Override
    default _modifiers getModifiers(){
        return _modifiers.of(this.astType() );
    }

    default boolean isPublic(){
        return this.astType().isPublic();
    }

    default boolean isDefaultAccess(){
        return !this.astType().isProtected() &&
                !this.astType().isPrivate() &&
                !this.astType().isPublic();
    }

    default boolean isProtected(){
        return this.astType().isProtected();
    }

    default boolean isPrivate(){
        return this.astType().isPrivate();
    }

    default boolean isStatic(){
        return this.astType().isStatic();
    }

    default boolean isStrictFp(){
        return this.astType().isStrictfp();
    }

    default T setPublic(){
        this.astType().setPublic(true);
        this.astType().setPrivate(false);
        this.astType().setProtected(false);
        return (T)this;
    }

    default T setProtected(){
        this.astType().setPublic(false);
        this.astType().setPrivate(false);
        this.astType().setProtected(true);
        return (T)this;
    }
    default T setPrivate(){
        this.astType().setPublic(false);
        this.astType().setPrivate(true);
        this.astType().setProtected(false);
        return (T)this;
    }
    default T setDefaultAccess(){
        this.astType().setPublic(false);
        this.astType().setPrivate(false);
        this.astType().setProtected(false);
        return (T)this;
    }

    @Override
    default NodeList<Modifier> getEffectiveModifiers() {
        NodeList<Modifier> implied = Ast.getImpliedModifiers( this.astType() );
        return Ast.merge( implied, this.astType().getModifiers());
        //return EnumSet.noneOf(Modifier.class);
    }

    @Override
    default T name( String name ){
        astType().setName( name );
        if( this instanceof _class ){
            //make sure to rename the CONSTRUCTORS
            _class _c = (_class)this;
            _c.forConstructors( c-> c.name(name));
        }
        if( this instanceof _enum ){
            //make sure to rename the CONSTRUCTORS
            _enum _e = (_enum)this;
            _e.forConstructors( c-> c.name(name));
        }
        return (T)this;
    }

    @Override
    default String getName(){
        return astType().getNameAsString();
    }

    @Override
    default List<_field> listFields() {
        List<_field> _fs = new ArrayList<>();
        astType().getFields().forEach( f->{
            FieldDeclaration fd = ((FieldDeclaration)f);
            for(int i=0;i<fd.getVariables().size();i++){
                _fs.add(_field.of(fd.getVariable( i ) ) );
            }
        });
        return _fs;
    }

    /**
     *
     */
    default List<_field> listFields( Predicate<_field> _fieldMatchFn ){
        return listFields().stream().filter(_fieldMatchFn).collect(Collectors.toList());
    }


    default List<String> listTypeNames(){
        List<_type> _ts = Walk.list(this, _type.class);
        List<String>names = new ArrayList<>();
        _ts.forEach(t-> names.add(t.getFullName()));
        return names;
    }

    /**
     * Iterate & apply some functional action towards members of the type
     * @param _memberAction the action to apply to ALL members
     * @return the (modified) T type
     */
    default T forMembers( Consumer<_member> _memberAction ){
        return forMembers( t-> true, _memberAction );
    }

    /**
     * Iterate & apply the action function to all Members that satisfy the _memberMatchFn
     * @param _memberMatchFn function for selecting which members to apply the _memberActionFn
     * @param _memberAction the action to apply to all selected members that satisfy the _memberMatchFn
     * @return the modified T type
     */
    T forMembers( Predicate<_member> _memberMatchFn, Consumer<_member> _memberAction );

    /**
     * returns a _type or a nested _type if
     * the NAME provided matches i.e.
     *
     * _class _c = _class.of("MyClass").PACKAGE_NAME("com.mypkg");
     * assertEquals( _c, _c.getType( "com.mypkg.MyClass" ) );
     * _class _c = _class.of("MyTopClass").PACKAGE_NAME("com.myo")
     * .$( _class.of( "MyNest") );
     *
     * //getAt the top level class (either by fully qualified NAME or simple NAME)
     * assertEquals( _c , _c.getType("com.myo.MyTopClass"));
     * assertEquals( _c , _c.getType("MyTopClass"));
     *
     * //getAt the $
     * assertEquals( _c.getNest("MyNest"), _c.getType("com.myo.MyTopClass.MyNest"));
     * assertEquals( _c.getNest("MyNest"), _c.getType("com.myo.MyTopClass$MyNest"));
     * assertEquals( _c.getNest("MyNest"), _c.getType("MyTopClass.MyNest"));
     * assertEquals( _c.getNest("MyNest"), _c.getType("MyTopClass$MyNest"));
     *
     * @param name the NAME (the simple NAME, canonical NAME, or fully qualified NAME
     * (with '.' or '$' notation for nested _type)
     * @return this _type a nested _type or null if not found
     */
    default _type getType(String name ){
        String fn = this.getFullName();
        if( name.startsWith( fn ) ){
            String left = name.substring( fn.length() );
            if( left.length() == 0 ){
                return this;
            }
            if( left.startsWith( "." ) ){
                return this.getNest( left.substring( 1 ) );
            }
            if( left.startsWith( "$") ){
                return this.getNest( left.substring( 1 ) );
            }
        }
        if( name.length() > 0 && name.startsWith(this.getName()) ){
            //maybe I want a nested
            String left = name.substring( this.getName().length() );
            if( left.length() == 0 ){
                return this;
            }
            if( left.startsWith( "." ) ){
                return this.getNest( left.substring( 1 ) );
            }
            if( left.startsWith( "$") ){
                return this.getNest( left.substring( 1 ) );
            }
        }
        return this.getNest( name );
    }

    /**
     * Does this TYPE have a main method?
     * @return
     */
    default boolean hasMain(){
        if( this instanceof _method._hasMethods){
            return ((_method._hasMethods)this).listMethods(_method.IS_MAIN).size() > 0;
        }
        return false;
    }

    /**
     * Ass a nested class to this _type
     * @param type the TYPE to add
     * @return
     */
    default T nest( _type type ){
        this.astType().addMember( type.astType() );
        return (T)this;
    }

    /**
     * Flatten all instances of the label included in this TYPE
     * @param labelName the NAME of the label to flatten
     * @return
     */
    default T flattenLabel( String labelName ){
        Walk.in(this, _body._hasBody.class, _hb-> _hb.hasLabel(labelName), _hb -> _hb.flattenLabel(labelName) );
        return (T) this;
    }

    /**
     * apply a function to matching nested types of this TYPE
     * @param _typeMatchFn
     * @param _typeActionFn
     * @return the modified _type
     */
    default T forNests( Predicate<_type> _typeMatchFn, Consumer<_type> _typeActionFn ){
        listNests(_typeMatchFn).forEach( _typeActionFn );
        return (T)this;
    }

    /**
     * apply a function to NESTS of this _type
     * @param _typeActionFn
     * @return the modified _type
     */
    default T forNests( Consumer<_type> _typeActionFn ){
        listNests().forEach( _typeActionFn );
        return (T)this;
    }

    /**
     * Get the nested _class with this NAME defined within this _type (or null)
     * @param name
     * @return the nested _class or null if not found
     */
    default _class getNestedClass( String name){
        List<_type> ts = listNests( (t)-> t instanceof _class && t.getName().equals(name));
        if( ts.size() == 1 ){
            return (_class)ts.get(0);
        }
        return null;
    }

    /**
     * Gets the nested _interface with this NAME defined in this _type
     * @param name
     * @return the _interface or null if not found
     */
    default _interface getNestedInterface( String name){
        List<_type> ts = listNests( (t)-> t instanceof _interface && t.getName().equals(name));
        if( ts.size() == 1 ){
            return (_interface)ts.get(0);
        }
        return null;
    }

    /**
     * Gets the nested _enum with this NAME defined in this _type
     * @param name
     * @return the _enum or null if not found
     */
    default _enum getNestedEnum( String name){
        List<_type> ts = listNests( (t)-> t instanceof _enum && t.getName().equals(name));
        if( ts.size() == 1 ){
            return (_enum)ts.get(0);
        }
        return null;
    }

    /**
     * Gets the nested _annotation defined in this _type
     * @param name the NAME of the annotation
     * @return the _annotation (if found) or null
     */
    default _annotation getNestedAnnotation( String name){
        List<_type> ts = listNests( (t)-> t instanceof _annotation && t.getName().equals(name));
        if( ts.size() == 1 ){
            return (_annotation)ts.get(0);
        }
        return null;
    }

    /**
     * Gets the nested TYPE with the NAME
     * @param name
     * @return the nested TYPE with the NAME or null if not found
     */
    default _type getNest( String name ){
        List<_type> ts = listNests( t-> t.getName().equals(name));
        if( ts.size() == 1 ){
            return ts.get(0);
        }
        return null;
    }

    /**
     * list all nested types that match this _typeMatchFn
     * @param typeMatchFn function to
     * @return matching nested types or empty list if none found
     */
    default List<_type> listNests( Predicate<? super _type> typeMatchFn ){
        return listNests().stream().filter( typeMatchFn ).collect(Collectors.toList());
    }

    /**
     * list all nested children underneath this logical _type
     * (1-level, DIRECT CHILDREN, and NOT grand children or great grand children)
     * for a more comprehensive gathering of all types, call:
     *
     * @return the direct children (nested {@link _type}s) of this {@link _type}
     */
    default List<_type> listNests(){
        NodeList<BodyDeclaration> bds = astType().getMembers();
        List<BodyDeclaration> ts =
                bds.stream().filter( n-> n instanceof TypeDeclaration )
                        .collect(Collectors.toList());

        List<_type> nests = new ArrayList<>();
        ts.forEach( t-> {
            if( t instanceof ClassOrInterfaceDeclaration ){
                ClassOrInterfaceDeclaration coid = (ClassOrInterfaceDeclaration)t;
                if( coid.isInterface() ){
                    nests.add( _interface.of( coid ));
                } else{
                    nests.add( _class.of( coid ));
                }
            }
            else if ( t instanceof EnumDeclaration ){
                nests.add( _enum.of( (EnumDeclaration)t));
            }
            else{
                nests.add( _annotation.of( (AnnotationDeclaration)t));
            }
        });
        return nests;
    }

    /**
     * _1_build and return a new _type based on the code provided
     * @param code the code for the _type
     * @return the _type {@link _class} {@link _enum} {@link _interface}, {@link _annotation}
     */
    static _type of ( String... code ){
        return of( Ast.typeDeclaration(code));
    }

    /**
     * Build and return the appropriate _type based on the
     * CompilationUnit (whichever the primary TYPE is)
     * @param astRoot the root AST node containing the top level TYPE
     * @return the _model _type
     */
    static _type of( CompilationUnit astRoot ){
        if( astRoot.getTypes().size() == 1 ){
            return of( astRoot, astRoot.getType( 0 ));
        }
        if( !astRoot.getPrimaryType().isPresent()){
            throw new DraftException("Unable to locate the primary TYPE");
        }
        return of( astRoot, astRoot.getPrimaryType().get());
    }

    /**
     * Return the appropriate _type given the AST TypeDeclaration
     * (also, insure that if it is a Top Level _type,
     * @param td
     * @return
     */
    static _type of( TypeDeclaration td ){
        if( td.isTopLevelType() ){
            return of( td.findCompilationUnit().get(), td);
        }
        if( td instanceof ClassOrInterfaceDeclaration ){
            ClassOrInterfaceDeclaration coid = (ClassOrInterfaceDeclaration)td;
            if( coid.isInterface() ){
                return _interface.of( coid );
            }
            return _class.of(  coid );
        }
        if( td instanceof EnumDeclaration ){
            return _enum.of( (EnumDeclaration)td);
        }
        return _annotation.of( (AnnotationDeclaration)td);
    }

    /**
     * Builds the appropriate _model _type ({@link _class}, {@link _enum},
     * {@link _interface}, {@link _annotation})
     *
     * @param astRoot the compilationUnit
     * @param td the primary TYPE declaration within the CompilationUnit
     * @return the appropriate _model _type (_class, _enum, _interface, _annotation)
     */
    static _type of(CompilationUnit astRoot, TypeDeclaration td){
        if( td instanceof ClassOrInterfaceDeclaration ){
            ClassOrInterfaceDeclaration coid = (ClassOrInterfaceDeclaration)td;
            if( coid.isInterface() ){
                return _interface.of( coid );
            }
            return _class.of( coid );
        }
        if( td instanceof EnumDeclaration ){
            return _enum.of( (EnumDeclaration)td);
        }
        return _annotation.of( (AnnotationDeclaration)td);
    }

    /**
     * When we initialze or "import" the body of code for a _type... i.e.
     * <PRE>
     * //create the _class from the name, and anonymous object body
     * _class _c = _class.of("aaaa.bbbb.C", new Serializable(){
     *      Map m;
     *
     *      public UUID id(List l) throws IOException {
     *          return UUID.randomUUID();
     *      }
     * });
     *
     * there are "inferred imports" that we should use for the _class... in the above case
     * we need to import:
     * <UL>
     *     <LI>java.io.Serializable : the interface the anonymous object implements</LI>
     *     <LI>java.util.Map : the type of field m</LI>
     *     <LI>java.util.UUID : the return type of method id</LI>
     *     <LI>java.util.List : the parameter passed into method id</LI>
     *     <LI>java.io.IOException : the exception type throws from method id</LI>
     * </UL>
     * To do this... we reflectively look at the {@link java.lang.reflect.Field}s,
     * {@link java.lang.reflect.Method}s, and {@link java.lang.reflect.Constructor}s,
     * that exist in the anonymous object body, (as well as the declared interfaces and extended classes)
     * and create a Set of classes that represent these runtime Classes (to add via _import)
     *
     * NOTE: we chaeck each of the
     *
     * </PRE>
     * @param anonymousObjectBody an anonymous Object
     * @return a Set<Class> containing the surface level classes that should be automatically
     * inferred to be imported
     */
    public static Set<Class>inferImportsFrom(Object anonymousObjectBody){
        return inferImportsFrom(anonymousObjectBody.getClass());
    }

    public static Set<Class>inferImportsFrom(Class anonymousClass){
        Set<Class>classes = new HashSet<>();
        //implemented interfaces
        Arrays.stream(anonymousClass.getInterfaces()).forEach(c-> classes.add(c));

        //super class if not Object
        if( !anonymousClass.getSuperclass().equals(Object.class )){
            classes.add(anonymousClass.getSuperclass() );
        }
        // field types
        Arrays.stream(anonymousClass.getDeclaredFields()).forEach( f-> {
            if( !f.isSynthetic() && f.getAnnotation(_remove.class) == null ) {
                //System.out.println( "ADDING FIELD "+ f.getType()+ f );
                classes.add(f.getType());
            }
        } );
        //constructors
        Arrays.stream(anonymousClass.getDeclaredConstructors()).forEach( c -> {
            if( c.getAnnotation(_remove.class) == null ) {
                Arrays.stream(c.getParameters()).forEach( p -> {
                    if( p.isSynthetic() ){
                        //System.out.println("ADDING NON SYNTHETIC "+ p.getType() );
                        classes.add( p.getType() );
                    }
                } );
                //Arrays.stream(c.getParameterTypes()).forEach(p -> {
                //    if( p.is)
                //    classes.add(p);
                //});
                Arrays.stream(c.getExceptionTypes()).forEach(p -> classes.add(p));
            }
        });
        //methods
        Arrays.stream(anonymousClass.getDeclaredMethods()).forEach( m -> {
            if( m.getAnnotation(_remove.class) == null ) {
                classes.add(m.getReturnType());

                Arrays.stream(m.getParameters()).forEach( p -> {
                    //System.out.println("ADDING NON SYNTHETIC "+ p.getType() );
                    if( p.isSynthetic() ){
                        classes.add( p.getType() );
                    }
                } );
                //Arrays.stream(m.getParameterTypes()).forEach(p -> classes.add(p));
                Arrays.stream(m.getExceptionTypes()).forEach(p -> classes.add(p));
            }
        });
        //nested declared classes
        Arrays.stream(anonymousClass.getDeclaredClasses()).forEach( c -> {
            if( c.getAnnotationsByType(_remove.class).length == 0 ) {
                classes.addAll(inferImportsFrom(c));
            }
        });
        //lets not try importing java.lang.String or int.class
        classes.removeIf( c-> c.isPrimitive() ||
                ( c.getPackage() != null && c.getPackage().getName().equals("java.lang") ));
        return classes;
    }

    /**
     * A container for {@link _type}s
     */
    interface _hasTypes{

        /** list all of the {@link _type}s */
        List<_type> list();

        /** container of Types that is empty */
        boolean isEmpty();
    }

    /**
     * {@link _type} that can implement an {@link _interface}
     * @param <T> the TYPE (either {@link _class}, or {@link _enum})
     */
    interface _hasImplements<T extends _type & _hasImplements> {

        boolean hasImplements();

        List<ClassOrInterfaceType> listImplements();

        default T implement( _interface..._interfaces ){
            Arrays.stream(_interfaces).forEach(i-> implement(i.getFullName() ) );
            return (T)this;
        }

        T implement( ClassOrInterfaceType... toImplement );

        T implement( Class... toImplement );

        T implement( String... toImplement );

        T removeImplements( Class clazz);

        T removeImplements( ClassOrInterfaceType coit );

        boolean isImplements( String str );

        boolean isImplements( ClassOrInterfaceType ct );

        boolean isImplements( Class clazz );

        default boolean isImplements( _interface _i){
            return isImplements( _i.getFullName() );
        }
    }

    /**
     * A TYPE that can extend (another {@link _class} or {@link _interface})
     * @param <T>
     */
    interface _hasExtends<T extends _type & _hasExtends>{

        boolean hasExtends();

        NodeList<ClassOrInterfaceType> listExtends();

        T extend( ClassOrInterfaceType toExtend );

        default T extend( _class _c ){
            extend( _c.getFullName() );
            return (T)this;
        }

        default T extend( _interface _i ){
            extend( _i.getFullName() );
            return (T)this;
        }

        T extend( Class toExtend );

        T extend( String toExtend );

        boolean isExtends( String str );

        boolean isExtends( ClassOrInterfaceType ct );

        boolean isExtends( Class clazz );

        T removeExtends( Class clazz);

        T removeExtends( ClassOrInterfaceType coit );
    }
}
