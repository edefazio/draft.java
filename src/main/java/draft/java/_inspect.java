package draft.java;

import draft.Text;
import draft.java._java.Component;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import name.fraser.neil.plaintext.diff_match_patch.Diff;
import name.fraser.neil.plaintext.diff_match_patch.Operation;

/**
 * <P>Inspect is an abstraction that knows how to "take apart" and inspect
 * a composite entity and inspect it (usually against another entity)</P>
 * 
 * <P>Think of it like a detailed RUNNABLE INSTRUCTION MANUAL for how to take apart
 * a composite instance (and all other composite instances contained within it)
 * and COMPARE these components to another (perhaps a PROTOTYPE instance).</P>
 * 
 * <P>A good analogy would be an Instruction manual for how to break down a Car 
 * Engine, and how to inspect the individual parts of the engine</P> 
 * 
 * <P>This provides a way of us to "deeply diff" based on the semantics of the Java 
 * language... for example given a class F with (2) versions in the code repository:
 * <PRE>
 * //Version 1
 * class F{
 *    static final int ID = 100;
 *    int a;
 *    float b;
 *    void m(){}
 * }
 * 
 * //Version 2
 * class F{
 *    void m(){ System.out.println(1);}
 *    @Deprecated int a;
 *    float b;
 *    String name;
 * } 
 * </PRE>
 * we can diff top find what differences occurred between version 1 and 
 * version 2.
 * 
 * <PRE>
 * //assuming we loaded these classes as _v1 and _v2
 * _diff _d = _diff( _v1, _v2 );
 * //prints paths to all entities added in _v2
 * _d.forAdds( a -> System.out.println(   "ADDED  :" + a.path) ); 
 * 
 * //prints paths to all of the entities removed in v2
 * _d.forRemoves( r -> System.out.println("REMOVED:" + r.path) ); 
 * 
 * //prints paths to all edited entities in v2
 * _d.forEdits( e -> System.out.println(  "EDITED :" + e.path) ); 
 * </PRE>
 * ...if we wanted to know the semantic differentces between the 2
 * </P>
 * 
 * if we have a Large program
 * 
 * 
 * @author Eric
 * @param <T> the type to be inspected
 */
public interface _inspect<T> {
    
    public boolean equivalent( T left, T right );    
    
    default _diff diff( T left, T right) {
        return _inspect.this.diff(new _path(), new _diff(), left, right );
    }
    
    default _diff diff( _diff dt, T left, T right) {
        return _inspect.this.diff( new _path(), dt, left, right );
    }
    
    default _diff diff( _path path, _diff dt, T left, T right ){
        return diff(_java.INSPECTOR, path, dt, left, right);
    }
    
    public _diff diff( _java._inspector _inspect, _path path, _diff dt, T left, T right);
    
   
    /**
     * Defines the components and identifies the path within a _type to
     * a specific member or property
     * 
     * for example:
     * <PRE>
     * (ALLCAPS = _java.COMPONENT, (inside parenthesis = id)
     * 
     * CLASS[MyClass].NAME                          : the name of class MyClass
     * INTERFACE[I].EXTENDS                         : the extends on the interface I
     * ENUM[Scope].IMPLEMENTS                       : the implements on the enum Scope
     * ANNOTATION[Retain].FIELD[hops].INIT          : init of a field hops on the annotation Retain
     * CLASS[MyClass].METHOD[m(int)].PARAMETER[0]   : the first parameter on method m(int) in class MyClass
     * 
     * //if we have nested components it can get interesting (omit Component for brevity) 
     * ENUM[E].NEST.CLASS[inner].METHOD[m()].BODY   : (the method body on a nested class within an enum)
     * </PRE>
     * 
     */
    public static class _path{
            
        /** 
         * the types of components that identify an entity 
         * for example: 
         * <PRE>
         * Component.CLASS, Component.NAME (the class Name)
         * Component.INTERFACE, Component.EXTENDS (the extends on the interface)
         * Component.ENUM, Component.IMPLEMENTS ( the implements on the enum)
         * Component.ANNOTATION. Component.FIELD, Component.INIT (init of a field on the annotation)
         * 
         * //if we have nested components it can get interesting (omit Component for brevity) 
         * ENUM, CLASS, METHOD, BODY (the method body on a nested class within an enum)
         * </PRE>
         * 
         * we build this as we traverse the class (when identifying diffs)
         */ 
        List<_java.Component> componentPath;
        
        /**
         * The identifying String of a member component (usually the name of the member) 
         * (i.e. for a _field, _type, _method, _annotation._element, or _enum._constant the name)
         * (for a constructor, the parameter types)
         * 
         * //NOTE: can be empty for non-named components (i.e. EXTENDS, IMPLEMENTS, etc.)
         */
        List<String> idPath;
        
        public _path(){
            componentPath = new ArrayList<>();
            idPath = new ArrayList<>();
        }
        
        public _path(_path original){
            componentPath = new ArrayList();
            componentPath.addAll(original.componentPath);
            idPath = new ArrayList();
            idPath.addAll(original.idPath);
        }

        /** Build and return a new _path that follows the current path down one component */
        public _path in( _java.Component component){
            return in( component, "");            
        }
        
        public int size(){
            return this.componentPath.size();
        }
        
        public Component leaf(){
            if( !this.componentPath.isEmpty() ){
                return this.componentPath.get(this.componentPath.size() -1 );
            }
            return null;
        }
        
        public boolean isLeaf( Component component ){
            return component.equals(leaf());
        }
        
        public boolean isLeafId( String id ){
            return id.equals(leafId());
        }
        
        public String leafId(){
            if( !this.idPath.isEmpty() ){
                return this.idPath.get(this.idPath.size() -1 );
            }
            return null;
        }
        
        public boolean has(String id){
            return idPath.contains(id);
        }
        
        public boolean has(Component... components){  
            Set<Component> s = new HashSet<>();
            Arrays.stream(components).forEach( c -> s.add(c));
            return componentPath.containsAll(s);
        }
        
        public boolean has(Component component){
            return componentPath.contains(component);
        }
        
        /** Build and return a new _path that follows the current path down one component */
        public _path in(_java.Component component, String id){
            _path _p = new _path(this);
            _p.componentPath.add(component);
            _p.idPath.add(id);
            return _p;
        }        
        
        /** return the component path as a String */
        public String componentPathString(){
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<this.componentPath.size();i++){
                if( i > 0 ){
                    sb.append(".");
                }
                sb.append(this.componentPath.get(i).getName());
            }
            return sb.toString();
        }
        
        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            for(int i=0;i<this.componentPath.size();i++){
                if( i > 0 ){
                    sb.append(".");
                }
                sb.append(this.componentPath.get(i).getName());
                if( this.idPath.get(i).length() > 0){
                    sb.append("[").append( this.idPath.get(i) ).append("]");
                }
            }
            return sb.toString();
        }
    }
    
    /**
     * collection of diff _nodes that represent the deep differences between
     * two domain objects (two _class es, _methods, _fields)
     */
    public static class _diff{
        
        /** the underlying diffs collected */
        public List<_node> diffs = new ArrayList<>();
        
        /** @return number of diffs */
        public int size(){
            return diffs.size();
        }
        
        public boolean isEmpty(){
            return size() == 0;
        }
        
        /**
         * List all paths that have diffs
         * @return 
         */
        public List<_path>paths(){
            return diffs.stream().map(d -> d.path() ).collect(Collectors.toList());
        }
        
        /**
         * Return the diff at the path, (or null if no diff exists at this path)
         * @param _p
         * @return 
         */
        public _node at(_path _p){      
            return first( d-> d.path().equals(_p));            
        } 
        
        /**
         * Does the diff happen for this specific component type 
         * (i.e. Component.FIELD, Component.METHOD, Component.BODY , etc. )
         * @param component the component type
         * @return true if there exists at least one diff containing the component
         */
        public boolean has(Component component){
            return has( new Component[]{component} );
        }
        
        /**
         * Is there at least (1) diff that contains these components
         * 
         * @param components the components expected to have a diff
         * @return true if there is at least (1) diff containing ALL of the components
         */
        public boolean has(Component...components){
           return first(components) != null;
        }
        
        /**
         * for example 
         * 
         * <PRE>
         * has( PARAMETER, "0" ); //is there a diff someone with the first parameter?
         * </PRE>
         * @param component
         * @param ids
         * @return 
         */
        public boolean has(Component component, String... ids ){
            return first( d -> d.has(component) && d.has(ids) ) != null;
        }
        
        /**
         * For each of the matching DiffNodes in the Tree, perform some action
         * @param _nodeMatchFn function for matching specific _diffNodes
         * @param _nodeActionFn consumer action function on selected _diffNodes
         * @return  the potentially modified) _diffTree
         */
        public _diff forEach( Predicate<_node> _nodeMatchFn, Consumer<_node> _nodeActionFn ){
            list(_nodeMatchFn).forEach(_nodeActionFn);
            return this;
        }
        
        /**
         * 
         * @param _nodeFn
         * @return the potentially modified diff
         */
        public _diff forEach( Consumer<_node> _nodeFn ){
            this.diffs.forEach(_nodeFn);
            return this;
        }
        /**
         * For all diffs that are ADD 
         * (meaning they are ABSENT in LEFT and PRESENT in RIGHT)
         * check if they match the _addNodeActionFn and, if so, execute the
         * _addNodeActionFn
         * @param _addNodeMatchFn matching function for Add nodes
         * @param _addNodeActionFn the action for adds that pass the matchFn
         * @return the potentially modified _diff
         */
        public _diff forAdds( Predicate<_addNode> _addNodeMatchFn, Consumer<_addNode> _addNodeActionFn){
            List<_node> ns = list(d -> d instanceof _addNode &&  _addNodeMatchFn.test((_addNode)d));
            List<_addNode> ans = new ArrayList<>();
            ns.forEach(n -> ans.add( (_addNode)n));
            ans.forEach( _addNodeActionFn );
            return this;
        }
        
        public _diff forAdds( Consumer<_addNode> _addNodeActionFn ){
            List<_node> ns = list(d -> d instanceof _addNode);
            List<_addNode> ans = new ArrayList<>();
            ns.forEach(n -> ans.add( (_addNode)n));
            ans.forEach( _addNodeActionFn );
            return this;            
        }

        public _diff forRemoves( Predicate<_removeNode> _removeNodeMatchFn, Consumer<_removeNode> _removeNodeActionFn){
            List<_node> ns = list(d -> d instanceof _removeNode &&  _removeNodeMatchFn.test((_removeNode)d));
            List<_removeNode> ans = new ArrayList<>();
            ns.forEach(n -> ans.add( (_removeNode)n));
            ans.forEach( _removeNodeActionFn );
            return this;
        }
        
        public _diff forRemoves( Consumer<_removeNode> _removeNodeActionFn ){
            List<_node> ns = list(d -> d instanceof _removeNode );
            List<_removeNode> ans = new ArrayList<>();
            ns.forEach(n -> ans.add( (_removeNode)n));
            ans.forEach( _removeNodeActionFn );
            return this;
        }        
        
        public _diff forChanges( Predicate<_changeNode> _changeNodeMatchFn, Consumer<_changeNode> _changeNodeActionFn){
            List<_node> ns = list(d -> d instanceof _removeNode &&  _changeNodeMatchFn.test((_changeNode)d));
            List<_changeNode> ans = new ArrayList<>();
            ns.forEach(n -> ans.add( (_changeNode)n));
            ans.forEach( _changeNodeActionFn );
            return this;
        }
        
        public _diff forChanges( Consumer<_changeNode> _changeNodeActionFn ){
            List<_node> ns = list(d -> d instanceof _changeNode );
            List<_changeNode> ans = new ArrayList<>();
            ns.forEach(n -> ans.add( (_changeNode)n));
            ans.forEach( _changeNodeActionFn );
            return this;
        }  
        
        public _diff forEdits(Consumer<_editNode> _editNodeActionFn){
            List<_node> ns = list(d -> d instanceof _editNode );
            List<_editNode> ans = new ArrayList<>();
            ns.forEach(n -> ans.add( (_editNode)n));
            ans.forEach( _editNodeActionFn );
            return this;
        }
        
        public _diff forEdits( Predicate<_editNode> _editNodeMatchFn, Consumer<_editNode> _editNodeActionFn){
            List<_node> ns = list(d -> d instanceof _editNode &&  _editNodeMatchFn.test((_editNode)d));
            List<_editNode> ans = new ArrayList<>();
            ns.forEach(n -> ans.add( (_editNode)n));
            ans.forEach( _editNodeActionFn );
            return this;
        }
        //forAddEdits
        //forRemoveEdits
        //forSameEdits
        
        public List<_editNode> listEdits( Predicate<_editNode> _editNodeMatchFn ){
            List<_editNode> ens = new ArrayList<>();            
            list( d -> d instanceof _editNode && _editNodeMatchFn.test( (_editNode)d) )
                    .forEach( e -> ens.add( (_editNode)e));
            return ens;
        }
        
        /**
         * Lists the diffs that are edit "text diffs" (usually diffs for some body of code)
         * 
         * these involve the edit distance algorithm (more low level involved for
         * determining edits needed to be applied to the left text to create the 
         * right text
         * 
         * @see _editNode
         * @return a list of _editNode that have edits (usually in code bodies)
         */
        public List<_editNode> listEdits(){
            List<_editNode> tds = new ArrayList<>();
            list(d -> d instanceof _editNode ).forEach(n -> tds.add( (_editNode)n));
            return tds;
        }
        
        public List<_node> list(){
            return this.diffs;
        } 
        
        public _node first( Component componet ){
            Optional<_node> first = 
                    this.diffs.stream().filter(d -> d.has(componet)).findFirst();
            if( first.isPresent() ){
                return first.get();
            }
            return null;
        }
        
        public _node first( Component... componets ){
            Optional<_node> first = 
                    this.diffs.stream().filter(d -> d.has(componets) ).findFirst();
            if( first.isPresent() ){
                return first.get();
            }
            return null;
        }
        
        public _node first( Predicate<_node> _diffNodeMatchFn ){
            Optional<_node> first = 
                    this.diffs.stream().filter(_diffNodeMatchFn).findFirst();
            if( first.isPresent() ){
                return first.get();
            }
            return null;
        }
        
        /**
         * List all diffs that have this component type in them
         * @param component the component type
         * @return a list of diffNodes
         */
        public List<_node> list( Component component ){
            return list( d-> d.has(component));
        }
        
        /**
         * List All diffs that have these components in their path IN THIS ORDER
         * 
         * _class _a = _class.of("C").field("int i;");
         * _class _b = _class.of("@A C").field("@Deprecated int i;");
         * 
         * _diffTree dt = _class.diffTree(_a, _b);
         * 
         * //verify that there is ONE match of an ANNOTATION on a FIELD
         * assertEquals(1, dt.list(Component.FIELD, Component.ANNO).size());
         * 
         * //components DONT have to be in order
         * assertEquals(1, dt.list(Component.ANNO, Component.FIELD).size());
         * 
         * //they only provide one
         * assertEquals(2, dt.list(Component.ANNO).size()); 
         * 
         * @param components
         * @return 
         */
        public List<_node> list( Component...components ){
            return list( d-> d.has(components));
        }
        
        /**
         * Lists nodes matching a matchFn
         * @param _nodeMatchFn matches diff nodes for retrieval
         * @return list of diff nodes that match the nodeMatchFn
         */
        public List<_node> list(Predicate<_node> _nodeMatchFn ){
            return this.diffs.stream().filter(_nodeMatchFn).collect(Collectors.toList());
        }
        
        /**
         * Add a "textual" edit diff between two entities
         * 
         * @param path the path to the entity
         * @param lds the linkedList of diffs based on the textual diffs between
         * the left and right entities
         * @param left the left entity
         * @param right the right entity
         * @return the diffTree
         */
        public _diff addEdit( _path path, LinkedList<Diff> lds, Object left, Object right ){
            diffs.add( _editNode.of(path, lds, left, right) );
            return this;
        }
        
        /**
         * Adds the appropriate _node (_addNode, _removeNode, _changeNode) depending
         * on the state of left and right
         * 
         * @param path the path to the diff node
         * @param left the value of the left entity
         * @param right the value of the right entity
         * @return  the updated _diff
         */
        public _diff add( _path path, Object left, Object right ){
            if( left == null ){
                diffs.add( new _addNode(path, right));
                return this;
            }
            if( right == null ){
                diffs.add( new _removeNode(path, left));
                return this;
            }
            diffs.add( new _changeNode(path, left, right));
            return this;
        }
        
        @Override
        public String toString(){
            if( isEmpty() ){
                return "-- (0) differences --";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("(").append( diffs.size()).append(") diffs").append(System.lineSeparator());
            this.diffs.forEach( d-> sb.append(d) );            
            return sb.toString();
        }        
    }    
    
    public interface _node{
        
        Object left();
        
        Object right();
        
        _path path();
        
        public boolean has( Component component );
        
        /**
         * 
         * @param components
         * @return 
         */
        public boolean has( Component... components );
        
        /** 
         * does this node have this id within the path?  
         * @param id a particular id (i.e. "0", "isFull", etc.)
         * @return true if the nod has the id within the path
         */
        public boolean has( String id );
        
        /** 
         * does this node have ALL these ids within the path?  
         * @param ids a particular ids (i.e. "0", "isFull", etc.)
         * @return true if the nod has the id within the path
         */
        public boolean has( String... ids );
    }
    
    /**
     * A node that is NOT found on the left but is found on the right
     * (it has been added in transitioning between the left and right)
     */
    public static class _addNode implements _node {
        Object add;
        _path path;
        
        public _addNode( _path path, Object added ){
            this.path = path;
            this.add = added;
        }
        
        @Override
        public Object left(){
            return null;
        }
        
        @Override
        public Object right(){
            return add;
        }
        
        @Override
        public _path path(){
            return path;
        }
        
        /**
         * does the path of the diffNode have the particualr id?
         * @param id the id of the entity("f", "m(String)", parameter[1]
         * @return if the 
         */
        @Override
        public boolean has(String id){
            return this.path.idPath.contains(id);
        }
        
        @Override
        public boolean has(String...ids){
            Set<String> idd = new HashSet<>();
            Arrays.stream(ids).forEach(i -> idd.add(i) );
            return this.path.idPath.containsAll(idd);
        }
        
        
        @Override
        public boolean has( Component component ){
            return path.has(component);
        }
        
        @Override
        public boolean has( Component... components ){
            return path.has( components );
        }
        
        @Override
        public String toString(){
            return "  + " + path.toString() + System.lineSeparator();
        }
    }
    
    /**
     * an entity that exists on the left which is removed on the right
     */
    public static class _removeNode implements _node{
        Object removed;
        _path path;
        
        public _removeNode( _path path, Object removed ){
            this.path = path;
            this.removed = removed;
        }
        
        @Override
        public Object left(){
            return removed;
        }
        
        @Override
        public Object right(){
            return null;
        }
        
        @Override
        public _path path(){
            return path;
        }
        
        /**
         * does the path of the diffNode have the particualr id?
         * @param id the id of the entity("f", "m(String)", parameter[1]
         * @return if the 
         */
        @Override
        public boolean has(String id){
            return this.path.idPath.contains(id);
        }
        
        @Override
        public boolean has(String...ids){
            Set<String> idd = new HashSet<>();
            Arrays.stream(ids).forEach(i -> idd.add(i) );
            return this.path.idPath.containsAll(idd);
        }
        
        @Override
        public boolean has( Component component ){
            return path.has(component);
        }
        
        @Override
        public boolean has( Component... components ){
            return path.has( components );
        }
        
        @Override
        public String toString(){
            return "  - " + path.toString() + System.lineSeparator();
        }
    }
    
    /**
     * A Node that has a changed state between the left and right node
     * (for example an added or changed modifier, implement or extends)
     */
    public static class _changeNode implements _node{
        Object left;
        Object right;
        _path path;
        
        public _changeNode( _path path, Object left, Object right ){
            this.path = path;
            this.left = left;
            this.right = right;
        }
        
        @Override
        public Object left(){
            return left;
        }
        
        @Override
        public Object right(){
            return right;
        }
        
        @Override
        public _path path(){
            return path;
        }
        
        /**
         * does the path of the diffNode have the particualr id?
         * @param id the id of the entity("f", "m(String)", parameter[1]
         * @return if the 
         */
        @Override
        public boolean has(String id){
            return this.path.idPath.contains(id);
        }
        
        @Override
        public boolean has(String...ids){
            Set<String> idd = new HashSet<>();
            Arrays.stream(ids).forEach(i -> idd.add(i) );
            return this.path.idPath.containsAll(idd);
        }
        
        @Override
        public boolean has( Component component ){
            return path.has(component);
        }
        
        @Override
        public boolean has( Component... components ){
            return path.has( components );
        }
        
        @Override
        public String toString(){
            return "  ~ " + path.toString() + System.lineSeparator();
        }
    }
    
    /**
     * A different kind of Node to represent edits of the text. 
     * This involves looking at the "edit distance" between two textual entities.
     * 
     * This is more in line your typical diff (between (2) files) where 
     * we internally try to figure out "edits" between the code/some text on the
     * left and the code/some text on the right
     * 
     * i.e. with (2) documents LEFT and RIGHT side by side:
     * <PRE>
     *  LEFT  |  RIGHT
     *        |
     *  AA    |  AA 
     *  BB    |  CC
     *  CC    |  DD
     *  EE    |  FF
     *  FF    |  GG
     * </PRE>
     * 
     * when we calculate the "EDIT DISTANCE" diff, we consider what edits
     * (adds and removals) from the LEFT document have to occur to reach the
     * RIGHT document.
     * 
     * 
     * <PRE>     
     *  LEFT     EDITS
     *            
     *  AA       RETAIN AA
     *  - BB     REMOVE BB
     *  CC       RETAIN CC 
     *  + DD     ADD    DD 
     *  - EE     REMOVE EE
     *  FF       RETAIN FF
     *  + GG     ADD    GG
     * </PRE>
     * 
     * ...if we "execute" these edits on LEFT we end up with RIGHT
     * <PRE>
     *  AA
     *  CC
     *  DD
     *  FF
     *  GG
     * </PRE>
     */
    public static class _editNode implements _node{
        
        public static final _editNode of (_path path, LinkedList<Diff> diffs, Object left, Object right ){
            return new _editNode(path, diffs, left, right );
        }
        
        /** A list of diff_match_patch Diffs based on the TEXTUAL DIFFS of the left and right entity */
        public LinkedList<Diff>diffs;        
        /** the path to the entity */
        public _path path;
        /** the full object of the left entity */
        public Object left; 
        /** the full right object entity */
        public Object right;
        
        public _editNode( _path path, LinkedList<Diff> diffs, Object left, Object right){
            this.diffs = diffs;
            this.path = path;
            this.left = left;
            this.right = right;
        }
        
        @Override
        public Object left(){
            return left;
        }
        
        @Override
        public Object right(){
            return right;
        }
        
        @Override
        public _path path(){
            return path;
        }
        
        /**
         * does the path of the diffNode have the particualr id?
         * @param id the id of the entity("f", "m(String)", parameter[1]
         * @return if the 
         */
        @Override
        public boolean has(String id){
            return this.path.idPath.contains(id);
        }
        
        @Override
        public boolean has(String...ids){
            Set<String> idd = new HashSet<>();
            Arrays.stream(ids).forEach(i -> idd.add(i) );
            return this.path.idPath.containsAll(idd);
        }
        
        @Override
        public boolean has( Component component ){
            return path.has(component);
        }
        
        @Override
        public boolean has( Component... components ){
            return path.has( components );
        }
        
        /** 
         * just give me the raw diffs to do with what I please
         * 
         * @return 
         */
        public LinkedList<Diff> listDiffs(){
            return this.diffs;
        }        
        
         public _editNode forEach( Consumer<Diff> diffActionFn ){
            listDiffs().forEach(diffActionFn);
            return this;
        }
        
        /**
         * For all instances where the text on the right needs to be added to 
         * the text on the left
         * @param addActionFn
         * @return 
         */
        public _editNode forAdds( Consumer<Diff> addActionFn ){
            listDiffs().stream().filter(d -> d.operation == Operation.INSERT).forEach(addActionFn);
            return this;
        }
        
        /**
         * For all instances where the text on the left is to be removed to 
         * create the text on the right
         * @param removeActionFn
         * @return 
         */
        public _editNode forRemoves( Consumer<Diff> removeActionFn ){
            listDiffs().stream().filter(d -> d.operation == Operation.DELETE).forEach(removeActionFn);
            return this;
        }
        
        /**
         * For all instances where the text is the same between left and right
         * @param retainActionFn
         * @return 
         */
        public _editNode forRetains( Consumer<Diff> retainActionFn ){
            listDiffs().stream().filter(d -> d.operation == Operation.EQUAL).forEach(retainActionFn);
            return this;
        }
        
        @Override
        public String toString(){
            StringBuilder sb = new StringBuilder();
            sb.append("  E ").append( path ).append(System.lineSeparator());          
            return sb.toString();
        }
    }    
}
