package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import draft.*;
import draft.java.*;
import draft.java._model._node;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Template for a Java Expression
 *
 * @param <T> the underlying Expression TYPE
 */
public final class $expr <T extends Expression>
        implements Template<T>, $query<T> {
    
    /**
     * find the first occurring instance of a matching expression within the rootNode
     * @param <N>
     * @param <E>
     * @param _n
     * @param protoExpr
     * @return the first matching expression or null if none found
     */
    public static final <N extends _node, E extends Expression> E first( N _n, String protoExpr ){
        return (E) $expr.of(protoExpr).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param astProtoExpr
     * @return 
     */
    public static final <N extends _node, E extends Expression> E first( N _n, E astProtoExpr ){
        return (E)$expr.of(astProtoExpr).firstIn(_n);
    }  
    
    /**
     * find the first occurring instance of a matching expression within the rootNode
     * @param <N>
     * @param <E>
     * @param _n
     * @param protoExpr
     * @param constraint
     * @return the first matching expression or null if none found
     */
    public static final <N extends _node, E extends Expression> E first( N _n, String protoExpr, Predicate<E> constraint){
        return (E) $expr.of(protoExpr, constraint).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param astProtoExpr
     * @param constraint
     * @return 
     */
    public static final <N extends _node, E extends Expression> E first( N _n, E astProtoExpr, Predicate<E> constraint){
        return (E)$expr.of(astProtoExpr, constraint).firstIn(_n);
    }  
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param protoExpr
     * @return 
     */
    public static final <N extends _node, E extends Expression> List<E> list( N _n, String protoExpr ){
        return (List<E>)$expr.of(protoExpr).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param protoExpr
     * @param constraint
     * @return 
     */
    public static final <N extends _node, E extends Expression> List<E> list( N _n, String protoExpr, Predicate<E> constraint){
        return (List<E>)$expr.of(protoExpr, constraint).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param astProtoExpr
     * @return 
     */
    public static final <N extends _node, E extends Expression> List<E> list( N _n, E astProtoExpr ){
        return $expr.of(astProtoExpr).listIn(_n);
    }    
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param astProtoExpr
     * @param constraint
     * @return 
     */
    public static final <N extends _node, E extends Expression> List<E> list( N _n, E astProtoExpr, Predicate<E> constraint){
        return $expr.of(astProtoExpr, constraint).listIn(_n);
    }  
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param astProtoExpr
     * @return 
     */
    public static final <N extends _node, E extends Expression> List<E> list( N _n, $expr<E> astProtoExpr ){
        return (List<E>)astProtoExpr.listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param protoSource
     * @param protoTarget
     * @return 
     */
    public static final <N extends _node> N replace(N _n, Expression protoSource, Expression protoTarget){
        return (N)$expr.of(protoSource)
            .replaceIn(_n, $expr.of(protoTarget));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param protoSource
     * @param protoTarget
     * @return 
     */
    public static final <N extends _node> N replace( N _n, String protoSource, String protoTarget){
        
        return (N)$expr.of(protoSource)
            .replaceIn(_n, $expr.of(protoTarget));
    }
    
    /**
     * 
     * @param expr
     * @param protoSource
     * @param protoTarget
     * @return 
     */
    public static final Expression replace( Expression expr, String protoSource, String protoTarget){
        Tokens ts = $expr.of(protoSource).deconstruct(expr);
        if( ts != null ){
            Expression t = $expr.of(protoTarget).construct(ts);
            expr.replace(t);
            return t;
        }
        return null;
    }
    
    /**
     * 
     * @param <T>
     * @param proto
     * @return 
     */
    public static <T extends Expression> $expr<T> of( String...proto ){
        return new $expr<>( (T)Expr.of(proto));
    }
    
    /**
     * 
     * @param <T>
     * @param proto
     * @param constraint
     * @return 
     */
    public static <T extends Expression> $expr<T> of( String proto, Predicate<T> constraint ){
        return new $expr<>( (T)Expr.of(proto)).constraint(constraint);
    }
 
    /**
     * 
     * @param <T>
     * @param protoExpr
     * @return 
     */
    public static <T extends Expression> $expr<T> of(T protoExpr ){
        return new $expr<>(protoExpr );
    }

    /**
     * 
     * @param <T>
     * @param protoExpr
     * @param constraint
     * @return 
     */
    public static <T extends Expression> $expr<T> of(T protoExpr, Predicate<T> constraint ){
        return new $expr<>(protoExpr ).constraint(constraint);
    }
        
    /**
     * i.e. "arr[3]"
     * @param proto
     * @return
     */
    public static $expr<ArrayAccessExpr> arrayAccess(String... proto ) {
        return new $expr<>( Expr.arrayAccess(proto) );
    }

    /**
     * i.e."arr[3]"
     * @param proto
     * @param constraint
     * @return
     */
    public static $expr<ArrayAccessExpr> arrayAccess(String proto, Predicate<ArrayAccessExpr> constraint) {
        return new $expr<>( Expr.arrayAccess(proto) ).constraint(constraint);
    }
    
    /**
     * i.e."new Obj[]", "new int[][]"
     * @param proto
     * @return 
     */
    public static $expr<ArrayCreationExpr> arrayCreation( String... proto ) {
        return new $expr<>( Expr.arrayCreation(proto ) );
    }

    /**
     * i.e."new Obj[]", "new int[][]"
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<ArrayCreationExpr> arrayCreation( String proto, Predicate<ArrayCreationExpr> constraint ) {
        return new $expr<>( Expr.arrayCreation(proto ) ).constraint(constraint);
    }
    
    /**
     * i.e."{1,2,3,4,5}"
     * @param proto
     * @return 
     */
    public static $expr<ArrayInitializerExpr> arrayInitializer( String... proto ) {
        return new $expr<>( Expr.arrayInitializer(proto ) );
    }
    
    /**
     * 
     * @param ints
     * @return 
     */
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
    
    /**
     * 
     * @param bools
     * @return 
     */
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
    
    /**
     * 
     * @param chars
     * @return 
     */
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
    
    /**
     * 
     * @param doubles
     * @return 
     */
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
     * 
     * @param floats
     * @return 
     */
    public static $expr<ArrayInitializerExpr> arrayInitializer( float[] floats ) {
        StringBuilder sb = new StringBuilder();
        sb.append("{");
        for(int i=0;i<floats.length; i++){
            if( i > 0){
                sb.append(",");
            }
            sb.append(floats[i]).append("f");
        }
        sb.append("}");
        return arrayInitializer(sb.toString());        
    }
    
    /**
     * i.e."{1,2,3,4,5}"
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<ArrayInitializerExpr> arrayInitializer( String proto, Predicate<ArrayInitializerExpr> constraint) {
        return new $expr<>( Expr.arrayInitializer(proto ) ).constraint(constraint);
    }

    /** 
     * i.e."a = 1", "a = 4" 
     * @param proto
     * @return 
     */
    public static $expr<AssignExpr> assign( String... proto ) {
        return new $expr<>( Expr.assign(proto ) );
    }
    
    /** 
     * i.e."a = 1", "a = 4" 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<AssignExpr> assign( String proto, Predicate<AssignExpr> constraint) {
        return new $expr<>( Expr.assign(proto ) ).constraint(constraint);
    }

    /** 
     * a || b 
     * @param proto
     * @return 
     */
    public static $expr<BinaryExpr> binary( String... proto ) {
        return new $expr<>( Expr.binary(proto ) );
    }
    
    /**
     * a || b 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<BinaryExpr> binary( String proto, Predicate<BinaryExpr> constraint) {
        return new $expr<>( Expr.binary(proto ) ).constraint(constraint);
    }    

    /** 
     *  i.e.true
     * @param b
     * @return  
     */
    public static $expr<BooleanLiteralExpr> of( boolean b ){
        return new $expr<>( Expr.of( b ) );
    }

    /**
     * 
     * @param b
     * @return 
     */
    public static $expr<BooleanLiteralExpr> booleanLiteral( boolean b) {
        return new $expr( Expr.of( b ) );
    }
    
    /**
     * 
     * @param b
     * @param constraint
     * @return 
     */
    public static $expr<BooleanLiteralExpr> booleanLiteral( boolean b, Predicate<BooleanLiteralExpr> constraint) {
        return new $expr( Expr.of( b ) ).constraint(constraint);
    }
    
    /** 
     * "true" / "false" 
     * 
     * @param proto
     * @return 
     */
    public static $expr<BooleanLiteralExpr> booleanLiteral( String... proto ) {
        return new $expr( Expr.booleanLiteral(proto ) );
    }

    /** 
     * "true" / "false" 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<BooleanLiteralExpr> booleanLiteral( String proto, Predicate<CastExpr>constraint ) {
        return new $expr( Expr.booleanLiteral(proto ) ).constraint(constraint);
    }
    
    /** 
     * (String)o 
     * @param proto
     * @return 
     */
    public static $expr<CastExpr> cast( String... proto ) {
        return new $expr( Expr.cast(proto ) );
    }

    /** 
     * (String)o 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<CastExpr> cast( String proto, Predicate<CastExpr> constraint ) {
        return new $expr( Expr.cast(proto ) ).constraint(constraint);
    }
    
    /** 
     * 'c' 
     * @param c
     * @return 
     */
    public static $expr<CharLiteralExpr> of( char c ){
        return new $expr( Expr.charLiteral( c ) );
    }

    /** 
     * 'c' 
     * @param c
     * @param constraint
     * @return 
     */
    public static $expr<CharLiteralExpr> of( char c, Predicate<CharLiteralExpr> constraint){
        return new $expr( Expr.charLiteral( c ) ).constraint(constraint);
    }
    
    /** 
     * 'c' 
     * @param c
     * @return 
     */
    public static $expr<CharLiteralExpr> charLiteral( char c ) {
        return new $expr( Expr.charLiteral( c ) );
    }

    /** 
     * 'c' 
     * @param c
     * @param constraint
     * @return 
     */
    public static $expr<CharLiteralExpr> charLiteral( char c, Predicate<CharLiteralExpr> constraint) {
        return new $expr( Expr.charLiteral( c ) ).constraint(constraint);
    }
    
    /** 
     * 'c' 
     * @param proto
     * @return 
     */
    public static $expr<CharLiteralExpr> charLiteral( String... proto ) {
        return new $expr( Expr.charLiteral(proto ) );
    }

    /** 
     * 'c' 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<CharLiteralExpr> charLiteral( String proto, Predicate<CharLiteralExpr> constraint) {
        return new $expr( Expr.charLiteral(proto ) ).constraint(constraint);
    }
    
    /** 
     * i.e."String.class" 
     * @param proto
     * @return 
     */
    public static $expr<ClassExpr> classExpr( String... proto ) {
        return new $expr( Expr.classExpr(proto ) );
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<ClassExpr> classExpr( String proto, Predicate<ConditionalExpr> constraint) {
        return new $expr( Expr.classExpr(proto ) ).constraint(constraint);
    }

    /** 
     * i.e."(a < b) ? a : b" 
     * @param proto
     * @return 
     */
    public static $expr<ConditionalExpr> conditional( String... proto ) {
        return new $expr( Expr.conditional(proto ) );
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<ConditionalExpr> conditional( String proto, Predicate<ConditionalExpr> constraint) {
        return new $expr( Expr.conditional(proto ) ).constraint(constraint);
    }
    
    /** 
     * 3.14d 
     * @param d
     * @return 
     */
    public static $expr<DoubleLiteralExpr> of( double d ){
        return new $expr( Expr.of( d ) );
    }

    /** 
     * 3.14d 
     * @param d
     * @return 
     */
    public static $expr<DoubleLiteralExpr> doubleLiteral( double d ) {
        return new $expr( Expr.doubleLiteral( d ) );
    }

    /**
     * 
     * @param d
     * @param constraint
     * @return 
     */
    public static $expr<DoubleLiteralExpr> doubleLiteral( double d, Predicate<DoubleLiteralExpr> constraint) {
        return new $expr( Expr.doubleLiteral( d ) ).constraint(constraint);
    }
    
    /**
     * 
     * @param proto
     * @return 
     */
    public static $expr<DoubleLiteralExpr> doubleLiteral( String proto ) {
        return new $expr( Expr.doubleLiteral(proto ) );
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<DoubleLiteralExpr> doubleLiteral( String proto, Predicate<DoubleLiteralExpr> constraint) {
        return new $expr( Expr.doubleLiteral(proto ) ).constraint(constraint);
    }
        
    /**
     * 
     * @param d
     * @return 
     */
    public static $expr<DoubleLiteralExpr> of( float d ){
        return new $expr( Expr.doubleLiteral( d ) );
    }

    /**
     * 
     * @param d
     * @return 
     */
    public static $expr<DoubleLiteralExpr> doubleLiteral( float d ) {
        return new $expr( Expr.of( d ) );
    }

    /**
     * 
     * @param d
     * @param constraint
     * @return 
     */
    public static $expr<DoubleLiteralExpr> doubleLiteral( float d, Predicate<DoubleLiteralExpr> constraint) {
        return new $expr( Expr.of( d ) ).constraint(constraint);
    }
        
    /** 
     * i.e."3.14f" 
     * @param proto
     * @return 
     */
    public static $expr<DoubleLiteralExpr> floatLiteral( String... proto ) {
        return new $expr( Expr.floatLiteral(proto ) );
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<DoubleLiteralExpr> floatLiteral( String proto, Predicate<DoubleLiteralExpr> constraint ) {
        return new $expr( Expr.floatLiteral(proto ) ).constraint(constraint);
    }
    
    /** 
     * i.e.( 3 + 4 ) 
     * @param proto
     * @return 
     */
    public static $expr<EnclosedExpr> enclosedExpr( String... proto ) {
        return new $expr( Expr.enclosedExpr(proto ) );
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<EnclosedExpr> enclosedExpr( String proto, Predicate<EnclosedExpr>constraint ) {
        return new $expr( Expr.enclosedExpr(proto ) ).constraint(constraint);
    }
    
    /** 
     *  i.e."person.NAME"
     * @param proto
     * @return 
     */
    public static $expr<FieldAccessExpr> fieldAccess(String... proto ) {
        return new $expr( Expr.fieldAccess(proto ) );
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<FieldAccessExpr> fieldAccess(String proto, Predicate<FieldAccessExpr> constraint ) {
        return new $expr( Expr.fieldAccess(proto ) ).constraint(constraint);
    }
    
    /** 
     * v instanceof Serializable 
     * @param proto
     * @return 
     */
    public static $expr<InstanceOfExpr> instanceOf(String... proto ) {
        return new $expr( Expr.instanceOf(proto ) );
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<InstanceOfExpr> instanceOf(String proto, Predicate<InstanceOfExpr> constraint ) {
        return new $expr( Expr.instanceOf(proto ) ).constraint(constraint);
    }
    
    /**
     * 
     * @param i
     * @return 
     */
    public static $expr<IntegerLiteralExpr> of(int i) {
        return new $expr( Expr.of( i ) );
    }

    /**
     * 
     * @param i
     * @param constraint
     * @return 
     */
    public static $expr<IntegerLiteralExpr> of(int i, Predicate<IntegerLiteralExpr> constraint) {
        return new $expr( Expr.of( i ) ).constraint(constraint);
    }
    
    /**
     * 
     * @param i
     * @return 
     */
    public static $expr<IntegerLiteralExpr> intLiteral(int i) {
        return new $expr( Expr.of( i ) );
    }

    /**
     * 
     * @param i
     * @param constraint
     * @return 
     */
    public static $expr<IntegerLiteralExpr> intLiteral(int i, Predicate<IntegerLiteralExpr> constraint) {
        return new $expr( Expr.of( i ) ).constraint(constraint);
    }
    
    /**
     * 
     * @param proto
     * @return 
     */
    public static $expr<IntegerLiteralExpr> intLiteral(String... proto ) {
        return new $expr( Expr.intLiteral(proto ) );
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<IntegerLiteralExpr> intLiteral(String proto, Predicate<IntegerLiteralExpr> constraint ) {
        return new $expr( Expr.intLiteral(proto ) ).constraint(constraint);
    }
    
    /** 
     * a-> System.out.println( a )  
     * @param proto
     * @return 
     */
    public static $expr<LambdaExpr> lambda(String... proto ) {
        return new $expr( Expr.lambda(proto ) );
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<LambdaExpr> lambda(String proto , Predicate<LambdaExpr> constraint) {
        return new $expr( Expr.lambda(proto ) ).constraint(constraint);
    }
    
    /**
     * 
     * @param l
     * @return 
     */
    public static $expr<LongLiteralExpr> of(long l) {
        return new $expr( Expr.of( l ) );
    }

    /**
     * 
     * @param l
     * @param constraint
     * @return 
     */
    public static $expr<LongLiteralExpr> of(long l, Predicate<LongLiteralExpr> constraint ) {
        return new $expr( Expr.of( l ) ).constraint(constraint);
    }
    
    /**
     * 
     * @param l
     * @return 
     */
    public static $expr<LongLiteralExpr> longLiteral( long l ) {
        return new $expr( Expr.of( l ) );
    }

    /**
     * 
     * @param l
     * @param constraint
     * @return 
     */
    public static $expr<LongLiteralExpr> longLiteral( long l, Predicate<LongLiteralExpr> constraint ) {
        return new $expr( Expr.of( l ) ).constraint(constraint);
    }
   
    /**
     * 
     * @param proto
     * @return 
     */
    public static $expr<LongLiteralExpr> longLiteral( String... proto ) {
        return new $expr( Expr.longLiteral(proto ) );
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<LongLiteralExpr> longLiteral( String proto, Predicate<LongLiteralExpr> constraint ) {
        return new $expr( Expr.longLiteral(proto ) ).constraint(constraint);
    }

    /** 
     * doMethod(t)
     * @param proto 
     * @return  
     */
    public static $expr<MethodCallExpr> methodCall( String... proto ) {
        return new $expr( Expr.methodCall(proto ) );
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<MethodCallExpr> methodCall( String proto, Predicate<MethodCallExpr> constraint ) {
        return new $expr( Expr.methodCall(proto ) ).constraint(constraint);
    }
    
    /** 
     * i.e."String:toString" 
     * @param proto
     * @return 
     */
    public static $expr<MethodReferenceExpr> methodReference( String... proto ) {
        return new $expr( Expr.methodReference(proto ) );
    }
    
    /** 
     * i.e."String:toString" 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<MethodReferenceExpr> methodReference( String proto, Predicate<MethodReferenceExpr>constraint ) {
        return new $expr( Expr.methodReference(proto ) ).constraint(constraint);
    }
    
    /** 
     *  i.e."null"
     * @return  
     */
    public static $expr<NullLiteralExpr> nullExpr(){
        return new $expr( Expr.nullExpr() );
    }

    /**
     * 
     * @param proto
     * @return 
     */
    public static $expr<NameExpr> name( String... proto ) {
        return new $expr( Expr.name(proto ) );
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<NameExpr> name( String proto, Predicate<NameExpr>constraint) {
        return new $expr( Expr.name(proto ) ).constraint(constraint);
    }

    /** 
     * "new Date();"
     * @param proto
     * @return 
     */
    public static $expr<ObjectCreationExpr> objectCreation(String... proto ) {
        return new $expr( Expr.objectCreation( proto ) );
    }
    
    /** 
     * "new Date();"
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<ObjectCreationExpr> objectCreation(String proto, Predicate<ObjectCreationExpr>constraint ) {
        return new $expr( Expr.objectCreation( proto ) ).constraint(constraint);
    }

    /** 
     * "literal"
     * @param proto
     * @return 
     */
    public static $expr<StringLiteralExpr> stringLiteral( String... proto ) {
        return new $expr( Expr.stringLiteral( proto ) );
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<StringLiteralExpr> stringLiteral( String proto, Predicate<StringLiteralExpr> constraint) {
        return new $expr( Expr.stringLiteral( proto ) ).constraint(constraint);
    }
    
    /** 
     * "super"
     * @return  
     */
    public static $expr<SuperExpr> superExpr(  ) {
        return new $expr( Expr.superExpr( ) );
    }

    /** 
     * "this"
     * @return  
     */
    public static $expr<ThisExpr> thisExpr(  ) {
        return new $expr( Expr.thisExpr( ) );
    }

    /** 
     * i.e."World" in World::greet 
     * @param proto
     * @return 
     */
    public static $expr<TypeExpr> typeExpr(String... proto ) {
        return new $expr( Expr.typeExpr( proto ) );
    }
    
    /** 
     * i.e."World" in World::greet 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<TypeExpr> typeExpr(String proto, Predicate<TypeExpr> constraint ) {
        return new $expr( Expr.typeExpr( proto ) ).constraint(constraint);
    }

    /** 
     * i.e."!true" 
     * @param proto
     * @return 
     */
    public static $expr<UnaryExpr> unary( String... proto ) {
        return new $expr( Expr.unary( proto ) );
    }
   
    /** 
     *  i.e."!true"
     * @param proto 
     * @param constraint 
     * @return  
     */
    public static $expr<UnaryExpr> unary( String proto, Predicate<UnaryExpr>constraint) {
        return new $expr( Expr.unary( proto ) ).constraint(constraint);
    }
    
    /** 
     * "int i = 1"
     * @param proto
     * @return  
     */
    public static $expr<VariableDeclarationExpr> varDecl( String... proto ) {
        return new $expr( Expr.varDecl( proto ) );
    }

    /** 
     * "int i = 1"
     * @param proto 
     * @param constraint 
     * @return  
     */
    public static $expr<VariableDeclarationExpr> varDecl( String proto, Predicate<VariableDeclarationExpr> constraint) {
        return new $expr( Expr.varDecl( proto ) ).constraint(constraint);
    }
    
    public Class<T> expressionClass;
    
    public Stencil exprPattern;
    
    /**
     * Additional Constraint for matching this expression (besides purely being
     * a pattern match)
     * By default, ALWAYS matches
     */
    public Predicate<T> constraint = (t)->true;

    /**
     * 
     * @param expressionProto 
     */
    public $expr(T expressionProto){
        this.expressionClass = (Class<T>)expressionProto.getClass();
        this.exprPattern = Stencil.of(expressionProto.toString() );
    }

    /**
     * 
     * @param expressionClass
     * @param stencil 
     */
    public $expr(Class<T>expressionClass, String stencil ){
        this.expressionClass = expressionClass;
        this.exprPattern = Stencil.of(stencil);
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public $expr constraint( Predicate<T> constraint){
        this.constraint = constraint;
        return this;
    }
    
    @Override
    public T fill(Object...values){
        String str = exprPattern.fill(Translator.DEFAULT_TRANSLATOR, values);
        return (T)Expr.of( str);
    }

    @Override
    public $expr<T> $(String target, String $name ) {
        this.exprPattern = this.exprPattern.$(target, $name);
        return this;
    }

    /**
     * 
     * @param e
     * @param $name
     * @return 
     */
    public $expr<T> $( Expression e, String $name){
        this.exprPattern = this.exprPattern.$(e.toString(), $name);
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

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $expr assign$( Translator translator, Tokens kvs ) {
        this.exprPattern = this.exprPattern.assign$(translator,kvs);
        return this;
    }

    @Override
    public T fill(Translator t, Object...values){
        return (T)Expr.of(exprPattern.fill(t, values));
    }

    @Override
    public T construct( Object...keyValues ){
        return (T)Expr.of(exprPattern.construct( Tokens.of(keyValues)));
    }

    /**
     * 
     * @param _n
     * @return 
     */
    public T construct( _node _n ){
        return (T)$expr.this.construct(_n.componentize());
    }

    @Override
    public T construct( Translator t, Object...keyValues ){
        return (T)Expr.of(exprPattern.construct( t, Tokens.of(keyValues) ));
    }

    @Override
    public T construct( Map<String,Object> tokens ){
        return (T)Expr.of(exprPattern.construct( Translator.DEFAULT_TRANSLATOR, tokens ));
    }

    @Override
    public T construct( Translator t, Map<String,Object> tokens ){
        return (T)Expr.of(exprPattern.construct( t, tokens ));
    }

    /**
     * 
     * @param expression
     * @return 
     */
    public boolean matches( String...expression ){
        return deconstruct( Expr.of(expression)) != null;
    }

    /**
     * 
     * @param expression
     * @return 
     */
    public boolean matches( Expression expression ){
        return deconstruct(expression) != null;
    }

    @Override
    public List<String> list$(){
        return this.exprPattern.list$();
    }

    @Override
    public List<String> list$Normalized(){
        return this.exprPattern.list$Normalized();
    }

    /**
     * 
     * @param expression
     * @return 
     */
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
            return exprPattern.deconstruct( expression.toString() );
        }
        return null;
    }

    /**
     * 
     * @param e
     * @return 
     */
    public Select select( Expression e){
        Tokens ts = this.deconstruct(e);
        if( ts != null){
            return new Select( e, ts );
        }
        return null;
    }

    /**
     * Returns the first Expression that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first Expression that matches (or null if none found)
     */
    public T firstIn( _node _n ){
        Optional<T> f = _n.ast().findFirst(this.expressionClass, s -> this.matches(s) );         
        if( f.isPresent()){
            return f.get();
        }
        return null;
    }

    /**
     * Returns the first Expression that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first Expression that matches (or null if none found)
     */
    public T firstIn( Node astNode ){
        Optional<T> f = astNode.findFirst(this.expressionClass, s -> this.matches(s) );         
        if( f.isPresent()){
            return f.get();
        }
        return null;
    }
    
    @Override
    public List<T> listIn(_node _n ){
        return listIn(_n.ast() );
    }

    @Override
    public List<T> listIn(Node astNode ){
        List<T> typesList = new ArrayList<>();
        astNode.walk(this.expressionClass, t->{
            if( this.matches(t) ){
                typesList.add(t);
            }
        } );
        return typesList;
    }

    @Override
    public <N extends Node> N forIn(N astNode, Consumer<T> expressionActionFn){
        astNode.walk(this.expressionClass, e-> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                expressionActionFn.accept( e);
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forIn(N _n, Consumer<T> expressionActionFn){
        Walk.in(_n, this.expressionClass, e -> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                expressionActionFn.accept( e);
            }
        });
        return _n;
    }

    @Override
    public List<Select<T>> listSelectedIn(Node astNode ){
        List<Select<T>>sts = new ArrayList<>();
        astNode.walk(this.expressionClass, e-> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    @Override
    public List<Select<T>> listSelectedIn(_node _n ){
        List<Select<T>>sts = new ArrayList<>();
        Walk.in(_n, this.expressionClass, e -> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    @Override
    public <N extends Node> N removeIn(N astNode){
        astNode.walk( this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.expression.removeForced();
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N removeIn(N _n){
        Walk.in(_n, this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.expression.removeForced();
            }
        });
        return _n;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param replacement
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, Node replacement ){
        Walk.in(_n, this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.expression.replace( replacement );
            }
        });
        return _n;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param protoReplaceExpr
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, String protoReplaceExpr ){
        return replaceIn(_n, $expr.of(protoReplaceExpr) );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param $repl
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, $expr $repl ){
        Walk.in(_n, this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.expression.replace($repl.construct(sel.tokens));
            }
        });
        return _n;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConsumer
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Consumer<Select> selectConsumer ){
        Walk.in(_n, this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return _n;
    }

    /**
     * 
     * @param <N>
     * @param astNode
     * @param selectConsumer
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Consumer<Select> selectConsumer ){
        astNode.walk(this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return astNode;
    }

    @Override
    public String toString() {
        return "(" + this.expressionClass.getSimpleName() + ") : \"" + this.exprPattern + "\"";
    }

    /**
     * 
     * @param <T> 
     */
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
