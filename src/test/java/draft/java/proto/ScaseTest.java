package draft.java.proto;

import com.github.javaparser.ast.stmt.SwitchEntry;
import com.github.javaparser.ast.stmt.SwitchStmt;
import draft.java.Ast;
import java.util.ArrayList;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class ScaseTest extends TestCase {
    
    public void testStaticCase(){
        String sss = "case 0: System.out.println(1);";
        SwitchEntry se = Ast.switchEntry(sss);
        $case $c = $case.of( se );
        assertNotNull( $c.select(se));
        assertNotNull( $c.select(sss));
        assertEquals( se, $c.select(se).astCase);
        assertTrue( $c.matches(se));
        assertTrue( $c.matches(sss));
        
        //label (w/o code)
        se = Ast.switchEntry("case 'c':");
        $c = $case.of( se );
        assertNotNull( $c.select("case 'c':"));
        
        se = Ast.switchEntry("default : System.out.println(\"default\");");
        $c = $case.of( se);
        assertNotNull( $c.select(se) );        
        assertTrue( $c.matches(se));
        assertNotNull( $c.select("default:System.out.println(\"default\");") );        
        assertTrue( $c.matches("default:System.out.println(\"default\");"));
    }
    
    public void testCaseAny(){
        $case $c = $case.any();
        assertTrue( $c.matches("case 1:") );
        assertTrue( $c.matches("case 1: return 2;") );
        assertTrue( $c.matches("case 'c': System.out.println(1); System.out.println(2);break;") );
        
        assertTrue( $c.matches("default: System.out.println(1);") );
        assertTrue( $c.matches("default: System.out.println(1); System.out.println(2);break;") );
    }
    
    
    public void testDynamicCase(){
        $case $c = $case.of($expr.any());
        
    }
    
    public void testTT(){        
        class CC{
            void m(int n){
                switch(n){
                    case 0: System.out.println("hi");
                    case 1: 
                    case 2: System.out.println("one or two"); break;
                    case 3: break;
                    case 4: System.out.println(1); 
                            System.out.println(2);
                            System.out.println(3);
                            break;
                    case 5:        
                    case 6: throw new RuntimeException();   
                    case 7: { System.out.println( "Block"); System.out.println("BB");}
                    default : System.out.println( "default");
                }
            }
        }
        assertEquals( 9, $case.any().count(CC.class));
        
        assertEquals(2, $.of(1).count(CC.class));
        
        
        ArrayList<$case> $cases = new ArrayList<>();
        SwitchStmt sts = $stmt.switchStmt().firstIn(CC.class);
        sts.getEntries().forEach( se -> $cases.add( new $case(se) ) );
        
        $cases.forEach( c-> System.out.println(c.label + " "+ c.statements));        
    }
    
}
