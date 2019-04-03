package draft.java.macro;

import com.github.javaparser.ast.stmt.BlockStmt;
import draft.java.*;
import draft.java.proto.pMethod;
import draft.java.proto.pStmt;

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
public @interface _autoToString {

    Macro $ = new Macro();

    class Macro implements _macro<_type> {

        @Override
        public _type apply(_type _t) {
            return to( _t);
        }

        public static final pMethod $TO_STRING = pMethod.of(
            "public String toString(){",
            "    StringBuilder sb = new StringBuilder();",
            "    sb.append( \"$className$\" ).append(\"{\" );",
            "    sb.append( System.lineSeparator() );",
            "    $body: {}",
            "    sb.append( \"}\" );",
            "    return sb.toString();",
            "    }");

        /**
         * NOTE: COMMENTD THIS OUT FOR STARTUP PERFORMANCE
         *
         * Template toString method
         * the $className$ is filled in
         * $BODY:{} is dynamically populated with Statements
         *
        public static final $method $TO_STRING = $method.of(
                "public String toString()", (String s)-> {
                    StringBuilder sb = new StringBuilder();
                    sb.append( "$className$" ).append("{" );
                    sb.append( System.lineSeparator() );
                    $body: {}
                    sb.append( "}" );
                    return sb.toString();
                });
        */

        /**
         * Decide which FIELDS are to be added into the toString method
         */
        public static final Predicate<_field> TO_STRING_FIELDS = f-> !f.isStatic() && !f.isTransient();

        static pStmt $simple = pStmt.of(
            "sb.append(\" $name$: \").append($name$).append(System.lineSeparator());" );

        static pStmt $arrayOfPrimitives = pStmt.of(
            "sb.append(\" $name$: \").append(java.util.Arrays.toString($name$)).append(System.lineSeparator());" );

        static pStmt $arrayOfObjects = pStmt.of(
            "sb.append(\" $name$: \").append(java.util.Arrays.deepToString($name$)).append(System.lineSeparator());");

        /* REMOVED FOR INCREASED STARTUP PERFORMANCE
        static $stmt $simple = $stmt.of( (StringBuffer sb, Object $name$) ->
                sb.append(" $name$: ").append($name$).append(System.lineSeparator()) );

        static $stmt $arrayOfPrimitives = $stmt.of( (StringBuffer sb, int[] $name$) ->
                sb.append(" $name$: ").append(java.util.Arrays.toString($name$)).append(System.lineSeparator()) );

        static $stmt $arrayOfObjects = $stmt.of( (StringBuffer sb, Object[] $name$) ->
                sb.append(" $name$: ").append(java.util.Arrays.deepToString($name$)).append(System.lineSeparator()));
        */

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
            _method _m = $TO_STRING.construct("className", _t.getName(), "$body", body );
            ((_method._hasMethods)_t).method(_m);
            return _t;
        }
    }
}
