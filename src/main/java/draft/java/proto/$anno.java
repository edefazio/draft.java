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
  * <PRE>
 * $stmt
 * CONSTRUCT
 *     .construct([Translator], Tokens) build & return 
 *     .fill([Translator], values)
 * PARAMETERIZE
 *     .$(Tokens)
 *     .assign$([translator], target, value)
 * MATCH
 *     .constraint(Predicate<T>) //set the matching constraint
 *     .matches(Statement)
 *     .select(Statement)
 *     .deconstruct( Statement )
 * QUERY       
 *     .first/.firstIn(_node, proto) find the first matching statement in
 *     .list/.listIn(_node, proto, Predicate<>) list all matches in        
 *     .selectFirst/.selectFirstIn(_node, proto) return the first "selection" match
 *     .selectList/.selectListIn(_node, proto) return a list of selection matches
 * MODIFY
 *     .remove/.removeIn(_node, proto)
 *     .replace/.replaceIn(_node, protoTarget, protoReplacement)
 *     .forIn(_node, Consumer<T>)
 *     .forSelectedIn(_node, Consumer<T>) 
 *</PRE> 
 * 
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
         
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N rootNode, String proto ){
        return $anno.of(proto).selectFirstIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( Node astNode, String proto ){
        return $anno.of(proto).selectFirstIn(astNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N rootNode, String proto, Predicate<_anno> constraint){
        return $anno.of(proto, constraint).selectFirstIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( Node astNode, String proto,Predicate<_anno>constraint){
        return $anno.of(proto, constraint).selectFirstIn(astNode);
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
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param rootNode
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N rootNode, String proto ){
        return $anno.of(proto).selectListIn(rootNode);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param rootNode
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N rootNode, String proto, Predicate<_anno> constraint){
        return $anno.of(proto, constraint).selectListIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param _proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectlist( N rootNode, _anno _proto ){
        return $anno.of(_proto).selectListIn(rootNode);
    }
    
    /**
     * 
     * @param <N>
     * @param rootNode
     * @param _proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N rootNode, _anno _proto, Predicate<_anno> constraint){
        return $anno.of(_proto, constraint).selectListIn(rootNode);
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
    
    public static $anno of( Predicate<_anno> constraint ){
        return new $anno( "@a" ).$("@a", "amy").constraint(constraint);
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
    
    public static $anno of( Class<? extends Annotation> clazz){
        return $anno.of( a -> a.isInstance(clazz) );         
    }
    
    public static $anno of( Class<? extends Annotation> clazz, String argumentStencil ){
        if( !argumentStencil.trim().startsWith("(") ){
            argumentStencil = "("+ argumentStencil + ")";
        }
        $anno $a = $anno.of("@$annotationName$"+argumentStencil).constraint(a -> a.isInstance(clazz));
        return $a;
    }
    
    public static $anno of( Class<? extends Annotation> clazz, String argumentStencil, Predicate<_anno> constraint){
        if( !argumentStencil.trim().startsWith("(") ){
            argumentStencil = "("+ argumentStencil + ")";
        }
        $anno $a = $anno.of("@$annotationName$"+argumentStencil).constraint(a -> a.isInstance(clazz) && constraint.test(a));
        return $a;
    }
    
    /** An additional Match constraint (By default always true) */
    public Predicate<_anno> constraint = a-> true;
    
    public Stencil annoStencil;
    
    /** 
     * An "alternate" Stencil, only used for matching purposes when the _anno
     * is defined via a fully qualified path, 
     * i.e. @my.path.a()
     * verses the more simple: 
     * @a() 
     
    public Stencil fullyQualifiedStencil = null;
    */
    
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
            Tokens r = annoStencil.deconstruct( _a.toString() ); 
            if( r != null){
                return r;
            }            
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
     * @param anno
     * @return 
     */
    public Select select(String anno){
        try{
            return select( _anno.of(anno));
        }catch(Exception e){
            return null;
        }
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
     * @param _n the _java node
     * @return  the first _anno that matches (or null if none found)
     */
    public Select selectFirstIn( _node _n ){
        Optional<AnnotationExpr> f = _n.ast().findFirst(AnnotationExpr.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
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
    
    /**
     * Returns the first $anno.Select that matches the pattern and constraint
     * @param astNode the node to look through
     * @return a Select the first _anno that matches (or null if none found)
     */
    public Select selectFirstIn( Node astNode ){
        Optional<AnnotationExpr> f = astNode.findFirst(AnnotationExpr.class, s -> this.matches(s) );         
        if( f.isPresent()){
            return select(f.get());
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
    public List<Select> selectListIn(Node n ){
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
    public List<Select> selectListIn(_node _m ){
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
                sel.astAnno.removeForced();
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
                sel.astAnno.removeForced();
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
                sel.astAnno.replace($a.construct(sel.args).ast() );
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
                sel.astAnno.replace($a.construct(sel.args).ast() );
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

    /**
     * A Matched Selection result returned from matching a prototype $anno
     * inside of some Node or _node
     */
    public static class Select 
        implements $query.selected, selected_model<_anno>, selectedAstNode<AnnotationExpr> {
        
        public final AnnotationExpr astAnno;
        public final $args args;

        public Select( AnnotationExpr expression, Tokens tokens){
            this.astAnno = expression;
            this.args = $args.of( tokens);
        }
        
        @Override
        public $args getArgs(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$anno.Select {"+ System.lineSeparator()+
                    Text.indent(astAnno.toString() )+ System.lineSeparator()+
                    Text.indent("ARGS : " + args) + System.lineSeparator()+
                    "}";
        }

        
        @Override
        public AnnotationExpr ast(){
            return astAnno;
        } 
        
        @Override
        public _anno model() {
            return _anno.of(astAnno);
        }
        
    }
}
