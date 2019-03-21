package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import draft.*;
import draft.java.Expr;
import draft.java.Walk;
import draft.java._anno;
import draft.java._model;
import draft.java._model._node;
import java.lang.annotation.Annotation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Template for an {@link _anno}
 *
 */
public final class $anno
    implements Template<_anno>, $query<_anno> {

    /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N rootNode, String source ){
        return $anno.of(source).listIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N rootNode, _anno source ){
        return $anno.of(source).listIn(rootNode);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param rootNode
     * @param source
     * @return the modified N
     */
    public static final <N extends _node> N remove( N rootNode, _anno source ){
        return $anno.of(source).removeIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @return 
     */
    public static final <N extends _node> N remove( N rootNode, String source ){
        return $anno.of(source).removeIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @param target
     * @return 
     */
    public static final <N extends _node> N replace(N rootNode, _anno source, _anno target){
        return $anno.of(source)
            .replaceIn(rootNode, $anno.of(target));
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @param target
     * @return 
     */
    public static final <N extends _node> N replace(N rootNode, String source, String target){
        return $anno.of(source)
            .replaceIn(rootNode, $anno.of(target));
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param source
     * @param target
     * @return 
     */
    public static final <N extends _node> N replace(N rootNode, AnnotationDeclaration source, AnnotationDeclaration target){
        return $anno.of(_anno.of(source))
            .replaceIn(rootNode, $anno.of(_anno.of(target)));
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param sourceAnno
     * @param targetAnno
     * @return 
     */
    public static final <N extends _node> N replace( 
        N rootNode, Class<? extends Annotation>sourceAnno, 
        Class<? extends Annotation>targetAnno){
        
        return $anno.of(sourceAnno)
            .replaceIn(rootNode, $anno.of(targetAnno));
    }
    
    public static $anno of( String code){
        return of( new String[]{code} );
    }
    
    public static $anno of( String code, Predicate<_anno> constraint){
        return of( new String[]{code} ).constraint(constraint);
    }
    
    public static $anno of( String...code){
        _anno _a = _anno.of( code );
        return new $anno( _a.toString().trim() ); //, _a.ast().getClass() );
    }

    public static $anno of( _anno _a ){
        return new $anno( _a.toString().trim() ); 
    }
    
    public static $anno of( _anno _a, Predicate<_anno> constraint ){
        return new $anno( _a.toString().trim() ).constraint(constraint);
    }
    
    public static $anno of( Object anonymousObjectWithAnnotation ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject( ste );
        NodeList<BodyDeclaration<?>> bds = oce.getAnonymousClassBody().get();
        BodyDeclaration bd = bds.stream().filter(b -> b.getAnnotations().isNonEmpty() ).findFirst().get();
        return of( _anno.of(bd.getAnnotation(0) ) );        
    }
    
    public static $anno of( Class<? extends Annotation> a){
        return of( _anno.of(a) );
    }
    
    /** An additional Match constraint (By default always true) */
    public Predicate<_anno> constraint = a-> true;
    
    public Stencil annoStencil;

    private $anno( String stencil) {
        this.annoStencil = Stencil.of(stencil );
    }

    public $anno constraint( Predicate<_anno> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    public boolean matches( String...annotation ){
        return matches(_anno.of(annotation));
    }

    public boolean matches( AnnotationExpr expression ){
        return deconstruct( expression ) != null;
    }

    public boolean matches( _anno _a){
        return deconstruct( _a ) != null;
    }

    /**
     * Deconstruct the expression into tokens, or return null if the statement doesnt match
     *
     * @param _a
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public Tokens deconstruct(_anno _a ){
        if( this.constraint.test(_a) ){
            return annoStencil.deconstruct( _a.toString() );
        }
        return null;
    }

    /**
     * Deconstruct the expression into tokens, or return null if the statement doesnt match
     *
     * @param a
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public Tokens deconstruct(AnnotationExpr a ){
        return deconstruct( _anno.of(a) );
    }

    @Override
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
        Tokens ts = this.deconstruct(e);
        if( ts != null){
            return new Select( e, ts );
        }
        return null;
    }

    @Override
    public List<_anno> listIn(_model._node _t ){
        return listIn( _t.ast() );
    }

    @Override
    public List<_anno> listIn(Node rootNode ){
        List<_anno> annosList = new ArrayList<>();
        rootNode.walk(AnnotationExpr.class, t->{
            if( this.matches(t) ){
                annosList.add(_anno.of(t));
            }
        } );
        return annosList;
    }

    @Override
    public List<Select> listSelectedIn(Node n ){
        List<Select>sts = new ArrayList<>();
        n.walk(AnnotationExpr.class, e-> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    @Override
    public List<Select> listSelectedIn(_model._node _m ){
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
    @Override
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
    @Override
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
     * Replace all occurrences of the template in the code with the replacement
     * (composing the replacement from the constructed tokens in the source)
     *
     * @param _m the model to find replacements
     * @param $a the template to be constructed as the replacement
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

    @Override
    public <N extends Node> N forIn(N node, Consumer<_anno> _annoActionFn){
        node.walk(AnnotationExpr.class, e-> {
            Tokens tokens = deconstruct( e );
            if( tokens != null ){
                _annoActionFn.accept( _anno.of(e));
            }
        });
        return node;
    }

    @Override
    public <M extends _model._node> M forIn(M _m, Consumer<_anno> _annoActionFn){
        Walk.in( _m, AnnotationExpr.class, e -> {
            Tokens tokens =  deconstruct( e );
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
        @Override
        public String toString(){
            return "$anno.Select {"+ System.lineSeparator()+
                    Text.indent( expression.toString() )+ System.lineSeparator()+
                    Text.indent( "TOKENS : " + tokens) + System.lineSeparator()+
                    "}";
        }
    }
}
