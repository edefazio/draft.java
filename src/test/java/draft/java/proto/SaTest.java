/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import com.github.javaparser.ast.expr.MemberValuePair;
import draft.java.Expr;
import draft.java._anno;
import draft.java._class;
import java.util.regex.Pattern;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;

/**
 *
 * @author Eric
 */
public class SaTest extends TestCase {
    
    public void goal(){
        //this is a prototype for a $method which matches all methods that
        //have a matching annotation @A(count=1)
        
        // $method.of( $a.of("@A(count=1)"), $typeDecl.of(String.class) );
    }
    
    
    public void testOutOfOrderKeyValues(){
        $a a = $a.of("@A(a=1,b=2)");
        assertTrue( a.matches(_anno.of("@A(b=2,a=1)")) );
    }
    
    public void testParensOrNoParens(){
        $a a = $a.of("@E");
        
        assertTrue(a.matches(_anno.of("@E")));
        //this doesnt work
        assertTrue(a.matches(_anno.of("@E()")));
    }
    
    public void testFullyQualified(){
        $a a = $a.of("@A");
        assertTrue( a.matches("@A") );
        assertTrue( a.matches("@A()") );
        assertTrue( a.matches("@fully.qualified.A") );
        assertTrue( a.matches("@fully.qualified.A()") );
        
        assertFalse( a.matches("@B") );
        assertFalse( a.matches("@fully.qualified.A.B") );
        
        
        a = $a.of("@fully.qualified.A");
        assertTrue( a.matches("@fully.qualified.A") );
        assertTrue( a.matches("@fully.qualified.A(1)") );
        assertTrue( a.matches("@fully.qualified.A(a=1)") );
        assertTrue( a.matches("@fully.qualified.A(a=1,b=2)") );
        assertFalse( a.matches("@A") );
        assertFalse( a.matches("@A()") );
        assertFalse( a.matches("@fully.qualified.B") );
        assertFalse( a.matches("@different.packge.A") );
        
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
 
    
    
    public void testAny(){
        _class _c = _class.of("C");
        assertEquals( 0, $a.list(_c).size());
        
        //add a top level annotation
        _c.annotate(Deprecated.class);
        assertEquals( 1, $a.list(_c).size());
        
        $a.forEach(_c, a-> System.out.println(a.getName()));
        $a.forEach(_c, a-> !a.hasValues(), a-> System.out.println( a) );
    }
    
    @interface R{ int value() default 10; }
    @interface S{ Class[] clazz() default String.class; }
   
    public void testMatchClass(){
        
        assertTrue($a.of(R.class).matches("R"));
        assertTrue($a.of(R.class).matches("@draft.java.proto.SaTest.R"));
        
        assertTrue($a.of(R.class).matches("R()"));
        assertTrue($a.of(R.class).matches("@draft.java.proto.SaTest.R()"));
        
        assertTrue($a.of(R.class).matches("R(1)"));
        assertTrue($a.of(R.class).matches("@draft.java.proto.SaTest.R(2)"));        
        
        assertTrue($a.of(R.class).matches("R(value=1)"));
        assertTrue($a.of(R.class).matches("@draft.java.proto.SaTest.R(value=2)"));   
        assertTrue($a.of(R.class).matches("@draft.java.proto.SaTest.R()"));   
        
        assertTrue($a.of(R.class).matches("R()"));   
        assertTrue($a.of(R.class).matches("@draft.java.proto.SaTest.R()"));   
        
        assertTrue($a.of("R($any$)").matches("@draft.java.proto.SaTest.R()"));  
        
        assertTrue($a.of(R.class, "($any$)").matches("@draft.java.proto.SaTest.R()"));   
        assertTrue($a.of(R.class, "($any$)").matches("R()"));   
        
        assertTrue($a.of(R.class, "($any$)").matches("@draft.java.proto.SaTest.R(1)"));   
        assertTrue($a.of(R.class, "($any$)").matches("R(2)"));
        
        assertTrue($a.of(R.class, "($any$)").select("@draft.java.proto.SaTest.R(1)").is("any", Expr.of(1) ) );   
        assertTrue($a.of(R.class, "($any$)").select("@draft.java.proto.SaTest.R(1)").is("any", "1" ) );  
       
        assertTrue($a.of(R.class, "($any$)").select("@draft.java.proto.SaTest.R(1)").is("any", Expr.of(1) ) );   
        assertTrue($a.of(R.class, "($any$)").select("@draft.java.proto.SaTest.R(1)").is("any", "1" ) );  
        
        
        
        assertTrue($a.of(R.class, "($any$)").matches("R(2)"));
        
        assertTrue($a.of(S.class, "($any$)").matches("S()"));
        assertTrue($a.of(S.class, "($any$)").matches("S(Float.class)"));
        assertTrue($a.of(S.class, "($any$)").constraint( a-> a.hasValue(v -> v.isClassExpr())).matches("@S(Float.class)"));
        assertFalse($a.of(S.class, "($any$)").constraint( a-> a.hasValue(v -> v.isClassExpr())).matches("@S"));
        assertFalse($a.of(S.class, "($any$)").constraint( a-> a.hasValue(v -> v.isClassExpr())).matches("@S({Float.class, String.class})"));
        
    }
    
    /**
     * Verify I can match $a with Class based on simple and/or
     * fully qualified annotations
     */
    public void testFullyQual(){        
        // when I do this, I need to change the regex as EITHER
        // 
        $a a = $a.of(R.class);
        
        @draft.java.proto.SannoTest.R
        class C{}        
        _class _c = _class.of(C.class);        
        assertNotNull( a.firstIn(_c) );
        
        @R
        class D{}        
        _class _d = _class.of(D.class);        
        assertNotNull( a.firstIn(_d) );                
    }
    
    @interface name{
        int value() default 1;
        String prefix() default "";
    }

    @interface name2{
        String string();
    }
    
    @interface num{
        int x();
    }
    
    @interface val{
        float value();
    }

    public void testN(){
        @val(3.1f)
        @num(x=2)
        class C{
            
        }
    }
    public void testFirst(){
        @name(2)
        class C{
            @name(3)
            int g =1;
        }
        
        _class _c = _class.of(C.class);
        assertNotNull( $a.first(_c, "name($any$)") );
        
        //verify that we can find 
        assertNotNull( $a.first(_c, "name($any$)", 
                //there is an Integer attribute value that is odd
                (a)-> a.hasValue(e -> e.isIntegerLiteralExpr() && e.asIntegerLiteralExpr().asInt() % 2 == 1)) );
        
        assertNotNull( $a.first(_c, "name($any$)", (a) -> a.hasValue(3)) );
        assertNotNull( $a.first(_c, "name(3)"));        
        assertNotNull( $a.first(_c, "name(3)", _a-> _a.hasValue(3)) );
    }
     
    public void testStatic$a(){
        $a a = $a.of("@name");
        assertEquals( _anno.of("@name"), a.construct());
        assertTrue( a.matches(_anno.of("@name")));

        @name
        class C{
            @name int x;
            @name void m(){}
        }
        _class _c = _class.of(C.class);
        assertEquals( 3, a.selectListIn(_c).size() );
        _c = a.replaceIn(_c, $a.of("@name2"));
        assertEquals( 0, a.selectListIn(_c).size() ); //verify they are all changed
        assertEquals( 3, a.of("@name2").selectListIn(_c).size() ); //verify they are all changed
    }

    public void testDynamicAnno(){
        //any @NAME annotation with a prefix
        $a a = $a.of("@name(prefix=$any$)");

        assertTrue( a.matches( _anno.of("@name(prefix=\"1\")") ));

        assertTrue( a.select(_anno.of("@name(prefix=\"1\")") ).is("any", "1") );

        assertTrue( a.select(_anno.of("@name(prefix=\"ABCD\")")).is("any", "ABCD"));
        assertTrue( a.list$().contains("any"));


        @name(prefix="Mr.")
        class C{
            @name int x;
            @name(prefix="Mrs.") void m(){}
        }
        _class _c = _class.of(C.class);
        assertEquals( 2, a.selectListIn(_c).size());

        // In this case, it'd be better to just use Walk
        // Here we Transpose the property information from @NAME to the @name2 annotation
        a.replaceIn(_c, $a.of("@name2(string=$any$)") );
        System.out.println(_c );

        _anno _a = a.construct("any", "\"Some String\"");
        assertEquals( _anno.of("@name(prefix=\"Some String\")"), _a );
    }


     public void testRegex(){
       Pattern p = Pattern.compile("([0-9]+)(?:st|nd|rd|th)");
       assertTrue( p.matcher("1st").matches() );     
       //assertTrue( p.matcher("1").matches() );     
       //assertTrue( p.matcher("11").matches() );     
       assertTrue( p.matcher("2nd").matches() );     
       
       //Pattern p2 = Pattern.compile("(@)(?:draft.java.proto.$aTest.)(R)");
       //Pattern p2 = Pattern.compile("\\@(?:draft.java.proto.$aTest.)?(R)");
       //assertTrue(p2.matcher("@R").matches());
       
       Pattern p3 = Pattern.compile( 
            Pattern.quote("@") + 
            "(?:"+ Pattern.quote("draft.java.proto.$aTest.") + ")?" +
            Pattern.quote("R") );
       
        assertFalse(p3.matcher("@S").matches());
        
        assertTrue(p3.matcher("@R").matches());
        assertTrue(p3.matcher("@draft.java.proto.$aTest.R").matches());       
        assertFalse(p3.matcher("@draft.java.proto.$aTestw.R").matches());       
    }
}
