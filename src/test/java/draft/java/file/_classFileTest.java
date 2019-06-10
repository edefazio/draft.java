package draft.java.file;

import draft.DraftException;
import draft.java._class;
import draft.java.runtime._javac;
import java.io.ByteArrayInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

/**
 *
 * @author Eric
 */
public class _classFileTest extends TestCase {
    
    /**
     * 
     */
    public void testReadClassNameFromBytecode(){
        byte[] bs = _javac.of(_class.of("aaaa.bbbb.CCCC")).get("aaaa.bbbb.CCCC").getBytes();
        ByteArrayInputStream bais = new ByteArrayInputStream( bs);
        assertEquals( "aaaa.bbbb.CCCC", _classFile.resolveClassNameFromBytecode(bais));
    }
    
    public void testReadClassFileNoPackage(){
        String tmp = System.getProperty("java.io.tmpdir");        
        Path basePath = Paths.get( tmp, "cft", "target", "classes"); 
        Path filePath = Paths.get( tmp, "cft", "target", "classes", "A.class"); 
        byte[] classFile = _javac.of(_class.of("A")).get("A").getBytes();
        
        try{           
            filePath.getParent().toFile().mkdirs();            
            Files.write(Paths.get( tmp, "cft", "target", "classes", "A.class"), classFile);
            Files.write( filePath, classFile);            
        } catch(Exception e){
            throw new DraftException ("unable to write file @"+filePath, e);
        }
        /** BUILD THE ACTUAL JAVAFILE */
        _classFile _cs =  _classFile.of(basePath, "A", classFile );
        assertEquals( _cs.basePath, basePath);
        assertTrue( Arrays.equals( _cs.getBytes(), classFile) );
        assertEquals( _cs.toUri(), Paths.get( tmp, "cft", "target", "classes", "A.class").toUri() );
        assertEquals( "A", _cs.getFullyQualifiedTypeName() );
        assertEquals( "/"+basePath.toString().replace("\\", "/")+ "/A.class", _cs.getName());    
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
        
        _classFile _cf = _classFile.of(basePath, "aaaa.bbbb.C", classFile );
        
        assertEquals( _cf.basePath, basePath);
        assertTrue( Arrays.equals( _cf.getBytes(), classFile) );
        assertEquals( _cf.toUri(), filePath.toUri() );
        assertEquals( "aaaa.bbbb.C", _cf.getFullyQualifiedTypeName() );
        assertEquals( "/"+basePath.toString().replace("\\", "/")+ "/aaaa/bbbb/C.class", _cf.getName());                
    }
}
