package draft.java.runtime;

import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import draft.java.Expr;
import draft.java.Stmt;
import draft.java._class;
import draft.java._method;
import draft.java.proto.pExpr;
import draft.java.proto.pMethod;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * A "hack" to evaluate expressions and statements, I'm a little worried about
 * the security implications here, but this is just a rough sketch
 * 
 * its not efficient either, but hey thats not the point
 * 
 * @author Eric
 */
public class _eval {
    
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
    
    static pMethod $evalExpr = pMethod.of("public static Object EVAL(){",
        "return $eval$;",
        "}");
    
    public static Object of( String... expression ){
        try{
            return of(Expr.of(expression));
        } catch( Exception e){
            return null;
            //return of(Stmt.block(expression ));
        }        
    } 
    
    public static Object of( Expression expr ){
        _class _c = _class.of("draft.adhoc.ExprEval")
            .method( $evalExpr.fill(expr) );            
        return _project.of(_c).call(_c);
    }
    
    static pMethod $evalStmt = pMethod.of("public static Object EVAL(){",
        "return $eval$;",
        "}");
    
    /*
    public static Object of( BlockStmt st ){
        _method _m = _method.of("public static Object EVAL(){}")
            .setBody(st); 
        
        
        //IF the blockStmt DOES NOT have a return or throw
        //_m.listStatements();
        if( _m.listStatements( Stmt.RETURN ).isEmpty() ){
            List<VariableDeclarator> vs = 
                    $expr.varDecl(v-> v.getVariables().size() > 0).listIn(_m);
            _m.add( Stmt.of("return null;"));
        }
        
        //IF the body defines a single variable, then I return that
        //ELSE if the body defines multiple variables, then I should create
        // a Map, and add each of the variables to the map with their values
        // and then return the map
        
        _class _c = _class.of("draft.adhoc.ExprEval")
            .method( _m );            
            return _project.of(_c).call(_c);               
    }
    */
    
}
