package draft.java.macro;

import draft.java.Expr;
import draft.java._class;
import draft.java._field;
import junit.framework.TestCase;

public class _initTest extends TestCase {

    public void testF(){
        _field _f = _field.of( "int i" );
        _init.Macro.to(_f, Expr.of(100));
        assertEquals( Expr.of(100), _f.getInit() );
    }

    public void testAnno(){
        _class _c = _class.of(new Object(){
            class G {
                @_init("1")
                int f;
            }
        });
        assertEquals( Expr.of(1), _c.getField("f").getInit());
    }
}
