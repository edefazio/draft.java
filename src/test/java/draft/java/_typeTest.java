package draft.java;

import draft.java.runtime._javac;
import junit.framework.TestCase;

public class _typeTest extends TestCase {


    public void testImportsFromOther_type(){
        _class _c = _class.of("aaaa.vvvv.C")
                .nest( _class.of("F", new Object(){
                    int x, y; }
                    )
                );

        _class _d = _class.of( "ffff.gggg.H")
                .imports(_c, _c.getNest("F") );

        //verify I can import another _type that hasnt been drafted yet
        assertTrue( _d.isImported(_c) );

        //verify I can import a nested type that hasnt been drafted yet
        assertTrue( _d.isImported(_c.getNest("F")) );
        _javac.of( _c, _d); //verify they both compile

        System.out.println(_d );
    }

    public void testS(){
        _type _t = _class.of("C").field("int x");

        //Not sure WHY I have to do this (_field)cast
        _t.forFields( f-> ((_field)f).isType(int.class) );
    }
}
