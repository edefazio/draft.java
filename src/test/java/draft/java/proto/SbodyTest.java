package draft.java.proto;

import draft.java._body;
import draft.java._method;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class SbodyTest extends TestCase {
    
    public void testNotImplemented(){
        $body $noImpl = $body.of(";"); //an "unimplemented" body        
        assertNotNull( $noImpl.select(_method.of("m();").getBody()) );
        
        $noImpl = $body.notImplemented();
        assertNotNull( $noImpl.select(_method.of("m();").getBody()) );        
    }
    
    public void testEmpty(){
        $body $empty = $body.of("{}"); //an "empty" body        
        assertTrue( $empty.matches("{       }"));
    }
    
    public void testSingleStatement(){
        $body $single = $body.of("{System.out.println(1);}");
        assertTrue( $single.matches("System.out.println(1);") ); 
        assertTrue( $single.matches("{System.out.println(1);}") ); 
        assertTrue( $single.matches("//comment ",
            "System.out.println(1);") ); 
        assertTrue( $single.matches("/* comment */",
            "System.out.println(1);") ); 
        
        assertTrue( $single.matches("/** comment **/",
            "System.out.println(1);") ); 

        //make it a variable prototype
        $single = $body.of("System.out.println($any$);");
        assertTrue( $single.matches("{ System.out.println(1); }") );
        assertTrue( $single.matches("{ System.out.println(i); }") );
        assertTrue( $single.matches("{ System.out.println(a + b); }") );
        assertTrue( $single.matches("System.out.println(a + b);") );     
        assertTrue( $single.matches("/** comment */ System.out.println(a + b);") );  
        
        assertNotNull( $single.select(()-> System.out.println(12)) );
        assertNotNull( $single.select(()-> { System.out.println(12); }) );
        assertNotNull( $single.select((a)-> { System.out.println(a); }) );        
        assertNotNull( $single.select((a, b)-> { System.out.println(a+" "+b); }) );
        assertNotNull( $single.select((a, b, c)-> { System.out.println(a+" "+b+" "+c); }) );
        assertNotNull( $single.select((a, b, c, d)-> { System.out.println(a+" "+b+" "+c+" "+ d); }) );
        assertNotNull( $single.select((a, b)-> { System.out.println(a+" "+b); }) );
        
        $single = $body.of("return $any$;");
        assertNotNull( $single.select((Integer a)-> { return a; })  );        
        assertNotNull( $single.select((Integer a,Integer b)-> { return a+b; })  );
        assertNotNull( $single.select((Integer a,Integer b, Integer c)-> { return a+b+c; })  );
        assertNotNull( $single.select((Integer a,Integer b, Integer c, Integer d)-> { return a+b+c+d; })  );
    }
    
    public void testMultiStatement(){
        
    }
    
}
