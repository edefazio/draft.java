package draft.java.template;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import draft.*;
import draft.java.Expr;
import draft.java.Walk;
import draft.java._anno;
import static draft.java._anno.of;
import draft.java._model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Template for an {@link _anno}
 *
 */
public final class $anno
        implements Template<_anno>, $query<_anno> {

    public static $anno of( String code){
        return of( new String[]{code} );
    }
    
    public static $anno of( String...code){
        _anno _a = _anno.of( code );
        return new $anno( _a.toString().trim() ); //, _a.ast().getClass() );
    }

    
    public static $anno of( Object anonymousObjectWithAnnotation ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousClass( ste );
        NodeList<BodyDeclaration<?>> bds = oce.getAnonymousClassBody().get();
        BodyDeclaration bd = bds.stream().filter(b -> b.getAnnotations().isNonEmpty() ).findFirst().get();
        return of( _anno.of(bd.getAnnotation(0) ) );        
    }
    
    public static $anno of( _anno _a){
        return new $anno( _a.toString() ); //, _a.ast().getClass() );
    }

    private Stencil annoStencil;

    private $anno( String stencil) {
        this.annoStencil = Stencil.of(stencil );
    }

    public boolean matches( String...annotation ){
        return matches(_anno.of(annotation));
    }

    public boolean matches( AnnotationExpr expression ){
        return annoStencil.deconstruct( expression.toString() ) != null;
    }

    public boolean matches( _anno _a){
        return annoStencil.deconstruct( _a.toString() ) != null;
    }

    /**
     * Decompose the expression into tokens, or return null if the statement doesnt match
     *
     * @param _a
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public Tokens decompose(_anno _a ){
        return annoStencil.deconstruct( _a.toString() );
    }

    /**
     * Decompose the expression into tokens, or return null if the statement doesnt match
     *
     * @param a
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public Tokens decompose(AnnotationExpr a ){
        return annoStencil.deconstruct( a.toString() );
    }



    public String toString() {
        return "($anno) : \"" + this.annoStencil + "\"";
    }

    @Override
    public _anno construct(Translator translator, Map<String, Object> keyValues) {
        return _anno.of(annoStencil.construct(translator, keyValues));
    }

    @Override
    public _anno construct(Map<String, Object> keyValues) {
        return _anno.of(annoStencil.construct(Translator.DEFAULT_TRANSLATOR, keyValues));
    }

    @Override
    public _anno construct(Object... keyValues) {
        return _anno.of( annoStencil.construct(Translator.DEFAULT_TRANSLATOR, keyValues));
    }

    @Override
    public _anno construct(Translator translator, Object... keyValues) {
        return _anno.of(annoStencil.construct(translator, keyValues));
    }

    @Override
    public _anno fill(Object... values) {
        return _anno.of( annoStencil.fill(Translator.DEFAULT_TRANSLATOR, values));
    }

    @Override
    public _anno fill(Translator translator, Object... values) {
        return _anno.of( annoStencil.fill(translator, values));
    }

    @Override
    public $anno $(String target, String $Name) {
        this.annoStencil = this.annoStencil.$(target, $Name);
        return this;
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $anno assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $anno assign$( Object... keyValues ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, Tokens.of( keyValues ) );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param translator translates values to be hardcoded into the Stencil
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public $anno assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    public $anno assign$( Translator translator, Tokens kvs ) {
        this.annoStencil = this.annoStencil.assign$(translator,kvs);
        return this;
    }

    @Override
    public List<String> list$() {
        return this.annoStencil.list$();
    }

    @Override
    public List<String> list$Normalized() {
        return this.annoStencil.list$Normalized();
    }

    public Select select(_anno _a){
        return select( _a.ast() );
    }

    public Select select(AnnotationExpr e){
        Tokens ts = this.decompose(e);
        if( ts != null){
            return new Select( e, ts );
        }
        return null;
    }

    public List<_anno> findAllIn(_model._node _t ){
        return findAllIn( _t.ast() );
    }

    public List<_anno> findAllIn(Node rootNode ){
        List<_anno> typesList = new ArrayList<>();
        rootNode.walk(AnnotationExpr.class, t->{
            if( this.matches(t) ){
                typesList.add(_anno.of(t));
            }
        } );
        return typesList;
    }

    public List<Select> selectAllIn(Node n ){
        List<Select>sts = new ArrayList<>();
        n.walk(AnnotationExpr.class, e-> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    public List<Select> selectAllIn(_model._node _m ){
        List<Select>sts = new ArrayList<>();
        Walk.in( _m, AnnotationExpr.class, e -> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    /**
     * Remove all matching occurrences of the template in the node and return the
     * modified node
     * @param node the root node to start search
     * @param <N> the input node TYPE
     * @return the modified node
     */
    public <N extends Node> N removeIn(N node ){
        node.walk( AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.expression.removeForced();
            }
        });
        return node;
    }

    /**
     *
     * @param _m the root model node to start from
     * @param <M> the TYPE of model node
     * @return the modified model node
     */
    public <M extends _model._node> M removeIn(M _m ){
        Walk.in( _m, AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.expression.removeForced();
            }
        });
        return _m;
    }

    /**
     * Replace all occurences of the template in the code with the replacement
     * (composing the replacement from the decomposed tokens in the source)
     *
     * @param _m the model to find replacements
     * @param $a the template to be composed as the replacement
     * @param <M> the TYPE of model
     * @return
     */
    public <M extends _model._node> M replaceIn(M _m, $anno $a ){
        Walk.in(_m, AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.expression.replace($a.construct(sel.tokens).ast() );
            }
        });
        return _m;
    }

    public <N extends Node> N replaceIn(N node, $anno $a ){
        node.walk(AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.expression.replace($a.construct(sel.tokens).ast() );
            }
        });
        return node;
    }

    public <M extends _model._node> M forSelectedIn(M _m, Consumer<Select> selectConsumer ){
        Walk.in( _m, AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return _m;
    }

    public <N extends Node> N forSelectedIn(N node, Consumer<Select> selectConsumer ){
        node.walk(AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return node;
    }

    public <N extends Node> N forAllIn(N node, Consumer<_anno> _annoActionFn){
        node.walk(AnnotationExpr.class, e-> {
            Tokens tokens = this.annoStencil.deconstruct( e.toString());
            if( tokens != null ){
                _annoActionFn.accept( _anno.of(e));
            }
        });
        return node;
    }

    public <M extends _model._node> M forAllIn(M _m, Consumer<_anno> _annoActionFn){
        Walk.in( _m, AnnotationExpr.class, e -> {
            Tokens tokens = annoStencil.deconstruct( e.toString());
            if( tokens != null ){
                _annoActionFn.accept( _anno.of(e) );
            }
        });
        return _m;
    }

    public static class Select implements $query.selected {
        public AnnotationExpr expression;
        public Tokens tokens;

        public Select( AnnotationExpr expression, Tokens tokens){
            this.expression = expression;
            this.tokens = tokens;
        }
        public String toString(){
            return "$anno.Select {"+ System.lineSeparator()+
                    Text.indent( expression.toString() )+ System.lineSeparator()+
                    Text.indent( "TOKENS : " + tokens) + System.lineSeparator()+
                    "}";
        }
    }
}
