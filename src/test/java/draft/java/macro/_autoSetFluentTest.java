package draft.java.macro;

import draft.java._class;
import junit.framework.TestCase;

public class _autoSetFluentTest extends TestCase {


    public void testSetNone(){
        @_settersFluent
        class C{

        }
        _class _c = _class.of(C.class);
        assertTrue( _c.listAnnos(_settersFluent.class).isEmpty());
        assertTrue(_c.listMethods().isEmpty());

        @_settersFluent
        class D{
            final int f = 100;
        }
        _c = _class.of(D.class);
        assertTrue( _c.listAnnos(_settersFluent.class).isEmpty());
        assertTrue(_c.listMethods().isEmpty());
    }

    public void testSetOne(){

        @_settersFluent
        class G{
            int a;
        }
        _class _c = _class.of(G.class);
        System.out.println( _c );
        assertTrue( _c.listAnnos(_settersFluent.class).isEmpty());
        assertTrue( _c.getMethod("setA").isType(G.class));
        assertTrue( _c.getMethod("setA").getParameter(0).isType(int.class));
    }

    public void testSetMulti(){

        @_settersFluent
        class G{
            int a,b,c;
            final String name = "Blah";
        }

        _class _c = _class.of(G.class);
        assertTrue( _c.listAnnos(_settersFluent.class).isEmpty());
        assertTrue( _c.getMethod("setA").isType(G.class));
        assertTrue( _c.getMethod("setA").getParameter(0).isType(int.class));
        assertTrue( _c.getMethod("setB").isType(G.class));
        assertTrue( _c.getMethod("setB").getParameter(0).isType(int.class));
        assertTrue( _c.getMethod("setC").isType(G.class));
        assertTrue( _c.getMethod("setC").getParameter(0).isType(int.class));
        assertNull( _c.getMethod("setName")); //make sure there is no setName method (final field)
    }

}
