package draft.java.macro;

import draft.java._anno._hasAnnos;
import draft.java._class;
import draft.java._type;
import draft.java.proto.pStmt;
import junit.framework.TestCase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.UUID;

/**
 * Things that should help with composition
 *
 * One way
 * Dependency injection, Reflection, Runtime Proxies, bytecode generation
 *
 *
 * Another way, generate & compile code
 * More code
 * But less encapsulation
 * Less frameworks
 * smaller binaries
 * easier to debug
 * faster bootstrap
 *
 */
public class _macroTest extends TestCase {

    public void testManualMac(){

        _class _c = _class.of("C").fields("int x, y, z;");

        //_class _r = $$.to(_c, _autoToString.INSTANCE, _autoConstructor.INSTANCE, _autoEquals.INSTANCE, _autoHashCode.INSTANCE);
        //_c = _class.of("C").FIELDS("int x, y, z;");
        _class _s = _autoHashCode.Macro.to( _autoEquals.Macro.to(_autoConstructor.Macro.to( _autoToString.Macro.to(_c))));

       // assertEquals( _r, _s );
        @_autoHashCode
        @_autoEquals
        @_autoConstructor
        @_autoToString
        class C{
            int x,y,z;
        }
        _class _v = _class.of(C.class);

        assertEquals( _v, _s);
    }

    /**
     * Code in a tweet:
     *
     * This will generate a new static field ID with a UUID
     */
    @Retention(RetentionPolicy.RUNTIME)
    @interface ID{
        class M implements _macro<_type> {

            public M( ID id ){}

            public _type apply(_type _t) {
                _t.field("public static String ID=\""+ UUID.randomUUID()+"\";");
                return _t;
            }
        }
    }

    public void testMacro(){
        @ID //Apply the _macro to a class
        class C{}

        _class _c = _class.of(C.class);
        assertTrue( _c.getField("ID").isStatic() );
        System.out.println( _c );
    }

    /**
     * Removes all System.out.println Statements within the code
     * either add to a whole TYPE, a single method or a single constructor
     */
    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD, ElementType.CONSTRUCTOR})
    @interface removePrintlns {
        
        pStmt $println = pStmt.of( "System.out.println($any$);" );

        class Macro implements _macro<_hasAnnos> {
            public Macro( removePrintlns rp ){}
            @Override
            public _hasAnnos apply(_hasAnnos _model) {
                $println.removeIn( (draft.java._model._node)_model);
                return _model;
            }
        }
    }

    public void testMacroRemovePrintlns(){

        @removePrintlns
        class C{
            void f(){
                int i = 23;
                System.out.println(i);
                System.out.println("Some text "+ 2 + " Some more text");
            }

            void g(){
                System.out.println("method println");
            }
            C(){
                System.out.println( "constructor println");
            }
        }

        _class _c = _class.of( C.class);
        _c.annotate(removePrintlns.class);

        //verify that AFTER I run the _macro, there are no matching $printlns left
        //assertEquals(0, removePrintlns.$println.selectAllIn(_c).size());
        // System.out.println( _c );
    }

    //instead of removing System.outs, convert them into comments
    public void testCommentOut() {
        pStmt $from = pStmt.of( "System.out.println( $any$ );" );

        pStmt $to = pStmt.of( "{ /*System.out.println( $any$ );*/ }" );
        class C{
            void f(){
                int i = 23;
                System.out.println(i);
                System.out.println("Some text "+ 2 + " Some more text");
            }

            void g(){
                System.out.println("method println");
            }
            C(){
                System.out.println( "constructor println");
            }
        }
        _class _c = _class.of(C.class);
        $from.replaceIn(_c, $to);
        System.out.println( _c );
    }
}
