package draft.java.proto;

import draft.java.Ast;
import draft.java.Expr;
import draft.java._class;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
//@Ast.cache
public class SclassUseTest extends TestCase {

    public interface Inter {

        public static final int VAL = 1;

        public static void tell() {
        }

        public static Integer blah() {
            return 1;
        }

        public static Integer mr(Object o) {
            return 1;
        }
    }

    public static class Base {

        public static final int VAL = 1;

        public static void tell() {
        }

        public static Integer blah() {
            return 1;
        }

        public static Integer mr(Object o) {
            return 1;
        }
    }
    
    public static class Replace{}

    public static class Outer{}
    
    public @interface Ann {

        int value() default 1;

        int a() default 2;
    }

    public @interface Stan{
        
    }
    
    public void test_annoReplacement(){
        _class _c = _class.of("C", new Object(){
            @Ann int a;
            @Ann(100) int b;
            @draft.java.proto.SclassUseTest.Ann int c;
            @SclassUseTest.Ann int d;
            public Object[] staticObjects = {
                "Base", "Ann", "Inter", 
                Base.class, Ann.class, Inter.class
            };            
        });
        $classUse.replace(_c, Ann.class, Stan.class);
        //System.out.println( _c );
        assertTrue( _c.getField("a").hasAnno(Stan.class));
        assertFalse( _c.getField("a").hasAnno(Ann.class));
        assertTrue( _c.getField("b").hasAnno(Stan.class));
        assertFalse( _c.getField("b").hasAnno(Ann.class));
        assertTrue( _c.getField("c").hasAnno(Stan.class));
        assertFalse( _c.getField("c").hasAnno(Ann.class));
        assertTrue( _c.getField("d").hasAnno(Stan.class));
        assertFalse( _c.getField("d").hasAnno(Ann.class));        
        
        $classUse.replace(_c,Base.class, Replace.class);
        $classUse.replace(_c,Inter.class, Outer.class);
        
        //System.out.println( _pExpr.list(_c, Expr.ARRAY_INITIALIZER ) );
        //gets the  array Initializer
        $expr $arrVals = $expr.arrayInitializer("{ $a$, $b$, $c$, Replace.class, Stan.class, Outer.class }", a-> a.getValues().size() == 6 );
        //assertNotNull($arrVals.selectFirstIn(_c));
        $expr.Select s = $arrVals.selectFirstIn(_c);
        
        assertTrue( s.is("a", "Base")); 
        assertTrue( s.is("b", "Ann")); 
        assertTrue( s.is("c", "Inter")); 
        
        assertTrue( s.is("a", "Base", "b", "Ann", "c", "Inter")); 
    // "b", "Ann", "c", "Inter") );
        
    }
    
    public void testExtendsReplacement(){
        _class _c = _class.of("C").extend(Base.class);
        
    }
    
    public void testClassApi(){
        class C{ int i= 100; }
        //will select C and int
        assertEquals( 2, $classUse.any().listSelectedIn(C.class).size());
        
        @Deprecated
        class D{ }
        assertEquals( 2, $classUse.any().listSelectedIn(D.class).size());
    }
    
    
    public void testN() {
        _class _c = _class.of("C", new Object() {
            @Ann
            Inter ifield = null;

            @SnodeTest.Ann
            Base bField = null;

            public Object[] staticObjects = new Object[] {
                "Base", "Ann", "Inter", //THESE SHOULD NOT BE AFFECTED
                Base.class, Ann.class, Inter.class //THESE SHOULD BE CHANGED   
            };
            
            @draft.java.proto.SnodeTest.Ann
            Map<Inter, Base> m = new HashMap<>();

            Inter getInter() {
                Inter.tell();
                System.out.println(Inter.VAL);
                Class II = Inter.class; //verify classExpr                
                Consumer<Object> C = Inter::mr; //verify method references

                BiConsumer<Inter, Base> f = (Inter i, Base b) -> System.out.println();

                Inter[] arr = new Inter[0];
                if (arr[0] instanceof Inter) {

                }
                class INN implements Inter { //check inner nest

                }

                return ifield;
            }

            Base getBase() {
                Base.tell();
                System.out.println(Base.VAL);
                System.out.println((Base) bField); //verify case
                Class CC = Base.class;
                //verify method references
                Consumer<Object> C = Base::mr;
                Base[] arr = new Base[0];
                if (arr[0] instanceof Base) {

                }
                class INN extends Base { //check inner nest

                }
                return bField;
            }

            public void setInter(Inter ifld) {
                this.ifield = ifld;
            }

            public <I extends Inter> void g(I in) {
            }

            public <B extends Base> void gg(B in) {
            }
        }).implement(Inter.class)
                .extend(Base.class);
        
        //System.out.println( $classUse.any().listSelectedIn(_c).size() );
        //System.out.println( _c );
        
        //$classUse.any().listSelectedIn(_c).forEach(s-> System.out.println( s.node.getClass() + " : " + s.node.toString()+ s.node.getBegin().get().toString() ));
        
        $classUse.replace(_c, Base.class, Replace.class);
        $classUse.replace(_c, Inter.class, Outer.class);
        $classUse.replace(_c, Ann.class, Stan.class);
        
        
        
        
        
        /*
        $node $n = new $node($nodeTest.Ann.class.getCanonicalName());
        $n.replaceIn(_c, $nodeTest.Ann.class.getCanonicalName().replace("Ann", "Stan") );
        
        $n = new $node("$nodeTest.Ann");
        $n.replaceIn(_c, "$nodeTest.Stan");
        */
        
        System.out.println( _c );
        
        //_anno _a = _c.getField("m").getAnno(Ann.class);
        //_a.ast().walk(c-> System.out.println( " \""+c + "\"  " + c.getClass()));
        
        
        //$typeUse $base = $typeUse.of(Base.class);
        //$base.replaceIn(_c, Replace.class);        
    }
}
