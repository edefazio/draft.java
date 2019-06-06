package draft.java.proto;

import draft.java._class;
import draft.java._import;
import java.net.URI;
import java.util.Map;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;

/**
 *
 * @author Eric
 */
public class SimportTest extends TestCase {
    
    public void testPostSet(){
        $import $i = $import.of("import a.MyClass");
        assertTrue( $i.matches("import a.MyClass"));
        
        $i.setStatic();
        assertFalse( $i.matches("import a.MyClass"));        
        assertTrue( $i.matches("import static a.MyClass"));
        
        $i.setWildcard();
        assertFalse( $i.matches("import static a.MyClass"));
        assertTrue( $i.matches("import static a.MyClass.*"));
    }
    
    public void testStaticImports(){
        $import $i = $import.of("import a.MyClass");
        assertTrue( $i.matches("import a.MyClass"));
        assertTrue( $i.matches("import a.MyClass.*"));
        assertTrue( $i.matches("import static a.MyClass"));

        //the proto is not static, the composed is not static
        assertEquals( _import.of("a.MyClass"), $i.construct() );
        
        //IF the prototype is marked static it WILL match
        $i = $import.of("import static a.MyClass");
        assertFalse( $i.matches("import a.MyClass"));
        assertTrue( $i.matches("import static a.MyClass"));
        assertTrue( $i.matches("import static a.MyClass.*"));
        
        //the proto is static, the composed is ALSO static
        assertEquals( _import.of("a.MyClass").setStatic(), $i.construct() );        
    }
    
    public void testWildcardImports(){
        $import $i = $import.of("import a.MyClass");        
        assertTrue( $i.matches("import a.MyClass"));
        
        //should match a wildcard import
        assertTrue( $i.matches("import a.MyClass.*"));

        //the proto is not static, the composed is not static
        assertEquals( _import.of("a.MyClass"), $i.construct() );
        
        //IF the prototype is marked static it WILL match
        $i = $import.of("import a.MyClass.*");
        assertFalse( $i.matches("import a.MyClass"));
        assertTrue( $i.matches("import a.MyClass.*"));
        
        //the proto is wildcard, the composed is ALSO a wildcard
        assertEquals( _import.of("a.MyClass.*"), $i.construct() );        
    }
    
    
    public void testImportWildcardStaticAssertions(){
        _class _c = _class.of("C").importStatic(Assert.class);
        $import.of(Assert.class).setStatic().setWildcard().replaceIn( _c, 
            _import.of(MatcherAssert.class).setStatic().setWildcard() );
        
        System.out.println( _c );
        
        assertFalse( _c.hasImport(Assert.class) );
        assertTrue( _c.hasImportStatic(MatcherAssert.class));
        assertTrue( _c.hasImport(MatcherAssert.class) );        
        
        _c = _class.of("C").imports(Assert.class);
        $import.of(Assert.class.getCanonicalName()).replaceIn( _c, 
            _import.of(MatcherAssert.class.getCanonicalName()) );
        
        assertFalse( _c.hasImport(Assert.class) );
        assertTrue( _c.hasImport(MatcherAssert.class));
    }
    
    public void testMatchRegularOrStatic(){
        _class _c = _class.of( "C").imports(Map.class);
        
        _class _cs = _class.of( "C").importStatic(Map.class);
        //System.out.println( _class.of("C").importStatic(Map.class));
        assertNotNull( $import.of(Map.class).firstIn(_c ) );
        assertNotNull( $import.of(Map.class).firstIn(_cs) );
        assertNotNull( $import.of(Map.class).selectFirstIn(_c ) );
        assertNotNull( $import.of(Map.class).selectFirstIn(_cs ) );
        
        assertNotNull( $import.of(Map.class).firstIn(_cs, i-> i.isStatic() && i.isWildcard()) );
        assertNotNull( $import.of(Map.class).firstIn(_c, i -> i.is(Map.class)) );
        assertNotNull( $import.of(Map.class).selectFirstIn(_c, i-> i.is(Map.class)) );
        assertNotNull( $import.of(Map.class).selectFirstIn(_cs, i-> i.isStatic()) );
        
        assertNotNull( $import.of(i-> i.isStatic()).firstIn(_cs) );
        
    }
   
    
    public void testConstantTemplate(){        
        _class _c = _class.of("C", new Object(){
            Map m = null;            
        });
        _c.imports(Assert.class);
        
        $import $i = $import.of(Assert.class);
        $i.replaceIn(_c, MatcherAssert.class );
        
        System.out.println(_c);
    }
    
    public void testDynamicTemplate(){
        //pImport $i = pImport.of("import static draft.java.Ast;").setWildcard();
        //assertTrue( $i.matches("import static draft.java.Ast.*;") );
        
        assertTrue( $import.of("import static draft.java.Ast.*;").matches("import static draft.java.Ast.*;"));                      
    }
}
