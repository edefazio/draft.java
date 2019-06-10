package draft.java.file;

import draft.DraftException;
import draft.java._class;
import draft.java.runtime._javac;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static junit.framework.TestCase.fail;

/**
 *
 * @author Eric
 */
public class _classFileTest extends TestCase {
    
    public void testCF(){
    
        
    }
    
     /**
     * Here we are manually creating and reading a .java file 
     * from 
     */
    public void testReadJavaFileFromFileSystem(){
        String tmp = System.getProperty("java.io.tmpdir");
        
        Path basePath = Paths.get( tmp, "cft", "target", "classes"); 
        Path filePath = Paths.get( tmp, "cft", "target", "classes", "aaaa", "bbbb", "C.class");
        
        byte[] classFile = _javac.of(_class.of("aaaa.bbbb.C") )
            .get("aaaa.bbbb.C").getBytes();
        
        try{
           
            filePath.getParent().toFile().mkdirs();            
            Files.write( filePath, classFile);
        }
        catch(Exception e ){
            throw new DraftException ("unable to write file @"+filePath, e);
        }
        
        
        /** BUILD THE ACTUAL JAVAFILE */
        _classFile _cf = _classFile.of(basePath, "aaaa.bbbb.C", classFile );
        
        assertEquals( _cf.basePath, basePath);
        assertTrue( Arrays.equals( _cf.getBytes(), classFile) );
        assertEquals( _cf.toUri(), filePath.toUri() );
        assertEquals( "aaaa.bbbb.C", _cf.getFullyQualifiedTypeName() );
        assertEquals( "/"+basePath.toString().replace("\\", "/")+ "/aaaa/bbbb/C.class", _cf.getName());                
    }
}
