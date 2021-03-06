package draft.java.proto;

import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import draft.Tokens;
import draft.java.Expr;
import draft.java.Stmt;
import draft.java._body;
import draft.java._class;
import draft.java._method;
import draft.java.proto.$proto.$args;
import draft.java.proto.$stmt.Select;
import java.util.ArrayList;
import junit.framework.TestCase;

import java.util.List;
import java.util.function.Consumer;
import static junit.framework.TestCase.assertTrue;

/**
 * Passing in templates as parameters to other templates
 * 
 * @author Eric
 */
public class SstmtTest extends TestCase {

    /**
     * $labels are easy ways to optional injecting removing or overrideing 
     * code parameterized statements within a code body
     * 
     * to explain: we introduce a labeled statement with the $ prefix somewhere 
     * within code:
     * 
     * $label : assert(1==1);
     * 
     * when we "construct" the statement, we treat labeled statements with the 
     * $ prefix specially...
     * 
     * we can
     * SHOW
     * HIDE
     * or
     * OVERRIDE
     * 
     */
    public void testExplain$label(){
        // with $block:
        $stmt $s = $stmt.of("if(a) { $label: doIt(); }");  
        // we can :
    
        Statement st = null;
        // SHOW                   we passed in a Boolean true, this means show
        //                        the content at the $label (& remove the $label)
        st = $s.construct("label",true);        
        assertTrue($stmt.of("if(a){ doIt();}").matches(st) );
        
        // HIDE                    by passing in false, we hide the code at the
        //                         $label (and the label itself)
        st = $s.construct("label",false);        
        assertTrue($stmt.of("if(a){ }").matches(st) );
        //                         null (or not found) also hides the code   
        st = $s.construct("label",null);        
        assertTrue($stmt.of("if(a){ }").matches(st) );
        
        // OVERRIDE                pass in a statement here b(); and Override
        //                         what is at the $label
        st = $s.construct("label",Stmt.of("b();"));        
        assertTrue($stmt.of("if(a){b();}").matches(st) );
        
        // OVERRIDE               pass in another $stmt that will be constructed
        st = $s.construct("label",$stmt.of("assert($cond$);"), "cond", "1==1" ); 
        assertTrue( $stmt.of("if(a){assert(1==1);}").matches(st));
        
        //can replace with a block statement
         st = $s.construct("label",$stmt.of("{assert($cond$); System.out.println(1);}"), "cond", "1==1" ); 
        assertTrue( $stmt.of("if(a){ {assert(1==1); System.out.println(1); } }").matches(st));
        
    }
    
    
    public void testExplainOptionalStatementsAndBlocks(){
        //sometimes we want to define optional blocks/statements blocks that
        //can appear within prototype code... for example, if we have simple 
        //value object Base:        
        class Base{
            int id;
            public String toString(){
                return " id: "+id;
            }
        }        
        // and we want to create a
        //toString
    }
    
    //optional statements
    //
    public void testConstruct$LabeledStatements(){
        //test no labeled statements
        Statement st = 
            $stmt.construct$LabelStmt(Stmt.of("{}"), new Tokens() );
        assertTrue( $stmt.of("{}").matches(st) );
        
        //if it's null or false.... remove it        
        st = $stmt.construct$LabelStmt(Stmt.of("{$l: assert true;}"), new Tokens() );
        assertTrue( st instanceof BlockStmt);
        assertTrue( st.asBlockStmt().isEmpty() );        
        st = $stmt.construct$LabelStmt(Stmt.of("{$l: assert true;}"), Tokens.of("l", false) );
        assertTrue( st instanceof BlockStmt);
        assertTrue( st.asBlockStmt().isEmpty() );        
        
        //if (the value of the $param "l" is true, leave the code in (without the label)
        st = $stmt.construct$LabelStmt(Stmt.of("{$l: assert true;}"), Tokens.of("l", true) );
        assertTrue( st instanceof BlockStmt);
        assertTrue( !st.asBlockStmt().isEmpty() );        
        assertTrue( $stmt.of("{assert true;}").matches( st ) );        
        assertTrue( $stmt.of("assert true;").matches( st.asBlockStmt().getStatement(0)) );        
        
        
        //Override at Labeled Statement
        //if (the value of the $param "l" is a statement), replace the code with the statement
        st = $stmt.construct$LabelStmt(Stmt.of("{$l: assert true;}"), Tokens.of("l", Stmt.of("assert(1==1);")) );
        assertTrue( st instanceof BlockStmt);
        assertTrue( !st.asBlockStmt().isEmpty() );        
        assertTrue( $stmt.of("{assert (1==1);}").matches( st ) );        
        
        
        
        
    }
    
    public void test$stmtOfBlockInternalsWithComment(){
        //match empty block
        $stmt s = $stmt.of("{}");        
        assertTrue( s.matches("{}") );
        
        //match single line block
        s = $stmt.of("{ assert true; }");
        assertTrue(s.matches("{ assert true; }"));
        assertTrue(s.matches("{ /* comment */ assert true; }"));
        
        //match multi nest block
        s = $stmt.of("{ if(1==1){ doIt(); } else{ System.out.println(1); } }");
        assertTrue(s.matches("{ if(1==1){ doIt(); } else{ System.out.println(1); } }"));
        assertTrue( s.matches("{ /* comment*/ if(1==1){ /*comment*/ doIt(); } else{ /*comment */ System.out.println(1); } }"));
        
        //multi statement block
        s = $stmt.of("{ doIt(); doNext(); } ");
        
        
    }
    
    public void test$stmtWithMultiLineBlock(){
        $stmt s = $stmt.of( (String $a$, Integer $b$)->{
            System.out.println($a$);
            if( $b$ > 2 ){
                System.out.println( "$b$ > 2");                
                assertTrue( $a$ == "eric");                
            }
        });
        
        class C{
            public void f( String a, int b){
                System.out.println(a);
                if( b > 2){
                    System.out.println( "b > 2" );
                    assertTrue( a == "eric" );
                }                
            }
        }
        assertEquals(1, s.count(C.class));
        
        
    }
    
    public void testStmtAnyMatchesEmptyOrLongBlocks(){
        //assertTrue( $snip.any().matches( _method.of("void m();").getBody().ast() ));
        assertTrue( $stmt.of().matches( _method.of("void m(){}").getBody().ast() ));
        assertTrue( $stmt.of().matches(Stmt.assertStmt("assert(1==1)")));
        assertTrue( $stmt.of().matches(Stmt.breakStmt("break;")));
        assertTrue( $stmt.of().matches(Stmt.assertStmt("assert(1==1)")));
        
        assertTrue( $stmt.of().matches( _body.of( new Object(){
            void m(){
                int i=0;
                int j=1;
                assertTrue(1==1);
                label: assertTrue(2==2);
                System.out.println("Hello");
            }
        }).ast() ) );
        
    }
    public void testStmtMatchesEmptyBlockEmptyStmt(){
        assertTrue($stmt.of().matches("{}"));
        assertTrue($stmt.of().matches(";"));
        
        assertTrue($stmt.of().matches( Stmt.of("a(1);") ) );
        assertTrue($stmt.of().matches( Stmt.block("{ a=1; assert(a != 0); }") ) );        
        
        assertFalse($stmt.of().matches( (Statement)null));        
    }
    
    public void test$protoQueryTutorial(){
        _class _c = _class.of("C", new Object(){
            void m(){
                //comment
                assertTrue( true );
                assertTrue(1==1);
                System.out.println( "Hello World!" );
            }
        });
        //the easy things should be easy
        assertTrue( $stmt.of("assertTrue(true);").firstIn(_c).getComment().isPresent());
        assertTrue( $stmt.of("assertTrue(true);").listIn(_c).size() ==1);
        
        $stmt.of("assertTrue(true);").replaceIn(_c, "Assert.assertTrue(true);");
        $stmt.of("Assert.assertTrue(true);").removeIn(_c);
        
        assertNull($stmt.of("Assert.assertTrue(true);").firstIn(_c));
    }
    
    
    public void test$protoQueryTutorialInstance(){
        _class _c = _class.of("C", new Object(){
            void m(){
                //comment
                assertTrue( true );
                assertTrue(1==1);
            }
        });
        $stmt $assertTrue = $stmt.of( ()->assertTrue(true));
        
        //the easy things should be easy
        assertNotNull( $assertTrue.firstIn(_c));
        assertTrue( $assertTrue.listIn(_c).size() ==1);
        
        $assertTrue.replaceIn(_c, "Assert.assertTrue(true);");
        $stmt.of("Assert.assertTrue(true);").removeIn(_c);
        
        assertNull($stmt.of("Assert.assertTrue(true);").firstIn(_c));        
    }
    
    public void test$ProtoWildcardVariablesAndSelect(){
        _class _c = _class.of("C", new Object(){
            void m(){
                //comment
                assertTrue( true );
                assertTrue(1==1);
                System.out.println("Hello World!");
            }
        });
        
        $stmt<Statement> $assertAny = $stmt.of("assertTrue($any$);");
        //this produces the same result just using the lambda as input 
        $assertAny = $stmt.of( (Boolean $any$)->assertTrue($any$));
        
        //here we match BOTH assert statements because the $any$ wildcard/variable
        //will match any String argument pattern inside the ()'s
        // we match: assertTrue( true ); & assertTrue(1==1)
        assertTrue( $assertAny.listIn(_c).size() == 2 );
        
        assertTrue( $assertAny.firstIn(_c).getComment().isPresent());
        
        
        //using "select" will parse . match and return the variables          
        assertTrue( $assertAny.selectFirstIn(_c).args.is("any", "true"));
        assertTrue( $assertAny.selectFirstIn(_c).args.is("any", true));
        
        //you can statically query for a Select object which wraps the result
        assertTrue( $stmt.of("assertTrue($any$);").selectFirstIn(_c).astStatement.getComment().isPresent());
        assertTrue( $stmt.of("assertTrue($any$);").listSelectedIn(_c).size() ==2);     
        
        //System.out.println( $stmt.selectFirst(_c, "System.out.println($any$);") );
        //What is GREAT about Select is that we can look at/verify the wildcard variables 
        assertTrue( $stmt.of( "System.out.println($any$);").selectFirstIn(_c).is("any", "Hello World!"));     
        assertTrue( $stmt.of("assertTrue( $a$ == $b$ );").selectFirstIn(_c)
                .is("a", 1,"b", 1) );       
        
        //$expr.of(new int[]{1,2,3}).listIn(_c);
        
        assertEquals( 2, $expr.of(1).listIn(_c).size());
    }
    
    public void testFullApi(){
        _class _c = _class.of("C", new Object(){
            void m(){
                //comment
                assertTrue( true );
                assertTrue(1==1);
            }
        });
        //the easy things should be easy
        assertNotNull( $stmt.of("assertTrue(true);").firstIn(_c));
        assertTrue( $stmt.of("assertTrue(true);").listIn(_c).size() ==1);
        assertTrue( $stmt.of("assertTrue(true);").selectFirstIn(_c).astStatement.getComment().isPresent());
        $stmt.of("assertTrue(true);").replaceIn(_c, "Assert.assertTrue(true);");
        $stmt.of("Assert.assertTrue(true);").removeIn(_c);
        
        $stmt.of("assertTrue($any$);").firstIn(_c);
        $stmt.of( ()-> assertTrue(true) )
                .firstIn(_c);
        $stmt.of( ()-> assertTrue(true) ).$("true", "any").firstIn(_c);
        
        $stmt.of( ($any$)-> assertNotNull($any$) ).firstIn(_c);
        
        $stmt.of("assertTrue(1==1);").firstIn(_c);
        $stmt.of("assertTrue( $obj$.equals($obj$) );").firstIn(_c);
        
        $stmt.of("assertTrue( $obj$.equals($obj$) );").firstIn(_c);
        $stmt.of("assertTrue( $a$.equals($b$) );").firstIn(_c);
        
    }
    
    public void testStaticIsInListLambda(){
        _class _c = _class.of("C");
        
        assertNull($stmt.of("System.out.println($any$);").firstIn(_c));
        
        _c = _class.of("C", new Object() { 
            void m(){
                System.out.println(1);
            }
        });
        

                
        assertNotNull($stmt.of("System.out.println($any$);").firstIn(_c));
        
        assertNotNull($stmt.of("System.out.println($any$);").listSelectedIn(_c).get(0));
        
        assertNotNull( $stmt.of(($any$)->System.out.println($any$)).listIn(_c));
    }
    
    public @interface r{

    }
    public void testCaseAnno(){
        char  c = 'a';


    }

    public void testLabelStmt(){
        Stmt.of( (Object $any$)-> {label: System.out.println($any$);} );
        $stmt $s = $stmt.of((Object $any$)-> {label: System.out.println($any$);});
        Statement s = $s.construct( "$any$" , 100);

        assertEquals( Stmt.of( "label: System.out.println(100);"), s );
    }

    // I have a lambda that is and Expression that contains statements
    // that
    public void testStatementMatchAsFieldLambda(){
        class L{
            Consumer<String> c = (String g)-> {System.out.println(g); System.out.println(1);};
        }
        $stmt $s = $stmt.of(($any$)-> System.out.println($any$));
        _class _c = _class.of(L.class);
        assertEquals( 2, $s.listSelectedIn(_c).size());
        assertEquals( 2, $stmt.of("System.out.println($any$);").listIn(_c).size() );
    }

    public void testReplaceStmt(){
        $stmt $s = $stmt.of(($any$)-> System.out.println($any$) );
        $stmt $r = $stmt.of(($any$)-> System.out.print($any$) );
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
        //$stmt.replace(_c, );
        System.out.println( _c );
        assertEquals( 2, $stmt.of("System.out.println($any$);").listIn(_c).size() );
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
        //$stmt $s = $stmt.of( Stmt.of( ()-> System.out.println(1) ));
        $stmt $s = $stmt.of( ()-> System.out.println(1) );
        //$args tokens = $s.deconstruct(Stmt.of( ()-> System.out.println(1) ));
        
        Select sel = $s.select(Stmt.of( ()-> System.out.println(1) ));
        assertNotNull( sel );

        //$s = $stmt.of( ($any$)-> System.out.println($any$) );
        $s = $stmt.of( ($any$)-> System.out.println($any$) );
        
        sel = $s.select(Stmt.of( ()-> System.out.println(1) ));
        //tokens = $s.deconstruct(Stmt.of( ()-> System.out.println(1) ));
        assertNotNull( sel );
        assertTrue( sel.is("any", "1"));

        //tokens = $s.deconstruct(Stmt.of( ()-> /** Comment */ System.out.println(1) ));
        sel = $s.select(Stmt.of( ()-> /** Comment */ System.out.println(1) ));
        assertNotNull( sel );
        assertTrue( sel.is("any", "1"));
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
        assertTrue( $s.select(st).is("any", "1") );
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
        assertTrue( $stmt.blockStmt("System.out.println($any$);").matches(Stmt.of("{System.out.println(\"anything\");}")));
        assertTrue( $stmt.breakStmt("break $where$;").matches(Stmt.of("break outer;")));
        assertTrue( $stmt.continueStmt("continue $where$;").matches(Stmt.of("continue outer;")));
        assertTrue( $stmt.ctorInvocationStmt("this($args$);").matches(Stmt.of("this(1,2,'a');")));
        assertTrue( $stmt.doStmt("do{ BODY(); }while($condition$)").$("BODY();", "BODY").matches(Stmt.of("do{ assert(1==1); }while(a<4)")));
        assertTrue( $stmt.expressionStmt("$any$+=1;").matches(Stmt.of("i+=1")));

        assertTrue( $stmt.forEachStmt("for(int $el$: $arr$){ assert($el$ > 0); }").matches(Stmt.of("for(int x:xs){ assert(x > 0); }")));
        assertTrue( $stmt.forStmt("for(int i=0;i<$count$;i++){ assert(i > 0); }").matches(Stmt.of("for(int i=0;i<gg;i++){ assert(i>0); }") ));
        assertTrue( $stmt.ifStmt("if($cond$){ print(\">1\");}").matches(Stmt.of("if(a>12345){ print(\">1\"); }")));
        assertTrue( $stmt.labeledStmt("$label$: assert(1==1);").matches(Stmt.of("myLabel: assert(1==1);")));
        assertTrue( $stmt.localClassStmt("class $name${ int i; }").$("int i;", "BODY").matches(Stmt.of("class F{ void m(){System.out.println(1);} }")));
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
