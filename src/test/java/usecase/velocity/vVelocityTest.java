package usecase.velocity;

import draft.java._field;
import draft.java._method;
import draft.java.proto.$method;
import draft.java.proto.$stmt;
import junit.framework.TestCase;

/**
 * Inspired from the talk <A HREF="https://youtu.be/Hk-L2Ozo1SI?t=2811">Plugging 
 * into the Java compiler</A>.  From Googlers (Christian Gruber, and 
 * Éamonn McManus) talking about Googles 
 * <A HREF="https://github.com/google/dagger">Dagger</A> 
 * and 
 * <A HREF="https://github.com/google/auto">Auto</A>
 * specifically how "Auto" 
 * (dependency injection/ code generation)
 * "Auto" (Annotations & Processor for generating Java code
 * integrating Velocity).  Also mentions JavaWriter (the precursor to JavaPoet).
 */
public class vVelocityTest extends TestCase {

    /** The skeleton of the method (without body) */
    static final $method $m = $method.of("@Override public $type$ $name$(){}")
        .$modifiers();

    /** These statements will make up the body */
    static final $stmt $arrayIsNullable = $stmt.of("return $name$ == null ? null : $name$.clone();" );
    static final $stmt $array = $stmt.of( "return $name$.clone();" );
    static final $stmt $default = $stmt.of("return $name$;");

    public static _method computeRange(_field _f) {
        _method _m = $m.construct(_f.deconstruct()); /* build the skeleton method */
        if (_f.hasAnno("nullable")) {  /* add body to the method */
            return _m.add($arrayIsNullable.construct(_f.deconstruct()));
        }
        if (_f.isArray()) {
            return _m.add($array.construct(_f.deconstruct()));
        }
        return _m.add($default.construct(_f.deconstruct()));
    }

    public void testTemplate(){
        _field _f = _field.of("@nullable public int[] postCodes;");
        System.out.println( _f.deconstruct() );
        System.out.println( computeRange(_f) );
        System.out.println( computeRange(_field.of("public int[] postCodes;") ) );
        System.out.println( computeRange(_field.of("public int postCode;") ) );
        System.out.println( computeRange(_field.of("int postCode;") ) );

        //List<_field> _fs = new ArrayList<>();
    }
}
