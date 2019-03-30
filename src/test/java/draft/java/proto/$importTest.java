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
public class $importTest extends TestCase {
    
    public void testImportWildcardStaticAssertions(){
        _class _c = _class.of("C").importStatic(Assert.class);
        $import.replace( _c, _import.of(Assert.class).setStatic().setWildcard(), _import.of(MatcherAssert.class).setStatic().setWildcard() );
        
        assertFalse( _c.hasImport(Assert.class) );
        assertTrue( _c.hasImportStatic(MatcherAssert.class));
        assertTrue( _c.hasImport(MatcherAssert.class) );        
        
        _c = _class.of("C").importStatic(Assert.class);
        $import.replace( _c, _import.of(Assert.class.getCanonicalName()).setStatic().setWildcard(), 
                _import.of(MatcherAssert.class.getCanonicalName()).setStatic().setWildcard() );
        
        assertFalse( _c.hasImport(Assert.class) );
        assertTrue( _c.hasImportStatic(MatcherAssert.class));
        assertTrue( _c.hasImport(MatcherAssert.class) );        
    }
    
    public void testMatchRegularOrStatic(){
        _class _c = _class.of( "C").imports(Map.class);
        
        _class _cs = _class.of( "C").importStatic(Map.class);
        //System.out.println( _class.of("C").importStatic(Map.class));
        assertNotNull( $import.first(_cs, Map.class) );
        assertNotNull( $import.first(_c, Map.class) );
        assertNotNull( $import.selectFirst(_c, Map.class) );
        assertNotNull( $import.selectFirst(_cs, Map.class) );
        
        assertNotNull( $import.first(_cs, Map.class, i-> i.isStatic() && i.isWildcard()) );
        assertNotNull( $import.first(_c, Map.class, i -> i.is(Map.class)) );
        assertNotNull( $import.selectFirst(_c, Map.class, i-> i.is(Map.class)) );
        assertNotNull( $import.selectFirst(_cs, Map.class, i-> i.isStatic()) );
        
        assertNotNull( $import.of(i-> i.isStatic()).firstIn(_cs) );
        
       
    }
    
    public void testStaticAPI(){
        _class _c = _class.of("C").imports(Map.class);
        
        assertNotNull( $import.first(_c, Map.class) );
        assertNull( $import.first(_c, URI.class) );
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
        $import $i = $import.of("import static draft.java.Ast$any$;");
        assertTrue( $i.matches("import static draft.java.Ast.*;") );
        
        $i.$("static ", "isStatic");
        
        assertTrue( $i.matches("import static draft.java.Ast;") );
        assertTrue( $i.matches("import draft.java.Ast;") );                
    }
}
