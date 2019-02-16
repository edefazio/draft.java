package draft.java.template;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithStatements;
import com.github.javaparser.ast.stmt.*;
import draft.*;
import draft.java.*;
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
 * it follows the same nameing conventions
 *
 * composed
 * match
 * select
 * decomposed
 */
public final class $snip implements Template<List<Statement>>, $query<List<Statement>> {

    List<$stmt> $sts = new ArrayList<>();

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
     * System.out.println( $s.compose( _field.of("int x;") ));
     * </PRE>
     * @param anonymousObjectWithBody
     * @return the dynamic code snippet
     */
    public static $snip of( Object anonymousObjectWithBody ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousClass(ste);
        //find the first method that doesnt have removeIn on it and has a BODY
        // to get it's contents
        MethodDeclaration theMethod = (MethodDeclaration)
                oce.getAnonymousClassBody().get().stream().filter(m -> m instanceof MethodDeclaration &&
                        !m.isAnnotationPresent(_remove.class) &&
                        ((MethodDeclaration) m).getBody().isPresent()).findFirst().get();
        return of( theMethod.getBody().get());
    }

    public static $snip of( String snippet ){
        return of( new String[] {snippet});
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

    public static $snip of( LambdaExpr le ){
        Statement st = Stmt.from(le);
        return new $snip(st);
    }

    public static $snip of( _body _b ){
        return of( _b.ast() );
    }

    public static $snip of(BlockStmt body ){
        $snip $s = new $snip();
        body.getStatements().forEach(s -> $s.add(s));
        return $s;
    }

    public static $snip of(String... snippetCode) {
        $snip $s = new $snip();
        //String st = Text.combine(snippetCode);
        //System.out.println( "COMBINED  >>>>>>>>>>>>>>>>>>>> "+ st );
        BlockStmt bs = Ast.blockStmt(snippetCode);
        //System.out.println( "BLOCK  >>>>>>>>>>>>>>>>>>>> "+ bs );

        bs.getStatements().forEach(s -> $s.add(s));
        return $s;
    }

    public $snip(){
    }

    private $snip( Statement st ){
        if( st instanceof BlockStmt ){
            st.asBlockStmt().getStatements().forEach(s -> add(s) );
        } else{
            add(st);
        }
    }

    public $snip($stmt $st ){
        add($st);
    }

    public $snip add(Statement st){
        $sts.add( new $stmt(st) );
        return this;
    }

    public $snip add($stmt $st ){
        $sts.add($st);
        return this;
    }

    public $snip $(String target, String paramName){
        this.$sts.forEach(s -> s.$(target, paramName));
        return this;
    }

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

    public $snip assign$( Translator translator, Tokens kvs ) {
        this.$sts.forEach( $s -> $s.assign$(translator, kvs));
        return this;
    }

    /**
     * NOTE: FILL DOES NOT HANDLE OPTIONAL STATEMENTS
     * i.e.
     *
     * $optional:
     *
     * so you cannot pass in the $optional parameter inline with fill
     * @param values
     * @return
     */
    public List<Statement> fill(Object...values){
        return fill( Translator.DEFAULT_TRANSLATOR, values );
    }

    public List<Statement> fill(Translator t, Object...values){
        List<Statement>sts = new ArrayList<>();
        List<String> keys = list$Normalized();
        if( values.length < keys.size() ){
            throw new DraftException( "not enough values("+values.length+") to fill ("+keys.size()+") variables "+ keys);
        }
        Map<String,Object> kvs = new HashMap<>();
        for(int i=0;i<values.length;i++){
            kvs.put( keys.get(i), values[i]);
        }
        return compose( t, kvs );
    }

    public List<Statement> compose( Object...keyValues ){
        return compose( Tokens.of(keyValues));
    }

    public List<Statement> compose( Tokens tokens ){
        return compose( Translator.DEFAULT_TRANSLATOR, tokens.map());
    }


    public List<Statement> compose( _model._node model ){
        return compose(model.decompose());
    }

    public List<Statement> compose( Map<String,Object> tokens ){
       return compose( Translator.DEFAULT_TRANSLATOR, tokens );
    }

    public List<Statement> compose( Translator t, Object...keyValues ){
        return compose( t, Tokens.of(keyValues ) );
    }

    public List<Statement> compose( Translator t, Map<String,Object> tokens ){
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
                    //compose the statement (it
                    LabeledStmt ls = (LabeledStmt)stmt.compose(t, tokens);
                    Statement st = ls.getStatement();
                    if( st instanceof BlockStmt ) {
                        //add each of the statements
                        st.asBlockStmt().getStatements().forEach( s-> sts.add(s));
                    }else{
                        sts.add(st ); //just add the derived statement
                    }
                }
            } else { //it is NOT a dymanically labeled Statement, so just process normally
                sts.add(stmt.compose(t, tokens));
            }
        });
        return sts;
    }

    public Select select( Statement statement ){
        int idx = 0;
        List<Statement> ss = new ArrayList<>();
        ss.add(statement);
        if( this.$sts.size() > 1  ){
            if( !statement.getParentNode().isPresent() ) {
                //System.out.println("cant be match, no parent");
                return null; //cant be
            }
            NodeWithStatements nws = (NodeWithStatements)statement.getParentNode().get();
            List<Statement>childs = nws.getStatements();
            //List<Node> childs =
            //        statement.getParentNode().get().getChildNodes();
            idx = childs.indexOf(statement);
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
            Tokens ts = this.$sts.get(i).decompose(ss.get(i));
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
     * Decompose the statement(s) into tokens, or return null if the statement doesnt match
     *
     * @param statement the statement to partsMap
     * @return Tokens from the stencil, or null if the statement doesnt match
     */
    public Tokens decompose( Statement statement ){
        Select s  = select( statement );
        if( s != null ){
            return s.tokens;
        }
        return null;
    }

    public boolean matches( _body _b ){
        return matches(_b.getStatement(0));
    }

    public boolean matches( Statement statement ){
        return decompose(statement) != null;
    }

    public List<List<Statement>> findAllIn(Node node ){
        List<List<Statement>>sts = new ArrayList<>();
        node.walk(this.$sts.get(0).statementClass, st -> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                sts.add( sel.statements );
            }
        });
        return sts;
    }

    public List<List<Statement>> findAllIn(_model._node _le ){
        List<List<Statement>>sts = new ArrayList<>();
        Walk.in( _le, this.$sts.get(0).statementClass, st -> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                sts.add( sel.statements );
            }
        });
        return sts;
    }

    public <M extends _model._node> M forAllIn(M _le, Consumer<List<Statement>> statementsConsumer ){
        Walk.in( _le, this.$sts.get(0).statementClass, st -> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                statementsConsumer.accept( sel.statements );
            }
        });
        return _le;
    }

    public <N extends Node> N forAllIn(N node, Consumer<List<Statement>> statementsConsumer ){
        node.walk(this.$sts.get(0).statementClass, st -> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                statementsConsumer.accept( sel.statements );
            }
        });
        return node;
    }


    public List<Select> selectAllIn(_model._node _le ){
        List<Select>sts = new ArrayList<>();
        Walk.in( _le, this.$sts.get(0).statementClass, st -> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                sts.add( sel );
            }
        });
        return sts;
    }

    public List<Select> selectAllIn(Node node ){
        List<Select>sts = new ArrayList<>();
        node.walk(this.$sts.get(0).statementClass, st -> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                sts.add( sel );
            }
        });
        return sts;
    }

    public <N extends Node> N removeIn( N node ){
        selectAllIn( node ).forEach(s -> s.statements.forEach(st-> st.removeForced()));
        return node;
    }

    public <M extends _model._node> M removeIn( M _m ){
        selectAllIn( _m ).forEach(s -> s.statements.forEach(st-> st.removeForced()));
        return _m;
    }

    public <N extends Node> N replaceAllIn(N n, $stmt $repl ){
        $snip $sn = new $snip($repl);
        return replaceAllIn(n, $sn);
    }

    public <N extends Node> N replaceAllIn(N n, $snip $repl ){
        AtomicInteger ai = new AtomicInteger(0);

        n.walk( this.$sts.get(0).statementClass, st-> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                //compose the replacement snippet
                List<Statement> replacements = $repl.compose( sel.tokens );
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

        _model._node _le = (_model._node)_java.of(n);
        if( n instanceof _body._hasBody){
            for(int i=0;i< ai.get(); i++){
                ((_body._hasBody)_le).flattenLabel("$replacement$");
            }
        } else if( _le instanceof _type ){
            ((_type)_le).flattenLabel("$replacement$");

        }
        return n;
    }

    public <M extends _model._node> M replaceAllIn(M _le, $stmt $repl ){
        $snip $sn = new $snip($repl);
        return replaceAllIn(_le, $sn);
    }

    public <M extends _model._node> M replaceAllIn(M _le, $snip $repl ){
        AtomicInteger ai = new AtomicInteger(0);

        Walk.in( _le, this.$sts.get(0).statementClass, st-> {
        //_le.walk( this.$sts.get(0).statementClass, st-> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                //compose the replacement snippet
                List<Statement> replacements = $repl.compose( sel.tokens );
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

        if( _le instanceof _body._hasBody){
            for(int i=0;i< ai.get(); i++){
                ((_body._hasBody)_le).flattenLabel("$replacement$");
            }
        } else if( _le instanceof _type ){
            ((_type)_le).flattenLabel("$replacement$");
        }
        return _le;
    }

    public <N extends Node> N forSelectedIn(N n, Consumer<Select>selectedAction ){
        n.walk( this.$sts.get(0).statementClass, st-> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                selectedAction.accept(sel);
            }
        });
        return n;
    }

    public <M extends _model._node> M forSelectedIn(M _hb, Consumer<Select>selectedAction ){
        Walk.in(_hb, this.$sts.get(0).statementClass, st-> {
            Select sel = select( (Statement)st );
            if( sel != null ){
                selectedAction.accept(sel);
            }
        });
        return _hb;
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        this.$sts.forEach(s -> sb.append(s).append( System.lineSeparator() ));
        return "$snip { "+ System.lineSeparator() + Text.indent( sb.toString() ) +"}";
    }

    public static class Select implements $query.selected {
        public List<Statement> statements;
        public Tokens tokens;

        public Select( List<Statement> statements, Tokens tokens){
            this.statements = statements;
            this.tokens = tokens;
        }

        public String toString(){
            StringBuilder sb = new StringBuilder();
            this.statements.forEach( s -> sb.append(s).append( System.lineSeparator()) );
            return "$snip.Selected{"+ System.lineSeparator()+
                    Text.indent( sb.toString() )+ System.lineSeparator()+
                    Text.indent( "TOKENS : " + tokens) + System.lineSeparator()+
                    "}";
        }
    }
}