/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java;

import java.io.Serializable;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertNotNull;
import static junit.framework.TestCase.assertTrue;
import test.ComplexEnum;

/**
 *
 * @author Eric
 */
public class _enumTest extends TestCase {
 
    interface MemberI{ }
    
    interface $Member{        
        interface MemberMember{}
    }
    
    public void testImplementMemberClass(){
        _enum _e = _enum.of("E").implement(MemberI.class).implement($Member.class).implement($Member.MemberMember.class);
        System.out.println( _e );
    }
    
    public void testHeader(){
        _enum _e = _enum.of("/* License */",
                "package aaaa.bbbb;",
                "/**",
                " * JavaDoc",
                " */",
                "public enum FFF{  ; }" );
        assertNotNull( _e.getHeaderComment() );
        assertNotNull( _e.getJavadoc() );
        assertEquals( _e.getJavadoc().toString().trim(), 
            Ast.javadocComment(
                "/**", 
                " * JavaDoc", 
                " */").toString().trim() );
        assertEquals( Ast.blockComment("/* License */").toString(), _e.getHeaderComment().toString() );
        System.out.println( _e.getHeaderComment() );
    }

    public void testForMembers(){
        _enum _e = _enum.of("E", new Object(){
            public static final int ID=102; 
            public static final String NAME = "Eric";
        }).constants("A", "B", "C", "D", "E");
           
        _e.forMembers(_field.class, f-> f.isStatic(), f->System.out.println(f));
        
        _e.forFields( f-> f.isStatic(), f-> System.out.println(f));
    }
    
    public void testConstants(){
        _enum _e = _enum.of("Suit").constants("Hearts","Clubs","Spades","Diamonds");
        assertEquals(4, _e.listConstants().size() );
    }

    
    public void testFullyQualified(){
        _enum _a = _enum.of("E").implement("aaaa.A", "bbbb.B");
        _enum _b = _enum.of("E").implement("B", "A");
        assertEquals( _a, _b);
        assertEquals( _a.hashCode(), _b.hashCode());
    }

    public void testEnum(){
        Ast.constant("A(1)");
        _enum _e = _enum.of("E")
                .constant("A(1)")
                .field("private int i;")
                .constructor("private E(int i){ this.i = i;}");
    }


    public void testImport(){
        _enum _e = _enum.of( ComplexEnum.class);
        System.out.println( _e );
        assertEquals("ComplexEnum", _e.getName());
        assertTrue(_e.getModifiers().is("public"));
        assertTrue(_e.isImplements( Serializable.class ) );
        assertTrue(_e.isImplements( "MarkerInterface<String>" ) );
        assertNotNull( _e.getConstant( "A"));
        assertNotNull( _e.getConstant( "A").getJavadoc());
        
        _enum._constant _c = _e.getConstant("B");
        
        assertTrue(_c.hasAnnos());
        assertTrue(_c.hasJavadoc());
        assertTrue(_c.hasFields());
        assertTrue(_c.hasArguments());
        assertEquals( 2, _c.listArguments().size());
        assertEquals( Expr.intLiteral( "1"), _c.getArgument(0));
        assertEquals( Expr.stringLiteral("String"), _c.getArgument(1));
        assertTrue( _c.getAnnos().is("@ann", "@ann2(k='o')"));
        _field _f = _c.getField( "num");
        assertNotNull( _f);
        assertTrue( _f.getModifiers().is( "public static final"));
        assertEquals( Expr.of(12233), _f.getInit() );
        
        _method _m = _c.getMethod("getNum");
        assertTrue( _m.hasJavadoc() );
        _m.getModifiers().is( "public final");
        assertTrue( _m.isType( int.class));
        assertTrue( _m.getBody().is("return 12345;") );
        
        _enum._constant _cc = _e.getConstant("C");
        assertEquals(2, _cc.listArguments().size() );
        assertEquals(Expr.of(2), _cc.getArgument(0));
        assertEquals(Expr.stringLiteral("Blah"), _cc.getArgument(1));
        
        assertTrue( _e.hasStaticBlock() );
        assertTrue( _e.getStaticBlock(0).is("System.out.println(12231);"));
        
        assertTrue( _e.hasConstructors());
        _constructor _ct = _e.getConstructor( 0 );
        
        assertFalse(_ct.hasParameters());
        System.out.println( "CONSTRUCTOR "+ _ct );
        assertTrue(_ct.isPrivate());
        
        assertTrue( _ct.getModifiers().is( "private"));
        assertTrue(_ct.getBody().isImplemented());
        assertTrue(_ct.getBody().isEmpty() );
        
        _ct = _e.getConstructor( 1 );
        assertTrue( _ct.hasAnnos() );
        System.out.println( _ct.getAnnos() );
        assertTrue( _ct.getAnnos().is("@ann","@ann2(k='y')"));
        assertTrue( _ct.isPrivate());
        assertTrue( _ct.getParameter( 0 ).is( "@ann @ann2(k='l',v=6) int i"));
        assertTrue( _ct.getParameter( 1 ).is( "String...s"));
        
        
        assertTrue( _e.hasMethods());
        _m = _e.getMethod( "AMethod");
        assertTrue( _m.getModifiers().is("public static final"));
        assertTrue( _m.isVoid() );
        assertTrue( _m.isVarArg() );
        assertTrue( _m.getParameter( 0 ).is("String...vals"));
        assertTrue( _m.getBody().is( "System.out.println(23123);"));
    }
    
     public void testConstructor(){
        _enum _e = _enum.of("E").field("int i;");

        _e.constructor(new Object(){
            int i;

            public void changeThisToEnumName(int i){
                this.i = i;
            }
        });
        //verify the enum
        //assertTrue(_e.getConstructor(0)  );
        assertEquals("E", _e.getConstructor(0).getName() );
        assertTrue(_e.getConstructor(0).is("E(int i){ this.i = i; }"));
        //_javac.of( _e);
    }
     
}
