package draft.java;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import draft.ObjectDiff;
import draft.ObjectDiff.DiffList;
import static draft.java.Ast.typesEqual;
import draft.java._java.Component;
import draft.java._parameter._parameters;
import draft.java._typeParameter._typeParameters;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import name.fraser.neil.plaintext.diff_match_patch;
import name.fraser.neil.plaintext.diff_match_patch.Diff;

/**
 *
 * @author Eric
 */
public interface _inspect<T> {
    
    public boolean equivalent( T left, T right );
    
    
    default _diffTree diffTree( T left, T right) {
        return diffTree(new _path(), new _diffTree(), left, right );
    }
    
    default _diffTree diffTree( _diffTree dt, T left, T right) {
        return diffTree( new _path(), dt, left, right );
    }
    
    public _diffTree diffTree( _path path, _diffTree dt, T left, T right );
    
    
    public DiffList diff( String path, DiffList dl, T left, T right );
    
    default DiffList diff( T left, T right ){
        DiffList dl = new DiffList();
        return diff( "", dl, left, right);
    }
    
    default DiffList diff(String path, T left, T right ){
        DiffList dl = new DiffList();
        return diff( path, dl, left, right);
    }
    
    
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
        
        public _diffTree forEach( Consumer<_diffNode> _diffNodeFn ){
            this.diffs.forEach(_diffNodeFn);
            return this;
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
                else{ //its a change
                    sb.append("  ~ ").append( d.path.toString() ).append(System.lineSeparator() );
                }
                /*
                if( d.right == null ){
                    sb.append("  + ").append( d.path.toString() ).append(System.lineSeparator() );
                } else if (d.left == null ){
                    sb.append("  - ").append( d.path.toString() ).append(System.lineSeparator() );
                } else{
                    sb.append("  ~ ").append( d.path.toString() ).append(System.lineSeparator() );
                }*/
                });
            return sb.toString();
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
        public boolean has(Component component){
            return this.path.componentPath.contains(component);
        }
        
        public boolean has(Component... component){
            return this.path.has(component);
        }
        
        public Component leafComponent(){
            return this.path.componentPath.get( this.path.size() -1 );
        }
    }
    
    
    public static _enumConstantsInspect INSPECT_ENUM_CONSTANTS = new _enumConstantsInspect();
    
    public static class _enumConstantsInspect implements _inspect<List<_enum._constant>>{

        @Override
        public boolean equivalent( List<_enum._constant> left, List<_enum._constant> right) {
            Set<_enum._constant>ls = new HashSet<>();
            Set<_enum._constant>rs = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            return Objects.equals(ls, rs);
        }

        public _enum._constant sameName( _enum._constant target, Set<_enum._constant> source ){
            Optional<_enum._constant> ec = 
                    source.stream().filter(c-> c.getName().equals(target.getName())).findFirst();
            if( ec.isPresent()){
                return ec.get();
            }
            return null;
        }
        
        @Override
        public _diffTree diffTree( _path path, _diffTree dt, List<_enum._constant> left, List<_enum._constant> right) {
            Set<_enum._constant>ls = new HashSet<>();
            Set<_enum._constant>rs = new HashSet<>();
            Set<_enum._constant>both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            both.addAll(ls);
            both.retainAll(rs);
            
            ls.removeAll(both);
            ls.forEach(f -> {
                _enum._constant cc = sameName( f, rs );
                if( cc != null ){
                    rs.remove( cc );
                    dt.add(path.in(_java.Component.CONSTANT, f.getName()), f, cc);
                } else{
                    dt.add(path.in(_java.Component.CONSTANT, f.getName()), f, null);                    
                }
            });            
            rs.forEach(f -> {
                dt.add( path.in(_java.Component.CONSTANT, f.getName()), null, f);                                    
            });
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, List<_enum._constant> left, List<_enum._constant> right) {
            Set<_enum._constant>ls = new HashSet<>();
            Set<_enum._constant>rs = new HashSet<>();
            Set<_enum._constant>both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            both.addAll(ls);
            both.retainAll(rs);
            
            ls.removeAll(both);
            ls.forEach(f -> {
                _enum._constant cc = sameName( f, rs );
                if( cc != null ){
                    rs.remove( cc );
                    dl.add(path+_java.Component.CONSTANT, f, cc);
                } else{
                    dl.add(path+_java.Component.CONSTANT, f, null);
                }
            });
            
            rs.forEach(f -> {
                dl.add(path+_java.Component.CONSTANT, null,f);                
            });
            return dl;
        }        
    }
    
    
    public static ListImportDeclarationInspect INSPECT_IMPORTS = new ListImportDeclarationInspect(_java.Component.IMPORT.getName() );
    
    public static class ListImportDeclarationInspect implements _inspect<List<ImportDeclaration>>{        

        private final String name;
        
        public ListImportDeclarationInspect(String name){
            this.name = name;
        }
                
        @Override
        public boolean equivalent(List<ImportDeclaration> left, List<ImportDeclaration> right) {
            Set<ImportDeclaration> ls = new HashSet<>();
            Set<ImportDeclaration> rs = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            return Objects.equals( ls, rs);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt, List<ImportDeclaration> left, List<ImportDeclaration> right) {
            Set<ImportDeclaration> ls = new HashSet<>();
            Set<ImportDeclaration> rs = new HashSet<>();
            Set<ImportDeclaration> both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            
            both.addAll( left);
            both.retainAll( right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            
            ls.forEach(c -> dt.add(path.in(_java.Component.IMPORT), c, null) );
            rs.forEach(c -> dt.add(path.in(_java.Component.IMPORT), null, c) );
            
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, List<ImportDeclaration> left, List<ImportDeclaration> right) {
            Set<ImportDeclaration> ls = new HashSet<>();
            Set<ImportDeclaration> rs = new HashSet<>();
            Set<ImportDeclaration> both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            
            both.addAll( left);
            both.retainAll( right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            
            ls.forEach(c -> dl.add(name, c, null) );
            rs.forEach(c -> dl.add(name, null, c) );
            
            return dl;
        }        
    }
    
    public static ListClassOrInterfaceTypeInspect INSPECT_EXTENDS = new ListClassOrInterfaceTypeInspect(_java.Component.EXTENDS);
    
    public static ListClassOrInterfaceTypeInspect INSPECT_IMPLEMENTS = new ListClassOrInterfaceTypeInspect(_java.Component.IMPLEMENTS );
    
    public static class ListClassOrInterfaceTypeInspect implements _inspect<List<ClassOrInterfaceType>>{        

        private final Component component;
        
        public ListClassOrInterfaceTypeInspect(Component component){
            this.component = component;
        }
                
        @Override
        public boolean equivalent(List<ClassOrInterfaceType> left, List<ClassOrInterfaceType> right) {
            Set<ClassOrInterfaceType> ls = new HashSet<>();
            Set<ClassOrInterfaceType> rs = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            return Objects.equals( ls, rs);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt, List<ClassOrInterfaceType> left, List<ClassOrInterfaceType> right) {
            Set<ClassOrInterfaceType> ls = new HashSet<>();
            Set<ClassOrInterfaceType> rs = new HashSet<>();
            Set<ClassOrInterfaceType> both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            
            both.addAll( left);
            both.retainAll( right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            
            ls.forEach(c -> dt.add(path.in(component), c, null) );
            rs.forEach(c -> dt.add(path.in(component), null, c) );
            
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, List<ClassOrInterfaceType> left, List<ClassOrInterfaceType> right) {
            Set<ClassOrInterfaceType> ls = new HashSet<>();
            Set<ClassOrInterfaceType> rs = new HashSet<>();
            Set<ClassOrInterfaceType> both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            
            both.addAll( left);
            both.retainAll( right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            
            ls.forEach(c -> dl.add(component.getName(), c, null) );
            rs.forEach(c -> dl.add(component.getName(), null, c) );
            
            return dl;
        }        
    }
    
    public static _enumConstantInspect INSPECT_ENUM_CONSTANT = new _enumConstantInspect();
    
    public static class _enumConstantInspect implements _inspect<_enum._constant>{        
        
        @Override
        public boolean equivalent(_enum._constant left, _enum._constant right) {
            return Objects.equals(left,right);
        }
        
        
        @Override
        public _diffTree diffTree( _path path, _diffTree dt, _enum._constant left, _enum._constant right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                dt.add( path.in(_java.Component.CONSTANT, right.getName()), null, right);
                return dt;
            }
            if( right == null){
                dt.add( path.in(_java.Component.CONSTANT, left.getName()), left, null );
                return dt;
                //dl.add( path+_java.Component.CONSTANT, left, null);
                //return dl;
            }
            INSPECT_ANNOS.diffTree(path, dt, left.getAnnos(), right.getAnnos());
            INSPECT_JAVADOC.diffTree(path, dt, left.getJavadoc(), right.getJavadoc());
            INSPECT_NAME.diffTree(path, dt, left.getName(), right.getName());
            INSPECT_ARGUMENTS.diffTree(path, dt, left.listArguments(), right.listArguments());
            INSPECT_METHODS.diffTree(path, dt, left.listMethods(), right.listMethods());
            INSPECT_FIELDS.diffTree(path, dt, left.listFields(), right.listFields());            
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl,_enum._constant left, _enum._constant right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.CONSTANT, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.CONSTANT, left, null);
                return dl;
            }
            INSPECT_ANNOS.diff(path, dl, left.getAnnos(), right.getAnnos());
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());
            INSPECT_NAME.diff(path, dl, left.getName(), right.getName());
            INSPECT_ARGUMENTS.diff(path, dl, left.listArguments(), right.listArguments());
            INSPECT_METHODS.diff(path, dl, left.listMethods(), right.listMethods());
            INSPECT_FIELDS.diff(path, dl, left.listFields(), right.listFields());            
            return dl;
        }                
    }
    public static final _typeInspect INSPECT_TYPE = new _typeInspect();
    
    public static class _typeInspect implements _inspect<_type>{

        @Override
        public boolean equivalent(_type left, _type right) {
            return Objects.equals(left, right);
        }

        public Component getComponent( _type t ){
            if( t instanceof _class){
                return Component.CLASS;
            }
            if( t instanceof _interface){
                return Component.INTERFACE;
            }
            if( t instanceof _enum){
                return Component.ENUM;
            }
            return Component.ANNOTATION;            
        }
        
        @Override
        public _diffTree diffTree( _path path, _diffTree dt, _type left, _type right) {
            if(left == null){
                if(right == null){
                    return dt;
                }
                dt.add(path.in(getComponent(right), right.getName()), null, right);
                return dt;
            }
            if( right == null){
                dt.add(path.in(getComponent(left), left.getName()), left, null);
                return dt;
            }
            if( !left.getClass().isAssignableFrom(right.getClass())){
                //on the fence about this
                dt.add(path.in(getComponent(left), left.getName()), left, null);
                dt.add(path.in(getComponent(right), right.getName() ), null, right);
                //dt.add(path.in(Component.NEST), left, null);
                //dt.add(path.in(Component.NEST), null, right);
            }
            if( left instanceof _class ){
                return INSPECT_CLASS.diffTree(path.in(Component.CLASS, left.getName()), dt, (_class)left, (_class)right);
            }
            if( left instanceof _interface ){
                return INSPECT_INTERFACE.diffTree(path.in(Component.INTERFACE, left.getName()), dt, (_interface)left, (_interface)right);
            }
            if( left instanceof _annotation ){
                return INSPECT_ANNOTATION.diffTree(path.in(Component.ANNOTATION, left.getName()),dt, (_annotation)left, (_annotation)right);                
            }
            return INSPECT_ENUM.diffTree(path.in(Component.ENUM, left.getName()),dt,  (_enum)left, (_enum)right);            
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, _type left, _type right) {
            if(left == null){
                if(right == null){
                    return dl;
                }
                dl.add(path, null, right);
                return dl;
            }
            if( right == null){
                dl.add(path, left, null);
                return dl;
            }
            if( !left.getClass().isAssignableFrom(right.getClass())){
                dl.add(path+_java.Component.NEST, left, null);
                dl.add(path+_java.Component.NEST, null, right);
            }
            if( left instanceof _class ){
                INSPECT_CLASS.diff(path, (_class)left, (_class)right);
                return dl;
            }
            if( left instanceof _interface ){
                INSPECT_INTERFACE.diff(path, (_interface)left, (_interface)right);
                return dl;
            }
            if( left instanceof _annotation ){
                INSPECT_ANNOTATION.diff(path, (_annotation)left, (_annotation)right);
                return dl;
            }
            INSPECT_ENUM.diff(path, (_enum)left, (_enum)right);
            return dl;
        }        
    }
    
    public static final _typesInspect INSPECT_NESTS = new _typesInspect();
            
    public static class _typesInspect implements _inspect<List<_type>>{
        
        @Override
        public boolean equivalent( List<_type> left, List<_type> right ){
            Set<_type> ls = new HashSet<>();
            Set<_type> rs = new HashSet<>();
            ls.addAll( left);
            rs.addAll( right);
            return Objects.equals( ls, right);
        }
        
        private static _type sameNameAndType(_type _t, Set<_type> target ){
            Optional<_type> ot = target.stream().filter(t-> t.getName().equals(_t) && 
                    _t.getClass().isAssignableFrom( t.getClass()) ).findFirst();
            if( ot.isPresent() ){
                return ot.get();
            }
            return null;
        }
        
        @Override
        public _diffTree diffTree( _path path, _diffTree dt, List<_type>left, List<_type>right ){
            Set<_type>ls = new HashSet<>();
            Set<_type>rs = new HashSet<>();
            Set<_type>both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            both.addAll(ls);
            both.retainAll(rs);
            
            ls.removeAll(both);
            ls.forEach(f -> {
                _type cc = sameNameAndType( f, rs );
                if( cc != null ){
                    rs.remove( cc );
                    INSPECT_TYPE.diffTree(path.in(_java.Component.NEST),dt, f, cc);
                    //dl.add(path+_java.Component.NEST, f, cc);                    
                } else{
                    dt.add(path.in(_java.Component.NEST), f, null);
                }
            });
            
            rs.forEach(f -> {
                dt.add(path.in(_java.Component.NEST), null,f);                
            });
            return dt;            
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, List<_type>left, List<_type>right ){
            Set<_type>ls = new HashSet<>();
            Set<_type>rs = new HashSet<>();
            Set<_type>both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            both.addAll(ls);
            both.retainAll(rs);
            
            ls.removeAll(both);
            ls.forEach(f -> {
                _type cc = sameNameAndType( f, rs );
                if( cc != null ){
                    rs.remove( cc );
                    INSPECT_TYPE.diff(path+_java.Component.NEST, f, cc);
                    //dl.add(path+_java.Component.NEST, f, cc);                    
                } else{
                    dl.add(path+_java.Component.NEST, f, null);
                }
            });
            
            rs.forEach(f -> {
                dl.add(path+_java.Component.NEST, null,f);                
            });
            return dl;
        }
    }
    
    public static _annotationInspect INSPECT_ANNOTATION = new _annotationInspect();
    
    public static class _annotationInspect implements _inspect<_annotation>{

        @Override
        public boolean equivalent(_annotation left,_annotation right) {
            return Objects.equals(left,right);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt, _annotation left, _annotation right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                return dt.add( path.in(_java.Component.ANNOTATION, right.getName()), null, right);                
            }
            if( right == null){
                return dt.add( path.in(_java.Component.ANNOTATION, left.getName()), left, null);
            }
            INSPECT_PACKAGE_NAME.diffTree(path,dt, left.getPackage(), right.getPackage() );
            INSPECT_IMPORTS.diffTree(path,dt, left.listImports(), right.listImports() );
            INSPECT_ANNOS.diffTree(path, dt, left.getAnnos(), right.getAnnos());                     
            INSPECT_JAVADOC.diffTree(path, dt, left.getJavadoc(), right.getJavadoc());              
            INSPECT_NAME.diffTree(path, dt, left.getName(), right.getName());
            INSPECT_MODIFIERS.diffTree(path, dt, left.getModifiers(), right.getModifiers());            
            INSPECT_FIELDS.diffTree(path, dt, left.listFields(), right.listFields() );
            INSPECT_ANNOTATION_ELEMENTS.diffTree(path, dt, left.listElements(), right.listElements() );
            INSPECT_NESTS.diffTree(path, dt, left.listNests(), right.listNests());
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl,_annotation left, _annotation right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.ANNOTATION, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.ANNOTATION, left, null);
                return dl;
            }
            INSPECT_PACKAGE_NAME.diff(path,dl, left.getPackage(), right.getPackage() );
            INSPECT_IMPORTS.diff(path,dl, left.listImports(), right.listImports() );
            INSPECT_ANNOS.diff(path, dl, left.getAnnos(), right.getAnnos());                     
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());              
            INSPECT_NAME.diff(path, dl, left.getName(), right.getName());
            INSPECT_MODIFIERS.diff(path, dl, left.getModifiers(), right.getModifiers());            
            INSPECT_FIELDS.diff(path, dl, left.listFields(), right.listFields() );
            INSPECT_ANNOTATION_ELEMENTS.diff(path, dl, left.listElements(), right.listElements() );
            INSPECT_NESTS.diff(path, dl, left.listNests(), right.listNests());       
            return dl;
        }
    }
     
    public static _interfaceInspect INSPECT_INTERFACE = new _interfaceInspect();
    
    public static class _interfaceInspect implements _inspect<_interface>{

        @Override
        public boolean equivalent(_interface left, _interface right) {
            return Objects.equals(left,right);
        }

        public _diffTree diffTree( _path path, _diffTree dt, _interface left, _interface right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                return dt.add( path.in(_java.Component.INTERFACE, right.getName()), null, right);
            }
            if( right == null){
                return dt.add( path.in(_java.Component.INTERFACE, left.getName()), left, null);
            }
            INSPECT_PACKAGE_NAME.diffTree(path,dt, left.getPackage(), right.getPackage() );
            INSPECT_IMPORTS.diffTree(path,dt, left.listImports(), right.listImports() );
            INSPECT_ANNOS.diffTree(path, dt, left.getAnnos(), right.getAnnos());          
            INSPECT_EXTENDS.diffTree(path, dt, left.listExtends(), right.listExtends());          
            INSPECT_JAVADOC.diffTree(path, dt, left.getJavadoc(), right.getJavadoc());  
            INSPECT_TYPE_PARAMETERS.diffTree(path, dt, left.getTypeParameters(), right.getTypeParameters());  
            INSPECT_NAME.diffTree(path, dt, left.getName(), right.getName());
            INSPECT_MODIFIERS.diffTree(path, dt, left.getModifiers(), right.getModifiers());
            INSPECT_METHODS.diffTree(path, dt, left.listMethods(), right.listMethods() );
            INSPECT_FIELDS.diffTree(path, dt, left.listFields(), right.listFields() );
            INSPECT_NESTS.diffTree(path, dt, left.listNests(), right.listNests());
            //INSPECT_NESTS            
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl,_interface left, _interface right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.INTERFACE, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.INTERFACE, left, null);
                return dl;
            }
            INSPECT_PACKAGE_NAME.diff(path,dl, left.getPackage(), right.getPackage() );
            INSPECT_IMPORTS.diff(path,dl, left.listImports(), right.listImports() );
            INSPECT_ANNOS.diff(path, dl, left.getAnnos(), right.getAnnos());          
            INSPECT_EXTENDS.diff(path, dl, left.listExtends(), right.listExtends());          
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());  
            INSPECT_TYPE_PARAMETERS.diff(path, dl, left.getTypeParameters(), right.getTypeParameters());  
            INSPECT_NAME.diff(path, dl, left.getName(), right.getName());
            INSPECT_MODIFIERS.diff(path, dl, left.getModifiers(), right.getModifiers());
            INSPECT_METHODS.diff(path, dl, left.listMethods(), right.listMethods() );
            INSPECT_FIELDS.diff(path, dl, left.listFields(), right.listFields() );
            
            INSPECT_NESTS.diff(path, dl, left.listNests(), right.listNests());
            return dl;
        }
    }
    
    public static _enumInspect INSPECT_ENUM = new _enumInspect();
    
    public static class _enumInspect implements _inspect<_enum>{

        @Override
        public boolean equivalent(_enum left, _enum right) {
            return Objects.equals(left,right);
        }

        public _diffTree diffTree( _path path, _diffTree dt, _enum left, _enum right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                return dt.add( path.in(_java.Component.ENUM, right.getName()), null, right);                
            }
            if( right == null){
                return dt.add( path.in(_java.Component.ENUM, left.getName()), left, null );
                //dl.add( path+_java.Component.ENUM, left, null);
                //return dl;
            }
            INSPECT_PACKAGE_NAME.diffTree(path,dt, left.getPackage(), right.getPackage() );
            INSPECT_IMPORTS.diffTree(path,dt, left.listImports(), right.listImports() );
            INSPECT_ANNOS.diffTree(path, dt, left.getAnnos(), right.getAnnos());                               
            INSPECT_IMPLEMENTS.diffTree(path, dt, left.listImplements(), right.listImplements());  
            INSPECT_JAVADOC.diffTree(path, dt, left.getJavadoc(), right.getJavadoc());  
            INSPECT_STATIC_BLOCKS.diffTree(path, dt, left.listStaticBlocks(), right.listStaticBlocks());            
            INSPECT_NAME.diffTree(path, dt, left.getName(), right.getName());
            INSPECT_MODIFIERS.diffTree(path, dt, left.getModifiers(), right.getModifiers());
            INSPECT_CONSTRUCTORS.diffTree(path, dt, left.listConstructors(), right.listConstructors());
            INSPECT_METHODS.diffTree(path, dt, left.listMethods(), right.listMethods() );
            INSPECT_FIELDS.diffTree(path, dt, left.listFields(), right.listFields() );
            INSPECT_ENUM_CONSTANTS.diffTree(path, dt, left.listConstants(), right.listConstants());
            
            INSPECT_NESTS.diffTree(path, dt, left.listNests(), right.listNests());  
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl,_enum left, _enum right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.ENUM, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.ENUM, left, null);
                return dl;
            }
            INSPECT_PACKAGE_NAME.diff(path,dl, left.getPackage(), right.getPackage() );
            INSPECT_IMPORTS.diff(path,dl, left.listImports(), right.listImports() );
            INSPECT_ANNOS.diff(path, dl, left.getAnnos(), right.getAnnos());                               
            INSPECT_IMPLEMENTS.diff(path, dl, left.listImplements(), right.listImplements());  
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());  
            INSPECT_STATIC_BLOCKS.diff(path, dl, left.listStaticBlocks(), right.listStaticBlocks());            
            INSPECT_NAME.diff(path, dl, left.getName(), right.getName());
            INSPECT_MODIFIERS.diff(path, dl, left.getModifiers(), right.getModifiers());
            INSPECT_CONSTRUCTORS.diff(path, dl, left.listConstructors(), right.listConstructors());
            INSPECT_METHODS.diff(path, dl, left.listMethods(), right.listMethods() );
            INSPECT_FIELDS.diff(path, dl, left.listFields(), right.listFields() );
            INSPECT_ENUM_CONSTANTS.diff(path, dl, left.listConstants(), right.listConstants());
            
            INSPECT_NESTS.diff(path, dl, left.listNests(), right.listNests());
            return dl;
        }
    }
    
    public static _classInspect INSPECT_CLASS = new _classInspect();
    
    public static class _classInspect implements _inspect<_class>{

        @Override
        public boolean equivalent(_class left, _class right) {
            return Objects.equals(left,right);
        }

        public _diffTree diffTree( _path path, _diffTree dt, _class left, _class right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                return dt.add( path.in(_java.Component.CLASS, right.getName()), null, right);
            }
            if( right == null){
                return dt.add( path.in(_java.Component.CLASS, left.getName()), left, null);
            }
            INSPECT_PACKAGE_NAME.diffTree(path, dt, left.getPackage(), right.getPackage() );
            INSPECT_IMPORTS.diffTree(path,dt, left.listImports(), right.listImports() );
            INSPECT_ANNOS.diffTree(path, dt, left.getAnnos(), right.getAnnos());          
            INSPECT_EXTENDS.diffTree(path, dt, left.listExtends(), right.listExtends());          
            INSPECT_IMPLEMENTS.diffTree(path, dt, left.listImplements(), right.listImplements());  
            INSPECT_JAVADOC.diffTree(path, dt, left.getJavadoc(), right.getJavadoc());  
            INSPECT_TYPE_PARAMETERS.diffTree(path, dt, left.getTypeParameters(), right.getTypeParameters());  
            INSPECT_STATIC_BLOCKS.diffTree(path, dt, left.listStaticBlocks(), right.listStaticBlocks());            
            INSPECT_NAME.diffTree(path, dt, left.getName(), right.getName());
            INSPECT_MODIFIERS.diffTree(path, dt, left.getModifiers(), right.getModifiers());
            INSPECT_CONSTRUCTORS.diffTree(path, dt, left.listConstructors(), right.listConstructors());
            INSPECT_METHODS.diffTree(path, dt, left.listMethods(), right.listMethods() );
            INSPECT_FIELDS.diffTree(path, dt, left.listFields(), right.listFields() );
            
            INSPECT_NESTS.diffTree(path, dt, left.listNests(), right.listNests());
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl,_class left, _class right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.CLASS, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.CLASS, left, null);
                return dl;
            }
            INSPECT_PACKAGE_NAME.diff(path,dl, left.getPackage(), right.getPackage() );
            INSPECT_IMPORTS.diff(path,dl, left.listImports(), right.listImports() );
            INSPECT_ANNOS.diff(path, dl, left.getAnnos(), right.getAnnos());          
            INSPECT_EXTENDS.diff(path, dl, left.listExtends(), right.listExtends());          
            INSPECT_IMPLEMENTS.diff(path, dl, left.listImplements(), right.listImplements());  
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());  
            INSPECT_TYPE_PARAMETERS.diff(path, dl, left.getTypeParameters(), right.getTypeParameters());  
            INSPECT_STATIC_BLOCKS.diff(path, dl, left.listStaticBlocks(), right.listStaticBlocks());            
            INSPECT_NAME.diff(path, dl, left.getName(), right.getName());
            INSPECT_MODIFIERS.diff(path, dl, left.getModifiers(), right.getModifiers());
            INSPECT_CONSTRUCTORS.diff(path, dl, left.listConstructors(), right.listConstructors());
            INSPECT_METHODS.diff(path, dl, left.listMethods(), right.listMethods() );
            INSPECT_FIELDS.diff(path, dl, left.listFields(), right.listFields() );
            
            INSPECT_NESTS.diff(path, dl, left.listNests(), right.listNests());
            return dl;
        }
    }
    
    
    public static _annotationElementsInspect INSPECT_ANNOTATION_ELEMENTS = new _annotationElementsInspect();
    
    public static class _annotationElementsInspect implements _inspect<List<_annotation._element>>{

        @Override
        public boolean equivalent( List<_annotation._element> left, List<_annotation._element> right) {
            Set<_annotation._element>ls = new HashSet<>();
            Set<_annotation._element>rs = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            return Objects.equals(ls, rs);
        }

        public _annotation._element sameName( _annotation._element target, Set<_annotation._element> source ){
            Optional<_annotation._element> ec = 
                    source.stream().filter(c-> c.getName().equals(target.getName())).findFirst();
            if( ec.isPresent()){
                return ec.get();
            }
            return null;
        }
        
        public _diffTree diffTree( _path path, _diffTree dt, List<_annotation._element> left, List<_annotation._element> right) {
            Set<_annotation._element>ls = new HashSet<>();
            Set<_annotation._element>rs = new HashSet<>();
            Set<_annotation._element>both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            both.addAll(ls);
            both.retainAll(rs);
            
            ls.removeAll(both);
            ls.forEach(f -> {
                _annotation._element cc = sameName( f, rs );
                if( cc != null ){
                    rs.remove( cc );
                    dt.add(path.in(_java.Component.ELEMENT, f.getName()), f, cc);
                } else{
                    dt.add(path.in( _java.Component.ELEMENT,f.getName()), f, null);
                }
            });
            
            rs.forEach(f -> {
                dt.add(path.in(_java.Component.ELEMENT, f.getName()), null,f);                
            });
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, List<_annotation._element> left, List<_annotation._element> right) {
            Set<_annotation._element>ls = new HashSet<>();
            Set<_annotation._element>rs = new HashSet<>();
            Set<_annotation._element>both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            both.addAll(ls);
            both.retainAll(rs);
            
            ls.removeAll(both);
            ls.forEach(f -> {
                _annotation._element cc = sameName( f, rs );
                if( cc != null ){
                    rs.remove( cc );
                    dl.add(path+_java.Component.ELEMENT, f, cc);
                } else{
                    dl.add(path+_java.Component.ELEMENT, f, null);
                }
            });
            
            rs.forEach(f -> {
                dl.add(path+_java.Component.ELEMENT, null,f);                
            });
            return dl;
        }       
    }
    
    
    public static _annotationElementInspect INSPECT_ANNOTATION_ELEMENT = new _annotationElementInspect();
    
    public static class _annotationElementInspect implements _inspect<_annotation._element>{

        @Override
        public boolean equivalent(_annotation._element left, _annotation._element right) {
            return Objects.equals(left,right);
        }

        public _diffTree diffTree( _path path, _diffTree dt, _annotation._element left, _annotation._element right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                return dt.add( path.in( _java.Component.ELEMENT, right.getName()), null, right);
                
            }
            if( right == null){
                return dt.add( path.in( _java.Component.ELEMENT, left.getName()), left, null);                
            }
            INSPECT_NAME.diffTree(path, dt, left.getName(), right.getName());
            INSPECT_TYPE_REF.diffTree(path, dt, left.getType(), right.getType());
            INSPECT_DEFAULT.diffTree(path, dt, left.getDefaultValue(), right.getDefaultValue());
            INSPECT_JAVADOC.diffTree(path, dt, left.getJavadoc(), right.getJavadoc());
            INSPECT_ANNOS.diffTree(path, dt, left.getAnnos(), right.getAnnos());            
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, _annotation._element left, _annotation._element right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.ELEMENT, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.ELEMENT, left, null);
                return dl;
            }
            INSPECT_NAME.diff(path, dl, left.getName(), right.getName());
            INSPECT_TYPE_REF.diff(path, dl, left.getType(), right.getType());
            INSPECT_DEFAULT.diff(path, dl, left.getDefaultValue(), right.getDefaultValue());
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());
            INSPECT_ANNOS.diff(path, dl, left.getAnnos(), right.getAnnos());            
            return dl;
        }
    }
    
    
    public static _fieldsInspect INSPECT_FIELDS = new _fieldsInspect();
    
    public static class _fieldsInspect implements _inspect<List<_field>>{

        @Override
        public boolean equivalent(List<_field> left, List<_field> right) {
            Set<_field> lf = new HashSet<>();
            Set<_field> rf = new HashSet<>();
            lf.addAll(left);
            rf.addAll(right);
            return Objects.equals(lf, rf);
        }

        public _field getFieldNamed(Set<_field> _fs , String name){
            Optional<_field> _f = 
                    _fs.stream().filter(f-> f.getName().equals( name) ).findFirst();
            if( _f.isPresent() ){
                return _f.get();
            }
            return null;
        }
        
        public _diffTree diffTree( _path path, _diffTree dt, List<_field> left, List<_field> right) {
            Set<_field> lf = new HashSet<>();
            Set<_field> rf = new HashSet<>();
            lf.addAll(left);
            rf.addAll(right);
            Set<_field> both = new HashSet<>();
            both.addAll(left);
            both.retainAll(right);
            
            //organize fields that have the same name as edits
            lf.removeAll(both);
            rf.removeAll(both);
            
            lf.forEach(f ->  {
                _field match = getFieldNamed( rf, f.getName() );
                if( match != null ){
                    dt.add(path.in( _java.Component.FIELD, f.getName()), f, match);
                } else{
                    dt.add(path.in(_java.Component.FIELD, f.getName()), f, null);
                }
            });
            
            rf.forEach(f ->  {
                _field match = getFieldNamed( lf, f.getName() );
                if( match != null ){
                    dt.add(path.in( _java.Component.FIELD, f.getName()), match, f);
                } else{
                    dt.add(path.in(_java.Component.FIELD, f.getName()), null, f);
                }
            });
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, List<_field> left, List<_field> right) {
            Set<_field> lf = new HashSet<>();
            Set<_field> rf = new HashSet<>();
            lf.addAll(left);
            rf.addAll(right);
            Set<_field> both = new HashSet<>();
            both.addAll(left);
            both.retainAll(right);
            
            //organize fields that have the same name as edits
            lf.removeAll(both);
            rf.removeAll(both);
            
            lf.forEach(f ->  {
                _field match = getFieldNamed( rf, f.getName() );
                if( match != null ){
                    dl.add(path+_java.Component.FIELD, f, match);
                } else{
                    dl.add(path+_java.Component.FIELD, f, null);
                }
            });
            
            rf.forEach(f ->  {
                _field match = getFieldNamed( lf, f.getName() );
                if( match != null ){
                    dl.add(path+_java.Component.FIELD, match, f);
                } else{
                    dl.add(path+_java.Component.FIELD, null, f);
                }
            });
            return dl;
        }        
    }
    
    public static _fieldInspect INSPECT_FIELD = new _fieldInspect();
    
    public static class _fieldInspect implements _inspect<_field>{

        @Override
        public boolean equivalent(_field left, _field right) {
            return Objects.equals(left,right);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt, _field left, _field right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                return dt.add( path.in(_java.Component.FIELD, right.getName()), null, right);
            }
            if( right == null){
                return dt.add( path.in(_java.Component.FIELD, left.getName()), left, null);
            }
            INSPECT_NAME.diffTree(path, dt, left.getName(), right.getName());
            INSPECT_TYPE_REF.diffTree(path, dt, left.getType(), right.getType());
            INSPECT_MODIFIERS.diffTree(path, dt, left.getModifiers(), right.getModifiers());
            INSPECT_JAVADOC.diffTree(path, dt, left.getJavadoc(), right.getJavadoc());
            INSPECT_ANNOS.diffTree(path, dt, left.getAnnos(), right.getAnnos());
            INSPECT_INIT.diffTree(path, dt, left.getInit(), right.getInit() );
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, _field left, _field right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.FIELD, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.FIELD, left, null);
                return dl;
            }
            INSPECT_NAME.diff(path, dl, left.getName(), right.getName());
            INSPECT_TYPE_REF.diff(path, dl, left.getType(), right.getType());
            INSPECT_MODIFIERS.diff(path, dl, left.getModifiers(), right.getModifiers());
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());
            INSPECT_ANNOS.diff(path, dl, left.getAnnos(), right.getAnnos());
            INSPECT_INIT.diff(path, dl, left.getInit(), right.getInit() );
            return dl;
        }        
    }
    
    public static _staticBlocksInspect INSPECT_STATIC_BLOCKS 
            = new _staticBlocksInspect();
    
    public static class _staticBlocksInspect 
            implements _inspect<List<_staticBlock>> {

        @Override
        public boolean equivalent(List<_staticBlock> left, List<_staticBlock> right) {
            Set<_staticBlock> ls = new HashSet<>();
            Set<_staticBlock> rs = new HashSet<>();
            ls.addAll( left);
            rs.addAll(right);
            
            return Objects.equals(ls, rs);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt, List<_staticBlock> left, List<_staticBlock> right) {
            Set<_staticBlock> ls = new HashSet<>();
            Set<_staticBlock> rs = new HashSet<>();
            Set<_staticBlock> both = new HashSet<>();
            ls.addAll( left);
            rs.addAll(right);
            
            both.addAll(left);
            both.retainAll(right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            
            ls.forEach(s -> dt.add(path.in(_java.Component.STATIC_BLOCK), s, null));
            rs.forEach(s -> dt.add(path.in(_java.Component.STATIC_BLOCK), null, s));
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, List<_staticBlock> left, List<_staticBlock> right) {
            Set<_staticBlock> ls = new HashSet<>();
            Set<_staticBlock> rs = new HashSet<>();
            Set<_staticBlock> both = new HashSet<>();
            ls.addAll( left);
            rs.addAll(right);
            
            both.addAll(left);
            both.retainAll(right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            
            ls.forEach(s -> dl.add(path+_java.Component.STATIC_BLOCK, s, null));
            rs.forEach(s -> dl.add(path+_java.Component.STATIC_BLOCK, null, s));
            return dl;
        }        
    }
    
    public static _staticBlockInspect INSPECT_STATIC_BLOCK 
            = new _staticBlockInspect();
            
    public static class _staticBlockInspect 
            implements _inspect<_staticBlock> {

        @Override
        public boolean equivalent(_staticBlock left, _staticBlock right) {
            return Objects.equals(left, right);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt, _staticBlock left, _staticBlock right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                return dt.add( path.in( _java.Component.STATIC_BLOCK), null, right);
            }
            if( right == null){
                return dt.add( path.in(_java.Component.STATIC_BLOCK), left, null);
            }
            return INSPECT_BODY.diffTree(path, dt, left.getBody(), right.getBody());            
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, _staticBlock left, _staticBlock right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.STATIC_BLOCK, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.STATIC_BLOCK, left, null);
                return dl;
            }
            INSPECT_BODY.diff(path, dl, left.getBody(), right.getBody());            
            return dl;
        }        
    }
    
    public static final _constructorsInspect INSPECT_CONSTRUCTORS = 
            new _constructorsInspect();
    
    public static class _constructorsInspect implements _inspect<List<_constructor>>{

        public static _constructor sameNameAndParameterTypes(_constructor ct, Set<_constructor> lcs ){
            _parameters _pts = ct.getParameters();
            _typeRef[] _trs = new _typeRef[_pts.count()];
            for(int i=0;i<_pts.count();i++){
                _trs[i] = _pts.get(i).getType();
            }
//            _pts.forEach( p -> _trs[i]  );
                    
            Optional<_constructor> oc = 
                lcs.stream().filter(
                    c -> c.getName().equals(ct) 
                            && c.getParameters().hasParametersOfType(_trs)).findFirst();
            if( oc.isPresent() ){
                return oc.get();
            }
            return null;
        }
        
        @Override
        public boolean equivalent(List<_constructor> left, List<_constructor> right) {
            Set<_constructor> ls = new HashSet<>();
            Set<_constructor> rs = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            return Objects.equals( ls, rs );
        }

        
        @Override
        public _diffTree diffTree( _path path, _diffTree dt, List<_constructor> left, List<_constructor> right) {
            Set<_constructor> ls = new HashSet<>();
            Set<_constructor> rs = new HashSet<>();
            Set<_constructor> both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            
            both.addAll(left);
            both.retainAll(right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            ls.forEach(c -> {
                _constructor _rct = sameNameAndParameterTypes(c, rs); 
                if( _rct != null ){
                    rs.remove(_rct);
                    dt.add(path.in( _java.Component.CONSTRUCTOR, _constructorInspect.constructorSignatureDescription(c) ), c, _rct);
                } else{
                    dt.add(path.in( _java.Component.CONSTRUCTOR, _constructorInspect.constructorSignatureDescription(c) ), c, null);
                }
            });
            rs.forEach(c -> {
                dt.add(path.in( _java.Component.CONSTRUCTOR, _constructorInspect.constructorSignatureDescription(c) ), null,c );                
            });
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, List<_constructor> left, List<_constructor> right) {
            Set<_constructor> ls = new HashSet<>();
            Set<_constructor> rs = new HashSet<>();
            Set<_constructor> both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            
            both.addAll(left);
            both.retainAll(right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            ls.forEach(c -> {
                _constructor _rct = sameNameAndParameterTypes(c, rs); 
                if( _rct != null ){
                    rs.remove(_rct);
                    dl.add(path+_java.Component.CONSTRUCTOR, c, _rct);
                } else{
                    dl.add(path+_java.Component.CONSTRUCTOR, c, null);
                }
            });
            rs.forEach(c -> {
                dl.add(path+_java.Component.CONSTRUCTOR, null, c);                
            });
            return dl;
        }        
    }
    
    public static final _constructorInspect INSPECT_CONSTRUCTOR = 
            new _constructorInspect();
    
    public static class _constructorInspect implements _inspect<_constructor>{

        @Override
        public boolean equivalent(_constructor left, _constructor right) {
            return Objects.equals(left, right);
        }
        
        public static String constructorSignatureDescription(_constructor ct){
            StringBuilder sb = new StringBuilder(); 
            
            _parameters _pts = ct.getParameters();
            //_typeRef[] _trs = new _typeRef[_pts.count()];
            sb.append( ct.getName() );
            sb.append( "(");
            for(int i=0;i<_pts.count();i++){
                if( i > 0){
                    sb.append(", ");
                }
                sb.append(_pts.get(i).getType() );
                if(_pts.get(i).isVarArg() ){
                    sb.append("...");
                }
                //_trs[i] = _pts.get(i).getType();
            }
            sb.append(")");
            return sb.toString();
        }
        
        @Override
        public _diffTree diffTree( _path path, _diffTree dt, _constructor left, _constructor right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                return dt.add(path.in(_java.Component.CONSTRUCTOR, constructorSignatureDescription(right)), null, right);
            }
            if( right == null){
                return dt.add(path.in(_java.Component.CONSTRUCTOR, constructorSignatureDescription(left)), left, null);
            }        
            INSPECT_JAVADOC.diffTree(path, dt, left.getJavadoc(), right.getJavadoc());
            INSPECT_ANNOS.diffTree( path, dt, left.getAnnos(), right.getAnnos());
            INSPECT_MODIFIERS.diffTree(path, dt, left.getModifiers(), right.getModifiers());            
            INSPECT_NAME.diffTree( path, dt, left.getName(), right.getName());            
            INSPECT_RECEIVER_PARAMETER.diffTree(path, dt, left.getReceiverParameter(), right.getReceiverParameter());
            INSPECT_PARAMETERS.diffTree(path, dt, left.getParameters(), right.getParameters());
            INSPECT_TYPE_PARAMETERS.diffTree(path, dt, left.getTypeParameters(), right.getTypeParameters());
            INSPECT_THROWS.diffTree( path, dt, left.getThrows(), right.getThrows());            
            INSPECT_BODY.diffTree(path, dt, left.getBody(), right.getBody());            
            return dt;
        }

        @Override
        public DiffList diff(String path, DiffList dl, _constructor left, _constructor right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.CONSTRUCTOR, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.CONSTRUCTOR, left, null);
                return dl;
            }        
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());
            INSPECT_ANNOS.diff( path, dl, left.getAnnos(), right.getAnnos());
            INSPECT_MODIFIERS.diff(path, dl, left.getModifiers(), right.getModifiers());            
            INSPECT_NAME.diff( path, dl, left.getName(), right.getName());            
            INSPECT_RECEIVER_PARAMETER.diff(path, dl, left.getReceiverParameter(), right.getReceiverParameter());
            INSPECT_PARAMETERS.diff(path, dl, left.getParameters(), right.getParameters());
            INSPECT_TYPE_PARAMETERS.diff(path, dl, left.getTypeParameters(), right.getTypeParameters());
            INSPECT_THROWS.diff( path, dl, left.getThrows(), right.getThrows());            
            INSPECT_BODY.diff(path, dl, left.getBody(), right.getBody());            
            return dl;
        }        
    } 
    
    
    public static final _methodsInspect INSPECT_METHODS = new _methodsInspect();
    
    public static class _methodsInspect implements _inspect<List<_method>>{

        static class _matchedMethods{
            _method left;
            _method right;
            
            public _matchedMethods(_method left, _method right){
                this.left = left;
                this.right = right;
            }
        }
        
        @Override
        public boolean equivalent(List<_method> left, List<_method> right) {
            Set<_method>ls = new HashSet<>();
            ls.addAll(left);
            Set<_method>rs = new HashSet<>();
            rs.addAll(right);
            return Objects.equals(ls,rs);            
        }

        public static String describeMethodSignature(_method _m ){
            StringBuilder sb = new StringBuilder();
            sb.append(_m.getName());
            sb.append("(");
            for(int i=0;i< _m.getParameters().count(); i++){
                if( i > 0 ){
                    sb.append(", ");
                }
                sb.append( _m.getParameter(i).getType() );
                if( _m.getParameter(i).isVarArg() ){
                    sb.append("...");
                }
            }
            sb.append(")");
            return sb.toString();
        }
        
        public static _method findSameNameAndParameters(_method tm, Set<_method>targets ){
            Optional<_method> om = targets.stream().filter(m -> m.getName().equals(tm.getName()) 
                    && m.getType().equals(tm.getType()) 
                    && m.getParameters().hasParametersOfType(tm.getParameters().types())).findFirst();
            if( om != null ){
                return om.get();
            }
            return null;
        }
        
        @Override
        public _diffTree diffTree( _path path, _diffTree dt,  List<_method> left, List<_method> right) {
            Set<_method>ls = new HashSet<>();
            ls.addAll(left);
            Set<_method>rs = new HashSet<>();
            rs.addAll(right);
            
            Set<_method> both = new HashSet<>();
            both.addAll(left);
            both.retainAll(right);
            
            ls.removeAll(right);
            rs.removeAll(left);
            
            ls.forEach(m -> {
                
                _method fm = findSameNameAndParameters(m, rs);
                //System.out.println(" LEFT NOT MATCHED "+m+" "+rs);
                if( fm != null ){
                    rs.remove(fm);
                    INSPECT_METHOD.diffTree(path.in(Component.METHOD, describeMethodSignature(m)), dt, m, fm);
                } else{
                    dt.add( path.in(Component.METHOD, describeMethodSignature(m)), m, null );
                }
            });            
            rs.forEach(m -> {
                //System.out.println(" RIGHT NOT MATCHED "+m);
                dt.add( path.in(Component.METHOD, describeMethodSignature(m)), null, m );                
            });                                             
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, List<_method> left, List<_method> right) {
            Set<_method>ls = new HashSet<>();
            ls.addAll(left);
            Set<_method>rs = new HashSet<>();
            rs.addAll(right);
            
            Set<_method> both = new HashSet<>();
            both.addAll(left);
            both.retainAll(right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            
            ls.forEach(m -> {
                _method fm = findSameNameAndParameters(m, rs);
                if( fm != null ){
                    rs.remove(fm);
                    INSPECT_METHOD.diff(path, m, fm);
                } else{
                    dl.add( path, m, null );
                }
            });
            
            rs.forEach(m -> {
                dl.add( path, m, null );                
            });
            
            
            if( both.size() ==  right.size() ){
                return dl;
            }           
            
            return dl;            
        }        
    }
    
    public static final _methodInspect INSPECT_METHOD = new _methodInspect();
    
    public static class _methodInspect implements _inspect<_method>{

        @Override
        public boolean equivalent(_method left, _method right) {
            return Objects.equals(left, right);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt, _method left, _method right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                return dt.add( path.in(_java.Component.METHOD,_methodsInspect.describeMethodSignature(right)), null, right);                
            }
            if( right == null){
                return dt.add( path.in(_java.Component.METHOD,_methodsInspect.describeMethodSignature(left)), left, null);
            }
            INSPECT_JAVADOC.diffTree(path, dt, left.getJavadoc(), right.getJavadoc());
            INSPECT_ANNOS.diffTree( path, dt, left.getAnnos(), right.getAnnos());
            INSPECT_MODIFIERS.diffTree(path, dt, left.getModifiers(), right.getModifiers());
            INSPECT_TYPE_REF.diffTree(path, dt, left.getType(), right.getType());
            INSPECT_NAME.diffTree( path, dt, left.getName(), right.getName());            
            INSPECT_RECEIVER_PARAMETER.diffTree(path, dt, left.getReceiverParameter(), right.getReceiverParameter());
            INSPECT_PARAMETERS.diffTree(path, dt, left.getParameters(), right.getParameters());
            INSPECT_TYPE_PARAMETERS.diffTree(path, dt, left.getTypeParameters(), right.getTypeParameters());
            INSPECT_THROWS.diffTree( path, dt, left.getThrows(), right.getThrows());            
            INSPECT_BODY.diffTree(path, dt, left.getBody(), right.getBody());            
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, _method left, _method right) {
            if( left == null){
                if( right == null){
                    return dl;
                }
                dl.add( path+_java.Component.METHOD, null, right);
                return dl;
            }
            if( right == null){
                dl.add( path+_java.Component.METHOD, left, null);
                return dl;
            }
            INSPECT_JAVADOC.diff(path, dl, left.getJavadoc(), right.getJavadoc());
            INSPECT_ANNOS.diff( path, dl, left.getAnnos(), right.getAnnos());
            INSPECT_MODIFIERS.diff(path, dl, left.getModifiers(), right.getModifiers());
            INSPECT_TYPE_REF.diff(path, dl, left.getType(), right.getType());
            INSPECT_NAME.diff( path, dl, left.getName(), right.getName());            
            INSPECT_RECEIVER_PARAMETER.diff(path, dl, left.getReceiverParameter(), right.getReceiverParameter());
            INSPECT_PARAMETERS.diff(path, dl, left.getParameters(), right.getParameters());
            INSPECT_TYPE_PARAMETERS.diff(path, dl, left.getTypeParameters(), right.getTypeParameters());
            INSPECT_THROWS.diff( path, dl, left.getThrows(), right.getThrows());            
            INSPECT_BODY.diff(path, dl, left.getBody(), right.getBody());            
            return dl;
        }
        
        /**
         * Calculates the similarity among (2) instances 
         * @param left
         * @param right
         * @return 
         */
        public int calcSimularity(_method left, _method right){
            int simularity = 0;
            if( INSPECT_BODY.equivalent(left.getBody(), right.getBody()) ){
                simularity += (1 << 9);
            }
            if( INSPECT_NAME.equivalent(left.getName(), right.getName()) ){
                simularity += (1 << 8);
            }
            if( INSPECT_TYPE_REF.equivalent(left.getType(), right.getType()) ){
                simularity += (1 << 7);
            }
            if( INSPECT_PARAMETERS.equivalent(left.getParameters(), right.getParameters()) ){
                simularity += (1 << 6);
            }
            if( INSPECT_MODIFIERS.equivalent(left.getModifiers(), right.getModifiers()) ){
                simularity += (1 << 5);
            }
            if( INSPECT_ANNOS.equivalent(left.getAnnos(), right.getAnnos()) ){
                simularity += (1 << 4);
            }
            if( INSPECT_THROWS.equivalent(left.getThrows(), right.getThrows()) ){
                simularity += (1 << 3);
            }
            if( INSPECT_TYPE_PARAMETERS.equivalent(left.getTypeParameters(), right.getTypeParameters()) ){
                simularity += (1 << 2);
            }
            if( INSPECT_RECEIVER_PARAMETER.equivalent(left.getReceiverParameter(), right.getReceiverParameter()) ){
                simularity += (1 << 1);
            }
            if( INSPECT_JAVADOC.equivalent(left.getJavadoc(), right.getJavadoc() ) ){
                simularity += (1);
            }
            return simularity;
        }
    }
    
    public static final _bodyInspect INSPECT_BODY =
        new _bodyInspect();

    public static class _bodyInspect implements _inspect<_body>{

        private static final diff_match_patch plainTextDiff = new diff_match_patch();
        
        @Override
        public boolean equivalent(_body left, _body right) {
            return Objects.equals(left, right);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt, _body left, _body right) {
             if(!left.isPresent() ){
                if( ! right.isPresent() ){
                    return dt;
                }
                else{
                    return dt.add(path.in( _java.Component.BODY), left, right);                    
                }
            }
            if( !right.isPresent()) {
                return dt.add(path.in( _java.Component.BODY), left, right);
            }
            String leftSer = left.toString(Ast.PRINT_NO_COMMENTS);
            String rightSer = right.toString(Ast.PRINT_NO_COMMENTS);
            if( !Objects.equals( leftSer, rightSer )){
                //ok. we know at least one diff (other than comments) are in the text
                // lets diff the originals WITH comments
                LinkedList<Diff> diffs = plainTextDiff.diff_main(left.toString(), right.toString());
                _textDiff td = new _textDiff(diffs);
                // NOTE: we treat code DIFFERENTLY than other objects that are diffedbecause 
                // diffs in code can cross object boundaries and it's not as "clean"
                // as simply having members with properties because of the nature of code
                // instead we have a _textDiff which encapsulates the apparent changes
                // the text has to undergo to get from LEFT, to RIGHT
                
                dt.add(path.in(_java.Component.BODY), td, td);                
            }            
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, _body left, _body right) {
            if(!left.isPresent() ){
                if( ! right.isPresent() ){
                    return dl;
                }
                else{
                    dl.add(path+_java.Component.BODY.getName(), left, right);
                    return dl;
                }
            }
            if( !right.isPresent()) {
                dl.add(path+_java.Component.BODY.getName(), left, right);
                return dl;
            }
            String leftSer = left.toString(Ast.PRINT_NO_COMMENTS);
            String rightSer = right.toString(Ast.PRINT_NO_COMMENTS);
            if( !Objects.equals( leftSer, rightSer )){
                //ok. we know at least one diff (other than comments) are in the text
                // lets diff the originals WITH comments
                LinkedList<Diff> diffs = plainTextDiff.diff_main(left.toString(), right.toString());
                _textDiff td = new _textDiff(diffs);
                // NOTE: we treat code DIFFERENTLY than other objects that are diffed because 
                // diffs in code can cross object boundaries and it's not as "clean"
                // as simply having members with properties because of the nature of code
                // instead we have a _textDiff which encapsulates the apparent changes
                // the text has to undergo to get from LEFT, to RIGHT
                
                dl.add(path+_java.Component.BODY.getName(), td, td);                
            }            
            return dl;
        }        
    }   
    
    public static final _parametersInspect INSPECT_PARAMETERS = 
        new _parametersInspect();

    public static class _parametersInspect 
            implements _inspect<_parameters>{

        @Override
        public boolean equivalent(_parameters left, _parameters right) {
            return Objects.equals(left, right);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt, _parameters left, _parameters right) {
            for(int i=0;i<left.count();i++){
                _parameter _l = left.get(i);
                _parameter _r = null;
                if( i < right.count() ){
                    _r = right.get(i);
                }
                if( !Objects.equals(left, right) ){
                    dt.add(path.in(_java.Component.PARAMETER,"["+i+"]"), _l, _r);
                }                
            }
            if( right.count() > left.count() ){
                for(int i=left.count(); i<right.count(); i++){
                    _parameter _r = right.get(i);
                    dt.add(path.in( _java.Component.PARAMETER,"["+i+"]"), null, _r);
                }
            }
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, _parameters left, _parameters right) {
            for(int i=0;i<left.count();i++){
                _parameter _l = left.get(i);
                _parameter _r = null;
                if( i < right.count() ){
                    _r = right.get(i);
                }
                if( !Objects.equals(left, right) ){
                    dl.add(path+_java.Component.PARAMETER+"["+i+"]", _l, _r);
                }                
            }
            if( right.count() > left.count() ){
                for(int i=left.count(); i<right.count(); i++){
                    _parameter _r = right.get(i);
                    dl.add(path+_java.Component.PARAMETER+"["+i+"]", null, _r);
                }
            }
            return dl;
        }        
    }   
    
    public static final _modifiersInspect INSPECT_MODIFIERS = 
        new _modifiersInspect();
    
    public static class _modifiersInspect implements _inspect<_modifiers>{
       
        @Override
        public boolean equivalent(_modifiers left, _modifiers right) {
            return Objects.equals(left, right);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt, _modifiers left, _modifiers right) {
            if( !equivalent( left, right)){
                dt.add(path.in( _java.Component.MODIFIERS), left, right);
            }
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, _modifiers left, _modifiers right) {
            if( !equivalent( left, right)){
                dl.add(path+_java.Component.MODIFIERS.getName(), left, right);
            }
            return dl;
        }        
    }
    
    public static final _javadocInspect INSPECT_JAVADOC = 
        new _javadocInspect();
    
    public static class _javadocInspect implements _inspect<_javadoc>{
       
        @Override
        public boolean equivalent(_javadoc left, _javadoc right) {
            return Objects.equals(left, right);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt,  _javadoc left, _javadoc right) {
            if( !equivalent( left, right)){
                dt.add(path.in( _java.Component.JAVADOC ), left, right);
            }
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, _javadoc left, _javadoc right) {
            if( !equivalent( left, right)){
                dl.add(path+_java.Component.JAVADOC.getName(), left, right);
            }
            return dl;
        }        
    }
    
    public static final ReceiverParameterInspect INSPECT_RECEIVER_PARAMETER = 
        new ReceiverParameterInspect();
    
    public static class ReceiverParameterInspect
        implements _inspect<_receiverParameter> {

        public static final String name = _java.Component.RECEIVER_PARAMETER.getName();
        
        @Override
        public boolean equivalent(_receiverParameter left, _receiverParameter right) {
            return Objects.equals( left, right);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt,  _receiverParameter left, _receiverParameter right) {
            if( !equivalent( left, right )){
                if(left == null){
                    return dt.add(path.in(Component.RECEIVER_PARAMETER), null, right);                    
                }
                if(right == null){
                    return dt.add(path.in(Component.RECEIVER_PARAMETER), left, null);                                        
                }                
                INSPECT_NAME.diffTree(path.in(Component.RECEIVER_PARAMETER), dt, left.getName(), right.getName());
                INSPECT_TYPE_REF.diffTree(path.in(Component.RECEIVER_PARAMETER), dt, left.getType(), right.getType());
                INSPECT_ANNOS.diffTree(path.in(Component.RECEIVER_PARAMETER), dt, left.getAnnos(), right.getAnnos());
            }
            return dt;            
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, _receiverParameter left, _receiverParameter right) {
            if( !equivalent( left, right )){
                if(left == null){
                    dl.add(path+name, null, right);
                    return dl;
                }
                if(right == null){
                    dl.add(path+name, left, null);
                    return dl;
                }                
                INSPECT_NAME.diff(path+name+".", dl, left.getName(), right.getName());
                INSPECT_TYPE_REF.diff(path+name+".", dl, left.getType(), right.getType());
                INSPECT_ANNOS.diff(path+name+".", left.getAnnos(), right.getAnnos());
            }
            return dl;
        }        
    }
    
    
    public static final AnnosInspect INSPECT_ANNOS = 
            new AnnosInspect();
    
    public static class AnnosInspect
        implements _inspect<_anno._annos> {

        String name = _java.Component.ANNOS.getName();
        
        public AnnosInspect(){ 
        }
        
        @Override
        public boolean equivalent(_anno._annos left, _anno._annos right) {            
            return Objects.equals(left, right);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt, _anno._annos left, _anno._annos right) {
            NodeList<AnnotationExpr> laes = left.astAnnNode.getAnnotations();
            NodeList<AnnotationExpr> raes = right.astAnnNode.getAnnotations();
            for( int i = 0; i < laes.size(); i++ ) {
                AnnotationExpr e = (AnnotationExpr)laes.get( i );
                //find a matching annotation in other, if one isnt found, then not equal
                if( !raes.stream().filter( a -> Ast.annotationEqual( e, (AnnotationExpr)a ) ).findFirst().isPresent() ) {
                    dt.add( path.in(Component.ANNO, e.getNameAsString()), e, null);
                }
            }
            
            for( int i = 0; i < raes.size(); i++ ) {
                AnnotationExpr e = (AnnotationExpr)raes.get( i );
                //find a matching annotation in other, if one isnt found, then not equal
                if( !laes.stream().filter( a -> Ast.annotationEqual( e, (AnnotationExpr)a ) ).findFirst().isPresent() ) {
                    dt.add(path.in(Component.ANNO, e.getNameAsString()), null, e);
                }
            }            
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, _anno._annos left, _anno._annos right) {
            NodeList<AnnotationExpr> laes = left.astAnnNode.getAnnotations();
            NodeList<AnnotationExpr> raes = right.astAnnNode.getAnnotations();
            
            for( int i = 0; i < laes.size(); i++ ) {
                AnnotationExpr e = (AnnotationExpr)laes.get( i );
                //find a matching annotation in other, if one isnt found, then not equal
                if( !raes.stream().filter( a -> Ast.annotationEqual( e, (AnnotationExpr)a ) ).findFirst().isPresent() ) {
                    dl.add(path+name, e, null);
                }
            }
            
            for( int i = 0; i < raes.size(); i++ ) {
                AnnotationExpr e = (AnnotationExpr)raes.get( i );
                //find a matching annotation in other, if one isnt found, then not equal
                if( !laes.stream().filter( a -> Ast.annotationEqual( e, (AnnotationExpr)a ) ).findFirst().isPresent() ) {
                    dl.add(path+name, null, e);
                }
            }            
            return dl;
        }        
    }
    
    public static final TypeRefInspect INSPECT_TYPE_REF = 
        new TypeRefInspect();
    
    public static class TypeRefInspect
        implements _inspect<_typeRef> {

        String name = _java.Component.TYPE.getName();
        
        public TypeRefInspect(){ 
        }
        
        @Override
        public boolean equivalent(_typeRef left, _typeRef right) {            
            return Objects.equals(left, right);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt, _typeRef left, _typeRef right) { 
            if( !equivalent(left, right) ){
                dt.add(path.in(Component.TYPE), left, right);
            }            
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, _typeRef left, _typeRef right) {
            if( !equivalent(left, right) ){
                dl.add(path+name, left, right);
            }            
            return dl;
        }        
    }
    
    
    public static final TypeParametersInspect INSPECT_TYPE_PARAMETERS = 
        new TypeParametersInspect();
    
    public static class TypeParametersInspect
        implements _inspect<_typeParameters> {

        String name = _java.Component.TYPE_PARAMETERS.getName();
        
        public TypeParametersInspect(){ 
        }
        @Override
        public boolean equivalent(_typeParameters left, _typeParameters right) {            
            return Objects.equals( left, right );
        }

        
        @Override
        public _diffTree diffTree( _path path, _diffTree dt, _typeParameters left, _typeParameters right) {
            //List<ObjectDiff.Entry> des = new ArrayList<>();
            for(int i=0; i<left.ast().size();i++){
                Type cit = left.ast().get(i);
                
                if( ! right.ast().stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    dt.add(path.in(Component.TYPE_PARAMETER), cit, null);
                    //des.add(new ObjectDiff.Entry( path + name, cit, null) );
                }
            }
            for(int i=0; i<right.ast().size();i++){
                Type cit = right.ast().get(i);
                if( ! left.ast().stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    dt.add(path.in(Component.TYPE_PARAMETER), null, cit);
                }
            }
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, _typeParameters left, _typeParameters right) {
            return diff(path, dl, left.ast(), right.ast());
        }
        
        public boolean equivalent(NodeList<TypeParameter>left, NodeList<TypeParameter> right) {            
            return Ast.typesEqual(left, right);
        }

        public DiffList diff(String path, DiffList dl, NodeList<TypeParameter> left, NodeList<TypeParameter> right) {
            
            List<ObjectDiff.Entry> des = new ArrayList<>();
            for(int i=0; i<left.size();i++){
                Type cit = left.get(i);
                
                if( ! right.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, cit, null) );
                }
            }
            for(int i=0; i<right.size();i++){
                Type cit = right.get(i);
                if( ! left.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, null, cit) );
                }
            }
            return dl.addList(des);
        }        
    }
    
    public static final ThrowsInspect INSPECT_THROWS = 
            new ThrowsInspect();
    
    public static class ThrowsInspect
        implements _inspect<_throws> {

        String name = _java.Component.THROWS.getName();
        
        public ThrowsInspect(){ 
        }
        
        @Override
        public boolean equivalent(_throws left, _throws right) {
            return Objects.equals(left, right);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt, _throws left, _throws right) {
            //List<ObjectDiff.Entry> des = new ArrayList<>();
            for(int i=0; i<left.count();i++){
                ReferenceType cit = left.get(i);
                if( ! right.ast().stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    dt.add( path.in(Component.THROWS), cit, null ); 
                }
            }
            for(int i=0; i<right.count();i++){
                ReferenceType cit = right.get(i);
                if( ! left.ast().stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    dt.add( path.in(Component.THROWS), null, cit ); 
                    //des.add(new ObjectDiff.Entry( path + name, null, cit) );
                }
            }
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, _throws left, _throws right) {
            
            List<ObjectDiff.Entry> des = new ArrayList<>();
            for(int i=0; i<left.count();i++){
                ReferenceType cit = left.get(i);
                if( ! right.ast().stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, cit, null) );
                }
            }
            for(int i=0; i<right.count();i++){
                ReferenceType cit = right.get(i);
                if( ! left.ast().stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, null, cit) );
                }
            }
            return dl.addList(des);
        }        

        public boolean equivalent(NodeList<ReferenceType>left, NodeList<ReferenceType> right) {            
            return Ast.typesEqual(left, right);
        }

        public DiffList diff(String path, DiffList dl, NodeList<ReferenceType> left, NodeList<ReferenceType> right) {
            
            List<ObjectDiff.Entry> des = new ArrayList<>();
            for(int i=0; i<left.size();i++){
                Type cit = left.get(i);
                if( ! right.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, cit, null) );
                }
            }
            for(int i=0; i<right.size();i++){
                Type cit = right.get(i);
                if( ! left.stream().filter( c-> typesEqual(c, cit) ).findFirst().isPresent()){
                    des.add(new ObjectDiff.Entry( path + name, null, cit) );
                }
            }
            return dl.addList(des);
        }        
    }
    
    public static final ArgsInspect INSPECT_ARGUMENTS = 
            new ArgsInspect();
    
    public static class ArgsInspect implements _inspect<List<Expression>>{

        @Override
        public boolean equivalent(List<Expression> left, List<Expression> right) {
            return Objects.equals(left, right);
        }

        @Override
        public _diffTree diffTree( _path path, _diffTree dt, List<Expression> left, List<Expression> right) {
            if(left == null ){
                if(right == null){
                    return dt;
                }
                for(int i=0;i<right.size();i++){
                    dt.add( path.in(Component.ARGUMENT,"["+i+"]"), null, right);
                }
                return dt;
            }
            if( right == null ){
                for(int i=0;i<left.size();i++){
                    dt.add(path.in(Component.ARGUMENT,"["+i+"]"), left, null);
                }
                return dt;
            }            
            return dt;
        }
        
        @Override
        public DiffList diff(String path, DiffList dl, List<Expression> left, List<Expression> right) {
            if(left == null ){
                if(right == null){
                    return dl;
                }
                for(int i=0;i<right.size();i++){
                    dl.add(path+_java.Component.ARGUMENT+"["+i+"]", null, right);
                }
                return dl;
            }
            if( right == null ){
                 for(int i=0;i<left.size();i++){
                    dl.add(path+_java.Component.ARGUMENT+"["+i+"]", left, null);
                }
                return dl;
            }            
            return dl;
        }
        
    }
    public static final ExpressionInspect INSPECT_DEFAULT = new ExpressionInspect(_java.Component.DEFAULT);
    
    public static final ExpressionInspect INSPECT_INIT = new ExpressionInspect(_java.Component.INIT);
    
    public static class ExpressionInspect 
            implements _inspect<Expression>{
        
        public Component component;
        
        public ExpressionInspect( Component component ){
            this.component = component;
        }
        
        @Override
        public boolean equivalent( Expression left, Expression right){
            return Objects.equals(left, right);
        }
        
        
        @Override
        public _diffTree diffTree( _path path, _diffTree dt, Expression left, Expression right ){
            if( !equivalent(left, right) ){                
                return dt.add( path.in( component), left, right);
            }
            return dt;
        }
        
        @Override
        public DiffList diff( String path, DiffList dl, Expression left, Expression right ){
            if( !equivalent(left, right) ){                
                return dl.add(path+component.name, left, right);
            }
            return dl;
        }        
    }
    
    public static final StringInspect INSPECT_PACKAGE_NAME = new StringInspect(_java.Component.PACKAGE_NAME);
    
    public static final StringInspect INSPECT_NAME = new StringInspect(_java.Component.NAME);
    
    public static class StringInspect 
            implements _inspect<String>{
        
        public Component component;
        
        public StringInspect( Component component ){
            this.component = component;
        }
        
        
        @Override
        public boolean equivalent( String left, String right){
            return Objects.equals(left, right);
        }
        
        @Override
        public _diffTree diffTree( _path path, _diffTree dt, String left, String right ){
            if( !equivalent(left, right)){
                return dt.add(path.in(component), left, right);
            }
            return dt;
        }
        
        @Override
        public DiffList diff( String path, DiffList dl, String left, String right ){
            if( !equivalent(left, right) ){                
                return dl.add(path+component.getName(), left, right);
            }
            return dl;
        }
    }
    
    
    /**
     * This represents a Textual Diff, it wraps/hides the 
     * underlying _diff_match_patch nuances and tried to make it
     * 
     * NOTE: we use the Concept LEFT and RIGHT to represent the two separate
     * pieces of text, rather than ASSUME the left is a previous and the right
     * is the current with a Patch Applied.
     * 
     */
    public static class _textDiff{
        
        /* the diff_match_patch derived diffs */
        public final LinkedList<Diff>diffs;
        
        public _textDiff(LinkedList<Diff> diffs ){
            this.diffs = diffs;
        }
        
        /**
         * Return the ENTIRE LEFT document as a String
         * @return 
         */
        public String left(){
            StringBuilder sb = new StringBuilder();
            diffs.forEach(s -> {
                if(s.operation != diff_match_patch.Operation.INSERT){
                    sb.append(s.text);
                }
            });
            return sb.toString();
        }
        
        /** 
         * return the entire RIGHT document as a String
         * 
         * @return the right document
         */
        public String right(){
            StringBuilder sb = new StringBuilder();
            diffs.forEach(s -> {
                if(s.operation != diff_match_patch.Operation.DELETE){
                    sb.append(s.text);
                }
            });
            return sb.toString();
        }
    }
}
