package draft.java;

import junit.framework.TestCase;
import draft.java.macro.*;

import java.io.Serializable;

public class autoTest extends TestCase {

    @_public
    //@_replace({"Internal", "Modified"})
    @_implement(Serializable.class)
    class Internal{

        @_replace({"int", "float"})
        public int value;

        @_public
        @_replace({"message", "hello world"})
        void doRep( ){
            System.out.println( "message" );
        }

        @_static
        @_replace()
        void anotherRep(){
            System.out.println( "message"); //isnt replaced
        }

        @_replace({"Start","End","first", "last"})
        void letsGetThisPartyStarted(){
            int first = 0;
        }
    }

    public void testY(){
        _class _c = _class.of( Internal.class );
        System.out.println("NOT PROCESSED" + _c );

        _c = _macro._class(Internal.class);
        System.out.println("PROCESSED" + _c );
    }
}
