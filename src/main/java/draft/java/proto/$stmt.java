package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import draft.*;
import draft.java.*;
import draft.java.Expr.QuadConsumer;
import draft.java.Expr.QuadFunction;
import draft.java.Expr.TriConsumer;
import draft.java.Expr.TriFunction;
import draft.java._model._node;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.*;

/**
 * Template of a Java {@link Statement} that can be
 *
 * @param <T>
 */
public final class $stmt<T extends Statement>
        implements Template<T>, $query<T> {

    /**
     * Does this stmt template appear in the root node?
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final boolean isIn( _node _n, String... proto ){
        return isIn(_n, Stmt.of(proto));
    }
    
    /**
     * Does this stmt template appear in the root node?
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final boolean isIn( _node _n, Statement proto ){
        return isIn(_n, $stmt.of(proto));
    }
    
    /**
     * 
     * @param _n
     * @param proto
     * @return 
     */
    public static final boolean isIn( _node _n, Expr.Command proto ){
        LambdaExpr le = Expr.lambda( Thread.currentThread().getStackTrace()[2] );
        return isIn(_n, $stmt.of(le.getBody()));
    }
    
    /**
     * 
     * @param <T>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <T extends Object> boolean isIn( _node _n, Consumer<T> proto ){
        LambdaExpr le = Expr.lambda( Thread.currentThread().getStackTrace()[2] );
        return isIn(_n, $stmt.of(le.getBody()));
    }
    
    /**
     * 
     * @param <A>
     * @param <B>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <A extends Object, B extends Object> boolean isIn( _node _n, BiConsumer<A,B> proto ){
        LambdaExpr le = Expr.lambda( Thread.currentThread().getStackTrace()[2] );
        return isIn(_n, $stmt.of(le.getBody()));
    }
    
    /**
     * 
     * @param <A>
     * @param <B>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <A extends Object, B extends Object> boolean isIn( _node _n, Function<A,B> proto ){
        LambdaExpr le = Expr.lambda( Thread.currentThread().getStackTrace()[2] );
        return isIn(_n, $stmt.of(le.getBody()));
    }
    
    /**
     * 
     * @param <A>
     * @param <B>
     * @param <C>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <A extends Object, B extends Object, C extends Object> boolean isIn( _node _n, BiFunction<A,B,C> proto ){
        LambdaExpr le = Expr.lambda( Thread.currentThread().getStackTrace()[2] );
        return isIn(_n, $stmt.of(le.getBody()));
    }
    
    /**
     * 
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <D>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <A extends Object, B extends Object, C extends Object, D extends Object> boolean isIn( _node _n, TriFunction<A,B,C,D> proto ){
        LambdaExpr le = Expr.lambda( Thread.currentThread().getStackTrace()[2] );
        return isIn(_n, $stmt.of(le.getBody()));
    }
    
        /**
     * 
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <D>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <A extends Object, B extends Object, C extends Object, D extends Object, E extends Object> boolean isIn( _node _n, QuadFunction<A,B,C,D,E> proto ){
        LambdaExpr le = Expr.lambda( Thread.currentThread().getStackTrace()[2] );
        return isIn(_n, $stmt.of(le.getBody()));
    }
    
    /**
     * Does this stmt template appear in the root node?
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final boolean isIn( _node _n, $stmt proto){
        return list(_n, proto).size() > 0;
    }    
    
    /**
     * Does this stmt template appear in the root node?
     * @param <T>
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final <T extends Statement> T first( _node _n, String... proto ){
        return (T)$stmt.of(proto).firstIn(_n);
    }
    
    /**
     * Does this stmt template appear in the root node?
     * @param <T>
     * @param _n where to look
     * @param proto the code of the Statement we're looking for
     * @return true if found else false
     */
    public static final <T extends Statement> T first( _node _n, Statement proto ){
        return (T)$stmt.of(proto).firstIn(_n);
    }
    
    /**
     * 
     * @param <T>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <T extends Statement> T first( _node _n, Expr.Command proto ){
        LambdaExpr le = Expr.lambda( Thread.currentThread().getStackTrace()[2] );
        return (T)$stmt.of(le.getBody()).firstIn(_n);
    }
    
    /**
     * 
     * @param <A>
     * @param <T>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <A extends Object, T extends Statement> T first( _node _n, Consumer<A> proto ){
        LambdaExpr le = Expr.lambda( Thread.currentThread().getStackTrace()[2] );
        return (T)$stmt.of(le.getBody()).firstIn(_n);
    }
    
    /**
     * 
     * @param <A>
     * @param <B>
     * @param <T>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <A extends Object, B extends Object, T extends Statement> T first( _node _n, BiConsumer<A,B> proto ){
        LambdaExpr le = Expr.lambda( Thread.currentThread().getStackTrace()[2] );
        return (T)$stmt.of(le.getBody()).firstIn(_n);
    }
    
    /**
     * 
     * @param <A>
     * @param <B>
     * @param <T>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <A extends Object, B extends Object, T extends Statement> T first( _node _n, Function<A,B> proto ){
        LambdaExpr le = Expr.lambda( Thread.currentThread().getStackTrace()[2] );
        return (T)$stmt.of(le.getBody()).firstIn(_n);
    }
    
    /**
     * 
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <T>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <A extends Object, B extends Object, C extends Object, T extends Statement> T first( _node _n, BiFunction<A,B,C> proto ){
        LambdaExpr le = Expr.lambda( Thread.currentThread().getStackTrace()[2] );
        return (T)$stmt.of(le.getBody()).firstIn(_n);
    }
    
    /**
     * 
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <D>
     * @param <T>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <A extends Object, B extends Object, C extends Object, D extends Object, T extends Statement> T first( _node _n, TriFunction<A,B,C,D> proto ){
        LambdaExpr le = Expr.lambda( Thread.currentThread().getStackTrace()[2] );
        return (T)$stmt.of(le.getBody()).firstIn(_n);
    }
    
    /**
     * 
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <D>
     * @param <E>
     * @param <T>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <A extends Object, B extends Object, C extends Object, D extends Object, E extends Object, T extends Statement> T first( _node _n, QuadFunction<A,B,C,D,E> proto ){
        LambdaExpr le = Expr.lambda( Thread.currentThread().getStackTrace()[2] );
        return (T) $stmt.of(le.getBody()) 
                .firstIn( _n );
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
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Statement> list( N _n, String proto ){
        return $stmt.of(proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Statement> list( N _n, Statement proto ){
        return $stmt.of(proto).listIn(_n);
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
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param proto
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, Statement proto ){
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
     * @param _n
     * @param protoTarget
     * @param protoReplacement
     * @return 
     */
    public static final <N extends _node> N replace(N _n, Statement protoTarget, Statement protoReplacement){
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
     * @param proto
     * @param constraint
     * @return 
     
    public static $stmt of(String proto, Predicate<Statement> constraint){
        Statement st = Stmt.of(proto);
        return new $stmt( st ).constraint(constraint);
    }
    */ 
    
    /**
     * 
     * @param <T>
     * @param astProto
     * @return 
     */ 
    public static $stmt of(Statement astProto ){
        return new $stmt<>(astProto);
    }
     

    /**
     * 
     * @param <T>
     * @param astProto
     * @param constraint
     * @return 
     
    public static <T extends Statement> $stmt<T> of(T astProto, Predicate<T> constraint){
        return new $stmt<>(astProto).constraint(constraint);
    }
    * */
    
    /**
     * i.e."assert(1==1);"
     * @param proto
     * @return and AssertStmt with the code
     */
    public static $stmt<AssertStmt> assertStmt(String... proto ) {
        return new $stmt( Stmt.assertStmt(proto));
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
     * i.e."break;" or "break outer;"
     * @param proto String representing the break of
     * @return the breakStmt
     */
    public static $stmt<BreakStmt> breakStmt(String... proto ) {
        return new $stmt( Stmt.breakStmt(proto));
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
     * i.e."do{ System.out.println(1); }while( a < 100 );" 
     * @param proto
     * @return 
     */
    public static $stmt<DoStmt> doStmt(String... proto ) {
        return new $stmt( Stmt.doStmt(proto));
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
     * i.e."s += t;" 
     * @param proto
     * @return 
     */
    public static $stmt<ExpressionStmt> expressionStmt( String... proto ) {
        return new $stmt( Stmt.expressionStmt(proto));
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
     * i.e."for(String element:arr){...}" 
     * @param proto
     * @return 
     */
    public static $stmt<ForEachStmt> forEachStmt( String... proto ) {
        return new $stmt( Stmt.forEachStmt(proto));
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
     * i.e."outer:   start = getValue();" 
     * @param proto
     * @return 
     */
    public static $stmt<LabeledStmt> labeledStmt( String... proto ) {
        return new $stmt( Stmt.labeledStmt(proto));
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
     * i.e."return VALUE;" 
     * @param proto
     * @return 
     */
    public static $stmt<ReturnStmt> returnStmt( String... proto ) {
        return new $stmt( Stmt.returnStmt(proto));
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
     * @return 
     */
    public static $stmt<SynchronizedStmt> synchronizedStmt( String... proto ) {
        return new $stmt( Stmt.synchronizedStmt(proto));
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
     * i.e."try{ clazz.getMethod("fieldName"); }" 
     * @param proto
     * @return 
     */
    public static $stmt<TryStmt> tryStmt( String... proto ) {
        return new $stmt( Stmt.tryStmt(proto));
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
     * @param statement
     * @return 
     */
    public boolean matches( Statement statement ){
        return deconstruct(statement) != null;
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
                    sts.add( (T)s.statement );
                }
            });
        return sts;
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
                sts.add((T) s.statement);
            }
        });
        return sts;
    }

    @Override
    public <N extends Node> N forIn(N astNode, Consumer<T> statementActionFn){
        astNode.walk(this.statementClass, e-> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                statementActionFn.accept( e);
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forIn(N _n, Consumer<T> statementActionFn){
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
    public List<Select<T>> listSelectedIn(Node astNode ){
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
    public static final PrettyPrinterConfiguration NO_COMMENTS = new PrettyPrinterConfiguration()
            .setPrintComments(false).setPrintJavadoc(false);

    @Override
    public List<Select<T>> listSelectedIn(_node _n ){
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
        this.listSelectedIn(_n).forEach(s-> s.statement.removeForced() );
        return _n;
    }

    @Override
    public <N extends Node> N removeIn(N astNode ){
        this.listSelectedIn(astNode).forEach(s-> s.statement.removeForced() );
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
     * @param $protoReplacement
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, $snip $protoReplacement ){
        AtomicInteger ai = new AtomicInteger(0);
        Walk.in(_n, this.statementClass, st->{
            $stmt.Select sel = select( st );
            if( sel != null ){
                //construct the replacement snippet
                List<Statement> replacements = $protoReplacement.construct( sel.tokens );

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
                sel.statement.replace( ls );
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

    public static class Select<T extends Statement> implements $query.selected {
        public T statement;
        public Tokens tokens;

        public Select( T statement, Tokens tokens){
            this.statement = statement;
            this.tokens = tokens;
        }

        @Override
        public String toString(){
            return "$stmt{"+ System.lineSeparator()+
                    Text.indent( statement.toString() )+ System.lineSeparator()+
                    Text.indent( "TOKENS : " + tokens) + System.lineSeparator()+
                    "}";
        }
    }
}