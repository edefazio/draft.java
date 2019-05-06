package draft.java.proto;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.nodeTypes.NodeWithModifiers;
import draft.Tokens;
import draft.Translator;
import draft.java.*;
import draft.java._model._node;
import draft.java._modifiers;
import draft.java._modifiers._hasModifiers;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * instead of directly matching (some expected set of modifiers) against a target
 * set of modifiers.  we specify the Modifiers that MUST be present in a set 
 * and other Modifiers that must NOT be present in the set...
 * 
 * if we want som EXACT set of modifiers (i.e. only "private"  )
 * @author Eric
 */
public class $modifiers 
    implements $proto<_modifiers>, $constructor.$part, $method.$part, $field.$part{
    
    public static $modifiers any(){
        return new $modifiers();
    }
    
    public static $modifiers of( Predicate<_modifiers> constraint ){
        return any().constraint(constraint);
    }
    
    public static $modifiers of( _hasModifiers _hm ){
        return $modifiers.of( _hm.getModifiers() );
    }
    
    /**
     * 
     * @param ms
     * @return 
     */
    public static $modifiers of( Modifier...ms  ){
        return $modifiers.of( _modifiers.of(ms) );
    }
    
    /**
     * Matches sets of modifiers that have all of these modifiers
     * @param _ms
     * @return 
     */
    public static $modifiers of( _modifiers _ms ){
        return of( _ms.ast() );
    }
    
    /**
     * Matches sets of modifiers that have all of these modifiers
     * @param astNwm
     * @return 
     */
    public static $modifiers of( NodeWithModifiers astNwm ){
        return of( astNwm.getModifiers() );
    }
    
    /**
     * Matches sets of modifiers that have all of these modifiers
     * @param mods
     * @return 
     */
    public static $modifiers of( Collection<Modifier> mods ){
        $modifiers $mods = new $modifiers();
        $mods.mustInclude.addAll(mods);
        return $mods;
    }
    
    /**
     * Matches sets of modifiers that have None of these modifiers
     * @param mods
     * @return 
     */
    public static $modifiers noneOf( Collection<Modifier> mods ){
        $modifiers $mods = new $modifiers();
        $mods.mustExclude.addAll(mods);
        return $mods;
    }
    
    /**
     * 
     * @param mods
     * @return 
     */
    public static $modifiers noneOf( Modifier... mods ){
        $modifiers $mods = new $modifiers();
        Arrays.stream(mods).forEach( m -> $mods.mustExclude.add(m) );        
        return $mods;
    }
   
    /** A matching lambda constraint */
    public Predicate<_modifiers> constraint = t-> true;
    
    /** The modifiers that MUST be present*/
    public Set<Modifier>mustInclude = new HashSet<>();    
    
    /** Modifiers that MUST NOT be present */
    public Set<Modifier>mustExclude = new HashSet<>();

    public $modifiers(){        
    }
    
    public $modifiers constraint( Predicate<_modifiers> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    public $modifiers addConstraint( Predicate<_modifiers> constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    public _modifiers compose(Translator translator, Map<String,Object> keyValues){
        _modifiers _ms = _modifiers.of();
        this.mustInclude.forEach(m -> _ms.set(m) );
        return _ms;
    }
    
     /**
     * Returns the first Statement that matches the 
     * @param astNode the 
     * @return 
     */
    @Override
    public _modifiers firstIn( Node astNode ){
        Optional<Node> f =                 
            astNode.findFirst( Node.class, 
                n -> (n instanceof NodeWithModifiers) 
                && select((NodeWithModifiers)n) != null 
            );         
        
        if( f.isPresent()){
            return _modifiers.of( (NodeWithModifiers)f.get());
        }
        return null;
    }    
    
    /**
     * Selects the first instance
     * @param astNode
     * @return 
     */
    public Select selectFirstIn( Node astNode ){
        Optional<Node> f =                 
            astNode.findFirst( Node.class, 
                n -> (n instanceof NodeWithModifiers) 
                && select((NodeWithModifiers)n) != null 
            );         
        
        if( f.isPresent()){
            return select( (NodeWithModifiers)f.get());
        }
        return null;
    }
    
    /**
     * 
     * @param _n
     * @return 
     */
    @Override
    public Select selectFirstIn( _node _n ){
        return selectFirstIn( _n.ast() );
    }
    
    @Override
    public _modifiers firstIn( _node _n ){
        return firstIn( _n.ast() );
    }
    
    @Override
    public List<_modifiers> listIn(_node _n) {
        List<_modifiers> found = new ArrayList<>();
        forEachIn( _n, f-> found.add(f) );
        return found;
    }

    @Override
    public List<_modifiers> listIn(Node astRootNode) {
        List<_modifiers> found = new ArrayList<>();
        forEachIn( astRootNode, f-> found.add(f) );
        return found;
    }

    @Override
    public List<Select> listSelectedIn(Node astRootNode) {
        List<Select> found = new ArrayList<>();
        forSelectedIn( astRootNode, f-> found.add(f) );
        return found;
    }

    @Override
    public List<? extends selected> listSelectedIn(_node _n) {
        List<Select> found = new ArrayList<>();
        forSelectedIn( _n, f-> found.add(f) );
        return found;
    }

    @Override
    public <N extends Node> N removeIn(N astRootNode) {
        //"remove" for modifiers is.... interesting... it "should" mean take them 
        //out AND leave the underlying NodeList to be empty
        //it sorta doesnt make sense
        throw new UnsupportedOperationException("Cant remove modifiers"); 
    }

    @Override
    public <N extends _model._node> N removeIn(N _n) {
        throw new UnsupportedOperationException("Cant remove modifiers"); 
    }

    @Override
    public <N extends Node> N forEachIn(N astRootNode, Consumer<_modifiers> _nodeActionFn) {
        forEachIn( (_node)_java.of( astRootNode ), _nodeActionFn );        
        return astRootNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_modifiers> _nodeActionFn) {
        return Walk.in( _n, 
            NodeWithModifiers.class, 
            nwm->{
                Select sel = select( nwm );
                if( sel != null ){
                    _nodeActionFn.accept(sel.model());
                }                
            });
    }

    public <N extends Node> N forSelectedIn(N astRootNode, Consumer<Select> selectActionFn) {
        forSelectedIn( (_node)_java.of(astRootNode), selectActionFn );
        return astRootNode;
    }
    
    public <N extends Node> N forSelectedIn(N astRootNode, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn) {
        forSelectedIn( (_node)_java.of(astRootNode), selectConstraint, selectActionFn );
        return astRootNode;
    }
    
    public <N extends _node> N forSelectedIn(N _n, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn) {
        return Walk.in( _n, 
            NodeWithModifiers.class, 
            nwm->{
                Select sel = select( nwm );
                if( sel != null && selectConstraint.test(sel)){
                    selectActionFn.accept(sel);
                }                
            });
    }
    
    public <N extends _node> N forSelectedIn(N _n, Consumer<Select> selectActionFn) {
        return Walk.in( _n, 
            NodeWithModifiers.class, 
            nwm->{
                Select sel = select( nwm );
                if( sel != null ){
                    selectActionFn.accept(sel);
                }                
            });
    }
    
    public Select select(_hasModifiers _hm ){
        return select(_hm.getModifiers());
    }
    
    public Select select(NodeWithModifiers astNwm ){
        return select(_modifiers.of(astNwm));
    }
    
    public Select select(_modifiers _ms){
        if( this.constraint.test(_ms) && 
            _ms.containsAll(this.mustInclude) &&  
            !_ms.containsAny(this.mustExclude) ){
            return new Select( _ms );
        }
        return null;
    }
    
    public _modifiers construct(Translator translator, Map<String, Object> keyValues) {
        return _modifiers.of(this.mustInclude.toArray(new Modifier[0]));
    }    
    
    public static class Select implements selected<_modifiers>, selected_model<_modifiers>{

        public _modifiers _mods;
        public $nameValues args = new $nameValues(new Tokens());
        
        public Select(_modifiers _mods ) {
            this._mods = _mods;            
        }
        
        public boolean isStatic(){
            return _mods.isStatic();
        }
        
        public boolean isSynchronized(){
            return _mods.isSynchronized();
        }
        
        public boolean isTransient(){
            return _mods.isTransient();
        }
        
        public boolean isVolatile(){
            return _mods.isVolatile();
        }
        
        public boolean isStrictFp(){
            return _mods.isStrict();
        }
        
        public boolean isPublic(){
            return _mods.isPublic();
        }
        
        public boolean isPrivate(){
            return _mods.isPrivate();
        }
        
        public boolean isProtected(){
            return _mods.isProtected();
        }
        
        public boolean isPackagePrivate(){
            return _mods.isPackagePrivate();
        }
        
        public boolean isAbstract(){
            return _mods.isAbstract();
        }
        
        public boolean isFinal(){
            return _mods.isFinal();
        }
        
        public boolean isEmpty(){
            return _mods.isEmpty();
        }
        
        public boolean isDefault(){
            return _mods.isDefault();
        }
        
        public boolean isNative(){
            return _mods.isNative();
        }
        
        public boolean containsAll( Collection<Modifier> mods ){
            return _mods.containsAll(mods);
        }
        
        public boolean containsAny( Collection<Modifier> mods ){
            return _mods.containsAny(mods);
        }
        
        @Override
        public $nameValues args() {
            return args;
        }

        @Override
        public _modifiers model() {
            return _mods;
        }        
    }
}
