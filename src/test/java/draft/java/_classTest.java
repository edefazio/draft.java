package draft.java;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.*;

import com.github.javaparser.ast.comments.*;
import draft.java.macro.*;
import draft.java.runtime.*;
import junit.framework.TestCase;
import draft.java._parameter.*;
import draft.java._typeParameter.*;
import java.net.URI;
import test.ComplexClass;
import test.NativeMethod;

import static java.util.Collections.emptyList;
import static java.util.Collections.sort;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 *
 * 
 * @author Eric
 */
public class _classTest extends TestCase {
    
    
    interface MemberI{ }
    
    interface $Member{        
        interface MemberMember{}
    }
    
    public static class $Base{
        public static class Mem{
            
        }
    }
    
    /**
     * We previously had issues with classes that started with $ for 
     * implementing and extending (expecially for MEMBER CLASSES)
     * this will test those scenarios
     * Classes that start with a 
     */
    public void testImplementMemberClass(){
        _class _c = _class.of("C")
                .implement(MemberI.class).implement($Member.class).implement($Member.MemberMember.class);
        _c = _class.of("C").extend($Base.class);
        _c = _class.of("C").extend($Base.Mem.class);
        System.out.println( _c );
    }
    
    public void testToString(){
        _class _c = _class.of("C", new Object(){
            int x = 100, y = 200;
            public String toString(){
                return "("+x+","+y+")";
            }            
        });
        
        assertEquals("(100,200)", _proxy.of(_c).toString());
        
        _c = _class.of("C")
            .fields("int x=100,y=200;")
            .method("public String toString(){",
                    "return \"(\"+x+\",\"+y+\")\";",
                    "}")
            .constructor("(){}")
            .constructor("(int x, int y){ this.x = x; this.y = y;}");
        
        assertEquals("(1,2)", _proxy.of(_c, 1,2).toString());
        
        _c = _class.of("C", new Object(){
            int x = 100, y = 200;
            
            /**
             * Note this is an "implied constructor,
             * it is VOID, and it's name "C" is the name of the type
             * it's converted into a constructor when we create the _class
             */
            public void C(int x, int y){
                this.x = x; this.y = y;
            }
            
            public String toString(){
                return "("+x+","+y+")";
            }            
        });
        assertEquals("(1,2)", _proxy.of(_c, 1,2).toString());
        
        
    }

    public interface ChumbaWomba{
        Map m(UUID u) throws IOException;
    }
    
    public @interface A{
        
    }
    
    //Instead of manually having to import each class
    // infer the imports from the API
    public void testImplementInferImports(){
        _class _c = _class.of("C").implement(new ChumbaWomba(){
            URI u = null;
            @Override
            public Map m(UUID u) throws IOException {
                return new HashMap<>();
            }            
        });
        
        // when we pass the anonymous ChuimbaWomba instance 
        // in to the implement method...
        assertTrue( _c.hasImport(Map.class));
        assertTrue( _c.hasImport(URI.class));
        assertTrue( _c.hasImport(IOException.class));
        assertTrue( _c.hasImport(UUID.class));
        assertTrue( _c.hasImport(ChumbaWomba.class));
        
        //System.out.println( _c );
    }
    
    public abstract class BBC{
        public abstract Set theSet(URI uri, UUID uuid) throws IOException;
    } 
    
    // When you extend and provide the implementation, we
    // the imports are inferred
    public void testExtendInferImports(){
        _class _c = _class.of("B").extend( new BBC(){
                    
            AtomicBoolean ab;
                    
            public Set theSet(URI uri, UUID uuid) throws IOException {
                return new HashSet();
            }
            
        });
       // System.out.println( _c );
        
        assertTrue( _c.hasImport(BBC.class));
        assertTrue( _c.hasImport(AtomicBoolean.class));
        assertTrue( _c.hasImport(Set.class));
        assertTrue( _c.hasImport(UUID.class));
        assertTrue( _c.hasImport(URI.class));
        assertTrue( _c.hasImport(IOException.class));        
    }
    
    // When you extend and provide the implementation, we
    // the imports are inferred
    public void testBodyInferImports(){
        _class _c = _class.of("B").body( new BBC(){
                    
            AtomicBoolean ab;
                    
            public Set theSet(URI uri, UUID uuid) throws IOException {
                return new HashSet();
            }
            
        });
        System.out.println( _c );
        
        assertTrue( _c.hasImport(BBC.class));
        assertTrue( _c.hasImport(AtomicBoolean.class));
        assertTrue( _c.hasImport(Set.class));
        assertTrue( _c.hasImport(UUID.class));
        assertTrue( _c.hasImport(URI.class));
        assertTrue( _c.hasImport(IOException.class));        
    }
    
    public void testInferLocalClassImportsBasedOnAPI(){
        class Local{
            Map map;
            public UUID uuid() throws IOException{ return UUID.randomUUID();}
        }
        _class _c = _class.of(Local.class);
        
        //System.out.println( _c);
        assertTrue( _c.hasImport(Map.class));// UUID.class, IOException.class) );
        
        
        //System.out.println( _c );
    }
    
    public void testJavadoc(){
        _class _c = _class.of("C").javadoc("Oh, Hello");
        assertEquals( "Oh, Hello", _c.getJavadoc().getContent());
    }

    public void testCopyright(){
        _class _c = _class.of( ClassWithCopyright.class );
        Comment c = _c.getHeaderComment();
        //System.out.println( c.getContent() );

        _c.setHeaderComment("The Header Comment");
        //System.out.println( _c );
        assertEquals( _c.getHeaderComment().getContent(), "The Header Comment" );

        _c.setHeaderComment(new BlockComment("The Header Comment"));
        assertEquals( _c.getHeaderComment().getContent(), "The Header Comment" );

        //for type... I should do something that says...
        // get or set the Header Comments

        //System.out.println(  "CONTAINED " + _c.ast().getAllContainedComments());
        //System.out.println( _c.ast().getOrphanComments() );

        /*
        //System.out.println( _c );
        if( _c.ast().getComment().isPresent() ){
            System.out.println( "CompilationUnitComments"+ _c.ast().getComment().get());
        }
        if( _c.astType().getComment().isPresent() ){
            System.out.println( "TypeComments"+ _c.astType().getComment().get());
        }
        System.out.println( _c.getJavadoc() );
        */
    }

    public void testImports(){
        _class _c = _class.of("aaaa.b.C")
                .importStatic( System.class )
                .main( "out.println(111);");
        _javac.of(_c);

        _c = _class.of("G").imports("java.util.*")
                .main(()-> System.out.println(UUID.randomUUID()));
        _javac.of(_c);
        //_proxy.of(_c).main();

        _c = _class.of("J").imports("java.util.*", "java.io.*")
                .field("Map m = new HashMap();")
                .field("File f = null;")
                .main(()-> System.out.println(UUID.randomUUID()))
                .method("void t() throws IOException {}");
        _javac.of(_c);
        //_proxy.of(_c).main();
    }

    public void test11Draft(){
        _class _c = _class.of("com.example.helloworld.HelloWorld", new Object(){
            List<Hoverboard> beyond() {
                List<Hoverboard> result = new ArrayList<>();
                result.add(createNimbus(2000));
                result.add(createNimbus("2001"));
                result.add(createNimbus(THUNDERBOLT));
                sort(result);
                return result.isEmpty() ? emptyList() : result;
            }

            @_remove class Hoverboard implements Comparable {
                public int compareTo(Object o){ return 1;}
            }
            @_remove public Hoverboard createNimbus( int val ){ return null; }
            @_remove public Hoverboard createNimbus( String val ){ return null; }
            @_remove public Hoverboard createNimbus( Hoverboard board ){ return null; }
            @_remove Hoverboard THUNDERBOLT = new Hoverboard();
        }).importStatic(Collections.class.getCanonicalName()+".*",
                "com.mattel.Hoverboard.Boards.*",
                "com.mattel.Hoverboard.createNimbus.*");
        System.out.println( _c);
    }

    public void testMethodParameterTypes(){
        class L{
            public void sortByLength(List<String> strings) {
            }
        }
        _method _m = _class.of(L.class).getMethod("sortByLength");

        //System.out.println( );
        //Arrays.stream( L.class.getMethods()[0].getGenericParameterTypes() ).forEach(t -> System.out.println("&&&&&&& " + t));
        //assertTrue(_m.hasParametersOfType(L.class.getMethods()[0].getGenericParameterTypes()));
        assertTrue(_m.hasParametersOf(L.class.getMethods()[0]));
        //assertTrue(_m.hasParametersOfType(L.class.getMethods()[0].getParameterTypes()));
    }

    public void test20Draft(){
        _class _c = _class.of("HelloWorld", new Object() {
            public void sortByLength(List<String> strings) {
                sort(strings, new Comparator<String>() {
                    public int compare(String a, String b) {
                        return a.length() - b.length();
                    }
                });
            }
        }).importStatic(Collections.class)
          .imports(Comparator.class, List.class);

        System.out.println(_c);
        _javac.of( _c );
    }

    public void testSimpleAssign(){
        _class _c = _class.of("public abstract class HelloWorld");
        //note... the above SHOULD WORK without {}
    }

    // verify that, when I define a class via the body of an anonymous object
    // the field types,
    // method return types
    // method parameter types
    // thrown exception types
    // are imported automatically into the _class
    public void testAutoImportInferredFromAnonymousObject(){
        _class _c = _class.of("aaa.gg.FF", new Serializable(){
            Map f;

            public List m(Set s, UUID uuid) throws IOException {
                return null;
            }
        });
        
        System.out.println( _c);
        assertTrue( _c.hasImport(Serializable.class)); //interface implemented
        assertTrue( _c.hasImport(Map.class)); //field type
        assertTrue( _c.hasImport(List.class));
        assertTrue( _c.hasImport(Set.class));
        assertTrue( _c.hasImport(UUID.class));
        assertTrue( _c.hasImport(IOException.class));
    }

    public interface MyI{
        void m();
    }

    public void testImplement(){
        _class _c = _class.of("C").implement( new MyI(){
            public void m(){
                System.out.println(1);
            }
        });
        System.out.println( _c );

        _c.forFields( f-> f.isType(int.class), f-> System.out.println(f) );
    }

    public abstract class Base{
        public abstract int getVal();
    }

    public void testExtends(){
        _class _c = _class.of("C").extend( new Base(){
            public int getVal(){
                return 123;
            }
        });
        System.out.println( _c );
    }


    public interface One{
        String id();
    }

    public void testFullyQualifiedTypeParamsEtc(){
        _class a = _class.of("A")
                .typeParameters("<T extends B, C extends List<D>>")
                .extend("aaaa.Base")
                .implement("rrrrr.T", "iiii.R")
                .method("aaaa.B blah( bbb.C c){}");

        _class b = _class.of("A")
                .typeParameters("<C extends java.util.List<ddd.D>, T extends bbbb.B>")
                .extend("Base")
                .implement("R", "T")
                .method("B blah( C c){}");

        assertEquals( a, b);
        assertEquals( a.hashCode(), b.hashCode());

    }

    public void testTypeHashCode(){
        assertEquals(
                Ast.typeHash( Ast.typeRef("String")),
                Ast.typeHash( Ast.typeRef("java.lang.String" )) );
    }

    /**
     * Verify that fully qualified vs out of order Classes evaluate to the same
     * and the hashcode is same
     */
    public void testExtendsEqualsFullyQualified(){

        _class _a = _class.of("A").extend("aaaa.bbb.C");
        _class _b = _class.of("A").extend("C");

        assertEquals( _a.hashCode(), _b.hashCode() );
        assertEquals( _a, _b);

        _a = _class.of("B").extend("aaaa.V").implement("bbbb.C", "cccc.D");
        _b = _class.of("B").extend("V").implement("D", "C");

        assertEquals( _a.hashCode(), _b.hashCode() );
        assertEquals( _a, _b);


        //if fully qualified but NOT equal, verify
        _a = _class.of("B").extend("aaaa.V");
        _b = _class.of("B").extend("bbbb.V");
        assertNotSame( _a, _b);

    }

    public void testDynamicImpl(){

        //of course we could ALWAYS do this with an anonymous class:
        One normal = new One(){
            public String id(){
                return "STATIC";
            }
        };


        //we might want to mutate and/or formalize (into a top level class)
        // the implementation,

        //this will:
        // 1) make the _class "D" implement the One interface
        // 2) make he _class "D" import the One interface
        // 3) add the id method to the _class
        // 4) compile and load the new class D into a new classLoader
        // 5) create a new instance of the D class and cast it to a One
        One o = (One) _new.of( _class.of("D", new One(){
            public String id() {
                return "ID";
            }
        }));
        //call the method through the ID interface
        assertEquals( "ID", o.id() );

        One t = (One)_new.of( _class.of("E", new One(){
            public String id(){
                return "DI";
            }
        }));

        //_new creates a new _classLoader each call
        assertNotSame( o.getClass().getClassLoader(), t.getClass().getClassLoader() );

        assertEquals( "DI", t.id());

        //ok, but how do I get the generated .java source back??
        _classLoader _cl = (_classLoader)o.getClass().getClassLoader(); //the _classLoader keeps the _class models

        _class _c = _cl.get_class( o.getClass() ); //
        assertEquals( 1, Walk.list(_c, Expr.STRING_LITERAL, sl-> sl.asString().equals("ID")).size() );

        _class _d = ((_classLoader)t.getClass().getClassLoader()).get_class(t.getClass());
        assertEquals( 1, Walk.list(_d, Expr.STRING_LITERAL, sl-> sl.asString().equals("DI")).size() );
    }

    /** Create (2) dynamic Implementations of an interface in the same _classLoader */
    public void testDynamicImplsSameClassLoader(){
        _project _p = _project.of(
                _class.of("D", new One(){
                    public String id() {
                        return "ID";
                    }
                }),
                _class.of("E", new One(){
                    public String id(){
                        return "DI";
                    }
                })
        );

        One o = (One)_p._new("D");
        One t = (One)_p._new("E");

        assertEquals( "ID", o.id());
        assertEquals( "DI", t.id());

        //not the same class
        assertNotSame(o.getClass(), t.getClass());

        //same classLoader
        assertEquals( o.getClass().getClassLoader(), o.getClass().getClassLoader());

    }

    public void testGet_typeFromClassLoader(){
        _class _c = _class.of("aaaa.bbbb.C")
                .nest(_class.of("N", new Object(){
                        int a;
                        public int getA(){
                            return a;
                        }
                }).setStatic()
                );
        _project _p = _project.of( _c );
        // make sure I can get the class from the classLoader
        Object c = _p._new("aaaa.bbbb.C" );
        Object n = _p._new("aaaa.bbbb.C$N" );
        _classLoader _cl = (_classLoader)c.getClass().getClassLoader();
        assertNotNull( _cl.get_type( c.getClass() ));
        _type _n = _cl.get_type( n.getClass() );
        //System.out.println( _n );
        assertNotNull( _n );
    }

    static class BaseClass{

    }

    public void testClassConstruct(){
        _class _c = _class.of("aaaa.B", new Object(){
           @_final int f = 100;
        });
        assertTrue( _c.getField("f").isFinal()  ); //this ensures that the

        //you can implement an interface
        _c = _class.of("aaaa.bbbb.C", new Serializable(){

        });
        assertTrue( _c.isImplementer(Serializable.class));
        assertTrue( _c.hasImport(Serializable.class));

        //you can set a base class
        _c = _class.of("D", new BaseClass(){

        });
        assertTrue( _c.isExtends(BaseClass.class));
        assertTrue( _c.hasImport(BaseClass.class));

        _c = _class.of("E", new Object(){
            @_static public int f = 100;
            public final int d = 2;

            @_static void m(){
                System.out.println(1);
            }
        }).staticBlock( (Integer f)->{f = 120;} );

        assertTrue( _c.getField("f").isStatic());
        assertTrue( _c.getMethod("m").isStatic());

        _autoDto.Macro.to(_c);
        //make sure it compiles
        _javac.of( _c );
    }

    /**
     * Here _1_build a static final variant of a _class (so we dont have to parse every time)
     *
     */
    public static final _class _c = //_autoDto.Macro.to(
            _class.of( "aaaa.bbbb.Local",
                    new Object(){
                        public int a,b,c;
                        @_final String name;
                        @_static public void main(String[] args){
                            System.out.println("Some Print Statement");
                        }
                }, _autoDto.$);

    @interface _annotat{

    }

    public void testMethodLambda(){
        _class _c = _class.of("aaaa.V").method("myMethod", ()-> System.out.println("Message"));
        _c = _class.of("aaaa.V").method("myMethod", (String a)-> System.out.println("a : "+a));
        _c = _class.of("aaaa.V").method("myMethod", (String a, Map<Integer,String>mi)-> System.out.println("a : "+a+" "+ mi));

    }
    public void testConstructorLambda(){
        _class _c = _class.of("aaaa.bbbb.C").constructor(()->System.out.println("in constructor"));
        System.out.println( _c );

        _c = _class.of("D").constructor((String s)->System.out.println("in constructor with "+s));
        System.out.println( _c );

        _c = _class.of("D").constructor((final @_annotat String s)->System.out.println("in constructor with "+s));
        System.out.println( _c );
    }

    public void testStaticClassLambdaAuto(){
        //when we create the model, we make it a top level model (not a Local Class)
        assertTrue( _c.isPublic() );
        assertTrue( _c.isTopClass() );
        assertTrue( _c.getPackage().equals("aaaa.bbbb") );
        assertTrue( _c.getField("a").isPublic() );
        assertTrue( _c.getField("a").isType(int.class) );
        assertTrue( _c.getField("b").isPublic() );
        assertTrue( _c.getField("b").isType(int.class) );

        //verify we have a static main() method
        assertTrue( _c.getMethod("main").isStatic() );
        assertTrue( _c.getMethod("main").isVoid() );
        assertTrue( _c.getMethod("main").getParameter(0).isType(String[].class) );
        //verify we created the getters
        assertTrue( _c.getMethod("getA" ).isType(int.class) );
        assertTrue( _c.getMethod("getB" ).isType(int.class) );
        assertTrue( _c.getMethod("getC" ).isType(int.class) );
        assertTrue( _c.getMethod("getName" ).isType(String.class) );

        //verify that we manufactured a constructor, and it accepts a String (the final field NAME) as the constructor
        assertTrue( _c.getConstructor(0).getParameter(0).isType(String.class) );
        assertTrue( _c.getConstructor(0).getBody().getStatement(0).equals( Stmt.of("this.name = name;") ) );
    }

    /**
     * Here we pass in a Lambda Expression that contains a Local Class Declaration
     * that is used as a _class
     *
     */
    public void testAnonymousClassWithMacroAnnotations(){
        //_class _c = _class.of( ()-> { class F{} });
        _class _c = _class.of( "F", new Object(){} );
        System.out.println( _c );
        assertEquals( "F", _c.getName());

        assertTrue( _c.isPublic() ); //we ensure the class is public
        assertTrue( _c.isTopClass() ); //we Promote it to a top level class

        assertTrue( _c.getPackage() == null );

        _c = _class.of( "aaaa.bbbb.F", new Object(){ @_static int a;} );
        assertTrue( _c.getField("a").isStatic() );
    }

    //TODO _class.of("pav.asda.CC");
    public void testShortcutClass(){
        _class _c = _class.of("C"); //public class C{}
        assertTrue( _c.isPublic());
        assertEquals("C", _c.getName());
        assertEquals(null, _c.getPackage());
        assertEquals("C", _c.getFullName());
        _c = _class.of("aaaa.bbbb.C");
        assertTrue( _c.isPublic());
        assertEquals("C", _c.getName());
        assertEquals( "aaaa.bbbb", _c.getPackage());
        assertEquals( "aaaa.bbbb.C", _c.getFullName());

        _c = _class.of("aaaa.bbbb.C<val>");
        assertTrue( _c.isPublic());
        assertEquals( _typeParameters.of("<val>"), _c.getTypeParameters());
        assertEquals("C", _c.getName());
        assertEquals( "aaaa.bbbb", _c.getPackage());
        assertEquals( "aaaa.bbbb.C", _c.getFullName());
    }

    public void testImportNative(){
        _class _c = _class.of( NativeMethod.class);
        _method _m1 = _method.of("native int getpid();");
        _method _m2 = _method.of("native int getpid();");
        _m1.equals(_m2);
        assertEquals( _m1, _m2);
        
        assertEquals( _c.getMethod( "getpid"), _method.of( "native int getpid();" ));
        assertTrue( _c.getMethod( "getpid").is( "native int getpid();" ));
        
        assertTrue( _c.hasMethods() );
        assertTrue( _c.hasStaticBlock() );
        assertTrue( _c.getStaticBlock(0).is( " System.loadLibrary(\"getpid\");"));
        _method _m = _c.getMethod(0);
        System.out.println( _m );
        
        assertTrue( _c.getMethod( "getpid").isNative());
        assertTrue( _c.getMethod( "getpid").isType( int.class));
        
    }
    
    
    public void testImportFromClass(){
        //read in the class from the Class file
        _class _c = _class.of( ComplexClass.class );
        System.out.println( _c ); 
    }
    
    public void testC(){
        _class _c = _class.of("class C{}");
        assertEquals("C", _c.getName());
        assertTrue( _c.getModifiers().is("") );
        assertFalse( _c.hasAnnos());
        assertFalse( _c.hasJavadoc());
        assertFalse( _c.hasExtends());
        assertFalse( _c.hasImplements());
        assertFalse( _c.hasFields());
        assertFalse( _c.hasMethods());
        assertFalse( _c.hasTypeParameters());
        assertFalse( _c.isPublic());
        assertFalse( _c.isPrivate());
        assertFalse( _c.isProtected());
        assertTrue( _c.isPackagePrivate());
        assertFalse( _c.isStatic());
        assertFalse( _c.isFinal());
        assertFalse( _c.isAbstract());
        assertFalse( _c.hasStaticBlock());
        assertFalse( _c.isExtends( Serializable.class ) );
        assertFalse( _c.isImplementer( Serializable.class ) );
        assertNull( _c.getStaticBlock(0) );
        assertTrue( _c.listMethods().isEmpty() );
        assertTrue( _c.listFields().isEmpty() );
        assertTrue( _c.listConstructors().isEmpty() );
        assertNull( _c.getExtends() );
        assertTrue( _c.listImplements().isEmpty() );
        assertTrue( _c.listAnnos( ).isEmpty());                
    }
    
    public void testMutate(){
        _class _c = _class.of("class C{}");
        _c.setPackage("blah.fromscratch");
        _c.imports(Map.class,HashMap.class);
        _c.imports( "aaaa.bbbb.C", "blah.dat.*");
        _c.javadoc("class JAVADOC");
        _c.annotate( "@ann", "@ann(k=1,v='y')");
        _c.setPublic();
        _c.name("Cgg");
        _c.typeParameters("<T extends Impl>");
        _c.extend( "Base");
        _c.implement( "A", "B");
        _c.staticBlock("System.out.println(34);");
        _c.field( "/** field JAVADOC */",
            "@ann2(k=2,v='g')",
            "public static final List<String> l = new ArrayList<>();");
        _c.constructor( "/** ctor JAVADOC */",
            "@ann",
            "@ann2(k=3,v='i')",
            "protected <e extends Element> Cgg( @ann @ann2(k=5)final String s, int...varArgs3 ) throws P, Q, D{",
            "     System.out.println(12);",
            "}");
        _c.method( "/** method JAVADOC */",
            "@ann",
            "@ann2(k=8,v='l')",
            "public static <e extends Fuzz> void doIt( @ann @ann2(k=7)final String xx, int...varArgs ) throws G, H, I{",
            "    System.out.println(15);",
            "}" );
        
        System.out.println( _c );
    }
    
    /**
     * Create a _class and then ask questions to verify the model
     */
    public void testFull(){
        _class _c = _class.of(
            "package blah.fromscratch;",
            "",
            "import blah.dat.*;",
            "import aaaa.bbbb.C;",
            "import java.util.Map;",
            "import java.util.HashMap;",
            "",
            "/** class JAVADOC */",
            "@ann",
            "@ann2(k=1,v='y')",
            "public class Cgg<T extends Impl> ",
            "    extends Base implements A, B {",
            "",
            "    static{",
            "         System.out.println(34);",
            "    }",
            "",
            "    /** field JAVADOC */",
            "    @ann",
            "    @ann2(k=2,v='g')",
            "    public static final List<String> l = new ArrayList<>();",
            "",
            "    /** ctor JAVADOC */",
            "    @ann",
            "    @ann2(k=3,v='i')",
            "    protected <e extends Element> Cgg( @ann @ann2(k=5)final String s, int...varArgs3 ) throws P, Q, D{",
            "         System.out.println(12);",
            "    }",
            "",
            "    /** method JAVADOC */",
            "    @ann",
            "    @ann2(k=3,v='i')",
            "    public static <e extends Fuzz> void doIt( @ann @ann2(k=5)final String xx, int...varArgs ) throws G, H, I{",
            "         System.out.println(15);",
            "    }",
            "}");
        
        assertTrue( _c.getPackage().equals( "blah.fromscratch"));
        
        assertTrue( _c.hasImport(Map.class) );
        assertTrue( _c.hasImport(HashMap.class) );
        assertTrue( _c.hasImport( "aaaa.bbbb.C"));
        assertTrue( _c.hasImport( "blah.dat.Blart"));
        assertTrue(_c.hasMethods());
        assertEquals(1, _c.listMethods().size());
        _method _m = _c.getMethod("doIt");
        assertTrue( _m.hasJavadoc());
        assertTrue( _m.getJavadoc().getContent().contains( "method JAVADOC" ));
        assertTrue( _m.hasAnnos());
        assertTrue( _m.getAnnos().is("@ann","@ann2(k=3,v='i')"));
        assertTrue( _m.getModifiers().is("public static"));
        assertTrue(_m.hasTypeParameters());
        assertTrue(_m.getTypeParameters().is("<e extends Fuzz>"));
        assertTrue( _m.isType( "void"));
        assertTrue( _m.isVoid());
        assertEquals("doIt", _m.getName());
        assertTrue( _m.hasParameters());
        assertTrue( _m.getParameters().is( "@ann @ann2(k=5)final String xx, int...varArgs"));
        assertTrue( _m.hasThrows());
        assertTrue( _m.hasThrow("I"));
        assertTrue( _m.hasThrow("H"));
        assertTrue( _m.hasThrow("G"));
        assertTrue( _m.getBody().is( "System.out.println(15);"));        
        assertEquals(1, _c.listConstructors().size());
        _constructor _ct = _c.getConstructor(0);
        assertTrue(_ct.hasJavadoc());
        assertTrue(_ct.getJavadoc().getContent().contains("ctor JAVADOC"));
        assertTrue(_ct.hasAnnos());
        assertTrue( _ct.getAnnos().is("@ann","@ann2(k=3,v='i')"));
        assertTrue( _ct.getModifiers().is("protected"));
        assertTrue( _ct.hasTypeParameters());
        assertTrue( _ct.getTypeParameters().is( "<e extends Element>"));
        assertEquals("Cgg", _ct.getName());
        assertTrue( _ct.hasThrows() );
        assertTrue( _ct.getThrows().is( "throws D, P, Q"));
        assertTrue( _ct.getBody().is( "System.out.println(12);"));
        assertTrue( _ct.hasThrow( "P") );
        assertTrue( _ct.hasThrow( "D") );
        assertTrue( _ct.hasThrow( "Q") );
        assertTrue( _ct.hasParameters() );
        assertEquals(2, _ct.listParameters().size() );
        _parameter _p = _ct.getParameter( 0 );
        assertTrue( _p.is( "@ann @ann2(k=5)final String s" ));
        assertTrue( _p.hasAnnos());
        assertEquals( 2, _p.listAnnos().size());
        assertTrue( _p.isFinal());
        assertTrue( _p.isType( String.class));
        assertEquals("s", _p.getName());
        assertFalse( _p.isVarArg() );
        _p = _ct.getParameter( 1 );
        assertTrue( _p.is( "int...varArgs3" ));
        assertFalse( _p.hasAnnos());
        assertEquals( 0, _p.listAnnos().size());
        assertFalse( _p.isFinal());
        assertTrue( _p.isType( int.class));
        assertEquals("varArgs3", _p.getName());
        assertTrue( _p.isVarArg() );
        assertTrue( _ct.isVarArg());
        
        _parameters _ps = _ct.getParameters();
        
        assertEquals(1, _c.listFields().size());
        _field _f = _c.getField("l");
        assertTrue( _f.hasJavadoc());
        assertTrue( _f.getJavadoc().getContent().contains("field JAVADOC"));
        assertTrue( _f.hasAnnos() );
        assertTrue( _f.getAnnos().is("@ann","@ann2(k=2,v='g')") );
        assertTrue( _f.getModifiers().is( "public static final"));
        assertTrue( _f.isType( "List<String>"));
        assertTrue( _f.getName().equals( "l"));
        assertTrue( _f.isInit(Expr.of("new ArrayList<>()")));
        System.out.println( _c.listFields());
        
        assertTrue(_c.hasJavadoc());
        assertTrue(_c.getJavadoc().getContent().contains( "class JAVADOC") );
        assertTrue(_c.hasAnnos());
        assertEquals(2, _c.listAnnos().size() );
        
        assertTrue( _c.getAnno(0).is( "@ann") );
        assertTrue( _c.getAnno(1).is( "@ann2(k=1,v='y')") );
        assertTrue( _c.getAnnos().is( "@ann","@ann2(k=1,v='y')"));
        assertTrue( _c.getModifiers().is("public"));
        assertEquals("Cgg", _c.getName());
        assertTrue( _c.hasTypeParameters() );
        assertTrue( _c.getTypeParameters().is( "<T extends Impl>"));
        assertTrue( _c.isExtends( "Base") );
        assertTrue( _c.isImplementer( "A"));
        assertTrue( _c.isImplementer( "B"));        
        assertTrue( _c.hasStaticBlock());
        assertNotNull( _c.getStaticBlock(0) ); //todo better static block
        assertTrue( _c.hasFields());        
    }
        
}
