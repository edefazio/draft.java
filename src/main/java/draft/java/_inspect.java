package draft.java;

import draft.java._java.Component;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import name.fraser.neil.plaintext.diff_match_patch.Diff;
import name.fraser.neil.plaintext.diff_match_patch.Operation;

/**
 *
 * @author Eric
 * @param <T> the type to be inspected
 */
public interface _inspect<T> {
    
    public boolean equivalent( T left, T right );    
    
    default _diffTree diffTree( T left, T right) {
        return diffTree(new _path(), new _diffTree(), left, right );
    }
    
    default _diffTree diffTree( _diffTree dt, T left, T right) {
        return diffTree( new _path(), dt, left, right );
    }
    
    default _diffTree diffTree( _path path, _diffTree dt, T left, T right ){
        return diffTree(_java.INSPECTOR, path, dt, left, right);
    }
    
    public _diffTree diffTree( _java._inspector _inspect, _path path, _diffTree dt, T left, T right);
    
   
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
     * A Tree of Diff Nodes that reference the domain
     * 
     */
    public static class _diffTree{
        
        public List<_diffNode> diffs = new ArrayList<>();
        
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
            return diffs.stream().map(d -> d.path ).collect(Collectors.toList());
        }
        
        /**
         * Return the diff at the path, (or null if no diff exists at this path)
         * @param _p
         * @return 
         */
        public _diffNode at(_path _p){      
            return first( d-> d.path.equals(_p));            
        } 
        
        
        public boolean has(Component component){
            return has( new Component[]{component} );
        }
        
        /**
         * Has a Diff that contains these components
         * 
         * @param components
         * @return 
         */
        public boolean has(Component...components){
           return first(components) != null;
        }
        
        /**
         * for example 
         * 
         * 
         * has( PARAMETER, "0" );
         * 
         * @param component
         * @param ids
         * @return 
         */
        public boolean has(Component component, String... ids ){
            return first( d -> d.has(component) && d.has(ids) ) != null;
        }
        
        /**
         * For each of the matching DiffNodes in the Tree, perform some action
         * @param _diffNodeMatchFn function for matching specific _diffNodes
         * @param _diffNodeActionFn consumer action function on selected _diffNodes
         * @return  the potentially modified) _diffTree
         */
        public _diffTree forEach( Predicate<_diffNode> _diffNodeMatchFn, Consumer<_diffNode> _diffNodeActionFn ){
            list(_diffNodeMatchFn).forEach(_diffNodeActionFn);
            return this;
        }
        
        /**
         * 
         * @param _diffNodeFn
         * @return 
         */
        public _diffTree forEach( Consumer<_diffNode> _diffNodeFn ){
            this.diffs.forEach(_diffNodeFn);
            return this;
        }
        
        public _diffTree forEdits(Consumer<_editNode> _editNodeActionFn){
            listEdits().forEach(_editNodeActionFn);
            return this;
        }
        
        public _diffTree forEdits( Predicate<_editNode> _editNodeMatchFn, Consumer<_editNode> _editNodeActionFn){
            listEdits(_editNodeMatchFn).forEach(_editNodeActionFn);
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
        
        public List<_diffNode> list(){
            return this.diffs;
        } 
        
        public _diffNode first( Component componet ){
            Optional<_diffNode> first = 
                    this.diffs.stream().filter(d -> d.has(componet)).findFirst();
            if( first.isPresent() ){
                return first.get();
            }
            return null;
        }
        
        public _diffNode first( Component... componets ){
            Optional<_diffNode> first = 
                    this.diffs.stream().filter(d -> d.has(componets) ).findFirst();
            if( first.isPresent() ){
                return first.get();
            }
            return null;
        }
        
        public _diffNode first( Predicate<_diffNode> _diffNodeMatchFn ){
            Optional<_diffNode> first = 
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
        public List<_diffNode> list( Component component ){
            return list( d-> d.path.has(component));
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
         * //they DONT have to be in order
         * assertEquals(1, dt.list(Component.ANNO, Component.FIELD).size());
         * 
         * //they only provide one
         * assertEquals(2, dt.list(Component.ANNO).size()); 
         * 
         * @param components
         * @return 
         */
        public List<_diffNode> list( Component...components ){
            return list( d-> d.path.has(components));
        }
        
        public List<_diffNode> list(Predicate<_diffNode> DiffNodeMatchFn ){
            return this.diffs.stream().filter(DiffNodeMatchFn).collect(Collectors.toList());
        }
        
        /**
         * Add a "textual" edit diff between two entities
         * 
         * @param path the path to the entity
         * @param lds the linkedList of diffs
         * @return the diffTree
         */
        public _diffTree addEdit( _path path, LinkedList<Diff> lds ){
            diffs.add( _editNode.of(path, lds) );
            return this;
        }
        
        public _diffTree add( _path path, Object left, Object right ){
            diffs.add( new _diffNode(path, left, right));
            return this;
        }
        
        @Override
        public String toString(){
            if( isEmpty() ){
                return " - no diffs found -";
            }
            StringBuilder sb = new StringBuilder();
            sb.append("(").append( diffs.size()).append(") diffs").append(System.lineSeparator());
            this.diffs.forEach( d -> {
                if( d.isAdd() ){
                    sb.append("  + ").append( d.path.toString() ).append(System.lineSeparator() );
                }
                else if( d.isRemove() ){
                    sb.append("  - ").append( d.path.toString() ).append(System.lineSeparator() );
                }
                else{ //its a change / edit
                    sb.append("  ~ ").append( d.path.toString() ).append(System.lineSeparator() );
                }
            });
            return sb.toString();
        }        
    }
    
    /**
     * A different kind of Node to represent a "diff", which involves looking
     * at the "edit distance" between two textual entities.
     * 
     * This is more in line your typical diff (between (2) files) where 
     * we internally try to figure out "edits" between the code on the left
     * and the code on the right
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
     *  AA       KEEP   AA
     *  - BB     REMOVE BB
     *  CC       KEEP   CC 
     *  + DD     ADD    DD 
     *  - EE     REMOVE EE
     *  FF       KEEP   FF
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
    public static class _editNode extends _diffNode{
        
        public static _editNode of( _path path, LinkedList<Diff>diffs ){
            return new _editNode(path, diffs);
        }
        
        public _editNode forEach( Consumer<Diff> diffActionFn ){
            getTextDiff().forEach(diffActionFn);
            return this;
        }
        
        /**
         * For all instances where the text on the right needs to be added to 
         * the text on the left
         */
        public _editNode forAdds( Consumer<Diff> addActionFn ){
            getTextDiff().stream().filter(d -> d.operation == Operation.INSERT).forEach(addActionFn);
            return this;
        }
        
        /**
         * For all instances where the text on the left is to be removed to 
         * create the text on the right
         * @param removeActionFn
         * @return 
         */
        public _editNode forRemoves( Consumer<Diff> removeActionFn ){
            getTextDiff().stream().filter(d -> d.operation == Operation.DELETE).forEach(removeActionFn);
            return this;
        }
        
        /**
         * For all instances where the text is the same between left and right
         * @param keepActionFn
         * @return 
         */
        public _editNode forSames( Consumer<Diff> keepActionFn ){
            getTextDiff().stream().filter(d -> d.operation == Operation.EQUAL).forEach(keepActionFn);
            return this;
        }
        
        public _editNode(_path path, LinkedList<Diff> diffs) {
            super( path, diffs, diffs);
        }
        
        public LinkedList<Diff> getTextDiff(){
            return (LinkedList<Diff>)left;
        }        
    }
    
    /**
     * Representation of a node within the Diff tree
     * 
     */ 
    public static class _diffNode{
        
        public Object left;
        public Object right;
        public _path path;
        
        /**
         * Is this diff a Textual Diff?
         * @return true if this is a textual diff
         */
        public boolean isTextDiff(){
            return this instanceof _editNode;
        }
        
        /**
         * Return the _textDiffNode implementation of this diff
         * OR throw and exception if this IS NOT a _textDiffNode
         * @return 
         */
        public _editNode asTextDiff(){
            return (_editNode)this;
        }
        
        public _diffNode(_path path, Object left, Object right) {
            this.path = path;
            this.left = left;
            this.right = right;
        }
        
        
        /**
         * Does the delta represent a node being ADDED from left to the right
         * for instance:
         * <PRE>
         * _class _a = _class.of("C");
         * _class _b = _class.of("C").field("int a;");
         * 
         * _diffTree diff = _class.diffTree(_a, _b);
         * //verify the adding of the field "int a" is an Add Diff
         * assertTrue( diff.list(Component.FIELD).get(0).isAdd() );
         * </PRE>
         * @return true if the diffNode represents an ADD of a component
         * proceeding FROM the left model to TO the RIGHT model.
         */
        public boolean isAdd(){
            return left == null;    
        }
        
        /**
         * Does the delta represent a node being ADDED from left to the right
         * for instance:
         * <PRE>
         * _class _a = _class.of("C").field("int a;");
         * _class _b = _class.of("C");
         * 
         * _diffTree diff = _class.diffTree(_a, _b);
         * //verify the adding of the field "int a" is an Add Diff
         * assertTrue( diff.list(Component.FIELD).get(0).isRemove() );
         * </PRE>
         * 
         * @return true if the diffNode represents an REMOVE
         */
        public boolean isRemove(){
            return right == null;
        }
        
        /**
         * the component has been changed from one to another
         * (there is a left and right component to compare)
         * @return 
         */
        public boolean isChange(){
            return left != null && right != null;
        }
        
        /**
         * does the path of the diffNode have the particualr id?
         * @param id the id of the entity("f", "m(String)", parameter[1]
         * @return if the 
         */
        public boolean has(String id){
            return this.path.idPath.contains(id);
        }
        
        public boolean has(String...ids){
            Set<String> idd = new HashSet<>();
            Arrays.stream(ids).forEach(i -> idd.add(i) );
            return this.path.idPath.containsAll(idd);
        }
        
        public boolean has(Component component){
            return this.path.componentPath.contains(component);
        }
        
        public boolean has(Component... components){
            return this.path.has(components);
        }        
    }
    
    /*
    public static _enum._enumConstantsInspect INSPECT_ENUM_CONSTANTS = new _enum._enumConstantsInspect();
    public static _type.ListImportDeclarationInspect INSPECT_IMPORTS = new _type.ListImportDeclarationInspect(_java.Component.IMPORT.getName() );
    public static _type.ListClassOrInterfaceTypeInspect INSPECT_EXTENDS = new _type.ListClassOrInterfaceTypeInspect(_java.Component.EXTENDS);    
    public static _type.ListClassOrInterfaceTypeInspect INSPECT_IMPLEMENTS = new _type.ListClassOrInterfaceTypeInspect(_java.Component.IMPLEMENTS );
    public static _enum._enumConstantInspect INSPECT_ENUM_CONSTANT = new _enum._enumConstantInspect();
    public static final _type._typeInspect INSPECT_TYPE = new _type._typeInspect();
    public static final _type._typesInspect INSPECT_NESTS = new _type._typesInspect();
    public static _annotation._annotationInspect INSPECT_ANNOTATION = new _annotation._annotationInspect();
    public static _interface._interfaceInspect INSPECT_INTERFACE = new _interface._interfaceInspect();
    public static _enum._enumInspect INSPECT_ENUM = new _enum._enumInspect();     
    public static _class._classInspect INSPECT_CLASS = new _class._classInspect();    
    public static _annotation._annotationElementsInspect INSPECT_ANNOTATION_ELEMENTS = new _annotation._annotationElementsInspect();
    public static _annotation._annotationElementInspect INSPECT_ANNOTATION_ELEMENT = new _annotation._annotationElementInspect();
    public static _field._fieldsInspect INSPECT_FIELDS = new _field._fieldsInspect();
    public static _field._fieldInspect INSPECT_FIELD = new _field._fieldInspect();
    
    public static _staticBlock._staticBlocksInspect INSPECT_STATIC_BLOCKS 
            = new _staticBlock._staticBlocksInspect();
    
    public static _staticBlock._staticBlockInspect INSPECT_STATIC_BLOCK 
            = new _staticBlock._staticBlockInspect();
    
    public static final _constructor._constructorsInspect INSPECT_CONSTRUCTORS = 
            new _constructor._constructorsInspect();
    
    public static final _constructor._constructorInspect INSPECT_CONSTRUCTOR = 
            new _constructor._constructorInspect();
    
    public static final _method._methodsInspect INSPECT_METHODS = new _method._methodsInspect();
    
    public static final _method._methodInspect INSPECT_METHOD = new _method._methodInspect();
    
    public static final _body._bodyInspect INSPECT_BODY = new _body._bodyInspect();
    
    public static final _parameter._parametersInspect INSPECT_PARAMETERS = 
        new _parameter._parametersInspect();
    
    public static final _modifiers._modifiersInspect INSPECT_MODIFIERS = 
        new _modifiers._modifiersInspect();
    
    public static final _javadoc._javadocInspect INSPECT_JAVADOC = 
        new _javadoc._javadocInspect();
    
    public static final _receiverParameter._receiverParameterInspect INSPECT_RECEIVER_PARAMETER = 
        new _receiverParameter._receiverParameterInspect();
    
    public static final _anno._annosInspect INSPECT_ANNOS = new _anno._annosInspect();
    
    public static final _typeRef._typeRefInspect INSPECT_TYPE_REF = new _typeRef._typeRefInspect();
    
    public static final _typeParameter._typeParametersInspect INSPECT_TYPE_PARAMETERS = 
        new _typeParameter._typeParametersInspect();
    
    public static final _throws._throwsInspect INSPECT_THROWS = new _throws._throwsInspect();
    
    public static final _enum.ArgsInspect INSPECT_ARGUMENTS = new _enum.ArgsInspect();
    
    public static final _java.ExpressionInspect INSPECT_DEFAULT = new _java.ExpressionInspect(_java.Component.DEFAULT);
    
    public static final _java.ExpressionInspect INSPECT_INIT = new _java.ExpressionInspect(_java.Component.INIT);
    
    
    public static final _java.StringInspect INSPECT_PACKAGE_NAME = new _java.StringInspect(_java.Component.PACKAGE_NAME);
    
    public static final _java.StringInspect INSPECT_NAME = new _java.StringInspect(_java.Component.NAME);

    */
    
}
