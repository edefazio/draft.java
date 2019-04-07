package draft.java;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.*;
import com.github.javaparser.ast.nodeTypes.*;
import com.github.javaparser.ast.nodeTypes.modifiers.NodeWithAbstractModifier;
import draft.DraftException;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Graph Traversal Algorithms for traversing nodes of a Java AST
 * for selecting / visiting the nodes, providing a simple API for operations
 * on Nodes within a Java source file (by manipulating the AST).
 *
 * The use is best illustrated by example:
 * <PRE>
 * _class _c = _class.of("aaaa.bbbb.C")
 *     .FIELDS("int x=1;", "int y=2;", "String NAME;");
 *
 * //intercept & print all of the {@link _field}s within the _class _c
 * Walk.in(_c, _field.class, f-> System.out.println(f));
 * // prints:
 * //    int x=1;
 * //    int y=2;
 * //    String NAME;
 *
 * //intercept & print all {@link _field}s within _class _c that have initial values
 * Walk.in(_c, _field.class, f-> f.hasInit(), f-> System.out.println(f));
 * // prints:
 * //    int x=1;
 * //    int y=2;
 *
 * // we are not limited to traversing based on either _model or AST classes
 * //... we can traverse the node graph looking for entities that are Node implementations
 *
 * // to find all Integer literals within the code:
 * Walk.in(_c, {@link Expr#INT_LITERAL}, i-> System.out.println(i));
 * // prints:
 * //    1
 * //    2
 *
 * // to find all types within the code:
 * Walk.in(_c, {@link Expr#TYPE}, i-> System.out.println(i));
 *  // prints:
 *  //    "int"
 *  //    "int"
 *  //    "String"
 * </PRE>
 *
 */
public enum Walk {
    ;

    /**
     * <PRE>
     *             A
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Parents:
     *    from D: B, A
     *    from E: B, A
     *    from C: A
     *    from B: A
     * </PRE>
     * @param astRootNode
     * @param targetClass
     * @param matchFn
     * @param action
     * @param <A>
     * @param <N>
     * @return
     */
    public static <A, N extends Node> N parents(
            N astRootNode, Class<A> targetClass, Predicate<A> matchFn, Consumer<A> action ) {
         return of( Node.TreeTraversal.PARENTS, astRootNode, targetClass, matchFn, action );
    }

    /**
     * <PRE>
     *             A
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Direct Children
     *     From A: B, C
     *     From B: D, E
     * </PRE>
     *
     * @param astRootNode
     * @param targetClass
     * @param matchFn
     * @param action
     * @param <A>
     * @param <N>
     * @return
     */
    public static <A, N extends Node> N directChildren(
            N astRootNode, Class<A> targetClass, Predicate<A> matchFn, Consumer<A> action ) {
        return of( Node.TreeTraversal.DIRECT_CHILDREN, astRootNode, targetClass, matchFn, action );
    }

    /**
     * <PRE>
     *             A
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Breadth-First (or Level Order) from A: A,B,C,D,E
     * </PRE>
     *
     * @param astRootNode
     * @param targetClass
     * @param matchFn
     * @param action
     * @param <A>
     * @param <N>
     * @return
     */
    public static <A, N extends Node> N breadthFirst(
            N astRootNode, Class<A> targetClass, Predicate<A> matchFn, Consumer<A> action ) {
        return of( Node.TreeTraversal.BREADTHFIRST, astRootNode, targetClass, matchFn, action );
    }

    /**
     * <PRE>
     *             A
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * PostOrder ("leaves first", or children, then parents) from A: D,E,B,C,A
     * </PRE>
     *
     * @param astRootNode
     * @param targetClass
     * @param matchFn
     * @param action
     * @param <A>
     * @param <N>
     * @return
     */
    public static <A, N extends Node> N postOrder(
            N astRootNode, Class<A> targetClass, Predicate<A> matchFn, Consumer<A> action ) {
        return of( Node.TreeTraversal.POSTORDER, astRootNode, targetClass, matchFn, action );
    }

    /**
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from A: A,B,D,E,C
     * </PRE>
     *
     * @param astRootNode
     * @param targetClass
     * @param matchFn
     * @param action
     * @param <A>
     * @param <N>
     * @return
     */
    public static <A, N extends Node> N preOrder(
            N astRootNode, Class<A> targetClass, Predicate<A> matchFn, Consumer<A> action ) {
        return of( Node.TreeTraversal.PREORDER, astRootNode, targetClass, matchFn, action );
    }

    /**
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from (A) : A,B,D,E,C
     * </PRE>
     *
     * @param astRootNode
     * @param targetClass
     * @param matchFn
     * @param action
     * @param <A>
     * @param <N>
     * @return
     */
    public static <A, N extends Node> N in(N astRootNode, Class<A> targetClass, Predicate<A> matchFn, Consumer<A> action ) {
        return of( Node.TreeTraversal.PREORDER, astRootNode, targetClass, matchFn, action );
    }


    /**
     *
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from (A) : A,B,D,E,C
     * </PRE>
     *
     * @param astRootNode the root node to start the walk
     * @param targetNodeClass the target class
     * @param nodeActionFn what to do with matching nodfes
     * @param <R> the root node type
     * @param <N> the target node type
     * @return the modified root astNode
     */
    public static <R extends Node, N extends Node> R in( R astRootNode, Class<N> targetNodeClass, Consumer<N> nodeActionFn) {
        Ast.walk(astRootNode, targetNodeClass, t -> true, nodeActionFn);
        return astRootNode;
    }


    /**
     * <PRE>
     *             A
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Parents:
     *    from D: B, A
     *    from E: B, A
     *    from C: A
     *    from B: A
     * </PRE>
     * @param _m
     * @param targetClass
     * @param matchFn
     * @param action
     * @param <A>
     * @param <M>
     * @return
     */
    public static <A, M extends _model._node> M parents(
            M _m, Class<A> targetClass, Predicate<A> matchFn, Consumer<A> action ) {
        of( Node.TreeTraversal.PARENTS, _m.ast(), targetClass, matchFn, action );
        return _m;
    }

    /**
     * <PRE>
     *            (A)
     *           /  \
     *         (B)   C
     *         / \
     *        D   E
     * Direct Children
     *     From (A): B, C
     *     From (B): D, E
     * </PRE>
     * @param _m
     * @param targetClass
     * @param matchFn
     * @param action
     * @param <A>
     * @param <M>
     * @return
     */
    public static <A, M extends _model._node> M directChildren(
            M _m, Class<A> targetClass, Predicate<A> matchFn, Consumer<A> action ) {
        of( Node.TreeTraversal.DIRECT_CHILDREN, _m.ast(), targetClass, matchFn, action );
        return _m;
    }

    /**
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Breadth-First (or Level Order) from (A): A,B,C,D,E
     * </PRE>
     *
     * @param _m
     * @param targetClass
     * @param matchFn
     * @param action
     * @param <A>
     * @param <M>
     * @return
     */
    public static <A, M extends _model._node> M breadthFirst(
            M _m, Class<A> targetClass, Predicate<A> matchFn, Consumer<A> action ) {
        of( Node.TreeTraversal.BREADTHFIRST, _m.ast(), targetClass, matchFn, action );
        return _m;
    }

    /**
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * PostOrder ("leaves first", or children, then parents) from (A) : D,E,B,C,A
     * </PRE>
     *
     * @param _m
     * @param targetClass
     * @param matchFn
     * @param action
     * @param <A>
     * @param <M>
     * @return
     */
    public static <A, M extends _model._node> M postOrder(
            M _m, Class<A> targetClass, Predicate<A> matchFn, Consumer<A> action ) {
        of( Node.TreeTraversal.POSTORDER, _m.ast(), targetClass, matchFn, action );
        return _m;
    }

    /**
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from (A) : A,B,D,E,C
     * </PRE>
     *
     * @param _m
     * @param targetClass
     * @param matchFn
     * @param action
     * @param <A>
     * @param <M>
     * @return
     */
    public static <A, M extends _model._node> M preOrder(
            M _m, Class<A> targetClass, Predicate<A> matchFn, Consumer<A> action ) {
        of( Node.TreeTraversal.PREORDER, _m.ast(), targetClass, matchFn, action );
        return _m;
    }


    /**
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from (A) : A,B,D,E,C
     * </PRE>
     *
     * @param _m
     * @param targetClass
     * @param matchFn
     * @param action
     * @param <A>
     * @param <M>
     * @return
     */
    public static <A, M extends _model._node> M in(
            M _m, Class<A> targetClass, Predicate<A> matchFn, Consumer<A> action ) {
        of( Node.TreeTraversal.PREORDER, _m.ast(), targetClass, matchFn, action );
        return _m;
    }


    /**
     * <PRE>
     *             A
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Parents:
     *    from D: B, A
     *    from E: B, A
     *    from C: A
     *    from B: A
     * </PRE>
     * @param _m
     * @param targetClass
     * @param action
     * @param <A>
     * @param <M>
     * @return
     */
    public static <A, M extends _model._node> M parents(
            M _m, Class<A> targetClass, Consumer<A> action ) {
        of( Node.TreeTraversal.PARENTS, _m.ast(), targetClass, t->true, action );
        return _m;
    }

    /**
     * <PRE>
     *             A
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Direct Children
     *     From A: B, C
     *     From B: D, E
     * </PRE>
     *
     * @param _m
     * @param targetClass
     * @param action
     * @param <A>
     * @param <M>
     * @return
     */
    public static <A, M extends _model._node> M directChildren(
            M _m, Class<A> targetClass, Consumer<A> action ) {
        of( Node.TreeTraversal.DIRECT_CHILDREN, _m.ast(), targetClass, t->true, action );
        return _m;
    }

    /**
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Breadth-First (or Level Order) from A: A,B,C,D,E
     * </PRE>
     * @param _m
     * @param targetClass
     * @param action
     * @param <A>
     * @param <M>
     * @return
     */
    public static <A, M extends _model._node> M breadthFirst(
            M _m, Class<A> targetClass, Consumer<A> action ) {
        of( Node.TreeTraversal.BREADTHFIRST, _m.ast(), targetClass, t->true, action );
        return _m;
    }

    /**
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * PostOrder ("leaves first", or children, then parents) from (A) : D,E,B,C,A
     * </PRE>
     * @param _m
     * @param targetClass
     * @param action
     * @param <A>
     * @param <M>
     * @return
     */
    public static <A, M extends _model._node> M postOrder(
            M _m, Class<A> targetClass, Consumer<A> action ) {
        of( Node.TreeTraversal.POSTORDER, _m.ast(), targetClass, t->true, action );
        return _m;
    }

    /**
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from A: A,B,D,E,C
     * </PRE>
     * @param _m
     * @param targetClass
     * @param action
     * @param <A>
     * @param <M>
     * @return
     */
    public static <A, M extends _model._node> M preOrder(
            M _m, Class<A> targetClass, Consumer<A> action ) {
        of( Node.TreeTraversal.PREORDER, _m.ast(), targetClass, t->true, action );
        return _m;
    }

    /**
     * A preorder walk within the _model _m for all entities that are of targetClass
     * and calls the action on them
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from A : A,B,D,E,C
     * </PRE>
     * @param _m
     * @param targetClass
     * @param action
     * @param <A>
     * @param <M> the supplied model node
     * @return
     */
    public static <A, M extends _model._node> M in(
            M _m, Class<A> targetClass, Consumer<A> action ) {
        of( Node.TreeTraversal.PREORDER, _m.ast(), targetClass, t->true, action );
        return _m;
    }

    /**
     * <PRE>
     *             A
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Parents:
     *    from D: B, A
     *    from E: B, A
     *    from C: A
     *    from B: A
     * </PRE>
     * @param _m
     * @param action
     * @param <M>
     * @return
     */
    public static <M extends _model._node> M parents(M _m, Consumer<Node> action ) {
        of( Node.TreeTraversal.PARENTS, _m.ast(), Node.class, t->true, action );
        return _m;
    }

    /**
     * <PRE>
     *            (A)
     *           /  \
     *         (B)   C
     *         / \
     *        D   E
     * Direct Children
     *     From (A): B, C
     *     From (B): D, E
     * </PRE>
     *
     * @param _m
     * @param action
     * @param <M>
     * @return
     */
    public static <M extends _model._node> M directChildren(M _m, Consumer<Node> action ) {
        of( Node.TreeTraversal.DIRECT_CHILDREN, _m.ast(), Node.class, t->true, action );
        return _m;
    }

    /**
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Breadth-First (or Level Order) from (A): A,B,C,D,E
     * </PRE>
     * @param _m
     * @param action
     * @param <M>
     * @return
     */
    public static <M extends _model._node> M breadthFirst(M _m, Consumer<Node> action ) {
        of( Node.TreeTraversal.BREADTHFIRST, _m.ast(), Node.class, t->true, action );
        return _m;
    }

    /**
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * PostOrder ("leaves first", or children, then parents) from (A) : D,E,B,C,A
     * </PRE>
     * @param _m
     * @param action
     * @param <M>
     * @return
     */
    public static <M extends _model._node> M postOrder(M _m, Consumer<Node> action ) {
        of( Node.TreeTraversal.POSTORDER, _m.ast(), Node.class, t->true, action );
        return _m;
    }

    /**
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from (A): A,B,D,E,C
     * </PRE>
     * @param _m
     * @param action
     * @param <M>
     * @return
     */
    public static <M extends _model._node> M preOrder(M _m, Consumer<Node> action ) {
        of( Node.TreeTraversal.PREORDER, _m.ast(), Node.class, t->true, action );
        return _m;
    }

    /**
     * A preorder walk within the _model node _m
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from (A) : A,B,D,E,C
     * </PRE>
     * @param _m
     * @param action
     * @param <M> the supplied model node
     * @return
     */
    public static <M extends _model._node> M in(M _m, Consumer<Node> action ) {
        of( Node.TreeTraversal.PREORDER, _m.ast(), Node.class, t->true, action );
        return _m;
    }


    /**
     * A preorder walk within the _model node _m
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from (A) : A,B,D,E,C
     * </PRE>
     * @param _m the model
     * @param predicate node matchFn
     * @param action the action to take on the predicate
     * @param <M>
     * @return the (modified)
     */
    public static <M extends _model._node> M in(M _m, Predicate<Node> predicate, Consumer<Node> action ) {
        of( Node.TreeTraversal.PREORDER, _m.ast(), Node.class, predicate, action );
        return _m;
    }


    /**
     * Walk the nodes within & collect all nodes that match all the predicate and return them in order
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from (A) : A,B,D,E,C
     * </PRE>
     * @param _m
     * @param predicate
     * @param <M>
     * @return
     */
    public static <M extends _model._node> List<Node> list(M _m, Predicate<Node> predicate ) {
        List<Node> found = new ArrayList<>();
        of( Node.TreeTraversal.PREORDER, _m.ast(), Node.class, predicate, f -> found.add(f) );
        return found;
    }

    /**
     * Perform a preorder walk through the _model _m, returning a listing all instances of
     * the targetClass found
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from (A) : A,B,D,E,C
     * </PRE>
     *
     * @param _m the _model entity to search
     * @param targetClass the target Class to search for (can be a Node class, a _model class)
     * @param <A> the target TYPE
     * @param <M> the model entity (i.e. _class, _method, _constructor, _staticBlock)
     * @return
     */
    public static <A, M extends _model._node> List<A> list(M _m, Class<A> targetClass ) {
        List<A> found = new ArrayList<>();
        of( Node.TreeTraversal.PREORDER, _m.ast(), targetClass, t->true, f -> found.add(f) );
        return found;
    }

    /**
     * Perform a preOrder walk through the astRootNode, returning a listing all instances of
     * the targetClass found
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from (A) : A,B,D,E,C
     * </PRE>
     *
     * @param astRootNode the _model entity to search
     * @param targetClass the target Class to search for (can be a Node class, a _model class)
     * @param <A> the target TYPE
     * @param <R> the Ast Node (i.e. EnumDeclaration, MethodDeclaration, ConstructorDeclaration)
     * @return the list of
     */
    public static <A, R extends Node> List<A> list(R astRootNode, Class<A> targetClass ) {

        List<A> found = new ArrayList<>();
        of( Node.TreeTraversal.PREORDER, astRootNode, targetClass, t->true, f -> found.add(f) );
        return found;
    }

    /**
     * Perform a preOrder walk through the astRootNode, returning a listing all instances of
     * the targetClass found
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from A : A,B,D,E,C
     * </PRE>
     * @param _m the _model entity to search
     * @param targetClass the target Class to search for (can be a Node class, a _model class)
     * @param <A> the target TYPE
     * @param <M> the model entity (i.e. _class, _method, _constructor, _staticBlock)
     * @param matchFn predicate for selecting nodes to collect
     * @return the list of
     */
    public static <A, M extends _model._node> List<A> list(
            M _m, Class<A> targetClass, Predicate<A> matchFn ) {

        List<A> found = new ArrayList<>();
        of( Node.TreeTraversal.PREORDER, _m.ast(), targetClass, matchFn, f -> found.add(f) );
        return found;
    }

    /**
     * Perform a PreOrder walk through the astRootNode, returning a listing all instances of
     * the targetClass found that match the matchFn
     * <PRE>
     *            (A)
     *           /  \
     *          B    C
     *         / \
     *        D   E
     * Preorder (Parent, then children) from (A) : A,B,D,E,C
     * </PRE>
     * @param astRootNode the Ast node to start walking
     * @param targetClass the target Class to search for (can be a Node class, a _model class)
     * @param matchFn the lambda for matching specific instances of the targetClass
     * @param <A> the target TYPE
     * @param <R> the Ast Root Node Type (i.e. EnumDeclaration, MethodDeclaration, ConstructorDeclaration)
     * @return the list of
     */
    public static <A, R extends Node> List<A> list(
            R astRootNode, Class<A> targetClass, Predicate<A> matchFn ) {

        List<A> found = new ArrayList<>();
        of( Node.TreeTraversal.PREORDER, astRootNode, targetClass, matchFn, f -> found.add(f) );
        return found;
    }

    /**
     * Starting at astRootNode walk the nodes using the walk traversal strategy provided
     * @see Ast#WALK_POST_ORDER
     * @see Ast#WALK_PRE_ORDER
     * @see Ast#WALK_BREADTH_FIRST
     * @see Ast#WALK_PARENTS
     * @see Ast#WALK_DIRECT_CHILDREN
     *
     * intercepting Nodes that implement the targetClass and collecting nodes that
     * pass the matchFn in a List and returning the list.
     *
     * @param tt the walk traversal strategy for walking nodes in the AST
     * @param astRootNode the starting node to start the walk
     * @param targetClass the target classes to intercept
     * @param matchFn function for selecting which nodes to accept and add to the list
     * @param <A>
     * @param <R>
     * @return
     */
    public static <A, R extends Node> List<A> list(
            Node.TreeTraversal tt, R astRootNode, Class<A> targetClass, Predicate<A> matchFn ) {

        List<A> found = new ArrayList<>();
        of( tt, astRootNode, targetClass, matchFn, f -> found.add(f) );
        return found;
    }

    /**
     * Given an AST root node n, and a Tree Traversal Strategy (default is PreOrder),
     * walk the AST tree and match for EVERY occurrence of the target class
     * that matches the matchFn and perform an action on it.
     * NOTE: this can query for targetClasses:
     * <UL>
     * <LI>AST Node classes
     * ({@link com.github.javaparser.ast.expr.Expression}, {@link com.github.javaparser.ast.stmt.Statement}, {@link MethodDeclaration}, {@link EnumDeclaration}...)
     *
     * <LI>AST Node TYPE interfaces(com.github.javaparser.ast.nodeTypes.*)
     * {@link NodeWithAnnotations}, {@link NodeWithCondition}, ...)
     *
     * <LI>Comment types
     * ({@link Comment}, {@link JavadocComment}, {@link LineComment}, {@link BlockComment}...)
     *
     * <LI>Logical classes
     * ({@link _field}, {@link _method}, {@link _enum._constant}...)
     *
     * <LI>Logical interfaces
     * ({@link _javadoc._hasJavadoc}, {@link _method._hasMethods}, {@link _anno._hasAnnos}, ...)
     * </UL>
     *
     * @param <A> the target Class TYPE ..we need this BECAUSE Node classes/interfaces dont have a common ancestor
     *           (Node is the base class for Ast Node entities, but NodeWithAnnotations, interfaces do not etc.
     * @param <N> the Node class (if the target class is a Node class)
     * @param <R> the root Node type
     * @param <L> the logical class (if the target class is a _model class)
     * @param tt the Tree Traversal Strategy ({@link Node.TreeTraversal#POSTORDER}, {@link Node.TreeTraversal#PREORDER}, {@link Node.TreeTraversal#PARENTS}, {@link Node.TreeTraversal#BREADTHFIRST}}
     * @param astRootNode the root AST node to search
     * @param targetClass the target Class (or interface) to intercept
     * @param matchFn the predicate for testing the intercepted Nodes/logical entities
     * @param action the action to take on nodes that match the matchFn
     */
    public static <A, N extends Node, L extends _model, R extends Node> R of(
            Node.TreeTraversal tt, R astRootNode, Class<A> targetClass, Predicate<A> matchFn, Consumer<A> action ) {

        if( Node.class.isAssignableFrom( targetClass ) //Stmts and Expressions
                || targetClass.getPackage().getName().equals( NodeWithAnnotations.class.getPackage().getName() ) // (NodeWithAnnotations, NodeWithArguments, NodeWithBlockStmt, ...
                || targetClass.getPackage().getName().equals( NodeWithAbstractModifier.class.getPackage().getName() )) { //NodeWithAbstractModifier, NodeWithStaticModifier, ...

            if( Comment.class.isAssignableFrom( targetClass ) ) { //we have to Walk Comments Differently
                //because comments can be Orphaned and never touched when we use the normal Ast walk
                if( targetClass == Comment.class ) {
                    comments( astRootNode, (Predicate<Comment>)matchFn, (Consumer<Comment>)action );
                }
                else if( targetClass == JavadocComment.class ) {
                    comments( astRootNode, JavadocComment.class, (Predicate<JavadocComment>)matchFn, (Consumer<JavadocComment>)action );
                }
                else if( targetClass == BlockComment.class ) {
                    comments( astRootNode, BlockComment.class, (Predicate<BlockComment>)matchFn, (Consumer<BlockComment>)action );
                }else {
                    comments(astRootNode, LineComment.class, (Predicate<LineComment>) matchFn, (Consumer<LineComment>) action);
                }
                return astRootNode;
            }else {
                Ast.walk(tt,
                        astRootNode,
                        (Class<N>) targetClass,
                        e -> targetClass.isAssignableFrom(e.getClass())
                                && matchFn.test((A) e),
                        (Consumer<N>) action);
                return astRootNode;
            }
        }//maybe
        else{
            return model(tt, astRootNode, (Class<L>)targetClass, (Predicate<L>)matchFn, (Consumer<L>)action);
        }
    }

    /**
     * A Walk that resolves {@link _model} classes (as apposed to AST {@link Node} implementation
     * this requires "special work" building temporary ad-hoc models (i.e. _model, _field, _class, _parameter)
     * to test against predicates
     *
     * NOTE: this ALSO works with {@link _model} interfaces like {@link _model}, {@link _method._hasMethods}
     * {@link _parameter._hasParameters} etc.
     *
     * @param tt the traversal TYPE
     * @param astRootNode the root AST node
     * @param _modelClass the class of the model
     * @param _modelMatchFn
     * @param _modelAction
     * @param <L>
     * @param <R> the Root node type
     * @return
     */
    public static <L extends _model, R extends Node> R model(
            Node.TreeTraversal tt, R astRootNode, Class<L> _modelClass, Predicate<L> _modelMatchFn, Consumer<L> _modelAction ){
        if( _java._JAVA_TO_AST_NODE_CLASSES.containsKey( _modelClass ) ) {
            //System.out.println("Node Classes ");
            // _anno.class, AnnotationExpr.class
            // _annotation._element.class, AnnotationMemberDeclaration.class
            // _enum._constant.class, EnumConstantDeclaration.class
            // _constructor.class, ConstructorDeclaration.class
            // _field.class, FieldDeclaration.class
            // _method.class, MethodDeclaration.class
            // _parameter.class, Parameter.class
            // _receiverParameter.class, ReceiverParameter.class
            // _staticBlock.class, InitializerDeclaration.class
            // _typeParameter.class, TypeParameter.class
            // _typeRef.class, Type.class
            // _type, TypeDeclaration.class
            // _class, ClassOrInterfaceDeclaration.class
            // _enum EnumDeclaration.class
            // _interface ClassOrInterfaceDeclaration.class
            // _annotation AnnotationDeclaration.class
            //class switch would be nice here
            Ast.walk( tt,
                    astRootNode,
                    _java._JAVA_TO_AST_NODE_CLASSES.get( _modelClass ),
                    t ->true,
                    a -> {
                        L logical = (L)_java.of( a );
                        if( _modelMatchFn.test( logical ) ) {
                            _modelAction.accept( logical );
                        }
                    } );
            return astRootNode;
        }
        else if( _modelClass == _model._member.class ) {
            Ast.walk( tt,
                    astRootNode,
                    BodyDeclaration.class,
                    t-> !t.isInitializerDeclaration(), //static Blocks are not members
                    n-> {
                        _model._member _n = (_model._member)_java.of(n);

                        if( ((Predicate<_model._member>)_modelMatchFn).test( _n) ){
                            ((Consumer<_model._member>)_modelAction).accept( _n);
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _model._node.class ) {
            Ast.walk( tt,
                    astRootNode,
                    BodyDeclaration.class,
                    t-> true,
                    n-> {
                        _model._node _n = (_model._node)_java.of(n);

                        if( ((Predicate<_model._node>)_modelMatchFn).test( _n) ){
                            ((Consumer<_model._node>)_modelAction).accept( _n);
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _body.class ) {
            Ast.walk( tt,
                    astRootNode,
                    Node.class,
                    n-> n instanceof NodeWithBlockStmt || n instanceof NodeWithOptionalBlockStmt,
                    n-> {
                        _body _b = null;
                        if( n instanceof NodeWithBlockStmt ){
                            _b = _body.of( (NodeWithBlockStmt)n );
                        }else{
                            _b = _body.of( (NodeWithOptionalBlockStmt)n );
                        }
                        if( ((Predicate<_body>)_modelMatchFn).test( _b) ){
                            ((Consumer<_body>)_modelAction).accept( _b);
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _body._hasBody.class ) {
            Ast.walk( tt,
                    astRootNode,
                    Node.class,
                    n-> n instanceof NodeWithBlockStmt || n instanceof NodeWithOptionalBlockStmt,
                    n-> {
                        _body._hasBody _hb = (_body._hasBody)_java.of(n);

                        if( ((Predicate<_body._hasBody>)_modelMatchFn).test( _hb) ){
                            ((Consumer<_body._hasBody>)_modelAction).accept( _hb);
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _anno._hasAnnos.class ) {
            Ast.walk(tt,
                    astRootNode,
                    Node.class,
                    //NOTE: we DONT use NodeWithAnnotations because Annotations CAN be applied in interesting ways
                    // within the code BODY (i.e. on casts) but logically not... to operate on Cast ANNOTATIONS
                    // do so with the AST BODY
                    n -> n instanceof BodyDeclaration || n instanceof Parameter || n instanceof ReceiverParameter,
                    n ->{
                        _anno._hasAnnos ha = (_anno._hasAnnos)_java.of( n );
                        if( ((Predicate<_anno._hasAnnos>)_modelMatchFn).test( ha ) ){
                            ((Consumer<_anno._hasAnnos>)_modelAction).accept(ha);
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _constructor._hasConstructors.class ) {
            Ast.walk( tt,
                    astRootNode,
                    Node.class,
                    n-> n instanceof NodeWithConstructors,
                    n-> {
                        _constructor._hasConstructors hc = (_constructor._hasConstructors)_java.of( n );

                        if( ((Predicate<_constructor._hasConstructors>)_modelMatchFn).test( hc) ){
                            ((Consumer<_constructor._hasConstructors>)_modelAction).accept( hc );
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _field._hasFields.class ) {
            Ast.walk( tt,
                    astRootNode,
                    Node.class,
                    n-> n instanceof TypeDeclaration || n instanceof EnumConstantDeclaration,
                    n-> {
                        _field._hasFields hf = (_field._hasFields)_java.of( n );

                        if( ((Predicate<_field._hasFields>)_modelMatchFn).test( hf) ){
                            ((Consumer<_field._hasFields>)_modelAction).accept( hf );
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _javadoc._hasJavadoc.class ) {
            Ast.walk( tt,
                    astRootNode,
                    Node.class,
                    n-> n instanceof NodeWithJavadoc,
                    n-> {
                        _javadoc._hasJavadoc hf = (_javadoc._hasJavadoc)_java.of( n );

                        if( ((Predicate<_javadoc._hasJavadoc>)_modelMatchFn).test( hf) ){
                            ((Consumer<_javadoc._hasJavadoc>)_modelAction).accept( hf );
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _method._hasMethods.class ) {
            Ast.walk( tt,
                    astRootNode,
                    Node.class,
                    n-> n instanceof EnumConstantDeclaration || n instanceof ClassOrInterfaceDeclaration || n instanceof EnumDeclaration,
                    n-> {
                        _method._hasMethods hm = (_method._hasMethods)_java.of( n );

                        if( ((Predicate<_method._hasMethods>)_modelMatchFn).test( hm) ){
                            ((Consumer<_method._hasMethods>)_modelAction).accept( hm );
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _modifiers._hasModifiers.class ) {
            Ast.walk( tt,
                    astRootNode,
                    Node.class,
                    n-> n instanceof NodeWithModifiers,
                    n-> {
                        _modifiers._hasModifiers  hm = (_modifiers._hasModifiers)_java.of( n );

                        if( ((Predicate<_modifiers._hasModifiers>)_modelMatchFn).test( hm) ){
                            ((Consumer<_modifiers._hasModifiers>)_modelAction).accept( hm );
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _parameter._hasParameters.class ) {
            Ast.walk( tt,
                    astRootNode,
                    Node.class,
                    n-> n instanceof NodeWithParameters,
                    n-> {
                        _parameter._hasParameters  hm = (_parameter._hasParameters)_java.of( n );

                        if( ((Predicate<_parameter._hasParameters>)_modelMatchFn).test( hm) ){
                            ((Consumer<_parameter._hasParameters>)_modelAction).accept( hm );
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _receiverParameter._hasReceiverParameter.class ) {
            Ast.walk( tt,
                    astRootNode,
                    Node.class,
                    n-> n instanceof MethodDeclaration || n instanceof ConstructorDeclaration,
                    n-> {
                        _receiverParameter._hasReceiverParameter hm = (_receiverParameter._hasReceiverParameter)_java.of( n );

                        if( ((Predicate<_receiverParameter._hasReceiverParameter>)_modelMatchFn).test( hm) ){
                            ((Consumer<_receiverParameter._hasReceiverParameter>)_modelAction).accept( hm );
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _staticBlock._hasStaticBlocks.class ) {
            Ast.walk( tt,
                    astRootNode,
                    Node.class,
                    n-> n instanceof ClassOrInterfaceDeclaration || n instanceof EnumDeclaration,
                    n-> {
                        _staticBlock._hasStaticBlocks hsb = null;
                        if( n instanceof ClassOrInterfaceDeclaration){
                            ClassOrInterfaceDeclaration coid = (ClassOrInterfaceDeclaration)n;
                            if( !coid.isInterface() ) {
                                hsb = _class.of((ClassOrInterfaceDeclaration) n);
                            }
                        } else {
                            hsb = _enum.of( (EnumDeclaration)n);
                        }
                        if( hsb != null && ((Predicate<_staticBlock._hasStaticBlocks>)_modelMatchFn).test( hsb) ){
                            ((Consumer<_staticBlock._hasStaticBlocks>)_modelAction).accept( hsb );
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _throws._hasThrows.class ) {
            Ast.walk( tt,
                    astRootNode,
                    Node.class,
                    n-> n instanceof NodeWithThrownExceptions,
                    n-> {
                        _throws._hasThrows ht = (_throws._hasThrows)_java.of(n);

                        if( ((Predicate<_throws._hasThrows>)_modelMatchFn).test( ht) ){
                            ((Consumer<_throws._hasThrows>)_modelAction).accept( ht );
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _typeParameter._hasTypeParameters.class ) {
            Ast.walk( tt,
                    astRootNode,
                    Node.class,
                    n-> n instanceof NodeWithTypeParameters,
                    n-> {
                        _typeParameter._hasTypeParameters ht = (_typeParameter._hasTypeParameters)_java.of(n);

                        if( ((Predicate<_typeParameter._hasTypeParameters>)_modelMatchFn).test( ht) ){
                            ((Consumer<_typeParameter._hasTypeParameters>)_modelAction).accept( ht );
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _throws.class ) {
            Ast.walk( tt,
                    astRootNode,
                    Node.class,
                    n -> n instanceof MethodDeclaration || n instanceof ConstructorDeclaration,
                    n -> {
                        _throws _t = _throws.of( (NodeWithThrownExceptions)n);

                        if( ((Predicate<_throws>)_modelMatchFn).test( _t) ){
                            ((Consumer<_throws>)_modelAction).accept( _t );
                        }
                    });
            return astRootNode;
        }
        else if( _modelClass == _typeParameter._typeParameters.class ) {
            Ast.walk( tt,
                    astRootNode,
                    Node.class,
                    n -> n instanceof MethodDeclaration || n instanceof ConstructorDeclaration,
                    n -> {
                        _throws _t = _throws.of( (NodeWithThrownExceptions)n);

                        if( ((Predicate<_throws>)_modelMatchFn).test( _t) ){
                            ((Consumer<_throws>)_modelAction).accept( _t );
                        }
                    });
            return astRootNode;
        }
        throw new DraftException( "Could not convert Node of TYPE " + _modelClass + " to _model TYPE" );
    }


    /**
     * Given an AST node, walk check its comments (in order)
     *
     * @param astRootNode
     * @param commentActionFn
     */
    public static void comments(Node astRootNode, Consumer<Comment> commentActionFn) {
        comments(astRootNode, t -> true, commentActionFn);
    }

    public static <C extends Comment> void comments(
            Node astRootNode, Class<C> commentClass, Predicate<C> commentMatchFn, Consumer<C> commentActionFn) {
        if (astRootNode instanceof NodeWithJavadoc && commentClass == JavadocComment.class) {
            NodeWithJavadoc nwj = (NodeWithJavadoc) astRootNode;
            if (nwj.getJavadocComment().isPresent()) {
                if (commentMatchFn.test((C) nwj.getJavadocComment().get())) {
                    commentActionFn.accept((C) nwj.getJavadocComment().get());
                }
            }
        }
        List<Comment> acs = astRootNode.getAllContainedComments();
        Collections.sort(acs, new CommentPositionComparator());
        acs.stream()
                .filter(c -> commentClass.isAssignableFrom(c.getClass())
                        && commentMatchFn.test((C) c))
                .forEach(c -> commentActionFn.accept((C) c));
    }

    /**
     * @param astRootNode
     * @param commentMatchFn
     * @param commentActionFn
     */
    public static void comments(
            Node astRootNode, Predicate<Comment> commentMatchFn, Consumer<Comment> commentActionFn) {
        if (astRootNode == null) {
            return;
        }
        if (astRootNode instanceof NodeWithJavadoc) {
            NodeWithJavadoc nwj = (NodeWithJavadoc) astRootNode;
            if (nwj.getJavadocComment().isPresent()) {
                if (commentMatchFn.test((Comment) nwj.getJavadocComment().get())) {
                    commentActionFn.accept((Comment) nwj.getJavadocComment().get());
                }
            }
        }
        List<Comment> acs = astRootNode.getAllContainedComments();
        List<Comment> ocs = astRootNode.getOrphanComments();
        // System.out.println( "ACS = "+acs.size()+" OCS "+ ocs.size());
        acs.addAll(ocs);
        Collections.sort(acs, new CommentPositionComparator());
        acs.stream()
                .filter(commentMatchFn).forEach(commentActionFn);
    }


    /**
     * Comparator for Comments within an AST node that organizes based on the
     * start position
     */
    public static class CommentPositionComparator implements Comparator<Comment> {

        @Override
        public int compare(Comment o1, Comment o2) {
            if (o1.getBegin().isPresent() && o2.getBegin().isPresent()) {
                return o1.getBegin().get().compareTo(o2.getBegin().get());
            }
            //if one or the other doesnt have a begin
            // put the one WITHOUT a being BEFORE the other
            // if neither have a being, return
            if (!o1.getBegin().isPresent() && !o2.getBegin().isPresent()) {
                return 0;
            }
            if (o1.getBegin().isPresent()) {
                return -1;
            }
            return 1;
        }
    }

    /**
     * list all comments within this astRootNode (including the comment applied
     * to the astRootNode if the AstRootNode is an instance of {@link NodeWithJavadoc}
     *
     * @param astRootNode the root node to look through
     * @return a list of all comments on or underneath the node
     */
    public static List<Comment> listComments(Node astRootNode) {
        List<Comment> found = new ArrayList<>();
        Walk.comments(astRootNode, c -> found.add(c));
        return found;
    }

    /**
     * list all comments within this astRootNode that match the predicate
     * (including the comment applied to the astRootNode if the AstRootNode is
     * an instance of {@link NodeWithJavadoc})
     *
     * @param astRootNode    the root node to look through
     * @param commentMatchFn matching function for comments
     * @return a list of all comments on or underneath the node
     */
    public static List<Comment> listComments(Node astRootNode, Predicate<Comment> commentMatchFn) {
        List<Comment> found = new ArrayList<>();
        Walk.comments(astRootNode, c -> {
            if (commentMatchFn.test(c)) {
                found.add(c);
            }
        });
        return found;
    }

    /**
     * @param <C>                the comment class
     * @param astRootNode        the root node to start the search
     * @param commentTargetClass the TYPE of comment ({@link Comment},
     *                           {@link LineComment}, {@link JavadocComment}, {@link BlockComment})
     * @param commentMatchFn     predicate for selecting comments
     * @return a list of matching comments
     */
    public static <C extends Comment> List<C> listComments(
            Node astRootNode, Class<C> commentTargetClass, Predicate<C> commentMatchFn) {

        List<C> found = new ArrayList<>();
        Walk.comments(astRootNode, c -> {
            if (commentTargetClass.isAssignableFrom(c.getClass())) {
                C cc = (C) c;
                if (commentMatchFn.test(cc)) {
                    found.add(cc);
                }
            }
        });
        return found;
    }
}
