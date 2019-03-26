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
        $field $f = $field.of("public int i=100;");
        $f.matches(Ast.field("public int i = 100;"));
    }
}
