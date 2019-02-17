package draft.java.template;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import draft.*;
import draft.java.*;
import draft.java.macro._macro;
import draft.java.macro._remove;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.*;

/**
 * Template for a Java {@link _method}
 */
public final class $method
        implements Template<_method>, $query<_method> {

    public static $method of( String methodDeclaration ){
        return of( new String[]{methodDeclaration});
    }
    /**
     * Pass in an anonymous Object containing the method to import
     * NOTE: if the anonymous Object contains more than one method, ENSURE only one method
     * DOES NOT have the @_remove annotation, (mark all trivial METHODS with @_remove)
     *
     * @param anonymousObjectContainingMethod
     * @return
     */
    public static $method of( Object anonymousObjectContainingMethod ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousClass( ste );
        MethodDeclaration theMethod = (MethodDeclaration)
                oce.getAnonymousClassBody().get().stream().filter(m -> m instanceof MethodDeclaration &&
                !m.isAnnotationPresent(_remove.class) ).findFirst().get();
        return of( _macro.to(anonymousObjectContainingMethod.getClass(), _method.of( theMethod ) ));
        //return of( _method.of(theMethod) );
    }

    public static $method of(String signature, Expr.Command body ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of( _method.updateBody(_m, le) );
    }

    public static <A extends Object> $method of(String signature, Consumer<A> body ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of( _method.updateBody(_m, le) );
    }

    public static <A extends Object, B extends Object> $method of(String signature, BiConsumer<A, B> body ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of( _method.updateBody(_m, le) );
    }

    public static <A extends Object, B extends Object, C extends Object> $method of(String signature, Expr.TriConsumer<A, B, C> body ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of( _method.updateBody(_m, le) );
    }

    public static <A extends Object, B extends Object, C extends Object, D extends Object> $method of(String signature, Expr.QuadConsumer<A, B, C, D> body ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of( _method.updateBody(_m, le) );
    }

    /**
     * $method.of( "public static final void print", ()->{ System.out.println(1); });
     *
     * @return
     */
    public static <T extends Object, U extends Object> $method  of( String signature, Function<T,U> parametersAndBody){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of(_method.updateBody(_m, le));
    }

    /**
     * $method.of( "public static final void print", ()->{ System.out.println(1); });
     *
     * @return
     */
    public static <T extends Object, U extends Object, V extends Object> $method of( String signature, BiFunction<T,U,V> parametersAndBody){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of(_method.updateBody(_m, le));
    }

    public static <A extends Object, B extends Object, C extends Object, D extends Object> $method of( String signature, Expr.TriFunction<A,B,C,D> parametersAndBody){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of(_method.updateBody(_m, le));
    }

    public static <A extends Object, B extends Object, C extends Object, D extends Object, E extends Object> $method of( String signature, Expr.QuadFunction<A,B,C,D,E> parametersAndBody){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of(_method.updateBody(_m, le));
    }

    public static $method of( _method _m ){
        return new $method( _m);
    }

    public static $method of( Class clazz, String name ){
         _method._hasMethods  _hm = (_method._hasMethods)_type.of(clazz);
         return of( _hm.getMethod(name) );
    }

    public static $method of( String...code ){
        return new $method(_method.of(code));
    }

    private Stencil signatureStencil;
    private $snip $body;

    public $method( _method _m ){
        //System.out.println( "CREATING "+ _m );
        if( _m.hasBody() ) {
            this.$body = $snip.of(_m.getBody());
            _method _cp = _m.copy();
            this.signatureStencil = Stencil.of( _cp.setBody("").toString() );
        } else {
            this.signatureStencil = Stencil.of( _m.toString() );
            this.$body = null; //no BODY
        }
    }

    public List<String> list$Normalized(){
        List<String>normalized$ = new ArrayList<>();
        normalized$.addAll( this.signatureStencil.list$Normalized() );
        if( this.$body != null){
            this.$body.list$Normalized().forEach( l-> {
                if (!normalized$.contains(l)) {
                    normalized$.add(l);
                }
            });
        }
        return normalized$;
    }

    public List<String> list$(){
        List<String>normalized$ = new ArrayList<>();
        normalized$.addAll( this.signatureStencil.list$Normalized() );
        normalized$.addAll( this.$body.list$Normalized() );
        return normalized$;
    }

    @Override
    public _method compose(Translator translator, Map<String, Object> keyValues) {
        //_1_build the signature
        _method _m = _method.of( this.signatureStencil.compose(translator, keyValues ));

        if( this.$body != null) {
            //_1_build the BODY
            List<Statement> sts = $body.compose(translator, keyValues);
            sts.forEach( s -> _m.add(s) );
        }
        return _m;
    }

    @Override
    public _method compose(Map<String, Object> keyValues) {
        return compose( Translator.DEFAULT_TRANSLATOR, keyValues );
    }

    public _method compose(_model._node model ){
        return compose( model.componentize() );
    }

    @Override
    public _method compose(Object... keyValues) {
        return compose( Translator.DEFAULT_TRANSLATOR, keyValues );
    }

    @Override
    public _method compose(Translator translator, Object... keyValues) {
        return compose(translator, Tokens.of(keyValues));
    }

    public _method fill(Object...values){
        return fill( Translator.DEFAULT_TRANSLATOR, values );
    }

    public _method fill(Translator t, Object...values){
        //List<Statement>sts = new ArrayList<>();
        List<String> keys = list$Normalized();
        if( values.length < keys.size() ){
            throw new DraftException("not enough values("+values.length+") to fill ("+keys.size()+") variables "+ keys);
        }
        Map<String,Object> kvs = new HashMap<>();
        for(int i=0;i<values.length;i++){
            kvs.put( keys.get(i), values[i]);
        }
        return compose( t, kvs );
    }

    public static final BlockStmt EMPTY = Stmt.block("{}");

    public Tokens decompose( MethodDeclaration astTarget ){
        if( astTarget.getBody().isPresent()){
            if( this.$body == null) {
                return null; //the target has a BODY, the template doesnt
            }
            Tokens ts = null;
            if( astTarget.getBody().get().isEmpty() ){
                ts = new Tokens();
            } else {
                //we check this first because we dont want to clone unless we need to
                ts = this.$body.decompose(astTarget.getBody().get().getStatement(0));
            }

            if( ts != null ){
                final Tokens toks = ts;
                //System.out.println( "BODY IS THE SAME");
                //System.out.println( this.signatureStencil );

                //we have to clone to check signature equality, also removeIn JAVADOC and BODY
                String signature = astTarget.clone()
                        .setBody(EMPTY) //make the clones' BODY empty
                        .removeComment() //removeIn any comments/JAVADOC from the clone
                        .toString();
                //System.out.println( "STR "+str );
                Tokens tss = this.signatureStencil.decompose( signature );
                if( tss == null ){
                    return null;
                }
                AtomicBoolean isConsistent = new AtomicBoolean(true);
                tss.forEach( (String k, Object v)->{
                    Object val = toks.get(k);
                    if( val != null && !val.equals(v) ){
                        isConsistent.set(false);
                    }
                });
                if( isConsistent.get() ){
                    ts.putAll(tss);
                    return ts;
                }
            }
            return null; //the BODY or signature isnt the same or BODY / signature tokens were inconsistent
        }
        if( this.$body != null ) {
            return null; //the target doesnt have a BODY , the template does
        }
        //just check the signature, (after removeing the JAVADOC) since there is no BODY
        return this.signatureStencil.decompose( astTarget.clone().removeComment().toString() );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $method assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $method assign$( Object... keyValues ) {
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
    public $method assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    public $method assign$( Translator translator, Tokens kvs ) {
        this.$body = this.$body.assign$(translator,kvs);
        this.signatureStencil = this.signatureStencil.assign$(translator,kvs);
        return this;
    }

    /** Post - parameterize, create a parameter from the target string named $Name#$*/
    @Override
    public $method $(String target, String $Name) {
        this.$body = this.$body.$(target, $Name);
        this.signatureStencil = this.signatureStencil.$(target, $Name);
        return this;
    }

    public $method $(Expression expr, String $name ){
        String exprString = expr.toString();
        return $(exprString, $name);
    }

    public boolean matches( _method _m ){
        return decompose( _m.ast() ) != null;
    }

    public boolean matches( MethodDeclaration astM ){
        return decompose( astM ) != null;
    }

    public Select select( _method _m){
        return select( _m.ast());
    }

    public Select select( MethodDeclaration astMethod){
        Tokens ts = decompose( astMethod );
        if( ts != null ){
            return new Select( astMethod, ts );
        }
        return null;
    }

    public List<Select> selectAllIn(Node n){
        List<Select>sts = new ArrayList<>();
        n.walk(MethodDeclaration.class, m-> {
            Select sel = select( m );
            if( sel != null ){
                sts.add(sel);
            }
        });
        return sts;
    }

    public List<Select> selectAllIn(_model._node _t){
        List<Select>sts = new ArrayList<>();
        Walk.in( _t, MethodDeclaration.class, m -> {
            Select sel = select( m );
            if( sel != null ){
                sts.add(sel);
            }
        });
        return sts;
    }

    public <N extends Node> N forSelectedIn(N  n, Consumer<Select> selectedActionFn ){
        n.walk( MethodDeclaration.class, m-> {
            Select s = select( m );
            if( s != null ){
                selectedActionFn.accept( s );
            }
        });
        return n;
    }
    public <T extends _model._node> T forSelectedIn(T _t, Consumer<Select> selectedActionFn ){
        Walk.in( _t, _method.class, m ->{
            Select s = select( m );
            if( s != null ){
                selectedActionFn.accept( s );
            }
        });
        return _t;
    }

    public <M extends _model._node> M removeIn(M _m ){
        Walk.in( _m, MethodDeclaration.class, e-> {
            Tokens tokens = this.decompose( e );
            if( tokens != null ){
                e.removeForced();
            }
        });
        return _m;
    }

    public <N extends Node> N removeIn(N n ){
        n.walk(MethodDeclaration.class, e-> {
            Tokens tokens = this.decompose( e );
            if( tokens != null ){
                e.removeForced();
            }
        });
        return n;
    }

    public <N extends Node> N forAllIn(N n, Consumer<_method> _methodActionFn){
        n.walk(MethodDeclaration.class, e-> {
            Tokens tokens = this.decompose( e );
            if( tokens != null ){
                _methodActionFn.accept( _method.of(e) );
            }
        });
        return n;
    }

    public  <T extends _model._node> T forAllIn(T _t, Consumer<_method> _methodActionFn){
        Walk.in( _t, MethodDeclaration.class, e -> {
            Tokens tokens = this.decompose( e );
            if( tokens != null ){
                _methodActionFn.accept( _method.of(e) );
            }
        });
        return _t;
    }

    public List<_method> findAllIn(_model._node _t ){
        return findAllIn( _t.ast() );
    }

    public List<_method> findAllIn(Node rootNode ){
        List<_method> typesList = new ArrayList<>();
        rootNode.walk(MethodDeclaration.class, m->{
            if( this.matches(m) ){
                typesList.add( _method.of(m));
            }
        } );
        return typesList;
    }

    public static class Select implements $query.selected {
        public MethodDeclaration method;
        public Tokens tokens;

        public Select( MethodDeclaration astMethod, Tokens tokens ){
            this.method = astMethod;
            this.tokens = tokens;
        }

        public String toString(){
            return "$method.Select{"+ System.lineSeparator()+
                    Text.indent( method.toString() )+ System.lineSeparator()+
                    Text.indent( "TOKENS : " + tokens) + System.lineSeparator()+
                    "}";
        }
    }
}
