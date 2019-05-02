/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import draft.java._class;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class SannosTest extends TestCase {
    
    public void testLambda(){
        class C{
            int x;
            public void m(){}
        }
        
        assertEquals(3, $annos.of( ans-> ans.isEmpty() ).count(C.class));
        
    }
    public void testT(){
        $annos $as = new $annos();
        _class _c = _class.of("C");
        assertNotNull( $as.select(_c) );
        _c.annotate("@A");
        assertNotNull( $as.select(_c) );
        
        
        $as = $annos.of("@A");
        assertNotNull( $as.select(_c) );
        _c.removeAnnos("A");
        assertNull( $as.select(_c) );
        
        $as = $annos.of($anno.of(Deprecated.class) );
        
        assertFalse( $as.matches(_class.of("C")));
        assertTrue( $as.matches(_class.of("C").annotate(Deprecated.class)));
    }
}
