package draft.java.proto;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.*;
import draft.*;
import draft.java.*;
import draft.java._model._node;
import draft.java.proto.$proto;
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
 *     .hardcode$([translator], target, value)
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
public final class Sanno
    implements Template<_anno>, $proto<_anno> {

    /** represents ANY annotation*/
    public static final Sanno ANY = Sanno.of("@A").$("A", "any");
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> _anno first( N _n, String proto ){
        return Sanno.of(proto).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @return 
     */
    public static final <N extends _node> _anno first( Node astNode, String proto ){
        return Sanno.of(proto).firstIn(astNode);
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
        return Sanno.of(proto, constraint).firstIn(_n);
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
        return Sanno.of(proto, constraint).firstIn(astNode);
    }
         
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String proto ){
        return Sanno.of(proto).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @return 
     */
    public static final <N extends _node> Select selectFirst( Node astNode, String proto ){
        return Sanno.of(proto).selectFirstIn(astNode);
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
        return Sanno.of(proto, constraint).selectFirstIn(_n);
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
        return Sanno.of(proto, constraint).selectFirstIn(astNode);
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
        return new Sanno( "@a" ).$("@a", "any").constraint(constraint).listIn(_n);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N _n, String proto ){
        return Sanno.of(proto).listIn(_n);
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
        return Sanno.of(proto, constraint).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N _n, _anno _proto ){
        return Sanno.of(_proto).listIn(_n);
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
        return Sanno.of(_proto, constraint).listIn(_n);
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
        return new Sanno( "@a" ).$("@a", "any").constraint(constraint).forEachIn(_n, _annoConsumer);
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
        return Sanno.of(proto).forEachIn(_n, _annoConsumer);
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
        return Sanno.of(proto, constraint).forEachIn(_n, _annoConsumer);
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
        return Sanno.of(_proto).forEachIn(_n, _annoConsumer);
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
        return Sanno.of(_proto, constraint).forEachIn(_n, _annoConsumer);
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
        return new Sanno( "@a" ).$("@a", "any").constraint(constraint).forSelectedIn(_n, _annoConsumer);
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
        return Sanno.of(proto).forSelectedIn(_n, _annoConsumer);
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
        return Sanno.of(proto, constraint).forSelectedIn(_n, _annoConsumer);
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
        return Sanno.of(_proto).forSelectedIn(_n, _annoConsumer);
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
        return Sanno.of(_proto, constraint).forSelectedIn(_n, _annoConsumer);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, String proto ){
        return Sanno.of(proto).listSelectedIn(_n);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, Predicate<_anno> constraint ){
        return new Sanno( "@a" ).$("@a", "any").constraint(constraint).listSelectedIn(_n);
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
        return Sanno.of(proto, constraint).listSelectedIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @return 
     */
    public static final <N extends _node> List<Select> selectList( N _n, _anno _proto ){
        return Sanno.of(_proto).listSelectedIn(_n);
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
        return Sanno.of(_proto, constraint).listSelectedIn(_n);
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
        return Sanno.of(_proto).removeIn(_n);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param constraint
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, Predicate<_anno> constraint ){
        return new Sanno( "@a" ).$("@a", "any").constraint(constraint).removeIn(_n);
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
        return Sanno.of(_proto, constraint).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String proto ){
        return Sanno.of(proto).removeIn(_n);
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
        return Sanno.of(proto, constraint).removeIn(_n);
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
        return Sanno.of(sourceProto)
            .replaceIn(_n, Sanno.of(targetProto));
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
        return Sanno.of(sourceProto)
            .replaceIn(_n, Sanno.of(targetProto));
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
        return Sanno.of(_anno.of(astSourceProto))
            .replaceIn(_n, Sanno.of(_anno.of(astTargetProto)));
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
        
        return Sanno.of(sourceProto)
            .replaceIn(_n, Sanno.of(targetProto));
    }
    
    public static Sanno of( String code){
        return of( new String[]{code} );
    }
    
    public static Sanno of( String code, Predicate<_anno> constraint){
        return of( new String[]{code} ).constraint(constraint);
    }
    
    public static Sanno of( String...code){
        _anno _a = _anno.of( code );
        return new Sanno( _a.toString().trim() );
    }

    public static Sanno of( _anno _a ){
        return new Sanno( _a.toString().trim() ); 
    }
    
    public static Sanno of( _anno _a, Predicate<_anno> constraint ){
        return new Sanno( _a.toString().trim() ).constraint(constraint);
    }
    
    public static Sanno of( Predicate<_anno> constraint ){
        return new Sanno( "@a" ).$("@a", "any").constraint(constraint);
    }
    
    /**
     * 
     * @param anonymousObjectWithAnnotation
     * @return 
     */
    public static Sanno of( Object anonymousObjectWithAnnotation ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject( ste );
        NodeList<BodyDeclaration<?>> bds = oce.getAnonymousClassBody().get();
        BodyDeclaration bd = bds.stream().filter(b -> b.getAnnotations().isNonEmpty() ).findFirst().get();
        return of( _anno.of(bd.getAnnotation(0) ) );        
    }
    
    public static Sanno of( Class<? extends Annotation> clazz){
        return Sanno.of( a -> a.isInstance(clazz) );         
    }
    
    public static Sanno of( Class<? extends Annotation> clazz, String argumentStencil ){
        if( !argumentStencil.trim().startsWith("(") ){
            argumentStencil = "("+ argumentStencil + ")";
        }
        Sanno $a = Sanno.of("@$annotationName$"+argumentStencil).constraint(a -> a.isInstance(clazz));
        return $a;
    }
    
    public static Sanno of( Class<? extends Annotation> clazz, String argumentStencil, Predicate<_anno> constraint){
        if( !argumentStencil.trim().startsWith("(") ){
            argumentStencil = "("+ argumentStencil + ")";
        }
        Sanno $a = Sanno.of("@$annotationName$"+argumentStencil).constraint(a -> a.isInstance(clazz) && constraint.test(a));
        return $a;
    }
    
    /** An additional Match constraint (By default always true) */
    public Predicate<_anno> constraint = a-> true;
    
    public Stencil pattern;
    
    
    
    private Sanno( String stencil) {
        this.pattern = Stencil.of(stencil );
    }

    /**
     * 
     * @param constraint
     * @return 
     */
    public Sanno constraint( Predicate<_anno> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    
    /**
     * ADDS an additional matching constraint to the prototype
     * @param constraint a constraint to be added
     * @return the modified prototype
     */
    public Sanno addConstraint( Predicate<_anno>constraint ){
        this.constraint = this.constraint.and(constraint);
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
        return select(astAnnoExpr ) != null;
    }

    /**
     * 
     * @param _a
     * @return 
     */
    public boolean matches( _anno _a){
        return select( _a ) != null;
    }

    /**
     * Deconstruct the expression into tokens, or return null if the statement doesnt match
     *
     * @param _a
     * @return Tokens from the stencil, or null if the expression doesnt match
     
    public $args deconstruct(_anno _a ){
        if( this.constraint.test(_a) ){
            Tokens r = pattern.deconstruct( _a.toString() ); 
            if( r != null){
                return new $args(r);
            }            
        }
        return null;
    }
    */ 

    /**
     * Deconstruct the expression into tokens, or return null if the statement doesnt match
     *
     * @param astAnnoExpr
     * @return Tokens from the stencil, or null if the expression doesnt match
     
    public $args deconstruct(AnnotationExpr astAnnoExpr ){
        return deconstruct(_anno.of(astAnnoExpr) );
    }
    */ 
    
     /**
     * 
     * @param _a
     * @return 
     */
    public Select select(_anno _a){
        if( this.constraint.test(_a) ){
            Tokens r = pattern.deconstruct( _a.toString() ); 
            if( r != null){
                return new Select( _a, r );
            }            
        }
        return null;        
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
    public Select select(AnnotationExpr astAnno){
        return select(_anno.of(astAnno));        
    }
    

    @Override
    public String toString() {
        return "($anno) : \"" + this.pattern + "\"";
    }

    @Override
    public _anno construct(Translator translator, Map<String, Object> keyValues) {
        return _anno.of(pattern.construct(translator, keyValues));
    }

    @Override
    public Sanno $(String target, String $Name) {
        this.pattern = this.pattern.$(target, $Name);        
        return this;
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param kvs the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public Sanno hardcode$( Tokens kvs ) {
        return hardcode$( Translator.DEFAULT_TRANSLATOR, kvs );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public Sanno hardcode$( Object... keyValues ) {
        return hardcode$( Translator.DEFAULT_TRANSLATOR, Tokens.of( keyValues ) );
    }

    /**
     * Hardcode parameterized values
     * (i.e. what was once a parameter, now is static text)
     *
     * @param translator translates values to be hardcoded into the Stencil
     * @param keyValues the key parameter NAME and String VALUE to assign to the
     * @return the modified Stencil
     */
    public Sanno hardcode$( Translator translator, Object... keyValues ) {
        return hardcode$( translator, Tokens.of( keyValues ) );
    }

    /**
     * 
     * @param translator
     * @param kvs
     * @return 
     */
    public Sanno hardcode$( Translator translator, Tokens kvs ) {
        this.pattern = this.pattern.hardcode$(translator,kvs);          
        return this;
    }

    @Override
    public List<String> list$() {
        return this.pattern.list$();
    }

    @Override
    public List<String> list$Normalized() {
        return this.pattern.list$Normalized();
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
    public List<Select> listSelectedIn(Node astNode ){
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
    public List<Select> listSelectedIn(_node _n ){
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
    public <N extends _node> N replaceIn(N _n, Sanno $a ){
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
    public <N extends Node> N replaceIn(N astNode, Sanno $a ){
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
            //$args tokens = deconstruct(a );
            Select select = select(a);
            if( select != null ){
                _annoActionFn.accept( select.model() );
            }
        });
        return astNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_anno> _annoActionFn){
        Walk.in(_n, AnnotationExpr.class, a -> {
            //$args tokens =  deconstruct(a );
            Select select = select(a);
            if( select != null ){
                _annoActionFn.accept( select.model() );
            }
        });
        return _n;
    }

    /**
     * A Matched Selection result returned from matching a prototype $anno
     * inside of some Node or _node
     */
    public static class Select 
        implements $proto.selected, selected_model<_anno>, selectedAstNode<AnnotationExpr> {
        
        public final AnnotationExpr astAnno;
        public final $args args;

        public Select ( _anno _a, Tokens tokens){
            this( _a, $args.of(tokens));
        }
        
        public Select ( _anno _a, $args tokens){
            this.astAnno = _a.ast();
            args = tokens;
        }
        
        public Select( AnnotationExpr expression, $args tokens){
            this.astAnno = expression;
            this.args = tokens; //$args.of( tokens);
        }
        
        @Override
        public $args getArgs(){
            return args;
        }
        
        @Override
        public String toString(){
            return "$anno.Select {"+ System.lineSeparator()+
                Text.indent(astAnno.toString() )+ System.lineSeparator()+
                Text.indent("$args : " + args) + System.lineSeparator()+
                "}";
        }

        public boolean isNamed(String name){            
            return astAnno.getNameAsString().equals(name);
        }
                
        public boolean isSingleValue(){
            return astAnno instanceof SingleMemberAnnotationExpr;
        }
        
        public boolean hasKeyValues(){
            return astAnno instanceof NormalAnnotationExpr;
        }
        
        public boolean hasNoValues(){
            return astAnno instanceof MarkerAnnotationExpr;
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
