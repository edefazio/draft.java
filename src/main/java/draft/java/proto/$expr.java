package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import draft.*;
import draft.java.*;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Template for a Java Expression
 *
 * @param <T> the underlying Expression TYPE
 */
public final class $expr <T extends Expression>
        implements Template<T>, $query<T>  {

    /**
     * 
     * @param <N>
     * @param <E>
     * @param rootNode
     * @param source
     * @return 
     */
    public static final <N extends _model._node, E extends Expression> List<E> list( N rootNode, String source ){
        return (List<E>)$expr.of(source).listIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param rootNode
     * @param astExpr
     * @return 
     */
    public static final <N extends _model._node, E extends Expression> List<E> list( N rootNode, E astExpr ){
        return $expr.of(astExpr).listIn(rootNode);
    }    
    
    public static final <N extends _model._node, E extends Expression> List<E> list( N rootNode, $expr<E> astExpr ){
        return (List<E>)astExpr.listIn(rootNode);
    }
    
    /**
     * 
     * @param <M>
     * @param rootNode
     * @param source
     * @param target
     * @return 
     */
    public static final <M extends _model._node> M replace(M rootNode, Expression source, Expression target){
        return (M)$expr.of(source)
            .replaceIn(rootNode, $expr.of(target));
    }
    
    /**
     * 
     * @param <M>
     * @param rootNode
     * @param source
     * @param target
     * @return 
     */
    public static final <M extends _model._node> M replace( 
        M rootNode, String source, String target){
        
        return (M)$expr.of(source)
            .replaceIn(rootNode, $expr.of(target));
    }
    
    public static final Expression replace( Expression expr, String source, String target){
        Tokens ts = $expr.of(source).deconstruct(expr);
        if( ts != null ){
            Expression t = $expr.of(target).construct(ts);
            expr.replace(t);
            return t;
        }
        return null;
    }
    
    public static <T extends Expression> $expr<T> of( String...code ){
        return new $expr<>( (T)Expr.of(code));
    }
    
    public static <T extends Expression> $expr<T> of( String code, Predicate<T> constraint ){
        return new $expr<>( (T)Expr.of(code)).constraint(constraint);
    }

    //public static $expr of( String...code ){
    //    return new $expr(Expr.of(code));
    //}
    
    public static <T extends Expression> $expr<T> of(T e ){
        return new $expr<>(e );
    }

    public static <T extends Expression> $expr<T> of(T e, Predicate<T> constraint ){
        return new $expr<>(e ).constraint(constraint);
    }
        
    //public static $expr of( Expression e, Predicate<Expression> constraint ){
    //    return new $expr(e ).constraint(constraint);
    //}
        
    /**
     * i.e. "arr[3]"
     * @param code
     * @return
     */
    public static $expr<ArrayAccessExpr> arrayAccess(String... code ) {
        return new $expr<ArrayAccessExpr>( Expr.arrayAccess(code) );
    }

    /**
     * i.e. "arr[3]"
     * @param code
     * @return
     */
    public static $expr<ArrayAccessExpr> arrayAccess(String code, Predicate<ArrayAccessExpr> constraint) {
        return new $expr<ArrayAccessExpr>( Expr.arrayAccess(code) ).constraint(constraint);
    }
    
    /**
     * i.e. "new Obj[]", "new int[][]"
     */
    public static $expr<ArrayCreationExpr> arrayCreation( String... code ) {
        return new $expr<ArrayCreationExpr>( Expr.arrayCreation( code ) );
    }

    /**
     * i.e. "new Obj[]", "new int[][]"
     */
    public static $expr<ArrayCreationExpr> arrayCreation( String code, Predicate<ArrayCreationExpr> constraint ) {
        return new $expr<ArrayCreationExpr>( Expr.arrayCreation( code ) ).constraint(constraint);
    }
    
    /**
     * i.e. "{1,2,3,4,5}"
     */
    public static $expr<ArrayInitializerExpr> arrayInitializer( String... code ) {
        return new $expr<ArrayInitializerExpr>( Expr.arrayInitializer( code ) );
    }
    
    public static $expr<ArrayInitializerExpr> arrayInitializer( int[] ints ) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i=0;i<ints.length; i++){
            if( i > 0){
                sb.append(",");
            }
            sb.append(ints[i]);
        }
        sb.append("}");
        return arrayInitializer(sb.toString());        
    }
    
    public static $expr<ArrayInitializerExpr> arrayInitializer( boolean[] bools ) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i=0;i<bools.length; i++){
            if( i > 0){
                sb.append(",");
            }
            sb.append(bools[i]);
        }
        sb.append("}");
        return arrayInitializer(sb.toString());        
    }
    
    public static $expr<ArrayInitializerExpr> arrayInitializer( char[] chars ) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i=0;i<chars.length; i++){
            if( i > 0){
                sb.append(",");
            }
            sb.append("'").append(chars[i]).append("'");
        }
        sb.append("}");
        return arrayInitializer(sb.toString());        
    }
    
    public static $expr<ArrayInitializerExpr> arrayInitializer( double[] doubles ) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i=0;i<doubles.length; i++){
            if( i > 0){
                sb.append(",");
            }
            sb.append(doubles[i]).append("d");
        }
        sb.append("}");
        return arrayInitializer(sb.toString());        
    }
    
    /**
     * i.e. "{1,2,3,4,5}"
     */
    public static $expr<ArrayInitializerExpr> arrayInitializer( String code, Predicate<ArrayInitializerExpr> constraint) {
        return new $expr<ArrayInitializerExpr>( Expr.arrayInitializer( code ) ).constraint(constraint);
    }
    //TODO String, int, float, boolean, double, long of`
    

    /** i.e. "a = 1", "a = 4" */
    public static $expr<AssignExpr> assign( String... code ) {
        return new $expr<AssignExpr>( Expr.assign( code ) );
    }
    
    /** i.e. "a = 1", "a = 4" */
    public static $expr<AssignExpr> assign( String code, Predicate<AssignExpr> constraint) {
        return new $expr<AssignExpr>( Expr.assign( code ) ).constraint(constraint);
    }

    /** a || b */
    public static $expr<BinaryExpr> binary( String... code ) {
        return new $expr<BinaryExpr>( Expr.binary( code ) );
    }
    
    public static $expr<BinaryExpr> binary( String  code, Predicate<BinaryExpr> constraint) {
        return new $expr<BinaryExpr>( Expr.binary( code ) ).constraint(constraint);
    }
    

    /** i.e. true */
    public static $expr<BooleanLiteralExpr> of( boolean b ){
        return new $expr<BooleanLiteralExpr>( Expr.of( b ) );
    }

    public static $expr<BooleanLiteralExpr> booleanLiteral( boolean b) {
        return new $expr( Expr.of( b ) );
    }
    
    public static $expr<BooleanLiteralExpr> booleanLiteral( boolean b, Predicate<BooleanLiteralExpr> constraint) {
        return new $expr( Expr.of( b ) ).constraint(constraint);
    }
    
    /** "true" / "false" */
    public static $expr<BooleanLiteralExpr> booleanLiteral( String... code ) {
        return new $expr( Expr.booleanLiteral( code ) );
    }

    /** "true" / "false" */
    public static $expr<BooleanLiteralExpr> booleanLiteral( String code, Predicate<CastExpr>constraint ) {
        return new $expr( Expr.booleanLiteral( code ) ).constraint(constraint);
    }
    
    /** (String)o */
    public static $expr<CastExpr> cast( String... code ) {
        return new $expr( Expr.cast( code ) );
    }

    /** (String)o */
    public static $expr<CastExpr> cast( String code, Predicate<CastExpr> constraint ) {
        return new $expr( Expr.cast( code ) ).constraint(constraint);
    }
    
    /** 'c' */
    public static $expr<CharLiteralExpr> of( char c ){
        return new $expr( Expr.charLiteral( c ) );
    }

    /** 'c' */
    public static $expr<CharLiteralExpr> of( char c, Predicate<CharLiteralExpr> constraint){
        return new $expr( Expr.charLiteral( c ) ).constraint(constraint);
    }
    
    /** 'c' */
    public static $expr<CharLiteralExpr> charLiteral( char c ) {
        return new $expr( Expr.charLiteral( c ) );
    }

    /** 'c' */
    public static $expr<CharLiteralExpr> charLiteral( char c, Predicate<CharLiteralExpr> constraint) {
        return new $expr( Expr.charLiteral( c ) ).constraint(constraint);
    }
    
    /** 'c' */
    public static $expr<CharLiteralExpr> charLiteral( String... code ) {
        return new $expr( Expr.charLiteral( code ) );
    }

    /** 'c' */
    public static $expr<CharLiteralExpr> charLiteral( String code, Predicate<CharLiteralExpr> constraint) {
        return new $expr( Expr.charLiteral( code ) ).constraint(constraint);
    }
    
    /** i.e. "String.class" */
    public static $expr<ClassExpr> classExpr( String... code ) {
        return new $expr( Expr.classExpr( code ) );
    }
    
    public static $expr<ClassExpr> classExpr( String code, Predicate<ConditionalExpr> constraint) {
        return new $expr( Expr.classExpr( code ) ).constraint(constraint);
    }

    /** i.e. "(a < b) ? a : b" */
    public static $expr<ConditionalExpr> conditional( String... code ) {
        return new $expr( Expr.conditional( code ) );
    }

    public static $expr<ConditionalExpr> conditional( String code, Predicate<ConditionalExpr> constraint) {
        return new $expr( Expr.conditional( code ) ).constraint(constraint);
    }
    
    /** 3.14d */
    public static $expr<DoubleLiteralExpr> of( double d ){
        return new $expr( Expr.of( d ) );
    }

    /** 3.14d */
    public static $expr<DoubleLiteralExpr> doubleLiteral( double d ) {
        return new $expr( Expr.doubleLiteral( d ) );
    }

    public static $expr<DoubleLiteralExpr> doubleLiteral( double d, Predicate<DoubleLiteralExpr> constraint) {
        return new $expr( Expr.doubleLiteral( d ) ).constraint(constraint);
    }
    
    public static $expr<DoubleLiteralExpr> doubleLiteral( String s ) {
        return new $expr( Expr.doubleLiteral( s ) );
    }

    public static $expr<DoubleLiteralExpr> doubleLiteral( String s, Predicate<DoubleLiteralExpr> constraint) {
        return new $expr( Expr.doubleLiteral( s ) ).constraint(constraint);
    }
        
    public static $expr<DoubleLiteralExpr> of( float d ){
        return new $expr( Expr.doubleLiteral( d ) );
    }

    public static $expr<DoubleLiteralExpr> doubleLiteral( float d ) {
        return new $expr( Expr.of( d ) );
    }

    public static $expr<DoubleLiteralExpr> doubleLiteral( float d, Predicate<DoubleLiteralExpr> constraint) {
        return new $expr( Expr.of( d ) ).constraint(constraint);
    }
        
    /** i.e. "3.14f" */
    public static $expr<DoubleLiteralExpr> floatLiteral( String... code ) {
        return new $expr( Expr.floatLiteral( code ) );
    }

    public static $expr<DoubleLiteralExpr> floatLiteral( String code, Predicate<DoubleLiteralExpr> constraint ) {
        return new $expr( Expr.floatLiteral( code ) ).constraint(constraint);
    }
    
    /** i.e. ( 3 + 4 ) */
    public static $expr<EnclosedExpr> enclosedExpr( String... code ) {
        return new $expr( Expr.enclosedExpr( code ) );
    }

    public static $expr<EnclosedExpr> enclosedExpr( String code, Predicate<EnclosedExpr>constraint ) {
        return new $expr( Expr.enclosedExpr( code ) ).constraint(constraint);
    }
    
    /** i.e. "person.NAME"*/
    public static $expr<FieldAccessExpr> fieldAccess(String... code ) {
        return new $expr( Expr.fieldAccess( code ) );
    }

    public static $expr<FieldAccessExpr> fieldAccess(String code, Predicate<FieldAccessExpr> constraint ) {
        return new $expr( Expr.fieldAccess( code ) ).constraint(constraint);
    }
    
    /** v instanceof Serializable */
    public static $expr<InstanceOfExpr> instanceOf(String... code ) {
        return new $expr( Expr.instanceOf( code ) );
    }
    
    public static $expr<InstanceOfExpr> instanceOf(String  code, Predicate<InstanceOfExpr> constraint ) {
        return new $expr( Expr.instanceOf( code ) ).constraint(constraint);
    }
    

    public static $expr<IntegerLiteralExpr> of(int i) {
        return new $expr( Expr.of( i ) );
    }

    public static $expr<IntegerLiteralExpr> of(int i, Predicate<IntegerLiteralExpr> constraint) {
        return new $expr( Expr.of( i ) ).constraint(constraint);
    }
    
    public static $expr<IntegerLiteralExpr> intLiteral(int i) {
        return new $expr( Expr.of( i ) );
    }

    public static $expr<IntegerLiteralExpr> intLiteral(int i, Predicate<IntegerLiteralExpr> constraint) {
        return new $expr( Expr.of( i ) ).constraint(constraint);
    }
    
    public static $expr<IntegerLiteralExpr> intLiteral(String... code ) {
        return new $expr( Expr.intLiteral( code ) );
    }

    public static $expr<IntegerLiteralExpr> intLiteral(String code, Predicate<IntegerLiteralExpr> constraint ) {
        return new $expr( Expr.intLiteral( code ) ).constraint(constraint);
    }
    /** a-> System.out.println( a )  */
    public static $expr<LambdaExpr> lambda(String... code ) {
        return new $expr( Expr.lambda( code ) );
    }

    public static $expr<LambdaExpr> lambda(String code , Predicate<LambdaExpr> constraint) {
        return new $expr( Expr.lambda( code ) ).constraint(constraint);
    }
    
    public static $expr<LongLiteralExpr> of(long l) {
        return new $expr( Expr.of( l ) );
    }

    public static $expr<LongLiteralExpr> of(long l, Predicate<LongLiteralExpr> constraint ) {
        return new $expr( Expr.of( l ) ).constraint(constraint);
    }
    
    public static $expr<LongLiteralExpr> longLiteral( long l ) {
        return new $expr( Expr.of( l ) );
    }

    public static $expr<LongLiteralExpr> longLiteral( long l, Predicate<LongLiteralExpr> constraint ) {
        return new $expr( Expr.of( l ) ).constraint(constraint);
    }
        
    public static $expr<LongLiteralExpr> longLiteral( String... code ) {
        return new $expr( Expr.longLiteral( code ) );
    }
    
    public static $expr<LongLiteralExpr> longLiteral( String code, Predicate<LongLiteralExpr> constraint ) {
        return new $expr( Expr.longLiteral( code ) ).constraint(constraint);
    }

    /** doMethod(t) */
    public static $expr<MethodCallExpr> methodCall( String... code ) {
        return new $expr( Expr.methodCall( code ) );
    }

    public static $expr<MethodCallExpr> methodCall( String code, Predicate<MethodCallExpr> constraint ) {
        return new $expr( Expr.methodCall( code ) ).constraint(constraint);
    }
    
    /** i.e. "String:toString" */
    public static $expr<MethodReferenceExpr> methodReference( String... code ) {
        return new $expr( Expr.methodReference( code ) );
    }
    
    /** i.e. "String:toString" */
    public static $expr<MethodReferenceExpr> methodReference( String code, Predicate<MethodReferenceExpr>constraint ) {
        return new $expr( Expr.methodReference( code ) ).constraint(constraint);
    }
    

    /** i.e. "null" */
    public static $expr<NullLiteralExpr> nullExpr(){
        return new $expr( Expr.nullExpr() );
    }

    public static $expr<NameExpr> name( String... code ) {
        return new $expr( Expr.name(  code ) );
    }
    
    public static $expr<NameExpr> name( String code, Predicate<NameExpr>constraint) {
        return new $expr( Expr.name(  code ) ).constraint(constraint);
    }

    /** "new Date();" */
    public static $expr<ObjectCreationExpr> objectCreation(String... code ) {
        return new $expr( Expr.objectCreation( code ) );
    }
    
    /** "new Date();" */
    public static $expr<ObjectCreationExpr> objectCreation(String code, Predicate<ObjectCreationExpr>constraint ) {
        return new $expr( Expr.objectCreation( code ) ).constraint(constraint);
    }

    /** "literal" */
    public static $expr<StringLiteralExpr> stringLiteral( String... code ) {
        return new $expr( Expr.stringLiteral( code ) );
    }

    public static $expr<StringLiteralExpr> stringLiteral( String code, Predicate<StringLiteralExpr> constraint) {
        return new $expr( Expr.stringLiteral( code ) ).constraint(constraint);
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
    
    /** i.e. "World" in World::greet */
    public static $expr<TypeExpr> typeExpr(String code, Predicate<TypeExpr> constraint ) {
        return new $expr( Expr.typeExpr( code ) ).constraint(constraint);
    }

    /** i.e. "!true" */
    public static $expr<UnaryExpr> unary( String... code ) {
        return new $expr( Expr.unary( code ) );
    }

    
    /** i.e. "!true" */
    public static $expr<UnaryExpr> unary( String code, Predicate<UnaryExpr>constraint) {
        return new $expr( Expr.unary( code ) ).constraint(constraint);
    }
    
    /** "int i = 1" */
    public static $expr<VariableDeclarationExpr> varDecl( String... code ) {
        return new $expr( Expr.varDecl( code ) );
    }

    /** "int i = 1" */
    public static $expr<VariableDeclarationExpr> varDecl( String code, Predicate<VariableDeclarationExpr> constraint) {
        return new $expr( Expr.varDecl( code ) ).constraint(constraint);
    }
    
    public Class<T> expressionClass;
    public Stencil stencil;
    
    /**
     * Additional Constraint for matching this expression (besides purely being
     * a pattern match)
     * By default, ALWAYS matches
     */
    public Predicate<T> constraint = (t)->true;

    public $expr(T ex){
        this.expressionClass = (Class<T>)ex.getClass();
        this.stencil = Stencil.of( ex.toString() );
    }

    public $expr(Class<T>expressionClass, String stencil ){
        this.expressionClass = expressionClass;
        this.stencil = Stencil.of(stencil);
    }

    public $expr constraint( Predicate<T> constraint){
        this.constraint = constraint;
        return this;
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

    public Tokens deconstruct( String... expression ){
        return deconstruct( Expr.of(expression) );
    }
    
    /**
     * Deconstruct the expression into tokens, or return null if the statement doesnt match
     *
     * @param expression expression
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public Tokens deconstruct( Expression expression ){
        if( expressionClass.isAssignableFrom(expression.getClass()) 
                && constraint.test( (T)expression)){
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
    public List<T> listIn(_model._node _t ){
        return listIn( _t.ast() );
    }

    @Override
    public List<T> listIn(Node rootNode ){
        List<T> typesList = new ArrayList<>();
        rootNode.walk(this.expressionClass, t->{
            if( this.matches(t) ){
                typesList.add(t);
            }
        } );
        return typesList;
    }

    @Override
    public <N extends Node> N forIn(N n, Consumer<T> expressionActionFn){
        n.walk(this.expressionClass, e-> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                expressionActionFn.accept( e);
            }
        });
        return n;
    }

    @Override
    public <M extends _model._node> M forIn(M _t, Consumer<T> expressionActionFn){
        Walk.in( _t, this.expressionClass, e -> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                expressionActionFn.accept( e);
            }
        });
        return _t;
    }

    @Override
    public List<Select<T>> listSelectedIn(Node n ){
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
    public List<Select<T>> listSelectedIn(_model._node _t ){
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

    public <M extends _model._node> M replaceIn(M _le, String replaceExpression ){
        return replaceIn( _le, $expr.of(replaceExpression) );
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
