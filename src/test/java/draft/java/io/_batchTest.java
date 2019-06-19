/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.io;

import draft.java.io._batch;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;
import draft.DraftException;
import draft.java.Ast;
import draft.java._class;
import draft.java._java;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import javax.tools.FileObject;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;

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
    
     public void testBP(){
        Path base = Paths.get("C:/temp/MyProject/src/test/java");
        Path sub = Paths.get("C:/temp/MyProject/src/test/java", "aaaa", "bbbb", "C.java");
        Path other = Paths.get("blah/blorg");
        Path rel1 = sub.relativize(base);
        
        Path rel2 = base.relativize(sub);
        
        System.out.println( rel1);
        System.out.println( rel2);
        
        //System.out.println( base.relativize(other));
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
            
            Files.write(
                simpleJavaFileInPath, 
                ("package com.github.javaparser.ast;"+System.lineSeparator()+"public class A{}").getBytes());
            
            Files.write(packageInfoFileInPath, 
                ("/* License Erics 101 */"+ System.lineSeparator() +    
                "package com.github.javaparser.ast;"+System.lineSeparator()+ 
                "/** Javadoc Comment */").getBytes());
            
            Files.write(moduleInfoPath, 
                ("/* License 101 */"    + System.lineSeparator()+
                "import java.util.Map;" + System.lineSeparator()+
                "module aaaa {" + System.lineSeparator()+
                "    requires bbbb;" + System.lineSeparator()+
                "    requires static cccc;" + System.lineSeparator()+
                "}").getBytes());        
        }catch(Exception e){
            throw new DraftException("unable to write", e);
        }
    }
    
    
    /**
     * Path simpleJavaFileInPath = 
            Paths.get(tmpDir, "draft_batchTest", "src", "main", "java", "com", "github", "javaparser", "ast", "A.java");       
     */
    public void testBatchLocal() throws IOException {
        buildExampleFiles();
        
        _batch batch = _batch.of(srcRoot);        
        assertEquals( srcRoot, batch.rootPath);
        
        assertEquals(3, batch.files.size());
        batch.files.forEach(f -> System.out.println( f.toUri() ));
        
        //add an import to every codeunit
        
        //TODO forJavaASTFiles
        List<CompilationUnit> asts = new ArrayList<>();
        batch.forFiles(f-> f.getName().endsWith(".java"), f -> {
            try{
                CompilationUnit ast = Ast.compilationUnit(f.getCharContent(true).toString());
                ast.setStorage( Paths.get( f.toUri() ) );
                ast.addImport(java.net.URI.class);
                asts.add( ast );             
            }catch(IOException ioe){
                throw new DraftException("Could not read from "+f);
            }            
        });
        
        System.out.println(asts);
        assertEquals(3, asts.size() );
        
        //verify that we modified all of the asts        
        asts.forEach(a-> assertTrue( a.getImports().contains(
            new ImportDeclaration(URI.class.getCanonicalName(), false, false)) )
        );        
    }
    
    /*
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
        
        _batch batch = _batch.of(basePath);
        assertEquals(2, batch.files.size());
        
        
        List<FileObject> cfs = new ArrayList<>();
        batch.forFiles(f-> f.getName().endsWith(".class"), f-> cfs.add(f));
        assertEquals( 2, cfs.size());
        
        batch.forFiles(c-> System.out.println(c.toUri()));
        /*
        batch.forFiles(c-> System.out.println(c.getSimpleName()));
        
        cfs.clear();
        batch.forClassFiles(c-> c.getSimpleName().equals("C"), c-> cfs.add(c));
        assertEquals( 1, cfs.size());
        cfs.clear();
        
        batch.forClassFiles(c-> c.getSimpleName().equals("A"), c-> cfs.add(c));
        assertEquals( 1, cfs.size());        
    
    }
    */
    
}
