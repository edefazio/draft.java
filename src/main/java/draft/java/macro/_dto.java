package draft.java.macro;

import com.github.javaparser.ast.expr.ObjectCreationExpr;
import draft.java.Expr;
import draft.java._class;
import draft.java._type;

import java.lang.annotation.*;
import java.util.Arrays;

/**
 * Treats the Object as a DTO (Data Transfer Object) adding:
 * <UL>
 *      <LI>setXXX() METHODS for appropriate FIELDS</LI>
 *      <LI>getXXX() METHODS for appropriate FIELDS</LI>
 *      <LI>a toString() method</LI>
 *      <LI>a hashCode() method</LI>
 *      <LI>an equals(Object o) method</LI>
 *      <LI>a default minimal constructor (initializing non initialized final non static FIELDS)</LI>
 * </UL>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.TYPE_USE})
public @interface _dto {

    Macro $ = new Macro();

    public static class Macro implements _macro<_type> {

        @Override
        public String toString(){
           return "macro[autoDto]"; 
        }
        
        @Override
        public _type apply(_type _t) {
            return to(_t);
        }

        public static <T extends _type> T to(T t) {
            t = _getters.Macro.to(t);
            t = _settersFluent.Macro.to(t);
            t = _equals.Macro.to(t);
            t = _hashCode.Macro.to(t);
            t = _toString.Macro.to(t);
            t = _autoConstructor.Macro.to(t);

            t.removeAnnos(_dto.class);
            return t;
        }

        /**
         * Provide a abstract class and signature to return a _class that is a Dto
         * @param signature the signature of the class
         * @param body the BODY (implemented as an annonymous class that can implement or extend a BaseClass)
         * @return
         */
        public static _class of(String signature, Object body ){
            StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
            ObjectCreationExpr oce = Expr.anonymousObject( ste );
            _class _c = _class.of(signature);
            oce.getAnonymousClassBody().get().forEach( b-> _c.ast().addMember(b));
            // Todo implement or extend
            Arrays.stream( body.getClass().getInterfaces()).forEach(e -> {
                _c.implement( e );
                _c.imports( e );
                });
            if( body.getClass().getSuperclass() != null){
                _c.extend( body.getClass().getSuperclass() );
                _c.imports( body.getClass().getSuperclass() );
            }
            return to(_c);
        }
    }
}
