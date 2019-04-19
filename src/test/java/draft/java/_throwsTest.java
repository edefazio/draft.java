package draft.java;

import com.github.javaparser.ast.type.ReferenceType;
import junit.framework.TestCase;

import java.io.IOException;

/**
 *
 * @author Eric
 */
public class _throwsTest
        extends TestCase {


    /** Verify that fully qualified throws == unqualified throws */
    public void testThrowsEquality(){
        _throws _t1 = _throws.of( RuntimeException.class );
        _throws _t2 = _throws.of( "java.lang.RuntimeException" );
        assertTrue( Ast.typesEqual( (ReferenceType)Ast.typeDecl(RuntimeException.class),
                (ReferenceType)Ast.typeDecl("java.lang.RuntimeException" ) ));

        assertTrue( Ast.typesEqual( (ReferenceType)Ast.typeDecl("RuntimeException"),
                (ReferenceType)Ast.typeDecl("java.lang.RuntimeException" ) ));

        assertTrue( _t1.contains((ReferenceType)Ast.typeDecl("java.lang.RuntimeException" )));
        assertTrue( _t1.contains((ReferenceType)Ast.typeDecl("RuntimeException" )));
        assertEquals( _t1, _t2 );

        assertEquals( _t1.hashCode(), _t2.hashCode());
    }

    public void testThrowsGeneric(){

    }
    public void testThrowsIs(){
        //_throws _tt = _throws.of( );
        //assertTrue( _tt.is( new AnnotatedType[0] ) );

        _throws _tt = _throws.of( RuntimeException.class );
        assertTrue( _tt.is( new Class[]{RuntimeException.class }) );

    }

    public void testT(){
        _throws _tt = _throws.of( RuntimeException.class );

        assertEquals( "throws RuntimeException", _tt.toString() );

        _tt = _throws.of( RuntimeException.class).add( "MyException" );

        assertEquals("throws RuntimeException, MyException", _tt.toString() );

    }

    //throws should be the same no matter the order
    public void testVerifyOrderDoesntMatter(){
        _throws _tt = new _throws( );
        _tt.add( RuntimeException.class );
        _tt.add( IOException.class );

        _throws _ot = _throws.of( IOException.class, RuntimeException.class );
        assertEquals( _tt, _ot );
    }
}
