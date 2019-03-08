package draft.java;

import draft.java._body._hasBody;
//import draft.java._dif;
//import draft.java._inspect;
//import draft.java._inspect._path;
import draft.java._java._path;
//import draft.java._java;
//import draft.java._java._diffMaster;
//import draft.java._java._inspector;
//import draft.java._model;
import draft.java._model._node;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;
import name.fraser.neil.plaintext.diff_match_patch.Operation;

/**
 *
 * 
 * @author Eric
 * @param <T>
 * @param <R>
 */
public interface _differ<T, R extends _node> {
    
    public <R extends _node> _dif diff(
            _path path, 
            build dt, 
            R leftRoot, 
            R rightRoot, 
            T left, 
            T right);
    
    //launch a diff using the left and right roots as the main roots to diff
    default _dif diff( _path path, build dt, R leftRoot, R rightRoot) {
        return diff( path,dt,leftRoot, rightRoot, (T)leftRoot, (T)rightRoot);
    }
    
     public interface build{
        
        public build node( _delta d );
        
        /*
        default build addRemoveOrChange(_path path, _node leftRoot, _node rightRoot, Object left, Object right ){
            if( left == null) { //its an add (added in RIGHT version)
                return add(path, leftRoot, rightRoot, right);
            }
            if( right == null ){ //its a remove... REMOVED from right version
                return remove(path, leftRoot, rightRoot, left);
            }
            return change(path, leftRoot, rightRoot, left, right);
        }
        
         * Add a "textual" edit diff between two entities
         *
         * @param path the path to the entity
         * @param lds the linkedList of diffs based on the textual diffs between the
         * left and right entities
         * @param _leftRoot the left entities root (i.e if the left entity is a _method, it might be a class containing the method)
         * @param _rightRoot the right entities root (i.e if the left entity is a _method, it might be a interface containing the method)
         * @return the diffTree
        
        public build edit(_path path,_hasBody _leftRoot,_hasBody _rightRoot, LinkedList<diff_match_patch.Diff> lds);
        
        public build remove(_path path, _node leftRoot, _node rightRoot, Object remove );
        
        public build add(_path path, _node leftRoot, _node rightRoot, Object add );
        
        public build change(_path path, _node leftRoot, _node rightRoot, Object original, Object changed );
        */
    }
     
    
    /**
     * collection of diff _nodes that represent the deep differences between
     * two domain objects (two _class es, _methods, _fields)
     */
    public static class _mydiff implements _dif, build {
        
        /** the underlying diffs collected */
        public final List<_delta> diffs;
        
        public _mydiff( ){
            diffs = new ArrayList<>();
        }
        
        public _mydiff( List<_delta> diffs ){
            this.diffs = diffs;
        }
        
        public List<_delta> list(){
            return diffs;
        }
        
        @Override
        public String toString(){
            if( list().isEmpty() ){
                return "-- (0) differences --";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("(").append( diffs.size()).append(") diffs").append(System.lineSeparator());
            this.diffs.forEach( d-> sb.append(d) );            
            return sb.toString();
        }        
        
        public build node( _delta d ){
            diffs.add( d);
            return this;
        }
        
        /**
         * Add a "textual" edit diff between two entities
         *
         * @param path the path to the entity
         * @param lds the linkedList of diffs based on the textual diffs between the
         * left and right entities
         * @param _leftRoot the left entities root (i.e if the left entity is a _method, it might be a class containing the method)
         * @param _rightRoot the right entities root (i.e if the left entity is a _method, it might be a interface containing the method)
         * @return the diffTree
         
        public build edit(_path path,_hasBody _leftRoot,_hasBody _rightRoot, LinkedList<diff_match_patch.Diff> lds) {            
            diffs.add( new _editNode(path, _leftRoot, _rightRoot, lds) );            
            return this;
        }
        
        public build remove(_path path, _node leftRoot, _node rightRoot, Object remove ){
            diffs.add( new _removeNode(path, leftRoot, rightRoot, remove) );
            return this;
        }
        
        public build add(_path path, _node leftRoot, _node rightRoot, Object add ){
            diffs.add( new _addNode(path, leftRoot, rightRoot, add) );
            return this;
        }
        
        public build change(_path path, _node leftRoot, _node rightRoot, Object original, Object changed ){
            diffs.add( new _changeNode(path, leftRoot, rightRoot, original, changed) );
            return this;
        }
        * */
    }    
    
    public interface _remove<T>{
         /** The entity in the LEFT, not in RIGHT */
        T removed();
        
        /** update the value (on LEFT and RIGHT) to be null */
        void keepRight();
        
        /** update the value to be null (on LEFT and RIGHT) */
        void keepLeft();
    }
    
    public interface _add<T>{
        /** The entity added in the RIGHT, not in LEFT */
        T added();
        
        /** keep the value of the right side ( the added value is added to left) */
        void keepRight();
        
        /** update the value to be null (on LEFT and RIGHT) */
        void keepLeft();
    }
    
    /**
     * A change between the left and right
     * @param <T> 
     */
    public interface _change<T>{
        
        T left();
        
        T right();
        
        void keepLeft();
        
        void keepRight();
    }
    
    /**
     * A difference between the "left" and "right" entities
     * denoting the path to identify the part
     * and other information relevant to the nature of the difference
     * @param <R>
     */
    public interface _delta <R extends _model>{
        
        R leftRoot();
        
        R rightRoot();
        
        void keepLeft();
        
        void keepRight();
        
        /** 
         * @return the path that marks the component types 
         * (METHOD, FIELD, PARAMETER, NEST, etc.)
         * and identifiers
         * ( "0", "m(String)") 
         * that are traversed to reach the diff node
         */ 
        _path path();
        
        default boolean isAdd(){
            return this instanceof _add;
        }
        
        default boolean isRemove(){
            return this instanceof _remove;
        }
        
        default boolean isChange(){
            return this instanceof _change;
        }
        
        default boolean isEdit(){
            return this instanceof _editNode;
        }
        
        default _add asAdd(){
            return (_add)this;
        }
        
        default _remove asRemove(){
            return (_remove)this;
        }
        
        default _change asChange(){
            return (_change)this;
        }
        
        default _editNode asEdit(){
            return (_editNode)this;
        }
        
        /**
         * is the last part of the path at this Component with this id:
         * i.e. examples
         * <PRE>
         * EXTENDS, ""             //the Extends (no id)
         * IMPLEMENTS, ""          //implements (no id)
         * PARAMETER, "0"          //the first parameter
         * FIELD, "f"              //the field named f
         * METHOD, m(String)       //the method "m" with the String argument
         * CONSTRUCTOR, c(int,int) //the constructor named c with int args
         * </PRE>
         * @param component the component
         * @param id the identifying features of this path
         * @return true if this is the last part of the path 
         */
        default boolean at( _java.Component component, String id ){
            return path().isLeaf(component) && path().isLeafId(id);
        }
        
        /**
         * Is the underlying leaf level component where the diff occurs this 
         * specific component? (i.e. METHOD, PARAMETER, FIELD)
         * @param component the expected leaf component
         * @return true if this node has a path ending with this component
         */
        default boolean at( _java.Component component ){
            return path().isLeaf(component);
        }
        
        /**
         * Is the underlying leaf level id where the diff occurs 
         * have an id that matches this id?
         * @param id the expected leaf id
         * @return true if this node has a path ending with this component
         */
        default boolean at( String id){
            return path().isLeafId(id);
        }
        
        default boolean at( _path path ){
            return path().equals(path);
        }        
    }
    
    public static class _editNode implements _delta<_hasBody>{
        final _hasBody _leftRoot;
        final _hasBody _rightRoot;
        final LinkedList<Diff> diffs;
        final _path path;
        
        public _editNode( _path path, _hasBody _leftRoot, _hasBody _rightRoot, LinkedList<Diff> diffs ){
            this._leftRoot = _leftRoot;
            this._rightRoot = _rightRoot;
            this.path = path;
            this.diffs = diffs;
        }
        
         @Override
        public _hasBody leftRoot(){
            return _leftRoot;
        }
        
        @Override
        public _hasBody rightRoot(){
            return _rightRoot;
        }
        
        @Override
        public _path path(){
            return path;
        }
        
        public LinkedList<Diff> listDiffs(){
            return this.diffs;
        }
        
        @Override
        public String toString(){
            return "  E " + path.toString() + System.lineSeparator();
        }
        
        
        public _editNode forEach( Consumer<Diff> diffActionFn ){
            listDiffs().forEach(diffActionFn);
            return this;
        }
        
        public void keepLeft(){
            StringBuilder sb = new StringBuilder();
            forEach( d -> {
                //DELETE, INSERT, EQUAL
                if( d.operation == Operation.EQUAL || d.operation == Operation.DELETE ){
                    sb.append(d.text);
                }
            });
            this.leftRoot().setBody(sb.toString());
            this.rightRoot().setBody(sb.toString());
        }
        
        public void keepRight(){
            StringBuilder sb = new StringBuilder();
            forEach( d -> {
                //DELETE, INSERT, EQUAL
                if( d.operation == Operation.EQUAL || d.operation == Operation.INSERT ){
                    sb.append(d.text);
                }
            });
            this.leftRoot().setBody(sb.toString());
            this.rightRoot().setBody(sb.toString());
        }
        
        /**
         * For all instances where the text on the right needs to be added to 
         * the text on the left
         * @param addActionFn
         * @return 
         */
        public _editNode forAdds( Consumer<Diff> addActionFn ){
            listDiffs().stream().filter(d -> d.operation == diff_match_patch.Operation.INSERT).forEach(addActionFn);
            return this;
        }
        
        /**
         * For all instances where the text on the left is to be removed to 
         * create the text on the right
         * @param removeActionFn
         * @return 
         */
        public _editNode forRemoves( Consumer<Diff> removeActionFn ){
            listDiffs().stream().filter(d -> d.operation == diff_match_patch.Operation.DELETE).forEach(removeActionFn);
            return this;
        }
        
        /**
         * For all instances where the text is the same between left and right
         * @param retainActionFn
         * @return 
         */
        public _editNode forRetains( Consumer<Diff> retainActionFn ){
            listDiffs().stream().filter(d -> d.operation == diff_match_patch.Operation.EQUAL).forEach(retainActionFn);
            return this;
        }
    }
    
    /**
     * A node that is NOT found on the left but is found on the right
     * (it has been added in transitioning between the left and right)
     * @param <R>
     
    public static  class _addNode<R extends _node> implements _delta<R> {
        
        R _leftRoot;
        R _rightRoot;
         entity that is ABSENT on the leftRoot and present on the rightRoot (added from left to right) 
        Object add;
        _path path;
        
        public _addNode( _path path, R _leftRoot, R _rightRoot, Object added ){
            this._leftRoot = _leftRoot;
            this._rightRoot = _rightRoot;
            this.path = path;
            this.add = added;
        }
        
        @Override
        public R leftRoot(){
            return _leftRoot;
        }
        
        @Override
        public R rightRoot(){
            return _rightRoot;
        }
        
        @Override
        public _path path(){
            return path;
        }
        
        public Object getAdd(){
            return this.add;
        }
        
        @Override
        public String toString(){
            return "  + " + path.toString() + System.lineSeparator();
        }
    }
    
    */
    
     /**
     * A node that is NOT found on the left but is found on the right
     * (it has been added in transitioning between the left and right)
     * @param <R>
    
    public static class _removeNode<R extends _node> implements _delta<R> {
        
        R _leftRoot;
        R _rightRoot;
         node that is on the left, but removed on the right
        Object remove; 
        _path path;
        
        public _removeNode( _path path, R _leftRoot, R _rightRoot, Object removed ){
            this._leftRoot = _leftRoot;
            this._rightRoot = _rightRoot;
            this.path = path;
            this.remove = removed;
        }
        
        @Override
        public R leftRoot(){
            return _leftRoot;
        }
        
        @Override
        public R rightRoot(){
            return _rightRoot;
        }
        
        @Override
        public _path path(){
            return path;
        }
        
        public Object getRemove(){
            return this.remove;
        }
        
        @Override
        public String toString(){
            return "  - " + path.toString() + System.lineSeparator();
        }
    }
    */ 
    
    /**
     * A node that is NOT found on the left but is found on the right
     * (it has been added in transitioning between the left and right)
     * R 
     
    public static class _changeNode<R extends _node> implements _delta<R> {
        
        R _leftRoot;
        R _rightRoot;
         node that is on the left, but removed on the right
        Object original; 
        Object changed;
        _path path;
        
        public _changeNode( _path path, R _leftRoot, R _rightRoot, Object original, Object changed ){
            this._leftRoot = _leftRoot;
            this._rightRoot = _rightRoot;
            this.path = path;
            this.original = original;
            this.changed = changed;
        }
        
        @Override
        public R leftRoot(){
            return _leftRoot;
        }
        
        @Override
        public R rightRoot(){
            return _rightRoot;
        }
        
        @Override
        public _path path(){
            return path;
        }
        
        public Object getOriginal(){
            return this.original;
        }
        
        public Object getChanged(){
            return this.changed;
        }
        
        @Override
        public String toString(){
            return "  ~ " + path.toString() + System.lineSeparator();
        }
    }
    * */
    
   
    
    /**
     * API for collecting to a diffs when done, just call compile() which
     * returns the _diff
     *
     * Builds and returns a list of diff deltas
     */
    public static class builder implements build {
        //what about MANY different Comparators for the diffs
        // i.e. which diff roots (on left or right) occurr before
        // the others (like reading the Range of the source code of tghe roots)
    
        
        //
        List<_delta> diffs = new ArrayList<>();
            
        public builder node(_delta d ){
            diffs.add( d);
            return this;
        }
        
        /**
         * Add a "textual" edit diff between two entities
         *
         * @param path the path to the entity
         * @param lds the linkedList of diffs based on the textual diffs between the
         * left and right entities
         * @param _leftRoot the left entities root (i.e if the left entity is a _method, it might be a class containing the method)
         * @param _rightRoot the right entities root (i.e if the left entity is a _method, it might be a interface containing the method)
         * @return the diffTree
         
        public builder edit(_path path,_hasBody _leftRoot,_hasBody _rightRoot, LinkedList<diff_match_patch.Diff> lds) {            
            diffs.add( new _editNode(path, _leftRoot, _rightRoot, lds) );            
            return this;
        }
        
        public builder remove(_path path, _node leftRoot, _node rightRoot, Object remove ){
            diffs.add( new _removeNode(path, leftRoot, rightRoot, remove) );
            return this;
        }
        
        public builder add(_path path, _node leftRoot, _node rightRoot, Object add ){
            diffs.add( new _addNode(path, leftRoot, rightRoot, add) );
            return this;
        }
        
        public builder change(_path path, _node leftRoot, _node rightRoot, Object original, Object changed ){
            diffs.add( new _changeNode(path, leftRoot, rightRoot, original, changed) );
            return this;
        }        
        
        public _mydiff compile(){
            return new _mydiff( this.diffs );
        }
        */ 
    }    
    
    
    /**
     * Both signifies a delta and provides a means to 
     * commit (via right()) 
     * or rollback( via left())
     */
    public static class _change_type 
            implements _differ._delta, _differ._change<_typeRef>{
        _path path;
        _model._namedType left;
        _model._namedType right;
        _typeRef leftType;
        _typeRef rightType;
        
        public _change_type(_path _p, _model._namedType left, _model._namedType right ){
            this.path = _p;
            this.left = left;
            this.leftType = left.getType().copy();
            this.right = right;
            this.rightType = right.getType().copy();            
        }
        
        public void keepLeft(){
            left.type(leftType);
            right.type(leftType);
        }
        
        public void keepRight(){
            left.type(rightType);
            right.type(rightType);
        }
        
        public _typeRef left(){
            return leftType;
        }
        
        public _typeRef right(){
            return rightType;
        }
        
        @Override
        public _model leftRoot() {
            return left;
        }

        @Override
        public _model rightRoot() {
            return right;
        }

        @Override
        public _path path() {
            return path;
        }
        
        @Override
        public String toString(){
            return "   ~ "+path;
        }
    }
    
    public static class _changeName implements _differ._delta, _differ._change<String> {
        _model._named left;
        _model._named right;
        String leftName;
        String rightName;
        _path path;
        
         public _changeName(_path _p, _model._named left, _model._named right ){
            this.path = _p;
            this.left = left;
            this.leftName = left.getName();
            this.right = right;
            this.rightName = right.getName();            
        }
         
        @Override
        public void keepLeft(){
            left.name(leftName);
            right.name(leftName);
        }
        
        @Override
        public void keepRight(){
            left.name(rightName);
            right.name(leftName);
        }
        
        @Override
        public String left(){
            return leftName;
        }
        
        public String right(){
            return rightName;
        }
        
        
        @Override
        public _model leftRoot() {
            return left;
        }

        @Override
        public _model rightRoot() {
            return right;
        }

        @Override
        public _path path() {
            return path;
        }
        
        @Override
        public String toString(){
            return "   ~ "+path;
        }
    } 
}
