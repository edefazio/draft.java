package draft.java.macro;

import draft.java.*;
import draft.java.proto._pMethod;

import java.lang.annotation.*;
import java.util.List;
import java.util.function.Predicate;

/**
 * Builds a setXXX METHODS for all non_static, non final FIELDS on the TYPE
 * Works on {@link _class}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface _autoSet {

    Macro $ = new Macro();

    class Macro implements _macro<_type> {

        public static final Macro INSTANCE = new Macro();

        /** picks _fields of the _class that are required in the constructor */
        public static Predicate<_field> SET_FIELDS = _f -> !_f.isStatic() && !_f.isFinal();

        /** template method for a setXXX() method */
        public static _pMethod $SET = _pMethod.of(
                "public void set$Name$($type$ $name$){ this.$name$ = $name$; }" );

        @Override
        public _type apply(_type _t) {
            return to( _t );
        }

        public static <T extends _type> T to(T t) {
            if (t instanceof _method._hasMethods) {
                List<_field> _fs = t.listFields(SET_FIELDS);
                _fs.forEach(f ->
                        ((_method._hasMethods) t).method($SET.construct("name", f.getName(), "type", f.getType()))
                );
            }
            return t;
        }
    }
}
