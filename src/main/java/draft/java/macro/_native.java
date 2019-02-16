package draft.java.macro;

import draft.java._anno;
import draft.java._modifiers;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface _native {
    Macro $ = new Macro();

    class Macro implements _macro<_anno._hasAnnos> {

        @Override
        public _anno._hasAnnos apply(_anno._hasAnnos _model) {
            return to(_model);
        }

        public static <T extends _anno._hasAnnos> T to( T _model ){
            ((_modifiers._hasModifiers) _model).getModifiers().setNative();
            return _model;
        }
    }
}
