package draft.java.file;

import draft.DraftException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import junit.framework.TestCase;
import draft.java._code;

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
     * Path simpleJavaFileInPath = 
            Paths.get(tmpDir, "draft_batchTest", "src", "main", "java", "com", "github", "javaparser", "ast", "A.java");       
     */
    public void testBatchLocal(){
        /** this is setup routines for creating sample files */
        String tmpDir = System.getProperty("java.io.tmpdir");
        Path root = Paths.get(tmpDir, "draft_batchTest");
        Path srcRoot = Paths.get(tmpDir, "draft_batchTest", "src", "main", "java");        
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
        
        _batch batch = _batch.of(srcRoot);
        
        //add an import to every codeunit
        List<_code> cus = new ArrayList<>();
        batch.forJavaCode( c -> {
            cus.add( c ); 
            c.imports(java.net.URI.class);} 
        );
        assertEquals(3, cus.size() );
        //verify that we modified 
        cus.forEach(c-> assertTrue(c.hasImport(URI.class)));
        
        //batch.forFiles( f -> {System.out.println("**** "+f.filePath+" *****"); System.out.println( new String(f.data) );} );
        //System.out.println( "NAMES "+ batch.files.listNames() );
    }
   
}
