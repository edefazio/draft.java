package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.nodeTypes.NodeWithAnnotations;
import draft.*;
import draft.java.Walk;
import draft.java._anno;
import draft.java._anno._annos;
import draft.java._anno._hasAnnos;
import draft.java._java;
import draft.java._model;
import draft.java._model._node;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author Eric
 */
public class $annos 
    implements Template<_annos>, $proto<_annos>, $constructor.$part, $method.$part, 
        $field.$part {

    /** 
     * List of anno prototypes, note: an empty list means it matches ANY list 
     * of _annos
     */
    List<$anno> $annosList = new ArrayList<>();
    
    /**
     * A Matching predicate for _annos
     */
    Predicate<_annos> constraint = t-> true;
    
    /**
     * prototype that matches any grouping of annos
     * @return 
     */
    public static $annos any(){
        return new $annos();
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static $annos of( Predicate<_annos> constraint ){
       return any().constraint(constraint);
    }
    
    /**
     * 
     * @param $anns
     * @return 
     */
    public static $annos of( $anno...$anns ){
        $annos $as = new $annos();
        Arrays.stream($anns).forEach(a -> $as.$annosList.add(a));
        return $as;
    }
    
    public static $annos of(String...annoPatterns){
        return new $annos(annoPatterns);
    }
    
    public static $annos of(_annos annos){
        return new $annos(annos);
    }
    
    public $annos( String...annoPatterns ){
        this(_annos.of(annoPatterns));
    }
    
    public $annos( _annos _anns){
        if( _anns != null ){
            for(int i=0;i<_anns.size();i++){
                $anno a = $anno.of(_anns.get(i) );
                $annosList.add(a);
            }    
        }        
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public $annos constraint( Predicate<_annos> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public $annos addConstraint( Predicate<_annos> constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    public boolean isMatchAny(){
        try{
            return( this.constraint.test(null) && 
                this.$annosList.isEmpty() || 
                this.$annosList.size() == 1 
                && this.$annosList.get(0).isMatchAny());
        } catch(Exception e){
            return false;
        }
    }
    /**
     * 
     * @param translator
     * @param keyValues
     * @return 
     */
    public String compose( Translator translator, Map<String, Object> keyValues){
        return compose(translator, keyValues, System.lineSeparator() );
    }
    
    /**
     * 
     * @param translator
     * @param keyValues
     * @param separator the separator to use between each anno 
     * (usually this will be a line break, but, for parameters we use an " " 
     * empty space
     * 
     * @return 
     */        
    public String compose( Translator translator, Map<String, Object> keyValues, String separator) {
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<$annosList.size();i++){            
            if( i > 0 ){
                sb.append(separator);
            }
            sb.append( $annosList.get(i).construct(translator, keyValues) );
        }
        if( $annosList.size() > 0 ){
            //add another separator AFTER the end (a line break of space) so
            // annos dont bleed into other code
            sb.append( separator );
        }
        if( keyValues.get("$annos") != null){ //they can supply the annos            
            $annos $as = $annos.of( keyValues.get("$annos").toString() );
            sb.append($as.compose(translator, keyValues, separator));                
        }
        return sb.toString();
    }
    
    @Override
    public _annos construct(Translator translator, Map<String, Object> keyValues) {
        _annos _as = _annos.of();
        for(int i=0;i<$annosList.size();i++){            
            _as.add( $annosList.get(i).construct(translator, keyValues) );
        }
        //handle $name OVERLOAD i.e. if they pass in a $annos
        if( keyValues.get("$annos") != null){ //they can supply the annos            
            $annos $as = $annos.of( keyValues.get("$annos").toString() );
            //must extract the $annos to avoid a stack overflow
            Map<String,Object> kvs = new HashMap<>();
            kvs.putAll(keyValues);
            kvs.remove("$annos");
            _as.addAll( $as.construct(translator, kvs).list() );
            //sb.append($as.compose(translator, keyValues, separator));                
        }
        return _as;
    }

    @Override
    public $annos $(String target, String $Name) {
        $annosList.forEach(a -> a.$(target, $Name));
        return this;
    }

    public $annos hardcode$( Translator tr, Object... keyValues ) {
        $annosList.forEach(a -> a.hardcode$(tr, keyValues));
        return this;
    }
    
    @Override
    public List<String> list$() {
        List<String>found = new ArrayList();
        $annosList.forEach(a -> found.addAll(a.list$()) );
        return found;
    }

    @Override
    public List<String> list$Normalized() {
        List<String>found = new ArrayList();
        $annosList.forEach(a -> found.addAll(a.list$Normalized()) );        
        return found.stream().distinct().collect(Collectors.toList());
    }

    public $annos add(String...annoPatterns){
        Arrays.stream(annoPatterns).forEach( a -> this.$annosList.add( $anno.of(a) ) );
        return this;
    }
    
    public boolean matches( NodeWithAnnotations astAnnoNode ){
        return select(astAnnoNode)!= null;
    }
    
    public boolean matches( _annos _as) {
        return select(_as)!= null;
    }
    
    public boolean matches( _hasAnnos _ha) {
        return select(_ha)!= null;
    }
    
    public Select select( NodeWithAnnotations astAnnoNode ){
        return select(_annos.of(astAnnoNode) );
    }
    
    public Select select( _annos _anns ){
        if( ! this.constraint.test(_anns)){
            return null;
        }
        List<_anno> annosLeft = new ArrayList<>();
        annosLeft.addAll( _anns.list() );
        Tokens tokens = new Tokens();
        //_annos _as = _annotated.getAnnos();
        for(int i=0;i<this.$annosList.size();i++){
            $anno $a = $annosList.get(i);
            Optional<_anno> oa = annosLeft.stream().filter(a-> $a.matches(a)).findFirst();
            if( !oa.isPresent() ){
                return null; //didnf find a matching anno
            }
            annosLeft.remove(oa.get());
            $anno.Select $as = $a.select(oa.get());
            if( tokens.isConsistent($as.args.asTokens())){ //args arent consistent
                tokens.putAll($as.args.asTokens());
            } else{
                return null;
            }
        }
        return new Select(_anns, tokens);
    }
    
    
    public Select select( _hasAnnos _annotated ){
        return select( _annotated.getAnnos() );        
    }
    
    public Tokens decomposeTo( _annos _as, Tokens allTokens ){
        if(allTokens == null){
            return allTokens;
        }
        Select sel = select(_as);
        if( sel != null ){
            if( allTokens.isConsistent(sel.args.asTokens()) ){
                allTokens.putAll(sel.args.asTokens());
                return allTokens;
            }
        }
        return null;
    }
    
    /**
     * Returns the first Statement that matches the 
     * @param astNode the 
     * @return 
     */
    @Override
    public _annos firstIn( Node astNode ){
        Optional<Node> f = 
                
            astNode.findFirst( Node.class, 
                n -> (n instanceof NodeWithAnnotations) 
                && matches((NodeWithAnnotations)n) 
            );         
        
        if( f.isPresent()){
            return _annos.of( (NodeWithAnnotations)f.get());
        }
        return null;
    }    
    
    /**
     * 
     * @param astNode
     * @return 
     */
    @Override
    public Select selectFirstIn( Node astNode ){
        Optional<Node> f = 
                
            astNode.findFirst( Node.class, 
                n -> (n instanceof NodeWithAnnotations) 
                && matches((NodeWithAnnotations)n) 
            );         
        
        if( f.isPresent()){
            return select( (NodeWithAnnotations)f.get());
        }
        return null;
    }
    
    @Override
    public Select selectFirstIn( _node _n ){
        return selectFirstIn( _n.ast() );
    }
    
    @Override
    public _annos firstIn( _node _n ){
        return firstIn( _n.ast() );
    }
    
    @Override
    public List<_annos> listIn(_model._node _n) {
        List<_annos> found = new ArrayList<>();
        Walk.in(_n, _hasAnnos.class, _ha-> {
            Select sel = select(_ha);
            if( sel != null ){
                found.add( sel._anns );
            }
        });
        return found;
    }

    @Override
    public List<_annos> listIn(Node astRootNode) {
        return listIn( (_node)_java.of(astRootNode) );        
    }

    @Override
    public List<Select> listSelectedIn(Node astRootNode) {
        return listSelectedIn( (_node)_java.of(astRootNode) );        
    }

    @Override
    public List<Select> listSelectedIn( _node _n) {
        List<Select> found = new ArrayList<>();
        Walk.in(_n, _hasAnnos.class, _ha-> {
            Select sel = select(_ha);
            if( sel != null ){
                found.add( sel );
            }
        });
        return found;
    }

    @Override
    public <N extends Node> N removeIn(N astRootNode) {
        removeIn( (_node)_java.of(astRootNode) );         
        return astRootNode;
    }

    @Override
    public <N extends _model._node> N removeIn(N _n) {
        return Walk.in(_n, _hasAnnos.class, _ha-> {
            Select sel = select(_ha);
            if( sel != null ){
                _ha.removeAnnos(h->true);//remove all annos
            }
        });        
    }

    @Override
    public <N extends Node> N forEachIn(N astRootNode, Consumer<_annos> _annosActionFn) {
        forEachIn( (_node)_java.of(astRootNode), _annosActionFn );         
        return astRootNode;
    }

    @Override
    public <N extends _model._node> N forEachIn(N _n, Consumer<_annos> _annosActionFn) {
        return Walk.in(_n, _hasAnnos.class, _ha-> {
            Select sel = select(_ha);
            if( sel != null ){
                _annosActionFn.accept(sel._anns);
            }
        });
    }
    
    @Override
    public String toString(){
        if( this.isMatchAny()){
            return "$annos(MATCH ANY)";
        }
        StringBuilder sb = new StringBuilder();
        this.$annosList.forEach(a -> sb.append("    ").append(a));
        return sb.toString();
    }
    
    /**
     * A Matched Selection result returned from matching a prototype $a
     * inside of some Node or _node
     */
    public static class Select
        implements $proto.selected, selected_model<_annos> {

        public final _annos _anns;
        public final $nameValues args;

        public Select(_annos _a, Tokens tokens) {
            this(_a, $nameValues.of(tokens));
        }

        public Select(_annos _as, $nameValues tokens) {
            this._anns = _as;
            args = tokens;
        }

        @Override
        public $nameValues args() {
            return args;
        }

        @Override
        public String toString() {
            return "$annos.Select {" + System.lineSeparator()
                + Text.indent(_anns.toString()) + System.lineSeparator()
                + Text.indent("$args : " + args) + System.lineSeparator()
                + "}";
        }

        @Override
        public _annos model() {
            return _anns;
        }
    }
}
