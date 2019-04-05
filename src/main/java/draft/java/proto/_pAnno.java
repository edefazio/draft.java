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
 * $anno
 * CONSTRUCT
 *     .construct([Translator], Tokens) build & return 
 *     .fill([Translator], values)
 * PARAMETERIZE
 *     .$(target, parameter)
 *     .assign$([translator], target, value)
 * MATCH
 *     .constraint(Predicate<T>) //set the matching constraint
 *     .matches(AnnotationExpr)
 *     .select(AnnotationExpr)
 *     .deconstruct( AnnotationExpr )
 * QUERY       
 *     .first/.firstIn(_node, proto) find the first matching statement in
 *     .list/.listIn(_node, proto, Predicate<>) list all matches in        
 *     .selectFirst/.selectFirstIn(_node, proto) return the first "selection" match
 *     .selectList/.selectListIn(_node, proto) return a list of selection matches
 * MODIFY
 *     .remove/.removeIn(_node, proto)
 *     .replace/.replaceIn(_node, protoTarget, protoReplacement)
 *     .forEach/forEachIn(_node, Consumer<T>)
 *     .forSelected/forSelectedIn(_node, Consumer<Select>) 
 *</PRE> 
 */
public final class _pAnno
    implements Template<_anno>, _pQuery<_anno> {

    /** represents ANY annotation*/
    public static final _pAnno ANY = _pAnno.of("@A").$("A", "any");
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> _anno first( N _n, String proto ){
        return _pAnno.of(proto).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @return 
     */
    public static final <N extends _node> _anno first( Node astNode, String proto ){
        return _pAnno.of(proto).firstIn(astNode);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _anno first( N _n, String proto, Predicate<_anno> constraint){
        return _pAnno.of(proto, constraint).firstIn(_n);
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
        return _pAnno.of(proto, constraint).firstIn(astNode);
    }
         
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String proto ){
        return _pAnno.of(proto).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( Node astNode, String proto ){
        return _pAnno.of(proto).selectFirstIn(astNode);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String proto, Predicate<_anno> constraint){
        return _pAnno.of(proto, constraint).selectFirstIn(_n);
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
        return _pAnno.of(proto, constraint).selectFirstIn(astNode);
    }
    
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N _n ){
        return ANY.listIn(_n);
    }
    
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N _n, Predicate<_anno> constraint){
        return new _pAnno( "@a" ).$("@a", "any").constraint(constraint).listIn(_n);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N _n, String proto ){
        return _pAnno.of(proto).listIn(_n);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N _n, String proto, Predicate<_anno> constraint){
        return _pAnno.of(proto, constraint).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N _n, _anno _proto ){
        return _pAnno.of(_proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N _n, _anno _proto, Predicate<_anno> constraint){
        return _pAnno.of(_proto, constraint).listIn(_n);
    }
        
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forEach( N _n, Consumer<_anno> _annoConsumer){
        return ANY.forEachIn(_n, _annoConsumer);
    }
    
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @param constraint
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forEach( N _n, Predicate<_anno> constraint, Consumer<_anno> _annoConsumer){
        return new _pAnno( "@a" ).$("@a", "any").constraint(constraint).forEachIn(_n, _annoConsumer);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param proto
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forEach( N _n, String proto, Consumer<_anno> _annoConsumer){
        return _pAnno.of(proto).forEachIn(_n, _annoConsumer);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forEach( N _n, String proto, Predicate<_anno> constraint, Consumer<_anno> _annoConsumer){
        return _pAnno.of(proto, constraint).forEachIn(_n, _annoConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forEach( N _n, _anno _proto , Consumer<_anno> _annoConsumer){
        return _pAnno.of(_proto).forEachIn(_n, _annoConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param constraint
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forEach( N _n, _anno _proto, Predicate<_anno> constraint, Consumer<_anno> _annoConsumer){
        return _pAnno.of(_proto, constraint).forEachIn(_n, _annoConsumer);
    }
    
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forSelected( N _n, Consumer<Select> _annoConsumer){
        return ANY.forSelectedIn(_n, _annoConsumer);
    }
    
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @param constraint
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forSelected( N _n, Predicate<_anno> constraint, Consumer<Select> _annoConsumer){
        return new _pAnno( "@a" ).$("@a", "any").constraint(constraint).forSelectedIn(_n, _annoConsumer);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param proto
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forSelected( N _n, String proto, Consumer<Select> _annoConsumer){
        return _pAnno.of(proto).forSelectedIn(_n, _annoConsumer);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forSelected( N _n, String proto, Predicate<_anno> constraint, Consumer<Select> _annoConsumer){
        return _pAnno.of(proto, constraint).forSelectedIn(_n, _annoConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forSelected( N _n, _anno _proto , Consumer<Select> _annoConsumer){
        return _pAnno.of(_proto).forSelectedIn(_n, _annoConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param constraint
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forSelected( N _n, _anno _proto, Predicate<_anno> constraint, Consumer<Select> _annoConsumer){
        return _pAnno.of(_proto, constraint).forSelectedIn(_n, _annoConsumer);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String proto ){
        return _pAnno.of(proto).selectListIn(_n);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, Predicate<_anno> constraint ){
        return new _pAnno( "@a" ).$("@a", "any").constraint(constraint).selectListIn(_n);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String proto, Predicate<_anno> constraint){
        return _pAnno.of(proto, constraint).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, _anno _proto ){
        return _pAnno.of(_proto).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, _anno _proto, Predicate<_anno> constraint){
        return _pAnno.of(_proto, constraint).selectListIn(_n);
    }
    
    /**
     * Removes ALL annotations within _n
     * @param <N>
     * @param _n
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n ){
        return ANY.removeIn(_n);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param _proto
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, _anno _proto ){
        return _pAnno.of(_proto).removeIn(_n);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param constraint
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, Predicate<_anno> constraint ){
        return new _pAnno( "@a" ).$("@a", "any").constraint(constraint).removeIn(_n);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param _proto
     * @param constraint
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, _anno _proto, Predicate<_anno> constraint){
        return _pAnno.of(_proto, constraint).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String proto ){
        return _pAnno.of(proto).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String proto, Predicate<_anno> constraint){
        return _pAnno.of(proto, constraint).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param sourceProto
     * @param targetProto
     * @return 
     */
    public static final <N extends _node> N replace(N _n, _anno sourceProto, _anno targetProto){
        return _pAnno.of(sourceProto)
            .replaceIn(_n, _pAnno.of(targetProto));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param sourceProto
     * @param targetProto
     * @return 
     */
    public static final <N extends _node> N replace(N _n, String sourceProto, String targetProto){
        return _pAnno.of(sourceProto)
            .replaceIn(_n, _pAnno.of(targetProto));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param astSourceProto
     * @param astTargetProto
     * @return 
     */
    public static final <N extends _node> N replace(N _n, AnnotationDeclaration astSourceProto, AnnotationDeclaration astTargetProto){
        return _pAnno.of(_anno.of(astSourceProto))
            .replaceIn(_n, _pAnno.of(_anno.of(astTargetProto)));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param sourceProto
     * @param targetProto
     * @return 
     */
    public static final <N extends _node> N replace( N _n, Class<? extends Annotation>sourceProto, Class<? extends Annotation>targetProto){
        
        return _pAnno.of(sourceProto)
            .replaceIn(_n, _pAnno.of(targetProto));
    }
    
    public static _pAnno of( String code){
        return of( new String[]{code} );
    }
    
    public static _pAnno of( String code, Predicate<_anno> constraint){
        return of( new String[]{code} ).constraint(constraint);
    }
    
    public static _pAnno of( String...code){
        _anno _a = _anno.of( code );
        return new _pAnno( _a.toString().trim() );
    }

    public static _pAnno of( _anno _a ){
        return new _pAnno( _a.toString().trim() ); 
    }
    
    public static _pAnno of( _anno _a, Predicate<_anno> constraint ){
        return new _pAnno( _a.toString().trim() ).constraint(constraint);
    }
    
    public static _pAnno of( Predicate<_anno> constraint ){
        return new _pAnno( "@a" ).$("@a", "any").constraint(constraint);
    }
    
    /**
     * 
     * @param anonymousObjectWithAnnotation
     * @return 
     */
    public static _pAnno of( Object anonymousObjectWithAnnotation ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject( ste );
        NodeList<BodyDeclaration<?>> bds = oce.getAnonymousClassBody().get();
        BodyDeclaration bd = bds.stream().filter(b -> b.getAnnotations().isNonEmpty() ).findFirst().get();
        return of( _anno.of(bd.getAnnotation(0) ) );        
    }
    
    public static _pAnno of( Class<? extends Annotation> clazz){
        return _pAnno.of( a -> a.isInstance(clazz) );         
    }
    
    public static _pAnno of( Class<? extends Annotation> clazz, String argumentStencil ){
        if( !argumentStencil.trim().startsWith("(") ){
            argumentStencil = "("+ argumentStencil + ")";
        }
        _pAnno $a = _pAnno.of("@$annotationName$"+argumentStencil).constraint(a -> a.isInstance(clazz));
        return $a;
    }
    
    public static _pAnno of( Class<? extends Annotation> clazz, String argumentStencil, Predicate<_anno> constraint){
        if( !argumentStencil.trim().startsWith("(") ){
            argumentStencil = "("+ argumentStencil + ")";
        }
        _pAnno $a = _pAnno.of("@$annotationName$"+argumentStencil).constraint(a -> a.isInstance(clazz) && constraint.test(a));
        return $a;
    }
    
    /** An additional Match constraint (By default always true) */
    public Predicate<_anno> constraint = a-> true;
    
    public Stencil annoStencil;
    
    private _pAnno( String stencil) {
        this.annoStencil = Stencil.of(stencil );
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public _pAnno constraint( Predicate<_anno> constraint ){
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
     * @param astAnnoExpr
     * @return 
     */
    public boolean matches( AnnotationExpr astAnnoExpr ){
        return deconstruct(astAnnoExpr ) != null;
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
    public args deconstruct(_anno _a ){
        if( this.constraint.test(_a) ){
            Tokens r = annoStencil.deconstruct( _a.toString() ); 
            if( r != null){
                return new args(r);
            }            
        }
        return null;
    }

    /**
     * Deconstruct the expression into tokens, or return null if the statement doesnt match
     *
     * @param astAnnoExpr
     * @return Tokens from the stencil, or null if the expression doesnt match
     */
    public args deconstruct(AnnotationExpr astAnnoExpr ){
        return deconstruct(_anno.of(astAnnoExpr) );
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
    public _pAnno $(String target, String $Name) {
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
    public _pAnno assign$( Tokens kvs ) {
        return assign$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public _pAnno assign$( Object... keyValues ) {
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
    public _pAnno assign$( Translator translator, Object... keyValues ) {
        return assign$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public _pAnno assign$( Translator translator, Tokens kvs ) {
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
     * @param astExpr
     * @return 
     */
    public Select select(AnnotationExpr astExpr){
        args ts = this.deconstruct(astExpr);
        if( ts != null){
            return new Select( astExpr, ts );
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
    public List<_anno> listIn(_node _n ){
        return listIn(_n.ast() );
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
    public List<Select> selectListIn(Node astNode ){
        List<Select>sts = new ArrayList<>();
        astNode.walk(AnnotationExpr.class, e-> {
            Select s = select( e );
            if( s != null ){
                sts.add( s);
            }
        });
        return sts;
    }

    @Override
    public List<Select> selectListIn(_node _n ){
        List<Select>sts = new ArrayList<>();
        Walk.in(_n, AnnotationExpr.class, e -> {
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
     * @param astNode the root node to start search
     * @param <N> the input node TYPE
     * @return the modified node
     */
    @Override
    public <N extends Node> N removeIn(N astNode ){
        astNode.walk( AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astAnno.removeForced();
            }
        });
        return astNode;
    }

    /**
     *
     * @param _n the root model node to start from
     * @param <N> the TYPE of model node
     * @return the modified model node
     */
    @Override
    public <N extends _node> N removeIn(N _n ){
        Walk.in(_n, AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astAnno.removeForced();
            }
        });
        return _n;
    }

    /**
     * Replace all occurrences of the template in the code with the replacement
     * (composing the replacement from the constructed tokens in the source)
     *
     * @param _n the model to find replacements
     * @param $a the template to be constructed as the replacement
     * @param <N> the TYPE of model
     * @return
     */
    public <N extends _node> N replaceIn(N _n, _pAnno $a ){
        Walk.in(_n, AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astAnno.replace($a.construct(sel.args).ast() );
            }
        });
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param $a
     * @return 
     */
    public <N extends Node> N replaceIn(N astNode, _pAnno $a ){
        astNode.walk(AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel.astAnno.replace($a.construct(sel.args).ast() );
            }
        });
        return astNode;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConsumer
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Consumer<Select> selectConsumer ){
        Walk.in(_n, AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return _n;
    }

    /**
     * 
     * @param <N>
     * @param astNode
     * @param selectConsumer
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astNode, Consumer<Select> selectConsumer ){
        astNode.walk(AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                selectConsumer.accept( sel );
            }
        });
        return astNode;
    }

    @Override
    public <N extends Node> N forEachIn(N astNode, Consumer<_anno> _annoActionFn){
        astNode.walk(AnnotationExpr.class, a-> {
            args tokens = deconstruct(a );
            if( tokens != null ){
                _annoActionFn.accept(_anno.of(a));
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_anno> _annoActionFn){
        Walk.in(_n, AnnotationExpr.class, a -> {
            args tokens =  deconstruct(a );
            if( tokens != null ){
                _annoActionFn.accept(_anno.of(a) );
            }
        });
        return _n;
    }

    /**
     * A Matched Selection result returned from matching a prototype $anno
     * inside of some Node or _node
     */
    public static class Select 
        implements _pQuery.selected, selected_model<_anno>, selectedAstNode<AnnotationExpr> {
        
        public final AnnotationExpr astAnno;
        public final args args;

        public Select( AnnotationExpr expression, args tokens){
            this.astAnno = expression;
            this.args = tokens; //$args.of( tokens);
        }
        
        @Override
        public args getArgs(){
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
