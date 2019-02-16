package draft.java.macro;

import draft.java._class;
import draft.java.runtime._project;
import draft.java.runtime._proxy;
import junit.framework.TestCase;

import java.util.Map;
import java.util.UUID;

public class _autoEqualsTest extends TestCase {

    public void testAutoEquals(){
        @_promote("fffff")
        @_autoEquals
        class V{
            public int a,b,c;
            public int[] x;
            public float z;
            public double d;
            public Map<String,Integer> map;
            public String[] names;
            public UUID blah = UUID.randomUUID();
        }

        System.out.println( _macro._class(V.class) );
    }


    /**
     * Verify that if _2_template a class with _autoEquals
     * AND it is a derived class, it will call super.typesEqual()
     */
    public void testSubClassCallsSuperEqualsAutoEquals(){

        @_autoEquals
        class A{
            public int a = 100;
        }

        @_autoEquals
        class B extends A{
            public boolean b = true;
        }

        _class _a = _macro.to(A.class);

        //_class _a = _java._class( A.class);
        System.out.println(_a );
        _class _b = _macro.to(B.class);
        //_class _b = _java._class( B.class);
        System.out.println(_b );

        //compile the classes
        _project _ab = _project.of(_a, _b);
        //create (2) new instances
        _proxy _a1 = _ab._proxy(_b);
        _proxy _a2 = _ab._proxy(_b);

        assertTrue( _a1.equals(_a2)); //make sure they are equal

        _a1.set("a", 200); //change the field in the instances base class

        assertFalse( _a1.equals(_a2) ); //verify that it is no longer equal
        //i.e. verify the B class calls super.typesEqual()
    }

}
