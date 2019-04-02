package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import draft.*;
import draft.java.*;
import draft.java.Expr.QuadConsumer;
import draft.java.Expr.TriConsumer;
import draft.java._model._node;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;

/**
 * Prototype of a Java {@link Statement} that provides operations for 
 * constructing, analyzing, extracting, removing, replacing / etc. Statement
 * type nodes within the AST.
 *
 * <PRE>
 * $stmt
 * CONSTRUCT
 *     .construct([Translator], Tokens) build & return 
 *     .fill([Translator], values)
 * PARAMETERIZE
 *     .$(Tokens)
 *     .assign$([translator], target, value)
 * MATCH
 *     .constraint(Predicate<T>) //set the matching constraint
 *     .matches(Statement)
 *     .select(Statement)
 *     .deconstruct( Statement )
 * QUERY       
 *     .first/.firstIn(_node, proto) find the first matching statement in
 *     .list/.listIn(_node, proto, Predicate<>) list all matches in        
 *     .selectFirst/.selectFirstIn(_node, proto) return the first "selection" match
 *     .selectList/.selectListIn(_node, proto) return a list of selection matches
 * MODIFY
 *     .remove/.removeIn(_node, proto)
 *     .replace/.replaceIn(_node, protoTarget, protoReplacement)
 *     .forIn(_node, Consumer<T>)
 *     .forSelectedIn(_node, Consumer<T>) 
 *</PRE>      
 * @param <T> underlying Statement implementation type
 */
public final class $stmt<T extends Statement>
    implements Template<T>, $query<T> {
    
    /**
     * Return the first statement matching the lambda
     * @param <T>
     * @param _n where to look
     * @param statementMatchFn
     * @return true if found else false
     */
    public static final <T extends Statement> T first( _node _n, Predicate<T> statementMatchFn ){
        Optional<Node> os = _n.ast().stream().filter(n -> n instanceof Statement 
                &&  statementMatchFn.test( (T)n) )
                .findFirst();
        if( os.isPresent() ){
            return (T)os.get();
        }
        return null;
    }
    
    /**
     * Return the first instance matching the proto and return it or null
     * @param <T>
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final <T extends Statement> T first( _node _n, String... proto ){
        return (T)$stmt.of(proto).firstIn(_n);
    }

    /**
     * Return the first instance matching the proto and return it or null
     * @param <T>
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @param constraint
     * @return true if found else false
     */
    public static final <T extends Statement> T first( _node _n, String proto, Predicate<T>constraint){
        return (T)$stmt.of(proto).constraint(constraint).firstIn(_n);
    }
    
    /**
     * Does this stmt template appear in the root node?
     * @param <T>
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final <T extends Statement> T first( _node _n, $stmt<T> proto){
        return proto.firstIn(_n);
    }    
           
    /**
     * Does this stmt template appear in the root node?
     * @param <T>
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final <T extends Statement> Select<T> selectFirst( _node _n, String... proto ){
        return (Select<T>)$stmt.of(proto).selectFirstIn(_n);
    }
    
    /**
     * Does this stmt template appear in the root node?
     * @param <T>
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @param constraint
     * @return true if found else false
     */
    public static final <T extends Statement> Select<T> selectFirst( _node _n, String proto, Predicate<T>constraint ){
        return (Select<T>)$stmt.of(proto).constraint(constraint).selectFirstIn(_n);
    }
    
    /**
     * Does this stmt template appear in the root node?
     * @param <T>
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final <T extends Statement> Select<T> selectFirst( _node _n, T proto ){
        return (Select<T>)$stmt.of(proto).selectFirstIn(_n);
    }
    
    /**
     * Does this stmt template appear in the root node?
     * @param <T>
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @param constraint
     * @return true if found else false
     */
    public static final <T extends Statement> Select<T> selectFirst( _node _n, T proto, Predicate<T> constraint){
        return (Select<T>)$stmt.of(proto).constraint(constraint).selectFirstIn(_n);
    }
    
    /**
     * return a Select of the first Stmt template appear in the root node?
     * @param <T>
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final <T extends Statement> Select<T> selectFirst( _node _n, $stmt<T> proto){
        return proto.selectFirstIn(_n);
    }
    
    /**
     * Returns a lkist of Select<T> that occur in the _node _n
     * @param <T>
     * @param _n where to look
     * @param $proto the prototype of the Statement we're looking for
     * @return a List of Select<T>
     */
    public static final <T extends Statement> List<Select<T>> selectList( _node _n, $stmt<T> $proto){
        return $proto.selectListIn(_n);
    }
    
    /**
     * 
     * @param <T>
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <T extends Statement, N extends _node> List<Select<T>> selectList( N _n, String proto ){
        return $stmt.of(proto).selectListIn(_n);
    }
    
    /**
     * 
     * @param <T>
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <T extends Statement, N extends _node> List<Select<T>> selectList( N _n, String proto, Predicate<T>constraint){
        return $stmt.of(proto).constraint(constraint).selectListIn(_n);
    }
    
    /**
     * Does this stmt template appear in the root node?
     * @param <T>
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final <T extends Statement> List<Select<T>> selectList( _node _n, String... proto ){
        return $stmt.of(proto).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node, T extends Statement> List<T> list( N _n, String proto ){
        return $stmt.of(proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node, T extends Statement> List<T> list( N _n, String proto, Predicate<T> constraint){
        return $stmt.of(proto).constraint(constraint).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node, T extends Statement> List<T> list( N _n, T proto ){
        return $stmt.of(proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node, T extends Statement> List<T> list( N _n, T proto, Predicate<T> constraint){
        return $stmt.of(proto).constraint(constraint).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Statement> list( N _n, $stmt proto ){
        return proto.listIn(_n);
    }
        
    /**
     * Removes all occurrences of the proto Statement in the node (recursively)
     * @param <N>
     * @param <T>
     * @param _n
     * @param proto
     * @return the modified N
     */
    public static final <N extends _node, T extends Statement> N remove( N _n, T proto ){
        return (N)$stmt.of(proto).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String proto ){
        return (N)$stmt.of(proto).removeIn(_n);
    }    
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node, T extends Statement> N remove( N _n, String proto, Predicate<T> constraint){
        return (N)$stmt.of(proto).constraint(constraint).removeIn(_n);
    }   
    
    /**
     * Removes all occurrences of the proto Statement in the node (recursively)
     * @param <N>
     * @param <T>
     * @param astNode
     * @param proto
     * @return the modified N
     */
    public static final <N extends Node, T extends Statement> N remove( N astNode, T proto ){
        return (N)$stmt.of(proto).removeIn(astNode);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @return 
     */
    public static final <N extends Node> N remove( N astNode, String proto ){
        return (N)$stmt.of(proto).removeIn(astNode);
    }    
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param astNode
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends Node, T extends Statement> N remove( N astNode, String proto, Predicate<T> constraint){
        return (N)$stmt.of(proto).constraint(constraint).removeIn(astNode);
    }   
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param protoTarget
     * @param protoReplacement the prototype/statement to replace
     * @return 
     */
    public static final <N extends _node,T extends Statement> N replace(N _n, T protoTarget, Statement protoReplacement){
        return (N)$stmt.of(protoTarget)
            .replaceIn(_n, $stmt.of(protoReplacement));
    }    

    /**
     * 
     * @param <N>
     * @param _n
     * @param protoTarget
     * @param protoReplacement
     * @return 
     */
    public static final <N extends _node> N replace(N _n, String protoTarget, String protoReplacement){
        return (N)$stmt.of(protoTarget)
            .replaceIn(_n, $stmt.of(protoReplacement));
    }            
    
    /**
     * 
     * @param ste
     * @return 
     */
    private static $stmt fromStackTrace( StackTraceElement ste ){
        Statement st = Stmt.from( ste );
        return new $stmt( st );
    }

    /**
     * 
     * @param <T>
     * @param proto
     * @return 
     */ 
    public static <T extends Object>  $stmt of( Expr.Command proto ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return fromStackTrace( ste );
    }
    
    /**
     * 
     * @param <T>
     * @param proto
     * @return 
     */
    public static <T extends Object>  $stmt of( Consumer<T> proto ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return fromStackTrace( ste );
    } 
    
    /**
     * 
     * @param <T>
     * @param <U>
     * @param proto
     * @return 
     */
    public static <T extends Object, U extends Object>  $stmt of( BiConsumer<T,U> proto ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return fromStackTrace( ste );
    }

    /**
     * 
     * @param <T>
     * @param <U>
     * @param <V>
     * @param proto
     * @return 
     */
    public static <T extends Object, U extends Object, V extends Object>  $stmt of(TriConsumer<T, U, V> proto ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return fromStackTrace( ste );
    }

    /**
     * 
     * @param <T>
     * @param <U>
     * @param <V>
     * @param <X>
     * @param proto
     * @return 
     */
    public static <T extends Object, U extends Object, V extends Object, X extends Object> $stmt of(QuadConsumer<T, U, V, X> proto ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return fromStackTrace( ste );
    }

    /**
     * 
     * @param proto
     * @return 
     */
    public static $stmt of(String...proto ){
        Statement st = Stmt.of(proto);
        return new $stmt( st );
    }
    
    /**
     * 
     * @param astProto
     * @return 
     */ 
    public static $stmt of(Statement astProto ){
        return new $stmt<>(astProto);
    }
     
    /**
     * i.e."assert(1==1);"
     * @param proto
     * @return and AssertStmt with the code
     */
    public static $stmt<AssertStmt> assertStmt(String... proto ) {
        return new $stmt( Stmt.assertStmt(proto));
    }

    /**
     * i.e."assert(1==1);"
     * @param constraint
     * @return and AssertStmt with the code
     */
    public static $stmt<AssertStmt> assertStmt(String proto, Predicate<AssertStmt> constraint) {
        return new $stmt( Stmt.assertStmt(proto) ).constraint(constraint);
    }
    
    /**
     * NOTE: If you omit the opening and closing braces { }, they will be added
     *
     * i.e."{ int i=1; return i;}"
     * @param constraint
     * @return the BlockStmt
     */
    public static $stmt<BlockStmt> block(Predicate<BlockStmt> constraint) {
        return new $stmt( Stmt.block("{ a(); }"))
                .$(Stmt.block("{ a(); }"), "any")
                .constraint(constraint);
    }
    
    /**
     * NOTE: If you omit the opening and closing braces { }, they will be added
     *
     * i.e."{ int i=1; return i;}"
     * @param proto the code making up the blockStmt
     * @return the BlockStmt
     */
    public static $stmt<BlockStmt> block(String... proto ) {
        return new $stmt( Stmt.block(proto));
    }
    
    /**
     * NOTE: If you omit the opening and closing braces { }, they will be added
     *
     * i.e."{ int i=1; return i;}"
     * @param proto the code making up the blockStmt
     * @param constraint
     * @return the BlockStmt
     */
    public static $stmt<BlockStmt> block(String proto, Predicate<BlockStmt> constraint) {
        return new $stmt( Stmt.block(proto)).constraint(constraint);
    }
    

    /**
     * i.e."break;" or "break outer;"
     * @param constraint
     * @return the breakStmt
     */
    public static $stmt<BreakStmt> breakStmt(Predicate<BreakStmt> constraint) {
        return new $stmt( Stmt.breakStmt("break;"))
                .$(Stmt.breakStmt("break;"), "any")
                .constraint(constraint);
    }
    
    /**
     * i.e."break;" or "break outer;"
     * @param proto String representing the break of
     * @return the breakStmt
     */
    public static $stmt<BreakStmt> breakStmt(String... proto ) {
        return new $stmt( Stmt.breakStmt(proto));
    }

    /**
     * i.e."break;" or "break outer;"
     * @param proto String representing the break of
     * @param constraint
     * @return the breakStmt
     */
    public static $stmt<BreakStmt> breakStmt(String  proto, Predicate<BreakStmt> constraint) {
        return new $stmt( Stmt.breakStmt(proto)).constraint(constraint);
    }
    
    /** 
     * i.e."continue outer;" 
     * @param constraint
     * @return 
     */
    public static $stmt<ContinueStmt> continueStmt(Predicate<ContinueStmt> constraint) {
        return new $stmt( Stmt.continueStmt("continue r;")).
                $(Stmt.continueStmt("continue r;"), "any").constraint(constraint);
    }
    
    /** 
     * i.e."continue outer;" 
     * @param proto
     * @return 
     */
    public static $stmt<ContinueStmt> continueStmt(String... proto ) {
        return new $stmt( Stmt.continueStmt(proto));
    }

    
    /** 
     * i.e."continue outer;" 
     * @param proto
     * @return 
     */
    public static $stmt<ContinueStmt> continueStmt(String proto, Predicate<ContinueStmt> constraint) {
        return new $stmt( Stmt.continueStmt(proto)).constraint(constraint);
    }
    
    
    /** 
     * i.e."do{ System.out.println(1); }while( a < 100 );" 
     * @param constraint
     * @return 
     */
    public static $stmt<DoStmt> doStmt(Predicate<DoStmt> constraint) {
        return new $stmt( Stmt.doStmt("do{ a(); } while(a==1);") )
                .$(Stmt.doStmt("do{ a(); } while(a==1);"), "any").constraint(constraint);
    }
    
    /** 
     * i.e."do{ System.out.println(1); }while( a < 100 );" 
     * @param proto
     * @return 
     */
    public static $stmt<DoStmt> doStmt(String... proto ) {
        return new $stmt( Stmt.doStmt(proto));
    }

    /** 
     * i.e."do{ System.out.println(1); }while( a < 100 );" 
     * @param proto
     * @return 
     */
    public static $stmt<DoStmt> doStmt(String proto, Predicate<DoStmt> constraint) {
        return new $stmt( Stmt.doStmt(proto)).constraint(constraint);
    }
    
    /** 
     * i.e."this(100,2900);" 
     * @param constraint
     * @return 
     */
    public static $stmt<ExplicitConstructorInvocationStmt> ctorInvocationStmt(Predicate<ExplicitConstructorInvocationStmt> constraint) {
        return new $stmt( Stmt.ctorInvocationStmt("this(a);"))
            .$(Stmt.ctorInvocationStmt("this(a);"), "any")
            .constraint(constraint);
    }
    
    /** 
     * i.e."this(100,2900);" 
     * @param proto
     * @return 
     */
    public static $stmt<ExplicitConstructorInvocationStmt> ctorInvocationStmt(String... proto ) {
        return new $stmt( Stmt.ctorInvocationStmt(proto));
    }

    /** 
     * i.e."this(100,2900);" 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $stmt<ExplicitConstructorInvocationStmt> ctorInvocationStmt(String proto, Predicate<ExplicitConstructorInvocationStmt> constraint) {
        return new $stmt( Stmt.ctorInvocationStmt(proto)).constraint(constraint);
    }
    
    /** 
     * i.e."s += t;" 
     * @param constraint
     * @return 
     */
    public static $stmt<ExpressionStmt> expressionStmt( Predicate<ExpressionStmt> constraint) {
        return new $stmt( Stmt.expressionStmt("a += t;"))
                .$("a += t;", "any")
                .constraint(constraint);
    }
    
    /** 
     * i.e."s += t;" 
     * @param proto
     * @return 
     */
    public static $stmt<ExpressionStmt> expressionStmt( String... proto ) {
        return new $stmt( Stmt.expressionStmt(proto));
    }
    
    /** 
     * i.e."s += t;" 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $stmt<ExpressionStmt> expressionStmt( String proto, Predicate<ExpressionStmt> constraint) {
        return new $stmt( Stmt.expressionStmt(proto)).constraint(constraint);
    }

    /** 
     * i.e."for(int i=0; i<100;i++) {...}" 
     * @param constraint
     * @return 
     */
    public static $stmt<ForStmt> forStmt( Predicate<ForStmt> constraint ) {
        return new $stmt( Stmt.forStmt("for(int i=0;i<1;i++){ a(); }"))
                .$(Stmt.forStmt("for(int i=0;i<1;i++){ a(); }"), "any")
                .constraint(constraint);
    }
    
    /** 
     * i.e."for(int i=0; i<100;i++) {...}" 
     * @param proto
     * @return 
     */
    public static $stmt<ForStmt> forStmt( String... proto ) {
        return new $stmt( Stmt.forStmt(proto));
    }
    
    /** 
     * i.e."for(int i=0; i<100;i++) {...}" 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $stmt<ForStmt> forStmt( String proto, Predicate<ForStmt> constraint ) {
        return new $stmt( Stmt.forStmt(proto)).constraint(constraint);
    }
    

    /** 
     * i.e."for(String element:arr){...}" 
     * @param constraint
     * @return 
     */
    public static $stmt<ForEachStmt> forEachStmt( Predicate<ForEachStmt> constraint) {
        return new $stmt( Stmt.forEachStmt("for(int i:arr){}"))
                .$(Stmt.forEachStmt("for(int i:arr){}"), "any")
                .constraint(constraint);
    }
    
    /** 
     * i.e."for(String element:arr){...}" 
     * @param proto
     * @return 
     */
    public static $stmt<ForEachStmt> forEachStmt( String... proto ) {
        return new $stmt( Stmt.forEachStmt(proto));
    }
    
    /** 
     * i.e."for(String element:arr){...}" 
     * @param proto
     * @return 
     */
    public static $stmt<ForEachStmt> forEachStmt( String proto, Predicate<ForEachStmt> constraint) {
        return new $stmt( Stmt.forEachStmt(proto)).constraint(constraint);
    }

    /** 
     * i.e."if(a==1){...}" 
     * @param constraint
     * @return 
     */
    public static $stmt<IfStmt> ifStmt(Predicate<IfStmt> constraint) {
        return new $stmt( Stmt.ifStmt("if(a){ b(); }"))
                .$(Stmt.of("if(a){ b();}"), "any")
                .constraint(constraint);
    }
    
    /** 
     * i.e."if(a==1){...}" 
     * @param proto
     * @return 
     */
    public static $stmt<IfStmt> ifStmt( String... proto ) {
        return new $stmt( Stmt.ifStmt(proto));
    }
    
    /** 
     * i.e."if(a==1){...}" 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $stmt<IfStmt> ifStmt( String proto, Predicate<IfStmt> constraint) {
        return new $stmt( Stmt.ifStmt(proto)).constraint(constraint);
    }
    

    /** 
     * i.e."outer:   start = getValue();" 
     * @param constraint
     * @return 
     */
    public static $stmt<LabeledStmt> labeledStmt( Predicate<LabeledStmt> constraint) {
        return new $stmt( Stmt.labeledStmt("l: a();"))
                .$(Stmt.labeledStmt("l:a();"), "any").constraint(constraint);
    }
    
    /** 
     * i.e."outer:   start = getValue();" 
     * @param proto
     * @return 
     */
    public static $stmt<LabeledStmt> labeledStmt( String... proto ) {
        return new $stmt( Stmt.labeledStmt(proto));
    }

    /** 
     * i.e."outer:   start = getValue();" 
     * @param proto
     * @return 
     */
    public static $stmt<LabeledStmt> labeledStmt( String proto, Predicate<LabeledStmt> constraint) {
        return new $stmt( Stmt.labeledStmt(proto)).constraint(constraint);
    }
    
    
    /**
     * i.e."class C{ int a, b; }"
     * @param constraint
     * @return the AST implementation
     */
    public static $stmt<LocalClassDeclarationStmt> localClass( Predicate<LocalClassDeclarationStmt> constraint) {
        return new $stmt( Stmt.localClass( "class C{}"))
                .$(Stmt.localClass("class C{}"), "any")
                .constraint(constraint);
    }
    
    /**
     * Converts from a String to a LocalClass
     * i.e. "class C{ int a, b; }"
     * @param proto the code that represents a local class
     * @return the AST implementation
     */
    public static $stmt<LocalClassDeclarationStmt> localClass( String... proto ) {
        return new $stmt( Stmt.localClass( proto));
    }
    
    /**
     * Converts from a String to a LocalClass
     * i.e."class C{ int a, b; }"
     * @param proto the code that represents a local class
     * @param constraint
     * @return the AST implementation
     */
    public static $stmt<LocalClassDeclarationStmt> localClass( String proto, Predicate<LocalClassDeclarationStmt> constraint) {
        return new $stmt( Stmt.localClass( proto)).constraint(constraint);
    }
    

    /** 
     * i.e."return VALUE;" 
     * @param constraint
     * @return 
     */
    public static $stmt<ReturnStmt> returnStmt( Predicate<ReturnStmt> constraint ) {
        return new $stmt( Stmt.returnStmt("return a;"))
                .$(Stmt.of("return a;"), "any").constraint(constraint);
    }
    
    /** 
     * i.e."return VALUE;" 
     * @param proto
     * @return 
     */
    public static $stmt<ReturnStmt> returnStmt( String... proto ) {
        return new $stmt( Stmt.returnStmt(proto));
    }
    
    /** 
     * i.e."return VALUE;" 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $stmt<ReturnStmt> returnStmt( String proto, Predicate<ReturnStmt> constraint ) {
        return new $stmt( Stmt.returnStmt(proto)).constraint(constraint);
    }

    /**
     * 
     * @param proto
     * @return 
     */
    public static $stmt<SwitchStmt> switchStmt( String... proto ) {
        return new $stmt( Stmt.switchStmt(proto));
    }

    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $stmt<SwitchStmt> switchStmt( String proto, Predicate<SwitchStmt> constraint) {
        return new $stmt( Stmt.switchStmt(proto)).constraint(constraint);
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static $stmt<SwitchStmt> switchStmt(Predicate<SwitchStmt> constraint) {
        return new $stmt( Stmt.switchStmt("switch(a){ default : a(); }"))
                .$(Stmt.of("switch(a){ default : a(); }"), "any")
                .constraint(constraint);
    }
    
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static $stmt<SynchronizedStmt> synchronizedStmt( Predicate<SynchronizedStmt> constraint ) {
        return new $stmt( Stmt.synchronizedStmt("synchronized(a){ b();}") ).
            $(Stmt.synchronizedStmt("synchronized(a){ b();}"), "any")
            .constraint(constraint);
    }
    
    /**
     * 
     * @param proto
     * @return 
     */
    public static $stmt<SynchronizedStmt> synchronizedStmt( String... proto ) {
        return new $stmt( Stmt.synchronizedStmt(proto));
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $stmt<SynchronizedStmt> synchronizedStmt( String  proto, Predicate<SynchronizedStmt> constraint ) {
        return new $stmt( Stmt.synchronizedStmt(proto)).constraint(constraint);
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static $stmt<ThrowStmt> throwStmt( Predicate<ThrowStmt> constraint ) {
        return new $stmt( Stmt.throwStmt("throw a;")).$(Stmt.throwStmt("throw a;"), "any").constraint(constraint);
    }
    
    /**
     * 
     * @param proto
     * @return 
     */
    public static $stmt<ThrowStmt> throwStmt( String... proto ) {
        return new $stmt( Stmt.throwStmt(proto));
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $stmt<ThrowStmt> throwStmt( String proto, Predicate<ThrowStmt> constraint) {
        return new $stmt( Stmt.throwStmt(proto)).constraint(constraint);
    }

    /** 
     * i.e."try{ clazz.getMethod("fieldName"); }" 
     * @param constraint
     * @return 
     */
    public static $stmt<TryStmt> tryStmt( Predicate<TryStmt> constraint ) {
        return new $stmt( Stmt.tryStmt("try{ a(); }"))
            .$(Stmt.tryStmt("try{ a();}"), "any")
            .constraint(constraint);
    }
    
    /** 
     * i.e."try{ clazz.getMethod("fieldName"); }" 
     * @param proto
     * @return 
     */
    public static $stmt<TryStmt> tryStmt( String... proto ) {
        return new $stmt( Stmt.tryStmt(proto));
    }

    /** 
     * i.e."try{ clazz.getMethod("fieldName"); }" 
     * @param proto
     * @return 
     */
    public static $stmt<TryStmt> tryStmt( String  proto, Predicate<TryStmt> constraint ) {
        return new $stmt( Stmt.tryStmt(proto)).constraint(constraint);
    }
    
    /** 
     * i.e."while(i< 1) { ...}"
     * @param proto
     * @return 
     */
    public static $stmt<WhileStmt> whileStmt( String... proto ) {
        return new $stmt( Stmt.whileStmt(proto));
    }

    /** 
     * i.e."while(i< 1) { ...}"
     * @param proto
     * @param constraint
     * @return 
     */
    public static $stmt<WhileStmt> whileStmt( String proto, Predicate<WhileStmt> constraint ) {
        return new $stmt( Stmt.whileStmt(proto)).constraint(constraint);
    }

    /** 
     * i.e."while(i< 1) { ...}"
     * @param constraint
     * @return 
     */
    public static $stmt<WhileStmt> whileStmt( Predicate<WhileStmt> constraint ) {
        return new $stmt( Stmt.whileStmt("while(true){a();}") ).
                $(Stmt.whileStmt("while(true){a();}").toString(), "any").constraint(constraint);
    }
    
    /**
     * IF the statement has a comment, it is stored separately, because,
     * in situations where we are composing the statement, we want to construct the
     */
    public Stencil commentStencil;

    /**
     * Optional matching predicate applied to matches to ensure 
     * not only pattern match
     */
    public Predicate<T> constraint = t-> true;
    
    /** The stencil representing the statement */
    public Stencil stencil;

    /** the class of the statement */
    public Class<T> statementClass;

    public $stmt( T st ){
        this.statementClass = (Class<T>)st.getClass();
        if( st.getComment().isPresent() ){
            Comment c = st.getComment().get();
            this.commentStencil = Stencil.of( c.toString() );
        }
        this.stencil = Stencil.of( st.toString(NO_COMMENTS) );
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public $stmt constraint( Predicate<T> constraint){
        this.constraint = constraint;
        return this;
    }
    
    @Override
    public T fill(Object...values){
        if( this.commentStencil != null ){
            return (T)Stmt.of(
                    Stencil.of(commentStencil, this.stencil ).fill(Translator.DEFAULT_TRANSLATOR, values) );
        }
        String str = stencil.fill(Translator.DEFAULT_TRANSLATOR, values);
        return (T)Stmt.of( str);
    }

    @Override
    public $stmt $(String target, String $name ) {
        if( this.commentStencil != null ) {
            this.commentStencil = this.commentStencil.$(target, $name);
        }
        this.stencil = this.stencil.$(target, $name);
        return this;
    }

    /**
     * 
     * @param expr
     * @param $name
     * @return 
     */
    public $stmt $(Expression expr, String $name ){
        String exprString = expr.toString();
        return $(exprString, $name);
    }
    
    /**
     * 
     * @param stmt
     * @param $name
     * @return 
     */
    public $stmt $(Statement stmt, String $name ){
        String stmtString = stmt.toString( Ast.PRINT_NO_COMMENTS );
        return $(stmtString, $name);
    }

    @Override
    public T fill(Translator t, Object...values){
        if( this.commentStencil != null ){
            return (T)Stmt.of( Stencil.of(commentStencil, stencil).fill(t, values) );
        }
        return (T)Stmt.of( stencil.fill(t, values));
    }

    @Override
    public T construct( Object...keyValues ){
        if( this.commentStencil != null ){
            return (T)Stmt.of(Stencil.of(commentStencil, stencil).construct( keyValues) );
        }
        return (T)Stmt.of( stencil.construct( Tokens.of(keyValues)));
    }

    /**
     * 
     * @param _n
     * @return 
     */
    public T construct( _node _n ){
        if( this.commentStencil != null ){
            return (T)Stmt.of(Stencil.of(commentStencil, stencil).construct(_n.componentize()) );
        }
        return (T)$stmt.this.construct(_n.componentize());
    }

    @Override
    public T construct( Translator t, Object...keyValues ){
        if( this.commentStencil != null ){
            return (T)Stmt.of( Stencil.of(commentStencil, stencil).construct(t, Tokens.of(keyValues) ) );
        }
        return (T)Stmt.of( stencil.construct( t, Tokens.of(keyValues) ));
    }

    @Override
    public T construct( Map<String,Object> tokens ){
        if( this.commentStencil != null ){
            return (T)Stmt.of( Stencil.of(commentStencil, stencil).construct( Translator.DEFAULT_TRANSLATOR, tokens ));
        }
        return (T)Stmt.of( stencil.construct( Translator.DEFAULT_TRANSLATOR, tokens ));
    }

    @Override
    public T construct( Translator t, Map<String,Object> tokens ){
        if( this.commentStencil != null ){
            return (T)Stmt.of(Stencil.of(commentStencil, stencil).construct( t, tokens ));
        }
        return (T)Stmt.of(stencil.construct( t, tokens ));
    }

    /**
     * 
     * @param stmt
     * @return 
     */
    public boolean matches( String...stmt ){
        return matches( Stmt.of(stmt));
    }

    /**
     * 
     * @param astStmt
     * @return 
     */
    public boolean matches( Statement astStmt ){
        return deconstruct(astStmt) != null;
    }

    @Override
    public List<String> list$(){
        if( this.commentStencil != null ){
            return Stencil.of( commentStencil, stencil).list$();
        }
        return this.stencil.list$();
    }

    @Override
    public List<String> list$Normalized(){
        if( this.commentStencil != null ){
            return Stencil.of( commentStencil, stencil).list$Normalized();
        }
        return this.stencil.list$Normalized();
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $stmt assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $stmt assign$( Object... keyValues ) {
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
    public $stmt assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $stmt assign$( Translator translator, Tokens kvs ) {
        if( this.commentStencil != null ){
            this.commentStencil = this.commentStencil.assign$(translator, kvs);
            this.stencil = this.stencil.assign$(translator,kvs);
        } else {
            this.stencil = this.stencil.assign$(translator, kvs);
        }
        return this;
    }

    /**
     * 
     * @param astStmt
     * @return 
     */
    public Select<T> select( Statement astStmt ){
        Tokens ts = deconstruct(astStmt );
        if( ts != null ){
            return new Select( astStmt, ts );
        }
        return null;
    }

    /**
     * Deconstruct the statement into tokens, or return null if the statement doesnt match
     *
     * @param astStmt the statement to partsMap
     * @return Tokens from the stencil, or null if the statement doesnt match
     */
    public Tokens deconstruct( Statement astStmt ){
        
        if( statementClass.isAssignableFrom(astStmt.getClass())){
            if( ! constraint.test((T) astStmt)){
                return null;
            }
            if( !this.stencil.getTextBlanks().hasBlanks()){ //if it's a static template
                if( astStmt.getComment().isPresent() ){ //removeIn any comments before checking                    
                    Tokens tks = stencil.deconstruct(astStmt.toString());
                    if( tks != null ){
                        return tks;
                    }
                    //if the statement HAS a comment and the template does not
                    Statement cpy = astStmt.clone();
                    cpy.removeComment();
                    if( cpy.toString().trim().equals(stencil.getTextBlanks().getFixedText().trim())){
                        return new Tokens();
                    }
                }else if( astStmt.toString().equals(stencil.getTextBlanks().getFixedText())){
                    return new Tokens();
                }
                return null;
            }
            if( !astStmt.getComment().isPresent() ) {
                return stencil.deconstruct(astStmt.toString().trim());
            } else{
                Tokens ts = stencil.deconstruct(astStmt.toString());
                if( ts != null ){
                    return ts;
                }
                Statement cpy = astStmt.clone();
                cpy.removeComment();
                ts = stencil.deconstruct(cpy.toString().trim());
                return ts;
            }
        }
        return null;
    }

    @Override
    public List<T> listIn(Node astNode){
        List<T>sts = new ArrayList<>();
        astNode.walk( this.statementClass,
            st -> {
                Select s = select( (Statement)st);
                if( s != null ){
                    sts.add( (T)s.astStatement );
                }
            });
        return sts;
    }

    /**
     * Returns the first Statement that matches the 
     * @param _n
     * @return 
     */
    public Select<T> selectFirstIn( _node _n ){
        Optional<T> f = _n.ast().findFirst(this.statementClass, s -> this.matches(s) );         
        if( f.isPresent()){
            return this.select( f.get() );
        }
        return null;
    }

    /**
     * Returns the first Statement that matches the 
     * @param astNode the 
     * @return a Select containing the Statement and the key value pairs from the prototype
     */
    public Select<T> selectFirstIn( Node astNode ){
        Optional<T> f = astNode.findFirst(this.statementClass, s -> this.matches(s) );         
        if( f.isPresent()){
            return this.select(f.get());
        }
        return null;
    }
    
    /**
     * Returns the first Statement that matches the 
     * @param _n
     * @return 
     */
    public T firstIn( _node _n ){
        Optional<T> f = _n.ast().findFirst(this.statementClass, s -> this.matches(s) );         
        if( f.isPresent()){
            return f.get();
        }
        return null;
    }

    /**
     * Returns the first Statement that matches the 
     * @param astNode the 
     * @return 
     */
    public T firstIn( Node astNode ){
        Optional<T> f = astNode.findFirst(this.statementClass, s -> this.matches(s) );         
        if( f.isPresent()){
            return f.get();
        }
        return null;
    }    
    
    @Override
    public List<T> listIn(_node _n ){
        List<T>sts = new ArrayList<>();
        Walk.in(_n, this.statementClass, st->{
            Select s = select( st);
            if (s != null) {
                sts.add((T) s.astStatement);
            }
        });
        return sts;
    }

    @Override
    public <N extends Node> N forEachIn(N astNode, Consumer<T> statementActionFn){
        astNode.walk(this.statementClass, e-> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                statementActionFn.accept( e);
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<T> statementActionFn){
        Walk.in(_n, this.statementClass, e->{
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                statementActionFn.accept( (T)e);
            }
        });
        return _n;
    }

    /**
     * 
     * @param <N>
     * @param astNode
     * @param selectedActionFn
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Consumer<Select<T>> selectedActionFn){
        astNode.walk(this.statementClass, e-> {
            Select<T> sel = select( e );
            if( sel != null ){
                selectedActionFn.accept( sel );
            }
        });
        return astNode;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param selectedActionFn
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Consumer<Select<T>> selectedActionFn){
        Walk.in(_n, this.statementClass, e->{
            Select<T> sel = select( e );
            if( sel != null ){
                selectedActionFn.accept( sel );
            }
        });
        return _n;
    }

    @Override
    public List<Select<T>> selectListIn(Node astNode ){
        List<Select<T>>sts = new ArrayList<>();
        astNode.walk(this.statementClass, st-> {
            Tokens tokens = deconstruct( st );
            if( tokens != null ){
                sts.add( new Select( (T)st, tokens) );
            }
        });
        return sts;
    }

    /** Write the Statements without comments (for matching, comparison) */
    public static final PrettyPrinterConfiguration NO_COMMENTS = 
        new PrettyPrinterConfiguration()
            .setPrintComments(false).setPrintJavadoc(false);

    @Override
    public List<Select<T>> selectListIn(_node _n ){
        List<Select<T>>sts = new ArrayList<>();
        Walk.in(_n, this.statementClass, st->{
            Tokens tokens = deconstruct(st);
            if (tokens != null) {
                sts.add(new Select(st, tokens));
            }
        });
        return sts;
    }

    @Override
    public <N extends _node> N removeIn(N _n ){
        this.selectListIn(_n).forEach(s-> s.astStatement.removeForced() );
        return _n;
    }

    @Override
    public <N extends Node> N removeIn(N astNode ){
        this.selectListIn(astNode).forEach(s-> s.astStatement.removeForced() );
        return astNode;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param $repl
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, $stmt $repl ){
        $snip $sn = new $snip($repl);
        return replaceIn(_n, $sn);
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param replacement
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, String... replacement ){
        $snip $sn = $snip.of(replacement);
        return replaceIn(_n, $sn);
    }    

   
    /**
     * 
     * @param <N>
     * @param _n
     * @param $protoReplacement
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, $snip $protoReplacement ){
        AtomicInteger ai = new AtomicInteger(0);
        Walk.in(_n, this.statementClass, st->{
            $stmt.Select sel = select( st );
            if( sel != null ){
                //construct the replacement snippet
                List<Statement> replacements = $protoReplacement.construct(sel.args );

                //Statement firstStmt = sel.statements.get(0);
                //Node par = firstStmt.getParentNode().get();
                //NodeWithStatements parentNode = (NodeWithStatements)par;
                //int addIndex = par.getChildNodes().indexOf( firstStmt );
                LabeledStmt ls = Stmt.labeledStmt("$replacement$:{}");
                // we want to add the contents of the replacement to a labeled statement,
                // because, (if we did it INLINE, we could  end up in an infinite loop, searching the
                // tree up to a cursor, then adding some code AT the cursor, then finding a match within the added
                // code, then adding more code, etc. etc.
                // this way, WE ADD A SINGLE LABELED STATEMENT AT THE LOCATION OF THE FIRST MATCH (which contains multiple statements)
                // then, we move to the next statement
                for(int i=0;i<replacements.size(); i++){
                    ls.getStatement().asBlockStmt().addStatement( replacements.get(i) );
                }
                sel.astStatement.replace( ls );
                //parentNode.addStatement(addIndex +1, ls);
                //removeIn all but the first statement
                //sel.statements.forEach( s-> s.removeIn() );
                //System.out.println("PAR AFTER Remove "+ par );
            }
        });
        if( _n instanceof _body._hasBody){
            for(int i=0;i< ai.get(); i++){
                ((_body._hasBody)_n).flattenLabel("$replacement$");
            }
        } else if( _n instanceof _type ){
            ((_type)_n).flattenLabel("$replacement$");
            for(int i=0;i< ai.get(); i++){
                ((_type)_n).flattenLabel("$replacement$");
            }
        }
        return (N)_n;
    }
    
    @Override
    public String toString(){
        return "("+this.statementClass.getSimpleName()+") : \""+ this.stencil+"\"";
    }

    /**
     * 
     * @param <T> 
     */
    public static class Select<T extends Statement> implements $query.selected, 
            $query.selectedAstNode<T> {
        public T astStatement;
        public $args args;

        public Select( T astStatement, Tokens tokens){
            this.astStatement = astStatement;
            this.args = $args.of( tokens );
        }
        
        @Override
        public $args getArgs(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$stmt.Select{"+ System.lineSeparator()+
                Text.indent(astStatement.toString() )+ System.lineSeparator()+
                Text.indent("Args : " + args) + System.lineSeparator()+
                "}";
        }

        @Override
        public T ast() {
            return astStatement;
        }
    }
}