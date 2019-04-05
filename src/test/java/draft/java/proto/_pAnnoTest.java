package draft.java.proto;

import draft.java.Expr;
import draft.java._anno;
import draft.java._class;
import java.util.regex.Pattern;
import junit.framework.TestCase;

public class _pAnnoTest extends TestCase {
 
    
    public void testAny(){
        _class _c = _class.of("C");
        assertEquals( 0, _pAnno.list(_c).size());
        
        //add a top level annotation
        _c.annotate(Deprecated.class);
        assertEquals( 1, _pAnno.list(_c).size());
        
        _pAnno.forEach(_c, a-> System.out.println(a.getName()));
        _pAnno.forEach(_c, a-> !a.hasValues(), a-> System.out.println( a) );
    }
    
    @interface R{ int value() default 10; }
    @interface S{ Class[] clazz() default String.class; }
   
    public void testMatchClass(){
        
        assertTrue(_pAnno.of(R.class).matches("R"));
        assertTrue(_pAnno.of(R.class).matches("@draft.java.proto._pAnnoTest.R"));
        
        assertTrue(_pAnno.of(R.class).matches("R()"));
        assertTrue(_pAnno.of(R.class).matches("@draft.java.proto._pAnnoTest.R()"));
        
        assertTrue(_pAnno.of(R.class).matches("R(1)"));
        assertTrue(_pAnno.of(R.class).matches("@draft.java.proto._pAnnoTest.R(2)"));        
        
        assertTrue(_pAnno.of(R.class).matches("R(value=1)"));
        assertTrue(_pAnno.of(R.class).matches("@draft.java.proto._pAnnoTest.R(value=2)"));   
        
        assertTrue(_pAnno.of(R.class, "()").matches("@draft.java.proto._pAnnoTest.R()"));   
        assertTrue(_pAnno.of(R.class, "()").matches("R()"));   
        
        assertTrue(_pAnno.of(R.class, "($any$)").matches("@draft.java.proto._pAnnoTest.R()"));   
        assertTrue(_pAnno.of(R.class, "($any$)").matches("R()"));   
        
        assertTrue(_pAnno.of(R.class, "($any$)").matches("@draft.java.proto._pAnnoTest.R(1)"));   
        assertTrue(_pAnno.of(R.class, "($any$)").matches("R(2)"));
        
        assertTrue(_pAnno.of(R.class, "($any$)").select("@draft.java.proto._pAnnoTest.R(1)").is("any", Expr.of(1) ) );   
        assertTrue(_pAnno.of(R.class, "($any$)").select("@draft.java.proto._pAnnoTest.R(1)").is("any", "1" ) );  
       
        assertTrue(_pAnno.of(R.class, "($any$)").select("@draft.java.proto._pAnnoTest.R(1)").is("any", Expr.of(1) ) );   
        assertTrue(_pAnno.of(R.class, "($any$)").select("@draft.java.proto._pAnnoTest.R(1)").is("any", "1" ) );  
        
        
        
        assertTrue(_pAnno.of(R.class, "($any$)").matches("R(2)"));
        
        assertTrue(_pAnno.of(S.class, "($any$)").matches("S()"));
        assertTrue(_pAnno.of(S.class, "($any$)").matches("S(Float.class)"));
        assertTrue(_pAnno.of(S.class, "($any$)", a-> a.hasValue(v -> v.isClassExpr())).matches("@S(Float.class)"));
        assertFalse(_pAnno.of(S.class, "($any$)", a-> a.hasValue(v -> v.isClassExpr())).matches("@S"));
        assertFalse(_pAnno.of(S.class, "($any$)", a-> a.hasValue(v -> v.isClassExpr())).matches("@S({Float.class, String.class})"));
        
    }
    
    /**
     * Verify I can match $anno with Class based on simple and/or
     * fully qualified annotations
     */
    public void testFullyQualified(){        
        // when I do this, I need to change the regex as EITHER
        // 
        _pAnno $a = _pAnno.of(R.class);
        
        @draft.java.proto._pAnnoTest.R
        class C{}        
        _class _c = _class.of(C.class);        
        assertNotNull( $a.firstIn(_c) );
        
        @R
        class D{}        
        _class _d = _class.of(D.class);        
        assertNotNull( $a.firstIn(_d) );                
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
        assertNotNull( _pAnno.first(_c, "name($any$)") );
        
        //verify that we can find 
        assertNotNull( _pAnno.first(_c, "name($any$)", 
                //there is an Integer attribute value that is odd
                (a)-> a.hasValue(e -> e.isIntegerLiteralExpr() && e.asIntegerLiteralExpr().asInt() % 2 == 1)) );
        
        assertNotNull( _pAnno.first(_c, "name($any$)", (a) -> a.hasValue(3)) );
        assertNotNull( _pAnno.first(_c, "name(3)"));        
        assertNotNull( _pAnno.first(_c, "name(3)", _a-> _a.hasValue(3)) );
    }
     
    public void testStatic$anno(){
        _pAnno $a = _pAnno.of("@name");
        assertEquals( _anno.of("@name"), $a.construct());
        assertTrue( $a.matches(_anno.of("@name")));

        @name
        class C{
            @name int x;
            @name void m(){}
        }
        _class _c = _class.of(C.class);
        assertEquals( 3, $a.selectListIn(_c).size() );
        _c = $a.replaceIn(_c, _pAnno.of("@name2"));
        assertEquals( 0, $a.selectListIn(_c).size() ); //verify they are all changed
        assertEquals( 3, _pAnno.of("@name2").selectListIn(_c).size() ); //verify they are all changed
    }

    public void testDynamicAnno(){
        //any @NAME annotation with a prefix
        _pAnno $a = _pAnno.of("@name(prefix=$any$)");

        assertTrue( $a.matches( _anno.of("@name(prefix=\"1\")") ));

        assertTrue( $a.select(_anno.of("@name(prefix=\"1\")") ).is("any", "1") );

        assertTrue($a.deconstruct(_anno.of("@name(prefix=\"ABCD\")")).is("any", "ABCD"));
        assertTrue( $a.list$().contains("any"));


        @name(prefix="Mr.")
        class C{
            @name int x;
            @name(prefix="Mrs.") void m(){}
        }
        _class _c = _class.of(C.class);
        assertEquals( 2, $a.selectListIn(_c).size());

        // In this case, it'd be better to just use Walk
        // Here we Transpose the property information from @NAME to the @name2 annotation
        $a.replaceIn(_c, _pAnno.of("@name2(string=$any$)") );
        System.out.println(_c );

        _anno _a = $a.construct("any", "\"Some String\"");
        assertEquals( _anno.of("@name(prefix=\"Some String\")"), _a );
    }


     public void testRegex(){
       Pattern p = Pattern.compile("([0-9]+)(?:st|nd|rd|th)");
       assertTrue( p.matcher("1st").matches() );     
       //assertTrue( p.matcher("1").matches() );     
       //assertTrue( p.matcher("11").matches() );     
       assertTrue( p.matcher("2nd").matches() );     
       
       //Pattern p2 = Pattern.compile("(@)(?:draft.java.proto.$annoTest.)(R)");
       //Pattern p2 = Pattern.compile("\\@(?:draft.java.proto.$annoTest.)?(R)");
       //assertTrue(p2.matcher("@R").matches());
       
       Pattern p3 = Pattern.compile( 
            Pattern.quote("@") + 
            "(?:"+ Pattern.quote("draft.java.proto.$annoTest.") + ")?" +
            Pattern.quote("R") );
       
        assertFalse(p3.matcher("@S").matches());
        
        assertTrue(p3.matcher("@R").matches());
        assertTrue(p3.matcher("@draft.java.proto.$annoTest.R").matches());       
        assertFalse(p3.matcher("@draft.java.proto.$annoTestw.R").matches());       
    }
}
