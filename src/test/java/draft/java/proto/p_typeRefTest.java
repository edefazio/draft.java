package draft.java.proto;

import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import draft.java.proto.p_typeRef;
import draft.java.Ast;
import draft.java.Expr;
import draft.java.Walk;
import draft.java._class;
import draft.java._typeRef;
import junit.framework.TestCase;

import java.util.*;

public class p_typeRefTest extends TestCase {

    public static class OldT{
        public static final int F = 1;
        
        public static final int m() {
            return 2;
        }
    }
    
    public static class NewT{
        public static final int F = 1;
        
        public static final int m() {
            return 2;
        }
    }
    
    public void testFindAllTypeReferences(){
        
        _class _c = _class.of("AA", new Object(){
            public OldT field = new OldT();
            
            public OldT method( OldT one ){
                /** WORKING */                
                draft.java.proto.p_typeRefTest.OldT fv = new draft.java.proto.p_typeRefTest.OldT();
                
                //annotation ?
                draft.java.proto.p_typeRefTest.OldT[] arr = new draft.java.proto.p_typeRefTest.OldT[2];
                
                draft.java.proto.p_typeRefTest.OldT rr = (draft.java.proto.p_typeRefTest.OldT)one; //cast
                
                /* NOT WORKING  */
                OldT var = new OldT();
                
                System.out.println( OldT.F ); //field access
                OldT.m();
                System.out.println(draft.java.proto.p_typeRefTest.OldT.F ); //field access
                draft.java.proto.p_typeRefTest.OldT.m();
                return var;
            }            
        });
        
        p_typeRef.replace(_c, OldT.class, NewT.class );
        System.out.println( _c );
        
        //assertEquals( 12, $typeRef.of(OldT.class).listIn(_c).size() );
        
        p_typeRef $t = p_typeRef.of("OldT");
        $t.replaceIn(_c, _typeRef.of("NewT"));
        
        List<MethodCallExpr> ms = Walk.list(_c, Ast.METHOD_CALL_EXPR );
        ms.forEach(m -> System.out.println( "METHOD SCOPE " + m.getScope().get().toString() ));
        
        Walk.in(_c, MethodCallExpr.class, mc -> {
            if(mc.getScope().isPresent()){
                if( mc.getScope().get().toString().equals("draft.java.proto.$typeRefTest.OldT")){
                    mc.setScope(Expr.of("draft.java.proto.$typeRefTest.NewT"));
                }
                if( mc.getScope().get().toString().equals("OldT")){
                    mc.setScope(Expr.of("NewT"));
                }
            }
        });
        
        Walk.in(_c, Ast.FIELD_ACCESS_EXPR, mc -> {
            if(mc.getScope().toString().equals("draft.java.proto.$typeRefTest.OldT")){
                mc.setScope(Expr.of("draft.java.proto.$typeRefTest.NewT"));
            }
            if( mc.getScope().toString().equals("OldT")){
                mc.setScope(Expr.of("NewT"));
            }
        });
        
        System.out.println( _c );
        
        System.out.println( "FOUND FIELDS + " + Walk.in(_c, Ast.FIELD_ACCESS_EXPR, f-> f.toString().equals( "draft.java.proto.$typeRefTest.OldT" ) ) );
        System.out.println( "FOUND FIELDS + " + Walk.in(_c, Ast.FIELD_ACCESS_EXPR, f-> f.toString().equals( "OldT" ) ) );
        System.out.println( "FOUND METHODS 1+ " + Walk.in(_c, Ast.METHOD_CALL_EXPR, m-> m.toString().equals( "draft.java.proto.$typeRefTest.OldT" ) ) );
        
        Walk.in( _c, Ast.METHOD_CALL_EXPR, mc -> mc.getScope().isPresent() && mc.getScope().get().toString().equals("draft.java.proto.$typeRefTest.OldT"), 
                mc -> mc.setScope(Expr.of("draft.java.proto.$typeRefTest.OldT")));
        
        System.out.println( _c );
        /*
        System.out.println( "FOUND METHODS 2+ " + 
                Walk.in(_c, 
                    Ast.METHOD_CALL_EXPR, m -> true;
                    //m -> m.getScope().isPresent() && 
                     //   m.getScope().get().toString().equals( "OldT" ) 
            ) 
        );
        */
        /*
        System.out.println( 
                Walk.list(_c, Ast.FIELD_ACCESS_EXPR, f->{ 
                    Expression s = f.getScope(); 
                    System.out.println( "SCOPE CLASS " + s.getClass()+ " "+ f.getScope() );
                    return s != null; }) );
        */
        //System.out.println( Walk.list(_c, Ast.TYPE ) );
        //System.out.println( $typeRef.list(_c, "$any$", t-> true) );
        
        //_c = _class.of( $typeRefTest.class);
        //_c = _class.of( II.class);
        //$typeRef $t = $typeRef.of(OldT.class);
        //$t.replaceIn(_c, NewT.class );
        //System.out.println( _c );
    }
    
    public void testStaticCalls(){
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
        assertEquals("expected (4) instance of int in _c", 4, p_typeRef.list(_c, int.class).size());
        
        p_typeRef.replace(_c, int.class, float.class); //change all int TYPE references to float
        //System.out.println( _c );
        assertEquals("expected (0) instance of int in _c", 0, p_typeRef.list(_c, int.class).size());
        assertEquals("expected (4) instance of float in _c", 4, p_typeRef.of(float.class).selectListIn(_c).size());

        //find all float types in _c and replaceIn them with double
        p_typeRef.replace(_c, float.class, double.class);
        assertEquals("expected (4) instance of double in _class", 4, p_typeRef.of(double.class).selectListIn(_c).size());
    }
    
    public void testSimple(){
        p_typeRef $t = p_typeRef.intType;
        assertEquals(Ast.INT_TYPE, $t.construct());

        assertNotNull( $t.deconstruct(Ast.INT_TYPE));
        assertNull( $t.deconstruct(Ast.FLOAT_TYPE));
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
        assertEquals( "expected (4) instance of int in _c", 4, $t.selectListIn(_c).size());
        $t.replaceIn(_c, float.class); //change all int TYPE references to float
        //System.out.println( _c );
        assertEquals( "expected (0) instance of int in _c", 0, $t.selectListIn(_c).size());
        assertEquals("expected (4) instance of float in _c", 4, p_typeRef.of(float.class).selectListIn(_c).size());

        //find all float types in _c and replaceIn them with double
        p_typeRef.of(float.class).replaceIn(_c, double.class);
        assertEquals("expected (4) instance of double in _class", 4, p_typeRef.of(double.class).selectListIn(_c).size());
    }


    public void testReplaceComplexType(){
        class FF{
            Set<Integer> is = new TreeSet<Integer>();
            Set<String> ss = new TreeSet<String>();

            public TreeSet<Double> m(){
                return new TreeSet<Double>();
            }

        }
        p_typeRef $anyTreeSet = p_typeRef.of("TreeSet<$any$>");
        _class _c = _class.of(FF.class);
        //verify I can find a
        assertEquals(4, $anyTreeSet.selectListIn(_c).size());

        $anyTreeSet.replaceIn(_c, p_typeRef.of("HashSet<$any$>") ); //convert TreeSet to HashSet
        //System.out.println( _c );
    }

    public void testReplaceComplexTypeStatic(){
        class FF{
            //make sure it handled fields
            Set<Integer> is = new TreeSet<Integer>();
            Set<String> ss = new TreeSet<String>();

            //make sure it changes the return type
            public TreeSet<Double> m(){
                //make sure it changes types in body
                return new TreeSet<Double>();
            }
            
            //make sure it changes parameters
            public void b( TreeSet<Integer> i ){}

        }
        //$typeRef $anyTreeSet = $typeRef.of("TreeSet<$any$>");
        _class _c = _class.of(FF.class);
        //verify I can find a
        assertEquals(5, p_typeRef.list(_c, "TreeSet<$any$>").size());
        p_typeRef.replace(_c, "TreeSet<$any$>", "HashSet<$any$>");
        //$anyTreeSet.replaceIn(_c, $typeRef.of("HashSet<$any$>") ); //convert TreeSet to HashSet
        System.out.println( _c );
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
        p_typeRef.of(Integer.class).replaceIn(_c, p_typeRef.of(Float.class));
        //System.out.println( _c );
    }
}
