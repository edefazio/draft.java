package draft.java.proto;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import draft.java.Expr;
import draft.java._class;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import static junit.framework.TestCase.assertNotNull;

public class pExprTest extends TestCase {

    public void testGenericExpr(){
        //LocalClassDeclarationExpr lc =  Expr.("class $any${}");
        
        //find EVERY lambda with a comment
        pExpr $anyLambda = pExpr.of("($params$)->$body$", e -> e.getComment().isPresent() );
        
        System.out.println( Expr.lambda("/** comment */ ()->true") );
        
        assertTrue( $anyLambda.matches( Expr.lambda("/** comment */ ()->true") ) );
        
        assertTrue( pExpr.lambda(l -> l.getComment().isPresent() ).matches("/** comment */ ()->true;") );
        
        /** A comment */
        //lass C{}
        
        //this disregards comments
        //StaticJavaParser.parseExpression(expression)
        
        
        
        //_class _c = _class.of($exprTest.class);
        //assertNotNull( $anyLocal.firstIn(_c) );
        
    }
    public void testStatic$expr(){
        _class _c = _class.of("C", new Object(){
            @aa(2) 
            int a = 1;
            int b = 3 + 4;
        });        
        assertEquals( 1, pExpr.list(_c, Expr.of(2) ).size());
        assertEquals( 4, pExpr.of(2).$("2", "number").listIn(_c).size());
    }
    
    
    
    @interface aa{
        int value();
    }

    public void test$exprPostParameterize(){
        //a template expression
        pExpr $e = pExpr.of("1 + 2");
        //post parameterize the + as an operator
        $e.$("+", "op");

        //verify I can match against other expressions
        assertTrue($e.matches("1 + 2"));
        assertTrue($e.matches("1 * 2"));
        assertTrue($e.matches("1 / 2"));
        assertTrue($e.matches("1 >> 2"));
        assertTrue($e.matches("1 << 2"));

        //works for expressions obviously
        assertTrue($e.matches(Expr.of("1 + 2")));

        //select returns the expression and
        assertNotNull($e.select(Expr.of("1 + 2")));

        //select returns the selected tokens
        assertTrue($e.select(Expr.of("1 * 2")).args.is("op", "*"));

        $e = pExpr.of("$a$ + $b$");
        @aa(1 + 2)
        class G{
            int f = 3 + 5;

            @aa(8+9)
            int g = 6 + 7;

        }
        _class _c = _class.of(G.class);
        assertEquals(4, $e.selectListIn(_c).size());
    }

    public void testExprOf(){
        pExpr $e = pExpr.of("1 + 2");
        assertEquals( $e.construct(), Expr.of("1 + 2"));
        assertTrue( $e.matches(Expr.of("1+2")));

        $e = pExpr.of("$a$ + $b$");
        assertTrue( $e.matches(Expr.of("1+2")));
    }

    public void testExprTypes(){
        boolean var = true;

        assertTrue( pExpr.assign("$var$=100").matches("y=100"));
        assertTrue(pExpr.arrayAccess("arr[$index$]").matches("arr[0]"));
        assertTrue( pExpr.arrayCreation("new $arr$[][]").matches("new xy[][]"));
        assertTrue(pExpr.arrayInitializer("{$any$}").matches("{1,2,3}"));
        assertTrue(pExpr.binary("$left$ > $right$").matches("a > b"));
        assertTrue(pExpr.of(true).matches("true"));
        assertTrue(pExpr.booleanLiteral(true).matches("true"));
        assertTrue(pExpr.of('c').matches( "'c'"));
        assertTrue(pExpr.cast("($type$)o").matches("(String)o"));
        assertTrue(pExpr.classExpr("$type$.class").matches("String.class"));
        assertTrue( pExpr.conditional("($left$ < $right$) ? $left$ : $right$;").matches("(a < b) ? a : b;"));
        assertEquals( Expr.of(3.14f), Expr.of(3.14f));
        assertEquals( Expr.of(12.3), Expr.of("12.3"));
        assertEquals( Expr.of("12.3f"), Expr.of("12.3f"));
        assertTrue(pExpr.doubleLiteral(12.3).matches("12.3"));
        assertTrue(pExpr.doubleLiteral("12.3f").matches("12.3f"));
        assertTrue(pExpr.enclosedExpr("($a$ + $b$)").matches("(100 + 200)"));
        assertTrue(pExpr.fieldAccess("my.$field$").matches("my.a"));
        assertTrue(pExpr.intLiteral(100).matches("100"));
        assertTrue(pExpr.of(100).matches("100"));
        assertTrue(pExpr.instanceOf("$obj$ instanceof String").matches("a instanceof String"));
        assertTrue( pExpr.longLiteral(100).matches( Expr.longLiteral( "100")));
        assertTrue(pExpr.lambda("$param$ -> a.$method$()").matches("x-> a.toString()"));
        assertTrue(pExpr.methodCall("$methodCall$($params$)").matches("a()"));
        assertTrue(pExpr.methodCall("$methodCall$($params$)").matches("a(1,2,3)"));
        assertTrue(pExpr.methodReference("$target$::$methodName$").matches("String::toString"));
        assertTrue(pExpr.nullExpr().matches("null"));
        assertTrue(pExpr.name("eric").matches("eric"));
        assertTrue(pExpr.objectCreation("new $Object$()").matches("new String()"));
        assertTrue(pExpr.objectCreation("new $Object$($params$)").matches("new Date(101010)"));
        assertTrue(pExpr.stringLiteral("Hello $name$").matches("\"Hello Eric\""));
        assertTrue(pExpr.superExpr().matches("super"));
        assertTrue( pExpr.thisExpr().matches("this"));
        assertTrue( pExpr.typeExpr("$type$").matches(Expr.typeExpr("AType")));
        assertTrue( pExpr.unary("!$var$").matches("!isDead"));
        assertTrue(pExpr.varDecl("int $var$").matches("int x"));

        assertTrue( pExpr.of("0b1010000101000101101000010100010110100001010001011010000101000101L")
                .matches("0b1010000101000101101000010100010110100001010001011010000101000101L"));
    }

    public void testSelect(){
        pExpr<IntegerLiteralExpr> e = pExpr.intLiteral("1").$("1", "val");
        assertTrue(e.matches("1"));
        class C{
            public void f(){
                int i = 1;
                System.out.println(2);
                System.out.println("A2");
                assert( 3== 4);
                System.out.println("multiple"+ 5);
            }

            public void g(){
                System.out.println("another method"+6+" values");
            }
        }
        List<pExpr.Select<IntegerLiteralExpr>> sel =  e.selectListIn( _class.of(C.class) );
        assertEquals(6, sel.size()); //verify that I have (6) selections

        //System.out.println(">>"+ sel.get(0).tokens );
        assertTrue(sel.get(0).args.is("val", "1"));
        assertTrue(sel.get(1).args.is("val", "2"));
        assertTrue(sel.get(2).args.is("val", "3"));
        assertTrue(sel.get(3).args.is("val", "4"));
        assertTrue(sel.get(4).args.is("val", "5"));
        assertTrue(sel.get(5).args.is("val", "6"));

        //use forAllIn to
        List<Integer>ints = new ArrayList<>();
        e.forEachIn(_class.of(C.class), ie -> ints.add(ie.asInt()));
        assertTrue( ints.contains(1) && ints.contains(2) && ints.contains(3) && ints.contains(4) && ints.contains(5) && ints.contains(6));
    }
    
    public void testAPI(){
        
        assertTrue(pExpr.arrayAccess(a -> a.getIndex().isIntegerLiteralExpr() ).matches("a[1]"));
        assertTrue(pExpr.arrayAccess("a[$any$]", a -> a.getIndex().isIntegerLiteralExpr() ).matches("a[1]"));
        assertFalse(pExpr.arrayAccess("a[$any$]", a -> a.getIndex().isIntegerLiteralExpr() ).matches("a[b.count()]"));
        _class _c = _class.of("C").field("int i=1;");
        assertTrue( pExpr.replace(_c, "1", "2").getField("i").initIs(2));
        
        assertTrue(pExpr.list(_c, "2").size() == 1);
        
        //assertTrue( $expr.list(_c, "2").size() == 1 );
        
        //look for every literal
        pExpr $bin = 
            pExpr.binary("$a$ > $b$", 
                b-> b.getLeft().isIntegerLiteralExpr() && b.getRight().isIntegerLiteralExpr());
        assertTrue($bin.matches("3 > 2"));
        assertFalse($bin.matches("3L > 2"));
        
        
        
        /*
        assertTrue( 
                $expr.of("a[$any$]")
                .constraint( (ArrayAccessExpr a) -> a.getIndex().isIntegerLiteralExpr() ).matches("a[1]"));
        */
    }
    
     public void testSelectlistQuery(){
        
        class C{
            int i = 1;
            int j = 2;            
            
        }             
        _class _c = _class.of(C.class);
        assertNotNull( pExpr.intLiteral("2").firstIn(_c));        
        assertNotNull( pExpr.intLiteral(1).firstIn(_c));                
        
        Predicate<IntegerLiteralExpr> p = (IntegerLiteralExpr i)-> i.asInt() % 2 == 1;
        pExpr.intLiteral( p );
        
        assertNotNull( pExpr.intLiteral( (i)-> i.asInt() % 2 == 1 ).firstIn(_c)); 
               
        pExpr $e = pExpr.of(1).$("1", "num");
        
    }
}
