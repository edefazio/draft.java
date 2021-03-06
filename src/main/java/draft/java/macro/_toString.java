package draft.java.macro;

import com.github.javaparser.ast.stmt.BlockStmt;
import draft.java.*;
import draft.java.proto.$method;
import draft.java.proto.$stmt;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.List;
import java.util.function.Predicate;

/**
 * Builds a toString METHODS for all {@link _field}s on the {@link _type} that
 * are not static Works on {@link _class}
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface _toString {

    Macro $ = new Macro();

    class Macro implements _macro<_type> {

        @Override
        public _type apply(_type _t) {
            return to( _t);
        }

        @Override
        public String toString(){
           return "macro[autoToString]"; 
        }
        
        public static final $method $TO_STRING = $method.of(
            "public String toString(){",
            "    StringBuilder sb = new StringBuilder();",
            "    sb.append( \"$className$\" ).append(\"{\" );",
            "    sb.append( System.lineSeparator() );",
            "    $body: {}",
            "    sb.append( \"}\" );",
            "    return sb.toString();",
            "    }");

        /**
         * Decide which FIELDS are to be added into the toString method
         */
        public static final Predicate<_field> TO_STRING_FIELDS = f-> !f.isStatic() && !f.isTransient();

        static $stmt $simple = $stmt.of(
            "sb.append(\" $name$: \").append($name$).append(System.lineSeparator());" );

        static $stmt $arrayOfPrimitives = $stmt.of(
            "sb.append(\" $name$: \").append(java.util.Arrays.toString($name$)).append(System.lineSeparator());" );

        static $stmt $arrayOfObjects = $stmt.of(
            "sb.append(\" $name$: \").append(java.util.Arrays.deepToString($name$)).append(System.lineSeparator());");

        public static <T extends _type> T to( T _t){
            List<_field> _fs = _t.listFields(TO_STRING_FIELDS);
            BlockStmt body = new BlockStmt();
            _fs.forEach( _f  -> {
                if( _f.isArray() ){
                    if( _f.getElementType().isPrimitive() ){
                        body.addStatement( $arrayOfPrimitives.fill(_f.getName()) );
                    }else{
                        body.addStatement( $arrayOfObjects.fill(_f.getName()) );
                    }
                } else{
                    body.addStatement( $simple.fill(_f.getName()) );
                }
            });
            _method _m = $TO_STRING.construct("className", _t.getName(), "body", body );
            ((_method._hasMethods)_t).method(_m);
            return _t;
        }
    }
}
