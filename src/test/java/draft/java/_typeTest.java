package draft.java;

import draft.java.macro._static;
import draft.java.runtime._javac;
import junit.framework.TestCase;
//import static draft.java.Ast.PrintRawComments.*;

public class _typeTest extends TestCase {


    public void testHasImports(){
        _type _t = _type.of(_typeTest.class);
        assertTrue( _t.hasImport(_javac.class) );
        assertTrue( _t.hasImport(TestCase.class) );
        
        //This is an "impled" import... it is a top level class in the same package
        // so it returns true
        assertTrue( _t.hasImport(_type.class) );        
        
        assertTrue( _t.hasImport(_javac.class.getCanonicalName()) );
        assertTrue( _t.hasImport(TestCase.class.getCanonicalName()) );
        
        //This is an "impled" import... it is a top level class in the same package
        // so it returns true
        assertTrue( _t.hasImport(_type.class.getCanonicalName()) );        
        
        
        assertTrue( _t.getImports().hasImport(_javac.class) );
        assertTrue( _t.getImports().hasImport(TestCase.class) );
        
        //implied import
        assertTrue( _t.getImports().hasImport(_type.class) );  
        
        
        assertTrue( _t.getImports().hasImport(_javac.class.getCanonicalName()) );
        assertTrue( _t.getImports().hasImport(TestCase.class.getCanonicalName()) );
        
        //implied import
        assertTrue( _t.getImports().hasImport(_type.class.getCanonicalName()) );  

    }
    
    public void testTypeImport(){
        _class _c = _class.of("aaaa.vvvv.G");
        _class _c2 = _class.of("aaaa.xxxx.G")
            .method( new Object(){ public @_static final void m(){} } );
        
        assertFalse( _c.hasImport(_c2) );
        assertFalse( _c.hasImport(_c2.getFullName()) );
        
        _c.imports("aaaa.xxxx.G");
        
        assertTrue( _c.hasImport(_c2) );
        assertTrue( _c.hasImport(_c2.getFullName()) );
        
        _c.removeImports(_c2);
        
        assertFalse( _c.hasImport(_c2) );
        assertFalse( _c.hasImport(_c2.getFullName()) );
        
        //test import static on a single 
        _c.importStatic(_c2);
        assertTrue( _c.hasImport(_c2) );
        assertTrue( _c.hasImport(_c2.getFullName()) );
        assertTrue( _c.hasImport(_c2.getFullName()+".SomeNestedClass") );
        assertTrue( _c.hasImport(_c2.getFullName()+".someNestedMethod()") );
        //assertTrue( _c.hasImport(_c2.getFullName()+".SomeNestedClass") );
        
        //doesnt import a class in the same package... only the nested members
        assertFalse( _c.hasImport(_c2.getPackage()+"."+"SomeClass") );
        
        
    }
    
    
    
    public void testImportsFromOther_type(){
        _class _c = _class.of("aaaa.vvvv.C")
                .nest( _class.of("F", new Object(){
                    int x, y; }
                    )
                );

        _class _d = _class.of( "ffff.gggg.H")
                .imports(_c, _c.getNest("F") );

        //verify I can import another _type that hasnt been drafted yet
        assertTrue( _d.hasImport(_c) );

        //verify I can import a nested type that hasnt been drafted yet
        assertTrue( _d.hasImport(_c.getNest("F")) );
        _javac.of( _c, _d); //verify they both compile

        System.out.println(_d );
    }

    public void testS(){
        _type _t = _class.of("C").field("int x");

        //Not sure WHY I have to do this (_field)cast
        _t.forFields( f-> ((_field)f).isType(int.class) );
    }
}
