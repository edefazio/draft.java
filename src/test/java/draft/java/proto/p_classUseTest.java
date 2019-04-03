/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.type.Type;
import draft.java.Walk;
import draft.java._anno;
import draft.java._class;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class p_classUseTest extends TestCase {

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
    
    public void testN() {
        _class _c = _class.of("C", new Object() {
            @Ann
            Inter ifield = null;

            @p_nodeTest.Ann
            Base bField = null;

            @draft.java.proto.p_nodeTest.Ann
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
        
        //$typeUse.replace(_c, Base.class, Replace.class);
        //$typeUse.replace(_c, Inter.class, Outer.class);
        p_classUse.replace(_c, Ann.class, Stan.class);
        
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
