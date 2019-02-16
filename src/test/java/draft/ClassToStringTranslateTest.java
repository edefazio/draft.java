package draft;

import junit.framework.TestCase;

import java.util.Map;

public class  ClassToStringTranslateTest
        extends TestCase {

    public void testClassToString(){
        assertEquals("java.util.Map", Translator.ClassToString.translate(Map.class));
    }
}