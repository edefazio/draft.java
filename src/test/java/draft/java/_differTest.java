/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java;

import draft.java._differ._changeName;
import draft.java._differ._change_type;
import draft.java._inspect._path;
import draft.java._java.Component;
import draft.java._model._node;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class _differTest extends TestCase {
    
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
        _method.INSPECT_METHOD.diff(new _path(), dt, leftRoot, rightRoot, _m1, _m2);
        
        System.out.println( dt );
        
        assertFalse( _m1.equals(_m2));
        
        //ok manually do this
        _m2.setBody("return 1;");
        dt.forChanges(c-> c.keepLeft() );        
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
