package draft.java;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import java.io.Serializable;

import com.github.javaparser.ast.type.Type;
import draft.java.macro._abstract;
import draft.java.macro._default;
import draft.java.macro._remove;
import draft.java.macro._static;
import java.util.Objects;
import junit.framework.TestCase;
import test.ComplexInterface;
import test.subpkg.MarkerInterface;
import test.subpkg.WithDefaultMethods;
import test.subpkg.ann2;

/**
 *
 * @author Eric
 */
public class _interfaceTest extends TestCase {

    interface MemberI{ }
    
    interface $Member{        
        interface MemberMember{}
    }
    
    public void testExtendsMemberWith$(){
        _interface _i = _interface.of("I")
                .extend(MemberI.class, $Member.class, $Member.MemberMember.class);
    }
    
    public void testHeader(){
        _interface _i = _interface.of("/* License */",
                "package aaaa.bbbb;",
                "/**",
                " * JavaDoc",
                " */",
                "public interface FFF{   }" );
        assertNotNull( _i.getHeaderComment() );
        assertNotNull( _i.getJavadoc() );
        assertEquals( _i.getJavadoc().toString().trim(), 
            Ast.javadocComment(
                "/**", 
                " * JavaDoc", 
                " */").toString().trim() );
        assertEquals( Ast.blockComment("/* License */").toString(), _i.getHeaderComment().toString() );
        System.out.println( _i.getHeaderComment() );
    }
        
    public void testInterfaceViaAnonymousObject(){
        _interface _i = _interface.of("I", new Object(){

            @_remove int a = 200;
            @_default void print(){
                System.out.println( "Hello");
            }

            @_abstract void getR(){
                System.out.println("the whole body will be removed");
            }

            @_static String doIt(int ii){
                return ""+ii + a;
            }
        });
        assertTrue( _i.getMethod("print").isDefault() );
        assertTrue( _i.getMethod("getR").isAbstract() );
        assertTrue( _i.getMethod("doIt").isStatic() );
        assertNull( _i.getField("a"));
    }

    public void testFullyQualified(){
        //the extensions are BOTH out of order, AND fully qualified vs simple
        _interface _a = _interface.of("I").extend("aaaa.A").extend("bbbb.B");
        _interface _b = _interface.of("I").extend("B").extend("A");

        //verify typesEqual and hashcode work
        assertEquals( _a, _b);
        assertEquals( _a.hashCode(), _b.hashCode());
    }

    /*
    interface I <T extends java.io.Serializable>{

    }
    interface R <U extends Comparable<? super java.io.Serializable>>{

    }
    */
    public void testFF(){
        //_interface _i1 = _interface.of(R.class).setPublic();
        _interface _i1 = _interface.of("interface R <U extends Comparable<? super java.io.Serializable>>{}");
        _interface _i2 = _interface.of("interface R <U extends Comparable<? super Serializable>>{}");

        _typeParameter._typeParameters _tps1 = _i1.getTypeParameters();
        _typeParameter._typeParameters _tps2 = _i2.getTypeParameters();
        assertEquals( _tps1, _tps2);

        assertEquals( _i1, _i2);

        _typeParameter tp = _i1.getTypeParameters().get(0);
        //System.out.println( tp.getTypeBound() );

        //Walk.directChildren( tp, Node.class, ni -> System.out.println( ni+" " +ni.getClass() ) );

        Walk.directChildren( tp, Node.class, ni -> {
            if( ni instanceof Type){
                System.out.println(ni + " " + Ast.tokenizeType( ni.toString() ) );
            } else {
                System.out.println(ni + " " + ni.getClass());
            }
        } );

        /*
        _i = _interface.of(I.class);
        tp = _i.getTypeParameters().get(0);
        System.out.println( tp.getTypeBound() );

        Walk.directChildren( tp, Node.class, ni -> System.out.println( ni+" " +ni.getClass() ) );

        assertTrue(_i.getTypeParameters().is("<T extends Serializable>"));
        */
    }

    public void testG(){
        FieldDeclaration fd = Ast.field( "int i;");        
    }
    
    public void testCompose(){
        //start with simple
        _interface _i = _interface.of( "interface ComplexInterface{}" );
        _i.setPackage("test");
        _i.imports( Serializable.class, MarkerInterface.class, WithDefaultMethods.class, ann2.class);
        _i.javadoc( "javadocs", "@author Eric", "@param <Y>", "@param <Z>");
        _i.annotate( "@ann", "@ann2(k='d')");
        _i.setPublic();
        _i.typeParameters( "<Y, Z extends Base>");
        _i.extend( "MarkerInterface<String>").extend( "WithDefaultMethods<Serializable>");
        _i.field( "/** field javadoc */", "@ann @ann2(k='2',v=3)", "static final int VALUE = 120;");
        
        _i.method( "@ann @ann2(k='F',v=12345)", "static int getValue(){","return 12345;", "}");
        _method _m = _method.of("<E extends Base> E genMethod(@ann @ann2(k='8',v=12) String s,final int...vals );");
        _m.javadoc( "the genMethod","@param <E>", "@param s", "@param vals", "@return");
        _i.method( _m );
        _i.method( "/**",
                   " * javadoc",
                   " * @return something",
                   " */",
                   " @ann",
                   " default Z doIt(){",
                   " System.out.println( 3 );",
                   " return null;",
                   "}");
        
        /*
        System.out.println( _i.getMethod( "doIt" ));
        _i.getMethod( "doIt" ).componentize().forEach((String a, Object b) -> {
            if( b != null) {
                System.out.println(a+" "+b.hashCode());
            }else{
                System.out.println(a+" null");
            }
        });
        */
        //System.out.println( _interface.of( ComplexInterface.class ).getMethod( "doIt" ));
        //this is how you can find out WHICH component is failing
        //_interface.of( ComplexInterface.class ).getMethod( "doIt" ).componentize().forEach((String a, Object b) -> {
        //    if( b != null) {
        //        System.out.println(a+" "+b.hashCode());
        //    }else{
        //        System.out.println(a+" null");
        //    }
        //});
        //System.out.println( );
        
        //System.out.println( _i.getMethod( "doIt" ).hashCode());
        //System.out.println( _interface.of( ComplexInterface.class ).getMethod( "doIt" ).hashCode());
        //System.out.println( _interface.of( ComplexInterface.class ).getMethod( "doIt" ).hashCode() );
        assertEquals(_i.getMethod("doIt"), _interface.of(ComplexInterface.class).getMethod("doIt"));
        
        //System.out.println( _i.getMethod( "getValue" ).hashCode());
        //System.out.println( _interface.of( ComplexInterface.class ).getMethod( "getValue" ).hashCode());
        assertEquals(_i.getMethod("getValue"),_interface.of(ComplexInterface.class).getMethod("getValue"));        
        assertEquals( _i, _interface.of( ComplexInterface.class ) );        
    }
    
    public void testImport(){
        _interface _i = _interface.of( ComplexInterface.class );
        assertTrue(_i.hasPackage() );
        assertEquals( "test", _i.getPackage() );    
        assertTrue( _i.hasImport( Serializable.class));
        assertTrue( _i.hasImport( MarkerInterface.class));
        assertTrue( _i.hasImport( WithDefaultMethods.class));
        assertTrue( _i.hasImport( ann2.class));
        
        assertTrue( _i.getJavadoc().getContent().contains("javadocs"));
        assertTrue( _i.getAnnos().is("@ann", "@ann2(k='d')"));
        assertTrue( _i.getModifiers().is( "public"));
        assertTrue( _i.getTypeParameters().is( "<Y, Z extends Base>"));
        assertTrue( _i.isExtends("MarkerInterface<String>"));
        assertTrue( _i.isExtends( "WithDefaultMethods<Serializable>"));
        
        assertTrue( _i.hasFields());
        _field _f = _i.getField("VALUE");
        assertTrue(_f.is("/** field javadoc */",
            "@ann @ann2(k='2',v=3)",
            "static final int VALUE = 120;"));
        
        _method _m = _i.getMethod( "getValue" );
        
        _m.deconstruct().forEach( (s,o)->{
            System.out.println( s+" "+o+" : "+ Objects.hashCode(o) );
        });
        
        _method _m2 = _method.of("@ann2(v=12345,k='F') @ann", //NOTE: i intentionally out of order
            "static int getValue(){",
            "return 12345;",
            "}");
        
        _m2.deconstruct().forEach( (s,o)->{
            System.out.println( s+" "+o+" : "+ Objects.hashCode(o) );
        });
        assertTrue( _m.is("@ann2(v=12345,k='F') @ann", //NOTE: i intentionally out of order
            "static int getValue(){",
            "return 12345;",
            "}") );        
    }    
    
    public interface I{
        static int a(){
            return 1;
        }
        public static int b(){
            return 2;
        }
    }
    
    public void testInterfaceStaticMethod() throws NoSuchMethodException {
        //the modifiers for a static method SHOUL be public
        assertEquals(I.class.getMethod("b", new Class[0]).getModifiers(), 
                I.class.getMethod("a", new Class[0]).getModifiers() );
    }
}
