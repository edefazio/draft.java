package usecase.gitter;

import com.github.javaparser.ast.stmt.Statement;
import draft.java._class;
import draft.java._method;
import draft.java.proto.$stmt;
import junit.framework.TestCase;

public class RemoveStmtsTest extends TestCase {

    public static class Clone1 {

        public static void main(String[] args) {
            System.out.println("I'm a clone1");
            System.out.println("I'm a clone2");
            System.out.println("I'm a clone3");
            System.out.println("I'm a clone4");
            System.out.println("I'm a clone5");
            System.out.println("I'm a clone6");
            System.out.println("I'm a clone7");
            System.out.println("I'm a clone8");
            System.out.println("I'm a clone9");
            System.out.println("I'm a clone10");
            System.out.println("I'm not a clone!");
            System.out.println("I'm a clone1");
            System.out.println("I'm a clone2");
            System.out.println("I'm a clone3");
            System.out.println("I'm a clone4");
            System.out.println("I'm a clone5");
            System.out.println("I'm a clone6");
            System.out.println("I'm a clone7");
            System.out.println("I'm a clone8");
            System.out.println("I'm a clone9");
            System.out.println("I'm a clone10");
        }
    }

    public void testC(){
         _class _c = _class.of(Clone1.class);
        $stmt $s = $stmt.of( ()->System.out.println("I'm a clone10") );
        assertEquals(2, $s.count(_c));
        Statement st = $s.selectFirstIn(_c).astStatement;
        //manually
        _c.getMethod("main").getBody().ast().remove(st);
        assertEquals(1, $s.count(_c));//make sure I didnt remove the second one
        System.out.println( _c);
    }
    
    public void testST() {
        _class _c = _class.of(Clone1.class);
        _method _m = _c.getMethod("main");
        
        $stmt $s = $stmt.of("System.out.println(\"I'm a clone1\");");
        assertEquals(2, $s.count(_m));
        
        $s.selectFirstIn(_c).astStatement.remove();        
        assertEquals(1, $s.count(_m));
        assertTrue( $s.selectFirstIn(_c).astStatement.getParentNode().isPresent());        
    }
}
