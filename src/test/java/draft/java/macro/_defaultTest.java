package draft.java.macro;

import draft.java._interface;
import draft.java._method;
import junit.framework.TestCase;

public class _defaultTest extends TestCase {

    interface I{
        @_default static int getY(){ return 1; }
    }
    public void test_default(){
        _interface _i = _interface.of(I.class);
        //System.out.println( _i);

        assertTrue( _i.getMethod("getY").isDefault());

        //manually apply the _macro to the method
        _method _m = _method.of("public static int getY(){ return 1; }");
        _default.Macro.to(_m);
        assertTrue( _m.isDefault() );
        assertFalse( _m.isStatic());
    }
}
