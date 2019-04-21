package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.Type;
import draft.*;
import draft.java.*;
import draft.java._model._node;
import draft.java._typeDecl;
import java.util.*;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Defines a mechanism to query into code via Templates
 *
 * @param <Q> the TYPE of the node being queried for (likely a {@link Node} or
 * {@link _model._node}
 */
public interface $proto<Q> {

    
    /**
     * Find and return a List of all matching node types within _n
     *
     * @param clazz the runtime class (MUST HAVE JAVA SOURCE AVAILABLE IN CLASSPATH)
     * @return a List of Q that match the query
     */
    default List<Q> listIn(Class clazz){
        return listIn(_type.of(clazz));
    }
    
    /**
     * Find and return a List of all matching node types within _n
     *
     * @param _n the root _java model node to start the search (i.e. _class,
     * _method, _
     * @return a List of Q that match the query
     */
    List<Q> listIn(_node _n);

    /**
     *
     * @param astRootNode the root AST node to start the search
     * @return a List of Q matching the query
     */
    List<Q> listIn(Node astRootNode);

    /**
     * return the selections (containing the node and deconstructed parts) of
     * all matching entities within the astRootNode
     *
     * @param clazz runtime class (MUST HAVE .java source code in CLASSPATH)
     * @return the selected
     */
    default List<? extends selected> listSelectedIn(Class clazz){
        return listSelectedIn(_type.of(clazz));
    }
    
    /**
     * return the selections (containing the node and deconstructed parts) of
     * all matching entities within the astRootNode
     *
     * @param astRootNode the node to start the search (TypeDeclaration,
     * MethodDeclaration)
     * @return the selected
     */
    List<? extends selected> listSelectedIn(Node astRootNode);

    /**
     * return the selections (containing the node and deconstructed parts) of
     * all matching entities within the _j
     *
     * @param _n the java entity (_type, _method, etc.) where to start the
     * search
     * @return a list of the selected
     */
    List<? extends selected> listSelectedIn(_node _n);
    
    /**
     * 
     * @param clazz the runtime _type (MUST have .java SOURCE in the classpath) 
     * @return the _type with all entities matching the prototype (& constraint) removed
     */
    default _type removeIn(Class clazz){
        return removeIn(_type.of(clazz));
    } 
    
   /**
     * Remove all matching occurrences of the template in the node and return
     * the modified node
     *
     * @param astRootNode the root node to start search
     * @param <N> the input node TYPE
     * @return the modified node
     */
    <N extends Node> N removeIn(N astRootNode);

    /**
     *
     * @param _n the root java node to start from (_type, _method, etc.)
     * @param <N> the TYPE of model node
     * @return the modified model node
     */
    <N extends _node> N removeIn(N _n);

    /**
     * 
     * @param clazz the runtime Class (.java source must be on the classpath)
     * @param _nodeActionFn what to do with each entity matching the prototype
     * @return the (potentially modified) _type 
     */
    default _type forEachIn(Class clazz, Consumer<Q>_nodeActionFn ){
        return forEachIn(_type.of(clazz), _nodeActionFn);
    }
    
    /**
     * Find and execute a function on all of the matching occurrences within
     * astRootNode
     *
     * @param <N>
     * @param astRootNode the node to search through (CompilationUnit,
     * MethodDeclaration, etc.)
     * @param _nodeActionFn the function to run upon each encounter with a
     * matching node
     * @return the modified astRootNode
     */
    <N extends Node> N forEachIn(N astRootNode, Consumer<Q> _nodeActionFn);

    /**
     * Find and execute a function on all of the matching occurrences within
     * astRootNode
     *
     * @param <N>
     * @param _n the java node to start the walk
     * @param _nodeActionFn the function to run on all matching entities
     * @return the modified _java node
     */
    <N extends _node> N forEachIn(N _n, Consumer<Q> _nodeActionFn);

    /**
     * An extra layer on top of a Tokens that is specifically
     * for holding value data that COULD be Expressions, Statements and the 
     * like
     */
    public static class $args implements Map<String, Object> {

        /**
         *
         */
        private Tokens tokens;

        public static $args of(Tokens ts) {
            if (ts == null) {
                return null;
            }
            return new $args(ts);
        }

        public $args(Tokens ts) {
            this.tokens = ts;
        }

        public Object get(String name) {
            return tokens.get(name);
        }

        public Tokens asTokens() {
            return tokens;
        }

        public Expression expr(String key) {
            Object obj = get(key);
            if (obj == null || obj.toString().trim().length() == 0) {
                return null;
            }
            return Expr.of(obj.toString());
        }

        public Statement stmt(String key) {
            Object obj = get(key);
            if (obj == null || obj.toString().trim().length() == 0) {
                return null;
            }
            return Stmt.of(obj.toString());
        }

        public _typeDecl type(String key) {
            Object obj = get(key);
            if (obj == null || obj.toString().trim().length() == 0) {
                return null;
            }
            return _typeDecl.of(obj.toString());
        }

        public List<Statement> stmts(String key) {
            Object obj = get(key);
            if (obj == null) {
                return null;
            }
            if (obj.toString().trim().length() == 0) {
                return Collections.EMPTY_LIST;
            }
            return Stmt.block(obj.toString()).getStatements();
        }

        public boolean isConsistent(Tokens tokens ){
            return this.tokens.isConsistent(tokens);
        }
        
        /**
         * is the clause with the key equal to the Type?
         *
         * @param key
         * @param astType
         * @return true if
         */
        public boolean is(String key, Type astType) {
            return is(key, _typeDecl.of(astType));
        }

        public boolean is(String key, _typeDecl _t) {
            return type(key).equals(_t);
        }

        /**
         * is the clause with the key equal to the expression?
         *
         * @param key
         * @param exp
         * @return true if
         */
        public boolean is(String key, Expression exp) {
            Expression ex = expr(key);
            return exp.equals(ex);
        }

        /**
         * is the clause with the key equal to the expression?
         *
         * @param key
         * @param st
         * @return true if
         */
        public boolean is(String key, Statement st) {
            Statement stmt = stmt(key);
            return stmt.toString(Ast.PRINT_NO_COMMENTS).equals(st.toString(Ast.PRINT_NO_COMMENTS));
        }

        public boolean is($args cs) {
            return this.equals(cs);
        }

        public boolean is(Tokens tks) {
            return this.equals($args.of(tks));
        }

        public boolean is(String key, Class clazz ){
            Object o = get(key);
            if( o != null ){
                return _typeDecl.of( o.toString() ).equals(_typeDecl.of(clazz));
            }
            return false;            
        }
        
        /**
         * Is there a argument called "type" that 
         * @param clazz
         * @return 
         */
        public boolean isType( Class clazz ){
            return is("type", clazz);
        }
        
        public boolean isName( String name ){
            return is("name", name);
        }
        
        
        public boolean is(String key, Object expectedValue) {
            //this matches nullExpr or simply not there
            Object o = get(key);

            if (expectedValue == null || expectedValue instanceof NullLiteralExpr) {
                if (!(o == null || o instanceof NullLiteralExpr || o.equals("null"))) {
                    return false;
                }
                return true;
            }
            if (expectedValue instanceof String && o instanceof String) {
                //System.out.println("both equals String"+ expectedValue +" "+o );
                String v = (String) expectedValue;
                String s = (String) o;

                if (s.startsWith("\"") && s.endsWith("\"")) {
                    s = s.substring(1, s.length() - 1);
                    //return v.equals( s.substring(1, s.length() -1) );
                }
                if (v.startsWith("\"") && v.endsWith("\"")) {
                    v = v.substring(1, v.length() - 1);
                    //return v.equals( s.substring(1, s.length() -1) );
                }
                return s.equals(v);
                //return o.equals(expectedValue);
            }
            if (expectedValue instanceof Expression) {
                //System.out.println( "Value is Expression");
                return Expr.equatesTo((Expression) expectedValue, get(key));
            } else if (expectedValue instanceof String) {
                try {
                    return Expr.equatesTo(Expr.of((String) expectedValue), o);
                } catch (Exception e) {

                }
                return Objects.equals(expectedValue, get(key));
            } else if (o.getClass().equals(expectedValue.getClass())) {
                return o.equals(expectedValue);
            }
            return expectedValue.toString().equals(o);
        }

        /**
         *
         * @param keyValues
         * @return
         */
        public boolean is(Object... keyValues) {
            if (keyValues.length % 2 == 1) {
                throw new DraftException("Expected an even number of key values, got (" + keyValues.length + ")");
            }
            for (int i = 0; i < keyValues.length; i += 2) {
                String key = keyValues[i].toString();
                if (!is(key, get(key))) {
                    //System.out.println( "NOT "+key+" "+get(key));
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !o.getClass().equals($args.class)) {
                return false;
            }
            $args co = ($args) o;
            return Objects.equals(co.tokens, tokens);
        }

        @Override
        public int hashCode() {
            return this.tokens.hashCode();
        }

        @Override
        public String toString() {
            return this.tokens.toString();
        }

        @Override
        public int size() {
            return tokens.size();
        }

        @Override
        public boolean isEmpty() {
            return tokens.isEmpty();
        }

        @Override
        public boolean containsKey(Object key) {
            return tokens.containsKey(key);
        }

        @Override
        public boolean containsValue(Object value) {
            return tokens.containsValue(value);
        }

        @Override
        public Object get(Object key) {
            return tokens.get(key);
        }

        @Override
        public Object put(String key, Object value) {
            return tokens.put(key, value);
        }

        @Override
        public Object remove(Object key) {
            return tokens.remove(key);
        }

        @Override
        public void putAll(Map<? extends String, ? extends Object> m) {
            tokens.putAll(m);
        }

        @Override
        public void clear() {
            tokens.clear();
        }

        @Override
        public Set<String> keySet() {
            return tokens.keySet();
        }

        @Override
        public Collection<Object> values() {
            return tokens.values();
        }

        @Override
        public Set<Entry<String, Object>> entrySet() {
            return tokens.entrySet();
        }
    }

    /**
     * Additive constraints?
     * 
     * @param <T> 
     */
    public static class $component<T> {

        public Stencil pattern;
        public Predicate<T> constraint = t -> true;

        /**
         * 
         * @param pattern 
         */
        public $component(String pattern) {
            this.pattern = Stencil.of(pattern);
        }

        public $component(String pattern, Predicate<T> constraint) {
            this.pattern = Stencil.of(pattern);
            this.constraint = constraint;
        }
        
        /**
         * SETS or OVERRIDES the constraint
         * @param constraint
         * @return 
         */
        public $component constraint( Predicate<T> constraint ){
            this.constraint = constraint;
            return this;
        }
        
        public $component stencil(_model _javaModel ){
            
            if( _javaModel != null ){
                return stencil( Stencil.of( _javaModel.toString() ) );
            }
            return this;
        }
        
        public $component stencil(_node _javaModel ){            
            if( _javaModel != null ){
                return stencil( Stencil.of( _javaModel.toString(Ast.PRINT_NO_COMMENTS) ) );
            }
            return this;
        }
        
        public $component stencil( String... pattern ){
            return stencil(Stencil.of(Text.combine(pattern) ) );
        }
        
        public $component stencil(  Stencil pattern ){
            this.pattern = pattern;
            return this;
        } 
        
        
        public $component $( String target, String $name ){
            this.pattern = this.pattern.$(target, $name);
            return this;
        }
        
        public List<String> list$(){
            return this.pattern.list$();
        }
        
        public List<String> list$Normalized(){
            return this.pattern.list$Normalized();
        }
        
        public String compose( Translator t, Map<String,Object> keyValues ){
            return this.pattern.construct(t, keyValues);
        }
                
        /**
         * 
         * @param constraint
         * @return 
         */
        public $component addConstraint( Predicate<T> constraint ){
            this.constraint = this.constraint.and(constraint);
            return this;
        }
        
        public boolean matches(T t) {
            return decompose(t) != null;
        }

        public Tokens decompose(T t) {
            
            if (t == null) {
                /** Null is allowed IF and ONLY If the Stencil $form isMatchAll*/
                if (pattern.isMatchAll()) {                    
                    return Tokens.of(pattern.list$().get(0), "");
                }
                return null;
            }
            if (constraint.test(t)){                
                if( t instanceof _node){
                    return pattern.deconstruct( ((_node)t).toString(Ast.PRINT_NO_COMMENTS).trim() );
                }
                if( t instanceof _body ){
                    return pattern.deconstruct( ((_body)t).toString(Ast.PRINT_NO_COMMENTS).trim() );
                }
                return pattern.deconstruct( t.toString() );
            }
            return null;
        }

        public $args decomposeTo(T t, $args args ){
            if( args == null) {
                return null;                
            }
            Tokens ts = decompose(t);
            if (ts != null) {                
                if (args.isConsistent(ts)) {                   
                    args.putAll(ts);
                    return args;
                }
            }
            return null;
        }
        
        public Tokens decomposeTo(T t, Tokens all) {
            if (all == null) { /* Skip decompose if the tokens already null*/
                return null;
            }
            
            Tokens ts = decompose(t);

            if (ts != null) {                
                if (all.isConsistent(ts)) {                   
                    all.putAll(ts);
                    return all;
                }
            }
            return null;
        }
    }

    /**
     * a selected entity from a prototype query
     *
     * @param <T>
     */
    interface selected<T> {

        $args getArgs();

        default Expression expr(String key) {
            return getArgs().expr(key);
        }

        default Statement stmt(String key) {
            return getArgs().stmt(key);
        }

        default _typeDecl type(String key) {
            return getArgs().type(key);
        }

        default boolean isExpr(String key, String expressionValue) {
            try {
                return getArgs().is(key, Expr.of(expressionValue));
            } catch (Exception e) {
                return false;
            }
        }

        default boolean is(Object... keyValues) {
            return getArgs().is(keyValues);
        }

        default boolean is(String key, String value) {
            //System.out.println( "ARGS "+ getArgs() );
            return getArgs().is(key, value);
        }

        default boolean is(String key, Expression value) {
            return getArgs().is(key, value);
        }

        default boolean is(String key, Statement value) {
            return getArgs().is(key, value);
        }

        default boolean is(String key, Type value) {
            return getArgs().is(key, value);
        }
    }

    /**
     * The entity that is Selected is/has an AST node Representation
     *
     * @param <N> the specific type of AST Node that is selected
     */
    interface selectedAstNode<N extends Node> {

        /**
         * @return the selected AST Node (i.e. Expression, Statement,
         * VariableDeclarator)
         */
        N ast();
    }

    /**
     * The entity that is selected is/has a draft _node Representation (which
     * wraps the underlying Ast Node/Nodes)
     *
     * @param <M> the Draft _model representation
     */
    interface selected_model<M extends _model> {

        /**
         * @return the selected node as a _model (i.e. _method for a MethodDeclaration)
         */
        M model();
    }
}
