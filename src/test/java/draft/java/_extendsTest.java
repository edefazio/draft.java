package draft.java;

import junit.framework.TestCase;

public class _extendsTest extends TestCase {

    class Base{
        
    }
    class C extends Base{
        
    }
    
    class D extends draft.java._extendsTest.Base{
        
    }
    
    public void testE(){
        _class _c = _class.of(C.class);
        _class _d = _class.of(D.class).name("C");
        assertEquals( _c, _d);
        
        
        
        System.out.println( _d );
        //DiffList od = draft.ObjectDiff.components(_c, _d);
        //System.out.println( od );
        
    }
}
