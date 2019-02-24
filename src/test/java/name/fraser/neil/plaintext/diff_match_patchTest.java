/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package name.fraser.neil.plaintext;

import draft.java.Ast;
import draft.java._class;
import java.util.LinkedList;
import junit.framework.TestCase;
import name.fraser.neil.plaintext.diff_match_patch.Diff;
import name.fraser.neil.plaintext.diff_match_patch.Operation;

/**
 *
 * @author Eric
 */
public class diff_match_patchTest extends TestCase {
    
    public void testDiffMatch(){
        class C{
            void a(){
                //Comment
                System.out.println( 1 );                
            }
            void b(){
                System.out.println(2);
            }
        }
        _class _c = _class.of( C.class);
        diff_match_patch dmp = new diff_match_patch();
        LinkedList<Diff> lld = dmp.diff_main(
                _c.getMethod("a").getBody().toString(Ast.PRINT_NO_COMMENTS),
                _c.getMethod("b").getBody().toString(Ast.PRINT_NO_COMMENTS) );
        lld.stream().filter( d -> d.operation == Operation.DELETE || d.operation == Operation.INSERT ).forEach(d -> System.out.println(d+" "));
        
        
        
    }
    static diff_match_patch dmp = new diff_match_patch();
    public void testNoDiff(){
        LinkedList<Diff> dd = dmp.diff_main("A", "A");        
        System.out.println(  dd  );
        System.out.println(  dd.size() );
        dd = dmp.diff_main("A", "B");
        System.out.println(  dd  );
        System.out.println(  dd.size() );
        
        if( dd.stream().filter(d -> d.operation != Operation.EQUAL).findFirst().isPresent() ){
            System.out.println( "DIFFS");
        } else{
            System.out.println( "DIFFS");
        }
    }
}
