package draft.java.template;

import draft.java.Ast;
import draft.java._class;
import junit.framework.TestCase;

import java.util.*;

public class $typeRefTest extends TestCase {

    public void testSimple(){
        $typeRef $t = $typeRef.intType;
        assertEquals(Ast.INT_TYPE, $t.compose());

        assertNotNull( $t.decompose(Ast.INT_TYPE));
        assertNull( $t.decompose(Ast.FLOAT_TYPE));
        class F{
            int a;
            public F( int b ){
                this.a = b;
            }
            public int getA(){
                return this.a;
            }
            public void setA( int a){
                this.a = a;
            }
        }
        _class _c = _class.of(F.class);
        assertEquals( "expected (4) instance of int in _c", 4, $t.selectAllIn(_c).size());
        $t.replaceIn(_c, float.class); //change all int TYPE references to float
        //System.out.println( _c );
        assertEquals( "expected (0) instance of int in _c", 0, $t.selectAllIn(_c).size());
        assertEquals( "expected (4) instance of float in _c", 4, $typeRef.of(float.class).selectAllIn(_c).size());

        //find all float types in _c and replaceIn them with double
        $typeRef.of(float.class).replaceIn(_c, double.class);
        assertEquals( "expected (4) instance of double in _class", 4, $typeRef.of(double.class).selectAllIn(_c).size());
    }


    public void testReplaceComplexType(){
        class FF{
            Set<Integer> is = new TreeSet<Integer>();
            Set<String> ss = new TreeSet<String>();

            public TreeSet<Double> m(){
                return new TreeSet<Double>();
            }

        }
        $typeRef $anyTreeSet = $typeRef.of("TreeSet<$any$>");
        _class _c = _class.of(FF.class);
        //verify I can find a
        assertEquals(4, $anyTreeSet.selectAllIn(_c).size());

        $anyTreeSet.replaceIn(_c, $typeRef.of("HashSet<$any$>") ); //convert TreeSet to HashSet
        //System.out.println( _c );
    }

    public void testReplaceGenericPart(){
        class GG{
            List<Integer> li;
            Set<Integer> si;

            public Map<Integer,Integer> getInts(){
                return new HashMap<Integer,Integer>();
            }
        }
        _class _c = _class.of(GG.class);
        $typeRef.of(Integer.class).replaceIn(_c, $typeRef.of(Float.class));
        //System.out.println( _c );
    }
}
