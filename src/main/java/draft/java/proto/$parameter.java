package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import draft.*;
import draft.java.Walk;
import draft.java._model._node;
import draft.java._parameter;
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
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param parameterActionFn
     * @return 
     */
    public static <N extends _node> N forEach(N _n, String pattern, Consumer<_parameter> parameterActionFn ){
        return $parameter.of(pattern).forEachIn(_n, parameterActionFn);        
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param parameterActionFn
     * @return 
     */
    public static <N extends Node> N forEach(N _n, String pattern, Consumer<_parameter> parameterActionFn ){
        return $parameter.of(pattern).forEachIn(_n, parameterActionFn);        
    }    

    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectActionFn
     * @return 
     */
    public static <N extends _node> N forSelected(N _n, String pattern, Consumer<Select> selectActionFn ){
        return $parameter.of(pattern).forSelectedIn(_n, selectActionFn);        
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectActionFn
     * @return 
     */
    public static <N extends Node> N forSelected(N _n, String pattern, Consumer<Select> selectActionFn ){
        return $parameter.of(pattern).forSelectedIn(_n, selectActionFn);        
    } 

    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConstraint
     * @param selectActionFn
     * @return 
     */
    public static <N extends _node> N forSelected(N _n, String pattern, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn ){
        return $parameter.of(pattern).forSelectedIn(_n, selectConstraint, selectActionFn);        
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConstraint
     * @param selectActionFn
     * @return 
     */
    public static <N extends Node> N forSelected(N _n, String pattern, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn ){
        return $parameter.of(pattern).forSelectedIn(_n, selectConstraint, selectActionFn);        
    } 
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static <N extends _node> List<_parameter> list(N _n, String pattern ){
        return $parameter.of(pattern).listIn(_n);        
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static <N extends Node> List<_parameter> list(N _n, String pattern ){
        return $parameter.of(pattern).listIn(_n );        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static <N extends _node> List<_parameter> list(N _n, String pattern, Predicate<_parameter> constraint){
        return $parameter.of(pattern).constraint(constraint).listIn(_n);        
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static <N extends Node> List<_parameter> list(N _n, String pattern, Predicate<_parameter> constraint){
        return $parameter.of(pattern).constraint(constraint).listIn(_n );        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static <N extends _node> List<Select> listSelected(N _n, String pattern ){
        return $parameter.of(pattern).listSelectedIn(_n);        
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static <N extends Node> List<Select> listSelected(N _n, String pattern ){
        return $parameter.of(pattern).listSelectedIn(_n );        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConstraint
     * @return 
     */
    public static <N extends _node> List<Select> listSelected(N _n, String pattern, Predicate<Select> selectConstraint){
        return $parameter.of(pattern).selectListIn( _n, selectConstraint);        
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConstraint
     * @return 
     */
    public static <N extends Node> List<Select> listSelected(N _n, String pattern, Predicate<Select> selectConstraint){
        return $parameter.of(pattern).selectListIn(_n, selectConstraint );        
    }
    
    /** a constraint to be applied for matching*/
    public Predicate<_parameter> constraint = t->true;
    
    /** 
     * in this context, false will match final or non final, 
     * will compose w/o final 
     */
    public Boolean isFinal = false;
    
    /** in this context, false will match varARg or non varArg, 
     * will compose w/o varArg */
    public Boolean isVarArg = false;
    
    /** Name of the parameter */
    public $component<String> name = new $component("$name$", t->true);
    
    /** the underlying type of the parameter */
    public $typeRef type;
    
    /** annos prototype */
    public $annos annos = new $annos();
    
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
        this.name.hardcode$(keyValues);
        this.annos.hardcode$(Translator.DEFAULT_TRANSLATOR, keyValues);
        this.type.hardcode$(keyValues);
        return this;
    }
    
    @Override
    public _parameter construct(Translator translator, Map<String, Object> keyValues) {
        return _parameter.of( compose(translator, keyValues));
    }
    
    /**
     * SET / OVERRIDE the constraint
     * @param constraint the constraint to set
     * @return the modified $parameter
     */
    public $parameter constraint( Predicate<_parameter> constraint ){
        this.constraint = constraint;
        return this;
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

    @Override
    public List<_parameter> listIn(_node _n) {
        return Walk.list(_n, _parameter.class, p-> matches(p) );
    }

    @Override
    public List<_parameter> listIn(Node astRootNode) {
        List<_parameter> found = new ArrayList<>();
        astRootNode.walk(Parameter.class, p-> {
            if( matches(p) ){
                found.add(_parameter.of(p));
            }
        });
        return found;
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
        
    public List<Select> selectListIn(Node astRootNode, Predicate<Select> selectConstraint) {
        List<Select> found = new ArrayList<>();
        astRootNode.walk(Parameter.class, p-> {
            Select sel = select(p);
            if( sel != null && selectConstraint.test(sel)){
                found.add(sel);
            }
        });
        return found;
    }

    public List<Select> selectListIn(_node _n, Predicate<Select> selectConstraint) {
        List<Select> found = new ArrayList<>();
        Walk.in(_n, _parameter.class, p-> {
            Select sel = select(p);
            if( sel != null && selectConstraint.test(sel)){
                found.add(sel);
            }
        });
        return found;
    }
    
    @Override
    public <N extends Node> N removeIn(N astRootNode) {
        return forEachIn( astRootNode, p->{
            p.ast().remove();
        });
    }

    @Override
    public <N extends _node> N removeIn(N _n) {
        return forEachIn( _n, p->{
            p.ast().remove();
        });
    }

    @Override
    public <N extends Node> N forEachIn(N astRootNode, Consumer<_parameter> _parameterActionFn) {        
        astRootNode.walk(Parameter.class, p-> {
            if( matches(p) ){
                _parameterActionFn.accept(_parameter.of(p));
            }
        });
        return astRootNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_parameter> _parameterActionFn) {
        return Walk.in(_n, _parameter.class, p->{
            if( matches(p) ){
                _parameterActionFn.accept(p);
            }
        });
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
    public <N extends _node> N forSelectedIn(N _n, Consumer<Select> selectActionFn) {    
        return Walk.in(_n, _parameter.class, p->{
            Select sel = select(p);
            if( sel != null ){
                selectActionFn.accept(sel);
            }
        });
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
    public <N extends _node> N forSelectedIn(N _n, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn) {    
        return Walk.in(_n, _parameter.class, p->{
            Select sel = select(p);
            if( sel != null && selectConstraint.test(sel)){
                selectActionFn.accept(sel);
            }
        });
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
        
        @Override
        public $args getArgs(){
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
