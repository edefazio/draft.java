package draft.java.template;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.Type;
import draft.*;
import draft.java.Ast;
import draft.java.Walk;
import draft.java._model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Template for a Java Type
 *
 * @param <T> the underlying Expression TYPE
 */
public final class $typeRef<T extends Type>
        implements Template<T>, $query<T>{

    public static $typeRef of(String code ){
        return new $typeRef(Ast.typeRef(code));
    }

    public static $typeRef of( Class typeClass ){
        return of( Ast.typeRef(typeClass) );
    }

    public static $typeRef of(Type t ){
        return new $typeRef( t );
    }

    public static $typeRef booleanType = of(PrimitiveType.booleanType());
    public static $typeRef charType = of(PrimitiveType.charType());
    public static $typeRef byteType = of(PrimitiveType.byteType());
    public static $typeRef shortType = of(PrimitiveType.shortType());
    public static $typeRef intType = of(PrimitiveType.intType());
    public static $typeRef longType = of(PrimitiveType.longType());
    public static $typeRef floatType = of(PrimitiveType.floatType());
    public static $typeRef doubleType = of(PrimitiveType.doubleType());


    public Class<T> typeClass;
    public Stencil stencil;

    public $typeRef(T ex){
        this.typeClass = (Class<T>)ex.getClass();
        this.stencil = Stencil.of( ex.toString() );
    }

    public $typeRef(Class<T>expressionClass, String stencil ){
        this.typeClass = expressionClass;
        this.stencil = Stencil.of(stencil);
    }

    public T fill(Object...values){
        String str = stencil.fill(Translator.DEFAULT_TRANSLATOR, values);
        return (T)Ast.typeRef( str );
    }

    public $typeRef $(String target, String $name ) {
        this.stencil = this.stencil.$(target, $name);
        return this;
    }

    public $typeRef $(Expression e, String $name){
        this.stencil = this.stencil.$(e.toString(), $name);
        return this;
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $typeRef assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $typeRef assign$( Object... keyValues ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, Tokens.of( keyValues ) );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param translator translates values to be hardcoded into the Stencil
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified $field
     */
    public $typeRef assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    public $typeRef assign$( Translator translator, Tokens kvs ) {
        this.stencil.assign$(translator,kvs);
        return this;
    }


    public T fill(Translator t, Object...values){
        return (T)Ast.typeRef( stencil.fill(t, values));
    }

    public T construct( Object...keyValues ){
        return (T)Ast.typeRef( stencil.construct( Tokens.of(keyValues)));
    }

    public T compose( _model._node model ){
        return (T)construct(model.componentize());
    }

    public T construct( Translator t, Object...keyValues ){
        return (T)Ast.typeRef( stencil.construct( t, Tokens.of(keyValues) ));
    }

    public T construct( Map<String,Object> tokens ){
        return (T)Ast.typeRef(stencil.construct( Translator.DEFAULT_TRANSLATOR, tokens ));
    }

    public T construct( Translator t, Map<String,Object> tokens ){
        return (T)Ast.typeRef(stencil.construct( t, tokens ));
    }

    public boolean matches( String type ){
        return decompose( Ast.typeRef(type)) != null;
    }

    public boolean matches( Type type ){
        return decompose(type) != null;
    }

    public List<String> list$(){
        return this.stencil.list$();
    }

    public List<String> list$Normalized(){
        return this.stencil.list$Normalized();
    }

    /**
     * Decompose the expression into tokens, or return null if the statement doesnt match
     *
     * @param t TYPE instance
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public Tokens decompose( Type t ){
        if( typeClass.isAssignableFrom(t.getClass())){
            return stencil.decompose( t.toString() );
        }
        return null;
    }

    public Select select( Type t){
        Tokens ts = this.decompose(t);
        if( ts != null){
            return new Select( t, ts );
        }
        return null;
    }

    public List<Select<T>> selectAllIn(Node n ){
        List<Select<T>>sts = new ArrayList<>();
        n.walk(this.typeClass, e-> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    public List<Select<T>> selectAllIn(_model._node _t ){
        return selectAllIn( _t.ast() );
    }

    public <M extends _model._node> M forSelectedIn(M _le, Consumer<Select> selectConsumer ){
        Walk.in( _le, this.typeClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return _le;
    }

    public <N extends Node> N forSelectedIn(N n, Consumer<Select> selectConsumer ){
        n.walk(this.typeClass, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return n;
    }

    public List<T> findAllIn(_model._node _t ){
        return findAllIn( _t.ast() );
    }

    public List<T> findAllIn(Node rootNode ){
        List<T> typesList = new ArrayList<>();
        rootNode.walk(this.typeClass, t->{
            if( this.matches(t) ){
                typesList.add(t);
            }
        } );
        return typesList;
    }



    public <M extends _model._node> M replaceIn(M _t, Class replacementType){
        return replaceIn( _t, $typeRef.of(replacementType));
    }

    /**
     * Find and replaceIn
     * @param _t
     * @param replacementType
     * @param <M>
     * @return
     */
    public <M extends _model._node> M replaceIn(M _t, Type replacementType){
        return replaceIn( _t, $typeRef.of(replacementType));
    }

    /**
     * Find and replaceIn the TYPE
     *
     * <PRE>
     * for example:
     * _class _c = _class.of(MyType.class);
     * //replaceIn all instances of TreeSet with HashSet within the code
     * _c = $typeRef.of("TreeSet<$key$>").replaceIn(_c, $typeRef.of("HashSet<$key$>"));
     * </PRE>
     *
     * @param _t
     * @param $replacementType the template for the replacement TYPE
     * @param <M>
     * @return
     */
    public <M extends _model._node> M replaceIn(M _t, $typeRef $replacementType){
        Walk.in(_t, this.typeClass, e -> {
            Tokens tokens = this.stencil.decompose( e.toString());
            if( tokens != null ){
                if( !e.replace($replacementType.construct(tokens))){
                    throw new DraftException("unable to replaceIn "+ e + " in "+ _t+" with "+$replacementType);
                }
            }
        });
        return _t;
    }

    public <N extends Node> N removeIn(N node ){
        node.walk(this.typeClass, e -> {
            Tokens tokens = this.stencil.decompose( e.toString());
            if( tokens != null ){
                e.removeForced();
            }
        });
        return node;
    }

    public <M extends _model._node> M removeIn(M _t ){
        Walk.in( _t, this.typeClass, e -> {
            Tokens tokens = this.stencil.decompose( e.toString());
            if( tokens != null ){
                e.removeForced();
            }
        });
        return _t;
    }

    public <N extends Node> N forAllIn(N n, Consumer<T> expressionActionFn){
        n.walk(this.typeClass, e-> {
            Tokens tokens = this.stencil.decompose( e.toString());
            if( tokens != null ){
                expressionActionFn.accept( e);
            }
        });
        return n;
    }

    public <M extends _model._node> M forAllIn(M _t, Consumer<T> expressionActionFn){
        Walk.in( _t, this.typeClass, e -> {
            Tokens tokens = this.stencil.decompose( e.toString());
            if( tokens != null ){
                expressionActionFn.accept( e);
            }
        });
        return _t;
    }

    public String toString() {
        return "(" + this.typeClass.getSimpleName() + ") : \"" + this.stencil + "\"";
    }

    public static class Select<T extends Type> implements $query.selected {
        public T type;
        public Tokens tokens;

        public Select( T type, Tokens tokens){
            this.type = type;
            this.tokens = tokens;
        }
        public String toString(){
            return "$typeRef.Select {"+ System.lineSeparator()+
                    Text.indent( type.toString() )+ System.lineSeparator()+
                    Text.indent( "TOKENS : " + tokens) + System.lineSeparator()+
                    "}";
        }
    }
}
