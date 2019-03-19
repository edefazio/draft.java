package draft.java.template;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import draft.*;
import draft.java.*;
import draft.java._model._node;
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
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @return 
     */
    public static final <N extends _model._node> List<_method> list( N rootNode, String source ){
        return $method.of(source).listIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @return 
     */
    public static final <N extends _model._node> List<_method> list( N rootNode, _method source ){
        return $method.of(source).listIn(rootNode);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param rootNode
     * @param source
     * @return the modified N
     */
    public static final <N extends _model._node> N remove( N rootNode, _method source ){
        return $method.of(source).removeIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @return 
     */
    public static final <N extends _model._node> N remove( N rootNode, String... source ){
        return $method.of(source).removeIn(rootNode);
    }
    
    public static final <N extends _node> N replace( N rootNode, _method source, _method replacement ){
        return $method.of(source).replaceIn(rootNode, replacement);
    }
    
    public static final <N extends _node> N replace( N rootNode, String[] protoMethod, String[] replacementMethod ){
        return $method.of(protoMethod).replaceIn(rootNode, replacementMethod);
    }
    
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
        ObjectCreationExpr oce = Expr.anonymousObject( ste );
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
     * @param <T>
     * @param <U>
     * @param signature
     * @param parametersAndBody
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
     * @param <T>
     * @param <U>
     * @param <V>
     * @param signature
     * @param parametersAndBody
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

    @Override
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

    @Override
    public List<String> list$(){
        List<String>normalized$ = new ArrayList<>();
        normalized$.addAll( this.signatureStencil.list$Normalized() );
        normalized$.addAll( this.$body.list$Normalized() );
        return normalized$;
    }

    @Override
    public _method construct(Translator translator, Map<String, Object> keyValues) {
        //_1_build the signature
        _method _m = _method.of(this.signatureStencil.construct(translator, keyValues ));

        if( this.$body != null) {
            //_1_build the BODY
            List<Statement> sts = $body.construct(translator, keyValues);
            sts.forEach( s -> _m.add(s) );
        }
        return _m;
    }

    @Override
    public _method construct(Map<String, Object> keyValues) {
        return $method.this.construct( Translator.DEFAULT_TRANSLATOR, keyValues );
    }

    public _method construct(_model._node model ){
        return $method.this.construct( model.componentize() );
    }

    @Override
    public _method construct(Object... keyValues) {
        return $method.this.construct( Translator.DEFAULT_TRANSLATOR, keyValues );
    }

    @Override
    public _method construct(Translator translator, Object... keyValues) {
        return $method.this.construct(translator, Tokens.of(keyValues));
    }

    @Override
    public _method fill(Object...values){
        return fill( Translator.DEFAULT_TRANSLATOR, values );
    }

    @Override
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
        return $method.this.construct( t, kvs );
    }

    public static final BlockStmt EMPTY = Stmt.block("{}");

    public Tokens deconstruct( _method _m ){
        return deconstruct(_m.ast() );
    }
    
    public Tokens deconstruct( MethodDeclaration astTarget ){
        if( astTarget.getBody().isPresent()){
            if( this.$body == null) {
                return null; //the target has a BODY, the template doesnt
            }
            Tokens ts = null;
            if( astTarget.getBody().get().isEmpty() ){
                ts = new Tokens();
            } else {
                //we check this first because we dont want to clone unless we need to
                ts = this.$body.deconstruct(astTarget.getBody().get().getStatement(0));
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
                Tokens tss = this.signatureStencil.deconstruct( signature );
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
        return this.signatureStencil.deconstruct( astTarget.clone().removeComment().toString() );
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
        return deconstruct( _m.ast() ) != null;
    }

    public boolean matches( MethodDeclaration astM ){
        return deconstruct( astM ) != null;
    }

    public Select select( _method _m){
        return select( _m.ast());
    }

    public Select select( MethodDeclaration astMethod){
        Tokens ts = deconstruct( astMethod );
        if( ts != null ){
            return new Select( astMethod, ts );
        }
        return null;
    }

    @Override
    public List<Select> listSelectedIn(Node n){
        List<Select>sts = new ArrayList<>();
        n.walk(MethodDeclaration.class, m-> {
            Select sel = select( m );
            if( sel != null ){
                sts.add(sel);
            }
        });
        return sts;
    }

    @Override
    public List<Select> listSelectedIn(_model._node _t){
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
    
    public  <T extends _model._node> T replaceIn( T _t, $method $replace ){
        return forSelectedIn( _t, s -> {
            _method repl = $replace.construct(s.tokens);
            s.method.replace(repl.ast());
        });
    }

    public  <T extends _model._node> T replaceIn( T _t, String... replacementMethod ){
        return replaceIn( _t, $method.of(replacementMethod));        
    }
    
    public  <T extends _model._node> T replaceIn( T _t, _method method ){
        return replaceIn( _t, $method.of(method));        
    }
    
    public  <T extends _model._node> T replaceIn( T _t, MethodDeclaration astMethod ){
        return replaceIn( _t, $method.of(astMethod));        
    }
    
    @Override
    public <M extends _model._node> M removeIn(M _m ){
        Walk.in(_m, MethodDeclaration.class, e-> {
            Tokens tokens = this.deconstruct( e );
            if( tokens != null ){
                e.removeForced();
            }
        });
        return _m;
    }

    @Override
    public <N extends Node> N removeIn(N n ){
        n.walk(MethodDeclaration.class, e-> {
            Tokens tokens = this.deconstruct( e );
            if( tokens != null ){
                e.removeForced();
            }
        });
        return n;
    }

    @Override
    public <N extends Node> N forIn(N n, Consumer<_method> _methodActionFn){
        n.walk(MethodDeclaration.class, e-> {
            Tokens tokens = this.deconstruct( e );
            if( tokens != null ){
                _methodActionFn.accept( _method.of(e) );
            }
        });
        return n;
    }

    @Override
    public  <T extends _model._node> T forIn(T _t, Consumer<_method> _methodActionFn){
        Walk.in(_t, MethodDeclaration.class, e -> {
            Tokens tokens = this.deconstruct( e );
            if( tokens != null ){
                _methodActionFn.accept( _method.of(e) );
            }
        });
        return _t;
    }

    @Override
    public List<_method> listIn(_model._node _t ){
        return listIn( _t.ast() );
    }

    @Override
    public List<_method> listIn(Node rootNode ){
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
