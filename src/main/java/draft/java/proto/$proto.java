package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.Type;
import draft.*;
import draft.java.*;
import draft.java._model._node;
import draft.java._typeRef;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Defines a mechanism to query into code via Templates
 *
 * @param <Q> the TYPE of the node being queried for (likely a {@link Node} or
 * {@link _model._node}
 */
public interface $proto<Q> {

    public interface $member{
        
    }
    
    /**
     * 
     * @param clazz
     * @return 
     */
    default Q firstIn(Class clazz){
        return firstIn( _type.of(clazz) );
    }
    
    /**
     * Find the first instance matching the prototype instance within the node
     * @param _n the "top" draft node
     * @return  the first matching instance or null if none is found
     */
    default Q firstIn(_node _n){
        return firstIn(_n.ast());
    }
    
    /**
     * 
     * @param astRootNode
     * @return the first matching instance or null
     */
    Q firstIn(Node astRootNode);
    
    /**
     * 
     * @param <S>
     * @param clazz
     * @return 
     */
    default <S extends selected> S selectFirstIn( Class clazz ){
        return selectFirstIn(_type.of(clazz));
    }
    
    /**
     * 
     * @param <S>
     * @param _n
     * @return 
     */
    default <S extends selected> S selectFirstIn( _node _n ){
        return selectFirstIn( _n.ast() );    
    }
    
    /**
     * Selects the first instance
     * @param <S>
     * @param n
     * @return 
     */
    <S extends selected> S selectFirstIn( Node n );
    
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
    default List<Q> listIn(_node _n) {
        return listIn(_n.ast());
    }
    
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
     * all matching entities within the _j
     *
     * @param _n the java entity (_type, _method, etc.) where to start the
     * search
     * @return a list of the selected
     */
    default List<? extends selected> listSelectedIn(_node _n){
        return listSelectedIn(_n.ast());
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
     * @param _n the java node to start the walk
     * @param _nodeActionFn the function to run on all matching entities
     * @return the modified _java node
     */
    default <N extends _node> N forEachIn(N _n, Consumer<Q> _nodeActionFn){
        forEachIn(_n.ast(), _nodeActionFn);
        return _n;
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
     * 
     * @param <N>
     * @param clazz
     * @return 
     */
    default <N extends Node> int count( Class clazz ){
        return count( _type.of(clazz));
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @return 
     */
    default <N extends Node> int count( N astNode ){
        AtomicInteger ai = new AtomicInteger(0);
        forEachIn( astNode, e -> ai.incrementAndGet() );
        return ai.get();
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @return 
     */
    default <N extends _node> int count(N _n ){
        AtomicInteger ai = new AtomicInteger(0);
        forEachIn( _n, e -> ai.incrementAndGet() );
        return ai.get();
    }
    
    /**
     * 
     * @param clazz the runtime _type (MUST have .java SOURCE in the classpath) 
     * @return the _type with all entities matching the prototype (& constraint) removed
     */
    default _type removeIn(Class clazz){
        return removeIn(_type.of(clazz));
    } 
    
    /**
     *
     * @param _n the root java node to start from (_type, _method, etc.)
     * @param <N> the TYPE of model node
     * @return the modified model node
     */
    default <N extends _node> N removeIn(N _n){
        removeIn(_n.ast());
        return _n;
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
     * An extra layer on top of a Tokens that is specifically
     * for holding value data that COULD be Expressions, Statements and the 
     * like
     */
    public static class $nameValues implements Map<String, Object> {

        /**
         *
         */
        private Tokens tokens;

        public static $nameValues of(){
            return new $nameValues( Tokens.of() ); 
        }
        
        public static $nameValues of(Tokens ts) {
            if (ts == null) {
                return null;
            }
            return new $nameValues(ts);
        }

        public $nameValues(Tokens ts) {
            this.tokens = ts;
        }

        public Object get(String $name) {
            return tokens.get($name);
        }

        public Tokens asTokens() {
            return tokens;
        }

        public Expression expr(String $name) {
            Object obj = get($name);
            if (obj == null || obj.toString().trim().length() == 0) {
                return null;
            }
            return Expr.of(obj.toString());
        }

        public Statement stmt(String $name) {
            Object obj = get($name);
            if (obj == null || obj.toString().trim().length() == 0) {
                return null;
            }
            return Stmt.of(obj.toString());
        }

        public _typeRef type(String $name) {
            Object obj = get($name);
            if (obj == null || obj.toString().trim().length() == 0) {
                return null;
            }
            return _typeRef.of(obj.toString());
        }

        /**
         * Reads the data from the $nameValues and parse & return the data as
         * a List of Statements
         * @param $name
         * @return 
         */
        public List<Statement> stmts(String $name) {
            Object obj = get($name);
            if (obj == null) {
                return null;
            }
            if (obj.toString().trim().length() == 0) {
                return Collections.EMPTY_LIST;
            }
            return Stmt.block(obj.toString()).getStatements();
        }

        /**
         * 
         * @param tokens
         * @return 
         */
        public boolean isConsistent(Tokens tokens ){
            return this.tokens.isConsistent(tokens);
        }
        
        /**
         * is the clause with the key equal to the Type?
         *
         * @param $name
         * @param astType
         * @return true if
         */
        public boolean is(String $name, Type astType) {
            return is($name, _typeRef.of(astType));
        }

        /**
         * 
         * @param $name
         * @param _t
         * @return 
         */
        public boolean is(String $name, _typeRef _t) {
            return type($name).equals(_t);
        }

        /**
         * is the clause with the key equal to the expression?
         *
         * @param $name
         * @param exp
         * @return true if
         */
        public boolean is(String $name, Expression exp) {
            Expression ex = expr($name);
            return exp.equals(ex);
        }

        /**
         * is the clause with the key equal to the expression?
         *
         * @param $name
         * @param st
         * @return true if
         */
        public boolean is(String $name, Statement st) {
            Statement stmt = stmt($name);
            return stmt.toString(Ast.PRINT_NO_COMMENTS).equals(st.toString(Ast.PRINT_NO_COMMENTS));
        }

        public boolean is($nameValues $nvs) {
            return this.equals($nvs);
        }

        public boolean is(Tokens tks) {
            return this.equals($nameValues.of(tks));
        }

        public boolean is(String $name, Class clazz ){
            Object o = get($name);
            if( o != null ){
                return _typeRef.of( o.toString() ).equals(_typeRef.of(clazz));
            }
            return false;            
        }
        
        /**
         * Is there a argument called "type" that 
         * @param clazz
         * @return 
         
        public boolean isType( Class clazz ){
            return is("type", clazz);
        }
        
        public boolean isName( String name ){
            return is("name", name);
        }
        */ 
        
        
        /**
         * 
         * @param $name
         * @param expectedValue
         * @return 
         */
        public boolean is(String $name, Object expectedValue) {
            //this matches nullExpr or simply not there
            Object o = get($name);

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
                return Expr.equatesTo((Expression) expectedValue, get($name));
            } else if (expectedValue instanceof String) {
                try {
                    return Expr.equatesTo(Expr.of((String) expectedValue), o);
                } catch (Exception e) {

                }
                return Objects.equals(expectedValue, get($name));
            } else if (o.getClass().equals(expectedValue.getClass())) {
                return o.equals(expectedValue);
            }
            return expectedValue.toString().equals(o);
        }

        /**
         *
         * @param $nvs the name values
         * @return
         */
        public boolean is(Object... $nvs) {
            if ($nvs.length % 2 == 1) {
                throw new DraftException("Expected an even number of key values, got (" + $nvs.length + ")");
            }
            for (int i = 0; i < $nvs.length; i += 2) {
                String key = $nvs[i].toString();
                if (!is(key, get(key))) {
                    //System.out.println( "NOT "+key+" "+get(key));
                    return false;
                }
            }
            return true;
        }

        @Override
        public boolean equals(Object o) {
            if (o == null || !o.getClass().equals($nameValues.class)) {
                return false;
            }
            $nameValues co = ($nameValues) o;
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
        public boolean containsKey(Object $name) {
            return tokens.containsKey($name);
        }

        @Override
        public boolean containsValue(Object value) {
            return tokens.containsValue(value);
        }

        @Override
        public Object get(Object $name) {
            return tokens.get($name);
        }

        @Override
        public Object put(String $name, Object value) {
            return tokens.put($name, value);
        }

        @Override
        public Object remove(Object $name) {
            return tokens.remove($name);
        }

        @Override
        public void putAll(Map<? extends String, ? extends Object> $nvs) {
            tokens.putAll($nvs);
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
     * Specifies an "optional" component
     * (i.e. a component (like an initializer for a field) 
     * or the lack of an initializer for a field
     * Additive constraints?
     * 
     * @param <T> 
     */
    public static class $component<T> {

        public static $component of( _model prototype ){
            return new $component( prototype.toString());
        }
        
        public static $component of( Node node ){
            return new $component( node.toString(Ast.PRINT_NO_ANNOTATIONS_OR_COMMENTS));
        }
        
        /** The pattern representation of the component */
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
        
        public $component addConstraint( Predicate<T> constraint ){
            this.constraint.and(constraint);
            return this;
        }
        
        public $component pattern(_model _javaModel ){
            
            if( _javaModel != null ){
                return pattern( Stencil.of( _javaModel.toString() ) );
            }
            return this;
        }
        
        public $component pattern(_node _javaModel ){            
            if( _javaModel != null ){
                return pattern( Stencil.of( _javaModel.toString(Ast.PRINT_NO_COMMENTS) ) );
            }
            return this;
        }
        
        public $component pattern( String... pattern ){
            return pattern(Stencil.of(Text.combine(pattern) ) );
        }
        
        public $component pattern(  Stencil pattern ){
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
        
        public $component hardcode$(Object...keyValues){
            this.pattern = this.pattern.hardcode$(keyValues);
            return this;
        }
        
        public boolean isMatchAny(){
            if( this.pattern.isMatchAny() ){
                try{
                    return this.constraint.test(null);
                }catch(Exception e){
                    return false;
                }
            }
            return false;
        }
        
        public boolean matches(T t) {
            return decompose(t) != null;
        }

        public Tokens decompose(T t) {
            
            if (t == null) {
                /** Null is allowed IF and ONLY If the Stencil $form isMatchAll*/
                if (pattern.isMatchAny()) {                    
                    return Tokens.of(pattern.list$().get(0), "");
                }
                return null;
            }
            if (constraint.test(t)){                
                if( t instanceof _node){
                    return pattern.deconstruct( ((_node)t).toString(Ast.PRINT_NO_COMMENTS).trim() );
                }
                if( t instanceof _body ){
                    //System.out.println( "IN BODY "+ pattern + t );
                    _body _b = (_body)t;
                    if( _b.isEmpty() ){
                        
                    }
                    if( pattern.isMatchAny() && _b.isEmpty()){
                        System.out.println( " " );
                        return Tokens.of( pattern.list$().get(0), _b.toString() );
                    }
                    return pattern.deconstruct( ((_body)t).toString(Ast.PRINT_NO_COMMENTS).trim() );
                }
                return pattern.deconstruct( t.toString() );
            }
            return null;
        }

        public $nameValues decomposeTo(T t, $nameValues args ){
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

        $nameValues args();

        /** Get the value of this $param$ via the name */
        default Object get(String $name ){
            return args().get($name);
        }
        
        default Expression expr(String $name) {
            return args().expr($name);
        }

        default Statement stmt(String $name) {
            return args().stmt($name);
        }

        default _typeRef type(String $name) {
            return args().type($name);
        }

        default boolean isExpr(String $name, String expressionValue) {
            try {
                return args().is($name, Expr.of(expressionValue));
            } catch (Exception e) {
                return false;
            }
        }

        default boolean is(Object... $nameValues) {
            return args().is($nameValues);
        }

        default boolean is(String $name, String value) {
            return args().is($name, value);
        }

        default boolean is(String $name, Expression value) {
            return args().is($name, value);
        }

        default boolean is(String $name, Statement value) {
            return args().is($name, value);
        }

        default boolean is(String $name, Type value) {
            return args().is($name, value);
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
