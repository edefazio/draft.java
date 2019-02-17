package draft.java.template;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import draft.*;
import draft.java.*;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
//import java.util.function.Function;

/**
 * Template of a Java {@link Statement} that can be
 * filled,
 * composed,
 * matched
 * decomposed
 * selected
 *
 * @param <T>
 */
public final class $stmt<T extends Statement>
        implements Template<T>, $query<T> {

    private static $stmt fromStackTrace( StackTraceElement ste ){
        Statement st = Stmt.from( ste );
        return new $stmt( st );
    }

    public static <T extends Object>  $stmt of( Expr.Command c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return fromStackTrace( ste );
    }

    public static <T extends Object>  $stmt of( Consumer<T> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return fromStackTrace( ste );
    }

    public static <T extends Object, U extends Object>  $stmt of( BiConsumer<T,U> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return fromStackTrace( ste );
    }

    public static <T extends Object, U extends Object, V extends Object>  $stmt of(Expr.TriConsumer<T, U, V> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return fromStackTrace( ste );
    }

    public static <T extends Object, U extends Object, V extends Object, X extends Object>  $stmt of(Expr.QuadConsumer<T, U, V, X> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return fromStackTrace( ste );
    }

    public static $stmt of(String...statement ){
        Statement st = Stmt.of(statement);
        return new $stmt( st );
        //return new $stmt(st.getClass(), st.removeComment().toString());
    }

    public static $stmt of(Statement st ){
        return new $stmt(st); //.getClass(), st.toString());
    }

    /**
     * i.e."assert(1==1);"
     * @param code
     * @return and AssertStmt with the code
     */
    public static $stmt<AssertStmt> assertStmt(String... code ) {
        return new $stmt( Stmt.assertStmt(code));
    }

    /**
     * NOTE: If you omit the opening and closing braces { }, they will be added
     *
     * i.e."{ int i=1; return i;}"
     * @param code the code making up the blockStmt
     * @return the BlockStmt
     */
    public static $stmt<BlockStmt> block(String... code ) {
        return new $stmt( Stmt.block(code));
    }

    /**
     * i.e."break;" or "break outer;"
     * @param code String representing the break of
     * @return the breakStmt
     */
    public static $stmt<BreakStmt> breakStmt(String... code ) {
        return new $stmt( Stmt.breakStmt(code));
    }

    /** i.e. "continue outer;" */
    public static $stmt<ContinueStmt> continueStmt(String... code ) {
        return new $stmt( Stmt.continueStmt(code));
    }

    /** i.e. "do{ System.out.println(1); }while( a < 100 );" */
    public static $stmt<DoStmt> doStmt(String... code ) {
        return new $stmt( Stmt.doStmt(code));
    }

    /** i.e. "this(100,2900);" */
    public static $stmt<ExplicitConstructorInvocationStmt> ctorInvocationStmt(String... code ) {
        return new $stmt( Stmt.ctorInvocationStmt(code));
    }

    /** i.e. "s += t;" */
    public static $stmt<ExpressionStmt> expressionStmt( String... code ) {
        return new $stmt( Stmt.expressionStmt(code));
    }

    /** i.e. "for(int i=0; i<100;i++) {...}" */
    public static $stmt<ForStmt> forStmt( String... code ) {
        return new $stmt( Stmt.forStmt(code));
    }

    /** i.e. "for(String element:arr){...}" */
    public static $stmt<ForEachStmt> forEachStmt( String... code ) {
        return new $stmt( Stmt.forEachStmt(code));
    }

    /** i.e. "if(a==1){...}" */
    public static $stmt<IfStmt> ifStmt( String... code ) {
        return new $stmt( Stmt.ifStmt(code));
    }

    /** i.e. "outer:   start = getValue();" */
    public static $stmt<LabeledStmt> labeledStmt( String... code ) {
        return new $stmt( Stmt.labeledStmt(code));
    }

    /**
     * Converts from a String to a LocalClass
     * i.e. "class C{ int a, b; }"
     * @param code the code that represents a local class
     * @return the AST implementation
     */
    public static $stmt<LocalClassDeclarationStmt> localClass( String... code ) {
        return new $stmt( Stmt.localClass( code));
    }

    /** i.e. "return VALUE;" */
    public static $stmt<ReturnStmt> returnStmt( String... code ) {
        return new $stmt( Stmt.returnStmt(code));
    }

    /**
     *
     * case 1: return 1;
     *
     * case 2: case 3: System.out.println(23); break;
     *
     * @param code
     * @return

    public static $stmt<SwitchEntryStmt> switchCaseStmt(String... code ) {
        return new $stmt( Stmt.switchCaseStmt(code));
    }
    */

    public static $stmt<SwitchStmt> switchStmt( String... code ) {
        return new $stmt( Stmt.switchStmt(code));
    }

    public static $stmt<SynchronizedStmt> synchronizedStmt( String... code ) {
        return new $stmt( Stmt.synchronizedStmt(code));
    }

    public static $stmt<ThrowStmt> throwStmt( String... code ) {
        return new $stmt( Stmt.throwStmt(code));
    }

    /** i.e. "try{ clazz.getMethod("fieldName"); }" */
    public static $stmt<TryStmt> tryStmt( String... code ) {
        return new $stmt( Stmt.tryStmt(code));
    }

    /** i.e. "while(i< 1) { ... }"*/
    public static $stmt<WhileStmt> whileStmt( String... code ) {
        return new $stmt( Stmt.whileStmt(code));
    }

    /**
     * IF the statement has a comment, it is stored separately, because,
     * in situations where we are composing the statement, we want to compose the
     *
     */
    public Stencil commentStencil;

    /** The stencil representing the statement */
    public Stencil stencil;

    public Class<T> statementClass;

    public $stmt( T st ){
        this.statementClass = (Class<T>)st.getClass();
        if( st.getComment().isPresent() ){
            Comment c = st.getComment().get();
            this.commentStencil = Stencil.of( c.toString() );
        }
        this.stencil = Stencil.of( st.toString(NO_COMMENTS) );
    }

    /*
    public $stmt(Class<T>statementClass, Stencil stencil ){
        this.statementClass = statementClass;
        this.stencil = stencil;
    }

    public $stmt(Class<T>statementClass, String stencil ){
        this.statementClass = statementClass;
        this.stencil = Stencil.of(stencil);
    }
    */

    public T fill(Object...values){
        if( this.commentStencil != null ){
            return (T)Stmt.of(
                    Stencil.of(commentStencil, this.stencil ).fill(Translator.DEFAULT_TRANSLATOR, values) );
        }
        String str = stencil.fill(Translator.DEFAULT_TRANSLATOR, values);
        return (T)Stmt.of( str);
    }

    public $stmt $(String target, String $name ) {
        if( this.commentStencil != null ) {
            this.commentStencil = this.commentStencil.$(target, $name);
        }
        this.stencil = this.stencil.$(target, $name);
        return this;
    }

    public $stmt $(Expression expr, String $name ){
        String exprString = expr.toString();
        return $(exprString, $name);
    }

    public T fill(Translator t, Object...values){
        if( this.commentStencil != null ){
            return (T)Stmt.of( Stencil.of(commentStencil, stencil).fill(t, values) );
        }
        return (T)Stmt.of( stencil.fill(t, values));
    }

    public T compose( Object...keyValues ){
        if( this.commentStencil != null ){
            return (T)Stmt.of( Stencil.of(commentStencil, stencil).compose( keyValues) );
        }
        return (T)Stmt.of( stencil.compose( Tokens.of(keyValues)));
    }

    public T compose( _model._node model ){
        if( this.commentStencil != null ){
            return (T)Stmt.of( Stencil.of(commentStencil, stencil).compose(model.componentize()) );
        }
        return (T)compose(model.componentize());
    }

    public T compose( Translator t, Object...keyValues ){
        if( this.commentStencil != null ){
            return (T)Stmt.of( Stencil.of(commentStencil, stencil).compose(t, Tokens.of(keyValues) ) );
        }
        return (T)Stmt.of( stencil.compose( t, Tokens.of(keyValues) ));
    }

    public T compose( Map<String,Object> tokens ){
        if( this.commentStencil != null ){
            return (T)Stmt.of( Stencil.of(commentStencil, stencil).compose( Translator.DEFAULT_TRANSLATOR, tokens ));
        }
        return (T)Stmt.of( stencil.compose( Translator.DEFAULT_TRANSLATOR, tokens ));
    }

    public T compose( Translator t, Map<String,Object> tokens ){
        if( this.commentStencil != null ){
            return (T)Stmt.of( Stencil.of(commentStencil, stencil).compose( t, tokens ));
        }
        return (T)Stmt.of( stencil.compose( t, tokens ));
    }

    public boolean matches( String...stmt ){
        return matches( Stmt.of(stmt));
    }

    public boolean matches( Statement statement ){
        return decompose(statement) != null;
    }

    public List<String> list$(){
        if( this.commentStencil != null ){
            return Stencil.of( commentStencil, stencil).list$();
        }
        return this.stencil.list$();
    }

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

    public $stmt assign$( Translator translator, Tokens kvs ) {
        if( this.commentStencil != null ){
            this.commentStencil = this.commentStencil.assign$(translator, kvs);
            this.stencil = this.stencil.assign$(translator,kvs);
        } else {
            this.stencil = this.stencil.assign$(translator, kvs);
        }
        return this;
    }

    public Select<T> select( Statement st ){
        Tokens ts = decompose( st );
        if( ts != null ){
            return new Select( st, ts );
        }
        return null;
    }

    /**
     * Decompose the statement into tokens, or return null if the statement doesnt match
     *
     *
     * @param statement the statement to partsMap
     * @return Tokens from the stencil, or null if the statement doesnt match
     */
    public Tokens decompose( Statement statement ){
        //System.out.println("Here");
        if( statementClass.isAssignableFrom(statement.getClass())){
            //System.out.println("Same stmt");
            if( !this.stencil.getTextBlanks().hasBlanks()){ //if it's a static template
                //System.out.println("Static template");
                //System.out.println( "testing "+statement+" against "+stencil.getTextBlanks().getFixedText());
                if( statement.getComment().isPresent() ){ //removeIn any comments before checking
                    //System.out.println("Static With Comments");
                    Tokens tks = stencil.decompose( statement.toString());
                    if( tks != null ){
                        return tks;
                    }
                    //if the statement HAS a comment and the template does not
                    Statement cpy = statement.clone();
                    cpy.removeComment();
                    //System.out.println( "testing "+cpy+" against "+stencil.getTextBlanks().getFixedText());
                    if( cpy.toString().trim().equals(stencil.getTextBlanks().getFixedText().trim())){
                        return new Tokens();
                    }
                }else if( statement.toString().equals(stencil.getTextBlanks().getFixedText())){
                    return new Tokens();
                }
                return null;
            }
            //System.out.println( "correct TYPE");
            if( !statement.getComment().isPresent() ) {
                //System.out.println( "checking >>>"+ statement.toString()+"<<");
                //System.out.println( "with     >>>"+ stencil.toString() +"<<");
                return stencil.decompose(statement.toString().trim());
            } else{
                Tokens ts = stencil.decompose(statement.toString());
                if( ts != null ){
                    return ts;
                }
                Statement cpy = statement.clone();
                cpy.removeComment();
                //System.out.println( "testing "+cpy+" against "+stencil.getTextBlanks().getFixedText());
                ts = stencil.decompose(cpy.toString().trim());
                return ts;
            }
            //System.out.println( "got "+ ts +"");
            //return ts;
        }
        return null;
    }

    public List<T> findAllIn(Node n){
        List<T>sts = new ArrayList<>();
        n.walk( this.statementClass,
                st -> {
                    Select s = select( (Statement)st);
                    if( s != null ){
                        sts.add( (T)s.statement );
                    }
                });
        return sts;
    }

    public List<T> findAllIn(_model._node _le ){
        List<T>sts = new ArrayList<>();
        Walk.in( _le, this.statementClass, st->{
            Select s = select( st);
            if (s != null) {
                sts.add((T) s.statement);
            }
        });
        return sts;
    }

    public <N extends Node> N forAllIn(N n, Consumer<T> statementActionFn){
        n.walk(this.statementClass, e-> {
            Tokens tokens = this.stencil.decompose( e.toString());
            if( tokens != null ){
                statementActionFn.accept( e);
            }
        });
        return n;
    }

    public <M extends _model._node> M forAllIn(M _le, Consumer<T> statementActionFn){
        Walk.in( _le, this.statementClass, e->{
            Tokens tokens = this.stencil.decompose( e.toString());
            if( tokens != null ){
                statementActionFn.accept( (T)e);
            }
        });
        return _le;
    }

    public <N extends Node> N forSelectedIn(N n, Consumer<Select<T>> selectedActionFn){
        n.walk(this.statementClass, e-> {
            Select<T> sel = select( e );
            if( sel != null ){
                selectedActionFn.accept( sel );
            }
        });
        return n;
    }

    public <M extends _model._node> M forSelectedIn(M _le, Consumer<Select<T>> selectedActionFn){
        Walk.in( _le, this.statementClass, e->{
            Select<T> sel = select( e );
            if( sel != null ){
                selectedActionFn.accept( sel );
            }
        });
        return _le;
    }

    public List<Select<T>> selectAllIn(Node n ){
        List<Select<T>>sts = new ArrayList<>();
        n.walk(this.statementClass, st-> {
            Tokens tokens = this.stencil.decompose( st.toString(NO_COMMENTS));
            if( tokens != null ){
                sts.add( new Select( (T)st, tokens) );
            }
        });
        return sts;
    }

    /** Write the Statements without comments (for matching, comparison) */
    public static final PrettyPrinterConfiguration NO_COMMENTS = new PrettyPrinterConfiguration()
            .setPrintComments(false).setPrintJavadoc(false);

    public List<Select<T>> selectAllIn(_model._node _t ){
        List<Select<T>>sts = new ArrayList<>();
        Walk.in( _t, this.statementClass, st->{
            Tokens tokens = this.stencil.decompose(st.toString(NO_COMMENTS));
            if (tokens != null) {
                sts.add(new Select(st, tokens));
            }
        });
        return sts;
    }

    public <T extends _model._node> T removeIn(T _e ){
        this.selectAllIn(_e).forEach(s-> s.statement.removeForced() );
        return _e;
    }

    public <N extends Node> N removeIn(N node ){
        this.selectAllIn(node).forEach(s-> s.statement.removeForced() );
        return node;
    }

    public <T extends _model._node> T replaceAllIn(T _le, $stmt $repl ){
        $snip $sn = new $snip($repl);
        return replaceAllIn(_le, $sn);
    }

    public <T extends _model._node> T replaceAllIn(T _le, $snip $repl ){
        AtomicInteger ai = new AtomicInteger(0);
        Walk.in( _le, this.statementClass, st->{
            $stmt.Select sel = select( st );
            if( sel != null ){
                //compose the replacement snippet
                List<Statement> replacements = $repl.compose( sel.tokens );

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
        if( _le instanceof _body._hasBody){
            for(int i=0;i< ai.get(); i++){
                ((_body._hasBody)_le).flattenLabel("$replacement$");
            }
        } else if( _le instanceof _type ){
            ((_type)_le).flattenLabel("$replacement$");
            for(int i=0;i< ai.get(); i++){
                ((_type)_le).flattenLabel("$replacement$");
            }
        }
        return (T)_le;
    }
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

        public String toString(){
            return "$stmt{"+ System.lineSeparator()+
                    Text.indent( statement.toString() )+ System.lineSeparator()+
                    Text.indent( "TOKENS : " + tokens) + System.lineSeparator()+
                    "}";
        }
    }
}