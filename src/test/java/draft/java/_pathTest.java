package draft.java;

import draft.java._java.Component;
import draft.java._java._path;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class _pathTest extends TestCase {
    
    public void testP(){
        _path p = _path.of(Component.ANNO, "N");
        assertTrue( p.isLeaf(_anno.class) );
    }
}
