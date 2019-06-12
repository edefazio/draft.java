package draft.java.file;

import draft.java.io._ioException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class _batchTestMove extends TestCase {
    
    public void testZipFiles(){
        Predicate<String> skipFiles = p-> 
            p.contains("\\.git\\") 
            || p.contains("\\surefire-reports\\") 
            || p.contains(".gitignore")
            || p.contains("SNAPSHOT-sources.jar")
            || p.contains("SNAPSHOT.jar"); 
        
        _batch batch = _batch.of("C:\\ren", skipFiles);
        
        batch.forFiles(f-> f.getName().endsWith(".zip"), 
             f->System.out.println("Found Zip file "+f.getName()));
        
        batch.forFiles(f-> f.getName().endsWith(".jar"), 
             f->System.out.println("Found Jar file "+f.getName()));
    }
    
    public void testSimple(){
        AtomicInteger ai = new AtomicInteger();
        
        Predicate<String> skipFiles = p-> 
            p.contains("\\.git\\") 
            || p.contains("\\surefire-reports\\") 
            || p.contains(".gitignore")
            || p.contains("SNAPSHOT-sources.jar")
            || p.contains("SNAPSHOT.jar"); 
        
        _batch batch = _batch.of("C:\\ren", skipFiles);
        
        //batch.forFiles(f-> f.getName().endsWith(".zip"), f->System.out.println("Found Zip file "+f.getName()));
        
        //this will change the content
        batch.forFiles(f-> f.isFileExtension(".java",".html",".htm", ".txt",
            ".md",".js",".json",".pom",".yml",".yaml", ".jj", ".lst", 
            ".sh", ".xml", ".bnd", ".props", ".properties", ".story", ".MF", ""), 
            f-> {
                boolean replaced = f.replaceIn(
                    "com.github.javaparser", "org.javaparser",
                    "com_github_javaparser", "org_javaparser",
                    "com\\github\\javaparser","org\\javaparser",
                    "com/github/javaparser/","org/javaparser/",
                    "com/github/javaparser","org/javaparser",
                    "\"com\", \"github\", \"javaparser\"", "\"org\", \"javaparser\""); //SourceZipTest
                if( replaced ){
                    System.out.println("replaced in "+f.getName());
                    ai.incrementAndGet();
                }
            });        
        System.out.println( "Updated ("+ ai.get() + ") files ");
        
        batch.forFiles(f -> {
            try{
                String str = f.getCharContent(true).toString();
                if( str.contains("github" )  && !f.isFileExtension(".class")){
                    System.out.println( "FOUND github in "+ f.getName() );
                }
            }catch(Exception e){
                
            }
            }
        );
        
        batch.forFiles(f-> {
            String originalFileName = f.getName();
            
            String newFileName = originalFileName.replace("C:\\ren\\javaparser\\", "C:\\out3\\javaparser\\");
            newFileName = newFileName.replace("com\\github\\javaparser", "org\\javaparser");            
            newFileName = newFileName.replace("com_github_javaparser", "org_javaparser");
            
            Path writePath = Paths.get(newFileName);
            //make sure the parent directory exists
            writePath.getParent().toFile().mkdirs();
            try{
                Files.write(writePath, f.openInputStream().readAllBytes());
            }catch(IOException ioe){
                throw new _ioException("unable to write file "+writePath, ioe);
            }
        });
    }
    
    /**
     * Move files from one path to a transformed path 
     
    public void testMove(){
        
        Predicate<String> skipFiles = fn-> 
            fn.contains("\\.git\\") 
            || fn.contains("\\surefire-reports\\") 
            || fn.contains(".gitignore");             
        
        _b batch = _b.of("C:\\ren", skipFiles);
        
        System.out.println( "BATCH SIZE"+batch.files.size() );
        System.out.println( "BATCH SKIPPED" + batch.filesSkipped.size() );
        
        batch.forFiles(f-> System.out.println(f.getName()) );
        
        Set<String> ext = new HashSet<>();
        batch.forFiles(f-> ext.add(f.getFileExtension()) );
        System.out.println( ext );
        
        //for selected files, do a search and replace 
        batch.forFiles(f-> f.isFileOfType(".java",".html",".htm", ".txt",
            ".md",".js",".json",".pom",".yml",".yaml",".sh", ".xml", ".bnd", ".props",
            ".properties", ".story"), 
            f-> {
                f.replaceIn("com.github.javaparser", "org.javaparser",
                            "com_github_javaparser", "org_javaparser",
                            "com/github/javaparser","org/javaparser");
            });        
        // the root directory
        // which files to select
        // functions to create the new path for each of the files encountered
        //Function<String,String> pathTransform = p-> p.replace("com/github/javaparser/", "org/javaparser/");
        //Function<String,String> rootTransform = p-> p.replace("ren/", "out3/");
        
        /*
        _batch.transpose(Paths.get("C:\\ren"), 
                
            rootTransform, 
            pathTransform);
            
    }
    */ 
}
