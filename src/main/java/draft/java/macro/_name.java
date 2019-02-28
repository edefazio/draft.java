package draft.java.macro;

import draft.java._anno;
import draft.java._model._named;
import draft.java._type;

import java.lang.annotation.*;
import java.util.Arrays;

/**
 * Annotation Macro to add imports to a _type
 */
@Retention( RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
public @interface _name{
    String value();

    class Macro implements _macro<_anno._hasAnnos> {
        String name;

        public Macro( _name _e ){
            this.name = _e.value();
        }

        public _anno._hasAnnos apply( _anno._hasAnnos _a){
            return to( _a, name );
        }

        public static _anno._hasAnnos to( _anno._hasAnnos _a, String name ){
            if( _a instanceof _named ){
                ((_named)_a).name(name);
            }
            return _a;            
        }
    }
}
