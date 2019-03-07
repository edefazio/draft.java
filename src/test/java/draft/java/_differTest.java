package draft.java;

import draft.java._differ._changeName;
import draft.java._differ._change_type;
import draft.java._inspect._path;
import draft.java._java.Component;
import draft.java._model._node;
import java.io.IOException;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class _differTest extends TestCase {
    
    public void test_enumConstantListDiff(){
        _enum._constant _a1 = _enum._constant.of("A");
        _enum._constant _b1 = _enum._constant.of("B");
        
        _enum._constant _a2 = _enum._constant.of("A");
        _enum._constant _b2 = _enum._constant.of("B");
        
        _enum _e1 = _enum.of("E");
        _enum _e2 = _enum.of("E");
        
        _e1.constant(_a1).constant(_b1);
        _e2.constant(_b2).constant(_a2);
        _path path = new _path();
        _differ._mydiff dt = new _differ._mydiff();
        assertEquals( _e1, _e2);
        _enum.INSPECT_ENUM_CONSTANTS.diff(path, dt, _e1, _e2, _e1.listConstants(), _e2.listConstants());
        assertTrue( dt.isEmpty() );
        System.out.println( dt );
        //assertTrue(dt.isEmpty());
    }
    
    public void test_enumConstantDiff(){
        _enum._constant _a1 = _enum._constant.of("A");
        _enum._constant _a2 = _enum._constant.of("A");
        _differ._mydiff dt = new _differ._mydiff();
        _enum _e1 = _enum.of("E");
        _enum _e2 = _enum.of("E");
        _e1.constant(_a1);
        _e2.constant(_a2);
        
        _node leftRoot = _e1;
        _node rightRoot = _e2;
        _path path = new _path().in(Component.ENUM, "E");
        _enum.INSPECT_ENUM_CONSTANT.diff(path, dt, leftRoot, rightRoot, _a1, _a2);
        System.out.println( dt );
        
        _a1.method("int m(){ return 1; }");
        _a1.field("int i=100;");
        _a1.addArgument(0);
        _a1.annotate(Deprecated.class);
        _a1.javadoc("Javadoc ");
        
        _enum.INSPECT_ENUM_CONSTANT.diff(path, dt, leftRoot, rightRoot, _a1, _a2);
        System.out.println( dt );
        
        dt.forEach( d-> d.keepLeft() );
        
        dt = new _differ._mydiff();
        _enum.INSPECT_ENUM_CONSTANT.diff(path, dt, leftRoot, rightRoot, _a1, _a2);
        
        //System.out.println( dt );
        assertEquals( _a1, _a2);        
        //_method.INSPECT_METHOD.diff(new _path(), dt, leftRoot, rightRoot, _m1, _m2);
    }
    
    public void test_methodDiff(){
        _method _m1 = _method.of("int m(){ return 1; }");
        _method _m2 = _method.of("float n(){ return 1.0f; }");
        
        _differ._mydiff dt = new _differ._mydiff();
        
        _node leftRoot = null;
        _node rightRoot = null;
        _method.INSPECT_METHOD.diff(new _path(), dt, leftRoot, rightRoot, _m1, _m2);
        
        dt = new _differ._mydiff();
        _m1.javadoc("Hello");
        _method.INSPECT_METHOD.diff(new _path(), dt, leftRoot, rightRoot, _m1, _m2);
        
        dt = new _differ._mydiff();
        _m1.typeParameters("<T extends A>");
        _m1.receiverParameter("rp this");
        _m1.addParameter("int i");
        _m1.addThrows(IOException.class);
        _method.INSPECT_METHOD.diff(new _path(), dt, leftRoot, rightRoot, _m1, _m2);
        
        System.out.println( dt );
        
        assertFalse( _m1.equals(_m2));
        
        //ok manually do this
        //_m2.setBody("return 1;");
        dt.forEach(c-> c.keepLeft() );        
        assertEquals(_m1, _m2);
        
        
        /*_change_type _ct = new _change_type(_path.of(Component.CLASS, "C"), _m1, _m2 );
        
        _ct.keepRight();
        assertEquals( _typeRef.of(float.class), _m1.getType() );
        _ct.keepLeft();
        assertEquals( _typeRef.of(int.class), _m1.getType() );       
        
        _changeName _cn = new _changeName(_path.of(Component.CLASS, "C"), _m1, _m2 );
        
        _cn.keepRight();
        assertEquals( "n", _m1.getName() );
        _cn.keepLeft();
        assertEquals( "m", _m1.getName() );               
        */
    }
    
    public void testDirectChangeInstance(){
        _method _m1 = _method.of("int m(){ return 1; }");
        _method _m2 = _method.of("float n(){ return 1.0f; }");
        
        _change_type _ct = new _change_type(_path.of(Component.CLASS, "C"), _m1, _m2 );
        
        _ct.keepRight();
        assertEquals( _typeRef.of(float.class), _m1.getType() );
        _ct.keepLeft();
        assertEquals( _typeRef.of(int.class), _m1.getType() );       
        
        _changeName _cn = new _changeName(_path.of(Component.CLASS, "C"), _m1, _m2 );
        
        _cn.keepRight();
        assertEquals( "n", _m1.getName() );
        _cn.keepLeft();
        assertEquals( "m", _m1.getName() );               
    }
    
    
    
    
}
