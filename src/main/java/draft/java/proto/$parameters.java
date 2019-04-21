/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.nodeTypes.NodeWithParameters;
import draft.Template;
import draft.Text;
import draft.Tokens;
import draft.Translator;
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
        _parameters _ps = _parameters.of();
        for(int i=0;i<$params.size(); i++){            
            _ps.add($params.get(i).construct(translator, keyValues));
        }
        return _ps;
    }

    @Override
    public $parameters $(String target, String $Name) {
        $params.forEach(p-> p.$(target, $Name));
        return this;
    }

    public Select select( _parameters _ps ){
        if( this.constraint.test(_ps)){
            if( this.$params.isEmpty()|| this.$params.size() == 1 && 
                    this.$params.get(0).isMatchAny() ){
                return new Select(_ps, new Tokens() );
            }
            if( _ps.size() != this.$params.size() ){
                return null;
            }
        }        
        return null;
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
        //TODO THIS
        return null;
    }

    @Override
    public List<_parameters> listIn(Node astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Select> listSelectedIn(Node astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<Select> listSelectedIn(_model._node _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends Node> N removeIn(N astRootNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _node> N removeIn(N _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends Node> N forEachIn(N astRootNode, Consumer<_parameters> _nodeActionFn) {
        return null;
        /*
        astRootNode.walk(NodeWithParameters.class, n-> {
            NodeList<Parameter>ps = n.getParameters();
            Select sel = 
        });
        */
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_parameters> _nodeActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
