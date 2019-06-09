package draft.java.macro;

import draft.java._anno;
import draft.java._modifiers;
import draft.java._type;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Macro annotation to apply the public modifier to a {@link _type}, {@link draft.java._field},
 * {@link draft.java._method}, or {@link draft.java._constructor}.<BR>
 *
 * when processed by
 * {@link _macro#to(Class)}
 * {@link _macro#_class(Class)}
 * {@link _macro#_interface(Class)}
 * {@link _macro#_enum(Class)}
 * {@link _macro#_annotation(Class)}
 *
 * example:
 * <PRE>
 * public void testMacroAnno(){
 *     @_public class D{
 *         @_public int x;
 *
 *         @_public D(){}
 *
 *         @_public void print(){}
 *     }
 *     _class _c = _macro._class(D.class);
 * }
 *     where _c is:
 *     //  public class D{
 *     //      public int x;
 *     //      public D(){}
 *     //      public void print(){}
 *     //  }
 * }
 * </PRE>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD, ElementType.METHOD, ElementType.CONSTRUCTOR})
public @interface _public {

    Macro $ = new Macro();

    /**
     * Because this static member class implements _macro, it can be processed by
     * {@link _macro#to(Class)}
     * {@link _macro#_class(Class)}
     * {@link _macro#_interface(Class)}
     * {@link _macro#_enum(Class)}
     * {@link _macro#_annotation(Class)}
     *
     * to _2_template the model
     */
    class Macro implements _macro<_anno._hasAnnos> {

        @Override
        public String toString(){
           return "macro[public]"; 
        }
        
        @Override
        public _anno._hasAnnos apply(_anno._hasAnnos _annotatedModel) {
            return to(_annotatedModel);
        }

        /**
         * Apply this Macro directly to the
         * @param _model
         * @param <T>
         * @return
         */
        public static <T extends _anno._hasAnnos> T to( T _model ){
            ((_modifiers._hasModifiers) _model).getModifiers().setPublic();
            return _model;
        }
    }
}
