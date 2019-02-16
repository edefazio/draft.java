package draft.java.runtime;

import draft.java._class;
import draft.java.macro.*;
import draft.java.file._classFiles;
import junit.framework.TestCase;

public class _javacTest extends TestCase {

    public void testJ(){
        _class _c = _autoToString.Macro.to( _class.of("aaaa.bbb.C").field("int x,y,z;"));
        _classFiles _cfs =_javac.of(_c);

        //use
        _javac.of( _c);
        _javac.of( _javac.options().parameterNamesStoredForRuntimeReflection(), _c);
    }

    public void testParentChildClassLoader(){
        _class _a = _class.of("aaaa.bbbb.A", new Object(){ int x,y,z; }, _autoDto.$);
        _class _b = _class.of("aaaa.bbbb.B", new Object(){ float a, b,c;}, _autoDto.$).extend(_a);
        //System.out.println( _a );

        //I HAVE to call _new b/c I have to realize/ lo
        _classLoader _parentClassLoader = (_classLoader)_new.of(_a).getClass().getClassLoader();

        //compile b using the base class Loader (_clA) containing _a, it's base class
        _javac.of(_parentClassLoader, _b );
        //_project.of(_parentClassLoader, _b );

    }
}
