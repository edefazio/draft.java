package draft.java.proto;

import draft.java._method;
import draft.java._modifiers;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class SmodifiersTest extends TestCase {
    
    static _method _m = _method.of(new Object(){
        public void m(){
        }
    });
    
    public void testAny(){
        assertNotNull( $modifiers.any().select(_modifiers.of()) );
        
    }
    public void testAll(){
        assertNull( $modifiers.all(_m).select(_modifiers.of()) );
        assertNotNull( $modifiers.all(_m).select(_modifiers.of(_modifiers.PUBLIC)) );
        assertNull( $modifiers.all(_m.ast()).select(_modifiers.of()) );
        assertNotNull( $modifiers.all(_m.ast()).select(_modifiers.of().setPublic()) );
    }
    
}
