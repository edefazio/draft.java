package draft.java;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.ReferenceType;

import draft.Text;
import draft.java._model.*;
import draft.java._anno.*;
import draft.java._parameter.*;
import draft.java._typeParameter.*;
import draft.java.macro._remove;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Model of a java constructor
 * 
 * @author Eric
 */
public final class _constructor implements _anno._hasAnnos<_constructor>, _javadoc._hasJavadoc<_constructor>,
        _throws._hasThrows<_constructor>, _body._hasBody<_constructor>, _modifiers._hasModifiers<_constructor>,
        _parameter._hasParameters<_constructor>,_receiverParameter._hasReceiverParameter<_constructor>,
        _member<ConstructorDeclaration, _constructor> { //_referenceable<_constructor>, _node<ConstructorDeclaration>,


    public static _constructor of( String signature ){
        return of( new String[]{signature});
    }

    /**
     * Build a constructor from a method on an anonymous object BODY
     *
     * @param anonymousObjectBody
     * @return
     */
    public static _constructor of(Object anonymousObjectBody ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject( ste );
        MethodDeclaration theMethod = (MethodDeclaration)
                oce.getAnonymousClassBody().get().stream().filter(m -> m instanceof MethodDeclaration &&
                        !m.isAnnotationPresent(_remove.class) ).findFirst().get();
        //build the base method first
        _constructor _ct = _constructor.of( theMethod.getNameAsString() + " " +_parameters.of( theMethod )+"{}" );
        //MODIFIERS
        if( theMethod.isPublic() ){
            _ct.setPublic();
        }
        if(theMethod.isProtected()){
            _ct.setProtected();
        }
        if(theMethod.isPrivate()){
            _ct.setPrivate();
        }
        _ct.setBody( theMethod.getBody().get() ); //BODY
        return _ct;
    }

    public static _constructor of( String... ctorDecl ) {
        //I need to do shortcut CONSTRUCTORS
        if( ctorDecl.length == 1 ){
            String[] toks = ctorDecl[0].split(" ");
            //C
            //C()
            //C(){}
            if( toks.length == 1){
                String token = toks[0];
                if( token.endsWith("}")){
                    return new _constructor( Ast.ctor(toks[0]));
                }
                if( token.endsWith(")")){
                    return new _constructor( Ast.ctor(toks[0]+"{}"));
                }
                return new _constructor( Ast.ctor("public "+toks[0]+"(){}"));
            }
        }
        return new _constructor( Ast.ctor( ctorDecl ) );
    }

    public static _constructor of( ConstructorDeclaration ctorDecl ) {
        return new _constructor( ctorDecl );
    }

    private final ConstructorDeclaration astCtor;

    public _constructor( ConstructorDeclaration md ) {
        this.astCtor = md;
    }

    public _constructor copy(){
        return new _constructor( this.astCtor.clone());
    }

    @Override
    public NodeList<Modifier> getEffectiveModifiers() {
        NodeList<Modifier> em = Ast.getImpliedModifiers( this.astCtor );
        if( em == null ){
            return this.astCtor.getModifiers();
        }
        return Ast.merge( em, this.astCtor.getModifiers());
        //em.addAll(this.astCtor.getModifiers());
        //return em;
    }

    @Override
    public ConstructorDeclaration ast() {
        return astCtor;
    }

    @Override
    public _constructor javadoc( String... javadoc ) {
        astCtor.setJavadocComment( Text.combine( javadoc ) );
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
        final _constructor other = (_constructor)obj;
        if( this.astCtor == other.astCtor ) {
            return true; //two _constructor instances pointing to same ConstructorDeclaration instance
        }
        //this.getAnnos(), this.getBody(), this.getJavadoc(), this.getModifiers(), this.getName(), this.getParameters(), this.getThrownExeptions(), this.getTypeParameters(), this.getType()
        if( !Ast.annotationsEqual( this.astCtor, other.astCtor)) {
            return false;
        }
        //if( !Objects.equals( this.getAnnos(), other.getAnnos() ) ) {
            //System.out.println("annos");
        //    return false;
        //}
        if( !Objects.equals( this.getBody(), other.getBody() ) ) {
            //System.out.println("BODY");
            return false;
        }
        if( this.hasJavadoc() != other.hasJavadoc() ) {
            return false;
        }
        if( this.hasJavadoc() && !Objects.equals( this.getJavadoc().getContent().trim(), other.getJavadoc().getContent().trim() ) ) {
            //System.out.println("JAVADOC >" + this.getJavadoc().getContent() +"< >"+other.getJavadoc().getContent());
            return false;
        }
        if( !Ast.modifiersEqual(this.astCtor, other.astCtor) ){
            return false;
        }
        //if( !Objects.equals( this.getModifiers(), other.getModifiers() ) ) {
            //System.out.println("MODIFIERS");
        //    return false;
       // }
        if( !Objects.equals( this.getName(), other.getName() ) ) {
            //System.out.println("NAME");
            return false;
        }
        if( !Objects.equals( this.getParameters(), other.getParameters() ) ) {
            //System.out.println("PARAMETERS");
            return false;
        }
        if( !Ast.typesEqual( this.astCtor.getThrownExceptions(), other.astCtor.getThrownExceptions()) ){
            return false;
        }
        //if( !Objects.equals( this.getThrows(), other.getThrows() ) ) {
            //System.out.println("thrownExpcet");
        //    return false;
        //}
        if( !Objects.equals( this.getTypeParameters(), other.getTypeParameters() ) ) {
            //System.out.println("TYPE params");
            return false;
        }
        if( !Objects.equals( this.getReceiverParameter(), other.getReceiverParameter() ) ) {
            //System.out.println("receiver params");
            return false;
        }
        return true;
    }

    @Override
    public Map<_java.Component, Object> componentsMap() {
        Map<_java.Component, Object> parts = new HashMap<>();
        parts.put(_java.Component.ANNOS, getAnnos() );
        parts.put( _java.Component.BODY, getBody() );
        parts.put( _java.Component.MODIFIERS, getModifiers() );
        parts.put( _java.Component.JAVADOC, getJavadoc() );
        parts.put( _java.Component.PARAMETERS, getParameters() );
        parts.put( _java.Component.RECEIVER_PARAMETER, getReceiverParameter() );
        parts.put( _java.Component.TYPE_PARAMETERS, getTypeParameters() );
        parts.put( _java.Component.THROWS, getThrows() );
        parts.put( _java.Component.NAME, getName() );
        return parts;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 79 * hash + Objects.hash(
                Ast.annotationsHash( astCtor ), this.getBody(), this.getJavadoc(),
                this.getEffectiveModifiers(),
                this.getName(), this.getParameters(),
                Ast.typesHashCode( astCtor.getThrownExceptions()  ), this.getTypeParameters(), this.getReceiverParameter() );
        return hash;
    }

    @Override
    public boolean hasJavadoc() {
        return this.astCtor.getJavadocComment().isPresent();
    }

    @Override
    public _constructor removeJavadoc() {
        this.astCtor.removeJavaDocComment();
        return this;
    }

    @Override
    public _javadoc getJavadoc() {
        return _javadoc.of( this.astCtor );
    }

    @Override
    public _constructor name( String name ) {
        this.astCtor.setName( name );
        return this;
    }

    @Override
    public _constructor setBody( String... body ) {
        this.astCtor.setBody( Stmt.block( body ) );
        return this;
    }

    @Override
    public _throws getThrows() {
        return _throws.of( astCtor );
    }

    public _constructor setTypeParameters( _typeParameters _tps ){
        this.astCtor.setTypeParameters( _tps.ast() );
        return this;
    }

    public _constructor setTypeParameters( String typeParameters ) {
        this.astCtor.setTypeParameters( Ast.typeParameters( typeParameters ) );
        return this;
    }

    public _typeParameters getTypeParameters() {
        return _typeParameters.of( this.astCtor );
    }

    public boolean hasTypeParameters() {
        return this.astCtor.getTypeParameters().isNonEmpty();
    }

    public boolean hasParametersOf( java.lang.reflect.Constructor ctor ){
        
        java.lang.reflect.Type[] genericParameterTypes = ctor.getGenericParameterTypes();
        List<_parameter> pl = this.listParameters();
        int delta = 0;
        if( genericParameterTypes.length != pl.size() ){
            //System.out.println( "DIFFERENT NUMBER OF CTOR ARGS ");
            if( genericParameterTypes.length == pl.size() + 1 && ctor.getDeclaringClass().isLocalClass()){
                //System.out.println( "THE FIRST ARG IS "+ genericParameterTypes[0] );
                //System.out.println( "THE DECLARING CLASS DEFINING "+ctor.getDeclaringClass() );
                if( ctor.getDeclaringClass().isLocalClass() ){
                    //System.out.println("Declaring Class is a Local Class");
                    delta = 1;
                }
                //System.out.println( "THE D DECLARING CLASS DEFINING "+ctor.getDeclaringClass().getDeclaringClass() );
            } else{
                return false;
            }            
        }
        for(int i=0;i<pl.size(); i++){
            _typeRef _t = _typeRef.of(genericParameterTypes[i+delta]);
            //System.out.println( "CHECKING "+ _t);
            if( !pl.get(i).isType( _t ) ){ 
                if( ctor.isVarArgs() &&  //if last parameter and varargs
                        Ast.typesEqual( pl.get(i).getType().getElementType(), 
                                _t.getElementType())  ){
                    
                } else{
                    System.out.println( "Failed at "+ _t+" =/= "+ pl.get(i).getType() );                
                    return false;
                }
            }
        }
        /*
        for(int i=0;i<genericParameterTypes.length; i++){
            _typeRef _t = _typeRef.of(genericParameterTypes[i]);
            if( !pl.get(i).isType( _t ) ){ 
                if( ctor.isVarArgs() &&  //if last parameter and varargs
                        Ast.typesEqual( pl.get(i).getType().getElementType(), 
                                _t.getElementType())  ){
                    
                } else{
                    System.out.println( "Failed at "+ _t+" =/= "+ pl.get(i).getType() );                
                    return false;
                }
            }
        }
        */
        return true;        
    }
    
    //NOTE: THIS FAILS FOR VARARGS, I SHOULD GET RID OF THE API ENTIRELY
    @Override
    public boolean hasParametersOfType(java.lang.reflect.Type...genericParameterTypes){
        if( genericParameterTypes.length != this.listParameters().size() ){
            
            //System.out.println( "Failed NOT SAME SIZE" );
            
            return false;
        }
        List<_parameter> pl = this.listParameters();
        for(int i=0;i<genericParameterTypes.length; i++){
            _typeRef _t = _typeRef.of(genericParameterTypes[i]);
            if( !pl.get(i).isType( _t ) ){
                
                //System.out.println( "Failed at "+ _t+" =/= "+ pl.get(i).getType() );
                
                return false;
            }
        }
        return true;
    }
    
    /*
    @Override
    public boolean hasParametersOfType(Class<?>... paramTypes) {
        return this.ast().hasParametersOfType(paramTypes);
    }
    */

    @Override
    public boolean hasParameters() {
        return this.astCtor.getParameters().isNonEmpty();
    }

    @Override
    public _constructor addThrows( String... throwExceptions ) {
        Arrays.stream( throwExceptions ).forEach( t -> addThrows( t ) );
        return this;
    }

    @Override
    public _constructor addThrows( String throwException ) {
        this.astCtor.addThrownException( (ReferenceType)Ast.typeRef( throwException ) );
        return this;
    }

    @Override
    public _constructor addThrows( Class<? extends Throwable>... throwExceptions ) {
        Arrays.stream( throwExceptions ).forEach( t -> addThrows( t ) );
        return this;
    }

    @Override
    public _constructor addThrows( Class<? extends Throwable> throwException ) {
        this.astCtor.addThrownException( (ReferenceType)Ast.typeRef( throwException ) );
        return this;
    }

    @Override
    public _constructor addParameters( Parameter... parameters ) {
        Arrays.stream( parameters ).forEach( p -> addParameter( p ) );
        return this;
    }

    @Override
    public _constructor addParameter( Parameter parameter ) {
        this.astCtor.addParameter( parameter );
        return this;
    }

    public boolean is( String...constructorDeclaration ){
        try {
            _constructor _ct = of(constructorDeclaration);
            return equals(_ct);
        }
        catch(Exception e){
            return false;
        }
    }

    public boolean is( ConstructorDeclaration astCd ){
        _constructor _ct = of( astCd );
        return equals( _ct );
    }

    @Override
    public _body getBody() {
        return _body.of( this.astCtor );
    }

    @Override
    public boolean hasBody() {
        return true;
    }

    @Override
    public boolean hasThrows() {
        return this.astCtor.getThrownExceptions().isNonEmpty();
    }

    @Override
    public _modifiers getModifiers() {
        return _modifiers.of( astCtor );
    }

    @Override
    public _annos getAnnos() {
        return _annos.of( astCtor );
    }

    @Override
    public String getName() {
        return astCtor.getNameAsString();
    }

    @Override
    public _parameter getParameter( int index ) {
        return _parameter.of( astCtor.getParameter( index ) );
    }

    @Override
    public boolean isVarArg() {
        if( this.astCtor.getParameters().isNonEmpty() ) {
            return this.astCtor.getParameter( this.astCtor.getParameters().size() - 1 ).isVarArgs();
        }
        return false;
    }

    @Override
    public _parameters getParameters() {
        return _parameters.of( astCtor );
    }

    @Override
    public String toString() {
        return this.astCtor.toString();
    }

    public boolean isPublic() {
        return this.astCtor.isPublic();
    }

    public boolean isProtected() {
        return this.astCtor.isProtected();
    }

    public boolean isPrivate() {
        return this.astCtor.isPrivate();
    }

    public boolean isStrictFp() {
        return this.astCtor.isStrictfp();
    }

    public boolean isFinal() {
        return this.astCtor.isFinal();
    }

    public _constructor setPublic() {
        this.astCtor.setPrivate(false);
        this.astCtor.setProtected(false);
        this.astCtor.setPublic(true);
        return this;
    }

    public _constructor setProtected() {
        this.astCtor.setPrivate(false);
        this.astCtor.setProtected(true);
        this.astCtor.setPublic(false);
        return this;
    }

    public _constructor setPrivate() {
        this.astCtor.setPrivate(true);
        this.astCtor.setProtected(false);
        this.astCtor.setPublic(false);
        return this;
    }

    public _constructor setDefaultAccess() {
        this.astCtor.setPrivate(false);
        this.astCtor.setProtected(false);
        this.astCtor.setPublic(false);
        return this;
    }

    @Override
    public boolean isThrown( Class<? extends Throwable> thrownClass ) {
        return this.astCtor.isThrown( thrownClass );
    }

    @Override
    public boolean isThrown( ReferenceType refType ) {
        return this.astCtor.getThrownExceptions().contains( refType );
    }

    @Override
    public boolean isThrown( String typeName ) {
        return this.astCtor.isThrown( typeName );
    }

    @Override
    public _constructor setBody( BlockStmt body ) {
        this.astCtor.setBody( body );
        return this;
    }

    @Override
    public _constructor clearBody() {
        this.astCtor.replace( this.astCtor.getBody(), new BlockStmt() );
        return this;
    }

    @Override
    public _constructor add( Statement... statements ) {
        Arrays.stream( statements ).forEach( s -> this.astCtor.getBody().addStatement( s ) );
        return this;
    }

    @Override
    public _constructor add( int startStatementIndex, Statement...statements ){

        for( int i=0;i<statements.length;i++) {
            this.astCtor.getBody().addStatement( i+ startStatementIndex, statements[i] );
        }
        return this;
    }

    @Override
    public boolean hasReceiverParameter() {
        return this.astCtor.getReceiverParameter().isPresent();
    }

    @Override
    public _receiverParameter getReceiverParameter() {
        if( this.astCtor.getReceiverParameter().isPresent() ) {
            return _receiverParameter.of( this.astCtor.getReceiverParameter().get() );
        }
        return null;
    }

    @Override
    public _constructor removeReceiverParameter() {
        this.astCtor.removeReceiverParameter();
        return this;
    }

    @Override
    public _constructor setReceiverParameter( String receiverParameter ) {
        return setReceiverParameter( Ast.receiverParameter( receiverParameter ) );
    }

    @Override
    public _constructor setReceiverParameter( _receiverParameter _rp ) {
        return setReceiverParameter( _rp.ast() );
    }

    @Override
    public _constructor setReceiverParameter( ReceiverParameter rp ) {
        this.astCtor.setReceiverParameter( rp );
        return this;
    }

    /**
     * Verify that one list of _constructor is equivalent to another list of _constructor
     */
    public static _java.Semantic<Collection<_constructor>> EQIVALENT_CTORS_LIST = 
            (Collection<_constructor> o1, Collection<_constructor> o2) -> {
        if( o1 == null ){
            return o2 == null;
        }
        if( o1.size() != o2.size()){
            return false;
        }
        Set<_constructor> tm = new HashSet<>();
        Set<_constructor> om = new HashSet<>();
        tm.addAll(o1);
        om.addAll(o2);
        return Objects.equals(tm, om);
    };
    
    public static boolean equivalent( Collection<_constructor> left, Collection<_constructor> right){
        return EQIVALENT_CTORS_LIST.equivalent(left, right);
    }
    
    /**
     *
     * @author Eric
     * @param <T>
     */
    public interface _hasConstructors<T extends _hasConstructors & _type>
            extends _model {

        List<_constructor> listConstructors();

        _constructor getConstructor( int index );

        default boolean hasConstructors() {
            return listConstructors().size() > 0;
        }

        default List<_constructor> listConstructors(
                Predicate<_constructor> _ctorMatchFn ) {
            return listConstructors().stream().filter( _ctorMatchFn ).collect( Collectors.toList() );
        }

        default T forConstructors(Consumer<_constructor> constructorConsumer ) {
            return forConstructors( m -> true, constructorConsumer );
        }

        default T forConstructors(
                Predicate<_constructor> constructorMatchFn,
                Consumer<_constructor> constructorConsumer ) {
            listConstructors( constructorMatchFn ).forEach( constructorConsumer );
            return (T)this;
        }

        T removeConstructor( ConstructorDeclaration cd );

        T removeConstructor( _constructor _ct);

        T removeConstructors( Predicate<_constructor> ctorMatchFn);

        /**
         * Build and add a constructor based on the contents of the anonymous Object passed in
         *
         * @param anonymousObjectContainingConstructor
         * @return
         */
        default T constructor( Object anonymousObjectContainingConstructor ){
            StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
            ObjectCreationExpr oce = Expr.anonymousObject(ste);
            MethodDeclaration theMethod = (MethodDeclaration)
                    oce.getAnonymousClassBody().get().stream().filter(m -> m instanceof MethodDeclaration &&
                            !m.isAnnotationPresent(_remove.class) ).findFirst().get();
            //build the base method first
            _constructor _ct = _constructor.of( theMethod.getNameAsString() + " " +_parameters.of( theMethod )+"{}" );
            //MODIFIERS
            if( theMethod.isPublic() ){
                _ct.setPublic();
            }
            if(theMethod.isProtected()){
                _ct.setProtected();
            }
            if(theMethod.isPrivate()){
                _ct.setPrivate();
            }
            _ct.setBody( theMethod.getBody().get() ); //BODY
            _ct.annotate( theMethod.getAnnotations() ); //ANNOTATIONS
            if( theMethod.hasJavaDocComment() ){
                _ct.ast().setJavadocComment( theMethod.getJavadocComment().get());
            }
            constructor(_ct);
            return (T)this;
        }

        T constructor( ConstructorDeclaration constructor );


        default T constructor(String ctor){
            return constructor( new String[]{ctor});
        }
        /**
         * builds a {@link _constructor} from a String and adds it to the
         *
         * ALSO supports the "shortCut Constructor" (you can avoid
         * passing in the signature or PARAMETERS
         *
         * 1) shortcut: pass in PARAMETERS and BODY
         * _type _t = _class.of("aaaa.bbb.C");
         * _t.constructor( "(String NAME){ this.NAME = NAME;}" );
         *
         * //builds the following constructor:
         *  public C (String NAME){
         *     this.NAME = NAME;
         *  }
         *
         * 2) shortcut: pass in only BODY
         * _t.constructor("{ System.out.println(100); }");
         *
         * //builds the following constructor:
         *  public C (){
         *     System.out.println(100);
         *  }
         *
         * "{this.}
         * @param constructor
         * @return
         */
        default T constructor( String... constructor ) {

            String combined = Text.combine(constructor);
            if( combined.startsWith("(")) {
                _constructor _ct = null;
                if( this instanceof _enum ){ //enums inferred private CONSTRUCTORS
                    _ct = _constructor.of(((_type) this).getName() + combined);
                } else {
                    _ct = _constructor.of("public " + ((_type) this).getName() + combined);
                }
                return constructor(_ct);
            }
            if( combined.startsWith("{")){ //no arg default public constructor
                _constructor _ct = null;
                if( this instanceof _enum ){ //enums only private CONSTRUCTORS
                    _ct = _constructor.of( ((_type) this).getName() + "()"+combined);
                } else {
                    _ct = _constructor.of("public " + ((_type) this).getName() + "()"+combined);
                }
                return constructor(_ct);
            }

            return constructor( Ast.ctor( constructor ) );
        }

        /**
         * constructor ( ()-> System.out.println("in constructor") );
         * @param command
         * @return
         */
        default <A extends Object, B extends Object>T constructor( BiConsumer<A,B> command ){
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            T t = (T)this;
            _constructor _ct = of(t.getName()+"(){}");
            if( t instanceof _enum ) {
                _ct.setPrivate();
            } else{
                _ct.setPublic();
            }
            //set the PARAMETERS
            _parameters _ps = _parameters.of(le);
            _ps.forEach(p->{
                if( p.getType().toString().isEmpty() ){
                    p.type("Object");
                }
                _ct.addParameter(p);
            });
            if( le.getBody().isBlockStmt() ){
                _ct.setBody(le.getBody().asBlockStmt());
            } else{
                _ct.add(le.getBody());
            }
            return constructor( _ct);
        }

        /**
         * constructor ( ()-> System.out.println("in constructor") );
         * @param command
         * @return
         */
        default <A extends Object> T constructor( Consumer<A> command ){
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            T t = (T)this;
            _constructor _ct = of(t.getName()+"(){}");
            if( t instanceof _enum ) {
                _ct.setPrivate();
            } else{
                _ct.setPublic();
            }
            //set the PARAMETERS
            _parameters _ps = _parameters.of(le);
            _ps.forEach(p->{
                if( p.getType().toString().isEmpty() ){
                    p.type("Object");
                }
                _ct.addParameter(p);
            });
            if( le.getBody().isBlockStmt() ){
                _ct.setBody(le.getBody().asBlockStmt());
            } else{
                _ct.add(le.getBody());
            }
            return constructor( _ct);
        }

        /**
         * constructor ( ()-> System.out.println("in constructor") );
         * @param command
         * @return
         */
        default T constructor( Expr.Command command ){
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            T t = (T)this;
            _constructor _ct = of(t.getName()+"(){}");
            if( t instanceof _enum ) {
                _ct.setPrivate();
            } else{
                _ct.setPublic();
            }
            //set the PARAMETERS
            _parameters _ps = _parameters.of(le);
            _ps.forEach(p->{
                if( p.getType().toString().isEmpty() ){
                    p.type("Object");
                }
                _ct.addParameter(p);
            });
            if( le.getBody().isBlockStmt() ){
                _ct.setBody(le.getBody().asBlockStmt());
            } else{
                _ct.add(le.getBody());
            }
            return constructor( _ct);
        }

        default T constructor( _constructor _c ) {
            return constructor( _c.ast() );
        }
    }
}
