package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.Type;
import draft.*;
import draft.java.*;
import draft.java._anno._annos;
import draft.java._java._node;
import draft.java._parameter._parameters;
import draft.java._typeParameter._typeParameters;
import draft.java.macro._macro;
import draft.java.macro._remove;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * prototype/template for a Java {@link _method}
 */
public class $method
    implements Template<_method>, $proto<_method> {
       
    /**
     * Marker interface for categorizing/identifying parts that make up the
     * $method
     */
    public interface $part{}
    
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

    public static $method any(){
        return of();
    }
    
    public static $method of(){
        return new $method(_method.of("$type$ $name$();") ).anyBody();
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
        return new $method( _m).addConstraint(constraint);
    }
        
    /**
     * 
     * @param clazz
     * @param name
     * @return 
     */
    public static $method of( Class clazz, String name ){
        _method._hasMethods  _hm = (_method._hasMethods)_java.type(clazz);
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
        return new $method(_method.of(proto) ).addConstraint(constraint);
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static $method of( Predicate<_method> constraint ){
        return of().addConstraint(constraint);
    }
    
    /**
     * 
     * @param part
     * @return 
     */
    public static $method of( $part part ){
        $part[] parts = new $part[]{part};
        return of(parts);
    }
    
    /**
     * Build a method from the constituent parts
     * @param parts the parts of the $method
     * @return the $method
     */
    public static $method of( $part...parts ){
        return new $method( parts ); 
    }

    public Predicate<_method> constraint = t -> true;
    
    //public $component<_javadoc> javadoc = new $component( "$javadoc$", t->true);    
    public $comment<JavadocComment> javadoc = $comment.javadocComment("$javadoc$");
    public $annos annos = new $annos();
    public $modifiers modifiers = $modifiers.of();
    public $typeRef type = $typeRef.of();
    
    //public $component<_typeParameters> typeParameters = new $component( "$typeParameters$", t->true);    
    public $typeParameters typeParameters = $typeParameters.any();
    public $id name = $id.of();    
    public $parameters parameters = $parameters.of();
    public $throws thrown = $throws.of();
    public $body body = $body.of();
    
    private $method( _method _p ){
        this( _p, t-> true );
    }
    
    /**
     * Build a $method from component parts
     * @param parts 
     */
    private $method($part ...parts ){
        for(int i=0;i<parts.length;i++){
            if( parts[i] instanceof $annos ){
                this.annos = ($annos)parts[i];
            }
            else if(parts[i] instanceof $anno){
                this.annos.$annosList.add( ($anno)parts[i]);
            }
            else if(parts[i] instanceof $modifiers){
                this.modifiers = ($modifiers)parts[i];
            }
            else if(parts[i] instanceof $typeRef){
                this.modifiers = ($modifiers)parts[i];
            }
            else if(parts[i] instanceof $id){
                this.name = ($id)parts[i];
            }
            else if(parts[i] instanceof $parameters){
                this.parameters = ($parameters)parts[i];
            }
            else if(parts[i] instanceof $body){
                this.body = ($body)parts[i];
            }
            else if( parts[i] instanceof $throws ){
                this.thrown = ($throws)parts[i];
            }
            else if( parts[i] instanceof $typeParameters ){
                this.typeParameters = ($typeParameters)parts[i];
            }
            else if( parts[i] instanceof $typeParameter ){
                this.typeParameters.typeParams.add( ($typeParameter)parts[i]);
            }
            else if( parts[i] instanceof $comment ){
                this.javadoc = ($comment<JavadocComment>)parts[i];
            }
            else{
                throw new DraftException("Unable to add $part "+ parts[i]+" to $method" );
            }
        }
    }
    
    /**
     * 
     * @param _proto 
     */
    private $method( _method _m , Predicate<_method> constraint){
        
        if( _m.hasJavadoc() ){
            javadoc = $comment.javadocComment(_m.getJavadoc() );
        }        
        if( _m.hasAnnos() ){
            annos = $annos.of(_m.getAnnos() );
        }
        modifiers = $modifiers.of(_m);
        type = $typeRef.of(_m.getType() );
        if( !_m.hasTypeParameters() ){
            final _typeParameters etps = _m.getTypeParameters();
            typeParameters = $typeParameters.of( etps );           
        }
        name = $id.of(_m.getName());
        if( _m.hasParameters() ){
            parameters = $parameters.of(_m.getParameters());
        }        
        thrown = $throws.of(_m.getThrows());
        body = $body.of( _m.ast() );
        this.constraint = constraint;
    }
    
    /**
     * matches a body that is implemented i.e. "m(){ ... }" 
     * -or- not implemented
     * "m();"
     * @return the $method prototype
     */
    public $method anyBody(){
        this.body = $body.of();
        return this;
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
    
    @Override
    public List<String> list$Normalized(){
        List<String>normalized$ = new ArrayList<>();
        if( javadoc.contentsPattern.isMatchAny() && javadoc.contentsPattern.list$().contains("javadoc")){
            //Javadoc is OPTIONAL (it's only 
        } else{
            //all$.addAll( javadoc.list$() );
            normalized$.addAll( javadoc.list$Normalized() );
        }        
        normalized$.addAll( annos.list$Normalized() );
        normalized$.addAll( typeParameters.list$Normalized() );
        normalized$.addAll( type.list$Normalized() );        
        normalized$.addAll( name.pattern.list$Normalized() );
        normalized$.addAll( parameters.list$Normalized() );
        normalized$.addAll( thrown.list$Normalized() );
        normalized$.addAll( body.list$Normalized() );
        return normalized$.stream().distinct().collect(Collectors.toList());        
    }

    @Override
    public List<String> list$(){
        List<String>all$ = new ArrayList<>();
        if( javadoc.contentsPattern.isMatchAny() && javadoc.contentsPattern.list$().contains("javadoc")){
            //Javadoc is OPTIONAL (it's only 
        } else{
            all$.addAll( javadoc.list$() );
        }
        all$.addAll( annos.list$() );
        all$.addAll( typeParameters.list$() );
        all$.addAll( type.list$() );        
        all$.addAll( name.pattern.list$() );
        all$.addAll( parameters.list$() );
        all$.addAll( thrown.list$() );
        all$.addAll( body.list$() );           
        return all$;
    }
    
    public $method $parameters(){
        this.parameters = $parameters.of();
        return this;
    }
    
    public $method $parameters( $parameters $ps ){
        this.parameters = $ps;
        return this;
    }
    
    public $method $parameters( String...params ){
        this.parameters = $parameters.of(params);
        return this;
    }
    
    public $method $parameters( Predicate<_parameters> constraint){
        this.parameters.addConstraint( constraint);
        return this;
    }
    
    public $method $throws(){
        this.thrown = $throws.any();
        return this;
    }
    
    public $method $throws($throws $ts){
        this.thrown = $ts;
        return this;
    }
    
    public $method $throws(Class<? extends Throwable>...thro ){
        this.thrown = $throws.of(thro);
        return this;
    }
    
    public $method $throws( Predicate<_throws> constraint ){
        this.thrown.addConstraint(constraint);
        return this;
    }
    
    public $method $throws(String...thro ){
        this.thrown = $throws.of(thro);
        return this;
    }
     
    public $method $annos(){
        this.annos = $annos.of();
        return this;
    }
    
    public $method $annos( Predicate<_annos> as ){
        this.annos.addConstraint(as);
        return this;
    }
    
    public $method $annos(String...annoPatterns ){
        this.annos.add(annoPatterns);
        return this;
    }
    
    public $method $annos($annos $as ){
        this.annos = $as;
        return this;
    }
    
    public $method $anno( Class clazz){
        this.annos.$annosList.add($anno.of(clazz) );
        return this;
    }
    
    public $method $anno( _anno _an){
        this.annos.$annosList.add($anno.of(_an) );
        return this;
    }
    
    public $method $name(){
        this.name = $id.of();
        return this;
    }
    
    public $method $name( Predicate<String> str){
        this.name.addConstraint(str);
        return this;
    }
    
    public $method $name($id id){
        this.name = id;
        return this;
    }
    
    public $method $name(String name){
        this.name = $id.of(name);
        return this;
    }
    
    public $method $type(){
        this.type = $typeRef.of();
        return this;
    }
    
    public $method $type(Predicate<_typeRef> _tr){
        this.type.addConstraint(_tr);
        return this;
    }
    
    public $method $type(Class typeClazz){
        this.type = $typeRef.of(typeClazz);
        return this;
    }
    
    public $method $type( String typeRef){
        this.type = $typeRef.of(typeRef);
        return this;
    }
    
    public $method $type( $typeRef $tr){
        this.type = $tr;
        return this;
    }
    
    public $method $typeParameters(){
        this.typeParameters = $typeParameters.any(); //pattern("$typeParameters$");
        return this;
    }
    
    public $method $typeParameters($typeParameter $tp){
        this.typeParameters.typeParams.add($tp);
        return this;
    }
    
    public $method $typeParameters(Predicate<_typeParameters> constraint){
        this.typeParameters.addConstraint(constraint);
        return this;
    }
    
    public $method $typeParameters(String... tps){
        this.typeParameters = $typeParameters.of(tps);
        return this;
    }

    public $method $modifiers(){
        this.modifiers = $modifiers.of();
        return this;
    }    
    
    public $method $modifiers(_modifiers _ms){
        this.modifiers = $modifiers.of(_ms);
        return this;
    }    
    
    public $method $modifiers(Predicate<_modifiers> constraint){
        this.modifiers.addConstraint(constraint);
        return this;
    }
    
    public $method $modifiers( $modifiers $m){
        this.modifiers = $m;
        return this;
    }
    
    public $method $javadoc(){
        this.javadoc = $comment.javadocComment("$javadoc$");
        return this;
    }
    
    public $method $javadoc( String... form ){
        this.javadoc.contentsPattern = Stencil.of((Object[])form);
        return this;
    }
    
    public $method $body (){
        this.body = $body.any();
        return this;
    }
    
    public $method $body ($body $bd ){
        this.body = $bd;
        return this;
    }
    
    public $method $body( Predicate<_body> constraint){
        this.body.addConstraint(constraint);
        return this;
    }
    
    public $method $body( Object anonymousClassWithMethodContainingBody ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject(ste);
        Optional<BodyDeclaration<?>> on = oce.getAnonymousClassBody().get().stream().filter(m -> 
            m instanceof MethodDeclaration 
            && !((MethodDeclaration)m)
                .getAnnotationByClass(_remove.class).isPresent() )
                .findFirst();
        if(!on.isPresent()){
            throw new DraftException("Could not locate the method containing the body in "+ oce);
        }
        MethodDeclaration md = (MethodDeclaration)on.get();
        md.getParentNode().get().remove(md); //decouple it from the "old" 
        _body _bd = _body.of( md );
        return $body( _bd );
    }
    
    public $method $body(_body _bd ){
       this.body.$bodyStmts( _bd ); 
        return this;
    }
    
    public $method notImplemented(){
        this.body = $body.of(";");
        return this;
    }
    
    public $method emptyBody(){
        this.body = $body.of("{}");
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
        nom.remove( "body");
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
        
        base.putAll(keyValues);
        
        StringBuilder sb = new StringBuilder();   
        JavadocComment jdc = javadoc.construct(translator, base );
        if( jdc != null ){
            sb.append(jdc);        
            sb.append(System.lineSeparator());
        }        
        sb.append( annos.compose(translator, base));
        sb.append(System.lineSeparator());
        sb.append( modifiers.compose(translator, base));
        sb.append(" ");
        sb.append( typeParameters.construct(translator, base));
        sb.append(" ");
        sb.append( type.construct(translator, base));
        sb.append(" ");
        sb.append( name.compose(translator, base));
        sb.append(" ");
        sb.append( parameters.construct(translator, base));
        sb.append(" ");
        sb.append( thrown.construct(translator, base));
        sb.append(System.lineSeparator());
        
        String bd = body.construct(translator, base).toString();
        if( bd.length() ==0 ){
            //it's an "anyBody", so I default it to an empty implementation
            sb.append("{}");
        } else{
            sb.append( bd );
        }
        return _method.of(sb.toString());        
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
     * @param method
     * @return 
     */ 
    public Select select( String...method){
        return select(_method.of(method));
    }
    
    /**
     * 
     * @param _m
     * @return 
     */
    public Select select( _method _m){
        if( !this.constraint.test(_m)){
            return null;
        }
        if( modifiers.select(_m) == null){
            return null;
        }
        Tokens all = new Tokens();
        if( _m.hasJavadoc() ){
            all = javadoc.decomposeTo(_m.getJavadoc().ast(), all);
        } else{
            if(!javadoc.isMatchAny() ){
                return null;
            }
        }
        all = annos.decomposeTo(_m.getAnnos(), all);
        all = typeParameters.decomposeTo(_m.getTypeParameters(), all);
        all = type.decomposeTo(_m.getType(), all);
        all = name.decomposeTo(_m.getName(), all);
        all = parameters.decomposeTo(_m.getParameters(), all);
        all = thrown.decomposeTo(_m.getThrows(), all);
        all = body.decomposeTo(_m.getBody(), all);
        if( all != null ){
            return new Select( _m, $args.of(all));
        }
        return null;        
    }

    /**
     * 
     * @param astMethod
     * @return 
     */
    public Select select( MethodDeclaration astMethod){
        return select(_method.of(astMethod ));
    }
    
    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $method hardcode$( Tokens kvs ) {
        return hardcode$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $method hardcode$( Object... keyValues ) {
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
    public $method hardcode$( Translator translator, Object... keyValues ) {
        return hardcode$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public $method hardcode$( Translator translator, Tokens kvs ) {
        javadoc = javadoc.hardcode$(translator, kvs);
        annos = annos.hardcode$(translator, kvs);
        typeParameters = typeParameters.hardcode$(translator, kvs);
        type = type.hardcode$(translator, kvs);
        name.pattern = name.pattern.hardcode$(translator, kvs);
        parameters = parameters.hardcode$(translator, kvs);
        thrown = thrown.hardcode$(translator, kvs);
        body = body.hardcode$(translator, kvs);
        
        return this;
    }

    /** Post - parameterize, create a parameter from the target string named $Name#$*/
    @Override
    public $method $(String target, String $Name) {
        javadoc = javadoc.$(target, $Name);
        annos = annos.$(target, $Name);
        typeParameters = typeParameters.$(target, $Name);
        type = type.$(target, $Name);
        name.pattern = name.pattern.$(target, $Name);
        parameters = parameters.$(target, $Name);
        thrown = thrown.$(target, $Name);
        body = body.$(target, $Name);  
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
     * @param method
     * @return 
     */
    public boolean matches( String method ){
        return select(_method.of(method)) != null;
    }
    
    /**
     * 
     * @param method
     * @return 
     */
    public boolean matches( String... method ){
        return select(_method.of(method)) != null;
    }
    
    /**
     * 
     * @param _m
     * @return 
     */
    public boolean matches( _method _m ){
        return select( _m ) != null;
    }

    /**
     * 
     * @param astMethod
     * @return 
     */
    public boolean matches( MethodDeclaration astMethod ){
        return select(astMethod ) != null;
    }

    /**
     * Returns the first _method that matches the pattern and constraint
     * @param astNode the node to look through
     * @param _methodMatchFn
     * @return  the first _method that matches (or null if none found)
     */
    @Override
    public _method firstIn( Node astNode, Predicate<_method> _methodMatchFn){
        Optional<MethodDeclaration> f = astNode.findFirst(MethodDeclaration.class, s -> {
            Select sel = select(s);
            return sel != null && _methodMatchFn.test(sel._m);
        });         
        if( f.isPresent()){
            return _method.of(f.get());
        }
        return null;
    }

    /**
     * Returns the first _method that matches the pattern and constraint
     * @param clazz the runtime class (WITH source available in classpath)
     * @return  the first _method that matches (or null if none found)
     */
    @Override
    public Select selectFirstIn( Class clazz){
        return selectFirstIn( _java.type(clazz));
    }
    
    /**
     * Returns the first _method that matches the pattern and constraint
     * @param clazz the runtime class (WITH source available in classpath)
     * @param selectConstraint
     * @return  the first _method that matches (or null if none found)
     */
    public Select selectFirstIn( Class clazz, Predicate<Select> selectConstraint){
        return selectFirstIn( _java.type(clazz), selectConstraint);
    }
    
    /**
     * Returns the first _method that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _method that matches (or null if none found)
     */
    @Override
    public Select selectFirstIn( Node astNode ){
        Optional<MethodDeclaration> f = astNode.findFirst(MethodDeclaration.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }
    
    /**
     * Returns the first _method that matches the pattern and constraint
     * @param _n the _java node
     * @param selectConstraint
     * @return  the first _method that matches (or null if none found)
     */
    public Select selectFirstIn( _java _n, Predicate<Select> selectConstraint){
         if( _n instanceof _code ){
            if( ((_code) _n).isTopLevel()){
                return selectFirstIn( ((_code) _n).astCompilationUnit(), selectConstraint);
            } else{
                return selectFirstIn(((_type) _n).ast(), selectConstraint);
            }
        }
        return selectFirstIn(((_node) _n).ast(), selectConstraint);
        /*
        Optional<MethodDeclaration> f = _n.ast().findFirst(MethodDeclaration.class, s -> {
            Select sel = this.select(s);
            return sel != null && selectConstraint.test(sel);
            });               
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
        */
    }

    /**
     * Returns the first _method that matches the pattern and constraint
     * @param astNode the node to look through
     * @param selectConstraint
     * @return  the first _method that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode, Predicate<Select> selectConstraint){
        Optional<MethodDeclaration> f = astNode.findFirst(MethodDeclaration.class, s -> {
            Select sel = this.select(s);
            return sel != null && selectConstraint.test(sel);
            });         
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }
    
    @Override
    public List<Select> listSelectedIn(Node astNode){
        List<Select>sts = new ArrayList<>();
        astNode.walk(MethodDeclaration.class, m-> {
            Select sel = select( m );
            if( sel != null ){
                sts.add(sel);
            }
        });
        return sts;
    }

    /*
    @Override
    public List<Select> listSelectedIn(_node _n){
        List<Select>sts = new ArrayList<>();
        Walk.in(_n, MethodDeclaration.class, m -> {
            Select sel = select( m );
            if( sel != null ){
                sts.add(sel);
            }
        });
        return sts;
    }
    */
    
    /**
     * 
     * @param clazz
     * @return 
     
    @Override
    public List<Select> listSelectedIn(Class clazz){
        return listSelectedIn( _java.type(clazz));        
    }
    */ 
    
    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(Class clazz, Predicate<Select> selectConstraint){
        return listSelectedIn( _java.type(clazz), selectConstraint);        
    }
    
    /**
     * 
     * @param astNode
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(Node astNode, Predicate<Select> selectConstraint){
        List<Select>sts = new ArrayList<>();
        astNode.walk(MethodDeclaration.class, m-> {
            Select sel = select( m );
            if( sel != null && selectConstraint.test(sel)){
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
    public List<Select> listSelectedIn(_java _n, Predicate<Select> selectConstraint){
        List<Select>sts = new ArrayList<>();
        Walk.in(_n, MethodDeclaration.class, m -> {
            Select sel = select( m );
            if( sel != null && selectConstraint.test(sel)){
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
     * @param clazz
     * @param selectedActionFn
     * @return 
     */
    public _type forSelectedIn(Class clazz, Consumer<Select> selectedActionFn ){
        return forSelectedIn( _java.type(clazz), selectedActionFn);
    }    
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param selectedActionFn
     * @return 
     */
    public <N extends _java> N forSelectedIn(N _n, Consumer<Select> selectedActionFn ){
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
     * @param astNode
     * @param selectConstraint
     * @param selectedActionFn
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Predicate<Select> selectConstraint, Consumer<Select> selectedActionFn ){
        astNode.walk( MethodDeclaration.class, m-> {
            Select s = select( m );
            if( s != null && selectConstraint.test(s)){
                selectedActionFn.accept( s );
            }
        });
        return astNode;
    }
    
    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @param selectedActionFn
     * @return 
     */
    public _type forSelectedIn(Class clazz, Predicate<Select> selectConstraint, Consumer<Select> selectedActionFn ){
        return forSelectedIn(_java.type(clazz), selectConstraint, selectedActionFn);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConstraint
     * @param selectedActionFn
     * @return 
     */
    public <N extends _java> N forSelectedIn(N _n, Predicate<Select> selectConstraint, Consumer<Select> selectedActionFn ){
        Walk.in(_n, _method.class, m ->{
            Select s = select( m );
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
    public _type replaceIn(Class clazz,  $method $replace ){
        return forSelectedIn(_java.type(clazz), s -> {
            _method repl = $replace.construct(Translator.DEFAULT_TRANSLATOR, s.args);
            s._m.ast().replace(repl.ast());
        });
    }

    /**
     * 
     * @param clazz
     * @param replacementProto
     * @return 
     */
    public _type replaceIn(Class clazz,  String... replacementProto ){
        return replaceIn(_java.type(clazz), $method.of(replacementProto));        
    }
    
    /**
     * 
     * @param clazz
     * @param method
     * @return 
     */
    public _type replaceIn(Class clazz,  _method method ){
        return replaceIn(_java.type(clazz), $method.of(method));        
    }
    
    /**
     * 
     * @param clazz
     * @param astMethod
     * @return 
     */
    public _type replaceIn(Class clazz, MethodDeclaration astMethod ){
        return replaceIn(_java.type(clazz), $method.of(astMethod));        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param $replace
     * @return 
     */
    public <N extends _java> N replaceIn( N _n, $method $replace ){
        return forSelectedIn(_n, s -> {
            _method repl = $replace.construct(Translator.DEFAULT_TRANSLATOR, s.args.asTokens());
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
    public <N extends _java> N replaceIn( N _n, String... replacementProto ){
        return replaceIn(_n, $method.of(replacementProto));        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param method
     * @return 
     */
    public <N extends _java> N replaceIn( N _n, _method method ){
        return replaceIn(_n, $method.of(method));        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param astMethod
     * @return 
     */
    public <N extends _java> N replaceIn( N _n, MethodDeclaration astMethod ){
        return replaceIn(_n, $method.of(astMethod));        
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param _methodMatchFn
     * @param _methodActionFn
     * @return 
     */
    @Override
    public <N extends Node> N forEachIn(N astNode, Predicate<_method>_methodMatchFn, Consumer<_method> _methodActionFn){
        astNode.walk(MethodDeclaration.class, e-> {
            Select sel = select(e);
            if( sel != null && _methodMatchFn.test(sel._m)){
                _methodActionFn.accept( sel._m );
            }
        });
        return astNode;
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
        public $args args(){
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
        
        public boolean isType( Class type ){
            return _m.isType(type);
        }
        
        public boolean isType( String type ){
            return _m.isType(type);
        }
        
        public boolean isType( Type type ){
            return _m.isType(type);
        }
        
        public boolean isType( _typeRef _tr ){
            return _m.isType(_tr);
        }
        
        public boolean isVarArg(){
            return _m.isVarArg();
        }
        
        public boolean isAbstract(){
            return _m.isAbstract();
        }
        
        public boolean hasBody(){
            return _m.hasBody();
        }
        
        public boolean isVoid(){            
            return _m.isVoid();
        }
        
        public boolean hasThrows(){
            return _m.hasThrows();
        }
        
        public boolean hasThrow(Class<? extends Throwable> throwsClass){            
            return _m.hasThrow(throwsClass);
        }
        
        public boolean hasParameters(){            
            return _m.hasParameters();
        }
        
        public boolean is(String...methodDeclaration){
            return _m.is(methodDeclaration);
        }
        
        public boolean hasTypeParameters(){            
            return _m.hasTypeParameters();
        }
    }
}
