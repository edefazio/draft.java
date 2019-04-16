package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.Parameter;
import draft.Stencil;
import draft.Template;
import draft.Text;
import draft.Tokens;
import draft.Translator;
import draft.java._model;
import draft.java._parameter;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * TODO need to check that if the target is an ARRAY
 * @author Eric
 */
public class $parameter implements Template<_parameter>, $proto<_parameter> {

    public Predicate<_parameter> constraint = t->true;
    
    /** 
     * in this context, false will match final or non final, 
     * will compose w/o final 
     */
    public Boolean isFinal = false;
    
    /** in this context, false will match varARg or non varArg, 
     * will compose w/o varArg */
    public Boolean isVarArg = false;
    
    public $component<String> name = new $component("$name$", t->true);
    
    public $typeRef type;
    
    //TODO $annos
    
    public static $parameter of( String parameter ){
        return new $parameter( _parameter.of(parameter), p->true );
    }
    
    public $parameter( _parameter _p, Predicate<_parameter> constraint){
        //TODO get $annos
        
        if( _p.isFinal() ){
            this.isFinal = true;
        }
        if( _p.isVarArg() ){
            this.isVarArg = true;
        }
        this.name.$form = Stencil.of(_p.getName() );
        this.type = $typeRef.of(_p.getType());                
    }
    
    
    public String compose( Translator translator, Map<String, Object> keyValues) {
        StringBuilder sb = new StringBuilder();
        //TODO $annos
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
    public _parameter construct(Translator translator, Map<String, Object> keyValues) {
        return _parameter.of( compose(translator, keyValues));
    }
    
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
    
    public boolean matches( Parameter astP ){
        return select(_parameter.of(astP)) != null;
    }
    
    public boolean matches( _parameter _p ){
        return select(_p) != null;
    }
    
    public boolean matches( String parameter ){
        return select(parameter) != null;
    }
    /*
    public $args deconstruct(Parameter astParameter){
        return deconstruct( _parameter.of(astParameter));
    }
    
    public $args deconstruct(String parameter){
        try{
            return deconstruct(_parameter.of(parameter));
        }catch(Exception e){
            return null;
        }
    }
    
    public $args deconstruct(_parameter _p ){
        if( this.isFinal && !_p.isFinal() 
            || this.isVarArg && !_p.isVarArg() ){            
            return null;
        }
        $args args = this.type.deconstruct(_p.getType() );
        args = this.name.decomposeTo(_p.getName(), args);
        
        return args;        
    } 
    */
    
    public Select select( Parameter astParameter ){
        return select(_parameter.of(astParameter));
    }
    
    public Select select( String parameter){
        try{
            return select( _parameter.of(parameter));
        }catch(Exception e){
            return null;
        }        
    }
    
    public Select select( _parameter _p ){
        if( this.isFinal && !_p.isFinal() 
            || this.isVarArg && !_p.isVarArg() ){            
            return null;
        }
        //$args args = this.type.deconstruct(_p.getType() );
        $typeRef.Select sel = type.select(_p.getType());
        
        if( sel != null ){
            $args as = sel.args;
            as = this.name.decomposeTo(_p.getName(), as );
            if( as != null ){
                return new Select(_p, as);
            }
        }
        return null;        
    }
    
    @Override
    public Template<_parameter> $(String target, String $Name) {
        this.name.$(target, $Name);
        this.type.$(target, $Name);
        return this;
    }

    @Override
    public List<String> list$() {
        List<String> all = new ArrayList<>();
        all.addAll( this.type.list$() );
        all.addAll( this.name.list$() );
        return all;
    }

    @Override
    public List<String> list$Normalized() {
        return list$().stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<_parameter> listIn(_model._node _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<_parameter> listIn(Node astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<? extends selected> selectListIn(Node astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<? extends selected> selectListIn(_model._node _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends Node> N removeIn(N astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node> N removeIn(N _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends Node> N forEachIn(N astRootNode, Consumer<_parameter> _nodeActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node> N forEachIn(N _n, Consumer<_parameter> _nodeActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
