/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import com.github.javaparser.ast.expr.MemberValuePair;
import draft.java.Expr;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class SaTest extends TestCase {
    
    public void goal(){
        //this is a prototype for a $method which matches all methods that
        //have a matching annotation @A(count=1)
        
        // $method.of( $anno.of("@A(count=1)"), $typeDecl.of(String.class) );
    }
    
    public void testSsa(){
        $a a = $a.of("@A(count=1)");
        assertTrue( a.matches("@A(count=1)") );
    }
    
    public void testG(){
        //a marker annotation match
        $a a = $a.of("@A");
        assertTrue( a.matches("@A") ); //matches a marker
        assertTrue( a.matches("@A()") ); //matches a marker with parens
        assertTrue( a.matches("@A(1)") ); //matches a "value" annotation
        assertTrue( a.matches("@A(count=1)") ); //matches a keyvalue
        assertTrue( a.matches("@A(count=1,str=\"eric\")") );//matches a multi key value
        
        assertTrue( a.matches("@fully.qualified.A") );
        
        //an annotation with single value
        a = $a.of("@A(\"value\")");
        assertFalse( a.matches("@A") ); //X missing a value
        assertTrue( a.matches("@A(\"value\")") ); //exact match w/ value
        assertTrue( a.matches("@A(key=\"value\")") );// with a key=(value)
        assertFalse( a.matches("@A(key=\"val\")") ); // X not the correct value
        
        //an annotation with key value
        a = $a.of("@A(count=1)");
        assertFalse( a.matches("@A") );
        assertTrue( a.matches("@A(1)") ); //simple value matches
        assertTrue( a.matches("@A(count=1)") ); //exact match
        assertFalse( a.matches("@A(count=2)") ); //X wrong value
        assertTrue( a.matches("@A(count=1,str=\"eric\")") ); 
        assertTrue( a.matches("@A(str=\"eric\", count=1)") ); //in whatever order
        
        //an annotation with multiple keyvalues
        a = $a.of("@A(count=1,str=\"e\")");
        assertFalse( a.matches("@A(1)") ); //simple value matches
        assertFalse( a.matches("@A(count=1)") ); //exact match
        assertFalse( a.matches("@A(count=2)") ); //X wrong value
        assertTrue( a.matches("@A(count=1,str=\"e\")") ); 
        assertTrue( a.matches("@A(str=\"e\",count=1)") ); // different order
        assertTrue( a.matches("@A(str=\"e\", count=1,extra=3.4)") ); //extra args
        
        assertFalse( a.matches("@A(count=1, str=\"d\")") ); //X wrong value
        
    }
    public void testA(){
        $a a = $a.of("@A");
        assertTrue( a.matches("@A"));
    }
    
    public void testS(){
        $a.$memberValue.any().matches(new MemberValuePair());
        $a.$memberValue.any().matches(new MemberValuePair("a", Expr.of(1)));
        
        //static  membervalues
        $a.$memberValue.of("a", "100").matches(new MemberValuePair("a", Expr.stringLiteral("100")));
        $a.$memberValue.of("a", "100").matches(new MemberValuePair("a", Expr.of("100")));
        
        //dynamic value
        $a.$memberValue.of("a", "$value$").matches(new MemberValuePair("a", Expr.of("100")));
        $a.$memberValue.of("a", "$value$").matches(new MemberValuePair("a", Expr.of("1")));
        $a.$memberValue.of("a", "$value$").matches(new MemberValuePair("a", Expr.stringLiteral("Blah")));
        $a.$memberValue.of("a", "$value$").matches(new MemberValuePair("a", Expr.of(new int[]{1,2,3,4})));
        
        
    }
    
    public void testSingleValueAnno(){
       MemberValuePair mvp = new MemberValuePair().setValue(Expr.of(1));
       assertTrue($a.$memberValue.of(Expr.of(1)).matches(mvp));
    }
    
}
