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
import draft.java._java.*;
import draft.java.io._in;
import draft.java.macro._macro;
import draft.java.macro._remove;

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
 * 
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
        _field._hasFields<T>, _member<AST, T>, _code<T>, _node<AST> {

    

    /**
     * List the members (_fields, _methods, _constructors,...) of the _type
     * @return
     */
    List<_member> listMembers();

    /**
     * 
     * @param _methodMatchFn function for matching appropriate methods
     * @return a list of matching methods
     */
    List<_method> listMethods(Predicate<_method> _methodMatchFn );
    
    /**
     * Is this TYPE the top level class TYPE within a compilationUnit 
     * (i.e. the child of a CompilationUnit?) 
     * @return true if the _type is a top level TYPE, false otherwise
     */
    @Override
    boolean isTopLevel();
    
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
    @Override
    CompilationUnit astCompilationUnit();    
    
    
    /**
     * If we are a top level _type add the types as companion types
     * (other top level types that are package private) to the CompilationUnit
     * 
     * @param _ts
     * @return 
     */
    default T addCompanionTypes( _type..._ts ){
        if( this.isTopLevel() ){            
            for(int i=0;i<_ts.length; i++){
                TypeDeclaration td = (TypeDeclaration)(_ts[i].setPackagePrivate()).ast();
                if( td.getParentNode().isPresent() ){
                    //System.out.println("divorcing parent");
                    td.getParentNode().get().remove(td);
                }
                //System.out.println( "ADDING "+ td );
                
                //CompilationUnit cu = this.astCompilationUnit();
                CompilationUnit cu = ast().findCompilationUnit().get();
                //System.out.println("BEFORE "+ cu);
                //System.out.println("TYPES "+ cu.getTypes() );
                
                //SO it looks like I have to add the 
                //NodeList<TypeDeclaration<?>> cuts = cu.getTypes();
                cu.getTypes().add(td);
                //cu.addClass("BILL").setPrivate(false).setPublic(false).setProtected(false);
                //cu = cu.addType(td);    
                
                //System.out.println("AFTER TYPES "+ cu.getTypes());
            }
            return (T)this;        
        }   
        throw new DraftException
            ("cannot add companion Types to a Nested Type \""+this.getName()+"\"");
    }
    
    /**
     * If we are a top level _type add the types as companion types
     * (other top level types that are package private) to the CompilationUnit
     * 
     * @param astTs
     * @return 
     */
    default T addCompanionTypes( TypeDeclaration...astTs ){
        if( this.isTopLevel() ){            
            for(int i=0;i<astTs.length; i++){
                //manually set it to package private
                astTs[i].setPrivate(false);
                astTs[i].setPublic(false);
                astTs[i].setProtected(false);
                this.astCompilationUnit().addType(astTs[i]);
            }
            return (T)this;        
        }   
        throw new DraftException
            ("cannot add companion Types to a Nested Type \""+this.getName()+"\"");
    }
    
    /**
     * Looks for all "companion types" that match the _typeMatchFn and removes them
     * "companion types" are top level types that are "package private" (i.e. they
     * are neither public, private, or protected)
     * 
     * @param _typeMatchFn
     * @return the modified T
     */
    default T removeCompanionTypes( Predicate<_type> _typeMatchFn){
        listCompanionTypes(_typeMatchFn)
            .forEach( t -> astCompilationUnit().remove( t.ast() ) );
        return (T)this;
    }
    
     /**
     * Looks for all "companion types" that match the _typeMatchFn and removes them
     * "companion types" are top level types that are "package private" (i.e. they
     * are neither public, private, or protected)
     * 
     * @param name the name of the companion type to remove
     * @return the modified T
     */
    default T removeCompanionType( String name ){
        return removeCompanionTypes( t-> t.getName().equals(name) );
    }
    
    /**
     * Applies transforms to "companion types" declared in this compilationUnit
     * "companion types" are top level types that are "package private" (i.e. they
     * are neither public, private, or protected)
     * 
     * {@link _type}s (NOTE: this COULD include the primary type if the 
     * "primary type" is also package private)
     * 
     * @param _typeActionFn
     * @return 
     */
    default T forCompanionTypes( Consumer<_type> _typeActionFn ){
        listCompanionTypes().forEach(_typeActionFn);
        return (T)this;
    }
    
    /**
     * Applies transforms to "companion types" declared in this compilationUnit
     * "companion types" are top level types that are "package private" (i.e. they
     * are neither public, private, or protected)
     * (i.e. {@link _class}.class {@link _interface}.class {@link _enum}.class, {@link _annotation}.class)
     * with the _typeActionFn
     * 
     * @param <CT>
     * @param packagePrivateType
     * @param _typeActionFn
     * @return the modified T
     */
    default <CT extends _type> T forCompanionTypes( 
        Class<CT> packagePrivateType, Consumer<CT> _typeActionFn ){
        
        _type.this.listCompanionTypes(packagePrivateType).forEach(_typeActionFn);
        return (T)this;
    }
    
    /**
     * Apply a transform to all "companion types"
     * 
     * "companion types" are top level types that are "package private" (i.e. they
     * are neither public, private, or protected)
     * @param <CT>
     * @param packagePrivateType
     * @param _typeMatchFn
     * @param _typeActionFn
     * @return the modified T
     */
    default <CT extends _type> T forCompanionTypes( 
        Class<CT> packagePrivateType, Predicate<CT>_typeMatchFn, Consumer<CT> _typeActionFn ){
        
        _type.this.listCompanionTypes(packagePrivateType, _typeMatchFn).forEach(_typeActionFn);
        return (T)this;
    }
    
    /**
     * List all top level "companion types" that match the _typeMatchFn
     * 
     * "companion types" are top level types that are "package private" (i.e. they
     * are neither public, private, or protected)
     * 
     * @param _typeMatchFn
     * @return 
     */
    default List<_type> listCompanionTypes(Predicate<_type> _typeMatchFn){
        List<_type> found = new ArrayList<>();
        listCompanionTypes().stream()
                .filter(t-> _typeMatchFn.test(t) )
                .forEach(t-> found.add(t) );
        return found;
    }
    
    /**
     * List all top level "companion types" within this compilationUnit
     * "companion types" are top level types that are "package private" (i.e. they
     * are neither public, private, or protected)
     * (assuming this is a top level type)
     * 
     * @param <CT>
     * @param _typeClass
     * @return 
     */
    default <CT extends _type> List<CT> listCompanionTypes(Class<CT> _typeClass ){
        List<CT> found = new ArrayList<>();
        listCompanionTypes().stream()
                .filter(t-> t.getClass().equals(_typeClass) )
                .forEach(t-> found.add((CT)t ) );
        return found;
    }
    
    /**
     * List all top level "companion types" within this compilationUnit that
     * are of the _typeClass( i.e. {@link _class}, {@link _enum}, ...) and match
     * the _typeMatchFn.
     * 
     * "companion types" are top level types that are "package private" (i.e. they
     * are neither public, private, or protected)
     * (assuming this is a top level type)
     * @param <CT>
     * @param _typeClass
     * @param _typeMatchFn
     * @return 
     */
    default <CT extends _type> List<CT> listCompanionTypes(Class<CT> _typeClass, Predicate<CT> _typeMatchFn){
        List<CT> found = new ArrayList<>();
        _type.this.listCompanionTypes(_typeClass).stream()
                .filter(t-> _typeMatchFn.test(t) )
                .forEach(t-> found.add((CT)t ) );
        return found;
    }
    
    /**
     * List all top level "companion types" within this compilationUnit
     * 
     * "companion types" are top level types that are "package private" 
     * (i.e. they are neither public, private, or protected)
     * 
     * NOTE: according to the JLS there can be ONLY ONE public top level type (or none)
     * but each "compilationUnit" can have 
     * @return 
     */
    default List<_type> listCompanionTypes(){
        if( isTopLevel() ){
            List<_type> _ts = new ArrayList<>();
            List<TypeDeclaration<?>> tds = 
                this.astCompilationUnit().getTypes();
            tds.stream().filter(t-> !t.isPublic()).forEach(t ->_ts.add(_java.type(t) ) );                    
            return _ts;
        }
        return Collections.EMPTY_LIST;
    }
    
    /**
     * Attempts to find a "companion type" by name and return it
     * 
     * "companion types" are top level types that are "package private" 
     * (i.e. they are neither public, private, or protected)
     * 
     * @param name the name of the package private type to get
     * @return the package private type
     */
    default _type getCompanionType( String name ){
        List<_type> ts = _type.this.listCompanionTypes( t-> t.getName().equals(name) ); 
        if( ts.isEmpty() ){
            return null;            
        }
        return ts.get(0); //just return the first one        
    }
    
    /**
     * Attempts to find a "companion type" by name and type and return it
     * 
     * "companion types" are top level types that are "package private" 
     * (i.e. they are neither public, private, or protected)
     * 
     * 
     * @param <CT> the package private type {@link _class}{@link _enum} 
     * {@link _interface}, {@link _annotation}
     * @param typeClass
     * 
     * @param name the name of the package private type to get
     * @return the package private type
     */
    default  <CT extends _type> CT getCompanionType(Class<CT>typeClass, String name ){
        List<CT> ts = _type.this.listCompanionTypes( typeClass, t-> t.getName().equals(name) ); 
        if( ts.isEmpty() ){
            return null;            
        }
        return ts.get(0); //just return the first one        
    }
    
    /**
     * Gets the Primary Type according to the CompilationUnit
     * the "primary type" is the type associated with the file name)
     * 
     * NOTE: it is not always easy to define there does NOT have to be a primary 
     * type for a compilationUnit.  
     * 
     * If we can find the compilationUnit, the compilationUnit SHould be 
     * associated with a fileName (in the Storage field).  the primary type
     * does NOT have to be public, but must be using the same name as the 
     * fileName
     * 
     * @return the primary type (if it can be resolved) or null
     */
    default _type getPrimaryType(){
        if( this.isTopLevel() ){
            if (this.astCompilationUnit().getPrimaryType().isPresent()) {
                return _java.type( this.astCompilationUnit().getPrimaryType().get() );
            }            
            Optional<TypeDeclaration<?>> ot = 
                this.astCompilationUnit().getTypes().stream().filter(t -> t.isPublic() ).findFirst();
            if( ot.isPresent() ){
                return _java.type(ot.get());
            }            
        }        
        return null;
    }
     
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
        if( this instanceof _class && ((_class)this).isImplements(clazz) ){
            implementsTypeAction.accept( ((_class)this) );
            return true;
        }       
        if( this instanceof _enum && ((_enum)this).isImplements(clazz) ){
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
     * Gets the comment (i.e. the copyright, etc.)
     * at the top of the CompilationUnit
     * (NOTE: returns null if there are no header comments or
     * this type is not a top level type)
     *
     * @return the Comment a JavaDoc comment or BlockComment or null
     */
    @Override
    default Comment getHeaderComment(){
        if( isTopLevel() && astCompilationUnit().getComment().isPresent()){
            return astCompilationUnit().getComment().get();
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
    @Override
    default T setHeaderComment( BlockComment astBlockComment ){
        if( isTopLevel() ){
            if( astCompilationUnit().getComment().isPresent()){
                astCompilationUnit().removeComment();
            }
            astCompilationUnit().setComment(astBlockComment);
        }
        return (T)this;
    }

    /**
     * add one or more _member(s) (_field, _method, _constructor, enum._constant, etc.) to the BODY of the _type
     * and return the modified _type
     * @param _members
     * @return
     */
    default T add( _member..._members ){
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
     * Gets the simple name of the Type
     * @return 
     */
    @Override
    default String getSimpleName(){
        TypeDeclaration astType = ast();
        return astType.getNameAsString();        
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
        CompilationUnit cu = astCompilationUnit();
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
        CompilationUnit cu = astCompilationUnit();
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
        if( !this.isTopLevel() ){ //this "means" that the class is an inner class
            // and we should move it OUT into it's own class at this package
            CompilationUnit cu = new CompilationUnit();    
            cu.setPackageDeclaration(packageName);
            cu.addType( (TypeDeclaration) this.ast() );            
            return (T) this;
        }
        CompilationUnit cu = astCompilationUnit();
        //System.out.println("Setting package name to \""+ packageName+"\" in "+ cu );
        //TODO I need to make sure it's a valid name
        cu.setPackageDeclaration( packageName );        
        return (T)this;
    }

    /**
     * Does this type reside in this package
     * @param packageName
     * @return 
     */
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
        if( this.isTopLevel()){
            if( this instanceof _class ) {
                return (T)_class.of(this.astCompilationUnit().clone());
            }
            if( this instanceof _enum ) {
                return (T)_enum.of(this.astCompilationUnit().clone());
            }
            if( this instanceof _interface ) {
                return (T)_interface.of(this.astCompilationUnit().clone());
            }
            return (T)_annotation.of(this.astCompilationUnit().clone());
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

    /**
     * does this type extend the other type?
     * NOTE: this does not work for Generics, use 
     * {@link #isExtends(ClassOrInterfaceType) }
     * @param _t the type to check
     * @return true if this type extends _t
     */
    default boolean isExtends( _type _t ){
        return isExtends(_t.getFullName() );
    }
    
    /**
     * 
     * @param astType
     * @return 
     */
    default boolean isExtends( ClassOrInterfaceType astType ){
        if( this instanceof _hasExtends ){
            NodeList<ClassOrInterfaceType> extnds = 
                ((NodeWithExtends)((_type)this).ast()).getExtendedTypes();
            return extnds.stream().filter(i -> Ast.typesEqual(i, astType)).findFirst().isPresent();
        }
        return false;
    }
    
    /**
     * does this type extend the type described in baseType?  
     * (Note this will parse/deal with Generics appropriately)
     * @param baseType
     * @return true if the type extends the baseType
     */
    default boolean isExtends( String baseType ){
        try{
            return isExtends( (ClassOrInterfaceType)Ast.typeRef( baseType ) );
        } catch( Exception e){}
        
        if( baseType.contains(".") ){
            return isExtends (baseType.substring(baseType.lastIndexOf(".")+1 ) );
        }
        return false;
    }
    
    /**
     * does this type extend this specific class 
     * (Note: this does not handle generic base classes... for that use 
     * {@link #isExtends(com.github.javaparser.ast.type.ClassOrInterfaceType)}
     * @param clazz the class type
     * @return true if the type extends this (raw) type
     */
    default boolean isExtends( Class clazz ){
        try{
            return isExtends( (ClassOrInterfaceType)Ast.typeRef( clazz ) ) ||
                isExtends( (ClassOrInterfaceType)Ast.typeRef( clazz.getSimpleName() ) );
        }catch( Exception e){}
        return false;
    }
   
    /**
     * 
     * @param str
     * @return 
     */
    default boolean isImplements( String str ){
        try{
            return isImplements( (ClassOrInterfaceType)Ast.typeRef( str ) );
        } catch( Exception e){}
        
        if( str.contains(".") ){
            return isImplements (str.substring(str.lastIndexOf(".")+1 ) );
        }
        return false;        
    }

    /**
     * does this _type implement this (specific) classOrInterfaceType
     * (NOTE: this WILL handle generics, i.e. (Fileable<File>)
     * @param astType 
     * @return 
     */
    default boolean isImplements( ClassOrInterfaceType astType ){        
        NodeList<ClassOrInterfaceType> impls = 
            ((NodeWithImplements)((_type)this).ast()).getImplementedTypes();
        return impls.stream().filter(i -> Ast.typesEqual(i, astType)).findFirst().isPresent();        
    }
        
    /**
     * does this class implement this (raw) interface
     * @param clazz the (raw...not generic) interface class
     * @return true 
     */
    default boolean isImplements( Class clazz ){        
        return isImplements( StaticJavaParser.parseClassOrInterfaceType(clazz.getCanonicalName()) ) 
                || isImplements( StaticJavaParser.parseClassOrInterfaceType(clazz.getSimpleName()) );        
    }
    
    /**
     * Does this type implement this (raw...not generic) interface type
     * @param _i the interface type to check
     * @return true if this _type implements _i 
     */
    default boolean isImplements( _interface _i){
        return isImplements(_i.getFullName() );        
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
        return removeNest( _java.type(nestToRemove ) );        
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
     * A container for {@link _type}s
     */
    interface _hasTypes extends _java{

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


        T removeExtends( Class clazz);

        T removeExtends( ClassOrInterfaceType coit );
    }            
}
