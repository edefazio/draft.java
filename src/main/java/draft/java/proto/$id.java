package draft.java.proto;

import draft.*;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * Identifier that can be "simple" i.e. "Map"
 * or fully qualified "java.util.Map"
 * 
 */
public class $id {

    public static $id any(){
        return of("$any$");
    }
    
    public static $id of(Predicate<String> constraint){
        return any().constraint(constraint);
    }
    
    public static $id of( String id ){
        return new $id( id );
    }
    
    /** the pattern of the id*/
    public Stencil pattern;

    /**  */
    public Predicate<String> constraint = t -> true;

    /**
     * 
     * @return 
     */
    public boolean isMatchAny(){
        if( this.pattern.isMatchAny()) {
            try{
                return this.constraint.test( null );
            } catch(Exception e){
                return false;
            }
        }
        return false;
    }
    
    /**
     * 
     * @param pattern
     */
    public $id(String pattern) {
        this(pattern, t->true);
    }

    public $id(String pattern, Predicate<String> constraint) {
        this.pattern = Stencil.of(pattern);
        this.constraint = constraint;
    }

    /**
     * SETS or OVERRIDES the constraint
     *
     * @param constraint
     * @return
     */
    public $id constraint(Predicate<String> constraint) {
        this.constraint = constraint;
        return this;
    }

    /**
     *
     * @param constraint
     * @return
     */
    public $id addConstraint(Predicate<String> constraint) {
        this.constraint = this.constraint.and(constraint);
        return this;
    }
   
    public $id $(String target, String $Name){
        this.pattern = this.pattern.$(target, $Name);
        return this;
    }
    
    public $id hardcode$( Translator tr, Object...keyValues){
        this.pattern = this.pattern.hardcode$(tr, keyValues);
        return this;
    }
    
    public List<String> list$() {
        return this.pattern.list$();
    }

    public List<String> list$Normalized() {
        return this.pattern.list$Normalized();
    }

    public String compose(Translator t, Map<String, Object> keyValues) {
        return this.pattern.construct(t, keyValues);
    }

    public boolean matches(String t) {
        return decompose(t) != null;
    }

    public Tokens decompose(String t) {

        if (t == null) {
            /**
             * Null is allowed IF and ONLY If the Stencil $form isMatchAll
             */
            if (pattern.isMatchAny()) {
                return Tokens.of(pattern.list$().get(0), "");
            }
            //System.out.println( "EXPECTED NOT NULL ");
            return null;
        }
        if( constraint.test( t ) ) {       
            //System.out.println( "PASED NAME CONSTRAINT");
            int idx = t.lastIndexOf(".");
            if( idx > 0 ){ //input is fully qualified id
                //if the pattern is NOT fully qualified
                if( !this.pattern.getTextBlanks().getFixedText().contains(".") ){ 
                    String oldPattern = this.pattern.toString();
                    String newPattern = oldPattern.substring(oldPattern.lastIndexOf(".")+1);
                    //System.out.println( "NEW PATTERN "+ newPattern);
                    //System.out.println( "NORMALIZED "+ normalize(t));
                    Tokens ts = Stencil.of(newPattern).deconstruct(normalize(t));
                    //System.out.println( "TOKENS "+ ts );
                    return ts;
                    //need to create a non-fully qualified Stencil
                    //TextBlanks tb = this.pattern.getTextBlanks();
                    //String ft = tb.getFixedText();
                    //this.pattern.toString()
                    //String normal = normalize(t);
                    //return pattern.deconstruct(normal);
                }
            }            
            //if neither or both are 
            return pattern.deconstruct( t );
        }
        return null;
    }

    public final String normalize( String str ){
        int idx = str.lastIndexOf(".");
        if( idx > 0 ){
            return str.substring(idx+1);
        }
        return str;
    }
    
    public $proto.$args decomposeTo(String t, $proto.$args args) {
        if (args == null) {
            return null;
        }
        Tokens ts = decompose(t);
        if (ts != null) {
            if (args.isConsistent(ts)) {
                args.putAll(ts);
                return args;
            }
        }
        return null;
    }

    public Tokens decomposeTo(String t, Tokens all) {
        if (all == null) {
            /* Skip decompose if the tokens already null*/
            return null;
        }

        Tokens ts = decompose(t);

        if (ts != null) {
            if (all.isConsistent(ts)) {
                all.putAll(ts);
                return all;
            }
        }
        return null;
    }
}
