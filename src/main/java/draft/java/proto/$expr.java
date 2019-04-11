package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import draft.*;
import draft.java.*;
import draft.java._model._node;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Template for a Java Expression (a com.github.javaparser.ast.expr.Expression)
 *
 * @param <T> the underlying Expression TYPE
 */
public final class $expr <T extends Expression>
    implements Template<T>, $proto<T> {
    
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
     * find the first occurring instance of a matching expression within the rootNode
     * @param <N>
     * @param <E>
     * @param _n
     * @param exprClass the specific Expression Class (i.e. Expr.ASSERT, Ast.RETURN_EXPR)
     * @return the first matching expression or null if none found
     */
    public static final <N extends _node, E extends Expression> E first( N _n, Class<E> exprClass ){
        return (E) new $expr(exprClass, "$any$").firstIn(_n);
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
     * find the first occurring instance of a matching expression within the rootNode
     * @param <N>
     * @param <E>
     * @param _n
     * @param protoExpr
     * @return the first matching expression or null if none found
     */
    public static final <N extends _node, E extends Expression> Select<E> selectFirst( N _n, String protoExpr ){
        return (Select<E>)$expr.of(protoExpr).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param astProtoExpr
     * @return 
     */
    public static final <N extends _node, E extends Expression> Select<E> selectFirst( N _n, E astProtoExpr ){
        return $expr.of(astProtoExpr).selectFirstIn(_n);
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
    public static final <N extends _node, E extends Expression> Select<E> selectFirst( N _n, String protoExpr, Predicate<E> constraint){
        return $expr.of(protoExpr, constraint).selectFirstIn(_n);
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
    public static final <N extends _node, E extends Expression> Select<E> selectFirst( N _n, E astProtoExpr, Predicate<E> constraint){
        return $expr.of(astProtoExpr, constraint).selectFirstIn(_n);
    }

    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param exprClass
     * @param expressionActionFn
     * @return 
     */
    public <N extends _node, E extends Expression> N forEach(N _n, Class<E> exprClass, Consumer<E> expressionActionFn){
        
        return (N) new $expr(exprClass, "$any$").forEachIn(_n, expressionActionFn);
    }
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param exprProto
     * @param expressionActionFn
     * @return 
     */
    public <N extends _node, E extends Expression> N forEach(N _n, E exprProto, Consumer<E> expressionActionFn){
        return $expr.of(exprProto).forEachIn(_n, expressionActionFn);
    }

    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param exprProto
     * @param constraint
     * @param expressionActionFn
     * @return 
     */
    public <N extends _node, E extends Expression> N forEach(N _n, E exprProto, Predicate<E> constraint, Consumer<E> expressionActionFn){
        return $expr.of(exprProto, constraint).forEachIn(_n, expressionActionFn);
    }
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param exprClass
     * @return 
     */
    public static final <N extends _node, E extends Expression> List<E> list( N _n, Class<E> exprClass ){
        return (List<E>)new $expr(exprClass, "$any$").listIn(_n);
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
     * @param <E>
     * @param _n
     * @param protoExpr
     * @return 
     */
    public static final <N extends _node, E extends Expression> List<Select<Expression>> selectList( N _n, String protoExpr ){
        return $expr.of(protoExpr).selectListIn(_n);
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
    public static final <N extends _node, E extends Expression> List<Select<E>> selectList( N _n, String protoExpr, Predicate<E> constraint){
        return $expr.of(protoExpr, constraint).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param astProtoExpr
     * @return 
     */
    public static final <N extends _node, E extends Expression> List<Select<E>> selectList( N _n, E astProtoExpr ){
        return $expr.of(astProtoExpr).selectListIn(_n);
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
    public static final <N extends _node, E extends Expression> List<Select<E>> selectList( N _n, E astProtoExpr, Predicate<E> constraint){
        return $expr.of(astProtoExpr, constraint).selectListIn(_n);
    }  
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param astProtoExpr
     * @return 
     */
    public static final <N extends _node, E extends Expression> List<Select<E>> selectList( N _n, $expr<E> astProtoExpr ){
        return astProtoExpr.selectListIn(_n);
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
     * @param astExpr
     * @param protoSource
     * @param protoTarget
     * @return 
     */
    public static final Expression replace( Expression astExpr, String protoSource, String protoTarget){
        $args ts = $expr.of(protoSource).deconstruct(astExpr);
        if( ts != null ){
            Expression t = $expr.of(protoTarget).construct(ts);
            astExpr.replace(t);
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
     * @param constraint
     * @return
     */
    public static $expr<ArrayAccessExpr> arrayAccess(Predicate<ArrayAccessExpr> constraint) {
        return new $expr<>( Expr.arrayAccess("a[0]") )
                .$(Expr.of("a[0]"), "any").constraint(constraint);
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
     * ANY array Access
     * i.e."arr[3]"
     * @return
     */
    public static $expr<ArrayAccessExpr> arrayAccessAny( ) {
        return new $expr<>( Expr.arrayAccess("a[0]") )
                .$(Expr.of("a[0]"), "any");
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
     * @param constraint
     * @return 
     */
    public static $expr<ArrayCreationExpr> arrayCreation(Predicate<ArrayCreationExpr> constraint ) {
        return new $expr<>( Expr.arrayCreation("new int[]")).$(Expr.of("new int[]"), "any").constraint(constraint);
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
     * i.e."new Obj[]", "new int[][]"
     * @return 
     */
    public static $expr<ArrayCreationExpr> arrayCreationAny( ) {
        return new $expr<>( Expr.arrayCreation("new int[]")).$(Expr.of("new int[]"), "any");
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
     * @param constraint
     * @return 
     */
    public static $expr<ArrayInitializerExpr> arrayInitializer( Predicate<ArrayInitializerExpr> constraint) {
        return new $expr<>( Expr.arrayInitializer("{1}") ).$(Expr.of("{1}"), "any").constraint(constraint);
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
     * Any array initializer
     * @return 
     */
    public static $expr<ArrayInitializerExpr> arrayInitializerAny() {
        return new $expr<>( Expr.arrayInitializer("{1}") ).$(Expr.of("{1}"), "any");
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
     * @param constraint
     * @return 
     */
    public static $expr<AssignExpr> assign(Predicate<AssignExpr> constraint) {
        return new $expr<>( Expr.assign("a=1") ).$(Expr.of("a=1"), "any").constraint(constraint);
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
     * i.e."a = 1", "a = 4" 
     * @return 
     */
    public static $expr<AssignExpr> assignAny( ) {
        return new $expr<>( Expr.assign("a=1") ).$(Expr.of("a=1"), "any");
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
     * a || b 
     * @param constraint
     * @return 
     */
    public static $expr<BinaryExpr> binary(Predicate<BinaryExpr> constraint) {
        return new $expr<>( Expr.binary("a || b" ) ).$(Expr.binary("a || b"), "any").constraint(constraint);
    }  
    
    /**
     * a || b    
     * @return 
     */
    public static $expr<BinaryExpr> binaryAny( ) {
        return new $expr<>( Expr.binary("a || b" ) ).$(Expr.binary("a || b"), "any");
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
    public static $expr<BooleanLiteralExpr> booleanLiteral( boolean b ) {
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
     * @param constraint
     * @return 
     */
    public static $expr<BooleanLiteralExpr> booleanLiteral( Predicate<BooleanLiteralExpr>constraint ) {
        return new $expr( Expr.booleanLiteral("true") ).$("true", "any").constraint(constraint);
    }
    
    /** 
     * "true" / "false" 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<BooleanLiteralExpr> booleanLiteral( String proto, Predicate<BooleanLiteralExpr>constraint ) {
        return new $expr( Expr.booleanLiteral(proto ) ).constraint(constraint);
    }
    
    /** 
     * "true" / "false" 
     * @return 
     */
    public static $expr<BooleanLiteralExpr> booleanLiteralAny( ) {
        return new $expr( Expr.booleanLiteral("true") ).$("true", "any");
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
     * @param constraint
     * @return 
     */
    public static $expr<CastExpr> cast( Predicate<CastExpr> constraint ) {
        return new $expr( Expr.cast("(String)o")).$(Expr.of("String(o)"),"any").constraint(constraint);
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
     * (String)o 
     * @return 
     */
    public static $expr<CastExpr> castAny( ) {
        return new $expr( Expr.cast("(String)o")).$(Expr.of("String(o)"),"any");
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
     * @param constraint
     * @return 
     */
    public static $expr<CharLiteralExpr> charLiteral( Predicate<CharLiteralExpr> constraint) {
        return new $expr( Expr.charLiteral('a') ).$("'a'", "any").constraint(constraint);
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
     * ANY char literal
     * 'c' 
     * @return 
     */
    public static $expr<CharLiteralExpr> charLiteralAny(  ) {
        return new $expr( Expr.charLiteral('a') ).$("'a'", "any");
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
     * Map.class
     * @param constraint
     * @return 
     */
    public static $expr<ClassExpr> classExpr( Predicate<ConditionalExpr> constraint) {
        return new $expr( Expr.classExpr("a.class") )
            .$("a.class", "any").constraint(constraint);
    }
    
    /**
     * String.class
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<ClassExpr> classExpr( String proto, Predicate<ConditionalExpr> constraint) {
        return new $expr( Expr.classExpr(proto ) ).constraint(constraint);
    }

    /**
     * String.class
     * @return 
     */
    public static $expr<ClassExpr> classExprAny() {
        return new $expr( Expr.classExpr("a.class") )
            .$("a.class", "any");
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
     * 
     * @param constraint
     * @return 
     */
    public static $expr<ConditionalExpr> conditional(Predicate<ConditionalExpr> constraint) {
        return new $expr( Expr.conditional("(a==1) ? 1 : 2" ) )
                .$(Expr.conditional("(a==1) ? 1 : 2"), "any")
                .constraint(constraint);
    }    
    
    /**
     * Any conditional 
     * @return 
     */
    public static $expr<ConditionalExpr> conditionalAny() {
        return new $expr( Expr.conditional("(a==1) ? 1 : 2" ) )
                .$(Expr.conditional("(a==1) ? 1 : 2"), "any");
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
     * 
     * @param constraint
     * @return 
     */
    public static $expr<DoubleLiteralExpr> doubleLiteral( Predicate<DoubleLiteralExpr> constraint) {
        return new $expr( Expr.of( 1.0d ) ).$("1.0d", "any").constraint(constraint);
    }
        
    /**
     * 10.1d
     * @return 
     */
    public static $expr<DoubleLiteralExpr> doubleLiteralAny( ) {
        return new $expr( Expr.of( 1.0d ) ).$("1.0d", "any");
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
     * 
     * @param constraint
     * @return 
     */
    public static $expr<DoubleLiteralExpr> floatLiteral(Predicate<DoubleLiteralExpr> constraint ) {
        return new $expr( Expr.of(1.0f) ).$(Expr.of(1.0f), "any").constraint(constraint);
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static $expr<DoubleLiteralExpr> floatLiteralAny(Predicate<DoubleLiteralExpr> constraint ) {
        return new $expr( Expr.of(1.0f) ).$(Expr.of(1.0f), "any");
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
     * i.e.( 3 + 4 ) 
     * @param constraint
     * @return 
     */
    public static $expr<EnclosedExpr> enclosedExpr(Predicate<EnclosedExpr>constraint ) {
        return new $expr( Expr.enclosedExpr("(a)" ) ).$("(a)", "any").constraint(constraint);
    }
    
    /**
     * i.e.( 3 + 4 ) 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<EnclosedExpr> enclosedExpr( String proto, Predicate<EnclosedExpr>constraint ) {
        return new $expr( Expr.enclosedExpr(proto ) ).constraint(constraint);
    }
    
    /**
     * i.e.( 3 + 4 )    
     * @return 
     */
    public static $expr<EnclosedExpr> enclosedExprAny( ) {
        return new $expr( Expr.enclosedExpr("(a)" ) ).$("(a)", "any");
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
     * @param constraint
     * @return 
     */
    public static $expr<FieldAccessExpr> fieldAccess(Predicate<FieldAccessExpr> constraint ) {
        return new $expr( Expr.fieldAccess("a.B") )
                .$(Expr.fieldAccess("a.B"), "any").constraint(constraint);
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
     * System.out
     * 
     * @return 
     */
    public static $expr<FieldAccessExpr> fieldAccessAny( ) {
        return new $expr( Expr.fieldAccess("a.B") )
                .$(Expr.fieldAccess("a.B"), "any");
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
     * @param constraint
     * @return 
     */
    public static $expr<InstanceOfExpr> instanceOf(Predicate<InstanceOfExpr> constraint ) {
        return new $expr( Expr.instanceOf( "a instanceof b" ) ).$("a instanceof b", "any")
                .constraint(constraint);
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
     * @return 
     */
    public static $expr<InstanceOfExpr> instanceOfAny() {
        return new $expr( Expr.instanceOf( "a instanceof b" ) ).$("a instanceof b", "any");
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
     * @param constraint
     * @return 
     */
    public static $expr<IntegerLiteralExpr> intLiteral(Predicate<IntegerLiteralExpr> constraint) {
        return new $expr( Expr.of( 1 ) ).$("1", "any").constraint(constraint);
    }
    
    public static $expr<IntegerLiteralExpr> intLiteralAny( ) {
        return new $expr( Expr.of( 1 ) ).$("1", "any");
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
     * a-> System.out.println( a )  
     * @param constraint
     * @return 
     */
    public static $expr<LambdaExpr> lambda(Predicate<LambdaExpr> constraint) {
        return new $expr( Expr.lambda("a-> true" ) ).$(Expr.lambda("a->true"), "any").constraint(constraint);
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
     * a-> System.out.println( a )  
     * @return 
     */
    public static $expr<LambdaExpr> lambdaAny( ) {
        return new $expr( Expr.lambda("a-> true" ) ).$(Expr.lambda("a->true"), "any");
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
     * @param constraint
     * @return 
     */
    public static $expr<LongLiteralExpr> longLiteral( Predicate<LongLiteralExpr> constraint ) {
        return new $expr( Expr.longLiteral(1L)).$(Expr.longLiteral(1L), "any")
                .constraint(constraint) ;
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
     * Any long literal
     * @return 
     */
    public static $expr<LongLiteralExpr> longLiteralAny( ) {
        return new $expr( Expr.longLiteral(1L)).$(Expr.longLiteral(1L), "any");
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
     * 
     * @param constraint
     * @return 
     */
    public static $expr<MethodCallExpr> methodCall( Predicate<MethodCallExpr> constraint ) {
        return new $expr( Expr.methodCall("a()" )).$(Expr.of("a()"), "any").constraint(constraint);
    }
    
    /**
     * Builds & returns a MethodCallExpr based on the FIRST method call inside
     * the body of the lambda passed in
     * 
     * (SOURCE PASSING)
     * 
     * @param lambdaWithMethodCallInSource a lambda expression containing the 
     * source with a METHOD CALL that will be converted into a MethodCallExpr
     * Ast node
     * @return the MethodCallExpr Ast Node representing the first method call
     * in the lambda body
     */
    public static MethodCallExpr methodCall( Expr.Command lambdaWithMethodCallInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return astLambda.getBody().findFirst(MethodCallExpr.class).get();
    }

    /**
     * Builds & returns a MethodCallExpr based on the FIRST method call inside
     * the body of the lambda passed in
     * 
     * (SOURCE PASSING)
     * 
     * @param lambdaWithMethodCallInSource a lambda expression containing the 
     * source with a METHOD CALL that will be converted into a MethodCallExpr
     * Ast node
     * @return the MethodCallExpr Ast Node representing the first method call
     * in the lambda body
     */
    public static $expr<MethodCallExpr> methodCall( Consumer<? extends Object> lambdaWithMethodCallInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return $expr.of(astLambda.getBody().findFirst(MethodCallExpr.class).get());
    }    
    
    /**
     * Builds & returns a MethodCallExpr based on the FIRST method call inside
     * the body of the lambda passed in
     * 
     * (SOURCE PASSING)
     * 
     * @param lambdaWithMethodCallInSource a lambda expression containing the 
     * source with a METHOD CALL that will be converted into a MethodCallExpr
     * Ast node
     * @return the MethodCallExpr Ast Node representing the first method call
     * in the lambda body
     */
    public static $expr<MethodCallExpr> methodCall( BiConsumer<? extends Object,? extends Object> lambdaWithMethodCallInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return $expr.of(astLambda.getBody().findFirst(MethodCallExpr.class).get());
    }  
    
    /**
     * Builds & returns a MethodCallExpr based on the FIRST method call inside
     * the body of the lambda passed in
     * 
     * (SOURCE PASSING)
     * 
     * @param lambdaWithMethodCallInSource a lambda expression containing the 
     * source with a METHOD CALL that will be converted into a MethodCallExpr
     * Ast node
     * @return the MethodCallExpr Ast Node representing the first method call
     * in the lambda body
     */
    public static $expr<MethodCallExpr> methodCall( Expr.TriConsumer<? extends Object,? extends Object, ? extends Object> lambdaWithMethodCallInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return $expr.of(astLambda.getBody().findFirst(MethodCallExpr.class).get());
    }
    
    /**
     * Builds & returns a MethodCallExpr based on the FIRST method call inside
     * the body of the lambda passed in
     * 
     * (SOURCE PASSING)
     * 
     * @param lambdaWithMethodCallInSource a lambda expression containing the 
     * source with a METHOD CALL that will be converted into a MethodCallExpr
     * Ast node
     * @return the MethodCallExpr Ast Node representing the first method call
     * in the lambda body
     */
    public static $expr<MethodCallExpr> methodCall( Expr.QuadConsumer<? extends Object,? extends Object, ? extends Object, ? extends Object> lambdaWithMethodCallInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return $expr.of(astLambda.getBody().findFirst(MethodCallExpr.class).get());
    }
    
    /**
     * ANY method call
     * @return 
     */
    public static $expr<MethodCallExpr> methodCallAny( ) {
        return new $expr( Expr.methodCall("a()" )).$(Expr.of("a()"), "any");
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
     * 
     * @param constraint
     * @return 
     */
    public static $expr<MethodReferenceExpr> methodReference( Predicate<MethodReferenceExpr> constraint) {
        return new $expr( Expr.methodReference("A:b")).$("A:b", "any").constraint(constraint);
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
     * 
     * @return 
     */
    public static $expr<MethodReferenceExpr> methodReferenceAny() {
        return new $expr( Expr.methodReference("A:b")).$("A:b", "any");
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
     * @param constraint
     * @return 
     */
    public static $expr<NameExpr> name( Predicate<NameExpr>constraint) {
        return new $expr( Expr.name("name" ) ).$("name", "any").constraint(constraint);
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
     * 
     * @return 
     */
    public static $expr<NameExpr> nameAny() {
        return new $expr( Expr.name("name" ) ).$("name", "any");
    }
    
    /** 
     * "new Date()"
     * @param proto
     * @return 
     */
    public static $expr<ObjectCreationExpr> objectCreation(String... proto ) {
        return new $expr( Expr.objectCreation( proto ) );
    }
    
    /** 
     * "new Date()"
     * @param constraint
     * @return 
     */
    public static $expr<ObjectCreationExpr> objectCreation(Predicate<ObjectCreationExpr>constraint ) {
        return new $expr( Expr.objectCreation( "new a()" ) ).$("new a()", "any").constraint(constraint);
    }
    
    /** 
     * "new Date()"
     * @param proto
     * @param constraint
     * @return 
     */
    public static $expr<ObjectCreationExpr> objectCreation(String proto, Predicate<ObjectCreationExpr>constraint ) {
        return new $expr( Expr.objectCreation( proto ) ).constraint(constraint);
    }

    /** 
     * "new Date()"
     * @return 
     */
    public static $expr<ObjectCreationExpr> objectCreationAny() {
        return new $expr( Expr.objectCreation( "new a()" ) ).$("new a()", "any");
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
     * @param constraint
     * @return 
     */
    public static $expr<StringLiteralExpr> stringLiteral(Predicate<StringLiteralExpr> constraint) {
        return new $expr( Expr.stringLiteral( "\"a\"" ) ).$("\"a\"", "any").constraint(constraint);
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
     * 
     * @return 
     */
    public static $expr<StringLiteralExpr> stringLiteralAny( ) {
        return new $expr( Expr.stringLiteral( "\"a\"" ) ).$("\"a\"", "any");
    }
    
    /** 
     * "super"
     * @return  
     */
    public static $expr<SuperExpr> superExpr(  ) {
        return new $expr( Expr.superExpr( ) );
    }

    /**
     * 
     * @param superExpr
     * @return 
     */
    public static $expr<SuperExpr> superExpr(String...superExpr ){
        return new $expr(Expr.superExpr(superExpr));
    }
    
    /**
     * 
     * @param superExpr
     * @param constraint
     * @return 
     */
    public static $expr<SuperExpr> superExpr(String superExpr, Predicate<SuperExpr> constraint ){
        return new $expr(Expr.superExpr(superExpr)).constraint(constraint);
    }
    
    /**
     * 
     * @param se
     * @return 
     */
    public static $expr<SuperExpr> superExpr(SuperExpr se){
        return new $expr(se);
    }
    
    /**
     * 
     * @param superExpr
     * @param constraint
     * @return 
     */
    public static $expr<SuperExpr> superExpr(SuperExpr superExpr, Predicate<SuperExpr> constraint ){
        return new $expr(superExpr).constraint(constraint);
    }
    
    /**
     * ANY super expression 
     * 
     * @return 
     */
    public static $expr<SuperExpr> superExprAny(){
        return new $expr(Expr.superExpr()).$("super", "any");
    }
    
    /** 
     * "this"
     * @return  
     */
    public static $expr<ThisExpr> thisExpr(  ) {
        return new $expr( Expr.thisExpr( ) );
    }

    /**
     * 
     * @param te
     * @return 
     */
    public static $expr<ThisExpr> thisExpr(String... te){
        return new $expr(Expr.thisExpr(te) );
    }
    
    /**
     * 
     * @param te
     * @param constraint
     * @return 
     */
    public static $expr<ThisExpr> thisExpr(String te, Predicate<ThisExpr> constraint){
        return new $expr(Expr.thisExpr(te) ).constraint(constraint);
    }
    
    /**
     * 
     * @param te
     * @return 
     */
    public static $expr<ThisExpr> thisExpr(ThisExpr te){
        return new $expr(te);
    }
    
    /**
     * 
     * @param te
     * @param constraint
     * @return 
     */
    public static $expr<ThisExpr> thisExpr(ThisExpr te, Predicate<ThisExpr> constraint){
        return new $expr(te ).constraint(constraint);
    }

    /**
     * ANY this expression
     * @return 
     */
    public static $expr<ThisExpr> thisExprAny( ){
        return new $expr(Expr.thisExpr()).$("this", "any");
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
     * @param constraint
     * @return 
     */
    public static $expr<TypeExpr> typeExpr(Predicate<TypeExpr> constraint ) {
        return new $expr( Expr.typeExpr( "a" ) ).$("a", "any").constraint(constraint);
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
     * i.e."World" in World::greet 
     * @return 
     */
    public static $expr<TypeExpr> typeExprAny( ) {
        return new $expr( Expr.typeExpr( "a" ) ).$("a", "any");
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
     * @param constraint 
     * @return  
     */
    public static $expr<UnaryExpr> unary( Predicate<UnaryExpr>constraint) {
        return new $expr( Expr.unary( "!true" ) ).$("!true", "any").constraint(constraint);
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
     *  i.e."!true"
     * @return  
     */
    public static $expr<UnaryExpr> unaryAny() {
        return new $expr( Expr.unary( "!true" ) ).$("!true", "any");
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
     * @param constraint 
     * @return  
     */
    public static $expr<VariableDeclarationExpr> varDecl( Predicate<VariableDeclarationExpr> constraint) {
        return new $expr( Expr.varDecl( "int i=1") ).$(Expr.of("int i=1"), "any").constraint(constraint);
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
    
    /** 
     * "int i = 1"
     * @return  
     */
    public static $expr<VariableDeclarationExpr> varDeclAny( ) {
        return new $expr( Expr.varDecl( "int i=1") ).$(Expr.of("int i=1"), "any");
    }
    
    
    public Class<T> expressionClass;
    
    public Stencil exprPattern;
    
    /**
     * Additional Constraint for matching this expression (besides purely being
     * a pattern match & the correct expressionClass)
     * By default, ALWAYS matches
     */
    public Predicate<T> constraint = (t)->true;

    /**
     * 
     * @param astExpressionProto 
     */
    public $expr(T astExpressionProto){
        this.expressionClass = (Class<T>)astExpressionProto.getClass();
        this.exprPattern = Stencil.of(astExpressionProto.toString() );
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
     * @param astExpr
     * @param $name
     * @return 
     */
    public $expr<T> $( Expression astExpr, String $name){
        this.exprPattern = this.exprPattern.$(astExpr.toString(), $name);
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

    /**
     * 
     * @param _n
     * @return 
     */
    public T construct( _node _n ){
        return (T)$expr.this.construct(_n.deconstruct());
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
     * @param astExpr
     * @return 
     */
    public boolean matches( Expression astExpr ){
        return deconstruct(astExpr) != null;
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
    public $args deconstruct( String... expression ){
        return deconstruct( Expr.of(expression) );
    }
    
    /**
     * Deconstruct the expression into tokens, or return null if the statement doesnt match
     *
     * @param astExpr expression
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public $args deconstruct( Expression astExpr ){
        if( expressionClass.isAssignableFrom(astExpr.getClass()) 
                && constraint.test((T)astExpr)){
            //slight modification..
            if( astExpr instanceof LiteralStringValueExpr ) {
                //there is an issue here the lowercase and uppercase Expressions 1.23d =/= 1.23D (which they are equivalent
                //need to handle postfixes 1.2f, 2.3d, 1000l
                //need to handle postfixes 1.2F, 2.3D, 1000L
            }
            if( astExpr instanceof DoubleLiteralExpr ){
                DoubleLiteralExpr dle = (DoubleLiteralExpr)astExpr;
            }
            Tokens ts = exprPattern.deconstruct(astExpr.toString(Ast.PRINT_NO_COMMENTS) );
            if( ts != null ){
                return $args.of(ts);
            }
        }
        return null;
    }

    /**
     * 
     * @param astExpr
     * @return 
     */
    public Select select( Expression astExpr){
        $args ts = this.deconstruct(astExpr);
        if( ts != null){
            return new Select( astExpr, ts );
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
    
    /**
     * Returns the first Expression that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first Expression that matches (or null if none found)
     */
    public Select<T> selectFirstIn( _node _n ){
        Optional<T> f = _n.ast().findFirst(this.expressionClass, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }

    /**
     * Returns the first Expression that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first Expression that matches (or null if none found)
     */
    public Select<T> selectFirstIn( Node astNode ){
        Optional<T> f = astNode.findFirst(this.expressionClass, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
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
    public <N extends Node> N forEachIn(N astNode, Consumer<T> expressionActionFn){
        astNode.walk(this.expressionClass, e-> {
            $args tokens = deconstruct( e );
            if( tokens != null ){
                expressionActionFn.accept( e);
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<T> expressionActionFn){
        Walk.in(_n, this.expressionClass, e -> {
            $args tokens = deconstruct( e );
            if( tokens != null ){
                expressionActionFn.accept( e);
            }
        });
        return _n;
    }

    @Override
    public List<Select<T>> selectListIn(Node astNode ){
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
    public List<Select<T>> selectListIn(_node _n ){
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
                sel.astExpression.removeForced();
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N removeIn(N _n){
        Walk.in(_n, this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astExpression.removeForced();
            }
        });
        return _n;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param astExprReplace
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, Node astExprReplace ){
        Walk.in(_n, this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astExpression.replace(astExprReplace );
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
                Expression replaceNode = (Expression)$repl.construct( sel.args.asTokens() );
                //System.out.println(replaceNode);
                //System.out.println(replaceNode.getClass());
                sel.astExpression.replace( replaceNode );
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
     * A Matched Selection result returned from matching a prototype $expr
     * inside of some (Ast)Node or (_java)_node
     * @param <T> expression type
     */
    public static class Select<T extends Expression> implements $proto.selected<T>,
            $proto.selectedAstNode<T> {
        
        public final T astExpression;
        public final $args args;

        public Select( T astExpr, $args tokens){
            this.astExpression = astExpr;
            this.args = tokens;
        }
        
        @Override
        public $args getArgs(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$expr.Select{"+ System.lineSeparator()+
                Text.indent(astExpression.toString() )+ System.lineSeparator()+
                Text.indent("$args : " + args) + System.lineSeparator()+
                "}";
        }

        @Override
        public T ast() {
            return this.astExpression;
        }
    }
}
