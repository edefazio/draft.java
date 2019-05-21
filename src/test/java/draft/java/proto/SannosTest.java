package draft.java.proto;

import draft.java._anno;
import draft.java._anno._annos;
import draft.java._class;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class SannosTest extends TestCase {
    
    public void testAnnosSingleMember(){
        $anno $a = $anno.of("A(2)" );
        _anno _a = $a.construct();
        
        _anno _aa = _anno.of("A(4)");
        _annos _aaa = _annos.of("@A(2)");
        
        $annos $as = $annos.of("@A(1)");
        $as.construct();
        System.out.println( $as.construct() );
    }
    
    public void testAnnosNone(){
        @Deprecated
        class DF{
            @Deprecated int a;
            String s;
            @Deprecated void m(){}
            int g;
        }
        //s and g
        assertEquals(2, $annos.none().count(DF.class));
    }
    
    public void testComposeAny(){
        $annos $as = $annos.of();
        _annos _as = $as.construct(); //should work fine... empty annos
        assertTrue( _as.isEmpty() );
        
        //here you can OVERRIDE
        _as = $as.construct("$annos", "@A" );
        
        assertTrue( _as.contains(_anno.of("@A")));
        
    }
    
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
