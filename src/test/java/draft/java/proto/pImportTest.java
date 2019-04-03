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
public class pImportTest extends TestCase {
    
    public void testImportWildcardStaticAssertions(){
        _class _c = _class.of("C").importStatic(Assert.class);
        pImport.replace( _c, _import.of(Assert.class).setStatic().setWildcard(), 
            _import.of(MatcherAssert.class).setStatic().setWildcard() );
        
        System.out.println( _c );
        
        assertFalse( _c.hasImport(Assert.class) );
        assertTrue( _c.hasImportStatic(MatcherAssert.class));
        assertTrue( _c.hasImport(MatcherAssert.class) );        
        
        _c = _class.of("C").imports(Assert.class);
        pImport.replace( _c, _import.of(Assert.class.getCanonicalName()), 
            _import.of(MatcherAssert.class.getCanonicalName()) );
        
        assertFalse( _c.hasImport(Assert.class) );
        assertTrue( _c.hasImport(MatcherAssert.class));
    }
    
    public void testMatchRegularOrStatic(){
        _class _c = _class.of( "C").imports(Map.class);
        
        _class _cs = _class.of( "C").importStatic(Map.class);
        //System.out.println( _class.of("C").importStatic(Map.class));
        assertNotNull( pImport.first(_c, Map.class) );
        assertNotNull( pImport.first(_cs, Map.class) );
        assertNotNull( pImport.selectFirst(_c, Map.class) );
        assertNotNull( pImport.selectFirst(_cs, Map.class) );
        
        assertNotNull( pImport.first(_cs, Map.class, i-> i.isStatic() && i.isWildcard()) );
        assertNotNull( pImport.first(_c, Map.class, i -> i.is(Map.class)) );
        assertNotNull( pImport.selectFirst(_c, Map.class, i-> i.is(Map.class)) );
        assertNotNull( pImport.selectFirst(_cs, Map.class, i-> i.isStatic()) );
        
        assertNotNull( pImport.of(i-> i.isStatic()).firstIn(_cs) );
        
       
    }
    
    public void testStaticAPI(){
        _class _c = _class.of("C").imports(Map.class);
        
        assertNotNull( pImport.first(_c, Map.class) );
        assertNull( pImport.first(_c, URI.class) );
    }
    
    public void testConstantTemplate(){        
        _class _c = _class.of("C", new Object(){
            Map m = null;            
        });
        _c.imports(Assert.class);
        
        pImport $i = pImport.of(Assert.class);
        $i.replaceIn(_c, MatcherAssert.class );
        
        System.out.println(_c);
    }
    
    public void testDynamicTemplate(){
        //pImport $i = pImport.of("import static draft.java.Ast;").setWildcard();
        //assertTrue( $i.matches("import static draft.java.Ast.*;") );
        
        assertTrue( pImport.of("import static draft.java.Ast.*;").matches("import static draft.java.Ast.*;"));                      
    }
}
