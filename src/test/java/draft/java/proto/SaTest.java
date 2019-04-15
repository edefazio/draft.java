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
