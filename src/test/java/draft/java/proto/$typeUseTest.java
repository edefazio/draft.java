/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.type.Type;
import draft.java.Walk;
import draft.java._class;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class $typeUseTest extends TestCase {
    
    public interface Inter{
        
    }
    public static class Base{
        
    }
    public void testT(){
        _class _c = _class.of("C",new Object(){
            Inter ifield = null;
            Base bField = null;
            Map<Inter,Base> m = new HashMap<>();
            
            Inter getInter(){
                return ifield;
            }
            
            Base getBase(){
                return bField;
            }
            
            public void setInter( Inter ifld ){
                this.ifield = ifld;
            }
            
            public <I extends Inter> void g( I in ) {}
            public <B extends Base> void gg( B in ) {}                        
        }).implement(Inter.class)
                .extend(Base.class);
        
        System.out.println( _c );
        
        Set<Class> s = new HashSet<Class>();
        s.add(Type.class);
        //s.add(FieldReference.class );
        
        List<Node> ns = Walk.list( _c, Node.class, n->n.toString().equals("Inter") );
        
        ns.forEach( n -> System.out.println( n +" "+ n.getClass() ));
        
        
    }
    
}
