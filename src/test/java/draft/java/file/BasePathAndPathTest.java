/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.file;

import java.nio.file.Path;
import java.nio.file.Paths;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class BasePathAndPathTest extends TestCase {
    
    public void testBP(){
        Path base = Paths.get("C:/temp/MyProject/src/test/java");
        Path sub = Paths.get("C:/temp/MyProject/src/test/java", "aaaa", "bbbb", "C.java");
        
        Path rel1 = sub.relativize(base);
        
        Path rel2 = base.relativize(sub);
        
        System.out.println( rel1);
        System.out.println( rel2);
    }
    
}
