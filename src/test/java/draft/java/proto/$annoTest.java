package draft.java.proto;

import draft.java.proto.$anno;
import draft.java._anno;
import draft.java._class;
import junit.framework.TestCase;

public class $annoTest extends TestCase {

    @interface name{
        int value() default 1;
        String prefix() default "";
    }

    @interface name2{
        String string();
    }

    public void testFirst(){
        @name(2)
        class C{
            @name(3)
            int g =1;
        }
        
        _class _c = _class.of(C.class);
        assertNotNull( $anno.first(_c, "name($any$)") );
        
        //verify that we can find 
        assertNotNull( $anno.first(_c, "name($any$)", 
                //there is an Integer attribute value that is odd
                (a)-> a.hasValue(e -> e.isIntegerLiteralExpr() && e.asIntegerLiteralExpr().asInt() % 2 == 1)) );
        
        assertNotNull( $anno.first(_c, "name($any$)", (a) -> a.hasValue(3)) );
        assertNotNull( $anno.first(_c, "name(3)"));        
        assertNotNull( $anno.first(_c, "name(3)", _a-> _a.hasValue(3)) );
    }
     
    public void testStatic$anno(){
        $anno $a = $anno.of("@name");
        assertEquals( _anno.of("@name"), $a.construct());
        assertTrue( $a.matches(_anno.of("@name")));

        @name
        class C{
            @name int x;
            @name void m(){}
        }
        _class _c = _class.of(C.class);
        assertEquals( 3, $a.listSelectedIn(_c).size() );
        _c = $a.replaceIn(_c, $anno.of("@name2"));
        assertEquals( 0, $a.listSelectedIn(_c).size() ); //verify they are all changed
        assertEquals( 3, $anno.of("@name2").listSelectedIn(_c).size() ); //verify they are all changed
    }

    public void testDynamicAnno(){
        //any @NAME annotation with a prefix
        $anno $a = $anno.of("@name(prefix=$any$)");

        assertTrue( $a.matches( _anno.of("@name(prefix=\"1\")") ));

        assertTrue( $a.select(_anno.of("@name(prefix=\"1\")") ).tokens.is("any", "\"1\"") );

        assertTrue($a.deconstruct(_anno.of("@name(prefix=\"ABCD\")")).is("any", "\"ABCD\""));
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
        $a.replaceIn(_c, $anno.of("@name2(string=$any$)") );
        System.out.println(_c );

        _anno _a = $a.construct("any", "\"Some String\"");
        assertEquals( _anno.of("@name(prefix=\"Some String\")"), _a );
    }


}
