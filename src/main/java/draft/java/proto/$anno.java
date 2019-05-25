package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.expr.*;
import draft.*;
import draft.java.*;
import draft.java._model._node;
import draft.java.proto.$proto.$component;
import java.lang.annotation.Annotation;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * prototype for an annotation
 *
 * @author Eric
 */
public class $anno
    implements Template<_anno>, $proto<_anno>, $constructor.$part, $method.$part, 
        $field.$part, $parameter.$part {

    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final _anno first( Class clazz, String pattern ){
        return $anno.of(pattern).firstIn(_type.of(clazz));
    }
    
    /**
     * 
     * @param clazz
     * @param constraint
     * @return 
     */
    public static final _anno first( Class clazz, Predicate<_anno> constraint ){
       return of().addConstraint(constraint).firstIn(clazz);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> _anno first( N _n, String pattern ){
        return $anno.of(pattern).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _anno first( N _n, Predicate<_anno> constraint ){
        return of().addConstraint(constraint).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param pattern
     * @return 
     */
    public static final <N extends _node> _anno first( Node astNode, String pattern ){
        return $anno.of(pattern).firstIn(astNode);
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final _anno first( Class clazz, String pattern, Predicate<_anno> constraint){
        return $anno.of(pattern, constraint).firstIn(_type.of(clazz));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _anno first( N _n, String pattern, Predicate<_anno> constraint){
        return $anno.of(pattern, constraint).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> _anno first( Node astNode, String pattern,Predicate<_anno>constraint){
        return $anno.of(pattern, constraint).firstIn(astNode);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String pattern ){
        return $anno.of(pattern).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @param selectConstraint
     * @return 
     */
    public static final Select selectFirst( Class clazz, String pattern, Predicate<Select>selectConstraint){
        return $anno.of(pattern).selectFirstIn(clazz, selectConstraint);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConstraint
     * @return 
     */
    public static final <N extends _node> Select selectFirst( N _n, String pattern, Predicate<Select>selectConstraint){
        return $anno.of(pattern).selectFirstIn(_n, selectConstraint);
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final Select selectFirst( Class clazz, String pattern ){
        return $anno.of(pattern).selectFirstIn(_type.of(clazz));
    }
    
    /**
     * lists all annos within the clazz
     * @param clazz the runtime class (with source on classpath)
     * @return 
     */
    public static final List<_anno> list( Class clazz ){
        return of().listIn(_type.of(clazz));
    }
    
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N _n ){
        return of().listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param annoClass
     * @return 
     */
    public static final <N extends _node> List<_anno> list(N _n, Class<? extends Annotation> annoClass ){
       return $anno.of(annoClass).listIn(_n);
    }
    
    /**
     * 
     * @param clazz
     * @param annoClass
     * @return 
     */
    public static final List<_anno> list(Class clazz, Class<? extends Annotation> annoClass ){
        return $anno.of(annoClass).listIn(clazz);
    }
    
    /**
     * lists all annos within the clazz
     * @param clazz
     * @param constraint
     * @return 
     */
    public static final List<_anno> list( Class clazz, Predicate<_anno> constraint){
        return of().addConstraint(constraint).listIn(clazz);
    }
    
    /**
     * lists all annos within the clazz
     * @param <N>
     * @param _n
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N _n, Predicate<_anno> constraint){
        return of().addConstraint(constraint).listIn(_n);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N _n, String pattern ){
        return $anno.of(pattern).listIn(_n);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<_anno> list( Class clazz, String pattern ){
        return $anno.of(pattern).listIn(clazz);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N _n, String pattern, Predicate<_anno> constraint){
        return $anno.of(pattern, constraint).listIn(_n);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param clazz
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final List<_anno> list(Class clazz, String pattern, Predicate<_anno> constraint){
        return $anno.of(pattern, constraint).listIn(clazz);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @return 
     */
    public static final <N extends _node> List<_anno> list( N _n, _anno _proto ){
        return $anno.of(_proto).listIn(_n);
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
        return $anno.of(_proto, constraint).listIn(_n);
    }
    
    /**
     * perform an action on all annos within the clazz
     * @param clazz
     * @param _annoConsumer
     * @return 
     */
    public static final _type forEach( Class clazz, Consumer<_anno> _annoConsumer){
        return of().forEachIn(clazz, _annoConsumer);
    }
    
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forEach( N _n, Consumer<_anno> _annoConsumer){
        return of().forEachIn(_n, _annoConsumer);
    }
    
    /**
     * 
     * @param clazz
     * @param constraint
     * @param _annoConsumer
     * @return 
     */
    public static final _type forEach( Class clazz, Predicate<_anno> constraint, Consumer<_anno> _annoConsumer){
        return of(constraint).forEachIn(clazz, _annoConsumer);
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
        return of().addConstraint(constraint).forEachIn(_n, _annoConsumer);
    }

    /**
     * lists all occurrences of annos that match the 
     * @param clazz
     * @param pattern
     * @param _annoConsumer
     * @return 
     */
    public static final _type forEach( Class clazz, String pattern, Consumer<_anno> _annoConsumer){
        return of(pattern).forEachIn(clazz, _annoConsumer);
    }   
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param pattern
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forEach( N _n, String pattern, Consumer<_anno> _annoConsumer){
        return $anno.of(pattern).forEachIn(_n, _annoConsumer);
    }

    /**
     * lists all occurrences of annos that match the 
     * @param clazz
     * @param pattern
     * @param constraint
     * @param _annoConsumer
     * @return 
     */
    public static final _type forEach(Class clazz, String pattern, Predicate<_anno> constraint, Consumer<_anno> _annoConsumer){
        return $anno.of(pattern, constraint).forEachIn(clazz, _annoConsumer);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forEach( N _n, String pattern, Predicate<_anno> constraint, Consumer<_anno> _annoConsumer){
        return $anno.of(pattern, constraint).forEachIn(_n, _annoConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _node> N forEach( N _n, _anno _proto, Consumer<_anno> _annoConsumer){
        return $anno.of(_proto).forEachIn(_n, _annoConsumer);
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
        return $anno.of(_proto, constraint).forEachIn(_n, _annoConsumer);
    }
    
    
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @param selectConsumer
     * @return 
     */
    public static final <N extends _node> N forSelected( N _n, Consumer<Select> selectConsumer){
        return of().forSelectedIn(_n, selectConsumer);
    }
    
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @param selectConstraint
     * @param selectConsumer
     * @return 
     */
    public static final <N extends _node> N forSelected( N _n, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer){
        return of().forSelectedIn(_n, selectConstraint, selectConsumer);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConsumer
     * @return 
     */
    public static final <N extends _node> N forSelected( N _n, String pattern, Consumer<Select> selectConsumer){
        return $anno.of(pattern).forSelectedIn(_n, selectConsumer);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConstraint
     * @param selectConsumer
     * @return 
     */
    public static final <N extends _node> N forSelected( N _n, String pattern, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer){
        return $anno.of(pattern).forSelectedIn(_n, selectConstraint, selectConsumer);
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
        return $anno.of(_proto).forSelectedIn(_n, _annoConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param selectConstraint
     * @param selectConsumer
     * @return 
     */
    public static final <N extends _node> N forSelected( N _n, _anno _proto, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer){
        return $anno.of(_proto).forSelectedIn(_n, selectConstraint, selectConsumer);
    }
    
    /**
     * 
     * @param clazz
     * @param _proto
     * @param selectConstraint
     * @param selectConsumer
     * @return 
     */
    public static final _type forSelected(Class clazz, _anno _proto, Predicate<Select> selectConstraint, Consumer<Select> selectConsumer){
        return $anno.of(_proto).forSelectedIn(clazz, selectConstraint, selectConsumer);
    }

    /**
     * lists all occurrences of annos that match the 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final List<Select> listSelected( Class clazz, String pattern ){
        return $anno.of(pattern).listSelectedIn(_type.of(clazz));
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> List<Select> listSelected( N _n, String pattern ){
        return $anno.of(pattern).listSelectedIn(_n);
    }

    /**
     * lists all occurrences of annos that match the 
     * @param clazz
     * @param selectConstraint
     * @return 
     */
    public static final List<Select> listSelected( Class clazz, Predicate<Select> selectConstraint ){
        return of().listSelectedIn(_type.of(clazz), selectConstraint);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param selectConstraint
     * @return 
     */
    public static final <N extends _node> List<Select> listSelected( N _n, Predicate<Select> selectConstraint ){
        return of().listSelectedIn(_n, selectConstraint);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param clazz
     * @param pattern
     * @param selectConstraint
     * @return 
     */
    public static final List<Select> listSelected(Class clazz, String pattern, Predicate<Select> selectConstraint){
        return $anno.of(pattern).listSelectedIn(_type.of(clazz), selectConstraint);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConstraint
     * @return 
     */
    public static final <N extends _node> List<Select> listSelected( N _n, String pattern, Predicate<Select> selectConstraint){
        return $anno.of(pattern).listSelectedIn(_n, selectConstraint);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @return 
     */
    public static final <N extends _node> List<Select> listSelected( N _n, _anno _proto ){
        return $anno.of(_proto).listSelectedIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param selectConstraint
     * @return 
     */
    public static final <N extends _node> List<Select> listSelected( N _n, _anno _proto, Predicate<Select> selectConstraint){
        return $anno.of(_proto).listSelectedIn(_n, selectConstraint);
    }
    
    /**
     * Removes ALL annotations within the clazz
     * @param clazz
     * @return the modified N
     */
    public static final _type remove( Class clazz ){
        return of().removeIn(clazz);
    }
    
    /**
     * Removes ALL annotations within _n
     * @param <N>
     * @param _n
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n ){
        return of().removeIn(_n);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param _proto
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, _anno _proto ){
        return $anno.of(_proto).removeIn(_n);
    }
    
    /**
     * 
     * @param clazz
     * @param annoClass
     * @return 
     */
    public static final _type remove(Class clazz, Class<? extends Annotation> annoClass){
        return $anno.of(annoClass).removeIn(clazz);
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static final _type remove( Class clazz, String... pattern ){
        return $anno.of(pattern).removeIn(clazz);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param clazz
     * @param constraint
     * @return the modified N
     */
    public static final _type remove( Class clazz, Predicate<_anno> constraint ){
        return $anno.of().addConstraint(constraint).removeIn(clazz);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param constraint
     * @return the modified N
     */
    public static final <N extends _node> N remove( N _n, Predicate<_anno> constraint ){
        return $anno.of().addConstraint(constraint).removeIn(_n);
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
        return $anno.of(_proto, constraint).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String pattern ){
        return $anno.of(pattern).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _node> N remove( N _n, String pattern, Predicate<_anno> constraint){
        return $anno.of(pattern, constraint).removeIn(_n);
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final _type remove(Class clazz, String pattern, Predicate<_anno> constraint){
        return $anno.of(pattern, constraint).removeIn(clazz);
    }
    
    /**
     * 
     * @param clazz
     * @param sourceProto
     * @param targetProto
     * @return 
     */
    public static final _type replace(Class clazz, _anno sourceProto, _anno targetProto){
        return $anno.of(sourceProto)
            .replaceIn(clazz, $anno.of(targetProto));
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
        return $anno.of(sourceProto)
            .replaceIn(_n, $anno.of(targetProto));
    }

    /**
     * 
     * @param clazz
     * @param sourcePattern
     * @param targetPattern
     * @return 
     */
    public static final _type replace(Class clazz, String sourcePattern, String targetPattern){
        return $anno.of(sourcePattern)
            .replaceIn(clazz, $anno.of(targetPattern));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param sourcePattern
     * @param targetPattern
     * @return 
     */
    public static final <N extends _node> N replace(N _n, String sourcePattern, String targetPattern){
        return $anno.of(sourcePattern)
            .replaceIn(_n, $anno.of(targetPattern));
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
        return $anno.of(_anno.of(astSourceProto))
            .replaceIn(_n, $anno.of(_anno.of(astTargetProto)));
    }

    /**
     * 
     * @param clazz
     * @param targetAnno
     * @param replacementAnno
     * @return 
     */
    public static final _type replace( Class clazz, Class<? extends Annotation>targetAnno, Class<? extends Annotation>replacementAnno){        
        return $anno.of(targetAnno)
            .replaceIn(clazz, $anno.of(replacementAnno));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param targetAnno
     * @param replacementAnno
     * @return 
     */
    public static final <N extends _node> N replace( N _n, Class<? extends Annotation>targetAnno, Class<? extends Annotation>replacementAnno){        
        return $anno.of(targetAnno)
            .replaceIn(_n, $anno.of(replacementAnno));
    }
    
    public static $anno any(){
        return of();
    }
    
    public static $anno of(){
        return new $anno( $id.of() );
    }
    
    public static $anno of( $id name, $memberValue...memberValues ){
        return new $anno(name, memberValues);
    }
    
    public static $anno of(String pattern) {
        return new $anno(_anno.of(pattern));
    }
    
    public static $anno of(String... pattern) {
        return new $anno(_anno.of(pattern));
    }
    
    public static $anno of( Predicate<_anno> constraint ){
        return of().addConstraint(constraint);
    }
    
    public static $anno of(String pattern, Predicate<_anno>constraint) {
        return new $anno(_anno.of(pattern)).addConstraint(constraint);
    }
    
    public static $anno of(_anno _an) {
        return new $anno(_an);
    }

    public static $anno of(_anno _an, Predicate<_anno>constraint) {
        return new $anno(_an).addConstraint(constraint);
    }
        
    public static $anno of(Class<? extends Annotation>sourceAnnoClass) {
        return new $anno(_anno.of(sourceAnnoClass));
    }
    
    public static $anno of (Class<? extends Annotation>sourceAnnoClass, Predicate<_anno> constraint) {
        return of( sourceAnnoClass).addConstraint(constraint); 
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
    
    public static $anno of(Class<? extends Annotation>sourceAnnoClass, String argumentStencil) {
        if( !argumentStencil.trim().startsWith("(") ){
            argumentStencil = "("+ argumentStencil + ")";
        }
        String str = "@"+sourceAnnoClass.getSimpleName()+argumentStencil;
        return new $anno(_anno.of(str));               
    }
        
    /** Default Matching constraint (by default ALWAYS Match)*/
    public Predicate<_anno> constraint = a -> true;

    /** the id of the annotation */
    public $id name;

    /** the member values of the annotation */
    public List<$memberValue> $mvs = new ArrayList<>();

    /**
     * 
     * @param name
     * @param $mvs 
     */
    private $anno( $id name, $memberValue...mvs ){
       this.name = name;
       Arrays.stream(mvs).forEach( mv -> this.$mvs.add(mv));       
    }
    
    public $anno(_anno proto) {
        this.name = $id.of(proto.getName());
        AnnotationExpr astAnn = proto.ast();
        if (astAnn instanceof NormalAnnotationExpr) {
            NormalAnnotationExpr na = (NormalAnnotationExpr) astAnn;
            na.getPairs().forEach(mv -> $mvs.add($memberValue.of(mv.getNameAsString(), mv.getValue())));
        } else if (astAnn instanceof SingleMemberAnnotationExpr) {
            SingleMemberAnnotationExpr sa = (SingleMemberAnnotationExpr) astAnn;
            $mvs.add($memberValue.of(sa.getMemberValue()));
        }
    }

    /**
     * updates the name of the prototype for matching and constructing
     * @param name
     * @return 
     */
    public $anno $name($id name){
        this.name = name;
        return this;
    }
    
    /**
     * Updates the name prototype for matching and constructing
     * @param name
     * @return 
     */
    public $anno $name(String name){
        this.name = $id.of(name);
        return this;
    } 
    
    /**
     * 
     * @param mv
     * @return 
     */
    public $anno $memberValue( $memberValue mv ){
        this.$mvs.add(mv);
        return this;
    }
    
    /**
     * 
     * @param key
     * @param value
     * @return 
     */
    public $anno $memberValue(String key, Expression value){
        this.$mvs.add( new $memberValue(key, value) );
        return this;
    }
    
    /**
     * 
     * @param key
     * @param value
     * @return 
     */
    public $anno $memberValue(String key, String value){
        this.$mvs.add( new $memberValue(key, value) );
        return this;
    }
    
    /**
     * 
     * @return 
     */
    public boolean isMatchAny(){
        return name.isMatchAny() && ( this.$mvs.isEmpty() || (this.$mvs.size() ==1 && this.$mvs.get(0).isMatchAll() ));
    }
    
    /**
     * Sets the underlying constraint
     * @param constraint
     * @return 
     
    public $anno constraint(Predicate<_anno> constraint) {
        this.constraint = constraint;
        return this;
    }
    */ 

    /**
     * ADDS an additional matching constraint to the prototype
     *
     * @param constraint a constraint to be added
     * @return the modified prototype
     */
    public $anno addConstraint(Predicate<_anno> constraint) {
        this.constraint = this.constraint.and(constraint);
        return this;
    }

    public Tokens decompose(_anno _a) {
        //Tokens ts = $name.decompose(_a.getName());
        if( ! this.constraint.test(_a)){
            //System.out.println( "Didnt pass MVP Constraint");
            return null;
        }
        
        Tokens ts = name.decompose(_a.getName());
        if( ts == null ){
            //System.out.println( "Decompose null for name "+name+" for \""+_a.getName()+"\"");
            return null;
        }
        if ($mvs.isEmpty() ) {
            //System.out.println( "Returning "+ts+" for name \""+_a.getName()+"\"");
            return ts;
        }
        AnnotationExpr astAnn = _a.ast();
        if (astAnn instanceof MarkerAnnotationExpr) {
            //System.out.println( "Marketer");            
            if ($mvs.size() == 1) {
                if ($mvs.get(0).isMatchAll()) {
                    return ts;
                }
                return null;
            }
            
            return null;
        }
        if (astAnn instanceof SingleMemberAnnotationExpr) {
            //System.out.println( "SingleMember");
            if ($mvs.size() == 1) {
                SingleMemberAnnotationExpr sme = (SingleMemberAnnotationExpr) astAnn;
                Tokens props = $mvs.get(0).decompose(sme.getMemberValue());
                if( props == null ){
                    return null;
                }
                if (ts.isConsistent(props)) {                    
                    ts.putAll(props);
                    return ts;
                } 
            }
            return null;
        }
        if (astAnn instanceof NormalAnnotationExpr) {
            //System.out.println( "Normal "+ astAnn );
            NormalAnnotationExpr sme = (NormalAnnotationExpr) astAnn;
            if( $mvs.size() == 1 && sme.getPairs().isEmpty() && $mvs.get(0).isMatchAll() ){
                return new Tokens();
            }
            if ($mvs.size() <= sme.getPairs().size()) {
                
                List<MemberValuePair> mvpsC = new ArrayList<>();
                mvpsC.addAll(sme.getPairs());
                for (int i = 0; i < $mvs.size(); i++) {
                    $memberValue.Select sel = $mvs.get(i).selectFirst(mvpsC);
                    if (sel == null) {
                        return null;
                    } else {
                        mvpsC.remove(sel.astMvp); //whenever I find a match, I remove the matcher
                        if( !ts.isConsistent(sel.args.asTokens())){
                            return null;
                        }
                        ts.putAll(sel.args.asTokens());
                    }
                }
                return ts;                 
            }
            return null;
        }
        return null;
    }

    public boolean matches( AnnotationExpr astAnno){
        return select( astAnno ) != null;
    }
    
    public boolean matches(String... anno) {
        return matches(_anno.of(anno));
    }

    public boolean matches(_anno _a) {
        return select(_a) != null;
    }

    public String compose( Object...values ){
        return compose( Translator.DEFAULT_TRANSLATOR, Tokens.of(values ));
    }
    
    public String compose(Translator translator, Map<String, Object> keyValues) {
        if( keyValues.get("$anno") != null ){
            //override parameter passed in
            $anno $a = $anno.of( keyValues.get("$anno").toString() );
            Map<String,Object> kvs = new HashMap<>();
            kvs.putAll(keyValues);
            kvs.remove("$anno"); //remove to avoid stackOverflow
            return $a.compose(translator, kvs);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("@");
        sb.append(name.compose(translator, keyValues));
        String properties = "";
        for (int i = 0; i < $mvs.size(); i++) {
            if ((properties.length() > 0) && (!properties.endsWith(","))) {
                properties = properties + ",";
            }
            properties += $mvs.get(i).compose(translator, keyValues);
        }
        if (properties.length() == 0) {
            return sb.toString();
        }
        return sb.toString() + "(" + properties + ")";
    }
    
    @Override
    public _anno construct(Translator translator, Map<String, Object> keyValues) {
        if( keyValues.get("$anno") != null ){
            //override parameter passed in
            $anno $a = $anno.of( keyValues.get("$anno").toString() );
            Map<String,Object> kvs = new HashMap<>();
            kvs.putAll(keyValues);
            kvs.remove("$anno"); //remove to avoid stackOverflow
            return $a.construct(translator, kvs);
        }
        return _anno.of(compose(translator, keyValues));      
    }

    @Override
    public $anno $(String target, String $Name) {
        name.$(target, $Name);
        $mvs.forEach(mv -> mv.key.pattern = mv.key.pattern.$(target, $Name));
        return this;
    }
    
    public $anno hardcode$( Translator tr, Object...keyValues){
        name.hardcode$(tr, keyValues);
        $mvs.forEach(mv -> mv.key.pattern = mv.key.pattern.hardcode$(tr, keyValues));
        return this;
    }

    public Select select( AnnotationExpr astAnn){
        return select(_anno.of(astAnn));
    }
    
    public Select select(String... anno){
        return select(_anno.of(anno));
    }
    
    public Select select(_anno _a){
        //System.out.println( "testing "+ _a);
        if(this.constraint.test(_a)){
            //System.out.println( "passed constraint "+ _a);
            Tokens ts = decompose(_a);            
            if( ts != null ){
                return new Select(_a, ts);
            }
        }
        return null;
    }
    
    @Override
    public List<String> list$() {
        List<String> params = new ArrayList<>();
        params.addAll( this.name.list$() );
        this.$mvs.forEach(m -> {
            params.addAll( m.key.pattern.list$() ); 
            params.addAll( m.value.pattern.list$() ); 
        });
        return params;
    }

    @Override
    public List<String> list$Normalized() {
        List<String> params = new ArrayList<>();
        params.addAll( this.name.list$() );
        this.$mvs.forEach(m -> {
            params.addAll( m.key.pattern.list$Normalized() ); 
            params.addAll( m.value.pattern.list$Normalized() ); 
        });
        return params.stream().distinct().collect(Collectors.toList() );
    }

    @Override
    public List<_anno> listIn(Class clazz) {
        return listIn( _type.of(clazz));
    }
    
    @Override    
    public List<_anno> listIn(_node _n) {
        List<_anno> found = new ArrayList<>();
        Walk.in(_n, _anno.class, a-> {
            Select sel = select(a); 
            if( sel != null ){
                found.add( sel._ann );
            }
        }); 
        return found;
    }

    @Override
    public List<_anno> listIn(Node astRootNode) {
        List<_anno> found = new ArrayList<>();
        astRootNode.walk(AnnotationExpr.class, a-> {
            Select sel = select(_anno.of(a));
            if( sel != null ){
                found.add( sel._ann );
            }
        });
        return found;
    }
    
    /**
     * Replace all occurrences of the template in the code with the replacement
     * (composing the replacement from the constructed tokens in the source)
     *
     * @param clazz 
     * @param a the template to be constructed as the replacement
     * @return
     */
    public _type replaceIn(Class clazz, $anno a ){
        return replaceIn(_type.of(clazz), a);
    }
    
    /**
     * Replace all occurrences of the template in the code with the replacement
     * (composing the replacement from the constructed tokens in the source)
     *
     * @param _n the model to find replacements
     * @param a the template to be constructed as the replacement
     * @param <N> the TYPE of model
     * @return
     */
    public <N extends _node> N replaceIn(N _n, $anno a ){
        Walk.in(_n, AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel._ann.ast().replace(a.construct(sel.args).ast() );
            }
        });
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param a
     * @return 
     */
    public <N extends Node> N replaceIn(N astNode, $anno a ){
        astNode.walk(AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel._ann.ast().replace(a.construct(sel.args).ast() );
            }
        });
        return astNode;
    }
    
    @Override
    public _anno firstIn(Class clazz){
        return firstIn(_type.of(clazz));
    }
    
    @Override
    public _anno firstIn(Node astRootNode) {
        Optional<Node>on = 
            astRootNode.stream().filter(
                n -> n instanceof AnnotationExpr 
                    && select((AnnotationExpr)n) != null )
                    .findFirst();
        if( on.isPresent() ){
            return _anno.of( (AnnotationExpr)on.get());
        }
        return null;        
    }

    @Override
    public _anno firstIn(_node _n) {
        return firstIn( _n.ast() );        
    }
    
    /**
     * 
     * @param astRootNode
     * @return 
     */
    @Override
    public Select selectFirstIn(Node astRootNode) {
        Optional<Node>on = 
            astRootNode.stream().filter(
                n -> n instanceof AnnotationExpr 
                    && select((AnnotationExpr)n) != null )
                .findFirst();
        if( on.isPresent() ){
            return select( (AnnotationExpr)on.get());
        }
        return null;        
    }

    /**
     * 
     * @param _n
     * @return 
     */
    @Override
    public Select selectFirstIn( _node _n) {
        return $anno.this.selectFirstIn( _n.ast() );        
    }
    
    /**
     * 
     * @param clazz
     * @return 
     */
    @Override
    public Select selectFirstIn( Class clazz){
        return selectFirstIn( _type.of(clazz));
    } 
    
    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @return 
     */
    public Select selectFirstIn( Class clazz, Predicate<Select> selectConstraint ){
        return selectFirstIn(_type.of(clazz), selectConstraint );
    }
    
    /**
     * 
     * @param astRootNode
     * @param selectConstraint
     * @return 
     */
    public Select selectFirstIn(Node astRootNode, Predicate<Select> selectConstraint) {
        Optional<Node>on = 
            astRootNode.stream().filter(
                n -> {
                    if( n instanceof AnnotationExpr){
                        Select sel = select( (AnnotationExpr)n);
                        return sel != null && selectConstraint.test(sel);
                    }
                    return false;
                }).findFirst();
        
        if( on.isPresent() ){
            return select( (AnnotationExpr)on.get());
        }
        return null;        
    }

    /**
     * 
     * @param _n
     * @param selectConstraint
     * @return 
     */
    public Select selectFirstIn(_node _n, Predicate<Select>selectConstraint) {
        return $anno.this.selectFirstIn( _n.ast(), selectConstraint);        
    }
    
    @Override
    public List<Select> listSelectedIn(Node astRootNode) {
        List<Select> found = new ArrayList<>();
        astRootNode.walk(AnnotationExpr.class, a-> {
            Select sel = select(_anno.of(a));
            if( sel != null ){
                found.add( sel  );
            }
        });
        return found;
    }

    @Override
    public List<Select> listSelectedIn(_node _n) {
        List<Select> found = new ArrayList<>();
        Walk.in(_n, _anno.class, a-> {
            Select sel = select(a); 
            if( sel != null ){
                found.add( sel );
            }
        }); 
        return found;
    }
    
    /**
     * 
     * @param astRootNode
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(Node astRootNode, Predicate<Select> selectConstraint) {
        List<Select> found = new ArrayList<>();
        astRootNode.walk(AnnotationExpr.class, a-> {
            Select sel = select(_anno.of(a));
            if( sel != null && selectConstraint.test(sel)){
                found.add( sel  );
            }
        });
        return found;
    }

    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(Class clazz, Predicate<Select> selectConstraint) {
        return listSelectedIn(_type.of(clazz), selectConstraint);
    }
    
    /**
     * 
     * @param _n
     * @param selectConstraint
     * @return 
     */
    public List<Select> listSelectedIn(_node _n, Predicate<Select> selectConstraint) {
        List<Select> found = new ArrayList<>();
        Walk.in(_n, _anno.class, a-> {
            Select sel = select(a); 
            if( sel != null && selectConstraint.test(sel)){
                found.add( sel );
            }
        }); 
        return found;
    }
    
    @Override
    public <N extends Node> N removeIn(N astRootNode) {
        return forEachIn(astRootNode, n -> n.ast().remove() );        
    }

    @Override
    public <N extends _node> N removeIn(N _n) {
        return forEachIn(_n, n -> n.ast().remove() );   
    }

    @Override
    public <N extends Node> N forEachIn(N astRootNode, Consumer<_anno> _nodeActionFn) {
        astRootNode.walk(AnnotationExpr.class, a-> {
            Select sel = select(_anno.of(a));
            if( sel != null ){
                _nodeActionFn.accept(sel._ann);
            }
        });
        return astRootNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<_anno> _nodeActionFn) {
        return Walk.in(_n, _anno.class, a-> {
            Select sel = select(a); 
            if( sel != null ){
                _nodeActionFn.accept( sel._ann );
            }
        }); 
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param selectActionFn
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astRootNode, Consumer<Select> selectActionFn) {
        astRootNode.walk(AnnotationExpr.class, a-> {
            Select sel = select(_anno.of(a));
            if( sel != null ){
                selectActionFn.accept(sel);
            }
        });
        return astRootNode;
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param selectConstraint
     * @param selectActionFn
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astRootNode, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn) {
        astRootNode.walk(AnnotationExpr.class, a-> {
            Select sel = select(_anno.of(a));
            if( sel != null && selectConstraint.test(sel)){
                selectActionFn.accept(sel);
            }
        });
        return astRootNode;
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param selectActionFn
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Consumer<Select> selectActionFn) {
        return Walk.in(_n, _anno.class, a-> {
            Select sel = select(a); 
            if( sel != null ){
                selectActionFn.accept( sel );
            }
        }); 
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConstraint
     * @param selectActionFn
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn) {
        return Walk.in(_n, _anno.class, a-> {
            Select sel = select(a); 
            if( sel != null && selectConstraint.test(sel)){
                selectActionFn.accept( sel );
            }
        }); 
    }
    
    /**
     * 
     * @param clazz
     * @param selectActionFn
     * @return 
     */
    public _type forSelectedIn(Class clazz, Consumer<Select> selectActionFn) {
        return forSelectedIn(_type.of(clazz), selectActionFn);         
    }

    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @param selectActionFn
     * @return 
     */
    public _type forSelectedIn(Class clazz, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn) {
        return forSelectedIn(_type.of(clazz), selectConstraint, selectActionFn);         
    }
    
    /**
     * 
     * @return 
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append("@");
        sb.append(this.name.pattern);
        if( this.$mvs.isEmpty() ){
            return sb.toString();
        }
        sb.append("(");
        for(int i=0;i<this.$mvs.size();i++){
            if( i > 0 ){
                sb.append(",");
            }
            sb.append( $mvs.toString() );            
        }
        sb.append(")");
        sb.append(System.lineSeparator());
        return sb.toString();        
    }
    
    /**
     * prototype for member values (i.e. the key values inside the annotation)
     * i.e. @A(key="value")
     */
    public static class $memberValue {

        public $id key = $id.any();
        
        public $component<String> value = new $component("$value$", t -> true);
        public Predicate<MemberValuePair> constraint = t -> true;

        public static $memberValue of(Expression value) {
            return new $memberValue("$_$", value);
        }

        public static $memberValue of(String key, String value) {
            return new $memberValue(key, value);
        }

        public String compose(Translator translator, Map<String, Object> keyValues) {
            String k = null;
            if( !key.isMatchAny() ){
                k = key.compose(translator, keyValues);
            }
            String v = value.compose(translator, keyValues);
            if (k == null || k.length() == 0) {
                return v;
            }
            return k + "=" + v;
        }

        public boolean isMatchAll() {
            return key.pattern.isMatchAny() && value.pattern.isMatchAny();
        }

        public static $memberValue of(String key, Expression exp) {
            return new $memberValue(key, exp);
        }

        public static $memberValue any() {
            return new $memberValue("$key$", "$value$");
        }

        public $memberValue(String name, String value) {
            this(name, Expr.of(value));
        }

        public $memberValue(String name, Expression value) {
            this.key.pattern = Stencil.of(name);
            this.value.pattern = Stencil.of(value.toString());
        }

        public $memberValue $(String target, String $name) {
            this.key.pattern = this.key.pattern.$(target, $name);
            this.value.pattern = this.value.pattern.$(target, $name);
            return this;
        }
        
        public Select selectFirst( List<MemberValuePair> pairs ){
            for(int i=0;i<pairs.size();i++){
                Select sel = select(pairs.get(i) );
                if( sel != null ){
                    return sel;
                }
            }            
            return null;
        }
        
        /**
         * 
         * @param mvp
         * @return 
         */
        public boolean matches(MemberValuePair mvp) {
            return select(mvp) != null;
        }

        /**
         * 
         * @param onlyValueExpression
         * @return 
         */
        public Tokens decompose(Expression onlyValueExpression){            
            if( constraint.test( new MemberValuePair("_", onlyValueExpression) ) ) {
                return value.decompose(onlyValueExpression.toString());
            }
            return null;
        }
        
        /**
         * 
         * @param mvp
         * @return 
         */
        public Tokens decompose(MemberValuePair mvp ){
            if (mvp == null) {
                return null;
            }
            if (constraint.test(mvp)) {
                Tokens ts = key.decompose(mvp.getNameAsString());
                ts = value.decomposeTo(mvp.getValue().toString(), ts);
                return ts;
            }
            return null;
        }
        
        /**
         * When we have an anno like
         * @param onlyValueExpression
         * @return 
         * @count(100)
         * (a SingleMemberAnnotationExpr)
         * 
         */
        public Select select (Expression onlyValueExpression){            
            MemberValuePair mvp = new MemberValuePair("", onlyValueExpression); 
            if( constraint.test( mvp ) ) {
                Tokens ts = value.decompose(onlyValueExpression.toString());
                return new Select(mvp, ts);
            }
            return null;
        }
        
        /**
         * 
         * @param mvp
         * @return 
         */
        public Select select(MemberValuePair mvp) {
            if (mvp == null) {
                return null;
            }
            if (constraint.test(mvp)) {
                Tokens ts = key.decompose(mvp.getNameAsString());
                ts = value.decomposeTo(mvp.getValue().toString(), ts);
                if( ts != null ){
                    return new Select(mvp, ts);
                }
            }
            return null;
        }

        public static class Select
            implements $proto.selected, selectedAstNode<MemberValuePair> {

            public final MemberValuePair astMvp;
            public final $args args;

            public Select(MemberValuePair astMvp, Tokens tokens) {
                this.astMvp = astMvp;
                this.args = $args.of(tokens);
            }

            @Override
            public $args args() {
                return args;
            }

            @Override
            public String toString() {
                return "$anno.$memberValue.Select {" + System.lineSeparator()
                        + Text.indent(astMvp.toString()) + System.lineSeparator()
                        + Text.indent("$args : " + args) + System.lineSeparator()
                        + "}";
            }

            /**
             * It's not a "true" member value pair, but rather a synthesized
             * MemberValue Pair with no name... this is for cases where we have an 
             * annotation like 
             * @name("Eric")
             * as apposed to cases where the key and value are spelled out
             * @name(value="Eric")
             * 
             * @return true if its a "value only" member value pair
             */
            public boolean isValueOnly(){
                return astMvp.getNameAsString().equals("_");
            }
            
            @Override
            public MemberValuePair ast() {
                return astMvp;
            }

            public String getName() {
                return astMvp.getNameAsString();
            }

            public Expression getValue() {
                return astMvp.getValue();
            }
        }
    }
    
    /**
     * A Matched Selection result returned from matching a prototype $a
     * inside of some Node or _node
     */
    public static class Select
        implements $proto.selected<_anno>, selected_model<_anno>, selectedAstNode<AnnotationExpr> {

        public final _anno _ann;
        public final $args args;

        public Select(_anno _a, Tokens tokens) {
            this(_a, $args.of(tokens));
        }

        public Select(_anno _a, $args tokens) {
            this._ann = _a;
            args = tokens;
        }

        public Select(AnnotationExpr astAnno, $args tokens) {
            this._ann = _anno.of( astAnno );
            this.args = tokens;
        }

        @Override
        public $args args() {
            return args;
        }

        @Override
        public String toString() {
            return "$anno.Select {" + System.lineSeparator()
                + Text.indent(_ann.toString()) + System.lineSeparator()
                + Text.indent("$args : " + args) + System.lineSeparator()
                + "}";
        }

        /**
         * Is the name of the selected _anno the name provided
         * @param name
         * @return 
         */
        public boolean isNamed(String name) {
            return _ann.isNamed(name);
        }

        public boolean isSingleValue() {
            return _ann.ast() instanceof SingleMemberAnnotationExpr;
        }

        public boolean hasKeyValues() {
            return _ann.ast() instanceof NormalAnnotationExpr;
        }

        public boolean hasNoValues() {
            return _ann.ast() instanceof MarkerAnnotationExpr;
        }

        @Override
        public AnnotationExpr ast() {
            return _ann.ast();
        }

        @Override
        public _anno model() {
            return _ann;
        }
    }
}
