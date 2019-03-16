package draft.java;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.nodeTypes.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.*;
import draft.DraftException;
import draft.Named;
import draft.Text;
import draft.java._model.*;
import java.util.*;

import static draft.java.Ast.*;

/**
 * Translates between AST {@link Node} entities to {@link _model} runtime
 * entities deals with Runtime Java Classes.
 *
 * This attempts to hel[p unify the many AST /_java models and model types so we
 * can convert/ translate between :
 * <UL>
 * <LI>a Plain Text String representation of a Java Method
 * <LI>a runtime Reflective entity (java.lang.reflect.Method)
 * <LI>an AST entity( com.github.javaparser.ast.body.MethodDeclaration)
 * <LI>a draft _java (draft.java._method) representation
 * </UL>
 *
 * @author Eric
 */
public enum _java {
    ;

   /** Map from the _model._node classes to the Ast Node equivalent */
    public static final Map<Class<? extends _node>, Class<? extends Node>> _JAVA_TO_AST_NODE_CLASSES = new HashMap<>();

    static {
        _JAVA_TO_AST_NODE_CLASSES.put(_anno.class, AnnotationExpr.class);
        _JAVA_TO_AST_NODE_CLASSES.put(_annotation._element.class, AnnotationMemberDeclaration.class);
        _JAVA_TO_AST_NODE_CLASSES.put(_enum._constant.class, EnumConstantDeclaration.class);
        _JAVA_TO_AST_NODE_CLASSES.put(_constructor.class, ConstructorDeclaration.class);
        _JAVA_TO_AST_NODE_CLASSES.put(_field.class, VariableDeclarator.class);
        _JAVA_TO_AST_NODE_CLASSES.put(_method.class, MethodDeclaration.class);
        _JAVA_TO_AST_NODE_CLASSES.put(_parameter.class, Parameter.class);
        _JAVA_TO_AST_NODE_CLASSES.put(_receiverParameter.class, ReceiverParameter.class);
        _JAVA_TO_AST_NODE_CLASSES.put(_staticBlock.class, InitializerDeclaration.class);
        _JAVA_TO_AST_NODE_CLASSES.put(_typeParameter.class, TypeParameter.class);
        _JAVA_TO_AST_NODE_CLASSES.put(_typeRef.class, Type.class);

        _JAVA_TO_AST_NODE_CLASSES.put(_type.class, TypeDeclaration.class);
        _JAVA_TO_AST_NODE_CLASSES.put(_annotation.class, AnnotationDeclaration.class);
        _JAVA_TO_AST_NODE_CLASSES.put(_class.class, ClassOrInterfaceDeclaration.class);
        _JAVA_TO_AST_NODE_CLASSES.put(_interface.class, ClassOrInterfaceDeclaration.class);
        _JAVA_TO_AST_NODE_CLASSES.put(_enum.class, EnumDeclaration.class);
    }

    /**
     * Map from the {@link _model._node} classes to the Ast
     * {@link com.github.javaparser.ast.Node} equivalent
     */
    public static final Map<Class<? extends Node>, Class<? extends _node>> AST_NODE_TO_JAVA_CLASSES = new HashMap<>();

    static {
        AST_NODE_TO_JAVA_CLASSES.put(AnnotationExpr.class, _anno.class); //base
        AST_NODE_TO_JAVA_CLASSES.put(NormalAnnotationExpr.class, _anno.class); //impl
        AST_NODE_TO_JAVA_CLASSES.put(MarkerAnnotationExpr.class, _anno.class);
        AST_NODE_TO_JAVA_CLASSES.put(SingleMemberAnnotationExpr.class, _anno.class);

        AST_NODE_TO_JAVA_CLASSES.put(AnnotationMemberDeclaration.class, _annotation._element.class);
        AST_NODE_TO_JAVA_CLASSES.put(EnumConstantDeclaration.class, _enum._constant.class);
        AST_NODE_TO_JAVA_CLASSES.put(ConstructorDeclaration.class, _constructor.class);

        AST_NODE_TO_JAVA_CLASSES.put(VariableDeclarator.class, _field.class);
        AST_NODE_TO_JAVA_CLASSES.put(FieldDeclaration.class, _field.class);

        AST_NODE_TO_JAVA_CLASSES.put(MethodDeclaration.class, _method.class);
        AST_NODE_TO_JAVA_CLASSES.put(Parameter.class, _parameter.class);
        AST_NODE_TO_JAVA_CLASSES.put(ReceiverParameter.class, _receiverParameter.class);
        AST_NODE_TO_JAVA_CLASSES.put(InitializerDeclaration.class, _staticBlock.class);
        AST_NODE_TO_JAVA_CLASSES.put(TypeParameter.class, _typeParameter.class);

        AST_NODE_TO_JAVA_CLASSES.put(Type.class, _typeRef.class);
        AST_NODE_TO_JAVA_CLASSES.put(ArrayType.class, _typeRef.class);
        AST_NODE_TO_JAVA_CLASSES.put(ClassOrInterfaceType.class, _typeRef.class);
        AST_NODE_TO_JAVA_CLASSES.put(IntersectionType.class, _typeRef.class);
        AST_NODE_TO_JAVA_CLASSES.put(PrimitiveType.class, _typeRef.class);
        AST_NODE_TO_JAVA_CLASSES.put(ReferenceType.class, _typeRef.class);
        AST_NODE_TO_JAVA_CLASSES.put(UnionType.class, _typeRef.class);
        AST_NODE_TO_JAVA_CLASSES.put(VarType.class, _typeRef.class);
        AST_NODE_TO_JAVA_CLASSES.put(VoidType.class, _typeRef.class);
        AST_NODE_TO_JAVA_CLASSES.put(WildcardType.class, _typeRef.class);

        AST_NODE_TO_JAVA_CLASSES.put(TypeDeclaration.class, _type.class);
        AST_NODE_TO_JAVA_CLASSES.put(ClassOrInterfaceDeclaration.class, _type.class);
        AST_NODE_TO_JAVA_CLASSES.put(EnumDeclaration.class, _enum.class);
        AST_NODE_TO_JAVA_CLASSES.put(AnnotationDeclaration.class, _annotation.class);

        //AST_NODE_TO_JAVA_CLASSES.put( AnnotationDeclaration.class, _annotation.class );
    }

    private static _node getLogicalParentNode(Node node) {
        if (_JAVA_TO_AST_NODE_CLASSES.containsValue(node.getClass())) {
            return (_node) of(node);
        }
        return getLogicalParentNode(node.getParentNode().get());
    }

    /**
     * Parse and return the appropriate node based on the Node class (the Node
     * class can be a _model, or Ast Node class
     *
     * @param nodeClass the class of the node (implementation class)
     * @param code the code for the AST to _1_build
     * @return the node implementation of the code
     */
    public static Node nodeOf(Class nodeClass, String... code) {
        if (!_model.class.isAssignableFrom(nodeClass)) {
            return Ast.of(nodeClass, code);
        }
        if (_anno.class == nodeClass) {
            return anno(code);
        }
        if (_method.class == nodeClass) {
            return method(code);
        }
        if (_constructor.class == nodeClass) {
            return ctor(code);
        }
        if (_typeRef.class == nodeClass) {
            return typeRef(Text.combine(code).trim());
        }
        if (_staticBlock.class == nodeClass) {
            return staticBlock(code);
        }
        if (_type.class.isAssignableFrom(nodeClass)) {
            //System.out.println( "Parsing TYPE Decl" + nodeClass );
            return typeDeclaration(code);
        }
        if (_parameter.class == nodeClass) {
            return parameter(code);
        }
        if (_receiverParameter.class == nodeClass) {
            return receiverParameter(Text.combine(code));
        }
        if (_field.class == nodeClass) {
            return field(code);
        }
        if (_enum._constant.class == nodeClass) {
            return constant(code);
        }
        if (_annotation._element.class == nodeClass) {
            return annotationMember(code);
        }
        if (_method.class == nodeClass) {
            return method(code);
        }
        throw new DraftException("Could not parse Node " + nodeClass);
    }

    /**
     * Builds the appropriate _model entities based on AST Nodes provided (Note:
     * since there are no logical entities for
     * {@link com.github.javaparser.ast.expr.Expression}, or
     * {@link com.github.javaparser.ast.stmt.Statement} Node implementations,
     * this will fail
     * <PRE>
     * handles:
     * all {@link _type}s:
     * {@link _annotation}, {@link _class}, {@link _enum}, {@link _interface}
     * {@link _anno}
     * {@link _annotation._element}
     * {@link _constructor}
     * {@link _enum._constant}
     * {@link _field}
     * {@link _javadoc}
     * {@link _method}
     * {@link _parameter}
     * {@link _receiverParameter}
     * {@link _staticBlock}
     * {@link _typeParameter}
     * {@link _typeRef}
     * </PRE>
     *
     * @param node the ast node
     * @return the _model entity
     */
    public static _model of(Node node) {
        if (node instanceof AnnotationExpr) {
            return _anno.of((AnnotationExpr) node);
        }
        if (node instanceof AnnotationDeclaration) {
            return _annotation.of((AnnotationDeclaration) node);
        }
        if (node instanceof AnnotationMemberDeclaration) {
            return _annotation._element.of((AnnotationMemberDeclaration) node);
        }
        if (node instanceof ClassOrInterfaceDeclaration) {
            ClassOrInterfaceDeclaration cois = (ClassOrInterfaceDeclaration) node;
            if (cois.isInterface()) {
                return _interface.of(cois);
            }
            return _class.of(cois);
        }
        if (node instanceof ConstructorDeclaration) {
            return _constructor.of((ConstructorDeclaration) node);
        }
        if (node instanceof EnumDeclaration) {
            return _enum.of((EnumDeclaration) node);
        }
        if (node instanceof EnumConstantDeclaration) {
            return _enum._constant.of((EnumConstantDeclaration) node);
        }
        if (node instanceof VariableDeclarator) {
            VariableDeclarator vd = (VariableDeclarator) node;
            //FieldDeclaration fd = (FieldDeclaration)vd.getParentNode().get();
            return _field.of(vd);
        }
        if (node instanceof FieldDeclaration) {
            FieldDeclaration fd = (FieldDeclaration) node;
            if (fd.getVariables().size() > 1) {
                throw new DraftException("Ambiguious node for FieldDeclaration " + fd + "pass in VariableDeclarator instead " + fd);
            }
            return _field.of(fd.getVariable(0));
        }
        if (node instanceof BlockStmt) {
            return _body.of((BlockStmt) node);
        }
        if (node instanceof JavadocComment) {
            JavadocComment jdc = (JavadocComment) node;
            if (jdc.getParentNode().isPresent()) {
                return _javadoc.of((NodeWithJavadoc) jdc.getParentNode().get());
            }
            throw new DraftException("No Parent provided for JavadocComment");
        }
        if (node instanceof MethodDeclaration) {
            MethodDeclaration md = (MethodDeclaration) node;
            return _method.of(md);
        }
        if (node instanceof Parameter) {
            return _parameter.of((Parameter) node);
        }
        if (node instanceof InitializerDeclaration) {
            InitializerDeclaration id = (InitializerDeclaration) node;
            return _staticBlock.of(id);
        }
        if (node instanceof ReceiverParameter) {
            ReceiverParameter rp = (ReceiverParameter) node;
            return _receiverParameter.of(rp);
        }
        if (node instanceof TypeParameter) {
            TypeParameter tp = (TypeParameter) node;
            return _typeParameter.of(tp);
        }
        if (node instanceof Type) {
            return _typeRef.of((Type) node);
        }
        if (node instanceof CompilationUnit) {
            CompilationUnit astRoot = (CompilationUnit) node;
            return _type.of(astRoot);
        }
        throw new DraftException("Unable to create logical entity from " + node);
    }

    /**
     * Determine if two entities are SEMANTICALLY equivalent.
     *
     * NOTE: this is MUCH different than testing AST Nodes for syntactical
     * equality because of
     * <UL>
     * <LI>Inferred Modifiers:
     * <PRE>
     * (i.e. a method with no modifiers on an interface is inferred to be public,
     * but not syntactically equal to an interface with an IMPLICIT marking of public)
     *
     *  (not syntactically equal, but equivalent in meaning)
     * interface i {int p;}  =/= interface i{ public int p;}
     * </PRE>
     * </LI>
     * <LI>Ordering: the order of many things (i.e. thrown exceptions,
     * typeParameters) matters from a readability perspective (we dont want to
     * change the syntactic order in which exceptions are thrown in the code).
     * But the underlying semantics are the same regardless if your throws
     * clause has A, then B or B, then A
     *
     * (not syntactically equal, but equivalent in meaning) void m() throws A,
     * B; =/= void m() throws B, A
     * </LI>
     * <LI>Fully Qualified Types vs "Bare" when comparing types if they have the
     * same name and are from an explicitly different packages, they should not
     * be equivalent. But the following ARE equivalent (specifiying the fully
     * qualified type on one hand should
     *
     * java.util.Map<java.net.URL,String> map; =/= Map<URL,String> map;
     *
     * @param <A>
     *
     * public interface Semantic<A> {
     *
     * /**
     * Are two entities "semantically" equivalent? Note: we need this level
     * difference between equality/equivalency precisely because we often times
     * cannot look at syntactical equality
     *
     * here are a few examples that are syntactically NOT EQUAL but semantically
     * EQUIVALENT
     *
     * (although not syntactically the same, semantically they MEAN the same
     * thing) interface i{ int x(); } interface i{ public int x(); }
     *
     * @param left the first instance
     * @param right the second instance
     * @return true if they are semantically equivalent
     *
     * public boolean equivalent(A left, A right); }
     */
    /**
     * A Way to consistently name things when we construct and deconstruct
     * Components of Java programs (external tools for building & matching &
     * diffing can be more valuable having the opportunity to compare like for
     * like (by componentizing things out and comparing or matching on a part by
     * part basis)
     */
    public enum Component implements Named {
        /**
         * i.e. @Deprecated @NotNull
         */
        ANNOS("annos", _anno._annos.class),
        //annotation
        /**
         * i.e. @Deprecated
         */
        ANNO("anno", _anno.class),
        CLASS("class", _class.class),
        ENUM("enum", _enum.class),
        INTERFACE("interface", _interface.class),
        ANNOTATION("annotation", _annotation.class),
        BODY("body", _body.class),
        MODIFIERS("modifiers", List.class, Modifier.class),
        MODIFIER("modifier", Modifier.class),
        JAVADOC("javadoc", _javadoc.class),
        PARAMETERS("parameters", _parameter._parameters.class),
        //parameter
        PARAMETER("parameter", _parameter.class),
        RECEIVER_PARAMETER("receiverParameter", _receiverParameter.class),
        TYPE_PARAMETERS("typeParameters", _typeParameter._typeParameters.class),
        TYPE_PARAMETER("typeParameter", TypeParameter.class), //_typeParameter.class
        THROWS("throws", _throws.class),
        NAME("name", String.class),
        KEY_VALUES("keyValues", List.class, MemberValuePair.class), //anno
        //KeyValue
        KEY_VALUE("keyValue", MemberValuePair.class), //anno
        VALUE("value", Expression.class), //anno
        PACKAGE_NAME("package", PackageDeclaration.class),
        IMPORTS("imports", List.class, ImportDeclaration.class),
        //IMPORT
        IMPORT("import", ImportDeclaration.class),
        ELEMENTS("elements", List.class, _annotation._element.class), //_annotation
        //ELEMENT
        ELEMENT("element", _annotation._element.class), //annotation
        FIELDS("fields", List.class, _field.class),
        //FIELD
        FIELD("field", _field.class),
        NESTS("nests", List.class, _type.class),
        NEST("nest", _type.class),
        
        TYPE("type", _typeRef.class), //annotation.element
        DEFAULT("default", Expression.class), //_annotation.element

        EXTENDS("extends", List.class, ClassOrInterfaceType.class), //_class, //_interface
        IMPLEMENTS("implements", List.class, ClassOrInterfaceType.class), //_class, _enum
        STATIC_BLOCKS("staticBlocks", List.class, _staticBlock.class), //class, _enum
        STATIC_BLOCK("staticBlocks", _staticBlock.class), //class, _enum
        CONSTRUCTORS("constructors", List.class, _constructor.class), //class, _enum
        //CONSTRUCTOR
        CONSTRUCTOR("constructor", _constructor.class),
        METHODS("methods", List.class, _method.class), //class, _enum, _interface, _enum._constant
        //METHOD
        METHOD("method", _method.class),
        CONSTANTS("constants", List.class, _enum._constant.class),
        //CONSTANT
        CONSTANT("constant", _enum._constant.class), //_enum

        ARGUMENT("argument", Expression.class), //_enum._constant
        ARGUMENTS("arguments", List.class, Expression.class), //_enum._constant

        INIT("init", Expression.class), //field
        FINAL("final", Boolean.class), //_parameter
        VAR_ARG("varArg", Boolean.class), //parameter

        AST_TYPE("astType", Type.class), //typeRef
        ARRAY_LEVEL("arrayLevel", Integer.class), //_typeRef
        ELEMENT_TYPE("elementType", Type.class); //typeRef

        public final String name;
        public final Class implementationClass;
        public final Class elementClass;

        Component(String name, Class implementationClass) {
            this.name = name;
            this.implementationClass = implementationClass;
            this.elementClass = null;
        }

        Component(String name, Class containerClass, Class elementClass) {
            this.name = name;
            this.implementationClass = containerClass;
            this.elementClass = elementClass;
        }

        @Override
        public String toString() {
            return name;
        }

        @Override
        public String getName() {
            return name;
        }

        public static Component of(String name) {
            Optional<Component> op = Arrays.stream(Component.values()).filter(p -> p.name.equals(name)).findFirst();
            if (op.isPresent()) {
                return op.get();
            }
            return null;
        }
        
        /**
         * Returns the appropriate Component based on the _type
         * @param t the type instance
         * @return the Component 
         */
        public static Component getComponent( _type t){
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
    }

    /**
     * Defines the components and identifies the path within a _type to
     * a specific member or property
     * 
     * for example:
     * <PRE>
     * 
     * class[MyClass].name                          : the name of class MyClass
     * interface[I].extends                         : the extends on the interface I
     * enum[Scope].implements                       : the implements on the enum Scope
     * annotation[Retain].field[hops].init          : init of a field hops on the annotation Retain
     * class[MyClass].method[m(int)].parameter[0]   : the first parameter on method m(int) in class MyClass
     * 
     * //if we have nested components it can get interesting (omit Component for brevity) 
     * enum[E].nest.class[inner].method[m()].body   : (the method body on a nested class within an enum)
     * </PRE>
     */
    public static class _path{
            
        public static _path of(Object...pathAsTokens){
            _path _p = new _path();
            for(int i=0;i<pathAsTokens.length;i+=2){
                if(! (pathAsTokens[i] instanceof Component)){
                    throw new DraftException("element ["+i+"] MUST be a Component");
                }
                if( (pathAsTokens.length > i + 1) && pathAsTokens[i+1] instanceof String){
                    _p = _p.in( (Component)pathAsTokens[i], (String)pathAsTokens[i+1]);
                } else{
                    _p = _p.in( (Component)pathAsTokens[i]);
                }                        
            }
            return _p;
        }
        
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
        public List<_java.Component> componentPath;
        
        /**
         * The identifying String of a member component (usually the name of the member) 
         * (i.e. for a _field, _type, _method, _annotation._element, or _enum._constant the name)
         * (for a constructor, the parameter types)
         * 
         * //NOTE: can be empty for non-named components (i.e. EXTENDS, IMPLEMENTS, etc.)
         */
        public List<String> idPath;
        
        /**
         * build a new empty path
         */
        public _path(){
            componentPath = new ArrayList<>();
            idPath = new ArrayList<>();
        }
        
        /**
         * create a new path containing all nodes as the original
         * @param original 
         */
        public _path(_path original){
            componentPath = new ArrayList();
            componentPath.addAll(original.componentPath);
            idPath = new ArrayList();
            idPath.addAll(original.idPath);
        }

        /** 
         * Build and return a new _path that follows the current path 
         * down one component
         * @param component 
         * @return a new _path that has a leaf node at the component
         */
        public _path in( _java.Component component){
            return in( component, "");            
        }
        
        /**
         * How many "nodes" are in the path
         * @return the number of nodes in the path
         */
        public int size(){
            return this.componentPath.size();
        }
        
        /**
         * get the leaf (last component) component in the path
         * @return the last component in the path (null if the path is empty)
         */
        public Component leaf(){
            if( !this.componentPath.isEmpty() ){
                return this.componentPath.get(this.componentPath.size() -1 );
            }
            return null;
        }
        
        /**
         * Gets the last id in the path (note: may be empty string, null if the path is empty)
         * @return the last id of the path or null if path is empty
         */
        public String leafId(){
            if( !this.idPath.isEmpty() ){
                return this.idPath.get(this.idPath.size() -1 );
            }
            return null;
        }
        
        /**
         * does the path at index  have the id provided?
         * @param index the index of the path (0 based)
         * @param id the id
         * @return true if the same, false otherwise
         */
        public boolean is( int index, String id){
            if( index <= this.size() && index >=0 ){
                return this.idPath.get(index).equals(id);         
            }
            return false;
        }
        
        /**
         * does the path at index have the component provided
         * @param index the index from the start of the path (0-based)
         * @param component the component type expected
         * @return true if the component at 
         */
        public boolean is( int index, Component component){
            if( index <= this.size()  && index >=0 ){
                return this.componentPath.get(index).equals(component);                        
            }
            return false;
        }
        
        /**
         * does the path at index have the component and id provided
         * @param index the index from the start of the path (0-based)
         * @param component the component type expected
         * @param id the expected id
         * @return true if the component at index has the component and id provided, false otherwise
         */
        public boolean is( int index, Component component, String id){
            if( index <= this.size()  && index >=0 ){
                return this.componentPath.get(index).equals(component) &&
                    this.idPath.get(index).equals(id);
            }
            return false;
        }
        
        /**
         * Does the leaf part of the path have the provided component and id
         * @param component the component
         * @param id the id
         * @return true if the path has the leaf at component and id
         */
        public boolean isLeaf(Component component, String id){
            return component.equals( leaf() )&& id.equals(leafId());
        }
        
        /**
         * @param component the component
         * @return is the last component in the path this component? 
         */
        public boolean isLeaf( Component component ){
            return component.equals(leaf());
        }
        
        /** 
         * @param id 
         * @return is the last id in the path this id? 
         */
        public boolean isLeafId( String id ){
            return id.equals(leafId());
        }
        
        /**
         * does the path contain this id at any node?
         * @param id 
         * @return true if 
         */
        public boolean has(String id){
            return idPath.contains(id);
        }
        
        /**
         * does the path contain ALL of these ids (in ANY order)
         * @param ids the ids to look for in the path
         * @return true if the path contains all ids
         */
        public boolean has(String...ids){
            Set<String>sids = new HashSet<>();
            Arrays.stream(ids).forEach(i -> sids.add(i));
            return idPath.containsAll(sids);
        }
        
        /**
         * does this path contain all of these components (in ANY order)?
         * @param components 
         * @return true if the path contains all these components in ANY order
         */
        public boolean has(Component... components){  
            Set<Component> s = new HashSet<>();
            Arrays.stream(components).forEach( c -> s.add(c));
            return componentPath.containsAll(s);
        }
        
        /**
         * does the path contain this component (anywhere?)
         * @param component component to look for
         * @return true if the component occurs anywhere in the path
         */
        public boolean has(Component component){
            return componentPath.contains(component);
        }
        
        /**
         * does the path contain a part that has this exact component and id
         * @param component the component we are looking for 
         * @param id the path we are looking for
         * @return true if the path contains part with this component & id, 
         * false otherwise
         */
        public boolean has(Component component, String id){
            for(int i=0;i<size(); i++){
                if( this.componentPath.get(i).equals(component) 
                        && this.idPath.get(i).equals(id)){
                    return true;
                }
            }
            return false;
        }
        
        /** 
         * Build and return a new _path that follows the current path down one component 
         * @param component the next component part
         * @param id the id for the component
         * @return a new _path advanced to the next component/id
         */
        public _path in(_java.Component component, String id){
            _path _p = new _path(this);
            _p.componentPath.add(component);
            _p.idPath.add(id);
            return _p;
        }        
        
        /** 
         * @return the component path as a String 
         */
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

        @Override
        public int hashCode() {
            int hash = 5;
            hash = 79 * hash + Objects.hashCode(this.componentPath);
            hash = 79 * hash + Objects.hashCode(this.idPath);
            return hash;
        }
        
        @Override
        public boolean equals( Object obj ){
            if( obj == null ){
                return false;
            }
            if( this == obj ){
                return true;
            }
            if( !( obj instanceof _path) ){
                return false;
            }
            _path other = (_path)obj;
            
            return Objects.equals(this.componentPath, other.componentPath) &&
                    Objects.equals( this.idPath, other.idPath);
        }
    }    
}
