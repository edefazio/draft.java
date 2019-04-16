/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import draft.java._parameter;
import draft.java._typeDecl;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class SparameterTest extends TestCase {
    
    public void testCompose(){
        $parameter $p = $parameter.of("int i");        
        _parameter _p = _parameter.of("int i");
        
        assertEquals( _p, $p.construct() );
        
        assertEquals( _p, $parameter.of("$type$ i").construct("type", int.class));        
        assertEquals( _p, $parameter.of("int $name$").construct("name", "i"));
        assertEquals( _p, $parameter.of("$type$ $name$").construct("name", "i", "type", int.class));   
        
        $p = $parameter.of("final String... nm");
        
        assertEquals( _parameter.of("final String... nm"), $p.construct());
        
        //verify they MUST be both vararg and final
        assertNotSame( _parameter.of("String... nm"), $p.construct());
        assertNotSame( _parameter.of("final String nm"), $p.construct());        
        
        $p = $parameter.of("$type$ name");
        
        assertEquals(_parameter.of(" String name"), $p.construct("type", String.class));
    }
    
    public void testMatches(){
        assertTrue( $parameter.of("int i").matches("int i") );
        assertTrue( $parameter.of("final int i").matches("final int i") );
        assertTrue( $parameter.of("final int... i").matches("final int... i") );
        
        assertTrue( $parameter.of("int... i").matches("final int... i") );
        assertTrue( $parameter.of("int... i").matches("int... i") );
        
        assertTrue( $parameter.of("final int i").matches("final int... i") );
        assertTrue( $parameter.of("final int i").matches("final int i") );
        
        assertFalse( $parameter.of("final int i").matches("int i") );
        
        assertTrue( $parameter.of("final $type$ i").matches("final int i") );
        assertTrue( $parameter.of("final $type$ i").matches("final int... i") );
        assertTrue( $parameter.of("final $type$ i").matches("final String... i") );
        
        assertFalse( $parameter.of("final $type$ i").matches("String... i") );
        
        
        assertTrue( $parameter.of("final $type$ $name$").matches("final int i") );
        assertTrue( $parameter.of("final $type$ $name$").matches("final int... i") );
        assertTrue( $parameter.of("final $type$ $name$").matches("final String... i") );        
    }
    
    public void testSelect(){
        assertNotNull( $parameter.of("int i").select("int i") );
        assertTrue( $parameter.of("$type$ i").select("int i").is("type", int.class));
        assertTrue( $parameter.of("$type$ i").select("int i").isType(int.class));
        
        assertTrue( $parameter.of("$type$ i").select("final int i").isType(int.class));        
        assertTrue( $parameter.of("$type$ i").select("final int... i").isType(int.class));
        
        assertTrue( $parameter.of("$type$ i").select("final int... i").isFinal());
        assertTrue( $parameter.of("$type$ i").select("final int... i").isVarArg());
        
        
        //assertEquals( _typeRef.of("String"), _typeRef.of(String.class));
        //isType
        //isParameter
        //isAnno
        //isExpr
        //isName
        //
        assertTrue( $parameter.of("$type$ i").select("String i").is("type", String.class));
        assertTrue( $parameter.of("$type$ i").select("String i").isType(String.class));
        
    }
    
}
