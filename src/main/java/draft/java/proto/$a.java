package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.AnnotationDeclaration;
import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MarkerAnnotationExpr;
import com.github.javaparser.ast.expr.MemberValuePair;
import com.github.javaparser.ast.expr.NormalAnnotationExpr;
import com.github.javaparser.ast.expr.SingleMemberAnnotationExpr;
import draft.Stencil;
import draft.Template;
import draft.Text;
import draft.Tokens;
import draft.Translator;
import draft.java.Expr;
import draft.java.Walk;
import draft.java._anno;
import draft.java._model;
import draft.java._model._node;
import draft.java.proto.$proto.$component;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * This will be the NEW $a
 *
 * @author Eric
 */
public class $a
        implements Template<_anno>, $proto<_anno> {

     /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _model._node> _anno first( N _n, String proto ){
        return $a.of(proto).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @return 
     */
    public static final <N extends _model._node> _anno first( Node astNode, String proto ){
        return $a.of(proto).firstIn(astNode);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _model._node> _anno first( N _n, String proto, Predicate<_anno> constraint){
        return $a.of(proto, constraint).firstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _model._node> _anno first( Node astNode, String proto,Predicate<_anno>constraint){
        return $a.of(proto, constraint).firstIn(astNode);
    }
         
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _model._node> $a.Select selectFirst( N _n, String proto ){
        return $a.of(proto).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @return 
     */
    public static final <N extends _model._node> $a.Select selectFirst( Node astNode, String proto ){
        return $a.of(proto).selectFirstIn(astNode);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _model._node> $a.Select selectFirst( N _n, String proto, Predicate<_anno> constraint){
        return $a.of(proto, constraint).selectFirstIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param astNode
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _model._node> $a.Select selectFirst( Node astNode, String proto,Predicate<_anno>constraint){
        return $a.of(proto, constraint).selectFirstIn(astNode);
    }
    
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @return 
     */
    public static final <N extends _model._node> List<_anno> list( N _n ){
        return any().listIn(_n);
    }
    
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @param constraint
     * @return 
     */
    public static final <N extends _model._node> List<_anno> list( N _n, Predicate<_anno> constraint){
        return any().constraint(constraint).listIn(_n);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param proto
     * @return 
     */
    public static final <N extends _model._node> List<_anno> list( N _n, String proto ){
        return $a.of(proto).listIn(_n);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param proto
     * @param constraint
     * @return 
     */
    public static final <N extends _model._node> List<_anno> list( N _n, String proto, Predicate<_anno> constraint){
        return $a.of(proto, constraint).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @return 
     */
    public static final <N extends _model._node> List<_anno> list( N _n, _anno _proto ){
        return $a.of(_proto).listIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param constraint
     * @return 
     */
    public static final <N extends _model._node> List<_anno> list( N _n, _anno _proto, Predicate<_anno> constraint){
        return $a.of(_proto, constraint).listIn(_n);
    }
        
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _model._node> N forEach( N _n, Consumer<_anno> _annoConsumer){
        return any().forEachIn(_n, _annoConsumer);
    }
    
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @param constraint
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _model._node> N forEach( N _n, Predicate<_anno> constraint, Consumer<_anno> _annoConsumer){
        return any().constraint(constraint).forEachIn(_n, _annoConsumer);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param proto
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _model._node> N forEach( N _n, String proto, Consumer<_anno> _annoConsumer){
        return $a.of(proto).forEachIn(_n, _annoConsumer);
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
    public static final <N extends _model._node> N forEach( N _n, String proto, Predicate<_anno> constraint, Consumer<_anno> _annoConsumer){
        return $a.of(proto, constraint).forEachIn(_n, _annoConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _model._node> N forEach( N _n, _anno _proto , Consumer<_anno> _annoConsumer){
        return $a.of(_proto).forEachIn(_n, _annoConsumer);
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
    public static final <N extends _model._node> N forEach( N _n, _anno _proto, Predicate<_anno> constraint, Consumer<_anno> _annoConsumer){
        return $a.of(_proto, constraint).forEachIn(_n, _annoConsumer);
    }
    
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _model._node> N forSelected( N _n, Consumer<$a.Select> _annoConsumer){
        return any().forSelectedIn(_n, _annoConsumer);
    }
    
    /**
     * lists all annos within the node
     * @param <N>
     * @param _n
     * @param constraint
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _model._node> N forSelected( N _n, Predicate<_anno> constraint, Consumer<$a.Select> _annoConsumer){
        return any().constraint(constraint).forSelectedIn(_n, _annoConsumer);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param pattern
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _model._node> N forSelected( N _n, String pattern, Consumer<$a.Select> _annoConsumer){
        return $a.of(pattern).forSelectedIn(_n, _annoConsumer);
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
    public static final <N extends _model._node> N forSelected( N _n, String pattern, Predicate<_anno> constraint, Consumer<$a.Select> _annoConsumer){
        return $a.of(pattern, constraint).forSelectedIn(_n, _annoConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param _annoConsumer
     * @return 
     */
    public static final <N extends _model._node> N forSelected( N _n, _anno _proto , Consumer<$a.Select> _annoConsumer){
        return $a.of(_proto).forSelectedIn(_n, _annoConsumer);
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
    public static final <N extends _model._node> N forSelected( N _n, _anno _proto, Predicate<_anno> constraint, Consumer<$a.Select> _annoConsumer){
        return $a.of(_proto, constraint).forSelectedIn(_n, _annoConsumer);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _model._node> List<$a.Select> selectList( N _n, String pattern ){
        return $a.of(pattern).selectListIn(_n);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param constraint
     * @return 
     */
    public static final <N extends _model._node> List<$a.Select> selectList( N _n, Predicate<_anno> constraint ){
        return any().constraint(constraint).selectListIn(_n);
    }
    
    /**
     * lists all occurrences of annos that match the 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _model._node> List<$a.Select> selectList( N _n, String pattern, Predicate<_anno> constraint){
        return $a.of(pattern, constraint).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @return 
     */
    public static final <N extends _model._node> List<$a.Select> selectList( N _n, _anno _proto ){
        return $a.of(_proto).selectListIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param _proto
     * @param constraint
     * @return 
     */
    public static final <N extends _model._node> List<$a.Select> selectList( N _n, _anno _proto, Predicate<_anno> constraint){
        return $a.of(_proto, constraint).selectListIn(_n);
    }
    
    /**
     * Removes ALL annotations within _n
     * @param <N>
     * @param _n
     * @return the modified N
     */
    public static final <N extends _model._node> N remove( N _n ){
        return any().removeIn(_n);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param _proto
     * @return the modified N
     */
    public static final <N extends _model._node> N remove( N _n, _anno _proto ){
        return $a.of(_proto).removeIn(_n);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param constraint
     * @return the modified N
     */
    public static final <N extends _model._node> N remove( N _n, Predicate<_anno> constraint ){
        return $a.of( "@a" ).$("@a", "any").constraint(constraint).removeIn(_n);
    }
    
    /**
     * Removes all occurrences of the source anno in the rootNode (recursively)
     * @param <N>
     * @param _n
     * @param _proto
     * @param constraint
     * @return the modified N
     */
    public static final <N extends _model._node> N remove( N _n, _anno _proto, Predicate<_anno> constraint){
        return $a.of(_proto, constraint).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static final <N extends _model._node> N remove( N _n, String pattern ){
        return $a.of(pattern).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param constraint
     * @return 
     */
    public static final <N extends _model._node> N remove( N _n, String pattern, Predicate<_anno> constraint){
        return $a.of(pattern, constraint).removeIn(_n);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param sourceProto
     * @param targetProto
     * @return 
     */
    public static final <N extends _model._node> N replace(N _n, _anno sourceProto, _anno targetProto){
        return $a.of(sourceProto)
            .replaceIn(_n, $a.of(targetProto));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param sourcePattern
     * @param targetPattern
     * @return 
     */
    public static final <N extends _model._node> N replace(N _n, String sourcePattern, String targetPattern){
        return $a.of(sourcePattern)
            .replaceIn(_n, $a.of(targetPattern));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param astSourceProto
     * @param astTargetProto
     * @return 
     */
    public static final <N extends _model._node> N replace(N _n, AnnotationDeclaration astSourceProto, AnnotationDeclaration astTargetProto){
        return $a.of(_anno.of(astSourceProto))
            .replaceIn(_n, $a.of(_anno.of(astTargetProto)));
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param sourceProto
     * @param targetProto
     * @return 
     */
    public static final <N extends _model._node> N replace( N _n, Class<? extends Annotation>sourceProto, Class<? extends Annotation>targetProto){        
        return $a.of(sourceProto)
            .replaceIn(_n, $a.of(targetProto));
    }
    
    public static $a any(){
        return of("@A").$("A", "any");
    } 
    
    public static $a of(String... pattern) {
        return new $a(_anno.of(pattern));
    }
    
    public static $a of(String pattern, Predicate<_anno>constraint) {
        return new $a(_anno.of(pattern)).constraint(constraint);
    }
    

    public static $a of(_anno _an) {
        return new $a(_an);
    }

    public static $a of(_anno _an, Predicate<_anno>constraint) {
        return new $a(_an).constraint(constraint);
    }
        
    public static $a of(Class<? extends Annotation>sourceAnnoClass) {
        return new $a(_anno.of(sourceAnnoClass));
    }
    
    public static $a of(Class<? extends Annotation>sourceAnnoClass, String argumentStencil) {
        if( !argumentStencil.trim().startsWith("(") ){
            argumentStencil = "("+ argumentStencil + ")";
        }
        //String str = "@$annoName$"+argumentStencil;
        String str = "@"+sourceAnnoClass.getSimpleName()+argumentStencil;
        System.out.println("THE STR IS " + str );
        return new $a(_anno.of(str));
                //.constraint( a -> a.isInstance(sourceAnnoClass));
        
        //$a a = new $a(_anno.of(sourceAnnoClass));
        
        //return a;
    }
        
    /** Default Matching constraint (by default ALWAYS Match)*/
    public Predicate<_anno> constraint = a -> true;

    /** */
    public $id name;

    public List<$memberValue> $mvs = new ArrayList<>();

    public $a(_anno proto) {
        this.name = $id.of(proto.getName());
        //this.$name.$form = Stencil.of(proto.getName());
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
     * Sets the underlying constraint
     * @param constraint
     * @return 
     */
    public $a constraint(Predicate<_anno> constraint) {
        this.constraint = constraint;
        return this;
    }

    /**
     * ADDS an additional matching constraint to the prototype
     *
     * @param constraint a constraint to be added
     * @return the modified prototype
     */
    public $a addConstraint(Predicate<_anno> constraint) {
        this.constraint = this.constraint.and(constraint);
        return this;
    }

    public Tokens decompose(_anno _a) {
        //Tokens ts = $name.decompose(_a.getName());
        if( ! this.constraint.test(_a)){
            System.out.println( "Didnt pass MVP Constraint");
            return null;
        }
        
        Tokens ts = name.decompose(_a.getName());
        if ($mvs.isEmpty() || ts == null) {
            System.out.println( "Returning "+ts+" for name \""+_a.getName()+"\"");
            return ts;
        }
        AnnotationExpr astAnn = _a.ast();
        if (astAnn instanceof MarkerAnnotationExpr) {
            System.out.println( "Marketer");
            if ($mvs.size() == 1) {
                if ($mvs.get(0).isMatchAll()) {
                    return ts;
                }
                return null;
            }
            return null;
        }
        if (astAnn instanceof SingleMemberAnnotationExpr) {
            System.out.println( "SingleMember");
            if ($mvs.size() == 1) {
                SingleMemberAnnotationExpr sme = (SingleMemberAnnotationExpr) astAnn;
                Tokens props = $mvs.get(0).decompose(sme.getMemberValue());
                if( props == null ){
                    return null;
                }
                if (ts.isConsistent(props)) {                    
                    ts.putAll(props);
                    return ts;
                } //else{
                  //  System.out.println("Not consistent with "+ ts + props );
                // }
            }
            return null;
        }
        if (astAnn instanceof NormalAnnotationExpr) {
            System.out.println( "Normal "+ astAnn );
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

    public boolean matches(String... anno) {
        return matches(_anno.of(anno));
    }

    public boolean matches(_anno _a) {
        return select(_a) != null;
    }

    @Override
    public _anno construct(Translator translator, Map<String, Object> keyValues) {
        StringBuilder sb = new StringBuilder();
        //sb.append($name.compose(translator, keyValues));
        sb.append(name.compose(translator, keyValues));
        String properties = "";
        for (int i = 0; i < $mvs.size(); i++) {
            if ((properties.length() > 0) && (!properties.endsWith(","))) {
                properties = properties + ",";
            }
            properties += $mvs.get(i).compose(translator, keyValues);
        }
        if (properties.length() == 0) {
            return _anno.of(sb.toString());
        }
        return _anno.of(sb.toString() + "(" + properties + ")");
    }

    @Override
    public $a $(String target, String $Name) {
        name.$(target, $Name);
        $mvs.forEach(mv -> mv.name.$form = mv.name.$form.$(target, $Name));
        return this;
    }

    public Select select( AnnotationExpr astAnn){
        return select(_anno.of(astAnn));
    }
    
    public Select select(String... anno){
        return select(_anno.of(anno));
    }
    
    public Select select(_anno _a){
        if(this.constraint.test(_a)){
            System.out.println( "Passed constraint");
            Tokens ts = decompose(_a);
            System.out.println( "   TOKENS "+ ts);
            if( ts != null ){
                System.out.println( "   TOKENS "+ ts);
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
            params.addAll( m.name.$form.list$() ); 
            params.addAll( m.value.$form.list$() ); 
        });
        return params;
    }

    @Override
    public List<String> list$Normalized() {
        List<String> params = list$();
        return params.stream().distinct().collect(Collectors.toList() );
    }

    @Override
    public List<_anno> listIn(_model._node _n) {
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
     * @param _n the model to find replacements
     * @param a the template to be constructed as the replacement
     * @param <N> the TYPE of model
     * @return
     */
    public <N extends _node> N replaceIn(N _n, $a a ){
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
     * @param $a
     * @return 
     */
    public <N extends Node> N replaceIn(N astNode, $a a ){
        astNode.walk(AnnotationExpr.class, e-> {
            Select sel = select( e );
            if( sel != null ){
                sel._ann.ast().replace(a.construct(sel.args).ast() );
            }
        });
        return astNode;
    }
    
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

    public _anno firstIn(_model._node _n) {
        return firstIn( _n.ast() );        
    }
    
    
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

    public Select selectFirstIn(_model._node _n) {
        return selectFirstIn( _n.ast() );        
    }
    
    
    @Override
    public List<Select> selectListIn(Node astRootNode) {
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
    public List<Select> selectListIn(_model._node _n) {
        List<Select> found = new ArrayList<>();
        Walk.in(_n, _anno.class, a-> {
            Select sel = select(a); 
            if( sel != null ){
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
    public <N extends _model._node> N removeIn(N _n) {
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
    public <N extends _model._node> N forEachIn(N _n, Consumer<_anno> _nodeActionFn) {
        return Walk.in(_n, _anno.class, a-> {
            Select sel = select(a); 
            if( sel != null ){
                _nodeActionFn.accept( sel._ann );
            }
        }); 
    }
    
    public <N extends Node> N forSelectedIn(N astRootNode, Consumer<Select> selectActionFn) {
        astRootNode.walk(AnnotationExpr.class, a-> {
            Select sel = select(_anno.of(a));
            if( sel != null ){
                selectActionFn.accept(sel);
            }
        });
        return astRootNode;
    }

    public <N extends _model._node> N forSelectedIn(N _n, Consumer<Select> selectActionFn) {
        return Walk.in(_n, _anno.class, a-> {
            Select sel = select(a); 
            if( sel != null ){
                selectActionFn.accept( sel );
            }
        }); 
    }

    public static class $memberValue {

        public $component<String> name = new $component("$key$", t -> true);
        public $component<String> value = new $component("$value$", t -> true);
        public Predicate<MemberValuePair> constraint = t -> true;

        public static $memberValue of(Expression value) {
            return new $memberValue("$_$", value);
        }

        public static $memberValue of(String key, String value) {
            return new $memberValue(key, value);
        }

        public String compose(Translator translator, Map<String, Object> keyValues) {
            String k = name.compose(translator, keyValues);
            String v = value.compose(translator, keyValues);
            if (k == null || k.length() == 0) {
                return v;
            }
            return k + "=" + v;
        }

        public boolean isMatchAll() {
            return name.$form.isMatchAll() && value.$form.isMatchAll();
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
            this.name.$form = Stencil.of(name);
            this.value.$form = Stencil.of(value.toString());
        }

        public $memberValue $(String target, String $name) {
            this.name.$form = this.name.$form.$(target, $name);
            this.value.$form = this.value.$form.$(target, $name);
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
        
        public Tokens decompose(MemberValuePair mvp ){
            if (mvp == null) {
                return null;
            }
            if (constraint.test(mvp)) {
                Tokens ts = name.decompose(mvp.getNameAsString());
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
                Tokens ts = name.decompose(mvp.getNameAsString());
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
            public $args getArgs() {
                return args;
            }

            @Override
            public String toString() {
                return "$memberValue.Select {" + System.lineSeparator()
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

    public static class $value {
        public $component<String> value = new $component("$value$", t -> true);
    }

    /**
     * A Matched Selection result returned from matching a prototype $a
     * inside of some Node or _node
     */
    public static class Select
        implements $proto.selected, selected_model<_anno>, selectedAstNode<AnnotationExpr> {

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
        public $args getArgs() {
            return args;
        }

        @Override
        public String toString() {
            return "$a.Select {" + System.lineSeparator()
                + Text.indent(_ann.toString()) + System.lineSeparator()
                + Text.indent("$args : " + args) + System.lineSeparator()
                + "}";
        }

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
