package draft.java.file;

import draft.DraftException;
import draft.java._class;
import draft.java._method;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        
        assertNull( _jf.basePath );
    }
    
    /**
     * Here we are manually creating and reading a .java file 
     * from 
     */
    public void testReadJavaFileFromFileSystem(){
        String tmp = System.getProperty("java.io.tmpdir");
        
        Path basePath = Paths.get( tmp, "jft", "src", "main", "java"); 
        Path filePath = Paths.get( tmp, "jft", "src", "main", "java", "aaaa", "bbbb", "C.java");
        _class _c = _class.of("aaaa.bbbb.C")
                    .fields("int x = 100;", "int y = 100;");
        try{
           
            filePath.getParent().toFile().mkdirs();
            
            Files.write(
                filePath, 
                _c.toString().getBytes()
            );
        }
        catch(Exception e ){
            throw new DraftException ("unable to write file @"+filePath, e);
        }
        
        
        /** BUILD THE ACTUAL JAVAFILE */
        _javaFile _jf = _javaFile.of(basePath, _c);
        
        assertEquals( _jf.basePath, basePath);
        assertEquals( _jf.type, _c);
        assertEquals( _jf.toUri(), filePath.toUri() );
        assertEquals( "aaaa.bbbb.C", _jf.type.getFullName() );
        assertEquals( "/"+basePath.toString().replace("\\", "/")+ "/aaaa/bbbb/C.java", _jf.getName());        
        try{
            assertTrue( _jf.getCharContent(true).equals(_c.toString()));
        }catch(Exception e){
            fail("couldnt verify file contents");
        }
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
