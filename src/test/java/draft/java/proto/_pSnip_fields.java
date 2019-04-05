package draft.java.proto;

import draft.java.proto._pSnip;
import com.github.javaparser.ast.stmt.Statement;
import draft.Tokens;
import draft.java.Expr;
import draft.java._field;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Given some FIELDS, return a block of code
 *
 * for each field, find the appropriate $snip, and _1_build code
 * to be returned
 */
public class _pSnip_fields{

    public Predicate<_field> _fieldFilter = (f) -> true;
    public List<$snip_field> $snipFields = new ArrayList<>();
    public _pSnip defaultSnip;

    public _pSnip_fields(){
        this( "");
    }

    public _pSnip_fields(String...defaultSnip){
        this.defaultSnip = _pSnip.of(defaultSnip);
    }

    public List<Statement> compose(_field _f, Object...keyValuePairs){
        Tokens ts = Tokens.of(keyValuePairs);
        decomposeField(_f, ts);
        return find$snipFor_field(_f).construct( ts );
    }

    public List<Statement> compose( _field _f, Tokens ts ){
        decomposeField(_f, ts);
        return find$snipFor_field(_f).construct( ts );
    }

    public List<Statement> compose( List<_field> _fs, Tokens ts ){
        List<_field> acceptedFields = _fs.stream().filter(_fieldFilter).collect(Collectors.toList());
        List<Statement>sts = new ArrayList<>();

        acceptedFields.forEach( f -> {
            decomposeField(f, ts);
            sts.addAll( find$snipFor_field(f).construct( ts ) );
        });
        return sts;
    }

    /*
    public $snip_fields TYPE( Class TYPE, $snip snippet ){
        this.$snipFields.add( new $snip_field(_f-> _f.isType(TYPE), snippet));
        return this;
    }

    public $snip_fields TYPE( Class TYPE, Expr.Command command ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        this.$snipFields.add( new $snip_field(_f-> _f.isType(TYPE), $snip.of( Expr.lambda(ste) )));
        return this;
    }

    public <A extends Object> $snip_fields TYPE( Class TYPE, Consumer<A> command ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        this.$snipFields.add( new $snip_field(_f-> _f.isType(TYPE), $snip.of( Expr.lambda(ste) )));
        return this;
    }

    public <A extends Object, B extends Object> $snip_fields TYPE( Class TYPE, BiConsumer<A,B> command ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        this.$snipFields.add( new $snip_field(_f-> _f.isType(TYPE), $snip.of( Expr.lambda(ste) )));
        return this;
    }

    public <A extends Object, B extends Object, C extends Object> $snip_fields TYPE( Class TYPE, Expr.TriConsumer<A,B,C> command ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        this.$snipFields.add( new $snip_field(_f-> _f.isType(TYPE), $snip.of( Expr.lambda(ste) )));
        return this;
    }

    public <A extends Object, B extends Object, C extends Object, D extends Object> $snip_fields TYPE( Class TYPE, Expr.QuadConsumer<A,B,C,D> command ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        this.$snipFields.add( new $snip_field(_f-> _f.isType(TYPE), $snip.of( Expr.lambda(ste) )));
        return this;
    }
    */
    /*
    public $snip_fields add( Predicate<_field>_fieldMatch, $snip snippet ){
        this.$snipFields.add( new $snip_field(_fieldMatch, snippet));
        return this;
    }

    public $snip_fields add( Predicate<_field>_fieldMatch, Expr.Command command ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        this.$snipFields.add( new $snip_field(_fieldMatch, $snip.of( Expr.lambda(ste) )));
        return this;
    }

    public <A extends Object> $snip_fields add( Predicate<_field>_fieldMatch, Consumer<A> command ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        this.$snipFields.add( new $snip_field(_fieldMatch, $snip.of( Expr.lambda(ste) )));
        return this;
    }

    public <A extends Object, B extends Object> $snip_fields add( Predicate<_field>_fieldMatch, BiConsumer<A,B> command ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        this.$snipFields.add( new $snip_field(_fieldMatch, $snip.of( Expr.lambda(ste) )));
        return this;
    }

    public <A extends Object, B extends Object, C extends Object> $snip_fields add( Predicate<_field>_fieldMatch, Expr.TriConsumer<A,B,C> command ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        this.$snipFields.add( new $snip_field(_fieldMatch, $snip.of( Expr.lambda(ste) )));
        return this;
    }

    public <A extends Object, B extends Object, C extends Object, D extends Object> $snip_fields add( Predicate<_field>_fieldMatch, Expr.QuadConsumer<A,B,C,D> command ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        this.$snipFields.add( new $snip_field(_fieldMatch, $snip.of( Expr.lambda(ste) )));
        return this;
    }
    */

    public _pSnip find$snipFor_field( _field _f){
        for(int i=0;i<$snipFields.size(); i++){
            if( $snipFields.get(i)._fieldMatch.test(_f) ){
                return $snipFields.get(i).snip;
            }
        }
        return defaultSnip;
    }

    public void decomposeField( _field _f, Tokens ts){
        ts.put("field", _f );
        ts.put("ANNOTATIONS", _f.getAnnos() );
        ts.put("NAME", _f.getName());
        ts.put("type", _f.getType() );
        ts.put("MODIFIERS", _f.getModifiers());
        ts.put("INIT", _f.getInit());
        ts.put("JAVADOC", _f.getJavadoc());
    }

    private static class $snip_field{
        Predicate<_field> _fieldMatch;
        _pSnip snip;

        public $snip_field(Predicate<_field>_fieldMatch, _pSnip $s){
            this._fieldMatch = _fieldMatch;
            this.snip = $s;
        }
    }
}