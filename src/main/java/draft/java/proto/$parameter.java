package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import draft.*;
import draft.java.Walk;
import draft.java._anno._annos;
import draft.java._java._node;
import draft.java.*;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author Eric
 */
public class $parameter implements Template<_parameter>, $proto<_parameter> {

    /**
     * the component parts of a $parameter
     */
    public interface $part{}
    
    
    /** a constraint to be applied for matching*/
    public Predicate<_parameter> constraint = t->true;
    
    /** 
     * in this context, false will match final or non final, 
     * will compose w/o final 
     */
    public Boolean isFinal = false;
    
    /** 
     * in this context, false will match varARg or non varArg, 
     * will compose w/o varArg 
     */
    public Boolean isVarArg = false;
    
    /** Name of the parameter */
    //public $component<String> name = new $component("$name$", t->true);
    public $id name = $id.of();
    
    /** the underlying type of the parameter */
    public $typeRef type = $typeRef.of();
    
    /** annos prototype */
    public $annos annos = new $annos();
    
    /**
     * <PRE>
     * This signifies that :
     * when matching the parameter MUST be final
     * when composing the parameter will be made final
     * </PRE>
     */
    public static class FINAL_PARAMETER implements $part { }
    
    /**
     * <PRE>
     * This signifies that :
     * when matching the parameter MUST be a Var Arg
     * when composing the parameter will be made a Var Arg
     * </PRE>
     */
    public static class VAR_ARG_PARAMETER implements $part { }
    
    /**
     * Build a prototype of a parameter accepting these parts
     * @param parts
     * @return 
     */
    public static $parameter of( $part... parts ){
        return new $parameter(parts);
    }

    /**
     * Build a prototype of a parameter accepting these parts
     * @param part the single part of the parameter
     * @return 
     */
    public static $parameter of( $part part ){
        $part[] parts = new $part[]{part};
        return new $parameter(parts);
    }
    
    /**
     * 
     * @param parts 
     */
    public $parameter( $part...parts){
        for(int i=0;i<parts.length;i++){
            if( parts[i] instanceof $id){
                name = ($id)parts[i];
            }
            else if( parts[i] instanceof $typeRef){
                type = ($typeRef)parts[i];
            }
            else if( parts[i] instanceof $annos ){
                annos = ($annos)parts[i];
            }
            else if( parts[i] instanceof $anno){
                annos.$annosList.add( ($anno)parts[i]);
            } 
            else if(parts[i] instanceof FINAL_PARAMETER){
                this.isFinal = true;
            }
            else if(parts[i] instanceof VAR_ARG_PARAMETER){
                this.isVarArg = true;
            }
            else{
                throw new DraftException("unable to process part ["+i+"] "+ parts[i]+" not of expected type");
            }
        }
    }
    
    /**
     * 
     * @return 
     */
    public static $parameter any(){
        return of();
    }
    
    /**
     * 
     * @return 
     */
    public static $parameter of(){
        return new $parameter( $typeRef.of( ), $id.of() );
    }
    
    
    /**
     * 
     * @param astParam
     * @return 
     */
    public static $parameter of( Parameter astParam ){
        return new $parameter( _parameter.of(astParam), p->true);
    }
    
    /**
     * Build and return a parameter
     * @param _p the prototype parameter
     * @return the $parameter
     */
    public static $parameter of( _parameter _p ){
        return new $parameter( _p, p->true );
    }
    
    /**
     * Build and return a parameter
     * @param parameter
     * @return 
     */
    public static $parameter of( String parameter ){
        return new $parameter( _parameter.of(parameter), p->true );
    }
    
    /**
     * 
     * @param type
     * @param name 
     */
    public $parameter( $typeRef type, $id name ){
        this( $annos.of(), type, name );
    }
    
    /**
     * 
     * @param annos
     * @param type
     * @param name 
     */
    public $parameter( $annos annos, $typeRef type, $id name ){
        this.annos = annos;
        this.type = type;
        this.name = name;        
    }
    
    /**
     * 
     * @param _p
     * @param constraint 
     */
    public $parameter( _parameter _p, Predicate<_parameter> constraint){
        if( _p.isFinal() ){
            this.isFinal = true;
        }
        if( _p.isVarArg() ){
            this.isVarArg = true;
        }
        this.name.pattern = Stencil.of(_p.getName() );
        this.type = $typeRef.of(_p.getType());     
        this.annos = $annos.of( _p.getAnnos() );        
    }
    
    public $parameter $anno(){
        this.annos = $annos.any();
        return this;
    }
    
    public $parameter $anno( $anno $ann ){
        this.annos.$annosList.add($ann);
        return this;
    }
    
    public $parameter $annos( $annos $anns){
        this.annos = $anns;
        return this;
    }
    
    public $parameter $annos( Predicate<_annos> annosPredicate){
        this.annos.addConstraint(annosPredicate);
        return this;
    }
    
    public $parameter $name(){
        this.name = $id.any();
        return this;
    }
    
    public $parameter $name( $id name ){
        this.name = name;
        return this;
    }
    
    public $parameter $name( String name ){
        this.name.pattern = Stencil.of(name);
        return this;
    }
    
    public $parameter $name( String name, Predicate<String> constraint){
        this.name= $id.of(name).addConstraint(constraint);
        return this;
    }
    
    public $parameter $name( Predicate<String> constraint){
        this.name.addConstraint(constraint);
        return this;
    }
    
    public $parameter $type(){
        this.type = $typeRef.any();
        return this;
    }
    
    public $parameter $type( String type ){
        this.type.typePattern = Stencil.of(type);
        return this;
    }
    
    public $parameter $type( String type, Predicate<_typeRef> constraint){
        this.type = $typeRef.of( type, constraint);
        return this;
    }
    
    public $parameter $type( Predicate<_typeRef> constraint){
        this.type.addConstraint(constraint);
        return this;
    }    
    
    /**
     * Will this $parameter match ANY
     * @return 
     */
    public boolean isMatchAny(){
        return this.annos.isMatchAny() && isFinal != true && isVarArg != true && this.name.isMatchAny() && this.type.isMatchAny();
    }
    
    /**
     * Compose the parameter into a String 
     * @param translator
     * @param keyValues
     * @return 
     */
    public String compose( Translator translator, Map<String, Object> keyValues) {
        StringBuilder sb = new StringBuilder();
        //here use a single " " as a separator between annos and after the last anno
        sb.append( this.annos.compose(translator, keyValues, " ") ); 
        if( isFinal ){
            sb.append("final ");
        }
        sb.append( type.construct(translator, keyValues).toString() );
        if( isVarArg){
            sb.append("...");
        }
        sb.append(" ");
        sb.append(name.compose(translator, keyValues));
        return sb.toString();
    }
    
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append( this.annos.toString() );
        if( isFinal ){
            sb.append("final ");
        }
        sb.append( type );
        if( isVarArg ){
            sb.append("...");
        }
        sb.append(" ");
        sb.append(name);
        return sb.toString();        
    }
    
    public $parameter hardcode$(Object...keyValues){
        this.name.hardcode$(Translator.DEFAULT_TRANSLATOR, keyValues);
        this.annos.hardcode$(Translator.DEFAULT_TRANSLATOR, keyValues);
        this.type.hardcode$(Translator.DEFAULT_TRANSLATOR, keyValues);
        return this;
    }
    
    @Override
    public _parameter construct(Translator translator, Map<String, Object> keyValues) {
        return _parameter.of( compose(translator, keyValues));
    }
    
    /**
     * ADDS an additional matching constraint to the prototype
     * @param constraint a constraint to be added
     * @return the modified prototype
     */
    public $parameter addConstraint( Predicate<_parameter>constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    /**
     * 
     * @param astParam
     * @return 
     */
    public boolean matches( Parameter astParam ){
        return select(_parameter.of(astParam)) != null;
    }
    
    /**
     * 
     * @param _p
     * @return 
     */
    public boolean matches( _parameter _p ){
        return select(_p) != null;
    }
    
    /**
     * 
     * @param parameter
     * @return 
     */
    public boolean matches( String parameter ){
        return select(parameter) != null;
    }
    
    /**
     * 
     * @param astParameter
     * @return 
     */
    public Select select( Parameter astParameter ){
        return select(_parameter.of(astParameter));
    }
    
    /**
     * 
     * @param parameter
     * @return 
     */
    public Select select( String parameter){
        try{
            return select( _parameter.of(parameter));
        }catch(Exception e){
            return null;
        }        
    }
    
    /**
     * 
     * @param _p
     * @return 
     */
    public Select select( _parameter _p ){
        
        if( this.isFinal && !_p.isFinal() 
            || this.isVarArg && !_p.isVarArg() ){            
            return null;
        }
        $annos.Select ans = annos.select(_p.ast() );
        if( ans == null ){
            return null;
        }
        Tokens all = ans.args.asTokens();
        
        $typeRef.Select sel = type.select(_p.getType());
        
        if( sel != null ){            
            if( !all.isConsistent( sel.args.asTokens() ) ){
                return null;
            }
            all.putAll(sel.args.asTokens() );
            
            all = this.name.decomposeTo(_p.getName(), all);
            
            if( all != null ){
                return new Select(_p, all);
            }
        }
        return null;        
    }
    
    @Override
    public $parameter $(String target, String $Name) {
        this.annos.$(target, $Name);
        this.name.$(target, $Name);
        this.type.$(target, $Name);
        return this;
    }

    @Override
    public List<String> list$() {
        List<String> all = new ArrayList<>();
        all.addAll( this.annos.list$());
        all.addAll( this.type.list$() );
        all.addAll( this.name.list$() );
        return all;
    }

    @Override
    public List<String> list$Normalized() {
        List<String> all = new ArrayList<>();
        all.addAll( this.annos.list$Normalized());
        all.addAll( this.type.list$Normalized() );
        all.addAll( this.name.list$Normalized() );
        return all.stream().distinct().collect(Collectors.toList());
    }
    
    /**
     * 
     * @param clazz
     * @return 
     */
    @Override
    public List<Select>listSelectedIn( Class clazz){
        return (List<Select>)listSelectedIn(_java.type(clazz));
    }
    
    @Override
    public List<Select> listSelectedIn(Node astRootNode) {
        List<Select> found = new ArrayList<>();
        astRootNode.walk(Parameter.class, p-> {
            Select sel = select(p);
            if( sel != null ){
                found.add(sel);
            }
        });
        return found;
    }

    /*
    @Override
    public List<Select> listSelectedIn(_node _n) {
        List<Select> found = new ArrayList<>();
        Walk.in(_n, _parameter.class, p-> {
            Select sel = select(p);
            if( sel != null ){
                found.add(sel);
            }
        });
        return found;
    }
    */
    
    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(Class clazz, Predicate<Select> selectConstraint) {
        return listSelectedIn(_java.type(clazz), selectConstraint);
    }
    
    /**
     * 
     * @param astRootNode
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(Node astRootNode, Predicate<Select> selectConstraint) {
        List<Select> found = new ArrayList<>();
        astRootNode.walk(Parameter.class, p-> {
            Select sel = select(p);
            if( sel != null && selectConstraint.test(sel)){
                found.add(sel);
            }
        });
        return found;
    }

    /**
     * 
     * @param _n
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(_java _n, Predicate<Select> selectConstraint) {
        List<Select> found = new ArrayList<>();
        Walk.in(_n, _parameter.class, p-> {
            Select sel = select(p);
            if( sel != null && selectConstraint.test(sel)){
                found.add(sel);
            }
        });
        return found;
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param _parameterMatchFn
     * @param _parameterActionFn
     * @return 
     */
    @Override
    public <N extends Node> N forEachIn(N astRootNode, Predicate<_parameter> _parameterMatchFn, Consumer<_parameter> _parameterActionFn) {        
        astRootNode.walk(Parameter.class, p-> {
            Select sel = select(p);
            if( sel != null && _parameterMatchFn.test(sel._param)){
                _parameterActionFn.accept(_parameter.of(p));
            }
        });
        return astRootNode;
    }

    /**
     * 
     * @param clazz
     * @param selectActionFn
     * @return 
     */
    public _type forSelectedIn(Class clazz, Consumer<Select> selectActionFn) {
        return forSelectedIn(_java.type(clazz), selectActionFn);
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param selectActionFn
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astRootNode, Consumer<Select> selectActionFn) {        
        astRootNode.walk(Parameter.class, p-> {
            Select sel = select(p);
            if( sel != null ){
                selectActionFn.accept(sel);
            }
        });
        return astRootNode;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param selectActionFn
     * @return 
     */
    public <N extends _java> N forSelectedIn(N _n, Consumer<Select> selectActionFn) {    
        return Walk.in(_n, _parameter.class, p->{
            Select sel = select(p);
            if( sel != null ){
                selectActionFn.accept(sel);
            }
        });
    }
    
    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @param selectActionFn
     * @return 
     */
    public _type forSelectedIn(Class clazz, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn) {
        return forSelectedIn(_java.type(clazz), selectConstraint, selectActionFn);
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param selectActionFn
     * @param selectConstraint
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astRootNode, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn) {        
        astRootNode.walk(Parameter.class, p-> {
            Select sel = select(p);
            if( sel != null && selectConstraint.test(sel)){
                selectActionFn.accept(sel);
            }
        });
        return astRootNode;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param selectActionFn
     * @param selectConstraint
     * @return 
     */
    public <N extends _java> N forSelectedIn(N _n, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn) {    
        return Walk.in(_n, _parameter.class, p->{
            Select sel = select(p);
            if( sel != null && selectConstraint.test(sel)){
                selectActionFn.accept(sel);
            }
        });
    }
    
    /**
     * 
     * @param clazz
     * @return 
     */
    public _parameter firstIn( Class clazz ){
        return firstIn(_java.type(clazz));
    }
    
    /**
     * Returns the first _field that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _field that matches (or null if none found)
     */
    @Override
    public _parameter firstIn( _java _n ){
        if( _n instanceof _code ){
            if( ((_code) _n).isTopLevel()){
                return firstIn( ((_code) _n).astCompilationUnit() );
            }
            return firstIn( ((_type)_n).ast() );
        }
        return firstIn( ((_node)_n).ast() );        
        /*
        Optional<Parameter> p = _n.ast().findFirst(Parameter.class, s -> this.matches(s) );         
        if( p.isPresent()){
            return _parameter.of(p.get());
        }
        return null;*/
    }
    
    /**
     * Returns the first _field that matches the pattern and constraint
     * @param astNode the AST Node
     * @param _parameterMatchFn
     * @return  the first _field that matches (or null if none found)
     */
    @Override
    public _parameter firstIn( Node astNode, Predicate<_parameter> _parameterMatchFn){
        Optional<Parameter> p = astNode.findFirst(Parameter.class, s ->{
            Select sel = select(s);
            return sel != null && _parameterMatchFn.test( sel._param );
          });         
        if( p.isPresent()){
            return _parameter.of(p.get());
        }
        return null;
    }
    
    /**
     * Returns the first _field that matches the pattern and constraint
     * @param astNode the AST Node
     * @return  the first _field that matches (or null if none found)
     */
    @Override
    public Select selectFirstIn( Node astNode ){
        Optional<Parameter> p = astNode.findFirst(Parameter.class, s -> this.matches(s) );         
        if( p.isPresent()){
            return select( p.get() );
        }
        return null;
    }
    
    /**
     * Returns the first _field that matches the pattern and constraint
     * @param clazz class
     * @param selectConstraint
     * @return  the first _field that matches (or null if none found)
     */
    public Select selectFirstIn( Class clazz, Predicate<Select>selectConstraint ){
        return selectFirstIn(_java.type(clazz), selectConstraint);
    }
    
    /**
     * Returns the first _field that matches the pattern and constraint
     * @param _n the _java node
     * @param selectConstraint
     * @return  the first _field that matches (or null if none found)
     */
    public Select selectFirstIn( _java _n, Predicate<Select>selectConstraint ){

        if( _n instanceof _code ){
            if( ((_code) _n).isTopLevel()){
                return selectFirstIn( ((_code) _n).astCompilationUnit(), selectConstraint );
            }
            return selectFirstIn( ((_type)_n).ast(), selectConstraint);
        }
        return selectFirstIn( ((_node)_n).ast(), selectConstraint);        
        /*
        Optional<Parameter> p = _n.ast().findFirst(Parameter.class, 
            pa -> {
                Select s = this.select(pa); 
                return s != null && selectConstraint.test(s);
            });         
        if( p.isPresent()){
            return select(p.get());
        }
        return null;
        */
    }
    
    /**
     * Returns the first _field that matches the pattern and constraint
     * @param astNode the AST Node
     * @param selectConstraint
     * @return  the first _field that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode, Predicate<Select>selectConstraint ){
        Optional<Parameter> p = astNode.findFirst(Parameter.class, 
            pa -> {
                Select s = this.select(pa); 
                return s != null && selectConstraint.test(s);
            });         
        if( p.isPresent()){
            return select(p.get());
        }
        return null;        
    }
    
    /**
     * A Matched Selection result returned from matching a prototype $anno
     * inside of some Node or _node
     */
    public static class Select 
        implements $proto.selected, selected_model<_parameter>, selectedAstNode<Parameter> {
        
        public final _parameter _param;
        public final $args args;

        public Select ( _parameter _p, Tokens tokens){
            this._param = _p;
            args = $args.of(tokens);
        }
        
        public Select ( _parameter _p, $args $a){
            this._param = _p;
            args = $a;
        }
        
        public Select ( Parameter astParam, $args tokens){
            this( _parameter.of(astParam), tokens);
        }
        
        public boolean hasAnno( Class<? extends Annotation> annotationClass){            
            return _param.hasAnno(annotationClass);
        }
        
        public boolean is(String...expectedParameter ){            
            return _param.is(expectedParameter);            
        }
        
        public boolean isType( Class typeClass ){
            return _param.isType(typeClass);            
        }
        
        public boolean isFinal(){
            return _param.isFinal();
        }
        
        public boolean isVarArg(){
            return _param.isVarArg();
        }
        
        public boolean isNamed(String name){
            return _param.isNamed(name);
        }
        
        /**
         * <PRE>
         * Unknown type is used for Lambda expressions, where you can define it like:
         * (u) -> System.out.println(u);
         * 
         * </PRE>
         * 
         * @return 
         */
        public boolean isUnknownType(){
            return this._param.getType().isUnknownType();
        }
        
        @Override
        public $args args(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$parameter.Select {"+ System.lineSeparator()+
                Text.indent(_param.toString() )+ System.lineSeparator()+
                Text.indent("$args : " + args) + System.lineSeparator()+
                "}";
        }
        
        @Override
        public Parameter ast(){
            return _param.ast();
        } 
        
        @Override
        public _parameter model() {
            return _param;
        }        
    }    
}
