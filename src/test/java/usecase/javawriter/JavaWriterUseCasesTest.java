package usecase.javawriter;

import draft.java._class;
import draft.java.macro._package;
import draft.java.macro._remove;
import junit.framework.TestCase;

/**
 * Inspired from the talk <A HREF="https://youtu.be/Hk-L2Ozo1SI?t=2644">Plugging 
 * into the Java compiler</A>.  From Googlers (Christian Gruber, and 
 * Éamonn McManus) talking about Googles 
 * <A HREF="https://github.com/google/dagger">Dagger</A> 
 * and 
 * <A HREF="https://github.com/google/auto">Auto</A> Frameworks
 * This excerpt is about using JavaWriter (the precursor to JavaPoet) to build 
 * code programmatically.
 * 
 * 
 * @author Eric
 */
public class JavaWriterUseCasesTest 
    extends TestCase {
    
    //A "Stringyfied" procedural way to creat the constuctor
    public void testJW(){
        _class _c = _class.of("some.packge.SomeType")
            .field("private final String foo;")
            .constructor("(Key key){",
                "this.foo = key.getFormat();",
                "}"); 
        System.out.println(_c);
        //_io.out( "C:\\temp", _c ); to write it to a .java file
    }
     
    /**
     * An alternative / more intuitive way for building the class....
     * (since this class is not dynamic) 
     * NOTE: the nested Key class has a @_remove annotation that will cause it 
     * to be removed from the _class
     * 
     * Note: I had to change the package name from "some.package" to 
     * "some.packge" ("package" is a reserved word)
     */    
    public void testFromLocalClass(){
    
        @_package("some.packge")
        class SomeType{
            private final String foo;
            
            public SomeType(Key key){
                this.foo = key.getFormat();
            }
            
            @_remove class Key{ public String getFormat(){ return "A";} }
        }
        _class _c = _class.of(SomeType.class);
        System.out.println( _c );
        assertNull( _c.getNest("Key") );//verify we @_remove the Key nested class
    }    
}
