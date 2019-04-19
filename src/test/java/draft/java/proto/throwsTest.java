/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import draft.java.Walk;
import draft.java._class;
import java.io.IOException;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class throwsTest extends TestCase {
    
    public void testTh(){
        class C {
            void m() throws IOException, java.net.MalformedURLException {
                
            }
        }
        _class _c = _class.of(C.class);
        Walk.in(_c, n-> System.out.println(n.getClass()+ " "+ n ) );
        
        $typeDecl.of(IOException.class).removeIn(_c);
        System.out.println( _c );
        
        $classUse.replace(_c, IOException.class, java.net.BindException.class);
        System.out.println( _c );
    }
}
