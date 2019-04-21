/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import java.util.function.Predicate;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class SmatchPredicateNull extends TestCase {
    
    public void testNullPredicate(){
        Predicate<String> STRING_PRED = s-> true;        
        assertTrue(STRING_PRED.test(null));
        
        Predicate<String> STRING_PRED_ENDS = s-> s.endsWith("V");        
        assertFalse(STRING_PRED_ENDS.test(null));
    }
    
}
