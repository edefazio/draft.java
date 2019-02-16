package draft.java;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
//import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.comments.*;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.nodeTypes.*;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.type.*;
import draft.DraftException;
import draft.Text;
import draft.java._model.*;
import java.util.*;

import static draft.java.Ast.*;

/**
 * Translates from AST {@link Node} entities to {@link _model} entities
 * and implementations of edge "walking" {@link _model} and {@link Node} entities
 *
 * @author Eric
 */
public enum _java {
    ;

    /** Map from the _model._node classes to the Ast Node equivalent */
    public static final Map<Class<? extends _node>, Class<? extends Node>> ENTITY_TO_NODE_CLASSES = new HashMap<>();

    static {
        ENTITY_TO_NODE_CLASSES.put( _anno.class, AnnotationExpr.class );
        ENTITY_TO_NODE_CLASSES.put( _annotation._element.class, AnnotationMemberDeclaration.class );
        ENTITY_TO_NODE_CLASSES.put( _enum._constant.class, EnumConstantDeclaration.class );
        ENTITY_TO_NODE_CLASSES.put( _constructor.class, ConstructorDeclaration.class );
        ENTITY_TO_NODE_CLASSES.put( _field.class, VariableDeclarator.class );
        ENTITY_TO_NODE_CLASSES.put( _method.class, MethodDeclaration.class );
        ENTITY_TO_NODE_CLASSES.put( _parameter.class, Parameter.class );
        ENTITY_TO_NODE_CLASSES.put( _receiverParameter.class, ReceiverParameter.class );
        ENTITY_TO_NODE_CLASSES.put( _staticBlock.class, InitializerDeclaration.class );
        ENTITY_TO_NODE_CLASSES.put( _typeParameter.class, TypeParameter.class );
        ENTITY_TO_NODE_CLASSES.put( _typeRef.class, Type.class );

        ENTITY_TO_NODE_CLASSES.put( _type.class, TypeDeclaration.class );
        ENTITY_TO_NODE_CLASSES.put( _annotation.class, AnnotationDeclaration.class );
        ENTITY_TO_NODE_CLASSES.put( _class.class, ClassOrInterfaceDeclaration.class );
        ENTITY_TO_NODE_CLASSES.put( _interface.class, ClassOrInterfaceDeclaration.class );
        ENTITY_TO_NODE_CLASSES.put( _enum.class, EnumDeclaration.class );
    }

    /** Map from the _model._node classes to the Ast Node equivalent */
    public static final Map<Class<? extends Node>, Class<? extends _node>> NODE_TO_MODEL_CLASSES = new HashMap<>();

    static {
        NODE_TO_MODEL_CLASSES.put( AnnotationExpr.class, _anno.class ); //base
        NODE_TO_MODEL_CLASSES.put( NormalAnnotationExpr.class, _anno.class ); //impl
        NODE_TO_MODEL_CLASSES.put( MarkerAnnotationExpr.class, _anno.class );
        NODE_TO_MODEL_CLASSES.put( SingleMemberAnnotationExpr.class, _anno.class );

        NODE_TO_MODEL_CLASSES.put( AnnotationMemberDeclaration.class,_annotation._element.class );
        NODE_TO_MODEL_CLASSES.put( EnumConstantDeclaration.class, _enum._constant.class );
        NODE_TO_MODEL_CLASSES.put( ConstructorDeclaration.class, _constructor.class );

        NODE_TO_MODEL_CLASSES.put( VariableDeclarator.class, _field.class );
        NODE_TO_MODEL_CLASSES.put( FieldDeclaration.class, _field.class );

        NODE_TO_MODEL_CLASSES.put( MethodDeclaration.class, _method.class );
        NODE_TO_MODEL_CLASSES.put( Parameter.class, _parameter.class );
        NODE_TO_MODEL_CLASSES.put( ReceiverParameter.class, _receiverParameter.class );
        NODE_TO_MODEL_CLASSES.put( InitializerDeclaration.class, _staticBlock.class );
        NODE_TO_MODEL_CLASSES.put( TypeParameter.class , _typeParameter.class );

        NODE_TO_MODEL_CLASSES.put( Type.class, _typeRef.class );
        NODE_TO_MODEL_CLASSES.put( ArrayType.class, _typeRef.class);
        NODE_TO_MODEL_CLASSES.put( ClassOrInterfaceType.class, _typeRef.class);
        NODE_TO_MODEL_CLASSES.put( IntersectionType.class, _typeRef.class);
        NODE_TO_MODEL_CLASSES.put( PrimitiveType.class, _typeRef.class);
        NODE_TO_MODEL_CLASSES.put( ReferenceType.class, _typeRef.class);
        NODE_TO_MODEL_CLASSES.put( UnionType.class, _typeRef.class);
        NODE_TO_MODEL_CLASSES.put( VarType.class, _typeRef.class);
        NODE_TO_MODEL_CLASSES.put( VoidType.class, _typeRef.class);
        NODE_TO_MODEL_CLASSES.put( WildcardType.class, _typeRef.class);

        NODE_TO_MODEL_CLASSES.put( TypeDeclaration.class, _type.class );
        NODE_TO_MODEL_CLASSES.put( ClassOrInterfaceDeclaration.class, _type.class );
        NODE_TO_MODEL_CLASSES.put( EnumDeclaration.class, _enum.class );
        NODE_TO_MODEL_CLASSES.put( AnnotationDeclaration.class, _annotation.class );

        NODE_TO_MODEL_CLASSES.put( AnnotationDeclaration.class, _annotation.class );
    }

    private static _node getLogicalParentNode(Node node ){
        if( ENTITY_TO_NODE_CLASSES.containsValue(node.getClass()) ){
            return (_node) of(node);
        }
        return getLogicalParentNode( node.getParentNode().get() );
    }

    /**
     * Parse and return the appropriate node based on the Node class
     * (the Node class can be a _model, or Ast Node class
     *
     * @param nodeClass the class of the node (implementation class)
     * @param code the code for the AST to _1_build
     * @return the node implementation of the code
     */
    public static Node nodeOf(Class nodeClass, String... code ) {
        if( !_model.class.isAssignableFrom( nodeClass )){
            return Ast.of(nodeClass, code );
        }
        if( _anno.class == nodeClass ) {
            return anno( code );
        }
        if( _method.class == nodeClass ) {
            return method( code );
        }
        if( _constructor.class == nodeClass ) {
            return ctor( code );
        }
        if( _typeRef.class == nodeClass ) {
            return typeRef( Text.combine( code ).trim() );
        }
        if( _staticBlock.class == nodeClass ) {
            return staticBlock( code );
        }
        if( _type.class.isAssignableFrom( nodeClass ) ) {
            //System.out.println( "Parsing TYPE Decl" + nodeClass );
            return typeDeclaration( code );
        }
        if( _parameter.class == nodeClass ) {
            return parameter( code );
        }
        if( _receiverParameter.class == nodeClass ) {
            return receiverParameter( Text.combine( code ) );
        }
        if( _field.class == nodeClass ){
            return field( code );
        }
        if( _enum._constant.class == nodeClass ) {
            return constant( code );
        }
        if( _annotation._element.class == nodeClass ) {
            return annotationMember( code );
        }
        if( _method.class == nodeClass ) {
            return method( code );
        }
        throw new DraftException( "Could not parse Node " + nodeClass );
    }

    /**
     * Builds the appropriate _model entities based on AST Nodes provided
     * (Note: since there are no logical entities for {@link com.github.javaparser.ast.expr.Expression}, or
     * {@link com.github.javaparser.ast.stmt.Statement} Node implementations, this will fail
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
     * @param node the ast node
     * @return the _model entity
     */
    public static _model of(Node node ) {
        if( node instanceof AnnotationExpr ) {
            return _anno.of( (AnnotationExpr)node );
        }
        if( node instanceof AnnotationDeclaration ) {
            return _annotation.of( (AnnotationDeclaration)node );
        }
        if( node instanceof AnnotationMemberDeclaration ) {
            return _annotation._element.of( (AnnotationMemberDeclaration)node );
        }
        if( node instanceof ClassOrInterfaceDeclaration ) {
            ClassOrInterfaceDeclaration cois = (ClassOrInterfaceDeclaration)node;
            if( cois.isInterface() ) {
                return _interface.of( cois );
            }
            return _class.of( cois );
        }
        if( node instanceof ConstructorDeclaration ) {
            return _constructor.of( (ConstructorDeclaration)node );
        }
        if( node instanceof EnumDeclaration ) {
            return _enum.of( (EnumDeclaration)node );
        }
        if( node instanceof EnumConstantDeclaration ) {
            return _enum._constant.of( (EnumConstantDeclaration)node );
        }
        if( node instanceof VariableDeclarator ) {
            VariableDeclarator vd = (VariableDeclarator)node;
            //FieldDeclaration fd = (FieldDeclaration)vd.getParentNode().get();
            return _field.of( vd );
        }
        if( node instanceof FieldDeclaration ) {
            FieldDeclaration fd = (FieldDeclaration)node;
            if( fd.getVariables().size() > 1 ) {
                throw new DraftException( "Ambiguious node for FieldDeclaration "+ fd+ "pass in VariableDeclarator instead " + fd );
            }
            return _field.of( fd.getVariable( 0 ) );
        }
        if( node instanceof BlockStmt){
            return _body.of( (BlockStmt)node );
        }
        if( node instanceof JavadocComment ) {
            JavadocComment jdc = (JavadocComment)node;
            if( jdc.getParentNode().isPresent() ){
                return _javadoc.of( (NodeWithJavadoc)jdc.getParentNode().get() );
            }
            throw new DraftException( "No Parent provided for JavadocComment");
        }
        if( node instanceof MethodDeclaration ) {
            MethodDeclaration md = (MethodDeclaration)node;
            return _method.of( md );
        }
        if( node instanceof Parameter ) {
            return _parameter.of( (Parameter)node );
        }
        if( node instanceof InitializerDeclaration ) {
            InitializerDeclaration id = (InitializerDeclaration)node;
            return _staticBlock.of( id );
        }
        if( node instanceof ReceiverParameter ){
            ReceiverParameter rp = (ReceiverParameter)node;
            return _receiverParameter.of(rp);
        }
        if( node instanceof TypeParameter ) {
            TypeParameter tp = (TypeParameter)node;
            return _typeParameter.of( tp );
        }
        if( node instanceof Type ) {
            return _typeRef.of( (Type)node );
        }
        if( node instanceof CompilationUnit ) {
            CompilationUnit astRoot = (CompilationUnit)node;
            return _type.of( astRoot );
        }
        throw new DraftException( "Unable to create logical entity from " + node );
    }

    public enum Part{ //association
        ANNOTATIONS("annotations", _anno._annos.class),
        BODY("body", _body.class),
        MODIFIERS("modifiers", List.class, Modifier.class ),
        JAVADOC( "javadoc",_javadoc.class ),
        PARAMETERS( "parameters", _parameter._parameters.class ),
        RECEIVER_PARAMETER( "receiverParameter", _receiverParameter.class),
        TYPE_PARAMETERS("typeParameters",_typeParameter._typeParameters.class),
        TYPE_PARAMETER("typeParameter", TypeParameter.class), //_typeParameter.class
        THROWS("throws", _throws.class),
        NAME("name", String.class),
        KEY_VALUES("keyValues", List.class, MemberValuePair.class ), //anno
        VALUE("value", Expression.class), //anno
        PACKAGE_NAME("package", PackageDeclaration.class ),
        IMPORTS("imports", List.class, ImportDeclaration.class),
        ELEMENTS("elements", List.class, _annotation._element.class), //_annotation
        FIELDS("fields", List.class, _field.class),
        NESTS("nests", List.class, _type.class),

        TYPE( "type", _typeRef.class), //annotation.element
        DEFAULT("default", Expression.class), //_annotation.element

        EXTENDS("extends", List.class, ClassOrInterfaceType.class), //_class, //_interface
        IMPLEMENTS("implements", List.class, ClassOrInterfaceType.class), //_class, _enum
        STATIC_BLOCKS( "staticBlocks",List.class, _staticBlock.class), //class
        CONSTRUCTORS("constructors", List.class, _constructor.class), //class, _enum
        METHODS("methods", List.class, _method.class), //class

        CONSTANTS("constants",List.class, _enum._constant.class),
        ARGUMENTS("arguments",List.class, Expression.class),     //_enum._constant

        INIT("init", Expression.class),
        FINAL("final", Boolean.class ), //_parameter
        VAR_ARG("varArg", Boolean.class), //parameter

        AST_TYPE ("astType", Type.class),
        ARRAY_LEVEL("arrayLevel", Integer.class), //_typeRef
        ELEMENT_TYPE("elementType", Type.class);

        public final String name;
        public final Class implementationClass;
        public final Class elementClass;

        Part(String name, Class implementationClass){
            this.name = name;
            this.implementationClass = implementationClass;
            this.elementClass = null;
        }

        Part(String name, Class containerClass, Class elementClass){
            this.name = name;
            this.implementationClass = containerClass;
            this.elementClass = elementClass;
        }

        public String toString(){
            return name;
        }

        public static Part of( String name ){
            Optional<Part> op = Arrays.stream(Part.values()).filter( p ->p.name.equals(name) ).findFirst();
            if( op.isPresent()){
                return op.get();
            }
            return null;
        }
    }
}

