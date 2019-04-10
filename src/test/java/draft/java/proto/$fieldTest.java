/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import draft.java.Ast;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class $fieldTest extends TestCase {
    public void testSimple(){
        $field $f = $field.of("public int z=100;");
        assertTrue( $f.matches(Ast.field("public int z = 100;")) );
        assertFalse( $f.matches(Ast.field("public int z = 5;")) );
        
        $f.$("100", "value");
        System.out.println( $f );
        assertTrue( $f.matches(Ast.field("public int z = 100;")) );
        assertTrue( $f.matches(Ast.field("public int z = 5;")) );
        
        
    }
}
