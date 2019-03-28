package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.type.Type;
import draft.DraftException;
import draft.Tokens;
import draft.java.Ast;
import draft.java.Expr;
import draft.java.Stmt;

import draft.java._model;
import draft.java._model._node;
import draft.java._typeRef;
import java.util.Collection;
import java.util.Collections;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;

/**
 * Defines a mechanism to query into code via Templates
 * 
 * @param <Q> the TYPE of the node being queried for (likely a {@link Node} or {@link _model._node}
 */
public interface $query<Q> {

    /** 
     * Find and return a List of all matching node types within _n 
     * 
     * @param _n the root _java model node to start the search (i.e. _class, _method, _
     * @return a List of Q that match the query
     */
    List<Q> listIn(_node _n );

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
    List<? extends selected> selectListIn(Node astRootNode);

    /**
     * return the selections (containing the node and deconstructed parts)
     * of all matching entities within the _j
     * @param _n the java entity (_type, _method, etc.) where to start the search
     * @return a list of the selected
     */
    List<? extends selected> selectListIn(_node _n);

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
     * @param <N> the TYPE of model node
     * @return the modified model node
     */
    <N extends _node> N removeIn(N _n );

    /**
     * Find and execute a function on all of the matching occurrences within astRootNode
     * @param <N>
     * @param astRootNode the node to search through (CompilationUnit, MethodDeclaration, etc.)
     * @param _nodeActionFn the function to run upon each encounter with a matching node
     * @return the modified astRootNode
     */
    <N extends Node> N forIn(N astRootNode, Consumer<Q> _nodeActionFn);

    /**
     * Find and execute a function on all of the matching occurrences within astRootNode
     * @param <N>
     * @param _n the java node to start the walk
     * @param _nodeActionFn the function to run on all matching entities
     * @return  the modified _java node
     */
    <N extends _node> N forIn(N _n, Consumer<Q> _nodeActionFn);

    /**
     * 
     */
    public static class $args implements Map<String,Object>{
        
        /**
         * 
         */
        private Tokens tokens;
        
        public static $args of( Tokens ts ){
            return new $args(ts);
        }
        
        public $args( Tokens ts ){
            this.tokens = ts;
        }
        
        public Object get( String name ){
            return tokens.get(name);
        }
        
        public Tokens asTokens(){
            return tokens;
        }
        
        public Expression expr(String key){
            Object obj = get(key);
            if( obj == null || obj.toString().trim().length() == 0 ){
                return null;
            }
            return Expr.of( obj.toString() );
        }
        
        public Statement stmt(String key){
            Object obj = get(key);
            if( obj == null || obj.toString().trim().length() == 0 ){
                return null;
            }
            return Stmt.of( obj.toString() );
        }
        
        public _typeRef type(String key){
            Object obj = get(key);
            if( obj == null || obj.toString().trim().length() == 0 ){
                return null;
            }
            return _typeRef.of( obj.toString() );
        }
        
        public List<Statement> stmts( String key ){
            Object obj = get(key);
            if( obj == null){ 
                return null;
            }
            if(obj.toString().trim().length() == 0 ){
                return Collections.EMPTY_LIST;
            }
            return Stmt.block( obj.toString() ).getStatements();            
        }
        
        /** 
         * check that it has this this exact key VALUE combination
         * @param key     
         * @param value     
         * @return true if 
         
        public boolean is( String key, String value){
            Object obj = get(key);
            if( obj == null){ 
                return false;
            }
            return obj.toString().equals( value );
        }
        */ 
               
        /** 
         * is the clause with the key equal to the Type?
         * @param key     
         * @param astType     
         * @return true if 
         */
        public boolean is( String key, Type astType){
            return is( key, _typeRef.of(astType));             
        }   
        
        public boolean is(String key, _typeRef _t){
            return type(key).equals(_t);            
        }
        
        /** 
         * is the clause with the key equal to the expression?
         * @param key     
         * @param exp     
         * @return true if 
         */
        public boolean is( String key, Expression exp){
            Expression ex = expr(key);
            return exp.equals(ex);
        }        

        /** 
         * is the clause with the key equal to the expression?
         * @param key     
         * @param st     
         * @return true if 
         */
        public boolean is( String key, Statement st){
            Statement stmt = stmt(key);
            return stmt.toString(Ast.PRINT_NO_COMMENTS).equals(st.toString(Ast.PRINT_NO_COMMENTS));
        }        
        
        public boolean is( $args cs ){
            return this.equals(cs );
        }
        
        public boolean is( Tokens tks ){
            return this.equals($args.of( tks) );
        }

        
        public boolean is( String key, Object value ){
            //this matches nullExpr or simply not there
            Object o = get(key);
            
            if( value == null || value instanceof NullLiteralExpr ){                
                if( !( o == null || o instanceof NullLiteralExpr || o.equals("null"))){
                    return false;
                }
                return true;
            }            
            if( value instanceof String && o instanceof String){
                //System.out.println("both equals String"+ value +" "+o );
                String v = (String)value;
                String s = (String)o;
                
                if( s.startsWith("\"") && s.endsWith("\"") ){
                    return v.equals( s.substring(1, s.length() -1) );
                }
                return o.equals(value);
            }
            if( value instanceof Expression ){
                return Expr.equatesTo((Expression)value, get(key) );
            }
            else if( value instanceof String ){                
                try{
                    return Expr.equatesTo( Expr.of( (String)value ), o);
                }
                catch(Exception e){
                    
                }
                return Objects.equals( value, get(key) );
            }      
            else if( o.getClass().equals( value.getClass()) ){
                return o.equals(value);
            }
            return value.toString().equals( o);
        }
        
        public boolean is( Object...keyValues ){
            if( keyValues.length % 2 == 1){
                throw new DraftException("Expected an even number of key values, got ("+ keyValues.length+")");
            }
            for(int i=0; i<keyValues.length; i+=2){
                String key = keyValues[i].toString();
                if( ! is( key, get(key))){
                    return false;
                }
            }
            return true;
        }
        
        @Override
        public boolean equals(Object o ){
            if( o == null || !o.getClass().equals($args.class ) ) {
                return false;
            }
            $args co = ($args)o;
            return Objects.equals( co.tokens, tokens);            
        }

        @Override
        public int hashCode(){
            return this.tokens.hashCode();
        }

        @Override
        public String toString(){
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
     * a selected entity from a prototype uery
     * @param <T> 
     */
    interface selected<T> {
        
        $args getArgs();        
        
        default Expression expr(String key){
            return getArgs().expr(key);            
        }
        
        default Statement stmt(String key){
            return getArgs().stmt(key);
        }
        
        default _typeRef type(String key){
            return getArgs().type(key);
        }
        
        default boolean isExpr(String key, String expressionValue){
            try{
                return getArgs().is(key, Expr.of(expressionValue));
            }catch(Exception e){
                return false;
            }            
        }
        
        default boolean is(Object...keyValues ){
            return getArgs().is(keyValues);
        }
        
        default boolean is(String key, String value){
            return getArgs().is(key, value);
        }
        
        default boolean is(String key, Expression value){
            return getArgs().is(key, value);
        }
        
        default boolean is(String key, Statement value){
            return getArgs().is(key, value);
        }
        
        default boolean is(String key, Type value){
            return getArgs().is(key, value);
        }
    }
    
    interface selectedAstNode<N extends Node>{
        
        /** return the selected AST Node */
        N ast();
    }
    
    interface selected_model<M extends _model>{
        
        /** return the selected node as a _model (i.e. _method for a MethodDeclaration)*/
        M model();
    }
}
