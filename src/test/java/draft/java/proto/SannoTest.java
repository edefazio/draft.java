package draft.java.proto;

import draft.java.Expr;
import draft.java._anno;
import draft.java._class;
import draft.java._type;
import java.util.regex.Pattern;
import junit.framework.TestCase;

public class SannoTest extends TestCase {
 
    public void testConstruct(){
        $anno $a = $anno.of("A");
        assertEquals(_anno.of("A"), $a.construct());
        
        $a = $anno.of( a-> a.isMarker() );
        
        $a = $anno.any();
        //we can Fill an anno by just providing a name
        assertEquals(_anno.of("A"), $a.construct("name", "A"));
        
        //override construct
        assertEquals(_anno.of("A"), $a.construct("$anno", "@A"));
    }
    
    public void testFullQualified(){
        
        class CL {
            @java.lang.Deprecated int a;
            @Deprecated int b;
        }
        
        //verify we can list fully and unqualified annotations 
        assertEquals( 2, $anno.list(CL.class, Deprecated.class).size());
        
        //verify I can remove the annotations
        _type _t = $anno.remove(CL.class, Deprecated.class );
        
        assertEquals( 0, $anno.of(Deprecated.class).count(_t));
        assertEquals( 0, $anno.list(_t, Deprecated.class).size());
        
        System.out.println( _t );
    }
    
    public void testAny(){
        _class _c = _class.of("C");
        assertEquals( 0, SOLDanno.list(_c).size());
        
        //add a top level annotation
        _c.annotate(Deprecated.class);
        assertEquals( 1, SOLDanno.list(_c).size());
        
        SOLDanno.forEach(_c, a-> System.out.println(a.getName()));
        SOLDanno.forEach(_c, a-> !a.hasValues(), a-> System.out.println( a) );
    }
    
    @interface R{ int value() default 10; }
    @interface S{ Class[] clazz() default String.class; }
   
    public void testMatchClass(){
        
        assertTrue(SOLDanno.of(R.class).matches("R"));
        assertTrue(SOLDanno.of(R.class).matches("@draft.java.proto.SannoTest.R"));
        
        assertTrue(SOLDanno.of(R.class).matches("R()"));
        assertTrue(SOLDanno.of(R.class).matches("@draft.java.proto.SannoTest.R()"));
        
        assertTrue(SOLDanno.of(R.class).matches("R(1)"));
        assertTrue(SOLDanno.of(R.class).matches("@draft.java.proto.SannoTest.R(2)"));        
        
        assertTrue(SOLDanno.of(R.class).matches("R(value=1)"));
        assertTrue(SOLDanno.of(R.class).matches("@draft.java.proto.SannoTest.R(value=2)"));   
        
        assertTrue(SOLDanno.of(R.class, "()").matches("@draft.java.proto.SannoTest.R()"));   
        assertTrue(SOLDanno.of(R.class, "()").matches("R()"));   
        
        assertTrue(SOLDanno.of(R.class, "($any$)").matches("@draft.java.proto.SannoTest.R()"));   
        assertTrue(SOLDanno.of(R.class, "($any$)").matches("R()"));   
        
        assertTrue(SOLDanno.of(R.class, "($any$)").matches("@draft.java.proto.SannoTest.R(1)"));   
        assertTrue(SOLDanno.of(R.class, "($any$)").matches("R(2)"));
        
        assertTrue(SOLDanno.of(R.class, "($any$)").select("@draft.java.proto.SannoTest.R(1)").is("any", Expr.of(1) ) );   
        assertTrue(SOLDanno.of(R.class, "($any$)").select("@draft.java.proto.SannoTest.R(1)").is("any", "1" ) );  
       
        assertTrue(SOLDanno.of(R.class, "($any$)").select("@draft.java.proto.SannoTest.R(1)").is("any", Expr.of(1) ) );   
        assertTrue(SOLDanno.of(R.class, "($any$)").select("@draft.java.proto.SannoTest.R(1)").is("any", "1" ) );  
        
        
        
        assertTrue(SOLDanno.of(R.class, "($any$)").matches("R(2)"));
        
        assertTrue(SOLDanno.of(S.class, "($any$)").matches("S()"));
        assertTrue(SOLDanno.of(S.class, "($any$)").matches("S(Float.class)"));
        assertTrue(SOLDanno.of(S.class, "($any$)", a-> a.hasValue(v -> v.isClassExpr())).matches("@S(Float.class)"));
        assertFalse(SOLDanno.of(S.class, "($any$)", a-> a.hasValue(v -> v.isClassExpr())).matches("@S"));
        assertFalse(SOLDanno.of(S.class, "($any$)", a-> a.hasValue(v -> v.isClassExpr())).matches("@S({Float.class, String.class})"));
        
    }
    
    /**
     * Verify I can match $anno with Class based on simple and/or
     * fully qualified annotations
     */
    public void testFullyQualified(){        
        // when I do this, I need to change the regex as EITHER
        // 
        SOLDanno $a = SOLDanno.of(R.class);
        
        @draft.java.proto.SannoTest.R
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
        assertNotNull( SOLDanno.first(_c, "name($any$)") );
        
        //verify that we can find 
        assertNotNull( SOLDanno.first(_c, "name($any$)", 
                //there is an Integer attribute value that is odd
                (a)-> a.hasValue(e -> e.isIntegerLiteralExpr() && e.asIntegerLiteralExpr().asInt() % 2 == 1)) );
        
        assertNotNull( SOLDanno.first(_c, "name($any$)", (a) -> a.hasValue(3)) );
        assertNotNull( SOLDanno.first(_c, "name(3)"));        
        assertNotNull( SOLDanno.first(_c, "name(3)", _a-> _a.hasValue(3)) );
    }
     
    public void testStatic$anno(){
        SOLDanno $a = SOLDanno.of("@name");
        assertEquals( _anno.of("@name"), $a.construct());
        assertTrue( $a.matches(_anno.of("@name")));

        @name
        class C{
            @name int x;
            @name void m(){}
        }
        _class _c = _class.of(C.class);
        assertEquals( 3, $a.listSelectedIn(_c).size() );
        _c = $a.replaceIn(_c, SOLDanno.of("@name2"));
        assertEquals( 0, $a.listSelectedIn(_c).size() ); //verify they are all changed
        assertEquals( 3, SOLDanno.of("@name2").listSelectedIn(_c).size() ); //verify they are all changed
    }

    public void testDynamicAnno(){
        //any @NAME annotation with a prefix
        SOLDanno $a = SOLDanno.of("@name(prefix=$any$)");

        assertTrue( $a.matches( _anno.of("@name(prefix=\"1\")") ));

        assertTrue( $a.select(_anno.of("@name(prefix=\"1\")") ).is("any", "1") );

        assertTrue( $a.select(_anno.of("@name(prefix=\"ABCD\")")).is("any", "ABCD"));
        assertTrue( $a.list$().contains("any"));


        @name(prefix="Mr.")
        class C{
            @name int x;
            @name(prefix="Mrs.") void m(){}
        }
        _class _c = _class.of(C.class);
        assertEquals( 2, $a.listSelectedIn(_c).size());

        // In this case, it'd be better to just use Walk
        // Here we Transpose the property information from @NAME to the @name2 annotation
        $a.replaceIn(_c, SOLDanno.of("@name2(string=$any$)") );
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
