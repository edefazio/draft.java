package draft.java;

import com.github.javaparser.ast.comments.Comment;
import draft.java.io._bulk;
import draft.java.io._io;
import draft.java.macro._autoDto;
import draft.java.macro._macro;
import draft.java.macro._package;
import draft.java.macro._replaceSystemOutWithLog;
import draft.java.runtime._javac;
import draft.java.runtime._project;
import draft.java.proto.$stmt;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.List;


/**
 * Can I?
 * bulk.load(...);
 *
 * but I want to bulk.load(),
 * read in a bunch of .java files into draft models
 * apply some macros to the models,
 * compile the models,
 * load the models into a new classLoader
 * then generate a test suite (code) i.e. look for tests within the compiled code
 * compile the test suite
 * load the test suite into a child classloader
 * then run the testsuite
 * IF the test suite succeeds,
 *    write the result to some place
 * IF not
 *
 * ... and then I want to generate
 */
public class _astFeatures extends TestCase {

    public void setUp(){
        @_package("aaaa.bbbb") @_autoDto class C{
            int x,y,z;

            /** TODO: blah */
            public void v(){
                System.out.println(1);
            }
        }
        _io.out( _io.config().outProjectDir("C:/dev/orig"),
                _project.of(_class.of(C.class)));
    }

    public void testMonster1(){
        //List<Comment>todoComments = new ArrayList();
        _io.out(_io.config().outProjectDir("C:/dev/refi"),
            _project.of(_javac.options().parameterNamesStoredForRuntimeReflection(),
                _bulk.fn("C:/dev/orig",
                    (_t) -> (_type)$stmt.of("System.out.println($any$);").removeIn(_t)
                    ).typesArray() ) );

    }

    public void testMonster(){
        List<Comment>todoComments = new ArrayList();

        _io.out( _io.config().outProjectDir("C:/dev/refi"),
            _project.of(_javac.options().parameterNamesStoredForRuntimeReflection(),
            _bulk.fn("C:/dev/orig",
                _replaceSystemOutWithLog.JavaLoggerFine,
                (t)->{ todoComments.addAll(
                        Walk.list(t, Comment.class, (Comment c) -> Ast.getContent(c).startsWith("TODO")) );
                    return t;
                }) )
            );
        assertTrue( todoComments.size() <10 );
    }

    public void testPrintCodeWithoutAnnotationsOrComments(){
        /** Javadoc */
        @Deprecated
        class C{

            /** JAVADOC */
            int f;
        }
        System.out.println( _class.of( C.class ).toString(Ast.PRINT_NO_ANNOTATIONS_OR_COMMENTS));
    }

    public void testBulkLoadFindTodoTags(){
        /**
         * TODO check in Javadocs
         */
        class L {
            /**
             * TODO: check tags
             */
            int f = 100;

            /* TODO Multi line block comment
             *
             */

            /* TODO unowned block comment */

            //TODO an unowned line comment
        }
        _class _c = _class.of(L.class);


        //System.out.println( "TOP COMMENT \"" + _javadoc.getContent( _c.astType().getComment().get() ) +"\"");
        //Walk.in( _c, Comment.class, (Comment c)-> System.out.println( Ast.getContent( c ) ));
        List<Comment> todoComments =
                Walk.list( _c, Comment.class, (Comment c)-> Ast.getContent( c ).startsWith("TODO")  );
        System.out.println( todoComments );
        assertEquals( 5, todoComments.size());
    }

}
