package draft.java;

import draft.java.runtime._project;
import junit.framework.TestCase;
import draft.java.macro.*;

import java.util.Map;
import java.util.UUID;

public class RuntimeAnnotationsTest extends TestCase {

    public void test_imports(){
        @_promote("aaaa.bbbb")
        @_import( {UUID.class, Map.class})
        class C{
            UUID u = UUID.randomUUID();
        }

        _class _c = _class.of(C.class);
        assertTrue(_c.isImported(UUID.class));
        assertTrue(_c.isImported(Map.class));
        _project.of( _c );
    }

    public void test_static(){
        class C{
            @_public @_static @_final int d = 100;
        }
        _class _c = _class.of(C.class);
        assertTrue(_c.getField("d").isStatic());
        assertTrue(_c.getField("d").isFinal());
        assertTrue(_c.getField("d").isPublic());
        _project.of( _c );

    }



}
