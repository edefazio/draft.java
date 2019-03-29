package draft.java.proto;

import draft.java._class;
import java.net.URI;
import java.util.Map;
import junit.framework.TestCase;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
/**
 *
 * @author Eric
 */
public class $importTest extends TestCase {
    
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
