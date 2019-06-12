package draft.java.file;

import draft.DraftException;
import draft.java._class;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import draft.java._java._code;
import draft.java._java;
import draft.java.runtime._javac;

/**
 *
 * @author Eric
 */
public class _batchOldTest extends TestCase {

    
    /**
     * Verify that I can read a batch from a source directory, then modify the 
     * paths selectively and then write them out
     */
    public void testRepath(){
        
    }
    
    
    public static final String tmpDir = System.getProperty("java.io.tmpdir");
    public static final Path srcRoot = Paths.get(tmpDir, "draft_batchTest", "src", "main", "java");  
    
    public static void buildExampleFiles(){
         /** this is setup routines for creating sample files */
        
        Path root = Paths.get(tmpDir, "draft_batchTest");
        
        //Path classesRoot = Paths.get(tmpDir, "draft_batchTest", "src", "main", "java", "classes");
        Path simpleJavaFileInPath = 
            Paths.get(tmpDir, "draft_batchTest", "src", "main", "java", "com", "github", "javaparser", "ast", "A.java");       
        simpleJavaFileInPath.getParent().toFile().mkdirs();
        
        Path packageInfoFileInPath = 
            Paths.get(tmpDir, "draft_batchTest", "src", "main", "java", "com", "github", "javaparser", "ast", "package-info.java");       
        
        Path moduleInfoPath =
            Paths.get(tmpDir, "draft_batchTest", "src","main", "java", "aaaa", "classes", "module-info.java");     
 
        moduleInfoPath.getParent().toFile().mkdirs();
        
        try{            
            Files.writeString(simpleJavaFileInPath, 
                "package com.github.javaparser.ast;"+System.lineSeparator()+"public class A{}");
            
            Files.writeString(packageInfoFileInPath, 
                "/* License Erics 101 */"+ System.lineSeparator() +    
                "package com.github.javaparser.ast;"+System.lineSeparator()+ 
                "/** Javadoc Comment */");
            
            Files.writeString(moduleInfoPath, 
                "/* License 101 */"    + System.lineSeparator()+
                "import java.util.Map;" + System.lineSeparator()+
                "module aaaa {" + System.lineSeparator()+
                "    requires bbbb;" + System.lineSeparator()+
                "    requires static cccc;" + System.lineSeparator()+
                "}");        
        }catch(Exception e){
            throw new DraftException("unable to write", e);
        }
    }
    
    
    /**
     * Path simpleJavaFileInPath = 
            Paths.get(tmpDir, "draft_batchTest", "src", "main", "java", "com", "github", "javaparser", "ast", "A.java");       
     */
    public void testBatchLocal(){
        buildExampleFiles();
        
        _batchOld batch = _batchOld.of(srcRoot);        
        assertEquals( srcRoot, batch.rootPath);
        
        assertEquals(3, batch.files.size());
        batch.files.forEach(f -> System.out.println( f.toUri() ));
        
        
        //add an import to every codeunit
        List<_java._code> cus = new ArrayList<>();
        batch.forJavaCode( c -> {
            cus.add( c ); 
            c.imports(java.net.URI.class);} 
        );
        System.out.println( cus );
        assertEquals(3, cus.size() );
        //verify that we modified 
        cus.forEach(c-> assertTrue(c.hasImport(URI.class)));        
    }
    
    //verify that the batch can read in _classFiles
    public void testBatchClassFiles(){
        
        String tmp = System.getProperty("java.io.tmpdir");
        
        Path basePath = Paths.get( tmp, "batchtest", "target", "classes"); 
        Path filePath = Paths.get( tmp, "batchtest", "target", "classes", "aaaa", "bbbb", "C.class");
        
        byte[] classFile = _javac.of(_class.of("aaaa.bbbb.C") )
            .get("aaaa.bbbb.C").getBytes();
        
        try{           
            filePath.getParent().toFile().mkdirs();                        
            Files.write( filePath, classFile);            
            Files.write(Paths.get( tmp, "batchtest", "target", "classes", "A.class"), 
                _javac.of(_class.of("A") ).get("A").getBytes() );
        }
        catch(Exception e ){
            throw new DraftException ("unable to write file @"+filePath, e);
        }
        
        _batchOld batch = _batchOld.of(basePath);
        assertEquals(2, batch.files.size());
        
        List<_classFile> cfs = new ArrayList<>();
        batch.forClassFiles(c-> cfs.add(c));
        assertEquals( 2, cfs.size());
        
        batch.forClassFiles(c-> System.out.println(c.toUri()));
        batch.forClassFiles(c-> System.out.println(c.getSimpleName()));
        
        cfs.clear();
        batch.forClassFiles(c-> c.getSimpleName().equals("C"), c-> cfs.add(c));
        assertEquals( 1, cfs.size());
        cfs.clear();
        
        batch.forClassFiles(c-> c.getSimpleName().equals("A"), c-> cfs.add(c));
        assertEquals( 1, cfs.size());        
    }
   
}
