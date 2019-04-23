package draft.java.proto;

import com.github.javaparser.ast.body.BodyDeclaration;
import draft.java.proto.$method;
import draft.java.Ast;
import draft.java._class;
import draft.java._field;
import draft.java._method;
import draft.java.macro._remove;
import draft.java.macro._static;
import draft.java.runtime._javac;
import junit.framework.TestCase;

public class SmethodTest extends TestCase {
    
    public void testSetTest(){
        $method SET = $method.of("public void set$Name$( $type$ $name$ ) {this.$name$ = $name$;}");
        System.out.println( SET.construct("type",int.class, "name", "x") );
    }
    
    interface I{
        void noBody();
        
        public static int someBody(){
            return 103;
        }
        
        /** Javadoc */ @Deprecated void f();
    }
    public void testAnyPrototype(){
        //verify that the any() prototype will list methods with or without body
        assertEquals( 3, $method.any().listIn(I.class).size() );
    }
    
    public void testSimpleMatch(){
        $method $m = $method.of( new Object(){
            $type$ $name$;
            class $type${} 
            public $type$ get$Name$(){
               return this.$name$;
            }           
        });
        
        //you can create a method based on the properties of a field
        // (i.e. a _field will decompose to 
        // even though the field is private, we DONT traspose the 
        //modifier because it is static
        assertEquals( $m.construct( _field.of("private int count") ),
                $m.construct( _field.of("int count") ) );
        //_method _m = $m.construct( _field.of("private int count") );                
        //System.out.println( _m );
        
        $m.$annos("@Deprecated");
        _method _m = $m.construct( _field.of("private int count") );                
        System.out.println( _m );
        
        assertTrue($m.matches(_m));
        _m.removeAnnos(Deprecated.class);
        
        /** SHOULD WORK, NEED TO REFACTOR $anno first 
        _m.annotate("@java.lang.Deprecated");
        System.out.println( _m );
        assertTrue($m.matches(_m));
        */
        
    }
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
        
        //$method stat = $method.of("public void setX(int x){ this.x = x; }");
        //_method _m = stat.construct();
        //System.out.println("COMPOSED" + _m );
        
        //assertEquals(1, stat.listIn(_c).size());
        //verify we can find (2) set methods
        
        assertEquals(2, $set.listIn(_c).size());
        
        //remove 'em
        $set.removeIn(_c);
        
        //verify they are removed
        assertEquals(0, $set.listIn(_c).size());
        
        //rebuild
        _c = _class.of(Loc.class);
        
        $set.listSelectedIn(_c).get(0).is("type", int.class);
        // call replace with a setFluent prototype
        $set.replaceIn(_c, $setFleunt.hardcode$("className", "Loc") );
        
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
    
    public void testSimpleGetter(){
        $method $get = $method.of("public $type$ get$Name$(){ return this.$name$; }");
        
        
        BodyDeclaration bd = Ast.declaration( "public java.lang.String getEric ()" + System.lineSeparator() +
                "{"+ System.lineSeparator()+
                "    return this.eric;"+ System.lineSeparator() +
                "}");
        System.out.println( bd );
        
        _method _m = $get.construct("name", "eric", "type", String.class);
        System.out.println( _m );
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

        _method composed = $get.construct("type", int.class, "name", "x" );
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
        assertEquals($get.select(_c.getMethod("getX") )._m, _method.of("public int getX(){ return x; }") );
        assertEquals( 3, $set.listSelectedIn(_c).size());

        //print all get METHODS
        $get.forSelectedIn(_c, g -> System.out.println(g._m ));

        //convert all void set METHODS to fluent set METHODS
        $set.forSelectedIn(_c, s-> {
            s._m.ast().setType( _c.getName() );
            s._m.ast().getBody().get().addStatement("return this;");
        });
        _javac.of( _c );

    }

}
