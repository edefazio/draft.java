package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.nodeTypes.NodeWithParameters;
import draft.Template;
import draft.Text;
import draft.Tokens;
import draft.Translator;
import draft.java.Walk;
import draft.java._model;
import draft.java._model._node;
import draft.java._parameter._parameters;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Eric
 */
public class $parameters implements Template<_parameters>, $proto<_parameters> {

    List<$parameter> $params = new ArrayList<>();
    
    Predicate<_parameters> constraint = t-> true;
    
    public static $parameters any(){
        return new $parameters( _parameters.of() );
    }
        
    public static $parameters of( _parameters _ps ){
        return new $parameters(_ps);
    }
    
    public static $parameters of( String...pattern){
        return new $parameters(_parameters.of(pattern));
    }
    
    /**
     * 
     * @param _ps prototype parameters 
     */
    private $parameters( _parameters _ps ){
        _ps.forEach( p -> $params.add( $parameter.of(p)) );
    }
    
    private $parameters( Predicate<_parameters> constraint){
        this.constraint = constraint;
    }
    
    public $parameters constraint(Predicate<_parameters> constraint){
        this.constraint = constraint;
        return this;
    }
    
    @Override
    public _parameters construct(Translator translator, Map<String, Object> keyValues) {
        System.out.println("IN CONSTRUCT PARAMETERS WITH "+$params.size());
        
        _parameters _ps = _parameters.of();
        
        for(int i=0;i<$params.size(); i++){            
            _ps.add($params.get(i).construct(translator, keyValues));
        }
        return _ps;
        /*
        if( this.isMatchAny() ){
            System.out.println( "Its Match any");
            if( keyValues.containsKey("parameters")){
                return _parameters.of((String)(keyValues.get("parameters")).toString());
            } else{
                return _parameters.of();
            }
        }
        */
    }

    @Override
    public $parameters $(String target, String $Name) {
        $params.forEach(p-> p.$(target, $Name));
        return this;
    }

    public boolean matches( String parameters ){
        return select(parameters) != null;
    }
    
    public boolean matches( _parameters _ps ){
        return select(_ps) != null;
    }
    
    public Select select( String... parameters ){
        return select(_parameters.of( parameters));
    }
    
    public Select select( _parameters _ps ){
        if( this.constraint.test(_ps)){
            
            if( this.$params.isEmpty() ){
                return new Select(_ps, Tokens.of("parameters", _ps.toString() ));
            }
            /*
            if( this.$params.size() == 1 && 
                this.$params.get(0).isMatchAny() ){
                return new Select(_ps, Tokens.of("parameters", _ps.toString() ));
            } 
            */
            if( _ps.size() != this.$params.size() ){
                return null;
            }
            Tokens ts = new Tokens();
            for(int i=0;i<_ps.size(); i++ ){
                $parameter.Select sel = this.$params.get(i).select( _ps.get(i) );
                if( sel != null && ts.isConsistent(sel.args.asTokens()) ){
                    ts.putAll( sel.args.asTokens() );
                } else{
                    return null;
                }
            }
            return new Select(_ps, ts);
        }        
        return null;
    } 
    
     public Tokens decomposeTo(_parameters p, Tokens all) {
        if (all == null) { /* Skip decompose if the tokens already null*/
            return null;
        }
            
        Select sel = select(p);
        if( sel != null ){
            if( all.isConsistent(sel.args.asTokens())){
                System.out.println("adding "+sel.args.asTokens() );
                all.putAll(sel.args.asTokens());
                return all;
            }
        }
        return null;        
    }
    
     
    public $parameters hardcode$(Object...keyValues){
        this.$params.forEach(p -> p.hardcode$(keyValues));
        return this;
    } 
    
    public boolean isMatchAny(){
        if( this.$params.isEmpty() || 
            this.$params.size() == 1 && 
            this.$params.get(0).isMatchAny() ){
            
            try{
                return constraint.test(null);
            } catch(Exception e){
                return false;
            }    
        }
        return false;
    }
    
    @Override
    public List<String> list$() {
        List<String> $s = new ArrayList<>();
        $params.forEach(p-> $s.addAll( p.list$() ) );
        return $s;
    }

    @Override
    public List<String> list$Normalized() {
        List<String> $s = new ArrayList<>();
        $params.forEach(p-> $s.addAll( p.list$Normalized() ) );
        return $s.stream().distinct().collect(Collectors.toList());
    }

    @Override
    public List<_parameters> listIn( _node _n) {
        List<_parameters> found = new ArrayList<>();
        forEachIn(_n, n-> found.add(n) );
        return found;
    }

    @Override
    public List<_parameters> listIn(Node astRootNode) {
        List<_parameters> found = new ArrayList<>();
        forEachIn(astRootNode, n-> found.add(n) );
        return found;
    }

    @Override
    public List<Select> listSelectedIn(Node astRootNode) {
        List<Select> found = new ArrayList<>();
        forSelectedIn( astRootNode, s-> found.add(s));
        return found;
    }

    @Override
    public List<Select> listSelectedIn(_model._node _n) {
        List<Select> found = new ArrayList<>();
        forSelectedIn( _n, s-> found.add(s));
        return found;
    }

    @Override
    public <N extends Node> N removeIn(N astRootNode) {
        return forEachIn( astRootNode, n-> n.forEach( p-> p.ast().remove() ) );
    }

    @Override
    public <N extends _node> N removeIn(N _n) {
        return forEachIn( _n, n-> n.forEach( p-> p.ast().remove() ) );
    }

    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param parametersPattern
     * @return 
     */
    public <N extends Node> N replaceIn(N astRootNode, String...parametersPattern ) {        
        return replaceIn(astRootNode, $parameters.of(parametersPattern));        
    }

    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param $replacementProto
     * @return 
     */
    public <N extends Node> N replaceIn(N astRootNode, $parameters $replacementProto) {
        
        return forSelectedIn( astRootNode, s->{            
            _parameters _replaceParams = $replacementProto.construct(s.args);
            s._params.astHolder().setParameters(_replaceParams.ast());             
            } );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param parametersPattern
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, String...parametersPattern ) {     
        return replaceIn(_n, $parameters.of(parametersPattern));    
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param $replacementProto
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, $parameters $replacementProto) {
        return forSelectedIn( _n, s->{            
            _parameters _replaceParams = $replacementProto.construct(s.args);
            s._params.astHolder().setParameters(_replaceParams.ast());             
            } );
    }
    
    @Override
    public <N extends Node> N forEachIn(N astRootNode, Consumer<_parameters> _parametersActionFn) {        
        astRootNode.walk( Node.class, n-> {            
            if( n instanceof NodeWithParameters){
                Select sel = select(_parameters.of( (NodeWithParameters)n ) );
                if( sel != null ){
                    _parametersActionFn.accept(sel._params);
                }
            }
        });        
        return astRootNode;        
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_parameters> _parametersActionFn) {
        Walk.in(_n, _parameters.class, n-> {            
            Select sel = select( n );
            if( sel != null ){
                _parametersActionFn.accept(sel._params);            
            }
        });        
        return _n;        
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param _parametersActionFn
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astRootNode, Consumer<Select> _parametersActionFn) {
        
        astRootNode.walk( Node.class, n-> {            
            if( n instanceof NodeWithParameters){
                Select sel = select(_parameters.of( (NodeWithParameters)n ) );
                if( sel != null ){
                    _parametersActionFn.accept(sel);
                }
            }
        });        
        return astRootNode;        
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param _parametersActionFn
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Consumer<Select> _parametersActionFn) {
        Walk.in(_n, _parameters.class, n-> {            
            Select sel = select( n );
            if( sel != null ){
                _parametersActionFn.accept(sel);            
            }
        });        
        return _n;        
    }
    
    @Override
    public String toString(){
        if( isMatchAny() ){
            return "( $any$ )";
        }
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<this.$params.size(); i++){
            if( i > 0 ){
                sb.append(",");
            }
            sb.append( $params.get(i) );
        }
        return sb.toString();    
    }
 
    /**
     * A Matched Selection result returned from matching a prototype $anno
     * inside of some Node or _node
     */
    public static class Select 
        implements $proto.selected, selected_model<_parameters> {
        
        public final _parameters _params;
        public final $args args;

        public Select ( _parameters _p, Tokens tokens){
            this._params = _p;
            args = $args.of(tokens);
        }
        
        public Select ( _parameters _p, $args $a){
            this._params = _p;
            args = $a;
        }
        
        public int size(){            
            return this._params.size();
        }
        
        public boolean isVarArg(){            
            return _params.isVarArg();
        }
        
        
        @Override
        public $args getArgs(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$parameters.Select {"+ System.lineSeparator()+
                Text.indent(_params.toString() )+ System.lineSeparator()+
                Text.indent("$args : " + args) + System.lineSeparator()+
                "}";
        }
        
        @Override
        public _parameters model() {
            return _params;
        }        
    }
}
