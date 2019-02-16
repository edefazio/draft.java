package draft.java.template;

import draft.java.Ast;
import draft.java._class;
import draft.java._field;
import draft.java._method;
import draft.java.macro._remove;
import draft.java.macro._static;
import draft.java.runtime._javac;
import junit.framework.TestCase;

public class $methodTest extends TestCase {

    public void testAnonymousBodyWithMacros(){
        $method $m = $method.of(new Object(){
            @_static void a(){ System.out.println(1); }
        });
        _method _m = $m.compose();
        assertTrue( _m.isStatic() );
    }
    public void testAnonymousBody(){
        $method $m = $method.of( new Object(){
            @_remove class $type${}
            @_remove $type$ $name$;
            /**
             * Gets the $name$
             *
             * @return
             */
            @Deprecated
            public $type$ get$Name$(){
                return this.$name$;
            }
        });
        System.out.println( $m.compose( _field.of("int count;")) );

        //verify that the ANNOTATIONS and JAVADOC are transposed
        //
        assertEquals( _method.of(
                "/**",
                " * Gets the count",
                " *",
                " * @return ",
                " */",
                "@Deprecated",
                "public int getCount(){ return this.count; }"), $m.compose(_field.of("int count;") ));
    }

    public void testGetterSetter(){

        $method $get = $method.of("public $type$ get$Name$(){ return $name$; }");
        $method $getThis = $method.of("public $type$ get$Name$(){ return this.$name$; }");


        $method $set = $method.of("public void set$Name$( $type$ $name$){ this.$name$ = $name$; }");

        _method composed = $get.fill(int.class, "x" );
        System.out.println( composed );
        _method _ma = _method.of("public int getX()", (Integer x)->{
            return x;
        });

        assertTrue( $get.matches( _ma) );

        assertEquals( composed, _ma );

        class F{
            int x,y,z;

            public int getX(){
                return x;
            }

            /**
             * With comment
             * @return
             */
            public int getY(){
                return y;
            }

            public int getZ(){
                return z;
            }

            public void setX( int x){
                this.x = x;
            }

            public void setY( int y){
                this.y = y;
            }
            public void setZ( int z){
                this.z = z;
            }
        }
        _class _c = _class.of( F.class);


        //find all (3) getter METHODS in the TYPE above
        assertEquals( 3, $get.selectAllIn(_c).size());
        assertEquals( $get.select(_c.getMethod("getX") ).method, Ast.method("public int getX(){ return x; }") );
        assertEquals( 3, $set.selectAllIn(_c).size());

        //print all get METHODS
        $get.forSelectedIn(_c, g -> System.out.println( g.method ));

        //convert all void set METHODS to fluent set METHODS
        $set.forSelectedIn(_c, s-> {
            s.method.setType( _c.getName() );
            s.method.getBody().get().addStatement("return this;");
        });
        _javac.of( _c );

    }

}
