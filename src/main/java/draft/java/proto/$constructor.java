package draft.java.proto;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import draft.*;
import draft.java.*;
import draft.java._model._node;
import draft.java._typeParameter._typeParameters;
import draft.java.macro._ctor;
import draft.java.macro._macro;
import draft.java.macro._remove;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * prototype/template for a Java {@link _constructor}
 */
public class $constructor
    implements Template<_constructor>, $proto<_constructor> {
    
    /**
     * Marker interface for designating prototypes that are "part" of
     * the $constructor
     * (i.e. all of the components $annos, $annos, $id, $body,... )
     * that make up the $constructor
     */
    interface $part{
        
    }
    
    /**
     * list all constructors in the clazz
     * @param clazz
     * @return 
     */
    public static final List<_constructor> list(Class clazz){
        return any().listIn(clazz);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @return 
     */
    public static final <N extends _node> List<_constructor> list( N _n ){
        return any().listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<_constructor> list( N _n, String... pattern ){
        return $constructor.of(pattern).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @return 
     */
    public static final <N extends _node> List<_constructor> list( N _n, _constructor _proto ){
        return $constructor.of(_proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_constructor> list( N _n, String pattern, Predicate<_constructor> constraint){
        return $constructor.of(pattern, constraint).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_constructor> list( N _n, _constructor _proto, Predicate<_constructor> constraint){
        return $constructor.of(_proto, constraint).listIn(_n);
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final List<_constructor> list( Class clazz, String... pattern ){
        return $constructor.of(pattern).listIn(_type.of(clazz));
    }
    
    /**
     * 
     * @param clazz
     * @param _proto
     * @return 
     */
    public static final List<_constructor> list(Class clazz, _constructor _proto ){
        return $constructor.of(_proto).listIn(_type.of(clazz));
    }
    
    /**
     * 
     * @param clazz
     * @param proto
     * @param constraint
     * @return 
     */
    public static final List<_constructor> list( Class clazz, String proto, Predicate<_constructor> constraint){
        return $constructor.of(proto, constraint).listIn(_type.of(clazz) );
    }
    
    /**
     *
     * @param clazz
     * @param _proto
     * @param constraint
     * @return 
     */
    public static final List<_constructor> list( Class clazz, _constructor _proto, Predicate<_constructor> constraint){
        return $constructor.of(_proto, constraint).listIn(_type.of(clazz) );
    }

    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final _constructor first(Class clazz, String... pattern){
        return $constructor.of(pattern).firstIn(_type.of(clazz) );
    }
    
    /**
     * 
     * @param clazz
     * @param _proto
     * @return 
     */
    public static final _constructor first(Class clazz, _constructor _proto){
        return $constructor.of(_proto).firstIn(_type.of(clazz) );
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final _constructor first(Class clazz, String pattern, Predicate<_constructor> constraint){
        return $constructor.of(pattern, constraint).firstIn(_type.of(clazz) );
    }
    
    /**
     * 
     * @param clazz
     * @param _proto
     * @param constraint
     * @return 
     */
    public static final _constructor first(Class clazz, _constructor _proto, Predicate<_constructor> constraint){
        return $constructor.of(_proto, constraint).firstIn(_type.of(clazz) );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> _constructor first(N _n, String... pattern){
        return $constructor.of(pattern).firstIn(_n );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @return 
     */
    public static final<N extends _node> _constructor first(N _n, _constructor _proto){
        return $constructor.of(_proto).firstIn(_n );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _constructor first(N _n, String pattern, Predicate<_constructor> constraint){
        return $constructor.of(pattern, constraint).firstIn(_n );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _constructor first(N _n, _constructor _proto, Predicate<_constructor> constraint){
        return $constructor.of(_proto, constraint).firstIn(_n );
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final Select selectFirst(Class clazz, String... pattern){
        return $constructor.of(pattern).selectFirstIn(_type.of(clazz) );
    }
    
    /**
     * 
     * @param clazz
     * @param _proto
     * @return 
     */
    public static final Select selectFirst(Class clazz, _constructor _proto){
        return $constructor.of(_proto).selectFirstIn(_type.of(clazz) );
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @param selectConstraint
     * @return 
     */
    public static final Select selectFirst(Class clazz, String pattern, Predicate<Select> selectConstraint){
        return $constructor.of(pattern).selectFirstIn(_type.of(clazz), selectConstraint );
    }
    
    /**
     * 
     * @param clazz
     * @param _proto
     * @param selectConstraint
     * @return 
     */
    public static final Select selectFirst(Class clazz, _constructor _proto, Predicate<Select> selectConstraint){
        return $constructor.of(_proto ).selectFirstIn(_type.of(clazz), selectConstraint);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> Select selectFirst(N _n, String... pattern){
        return $constructor.of(pattern).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst(N _n, _constructor _proto){
        return $constructor.of(_proto).selectFirstIn(_n );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConstraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst(N _n, String pattern, Predicate<Select> selectConstraint){
        return $constructor.of(pattern).selectFirstIn(_n, selectConstraint );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param selectConstraint
     * @return 
     */   
    public static final <N extends _node> Select selectFirst(N _n, _constructor _proto, Predicate<Select> selectConstraint){
        return $constructor.of(_proto ).selectFirstIn(_n, selectConstraint);
    }
    
    /**
     * Removes all occurrences of the prototype method in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param _proto
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, _constructor _proto ){
        return $constructor.of(_proto).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String... proto ){
        return $constructor.of(proto).removeIn(_n);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param clazz
     * @param _proto
     * @return the modified N
     */
    public static final _type remove( Class clazz, _constructor _proto ){
        return $constructor.of(_proto).removeIn(_type.of(clazz));
    }
    
    /**
     * @param clazz
     * @param proto
     * @return 
     */
    public static final _type remove( Class clazz, String... proto ){
        return $constructor.of(proto).removeIn(_type.of(clazz));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _protoSource
     * @param _protoReplacement
     * @return 
     */
    public static final <N extends _node> N replace( N _n, _constructor _protoSource, _constructor _protoReplacement ){
        return $constructor.of(_protoSource).replaceIn(_n, _protoReplacement);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param targetCtor
     * @param replacementCtor
     * @return 
     */
    public static final <N extends _node> N replace( N _n, String[] targetCtor, String[] replacementCtor ){
        return $constructor.of(targetCtor).replaceIn(_n, replacementCtor);
    }
    
    /**
     * 
     * @param clazz
     * @param _protoTarget
     * @param _protoReplacement
     * @return 
     */
    public static final _type replace( Class clazz, _constructor _protoTarget, _constructor _protoReplacement ){
        return $constructor.of(_protoTarget).replaceIn(_type.of(clazz), _protoReplacement);
    }
    
    /**
     * 
     * @param clazz
     * @param protoTarget
     * @param replacementProto
     * @return 
     */
    public static final _type replace( Class clazz, String[] protoTarget, String[] replacementProto ){
        return $constructor.of(protoTarget).replaceIn(_type.of(clazz), replacementProto);
    }
    
    /**
     * 
     * @param ctorPattern
     * @return 
     */
    public static $constructor of( String ctorPattern ){
        return of(new String[]{ctorPattern});
    }
    
    /**
     * Pass in an anonymous Object containing the method to import
     * NOTE: if the anonymous Object contains more than one method, ENSURE only one method
     * DOES NOT have the @_remove annotation, (mark all trivial METHODS with @_remove)
     *
     * @param anonymousObjectContainingMethod
     * @return
     */
    public static $constructor of( Object anonymousObjectContainingMethod ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject( ste );        
        
        _class _c = _class.of("C");
        if( oce.getAnonymousClassBody().isPresent() ){
            NodeList<BodyDeclaration<?>> bs = oce.getAnonymousClassBody().get();
            bs.forEach( b -> _c.ast().addMember(b));
        }
        
        //run macros on the things
        _macro.to( anonymousObjectContainingMethod.getClass(), _c);
        
        MethodDeclaration theMethod = (MethodDeclaration)
            oce.getAnonymousClassBody().get().stream().filter(m -> m instanceof MethodDeclaration &&
                !m.isAnnotationPresent(_remove.class) ).findFirst().get();
        
        //build the base method first
        _constructor _ct = _constructor.of( theMethod.getNameAsString() + " " +_parameter._parameters.of( theMethod )+"{}" );
        
        //MODIFIERS
        if( theMethod.isPublic() ){
            _ct.setPublic();
        }
        if(theMethod.isProtected()){
            _ct.setProtected();
        }
        if(theMethod.isPrivate()){
            _ct.setPrivate();
        }
        if( theMethod.hasJavaDocComment() ){
            _ct.javadoc(theMethod.getJavadocComment().get());
        }
        _ct.setThrows( theMethod.getThrownExceptions() );
        _ct.annotate( theMethod.getAnnotations()); //add annos
        _ct.removeAnnos(_ctor.class); //remove the _ctor anno if it exists
        _ct.setBody( theMethod.getBody().get() ); //BODY
        
        return of(_ct);        
    }

    public static $constructor any(){
        return new $constructor(_constructor.of("$name$(){}") );
    }
    
    /**
     * 
     * @param _ct
     * @return 
     */
    public static $constructor of( _constructor _ct ){
        return new $constructor( _ct);
    }

    /**
     * 
     * @param _ct
     * @param constraint
     * @return 
     */
    public static $constructor of( _constructor _ct, Predicate<_constructor> constraint){
        return new $constructor( _ct).constraint(constraint);
    }
    
    /**
     * 
     * @param pattern
     * @return 
     */
    public static $constructor of( String...pattern ){
        return new $constructor(_constructor.of(pattern));
    }
    
    /**
     * 
     * @param pattern
     * @param constraint
     * @return 
     */
    public static $constructor of( String pattern, Predicate<_constructor> constraint ){
        return new $constructor(_constructor.of(pattern) ).constraint(constraint);
    }

    /**
     * 
     * @param component
     * @return 
     */
    public static $constructor of( $part component ){       
        $part[] pr = new $part[]{component};
        return of(pr);
    }
    
    /**
     * 
     * @param components
     * @return 
     */
    public static $constructor of( $part...components ){       
        return new $constructor( components );
    }
    
    public Predicate<_constructor> constraint = t -> true;
    
    public $component<_javadoc> javadoc = new $component( "$javadoc$", t->true);    
    public $annos annos = new $annos();
    public $modifiers modifiers = $modifiers.any();
    
    public $component<_typeParameters> typeParameters = new $component( "$typeParameters$", t->true);
    public $id name = $id.any();
    public $parameters parameters = $parameters.of();
    //public $component<_throws> thrown = new $component( "$throws$", t-> true);
    public $throws thrown = $throws.any();
    public $body body = $body.any();
    
    private $constructor( _constructor _ct ){
        this(_ct, t-> true );
    }
    
    private $constructor(){        
    }
    
    /**
     * i.e. 
     * 
     * //look for all constructors with a matching annotation to A
     * $constructor $ct = $constructor.of( $anno.of("A") );
     * 
     * @param components 
     */
    private $constructor( $part...components ){        
        for(int i=0;i<components.length; i++){
            if( components[i] instanceof $annos ){
                this.annos = ($annos)components[i];
            }
            else if( components[i] instanceof $anno ){
                this.annos.$annosList.add( ($anno)components[i] );
            }
            else if( components[i] instanceof $modifiers ){
                this.modifiers = ($modifiers)components[i];
            }
            else if( components[i] instanceof $id ){
                this.name = ($id)components[i];
            }
            else if( components[i] instanceof $parameters ){
                this.parameters = ($parameters)components[i];
            }
            else if( components[i] instanceof $body ){
                this.body = ($body)components[i];
            } 
            else if( components[i] instanceof $throws ){
                this.thrown = ($throws)components[i];
            }
            else{
                throw new DraftException("Unable to use $proto component " +components[i]+" for $constructor" );
            }            
        }
    }
    
    /**
     * 
     * @param _proto 
     */
    private $constructor( _constructor _ct, Predicate<_constructor> constraint){
        
        if( _ct.hasJavadoc() ){
            javadoc.pattern(_ct.getJavadoc().toString() );
        }        
        if( _ct.hasAnnos() ){
            annos = $annos.of(_ct.getAnnos() );
        }
        modifiers = $modifiers.of(_ct );        
        if( !_ct.hasTypeParameters() ){
            final _typeParameters etps = _ct.getTypeParameters();
            typeParameters = new $component(
                "$typeParameters$", 
                (tps)-> tps.equals(etps) );
        }
        name = $id.of(_ct.getName() );
        if( _ct.hasParameters() ){
            parameters = $parameters.of(_ct.getParameters());
        }        
        thrown = $throws.of( _ct.getThrows() );
        //if( _ct.hasThrows() ){
        //    final _throws ths = _ct.getThrows();
        //    thrown = new $component( "$throws$", (t)-> t.equals(ths) );
        //}
        if( _ct.hasBody() ){            
            body = $body.of(_ct.ast());    
        }
        this.constraint = constraint;
    }
    
    /**
     * ADDS an additional matching constraint to the prototype
     * @param constraint a constraint to be added
     * @return the modified prototype
     */
    public $constructor addConstraint( Predicate<_constructor>constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    /**
     * SETS/ OVERRIDES the matching constraint
     * @param constraint
     * @return 
     */
    public $constructor constraint( Predicate<_constructor> constraint){
        this.constraint = constraint;
        return this;
    }
    
    @Override
    public List<String> list$Normalized(){
        List<String>normalized$ = new ArrayList<>();
        normalized$.addAll( javadoc.pattern.list$Normalized() );
        normalized$.addAll( annos.list$Normalized() );
        normalized$.addAll( typeParameters.pattern.list$Normalized() );
        normalized$.addAll( name.pattern.list$Normalized() );
        normalized$.addAll( parameters.list$Normalized() );
        
        //normalized$.addAll( thrown.pattern.list$Normalized() );
        normalized$.addAll( thrown.list$Normalized() );
        normalized$.addAll( body.list$Normalized());
        return normalized$.stream().distinct().collect(Collectors.toList());        
    }

    @Override
    public List<String> list$(){
        List<String>all$ = new ArrayList<>();
        all$.addAll( javadoc.pattern.list$() );
        all$.addAll( annos.list$() );
        all$.addAll( typeParameters.pattern.list$() );
        all$.addAll( name.pattern.list$() );
        all$.addAll( parameters.list$() );
        all$.addAll( thrown.list$() );
        //all$.addAll( thrown.pattern.list$() );
        all$.addAll( body.list$() );        
        return all$;
    }
    
    public $constructor $parameters( ){
        this.parameters = $parameters.any();
        return this;
    }
    
    public $constructor $parameters($parameters $ps ){
        this.parameters = $ps;
        return this;
    }
    
    public $constructor $annos(){
        this.annos = $annos.any();
        return this;
    }
    
    public $constructor $annos( $annos $as){
        this.annos = $as;
        return this;
    }
    
    public $constructor $annos(String...annoPatterns ){
        this.annos.add(annoPatterns);
        return this;
    }
    
    public $constructor $anno( $anno $a){
        this.annos.$annosList.add($a);
        return this;
    }
    
    public $constructor $name(){
        this.name = $id.any();
        return this;
    }
    
    public $constructor $name(String name){
        this.name.pattern = Stencil.of(name);
        return this;
    }
    
    public $constructor $name($id name ){
        this.name = name;
        return this;
    }
    
    public $constructor $typeParameters(){
        this.typeParameters.pattern("$typeParameters$");
        return this;
    }
    
    public $constructor $typeParameters(String typeParameters){
        this.typeParameters.pattern(typeParameters);
        return this;
    }

    public $constructor $modifiers(){
        this.modifiers = $modifiers.any();
        return this;
    }
    
    public $constructor $modifiers( Modifier...modifiers ){
        this.modifiers = $modifiers.of(modifiers);
        return this;
    }
    
    public $constructor $modifiers( $modifiers $ms ){
        this.modifiers = $ms;
        return this;
    }
    
    public $constructor $throws(){
        this.thrown = $throws.any();
        //this.thrown.pattern = Stencil.of("$throws$");
        return this;
    }
    
    public $constructor $throws( $throws $th ){
        this.thrown = $th;
        return this;
    }
    
    public $constructor $javadoc(){
        this.javadoc.pattern = Stencil.of( "$javadoc$" ); 
        return this;
    }
    
    public $constructor $javadoc( String... form ){
        this.javadoc.pattern(form);
        return this;
    }
    
    public $constructor $body (){
        this.body = $body.any();
        return this;
    }
    
    public $constructor $body( $body body ){
        this.body = body;
        return this;
    }
    
    public $constructor $body( String... body){
        this.body = $body.of(body);
        return this;
    }
    
    public $constructor $emptyBody(){
        body = $body.of("{}");
        return this;
    }
    
    @Override
    public _constructor fill(Translator translator, Object... values) {
        List<String> nom = this.list$Normalized();
        nom.remove( "javadoc");
        nom.remove( "annos");
        nom.remove( "modifiers");
        nom.remove( "typeParameters");
        nom.remove( "parameters");
        nom.remove( "throws");
        nom.remove("body");
        if( nom.size() != values.length ){
            throw new DraftException ("Fill expected ("+nom.size()+") values "+ nom+" got ("+values.length+")");
        }
        Tokens ts = new Tokens();
        for(int i=0;i<nom.size();i++){
            ts.put( nom.get(i), values[i]);
        }
        return construct(translator, ts);        
    }
    
    @Override
    public _constructor construct(Translator translator, Map<String, Object> keyValues) {
        
        //the base values (so we dont get Nulls for base values
        Tokens base = Tokens.of(
                "javadoc", "", 
                "annos", "", 
                "modifiers", "", 
                "typeParameters", "", 
                "parameters", "()", 
                "throws", "", 
                "body", "");
        
        
        base.putAll(keyValues);
        
        StringBuilder sb = new StringBuilder();   
        sb.append( javadoc.compose(translator, base ));        
        sb.append(System.lineSeparator());
        sb.append( annos.compose(translator, base));
        sb.append(System.lineSeparator());
        sb.append( modifiers.compose(translator, base));
        sb.append(" ");
        sb.append( typeParameters.compose(translator, base));
        sb.append(" ");
        sb.append( name.compose(translator, base));
        sb.append(" ");
        sb.append( parameters.construct(translator, base));
        sb.append(" ");
        sb.append( thrown.construct(translator, base));
        sb.append(System.lineSeparator());
        sb.append( body.construct(translator, keyValues));
        return _constructor.of(sb.toString() );        
    }
    
    /**
     * 
     * @param _n
     * @return 
     */
    public _constructor construct(_node _n ){
        return construct(_n.deconstruct() );
    }

    public static final BlockStmt EMPTY = Stmt.block("{}");

    /**
     * 
     * @param _m
     * @return 
     */
    public Select select( _constructor _m){
        if( !this.constraint.test(_m)){
            return null;
        }
        if( modifiers.select(_m) == null ){
            return null;
        }
        Tokens all = new Tokens();
        all = javadoc.decomposeTo(_m.getJavadoc(), all);
        all = annos.decomposeTo(_m.getAnnos(), all);
        
        //all = modifiers.decomposeTo(_m.getModifiers(), all);
        all = typeParameters.decomposeTo(_m.getTypeParameters(), all);
        all = name.decomposeTo(_m.getName(), all);
        all = parameters.decomposeTo(_m.getParameters(), all);
        all = thrown.decomposeTo(_m.getThrows(), all);
        all = body.decomposeTo(_m.getBody(), all);
        if( all != null ){
            return new Select( _m, $nameValues.of(all));
        }
        return null;        
    }

    /**
     * 
     * @param astCtor
     * @return 
     */
    public Select select( ConstructorDeclaration astCtor){
        return select(_constructor.of(astCtor));
    }
    
    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $constructor hardcode$( Tokens kvs ) {
        return hardcode$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $constructor hardcode$( Object... keyValues ) {
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
    public $constructor hardcode$( Translator translator, Object... keyValues ) {
        return hardcode$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $constructor hardcode$( Translator translator, Tokens kvs ) {
        javadoc.pattern = javadoc.pattern.hardcode$(translator, kvs);
        annos = annos.hardcode$(translator, kvs);
        typeParameters.pattern = typeParameters.pattern.hardcode$(translator, kvs);
        name.pattern = name.pattern.hardcode$(translator, kvs);
        parameters = parameters.hardcode$(translator, kvs);
        thrown = thrown.hardcode$(translator, kvs);
        //thrown.pattern = thrown.pattern.hardcode$(translator, kvs);
        body = body.hardcode$(translator, kvs );
        
        return this;
    }

    /** Post - parameterize, create a parameter from the target string named $Name#$*/
    @Override
    public $constructor $(String target, String $Name) {
        javadoc.pattern = javadoc.pattern.$(target, $Name);
        annos = annos.$(target, $Name);
        typeParameters.pattern = typeParameters.pattern.$(target, $Name);
        name.pattern = name.pattern.$(target, $Name);
        parameters = parameters.$(target, $Name);
        thrown = thrown.$(target, $Name);
        //thrown.pattern = thrown.pattern.$(target, $Name);
        body = body.$(target, $Name);
        return this;
    }

    /**
     * 
     * @param astExpr
     * @param $name
     * @return 
     */
    public $constructor $(Expression astExpr, String $name ){
        String exprString = astExpr.toString();
        return $(exprString, $name);
    }

    /**
     * 
     * @param _ct
     * @return 
     */
    public boolean matches( _constructor _ct ){
        return select( _ct ) != null;
    }

    /**
     * 
     * @param astCtor
     * @return 
     */
    public boolean matches( ConstructorDeclaration astCtor ){
        return select(astCtor ) != null;
    }

    /**
     * returns the first matching method from the source of the _class (or null)
     * @param clazz
     * @return 
     */
    public _constructor firstIn( Class clazz){
        return firstIn(_type.of(clazz));
    }
    
    /**
     * Returns the first _constructor that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _constructor that matches (or null if none found)
     */
    public _constructor firstIn( _node _n ){
        Optional<ConstructorDeclaration> f = _n.ast().findFirst(
            ConstructorDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return _constructor.of(f.get());
        }
        return null;
    }

    /**
     * Returns the first _constructor that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _constructor that matches (or null if none found)
     */
    public _constructor firstIn( Node astNode ){
        Optional<ConstructorDeclaration> f = astNode.findFirst(
            ConstructorDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return _constructor.of(f.get());
        }
        return null;
    }

    /**
     * Returns the first _constructor that matches the pattern and constraint
     * @param clazz the runtime class (WITH source available in classpath)
     * @return  the first _constructor that matches (or null if none found)
     */
    public Select selectFirstIn( Class clazz){
        return selectFirstIn( _type.of(clazz));
    }
    
    /**
     * Returns the first _constructor that matches the pattern and constraint
     * @param clazz the runtime class (WITH source available in classpath)
     * @param selectConstraint
     * @return  the first _constructor that matches (or null if none found)
     */
    public Select selectFirstIn( Class clazz, Predicate<Select> selectConstraint){
        return selectFirstIn( _type.of(clazz), selectConstraint);
    }
    
    /**
     * Returns the first _constructor that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _constructor that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n ){
        Optional<ConstructorDeclaration> f = _n.ast().findFirst(ConstructorDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }

    /**
     * Returns the first _constructor that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _constructor that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode ){
        Optional<ConstructorDeclaration> f = astNode.findFirst(ConstructorDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }
    
    /**
     * Returns the first _constructor that matches the pattern and constraint
     * @param _n the _java node
     * @param selectConstraint
     * @return  the first _constructor that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n, Predicate<Select> selectConstraint){
        Optional<ConstructorDeclaration> f = _n.ast().findFirst(ConstructorDeclaration.class, s -> {
            Select sel = this.select(s);
            return sel != null && selectConstraint.test(sel);
            });               
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }

    /**
     * Returns the first _constructor that matches the pattern and constraint
     * @param astNode the node to look through
     * @param selectConstraint
     * @return  the first _constructor that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode, Predicate<Select> selectConstraint){
        Optional<ConstructorDeclaration> f = astNode.findFirst(ConstructorDeclaration.class, s -> {
            Select sel = this.select(s);
            return sel != null && selectConstraint.test(sel);
            });         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }
    
    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(Class clazz, Predicate<Select> selectConstraint){
        return $constructor.this.listSelectedIn(_type.of(clazz), selectConstraint);
    }
    
    /**
     * 
     * @param astNode
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(Node astNode, Predicate<Select> selectConstraint){
        List<Select>sts = new ArrayList<>();
        astNode.walk(ConstructorDeclaration.class, m-> {
            Select sel = select( m );
            if( sel != null && selectConstraint.test(sel)) {
                sts.add(sel);
            }
        });
        return sts;
    }
    
    @Override
    public List<Select> listSelectedIn(Node astNode){
        List<Select>sts = new ArrayList<>();
        astNode.walk(ConstructorDeclaration.class, m-> {
            Select sel = select( m );
            if( sel != null ){
                sts.add(sel);
            }
        });
        return sts;
    }

    /**
     * 
     * @param _n
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(_node _n, Predicate<Select> selectConstraint){
        List<Select>sts = new ArrayList<>();
        Walk.in(_n, ConstructorDeclaration.class, m -> {
            Select sel = select( m );
            if( sel != null && selectConstraint.test(sel)){
                sts.add(sel);
            }
        });
        return sts;
    }
    
    @Override
    public List<Select> listSelectedIn(_node _n){
        List<Select>sts = new ArrayList<>();
        Walk.in(_n, ConstructorDeclaration.class, m -> {
            Select sel = select( m );
            if( sel != null ){
                sts.add(sel);
            }
        });
        return sts;
    }
    
    /**
     * 
     * @param clazz
     * @return 
     */
    @Override
    public List<Select> listSelectedIn(Class clazz){
        return listSelectedIn( _type.of(clazz));        
    }
    
    /**
     * 
     * @param clazz
     * @param selectActionFn
     * @return 
     */
    public _type forSelectedIn(Class clazz, Consumer<Select> selectActionFn ){
        return forSelectedIn(_type.of(clazz), selectActionFn );
    }
    
    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @param selectActionFn
     * @return 
     */
    public _type forSelectedIn(Class clazz, Predicate<Select>selectConstraint, Consumer<Select> selectActionFn ){
        return forSelectedIn(_type.of(clazz), selectConstraint, selectActionFn );
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param selectedActionFn
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Consumer<Select> selectedActionFn ){
        astNode.walk( ConstructorDeclaration.class, c-> {
            Select s = select(c );
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
        Walk.in(_n, _constructor.class, c ->{
            Select s = select(c );
            if( s != null ){
                selectedActionFn.accept( s );
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
    public <N extends Node> N forSelectedIn(
        N astNode, Predicate<Select> selectConstraint, Consumer<Select> selectedActionFn ){
        
        astNode.walk( ConstructorDeclaration.class, c-> {
            Select s = select(c );
            if( s != null && selectConstraint.test(s)){
                selectedActionFn.accept( s );
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
    public <N extends _node> N forSelectedIn(N _n, Predicate<Select> selectConstraint, Consumer<Select> selectedActionFn ){
        Walk.in(_n, _constructor.class, c ->{
            Select s = select(c );
            if( s != null && selectConstraint.test(s)){
                selectedActionFn.accept( s );
            }
        });
        return _n;
    }
    
    /**
     * 
     * @param clazz
     * @param $replace
     * @return 
     */
    public _type replaceIn(Class clazz, $constructor $replace ){
        return forSelectedIn(_type.of(clazz), s -> {
            _constructor repl = $replace.construct(Translator.DEFAULT_TRANSLATOR, s.args);
            s._ct.ast().replace(repl.ast());
        });
    }

    /**
     * 
     * @param clazz
     * @param replacementProto
     * @return 
     */
    public _type replaceIn(Class clazz,  String... replacementProto ){
        return replaceIn(_type.of(clazz), $constructor.of(replacementProto));        
    }
    
    /**
     * 
     * @param clazz
     * @param _ct
     * @return 
     */
    public _type replaceIn(Class clazz,  _constructor _ct ){
        return replaceIn(_type.of(clazz), $constructor.of(_ct));        
    }
    
    /**
     * 
     * @param clazz
     * @param astCtor
     * @return 
     */
    public _type replaceIn(Class clazz, ConstructorDeclaration astCtor ){
        return replaceIn(_type.of(clazz), $constructor.of(astCtor));        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param $replace
     * @return 
     */
    public <N extends _node> N replaceIn( N _n, $constructor $replace ){
        return forSelectedIn(_n, s -> {
            _constructor repl = $replace.construct(Translator.DEFAULT_TRANSLATOR, s.args.asTokens());
            s._ct.ast().replace(repl.ast());
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
        return replaceIn(_n, $constructor.of(replacementProto));        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _ct
     * @return 
     */
    public <N extends _node> N replaceIn( N _n, _constructor _ct ){
        return replaceIn(_n, $constructor.of(_ct));        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param astCtor
     * @return 
     */
    public <N extends _node> N replaceIn( N _n, ConstructorDeclaration astCtor ){
        return replaceIn(_n, $constructor.of(astCtor));        
    }
    
    /**
     * removes the matching prototype methods from the source of the class
     * and returns the modified _type
     * @param clazz
     * @return 
     */
    @Override
    public _type removeIn(Class clazz){
        return removeIn(_type.of(clazz));
    }
    
    @Override
    public <N extends _node> N removeIn(N _n ){
        Walk.in(_n, ConstructorDeclaration.class, e-> {
            //$args tokens = this.deconstruct( e );
            
            if( select(e) != null ){
                e.removeForced();
            }
        });
        return _n;
    }

    @Override
    public <N extends Node> N removeIn(N astNode){
        astNode.walk(ConstructorDeclaration.class, e-> {
            //$args tokens = this.deconstruct( e );
            if( select(e) != null ){
                e.removeForced();
            }
        });
        return astNode;
    }
    
    @Override
    public <N extends Node> N forEachIn(N astNode, Consumer<_constructor> _constructorActionFn){
        astNode.walk(ConstructorDeclaration.class, e-> {
            //$args tokens = this.deconstruct( e );
            if( select(e) != null ){
                _constructorActionFn.accept( _constructor.of(e) );
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_constructor> _constructorActionFn){
        Walk.in(_n, ConstructorDeclaration.class, e -> {
            //$args tokens = this.deconstruct( e );
            if( select(e) != null ){
                _constructorActionFn.accept( _constructor.of(e) );
            }
        });
        return _n;
    }

    @Override
    public List<_constructor> listIn( Class clazz){
        return listIn(_type.of(clazz));
    }
    
    @Override
    public List<_constructor> listIn(_node _n ){
        return listIn(_n.ast() );
    }

    @Override
    public List<_constructor> listIn(Node astNode ){
        List<_constructor> typesList = new ArrayList<>();
        astNode.walk(ConstructorDeclaration.class, m->{
            if( this.matches(m) ){
                typesList.add( _constructor.of(m));
            }
        } );
        return typesList;
    }

    /**
     * A Matched Selection result returned from matching a prototype $ctor
     * inside of some Node or _node
     */
    public static class Select implements $proto.selected, 
            $proto.selectedAstNode<ConstructorDeclaration>, 
            $proto.selected_model<_constructor> {
        
        public final _constructor _ct;
        public final $nameValues args;

        public Select( _constructor _m, $nameValues tokens ){
            this._ct = _m;
            this.args = tokens;
        }
                
        public Select( ConstructorDeclaration astMethod, $nameValues tokens ){
            this._ct = _constructor.of(astMethod);
            this.args = tokens;
        }

        @Override
        public $nameValues args(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$ctor.Select{"+ System.lineSeparator()+
                Text.indent(_ct.toString() )+ System.lineSeparator()+
                Text.indent("$args : " + args) + System.lineSeparator()+
                "}";
        }

        @Override
        public ConstructorDeclaration ast() {
            return _ct.ast();
        }

        @Override
        public _constructor model() {
            return _ct;
        }
        
        public boolean isNamed(String name){
            return _ct.isNamed(name);
        }
        
        public boolean isParameters(String...params ){
            return _ct.getParameters().is(params);
        }
        
        public boolean isVarArg(){
            return _ct.isVarArg();
        }
        
        public boolean hasBody(){
            return _ct.hasBody();
        }
        
        public boolean isThrows(String...throwDecl){
            return _ct.getThrows().is(throwDecl);
        }
        
        public boolean hasThrows(){
            return _ct.hasThrows();
        }
        
        public boolean hasThrow(Class<? extends Throwable> throwsClass){            
            return _ct.hasThrow(throwsClass);
        }
        
        public boolean hasParameters(){            
            return _ct.hasParameters();
        }
        
        public boolean is(String...methodDeclaration){
            return _ct.is(methodDeclaration);
        }
        
        public boolean hasTypeParameters(){            
            return _ct.hasTypeParameters();
        }
    }
}
