package draft.java;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.TypeParameter;

import draft.java._typeParameter._typeParameters;
import draft.java._anno.*;
import draft.DraftException;
import draft.Text;
import draft.java._inspect._diffTree;
import draft.java._inspect._path;
import draft.java.io._in;
import draft.java.macro._macro;
import draft.java.macro._remove;

import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

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
        _method._hasMethods<_class>, _constructor._hasConstructors<_class>,
        _typeParameter._hasTypeParameters<_class>, _staticBlock._hasStaticBlock<_class>,
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
            for(int i=0; i< typeFns.length; i++){
                _c = (_class)typeFns[i].apply(_c);
            }
            //Arrays.stream(typeFns).forEach( t$ -> t$.apply( _c) );
            return _c;
        } else if( n instanceof LocalClassDeclarationStmt){
            //System.out.println( "LOCAL CLASS "+ n );
            LocalClassDeclarationStmt loc = (LocalClassDeclarationStmt)n;
            _class _c = of(  ((LocalClassDeclarationStmt)n).getClassDeclaration());
            if( loc.getComment().isPresent() ){
                _c.astType().setComment( loc.getComment().get());
            }
            _c = _macro.to(clazz, _c);
            for(int i=0; i< typeFns.length; i++){
                _c = (_class)typeFns[i].apply(_c);
            }
            //Arrays.stream(typeFns).forEach( t$ -> t$.apply( _c) );
            return _c;
        }
        throw new DraftException("Abstract or synthetic classes are not supported"+ clazz);
    }

    public static _class of( _in in ){
        return of( in.getInputStream() );
    }

    public static _class of( InputStream is ){
        return(_class)_type.of(is);
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
                                //System.out.println( "TRYING "+ c.getName()+" FOR "+ coid.getNameAsString() );
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

            Class[] toImport = _type.inferImportsFrom(clazz).toArray(new Class[0]);
            for(int i=0;i<toImport.length;i++){
                _c.imports(toImport[i]);
            }
            return _c;
        }
        throw new DraftException("No Class Body for Anonymous Object containing a Local class to build");
    }

    public static _class of( String classDef){
        return of( new String[]{classDef});
    }

    //if you pass a single line, with a single token (NO SPACES) into this, we create a shortcut class
    // you can specify the PACKAGE_NAME.className
    //shortcut classes, i.e. _class.of("C") -> creates "public class C{}"
    //shortcut classes, i.e. _class.of("C<T>") -> creates "public class C<T>{}"
    //shortcut classes, i.e. _class.of("aaaa.bbbb.C") -> creates "package aaaa.bbbb;  public class C{}"
    //shortcut classes, i.e. _class.of("aaaa.bbbb.C<Obj>") -> creates "package aaaa.bbbb;  public class C<Obj>{}"
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
                    return of( Ast.compilationUnit( "package "+packageName+";"+System.lineSeparator()+
                            "public class "+shortcutClass));
                }
                if(!shortcutClass.endsWith("}")){
                    shortcutClass = shortcutClass + "{}";
                }
                return of( Ast.compilationUnit("public class "+shortcutClass));
            }
            else{
                //System.out.println( "SINGLE LINE" );
                if( !classDef[0].trim().endsWith("}" ) ){
                    //System.out.println( "ADDING" );
                    classDef[0] = classDef[0]+"{}";
                }
                //System.out.println( "SINGLE LINE"+classDef[0] );

            }
        }
        return of( Ast.compilationUnit( classDef ));
    }

    public static _class of( TypeDeclaration td ){
        if( td instanceof ClassOrInterfaceDeclaration && !td.asClassOrInterfaceDeclaration().isInterface() ) {
            System.out.println( "GOt HEre in TypeDecl");
            return new _class( (ClassOrInterfaceDeclaration)td);
        }
        throw new DraftException("Expected AST ClassOrInterfaceDeclaration as Class, got "+ td.getClass() );        
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
        //System.out.println( "ANNOTATIONS" + abstractMethodBody.getClass().getAnnotations().length );
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        _class _c = _class.of(signature);
        Class theClass = anonymousClassBody.getClass();

        //interfaces
        if(theClass.getInterfaces().length > 0){
            for(int i=0; i< theClass.getInterfaces().length; i++){
            //Arrays.stream(theClass.getInterfaces()).forEach( i -> {
                _c.imports(theClass.getInterfaces()[i]);
                _c.implement(theClass.getInterfaces()[i]);
            }
        }
        //extends
        if( theClass.getSuperclass() != Object.class ){
            _c.imports(theClass.getSuperclass());
            _c.extend(theClass.getSuperclass());
        }

        ObjectCreationExpr oce = Expr.anonymousObject(ste);
        if( oce.getAnonymousClassBody().isPresent() ) {
            NodeList<BodyDeclaration<?>> bds = oce.getAnonymousClassBody().get();
            for (int i = 0; i <bds.size(); i++){
                _c.astType().addMember( bds.get(i));
            }
        }
        //add imports from methods return types parametder types
        Set<Class>toImport = _type.inferImportsFrom(anonymousClassBody);
        //System.out.println( toImport );

        _c.imports(toImport.toArray(new Class[0]));
        //we process the ANNOTATIONS on the TYPE
        _macro.to( theClass, _c);
        for(int i=0;i<typeFn.length; i++){
            _c = (_class)typeFn[i].apply( _c);
        }
        return _c;
    }

    public _class( ClassOrInterfaceDeclaration astClass ){
        this.astClass = astClass;
    }

    /**
     * i.e. _class _c = _class.of("C").implement(
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
            oce.getAnonymousClassBody().get().forEach( m->this.astType().addMember(m) );
        }
        return this;
    }

    /** Make sure to route this to the correct (default method) */
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

    /** Make sure to route this to the correct (default method) */
    public _class implement( Class clazz ){
        return implement( new Class[]{clazz} );
    }

    /**
     * i.e. _class _c = _class.of("C").implement(
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
            oce.getAnonymousClassBody().get().forEach( m->this.astType().addMember(m) );
        }
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
            oce.getAnonymousClassBody().get().forEach(b-> _temp.astType().addMember(b));
        }
        //run the macros on the temp class (we might removeIn some stuff, etc.)
        _macro.to( anonymousClassBody.getClass(), _temp);

        //now add the finished members from temp to this _class
        _temp.astType().getMembers().forEach( m -> this.astClass.addMember(m));
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
    public boolean isTopClass(){
        return astType().isTopLevelType();
    }

    @Override
    public ClassOrInterfaceDeclaration astType(){
        return astClass;
    }

    @Override
    public CompilationUnit findCompilationUnit(){
        if( astType().isTopLevelType()){
            return (CompilationUnit)astType().getParentNode().get(); //astCompilationUnit.get();
        }
        //it might be a member class
        if( this.astClass.findCompilationUnit().isPresent()){
            return this.astClass.findCompilationUnit().get();
        }
        return null; //its an orphan
    }

    @Override
    public boolean hasTypeParameters(){
        return this.astClass.getTypeParameters().isNonEmpty();
    }

    @Override
    public _class typeParameters( String typeParameters ){
        this.astClass.setTypeParameters( Ast.typeParameters( typeParameters ) );
        return this;
    }

    @Override
    public _class typeParameters( NodeList<TypeParameter> typeParameters ){
        this.astClass.setTypeParameters( typeParameters  );
        return this;
    }

    @Override
    public _typeParameters getTypeParameters(){
        return _typeParameters.of(astClass);
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
        this.astClass.getExtendedTypes().clear();
        this.astClass.addExtendedType( toExtend );
        return this;
    }

    @Override
    public _class extend( String toExtend ){
        this.astClass.getExtendedTypes().clear();
        this.astClass.addExtendedType( toExtend );
        return this;
    }

    @Override
    public boolean hasImplements(){
        return !this.astClass.getImplementedTypes().isEmpty();
    }

    @Override
    public List<ClassOrInterfaceType> listImplements(){
        return astClass.getImplementedTypes();
    }

    @Override
    public _class implement( ClassOrInterfaceType... toImplement ){
        Arrays.stream( toImplement ).forEach( i -> this.astClass.addImplementedType( i ) );
        return this;
    }

    @Override
    public _class implement( Class... toImplement ){
        Arrays.stream( toImplement ).forEach( i -> {
            this.astClass.addImplementedType( i );
            this.astClass.tryAddImportToParentCompilationUnit(i);
        } );

        return this;
    }

    @Override
    public _class implement( String... toImplement ){
        Arrays.stream( toImplement ).forEach( i -> this.astClass.addImplementedType( i ) );
        return this;
    }

    @Override
    public _staticBlock getStaticBlock(int index ){
        NodeList<BodyDeclaration<?>> mems = this.astClass.getMembers();
        for( BodyDeclaration mem : mems ){
            if( mem instanceof InitializerDeclaration){
                if( index == 0 ){
                    return new _staticBlock( (InitializerDeclaration)mem);
                }
                index --;
            }
        }
        return null;
    }

    @Override
    public List<_staticBlock> listStaticBlocks(){
        List<_staticBlock> sbs = new ArrayList<>();
        NodeList<BodyDeclaration<?>> mems = this.astClass.getMembers();
        for( BodyDeclaration mem : mems ){
            if( mem instanceof InitializerDeclaration){
                sbs.add( new _staticBlock( (InitializerDeclaration)mem));
            }
        }
        return sbs;
    }
    @Override
    public _class removeStaticBlock( _staticBlock _sb ){
        this.astClass.remove(_sb.ast());
        return this;
    }

    @Override
    public _class removeStaticBlock( InitializerDeclaration astSb ){
        this.astClass.remove(astSb);
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
    public _class removeFields( Predicate<_field> _fieldMatchFn){
        List<_field> fs = listFields(_fieldMatchFn);
        fs.forEach(f -> removeField(f));
        return this;
    }

    @Override
    public _class removeField( _field _f ){
        if( listFields().contains(_f) ){
            if( _f.getFieldDeclaration().getVariables().size() == 1){
                _f.getFieldDeclaration().remove();
            } else{
                _f.getFieldDeclaration().getVariables().removeIf( v-> v.getNameAsString().equals(_f.getName() ));
            }
        }
        return this;
    }

    @Override
    public _class removeField( String fieldName ){
        Optional<FieldDeclaration> ofd = this.astClass.getFieldByName(fieldName );
        if( ofd.isPresent() ){
            FieldDeclaration fd = ofd.get();
            if( fd.getVariables().size() == 1 ){
                fd.remove();
            } else{
                fd.getVariables().removeIf( v-> v.getNameAsString().equals(fieldName));
            }
        }
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
    public _class removeConstructor( ConstructorDeclaration cd ){
        this.astClass.remove( cd );
        return this;
    }

    @Override
    public _class removeConstructor( _constructor _ct){
        this.astClass.remove(_ct.ast() );
        return this;
    }

    @Override
    public _class removeConstructors( Predicate<_constructor> ctorMatchFn){
        listConstructors(ctorMatchFn).forEach(c -> removeConstructor(c));
        return this;
    }

    @Override
    public _annos getAnnos() {
        return _annos.of(this.astClass );
    }

    @Override
    public boolean isImplements( String str ){
        try{
            return isImplements( (ClassOrInterfaceType)Ast.typeRef( str ) );
        }catch( Exception e){}
        return false;
    }

    public boolean is( String...classDeclaration){
        try{
            _class _o = of( classDeclaration );
            return _o.equals( this );
        }catch(Exception e){
            return false;
        }
    }

    public boolean is( ClassOrInterfaceDeclaration astC ){
        try{
            _class _o = of( astC );
            return _o.equals( this );
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public boolean isImplements( ClassOrInterfaceType ct ){
        return this.astClass.getImplementedTypes().contains( ct );
    }

    @Override
    public boolean isImplements( Class clazz ){
        try{
            return isImplements( (ClassOrInterfaceType)Ast.typeRef( clazz ) ) ||
                    isImplements( (ClassOrInterfaceType)Ast.typeRef( clazz.getSimpleName() ) );
        }catch( Exception e){ }
        return false;
    }

    @Override
    public boolean isExtends( ClassOrInterfaceType ct ){
        return this.astClass.getExtendedTypes().contains( ct );
    }

    @Override
    public boolean isExtends( String str ){
        try{

            return isExtends( (ClassOrInterfaceType)Ast.typeRef( str ) );
        }catch( Exception e){}
        return false;
    }

    @Override
    public boolean isExtends( Class clazz ){
        try{
            return isExtends( (ClassOrInterfaceType)Ast.typeRef( clazz ) ) ||
                    isExtends( (ClassOrInterfaceType)Ast.typeRef( clazz.getSimpleName() ) );
        }catch( Exception e){}
        return false;
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
    public _class setStatic(){
        return setStatic(true);
    }

    @Override
    public _class setAbstract(){
        return setAbstract(true);
    }

    @Override
    public _class setFinal(){
        return setFinal(true);
    }

    @Override
    public _class setStatic(boolean toSet){
        this.astClass.setStatic(toSet);
        return this;
    }

    @Override
    public _class setAbstract(boolean toSet){
        this.astClass.setAbstract(toSet);
        return this;
    }

    @Override
    public _class setFinal(boolean toSet){
        this.astClass.setFinal(toSet);
        return this;
    }

    @Override
    public String toString(){
        //if( this.astCompilationUnit.isPresent()){
        //    return this.astCompilationUnit.get().toString();
        //}
        if( this.astClass.isTopLevelType()){
            //System.out.println( getFullName()+">>>>>>>> IS TOP LEVEL TYPE" );
            return this.findCompilationUnit().toString();
        }
        return this.astClass.toString();
        //System.out.println( getFullName()+"!!!!!!!!! >>>>>>>> IS NOT TOP LEVEL TYPE" );
        //if( this.astClass.getParentNode().isPresent() ){
            //System.out.println( getFullName()+" DOES have a parent node" );
        //    Node parent = this.astClass.getParentNode().get();
        //    System.out.println( getFullName()+" DOES have a parent node of "+parent.getClass() );
        //}
    }

    @Override
    public _class typeParameters( String... typeParameters ) {
        this.astClass.setTypeParameters( Ast.typeParameters( Text.combine( typeParameters) ));
        return this;
    }

    @Override
    public _class typeParameters( _typeParameters _tps ) {
        this.astClass.setTypeParameters( _tps.ast() );
        return this;
    }

    @Override
    public _class removeTypeParameters() {
        this.astClass.getTypeParameters().clear();
        return this;
    }

    public _class removeTypeParameter( TypeParameter tp ){
        this.astClass.getTypeParameters().remove(tp);
        return this;
    }

    /**
     * Apply customization macros to this _class and return the result
     * @param customMacros macros to customize the _class
     * @return the modified _class
     */
    public _class customize( _macro<_class>...customMacros ){
        for(int i=0;i<customMacros.length;i++){
            customMacros[i].apply(this);
        }
        return this;
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
        if( !Ast.annotationsEqual( this.astClass, other.astClass)){
            return false;
        }
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
        if( !Ast.importsEqual(this.listImports(), other.listImports())){
            return false;
        }
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
        parts.put( _java.Component.PACKAGE_NAME, this.getPackage() );
        parts.put( _java.Component.IMPORTS, this.listImports() );
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
                sbs, Ast.typesHashCode( astType().getImplementedTypes() ),
                Ast.importsHash(astClass),
                tf, tm, tc, tn);

        return hash;
    }
    
    /**
     * statically diff the contents of the two _class objects
     * @param left
     * @param right
     * @return the _diffTree showing the diffs at the levels of the _class
     */
    public static _diffTree diffTree(_class left, _class right){        
        _path path = new _path();
        _diffTree dt = new _diffTree();
        return _inspect.INSPECT_CLASS.diffTree(path, dt, left, right);
    }
}
