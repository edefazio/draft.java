package draft.java.file;

import java.nio.file.Paths;
import java.util.function.Function;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class _batchTestMove extends TestCase {
    
    /**
     * Move files from one path to a transformed path 
     */
    public void testMove(){
        // the root directory
        // which files to select
        // functions to create the new path for each of the files encountered
        Function<String,String> pathTransform = p-> p.replace("com/github/javaparser/", "org/javaparser/");
        Function<String,String> rootTransform = p-> p.replace("ren/", "out3/");
        
        /*
        _batch.transpose(Paths.get("C:\\ren"), 
                
            rootTransform, 
            pathTransform);
        */    
    }
}
