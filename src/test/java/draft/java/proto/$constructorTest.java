package draft.java.proto;

import draft.java.Stmt;
import draft.java._class;
import draft.java._constructor;
import draft.java.macro._ctor;
import junit.framework.TestCase;


public class $constructorTest extends TestCase {

    public void testNoArgConstructor(){
        $constructor $c = $constructor.of( "public $name$(){ assert(1==1); }" ).$(Stmt.of("assert(1==1);"), "body");
        assertTrue($c.matches(_constructor.of("public C(){}")));
    }
    
    /**
     * Verify
     */
    public void testCompareWithJavadocAndAnnotationsAndComments(){
        $constructor $c = $constructor.of( "public $name$(int x){ this.x = x; }" );

        assertTrue($c.matches(_constructor.of("public C(int x){ this.x = x;}")));
        assertTrue($c.matches(_constructor.of("/** Javadoc */ public C(int x){ this.x = x;}")));
        assertTrue($c.matches(_constructor.of("/** Javadoc */ @Deprecated public C(int x){ this.x = x;}")));
        assertTrue($c.matches(_constructor.of("public C(int x){ /* block comment*/ this.x = x;}")));
        assertTrue($c.matches(_constructor.of("/** Javadoc */ @Deprecated public C(int x){ /** jd comment */ this.x = x;}")));

        $c = $constructor.of( "/** JAVADOC $name$ */ public $name$(int x){ this.x = x; }" );
        assertTrue($c.matches(_constructor.of("public C(int x){ this.x = x;}")));
        assertTrue($c.matches(_constructor.of("/** Javadoc */ public C(int x){ this.x = x;}")));
        assertTrue($c.matches(_constructor.of("/** Javadoc */ @Deprecated public C(int x){ this.x = x;}")));
        assertTrue($c.matches(_constructor.of("public C(int x){ /* block comment*/ this.x = x;}")));
        assertTrue($c.matches(_constructor.of("/** Javadoc */ @Deprecated public C(int x){ /** jd comment */ this.x = x;}")));
    }

    /** I should partsMap to create stencils for each component
    public void testCompareAnySignature(){
        //almost anything
        $constructor $c =  $constructor.of("private $name$(int x){ assert(true); }")
                .$("private", "mods")
                .$("int x", "params")
                .$( Stmt.of("assert(true);"), "BODY");
        //$BODY(); //this means ANY BODY will do
        //$PARAMETERS();

        System.out.println( $c );


        assertTrue( $c.matches(_constructor.of("private Name(int x){ assert(true); }")));
        assertTrue( $c.matches(_constructor.of("public Name(int x){ assert(true); }")));
        assertTrue( $c.matches(_constructor.of("public Name(String x){ assert(true); }")));
        assertTrue( $c.matches(_constructor.of("public Name(String x){ System.out.println(1); }")));
    }
    */

    public void testBuildViaAnonymousClass(){
        $constructor $ct = $constructor.of( new Object() {
                 int a; String name;

                 @_ctor public void ct(int a, String name ){
                     this.a = a;
                     this.name = name;
                 }
            }
        );

        //System.out.println( $ct.signatureStencil );
        //System.out.println( $ct.construct() );
        //System.out.println( $ct.construct() );

        _constructor _c1 =  $ct.construct();
        _constructor _c2 =  $ct.construct();
        
        assertEquals( _c1, _c2);
        
        //System.out.println( "START");
        assertTrue( _c1.is( _c2.toString() ));
        //System.out.println( "END");
        
        
        assertTrue(
                $ct.construct().is("public ct(int a, String name){",
                "this.a = a;",
                "this.name = name;",
                "}"));

        $ct = $constructor.of( new Object(){
            String s;
            /**
             * Some Javadoc
             */
            @Deprecated
            public void Ctor( ){
                this.s = "Some S";
            }
        });
        _constructor _ct = $ct.construct();
        //verify that the ANNOTATIONS and JAVADOC are transposed
        assertTrue( _ct.hasAnno(Deprecated.class));
        assertTrue( _ct.getJavadoc().getContent().contains("Some Javadoc"));
    }

    public void testCtorLabels() {
        $constructor $c = $constructor.of(new Object() {
            public void C() {
                label:
                System.out.println(12);
            }
        });

        assertTrue($c.construct().getBody().isEmpty());
        assertEquals($c.construct("label", true).getBody().getStatement(0),
                Stmt.of("System.out.println(12);"));
    }
    public void testCtorLabelForAddingCode(){
        $constructor $c = $constructor.of( new Object(){
            public void C(){
                label:{}
            }
        });

        assertTrue($c.construct().getBody().isEmpty());
        assertEquals( Stmt.of("System.out.println(1);"),
                $c.construct("label", "System.out.println(1);").getBody().getStatement(0));

        //
        assertEquals( Stmt.of("System.out.println(1);"),
                $c.construct("label", Stmt.of("System.out.println(1);")).getBody().getStatement(0));

        //block Statement
        assertEquals( Stmt.of("System.out.println(1);"),
                $c.construct("label", Stmt.block("{ System.out.println(1); }")).getBody().getStatement(0));




        /*
        //Comment
        assertEquals( ,
                $c.compose("label", Ast.comment("/* hello "))
                        .getBody().getStatement(0));
                        */
    }



    public void testC(){
        //match any no arg CONSTRUCTORS
        $constructor $noArgNoBody = $constructor.of("$name$(){}");

        assertTrue( $noArgNoBody.matches(_constructor.of("name(){}") ));

        class Y{
            Y(){ }
        }
        _class _c = _class.of(Y.class);
        assertNotNull( $noArgNoBody.selectListIn(_c).get(0) );

        class Z{
            Z(){
            }
        }
        _c = _class.of(Z.class);
        assertTrue( $noArgNoBody.selectListIn(_c).size() == 1 );

        System.out.println( $noArgNoBody.selectListIn(_c) );

        assertTrue( $noArgNoBody.selectListIn(_c).get(0).args.is("name", "Z"));
        //$c.select(_constructor.of("public "))
    }

    public void testAnyParams(){
        $constructor $oneArgInit = $constructor.of("$ctorName$ ($type$ $name$){",
                "this.$name$ = $name$;",
                "}");

        assertTrue( $oneArgInit.matches(_constructor.of("A(String s){ this.s = s;}") ));

        assertTrue( $oneArgInit.select(_constructor.of("A(String s){ this.s = s;}") ).args.is("ctorName", "A") );
        assertTrue( $oneArgInit.select(_constructor.of("A(String s){ this.s = s;}") ).args.is("type", "String") );
        assertTrue( $oneArgInit.select(_constructor.of("A(String s){ this.s = s;}") ).args.is("name", "s") );
    }
}
