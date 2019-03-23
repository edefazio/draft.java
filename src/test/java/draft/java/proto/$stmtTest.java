package draft.java.proto;

import com.github.javaparser.ast.stmt.Statement;
import draft.Tokens;
import draft.java.Stmt;
import draft.java._class;
import junit.framework.TestCase;

import java.util.List;
import java.util.function.Consumer;

public class $stmtTest extends TestCase {

    public void testStaticIsInListLambda(){
        _class _c = _class.of("C");
        
        assertFalse($stmt.isIn(_c, (Object $any$)->System.out.println($any$) ));
        _c = _class.of("C", new Object() { 
            void m(){
                System.out.println(1);
            }
        });
        
        assertTrue($stmt.isIn(_c, (Object $any$)->System.out.println($any$) ));
        
        assertNotNull( $stmt.of(($any$)->System.out.println($any$)).listIn(_c) );
    }
    
    public @interface r{

    }
    public void testCaseAnno(){
        char  c = 'a';


    }

    public void testLabelStmt(){
        $stmt $s = $stmt.of( ($any$)-> {label: System.out.println($any$);} );
        Statement s = $s.construct( "$any$" , 100);

        assertEquals( Stmt.of( "label: System.out.println(100);"), s );
    }

    // I have a lambda that is and Expression that contains statements
    // that
    public void testStatementMatchAsFieldLambda(){
        class L{
            Consumer<String> c = (String g)-> {System.out.println(g); System.out.println(1);};
        }
        $stmt $s = $stmt.of( ($any$)-> System.out.println($any$));
        _class _c = _class.of(L.class);
        assertEquals( 2, $s.listSelectedIn(_c).size());
        assertEquals( 2, $stmt.list(_c, "System.out.println($any$);" ).size() );
    }

    public void testReplaceStmt(){
        $stmt $s = $stmt.of( ($any$)-> System.out.println($any$) );
        $stmt $r = $stmt.of( ($any$)-> System.out.print($any$) );
        class L{
            void f(){
                System.out.println(1);
                assert true;
                System.out.println('v');
            }
        }
        _class _c = _class.of( L.class);
        $s.replaceIn(_c, $r);
        
        //verify we can match the (2) replacements
        assertEquals( 2, $r.listSelectedIn(_c).size());
        
        //static replace/ list
        _c = _class.of(L.class);
        $stmt.replace(_c, "System.out.println($any$);", "System.out.print($any$);");
        assertEquals( 2, $stmt.list(_c, "System.out.print($any$);").size() );
        System.out.println( _c );
    }

    /**
     * You can replaceIn a single statement with multiple dynamic statements
     * of a $snip
     * i.e.
     *
     *
     *
     */
    public void testReplace$stmtWith$snip(){
        $stmt $s = $stmt.of( ($any$)-> System.out.println($any$) );
        $snip $r = $snip.of( (Integer $any$)-> {
            /* comment */
            assert $any$ != null;
            System.out.print($any$);
        });
        class L{
            void f(){
                Integer l = 12;
                System.out.println(l);
                assert true;
                System.out.println('v');
            }
        }
        _class _c = _class.of( L.class);
        $s.replaceIn(_c, $r);

        System.out.println( _c );

        //verify we can match the (2) replacements
        assertEquals( 2, $r.listSelectedIn(_c).size());
        System.out.println( _c );
    }

    /**
     * Make sure we can match statements with/ without comments
     */
    public void testMatchComment(){
        $stmt $commA = $stmt.of( ($any$)-> {
            /* comment */
            assert $any$ != null;
        });

        assertNotNull( $commA.select( Stmt.of("/* comment */ assert r != null;")));

    }

    public void testMatchWithComments(){
        $stmt $s = $stmt.of( ()-> System.out.println(1) );
        Tokens tokens = $s.deconstruct(Stmt.of( ()-> System.out.println(1) ));
        assertNotNull( tokens );

        $s = $stmt.of( ($any$)-> System.out.println($any$) );
        tokens = $s.deconstruct(Stmt.of( ()-> System.out.println(1) ));
        assertNotNull( tokens );
        assertTrue( tokens.has("any", "1"));

        tokens = $s.deconstruct(Stmt.of( ()-> /** Comment */ System.out.println(1) ));
        assertNotNull( tokens );
        assertTrue( tokens.has("any", "1"));
    }

    public void testStmtMatchComments() {
        $stmt $s = $stmt.of("assert(true);");

        assertTrue($s.matches("/** comment*/assert(true);"));
        assertTrue($s.matches("assert(true);"));
    }
    public void testSM(){
        $stmt $s = $stmt.of("assert(true);");
        assertTrue($s.matches("/** comment*/assert(true);" ));

    }
    public void test$Stmt(){
        //_1_build a prototype
        $stmt $s = $stmt.of("System.out.println($any$);");

        //specialize a prototype via fill or compose
        Statement st = $s.fill(1);
        Statement st2 = $s.construct("any", 1);
        assertEquals(st, st2);

        //verify we get what we expect
        assertEquals( Stmt.of("System.out.println(1);"), st);

        //verify the prototype STILL matches the instance
        assertTrue( $s.matches(st));

        //verify we can partsMap the Statement and return the 1 (the filled parameter)
        assertTrue( $s.deconstruct(st).has("any", "1") );
    }

    /*
    public void testStr(){
        assertEquals(
                Stmt.switchCaseStmt("case 1: System.out.println(1);"),
                        Stmt.switchCaseStmt("case 1: System.out.println(1);"));

        assertEquals( Stmt.switchCaseStmt("case 1: System.out.println(1);").toString(),
                $stmt.switchCaseStmt("case 1: System.out.println(1);").stencil.toString());

        assertNotNull( Stencil.of("A").deconstruct("A"));
        assertTrue( $stmt.switchCaseStmt("case 1: System.out.println(1);")
                .matches(Stmt.switchCaseStmt("case 1: System.out.println(1);")));
    }
    */

    public void testStmtTypes(){
        //instead of Stmt.of, we can specify which TYPE of statement, verify the prototypes match the realized statements
        assertTrue( $stmt.assertStmt("assert($any$);").matches(Stmt.of("assert(1==1);")));
        assertTrue( $stmt.block("System.out.println($any$);").matches(Stmt.of("{System.out.println(\"anything\");}")));
        assertTrue( $stmt.breakStmt("break $where$;").matches(Stmt.of("break outer;")));
        assertTrue( $stmt.continueStmt("continue $where$;").matches(Stmt.of("continue outer;")));
        assertTrue( $stmt.ctorInvocationStmt("this($args$);").matches(Stmt.of("this(1,2,'a');")));
        assertTrue( $stmt.doStmt("do{ BODY(); }while($condition$)").$("BODY();", "BODY").matches(Stmt.of("do{ assert(1==1); }while(a<4)")));
        assertTrue( $stmt.expressionStmt("$any$+=1;").matches(Stmt.of("i+=1")));

        assertTrue( $stmt.forEachStmt("for(int $el$: $arr$){ assert($el$ > 0); }").matches(Stmt.of("for(int x:xs){ assert(x > 0); }")));
        assertTrue( $stmt.forStmt("for(int i=0;i<$count$;i++){ assert(i > 0); }").matches(Stmt.of("for(int i=0;i<gg;i++){ assert(i>0); }") ));
        assertTrue( $stmt.ifStmt("if($cond$){ print(\">1\");}").matches(Stmt.of("if(a>12345){ print(\">1\"); }")));
        assertTrue( $stmt.labeledStmt("$label$: assert(1==1);").matches(Stmt.of("myLabel: assert(1==1);")));
        assertTrue( $stmt.localClass("class $name${ int i; }").$("int i;", "BODY").matches(Stmt.of("class F{ void m(){System.out.println(1);} }")));
        assertTrue( $stmt.returnStmt("return $any$;").matches(Stmt.of("return this;")));
        assertTrue( $stmt.switchStmt("switch($any$){ default: assert($any$ > 0); }").matches(Stmt.of("switch(someVar){ default: assert(someVar > 0);}")));

        //System.out.println($stmt.switchCaseStmt("case 'a': System.out.println($c$);").fill('a') );
        //System.out.println(Stmt.switchCaseStmt("case 'a': System.out.println(1);").toString().replace('\t', ' ') );
        //assertEquals(Stmt.switchCaseStmt("case 1: System.out.println(1);"), Stmt.switchCaseStmt("case 1: System.out.println(1);") );
        //assertTrue( $stmt.switchCaseStmt("case 1: System.out.println(1);").matches(Stmt.switchCaseStmt("case 1: System.out.println(1);")));

        assertTrue( $stmt.synchronizedStmt("synchronized($any$){ BODY(); }").$("BODY()", "BODY").matches(Stmt.of("synchronized(someVar){ assert(true); }")));
        assertTrue( $stmt.throwStmt("throw $any$;").matches(Stmt.of("throw new BlahException();")));
        assertTrue( $stmt.tryStmt("try{ assert($any$); }catch($etype$ $e$){}").matches(Stmt.of("try{ assert(a>1); }catch(IOException ioe){}")));
        assertTrue( $stmt.whileStmt("while($condition$){ BODY();}").$("BODY();", "BODY").matches(Stmt.of("while(a){ assert(a); }")));
    }

    public void testSelect(){
        $stmt s = $stmt.of("System.out.println($any$);");
        assertTrue(s.matches(Stmt.of("System.out.println(1);")));
        class C{
            public void f(){
                int i = 100;
                System.out.println(1);
                System.out.println("A2");
                assert( 1== 1);
                System.out.println("multiple"+ 3);
            }

            public void g(){
                System.out.println("another method"+4+" values");
            }
        }
        List<$stmt.Select> sel =  s.listSelectedIn( _class.of(C.class) );
        assertEquals(4, sel.size());
    }
}
