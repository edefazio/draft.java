/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usecase.errorprone.bugpattern;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.type.Type;
import draft.java._type;
import draft.java.proto.$;
import draft.java.proto.$expr;
import java.util.Objects;
import junit.framework.TestCase;

/**
 * Really we should use the Java Symbol Solver to 
 * 
 * @author Eric
 */
public class SimpleResolveParam extends TestCase {
    
    public void testG(){
        class T{
            
            void m(int p){
                int[] a = {1,2,3};
                int[] b = {1,2,3};
                char c;
                
                if( a.equals(b) ){
                    
                }
                if (Objects.equals(a, b)) {
                    
                }
            }            
        }
        
        $expr.methodCall("$a$.equals($b$)")
            .addConstraint( m-> m.getScope().isPresent() && m.getScope().get().isNameExpr() )
            .forSelectedIn(T.class, s-> {                
                String aVar = s.get("a").toString();
                String bVar = s.get("b").toString();
                System.out.println( aVar+ " "+bVar );
                //Type aType = resolveTypeOfLocalParam( aVar, s )
            });
    }
    
    public static final Type resolveTypeOfLocalParamFrom( String name, Node n ){
        return null;
    }
    
}
