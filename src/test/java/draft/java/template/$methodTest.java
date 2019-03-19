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
    
    public void testStaticMethodReplaceRemoveList(){
        $method $set = $method.of(
            "public void set$Name$($type$ $name$){",
            "this.$name$ = $name$;",
            "}");
        
        $method $setFleunt = $method.of(
            "public $className$ set$Name$( $type$ $name$ ){",
            "this.$name$ = $name$;",
            "return this;",
            "}");
        
        class Loc{
            int x;
            String y;
            
            public void setX(int x){
                //comment
                this.x = x;
            }
            
            public void setY(String y){
                
                this.y = y;
                
            }
        }
        _class _c = _class.of(Loc.class);
        //verify we can find (2) set methods
        assertEquals(2, $set.listIn(_c).size());
        
        //remove 'em
        $set.removeIn(_c);
        
        //verify they are removed
        assertEquals(0, $set.listIn(_c).size());
        
        //rebuild
        _c = _class.of(Loc.class);
        
        // call replace with a setFluent prototype
        $set.replaceIn(_c, $setFleunt.assign$("className", "Loc") );
        
        //verify there are no simple sets left 
        assertEquals(0, $set.listIn(_c).size());
        
        //verify there are (2) setFluents
        assertEquals(2, $setFleunt.listIn(_c).size());        
    }

    public void testAnonymousBodyWithMacros(){
        $method $m = $method.of(new Object(){
            @_static void a(){ System.out.println(1); }
        });
        _method _m = $m.construct();
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
        System.out.println( $m.construct( _field.of("int count;")) );

        //verify that the ANNOTATIONS and JAVADOC are transposed
        //
        assertEquals( _method.of(
                "/**",
                " * Gets the count",
                " *",
                " * @return ",
                " */",
                "@Deprecated",
                "public int getCount(){ return this.count; }"), $m.construct(_field.of("int count;") ));
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
        assertEquals( 3, $get.listSelectedIn(_c).size());
        assertEquals( $get.select(_c.getMethod("getX") ).method, Ast.method("public int getX(){ return x; }") );
        assertEquals( 3, $set.listSelectedIn(_c).size());

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
