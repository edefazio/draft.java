package draft.java.proto;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import draft.*;
import draft.java.*;
import draft.java._model._node;
import java.lang.annotation.Annotation;

import java.util.*;
import java.util.function.*;

/**
 * Template for an {@link _anno}
 * provides:
 * $anno.first( _c, "");
 * 
 */
public final class $anno
    implements Template<_anno>, $query<_anno> {

    /**
     * 
     * @param <N>
     * @param rootNode
     * @param proto
     * @return 
     */
    public static final <N extends _node> _anno first( N rootNode, String proto ){
        return $anno.of(proto).firstIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @return 
     */
    public static final <N extends _node> _anno first( Node astNode, String proto ){
        return $anno.of(proto).firstIn(astNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _anno first( N rootNode, String proto, Predicate<_anno> constraint){
        return $anno.of(proto, constraint).firstIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _anno first( Node astNode, String proto,Predicate<_anno>constraint){
        return $anno.of(proto, constraint).firstIn(astNode);
    }
       
    //?? TODO add constraint parameter at end (make it a vararg?)
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param rootNode
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N rootNode, String proto ){
        return $anno.of(proto).listIn(rootNode);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param rootNode
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N rootNode, String proto, Predicate<_anno> constraint){
        return $anno.of(proto, constraint).listIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param _proto
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N rootNode, _anno _proto ){
        return $anno.of(_proto).listIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param _proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N rootNode, _anno _proto, Predicate<_anno> constraint){
        return $anno.of(_proto, constraint).listIn(rootNode);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param rootNode
     * @param _proto
     * @return the modified N
     */
    public static final <N extends _node> N remove( N rootNode, _anno _proto ){
        return $anno.of(_proto).removeIn(rootNode);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param rootNode
     * @param _proto
     * @param constraint
     * @return the modified N
     */
    public static final <N extends _node> N remove( N rootNode, _anno _proto, Predicate<_anno> constraint){
        return $anno.of(_proto, constraint).removeIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param proto
     * @return 
     */
    public static final <N extends _node> N remove( N rootNode, String proto ){
        return $anno.of(proto).removeIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> N remove( N rootNode, String proto, Predicate<_anno> constraint){
        return $anno.of(proto, constraint).removeIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param sourceProto
     * @param targetProto
     * @return 
     */
    public static final <N extends _node> N replace(N rootNode, _anno sourceProto, _anno targetProto){
        return $anno.of(sourceProto)
            .replaceIn(rootNode, $anno.of(targetProto));
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param sourceProto
     * @param targetProto
     * @return 
     */
    public static final <N extends _node> N replace(N rootNode, String sourceProto, String targetProto){
        return $anno.of(sourceProto)
            .replaceIn(rootNode, $anno.of(targetProto));
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param astSourceProto
     * @param astTargetProto
     * @return 
     */
    public static final <N extends _node> N replace(N rootNode, AnnotationDeclaration astSourceProto, AnnotationDeclaration astTargetProto){
        return $anno.of(_anno.of(astSourceProto))
            .replaceIn(rootNode, $anno.of(_anno.of(astTargetProto)));
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param sourceProto
     * @param targetProto
     * @return 
     */
    public static final <N extends _node> N replace( 
        N rootNode, Class<? extends Annotation>sourceProto, 
        Class<? extends Annotation>targetProto){
        
        return $anno.of(sourceProto)
            .replaceIn(rootNode, $anno.of(targetProto));
    }
    
    public static $anno of( String code){
        return of( new String[]{code} );
    }
    
    public static $anno of( String code, Predicate<_anno> constraint){
        return of( new String[]{code} ).constraint(constraint);
    }
    
    public static $anno of( String...code){
        _anno _a = _anno.of( code );
        return new $anno( _a.toString().trim() );
    }

    public static $anno of( _anno _a ){
        return new $anno( _a.toString().trim() ); 
    }
    
    public static $anno of( _anno _a, Predicate<_anno> constraint ){
        return new $anno( _a.toString().trim() ).constraint(constraint);
    }
    
    /**
     * 
     * @param anonymousObjectWithAnnotation
     * @return 
     */
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

    /**
     * 
     * @param constraint
     * @return 
     */
    public $anno constraint( Predicate<_anno> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    /**
     * 
     * @param annotation
     * @return 
     */
    public boolean matches( String...annotation ){
        return matches(_anno.of(annotation));
    }

    /**
     * 
     * @param expression
     * @return 
     */
    public boolean matches( AnnotationExpr expression ){
        return deconstruct( expression ) != null;
    }

    /**
     * 
     * @param _a
     * @return 
     */
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

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
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

    /**
     * 
     * @param _a
     * @return 
     */
    public Select select(_anno _a){
        return select( _a.ast() );
    }

    /**
     * 
     * @param e
     * @return 
     */
    public Select select(AnnotationExpr e){
        Tokens ts = this.deconstruct(e);
        if( ts != null){
            return new Select( e, ts );
        }
        return null;
    }

    /**
     * Returns the first _anno that matches the pattern and constraint
     * @param _n the _java node
     * @return  the first _anno that matches (or null if none found)
     */
    public _anno firstIn( _node _n ){
        Optional<AnnotationExpr> f = _n.ast().findFirst(AnnotationExpr.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return _anno.of(f.get());
        }
        return null;
    }

    /**
     * Returns the first _anno that matches the pattern and constraint
     * @param astNode the node to look through
     * @return  the first _anno that matches (or null if none found)
     */
    public _anno firstIn( Node astNode ){
        Optional<AnnotationExpr> f = astNode.findFirst(AnnotationExpr.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return _anno.of(f.get());
        }
        return null;
    }
    
    @Override
    public List<_anno> listIn(_node _t ){
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
    public List<Select> listSelectedIn(_node _m ){
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
    public <M extends _node> M removeIn(M _m ){
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
    public <M extends _node> M replaceIn(M _m, $anno $a ){
        Walk.in(_m, AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.expression.replace($a.construct(sel.tokens).ast() );
            }
        });
        return _m;
    }
    
    /**
     * 
     * @param <N>
     * @param node
     * @param $a
     * @return 
     */
    public <N extends Node> N replaceIn(N node, $anno $a ){
        node.walk(AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.expression.replace($a.construct(sel.tokens).ast() );
            }
        });
        return node;
    }

    /**
     * 
     * @param <M>
     * @param _m
     * @param selectConsumer
     * @return 
     */
    public <M extends _node> M forSelectedIn(M _m, Consumer<Select> selectConsumer ){
        Walk.in( _m, AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return _m;
    }

    /**
     * 
     * @param <N>
     * @param node
     * @param selectConsumer
     * @return 
     */
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
    public <M extends _node> M forIn(M _m, Consumer<_anno> _annoActionFn){
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
