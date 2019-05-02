package draft.java.proto;

import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithBlockStmt;
import com.github.javaparser.ast.nodeTypes.NodeWithBody;
import com.github.javaparser.ast.nodeTypes.NodeWithOptionalBlockStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import draft.Template;
import draft.Tokens;
import draft.Translator;
import draft.java.Ast;
import draft.java.Expr;
import draft.java._body;
import draft.java._constructor;
import draft.java.proto.$proto.$args;
import draft.java.proto.$proto.selected;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Prototype of a body
 * @author Eric
 */
public class $body implements Template<_body> {
    
    public static $body any(){
        return new $body(); 
    }
    
    public static $body of( String body ){
        return new $body( _body.of(body));
    }
    
    public static $body of( String...body ){
        return new $body( _body.of(body));
    }
    
    public static $body of( _body _bd ){
        return new $body(_bd);
    }
    
    public static $body of( NodeWithBlockStmt astNodeWithBlock ){
        return new $body(_body.of(astNodeWithBlock));
    }
    
    public static $body of( NodeWithOptionalBlockStmt astNodeWithBlock ){
        return new $body(_body.of(astNodeWithBlock));
    }
    
    public static $body of(Expr.Command commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return of( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public static $body of(Consumer commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return of( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public static $body of(BiConsumer commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return of( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public static $body of(Expr.TriConsumer commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return of( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public static $body of(Expr.QuadConsumer commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return of( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public static <A extends Object, B extends Object> $body of(Function<A,B> commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return of( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public static <A extends Object, B extends Object, C extends Object>  $body of(BiFunction<A,B,C> commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return of( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public static <A extends Object, B extends Object, C extends Object, D extends Object> $body of(Expr.TriFunction<A,B,C,D> commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return of( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public static <A extends Object, B extends Object, C extends Object, D extends Object, E extends Object>  $body of(Expr.QuadFunction<A,B,C,D,E> commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return of( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public static $body notImplemented(){
        return new $body();
    }
    
    public Predicate<_body> constraint = t->true;
    public Boolean isImplemented = true;    
    public $stmt bodyStmts = null;
    
    
    /**
     * This represents a "non implemented body"
     * which is 
     */
    private $body( ){
        this.isImplemented = false;        
    }
    
    private $body( _body body ){
        if( body.isImplemented() ){
            this.bodyStmts = $stmt.of(body.ast());
        } else{
            this.isImplemented = false;
        }        
    }
    
    public $body $bodyStmts(_body body ){
        if( body == null ){
            this.isImplemented = false;
            this.bodyStmts = null;
        } else{
            this.isImplemented = true;
            this.bodyStmts = $stmt.of(body.ast());
        }
        return this;
    }
    
    @Override
    public List<String> list$(){
        if(this.isImplemented){
            return this.bodyStmts.list$();
        }
        return Collections.EMPTY_LIST;
    }
    
    
    @Override
    public List<String> list$Normalized(){
        if( isImplemented ){
            this.bodyStmts.list$Normalized();
        }
        return Collections.EMPTY_LIST;
    }
    
    public boolean matches(String...body){
        return select(body) != null;
    }
    
    public boolean matches( _body body){
        return select(body) != null;
    }
    
    public Select select(Expr.Command commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return select( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public Select select(Consumer commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return select( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public Select select(BiConsumer commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return select( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public Select select(Expr.TriConsumer commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return select( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public Select select(Expr.QuadConsumer commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return select( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    
    public <A extends Object, B extends Object> Select select(Function<A,B> commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return select( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public <A extends Object, B extends Object, C extends Object> Select select(BiFunction<A,B,C> commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return select( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public <A extends Object, B extends Object, C extends Object, D extends Object>Select select(Expr.TriFunction<A,B,C,D> commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return select( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public <A extends Object, B extends Object, C extends Object, D extends Object, E extends Object> Select select(Expr.QuadFunction<A,B,C,D,E> commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return select( le.getBody().toString(Ast.PRINT_NO_COMMENTS ) );
    }
    
    public Select select (String body ){
        return select(_body.of(body));
    }
    
    public Select select (String...body ){
        return select(_body.of(body));
    }
    
    /**
     * 
     * @return 
     */
    public boolean isMatchAny(){
        try{
            return this.constraint.test(null) && this.bodyStmts.isMatchAny();
        }catch(Exception e){
            return false;
        }
    }
    
    public Select select( _body body ){
        if( isMatchAny() ){
            return new Select( body, $args.of());
        }
        //System.out.println( "Not is match any");
        if( !this.constraint.test(body)){
            //System.out.println( "failed Constraint");
            return null;
        }        
        if( !body.isImplemented() ){
            if( this.isImplemented ){
                //System.out.println( "Expected Implemented");
                return null;
            } 
            return new Select(body, $args.of());                
        }       
        if( this.isImplemented ){
            $stmt.Select ss = this.bodyStmts.select((Statement)body.ast());
            if( ss != null ){
                return new Select( body, ss.args);
            }
        } //else{
        return new Select( body, $args.of());
        //}
        //return null;    
        
    }
    
    public Select select( NodeWithBlockStmt nwb ){
        return select(_body.of(nwb));
        /*
        //if( this.constraint.test(t))
        if( ! isImplemented ){
            return null;
        }
        $stmt.Select ss = this.body.select((Statement)nwb.getBody());        
        if( ss != null ){
            return new Select( _body.of(nwb), ss.args);
        }
        return null;             
        */
    }
    
    public Select select( NodeWithOptionalBlockStmt nwb ){
        return select(_body.of(nwb ));
        /*
        if( !nwb.getBody().isPresent() ){
            if(! isImplemented ){
                return new Select(_body.of(nwb), $args.of() );
            }
            return null;
        }
        if( !isImplemented ){
            return null;
        }        
        $stmt.Select ss = this.body.select((Statement)nwb.getBody().get());        
        if( ss != null ){
            return new Select( _body.of(nwb), ss.args);
        }
        return null;        
        */
    }

    @Override
    public _body construct(Translator translator, Map<String, Object> keyValues) {
        if( !this.isImplemented ){
            return _body.of(";");
        }
        return _body.of( (Statement)this.bodyStmts.construct(translator, keyValues) );        
    }

    public Tokens decomposeTo(_body body, Tokens all) {
        if (all == null) { /* Skip decompose if the tokens already null*/
            return null;
        }    
        Select select = select( body );

        if (select != null) {                
            if (all.isConsistent(select.args.asTokens())) {                   
                all.putAll(select.args.asTokens());
                return all;
            }
        }
        return null;
    }
    
    @Override
    public $body $(String target, String $Name) {
        if( this.isImplemented ){
            this.bodyStmts.$(target, $Name);
        }
        return this;
    }
    
    public $body hardcode$(Translator translator, Tokens kvs ) {
        if( this.isImplemented ){
            this.bodyStmts.hardcode$(translator, kvs);
        }
        return this;
    }
    
    public static class Select implements selected<_body>{

        public _body body;
        public $args args;
        
        public Select( _body body, $args args){
            this.body = body;
            this.args = args;
        }
        
        @Override
        public $args getArgs() {
            return args;
        }
        
    }
}
