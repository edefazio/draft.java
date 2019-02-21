package draft.java.macro;

import draft.java._class;
import junit.framework.TestCase;

public class _abstractTest extends TestCase {

    public void testAbstract(){
         @_abstract class C{
             @_abstract void getR(){}
         }

         _class _c = _class.of(C.class);

         System.out.println( _c );
    }


}
