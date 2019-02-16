package draft.java.runtime;

import draft.java._class;
import draft.java.macro._autoDto;
import junit.framework.TestCase;

public class _newTest extends TestCase {

    public void testF(){
        Object o = _new.of(
                _autoDto.Macro.to(
                    _class.of("aaaa.VV").fields("int x, y, z;")
                )
        );
        assertEquals( "VV", o.getClass().getSimpleName() );
        assertEquals( "aaaa.VV", o.getClass().getCanonicalName() );
    }


}
