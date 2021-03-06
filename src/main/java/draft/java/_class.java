package draft.java;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;

import draft.java._anno.*;
import draft.DraftException;
import draft.java.io._in;
import draft.java.macro._macro;
import draft.java.macro._remove;

import java.io.InputStream;
import java.util.*;
import java.util.function.*;
import draft.java.macro._toCtor;

/**
 * Top-Level draft object representing a Java class, and implementation of a {@link _type}<BR/>
 *
 * Logical Mutable Model of the source code representing a Java class.<BR>
 *
 * Implemented as a "Logical Facade" on top of an AST
 * ({@link ClassOrInterfaceDeclaration}) for logical manipulation
 *
 * @author Eric
 */
public final class _class implements _type<ClassOrInterfaceDeclaration, _class>,
        _method._hasMethods<_class>, _constructor._hasConstructors<_class, ClassOrInterfaceDeclaration>,
        _typeParameter._hasTypeParameters<_class>, _staticBlock._hasStaticBlocks<_class>,
        _modifiers._hasAbstract<_class>,_modifiers._hasFinal<_class>,
        _modifiers._hasStatic<_class>,_type._hasImplements<_class>,
        _type._hasExtends<_class>{

    /**
     * Build a _class from the source of the Class, while applying any
     * runtime _macro ANNOTATIONS (i.e. @_static, @_final...) to the components on the
     * class; and finally calling the optional typeFns in order to mutate the {@link _class}<BR/>
     * Example:
     * <PRE>
     *     @_package("aaaa") class C { int a,b; @_static String NAME = "A";}
     *     _class _c = _class.of( C.class, _autoDto.$);
     *
     *     // the above will build the class _c from the source of C,
     *     // apply the @_package, and @_static macros to update _c
     *     // then apply the {@link draft.java.macro._autoDto} _macro to _c
     * </PRE>
     *
     * @param clazz
     * @param typeFns
     * @return
     */
    public static _class of( Class clazz, Function<_type, _type>...typeFns  ){
        Node n = Ast.type( clazz );
        if( n instanceof CompilationUnit ){            
            _class _c = of( (CompilationUnit)n);
            
            _macro.to(clazz, _c);
            
            for(int i=0; i< typeFns.length; i++){
                _c = (_class)typeFns[i].apply(_c);
            }
            return _c;
        } else if( n instanceof ClassOrInterfaceDeclaration){
            _class _c = of( (ClassOrInterfaceDeclaration)n);
            
            _c = _macro.to(clazz, _c); //run annotation macros on the class
            Set<Class> importClasses = _import.inferImportsFrom(clazz);
            _c.imports(importClasses.toArray(new Class[0]));
            for(int i=0; i< typeFns.length; i++){
                _c = (_class)typeFns[i].apply(_c);
            }
            return _c;
        } else if( n instanceof LocalClassDeclarationStmt){
            //System.out.println("Local Class");
            //TODO I need to break the reference to the "old" AST
            LocalClassDeclarationStmt loc = (LocalClassDeclarationStmt)n;
            
            _class _c = of(  ((LocalClassDeclarationStmt)n).getClassDeclaration());
            if( loc.getComment().isPresent() ){
                _c.ast().setComment( loc.getComment().get());
            }
            Set<Class> importClasses = _import.inferImportsFrom(clazz);            
            _c.imports(importClasses.toArray(new Class[0]));
            _c = _macro.to(clazz, _c);
            for(int i=0; i< typeFns.length; i++){
                _c = (_class)typeFns[i].apply(_c);
            }            
            return _c;
        }
        throw new DraftException("Abstract or synthetic classes are not supported"+ clazz);
    }

    /**
     * return a _class from this input
     * @param in
     * @return 
     */
    public static _class of( _in in ){
        return of( in.getInputStream() );
    }

    /**
     * create and return a _class representing the Class source within the inputStream
     * @param is
     * @return 
     */
    public static _class of( InputStream is ){
        return (_class)_java.type(is);
    }

    /**
     * 
     * <PRE>
     * i.e.
     * _class _c = _class.of( new Object(){
     *     @_public @_static class F{
     *          int x;
     *     }
     * }, _autoGet.$);
     * //produces:
     * 
     * public static class F{
     *    int x;
     *    
     *    public int getX(){
     *       return this.x;
     *    }
     * }
     * </PRE>
     * @param anonymousObjectWithLocalClass
     * @param macroFunctions
     * @return
     */
    public static _class of( Object anonymousObjectWithLocalClass, Function<_type, _type>...macroFunctions ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr anon = Expr.anonymousObject(ste);
        if( anon.getAnonymousClassBody().isPresent() ){
            NodeList<BodyDeclaration<?>> bdy = anon.getAnonymousClassBody().get();
            Optional<BodyDeclaration<?>> obd = bdy.stream().filter( b -> b instanceof ClassOrInterfaceDeclaration
                    && !b.getAnnotationByClass(_remove.class).isPresent()
                    && !b.asClassOrInterfaceDeclaration().isInterface() ).findFirst();

            if( !obd.isPresent()){
                throw new DraftException("Unable to find Local Class in anonymous Object body ");
            }
            ClassOrInterfaceDeclaration coid = (ClassOrInterfaceDeclaration)obd.get();
            //get the class
            Class clazz =
                Arrays.stream(anonymousObjectWithLocalClass.getClass().getDeclaredClasses())
                    .filter( c -> {
                    //NOTE: the name is a mess with $1$ nonsense for Anonymous Local class
                    // so convert it to a typeRef for simplicity
                    return _typeRef.of( coid.getNameAsString() ).is(c.getName());
                    }).findFirst().get();

            coid.remove(); //remove it from the old AST (the Anonymous class)

            CompilationUnit cu = new CompilationUnit(); //add it to a new CompilationUnit
            cu.addType(coid);
            _class _c = _class.of(coid); //create the instance
            _c = _macro.to(clazz, _c); //run annotation macros on the class

            for(int i=0;i<macroFunctions.length;i++){ //run supplied macros
                _c = (_class)macroFunctions[i].apply(_c);
            }

            Class[] toImport = _import.inferImportsFrom(clazz).toArray(new Class[0]);
            for(int i=0;i<toImport.length;i++){
                _c.imports(toImport[i]);
            }
            return _c;
        }
        throw new DraftException("No Class Body for Anonymous Object containing a Local class to build");
    }

    /**
     * Return the _class represented by this single line ClassDef
     * @param classDef
     * @return 
     */
    public static _class of( String classDef){
        return of( new String[]{classDef});
    }

    /**
     * if you pass a single line, with a single token (NO SPACES) into this, we create a shortcut class
     * you can specify the PACKAGE_NAME.className
     * 
     * <UL>
     * <LI>shortcut classes, i.e._class.of("C") -> creates "public class C{}"
     * <LI>shortcut classes, i.e._class.of("C<T>") -> creates "public class C<T>{}"
     * <LI>shortcut classes, i.e. _class.of("aaaa.bbbb.C") -> creates "package aaaa.bbbb;  public class C{}"
     * <LI>shortcut classes, i.e. _class.of("aaaa.bbbb.C<Obj>") -> creates "package aaaa.bbbb;  public class C<Obj>{}"
     * </UL>
     * 
     * @param classDef
     * @return 
     */ 
    public static _class of( String...classDef ){

        if( classDef.length == 1){
            String[] strs = classDef[0].split(" ");
            if( strs.length == 1 ){
                //definately shortcut classes
                String shortcutClass = strs[0];
                String packageName = null;
                int lastDotIndex = shortcutClass.lastIndexOf('.');
                if( lastDotIndex >0 ){
                    packageName = shortcutClass.substring(0, lastDotIndex );
                    shortcutClass = shortcutClass.substring(lastDotIndex + 1);
                    if(!shortcutClass.endsWith("}")){
                        shortcutClass = shortcutClass + "{}";
                    }
                    return of( Ast.of( "package "+packageName+";"+System.lineSeparator()+
                        "public class "+shortcutClass));
                }
                if(!shortcutClass.endsWith("}")){
                    shortcutClass = shortcutClass + "{}";
                }
                return of( Ast.of("public class "+shortcutClass));
            }
            else{
                if( !classDef[0].trim().endsWith("}" ) ){
                    classDef[0] = classDef[0]+"{}";
                }
            }
        }
        return of( Ast.of( classDef ));
    }

    /**
     * return the _class represented by this Ast TypeDeclaration
     * @param astTypeDecl the ast type declaration
     * @return the _class representing the TypeDeclaration
     */
    public static _class of( TypeDeclaration astTypeDecl ){
        if( astTypeDecl instanceof ClassOrInterfaceDeclaration && !astTypeDecl.asClassOrInterfaceDeclaration().isInterface() ) {
            return new _class( (ClassOrInterfaceDeclaration)astTypeDecl);
        }
        throw new DraftException("Expected AST ClassOrInterfaceDeclaration as Class, got "+ astTypeDecl.getClass() );        
    }
    
    public static _class of( CompilationUnit cu ){
        if( cu.getPrimaryTypeName().isPresent() ){
            return of( cu.getClassByName( cu.getPrimaryTypeName().get() ).get() );
        }
        NodeList<TypeDeclaration<?>> tds = cu.getTypes();
        if( tds.size() == 1 ){
            return of( (ClassOrInterfaceDeclaration)tds.get(0) );
        }
        throw new DraftException("Unable to locate primary TYPE in "+ cu);
    }

    public static _class of( ClassOrInterfaceDeclaration astClass ){
        return new _class( astClass );
    }

    /**
     * builds a class with the signature and the anonymous class body
     * applies any macro annotations to the Object Body
     * then apply the typeFunctions in order
     * for example:
     * <PRE>
     * _class.of("aaaa.bbb.C", new Object(){ @_init("100") int x,y }, @_autoGetter.$);
     * //produces
     * package aaaa.bbb;
     * 
     * public class C{
     *   int x =100, y = 100;
     * 
     *   public int getX(){
     *       return x;
     *   }
     * 
     *   public int getY(){
     *       return y;
     *   }
     * }
     * </PRE>
     * @param signature
     * @param anonymousClassBody
     * @param typeFn
     * @return
     */
    public static _class of( String signature, Object anonymousClassBody, Function<_type, _type>...typeFn){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        _class _c = _class.of(signature);
        //System.out.println("Set Package"+_c.getPackage());
        Class theClass = anonymousClassBody.getClass();

        //interfaces to implement
        if(theClass.getInterfaces().length > 0){
            for(int i=0; i< theClass.getInterfaces().length; i++){
                
                _c.imports(theClass.getInterfaces()[i]);
                _c.implement(theClass.getInterfaces()[i]);
            }
        }
        //extends to extend
        if( theClass.getSuperclass() != Object.class ){
            _c.imports(theClass.getSuperclass());
            _c.extend(theClass.getSuperclass());
        }

        ObjectCreationExpr oce = Expr.anonymousObject(ste);
        if( oce.getAnonymousClassBody().isPresent() ) {
            NodeList<BodyDeclaration<?>> bds = oce.getAnonymousClassBody().get();
            for (int i = 0; i <bds.size(); i++){
                // check if the class has (one or more) void methods named the same as 
                // the Class name (these "methods" are really "constructors"
                BodyDeclaration bd = bds.get(i);
                if( bd instanceof MethodDeclaration ){
                    MethodDeclaration md = (MethodDeclaration)bd;
                    if( md.getNameAsString().equals(_c.getName() ) && md.getType().isVoidType() ){
                    //if( _method.of(md).hasAnno(_ctor.class) ){
                        //this is REALLY a method that is a constructor
                        //_c.ast().addMember( _ctor.Macro.fromMethod(md) );
                        _c.constructor(_constructor.of(_toCtor.Macro.fromMethod(md)));
                    } else{
                        _c.ast().addMember( bd );    
                    }
                } else{
                    _c.ast().addMember( bd );
                }
            }
        }
        //add imports from methods return types parameter types
        Set<Class>toImport = _import.inferImportsFrom(anonymousClassBody);

        _c.imports(toImport.toArray(new Class[0]));
        
        //we process the ANNOTATIONS on the TYPE
        _macro.to( theClass, _c);
        for(int i=0;i<typeFn.length; i++){
            _c = (_class)typeFn[i].apply( _c);
        }
        //System.out.println("End Package"+_c.getPackage());
        return _c;
    }

    public _class( ClassOrInterfaceDeclaration astClass ){
        this.astClass = astClass;
    }
    
    @Override
    public ClassOrInterfaceDeclaration ast() {
        return this.astClass;
    }

    /**
     * <PRE>
     * i.e. 
     * _class _c = _class.of("C").implement(
     *    new Descriptive(){
     *       public String describe() throws IOException{
     *           return "a description";
     *       }
     * });
     *</PRE>
     * will update C, and import any classes on the interface that is 
     * implemented.
     * <PRE>
     * //NOTE this import is on the public Descriptive API, so it gets added
     * import java.io.IOException;
     * 
     * public class C implements Descriptive{
     *     public String describe() throws IOException {
     *         return "a description";
     *     }
     * }
     * </PRE>
     * @param anonymousImplementation
     * @return the modified Class
     */
    public _class implement( Object anonymousImplementation ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        for(int i=0;i<anonymousImplementation.getClass().getInterfaces().length;i++ ){
            implement( new Class[]{anonymousImplementation.getClass().getInterfaces()[i]} );
            imports( new Class[]{anonymousImplementation.getClass().getInterfaces()[i]});
        }
        ObjectCreationExpr oce = Expr.anonymousObject(ste);
        if( oce.getAnonymousClassBody().isPresent()){
            oce.getAnonymousClassBody().get().forEach( m->this.ast().addMember(m) );
        }
        Set<Class> ims = _import.inferImportsFrom(anonymousImplementation);
        ims.forEach( i -> imports(i) );
        return this;
    }

    /** Make sure to route this to the correct (default method)
     * @param classToImplement 
     * @return  
     */
    public _class implement ( String classToImplement ){
        return implement( new String[]{classToImplement});
    }

    @Override
    public _class removeImplements( Class toRemove ){
        this.astClass.getImplementedTypes().removeIf( im -> im.getNameAsString().equals( toRemove.getSimpleName() ) ||
                im.getNameAsString().equals(toRemove.getCanonicalName()) );
        return this;
    }

    @Override
    public _class removeExtends( Class toRemove ){
        this.astClass.getExtendedTypes().removeIf( im -> im.getNameAsString().equals( toRemove.getSimpleName() ) ||
                im.getNameAsString().equals(toRemove.getCanonicalName()) );
        return this;
    }

    @Override
    public _class removeImplements( ClassOrInterfaceType toRemove ){
        this.astClass.getImplementedTypes().remove( toRemove );
        return this;
    }

    @Override
    public _class removeExtends( ClassOrInterfaceType toRemove ){
        this.astClass.getExtendedTypes().remove( toRemove );
        return this;
    }

    /** 
     * Make sure to route this to the correct (default method)
     * @param clazz the class to implement
     * @return the modified _class
     */
    public _class implement( Class clazz ){
        return implement( new Class[]{clazz} );
    }

    /**
     * i.e. 
     * <PRE>
     * _class _c = _class.of("C").implement(
     *    new Descriptive(){
     *       public String describe(){
     *           return "a description";
     *       }
     * });
     *
     * public class C implements Descriptive{
     *     public String describe(){
     *         return "a description";
     *     }
     * }
     * </PRE>
     * @param anonymousImplementationBody anonymous Class that implements the interface and the method(s)
     *                                    required that will be "imported" in the _class
     * @return the modified Class
     */
    public _class extend( Object anonymousImplementationBody ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];

        Class sup = anonymousImplementationBody.getClass().getSuperclass();
        extend(sup);
        imports( new Class[]{sup} );
        ObjectCreationExpr oce = Expr.anonymousObject(ste);
        if( oce.getAnonymousClassBody().isPresent()){
            oce.getAnonymousClassBody().get().forEach( m->this.ast().addMember(m) );
        }
        _import.inferImportsFrom(anonymousImplementationBody).forEach( i -> imports(i) );
        return this;
    }

    /**
     * Given an abstract Object, add the members of the BODY into the
     * _class (after running any _macro ANNOTATIONS on
     * add the source content to the _class, an
     *
     * NOTE: If the Object is an interface, then implement the interface
     * If the Object is an AbstractClass then extends the abstract class
     * <PRE>
     *     _class _c = _class.of("C").field("int i;");
     *     _c.BODY( new Object(){
     *          @_remove int i; //_remove will removeIn this from _c
     *          int j;
     *          @_final public int add( ){
     *              return i + j;
     *          }
     *     });
     * //will produce the following _class:
     * public class C{
     *     int i;
     *     int j;
     *
     *     public final int add(){
     *         return i + j;
     *     }
     * }
     * </PRE>
     *
     * @param anonymousClassBody anonymous Class that defines FIELDS and METHODS that will be merged
     * into the _class
     * @return the modified _class
     */
    public _class body(Object anonymousClassBody ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject(ste);

        //create a temp _class to add these to so I can run _macro ANNOTATIONS on them        
        _class _temp = _class.of("temp");
        if( oce != null && oce.getAnonymousClassBody().isPresent() ){
            //add the anonymous class members to the temp class
            oce.getAnonymousClassBody().get().forEach(b-> _temp.ast().addMember(b));
        }
        //run the macros on the temp class (we might removeIn some stuff, etc.)
        _macro.to( anonymousClassBody.getClass(), _temp);

        //now add the finished members from temp to this _class
        _temp.ast().getMembers().forEach( m -> this.astClass.addMember(m));
        
        //create the approrpriate imports based on the signature of the 
        // added fields and methods, throws, etc.
        _import.inferImportsFrom(anonymousClassBody).forEach( i -> imports(i) );
        
        Class[] ints = anonymousClassBody.getClass().getInterfaces();
        Arrays.stream(ints).forEach( i -> implement(i) );
        
        if( anonymousClassBody.getClass().getSuperclass() != null && anonymousClassBody.getClass().getSuperclass() != Object.class ){
            this.extend( anonymousClassBody.getClass().getSuperclass() );
        }
        
        return this;
    }

    /**
     * the AST storing the state of the _class
     * the _class is simply a facade into the state astClass
     *
     * the _class facade is a "Logical View" of the _class state stored in the
     * AST and can interpret or manipulate the AST without:
     * having to deal with syntax issues
     */
    private final ClassOrInterfaceDeclaration astClass;

    @Override
    public boolean isTopLevel(){
        return ast().isTopLevelType();
    }
    
    @Override
    public CompilationUnit astCompilationUnit(){
        if( ast().isTopLevelType()){
            return (CompilationUnit)ast().getParentNode().get(); //astCompilationUnit.get();
        }
        //it might be a member class
        if( this.astClass.findCompilationUnit().isPresent()){
            return this.astClass.findCompilationUnit().get();
        }
        return null; //its an orphan
    }
    
    @Override
    public boolean hasExtends(){
        return !this.astClass.getExtendedTypes().isEmpty();
    }

    @Override
    public NodeList<ClassOrInterfaceType> listExtends(){
        return astClass.getExtendedTypes();
    }

    public ClassOrInterfaceType getExtends(){
        List<ClassOrInterfaceType> exts = astClass.getExtendedTypes();
        if( exts.isEmpty() ){
            return null;
        }
        return exts.get( 0 );
    }

    @Override
    public _class extend( ClassOrInterfaceType toExtend ){
        this.astClass.getExtendedTypes().clear();
        this.astClass.addExtendedType( toExtend );
        return this;
    }

    @Override
    public _class extend( Class toExtend ){
        return extend( (ClassOrInterfaceType) Ast.typeRef(toExtend.getCanonicalName() ) );
    }

    @Override
    public _class extend( String toExtend ){
        this.astClass.getExtendedTypes().clear();
        this.astClass.addExtendedType( toExtend );
        return this;
    }
    
    @Override
    public List<_member> listMembers(){
        List<_member> _mems = new ArrayList<>();
        forFields( f-> _mems.add( f));
        forMethods(m -> _mems.add(m));
        forConstructors(c -> _mems.add(c));
        forNests(n -> _mems.add(n));
        return _mems;
    }

    @Override
    public _class forMembers( Predicate<_member>_memberMatchFn, Consumer<_member> _memberActionFn){
        listMembers(_memberMatchFn).forEach(m -> _memberActionFn.accept(m) );
        return this;
    }

    @Override
    public List<_method> listMethods() {
        List<_method> _ms = new ArrayList<>();
        astClass.getMethods().forEach( m-> _ms.add(_method.of( m ) ) );
        return _ms;
    }

    @Override
    public List<_method> listMethods(Predicate<_method> _methodMatchFn ){
        List<_method> _ms = new ArrayList<>();
        astClass.getMethods().forEach( m-> {
            _method _m = _method.of( m);
            if( _methodMatchFn.test(_m)){
                _ms.add(_m ); 
            }
        } );
        return _ms;
    }
    
    public _method getMethod( int index ){
        return _method.of(astClass.getMethods().get( index ));
    }

    @Override
    public _class method( MethodDeclaration method ) {
        astClass.addMember( method );
        return this;
    }

    @Override
    public _class field( VariableDeclarator field ) {
        if(! field.getParentNode().isPresent()){
            throw new DraftException("cannot add Var without parent FieldDeclaration");
        }
        FieldDeclaration fd = (FieldDeclaration)field.getParentNode().get();
        //we already added it to the parent
        if( this.astClass.getFields().contains( fd ) ){
            if( !fd.containsWithin( field ) ){
                fd.addVariable( field );
            }
            return this;
        }
        this.astClass.addMember( fd );
        return this;
    }
 
    @Override
    public List<_constructor> listConstructors() {
        List<_constructor> _cs = new ArrayList<>();
        astClass.getConstructors().forEach( c-> _cs.add( _constructor.of(c) ));
        return _cs;
    }

    @Override
    public _constructor getConstructor(int index){
        return _constructor.of(astClass.getConstructors().get( index ));
    }

    @Override
    public _class constructor( ConstructorDeclaration constructor ) {
        constructor.setName(this.getName()); //alwyas set the constructor NAME to be the classes NAME
        this.astClass.addMember( constructor );
        return this;
    }

    @Override
    public _annos getAnnos() {
        return _annos.of(this.astClass );
    }

    @Override
    public boolean is( String...classDeclaration){
        try{
            _class _o = of( classDeclaration );
            return _o.equals( this );
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean is( ClassOrInterfaceDeclaration astC ){
        try{
            _class _o = of( astC );
            return _o.equals( this );
        }catch(Exception e){
            return false;
        }
    }
    
    @Override
    public boolean isAbstract(){
        return this.astClass.isAbstract();
    }

    @Override
    public boolean isFinal(){
        return this.astClass.isFinal();
    }

    @Override
    public boolean isStatic(){
        return this.astClass.isStatic();
    }
    
    @Override
    public _class setFinal(boolean toSet){
        this.astClass.setFinal(toSet);
        return this;
    }

    @Override
    public String toString(){
        if( this.astClass.isTopLevelType()){
            //try{
            //    return LexicalPreservingPrinter.print(this.astCompilationUnit());            
            //}catch(Exception e){
                ///well... I tried to keep the code formatted the same
                // ... but it failed... so just toString the old fashioned way
                return this.astCompilationUnit().toString();
            //}
        }
        //try{
        //    return LexicalPreservingPrinter.print( this.astClass );        
        //}catch(Exception e){
            return this.astClass.toString();
        //}
    }   

    @Override
    public boolean equals( Object obj ) {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        final _class other = (_class)obj;

        if( !Objects.equals( this.getPackage(), other.getPackage() ) ) {
            return false;
        }
        if( !Objects.equals( this.getJavadoc(), other.getJavadoc() ) ) {
            return false;
        }
        if( ! Expr.equivalentAnnos(this.astClass, other.astClass)){
            return false;
        }
        //if( !Ast.annotationsEqual( this.astClass, other.astClass)){
        //    return false;
        //}
        if( !Objects.equals( this.getModifiers(), other.getModifiers() ) ) {
            return false;
        }
        if( !Objects.equals( this.getTypeParameters(), other.getTypeParameters() ) ) {
            return false;
        }
        if( !Objects.equals( this.getName(), other.getName() ) ) {
            return false;
        }
        if( !Ast.typesEqual(this.getExtends(), other.getExtends()) ){
            return false;
        }
        if( !Ast.importsEqual(this.astClass, other.astClass)){
            return false;
        }
        //if( !Ast.importsEqual(this.listAstImports(), other.listAstImports())){
        //    return false;
        //}
        if( !Ast.typesEqual( this.listImplements(), other.listImplements())){
            return false;
        }
        Set<_staticBlock> tsb = new HashSet<>();
        Set<_staticBlock> osb = new HashSet<>();
        tsb.addAll(listStaticBlocks());
        osb.addAll(other.listStaticBlocks());
        if( !Objects.equals( tsb, osb)){
            return false;
        }
        Set<_field> tf = new HashSet<>();
        Set<_field> of = new HashSet<>();
        tf.addAll( this.listFields());
        of.addAll( other.listFields());

        if( !Objects.equals( tf, of ) ) {
            return false;
        }

        Set<_method> tm = new HashSet<>();
        Set<_method> om = new HashSet<>();
        tm.addAll( this.listMethods());
        om.addAll( other.listMethods());

        if( !Objects.equals( tm, om ) ) {
            return false;
        }

        Set<_constructor> tc = new HashSet<>();
        Set<_constructor> oc = new HashSet<>();
        tc.addAll( this.listConstructors());
        oc.addAll( other.listConstructors());

        if( !Objects.equals( tc, oc ) ) {
            return false;
        }

        Set<_type> tn = new HashSet<>();
        Set<_type> on = new HashSet<>();
        tn.addAll( this.listNests());
        on.addAll( other.listNests());

        if( !Objects.equals( tn, on ) ) {
            return false;
        }
        return true;
    }

    @Override
    public Map<_java.Component, Object> componentsMap( ) {
        Map<_java.Component, Object> parts = new HashMap<>();
        parts.put( _java.Component.HEADER_COMMENT, this.getHeaderComment() );
        parts.put( _java.Component.PACKAGE_NAME, this.getPackage() );
        parts.put( _java.Component.IMPORTS, this.getImports().list() );
        parts.put( _java.Component.ANNOS, this.listAnnos() );
        parts.put( _java.Component.EXTENDS, this.astClass.getExtendedTypes() );        
        parts.put( _java.Component.IMPLEMENTS, this.listImplements() );
        parts.put( _java.Component.JAVADOC, this.getJavadoc() );
        parts.put( _java.Component.TYPE_PARAMETERS, this.getTypeParameters() );
        parts.put( _java.Component.STATIC_BLOCKS, this.listStaticBlocks());
        parts.put( _java.Component.NAME, this.getName() );
        parts.put( _java.Component.MODIFIERS, this.getModifiers() );
        parts.put( _java.Component.CONSTRUCTORS, this.listConstructors() );
        parts.put( _java.Component.METHODS, this.listMethods() );
        parts.put( _java.Component.FIELDS, this.listFields() );
        parts.put( _java.Component.NESTS, this.listNests() );
        return parts;
    }

    @Override
    public int hashCode(){

        int hash = 3;

        Set<_field> tf = new HashSet<>();
        tf.addAll( this.listFields());
        Set<_method> tm = new HashSet<>();
        tm.addAll( this.listMethods());
        Set<_constructor> tc = new HashSet<>();
        tc.addAll( this.listConstructors());
        Set<_type> tn = new HashSet<>();
        tn.addAll( this.listNests());

        Set<_staticBlock> sbs = new HashSet<>();
        sbs.addAll( this.listStaticBlocks());

        hash = 47 * hash + Objects.hash( this.getPackage(), this.getName(),
                this.getJavadoc(), this.getAnnos(), this.getModifiers(),
                this.getTypeParameters(), Ast.typeHash(this.getExtends()),
                sbs, Ast.typesHashCode( ast().getImplementedTypes() ),
                Expr.hashAnnos(astClass),
                //Ast.importsHash(astClass),
                tf, tm, tc, tn);

        return hash;
    }    
}
