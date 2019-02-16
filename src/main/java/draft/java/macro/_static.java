package draft.java.macro;

import draft.java.*;
import draft.java._anno._hasAnnos;

import java.lang.annotation.*;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD})
public @interface _static  {

    Macro $ = new Macro();

    class Macro implements _macro<_hasAnnos> {

        @Override
        public _hasAnnos apply(_anno._hasAnnos _annotatedModel) {
            return to(_annotatedModel);
        }

        public static <T extends _anno._hasAnnos> T to( T _model ){
            ((_modifiers._hasModifiers) _model).getModifiers().setStatic();
            return _model;
        }
    }
}
