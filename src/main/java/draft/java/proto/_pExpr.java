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
public final class _pExpr <T extends Expression>
    implements Template<T>, _pQuery<T> {
    
    /**
     * find the first occurring instance of a matching expression within the rootNode
     * @param <N>
     * @param <E>
     * @param _n
     * @param protoExpr
     * @return the first matching expression or null if none found
     */
    public static final <N extends _node, E extends Expression> E first( N _n, String protoExpr ){
        return (E) _pExpr.of(protoExpr).firstIn(_n);
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
        return (E) new _pExpr(exprClass, "$any$").firstIn(_n);
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
        return (E)_pExpr.of(astProtoExpr).firstIn(_n);
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
        return (E) _pExpr.of(protoExpr, constraint).firstIn(_n);
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
        return (E)_pExpr.of(astProtoExpr, constraint).firstIn(_n);
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
        return (Select<E>)_pExpr.of(protoExpr).selectFirstIn(_n);
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
        return _pExpr.of(astProtoExpr).selectFirstIn(_n);
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
        return _pExpr.of(protoExpr, constraint).selectFirstIn(_n);
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
        return _pExpr.of(astProtoExpr, constraint).selectFirstIn(_n);
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
        
        return (N) new _pExpr(exprClass, "$any$").forEachIn(_n, expressionActionFn);
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
        return _pExpr.of(exprProto).forEachIn(_n, expressionActionFn);
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
        return _pExpr.of(exprProto, constraint).forEachIn(_n, expressionActionFn);
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
        return (List<E>)new _pExpr(exprClass, "$any$").listIn(_n);
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
        return (List<E>)_pExpr.of(protoExpr).listIn(_n);
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
        return (List<E>)_pExpr.of(protoExpr, constraint).listIn(_n);
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
        return _pExpr.of(astProtoExpr).listIn(_n);
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
        return _pExpr.of(astProtoExpr, constraint).listIn(_n);
    }  
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param astProtoExpr
     * @return 
     */
    public static final <N extends _node, E extends Expression> List<E> list( N _n, _pExpr<E> astProtoExpr ){
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
        return _pExpr.of(protoExpr).selectListIn(_n);
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
        return _pExpr.of(protoExpr, constraint).selectListIn(_n);
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
        return _pExpr.of(astProtoExpr).selectListIn(_n);
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
        return _pExpr.of(astProtoExpr, constraint).selectListIn(_n);
    }  
    
    /**
     * 
     * @param <N>
     * @param <E>
     * @param _n
     * @param astProtoExpr
     * @return 
     */
    public static final <N extends _node, E extends Expression> List<Select<E>> selectList( N _n, _pExpr<E> astProtoExpr ){
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
        return (N)_pExpr.of(protoSource)
            .replaceIn(_n, _pExpr.of(protoTarget));
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
        
        return (N)_pExpr.of(protoSource)
            .replaceIn(_n, _pExpr.of(protoTarget));
    }
    
    /**
     * 
     * @param astExpr
     * @param protoSource
     * @param protoTarget
     * @return 
     */
    public static final Expression replace( Expression astExpr, String protoSource, String protoTarget){
        args ts = _pExpr.of(protoSource).deconstruct(astExpr);
        if( ts != null ){
            Expression t = _pExpr.of(protoTarget).construct(ts);
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
    public static <T extends Expression> _pExpr<T> of( String...proto ){
        return new _pExpr<>( (T)Expr.of(proto));
    }
    
    /**
     * 
     * @param <T>
     * @param proto
     * @param constraint
     * @return 
     */
    public static <T extends Expression> _pExpr<T> of( String proto, Predicate<T> constraint ){
        return new _pExpr<>( (T)Expr.of(proto)).constraint(constraint);
    }
    
    /**
     * 
     * @param <T>
     * @param protoExpr
     * @return 
     */
    public static <T extends Expression> _pExpr<T> of(T protoExpr ){
        return new _pExpr<>(protoExpr );
    }

    /**
     * 
     * @param <T>
     * @param protoExpr
     * @param constraint
     * @return 
     */
    public static <T extends Expression> _pExpr<T> of(T protoExpr, Predicate<T> constraint ){
        return new _pExpr<>(protoExpr ).constraint(constraint);
    }
     
    
    /**
     * i.e. "arr[3]"
     * @param proto
     * @return
     */
    public static _pExpr<ArrayAccessExpr> arrayAccess(String... proto ) {
        return new _pExpr<>( Expr.arrayAccess(proto) );
    }
    
    /**
     * i.e."arr[3]"
     * @param constraint
     * @return
     */
    public static _pExpr<ArrayAccessExpr> arrayAccess(Predicate<ArrayAccessExpr> constraint) {
        return new _pExpr<>( Expr.arrayAccess("a[0]") )
                .$(Expr.of("a[0]"), "any").constraint(constraint);
    }    

    /**
     * i.e."arr[3]"
     * @param proto
     * @param constraint
     * @return
     */
    public static _pExpr<ArrayAccessExpr> arrayAccess(String proto, Predicate<ArrayAccessExpr> constraint) {
        return new _pExpr<>( Expr.arrayAccess(proto) ).constraint(constraint);
    }
    
    /**
     * ANY array Access
     * i.e."arr[3]"
     * @return
     */
    public static _pExpr<ArrayAccessExpr> arrayAccessAny( ) {
        return new _pExpr<>( Expr.arrayAccess("a[0]") )
                .$(Expr.of("a[0]"), "any");
    }
    
    /**
     * i.e."new Obj[]", "new int[][]"
     * @param proto
     * @return 
     */
    public static _pExpr<ArrayCreationExpr> arrayCreation( String... proto ) {
        return new _pExpr<>( Expr.arrayCreation(proto ) );
    }
    
    /**
     * i.e."new Obj[]", "new int[][]"
     * @param constraint
     * @return 
     */
    public static _pExpr<ArrayCreationExpr> arrayCreation(Predicate<ArrayCreationExpr> constraint ) {
        return new _pExpr<>( Expr.arrayCreation("new int[]")).$(Expr.of("new int[]"), "any").constraint(constraint);
    }
    
    /**
     * i.e."new Obj[]", "new int[][]"
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<ArrayCreationExpr> arrayCreation( String proto, Predicate<ArrayCreationExpr> constraint ) {
        return new _pExpr<>( Expr.arrayCreation(proto ) ).constraint(constraint);
    }

    /**
     * i.e."new Obj[]", "new int[][]"
     * @return 
     */
    public static _pExpr<ArrayCreationExpr> arrayCreationAny( ) {
        return new _pExpr<>( Expr.arrayCreation("new int[]")).$(Expr.of("new int[]"), "any");
    }
    
    /**
     * i.e."{1,2,3,4,5}"
     * @param proto
     * @return 
     */
    public static _pExpr<ArrayInitializerExpr> arrayInitializer( String... proto ) {
        return new _pExpr<>( Expr.arrayInitializer(proto ) );
    }
    
    /**
     * 
     * @param ints
     * @return 
     */
    public static _pExpr<ArrayInitializerExpr> arrayInitializer( int[] ints ) {
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
    public static _pExpr<ArrayInitializerExpr> arrayInitializer( boolean[] bools ) {
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
    public static _pExpr<ArrayInitializerExpr> arrayInitializer( char[] chars ) {
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
    public static _pExpr<ArrayInitializerExpr> arrayInitializer( double[] doubles ) {
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
    public static _pExpr<ArrayInitializerExpr> arrayInitializer( float[] floats ) {
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
    public static _pExpr<ArrayInitializerExpr> arrayInitializer( Predicate<ArrayInitializerExpr> constraint) {
        return new _pExpr<>( Expr.arrayInitializer("{1}") ).$(Expr.of("{1}"), "any").constraint(constraint);
    }
    
    /**
     * i.e."{1,2,3,4,5}"
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<ArrayInitializerExpr> arrayInitializer( String proto, Predicate<ArrayInitializerExpr> constraint) {
        return new _pExpr<>( Expr.arrayInitializer(proto ) ).constraint(constraint);
    }
    
    /**
     * Any array initializer
     * @return 
     */
    public static _pExpr<ArrayInitializerExpr> arrayInitializerAny() {
        return new _pExpr<>( Expr.arrayInitializer("{1}") ).$(Expr.of("{1}"), "any");
    }
    

    /** 
     * i.e."a = 1", "a = 4" 
     * @param proto
     * @return 
     */
    public static _pExpr<AssignExpr> assign( String... proto ) {
        return new _pExpr<>( Expr.assign(proto ) );
    }
    
    /** 
     * i.e."a = 1", "a = 4" 
     * @param constraint
     * @return 
     */
    public static _pExpr<AssignExpr> assign(Predicate<AssignExpr> constraint) {
        return new _pExpr<>( Expr.assign("a=1") ).$(Expr.of("a=1"), "any").constraint(constraint);
    }
    
    /** 
     * i.e."a = 1", "a = 4" 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<AssignExpr> assign( String proto, Predicate<AssignExpr> constraint) {
        return new _pExpr<>( Expr.assign(proto ) ).constraint(constraint);
    }
    
    /** 
     * i.e."a = 1", "a = 4" 
     * @return 
     */
    public static _pExpr<AssignExpr> assignAny( ) {
        return new _pExpr<>( Expr.assign("a=1") ).$(Expr.of("a=1"), "any");
    }
    

    /** 
     * a || b 
     * @param proto
     * @return 
     */
    public static _pExpr<BinaryExpr> binary( String... proto ) {
        return new _pExpr<>( Expr.binary(proto ) );
    }
    
    /**
     * a || b 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<BinaryExpr> binary( String proto, Predicate<BinaryExpr> constraint) {
        return new _pExpr<>( Expr.binary(proto ) ).constraint(constraint);
    }    

    /**
     * a || b 
     * @param constraint
     * @return 
     */
    public static _pExpr<BinaryExpr> binary(Predicate<BinaryExpr> constraint) {
        return new _pExpr<>( Expr.binary("a || b" ) ).$(Expr.binary("a || b"), "any").constraint(constraint);
    }  
    
    /**
     * a || b    
     * @return 
     */
    public static _pExpr<BinaryExpr> binaryAny( ) {
        return new _pExpr<>( Expr.binary("a || b" ) ).$(Expr.binary("a || b"), "any");
    } 
    
    /** 
     *  i.e.true
     * @param b
     * @return  
     */
    public static _pExpr<BooleanLiteralExpr> of( boolean b ){
        return new _pExpr<>( Expr.of( b ) );
    }

    /**
     * 
     * @param b
     * @return 
     */
    public static _pExpr<BooleanLiteralExpr> booleanLiteral( boolean b ) {
        return new _pExpr( Expr.of( b ) );
    }
    
    /**
     * 
     * @param b
     * @param constraint
     * @return 
     */
    public static _pExpr<BooleanLiteralExpr> booleanLiteral( boolean b, Predicate<BooleanLiteralExpr> constraint) {
        return new _pExpr( Expr.of( b ) ).constraint(constraint);
    }
    
    /** 
     * "true" / "false" 
     * 
     * @param proto
     * @return 
     */
    public static _pExpr<BooleanLiteralExpr> booleanLiteral( String... proto ) {
        return new _pExpr( Expr.booleanLiteral(proto ) );
    }

    /** 
     * "true" / "false" 
     * @param constraint
     * @return 
     */
    public static _pExpr<BooleanLiteralExpr> booleanLiteral( Predicate<BooleanLiteralExpr>constraint ) {
        return new _pExpr( Expr.booleanLiteral("true") ).$("true", "any").constraint(constraint);
    }
    
    /** 
     * "true" / "false" 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<BooleanLiteralExpr> booleanLiteral( String proto, Predicate<BooleanLiteralExpr>constraint ) {
        return new _pExpr( Expr.booleanLiteral(proto ) ).constraint(constraint);
    }
    
    /** 
     * "true" / "false" 
     * @return 
     */
    public static _pExpr<BooleanLiteralExpr> booleanLiteralAny( ) {
        return new _pExpr( Expr.booleanLiteral("true") ).$("true", "any");
    }
    
    /** 
     * (String)o 
     * @param proto
     * @return 
     */
    public static _pExpr<CastExpr> cast( String... proto ) {
        return new _pExpr( Expr.cast(proto ) );
    }

    /** 
     * (String)o 
     * @param constraint
     * @return 
     */
    public static _pExpr<CastExpr> cast( Predicate<CastExpr> constraint ) {
        return new _pExpr( Expr.cast("(String)o")).$(Expr.of("String(o)"),"any").constraint(constraint);
    }
    
    /** 
     * (String)o 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<CastExpr> cast( String proto, Predicate<CastExpr> constraint ) {
        return new _pExpr( Expr.cast(proto ) ).constraint(constraint);
    }
    
    /** 
     * (String)o 
     * @return 
     */
    public static _pExpr<CastExpr> castAny( ) {
        return new _pExpr( Expr.cast("(String)o")).$(Expr.of("String(o)"),"any");
    }
    
    /** 
     * 'c' 
     * @param c
     * @return 
     */
    public static _pExpr<CharLiteralExpr> of( char c ){
        return new _pExpr( Expr.charLiteral( c ) );
    }

    /** 
     * 'c' 
     * @param c
     * @param constraint
     * @return 
     */
    public static _pExpr<CharLiteralExpr> of( char c, Predicate<CharLiteralExpr> constraint){
        return new _pExpr( Expr.charLiteral( c ) ).constraint(constraint);
    }
    
    /** 
     * 'c' 
     * @param c
     * @return 
     */
    public static _pExpr<CharLiteralExpr> charLiteral( char c ) {
        return new _pExpr( Expr.charLiteral( c ) );
    }

    /** 
     * 'c' 
     * @param c
     * @param constraint
     * @return 
     */
    public static _pExpr<CharLiteralExpr> charLiteral( char c, Predicate<CharLiteralExpr> constraint) {
        return new _pExpr( Expr.charLiteral( c ) ).constraint(constraint);
    }
    
    /** 
     * 'c' 
     * @param proto
     * @return 
     */
    public static _pExpr<CharLiteralExpr> charLiteral( String... proto ) {
        return new _pExpr( Expr.charLiteral(proto ) );
    }

    /** 
     * 'c' 
     * @param constraint
     * @return 
     */
    public static _pExpr<CharLiteralExpr> charLiteral( Predicate<CharLiteralExpr> constraint) {
        return new _pExpr( Expr.charLiteral('a') ).$("'a'", "any").constraint(constraint);
    }
    
    /** 
     * 'c' 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<CharLiteralExpr> charLiteral( String proto, Predicate<CharLiteralExpr> constraint) {
        return new _pExpr( Expr.charLiteral(proto ) ).constraint(constraint);
    }
    
    /** 
     * ANY char literal
     * 'c' 
     * @return 
     */
    public static _pExpr<CharLiteralExpr> charLiteralAny(  ) {
        return new _pExpr( Expr.charLiteral('a') ).$("'a'", "any");
    }
    
    /** 
     * i.e."String.class" 
     * @param proto
     * @return 
     */
    public static _pExpr<ClassExpr> classExpr( String... proto ) {
        return new _pExpr( Expr.classExpr(proto ) );
    }
    
    /**
     * Map.class
     * @param constraint
     * @return 
     */
    public static _pExpr<ClassExpr> classExpr( Predicate<ConditionalExpr> constraint) {
        return new _pExpr( Expr.classExpr("a.class") )
            .$("a.class", "any").constraint(constraint);
    }
    
    /**
     * String.class
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<ClassExpr> classExpr( String proto, Predicate<ConditionalExpr> constraint) {
        return new _pExpr( Expr.classExpr(proto ) ).constraint(constraint);
    }

    /**
     * String.class
     * @return 
     */
    public static _pExpr<ClassExpr> classExprAny() {
        return new _pExpr( Expr.classExpr("a.class") )
            .$("a.class", "any");
    }
    
    /** 
     * i.e."(a < b) ? a : b" 
     * @param proto
     * @return 
     */
    public static _pExpr<ConditionalExpr> conditional( String... proto ) {
        return new _pExpr( Expr.conditional(proto ) );
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<ConditionalExpr> conditional( String proto, Predicate<ConditionalExpr> constraint) {
        return new _pExpr( Expr.conditional(proto ) ).constraint(constraint);
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static _pExpr<ConditionalExpr> conditional(Predicate<ConditionalExpr> constraint) {
        return new _pExpr( Expr.conditional("(a==1) ? 1 : 2" ) )
                .$(Expr.conditional("(a==1) ? 1 : 2"), "any")
                .constraint(constraint);
    }    
    
    /**
     * Any conditional 
     * @return 
     */
    public static _pExpr<ConditionalExpr> conditionalAny() {
        return new _pExpr( Expr.conditional("(a==1) ? 1 : 2" ) )
                .$(Expr.conditional("(a==1) ? 1 : 2"), "any");
    }    
    
    /** 
     * 3.14d 
     * @param d
     * @return 
     */
    public static _pExpr<DoubleLiteralExpr> of( double d ){
        return new _pExpr( Expr.of( d ) );
    }

    /** 
     * 3.14d 
     * @param d
     * @return 
     */
    public static _pExpr<DoubleLiteralExpr> doubleLiteral( double d ) {
        return new _pExpr( Expr.doubleLiteral( d ) );
    }

    /**
     * 
     * @param d
     * @param constraint
     * @return 
     */
    public static _pExpr<DoubleLiteralExpr> doubleLiteral( double d, Predicate<DoubleLiteralExpr> constraint) {
        return new _pExpr( Expr.doubleLiteral( d ) ).constraint(constraint);
    }
    
    /**
     * 
     * @param proto
     * @return 
     */
    public static _pExpr<DoubleLiteralExpr> doubleLiteral( String proto ) {
        return new _pExpr( Expr.doubleLiteral(proto ) );
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<DoubleLiteralExpr> doubleLiteral( String proto, Predicate<DoubleLiteralExpr> constraint) {
        return new _pExpr( Expr.doubleLiteral(proto ) ).constraint(constraint);
    }
        
    /**
     * 
     * @param d
     * @return 
     */
    public static _pExpr<DoubleLiteralExpr> of( float d ){
        return new _pExpr( Expr.doubleLiteral( d ) );
    }

    /**
     * 
     * @param d
     * @return 
     */
    public static _pExpr<DoubleLiteralExpr> doubleLiteral( float d ) {
        return new _pExpr( Expr.of( d ) );
    }

    /**
     * 
     * @param d
     * @param constraint
     * @return 
     */
    public static _pExpr<DoubleLiteralExpr> doubleLiteral( float d, Predicate<DoubleLiteralExpr> constraint) {
        return new _pExpr( Expr.of( d ) ).constraint(constraint);
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static _pExpr<DoubleLiteralExpr> doubleLiteral( Predicate<DoubleLiteralExpr> constraint) {
        return new _pExpr( Expr.of( 1.0d ) ).$("1.0d", "any").constraint(constraint);
    }
        
    /**
     * 10.1d
     * @return 
     */
    public static _pExpr<DoubleLiteralExpr> doubleLiteralAny( ) {
        return new _pExpr( Expr.of( 1.0d ) ).$("1.0d", "any");
    }
    
    /** 
     * i.e."3.14f" 
     * @param proto
     * @return 
     */
    public static _pExpr<DoubleLiteralExpr> floatLiteral( String... proto ) {
        return new _pExpr( Expr.floatLiteral(proto ) );
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<DoubleLiteralExpr> floatLiteral( String proto, Predicate<DoubleLiteralExpr> constraint ) {
        return new _pExpr( Expr.floatLiteral(proto ) ).constraint(constraint);
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static _pExpr<DoubleLiteralExpr> floatLiteral(Predicate<DoubleLiteralExpr> constraint ) {
        return new _pExpr( Expr.of(1.0f) ).$(Expr.of(1.0f), "any").constraint(constraint);
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static _pExpr<DoubleLiteralExpr> floatLiteralAny(Predicate<DoubleLiteralExpr> constraint ) {
        return new _pExpr( Expr.of(1.0f) ).$(Expr.of(1.0f), "any");
    }
    
    /** 
     * i.e.( 3 + 4 ) 
     * @param proto
     * @return 
     */
    public static _pExpr<EnclosedExpr> enclosedExpr( String... proto ) {
        return new _pExpr( Expr.enclosedExpr(proto ) );
    }

    /**
     * i.e.( 3 + 4 ) 
     * @param constraint
     * @return 
     */
    public static _pExpr<EnclosedExpr> enclosedExpr(Predicate<EnclosedExpr>constraint ) {
        return new _pExpr( Expr.enclosedExpr("(a)" ) ).$("(a)", "any").constraint(constraint);
    }
    
    /**
     * i.e.( 3 + 4 ) 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<EnclosedExpr> enclosedExpr( String proto, Predicate<EnclosedExpr>constraint ) {
        return new _pExpr( Expr.enclosedExpr(proto ) ).constraint(constraint);
    }
    
    /**
     * i.e.( 3 + 4 )    
     * @return 
     */
    public static _pExpr<EnclosedExpr> enclosedExprAny( ) {
        return new _pExpr( Expr.enclosedExpr("(a)" ) ).$("(a)", "any");
    }
    
    /** 
     *  i.e."person.NAME"
     * @param proto
     * @return 
     */
    public static _pExpr<FieldAccessExpr> fieldAccess(String... proto ) {
        return new _pExpr( Expr.fieldAccess(proto ) );
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public static _pExpr<FieldAccessExpr> fieldAccess(Predicate<FieldAccessExpr> constraint ) {
        return new _pExpr( Expr.fieldAccess("a.B") )
                .$(Expr.fieldAccess("a.B"), "any").constraint(constraint);
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<FieldAccessExpr> fieldAccess(String proto, Predicate<FieldAccessExpr> constraint ) {
        return new _pExpr( Expr.fieldAccess(proto ) ).constraint(constraint);
    }
    
    /**
     * System.out
     * 
     * @return 
     */
    public static _pExpr<FieldAccessExpr> fieldAccessAny( ) {
        return new _pExpr( Expr.fieldAccess("a.B") )
                .$(Expr.fieldAccess("a.B"), "any");
    }
    
    
    /** 
     * v instanceof Serializable 
     * @param proto
     * @return 
     */
    public static _pExpr<InstanceOfExpr> instanceOf(String... proto ) {
        return new _pExpr( Expr.instanceOf(proto ) );
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static _pExpr<InstanceOfExpr> instanceOf(Predicate<InstanceOfExpr> constraint ) {
        return new _pExpr( Expr.instanceOf( "a instanceof b" ) ).$("a instanceof b", "any")
                .constraint(constraint);
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<InstanceOfExpr> instanceOf(String proto, Predicate<InstanceOfExpr> constraint ) {
        return new _pExpr( Expr.instanceOf(proto ) ).constraint(constraint);
    }
    
    /**
     * 
     * @return 
     */
    public static _pExpr<InstanceOfExpr> instanceOfAny() {
        return new _pExpr( Expr.instanceOf( "a instanceof b" ) ).$("a instanceof b", "any");
    }
    
    /**
     * 
     * @param i
     * @return 
     */
    public static _pExpr<IntegerLiteralExpr> of(int i) {
        return new _pExpr( Expr.of( i ) );
    }

    /**
     * 
     * @param i
     * @param constraint
     * @return 
     */
    public static _pExpr<IntegerLiteralExpr> of(int i, Predicate<IntegerLiteralExpr> constraint) {
        return new _pExpr( Expr.of( i ) ).constraint(constraint);
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static _pExpr<IntegerLiteralExpr> intLiteral(Predicate<IntegerLiteralExpr> constraint) {
        return new _pExpr( Expr.of( 1 ) ).$("1", "any").constraint(constraint);
    }
    
    public static _pExpr<IntegerLiteralExpr> intLiteralAny( ) {
        return new _pExpr( Expr.of( 1 ) ).$("1", "any");
    }
    
    /**
     * 
     * @param i
     * @return 
     */
    public static _pExpr<IntegerLiteralExpr> intLiteral(int i) {
        return new _pExpr( Expr.of( i ) );
    }

    /**
     * 
     * @param i
     * @param constraint
     * @return 
     */
    public static _pExpr<IntegerLiteralExpr> intLiteral(int i, Predicate<IntegerLiteralExpr> constraint) {
        return new _pExpr( Expr.of( i ) ).constraint(constraint);
    }
    
    /**
     * 
     * @param proto
     * @return 
     */
    public static _pExpr<IntegerLiteralExpr> intLiteral(String... proto ) {
        return new _pExpr( Expr.intLiteral(proto ) );
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<IntegerLiteralExpr> intLiteral(String proto, Predicate<IntegerLiteralExpr> constraint ) {
        return new _pExpr( Expr.intLiteral(proto ) ).constraint(constraint);
    }
    
    /** 
     * a-> System.out.println( a )  
     * @param proto
     * @return 
     */
    public static _pExpr<LambdaExpr> lambda(String... proto ) {
        return new _pExpr( Expr.lambda(proto ) );
    }

    /** 
     * a-> System.out.println( a )  
     * @param constraint
     * @return 
     */
    public static _pExpr<LambdaExpr> lambda(Predicate<LambdaExpr> constraint) {
        return new _pExpr( Expr.lambda("a-> true" ) ).$(Expr.lambda("a->true"), "any").constraint(constraint);
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<LambdaExpr> lambda(String proto , Predicate<LambdaExpr> constraint) {
        return new _pExpr( Expr.lambda(proto ) ).constraint(constraint);
    }
    
    /** 
     * a-> System.out.println( a )  
     * @return 
     */
    public static _pExpr<LambdaExpr> lambdaAny( ) {
        return new _pExpr( Expr.lambda("a-> true" ) ).$(Expr.lambda("a->true"), "any");
    }
    
    /**
     * 
     * @param l
     * @return 
     */
    public static _pExpr<LongLiteralExpr> of(long l) {
        return new _pExpr( Expr.of( l ) );
    }

    /**
     * 
     * @param l
     * @param constraint
     * @return 
     */
    public static _pExpr<LongLiteralExpr> of(long l, Predicate<LongLiteralExpr> constraint ) {
        return new _pExpr( Expr.of( l ) ).constraint(constraint);
    }
    
    /**
     * 
     * @param l
     * @return 
     */
    public static _pExpr<LongLiteralExpr> longLiteral( long l ) {
        return new _pExpr( Expr.of( l ) );
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public static _pExpr<LongLiteralExpr> longLiteral( Predicate<LongLiteralExpr> constraint ) {
        return new _pExpr( Expr.longLiteral(1L)).$(Expr.longLiteral(1L), "any")
                .constraint(constraint) ;
    }
    
    /**
     * 
     * @param l
     * @param constraint
     * @return 
     */
    public static _pExpr<LongLiteralExpr> longLiteral( long l, Predicate<LongLiteralExpr> constraint ) {
        return new _pExpr( Expr.of( l ) ).constraint(constraint);
    }
   
    /**
     * 
     * @param proto
     * @return 
     */
    public static _pExpr<LongLiteralExpr> longLiteral( String... proto ) {
        return new _pExpr( Expr.longLiteral(proto ) );
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<LongLiteralExpr> longLiteral( String proto, Predicate<LongLiteralExpr> constraint ) {
        return new _pExpr( Expr.longLiteral(proto ) ).constraint(constraint);
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public static _pExpr<LongLiteralExpr> longLiteralAny( ) {
        return new _pExpr( Expr.longLiteral(1L)).$(Expr.longLiteral(1L), "any");
    }
    
    /** 
     * doMethod(t)
     * @param proto 
     * @return  
     */
    public static _pExpr<MethodCallExpr> methodCall( String... proto ) {
        return new _pExpr( Expr.methodCall(proto ) );
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<MethodCallExpr> methodCall( String proto, Predicate<MethodCallExpr> constraint ) {
        return new _pExpr( Expr.methodCall(proto ) ).constraint(constraint);
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static _pExpr<MethodCallExpr> methodCall( Predicate<MethodCallExpr> constraint ) {
        return new _pExpr( Expr.methodCall("a()" )).$(Expr.of("a()"), "any").constraint(constraint);
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
    public static _pExpr<MethodCallExpr> methodCall( Consumer<? extends Object> lambdaWithMethodCallInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return _pExpr.of(astLambda.getBody().findFirst(MethodCallExpr.class).get());
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
    public static _pExpr<MethodCallExpr> methodCall( BiConsumer<? extends Object,? extends Object> lambdaWithMethodCallInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return _pExpr.of(astLambda.getBody().findFirst(MethodCallExpr.class).get());
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
    public static _pExpr<MethodCallExpr> methodCall( Expr.TriConsumer<? extends Object,? extends Object, ? extends Object> lambdaWithMethodCallInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return _pExpr.of(astLambda.getBody().findFirst(MethodCallExpr.class).get());
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
    public static _pExpr<MethodCallExpr> methodCall( Expr.QuadConsumer<? extends Object,? extends Object, ? extends Object, ? extends Object> lambdaWithMethodCallInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return _pExpr.of(astLambda.getBody().findFirst(MethodCallExpr.class).get());
    }
    
    /**
     * ANY method call
     * @return 
     */
    public static _pExpr<MethodCallExpr> methodCallAny( ) {
        return new _pExpr( Expr.methodCall("a()" )).$(Expr.of("a()"), "any");
    }
    
    /** 
     * i.e."String:toString" 
     * @param proto
     * @return 
     */
    public static _pExpr<MethodReferenceExpr> methodReference( String... proto ) {
        return new _pExpr( Expr.methodReference(proto ) );
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static _pExpr<MethodReferenceExpr> methodReference( Predicate<MethodReferenceExpr> constraint) {
        return new _pExpr( Expr.methodReference("A:b")).$("A:b", "any").constraint(constraint);
    }
    
    /** 
     * i.e."String:toString" 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<MethodReferenceExpr> methodReference( String proto, Predicate<MethodReferenceExpr>constraint ) {
        return new _pExpr( Expr.methodReference(proto ) ).constraint(constraint);
    }
    
    /**
     * 
     * @return 
     */
    public static _pExpr<MethodReferenceExpr> methodReferenceAny() {
        return new _pExpr( Expr.methodReference("A:b")).$("A:b", "any");
    }
    
    /** 
     *  i.e."null"
     * @return  
     */
    public static _pExpr<NullLiteralExpr> nullExpr(){
        return new _pExpr( Expr.nullExpr() );
    }

    /**
     * 
     * @param proto
     * @return 
     */
    public static _pExpr<NameExpr> name( String... proto ) {
        return new _pExpr( Expr.name(proto ) );
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static _pExpr<NameExpr> name( Predicate<NameExpr>constraint) {
        return new _pExpr( Expr.name("name" ) ).$("name", "any").constraint(constraint);
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<NameExpr> name( String proto, Predicate<NameExpr>constraint) {
        return new _pExpr( Expr.name(proto ) ).constraint(constraint);
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public static _pExpr<NameExpr> nameAny() {
        return new _pExpr( Expr.name("name" ) ).$("name", "any");
    }
    
    /** 
     * "new Date()"
     * @param proto
     * @return 
     */
    public static _pExpr<ObjectCreationExpr> objectCreation(String... proto ) {
        return new _pExpr( Expr.objectCreation( proto ) );
    }
    
    /** 
     * "new Date()"
     * @param constraint
     * @return 
     */
    public static _pExpr<ObjectCreationExpr> objectCreation(Predicate<ObjectCreationExpr>constraint ) {
        return new _pExpr( Expr.objectCreation( "new a()" ) ).$("new a()", "any").constraint(constraint);
    }
    
    /** 
     * "new Date()"
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<ObjectCreationExpr> objectCreation(String proto, Predicate<ObjectCreationExpr>constraint ) {
        return new _pExpr( Expr.objectCreation( proto ) ).constraint(constraint);
    }

    /** 
     * "new Date()"
     * @return 
     */
    public static _pExpr<ObjectCreationExpr> objectCreationAny() {
        return new _pExpr( Expr.objectCreation( "new a()" ) ).$("new a()", "any");
    }
    
    /** 
     * "literal"
     * @param proto
     * @return 
     */
    public static _pExpr<StringLiteralExpr> stringLiteral( String... proto ) {
        return new _pExpr( Expr.stringLiteral( proto ) );
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public static _pExpr<StringLiteralExpr> stringLiteral(Predicate<StringLiteralExpr> constraint) {
        return new _pExpr( Expr.stringLiteral( "\"a\"" ) ).$("\"a\"", "any").constraint(constraint);
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<StringLiteralExpr> stringLiteral( String proto, Predicate<StringLiteralExpr> constraint) {
        return new _pExpr( Expr.stringLiteral( proto ) ).constraint(constraint);
    }
    
    /**
     * 
     * @return 
     */
    public static _pExpr<StringLiteralExpr> stringLiteralAny( ) {
        return new _pExpr( Expr.stringLiteral( "\"a\"" ) ).$("\"a\"", "any");
    }
    
    /** 
     * "super"
     * @return  
     */
    public static _pExpr<SuperExpr> superExpr(  ) {
        return new _pExpr( Expr.superExpr( ) );
    }

    /**
     * 
     * @param superExpr
     * @return 
     */
    public static _pExpr<SuperExpr> superExpr(String...superExpr ){
        return new _pExpr(Expr.superExpr(superExpr));
    }
    
    /**
     * 
     * @param superExpr
     * @return 
     */
    public static _pExpr<SuperExpr> superExpr(String superExpr, Predicate<SuperExpr> constraint ){
        return new _pExpr(Expr.superExpr(superExpr)).constraint(constraint);
    }
    
    /**
     * 
     * @param se
     * @return 
     */
    public static _pExpr<SuperExpr> superExpr(SuperExpr se){
        return new _pExpr(se);
    }
    
    /**
     * 
     * @param superExpr
     * @param constraint
     * @return 
     */
    public static _pExpr<SuperExpr> superExpr(SuperExpr superExpr, Predicate<SuperExpr> constraint ){
        return new _pExpr(superExpr).constraint(constraint);
    }
    
    /**
     * ANY super expression 
     * 
     * @return 
     */
    public static _pExpr<SuperExpr> superExprAny(){
        return new _pExpr(Expr.superExpr()).$("super", "any");
    }
    
    /** 
     * "this"
     * @return  
     */
    public static _pExpr<ThisExpr> thisExpr(  ) {
        return new _pExpr( Expr.thisExpr( ) );
    }

    /**
     * 
     * @param te
     * @return 
     */
    public static _pExpr<ThisExpr> thisExpr(String... te){
        return new _pExpr(Expr.thisExpr(te) );
    }
    
    /**
     * 
     * @param te
     * @param constraint
     * @return 
     */
    public static _pExpr<ThisExpr> thisExpr(String te, Predicate<ThisExpr> constraint){
        return new _pExpr(Expr.thisExpr(te) ).constraint(constraint);
    }
    
    /**
     * 
     * @param te
     * @return 
     */
    public static _pExpr<ThisExpr> thisExpr(ThisExpr te){
        return new _pExpr(te);
    }
    
    /**
     * 
     * @param te
     * @param constraint
     * @return 
     */
    public static _pExpr<ThisExpr> thisExpr(ThisExpr te, Predicate<ThisExpr> constraint){
        return new _pExpr(te ).constraint(constraint);
    }

    /**
     * ANY this expression
     * @return 
     */
    public static _pExpr<ThisExpr> thisExprAny( ){
        return new _pExpr(Expr.thisExpr()).$("this", "any");
    }
    
    /** 
     * i.e."World" in World::greet 
     * @param proto
     * @return 
     */
    public static _pExpr<TypeExpr> typeExpr(String... proto ) {
        return new _pExpr( Expr.typeExpr( proto ) );
    }
    
    /** 
     * i.e."World" in World::greet 
     * @param constraint
     * @return 
     */
    public static _pExpr<TypeExpr> typeExpr(Predicate<TypeExpr> constraint ) {
        return new _pExpr( Expr.typeExpr( "a" ) ).$("a", "any").constraint(constraint);
    }
    
    /** 
     * i.e."World" in World::greet 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pExpr<TypeExpr> typeExpr(String proto, Predicate<TypeExpr> constraint ) {
        return new _pExpr( Expr.typeExpr( proto ) ).constraint(constraint);
    }

    /** 
     * i.e."World" in World::greet 
     * @return 
     */
    public static _pExpr<TypeExpr> typeExprAny( ) {
        return new _pExpr( Expr.typeExpr( "a" ) ).$("a", "any");
    }
    
    /** 
     * i.e."!true" 
     * @param proto
     * @return 
     */
    public static _pExpr<UnaryExpr> unary( String... proto ) {
        return new _pExpr( Expr.unary( proto ) );
    }
   
    /** 
     *  i.e."!true"
     * @param constraint 
     * @return  
     */
    public static _pExpr<UnaryExpr> unary( Predicate<UnaryExpr>constraint) {
        return new _pExpr( Expr.unary( "!true" ) ).$("!true", "any").constraint(constraint);
    }
    
    /** 
     *  i.e."!true"
     * @param proto 
     * @param constraint 
     * @return  
     */
    public static _pExpr<UnaryExpr> unary( String proto, Predicate<UnaryExpr>constraint) {
        return new _pExpr( Expr.unary( proto ) ).constraint(constraint);
    }
    
    /** 
     *  i.e."!true"
     * @param constraint 
     * @return  
     */
    public static _pExpr<UnaryExpr> unaryAny() {
        return new _pExpr( Expr.unary( "!true" ) ).$("!true", "any");
    }
    
    /** 
     * "int i = 1"
     * @param proto
     * @return  
     */
    public static _pExpr<VariableDeclarationExpr> varDecl( String... proto ) {
        return new _pExpr( Expr.varDecl( proto ) );
    }

    /** 
     * "int i = 1"
     * @param constraint 
     * @return  
     */
    public static _pExpr<VariableDeclarationExpr> varDecl( Predicate<VariableDeclarationExpr> constraint) {
        return new _pExpr( Expr.varDecl( "int i=1") ).$(Expr.of("int i=1"), "any").constraint(constraint);
    }
    
    /** 
     * "int i = 1"
     * @param proto 
     * @param constraint 
     * @return  
     */
    public static _pExpr<VariableDeclarationExpr> varDecl( String proto, Predicate<VariableDeclarationExpr> constraint) {
        return new _pExpr( Expr.varDecl( proto ) ).constraint(constraint);
    }
    
    /** 
     * "int i = 1"
     * @return  
     */
    public static _pExpr<VariableDeclarationExpr> varDeclAny( ) {
        return new _pExpr( Expr.varDecl( "int i=1") ).$(Expr.of("int i=1"), "any");
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
    public _pExpr(T astExpressionProto){
        this.expressionClass = (Class<T>)astExpressionProto.getClass();
        this.exprPattern = Stencil.of(astExpressionProto.toString() );
    }

    /**
     * 
     * @param expressionClass
     * @param stencil 
     */
    public _pExpr(Class<T>expressionClass, String stencil ){
        this.expressionClass = expressionClass;
        this.exprPattern = Stencil.of(stencil);
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public _pExpr constraint( Predicate<T> constraint){
        this.constraint = constraint;
        return this;
    }
    
    @Override
    public T fill(Object...values){
        String str = exprPattern.fill(Translator.DEFAULT_TRANSLATOR, values);
        return (T)Expr.of( str);
    }

    @Override
    public _pExpr<T> $(String target, String $name ) {
        this.exprPattern = this.exprPattern.$(target, $name);
        return this;
    }

    /**
     * 
     * @param astExpr
     * @param $name
     * @return 
     */
    public _pExpr<T> $( Expression astExpr, String $name){
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
    public _pExpr assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public _pExpr assign$( Object... keyValues ) {
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
    public _pExpr assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public _pExpr assign$( Translator translator, Tokens kvs ) {
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
        return (T)_pExpr.this.construct(_n.deconstruct());
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
    public args deconstruct( String... expression ){
        return deconstruct( Expr.of(expression) );
    }
    
    /**
     * Deconstruct the expression into tokens, or return null if the statement doesnt match
     *
     * @param astExpr expression
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public args deconstruct( Expression astExpr ){
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
                return args.of(ts);
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
        args ts = this.deconstruct(astExpr);
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
            args tokens = deconstruct( e );
            if( tokens != null ){
                expressionActionFn.accept( e);
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<T> expressionActionFn){
        Walk.in(_n, this.expressionClass, e -> {
            args tokens = deconstruct( e );
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
        return replaceIn(_n, _pExpr.of(protoReplaceExpr) );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param $repl
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, _pExpr $repl ){
        Walk.in(_n, this.expressionClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astExpression.replace($repl.construct(sel.args));
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
    public static class Select<T extends Expression> implements _pQuery.selected<T>,
            _pQuery.selectedAstNode<T> {
        
        public final T astExpression;
        public final args args;

        public Select( T astExpr, args tokens){
            this.astExpression = astExpr;
            this.args = tokens;
        }
        
        @Override
        public args getArgs(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$expr.Select{"+ System.lineSeparator()+
                Text.indent(astExpression.toString() )+ System.lineSeparator()+
                Text.indent("ARGS : " + args) + System.lineSeparator()+
                "}";
        }

        @Override
        public T ast() {
            return this.astExpression;
        }
    }
}
