package draft.java;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.*;
import com.github.javaparser.ast.nodeTypes.*;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import draft.DraftException;
import draft.Text;
import draft.java._import._imports;
import draft.java._model.*;
import draft.java.io._in;
import draft.java.io._io;
import draft.java.macro._macro;
import draft.java.macro._remove;

import java.io.InputStream;
import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * The Definition of a Java type (one of : class, enum, interface, @interface)
 * 
 * <PRE>
 * <H2>Traditional Programming Workflow</H2>:
 * 1) write code as text within an editor to create .java files
 * 2) call the javac compiler to convert the .java files to an AST then into
 * Java .class files (ByteCode)
 * 3) run the java command/program to create a Java VM to run the bytecode
 * <PRE> 
 * you
 *   |---------->---------->---------->
 * .java     [javac]     .class     [JVM] 
 * </PRE>
 * 
 * <H2>Draft Programming Workflow</H2>:
 * 1) (At runtime), build draft objects (which ARE ASTs) /instead of .java files/
 * 2) (At runtime) call the javac compiler to convert the draft objects (which ARE ASTs) into
 * Java .class files (ByteCode)
 * 3) (At runtime) load the .class byteCodes into the current runtime to use the classes
 * <PRE> 
 * you__________
 *   |          |
 *   |    ----draft---------------- 
 *   |   /      |       \          \
 *   |  /------AST       \          \
 *   | /        |         \          \
 *   |---------->---------->---------->
 * .java     [javac]     .class     [JVM]
 * </PRE>
 *
 * <PRE>
 * Examples:
 * //draft & compile return the (unloaded) byteCode for a drafted class
 * _classFiles _cfs = _javac.of(_class.of("C").fields("String firstName, lastName;"));
 *
 * //draft, compile & load a new {@link Class} into a new {@link ClassLoader}:
 * Class draft = _project.of(_class.of("C").fields("String firstName, lastName;")).getClass("C");
 *
 * //draft / compile/ load a new {@link Class} into a new {@link ClassLoader}
 * //& create a new instance:
 * Object o = _new.of( _class.of("C").fields("String firstName, lastName;"));
 * 
 * //draft / compile & load a new Class then create a new proxied instance and 
 * // call the getNum instance method
 * int num = (int)
 *    project.of( _class.of("C", new Object(){ public static int getNum(){return 123;} }))
 *        .proxy("C").call("getNum");
 * </PRE>
 * @author Eric
 * @param <AST> the Ast {@link TypeDeclaration} ({@link ClassOrInterfaceDeclaration},
 * {@link AnnotationDeclaration}, {@link EnumDeclaration}) that stores the state
 * and maintains the Bi-Directional AST Tree implementation
 * @param <T> the _type entity that provides logical access to manipulating the
 * AST
 */
public interface _type<AST extends TypeDeclaration & NodeWithJavadoc & NodeWithModifiers & NodeWithAnnotations, T extends _type>
    extends _javadoc._hasJavadoc<T>, _anno._hasAnnos<T>, _modifiers._hasModifiers<T>,
        _field._hasFields<T>, _member<AST, T> {

    static _type of( InputStream is ){
        return of(StaticJavaParser.parse(is));
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
     * List the members (_fields, _methods, _constructors,...) of the _type
     * @return
     */
    List<_member> listMembers();

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
     * find members that are of the specific class and perform the _memberAction on them
     * @param <M>
     * @param memberClass the member Class (i.e. _field, _method, _constructor)
     * @param _memberAction the Action function to apply to candidates
     * @return the modified T
     */
    default <M extends _member> T forMembers( Class<M> memberClass, Consumer<M> _memberAction){
        listMembers(memberClass).forEach(_memberAction);
        return (T)this;
    }
    
    /**
     * perform some action on the code if the _type extends a 
     * @param clazz specific class to test for extension
     * @param extendsTypeAction the action to perform if the type extends
     * @return true if the action was taken, false otherwise
     */
    default boolean ifExtends( Class clazz, Consumer<_hasExtends> extendsTypeAction){
        if( this instanceof _class && ((_class)this).isExtends(clazz) ){
            extendsTypeAction.accept((_class)this);            
            return true;
        }
        if( this instanceof _interface && ((_interface)this).isExtends(clazz) ){
            extendsTypeAction.accept((_interface)this);
            return true;
        }
        return false;
    }
    
    /**
     * Perform some action on the code if the code implements the specific class
     * @param clazz specific class to test for implementing
     * @param implementsTypeAction the action to perform if the type implements
     * @return true if the action was taken, false otherwise
     */
    default boolean ifImplements( Class clazz, Consumer<_hasImplements> implementsTypeAction ){
        if( this instanceof _class && ((_class)this).isImplementer(clazz) ){
            implementsTypeAction.accept( ((_class)this) );
            return true;
        }       
        if( this instanceof _enum && ((_enum)this).isImplementer(clazz) ){
            implementsTypeAction.accept( ((_enum)this) );
            return true;
        }
        return false;
    }
    
    /**
     * find members that are of the specific class and perform the _memberAction on them
     * @param <M>
     * @param memberClass the member Class (i.e. _field, _method, _constructor)
     * @param _memberMatchFn the matching function for selecting which members to take action on
     * @param _memberAction the Action function to apply to candidates
     * @return the modified T
     */
    default <M extends _member> T forMembers( Class<M> memberClass, Predicate<M> _memberMatchFn, Consumer<M> _memberAction){
        listMembers(memberClass, _memberMatchFn).forEach(_memberAction);
        return (T)this;
    }
    
    /**
     * Iterate & apply the action function to all Members that satisfy the _memberMatchFn
     * @param _memberMatchFn function for selecting which members to apply the _memberActionFn
     * @param _memberAction the action to apply to all selected members that satisfy the _memberMatchFn
     * @return the modified T type
     */
    T forMembers( Predicate<_member> _memberMatchFn, Consumer<_member> _memberAction );
    
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
                return _macro.to(clazz, _interface.of(coid));
            }
            return _macro.to(clazz,  _class.of(coid) );
        }else if( td instanceof EnumDeclaration){
            return _macro.to(clazz, _enum.of( (EnumDeclaration)td));
        }
        return _macro.to(clazz, _annotation.of( (AnnotationDeclaration)td));
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
     * @param astBlockComment the comment (i.e. the copyright)
     * @return the modified T
     */
    default T setHeaderComment( BlockComment astBlockComment ){
        if( isTopClass() ){
            if( ast().getComment().isPresent()){
                ast().removeComment();
            }
            ast().setComment(astBlockComment);
        }
        return (T)this;
    }

    /**
     * Sets the header comment (i.e. the copywrite)
     * @param commentLines the lines in the header comment
     * @return the modified T
     */
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
                this.ast().addMember( ((_field)_m).getFieldDeclaration() );
            } else{
                this.ast().addMember( (BodyDeclaration)_m.ast() );
            }
        }  );
        return (T)this;
    }
    
    /**
     * Apply a single type Function to the type and return the modified _type
     * @param _typeFn the function on the type
     * @return the modified type
     */
    default T apply( Function<_type, _type> _typeFn ){
        return (T)_typeFn.apply(this);
    }
    
    /**
     * Apply each of the Macros and return the modified T
     * @param _typeFn all of the macros to execute
     * @return the modified T after applying the macros
     */
    default T apply( Function<_type, _type>..._typeFn ){
        for(int i=0; i < _typeFn.length; i++){
            _typeFn[i].apply(this);
        }
        return (T)this;
    }
    
    /**
     * list all the members that match the predicate
     *
     * @param _memberMatchFn
     * @return matching members
     */
    default List<_member> listMembers( Predicate<_member> _memberMatchFn ){
        return listMembers().stream().filter(_memberMatchFn).collect(Collectors.toList());
    }

    /**
     * lists all of the members that are of a specific member class
     * @param <M> the specific member class to find
     * @param memberClass the member class
     * @return the list of members
     */
    default <M extends _member> List<M> listMembers( Class<M> memberClass ){
        List<M> found = new ArrayList<>();
        listMembers().stream().filter(m -> memberClass.isAssignableFrom(m.getClass()))
                .forEach(m -> found.add( (M)m) );
        return found;
    }
    
    /**
     * lists all of the members that are of a specific member class
     * @param <M> the specific member class to find
     * @param memberClass the member class
     * @param _memberMatchFn a matching function for selecting which members
     * @return the list of members
     */
    default <M extends _member> List<M> listMembers( Class<M> memberClass, Predicate<M> _memberMatchFn){
        return listMembers(memberClass).stream().filter(_memberMatchFn).collect(Collectors.toList());        
    }
    
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
        return getFullName( ast() );
    }

    /**
     * Builds the full NAME for the TYPE based on it's nest position within a class
     * (and its package)
     *
     * @param astType
     * @return the full NAME of the TYPE (separated by '.'s)
     */
    static String getFullName(TypeDeclaration astType){
        if( astType.isTopLevelType() ){
            if( astType.findCompilationUnit().get().getPackageDeclaration().isPresent()) {
                String pkgName = astType.findCompilationUnit().get().getPackageDeclaration().get().getNameAsString();
                return pkgName +"."+ astType.getNameAsString();
            }
            return astType.getNameAsString(); //top level TYPE but no package declaration
        }
        if(!astType.getParentNode().isPresent()){
            return astType.getNameAsString();
        }
        //prefix back to parents
        String name = astType.getNameAsString();
        Node n = astType.getParentNode().get();
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
        if( !this.isTopClass() ){ //this "means" that the class is an inner class
            // and we should move it OUT into it's own class at this package
            CompilationUnit cu = new CompilationUnit();    
            cu.setPackageDeclaration(packageName);
            cu.addType( (TypeDeclaration) this.ast() );            
            return (T) this;
        }
        CompilationUnit cu = findCompilationUnit();
        //System.out.println("Setting package name to \""+ packageName+"\" in "+ cu );
        //TODO I need to make sure it's a valid name
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
            return (T)_class.of( ((_class) this).ast().clone());
        }
        if( this instanceof _enum ) {
            return (T)_enum.of( ((_enum) this).ast().clone());
        }
        if( this instanceof _interface ) {
            return (T)_interface.of( ((_interface) this).ast().clone());
        }
        return (T)_annotation.of( ((_annotation) this).ast().clone());
    }

    /**
     * remove imports based on predicate
     * @param _importMatchFn filter for deciding which imports to removeIn
     * @return the modified TYPE
     */
    default T removeImports( Predicate<_import> _importMatchFn ){
        getImports().remove(_importMatchFn );
        return (T)this;
    }
    
    /**
     * removeIn imports by classes
     * @param clazzes
     * @return
     */
    default T removeImports( Class...clazzes ){
        _imports.of(findCompilationUnit()).remove(clazzes);
        return (T)this;        
    }

    default T removeImports( _import...toRemove ){
        _imports.of(findCompilationUnit()).remove(toRemove);
        return (T)this;
    }
    
    default T removeImports( _type..._typesToRemove ){
        getImports().remove(_typesToRemove);        
        return (T)this;
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
    
    /**
     * Select some imports based on the astImportPredicate and apply the 
     * astImportActionFn on the selected Imports
     * @param _importActionFn function to apply to the imports
     * @return the T
     */
    default T forImports( Consumer<_import> _importActionFn ){
        _imports.of(findCompilationUnit()).forEach(_importActionFn);
        //listImports(_importMatchFn).forEach(astImportActionFn);
        return (T)this;
    }
    
    /**
     * Select some imports based on the astImportPredicate and apply the 
     * astImportActionFn on the selected Imports
     * @param _importMatchFn selects the Imports to act on
     * @param _importActionFn function to apply to the imports
     * @return the T
     */
    default T forImports( Predicate<_import> _importMatchFn, Consumer<_import> _importActionFn ){
        _imports.of(findCompilationUnit()).forEach(_importMatchFn, _importActionFn);
        //listImports(_importMatchFn).forEach(astImportActionFn);
        return (T)this;
    }
    
    /**
     * Gets the _imports abstraction for the _type
     * @return the imports abstraction
     */
    default _imports getImports(){       
        return _imports.of(findCompilationUnit());
    }
    
    //TODO get rid of this in place of _imports, or getImports()    
    default List<ImportDeclaration> listAstImports(){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            return cu.getImports();
        }
        return new ArrayList<>();
    }
    
    default List<_import> listImports(){
        return getImports().list();       
    }
    
    default boolean hasImport( _type _t ){
        return hasImport( _t.getFullName() );
    }
    
    /**
     * Does this class statically import this class
     * i.e.
     * <PRE>
     * _class _c = _class.of("A").imports("import static draft.java.Ast.*;");
     * assertTrue( _c.hasImportStatic(Ast.class));
     * </PRE>
     * 
     * @param clazz
     * @return 
     */
    default boolean hasImportStatic( Class clazz ){
        return !listImports( i -> i.isStatic() && i.isWildcard() && i.hasImport(clazz)).isEmpty();        
    }

    /**
     * class or method name
     * <PRE>
     * 
     * </PRE>
     * @param className
     * @return 
     */
    default boolean hasImport(String className){
        return _imports.of(findCompilationUnit()).hasImport(className);        
    }

    default boolean hasImports( Class...clazzes ){
        return _imports.of(findCompilationUnit()).hasImports(clazzes);
    }
    
    default boolean hasImport(Class clazz ){        
        return _imports.of(findCompilationUnit()).hasImport(clazz);        
    }
    
    default boolean hasImport( Predicate<_import> _importMatchFn ){
        return !listImports( _importMatchFn ).isEmpty();
    }
    
    default boolean hasImport( _import _i){
        return listImports( i -> i.equals(_i )).size() > 0;
    }

    default List<_import> listImports( Predicate<_import> _importMatchFn ){
        return this.getImports().list().stream().filter( _importMatchFn ).collect(Collectors.toList());
    }

    /**
     * Adds static wildcard imports for all Classes
     * @param wildcardStaticImports a list of classes that will WildcardImports
     * @return the T
     */
    default T importStatic( Class ...wildcardStaticImports ){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            Arrays.stream(wildcardStaticImports).forEach( i -> {
                ImportDeclaration id = Ast.importDeclaration(i);
                id.setAsterisk(true);
                id.setStatic(true);
                cu.addImport( id );
                /*
                if( i.isArray() ){
                    cu.addImport(new ImportDeclaration((i.getComponentType().getCanonicalName()), true, true));
                } else {
                    cu.addImport(new ImportDeclaration(i.getCanonicalName(), true, true));
                }
                */
            });
        }
        return (T)this;
    }

    /**
     *
     * @param staticWildcardImports
     * @return
     */
    default T importStatic( String...staticWildcardImports){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            Arrays.stream(staticWildcardImports).forEach( i -> {
                ImportDeclaration id = Ast.importDeclaration(i);
                id.setStatic(true);
                id.setAsterisk(true);
                cu.addImport(id);
                /*
                if( i.endsWith(".*")) {
                    ImportDeclaration id = Ast.importDeclaration(i);
                    cu.addImport(new ImportDeclaration(i.substring(0, i.length() - 2), true, true));
                } else{
                    cu.addImport(new ImportDeclaration(i, true, false));
                }
                */
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
            for(int i=0;i<classesToImport.length; i++){
                if( classesToImport[i].isArray() ){
                    //System.out.println("CT " + classesToImport[i].getComponentType() );
                    imports(classesToImport[i].getComponentType());
                }else{
                    //dont import primitives or primitive arrays
                    if( classesToImport[i] == null 
                        || classesToImport[i].isPrimitive() 
                        || classesToImport[i].isArray() && classesToImport[i].getComponentType().isPrimitive() 
                        || classesToImport[i].getPackageName().equals("java.lang") ) {
                        break;
                    }
                    String cn = classesToImport[i].getCanonicalName();
                    //fix a minor bug in JavaParser API where anything in "java.lang.**.*" is not imported
                    // so java.lang.annotation.* classes are not imported when they should be
                    if( classesToImport[i].getPackage() != Integer.class.getPackage()
                        && classesToImport[i].getCanonicalName().startsWith("java.lang") ) {
                        //System.out.println( "manually adding "+ classesToImport[i].getCanonicalName());
                        cu.addImport(classesToImport[i].getCanonicalName());
                    } else {
                        cu.addImport(cn);
                    }
                }
            }
            return (T)this;
        }
        throw new DraftException("No AST CompilationUnit of TYPE "+ ((T)this).getName()+" to add imports");
    }

    default T imports( ImportDeclaration...importDecls){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            Arrays.stream( importDecls ).forEach( c-> cu.addImport( c ) );
            return (T)this;
        }
        //return (T) this;
        throw new DraftException("No AST CompilationUnit of class "+ getName()+" to add imports");
    }

    default T imports( String...importStatements ){
        CompilationUnit cu = findCompilationUnit();
        if( cu != null ){
            Arrays.stream( importStatements ).forEach( c-> cu.addImport( Ast.importDeclaration( c ) ) );
            return (T)this;
        }
        //return (T) this;
        throw new DraftException("No AST CompilationUnit of "+ getName()+" to add imports");
    }

    @Override
    default T javadoc( String...content ){
        ast().setJavadocComment( Text.combine(content));
        return (T)this;
    }

    @Override
    default T javadoc( JavadocComment astJavadocComment ){
        ast().setJavadocComment( astJavadocComment );
        return (T)this;
    }
    
    @Override
    default T removeJavadoc(){
        ast().removeJavaDocComment();
        return (T) this;
    }

    @Override
    default boolean hasJavadoc(){
        return ast().getJavadocComment().isPresent();
    }

    @Override
    default _javadoc getJavadoc() {
        return _javadoc.of(this.ast());
    }

    @Override
    default _modifiers getModifiers(){
        return _modifiers.of(this.ast() );
    }

    default boolean isPublic(){
        return this.ast().isPublic();
    }

    /**
     * Is this type of "package private" accessibility
     * (i.e. it does NOT have public private or protected modifier)
     * <PRE>
     * interface F{}
     * class C{}
     * @interface A{}
     * enum E{}
     * </PRE>
     * @return 
     */
    default boolean isPackagePrivate(){
        return !this.ast().isProtected() &&
                !this.ast().isPrivate() &&
                !this.ast().isPublic();
    }

    default boolean isProtected(){
        return this.ast().isProtected();
    }

    default boolean isPrivate(){
        return this.ast().isPrivate();
    }

    default boolean isStatic(){
        return this.ast().isStatic();
    }

    default boolean isStrictFp(){
        return this.ast().isStrictfp();
    }

    default T setPublic(){
        this.ast().setPrivate(false);
        this.ast().setProtected(false);
        this.ast().setPublic(true);        
        return (T)this;
    }

    default T setProtected(){
        this.ast().setPublic(false);
        this.ast().setPrivate(false);
        this.ast().setProtected(true);
        return (T)this;
    }
    default T setPrivate(){
        this.ast().setPublic(false);
        this.ast().setPrivate(true);
        this.ast().setProtected(false);
        return (T)this;
    }
    /**
     * Sets the type to be package private (i.e. NO public, protected or private modifier)
     * i.e.
     * <PRE>
     * class P{
     * }
     * </PRE>
     * @return the modified T
     */
    default T setPackagePrivate(){
        this.ast().setPublic(false);
        this.ast().setPrivate(false);
        this.ast().setProtected(false);
        return (T)this;
    }

    @Override
    default NodeList<Modifier> getEffectiveModifiers() {
        NodeList<Modifier> implied = Ast.getImpliedModifiers( this.ast() );
        return Ast.merge( implied, this.ast().getModifiers());
    }

    @Override
    default T name( String name ){
        ast().setName( name );
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
        return ast().getNameAsString();
    }

    @Override
    default List<_field> listFields() {
        List<_field> _fs = new ArrayList<>();
        ast().getFields().forEach( f->{
            FieldDeclaration fd = ((FieldDeclaration)f);
            for(int i=0;i<fd.getVariables().size();i++){
                _fs.add(_field.of(fd.getVariable( i ) ) );
            }
        });
        return _fs;
    }

    /**
     *
     * @param _fieldMatchFn
     * @return 
     */
    @Override
    default List<_field> listFields( Predicate<_field> _fieldMatchFn ){
        return listFields().stream().filter(_fieldMatchFn).collect(Collectors.toList());
    }

    /**
     * List the fully qualified names of all types defined in this _type
     * @return 
     */
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
        //this.ast().addMember(type.ast());
        ((TypeDeclaration)this.ast()).addMember( (TypeDeclaration)type.ast() );
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
     * 
     * @param nestToRemove
     * @return 
     */
    default T removeNest( _type nestToRemove ){
        listNests( t-> t.equals(nestToRemove) ).forEach( n -> n.ast().removeForced() );
        return (T) this; 
    }
    
    /**
     * 
     * @param nestToRemove
     * @return 
     */
    default T removeNest( TypeDeclaration nestToRemove ){
        return removeNest( _type.of(nestToRemove ) );        
    }
    
    /**
     * list all nested types that match this _typeMatchFn
     * @param typeMatchFn function to
     * @return matching nested types or empty list if none found
     */
    default List<_type> listNests( Predicate<? super _type> typeMatchFn ){
        return listNests().stream().filter( typeMatchFn ).collect(Collectors.toList());
    }

    /*
    default void ifClass( Consumer<_class> _classAction ){
        if( this instanceof _class ){
            _classAction.accept((_class)this);
        }
    }
    
    default void ifEnum( Consumer<_enum> _enumAction ){
        if( this instanceof _enum ){
            _enumAction.accept((_enum)this);
        }
    }
    
    default void ifInterface( Consumer<_interface> _interfaceAction ){
        if( this instanceof _interface ){
            _interfaceAction.accept((_interface)this);
        }
    }
    
    default void ifAnnotation( Consumer<_annotation> _annotationAction ){
        if( this instanceof _annotation ){
            _annotationAction.accept((_annotation)this);
        }
    }
    */
    
    /**
     * list all nested children underneath this logical _type
     * (1-level, DIRECT CHILDREN, and NOT grand children or great grand children)
     * for a more comprehensive gathering of all types, call:
     *
     * @return the direct children (nested {@link _type}s) of this {@link _type}
     */
    default List<_type> listNests(){
        NodeList<BodyDeclaration> bds = ast().getMembers();
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
                //System.out.println( "ADDING ENUM NEST "+ ((EnumDeclaration) t).getNameAsString());
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
     * 
     * When we initialize or "import" the body of code for a _type... i.e.
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
     * TODO: ???? What about RuntimeAnnotations? (on each type, method, parameter, etc.
     * should I include the imports for these things?? and when... 
     * for right now NO, but possible in the future
     * 
     * </PRE>
     * @param anonymousObjectBody an anonymous Object
     * @return a Set<Class> containing the surface level classes that should be automatically
     * inferred to be imported
     */
    public static Set<Class>inferImportsFrom(Object anonymousObjectBody){
        return inferImportsFrom(anonymousObjectBody.getClass());
    }

    /**
     * 
     * @param anonymousClass
     * @return 
     */
    public static Set<Class>inferImportsFrom(Class anonymousClass){
        Set<Class>classes = new HashSet<>();
        //implemented interfaces
        Arrays.stream(anonymousClass.getInterfaces()).forEach(c-> classes.add(c));

        //super class if not Object
        if( anonymousClass.getSuperclass() != null &&
            !anonymousClass.getSuperclass().equals(Object.class )){
            classes.add(anonymousClass.getSuperclass() );
        }
        // field types
        Arrays.stream(anonymousClass.getDeclaredFields()).forEach( f-> {
            if( !f.isSynthetic() && f.getAnnotation(_remove.class) == null ) {
                classes.add(f.getType());
            }
        } );
        //constructors
        Arrays.stream(anonymousClass.getDeclaredConstructors()).forEach( c -> {
            if( c.getAnnotation(_remove.class) == null ) {
                Arrays.stream(c.getParameters()).forEach( p -> {
                    if( p.isSynthetic() ){
                        classes.add( p.getType() );
                    }
                } );
                Arrays.stream(c.getExceptionTypes()).forEach(p -> classes.add(p));
            }
        });
        //methods
        Arrays.stream(anonymousClass.getDeclaredMethods()).forEach( m -> {
            if( m.getAnnotation(_remove.class) == null ) {
                classes.add(m.getReturnType());

                Arrays.stream(m.getParameters()).forEach( p -> {
                    if( !p.isSynthetic() ){
                        classes.add( p.getType() );
                    }
                } );
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
        classes.removeIf( c-> c.isPrimitive() || ( c.isArray() && c.getComponentType().isPrimitive() ) 
                || (c.getCanonicalName() == null)
                || ( c.getPackage() != null && c.getPackage().getName().equals("java.lang") ));
        return classes;
    }

    /**
     * A container for {@link _type}s
     */
    interface _hasTypes extends _model{

        /** @return list all of the {@link _type}s */
        List<_type> list();

        default List<_type> list(Predicate<_type> _typePredicate ){
            return list().stream().filter(_typePredicate).collect(Collectors.toList());
        }
         
        /** @return container of Types that is empty */
        boolean isEmpty();
    }

    /**
     * {@link _type} that can implement an {@link _interface}
     * @param <T> the TYPE (either {@link _class}, or {@link _enum})
     */
    interface _hasImplements<T extends _type & _hasImplements> {

        default boolean hasImplements(){
            return !listImplements().isEmpty();
        }
        
        default boolean isImplementer( String str ){
            try{
                return _hasImplements.this.isImplementer( (ClassOrInterfaceType)Ast.typeRef( str ) );
            }catch( Exception e){}
            return false;
        }

        default boolean isImplementer( ClassOrInterfaceType ct ){
            return ((NodeWithImplements)((_type)this).ast()).getImplementedTypes().contains(ct);
            //return this.astEnum.getImplementedTypes().contains( ct );
        }
        
        default boolean isImplementer( Class clazz ){
            try{
                _type t = ((_type)this);
                
                return _hasImplements.this.isImplementer( (ClassOrInterfaceType)Ast.typeRef( clazz ) ) ||
                    t.hasImport( clazz ) && _hasImplements.this.isImplementer(clazz.getSimpleName() );
            } catch( Exception e){ }
            return false;
        }
    
        default boolean isImplementer( _interface _i){
            return _hasImplements.this.isImplementer( _i.getFullName() );
        }
        
        default List<ClassOrInterfaceType> listImplements(){
            return ((NodeWithImplements)((_type)this).ast()).getImplementedTypes();
        }

        default T implement( _interface..._interfaces ){
            Arrays.stream(_interfaces).forEach(i-> implement(i.getFullName() ) );
            return (T)this;
        }        
        
        default T implement( ClassOrInterfaceType... toImplement ){
            NodeWithImplements nwi = ((NodeWithImplements)((_type)this).ast());
            Arrays.stream( toImplement ).forEach(i -> nwi.addImplementedType( i ) );
            return (T)this;
        }
        
        default T implement( Class... toImplement ){
            NodeWithImplements nwi = ((NodeWithImplements)((_type)this).ast());
            
            Arrays.stream( toImplement )
                .forEach(i -> {
                        ClassOrInterfaceType coit = (ClassOrInterfaceType)Ast.typeRef(i);                    
                        nwi.addImplementedType( coit );   
                        ((_type)this).imports(i);
                    });
            return (T)this;
        }

        default T implement( String... toImplement ){
            NodeWithImplements nwi = ((NodeWithImplements)((_type)this).ast());
            Arrays.stream( toImplement ).forEach(i -> nwi.addImplementedType( i ) );
            return (T)this;
        }
        
        T removeImplements( Class clazz);
        
        T removeImplements( ClassOrInterfaceType coit );
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
