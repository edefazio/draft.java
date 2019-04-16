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
 *     .hardcode$([translator], target, value)
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
    implements Template<T>, $proto<T> {
    
    /**
     * Return the first statement matching the lambda
     * @param <T>
     * @param _n where to look
     * @param constraint
     * @return true if found else false
     */
    public static final <T extends Statement> T first( _node _n, Predicate<T> constraint ){
        Optional<Node> os = _n.ast().stream().filter(n -> n instanceof Statement 
                &&  constraint.test( (T)n) )
                .findFirst();
        if( os.isPresent() ){
            return (T)os.get();
        }
        return null;
    }

    /**
     * Return the first statement of the specific class
     * @param <T>
     * @param _n where to look
     * @param stmtClass
     * @return true if found else false
     */
    public static final <T extends Statement> T first( _node _n, Class<T> stmtClass){
        Optional<Node> os = _n.ast().stream().filter(n -> stmtClass.isAssignableFrom(n.getClass()))
                .findFirst();
        if( os.isPresent() ){
            return (T)os.get();
        }
        return null;
    }
    
    /**
     * Return the first statement matching the lambda
     * @param <T>
     * @param _n where to look
     * @param stmtClass
     * @param constraint
     * @return true if found else false
     */
    public static final <T extends Statement> T first( _node _n, Class<T> stmtClass, Predicate<T> constraint ){
        Optional<Node> os = _n.ast().stream().filter(n -> stmtClass.isAssignableFrom(n.getClass()) 
                &&  constraint.test( (T)n) )
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
     * @param pattern the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final <T extends Statement> T first( _node _n, String... pattern ){
        return (T)$stmt.of(pattern).firstIn(_n);
    }

    /**
     * Return the first instance matching the proto and return it or null
     * @param <T>
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final <T extends Statement> T first( _node _n, T proto ){
        return (T)$stmt.of(proto).firstIn(_n);
    }
    
    /**
     * Return the first instance matching the proto and return it or null
     * @param <T>
     * @param _n where to look
     * @param pattern the code of the Statement we're looking for
     * @param constraint
     * @return true if found else false
     */
    public static final <T extends Statement> T first( _node _n, String pattern, Predicate<T>constraint){
        return (T)$stmt.of(pattern).constraint(constraint).firstIn(_n);
    }
    
    /**
     * Return the first instance matching the proto and return it or null
     * @param <T>
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @param constraint
     * @return true if found else false
     */
    public static final <T extends Statement> T first( _node _n, T proto, Predicate<T>constraint){
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
     * @param pattern the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final <T extends Statement> Select<T> selectFirst( _node _n, String... pattern ){
        return (Select<T>)$stmt.of(pattern).selectFirstIn(_n);
    }
    
    /**
     * Does this stmt template appear in the root node?
     * @param <T>
     * @param _n where to look
     * @param pattern the code of the Statement we're looking for
     * @param constraint
     * @return true if found else false
     */
    public static final <T extends Statement> Select<T> selectFirst( _node _n, String pattern, Predicate<T>constraint ){
        return (Select<T>)$stmt.of(pattern).constraint(constraint).selectFirstIn(_n);
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
     * Returns a list of Select<T> that occur in the _node _n
     * @param <T>
     * @param _n where to look
     * @param proto the prototype of the Statement we're looking for
     * @return a List of Select<T>
     */
    public static final <T extends Statement> List<Select<T>> selectList( _node _n, $stmt<T> proto){
        return proto.selectListIn(_n);
    }
    
    /**
     * 
     * @param <T>
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <T extends Statement, N extends _node> List<Select<T>> selectList( N _n, String pattern ){
        return $stmt.of(pattern).selectListIn(_n);
    }
    
    /**
     * 
     * @param <T>
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <T extends Statement, N extends _node> List<Select<T>> selectList( N _n, String pattern, Predicate<T>constraint){
        return $stmt.of(pattern).constraint(constraint).selectListIn(_n);
    }
    
    /**
     * Does this stmt template appear in the root node?
     * @param <T>
     * @param _n where to look
     * @param pattern the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final <T extends Statement> List<Select<T>> selectList( _node _n, String... pattern ){
        return $stmt.of(pattern).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node, T extends Statement> List<T> list( N _n, String... pattern ){
        return $stmt.of(pattern).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param stmtClass
     * @return 
     */
    public static final <N extends _node, T extends Statement> List<T> list( N _n, Class<T> stmtClass ){
        return $stmt.of( (s)-> stmtClass.isAssignableFrom(s.getClass()) ).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node, T extends Statement> List<T> list( N _n, String pattern, Predicate<T> constraint){
        return $stmt.of(pattern).constraint(constraint).listIn(_n);
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
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param stmtClass
     * @param statementFn
     * @return 
     */
    public static final <N extends _node, T extends Statement> N forEach( N _n, Class<T>stmtClass, Consumer<T> statementFn){
        return (N)$stmt.of( (s)-> stmtClass.isAssignableFrom(s.getClass()) ).forEachIn(_n, statementFn);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param pattern
     * @param statementFn
     * @return 
     */
    public static final <N extends _node, T extends Statement> N forEach( N _n, String pattern, Consumer<T> statementFn){
        return (N)$stmt.of(pattern).forEachIn(_n, statementFn);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param pattern
     * @param constraint
     * @param statementFn
     * @return 
     */
    public static final <N extends _node, T extends Statement> N forEach( N _n, String pattern, Predicate<T> constraint, Consumer<T> statementFn){
        return (N)$stmt.of(pattern).constraint(constraint).forEachIn(_n, statementFn);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param proto
     * @param statementFn
     * @return 
     */
    public static final <N extends _node, T extends Statement> N forEach( N _n, T proto , Consumer<T> statementFn){
        return (N)$stmt.of(proto).forEachIn(_n, statementFn);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param proto
     * @param constraint
     * @param statementFn
     * @return 
     */
    public static final <N extends _node, T extends Statement> N forEach( N _n, T proto, Predicate<T> constraint, Consumer<T> statementFn){
        return (N)$stmt.of(proto).constraint(constraint).forEachIn(_n, statementFn);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param proto
     * @param statementFn
     * @return 
     */
    public static final <N extends _node, T extends Statement> N forEach( N _n, $stmt<T> proto, Consumer<T> statementFn){
        return proto.forEachIn(_n, statementFn);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param pattern
     * @param selectActionFn
     * @return 
     */
    public static final <N extends _node, T extends Statement> N forSelected( N _n, String pattern, Consumer<Select<T>> selectActionFn){
        return (N)$stmt.of(pattern).forEachIn(_n, selectActionFn);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param pattern
     * @param constraint
     * @param selectActionFn
     * @return 
     */
    public static final <N extends _node, T extends Statement> N forSelected( N _n, String pattern, Predicate<T> constraint, Consumer<Select<T>> selectActionFn){
        return (N)$stmt.of(pattern).constraint(constraint).forSelectedIn(_n, selectActionFn);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param proto
     * @param selectActionFn
     * @return 
     */
    public static final <N extends _node, T extends Statement> N forSelected( N _n, T proto , Consumer<Select<T>> selectActionFn){
        return (N)$stmt.of(proto).forSelectedIn(_n, selectActionFn);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param proto
     * @param constraint
     * @param selectActionFn
     * @return 
     */
    public static final <N extends _node, T extends Statement> N forSelected( N _n, T proto, Predicate<T> constraint, Consumer<Select<T>> selectActionFn){
        return (N)$stmt.of(proto).constraint(constraint).forSelectedIn(_n, selectActionFn);
    }
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param proto
     * @param selectActionFn
     * @return 
     */
    public static final <N extends _node, T extends Statement> N forSelected( N _n, $stmt<T> proto, Consumer<Select<T>> selectActionFn){
        return proto.forSelectedIn(_n, selectActionFn);
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
     * @param pattern
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String pattern ){
        return (N)$stmt.of(pattern).removeIn(_n);
    }    
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node, T extends Statement> N remove( N _n, String pattern, Predicate<T> constraint){
        return (N)$stmt.of(pattern).constraint(constraint).removeIn(_n);
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
    public static final <N extends _node, T extends Statement> N remove( N _n, T proto, Predicate<T> constraint){
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
     * @param pattern
     * @return 
     */
    public static final <N extends Node> N remove( N astNode, String... pattern ){
        return (N)$stmt.of(pattern).removeIn(astNode);
    }    
    
    /**
     * 
     * @param <N>
     * @param <T>
     * @param astNode
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends Node, T extends Statement> N remove( N astNode, String pattern, Predicate<T> constraint){
        return (N)$stmt.of(pattern).constraint(constraint).removeIn(astNode);
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
     * @param targetPattern
     * @param replacePattern
     * @return 
     */
    public static final <N extends _node> N replace(N _n, String targetPattern, String replacePattern){
        return (N)$stmt.of(targetPattern)
            .replaceIn(_n, $stmt.of(replacePattern));
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
    public static <T extends Object> $stmt of( Expr.Command proto ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return fromStackTrace( ste );
    }
    
    /**
     * 
     * @param <T>
     * @param proto
     * @return 
     */
    public static <T extends Object> $stmt of( Consumer<T> proto ){
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
    public static <T extends Object, U extends Object> $stmt of( BiConsumer<T,U> proto ){
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
    public static <T extends Object, U extends Object, V extends Object> $stmt of(TriConsumer<T, U, V> proto ){
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
     * @param pattern
     * @return 
     */
    public static $stmt of(String...pattern ){
        Statement st = Stmt.of(pattern);
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
     * @param pattern
     * @return and AssertStmt with the code
     */
    public static $stmt<AssertStmt> assertStmt(String... pattern ) {
        return new $stmt( Stmt.assertStmt(pattern));
    }

    /**
     * i.e."assert(1==1);"
     * @param pattern
     * @param constraint
     * @return and AssertStmt with the code
     */
    public static $stmt<AssertStmt> assertStmt(String pattern, Predicate<AssertStmt> constraint) {
        return new $stmt( Stmt.assertStmt(pattern) ).constraint(constraint);
    }
    
    /**
     * NOTE: If you omit the opening and closing braces { }, they will be added
     *
     * i.e."{ int i=1; return i;}"
     * @param constraint
     * @return the BlockStmt
     */
    public static $stmt<BlockStmt> blockStmt(Predicate<BlockStmt> constraint) {
        return new $stmt( Stmt.block("{ a(); }"))
                .$(Stmt.block("{ a(); }"), "any")
                .constraint(constraint);
    }
    
    /**
     * NOTE: If you omit the opening and closing braces { }, they will be added
     *
     * i.e."{ int i=1; return i;}"
     * @param pattern the code making up the blockStmt
     * @return the BlockStmt
     */
    public static $stmt<BlockStmt> blockStmt(String... pattern ) {
        return new $stmt( Stmt.block(pattern));
    }
    
    /**
     * NOTE: If you omit the opening and closing braces { }, they will be added
     *
     * i.e."{ int i=1; return i;}"
     * @param pattern the code making up the blockStmt
     * @param constraint
     * @return the BlockStmt
     */
    public static $stmt<BlockStmt> blockStmt(String pattern, Predicate<BlockStmt> constraint) {
        return new $stmt( Stmt.block(pattern)).constraint(constraint);
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
     * @param pattern String representing the break of
     * @return the breakStmt
     */
    public static $stmt<BreakStmt> breakStmt(String... pattern ) {
        return new $stmt( Stmt.breakStmt(pattern));
    }

    /**
     * i.e."break;" or "break outer;"
     * @param pattern String representing the break of
     * @param constraint
     * @return the breakStmt
     */
    public static $stmt<BreakStmt> breakStmt(String pattern, Predicate<BreakStmt> constraint) {
        return new $stmt( Stmt.breakStmt(pattern)).constraint(constraint);
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
     * @param pattern
     * @return 
     */
    public static $stmt<ContinueStmt> continueStmt(String... pattern ) {
        return new $stmt( Stmt.continueStmt(pattern));
    }

    
    /** 
     * i.e."continue outer;" 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $stmt<ContinueStmt> continueStmt(String pattern, Predicate<ContinueStmt> constraint) {
        return new $stmt( Stmt.continueStmt(pattern)).constraint(constraint);
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
     * @param pattern
     * @return 
     */
    public static $stmt<DoStmt> doStmt(String... pattern ) {
        return new $stmt( Stmt.doStmt(pattern));
    }

    /** 
     * i.e."do{ System.out.println(1); }while( a < 100 );" 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $stmt<DoStmt> doStmt(String pattern, Predicate<DoStmt> constraint) {
        return new $stmt( Stmt.doStmt(pattern)).constraint(constraint);
    }
    
    /**
     * 
     * @return 
     */
    public static $stmt<EmptyStmt> emptyStmt(){
        return new $stmt( new EmptyStmt() );
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
     * @param pattern
     * @return 
     */
    public static $stmt<ExplicitConstructorInvocationStmt> ctorInvocationStmt(String... pattern ) {
        return new $stmt( Stmt.ctorInvocationStmt(pattern));
    }

    /** 
     * i.e."this(100,2900);" 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $stmt<ExplicitConstructorInvocationStmt> ctorInvocationStmt(String pattern, Predicate<ExplicitConstructorInvocationStmt> constraint) {
        return new $stmt( Stmt.ctorInvocationStmt(pattern)).constraint(constraint);
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
     * @param pattern
     * @return 
     */
    public static $stmt<ExpressionStmt> expressionStmt( String... pattern ) {
        return new $stmt( Stmt.expressionStmt(pattern));
    }
    
    /** 
     * i.e."s += t;" 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $stmt<ExpressionStmt> expressionStmt( String pattern, Predicate<ExpressionStmt> constraint) {
        return new $stmt( Stmt.expressionStmt(pattern)).constraint(constraint);
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
     * @param pattern
     * @return 
     */
    public static $stmt<ForStmt> forStmt( String... pattern ) {
        return new $stmt( Stmt.forStmt(pattern));
    }
    
    /** 
     * i.e."for(int i=0; i<100;i++) {...}" 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $stmt<ForStmt> forStmt( String pattern, Predicate<ForStmt> constraint ) {
        return new $stmt( Stmt.forStmt(pattern)).constraint(constraint);
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
     * @param pattern
     * @return 
     */
    public static $stmt<ForEachStmt> forEachStmt( String... pattern ) {
        return new $stmt( Stmt.forEachStmt(pattern));
    }
    
    /** 
     * i.e."for(String element:arr){...}" 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $stmt<ForEachStmt> forEachStmt( String pattern, Predicate<ForEachStmt> constraint) {
        return new $stmt( Stmt.forEachStmt(pattern)).constraint(constraint);
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
     * @param pattern
     * @return 
     */
    public static $stmt<IfStmt> ifStmt( String... pattern ) {
        return new $stmt( Stmt.ifStmt(pattern));
    }
    
    /** 
     * i.e."if(a==1){...}" 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $stmt<IfStmt> ifStmt( String pattern, Predicate<IfStmt> constraint) {
        return new $stmt( Stmt.ifStmt(pattern)).constraint(constraint);
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
     * @param pattern
     * @return 
     */
    public static $stmt<LabeledStmt> labeledStmt( String... pattern ) {
        return new $stmt( Stmt.labeledStmt(pattern));
    }

    /** 
     * i.e."outer:   start = getValue();" 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $stmt<LabeledStmt> labeledStmt( String pattern, Predicate<LabeledStmt> constraint) {
        return new $stmt( Stmt.labeledStmt(pattern)).constraint(constraint);
    }
    
    
    /**
     * i.e."class C{ int a, b; }"
     * @param constraint
     * @return the AST implementation
     */
    public static $stmt<LocalClassDeclarationStmt> localClassStmt( Predicate<LocalClassDeclarationStmt> constraint) {
        return new $stmt( Stmt.localClass( "class C{}"))
                .$(Stmt.localClass("class C{}"), "any")
                .constraint(constraint);
    }
    
    /**
     * Converts from a String to a LocalClass
     * i.e. "class C{ int a, b; }"
     * @param pattern the code that represents a local class
     * @return the AST implementation
     */
    public static $stmt<LocalClassDeclarationStmt> localClassStmt( String... pattern ) {
        return new $stmt( Stmt.localClass(pattern));
    }
    
    /**
     * Converts from a String to a LocalClass
     * i.e."class C{ int a, b; }"
     * @param pattern the code that represents a local class
     * @param constraint
     * @return the AST implementation
     */
    public static $stmt<LocalClassDeclarationStmt> localClassStmt( String pattern, Predicate<LocalClassDeclarationStmt> constraint) {
        return new $stmt( Stmt.localClass(pattern)).constraint(constraint);
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
     * @param pattern
     * @return 
     */
    public static $stmt<ReturnStmt> returnStmt( String... pattern ) {
        return new $stmt( Stmt.returnStmt(pattern));
    }
    
    /** 
     * i.e."return VALUE;" 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $stmt<ReturnStmt> returnStmt( String pattern, Predicate<ReturnStmt> constraint ) {
        return new $stmt( Stmt.returnStmt(pattern)).constraint(constraint);
    }

    /**
     * 
     * @param pattern
     * @return 
     */
    public static $stmt<SwitchStmt> switchStmt( String... pattern ) {
        return new $stmt( Stmt.switchStmt(pattern));
    }

    /**
     * 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $stmt<SwitchStmt> switchStmt( String pattern, Predicate<SwitchStmt> constraint) {
        return new $stmt( Stmt.switchStmt(pattern)).constraint(constraint);
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
     * @param pattern
     * @return 
     */
    public static $stmt<SynchronizedStmt> synchronizedStmt( String... pattern ) {
        return new $stmt( Stmt.synchronizedStmt(pattern));
    }
    
    /**
     * 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $stmt<SynchronizedStmt> synchronizedStmt( String pattern, Predicate<SynchronizedStmt> constraint ) {
        return new $stmt( Stmt.synchronizedStmt(pattern)).constraint(constraint);
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
     * @param pattern
     * @return 
     */
    public static $stmt<ThrowStmt> throwStmt( String... pattern ) {
        return new $stmt( Stmt.throwStmt(pattern));
    }
    
    /**
     * 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $stmt<ThrowStmt> throwStmt( String pattern, Predicate<ThrowStmt> constraint) {
        return new $stmt( Stmt.throwStmt(pattern)).constraint(constraint);
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
     * @param pattern
     * @return 
     */
    public static $stmt<TryStmt> tryStmt( String... pattern ) {
        return new $stmt( Stmt.tryStmt(pattern));
    }

    /** 
     * i.e."try{ clazz.getMethod("fieldName"); }" 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $stmt<TryStmt> tryStmt( String pattern, Predicate<TryStmt> constraint ) {
        return new $stmt( Stmt.tryStmt(pattern)).constraint(constraint);
    }
    
    /** 
     * i.e."while(i< 1) { ...}"
     * @param pattern
     * @return 
     */
    public static $stmt<WhileStmt> whileStmt( String... pattern ) {
        return new $stmt( Stmt.whileStmt(pattern));
    }

    /** 
     * i.e."while(i< 1) { ...}"
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $stmt<WhileStmt> whileStmt( String pattern, Predicate<WhileStmt> constraint ) {
        return new $stmt( Stmt.whileStmt(pattern)).constraint(constraint);
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
    public Stencil commentPattern;

    /**
     * Optional matching predicate applied to matches to ensure 
     * not only pattern match
     */
    public Predicate<T> constraint = t-> true;
    
    /** The stencil representing the statement */
    public Stencil stmtPattern;

    /** the class of the statement */
    public Class<T> statementClass;

    public $stmt( T st ){
        this.statementClass = (Class<T>)st.getClass();
        if( st.getComment().isPresent() ){
            Comment c = st.getComment().get();
            this.commentPattern = Stencil.of( c.toString() );
        }
        this.stmtPattern = Stencil.of( st.toString(NO_COMMENTS) );
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
        if( this.commentPattern != null ){
            return (T)Stmt.of(Stencil.of(commentPattern, this.stmtPattern ).fill(Translator.DEFAULT_TRANSLATOR, values) );
        }
        String str = stmtPattern.fill(Translator.DEFAULT_TRANSLATOR, values);
        return (T)Stmt.of( str);
    }

    @Override
    public $stmt $(String target, String $name ) {
        if( this.commentPattern != null ) {
            this.commentPattern = this.commentPattern.$(target, $name);
        }
        this.stmtPattern = this.stmtPattern.$(target, $name);
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
        if( this.commentPattern != null ){
            return (T)Stmt.of(Stencil.of(commentPattern, stmtPattern).fill(t, values) );
        }
        return (T)Stmt.of(stmtPattern.fill(t, values));
    }

    @Override
    public T construct( Object...keyValues ){
        if( this.commentPattern != null ){
            return (T)Stmt.of(Stencil.of(commentPattern, stmtPattern).construct( keyValues) );
        }
        return (T)Stmt.of(stmtPattern.construct( Tokens.of(keyValues)));
    }
    
    @Override
    public T construct( Translator t, Object...keyValues ){
        if( this.commentPattern != null ){
            return (T)Stmt.of(Stencil.of(commentPattern, stmtPattern).construct(t, Tokens.of(keyValues) ) );
        }
        return (T)Stmt.of(stmtPattern.construct( t, Tokens.of(keyValues) ));
    }

    @Override
    public T construct( Map<String,Object> tokens ){
        if( this.commentPattern != null ){
            return (T)Stmt.of(Stencil.of(commentPattern, stmtPattern).construct( Translator.DEFAULT_TRANSLATOR, tokens ));
        }
        return (T)Stmt.of(stmtPattern.construct( Translator.DEFAULT_TRANSLATOR, tokens ));
    }
    
    /**
     * 
     * @param _n
     * @return 
     */
    public T construct( _node _n ){
        if( this.commentPattern != null ){
            return (T)Stmt.of(Stencil.of(commentPattern, stmtPattern).construct(_n.deconstruct()) );
        }
        return (T)$stmt.this.construct(_n.deconstruct());
    }

    @Override
    public T construct( Translator t, Map<String,Object> tokens ){
        if( this.commentPattern != null ){
            return (T)Stmt.of(Stencil.of(commentPattern, stmtPattern).construct( t, tokens ));
        }
        return (T)Stmt.of(stmtPattern.construct( t, tokens ));
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
        return select(astStmt) != null;
    }

    @Override
    public List<String> list$(){
        if( this.commentPattern != null ){
            return Stencil.of(commentPattern, stmtPattern).list$();
        }
        return this.stmtPattern.list$();
    }

    @Override
    public List<String> list$Normalized(){
        if( this.commentPattern != null ){
            return Stencil.of(commentPattern, stmtPattern).list$Normalized();
        }
        return this.stmtPattern.list$Normalized();
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $stmt hardcode$( Tokens kvs ) {
        return hardcode$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $stmt hardcode$( Object... keyValues ) {
        return hardcode$( Translator.DEFAULT_TRANSLATOR, Tokens.of( keyValues ) );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param translator translates values to be hardcoded into the Stencil
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $stmt hardcode$( Translator translator, Object... keyValues ) {
        return hardcode$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $stmt hardcode$( Translator translator, Tokens kvs ) {
        if( this.commentPattern != null ){
            this.commentPattern = this.commentPattern.hardcode$(translator, kvs);
            this.stmtPattern = this.stmtPattern.hardcode$(translator,kvs);
        } else {
            this.stmtPattern = this.stmtPattern.hardcode$(translator, kvs);
        }
        return this;
    }

    /**
     * 
     * @param stmt
     * @return 
     */
    public Select<T> select(String...stmt){
        try{
            return select(Stmt.of(stmt));
        }catch(Exception e){
            return null;
        }
    }
    
    /**
     * 
     * @param astStmt
     * @return 
     */
    public Select<T> select( Statement astStmt ){
         if( statementClass.isAssignableFrom(astStmt.getClass())){
            if( ! constraint.test((T) astStmt)){
                return null;
            }
            if( !this.stmtPattern.getTextBlanks().hasBlanks()){ //if it's a static template
                if( astStmt.getComment().isPresent() ){ //removeIn any comments before checking                    
                    Tokens tks = stmtPattern.deconstruct(astStmt.toString());
                    if( tks != null ){
                        return new Select( astStmt, $args.of(tks) );
                        //return $args.of(tks);
                    }
                    //if the statement HAS a comment and the template does not
                    Statement cpy = astStmt.clone();
                    cpy.removeComment();
                    if( cpy.toString().trim().equals(stmtPattern.getTextBlanks().getFixedText().trim())){
                        //return $args.of(Tokens.of());
                        return new Select( astStmt, $args.of( new Tokens()) );
                    }
                }else if( astStmt.toString().equals(stmtPattern.getTextBlanks().getFixedText())){
                    return new Select( astStmt, $args.of( new Tokens()) );
                    //return $args.of(Tokens.of());
                }
                return null;
            }
            if( !astStmt.getComment().isPresent() ) {
                Tokens ts = stmtPattern.deconstruct(astStmt.toString().trim());
                if( ts != null ){
                    //return $args.of(ts);
                    return new Select( astStmt, $args.of(ts) );
                }
            } else{
                Tokens ts = stmtPattern.deconstruct(astStmt.toString());
                if( ts != null ){
                    //return $args.of(ts);
                    return new Select( astStmt, $args.of(ts) );
                }
                Statement cpy = astStmt.clone();
                cpy.removeComment();
                ts = stmtPattern.deconstruct(cpy.toString().trim());
                if( ts != null ){
                    return new Select( astStmt, $args.of(ts) );
                }
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
            return select( f.get() );
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
     * @param selectConstraint
     * @return 
     */
    public Select<T> selectFirstIn( _node _n, Predicate<Select<T>> selectConstraint ){
        Optional<T> f = _n.ast().findFirst(this.statementClass, s -> {
            Select<T> sel = this.select(s); 
            return sel != null && selectConstraint.test(sel);
            });         
        if( f.isPresent()){
            return select( f.get() );
        }
        return null;
    }

    /**
     * Returns the first Statement that matches the 
     * @param astNode the 
     * @param selectConstraint 
     * @return a Select containing the Statement and the key value pairs from the prototype
     */
    public Select<T> selectFirstIn( Node astNode, Predicate<Select<T>> selectConstraint ){
        Optional<T> f = astNode.findFirst(this.statementClass, s -> {
            Select<T> sel = this.select(s); 
            return sel != null && selectConstraint.test(sel);
            });                         
                //s -> this.matches(s) );         
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
            if( select(e)  != null ){
                statementActionFn.accept( e);
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<T> statementActionFn){
        Walk.in(_n, this.statementClass, e->{
            //$args tokens = deconstruct( e );
            if( select(e) != null ){
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

    /**
     * 
     * @param <N>
     * @param astNode
     * @param selectConstraint
     * @param selectedActionFn
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Predicate<Select<T>> selectConstraint, Consumer<Select<T>> selectedActionFn){
        astNode.walk(this.statementClass, e-> {
            Select<T> sel = select( e );
            if( sel != null  && selectConstraint.test(sel) ){
                selectedActionFn.accept( sel );
            }
        });
        return astNode;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConstraint
     * @param selectedActionFn
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Predicate<Select<T>> selectConstraint, Consumer<Select<T>> selectedActionFn){
        Walk.in(_n, this.statementClass, e->{
            Select<T> sel = select( e );
            if( sel != null && selectConstraint.test(sel)){
                selectedActionFn.accept( sel );
            }
        });
        return _n;
    }
    
    /** Write the Statements without comments (for matching, comparison) */
    public static final PrettyPrinterConfiguration NO_COMMENTS = 
        new PrettyPrinterConfiguration()
            .setPrintComments(false).setPrintJavadoc(false);

    @Override
    public List<Select<T>> selectListIn(Node astNode ){
        List<Select<T>>sts = new ArrayList<>();
        astNode.walk(this.statementClass, st-> {
            //$args tokens = deconstruct( st );
            Select sel = select(st);
            if( sel != null ){
                sts.add( sel); //new Select( (T)st, tokens) );
            }
        });
        return sts;
    }
    
    @Override
    public List<Select<T>> selectListIn(_node _n ){
        List<Select<T>>sts = new ArrayList<>();
        Walk.in(_n, this.statementClass, st->{
            //$args tokens = deconstruct(st);
            Select sel = select(st);
            if (sel != null) {
                sts.add(sel);
            }
        });
        return sts;
    }

    public List<Select<T>> selectListIn(Node astNode, Predicate<Select<T>> selectConstraint ){
        List<Select<T>>sts = new ArrayList<>();
        astNode.walk(this.statementClass, st-> {
            //$args tokens = deconstruct( st );
            Select sel = select(st);
            if( sel != null && selectConstraint.test(sel)){
                sts.add( sel); //new Select( (T)st, tokens) );
            }
        });
        return sts;
    }
    
    public List<Select<T>> selectListIn(_node _n, Predicate<Select<T>> selectConstraint ){
        List<Select<T>>sts = new ArrayList<>();
        Walk.in(_n, this.statementClass, st->{
            //$args tokens = deconstruct(st);
            Select sel = select(st);
            if (sel != null && selectConstraint.test(sel)){
                sts.add(sel);
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
     * @param statment_s
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, String... statment_s ){
        $snip $sn = $snip.of(statment_s);
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
        return "("+this.statementClass.getSimpleName()+") : \""+ this.stmtPattern+"\"";
    }

    /**
     * 
     * @param <T> 
     */
    public static class Select<T extends Statement> implements $proto.selected, 
            $proto.selectedAstNode<T> {
        
        public T astStatement;
        public $args args;
        
        public Select( T astStatement, $args tokens){
            this.astStatement = astStatement;
            this.args = tokens;
        }
        
        @Override
        public $args getArgs(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$stmt.Select{"+ System.lineSeparator()+
                Text.indent(astStatement.toString() )+ System.lineSeparator()+
                Text.indent("$args : " + args) + System.lineSeparator()+
                "}";
        }

        @Override
        public T ast() {
            return astStatement;
        }
    }
}