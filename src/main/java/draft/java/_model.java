package draft.java;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import draft.Composite;
import draft.Named;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * API adapting the access & manipulation of an interconnected nodes of an AST
 * as an intuitive logical model
 *
 * The Model stores ALL of it's state in the AST and acts as an "interpreter"/"mediator"
 *
 * Logical Facade instances store NO DATA, but references(s) into
 * {@link com.github.javaparser.ast.Node}s of the AST, and
 * provide a simple API to query or manipulate the AST as is it were
 * a simple Dto or VALUE object.
 *
 * The purpose of these nested interfaces is to define the relationship between
 * the AST Node(s) and the Logical entities... and trying to provide consistency
 * for how the logical API presents operations for adding, removing, and
 * replacing the underlying AST Nodes.
 *
 * The _model HIDES certain details that exist in the AST
 * (for instance all of the Position details for line and column numbers where the text of a component is
 * declared in the source file)
 *
 * The _model also simplifies ways of interacting with entities (in a logical fashion)
 * (for instance, we can extend a _type by passing in a Runtime Class, and this class is
 * converted into a AST component and both added to the extensions, AND the class is added
 * as an AST import if required)
 *
 * @author Eric
 */
public interface _model {
    
    
    /**
     * {@link _model} entity that maps directly to an AST {@link Node}
     * for example:
     * <UL>
     * <LI>{@link _anno} {@link AnnotationExpr}
     * <LI>{@link _annotation} {@link com.github.javaparser.ast.body.AnnotationDeclaration}
     * <LI>{@link _constructor} {@link com.github.javaparser.ast.body.ConstructorDeclaration}</LI>
     * <LI>{@link _class} {@link com.github.javaparser.ast.body.ClassOrInterfaceDeclaration}</LI>
     * <LI>{@link _enum} {@link com.github.javaparser.ast.body.EnumDeclaration}</LI>
     * <LI>{@link _field} {@link com.github.javaparser.ast.body.FieldDeclaration}</LI>
     * <LI>{@link _interface} {@link com.github.javaparser.ast.body.ClassOrInterfaceDeclaration}</LI>
     * <LI>{@link _method} {@link MethodDeclaration}</LI>
     * <LI>{@link _parameter} {@link com.github.javaparser.ast.body.Parameter}</LI>
     * <LI>{@link _receiverParameter} {@link com.github.javaparser.ast.body.ReceiverParameter}</LI>
     * <LI>{@link _staticBlock} {@link com.github.javaparser.ast.body.InitializerDeclaration}</LI>
     * <LI>{@link _typeParameter} {@link com.github.javaparser.ast.type.TypeParameter}</LI>
     * <LI>{@link _typeRef} {@link Type}</LI>
     * </UL>
     * @see _java for mappings
     * 
     * @param <N> ast node {@link MethodDeclaration}, {@link com.github.javaparser.ast.body.FieldDeclaration}
     */
    interface _node<N extends Node>
            extends _model, Composite {

        /**
         * Decompose the entity into key-VALUE pairs
         * @return a map of key values
         */
        Map<_java.Component,Object> componentsMap();

        /**
         * Decompose the entity into smaller named components
         * returning a mapping between the name and the constituent part
         * @return a Map with the names mapped to the corresponding components
         */
        @Override
        default Map<String,Object>componentize(){
            Map<_java.Component, Object> parts = componentsMap();
            Map<String,Object> mdd = new HashMap<>();
            parts.forEach( (p,o) -> {mdd.put( p.name, o);});
            return mdd;
        }

        
        /**
         * @return the underlying AST Node instance being manipulated
         * by the _model._node facade
         * NOTE: the AST node contains physical information (i.e. location in
         * the file (line numbers) and syntax related parent/child relationships
         */
        N ast();

        /**
         * Pass in the AST Pretty Printer configuration which will determine how the code
         * is formatted and return the formatted String representing the code.
         *
         * @see com.github.javaparser.printer.PrettyPrintVisitor the original visitor for walking and printing nodes in AST
         * @see Ast.PrintNoAnnotations a configurable subclass of PrettyPrintVisitor that will not print ANNOTATIONS
         * @see PrettyPrinterConfiguration the configurations for spaces, Tabs, for printing
         * @see Ast#PRINT_NO_ANNOTATIONS_OR_COMMENTS a premade implementation for
         * @see Ast#PRINT_NO_COMMENTS
         *
         * @param codeFormat the details on how the code will be formatted (for this element and all sub ELEMENTS)
         * @return A String representing the .java code
         */
        default String toString( PrettyPrinterConfiguration codeFormat ){
            return ast().toString( codeFormat );
        }
    }

    /**
     * A member component of the {@link _type} (it can be associated with a larger entity or context)
     * NOTE: each {@link _member} maps directly to an AST Node
     * <UL>
     * <LI>{@link _field} {@link com.github.javaparser.ast.body.FieldDeclaration}
     * <LI>{@link _constructor} {@link com.github.javaparser.ast.body.ConstructorDeclaration}
     * <LI>{@link _method} {@link MethodDeclaration}
     * <LI>{@link _enum._constant} {@link com.github.javaparser.ast.body.EnumConstantDeclaration}
     * <LI>{@link _annotation._element} {@link com.github.javaparser.ast.body.AnnotationMemberDeclaration}
     * <LI>{@link _type} {@link com.github.javaparser.ast.body.TypeDeclaration}
     * <LI>{@link _class} {@link com.github.javaparser.ast.body.ClassOrInterfaceDeclaration}
     * <LI>{@link _enum} {@link com.github.javaparser.ast.body.EnumDeclaration}
     * <LI>{@link _interface} {@link com.github.javaparser.ast.body.ClassOrInterfaceDeclaration}
     * <LI>{@link _annotation} {@link com.github.javaparser.ast.body.AnnotationDeclaration}
     * </UL>
     *
     * NOTE:
     * <LI>{@link _staticBlock} {@link com.github.javaparser.ast.body.InitializerDeclaration}
     * is NOT a member (primarily because it does not satisfy the {@link _named} 
     * {@link _anno._hasAnnos} or {@link _javadoc._hasJavadoc} interfaces
     * (this generally maps to Accessible Java Member things 
     * (available via reflection at runtime)
     *
     * @param <N> the node type
     * @param <T> the model type
     */
    interface _member<N extends Node, T extends _named & _anno._hasAnnos & _javadoc._hasJavadoc>
            extends _node<N>, _named<T>, _anno._hasAnnos<T>, _javadoc._hasJavadoc<T>{
    }

    /**
     * Named java entities 
     *
     * @author Eric
     * @param <T>
     */
    interface _named <T extends _named> extends _model, Named {

        /**
         * @param name set the name on the entity and return the modified entity
         * @return the modified entity
         */
        T name( String name );

        /**
         * gets the name of the entity
         * @return the name of the entity
         */
        @Override
        String getName();
    }

    /**
     * Entity with TYPE/NAME pair
     * <UL>
     *     <LI>{@link _field},
     *     <LI>{@link _parameter},
     *     <LI>{@link _method},
     *     <LI>{@link _annotation._element},
     *     <LI>{@link _receiverParameter}
     * </UL>
     * @param <T> the specialized entity that is a named TYPE
     */
    interface _namedType <T extends _namedType>
            extends _named<T> {

        /**
         * @return they TYPE
         */
        _typeRef getType();

        default _typeRef getElementType(){
            return _typeRef.of(getType().ast().getElementType());
        }

        /**
         * Set the TYPE and return the modified entity
         * @param _tr the _typeRef object
         * @return the modified entity after setting the TYPE
         */
        T type( Type _tr );

        /**
         * set the TYPE and return
         * @param t
         * @return
         */
        default T type( _typeRef t ){
            return type( t.ast() );
        }

        /**
         * Set the TYPE and return the modified entity
         * @param typeRef the String representation of the TYPE
         * @return the modified entity after setting the TYPE
         */
        default T type( String typeRef ){
            return type( Ast.typeRef( typeRef) );
        }

        /**
         * Set the TYPE and return the modified entity
         * @param clazz the class of the TYPE to set
         * @return the modified entity after setting the TYPE
         */
        default T type( Class clazz ){
            return type( Ast.typeRef(clazz.getCanonicalName() ));
        }

        /**
         * Perform the action is the entity is of the TYPE clazz and return it
         * @param clazz the class to perform the action
         * @param _isTypeActionFn the consumer function to run if the TYPE is correct
         * @return the T
         */
        default T ifType( Class clazz, Consumer<T> _isTypeActionFn ){
            if(isType(clazz)){
                _isTypeActionFn.accept( (T)this );
            }
            return (T)this;
        }

        /**
         * Perform the action is the entity is of the TYPE clazz and return it
         * @param clazz the class to perform the action
         * @param _isTypeActionFn the consumer function to run if the TYPE matches
         * @param _isNotTypeActionFn the consumer function to run if the TYPE does not match
         * @return the T
         */
        default T ifType( Class clazz, Consumer<T> _isTypeActionFn, Consumer<T> _isNotTypeActionFn ){
            if(isType(clazz)){
                _isTypeActionFn.accept( (T)this );
            } else{
                _isNotTypeActionFn.accept( (T)this);
            }
            return (T)this;
        }

        /**
         * is the type void
         * @return true if void
         */
        default boolean isVoid(){
            return getType().ast().isVoidType();
        }

        /**
         * Is the TYPE of this entity the same as represented where the _typeRef TYPE
         * @param type the _typeRef representation of the TYPE
         * @return true if the TYPE is the same
         */
        default boolean isType( Type type ) {
            return getType().ast().equals( type );
        }

        /**
         * Is the TYPE of this entity the same as represented where the _typeRef TYPE
         * @param type the _typeRef representation of the TYPE
         * @return true if the TYPE is the same
         */
        default boolean isType( _typeRef type ) {
            return getType().equals( type );
        }

        /**
         * Is the TYPE of this entity the same as represented where the _typeRef TYPE
         * @param clazz the Class representation of the TYPE
         * @return true if the TYPE is the same
         */
        default boolean isType( Class clazz ) {
            try{
                return isType( clazz.getCanonicalName() )|| isType(clazz.getSimpleName() );
            }catch(Exception e){
            }
            return false;
        }

        /**
         * Is the TYPE of this entity the same as represented where the String TYPE
         * @param type the String representation of the TYPE
         * @return true if the TYPE is the same
         */
        default boolean isType( String type ) {
            try{
                return isType( Ast.typeRef( type ));
            }catch(Exception e){
            }
            return false;
        }
    }
}
