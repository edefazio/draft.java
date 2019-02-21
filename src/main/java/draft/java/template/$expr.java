package draft.java.template;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import draft.*;
import draft.java.*;

import java.util.*;
import java.util.function.Consumer;

/**
 * Template for a Java Expression
 *
 * @param <T> the underlying Expression TYPE
 */
public final class $expr <T extends Expression>
        implements Template<T>, $query<T>  {

    public static $expr of( String...code ){
        return new $expr(Expr.of(code));
    }

    public static $expr of( Expression e ){
        return new $expr(e );
    }

    /**
     * i.e. "arr[3]"
     * @param code
     * @return
     */
    public static $expr<ArrayAccessExpr> arrayAccess(String... code ) {
        return new $expr<ArrayAccessExpr>( Expr.arrayAccess(code) );
    }

    /**
     * i.e. "new Obj[]", "new int[][]"
     */
    public static $expr<ArrayCreationExpr> arrayCreation( String... code ) {
        return new $expr<ArrayCreationExpr>( Expr.arrayCreation( code ) );
    }

    /**
     * i.e. "{1,2,3,4,5}"
     */
    public static $expr<ArrayInitializerExpr> arrayInitializer( String... code ) {
        return new $expr<ArrayInitializerExpr>( Expr.arrayInitializer( code ) );
    }

    /** i.e. "a = 1", "a = 4" */
    public static $expr<AssignExpr> assign( String... code ) {
        return new $expr<AssignExpr>( Expr.assign( code ) );
    }

    /** a || b */
    public static $expr<BinaryExpr> binary( String... code ) {
        return new $expr<BinaryExpr>( Expr.binary( code ) );
    }

    /** i.e. true */
    public static $expr<BooleanLiteralExpr> of( boolean b ){
        return new $expr<BooleanLiteralExpr>( Expr.of( b ) );
    }

    public static $expr<BooleanLiteralExpr> booleanLiteral( boolean b) {
        return new $expr( Expr.of( b ) );
    }
    /** "true" / "false" */
    public static $expr<BooleanLiteralExpr> booleanLiteral( String... code ) {
        return new $expr( Expr.booleanLiteral( code ) );
    }

    /** (String)o */
    public static $expr<CastExpr> cast( String... code ) {
        return new $expr( Expr.cast( code ) );
    }

    /** 'c' */
    public static $expr<CharLiteralExpr> of( char c ){
        return new $expr( Expr.charLiteral( c ) );
    }

    /** 'c' */
    public static $expr<CharLiteralExpr> charLiteral( char c ) {
        return new $expr( Expr.charLiteral( c ) );
    }

    /** 'c' */
    public static $expr<CharLiteralExpr> charLiteral( String... code ) {
        return new $expr( Expr.charLiteral( code ) );
    }

    /** i.e. "String.class" */
    public static $expr<ClassExpr> classExpr( String... code ) {
        return new $expr( Expr.classExpr( code ) );
    }

    /** i.e. "(a < b) ? a : b" */
    public static $expr<ConditionalExpr> conditional( String... code ) {
        return new $expr( Expr.conditional( code ) );
    }

    /** 3.14d */
    public static $expr<DoubleLiteralExpr> of( double d ){
        return new $expr( Expr.of( d ) );
    }

    /** 3.14d */
    public static $expr<DoubleLiteralExpr> doubleLiteral( double d ) {
        return new $expr( Expr.doubleLiteral( d ) );
    }

    public static $expr<DoubleLiteralExpr> doubleLiteral( String s ) {
        return new $expr( Expr.doubleLiteral( s ) );
    }

    public static $expr<DoubleLiteralExpr> of( float d ){
        return new $expr( Expr.doubleLiteral( d ) );
    }

    public static $expr<DoubleLiteralExpr> doubleLiteral( float d ) {
        return new $expr( Expr.of( d ) );
    }

    /** i.e. "3.14f" */
    public static $expr<DoubleLiteralExpr> floatLiteral( String... code ) {
        return new $expr( Expr.floatLiteral( code ) );
    }

    /** i.e. ( 3 + 4 ) */
    public static $expr<EnclosedExpr> enclosedExpr( String... code ) {
        return new $expr( Expr.enclosedExpr( code ) );
    }

    /** i.e. "person.NAME"*/
    public static $expr<FieldAccessExpr> fieldAccess(String... code ) {
        return new $expr( Expr.fieldAccess( code ) );
    }

    /** v instanceof Serializable */
    public static $expr<InstanceOfExpr> instanceOf(String... code ) {
        return new $expr( Expr.instanceOf( code ) );
    }

    public static $expr<IntegerLiteralExpr> of(int i) {
        return new $expr( Expr.of( i ) );
    }

    public static $expr<IntegerLiteralExpr> intLiteral(int i) {
        return new $expr( Expr.of( i ) );
    }

    public static $expr<IntegerLiteralExpr> intLiteral(String... code ) {
        return new $expr( Expr.intLiteral( code ) );
    }

    /** a-> System.out.println( a )  */
    public static $expr<LambdaExpr> lambda(String... code ) {
        return new $expr( Expr.lambda( code ) );
    }

    public static $expr<LongLiteralExpr> of(long l) {
        return new $expr( Expr.of( l ) );
    }

    public static $expr<LongLiteralExpr> longLiteral( long l ) {
        return new $expr( Expr.of( l ) );
    }

    public static $expr<LongLiteralExpr> longLiteral( String... code ) {
        return new $expr( Expr.longLiteral( code ) );
    }

    /** doMethod(t) */
    public static $expr<MethodCallExpr> methodCall( String... code ) {
        return new $expr( Expr.methodCall( code ) );
    }

    /** i.e. "String:toString" */
    public static $expr<MethodReferenceExpr> methodReference( String... code ) {
        return new $expr( Expr.methodReference( code ) );
    }

    /** i.e. "null" */
    public static $expr<NullLiteralExpr> nullExpr(){
        return new $expr( Expr.nullExpr() );
    }

    public static $expr<NameExpr> name( String... code ) {
        return new $expr( Expr.name(  code ) );
    }

    /** "new Date();" */
    public static $expr<ObjectCreationExpr> objectCreation(String... code ) {
        return new $expr( Expr.objectCreation( code ) );
    }

    /** "literal" */
    public static $expr<StringLiteralExpr> stringLiteral( String... code ) {
        return new $expr( Expr.stringLiteral( code ) );
    }

    /** "super" */
    public static $expr<SuperExpr> superExpr(  ) {
        return new $expr( Expr.superExpr( ) );
    }

    /** "this" */
    public static $expr<ThisExpr> thisExpr(  ) {
        return new $expr( Expr.thisExpr( ) );
    }

    /** i.e. "World" in World::greet */
    public static $expr<TypeExpr> typeExpr(String... code ) {
        return new $expr( Expr.typeExpr( code ) );
    }

    /** i.e. "!true" */
    public static $expr<UnaryExpr> unary( String... code ) {
        return new $expr( Expr.unary( code ) );
    }

    /** "int i = 1" */
    public static $expr<VariableDeclarationExpr> varDecl( String... code ) {
        return new $expr( Expr.varDecl( code ) );
    }

    public Class<T> expressionClass;
    public Stencil stencil;

    public $expr(T ex){
        this.expressionClass = (Class<T>)ex.getClass();
        this.stencil = Stencil.of( ex.toString() );
    }

    public $expr(Class<T>expressionClass, String stencil ){
        this.expressionClass = expressionClass;
        this.stencil = Stencil.of(stencil);
    }

    @Override
    public T fill(Object...values){
        String str = stencil.fill(Translator.DEFAULT_TRANSLATOR, values);
        return (T)Expr.of( str);
    }

    @Override
    public $expr<T> $(String target, String $name ) {
        this.stencil = this.stencil.$(target, $name);
        return this;
    }

    public $expr<T> $( Expression e, String $name){
        this.stencil = this.stencil.$(e.toString(), $name);
        return this;
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $expr assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $expr assign$( Object... keyValues ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, Tokens.of( keyValues ) );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param translator translates values to be hardcoded into the Stencil
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $expr assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    public $expr assign$( Translator translator, Tokens kvs ) {
        this.stencil = this.stencil.assign$(translator,kvs);
        return this;
    }

    @Override
    public T fill(Translator t, Object...values){
        return (T)Expr.of( stencil.fill(t, values));
    }

    @Override
    public T construct( Object...keyValues ){
        return (T)Expr.of( stencil.construct( Tokens.of(keyValues)));
    }


    public T construct( _model._node model ){
        return (T)$expr.this.construct(model.componentize());
    }

    @Override
    public T construct( Translator t, Object...keyValues ){
        return (T)Expr.of( stencil.construct( t, Tokens.of(keyValues) ));
    }

    @Override
    public T construct( Map<String,Object> tokens ){
        return (T)Expr.of( stencil.construct( Translator.DEFAULT_TRANSLATOR, tokens ));
    }

    @Override
    public T construct( Translator t, Map<String,Object> tokens ){
        return (T)Expr.of(stencil.construct( t, tokens ));
    }

    public boolean matches( String...expression ){
        return deconstruct( Expr.of(expression)) != null;
    }

    public boolean matches( Expression expression ){
        return deconstruct(expression) != null;
    }

    @Override
    public List<String> list$(){
        return this.stencil.list$();
    }

    @Override
    public List<String> list$Normalized(){
        return this.stencil.list$Normalized();
    }

    /**
     * Deconstruct the expression into tokens, or return null if the statement doesnt match
     *
     * @param expression expression
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public Tokens deconstruct( Expression expression ){
        if( expressionClass.isAssignableFrom(expression.getClass())){
            //slight modification..
            if( expression instanceof LiteralStringValueExpr ) {
                //there is an issue here the lowercase and uppercase Expressions 1.23d =/= 1.23D (which they are equivalent
                //need to handle postfixes 1.2f, 2.3d, 1000l
                //need to handle postfixes 1.2F, 2.3D, 1000L
            }
            if( expression instanceof DoubleLiteralExpr ){
                DoubleLiteralExpr dle = (DoubleLiteralExpr)expression;
            }
            return stencil.deconstruct( expression.toString() );
        }
        return null;
    }

    public Select select( Expression e){
        Tokens ts = this.deconstruct(e);
        if( ts != null){
            return new Select( e, ts );
        }
        return null;
    }

    @Override
    public List<T> findAllIn(_model._node _t ){
        return findAllIn( _t.ast() );
    }

    @Override
    public List<T> findAllIn(Node rootNode ){
        List<T> typesList = new ArrayList<>();
        rootNode.walk(this.expressionClass, t->{
            if( this.matches(t) ){
                typesList.add(t);
            }
        } );
        return typesList;
    }

    @Override
    public <N extends Node> N forAllIn(N n, Consumer<T> expressionActionFn){
        n.walk(this.expressionClass, e-> {
            Tokens tokens = this.stencil.deconstruct( e.toString());
            if( tokens != null ){
                expressionActionFn.accept( e);
            }
        });
        return n;
    }

    @Override
    public <M extends _model._node> M forAllIn(M _t, Consumer<T> expressionActionFn){
        Walk.in( _t, this.expressionClass, e -> {
            Tokens tokens = this.stencil.deconstruct( e.toString());
            if( tokens != null ){
                expressionActionFn.accept( e);
            }
        });
        return _t;
    }

    @Override
    public List<Select<T>> selectAllIn(Node n ){
        List<Select<T>>sts = new ArrayList<>();
        n.walk(this.expressionClass, e-> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    @Override
    public List<Select<T>> selectAllIn(_model._node _t ){
        List<Select<T>>sts = new ArrayList<>();
        Walk.in( _t, this.expressionClass, e -> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    @Override
    public <N extends Node> N removeIn(N node){
        node.walk( this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.expression.removeForced();
            }
        });
        return node;
    }

    @Override
    public <M extends _model._node> M removeIn(M _model){
        Walk.in( _model, this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.expression.removeForced();
            }
        });
        return _model;
    }

    public <M extends _model._node> M replaceIn(M _le, Node replacement ){
        Walk.in( _le, this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.expression.replace( replacement );
            }
        });
        return _le;
    }

    public <M extends _model._node> M replaceIn(M _le, $expr $repl ){
        Walk.in(_le, this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.expression.replace($repl.construct(sel.tokens));
            }
        });
        return _le;
    }

    public <M extends _model._node> M forSelectedIn(M _le, Consumer<Select> selectConsumer ){
        Walk.in( _le, this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return _le;
    }

    public <N extends Node> N forSelectedIn(N n, Consumer<Select> selectConsumer ){
        n.walk(this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return n;
    }

    @Override
    public String toString() {
        return "(" + this.expressionClass.getSimpleName() + ") : \"" + this.stencil + "\"";
    }

    public static class Select<T extends Expression> implements $query.selected {
        public T expression;
        public Tokens tokens;

        public Select( T expression, Tokens tokens){
            this.expression = expression;
            this.tokens = tokens;
        }
        
        @Override
        public String toString(){
            return "$expr.Select{"+ System.lineSeparator()+
                    Text.indent( expression.toString() )+ System.lineSeparator()+
                    Text.indent( "TOKENS : " + tokens) + System.lineSeparator()+
                    "}";
        }
    }
}
