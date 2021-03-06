package draft.java.macro;

import com.github.javaparser.ast.stmt.BlockStmt;
import draft.Tokens;
import draft.java.*;
import draft.java.proto.$method;
import draft.java.proto.$stmt;

import java.lang.annotation.* ;
import java.util.List;
import java.util.function.Predicate;

/**
 * Builds a an typesEqual(Object){...} method for all non_static FIELDS on the _type
 * Works on {@link _class} and {@link _enum} {@link _type}s
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface _equals {

    Macro $ = new Macro();

    class Macro implements _macro<_type> {

        @Override
        public String toString(){
           return "macro[autoEquals]"; 
        }
        
        @Override
        public _type apply(_type _t) {
            return to(_t);
        }

        /** Select which FIELDS are being checked for the typesEqual method */
        static final Predicate<_field> FIELDS_FOR_EQUALS = f -> !f.isStatic();

        /** template statements for typesEqual based on the field TYPE */
        static $stmt $float = $stmt.of("eq = eq && Float.compare(this.$name$,test.$name$) == 0;");
        static $stmt $double = $stmt.of("eq = eq && Double.compare(this.$name$,test.$name$) == 0;");
        static $stmt $primitive = $stmt.of("eq = eq && this.$name$ == test.$name$;");
        static $stmt $arrayOfPrimitives = $stmt.of("eq = eq && java.util.Arrays.equals(this.$name$,test.$name$);");
        static $stmt $arrayOfObject = $stmt.of("eq = eq && java.util.Arrays.deepEquals(this.$name$,test.$name$);");
        static $stmt $default = $stmt.of("eq = eq && java.util.Objects.equals(this.$name$,test.$name$);");

        /** dummy class only used as a template parameter */
        private class $className${}

        /**
         * Get the AST for {@link #equals(Object)} below
         * and use it as a the baseline method template
          WE COMMENTD THIS OUT FOR STARTUP TIME IMPROVEMENT

        static $method $typesEqual = $method.of( Macro.class, "typesEqual" );

         public boolean equals(Object o){
         if(o == null) {
         return false;
         }
         if(this == o) {
         return true;
         }
         if(getClass() != o.getClass()){
         return false;
         }
         $className$ test = ($className$)o;
         boolean eq = true;
         $callSuperEquals: {eq = super.equals( test );}
         $body:{}
         return eq;
         }
         */

        static $method $equals = $method.of(
            "public boolean equals(Object o){",
            "if(o == null) {",
            "   return false;",
            "}",
            "if(this == o) {",
            "    return true;",
            "}",
            "if(getClass() != o.getClass()){",
            "    return false;",
            "}",
            "$className$ test = ($className$)o;",
            "boolean eq = true;",
            "$callSuperEquals: {eq = super.equals( test );}",
            "$BODY:{}",
            "return eq;",
            "}");


        public static <T extends _type> T to( T _t ){
            if( _t instanceof _class ) {
                _class _c = (_class)_t;

                Tokens ts = new Tokens();
                ts.put("className", _c.getName());
                if( _c.hasExtends() ){
                    //ts.put("$callSuperEquals", true);
                    ts.put("callSuperEquals", true);
                }
                //_1_build the BODY of statements for checking the FIELDS
                // then update the $typesEqual method template with the code
                // by populating the $BODY{}
                BlockStmt body = new BlockStmt();

                List<_field> _fs = _t.listFields(FIELDS_FOR_EQUALS);

                _fs.forEach(f-> {
                    if( f.isType(float.class) ){
                        body.addStatement( $float.fill(f.getName()));
                    }else if( f.isType(double.class)){
                        body.addStatement($double.fill(f.getName()));
                    }else if( f.isPrimitive() ){
                        body.addStatement($primitive.fill(f.getName()));
                    }else{
                        if( f.isArray()){
                            if( f.getType().getElementType().isPrimitiveType() ){
                                body.addStatement($arrayOfPrimitives.fill(f.getName()));
                            } else {
                                body.addStatement($arrayOfObject.fill(f.getName()));
                            }
                        }else {
                            body.addStatement($default.fill(f.getName()));
                        }
                    }
                });
                //System.out.println(">>>>> THE BODY IS " + body );
                //ts.put("$BODY", body);
                ts.put("BODY", body);
                _c.method( $equals.construct(ts) );
            }
            return _t;
        }
    }
}

