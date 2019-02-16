package draft.java.macro;

import draft.java._class;
import junit.framework.TestCase;

public class _autoConstructorTest extends TestCase {

    public void testAutoCtorNoArg(){
        @_autoConstructor
        class F{

        }
        _class _c =  _macro._class(F.class);
        //verify I created a constructor AND it has no PARAMETERS
        assertTrue( _c.getConstructor(0).getParameters().isEmpty() );

        @_autoConstructor
        class G{
            int a,b,c;
            String s;
        }
        _c =  _macro._class(G.class);
        //verify I created a constructor AND it has no PARAMETERS (no final FIELDS)
        assertTrue( _c.getConstructor(0).getParameters().isEmpty() );
    }

    @_autoConstructor
    private static class S{
        @_final int val;
    }

    public void testCtorSingleArg(){
        _class _c = _macro._class(S.class);
        assertTrue( _c.getConstructor(0).getParameter(0).isType(int.class));
    }

}
