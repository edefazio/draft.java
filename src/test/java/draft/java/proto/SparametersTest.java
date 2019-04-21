package draft.java.proto;

import draft.java._parameter._parameters;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class SparametersTest extends TestCase {
    
    public void testParametersAny(){
        $parameters $ps = $parameters.any();
        
        //verify that ANY matches / Selects NO parameters at all
        assertNotNull( $ps.select("") );
        assertNotNull( $ps.select(_parameters.of("")) );
        assertNotNull( $ps.select("( )") );
        assertNotNull( $ps.select(_parameters.of("()")) );        
        assertTrue( $ps.matches("") );
        assertTrue( $ps.matches("()") );
        
        //selects a single
        assertTrue( $ps.select("int i").is("parameters", "(int i)"));
        assertTrue( $ps.select("(int i)").is("parameters", "(int i)"));
        
        assertTrue( $ps.select("int i, String j").is("parameters", "(int i, String j)"));
        assertTrue( $ps.select("(int i, String... j)").is("parameters", "(int i, String... j)"));
        
        assertNotNull( $ps.select(_parameters.of()) );
        assertTrue( $ps.matches(_parameters.of()) );
        
        //lets test compose...
        assertEquals( _parameters.of("int i"), $ps.construct("parameters", "int i"));        
    }
    
    public void testFixedParameters(){
        //single
        $parameters $ps = $parameters.of("int i");
        assertTrue($ps.matches("int i"));
        assertNotNull($ps.select("int i"));
        
        assertFalse( $ps.matches("int j"));
        assertFalse( $ps.matches("String i"));
        assertNull( $ps.select("int j"));
        assertNull( $ps.select("String i"));
        
        assertFalse( $ps.matches("int i, String j") ); //extra param
        assertNull( $ps.select("int i, String j") ); //extra param
        
        //finalVarArg
        $ps = $parameters.of("final int... f");
        assertTrue($ps.matches("final int...f"));
        assertNotNull($ps.select("final int... f"));
        
        assertFalse($ps.matches("int...f"));
        assertFalse($ps.matches("final int f"));
        assertFalse($ps.matches("final int...j"));
        assertFalse($ps.matches("final String...f"));
        
    }
    
}
