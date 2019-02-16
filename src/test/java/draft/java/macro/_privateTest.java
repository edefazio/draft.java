package draft.java.macro;

import draft.java._class;
import draft.java._constructor;
import draft.java._field;
import draft.java._method;
import junit.framework.TestCase;

public class _privateTest extends TestCase {

    public void testDirectCall(){
        assertTrue( _private.Macro.to(_class.of("C")).isPrivate());
        assertTrue( _private.Macro.to(_constructor.of("C(){}")).isPrivate());
        assertTrue( _private.Macro.to(_field.of("int a;")).isPrivate());
        assertTrue( _private.Macro.to(_method.of("void m(){}")).isPrivate());
    }

    public void testAnnotationExpand(){
        @_private class F{
            @_private int g;
            @_private F(){}
            @_private void b(){}

        }
        _class _c = _macro.to(F.class);
        //assertTrue( _c.isPrivate() );
        assertTrue( _c.getConstructor(0).isPrivate());
        assertTrue( _c.getField("g").isPrivate());
        assertTrue( _c.getMethod("b").isPrivate());

    }
}
