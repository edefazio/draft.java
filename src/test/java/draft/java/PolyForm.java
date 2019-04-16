/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.AnnotationMemberDeclaration;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.ConstructorDeclaration;
import com.github.javaparser.ast.body.EnumConstantDeclaration;
import com.github.javaparser.ast.body.EnumDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.body.Parameter;
import com.github.javaparser.ast.body.ReceiverParameter;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import com.github.javaparser.ast.type.ArrayType;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.IntersectionType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.type.TypeParameter;
import com.github.javaparser.ast.type.UnionType;
import com.github.javaparser.ast.type.VarType;
import com.github.javaparser.ast.type.VoidType;
import com.github.javaparser.ast.type.WildcardType;
import draft.Named;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Relationships between representational forms (AST, _node) etc.
 * 
 * @author Eric
 */
public class PolyForm {
 
    /** Map from the _model._node classes to the Ast Node equivalent */
    public static final Map<Class<? extends _model._node>, Class<? extends Node>> _JAVA_TO_AST_NODE_CLASSES = new HashMap<>();

    static {
        _JAVA_TO_AST_NODE_CLASSES.put( _anno.class, AnnotationExpr.class );
        _JAVA_TO_AST_NODE_CLASSES.put( _annotation._element.class, AnnotationMemberDeclaration.class );
        _JAVA_TO_AST_NODE_CLASSES.put( _enum._constant.class, EnumConstantDeclaration.class );
        _JAVA_TO_AST_NODE_CLASSES.put( _constructor.class, ConstructorDeclaration.class );
        _JAVA_TO_AST_NODE_CLASSES.put( _field.class, VariableDeclarator.class );
        _JAVA_TO_AST_NODE_CLASSES.put( _method.class, MethodDeclaration.class );
        _JAVA_TO_AST_NODE_CLASSES.put( _parameter.class, Parameter.class );
        _JAVA_TO_AST_NODE_CLASSES.put( _receiverParameter.class, ReceiverParameter.class );
        _JAVA_TO_AST_NODE_CLASSES.put( _staticBlock.class, InitializerDeclaration.class );
        _JAVA_TO_AST_NODE_CLASSES.put( _typeParameter.class, TypeParameter.class );
        _JAVA_TO_AST_NODE_CLASSES.put( _typeDecl.class, Type.class );

        _JAVA_TO_AST_NODE_CLASSES.put( _type.class, TypeDeclaration.class );
        _JAVA_TO_AST_NODE_CLASSES.put( _annotation.class, AnnotationDeclaration.class );
        _JAVA_TO_AST_NODE_CLASSES.put( _class.class, ClassOrInterfaceDeclaration.class );   //arrrrrrgggggg
        _JAVA_TO_AST_NODE_CLASSES.put( _interface.class, ClassOrInterfaceDeclaration.class ); //arrrrrrgggggg
        _JAVA_TO_AST_NODE_CLASSES.put( _enum.class, EnumDeclaration.class );
    }

    //public static final Class<Type> TYPE = Type.class;
    //public static final Class<ArrayType>ARRAY_TYPE = ArrayType.class;
    //public static final Class<ClassOrInterfaceType> CLASS_OR_INTERFACE_TYPE= ClassOrInterfaceType.class;
    //public static final Class<IntersectionType> INTERSECTION_TYPE = IntersectionType.class;
    //public static final Class<PrimitiveType> PRIMITIVE_TYPE = PrimitiveType.class;
    //public static final Class<ReferenceType> REFERENCE_TYPE = ReferenceType.class;

    //public static final Class<TypeParameter> TYPE_PARAMETER = TypeParameter.class;
    //public static final Class<UnionType> UNION_TYPE = UnionType.class;
    //    com.github.javaparser.ast.TYPE.UnknownType
    //public static final Class<VarType> VAR_TYPE = VarType.class;
    //public static final Class<VoidType> VOID_TYPE = VoidType.class;
    //public static final Class<WildcardType> WILDCARD_TYPE = WildcardType.class;
    
    static MappedForm TYPE_REF = new MappedForm( "typeRef", _typeDecl.class, Type.class)
            .altNodeClasses(WildcardType.class, PrimitiveType.class, UnionType.class, IntersectionType.class, ArrayType.class, ClassOrInterfaceType.class, ReferenceType.class, VarType.class, VoidType.class);
    
    
    static MappedForm TYPE_PARAMETER = new MappedForm( "typeParameter", _typeParameter.class, TypeParameter.class);
    static ListForm TYPE_PARAMETERS = new ListForm( "typeParameters", _typeParameter.class, TypeParameter.class)
            .javaCollection(_typeParameter._typeParameters.class);
    
    static MappedForm STATIC_BLOCK = new MappedForm( "staticBlock", _staticBlock.class, InitializerDeclaration.class);
    static ListForm STATIC_BLOCKS = new ListForm( "staticBlocks", _staticBlock.class, InitializerDeclaration.class);
    
    static MappedForm RECEIVER_PARAMETER = new MappedForm( "receiverParameter", _receiverParameter.class, ReceiverParameter.class);
    
    static MappedForm PARAMETER = new MappedForm( "parameter", _parameter.class, Parameter.class);
    static ListForm PARAMETERS = new ListForm( "parameter", _parameter.class, Parameter.class);
    
    static MappedForm METHOD = new MappedForm( "method", _method.class, MethodDeclaration.class);
    static ListForm METHODS = new ListForm( "methods", _method.class, MethodDeclaration.class);
    
    static MappedForm FIELD = new MappedForm( "field", _field.class, VariableDeclarator.class);
    static ListForm FIELDS = new ListForm( "fields", _field.class, VariableDeclarator.class);
    
    static MappedForm CONSTRUCTOR = new MappedForm( "constructor", _constructor.class, ConstructorDeclaration.class);
    static ListForm CONSTRUCTORS = new ListForm( "constructors", _constructor.class, ConstructorDeclaration.class);
    
    static MappedForm CONSTANT = new MappedForm( "constant", _enum._constant.class, EnumConstantDeclaration.class);
    static ListForm CONSTANTS = new ListForm( "constants", _enum._constant.class, EnumConstantDeclaration.class);
    
    static MappedForm ANNO = new MappedForm( "anno", _anno.class, AnnotationExpr.class)
            .altNodeClasses(SingleMemberAnnotationExpr.class, NormalAnnotationExpr.class,MarkerAnnotationExpr.class);

    static ListForm ANNOS = new ListForm( "annos", _anno.class, AnnotationExpr.class)
            .javaCollection(_anno._annos.class )
            .altAstElementClasses(SingleMemberAnnotationExpr.class, NormalAnnotationExpr.class,MarkerAnnotationExpr.class);
    
    static MappedForm ANNOTATION_ELEMENT = new MappedForm( "annotationElement", _annotation._element.class, AnnotationMemberDeclaration.class);
    
    static class ListForm implements Named{
        String name;
        Class<? extends _model._node> _javaElementType;
        Class<? extends _model> _javaCollectionType;
        Class<? extends Node> astNodeElementType;
        Set<Class<? extends Node>> altAstNodeClass = new HashSet<>();//this MAY be empty or have one or more specific impls i.e.  
        
        public ListForm( String name, Class<? extends _model._node> _javaClass, Class<? extends Node> astNodeClass){
            this.name = name;
            this._javaElementType = _javaClass;
            this.astNodeElementType = astNodeClass;
        }
        
        public ListForm javaCollection( Class javaCollectionTypeClass ){
            this._javaCollectionType = javaCollectionTypeClass;
            return this;
        }
        
        public ListForm altAstElementClasses(Class<? extends Node>...altNodeClasses){
            Arrays.stream(altNodeClasses).forEach( a-> this.altAstNodeClass.add(a));
            return this;
        }
        
        @Override
        public String getName(){
            return name;
        }
    }   
    
    /** 
     * Mediate between different Representation types 
     * (i.e. Ast Node type & _java type)    
     */
    static class MappedForm implements Named {
        String name; 
        Class<? extends _model._node> _javaClass;
        Class<? extends Node> astNodeClass;           //this COULD be an interface i..e AnnotationExpr.class
        Set<Class<? extends Node>> alternateNodeClass = new HashSet<>();//this MAY be empty or have one or more specific impls i.e.  
        
        public MappedForm( String name, Class<? extends _model._node> _javaClass, Class<? extends Node> astNodeClass){
            this.name = name;
            this._javaClass = _javaClass;
            this.astNodeClass = astNodeClass;
        }
        
        public MappedForm altNodeClasses(Class<? extends Node>...altNodeClasses){
            Arrays.stream(altNodeClasses).forEach( a-> this.alternateNodeClass.add(a));
            return this;
        }
        
        @Override
        public String getName(){
            return name;
        }
    }
    
}
