/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft;

import draft.Diff.DiffList;
import draft.java.Expr;
import draft.java.*;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 * 
 * Diff
 * 
 * Match  given a Map of Name & Object find a Match (i.e. a name and type)
 * 
 * Assemble
 * Disassemble
 * 
 * @author Eric
 */
public class DiffTest extends TestCase {
 
    public void testEqual(){
        assertTrue(Diff.components(_class.of("C"),_class.of("C")).isEmpty());
        assertTrue(Diff.components(_interface.of("I"),_interface.of("I")).isEmpty());
        assertTrue(Diff.components(_enum.of("E"),_enum.of("E")).isEmpty());
        assertTrue(Diff.components(_annotation.of("A"),_annotation.of("A")).isEmpty());
        assertTrue(Diff.components(_method.of("void m();"),_method.of("void m();")).isEmpty());
        assertTrue(Diff.components(_field.of("int x;"),_field.of("int x;")).isEmpty());
        assertTrue(Diff.components(_parameter.of("int x"),_parameter.of("int x") ).isEmpty());
        assertTrue(Diff.components(_typeRef.of("List<String>"),_typeRef.of("List<String>")).isEmpty());
        assertTrue(Diff.components(_anno.of("A"),_anno.of("A")).isEmpty());
        assertTrue(Diff.components(_constructor.of("Ct(){}"),_constructor.of("Ct(){}")).isEmpty());
        assertTrue(Diff.components(_anno.of("A"),_anno.of("A")).isEmpty());
        assertTrue(Diff.components(_enum._constant.of("A"),_enum._constant.of("A")).isEmpty());
        assertTrue(Diff.components(_annotation._element.of("int value();"),_annotation._element.of("int value();")).isEmpty());
    }
    
    public void testDiffProperties(){
        _field _left = _field.of("int x;");
        _field _right = _field.of("int x;");
        
        //System.out.println( Diff.diff(_left, _right) );
        assertTrue( Diff.components(_left, _right).isEmpty() );
        
        //change right to make sure we get a simple diff
        _right.name("y");        
        assertTrue( Diff.components(_left, _right).containsNames("name") );
        assertEquals( 1, Diff.components(_left, _right).size() );        
    }
    
    public void testNullDiffProperties(){
        _field _f1 = _field.of("public static final int x = 100;");
        _field _f2 = _field.of("public static final int x;");
        
        DiffList dl = Diff.components(_f1, _f2); 
        assertTrue( dl.containsNames("init") );
        assertEquals( dl.left("init"), Expr.of(100) );
        assertTrue( Diff.components(_f2, _f1).containsNames("init") );
    }
    
    public void testDiffPropertiesNotMonotonic(){        
        //we 
        _field _f = _field.of("int x;");
        _method _m = _method.of("int x(){}"); //the name and type are the same
        DiffList dl = Diff.components(_f, _m);
        assertTrue(dl.containsNames("body", "throws", "typeParameters", "parameters"));
        
        //System.out.println(Diff.components(_f, _m) );        
    }
}
