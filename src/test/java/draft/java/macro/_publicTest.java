package draft.java.macro;

import draft.java._class;
import draft.java._constructor;
import draft.java._field;
import draft.java._method;
import junit.framework.TestCase;

public class _publicTest extends TestCase {

    public void testDirectCall(){
        assertTrue( _public.Macro.to(_class.of("C")).isPublic());
        assertTrue( _public.Macro.to(_constructor.of("C(){}")).isPublic());
        assertTrue( _public.Macro.to(_field.of("int a;")).isPublic());
        assertTrue( _public.Macro.to(_method.of("void m(){}")).isPublic());
    }

    public void testAnnotationExpand(){
        @_public class F{
            @_public int g;
            @_public F(){}
            @_public void b(){}

        }
        _class _c = _macro.to(F.class);
        assertTrue( _c.isPublic() );
        assertTrue( _c.getConstructor(0).isPublic());
        assertTrue( _c.getField("g").isPublic());
        assertTrue( _c.getMethod("b").isPublic());

    }
}
