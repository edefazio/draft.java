package draft.java.proto;

import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class ScatchTest 
    extends TestCase {
    
    public void testCatch(){
        class C{
            void m(){
                try{
                    
                }catch(Exception e){
                    System.out.println(e);
                }
            }
        }
        assertEquals(1, $catch.any().count(C.class));
    }
    
}
