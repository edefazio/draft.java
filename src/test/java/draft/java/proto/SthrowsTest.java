/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import draft.java._throws;
import java.io.IOException;
import java.net.URISyntaxException;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class SthrowsTest extends TestCase {
    
    public void testThrowCompose(){
        _throws _th = $throws.any().construct();
        assertTrue( _th.isEmpty());
        
        
        //parameter override
        _th = $throws.any().construct("$throws$", _throws.of(IOException.class) );
        assertEquals( _throws.of(IOException.class), _th);
        System.out.println(_th ); 
    }
    
    public void testThroMatch(){
        $throws $ts = $throws.any();
        class B{
            void m() throws IOException{}       //Yes     
            void r() throws java.io.IOException, URISyntaxException{} //YES
            void f() throws URISyntaxException{} //YES
            void g() throws RuntimeException{} //YES            
        }
        assertEquals( 4, $ts.count(B.class));
        
        $ts = $throws.of(IOException.class);
        
        class C{
            void m() throws IOException{}       //Yes     
            void r() throws java.io.IOException, URISyntaxException{} //tyes
            void f() throws URISyntaxException{} //no
            void g() throws RuntimeException{} //no
            
        }        
        assertEquals(2, $ts.count(C.class));
        
        $ts = $throws.of( URISyntaxException.class, IOException.class);
        class D{
            void m() throws IOException{}         //NO          
            void r() throws java.io.IOException{} //NO
            void g() throws URISyntaxException, IOException{} //YES
            void y() throws java.io.IOException, java.net.URISyntaxException{} //YES
        }
        assertEquals(2, $ts.count( D.class));
        
        //with ONLY constraint
        $ts = $throws.of( ts-> ts.size() == 0 );
        class E{
            void m() throws IOException{}         //NO          
            void r() throws java.io.IOException{} //NO
            void g(){} //YES
            void y(){} //YES
        }
        assertEquals( 2, $ts.count(E.class));
        
        
    }
    
}
