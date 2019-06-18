package draft.java;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import com.github.javaparser.ast.nodeTypes.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.*;
import draft.DraftException;
import draft.Text;
import draft.java._model.*;
import java.util.*;

import static draft.java.Ast.*;
import draft.java._anno._annos;
import draft.java._anno._hasAnnos;
import draft.java._annotation._element;
import draft.java._body._hasBody;
import draft.java._constructor._hasConstructors;
import draft.java._enum._constant;
import draft.java._import._imports;
import draft.java._javadoc._hasJavadoc;
import draft.java._method._hasMethods;
import draft.java._modifiers.*;
import draft.java._receiverParameter._hasReceiverParameter;
import draft.java._staticBlock._hasStaticBlocks;
import draft.java._throws._hasThrows;
import draft.java._type._hasExtends;
import draft.java._type._hasImplements;
import draft.java._typeParameter._typeParameters;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    /* 
        Easy access to the draft _java classes and interfaces that represent
        entities... his allows convenient autocomplete when you do a Walk.in
        of Walk.list, etc.
        <PRE>
        Walk.list( _c, _java.THROWS );
        Walk.list( _c, _java.THROWS );
        </PRE>
        
        to make it similar (in feel) to Ast.* :
        Walk.in( _c, Ast.INITIALIZER_DECLARATION, 
            id-> id.getBody().add(Stmt.of("System.out.println(1);"));
        Walk.list( _m, Ast.RETURN_STMT );
      */    
        
    public static final Class<_model> MODEL = _model.class;
    public static final Class<_member> MEMBER = _member.class;
    public static final Class<_node> NODE = _node.class;
    public static final Class<_named> NAMED = _named.class;
    public static final Class<_namedType> NAMED_TYPE = _namedType.class;

    public static final Class<_type> TYPE = _type.class;

    public static final Class<_class> CLASS = _class.class;
    public static final Class<_enum> ENUM = _enum.class;
    public static final Class<_interface> INTERFACE = _interface.class;
    public static final Class<_annotation> ANNOTATION = _annotation.class;

    public static final Class<_method> METHOD = _method.class;
    public static final Class<_field> FIELD = _field.class;
    public static final Class<_constructor> CONSTRUCTOR = _constructor.class;

    /**
     * ENUM constant i.e. enum E { CONSTANT; }
     */
    public static final Class<_constant> CONSTANT = _constant.class;

    /**
     * Annotation Element i.e. @interface A{ int element(); }
     */
    public static final Class<_element> ELEMENT = _element.class;

    public static final Class<_body> BODY = _body.class;
    public static final Class<_anno> ANNO = _anno.class;
    public static final Class<_annos> ANNOS = _annos.class;
    public static final Class<_import> IMPORT = _import.class;
    public static final Class<_imports> IMPORTS = _imports.class;
    public static final Class<_javadoc> JAVADOC = _javadoc.class;
    public static final Class<_modifiers> MODIFIERS = _modifiers.class;
    public static final Class<_parameter> PARAMETER = _parameter.class;
    public static final Class<_parameter._parameters> PARAMETERS = _parameter._parameters.class;
    public static final Class<_typeParameter> TYPE_PARAMETER = _typeParameter.class;
    public static final Class<_typeParameters> TYPE_PARAMETERS = _typeParameters.class;
    public static final Class<_receiverParameter> RECEIVER_PARAMETER = _receiverParameter.class;
    public static final Class<_staticBlock> STATIC_BLOCK = _staticBlock.class;
    public static final Class<_throws> THROWS = _throws.class;
    public static final Class<_typeRef> TYPEREF = _typeRef.class;

    public static final Class<_hasThrows> HAS_THROWS = _hasThrows.class;
    public static final Class<_hasBody> HAS_BODY = _hasBody.class;
    public static final Class<_hasAnnos> HAS_ANNOS = _hasAnnos.class;
    public static final Class<_hasConstructors> HAS_CONSTRUCTORS = _hasConstructors.class;
    public static final Class<_hasJavadoc> HAS_JAVADOC = _hasJavadoc.class;
    public static final Class<_hasMethods> HAS_METHODS = _hasMethods.class;
    public static final Class<_hasModifiers> HAS_MODIFIERS = _hasModifiers.class;
    public static final Class<_parameter._hasParameters> HAS_PARAMETERS = _parameter._hasParameters.class;
    public static final Class<_hasReceiverParameter> HAS_RECEIVER_PARAMETER = _hasReceiverParameter.class;
    public static final Class<_hasStaticBlocks> HAS_STATIC_BLOCKS = _hasStaticBlocks.class;
    public static final Class<_hasExtends> HAS_EXTENDS = _hasExtends.class;
    public static final Class<_hasImplements> HAS_IMPLEMENTS = _hasImplements.class;

    public static final Class<_hasFinal> HAS_FINAL = _hasFinal.class;
    public static final Class<_hasAbstract> HAS_ABSTRACT = _hasAbstract.class;
    public static final Class<_hasNative> HAS_NATIVE = _hasNative.class;
    public static final Class<_hasStatic> HAS_STATIC = _hasStatic.class;
    public static final Class<_hasStrictFp> HAS_STRICTFP = _hasStrictFp.class;
    public static final Class<_hasSynchronized> HAS_SYNCHRONIZED = _hasSynchronized.class;
    public static final Class<_hasTransient> HAS_TRANSIENT = _hasTransient.class;
    public static final Class<_hasVolatile> HAS_VOLATILE = _hasVolatile.class;

    /**
     * Map from the _model._node classes to the Ast Node equivalent
     */
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
    }

    public static _code _codeOf( CompilationUnit astRoot ){
        if (astRoot.getModule().isPresent()) {
            return _moduleInfo.of(astRoot);
        }
        if (astRoot.getTypes().isEmpty()) {
            return _packageInfo.of(astRoot);
        }
        if( astRoot.getTypes().size() == 1 ){ //only one type
            return _type.of(astRoot, astRoot.getTypes().get(0));
        }        
        //the first public type
        Optional<TypeDeclaration<?>> otd = 
            astRoot.getTypes().stream().filter(t-> t.isPublic()).findFirst();
        if( otd.isPresent() ){
            return _type.of( astRoot, otd.get());
        }
        //the primary type
        if (astRoot.getPrimaryType().isPresent()) {
            return _type.of(astRoot, astRoot.getPrimaryType().get());
        }
        return _type.of(astRoot, astRoot.getType(0));
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
        if (node instanceof LambdaExpr) {
            return _lambda.of((LambdaExpr) node);
        }
        if (node instanceof VariableDeclarator) {
            VariableDeclarator vd = (VariableDeclarator) node;
            //FieldDeclaration fd = (FieldDeclaration)vd.getParentNode().get();
            return _field.of(vd);
        }
        if (node instanceof FieldDeclaration) {
            FieldDeclaration fd = (FieldDeclaration) node;
            if (fd.getVariables().size() > 1) {
                throw new DraftException(
                    "Ambiguious node for FieldDeclaration " + fd + "pass in VariableDeclarator instead " + fd);
            }
            return _field.of(fd.getVariable(0));
        }
        if (node instanceof BlockStmt) {
            if (node.getParentNode().isPresent()) {
                if (node.getParentNode().get() instanceof NodeWithBlockStmt) {
                    return _body.of((NodeWithBlockStmt) node.getParentNode().get());
                }
                if (node.getParentNode().get() instanceof NodeWithOptionalBlockStmt) {
                    return _body.of((NodeWithOptionalBlockStmt) node.getParentNode().get());
                }
            }
            throw new DraftException("Unable to return draft _java node for BlockStmt without NodeWithBlockStmt parent");
            //return _body.of((BlockStmt) node);
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
            return _codeOf( (CompilationUnit)node);                     
        }
        throw new DraftException("Unable to create logical entity from " + node);
    }

    /**
     * A Way to consistently name things when we construct and deconstruct
     * Components of Java programs (external tools for building & matching &
     * diffing can be more valuable having the opportunity to compare like for
     * like (by componentizing things out and comparing or matching on a part by
     * part basis)
     */
    public enum Component {
        MODULE_DECLARATION("moduleDeclaration", ModuleDeclaration.class),
        /**
         * i.e. @Deprecated @NotNull
         */
        ANNOS("annos", _anno._annos.class),
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
        HEADER_COMMENT("header", Comment.class),
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

        //VALUE("value", Expression.class), //anno
        PACKAGE_NAME("package", PackageDeclaration.class),
        IMPORTS("imports", List.class, _import.class),
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

        public static <N extends _node> Component of(Class<N> nodeClass) {
            Optional<Component> op = Arrays.stream(Component.values()).filter(p -> p.implementationClass.equals(nodeClass)).findFirst();
            if (op.isPresent()) {
                return op.get();
            }
            return null;
        }

        /**
         * Returns the appropriate Component based on the _type
         *
         * @param t the type instance
         * @return the Component
         */
        public static Component getComponent(_type t) {
            if (t instanceof _class) {
                return Component.CLASS;
            }
            if (t instanceof _interface) {
                return Component.INTERFACE;
            }
            if (t instanceof _enum) {
                return Component.ENUM;
            }
            return Component.ANNOTATION;
        }
    }

    /**
     * Defines the Path through the hierarchial member components ( _types,
     * methods, etc.) a specific member or property
     *
     * (the notation we use is [xxxx] represents a name, and the other names
     * represent components for example:
     * <PRE>
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
    public static class _path {

        public static _path of(Object... pathAsTokens) {
            _path _p = new _path();
            for (int i = 0; i < pathAsTokens.length; i += 2) {
                if (!(pathAsTokens[i] instanceof Component)) {
                    throw new DraftException("element [" + i + "] MUST be a Component");
                }
                if ((pathAsTokens.length > i + 1) && pathAsTokens[i + 1] instanceof String) {
                    _p = _p.in((Component) pathAsTokens[i], (String) pathAsTokens[i + 1]);
                } else {
                    _p = _p.in((Component) pathAsTokens[i]);
                }
            }
            return _p;
        }

        /**
         * the types of components that identify an entity for example:
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
         * The identifying String of a member component (usually the name of the
         * member) (i.e. for a _field, _type, _method, _annotation._element, or
         * _enum._constant the name) (for a constructor, the parameter types)
         *
         * //NOTE: can be empty for non-named components (i.e. EXTENDS,
         * IMPLEMENTS, etc.)
         */
        public List<String> idPath;

        /**
         * build a new empty path
         */
        public _path() {
            componentPath = new ArrayList<>();
            idPath = new ArrayList<>();
        }

        /**
         * create a new path containing all nodes as the original
         *
         * @param original
         */
        public _path(_path original) {
            componentPath = new ArrayList();
            componentPath.addAll(original.componentPath);
            idPath = new ArrayList();
            idPath.addAll(original.idPath);
        }

        /**
         * Build and return a new _path that follows the current path down one
         * component
         *
         * @param component
         * @return a new _path that has a leaf node at the component
         */
        public _path in(_java.Component component) {
            return in(component, "");
        }

        /**
         * How many "nodes" are in the path
         *
         * @return the number of nodes in the path
         */
        public int size() {
            return this.componentPath.size();
        }

        /**
         * get the leaf (last component) component in the path
         *
         * @return the last component in the path (null if the path is empty)
         */
        public Component leaf() {
            if (!this.componentPath.isEmpty()) {
                return this.componentPath.get(this.componentPath.size() - 1);
            }
            return null;
        }

        /**
         * Gets the last id in the path (note: may be empty string, null if the
         * path is empty)
         *
         * @return the last id of the path or null if path is empty
         */
        public String leafId() {
            if (!this.idPath.isEmpty()) {
                return this.idPath.get(this.idPath.size() - 1);
            }
            return null;
        }

        /**
         * does the path at index have the id provided?
         *
         * @param index the index of the path (0 based)
         * @param id the id
         * @return true if the same, false otherwise
         */
        public boolean is(int index, String id) {
            if (index <= this.size() && index >= 0) {
                return this.idPath.get(index).equals(id);
            }
            return false;
        }

        /**
         * does the path at index have the component provided
         *
         * @param index the index from the start of the path (0-based)
         * @param component the component type expected
         * @return true if the component at
         */
        public boolean is(int index, Component component) {
            if (index <= this.size() && index >= 0) {
                return this.componentPath.get(index).equals(component);
            }
            return false;
        }

        /**
         * does the path at index have the component and id provided
         *
         * @param index the index from the start of the path (0-based)
         * @param component the component type expected
         * @param id the expected id
         * @return true if the component at index has the component and id
         * provided, false otherwise
         */
        public boolean is(int index, Component component, String id) {
            if (index <= this.size() && index >= 0) {
                return this.componentPath.get(index).equals(component)
                        && this.idPath.get(index).equals(id);
            }
            return false;
        }

        /**
         *
         * @param <N>
         * @param index
         * @param clazz
         * @param id
         * @return
         */
        public <N extends _node> boolean is(int index, Class<N> clazz, String id) {
            if (index <= this.size() && index >= 0) {
                return this.componentPath.get(index).implementationClass.equals(clazz)
                        && this.idPath.get(index).equals(id);
            }
            return false;
        }

        /**
         * Does the leaf part of the path have the provided component and id
         *
         * @param component the component
         * @param id the id
         * @return true if the path has the leaf at component and id
         */
        public boolean isLeaf(Component component, String id) {
            return component.equals(leaf()) && id.equals(leafId());
        }

        /**
         *
         * @param <N>
         * @param clazz
         * @param id
         * @return
         */
        public <N extends _node> boolean isLeaf(Class<N> clazz, String id) {
            return leaf().implementationClass.equals(clazz) && leaf().name.equals(id);
        }

        /**
         * @param component the component
         * @return is the last component in the path this component?
         */
        public boolean isLeaf(Component component) {
            return component.equals(leaf());
        }

        /**
         *
         * @param <N>
         * @param clazz
         * @return
         */
        public <N extends _node> boolean isLeaf(Class<N> clazz) {
            return leaf().implementationClass.equals(clazz);
        }

        /**
         * @param id
         * @return is the last id in the path this id?
         */
        public boolean isLeafId(String id) {
            return id.equals(leafId());
        }

        /**
         * does the path contain this id at any node?
         *
         * @param id
         * @return true if
         */
        public boolean has(String id) {
            return idPath.contains(id);
        }

        /**
         * does the path contain ALL of these ids (in ANY order)
         *
         * @param ids the ids to look for in the path
         * @return true if the path contains all ids
         */
        public boolean has(String... ids) {
            Set<String> sids = new HashSet<>();
            Arrays.stream(ids).forEach(i -> sids.add(i));
            return idPath.containsAll(sids);
        }

        /**
         * does this path contain all of these components (in ANY order)?
         *
         * @param components
         * @return true if the path contains all these components in ANY order
         */
        public boolean has(Component... components) {
            Set<Component> s = new HashSet<>();
            Arrays.stream(components).forEach(c -> s.add(c));
            return componentPath.containsAll(s);
        }

        /**
         * does the path contain this component (anywhere?)
         *
         * @param component component to look for
         * @return true if the component occurs anywhere in the path
         */
        public boolean has(Component component) {
            return componentPath.contains(component);
        }

        /**
         * id there a
         *
         * @param <N>
         * @param clazz
         * @return
         */
        public <N extends _node> boolean has(Class<N> clazz) {
            for (int i = 0; i < size(); i++) {
                if (this.componentPath.get(i).implementationClass.equals(clazz)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * does the path contain a part that has this exact component and id
         *
         * @param component the component we are looking for
         * @param id the path we are looking for
         * @return true if the path contains part with this component & id,
         * false otherwise
         */
        public boolean has(Component component, String id) {
            for (int i = 0; i < size(); i++) {
                if (this.componentPath.get(i).equals(component)
                        && this.idPath.get(i).equals(id)) {
                    return true;
                }
            }
            return false;
        }

        /**
         *
         * @param <N>
         * @param clazz
         * @param id
         * @return
         */
        public <N extends _node> boolean has(Class<N> clazz, String id) {
            for (int i = 0; i < size(); i++) {
                if (this.componentPath.get(i).implementationClass.equals(clazz)
                        && this.idPath.get(i).equals(id)) {
                    return true;
                }
            }
            return false;
        }

        /**
         * Build and return a new _path that follows the current path down one
         * component
         *
         * @param component the next component part
         * @param id the id for the component
         * @return a new _path advanced to the next component/id
         */
        public _path in(_java.Component component, String id) {
            _path _p = new _path(this);
            _p.componentPath.add(component);
            _p.idPath.add(id);
            return _p;
        }

        /**
         * @return the component path as a String
         */
        public String componentPathString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.componentPath.size(); i++) {
                if (i > 0) {
                    sb.append(".");
                }
                sb.append(this.componentPath.get(i).getName());
            }
            return sb.toString();
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder();
            for (int i = 0; i < this.componentPath.size(); i++) {
                if (i > 0) {
                    sb.append(".");
                }
                sb.append(this.componentPath.get(i).getName());
                if (this.idPath.get(i).length() > 0) {
                    sb.append("[").append(this.idPath.get(i)).append("]");
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
        public boolean equals(Object obj) {
            if (obj == null) {
                return false;
            }
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof _path)) {
                return false;
            }
            _path other = (_path) obj;

            return Objects.equals(this.componentPath, other.componentPath)
                    && Objects.equals(this.idPath, other.idPath);
        }
    }

    /**
     * Common model for all source code units of a Java codebase (a "code unit"
     * is a model of the contents contained within a source file) i.e.<UL>
     * <LI> a model (AST, etc.) of a regular XXX.java files
     * <LI> a model of the contents  <B>package-info.java</B> files
     * <LI> <B>module-info.java</B> files
     * </UL>
     *
     * (i.e.{@link _type}
     * {@link _class} {@link _enum} {@link _interface} {@link _annotation},
     * {@link _packageInfo}, {@link _moduleInfo}
     *
     * @author Eric
     * @param <T>
     */
    public interface _code<T> extends _model  {

        /**
         * @return the compilationUnit (NOTE: could be null for nested _types)
         */
        public CompilationUnit astCompilationUnit();

        /**
         * A top level source code unit is the top level type, a
         * "module-info.java" file or a "package-info.java" file
         *
         * @return
         */
        public boolean isTopLevel();

        /**
         * Gets the "Header comment" (usually the License) from the
         * compilationUnit (NOTE: returns null if there are no header comments
         * or this is nested _code)
         *
         * @return the Comment implementation of the Header comment
         */
        default Comment getHeaderComment() {
            if (this.isTopLevel()) {
                CompilationUnit cu = astCompilationUnit();
                if (cu.getComment().isPresent()) {
                    return astCompilationUnit().getComment().get();
                }
            }
            return null;
        }

        /**
         * sets the header comment (i.e.the license copyright, etc.) at the top
         * of the _compilationUnit
         *
         * @param astBlockComment
         * @return the Comment a JavaDoc comment or BlockComment or null
         */
        default T setHeaderComment(BlockComment astBlockComment) {
            astCompilationUnit().setComment(astBlockComment);
            return (T) this;
        }

        /**
         * Sets the header comment (i.e. the copywrite)
         *
         * @param commentLines the lines in the header comment
         * @return the modified T
         */
        default T setHeaderComment(String... commentLines) {
            return setHeaderComment(Ast.blockComment(commentLines));
        }

        /**
         * gets the imports from the CompilationUnit
         *
         * @return the _imports
         */
        default _imports getImports() {
            CompilationUnit cu = astCompilationUnit();
            if (cu != null) {
                return _imports.of(cu);
            }
            return new _imports(new CompilationUnit()); //better of all the evils
        }

        /**
         * remove imports based on predicate
         *
         * @param _importMatchFn filter for deciding which imports to removeIn
         * @return the modified TYPE
         */
        default T removeImports(Predicate<_import> _importMatchFn) {
            getImports().remove(_importMatchFn);
            return (T) this;
        }

        /**
         * removeIn imports by classes
         *
         * @param clazzes
         * @return
         */
        default T removeImports(Class... clazzes) {
            _imports.of(astCompilationUnit()).remove(clazzes);
            return (T) this;
        }

        /**
         *
         * @param toRemove
         * @return
         */
        default T removeImports(_import... toRemove) {
            _imports.of(astCompilationUnit()).remove(toRemove);
            return (T) this;
        }

        /**
         *
         * @param _typesToRemove
         * @return
         */
        default T removeImports(_type... _typesToRemove) {
            getImports().remove(_typesToRemove);
            return (T) this;
        }

        /**
         *
         * @param toRemove the ImportDeclarations to removeIn
         * @return the modified _type
         */
        default T removeImports(ImportDeclaration... toRemove) {
            CompilationUnit cu = astCompilationUnit();
            if (cu != null) {
                for (int i = 0; i < toRemove.length; i++) {
                    cu.getImports().remove(toRemove[i]);
                }
            }
            return (T) this;
        }

        /**
         *
         * @param toRemove
         * @return
         */
        default T removeImports(List<ImportDeclaration> toRemove) {
            CompilationUnit cu = astCompilationUnit();
            if (cu != null) {
                for (int i = 0; i < toRemove.size(); i++) {
                    cu.getImports().remove(toRemove.get(i));
                }
            }
            return (T) this;
        }

        /**
         * Select some imports based on the astImportPredicate and apply the
         * astImportActionFn on the selected Imports
         *
         * @param _importActionFn function to apply to the imports
         * @return the T
         */
        default T forImports(Consumer<_import> _importActionFn) {
            getImports().forEach(_importActionFn);
            return (T) this;
        }

        /**
         * Select some imports based on the astImportPredicate and apply the
         * astImportActionFn on the selected Imports
         *
         * @param _importMatchFn selects the Imports to act on
         * @param _importActionFn function to apply to the imports
         * @return the T
         */
        default T forImports(Predicate<_import> _importMatchFn, Consumer<_import> _importActionFn) {
            getImports().forEach(_importMatchFn, _importActionFn);
            return (T) this;
        }

        /**
         *
         * @return
         */
        default List<_import> listImports() {
            return getImports().list();
        }

        /**
         * Does this compilationUnit import (explicitly or *) import this _type
         *
         * @param _t a top level _type
         * @return true if the CompilationUnit imports this type, false
         * otherwise
         */
        default boolean hasImport(_type _t) {
            return hasImport(_t.getFullName());
        }

        /**
         * Does this class statically import this class i.e.
         * <PRE>
         * _class _c = _class.of("A").imports("import static draft.java.Ast.*;");
         * assertTrue( _c.hasImportStatic(Ast.class));
         * </PRE>
         *
         * @param clazz
         * @return
         */
        default boolean hasImportStatic(Class clazz) {
            return !listImports(i -> i.isStatic() && i.isWildcard() && i.hasImport(clazz)).isEmpty();
        }

        /**
         * class or method name
         * <PRE>
         *
         * </PRE>
         *
         * @param className
         * @return
         */
        default boolean hasImport(String className) {
            return _imports.of(astCompilationUnit()).hasImport(className);
        }

        default boolean hasImports(Class... clazzes) {
            return _imports.of(astCompilationUnit()).hasImports(clazzes);
        }

        default boolean hasImport(Class clazz) {
            return _imports.of(astCompilationUnit()).hasImport(clazz);
        }

        default boolean hasImport(Predicate<_import> _importMatchFn) {
            return !listImports(_importMatchFn).isEmpty();
        }

        default boolean hasImport(_import _i) {
            return listImports(i -> i.equals(_i)).size() > 0;
        }

        default List<_import> listImports(Predicate<_import> _importMatchFn) {
            return this.getImports().list().stream().filter(_importMatchFn).collect(Collectors.toList());
        }

        /**
         * Adds static wildcard imports for all Classes
         *
         * @param wildcardStaticImports a list of classes that will
         * WildcardImports
         * @return the T
         */
        default T importStatic(Class... wildcardStaticImports) {
            CompilationUnit cu = astCompilationUnit();
            if (cu != null) {
                Arrays.stream(wildcardStaticImports).forEach(i -> {
                    ImportDeclaration id = Ast.importDeclaration(i);
                    id.setAsterisk(true);
                    id.setStatic(true);
                    cu.addImport(id);
                });
            }
            return (T) this;
        }

        /**
         *
         * @param staticWildcardImports
         * @return
         */
        default T importStatic(String... staticWildcardImports) {
            CompilationUnit cu = astCompilationUnit();
            if (cu != null) {
                Arrays.stream(staticWildcardImports).forEach(i -> {
                    ImportDeclaration id = Ast.importDeclaration(i);
                    id.setStatic(true);
                    id.setAsterisk(true);
                    cu.addImport(id);
                });
            }
            return (T) this;
        }

        /**
         * Statically import all of the
         *
         * @param wildcardTypeStaticImport
         * @return
         */
        default T importStatic(_type... wildcardTypeStaticImport) {
            CompilationUnit cu = astCompilationUnit();
            if (cu != null) {
                Arrays.stream(wildcardTypeStaticImport).forEach(i -> {
                    cu.addImport(new ImportDeclaration(i.getFullName(), true, true));
                });
            }
            return (T) this;
        }

        /**
         *
         * @param _ts
         * @return
         */
        default T imports(_type... _ts) {
            Arrays.stream(_ts).forEach(_t -> imports(_t.getFullName()));
            return (T) this;
        }

        /**
         * Add a single class import to the compilationUnit
         *
         * @param singleClass
         * @return the modified compilationUnit
         */
        default T imports(Class singleClass) {
            return imports(new Class[]{singleClass});
        }

        /**
         * Regularly import a class
         *
         * @param classesToImport
         * @return
         */
        default T imports(Class... classesToImport) {
            CompilationUnit cu = astCompilationUnit();
            if (cu != null) {
                for (int i = 0; i < classesToImport.length; i++) {
                    if (classesToImport[i].isArray()) {
                        //System.out.println("CT " + classesToImport[i].getComponentType() );
                        imports(classesToImport[i].getComponentType());
                    } else {
                        //dont import primitives or primitive arrays
                        if (classesToImport[i] == null
                                || classesToImport[i].isPrimitive()
                                || classesToImport[i].isArray() && classesToImport[i].getComponentType().isPrimitive()
                                || classesToImport[i].getPackageName().equals("java.lang")) {
                            break;
                        }
                        String cn = classesToImport[i].getCanonicalName();
                        //fix a minor bug in JavaParser API where anything in "java.lang.**.*" is not imported
                        // so java.lang.annotation.* classes are not imported when they should be
                        if (classesToImport[i].getPackage() != Integer.class.getPackage()
                                && classesToImport[i].getCanonicalName().startsWith("java.lang")) {
                            //System.out.println( "manually adding "+ classesToImport[i].getCanonicalName());
                            cu.addImport(classesToImport[i].getCanonicalName());
                        } else {
                            cu.addImport(cn);
                        }
                    }
                }
                return (T) this;
            }
            throw new DraftException("No AST CompilationUnit to add imports");
        }

        /**
         * Adds these importDeclarations
         *
         * @param astImportDecls
         * @return
         */
        default T imports(ImportDeclaration... astImportDecls) {
            CompilationUnit cu = astCompilationUnit();
            if (cu != null) {
                Arrays.stream(astImportDecls).forEach(c -> cu.addImport(c));
                return (T) this;
            }
            //return (T) this;
            throw new DraftException("No AST CompilationUnit of class to add imports");
        }

        default T imports(String anImport) {
            return imports(new String[]{anImport});
        }

        default T imports(String... importStatements) {
            CompilationUnit cu = astCompilationUnit();
            if (cu != null) {
                Arrays.stream(importStatements).forEach(c -> cu.addImport(Ast.importDeclaration(c)));
                return (T) this;
            }
            throw new DraftException("No AST CompilationUnit of to add imports");
        }        
    }

    /**
     * a module-info.java file it is it's own
     *
     */
    public static class _moduleInfo implements _code<_moduleInfo>, _model._node<CompilationUnit> {

        public CompilationUnit astCompUnit;
        private final _javadoc.JavadocHolderAdapter javadocHolder;

        @Override
        public CompilationUnit astCompilationUnit() {
            return astCompUnit;
        }

        /**
         * is the String representation equal to the _node entity (i.e. if we
         * parse the string, does it create an AST entity that is equal to the
         * node?)
         *
         * @param stringRep the string representation of the node (parsed as an
         * AST and compared to this entity to see if equal)
         * @return true if the Parsed String represents the entity
         */
        @Override
        public boolean is(String... stringRep) {
            try {
                return is(Ast.compilationUnit(stringRep));
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final _moduleInfo other = (_moduleInfo) obj;
            if (!Objects.equals(this.astCompUnit, other.astCompUnit)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return this.astCompUnit.hashCode();
        }

        /**
         * Is the AST node representation equal to the underlying entity
         *
         * @param astCu the astNode to compare against
         * @return true if they represent the same _node, false otherwise
         */
        @Override
        public boolean is(CompilationUnit astCu) {
            return false;
        }

        /**
         * Decompose the entity into key-VALUE pairs
         *
         * @return a map of key values
         */
        @Override
        public Map<_java.Component, Object> componentsMap() {

            Map m = new HashMap();
            m.put(_java.Component.HEADER_COMMENT, getHeaderComment());
            m.put(_java.Component.NAME, getModuleAst().getNameAsString());
            m.put(_java.Component.MODULE_DECLARATION, getModuleAst());
            m.put(_java.Component.ANNOS, _anno._annos.of(getModuleAst()));
            m.put(_java.Component.IMPORTS, _imports.of(astCompUnit));
            m.put(_java.Component.JAVADOC, this.javadocHolder.getJavadoc());

            return m;
        }

        @Override
        public String toString() {
            return this.astCompUnit.toString();
        }

        @Override
        public CompilationUnit ast() {
            return astCompUnit;
        }

        public static _moduleInfo of(String... input) {
            return new _moduleInfo(Ast.parse(Text.combine(input)));
        }

        public static _moduleInfo of(CompilationUnit astCu) {
            return new _moduleInfo(astCu);
        }

        public _moduleInfo(CompilationUnit cu) {
            this.astCompUnit = cu;
            this.javadocHolder = new _javadoc.JavadocHolderAdapter(cu);
        }

        @Override
        public boolean isTopLevel() {
            return true;
        }

        public ModuleDeclaration getModuleAst() {
            if (this.astCompUnit.getModule().isPresent()) {
                return this.astCompUnit.getModule().get();
            }
            return null;
        }
    }

    /**
     * model of a package-info
     *
     */
    public static class _packageInfo
            implements _code<_packageInfo>, _anno._hasAnnos<_packageInfo>, _node<CompilationUnit> {

        public static _packageInfo of(String... pkgInfo) {
            return new _packageInfo(StaticJavaParser.parse(Text.combine(pkgInfo)));
        }

        public static _packageInfo of(CompilationUnit astCu) {
            return new _packageInfo(astCu);
        }

        public CompilationUnit astCompUnit;
        private final _javadoc.JavadocHolderAdapter javadocHolder;
        
        @Override
        public CompilationUnit astCompilationUnit() {
            return astCompUnit;
        }

        public _packageInfo(CompilationUnit astCu) {
            this.astCompUnit = astCu;
            this.javadocHolder = new _javadoc.JavadocHolderAdapter(astCu);
        }

        @Override
        public boolean isTopLevel() {
            return true;
        }

        @Override
        public CompilationUnit ast() {
            return astCompUnit;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null) {
                return false;
            }
            if (getClass() != obj.getClass()) {
                return false;
            }
            final _packageInfo other = (_packageInfo) obj;
            if (!Objects.equals(this.astCompUnit, other.astCompUnit)) {
                return false;
            }
            return true;
        }

        @Override
        public int hashCode() {
            return this.astCompUnit.hashCode();
        }

        public String getPackage() {
            if (astCompilationUnit().getPackageDeclaration().isPresent()) {
                return astCompilationUnit().getPackageDeclaration().get().getNameAsString();
            }
            return "";
        }

        /**
         * Sets the package this TYPE is in
         *
         * @param packageName
         * @return the modified TYPE
         */
        public _packageInfo setPackage(String packageName) {
            CompilationUnit cu = astCompilationUnit();
            //System.out.println("Setting package name to \""+ packageName+"\" in "+ cu );
            //TODO I need to make sure it's a valid name
            cu.setPackageDeclaration(packageName);
            return this;
        }

        /**
         * Returns true if this compilationUnit is located directly within this
         * package
         *
         * @param packageName
         * @return
         */
        public boolean isInPackage(String packageName) {
            String pn = getPackage();
            if (pn == null) {
                return packageName == null || packageName.length() == 0;
            }
            if (Objects.equals(pn, packageName)) {
                return true;
            }
            if (packageName != null) {
                return packageName.indexOf(pn) == 0;
            }
            return false;
        }

        @Override
        public _anno._annos getAnnos() {
            if (astCompilationUnit().getPackageDeclaration().isPresent()) {
                //annos are on the packageDeclaration
                return _anno._annos.of(astCompilationUnit().getPackageDeclaration().get());
            }
            return _anno._annos.of(); //dont like this... but
        }

        /**
         * is the String representation equal to the _node entity (i.e. if we
         * parse the string, does it create an AST entity that is equal to the
         * node?)
         *
         * @param stringRep the string representation of the node (parsed as an
         * AST and compared to this entity to see if equal)
         * @return true if the Parsed String represents the entity
         */
        @Override
        public boolean is(String... stringRep) {
            try {
                return is(Ast.compilationUnit(stringRep));
            } catch (Exception e) {
                return false;
            }
        }

        /**
         * Is the AST node representation equal to the underlying entity
         *
         * @param astCu the astNode to compare against
         * @return true if they represent the same _node, false otherwise
         */
        @Override
        public boolean is(CompilationUnit astCu) {
            return false;
        }

        @Override
        public String toString() {
            return this.astCompUnit.toString();
        }

        /**
         * Decompose the entity into key-VALUE pairs
         *
         * @return a map of key values
         */
        @Override
        public Map<_java.Component, Object> componentsMap() {
            Map m = new HashMap();
            m.put(_java.Component.HEADER_COMMENT, this.getHeaderComment());
            m.put(_java.Component.JAVADOC, this.javadocHolder.getJavadoc());
            m.put(_java.Component.PACKAGE_NAME, getPackage());
            m.put(_java.Component.ANNOS, getAnnos());
            m.put(_java.Component.IMPORTS, _imports.of(astCompUnit));
            return m;
        }
    }
}
