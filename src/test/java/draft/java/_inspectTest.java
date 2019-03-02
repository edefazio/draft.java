package draft.java;

import draft.java._anno._annos;
import draft.java._inspect._diffTree;
import draft.java._java.StringInspect;
import draft.java._inspect._path;
import static draft.java._java.Component.*;
import draft.java._parameter._parameters;
import draft.java._typeParameter._typeParameters;
import draft.java.macro._autoDto;
import draft.java.macro._name;
import draft.java.macro._static;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.List;
import junit.framework.TestCase;

/**
 * TODO, I COULD make it that I get a TextDiff for a change in javadoc
 * @author Eric
 */
public class _inspectTest extends TestCase {
    
    public void testField(){
        _field _f1 =_field.of("int a;");
        _field _f2 =_field.of("int a;");
        
        _class _c1 = _class.of("C");
        _class _c2 = _class.of("C").field(_f2);
               
        _diffTree dt = _class.diffTree(_c1, _c2);
        assertNotNull( dt.first(FIELD) );
        assertTrue( dt.first(FIELD).isAdd()); //its added between left and right
        
        _c1.field(_f1);
        dt = _class.diffTree(_c1, _c2);
        assertTrue( dt.isEmpty() );
        
        _f1.init(1);
        dt = _class.diffTree(_c1, _c2);
        //System.out.println( dt );
        assertTrue( dt.first(FIELD, INIT).isRemove()); //field init is removed from left to right 
        _f2.init(1);        
        _f2.annotate("@Ann");
        dt = _class.diffTree(_c1, _c2);
        assertTrue(dt.first(FIELD, ANNO).isAdd());
        
        _f1.annotate("@Ann");
        _f1.setPrivate();
        dt = _class.diffTree(_c1, _c2);
        assertTrue(dt.first(FIELD, MODIFIERS).isChange()); 
        
        _f2.setPrivate();        
        _f2.javadoc("A javadoc");
        dt = _class.diffTree(_c1, _c2);
        assertTrue(dt.first(FIELD, JAVADOC).isChange());
        
        _f1.javadoc("A javadoc");        
        _f1.type(float.class);
        _f1.init(1.0f);
        dt = _class.diffTree(_c1, _c2);
        assertTrue(dt.first(FIELD, TYPE).isChange());
        
        _f2.type(float.class);
        _f2.init(1.0f);
        _f2.name("b");        
        dt = _class.diffTree(_c1, _c2);
        //When we change the name of the field, we are affectively changing the API
        // so instead of it being considered a CHANGE it is rather a: 
        // REMOVE (of the old field "a")
        // ADD ( of the new field "b")
        // ...since all accesses to "a" are have to change to "b"
        assertTrue(dt.list(FIELD).size() == 2 );
        
        _f1.name("b");
        dt = _class.diffTree(_c1, _c2);
        assertTrue(dt.isEmpty());        
    }
    
    public void testBody(){
        _method _m1 = _method.of( new Object(){ void m(){} } );
        _method _m2 = _method.of( new Object(){ void m(){} } );
        _class _c1 = _class.of("C").method(_m1);
        _class _c2 = _class.of("C").method(_m2);
        assertTrue(_class.diffTree(_c1, _c2).isEmpty());
        
        _m1.setBody(()->{System.out.println(1);} );
        
        _diffTree _dt = _class.diffTree(_c1, _c2);
        assertTrue( _dt.first(BODY).isChange() );
        assertTrue( _dt.first(METHOD, BODY).isChange() );
        
        _dt.listEdits();
        
        _dt.first(d -> d.isTextDiff() );
                
        
        _dt.listEdits().get(0).forRemoves(d->System.out.println("REMOVED: " + d.text));
        
        //_dt.first(BODY).textDiff().forRemoves()...;
        //_dt.first(BODY).textDiff().for
        
        
        
        // changing any of these
        // name, parameter types
        // will diff as an ADD / REMOVE instead of a change        
    }    
    
    public void testMethodDiff(){
        _class _c1 = _class.of("C").method("void m(){}");
        _class _c2 = _class.of("C").method("void m(){}");
        _c2.forMethods(m-> m.annotate(Deprecated.class));
        
        //System.out.println( _c2 );
        _diffTree dt = _class.diffTree(_c1, _c2);
        
        //System.out.println( dt );
        
        assertEquals( 1, dt.list(METHOD,ANNO).size() );
        assertTrue(
            dt.first(METHOD,ANNO).isAdd()); //its Added from left -> right        
        assertNotNull(dt.first(METHOD));
        assertNotNull(dt.first(ANNO));
       
        _c1.forMethods(m -> m.setFinal());
        
        dt = _class.diffTree(_c1, _c2);
        //System.out.println( dt );
        assertNotNull(
            dt.first(METHOD, MODIFIERS) );     
        
        assertTrue(
            dt.first(METHOD, MODIFIERS).isChange() );            
        //System.out.println( dt );        
    }
    public void testClassDiff(){
        class C{
            int a;
            void m(){}
            C(){}
        }
        
        @_name("C")
        class D{
            int a;
            void m(){}
            D(){}
        }
        _class _c1 = _class.of(C.class);
        _class _c2 = _class.of(D.class);
        
        assertTrue(_class.diffTree(_c1, _c2).isEmpty());
        
        _diffTree dt = _class.diffTree(_c1.name("D"), _c2);
        
        //there is a name diff
        System.out.println( dt );
        
        //inserted is [+] null, right
        //deleted  is [-] left, null
        //similar is  [~] left, right
        
        //undercomponent 
        //onComponent    (NAME)
        
        assertTrue(dt.list(d -> d.has(NAME)).size() == 1);
        
        dt.forEach(d -> System.out.println( d.path.componentPath) );
        assertTrue(dt.list(CONSTRUCTOR).size() >= 1);
        
        _c1.getMethod("m").annotate(Deprecated.class);        
        dt = _class.diffTree(_c1, _c2);
        assertTrue(dt.first(ANNO).isRemove()); //its removed from left -> right
        
        
        
    }
    
    public void testInspectBodyDiffs(){
        class C{
            public void m(){
               System.out.println(1);
               System.out.println(2);
            } 
        } 
        @_name("C")
        class D{
            public void m(){
               System.out.println(2);
               System.out.println(1);
            }
        }
        _class _c = _class.of( C.class );
        _class _c2 = _class.of( D.class );
        _diffTree dt = _class.diffTree(_c, _c2);
        System.out.println( dt );
        List<_path> paths = dt.paths();
        System.out.println( dt.at( paths.get(0) ) );
        
        /*
        //transposed
        _method _c = _method.of(new Object(){
           public void m(){
               System.out.println(1);
               System.out.println(2);
           } 
        });
        _method _m2 = _method.of(new Object(){
           public void m(){
               System.out.println(2);
               System.out.println(1);
           } 
        });
        DiffTree dt = _inspect.INSPECT_METHOD.diffTree(_m, _m2);
        
        System.out.println( dt );
        */
        
    }
    
    public void testInspectMethods(){
        _class _c = _class.of("A", new Object(){
            final String name = "bozo";
            int x,y,z;                
            public @_static int calc(){
                return 1 * 2 * 3;
            }            
        }, _autoDto.$);
        
        _class _d = _class.of("A", new Object(){            
            final String name = "bozo";
            int x,y,z;                       
            public @_static int calc(){
                return 1 * 2 * 3;
            }            
        }, _autoDto.$);
        
        List<_method> ms = _c.listMethods();
        List<_method> ms2 = _d.listMethods();
        
        assertTrue(_field.INSPECT_FIELDS.diffTree(_c.listFields(), _d.listFields()).isEmpty());
        assertTrue(_method.INSPECT_METHODS.diffTree(ms, ms2).isEmpty());                   
        assertTrue(_class.INSPECT_CLASS.diffTree(_c, _d).isEmpty());        
        
        assertTrue(_field.INSPECT_FIELDS.diffTree(_c.listFields(), _d.listFields()).isEmpty());
        assertTrue(_method.INSPECT_METHODS.diffTree(ms, ms2).isEmpty());
        assertTrue(_class.INSPECT_CLASS.diffTree(_c, _d).isEmpty());                
    }
    
    /*
    public void testInspect_class(){
        _class _c = _class.of("A", new Object(){
            int x,y,z;
            
            
            public int b(){
                return 100;
            }
            
            String name = "Eric";
            
        }, _autoDto.$);
        
        _class _d = _class.of("A", new Object(){            
            String name = "Eric";
            
            public int b(){
                return 100;
            }
            int x,y,z;
        }, _autoDto.$);
        
        assertEquals( _c, _c);
        assertEquals( _c, _d);
        System.out.println( _c);
        System.out.println( _d);
        
        //System.out.println( _inspect.INSPECT_CLASS.diff(_c, _d). );
        
        assertTrue( _inspect.INSPECT_CLASS.diff(_c, _d).isEmpty());
    }
    */
    
    
    public void testInspectMethod(){
        _method _m1 = _method.of(new Object(){ 
            void a(){}
        });
        _method _m2 = _method.of(new Object(){ 
            void a(){}
        });
        
        assertTrue(_method.INSPECT_METHOD.diffTree(_m1, _m2).isEmpty());
        _m1.name("b");
        System.out.println( _method.INSPECT_METHOD.diffTree(_m1, _m2) );
        
        assertTrue(_method.INSPECT_METHOD.diffTree(_m1, _m2).first(_java.Component.NAME) != null);
        _m1.setBody( ()-> System.out.println(1) );
        
        _diffTree dl = _method.INSPECT_METHOD.diffTree(_m1, _m2);
        assertTrue(dl.first(BODY) != null);
        
        //_textDiff dmp = (_textDiff)dl.left(_java.Component.BODY);
        //System.out.println( dmp );
        //System.out.println( dmp.left() );
        //System.out.println( dmp.right() );
        
        //_m1.setBody( (a,b)-> System.out.println(1) );
    }
    
    public void testInspectStringBase(){
        StringInspect si = new StringInspect(NAME);
        assertTrue( si.equivalent("a", "a") );
        assertFalse( si.equivalent("A", "a") );
        
        assertTrue( si.diffTree("a", "a").isEmpty() );
        assertTrue( si.diffTree("a", "b").has(NAME) );        
    }
   
    
    interface C{
        void t() throws IOException, FileNotFoundException;
        void t2() throws java.io.FileNotFoundException, java.io.IOException;
        
        void t3() throws IOException;        
        void t4() throws FileNotFoundException;
    }
    
    public void testTypeListInspect(){
        _interface _i = _interface.of( C.class );
        
        assertTrue( _throws.INSPECT_THROWS.equivalent( 
            _i.getMethod("t").getThrows(),
            _i.getMethod("t2").getThrows() ));
        
        assertFalse( _throws.INSPECT_THROWS.equivalent( 
            _i.getMethod("t").getThrows(),
            _i.getMethod("t3").getThrows()));
        
        assertFalse( _throws.INSPECT_THROWS.equivalent( 
            _i.getMethod("t3").getThrows(),
            _i.getMethod("t4").getThrows()));
        
        
        assertTrue( _throws.INSPECT_THROWS.diffTree( 
            _i.getMethod("t").getThrows(),
            _i.getMethod("t2").getThrows()).isEmpty() );
        
        assertTrue( _throws.INSPECT_THROWS.diffTree( 
            _i.getMethod("t").getThrows(),
            _i.getMethod("t3").getThrows()).has( THROWS ) );
        
        assertTrue( _throws.INSPECT_THROWS.diffTree( 
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
        
        assertTrue( _typeParameter.INSPECT_TYPE_PARAMETERS.equivalent(_ntp.ast(), _ntp.ast()) ); //none        
        assertTrue( _typeParameter.INSPECT_TYPE_PARAMETERS.equivalent(_tps.ast(), _tps.ast()) );//one simple        
        assertTrue( _typeParameter.INSPECT_TYPE_PARAMETERS.equivalent(_tpe.ast(), _tpe2.ast()) ); //one fully qual other not        
        assertTrue( _typeParameter.INSPECT_TYPE_PARAMETERS.equivalent(_tp21.ast(), _tp22.ast()) ); //out of order one fully qualified other not
        
        assertTrue( _typeParameter.INSPECT_TYPE_PARAMETERS.diffTree(_ntp, _tp22 ).has(TYPE_PARAMETER) );
        assertTrue( _typeParameter.INSPECT_TYPE_PARAMETERS.diffTree(_ntp, _tp22 ).size() == 2 );                
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
        
        System.out.println( _anno.INSPECT_ANNOS.diffTree(c, e) );
        
        assertTrue(_anno.INSPECT_ANNOS.equivalent(c, e));
        assertTrue(_anno.INSPECT_ANNOS.equivalent(ab, ba));        
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
        assertTrue( _parameter.INSPECT_PARAMETERS.equivalent(
                _parameters.of(""), _parameters.of("()") ) );
        
        assertTrue( _parameter.INSPECT_PARAMETERS.equivalent(
                _parameters.of("String one"), _parameters.of("(String one)") ) );
        
        assertTrue( _parameter.INSPECT_PARAMETERS.equivalent(
                _parameters.of("()"), _parameters.of("()") ) );
        
        assertTrue( _parameter.INSPECT_PARAMETERS.diffTree(
                _parameters.of("()"), _parameters.of("(int i)") ).has(PARAMETER, "0") );
        
        assertTrue( _parameter.INSPECT_PARAMETERS.diffTree(
                _parameters.of("(int i)"), _parameters.of("()") ).has(PARAMETER, "0") );
        
        assertTrue( _parameter.INSPECT_PARAMETERS.diffTree(
                _parameters.of("(int i)"), _parameters.of("(String i)") ).has(PARAMETER, "0") );
        
        assertTrue( _parameter.INSPECT_PARAMETERS.diffTree(
                _parameters.of("(int i)"), _parameters.of("(int i, String s)") ).has(PARAMETER, "1") );
        
        assertTrue( _parameter.INSPECT_PARAMETERS.diffTree(
                _parameters.of("(int i, String s)"), _parameters.of("(int i)") ).has(PARAMETER, "1") );        
    }   
}
