package usecase.spoon;

import com.github.javaparser.ast.comments.Comment;
import draft.java.io._bulk;
import draft.java.proto.$comment;
import junit.framework.TestCase;

/**
 * I just created this one because it's something I think would be widely used
 * 
 * @author Eric
 */
public class HasTodoCommentTest extends TestCase {
    //find all TODO comments in code
    
    public void testFindTodo(){
        $comment $anyTodoComment =
            $comment.any().addConstraint(c-> c.getContent().contains("TODO"));
        _bulk.consume( 
            "C:\\Users\\Eric\\.m2\\repository\\com\\github\\javaparser\\javaparser-core\\3.14.2\\javaparser-core-3.14.2-sources.jar",
            t->{
                $anyTodoComment.forEachIn(t, c->System.out.println(t.getFullName()+ "Found "+ c +" at "+ ((Comment)c).getBegin() ) );
            });
        /**
         Output on the source of javapaser-core 3.14.2:
 com.github.javaparser.resolution.MethodUsageFound // TODO if the method declaration has a type param with that name ignore this call
 at Optional[(line 158,col 9)]
com.github.javaparser.resolution.MethodUsageFound // TODO use the type parameters
 at Optional[(line 185,col 9)]
com.github.javaparser.ast.validator.Java5ValidatorFound // TODO validate annotations on classes, fields and methods but nowhere else
 at Optional[(line 59,col 9)]
com.github.javaparser.resolution.types.parametrization.ResolvedTypeParametersMapFound // TODO: we shouldn't just silently overwrite existing types!
 at Optional[(line 60,col 13)]
com.github.javaparser.ast.validator.Java8ValidatorFound // TODO validate more annotation locations http://openjdk.java.net/jeps/104
 at Optional[(line 29,col 9)]
com.github.javaparser.ast.validator.Java8ValidatorFound // TODO validate repeating annotations http://openjdk.java.net/jeps/120
 at Optional[(line 30,col 9)]
com.github.javaparser.printer.lexicalpreservation.LexicalPreservingPrinterFound 
  TODO: Process CsmIndent and CsmUnindent before reaching this point
 at Optional[(line 467,col 5)]
com.github.javaparser.GeneratedJavaParserBaseFound // TODO tokenRange only takes the final token. Need all the tokens.
 at Optional[(line 66,col 9)]
com.github.javaparser.utils.PositionUtilsFound // TODO < or <= ?
 at Optional[(line 147,col 13)]
com.github.javaparser.ast.NodeListFound // TODO take care of "Iterator.remove"
 at Optional[(line 134,col 9)]
         **/        
    } 
}
