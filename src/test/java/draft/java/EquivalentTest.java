/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class EquivalentTest extends TestCase {
    
    public void testSimple(){
        //test both null
        assertTrue(_method.equivalent(null,null));
        
        List<_method> ms1 = new ArrayList<>();
        List<_method> ms2 = new ArrayList<>();
        //both empty
        assertTrue(_method.equivalent(ms1, ms2));
        
        //one not the other
        _method m1 = _method.of( "void a();"); 
        ms1.add(m1);
        assertFalse(_method.equivalent(ms1, ms2));
        ms2.add(m1);
        //both same 1
        assertTrue(_method.equivalent(ms1, ms2));
        
        _method m2 = _method.of( "int b();");
        ms2.add( m2);
        assertFalse(_method.equivalent(ms1, ms2));
        ms1.add(m2);
        assertTrue(_method.equivalent(ms1, ms2));        
        
    }
    
}
