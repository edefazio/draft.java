package draft.java.template;

import com.github.javaparser.ast.Node;

import draft.java._model;

import java.util.List;
import java.util.function.Consumer;

/**
 *
 * @param <Q> the TYPE of the node being queried for (likely a {@link Node} or {@link _model._node}
 */
public interface $query<Q> {

    List<Q> findAllIn(_model._node _t );

    List<Q> findAllIn(Node rootNode );

    List<? extends selected> selectAllIn(Node n );

    List<? extends selected> selectAllIn(_model._node _m );

    /**
     * Remove all matching occurrences of the template in the node and return the
     * modified node
     * @param node the root node to start search
     * @param <N> the input node TYPE
     * @return the modified node
     */
    <N extends Node> N removeIn(N node );

    /**
     *
     * @param _m the root model node to start from
     * @param <M> the TYPE of model node
     * @return the modified model node
     */
    <M extends _model._node> M removeIn(M _m );


    <N extends Node> N forAllIn(N node, Consumer<Q> modelActionFn);

    <M extends _model._node> M forAllIn(M _m, Consumer<Q> modelActionFn);

    /**
     * Replace all occurences of the template in the code with the replacement
     * (composing the replacement from the decomposed tokens in the source)
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
