package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithStatements;
import com.github.javaparser.ast.stmt.*;
import draft.*;
import draft.java.*;
import draft.java._model._node;
import draft.java.macro._remove;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Template of a Java code snippet (one or more {@link Statement}s
 *
 * NOTE: although this does not implement the Template<> and $query<> interfaces
 * it follows the same naming conventions
 */
public final class $snip implements Template<List<Statement>>, $proto<List<Statement>> {

    /**
     * 
     * @param <N>
     * @param _n
     * @param sourceProto
     * @param targetProto
     * @return 
     */
    public static final <N extends _node> N replace( N _n, String[] sourceProto, String[] targetProto){
        $snip.of(sourceProto).replaceIn(_n, $snip.of(targetProto));
        return _n;
    } 
        
    /**
     * 
     * @param <N>
     * @param _n
     * @param sourceProto
     * @param targetProto
     * @return 
     */
    public static final <N extends _node> N replace( N _n, String sourceProto, String targetProto){
        $snip.of(sourceProto).replaceIn(_n, $snip.of(targetProto));
        return _n;
    } 
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String... proto){
        $snip.of(proto).removeIn(_n);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Statement> first( N _n, String... proto){
        return $snip.of(proto).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String... proto){
        return $snip.of(proto).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<List<Statement>> list( N _n, String... proto){
        return $snip.of(proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String... proto){
        return $snip.of(proto).selectListIn(_n);
    }
        
    public List<$stmt> $sts = new ArrayList<>();

    /**
     * Build a dynamic code snippet based on the content of a method defined within an anonymous Object
     * <PRE>
     * i.e.
     * $snip $s = $snip.of( new Object(){
     *     int m(String $name$, Integer $init$){
     *         // The code inside is used
     *
     *         if( $name$ == null ){
     *              return $init$;
     *         }
     *         return $name$.hashCode();
     *
     *     }
     * });
     * System.out.println( $s.construct( _field.of("int x;") ));
     * </PRE>
     * @param anonymousObjectWithBody
     * @return the dynamic code snippet
     */
    public static $snip of( Object anonymousObjectWithBody ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject(ste);
        //find the first method that doesnt have removeIn on it and has a BODY
        // to get it's contents
        MethodDeclaration theMethod = (MethodDeclaration)
            oce.getAnonymousClassBody().get().stream().filter(m -> m instanceof MethodDeclaration &&
                !m.isAnnotationPresent(_remove.class) &&
                ((MethodDeclaration) m).getBody().isPresent()).findFirst().get();
        return of( theMethod.getBody().get());
    }

    public static $snip of( String proto ){
        return of(new String[] {proto});
    }

    public static $snip of( Expr.Command c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return of( Expr.lambda(ste));
    }

    public static <T extends Object>  $snip of( Consumer<T> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return of( Expr.lambda(ste));
    }

    public static <T extends Object, U extends Object>  $snip of( Function<T,U> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return of( Expr.lambda(ste));
    }

    public static <T extends Object, U extends Object, V extends Object>  $snip of( BiFunction<T,U, V> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return of( Expr.lambda(ste));
    }

    public static <T extends Object, U extends Object>$snip of( BiConsumer<T,U> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return of( Expr.lambda(ste));
    }

    public static <T extends Object, U extends Object, V extends Object>$snip of( Expr.TriConsumer<T,U,V> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return of( Expr.lambda(ste));
    }

    public static <T extends Object, U extends Object, V extends Object, Z extends Object>$snip of( Expr.QuadConsumer<T,U,V,Z> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return of( Expr.lambda(ste));
    }

    public static $snip of( LambdaExpr astLambda ){
        Statement st = Stmt.from(astLambda);
        return new $snip(st);
    }

    public static $snip of( _body _b ){
        return of( _b.ast() );
    }

    public static $snip of(BlockStmt astBlockStmt ){
        $snip $s = new $snip();
        astBlockStmt.getStatements().forEach(s -> $s.add(s));
        return $s;
    }

    /**
     * 
     * @param protoSnippet
     * @return 
     */
    public static $snip of(String... protoSnippet) {
        $snip $s = new $snip();
        BlockStmt bs = Ast.blockStmt(protoSnippet);

        bs.getStatements().forEach(s -> $s.add(s));
        return $s;
    }

    public $snip(){
    }

    private $snip( Statement astProtoStmt ){
        if( astProtoStmt instanceof BlockStmt ){
            astProtoStmt.asBlockStmt().getStatements().forEach(s -> add(s) );
        } else{
            add(astProtoStmt);
        }
    }

    public $snip($stmt $st){
        add($st);
    }

    public $snip add(Statement astProtoStmt){
        $sts.add(new $stmt(astProtoStmt) );
        return this;
    }

    public $snip add($stmt $st ){
        $sts.add($st);
        return this;
    }

    @Override
    public $snip $(String target, String paramName){
        this.$sts.forEach(s -> s.$(target, paramName));
        return this;
    }

    @Override
    public List<String> list$Normalized(){
        List<String>normalized$ = new ArrayList<>();
        $sts.forEach(s -> {
            List<String> ls = s.list$Normalized();
            ls.forEach( l-> {
                if (!normalized$.contains(l)) {
                    normalized$.add(l);
                }
            });
        });
        return normalized$;
    }

    @Override
    public List<String> list$(){
        List<String>$s = new ArrayList<>();
        $sts.forEach(s -> {
            List<String> ls = s.list$();
            ls.forEach( l-> {
                if (!$s.contains(l)) {
                    $s.add(l);
                }
            });
        });
        return $s;
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $snip assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $snip assign$( Object... keyValues ) {
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
    public $snip assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $snip assign$( Translator translator, Tokens kvs ) {
        this.$sts.forEach( $s -> $s.assign$(translator, kvs));
        return this;
    }
    
    /**
     * 
     * @param tokens
     * @return 
     */
    public List<Statement> construct( Tokens tokens ){
        return $snip.this.construct( Translator.DEFAULT_TRANSLATOR, tokens.map());
    }

    /**
     * 
     * @param _n
     * @return 
     */
    public List<Statement> construct( _node _n ){
        return construct(_n.deconstruct());
    }

    @Override
    public List<Statement> construct( Translator t, Map<String,Object> tokens ){
        List<Statement>sts = new ArrayList<>();
        $sts.forEach(stmt -> {
            if( stmt.statementClass == LabeledStmt.class &&
                    stmt.stencil.getTextBlanks().startsWithText()) {
                    //&&
                    //stmt.stencil.getTextBlanks().getFixedText().startsWith("$") ){
                /* Dynamic labeled Statements are Labeled Statements like this:
                 * $callSuperEquals: eq = super.typesEqual($b$) && eq;
                 *
                 * THEY MUST start with a $ and be Labeled Statements
                 * we process these by checking to see if a parameter
                 * "$callSuperEquals" if the VALUE associated with $doThis is passed into the input,
                 * and the VALUE of $doThis is NOT NULL or NOT Boolean.FALSE
                 */
                String sttext = stmt.stencil.getTextBlanks().getFixedText();
                String name = sttext.substring(0, sttext.indexOf(":") );
                Object val = tokens.get(name );
                if( val instanceof BlockStmt ){
                    //we are filling in with static code
                    ((BlockStmt)val).getStatements().forEach( s-> sts.add(s));
                }
                else if( val instanceof Statement){
                    sts.add( (Statement)val);
                }
                else if( val instanceof String){
                    sts.add( Stmt.of((String)val));
                }
                else if( val != null && val != Boolean.FALSE ){
                    //construct the statement (it
                    LabeledStmt ls = (LabeledStmt)stmt.construct(t, tokens);
                    Statement st = ls.getStatement();
                    if( st instanceof BlockStmt ) {
                        //add each of the statements
                        st.asBlockStmt().getStatements().forEach( s-> sts.add(s));
                    }else{
                        sts.add(st ); //just add the derived statement
                    }
                }
            } else { //it is NOT a dymanically labeled Statement, so just process normally
                sts.add(stmt.construct(t, tokens));
            }
        });
        return sts;
    }

    /**
     * 
     * @param astStmt
     * @return 
     */
    public Select select( Statement astStmt ){
        int idx = 0;
        List<Statement> ss = new ArrayList<>();
        ss.add(astStmt);
        if( this.$sts.size() > 1  ){
            if( !astStmt.getParentNode().isPresent() ) {
                //System.out.println("cant be match, no parent");
                return null; //cant be
            }
            NodeWithStatements nws = (NodeWithStatements)astStmt.getParentNode().get();
            List<Statement>childs = nws.getStatements();
            //List<Node> childs =
            //        statement.getParentNode().get().getChildNodes();
            idx = childs.indexOf(astStmt);
            if( childs.size() - idx < this.$sts.size() ){
                //System.out.println("cant be a match, b/c not enough sibling nodes to match $snip");
                return null; //cant be a match, b/c not enough sibling nodes to match $snip
            }
            for(int i = 1; i<this.$sts.size(); i++){ //add the rest of the statements to the array
                //System.out.println( "adding "+ childs.get(idx+i) );
                ss.add( (Statement)childs.get(idx+i) );
            }
        }
        //check if we can partsMap the first one
        Tokens all = new Tokens();
        for(int i = 0; i< this.$sts.size(); i++) {
            $args ts = this.$sts.get(i).deconstruct(ss.get(i));
            if (ts == null) {
                //System.out.println( "NO Match with >"+ this.$sts.get(i)+ "< against >"+ss.get(i)+"< tokens");
                return null;
            }
            String[] keys = ts.keySet().toArray(new String[0]);
            for(int j=0;j<keys.length;j++){
                if( all.containsKeys(keys[j]) && !all.get( keys[j] ).equals( ts.get(keys[j])) ){
                    //System.out.println( "Inconsistent Key Values");
                    return null; //inconsistent key values
                }
            }
            all.putAll(ts);
        }
        return new Select( ss, all);
    }

    /**
     * Deconstruct the statement(s) into tokens, or return null if the statement 
     * doesnt match
     *
     * @param astStmt the statement to partsMap
     * @return Tokens from the stencil, or null if the statement doesnt match
     */
    public Tokens deconstruct( Statement astStmt ){
        Select s  = select(astStmt );
        if( s != null ){
            return s.args.asTokens();
        }
        return null;
    }

    /**
     * 
     * @param _b
     * @return 
     */
    public boolean matches( _body _b ){
        return matches(_b.getStatement(0));
    }

    /**
     * 
     * @param astStmt
     * @return 
     */
    public boolean matches( Statement astStmt ){
        return deconstruct(astStmt) != null;
    }

    /**
     * Returns the first List<Statement>  that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first List<Statement>  that matches (or null if none found)
     */
    public List<Statement> firstIn( _node _n ){
        return firstIn(_n.ast());        
    }

    /**
     * Returns the first List<Statement>  that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first List<Statement> that matches (or null if none found)
     */
    public List<Statement> firstIn( Node astNode ){
        List<List<Statement>>ss =  listIn(astNode);
        if( ss.isEmpty() ){
            return null;
        }        
        return ss.get(0);        
    }
    
    @Override
    public List<List<Statement>> listIn(Node astNode ){
        List<List<Statement>>sts = new ArrayList<>();
        astNode.walk(this.$sts.get(0).statementClass, st -> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                sts.add( sel.statements );
            }
        });
        return sts;
    }
    
    /**
     * 
     * @param _n
     * @return 
     */
    public Select selectFirstIn( _node _n){
        return selectFirstIn( _n.ast());
    }
    
    /**
     * 
     * @param astNode the top ast node to run
     * @return the Select
     */
    public Select selectFirstIn( Node astNode ){
        Optional<Node> os = astNode.stream().filter(
                n -> n instanceof Statement &&
                    select( (Statement)n ) !=null ).findFirst();
        
        if( os.isPresent() ){
            return select( (Statement)os.get());
        }
        return null;
    }

    @Override
    public List<List<Statement>> listIn(_node _n ){
        List<List<Statement>>sts = new ArrayList<>();
        Walk.in(_n, this.$sts.get(0).statementClass, st -> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                sts.add( sel.statements );
            }
        });
        return sts;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<List<Statement>> statementsConsumer ){
        Walk.in(_n, this.$sts.get(0).statementClass, st -> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                statementsConsumer.accept( sel.statements );
            }
        });
        return _n;
    }

    @Override
    public <N extends Node> N forEachIn(N astNode, Consumer<List<Statement>> statementsConsumer ){
        astNode.walk(this.$sts.get(0).statementClass, st -> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                statementsConsumer.accept( sel.statements );
            }
        });
        return astNode;
    }

    @Override
    public List<Select> selectListIn(_node _n ){
        List<Select>sts = new ArrayList<>();
        Walk.in(_n, this.$sts.get(0).statementClass, st -> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                sts.add( sel );
            }
        });
        return sts;
    }

    @Override
    public List<Select> selectListIn(Node astNode ){
        List<Select>sts = new ArrayList<>();
        astNode.walk(this.$sts.get(0).statementClass, st -> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                sts.add( sel );
            }
        });
        return sts;
    }

    @Override
    public <N extends Node> N removeIn( N astNode ){
        selectListIn(astNode ).forEach(s -> s.statements.forEach(st-> st.removeForced()));
        return astNode;
    }

    @Override
    public <N extends _node> N removeIn( N _n ){
        selectListIn(_n ).forEach(s -> s.statements.forEach(st-> st.removeForced()));
        return _n;
    }

    /**
     * 
     * @param <N>
     * @param astNode
     * @param $repl
     * @return 
     */
    public <N extends Node> N replaceIn(N astNode, $stmt $repl ){
        $snip $sn = new $snip($repl);
        return $snip.this.replaceIn(astNode, $sn);
    }

    /**
     * 
     * @param <N>
     * @param astNode
     * @param $repl
     * @return 
     */
    public <N extends Node> N replaceIn(N astNode, $snip $repl ){
        AtomicInteger ai = new AtomicInteger(0);

        astNode.walk(this.$sts.get(0).statementClass, st-> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                //constrct the replacement snippet
                List<Statement> replacements = $repl.construct(sel.args );
                Statement firstStmt = sel.statements.get(0);
                Node par = firstStmt.getParentNode().get();
                NodeWithStatements parentNode = (NodeWithStatements)par;
                int addIndex = par.getChildNodes().indexOf( firstStmt );
                LabeledStmt ls = Stmt.labeledStmt("$replacement$:{}");
                // we want to add the contents of the replacement to a labeled statement,
                // because, (if we did it INLINE, we could  end up in an infinite loop, searching the
                // tree up to a cursor, then adding some code AT the cursor, then finding a match within the added
                // code, then adding more code, etc. etc.
                // this way, WE ADD A SINGLE LABELED STATEMENT AT THE LOCATION OF THE FIRST MATCH
                // (which contains multiple statements)
                // then, we move to the next statement skipping over the code that was added
                //at the end we "flatten the labeled statements" making them inline
                for(int i=0;i<replacements.size(); i++){
                    ls.getStatement().asBlockStmt().addStatement( replacements.get(i) );
                }
                //add the labeled statement after the last statement
                parentNode.addStatement(addIndex +1, ls);

                //removeIn all but the matched statements
                sel.statements.forEach( s-> s.remove() );
                ai.incrementAndGet();
                //System.out.println("PAR AFTER Remove "+ par );
            }
        });

        _node _le = (_node)_java.of(astNode);
        if( astNode instanceof _body._hasBody){
            for(int i=0;i< ai.get(); i++){
                ((_body._hasBody)_le).flattenLabel("$replacement$");
            }
        } else if( _le instanceof _type ){
            ((_type)_le).flattenLabel("$replacement$");

        }
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
        return $snip.this.replaceIn(_n, $sn);
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param $repl
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, $snip $repl ){
        AtomicInteger ai = new AtomicInteger(0);

        Walk.in(_n, this.$sts.get(0).statementClass, st-> {
        //_le.walk( this.$sts.get(0).statementClass, st-> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                //construct the replacement snippet
                List<Statement> replacements = $repl.construct(sel.args );
                Statement firstStmt = sel.statements.get(0);
                Node par = firstStmt.getParentNode().get();
                NodeWithStatements parentNode = (NodeWithStatements)par;
                int addIndex = par.getChildNodes().indexOf( firstStmt );
                LabeledStmt ls = Stmt.labeledStmt("$replacement$:{}");
                // we want to add the contents of the replacement to a labeled statement,
                // because, (if we did it INLINE, we could  end up in an infinite loop, searching the
                // tree up to a cursor, then adding some code AT the cursor, then finding a match within the added
                // code, then adding more code, etc. etc.
                // this way, WE ADD A SINGLE LABELED STATEMENT AT THE LOCATION OF THE FIRST MATCH
                // (which contains multiple statements)
                // then, we move to the next statement skipping over the code that was added
                //at the end we "flatten the labeled statements" making them inline
                for(int i=0;i<replacements.size(); i++){
                    ls.getStatement().asBlockStmt().addStatement( replacements.get(i) );
                }
                //add the labeled statement after the last statement
                parentNode.addStatement(addIndex +1, ls);

                //removeIn all but the matched statements
                sel.statements.forEach( s-> s.remove() );
                ai.incrementAndGet();
                //System.out.println("PAR AFTER Remove "+ par );
            }
        });

        if( _n instanceof _body._hasBody){
            for(int i=0;i< ai.get(); i++){
                ((_body._hasBody)_n).flattenLabel("$replacement$");
            }
        } else if( _n instanceof _type ){
            ((_type)_n).flattenLabel("$replacement$");
        }
        return _n;
    }

    /**
     * 
     * @param <N>
     * @param astNode
     * @param selectedAction
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Consumer<Select>selectedAction ){
        astNode.walk( this.$sts.get(0).statementClass, st-> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                selectedAction.accept(sel);
            }
        });
        return astNode;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param selectedAction
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Consumer<Select>selectedAction ){
        Walk.in(_n, this.$sts.get(0).statementClass, st-> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                selectedAction.accept(sel);
            }
        });
        return _n;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        this.$sts.forEach(s -> sb.append(s).append( System.lineSeparator() ));
        return "$snip { "+ System.lineSeparator() + Text.indent( sb.toString() ) +"}";
    }

    /**
     * A Matched Selection result returned from matching a prototype $field
     * inside of some Node or _node
     */
    public static class Select implements $proto.selected {
        public List<Statement> statements;
        public $args args;

        @Override
        public $args getArgs(){
            return args;
        }
        
        public Select( List<Statement> statements, Tokens tokens){
            this.statements = statements;
            this.args = args.of(tokens);
        }

        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            this.statements.forEach( s -> sb.append(s).append( System.lineSeparator()) );
            return "$snip.Selected{"+ System.lineSeparator()+
                Text.indent( sb.toString() )+ System.lineSeparator()+
                Text.indent("$args : " + args) + System.lineSeparator()+
                "}";
        }
    }
}