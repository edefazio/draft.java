package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import draft.*;
import draft.java.*;
import draft.java._anno._annos;
import draft.java._model._node;
import draft.java._parameter._parameters;
import draft.java._typeParameter._typeParameters;
import draft.java.macro._macro;
import draft.java.macro._remove;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Template for a Java {@link _method}
 */
public class $method
    implements Template<_method>, $proto<_method> {
       
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<_method> list( N _n, String proto ){
        return $method.of(proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @return 
     */
    public static final <N extends _node> List<_method> list( N _n, _method _proto ){
        return $method.of(_proto).listIn(_n);
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
        return $method.of(proto, constraint).listIn(_n);
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
        return $method.of(_proto, constraint).listIn(_n);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param _proto
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, _method _proto ){
        return $method.of(_proto).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String... proto ){
        return $method.of(proto).removeIn(_n);
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
        return $method.of(_protoSource).replaceIn(_n, _protoReplacement);
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
        return $method.of(protoMethod).replaceIn(_n, replacementMethod);
    }
    
    /**
     * 
     * @param protoMethod
     * @return 
     */
    public static $method of( String protoMethod ){
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
    public static $method of( Object anonymousObjectContainingMethod ){
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
    public static $method of(String signature, Expr.Command body ){
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
    public static <A extends Object> $method of(String signature, Consumer<A> body ){
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
    public static <A extends Object, B extends Object> $method of(String signature, BiConsumer<A, B> body ){
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
    public static <A extends Object, B extends Object, C extends Object> $method of(String signature, Expr.TriConsumer<A, B, C> body ){
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
    public static <A extends Object, B extends Object, C extends Object, D extends Object> $method of( String signature, Expr.TriFunction<A,B,C,D> parametersAndBody){
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
    public static <A extends Object, B extends Object, C extends Object, D extends Object, E extends Object> $method of( String signature, Expr.QuadFunction<A,B,C,D,E> parametersAndBody){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = _method.fromSignature( signature );
        return of(_method.updateBody(_m, le));
    }

    /**
     * 
     * @param _m
     * @return 
     */
    public static $method of( _method _m ){
        return new $method( _m);
    }

    /**
     * 
     * @param _m
     * @param constraint
     * @return 
     */
    public static $method of( _method _m, Predicate<_method> constraint){
        return new $method( _m).constraint(constraint);
    }
        
    /**
     * 
     * @param clazz
     * @param name
     * @return 
     */
    public static $method of( Class clazz, String name ){
        _method._hasMethods  _hm = (_method._hasMethods)_type.of(clazz);
        return of( _hm.getMethod(name) );
    }       

    /**
     * 
     * @param proto
     * @return 
     */
    public static $method of( String...proto ){
        return new $method(_method.of(proto));
    }
    
    /**
     * 
     * @param proto
     * @param constraint
     * @return 
     */
    public static $method of( String proto, Predicate<_method> constraint ){
        return new $method(_method.of(proto) ).constraint(constraint);
    }

    public Predicate<_method> constraint = t -> true;
    
    public $component<_javadoc> javadoc = new $component( "$javadoc$", t->true);
    public $component<_annos> annos = new $component( "$annos$", t->true);    
    public $component<_modifiers> modifiers = new $component( "$modifiers$", t->true);
    public $component<_typeRef> type = new $component( "$type$", t->true);
    public $component<_typeParameters> typeParameters = new $component( "$typeParameters$", t->true);
    public $component<String> name = new $component( "$name$", t->true);    
    public $component<_parameters> parameters = new $component( "$parameters$", t-> true);
    public $component<_throws> thrown = new $component( "$throws$", t-> true);
    public $component<_body> body = new $component("$body$", t->true);
    
    private $method( _method _p ){
        this( _p, t-> true );
    }
    
    /**
     * 
     * @param _proto 
     */
    private $method( _method _m , Predicate<_method> constraint){
        
        if( _m.hasJavadoc() ){
            //System.out.println( "The JavaDoc is "+ _m.getJavadoc() );
            javadoc.stencil(_m.getJavadoc().toString() );
            //javadoc.$form = Stencil.of(_p.getJavadoc().toString());
        }
        
        if( _m.hasAnnos() ){
            annos.stencil(_m.getAnnos() );
            //annos.$form = Stencil.of(_p.getAnnos().toString() );
        }
        if( !_m.getModifiers().isEmpty() ){
            final _modifiers ms = _m.getModifiers();
            modifiers = new $component(
                ms.toString(), 
                (m)-> ms.equals(m));
        }
        type = new $component<>(_m.getType().toString());
        if( !_m.hasTypeParameters() ){
            final _typeParameters etps = _m.getTypeParameters();
            //HMMMMM matvhing orfer shouldnt matter
            typeParameters = new $component(
                "$typeParameters$", 
                (tps)-> tps.equals(etps) );
        }
        name = new $component(_m.getName());
        if( _m.hasParameters() ){
            parameters = new $component( _m.getParameters().toString() );
        }        
        if( _m.hasThrows() ){
            final _throws ths = _m.getThrows();
            thrown = new $component( "$throws$", (t)-> t.equals(ths) );
        }
        if( _m.hasBody() ){            
            String bdy = _m.getBody().toString(new PrettyPrinterConfiguration()
                .setPrintComments(false).setPrintJavadoc(false) );
            //System.out.println( "the Body is "+ bdy);
            body = new $component(bdy.trim());            
        }
        this.constraint = constraint;
    }
    
    /**
     * ADDS an additional matching constraint to the prototype
     * @param constraint a constraint to be added
     * @return the modified prototype
     */
    public $method addConstraint( Predicate<_method>constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    /**
     * SETS/ OVERRIDES the matching constraint
     * @param constraint
     * @return 
     */
    public $method constraint( Predicate<_method> constraint){
        this.constraint = constraint;
        return this;
    }
    
    @Override
    public List<String> list$Normalized(){
        List<String>normalized$ = new ArrayList<>();
        normalized$.addAll( javadoc.$form.list$Normalized() );
        normalized$.addAll( annos.$form.list$Normalized() );
        normalized$.addAll( modifiers.$form.list$Normalized() );
        normalized$.addAll( typeParameters.$form.list$Normalized() );
        normalized$.addAll( type.$form.list$Normalized() );        
        normalized$.addAll( name.$form.list$Normalized() );
        normalized$.addAll( parameters.$form.list$Normalized() );
        normalized$.addAll( thrown.$form.list$Normalized() );
        normalized$.addAll( body.$form.list$Normalized() );
        return normalized$.stream().distinct().collect(Collectors.toList());        
    }

    @Override
    public List<String> list$(){
        List<String>all$ = new ArrayList<>();
        all$.addAll( javadoc.$form.list$() );
        all$.addAll( annos.$form.list$() );
        all$.addAll( modifiers.$form.list$() );
        all$.addAll( typeParameters.$form.list$() );
        all$.addAll( type.$form.list$() );        
        all$.addAll( name.$form.list$() );
        all$.addAll( parameters.$form.list$() );
        all$.addAll( thrown.$form.list$() );
        all$.addAll( body.$form.list$() );        
        return all$;
    }
    
    public $method $name(){
        this.name.stencil("$name$");
        return this;
    }
    
    public $method $type(){
        this.type.stencil("$type$");
        return this;
    }
    
    public $method $typeParameters(){
        this.typeParameters.stencil("$typeParameters$");
        return this;
    }

    public $method $modifiers(){
        this.modifiers.stencil("$modifiers$");
        return this;
    }
    
    public $method $annos(){
        this.annos.stencil("$annos$");
        return this;
    }
    
    public $method $javadoc(){
        this.javadoc.stencil("$javadoc$");
        return this;
    }
    
    public $method $javadoc( String... form ){
        this.javadoc.stencil(form);
        return this;
    }
    
    public $method noBody(){
        body.$form = Stencil.of( ";" );
        return this;
    }
    
    public $method emptyBody(){
        body.$form = Stencil.of( "{}" );
        return this;
    }
    
    @Override
    public _method fill(Translator translator, Object... values) {
        List<String> nom = this.list$Normalized();
        nom.remove( "javadoc");
        nom.remove( "annos");
        nom.remove( "modifiers");
        nom.remove( "typeParameters");
        nom.remove( "parameters");
        nom.remove( "throws");
        nom.remove("body");
        if( nom.size() != values.length ){
            throw new DraftException ("FILL eXPECTED ("+nom.size()+") values "+ nom+" got ("+values.length+")");
        }
        Tokens ts = new Tokens();
        for(int i=0;i<nom.size();i++){
            ts.put( nom.get(i), values[i]);
        }
        return construct(translator, ts);
        
        /*
        if( values.length != 2 ){
            throw new DraftException("expected type and name");
        }
        return construct( translator, Tokens.of("type", values[0], "name", values[1] ) );        
        */
    }
    
    @Override
    public _method construct(Translator translator, Map<String, Object> keyValues) {
        
        //the base values (so we dont get Nulls for base values
        Tokens base = Tokens.of(
                "javadoc", "", 
                "annos", "", 
                "modifiers", "", 
                "typeParameters", "", 
                "parameters", "()", 
                "throws", "", 
                "body", "");
        
        //System.out.println( "KEYVALUES "+ keyValues );
        base.putAll(keyValues);
        
        StringBuilder sb = new StringBuilder();   
        //System.out.println( "&&&& JD "+ javadoc.$form +"<<" );
        sb.append( javadoc.compose(translator, base ));        
        sb.append(System.lineSeparator());
        //System.out.println( "JAVADOC \""+ sb.toString()+"\"");
        sb.append( annos.compose(translator, base));
        sb.append(System.lineSeparator());
        //System.out.println( "JAVADOC ANNOS \""+ sb.toString()+"\"");
        sb.append( modifiers.compose(translator, base));
        sb.append(" ");
        sb.append( typeParameters.compose(translator, base));
        sb.append(" ");
        sb.append( type.compose(translator, base));
        sb.append(" ");
        sb.append( name.compose(translator, base));
        sb.append(" ");
        sb.append( parameters.compose(translator, base));
        sb.append(" ");
        sb.append( thrown.compose(translator, base));
        sb.append(System.lineSeparator());
        /** 
         * with the body, I need to fo some more processing
         * I need to process the labeled Statements like snips
         */
        String str = body.compose(translator, base); 
        try{
            //I might need another specialization
            //BlockStmt astBs = Ast.blockStmt(str);
            $snip $s = $snip.of(str);
            List<Statement> sts = $s.construct(translator, base );
            //System.out.println( specialBody );
            //BlockStmt resolved = new BlockStmt();
            _method _mb  = _method.of(sb.toString()+"{}");
            
            sts.forEach(s -> {
                if(! (s instanceof EmptyStmt)){ 
                    _mb.add(s);
                } 
            });            
            //it's possible we have empty statements, lets remove all of them
            Walk.in(_mb, EmptyStmt.class, es-> es.remove() );            
            return _mb;            
        } catch(Exception e){
            sb.append( str );
            return _method.of(sb.toString());     
        }        
        //return _method.of(sb.toString());
    }
    
    /**
     * 
     * @param _n
     * @return 
     */
    public _method construct(_node _n ){
        return construct(_n.deconstruct() );
    }

    public static final BlockStmt EMPTY = Stmt.block("{}");

    /**
     * 
     * @param _m
     * @return 
     */
    public $args deconstruct( _method _m ){
        if( !this.constraint.test(_m)){
            System.out.println("FAILED CONSTRAINT");
            return null;
        }
        Tokens all = new Tokens();
        all = javadoc.decomposeTo(_m.getJavadoc(), all);
        all = annos.decomposeTo(_m.getAnnos(), all);
        all = modifiers.decomposeTo(_m.getModifiers(), all);
        all = typeParameters.decomposeTo(_m.getTypeParameters(), all);
        all = type.decomposeTo(_m.getType(), all);
        all = name.decomposeTo(_m.getName(), all);
        all = parameters.decomposeTo(_m.getParameters(), all);
        all = thrown.decomposeTo(_m.getThrows(), all);
        all = body.decomposeTo(_m.getBody(), all);
        if( all != null ){
            return $args.of(all);
        }
        return null;
    }
    
    /**
     * 
     * @param astTarget
     * @return 
     */
    public $args deconstruct( MethodDeclaration astTarget ){
        return deconstruct( _method.of(astTarget ) );        
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

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $method assign$( Translator translator, Tokens kvs ) {
        javadoc.$form = javadoc.$form.assign$(translator, kvs);
        annos.$form = annos.$form.assign$(translator, kvs);
        modifiers.$form = modifiers.$form.assign$(translator, kvs);
        typeParameters.$form = typeParameters.$form.assign$(translator, kvs);
        type.$form = type.$form.assign$(translator, kvs);
        name.$form = name.$form.assign$(translator, kvs);
        parameters.$form = parameters.$form.assign$(translator, kvs);
        thrown.$form = thrown.$form.assign$(translator, kvs);
        body.$form = body.$form.assign$(translator, kvs);
        
        return this;
    }

    /** Post - parameterize, create a parameter from the target string named $Name#$*/
    @Override
    public $method $(String target, String $Name) {
        javadoc.$form = javadoc.$form.$(target, $Name);
        annos.$form = annos.$form.$(target, $Name);
        modifiers.$form = modifiers.$form.$(target, $Name);
        typeParameters.$form = typeParameters.$form.$(target, $Name);
        type.$form = type.$form.$(target, $Name);
        name.$form = name.$form.$(target, $Name);
        parameters.$form = parameters.$form.$(target, $Name);
        thrown.$form = thrown.$form.$(target, $Name);
        body.$form = body.$form.$(target, $Name);        
        return this;
    }

    /**
     * 
     * @param astExpr
     * @param $name
     * @return 
     */
    public $method $(Expression astExpr, String $name ){
        String exprString = astExpr.toString();
        return $(exprString, $name);
    }

    /**
     * 
     * @param _m
     * @return 
     */
    public boolean matches( _method _m ){
        return deconstruct( _m ) != null;
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
        $args ts = deconstruct( _m );
        if( ts != null ){
            return new Select( _m, ts );
        }
        return null;
        //return select( _m );
    }

    /**
     * 
     * @param astMethod
     * @return 
     */
    public Select select( MethodDeclaration astMethod){
        $args ts = deconstruct( astMethod );
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

    /**
     * 
     * @param <N>
     * @param astNode
     * @param selectedActionFn
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Consumer<Select> selectedActionFn ){
        astNode.walk( MethodDeclaration.class, m-> {
            Select s = select( m );
            if( s != null ){
                selectedActionFn.accept( s );
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
    public <N extends _node> N replaceIn( N _n, $method $replace ){
        return forSelectedIn(_n, s -> {
            _method repl = $replace.construct(Translator.DEFAULT_TRANSLATOR, s.args);
            s._m.ast().replace(repl.ast());
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
        return replaceIn(_n, $method.of(replacementProto));        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param method
     * @return 
     */
    public <N extends _node> N replaceIn( N _n, _method method ){
        return replaceIn(_n, $method.of(method));        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param astMethod
     * @return 
     */
    public <N extends _node> N replaceIn( N _n, MethodDeclaration astMethod ){
        return replaceIn(_n, $method.of(astMethod));        
    }
    
    @Override
    public <N extends _node> N removeIn(N _n ){
        Walk.in(_n, MethodDeclaration.class, e-> {
            $args tokens = this.deconstruct( e );
            if( tokens != null ){
                e.removeForced();
            }
        });
        return _n;
    }

    @Override
    public <N extends Node> N removeIn(N astNode){
        astNode.walk(MethodDeclaration.class, e-> {
            $args tokens = this.deconstruct( e );
            if( tokens != null ){
                e.removeForced();
            }
        });
        return astNode;
    }

    @Override
    public <N extends Node> N forEachIn(N astNode, Consumer<_method> _methodActionFn){
        astNode.walk(MethodDeclaration.class, e-> {
            $args tokens = this.deconstruct( e );
            if( tokens != null ){
                _methodActionFn.accept( _method.of(e) );
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_method> _methodActionFn){
        Walk.in(_n, MethodDeclaration.class, e -> {
            $args tokens = this.deconstruct( e );
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
    public static class Select implements $proto.selected, 
            $proto.selectedAstNode<MethodDeclaration>, 
            $proto.selected_model<_method> {
        
        public final _method _m;
        public final $args args;

        public Select( _method _m, $args tokens ){
            this._m = _m;
            this.args = tokens;
        }
                
        public Select( MethodDeclaration astMethod, $args tokens ){
            this._m = _method.of(astMethod);
            this.args = tokens;
        }

        @Override
        public $args getArgs(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$method.Select{"+ System.lineSeparator()+
                Text.indent(_m.toString() )+ System.lineSeparator()+
                Text.indent("$args : " + args) + System.lineSeparator()+
                "}";
        }

        @Override
        public MethodDeclaration ast() {
            return _m.ast();
        }

        @Override
        public _method model() {
            return _m;
        }
    }
}
