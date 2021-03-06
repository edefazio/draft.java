package draft.java.proto;

import com.github.javaparser.ast.comments.JavadocComment;
import draft.java.Ast;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class ScommentTest extends TestCase {
    
    public void testCompose(){
        assertEquals( Ast.lineComment("//Hello ").getContent().trim(), 
                $comment.of("//Hello").construct().getContent().trim());
        //assertEquals( Ast.blockComment("/* Hello */"), $comment.of("/* Hello */").construct());
    }
    
    public void testConstruct(){
        assertNull($comment.javadocComment().construct());        
        assertNotNull($comment.javadocComment().construct("javadoc", "Hi"));
        System.out.println($comment.javadocComment().construct("javadoc", "Hi"));
        System.out.println($comment.javadocComment().construct("javadoc", "Hi"+System.lineSeparator()+"There"));
        
        assertNull($comment.lineComment().construct());
        assertNotNull($comment.lineComment().construct("comment", "Hi"));
        
        System.out.println($comment.lineComment().construct("comment", "Hi"));
        System.out.println($comment.lineComment().construct("comment", "Hi"+System.lineSeparator()+"There"));
    }
    
    public void testAnyMatch(){
        assertTrue( $comment.any().matches("//Hello") );
        assertTrue( $comment.any().matches("/*Hello*/") );
        assertTrue( $comment.any().matches("/** Hello*/") );
        
        assertTrue( $comment.lineComment().matches("//Hello") );
        assertFalse( $comment.lineComment().matches("/*Hello*/") );
        assertFalse( $comment.lineComment().matches("/** Hello*/") );
        
        assertFalse( $comment.blockComment().matches("//Hello") );
        assertTrue( $comment.blockComment().matches("/*Hello*/") );
        assertFalse( $comment.blockComment().matches("/** Hello*/") );
        
        assertFalse( $comment.javadocComment().matches("//Hello") );
        assertFalse( $comment.javadocComment().matches("/*Hello*/") );
        assertTrue( $comment.javadocComment().matches("/** Hello*/") );        
    }
    
    public void testFindFirst(){
        /** Javadoc */
        class C{
            /* block comment */
            int i=1200;
            
            void m(){
               // line comment (orphan)
               
               // line comment (assigned)
               assert(1==1);
            }            
        }
        assertEquals(4, $comment.any().count(C.class));
        
        
        //as expected the orphaned comment comes first...
        System.out.println( $comment.any().firstIn(C.class) );
        assertTrue( $comment.any().firstIn(C.class).isJavadocComment());
        
        assertEquals( Ast.lineComment("// line comment (orphan)"), 
            $comment.lineComment().firstIn(C.class));
        
        assertEquals( Ast.blockComment("/* block comment */"), 
            $comment.blockComment().firstIn(C.class));
        
        assertEquals( Ast.lineComment("// line comment (orphan)"), 
            $comment.lineComment(c -> c.isOrphan()).firstIn(C.class)); //first orphaned line comment
        
        assertEquals( Ast.lineComment("// line comment (assigned)"), 
            $comment.lineComment(c -> !c.isOrphan()).firstIn(C.class)); //first un orphaed line comment
        
        assertEquals( Ast.lineComment("// line comment (orphan)"), 
            $comment.any().addConstraint( (c) -> c.isOrphan() )
                    .firstIn(C.class)); //first orphaned comment
    }       
}
