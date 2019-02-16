package draft.java.macro;

import junit.framework.TestCase;

public class _autoGetTest extends TestCase {
    public void testM(){

    }
/*
    public void testGetNothing(){
        @_autoGet
        class D{
        }
        _class _c = _java._class( D.class);
        assertFalse( _c.hasAnno(_autoGet.class)); //verify we removed the annotation
        assertTrue( _c.listMethods().isEmpty());
    }
    public void testGetSingle(){
        @_autoGet
        class DD{
            int x;
        }
        _class _c = _java._class( DD.class);
        assertFalse( _c.hasAnno(_autoGet.class)); //verify we removed the annotation
        assertTrue( _c.getMethod("getX").isType(int.class));

    }
    */
}
