package draft.java.template;

import com.github.javaparser.ast.Node;

import draft.java._model;

import java.util.List;
import java.util.function.Consumer;

/**
 * Defines a mechanism to query into code via Templates
 * 
 * @param <Q> the TYPE of the node being queried for (likely a {@link Node} or {@link _model._node}
 */
public interface $query<Q> {

    /** 
     * Find and return a List of all matching node types within _t 
     * 
     * @param _n the root _java model node to start the search (i.e. _class, _method, _
     * @return a List of Q that match the query
     */
    List<Q> listIn(_model._node _n );

    /**
     * 
     * @param astRootNode the root AST node to start the search
     * @return a List of Q matching the query
     */
    List<Q> listIn(Node astRootNode );

    /**
     * return the selections (containing the node and deconstructed parts)
     * of all matching entities within the astRootNode
     * @param astRootNode the node to start the search (TypeDeclaration, MethodDeclaration)
     * @return the selected
     */
    List<? extends selected> listSelectedIn(Node astRootNode);

    /**
     * return the selections (containing the node and deconstructed parts)
     * of all matching entities within the _j
     * @param _n the java entity (_type, _method, etc.) where to start the search
     * @return a list of the selected
     */
    List<? extends selected> listSelectedIn(_model._node _n);

    /**
     * Remove all matching occurrences of the template in the node and return the
     * modified node
     * @param astRootNode the root node to start search
     * @param <N> the input node TYPE
     * @return the modified node
     */
    <N extends Node> N removeIn(N astRootNode );

    /**
     *
     * @param _n the root java node to start from (_type, _method, etc.)
     * @param <M> the TYPE of model node
     * @return the modified model node
     */
    <M extends _model._node> M removeIn(M _n );


    /**
     * Find and execute a function on all of the matching occurrences within astRootNode
     * @param <N>
     * @param astRootNode the node to search through (CompilationUnit, MethodDeclaration, etc.)
     * @param modelActionFn the function to run upon each encounter with a matching node
     * @return the modified astRootNode
     */
    <N extends Node> N forIn(N astRootNode, Consumer<Q> modelActionFn);

    /**
     * Find and execute a function on all of the matching occurrences within astRootNode
     * @param <M>
     * @param _n the java node to start the walk
     * @param modelActionFn the function to run on all matching entities
     * @return  the modified _java node
     */
    <M extends _model._node> M forIn(M _n, Consumer<Q> modelActionFn);

    /**
     * Replace all occurences of the template in the code with the replacement
     * (composing the replacement from the Deconstruct tokens in the source)
     *
     * @return

    <M extends _model._node> M replaceIn(M _m, Template<Q> $replaceTemplate );


    <N extends Node> N replaceIn(N node, Template<Q> $replaceTemplate );

    //<M extends _model._node> M forSelectedIn(M _m, Consumer<? extends selected> selectConsumer );
    //<N extends Node> N forSelectedIn(N node, Consumer<? extends selected> selectConsumer );
    */



    interface selected{
        //Tokens getTokens();
    }
}
