package draft.java.macro;

import draft.java._class;
import draft.java._field;
import draft.java._method;
import junit.framework.TestCase;

public class _staticTest extends TestCase {

    public void testStatic(){
        assertTrue( _static.Macro.to(_class.of("C")).isStatic());
        assertTrue( _static.Macro.to(_field.of("int a;")).isStatic());
        assertTrue( _static.Macro.to(_method.of("void m(){}")).isStatic());
    }

    public void testT(){
        class F{
            @_static int g;
        }
        System.out.println( _macro.to(F.class, _class.of(F.class)) );
    }
}
