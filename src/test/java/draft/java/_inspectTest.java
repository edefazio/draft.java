package draft.java;

import draft.ObjectDiff.DiffList;
import draft.java._anno._annos;
import draft.java._inspect.StringInspect;
import draft.java._inspect._textDiff;
import draft.java._parameter._parameters;
import draft.java._typeParameter._typeParameters;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import junit.framework.TestCase;

/**
 * TODO, I COULD make it that I get a TextDiff for a change in javadoc
 * @author Eric
 */
public class _inspectTest extends TestCase {
    
    public void testInspectMethod(){
        _method _m1 = _method.of(new Object(){ 
            void a(){}
        });
        _method _m2 = _method.of(new Object(){ 
            void a(){}
        });
        
        assertTrue(_inspect.INSPECT_METHOD.diff(_m1, _m2).isEmpty());
        _m1.name("b");
        System.out.println( _inspect.INSPECT_METHOD.diff(_m1, _m2) );
        
        assertTrue(_inspect.INSPECT_METHOD.diff(_m1, _m2).hasDiff(_java.Component.NAME));
        _m1.setBody( ()-> System.out.println(1) );
        
        DiffList dl = _inspect.INSPECT_METHOD.diff(_m1, _m2);
        assertTrue(dl.hasDiff(_java.Component.NAME, _java.Component.BODY));
        
        _textDiff dmp = (_textDiff)dl.left(_java.Component.BODY);
        System.out.println( dmp );
        System.out.println( dmp.left() );
        System.out.println( dmp.right() );
        
        //_m1.setBody( (a,b)-> System.out.println(1) );
    }
    
    public void testInspectStringBase(){
        StringInspect si = new StringInspect("Name");
        assertTrue( si.equivalent("a", "a") );
        assertFalse( si.equivalent("A", "a") );
        
        assertTrue( si.diff("a", "a").isEmpty() );
        assertTrue( si.diff("a", "b").hasDiff("Name") );        
    }
    
    public void testInspectStringParamAtPath(){
        StringInspect si = new StringInspect("name");
        assertTrue( si.diff("parameter[0].", "a", "a").isEmpty() );
        assertTrue( si.diff("parameter[0].", "b", "a").hasDiff("parameter[0].name") );        
    }
    
    interface C{
        void t() throws IOException, FileNotFoundException;
        void t2() throws java.io.FileNotFoundException, java.io.IOException;
        
        void t3() throws IOException;        
        void t4() throws FileNotFoundException;
    }
    
    public void testTypeListInspect(){
        _interface _i = _interface.of( C.class );
        
        assertTrue( _inspect.INSPECT_THROWS.equivalent( 
            _i.getMethod("t").getThrows(),
            _i.getMethod("t2").getThrows() ));
        
        assertFalse( _inspect.INSPECT_THROWS.equivalent( 
            _i.getMethod("t").getThrows(),
            _i.getMethod("t3").getThrows()));
        
        assertFalse( _inspect.INSPECT_THROWS.equivalent( 
            _i.getMethod("t3").getThrows(),
            _i.getMethod("t4").getThrows()));
        
        
        assertTrue( _inspect.INSPECT_THROWS.diff( 
            _i.getMethod("t").getThrows(),
            _i.getMethod("t2").getThrows()).isEmpty() );
        
        assertTrue( _inspect.INSPECT_THROWS.diff( 
            _i.getMethod("t").getThrows(),
            _i.getMethod("t3").getThrows()).hasDiff("throws") );
        
        assertTrue( _inspect.INSPECT_THROWS.diff( 
            _i.getMethod("t3").getThrows(),
            _i.getMethod("t4").getThrows()).size() == 2 );        
    }
    
    class NTP{}
    class TP<T>{}
    class TPE<T extends Serializable>{}
    class TPE2<T extends java.io.Serializable>{}
    
    class TP21<T extends Serializable, R extends Runnable>{}
    class TP22<R extends java.lang.Runnable, T extends java.io.Serializable>{} //out of order & 
    
    public void testTypeParameters(){
        _typeParameters _ntp = _class.of(NTP.class).getTypeParameters();
        _typeParameters _tps = _class.of(TP.class).getTypeParameters();
        _typeParameters _tpe = _class.of(TPE.class).getTypeParameters();
        _typeParameters _tpe2 = _class.of(TPE2.class).getTypeParameters();
        
        _typeParameters _tp21 = _class.of(TP21.class).getTypeParameters();
        _typeParameters _tp22 = _class.of(TP22.class).getTypeParameters();
        
        assertTrue( _inspect.INSPECT_TYPE_PARAMETERS.equivalent(_ntp.ast(), _ntp.ast()) ); //none        
        assertTrue( _inspect.INSPECT_TYPE_PARAMETERS.equivalent(_tps.ast(), _tps.ast()) );//one simple        
        assertTrue( _inspect.INSPECT_TYPE_PARAMETERS.equivalent(_tpe.ast(), _tpe2.ast()) ); //one fully qual other not        
        assertTrue( _inspect.INSPECT_TYPE_PARAMETERS.equivalent(_tp21.ast(), _tp22.ast()) ); //out of order one fully qualified other not
        
        assertTrue( _inspect.INSPECT_TYPE_PARAMETERS.diff("type.", _ntp, _tp22 ).hasDiff("type.typeParameters") );
        assertTrue( _inspect.INSPECT_TYPE_PARAMETERS.diff("type.", _ntp, _tp22 ).size() == 2 );                
    }
    
    public @interface A{}    
    public @interface B{}
    
    public void testAnnos(){
        class F{
            @Deprecated int c;
            @java.lang.Deprecated int e;
            
            @A int a;
            @B int b;
            
            @A @B int ab;
            @draft.java._inspectTest.B @draft.java._inspectTest.A int ba;
        }
        _class _c = _class.of( F.class );
        _annos c = _c.getField("c").getAnnos();
        _annos e = _c.getField("e").getAnnos();
        
        _annos a = _c.getField("a").getAnnos();
        _annos b = _c.getField("b").getAnnos();
        
        _annos ab = _c.getField("ab").getAnnos();
        _annos ba = _c.getField("ba").getAnnos();
        
        //hashcode should be the same
        assertEquals( ab.hashCode(), ba.hashCode() );
        assertEquals( ab,ba );
        
        System.out.println( _inspect.INSPECT_ANNOS.diff(c, e) );
        
        assertTrue(_inspect.INSPECT_ANNOS.equivalent(c, e));
        assertTrue(_inspect.INSPECT_ANNOS.equivalent(ab, ba));        
    }
    
    public void testParameters(){
        class C{
            void no(){}
            void oneString(String one){}
            void twoStrings(String one, String two){}
            void oneInt(int one){}
            void twoInt(int one, int two){}
        }
        _class _c = _class.of(C.class);
        assertTrue( _inspect.INSPECT_PARAMETERS.equivalent(
                _parameters.of(""), _parameters.of("()") ) );
        
        assertTrue( _inspect.INSPECT_PARAMETERS.equivalent(
                _parameters.of("String one"), _parameters.of("(String one)") ) );
        
        assertTrue( _inspect.INSPECT_PARAMETERS.equivalent(
                _parameters.of("()"), _parameters.of("()") ) );
        
        assertTrue( _inspect.INSPECT_PARAMETERS.diff(
                _parameters.of("()"), _parameters.of("(int i)") ).hasDiff("parameter[0]") );
        
        assertTrue( _inspect.INSPECT_PARAMETERS.diff(
                _parameters.of("(int i)"), _parameters.of("()") ).hasDiff("parameter[0]") );
        
        assertTrue( _inspect.INSPECT_PARAMETERS.diff(
                _parameters.of("(int i)"), _parameters.of("(String i)") ).hasDiff("parameter[0]") );
        
        assertTrue( _inspect.INSPECT_PARAMETERS.diff(
                _parameters.of("(int i)"), _parameters.of("(int i, String s)") ).hasDiff("parameter[1]") );
        
        assertTrue( _inspect.INSPECT_PARAMETERS.diff(
                _parameters.of("(int i, String s)"), _parameters.of("(int i)") ).hasDiff("parameter[1]") );        
    }
    
    public void testBody(){
        class C{
            void noBody(){}
            void noBoddyComment() {/*comment*/}
            
            void oneStatement(){ System.out.println(1); }
            void oneStatementComment(){ /*comment*/ System.out.println(1); }
        }
        
    }
}
