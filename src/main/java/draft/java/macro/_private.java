package draft.java.macro;

import draft.java._anno;
import draft.java._modifiers;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface _private {

    Macro INSTANCE = new Macro();

    class Macro implements _macro<_anno._hasAnnos> {

        @Override
        public _anno._hasAnnos apply(_anno._hasAnnos _annotatedModel) {
            return to(_annotatedModel);
        }

        public static <T extends _anno._hasAnnos> T to( T _model ){
            ((_modifiers._hasModifiers) _model).getModifiers().setPrivate();
            return _model;
        }
    }
}
