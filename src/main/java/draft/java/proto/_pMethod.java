package draft.java.proto;

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
public final class _pMethod
    implements Template<_method>, _pQuery<_method> {
    
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<_method> list( N _n, String proto ){
        return _pMethod.of(proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @return 
     */
    public static final <N extends _node> List<_method> list( N _n, _method _proto ){
        return _pMethod.of(_proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_method> list( N _n, String proto, Predicate<_method> constraint){
        return _pMethod.of(proto, constraint).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_method> list( N _n, _method _proto, Predicate<_method> constraint){
        return _pMethod.of(_proto, constraint).listIn(_n);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param _proto
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, _method _proto ){
        return _pMethod.of(_proto).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String... proto ){
        return _pMethod.of(proto).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _protoSource
     * @param _protoReplacement
     * @return 
     */
    public static final <N extends _node> N replace( N _n, _method _protoSource, _method _protoReplacement ){
        return _pMethod.of(_protoSource).replaceIn(_n, _protoReplacement);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param protoMethod
     * @param replacementMethod
     * @return 
     */
    public static final <N extends _node> N replace( N _n, String[] protoMethod, String[] replacementMethod ){
        return _pMethod.of(protoMethod).replaceIn(_n, replacementMethod);
    }
    
    /**
     * 
     * @param protoMethod
     * @return 
     */
    public static _pMethod of( String protoMethod ){
        return of(new String[]{protoMethod});
    }
    
    /**
     * Pass in an anonymous Object containing the method to import
     * NOTE: if the anonymous Object contains more than one method, ENSURE only one method
     * DOES NOT have the @_remove annotation, (mark all trivial METHODS with @_remove)
     *
     * @param anonymousObjectContainingMethod
     * @return
     */
    public static _pMethod of( Object anonymousObjectContainingMethod ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject( ste );
        MethodDeclaration theMethod = (MethodDeclaration)
                oce.getAnonymousClassBody().get().stream().filter(m -> m instanceof MethodDeclaration &&
                !m.isAnnotationPresent(_remove.class) ).findFirst().get();
        return of( _macro.to(anonymousObjectContainingMethod.getClass(), _method.of( theMethod ) ));
    }

    /**
     * 
     * @param signature
     * @param body
     * @return 
     */
    public static _pMethod of(String signature, Expr.Command body ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of( _method.updateBody(_m, le) );
    }

    /**
     * 
     * @param <A>
     * @param signature
     * @param body
     * @return 
     */
    public static <A extends Object> _pMethod of(String signature, Consumer<A> body ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of( _method.updateBody(_m, le) );
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param signature
     * @param body
     * @return 
     */
    public static <A extends Object, B extends Object> _pMethod of(String signature, BiConsumer<A, B> body ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of( _method.updateBody(_m, le) );
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param <C>
     * @param signature
     * @param body
     * @return 
     */
    public static <A extends Object, B extends Object, C extends Object> _pMethod of(String signature, Expr.TriConsumer<A, B, C> body ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of( _method.updateBody(_m, le) );
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <D>
     * @param signature
     * @param body
     * @return 
     */
    public static <A extends Object, B extends Object, C extends Object, D extends Object> _pMethod of(String signature, Expr.QuadConsumer<A, B, C, D> body ){
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
    public static <T extends Object, U extends Object> _pMethod  of( String signature, Function<T,U> parametersAndBody){
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
    public static <T extends Object, U extends Object, V extends Object> _pMethod of( String signature, BiFunction<T,U,V> parametersAndBody){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of(_method.updateBody(_m, le));
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <D>
     * @param signature
     * @param parametersAndBody
     * @return 
     */
    public static <A extends Object, B extends Object, C extends Object, D extends Object> _pMethod of( String signature, Expr.TriFunction<A,B,C,D> parametersAndBody){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of(_method.updateBody(_m, le));
    }

    /**
     * 
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <D>
     * @param <E>
     * @param signature
     * @param parametersAndBody
     * @return 
     */
    public static <A extends Object, B extends Object, C extends Object, D extends Object, E extends Object> _pMethod of( String signature, Expr.QuadFunction<A,B,C,D,E> parametersAndBody){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of(_method.updateBody(_m, le));
    }

    /**
     * 
     * @param _m
     * @return 
     */
    public static _pMethod of( _method _m ){
        return new _pMethod( _m);
    }

    /**
     * 
     * @param _m
     * @param constraint
     * @return 
     */
    public static _pMethod of( _method _m, Predicate<_method> constraint){
        return new _pMethod( _m).constraint(constraint);
    }
        
    /**
     * 
     * @param clazz
     * @param name
     * @return 
     */
    public static _pMethod of( Class clazz, String name ){
        _method._hasMethods  _hm = (_method._hasMethods)_type.of(clazz);
        return of( _hm.getMethod(name) );
    }       

    /**
     * 
     * @param proto
     * @return 
     */
    public static _pMethod of( String...proto ){
        return new _pMethod(_method.of(proto));
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static _pMethod of( String proto, Predicate<_method> constraint ){
        return new _pMethod(_method.of(proto)).constraint(constraint);
    }

    public Predicate<_method> constraint = t -> true;
    public Stencil signatureStencil;
    public _pSnip $body;

    /**
     * 
     * @param _proto 
     */
    public _pMethod( _method _proto ){
        if( _proto.hasBody() ) {
            this.$body = _pSnip.of(_proto.getBody());
            _method _cp = _proto.copy();
            this.signatureStencil = Stencil.of( _cp.setBody("").toString() );
        } else {
            this.signatureStencil = Stencil.of(_proto.toString() );
            this.$body = null; //no BODY
        }
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public _pMethod constraint( Predicate<_method> constraint){
        this.constraint = constraint;
        return this;
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
        return _pMethod.this.construct( Translator.DEFAULT_TRANSLATOR, keyValues );
    }

    /**
     * 
     * @param _n
     * @return 
     */
    public _method construct(_node _n ){
        return _pMethod.this.construct(_n.deconstruct() );
    }

    @Override
    public _method construct(Object... keyValues) {
        return _pMethod.this.construct( Translator.DEFAULT_TRANSLATOR, keyValues );
    }

    @Override
    public _method construct(Translator translator, Object... keyValues) {
        return _pMethod.this.construct(translator, Tokens.of(keyValues));
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
        return _pMethod.this.construct( t, kvs );
    }

    public static final BlockStmt EMPTY = Stmt.block("{}");

    /**
     * 
     * @param _m
     * @return 
     */
    public args deconstruct( _method _m ){
        return deconstruct(_m.ast() );
    }
    
    /**
     * 
     * @param astTarget
     * @return 
     */
    public args deconstruct( MethodDeclaration astTarget ){
        if( !this.constraint.test(_method.of(astTarget))){
            return null;
        }
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
                
                //we have to clone to check signature equality, also removeIn JAVADOC and BODY
                String signature = astTarget.clone()
                        .setBody(EMPTY) //make the clones' BODY empty
                        .removeComment() //removeIn any comments/JAVADOC from the clone
                        .toString();
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
                    return args.of(ts);
                }
            }
            return null; //the BODY or signature isnt the same or BODY / signature tokens were inconsistent
        }
        if( this.$body != null ) {
            return null; //the target doesnt have a BODY , the template does
        }
        //just check the signature, (after removeing the JAVADOC) since there is no BODY
        return args.of(this.signatureStencil.deconstruct( astTarget.clone().removeComment().toString() ));
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public _pMethod assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public _pMethod assign$( Object... keyValues ) {
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
    public _pMethod assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public _pMethod assign$( Translator translator, Tokens kvs ) {
        this.$body = this.$body.assign$(translator,kvs);
        this.signatureStencil = this.signatureStencil.assign$(translator,kvs);
        return this;
    }

    /** Post - parameterize, create a parameter from the target string named $Name#$*/
    @Override
    public _pMethod $(String target, String $Name) {
        this.$body = this.$body.$(target, $Name);
        this.signatureStencil = this.signatureStencil.$(target, $Name);
        return this;
    }

    /**
     * 
     * @param astExpr
     * @param $name
     * @return 
     */
    public _pMethod $(Expression astExpr, String $name ){
        String exprString = astExpr.toString();
        return $(exprString, $name);
    }

    /**
     * 
     * @param _m
     * @return 
     */
    public boolean matches( _method _m ){
        return deconstruct( _m.ast() ) != null;
    }

    /**
     * 
     * @param astMethod
     * @return 
     */
    public boolean matches( MethodDeclaration astMethod ){
        return deconstruct(astMethod ) != null;
    }

    /**
     * 
     * @param _m
     * @return 
     */
    public Select select( _method _m){
        return select( _m.ast());
    }

    /**
     * 
     * @param astMethod
     * @return 
     */
    public Select select( MethodDeclaration astMethod){
        args ts = deconstruct( astMethod );
        if( ts != null ){
            return new Select( astMethod, ts );
        }
        return null;
    }

    /**
     * Returns the first _method that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _method that matches (or null if none found)
     */
    public _method firstIn( _node _n ){
        Optional<MethodDeclaration> f = _n.ast().findFirst(MethodDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return _method.of(f.get());
        }
        return null;
    }

    /**
     * Returns the first _method that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _method that matches (or null if none found)
     */
    public _method firstIn( Node astNode ){
        Optional<MethodDeclaration> f = astNode.findFirst(MethodDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return _method.of(f.get());
        }
        return null;
    }
    
    
    /**
     * Returns the first _method that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _method that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n ){
        Optional<MethodDeclaration> f = _n.ast().findFirst(MethodDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }

    /**
     * Returns the first _method that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _method that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode ){
        Optional<MethodDeclaration> f = astNode.findFirst(MethodDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }
    
    @Override
    public List<Select> selectListIn(Node astNode){
        List<Select>sts = new ArrayList<>();
        astNode.walk(MethodDeclaration.class, m-> {
            Select sel = select( m );
            if( sel != null ){
                sts.add(sel);
            }
        });
        return sts;
    }

    @Override
    public List<Select> selectListIn(_node _n){
        List<Select>sts = new ArrayList<>();
        Walk.in(_n, MethodDeclaration.class, m -> {
            Select sel = select( m );
            if( sel != null ){
                sts.add(sel);
            }
        });
        return sts;
    }

    public <N extends Node> N forSelectedIn(N astNode, Consumer<Select> selectedActionFn ){
        astNode.walk( MethodDeclaration.class, m-> {
            Select s = select( m );
            if( s != null ){
                selectedActionFn.accept( s );
            }
        });
        return astNode;
    }
    public <N extends _node> N forSelectedIn(N _n, Consumer<Select> selectedActionFn ){
        Walk.in(_n, _method.class, m ->{
            Select s = select( m );
            if( s != null ){
                selectedActionFn.accept( s );
            }
        });
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param $replace
     * @return 
     */
    public <N extends _node> N replaceIn( N _n, _pMethod $replace ){
        return forSelectedIn(_n, s -> {
            _method repl = $replace.construct(s.args);
            s.astMethod.replace(repl.ast());
        });
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param replacementProto
     * @return 
     */
    public <N extends _node> N replaceIn( N _n, String... replacementProto ){
        return replaceIn(_n, _pMethod.of(replacementProto));        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param method
     * @return 
     */
    public <N extends _node> N replaceIn( N _n, _method method ){
        return replaceIn(_n, _pMethod.of(method));        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param astMethod
     * @return 
     */
    public <N extends _node> N replaceIn( N _n, MethodDeclaration astMethod ){
        return replaceIn(_n, _pMethod.of(astMethod));        
    }
    
    @Override
    public <N extends _node> N removeIn(N _n ){
        Walk.in(_n, MethodDeclaration.class, e-> {
            args tokens = this.deconstruct( e );
            if( tokens != null ){
                e.removeForced();
            }
        });
        return _n;
    }

    @Override
    public <N extends Node> N removeIn(N astNode){
        astNode.walk(MethodDeclaration.class, e-> {
            args tokens = this.deconstruct( e );
            if( tokens != null ){
                e.removeForced();
            }
        });
        return astNode;
    }

    @Override
    public <N extends Node> N forEachIn(N astNode, Consumer<_method> _methodActionFn){
        astNode.walk(MethodDeclaration.class, e-> {
            args tokens = this.deconstruct( e );
            if( tokens != null ){
                _methodActionFn.accept( _method.of(e) );
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_method> _methodActionFn){
        Walk.in(_n, MethodDeclaration.class, e -> {
            args tokens = this.deconstruct( e );
            if( tokens != null ){
                _methodActionFn.accept( _method.of(e) );
            }
        });
        return _n;
    }

    @Override
    public List<_method> listIn(_node _n ){
        return listIn(_n.ast() );
    }

    @Override
    public List<_method> listIn(Node astNode ){
        List<_method> typesList = new ArrayList<>();
        astNode.walk(MethodDeclaration.class, m->{
            if( this.matches(m) ){
                typesList.add( _method.of(m));
            }
        } );
        return typesList;
    }
    

    /**
     * A Matched Selection result returned from matching a prototype $method
     * inside of some Node or _node
     */
    public static class Select implements _pQuery.selected, 
            _pQuery.selectedAstNode<MethodDeclaration>, 
            _pQuery.selected_model<_method> {
        
        public final MethodDeclaration astMethod;
        public final args args;

        public Select( MethodDeclaration astMethod, args tokens ){
            this.astMethod = astMethod;
            this.args = tokens;
        }

        @Override
        public args getArgs(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$method.Select{"+ System.lineSeparator()+
                Text.indent(astMethod.toString() )+ System.lineSeparator()+
                Text.indent("ARGS : " + args) + System.lineSeparator()+
                "}";
        }

        @Override
        public MethodDeclaration ast() {
            return astMethod;
        }

        @Override
        public _method model() {
            return _method.of(astMethod);
        }
    }
}
