package draft.java.file;

import draft.java._class;
import junit.framework.TestCase;

public class _javaFileTest extends TestCase {

    public void testURIName(){
        _javaFile jf = _javaFile.of( _class.of("B") );
        assertEquals( "/B.java", jf.getName());

        jf = _javaFile.of( _class.of("aaaa.B") );
        assertEquals( "/aaaa/B.java", jf.getName());
        System.out.println( jf.toUri() );
    }
}
