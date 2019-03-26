package draft.java;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithConstructors;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;

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
public final class _constructor implements _anno._hasAnnos<_constructor>, 
    _javadoc._hasJavadoc<_constructor>,_throws._hasThrows<_constructor>, 
    _body._hasBody<_constructor>, _modifiers._hasModifiers<_constructor>,
    _parameter._hasParameters<_constructor>, _typeParameter._hasTypeParameters<_constructor>,
    _receiverParameter._hasReceiverParameter<_constructor>,
    _member<ConstructorDeclaration, _constructor> {


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
    }

    @Override
    public ConstructorDeclaration ast() {
        return astCtor;
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
        if( !Ast.annotationsEqual( this.astCtor, other.astCtor)) {
            return false;
        }
        if( !Objects.equals( this.getBody(), other.getBody() ) ) {
            return false;
        }
        if( this.hasJavadoc() != other.hasJavadoc() ) {
            return false;
        }
        if( this.hasJavadoc() && !Objects.equals( this.getJavadoc().getContent().trim(), other.getJavadoc().getContent().trim() ) ) {
            return false;
        }
        if( !Ast.modifiersEqual(this.astCtor, other.astCtor) ){
            return false;
        }       
        if( !Objects.equals( this.getName(), other.getName() ) ) {
            return false;
        }
        if( !Objects.equals( this.getParameters(), other.getParameters() ) ) {
            return false;
        }
        if( !Ast.typesEqual( this.astCtor.getThrownExceptions(), other.astCtor.getThrownExceptions()) ){
            return false;
        }        
        if( !Objects.equals( this.getTypeParameters(), other.getTypeParameters() ) ) {
            return false;
        }
        if( !Objects.equals( this.getReceiverParameter(), other.getReceiverParameter() ) ) {
            return false;
        }
        return true;
    }

    @Override
    public Map<_java.Component, Object> componentsMap() {
        Map<_java.Component, Object> parts = new HashMap<>();
        parts.put( _java.Component.ANNOS, getAnnos() );
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
                Ast.annotationsHash( astCtor ), 
                this.getBody(), 
                this.getJavadoc(),
                this.getEffectiveModifiers(),
                this.getName(), 
                this.getParameters(),
                Ast.typesHashCode( astCtor.getThrownExceptions()), 
                this.getTypeParameters(), 
                this.getReceiverParameter() );
        return hash;
    }

    @Override
    public _constructor name( String name ) {
        this.astCtor.setName( name );
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
            if( genericParameterTypes.length == pl.size() + 1 && ctor.getDeclaringClass().isLocalClass()){
                if( ctor.getDeclaringClass().isLocalClass() ){
                    delta = 1;
                }
            } else{
                return false;
            }            
        }
        for(int i=0;i<pl.size(); i++){
            _typeRef _t = _typeRef.of(genericParameterTypes[i+delta]);
            if( !pl.get(i).isType( _t ) ){ 
                if( ctor.isVarArgs() &&  //if last parameter and varargs
                    Ast.typesEqual( pl.get(i).getType().getElementType(), 
                        _t.getElementType())  ){                    
                } else{             
                    return false;
                }
            }
        }        
        return true;        
    }
    
    public boolean is( String...constructorDeclaration ){
        try {
            _constructor _ct = of(constructorDeclaration);
            _ct.astCtor.setModifiers( Ast.merge(_ct.ast().getModifiers(), Ast.getImpliedModifiers( this.astCtor ) ) );
            return equals(_ct);
        }
        catch(Exception e){
            //System.out.println( "Expecetion"+ e);
            //e.printStackTrace();
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
        return this.astCtor.isPrivate() || this.getEffectiveModifiers().contains(Modifier.privateModifier());
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
    
    /**
     *
     * @author Eric
     * @param <T>
     * @param <N> the AST node type (must implement NodeWithConstructors)
     */
    public interface _hasConstructors<T extends _hasConstructors & _type, N extends Node & NodeWithConstructors>
            extends _model {

        N ast();
        
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

        /**
         * remove the constructor defined by this astConstructor
         * @param astConstructor the ast representation of the constructor
         * @return  the modified T         
         */ 
        default T removeConstructor( ConstructorDeclaration astConstructor ){
            return removeConstructor( _constructor.of(astConstructor).name(((_type)this).getName()) );        
        }

        /**
         * Remove the constructor and return the modified T
         * @param _ct the constructor instance to remove
         * @return 
         */
        default T removeConstructor( _constructor _ct){
            _constructor _cc = _ct.copy().name( ((_type)this).getName() );
        
            listConstructors( c-> c.equals( _cc ) ).forEach(c-> c.ast().removeForced() );        
            return (T)this;
        }

        /**
         * Remove all constructors that match the _constructorMatchFn and return 
         * the modified T
         * @param ctorMatchFn function for matching constructors for removal
         * @return the modified T
         */ 
        default T  removeConstructors( Predicate<_constructor> ctorMatchFn){
            listConstructors(ctorMatchFn).forEach(c -> removeConstructor(c));
            return (T)this;
        }
        
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

        /**
         * Build & add a constructor based on the AST representation
         * @param astConstructor
         * @return 
         */
        T constructor( ConstructorDeclaration astConstructor );


        /**
         * Build & Add a constructor from the String representation of the code
         * @param ctor the constructor that was built
         * @return 
         */
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
         * 
         * constructor ( ()-> System.out.println("in constructor") );
         * 
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
         * @param <A>
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
