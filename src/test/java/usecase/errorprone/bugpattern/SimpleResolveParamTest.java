/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usecase.errorprone.bugpattern;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.CallableDeclaration;
import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.type.Type;
import draft.java.Ast;
import draft.java.W;
import draft.java._type;
import draft.java.proto.$;
import draft.java.proto.$expr;
import draft.java.proto.$id;
import draft.java.proto.$parameter;
import draft.java.proto.$var;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import junit.framework.TestCase;

/**
 * Really we should use the Java Symbol Solver to 
 * 
 * @author Eric
 */
public class SimpleResolveParamTest extends TestCase {
    
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
        System.out.println( $var.of().$name("b").count(T.class) );
        /*
        $expr.methodCall("$a$.equals($b$)")
            .addConstraint( m-> m.getScope().isPresent() && m.getScope().get().isNameExpr() )
            .forSelectedIn(T.class, s-> {                
                String aVar = s.get("a").toString();
                String bVar = s.get("b").toString();
                System.out.println( aVar+ " "+bVar );
                //Type aType = resolveTypeOfLocalParam( aVar, s )
            });
        */    
    }
    
    public static final Type resolveTypeOfLocalParamFrom( String name, Node n ){
        
        Optional<Type> typeOfParam = Optional.empty();
        
        $var $v = $var.of( $id.of(name) );
        $parameter $p = $parameter.of().$name(name);
        
        //lets walk up
        n.walk(W.PARENTS, p -> {
            if( p instanceof CallableDeclaration ){
                //I've reached the callable declaration
                System.out.println( "Reached top Callable "+ p );
                List<Node> chillins = p.getChildNodes();
                boolean foundn = false;
                for(int i=0;i<chillins.size(); i++){
                    Node c = chillins.get(i);
                    if( c.equals( n ) ){
                        System.out.println( "Found N "+ n);
                        foundn = true;
                    }
                    if( ! foundn ){
                        
                    }
                    
                }
            }
            if( p instanceof InitializerDeclaration ){
                //I've reached the top of the 
                System.out.println( "Reached top Initializer "+ p );
            }            
        });
        if( typeOfParam.isPresent() ){
            return typeOfParam.get();
        }
        return null;
    }
    
}
