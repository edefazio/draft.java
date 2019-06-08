package draft.java.file;

import draft.java.io._ioException;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Predicate;
import javax.tools.FileObject;

/**
 *
 * @author Eric
 */
public class JavaParserRename {
     public static final String drive = "C:\\";
    
    public static final String sourceDir = "ren";
    public static final String sourcePath = drive+sourceDir;
    
    public static final String modDir = "mod";
    public static final String modPath = drive+modDir;
    
    
    public static final String outDir = "out2";
    
    //file extensions
    //.mf
    public static void main(String[] args){
        batchReadTransformAndWrite();
        
        //bacthModificationApply();
    }
    
    public static void bacthModificationApply(){
        System.out.println( "writing Mods");
        //_load _l = _batch.read(Paths.get("C:\\mod"));
        _batch _l = _batch.of(Paths.get(modPath));
        _l.files.forEach(f -> writeToTarget(f) );
        
    }
    
    public static void batchReadTransformAndWrite() {
        //_load _l = _batch.read(Paths.get("C:\\ren"));
        
        Predicate<String> skipFiles = p-> 
            p.contains("\\.git\\") 
            || p.contains("\\surefire-reports\\") 
            || p.contains(".gitignore");             
            //|| p.contains("target\\classes") 
            //|| p.endsWith(".class");
        _batch _l = _batch.of(Paths.get(sourcePath), skipFiles );
        AtomicInteger ai = new AtomicInteger(0);
        List<URI>filesChanged = new ArrayList<>();
        Set<String> fileExtensions = new HashSet<>();
        _l.files.forEach(f -> {
            String name = f.getName();
            int idx = name.lastIndexOf('.');
            if (idx > 0) {
                fileExtensions.add(name.substring(idx));
            }
            if (f.getName().endsWith(".jar")) {
                writeToTarget(f); //dont do anything to jars... just write them over
            } else{
                //we need to replace the occurrences of these text patterns in code
                // and html and build scripts and yaml files
                String dat = null;
                try{
                    dat = 
                        f.getCharContent(true).toString();
                }catch(IOException ioe){
                    throw new _ioException("unable to read from file "+ f.toUri(), ioe);
                }
                        //new String(f.data);
                int originalHashCode = Objects.hashCode( dat );
                
                dat.replace("com.github.javaparser", 
                                "org.javaparser");
                dat.replace("com_github_javaparser", 
                                "org_javaparser");
                dat.replace("com/github/javaparser", 
                                "org/javaparser");
                dat.replace("\"com\", \"github\", \"javaparser\"", 
                                "\"org\", \"javaparser\"");
                int transformedHashCode = Objects.hashCode( dat );
                if( transformedHashCode != originalHashCode ){
                    filesChanged.add( f.toUri() );
                }
                
                try{
                    f.openWriter().write(dat);
                }catch(IOException ioe){
                    throw new _ioException("unable to write to file "+f.toUri(), ioe);
                }
                writeToTarget(f);            
                ai.addAndGet(1);
            }
        });

        System.out.println("Looked through " + ai.get() + " files");
        System.out.println("file extensions" + fileExtensions);
    }

    private static void writeToTarget(FileObject f) {
        
    }
    
    private static void writeToTarget(_file f) {
        //String outPath = "C:\\";
        String outPath = drive;
        for (int i = 0; i < f.filePath.getNameCount(); i++) {
            if (i > 0) {
                outPath = outPath + File.separatorChar;
            }
            
            //if (f.filePath.getName(i).toString().equals("ren") 
            //    || f.filePath.getName(i).toString().equals("mod")) {
            if (f.filePath.getName(i).toString().equals(sourceDir) 
                || f.filePath.getName(i).toString().equals(modDir)) {    
                //outPath = outPath + "out2";
                outPath = outPath + outDir;
            } else {
                outPath = outPath + f.filePath.getName(i).toString();
            }
        }

        //this should be called repath
        //I should also have a repackage
        if (outPath.contains("com\\github\\javaparser")) { //this happens in certain paths
            outPath = outPath.replace("com\\github\\javaparser", "org\\javaparser");
        }
        if (outPath.contains("com_github_javaparser")) { //this happens in certain file names
            outPath = outPath.replace("com_github_javaparser", "org_javaparser");
        }
        //System.out.println( "Created outpath "+ outPath );            
        try {
            Path op = Paths.get(outPath);
            Files.createDirectories(op.getParent());
            Files.write(op, f.data, StandardOpenOption.CREATE);
        } catch (IOException ioe) {
            System.err.println("^^^^^^^^ Unable to write \"" + outPath + "\"" + ioe);
        }
    }     
}
