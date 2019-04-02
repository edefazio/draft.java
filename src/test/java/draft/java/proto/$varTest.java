/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import com.github.javaparser.ast.body.VariableDeclarator;
import draft.java.Ast;
import draft.java._class;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class $varTest extends TestCase {
    
    public void testVar(){
        $var $anyInt = $var.of("int $name$");
        $var $anyString = $var.of("String $name$");
        $var $anyInit = $var.of("$type$ $name$ = $init$");
        
        VariableDeclarator v = Ast.variable("int count");
        assertEquals( $anyInt.fill("count"), v );
        
        _class _c = _class.of("C", new Object(){
            int x, y = 0;            
            int z;
            String n;
        });
        
        assertEquals( 3, $anyInt.listIn(_c).size());
        assertEquals( 1, $anyString.listIn(_c).size());
        assertEquals( 1, $anyInit.listIn(_c).size());
        
        //I want to list all var names
        
        List<String> varNames = new ArrayList<>();
        $var.forEach(_c, vv -> varNames.add(vv.getNameAsString()));        
        System.out.println( varNames );
        
        List<String> typeNames = new ArrayList<>();
        $var.forEach(_c, vv -> typeNames.add(vv.getTypeAsString()));
        System.out.println( typeNames );
        
    }
}
