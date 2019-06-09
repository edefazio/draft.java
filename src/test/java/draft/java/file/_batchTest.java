package draft.java.file;

import draft.DraftException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import draft.java._java._code;
import draft.java._java;

/**
 *
 * @author Eric
 */
public class _batchTest extends TestCase {

    public void testRelativize(){
        Path basePath = Paths.get("C:\\ren");
        Path path = Paths.get("C:\\ren\\javaparser\\javaparser-core");
        Path rel = path.relativize(basePath);
        assertEquals( "javaparser\\javaparser-core", basePath.relativize(path).toString());
    }
    
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
        
        _batch batch = _batch.of(srcRoot);        
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
   
}
