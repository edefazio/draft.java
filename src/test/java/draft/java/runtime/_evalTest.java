/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.runtime;

import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class _evalTest extends TestCase {
    
    public void testEval(){        
        assertEquals( 7,  _eval.of( ()-> 3+4 ));
        assertEquals( 7,  _eval.of("3+4"));
        //assertEquals( 7,  _eval.of("int i=1+1;", "i++;", "i++;", "i++;", "i++;", "i++;", "return i;"));
        assertEquals( 1,  _eval.of( ()-> Map.of("X", 1).size() ) );
        
        //assertEquals( 1,  _eval.of( 
        //        (Integer res, String r) -> Map.of("X",res).size() ) 
        //);
    }
}
