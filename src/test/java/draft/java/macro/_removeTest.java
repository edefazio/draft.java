package draft.java.macro;

import draft.java._class;
import junit.framework.TestCase;

public class _removeTest extends TestCase {

    public void testRemove(){

        class D{
            @_remove int f;      //field
            @_remove void f(){}  //method
            @_remove D(){}       //constructor
            @_remove class IN{ } //nested TYPE
        }

        _class _c = _macro._class(D.class);
        assertEquals(0, _c.listMethods().size());
        assertEquals(0, _c.listFields().size());
        assertEquals(0, _c.listConstructors().size());
        assertEquals(0, _c.listNests().size());
    }
}
