/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class SfieldTest extends TestCase {
    public void testSimple(){
        $field $fi = $field.of("public int z=100;");
        assertTrue( $fi.matches("public int z = 100;") );
        assertFalse( $fi.matches("public int z = 5;") );
        
        $fi.$init();
        //System.out.println( $fi );
        assertTrue( $fi.matches("public int z = 100;") );
        assertTrue( $fi.matches("public int z = 5;") );
        
        
        
    }
}
