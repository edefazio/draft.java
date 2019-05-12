/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java;

import junit.framework.TestCase;

import draft.java.proto.$;

/**
 *
 * @author Eric
 */
public class STest extends TestCase {
    
    public void testQueryCount(){
        class MyClass{
            String[] s = new String[0];
            
            void m(){
                int i = s.length;
                assert(true);
                System.out.println( "HELLO" );
                System.out.println( 314 );
                System.out.println( 3.14f );
            }
        }
        assertEquals(1, $.of(true).count(MyClass.class));
        assertEquals(1, $.of(0).count(MyClass.class));
        assertEquals(1, $.of("HELLO").count(MyClass.class));
        assertEquals(1, $.assertStmt().count(MyClass.class));
        assertEquals(1, $.doubleLiteralExpr().count(MyClass.class));
        
        assertEquals(1, $.doubleLiteralExpr("3.14f").count(MyClass.class));
        assertEquals(1, $.doubleLiteralExpr("3.14").count(MyClass.class));
        
        
        assertEquals(1, $.field().count(MyClass.class));
        //assertEquals(1, $.fieldAccessExpr().count(MyClass.class));
        assertEquals(1, $.varDeclarationExpr().count(MyClass.class));
        assertEquals(1, $.arrayCreationExpr().count(MyClass.class));
        
        assertEquals(1, $.assertStmt().count(MyClass.class));
        
    }
}
