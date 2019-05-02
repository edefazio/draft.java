package draft.java.runtime;

import com.github.javaparser.ast.expr.Expression;
import draft.java.Expr;
import draft.java._class;
import draft.java._method;
import draft.java._type;
import draft.java.proto.$method;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Evaluate expressions & statements. (this is not like a "full interpreter", but
 * rather a simple tool that can be used to build and run expressions, methods, 
 * etc. that come from draft)
 * 
 * I'm a little worried about the security implications here, but this is just a 
 * rough sketch its not really efficient either (creates separate ClassLoaders, 
 * etc.), but still useful in a pinch.
 * 
 * @author Eric
 */
public class _eval {
    
    /** 
     * the prototype method used to inject an expression into so that it can be 
     * evaluated and returned
     */ 
    private static $method $evalExpr = $method.of(
        "public static Object EVAL(){",
        "return $eval$;",
        "}");
    
    public static Object expr( String expression ){
        try{
            return of(Expr.of(expression));
        } catch( Exception e){
            return null;
        }
    }
    /**
     * Convert the String into an Expression and evaluate the expression 
     * then return the result
     * @param expression the String representation of the expression (each element
     * represents a separate line)
     * @return the evaluated value of the expression;
     */
    public static Object expr( String... expression ){
        try{
            return of(Expr.of(expression));
        } catch( Exception e){
            return null;
        }        
    } 
    
    public static Object method( String method, Object...args){
        return of( _method.of(method), args );
    }
    
    /**
     * Build a new _class, compile it, load it and call prototyped method with
     * the expression in it 
     * @param expr
     * @return 
     */
    public static Object of( Expression expr ){
        //System.out.println("************************* >>>> EVALUATION OF "+expr);
        _method gen = $evalExpr.construct("eval", expr);
        //System.out.println("************************* >>>> EVALUATION OF "+gen);
        _class _c = _class.of("draft.adhoc.ExprEval").method(gen);
            //.method( $evalExpr.fill(expr) );   
        //System.out.println("************************* >>>> EVALUATION OF " +  _c );
        return _project.of(_c).call(_c);
    }
    
    
    public static Object type( String type, Object...args ){
        _type _t = _type.of(type);
        _t.setPublic(); //always make it public    
        _t.setPackage("adhoc");
        System.out.println( _t);
        return of( _t, args);
    }
    
    /**
     * compile and load the class for the type and call the public static method
     * on the type
     * @param _t the type
     * @param args the args for the method to call
     * @return the result of the method
     */
    public static Object of( _type _t, Object...args){
        return _project.of(_t).call(_t, args);
    }
    
    /**
     * Build a prototype class to house the method and call the method to return 
     * the result
     * @param _m the method to run/eval
     * @param args the arguments to the method
     * @return the result of the method
     */
    public static Object of( _method _m, Object...args ){
        _method _mc = _m.copy(); //copy the method (so we can modify it if we need to)
        _mc.setPublic().setStatic(); //
        _class _c = _class.of("adhoc.AdHoc").method(_mc); 
        System.out.println( _c );
        return _project.of(_c).call(_mc, args);        
    }
    
    /**
     * No magic here... just evaluate and return the supplier
     * i.e. _eval.of( ()-> Map.of(1).size() );
     * 
     * @param c
     * @return 
     */
    public static Object of( Supplier c ){
        return c.get();        
    }
    
    /**
     * Again, no magic, just evaluate and return the 
     * @param <A>
     * @param <B>
     * @param fn
     * @param input
     * @return 
     */
    public static <A extends Object, B extends Object> Object of( Function<A,B> fn, A input ){
        return fn.apply(input);
    }
    
    /**
     * 
     * @param <A> input arg type for arg 1
     * @param <B> input arg type for arg 2
     * @param <C> output arg type
     * @param fn the lambda function
     * @param arg1 the first argument to the lambda
     * @param arg2 the second argument to the lambda
     * @return the result of the lambda function
     */
    public static <A extends Object, B extends Object, C extends Object> Object of( BiFunction<A,B,C> fn, A arg1, B arg2 ){
        return fn.apply(arg1,arg2);
    }
}
