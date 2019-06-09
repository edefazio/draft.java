package draft.java.file;

import draft.java._class;
import draft.java._method;
import java.io.IOException;
import java.lang.reflect.Modifier;
import java.net.URI;
import javax.lang.model.element.NestingKind;
import javax.tools.JavaFileObject;
import junit.framework.TestCase;

public class _javaFileTest extends TestCase {

    /**
     * Here, I tired using the lexical preserving printer and it caused more 
     * problems (and only does code formatting)
     * 
     * @throws IOException 
     */
    public void testCreateAndQuery() throws IOException{
        _javaFile _jf = _javaFile.of(
                _class.of("aaaa.bbbb.PP")
                    .field("int i=0;") );
        
        _jf.type.field("int jj = 0;");
        _jf.type.field("int kk = 0;");
        _jf.type.add(_method.of("    public void l(){"+ System.lineSeparator()+
            "        System.out.println(101010);"+ System.lineSeparator()+
            "    }"));
        String s = _jf.getCharContent(true).toString();
        
        assertEquals( _jf.type, _class.of("aaaa.bbbb.PP")
            .fields("int i=0;", "int jj=0;", "int kk= 0;")
            .method(new Object(){
                public void l(){
                    System.out.println(101010);
                }
            }));
        
        assertEquals( null, _jf.getAccessLevel());
        assertTrue( _jf.isNameCompatible("PP", JavaFileObject.Kind.SOURCE));
        assertEquals( "aaaa.bbbb.PP", _jf.type.getFullName() );
        assertEquals( JavaFileObject.Kind.SOURCE, _jf.getKind() );
        assertEquals( "/aaaa/bbbb/PP.java", _jf.getName() );
        assertEquals( NestingKind.TOP_LEVEL, _jf.getNestingKind() );
        assertEquals( URI.create("file:///aaaa/bbbb/PP.java"), _jf.toUri() );
    }
    
    public void testURIName(){
        //test no package
        _javaFile jf = _javaFile.of( _class.of("B") );
        assertEquals( "/B.java", jf.getName());
        assertEquals( URI.create("file:///B.java"), jf.toUri() );
        

        //test sub package
        jf = _javaFile.of( _class.of("aaaa.B") );
        assertEquals( "/aaaa/B.java", jf.getName());
        assertEquals( URI.create("file:///aaaa/B.java"), jf.toUri() );
        System.out.println( jf.toUri() );
    }
}
