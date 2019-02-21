package draft.java.macro;

import com.github.javaparser.ast.stmt.*;
import draft.Tokens;
import draft.java.*;
import draft.java.template.*;

import java.lang.annotation.*;
import java.util.function.Predicate;

/**
 * Builds a hashCode for all non-static FIELDS on the {@link _class}
 * optionally calls super.hashCode() if the class extends something
 * other than Object.
 *
 * can be applied:
 * <PRE>
 * <OL>
 * <LI> via annotation : using the annotation _macro processor {@link _macro#to(Class)}
 * @_autoHashCode class A{ int x,y,z; }
 * _class _c = $$.to(A.class);
 *
 * <LI> via _class constructor argument : pass the {@link _autoHashCode#$} instance in
 * class A{ int x,y,z; }
 * _class _c = _class.of(A.class, _autoHashCode.$);
 *
 * <LI> via the {@link _type#apply(_macro[])} method
 * class A{ int x,y,z; }
 * _class _c = _class.of(A.class)).apply(_autoHashCode.$);
 *
 * <LI> via external call: pass the _class in to the {@link _autoHashCode.Macro#to(_type)}
 * class A{ int x,y,z; }
 * _class _c = _autoHashCode.Macro.to(_class.of(A.class));
 * </OL>
 * </PRE>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface _autoHashCode {

    /** Static Instance */
     Macro $ = new Macro();

     /**
      * method template for hashCode with PARAMETERS:
      * <UL>
      * <LI>$seed$ and $prime$ are prime integers populated into the code as initial values
      * <LI>callSuperHashCode: is a boolean which will write (or hide) code for calling the super class
      * <LI>BODY:{} is a BlockStmt of one or more {@link Statement}s for processing each field for the hashcode
      * </UL>
      */
     $method $HASHCODE = $method.of(
         "public int hashCode( ){",
         "    int hash = $seed$;",
         "    int prime = $prime$;",
         "    callSuperHashCode: hash = hash * prime + super.hashCode();",
         "    body:{}",
         "    return hash;",
         "}");

     /*
     NOTE COMMENTED THIS OUT FOR STARTUP PERFORMANCE

     i.e we need to read the .java source from a file on startup

     $method $HASHCODE = $method.of(new Object() {
        int $seed$, $prime$;

        public int hashCode( ){
            int hash = $seed$;
            int prime = $prime$;
            callSuperHashCode: hash = hash * prime + super.hashCode();
            body:{}
            return hash;
        }
     });
     */

    /** Primes used by seeding and factoring in the hashCode method*/
    int[] PRIMES = {
            3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, 103,
            107, 109, 113, 127, 131, 137, 139, 149, 151, 157, 163, 167, 173, 179, 181, 191, 193, 197, 199, 211, 223,
            227, 229, 233, 239, 241, 251, 257, 263, 269, 271
    };

    /** Filters out which FIELDS need to be part of the hashCode calculation*/
    Predicate<_field> HASH_CODE_FIELD_MATCH_FN = f-> !f.isStatic();

     /**
      * Building the {@link Statement} to make up the BODY of the hashCode
      * for all {@link _field}s depending on the {@link _field}s TYPE
      */
     @Ast.cache
     class _fieldToStatement {
         private static Integer prime, hash;

         public static $stmt $default = $stmt.of( "hash = hash * prime + java.util.Objects.hashCode($name$);");
         public static $stmt $arrayOfPrimitives = $stmt.of( "hash = hash * prime + java.util.Arrays.hashCode($name$);");
         public static $stmt $arrayOfObject = $stmt.of( "hash = hash * prime + java.util.Arrays.deepHashCode($name$);");

         public static $stmt $boolean = $stmt.of( "hash = hash * prime + ($name$ ? 1 : 0 );" );
         public static $stmt $float = $stmt.of( "hash = hash * prime + Float.floatToIntBits($name$);" );
         public static $stmt $double = $stmt.of(
                 "hash = hash * prime + (int)(Double.doubleToLongBits($name$)^(Double.doubleToLongBits($name$) >>> 32));");
         public static $stmt $long = $stmt.of("hash = hash * prime + (int)($name$ ^ ($name$ >>> 32));");
         public static $stmt $simplePrimitive = $stmt.of("hash = hash * prime + $name$;");

         /* REMOVED FOR STARTUP PERFORMANCE (USE ABOVE INSTEAD)
         public static $stmt $default = $stmt.of( (Object $name$) -> hash = hash * prime + java.util.Objects.hashCode($name$));
         public static $stmt $arrayOfPrimitives = $stmt.of( (int[] $name$) -> hash = hash * prime + java.util.Arrays.hashCode($name$));
         public static $stmt $arrayOfObject = $stmt.of( (Object[] $name$) -> hash = hash * prime + java.util.Arrays.deepHashCode($name$));

         public static $stmt $boolean = $stmt.of( (Boolean $name$) -> hash = hash * prime + ($name$ ? 1 : 0 ) );
         public static $stmt $float = $stmt.of( (Float $name$) -> hash = hash * prime + Float.floatToIntBits($name$) );
         public static $stmt $double = $stmt.of( (Double $name$) ->
                 hash = hash * prime + (int) (Double.doubleToLongBits($name$) ^ (Double.doubleToLongBits($name$) >>> 32)));
         public static $stmt $long = $stmt.of( (Long $name$) ->  hash = hash * prime + (int)($name$ ^ ($name$ >>> 32)));
         public static $stmt $simplePrimitive = $stmt.of( (Byte $name$) -> hash = hash * prime + $name$ );
         */

         public static Statement composeStmt(_field _f){
            if( _f.getType().isArray() ){
                if( _f.getType().getElementType().isPrimitiveType()){
                    return $arrayOfPrimitives.compose(_f);
                }
                return $arrayOfObject.compose(_f);
            }
            if( _f.getType().isPrimitive()){
                if( _f.isType(boolean.class)){
                    return $boolean.compose(_f);
                }
                if( _f.isType(double.class)){
                    return $double.compose(_f);
                }
                if( _f.isType(float.class)){
                    return $float.compose(_f);
                }
                if( _f.isType(long.class)){
                    return $long.compose(_f);
                }
                return $simplePrimitive.compose(_f);
            }
            return $default.compose(_f);
        }
    }

    class Macro implements _macro<_type> {
         @Override
         public _type apply(_type _t) {
             return to(_t);
         }

         public static <T extends _type> T to(T _t){
            if( _t instanceof _class){
                _class _c = (_class)_t;
                Tokens tokens = new Tokens(); /** tokens for the {@link $HASHCODE} template */

                int prime = PRIMES[Math.abs(_c.getFullName().hashCode()) % PRIMES.length];
                tokens.put("prime",prime);
                tokens.put("seed",PRIMES[Math.abs(prime - _c.listFields(HASH_CODE_FIELD_MATCH_FN).size()) % PRIMES.length]);

                if( _c.hasExtends() && !_c.isExtends(Object.class)){ //if _class extends something other than Object
                    tokens.put("callSuperHashCode", true); /** print the code at "callSuperEquals" in {@link #$HASHCODE} */
                }
                BlockStmt body = new BlockStmt();
                //compose Statements for all FIELDS into the BODY BlockStmt
                _c.forFields(HASH_CODE_FIELD_MATCH_FN, f-> body.addStatement(_fieldToStatement.composeStmt(f)));
                tokens.put("body", body); //the body:{} will be replaced with the code in the BlockStmt
                _c.method($HASHCODE.construct(tokens));
            }
            return _t;
        }
    }
}

