package draft.java.proto;

import draft.java._class;
import java.util.Map;
import junit.framework.TestCase;
import org.hamcrest.MatcherAssert;
import org.junit.Assert;
/**
 *
 * @author Eric
 */
public class $importTest extends TestCase {
    
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
