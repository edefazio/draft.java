package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import draft.Stencil;
import draft.Text;
import draft.Tokens;
import draft.java.Ast;
import draft.java._model._node;
import draft.java._type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Any Ast Node that can be matched, extracted, Selected, Removed, Replaced
 * 
 * usually a simple String pattern (or Node-based lambda) will suffice 
 * 
 * sometimes we need this because the "type" of the thing that we are looking for 
 * is not always important
 * 
 * @author Eric
 */
public class $node implements $proto<Node> {
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param pattern
     * @param selectConsumer
     * @return 
     */
    public static <N extends Node> N forEach(N astRootNode, String pattern, Consumer<Node> selectConsumer){
        return $node.of(pattern)
            .forEachIn(astRootNode, selectConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConsumer
     * @return 
     */
    public static <N extends _node> N forEach(N _n, String pattern, Consumer<Node> selectConsumer){
        return $node.of(pattern)
            .forEachIn(_n, selectConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param pattern
     * @param selectConsumer
     * @return 
     */
    public static <N extends Node> N forSelected(N astRootNode, String pattern, Consumer<Select> selectConsumer){
        return $node.of(pattern).forSelectedIn(astRootNode, selectConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @param selectConsumer
     * @return 
     */
    public static <N extends _node> N forSelected(N _n, String pattern, Consumer<Select> selectConsumer){
        return $node.of(pattern).forSelectedIn(_n, selectConsumer);
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param matchPattern
     * @param targetPattern
     * @return 
     */
    public static <N extends Node> N replace( N astRootNode, String matchPattern, String targetPattern ){
        return $node.of(matchPattern).replaceIn(astRootNode, targetPattern);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param matchPattern
     * @param targetPattern
     * @return 
     */
    public static <N extends _node> N replace( N _n, String matchPattern, String targetPattern ){
        return $node.of(matchPattern).replaceIn(_n, targetPattern);
    }

    /**
     * 
     * @param clazz the runtime Class 
     * @param matchPattern
     * @param targetPattern
     * @return 
     */
    public static _type replace( Class clazz, String matchPattern, String targetPattern ){
        return $node.of(matchPattern).replaceIn(_type.of(clazz), targetPattern);
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param pattern
     * @return 
     */
    public static <N extends Node> N remove( N astRootNode, String pattern ){
        return $node.of(pattern).removeIn(astRootNode );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static <N extends _node> N remove( N _n, String pattern ){
        return $node.of(pattern).removeIn(_n);
    }

    /**
     * 
     * @param clazz the runtime Class 
     * @param pattern
     * @return 
     */
    public static _type remove( Class clazz, String pattern ){
        return $node.of(pattern).removeIn(_type.of(clazz));
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static List<Node> list( Class clazz, String pattern ){
        return list(_type.of(clazz), pattern);
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param pattern
     * @return 
     */
    public static <N extends Node> List<Node> list( N astRootNode, String pattern ){
        return $node.of(pattern).listIn(astRootNode );
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param pattern
     * @return 
     */
    public static <N extends _node> List<Node> list( N _n, String pattern ){
        return $node.of(pattern).listIn(_n );
    }
    
    /**
     * 
     * @param clazz
     * @param pattern
     * @return 
     */
    public static List<Select> listSelected( Class clazz, String pattern ){
        return listSelected(_type.of(clazz), pattern);
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param pattern
     * @return 
     */
    public static <N extends Node> List<Select> listSelected( N astRootNode, String pattern ){
        return $node.of(pattern).listSelectedIn(astRootNode );
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param pattern
     * @return 
     */
    public static <N extends _node> List<Select> listSelected( N astRootNode, String pattern ){
        return $node.of(pattern).listSelectedIn(astRootNode );
    }
    
    /**
     * 
     * @param name
     * @return 
     */
    public static $node of( String name ){
        return new $node( name );
    }
    
    /**
     * 
     * @param constraint
     * @return 
     */
    public static $node of( Predicate<Node> constraint ){
        return new $node("$name$").addConstraint(constraint);
    }
    
    /** the string pattern */
    public Stencil pattern;
    
    public Predicate<Node> constraint = t -> true;
    
    public $node( String pattern ){
        this( Stencil.of(pattern) );
    }
    
    public $node( Stencil pattern ){
        this.pattern = pattern;
        this.constraint = t-> true;
    }
    
    /**
     * 
     * @param constraint
     * @return 
     
    public $node constraint( Predicate<Node> constraint ){
        this.constraint = constraint;
        return this;
    }
    */ 
    
    /**
     * ADDS an additional matching constraint to the prototype
     * @param constraint a constraint to be added
     * @return the modified prototype
     */
    public $node addConstraint( Predicate<Node>constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
   /**
    * 
    * @param target
    * @param $Name
    * @return 
    */
    public $node $(String target, String $Name) {
        this.pattern = this.pattern.$(target, $Name);
        return this;
    }
    
    /**
     * 
     * @return 
     */
    public List<String> list$() {
        return this.pattern.list$();
    }

    /**
     * 
     * @return 
     */
    public List<String> list$Normalized() {
        return this.pattern.list$Normalized();
    }

    /**
     * 
     * @param astNode
     * @return 
     */
    public Select select(Node astNode){
        if( this.constraint.test(astNode)) {
            Tokens ts = this.pattern.deconstruct( astNode.toString(Ast.PRINT_NO_COMMENTS) );
            if( ts != null ){
                return new Select( astNode, ts);
            }
        }
        return null;
    }
    
    /**
     * Returns the first Statement that matches the 
     * @param astNode the 
     * @return 
     */
    @Override
    public Node firstIn( Node astNode ){
        Optional<Node> f = 
                
            astNode.findFirst( Node.class, 
                n -> select(n) != null );         
        
        if( f.isPresent()){
            return f.get();
        }
        return null;
    }    
    
    /**
     * Selects the first instance
     * @param astNode
     * @return 
     */
    @Override
    public Select selectFirstIn( Node astNode ){
        Optional<Node> f = 
                
            astNode.findFirst( Node.class, 
                n -> select(n) != null );         
        
        if( f.isPresent()){
            return select(f.get());
        }
        return null;
    }
    
    @Override
    public Node firstIn( _node _n ){
        return firstIn( _n.ast() );
    }
    
    @Override
    public List<Node> listIn(_node _n) {
        if( _n instanceof _type && ((_type)_n).isTopLevel()){
            return listIn( ((_type)_n).astCompilationUnit() );
        }
        return listIn(_n.ast());
    }

    @Override
    public List<Node> listIn(Node astRootNode) {
        List<Node> found = new ArrayList<>();
        astRootNode.walk(n -> {
            Select select = select(n);
            if( select != null ){
                found.add(n);
            }            
        });
        return found;
    }

    @Override
    public List<Select> listSelectedIn(Node astRootNode) {
        List<Select> found = new ArrayList<>();
        astRootNode.walk(n -> {
            Select select = select(n);
            if( select != null ){
                found.add(select); 
            }            
        });
        return found;
    }

    @Override
    public List<Select> listSelectedIn(_node _n) {
        if( _n instanceof _type && ((_type)_n).isTopLevel()){
            return listSelectedIn( ((_type)_n).astCompilationUnit() );
        }
        return listSelectedIn(_n.ast());
    }

    /**
     * 
     * @param astRootNode
     * @param selectConstraint
     * @return 
     */
    public List<Select> selectListIn(Node astRootNode, Predicate<Select> selectConstraint) {
        List<Select> found = new ArrayList<>();
        astRootNode.walk(n -> {
            Select select = select(n);
            if( select != null && selectConstraint.test(select)){
                found.add(select); 
            }            
        });
        return found;
    }

    /**
     * 
     * @param _n
     * @param selectConstraint
     * @return 
     */
    public List<Select> selectListIn(_node _n, Predicate<Select> selectConstraint) {
        if( _n instanceof _type && ((_type)_n).isTopLevel()){
            return selectListIn( ((_type)_n).astCompilationUnit(), selectConstraint );
        }
        return selectListIn(_n.ast(), selectConstraint);
    }
    
    @Override
    public <N extends Node> N removeIn(N astRootNode) {
        astRootNode.walk(n -> {
            Select select = select( n );
            if( select != null ){
                n.removeForced();
            }            
        });
        return astRootNode;
    }

    @Override
    public <N extends _node> N removeIn(N _n) {
        if( _n instanceof _type && ((_type)_n).isTopLevel()){
            removeIn( ((_type)_n).astCompilationUnit() );
            return _n;
        }
        removeIn(_n.ast());
        return _n;
    }
    
    private static boolean replaceNode( Node target, Node replacement ){
        boolean isRep = false;
        
        if( target.getClass() != replacement.getClass() ){            
            if( target instanceof ClassOrInterfaceType ){
                isRep = target.replace( Ast.typeRef( replacement.toString() ) );                
            } else if( target instanceof Name) {
                isRep = target.replace( new Name( replacement.toString()) );                
            } else if( target instanceof SimpleName) {
                isRep = target.replace( new SimpleName( replacement.toString()) );
            } else if( target instanceof VariableDeclarator ){
                System.out.println( "replacing Variable "+ target);
                VariableDeclarator vd = (VariableDeclarator)target;
                vd.setName(replacement.toString());
                isRep = true;
            }
        }
        //
        if( ! isRep ){
            isRep = target.replace(replacement);                                
        }
        return isRep;        
    }
    
    private static boolean replaceNode( Node target, String replacement ){
        if( target instanceof VariableDeclarator ){
            //since a Variable Declarator CAN match
            ((VariableDeclarator)target).setName(replacement);
            return true;
        }  
        Node n = Ast.of(target.getClass(), replacement);        
        return replaceNode( target, n);                        
    }
     
    /**
     * 
     * @param <N>
     * @param _n
     * @param $replacement
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, $node $replacement) {
        if( _n instanceof _type && ((_type)_n).isTopLevel()){            
            replaceIn( ((_type)_n).astCompilationUnit(), $replacement);
            return _n;
        }
        replaceIn(_n.ast(), $replacement);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param $replacement
     * @return 
     */
    public <N extends Node> N replaceIn(N astRootNode, $node $replacement) {
        astRootNode.walk(n -> {
            if( this.constraint.test(n) ) {
                Tokens ts = this.pattern.deconstruct( n.toString(Ast.PRINT_NO_COMMENTS) );
                if( ts != null ){
                    String constructed = $replacement.pattern.construct(ts);
                    if( ! replaceNode( n, constructed ) ){
                        //System.out.println("DIDNT REPLACE "+ n);
                    }                    
                }                
            }
        });
        return astRootNode;
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param replacement
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, String replacement) {
        if( _n instanceof _type && ((_type)_n).isTopLevel()){
            replaceIn( ((_type)_n).astCompilationUnit(), replacement);
            return _n;
        }
        replaceIn(_n.ast(), replacement);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param replacement
     * @return 
     */
    public <N extends Node> N replaceIn(N astRootNode, String replacement) {
        astRootNode.walk(n -> {
            if( this.constraint.test(n)) {
                String st = n.toString(Ast.PRINT_NO_COMMENTS);
                Tokens ts = this.pattern.deconstruct( st );
                if( ts != null ){
                    System.out.println( "replacing "+ n +" of "+n.getClass()+" with "+ replacement );
                    boolean isRep = replaceNode( n, replacement );                    
                    if( !isRep ){
                    }
                }                
            }
        });
        return astRootNode;
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param replacement
     * @return 
     */
    public <N extends Node> N replaceIn(N astRootNode, Node replacement) {
        astRootNode.walk(n -> {
            if( this.constraint.test(n)) {
                Tokens ts = this.pattern.deconstruct( n.toString(Ast.PRINT_NO_COMMENTS) );
                if( ts != null ){
                    replaceNode( n, replacement );                    
                }                
            }
        });
        return astRootNode;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param replacement
     * @return 
     */
    public <N extends _node> N replaceIn(N _n, Node replacement) {
        if( _n instanceof _type && ((_type)_n).isTopLevel()){
            replaceIn( ((_type)_n).astCompilationUnit(), replacement);
            return _n;
        }
        replaceIn(_n.ast(), replacement);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param selectActionFn
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astRootNode, Consumer<Select> selectActionFn) {
         astRootNode.walk(n -> {
            Select sel = select(n);
            if( sel != null ){
                selectActionFn.accept(sel);
            }            
        });
        return astRootNode;
    }

    /**
     * 
     * @param <N>
     * @param _n
     * @param nodeActionFn
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Consumer<Select> nodeActionFn) {
        if( _n instanceof _type && ((_type)_n).isTopLevel()){
            forSelectedIn( ((_type)_n).astCompilationUnit(), nodeActionFn);
            return _n;
        }
        forSelectedIn(_n.ast(), nodeActionFn);
        return _n;
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param selectConstraint
     * @param selectActionFn
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astRootNode, Predicate<Select>selectConstraint, Consumer<Select> selectActionFn) {
         astRootNode.walk(n -> {
            Select sel = select(n);
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
     * @param selectConstraint
     * @param nodeActionFn
     * @return 
     */
    public <N extends _node> N forSelectedIn(N _n, Predicate<Select> selectConstraint, Consumer<Select> nodeActionFn) {
        if( _n instanceof _type && ((_type)_n).isTopLevel()){
            forSelectedIn( ((_type)_n).astCompilationUnit(), selectConstraint, nodeActionFn);
            return _n;
        }
        forSelectedIn(_n.ast(), selectConstraint, nodeActionFn);
        return _n;
    }    
    
    @Override
    public <N extends Node> N forEachIn(N astRootNode, Consumer<Node> nodeActionFn) {
         astRootNode.walk(n -> {
            if( this.constraint.test(n)) {
                Tokens ts = this.pattern.deconstruct( n.toString(Ast.PRINT_NO_COMMENTS) );
                if( ts != null ){
                    nodeActionFn.accept(n);
                }
            }
        });
        return astRootNode;
    }

    @Override
    public <N extends _node> N forEachIn(N _n, Consumer<Node> nodeActionFn) {
        if( _n instanceof _type && ((_type)_n).isTopLevel()){
            forEachIn( ((_type)_n).astCompilationUnit(), nodeActionFn);
            return _n;
        }
        forEachIn(_n.ast(), nodeActionFn);
        return _n;
    }
    
    /**
     * Singleton that compares the start position of Select entities 
     * (based on the Ast node that was selected )
     */
    public static final SelectStartPositionComparator SELECT_START_POSITION_COMPARATOR = 
        new SelectStartPositionComparator();   
    
    /**
     * A compares the start position of Select Ast node entities
     */
    public static class SelectStartPositionComparator implements Comparator<$proto.selectedAstNode>{
        @Override
        public int compare($proto.selectedAstNode o1, $proto.selectedAstNode o2) {
            return Ast.COMPARE_NODE_BY_LOCATION.compare(o1.ast(), o2.ast());
        }        
    }
    
    /**
     * A Matched Selection result returned from matching a prototype Node
     * @param <T> the underlying Node implementation, because classUse can be
     * a ClassOrInterfaceType, or a SimpleName, or a Name depending on the 
     * scenario and how it is used (in an annotation, a throws class, an extends
     * implements, CastExpr, etc.)
     */     
    public static class Select<T extends Node> implements $proto.selected, $proto.selectedAstNode<T> {
        public T node;
        public $args args;

        @Override
        public $args args(){
            return args;
        }
        
        public Select( T node, Tokens tokens){
            this.node = node;
            this.args = args.of(tokens);
        }

        @Override
        public T ast() {
            return node;
        }
        
        @Override
        public String toString(){
            return "$node.Select{"+ System.lineSeparator()+
                Text.indent( node.toString() )+ System.lineSeparator()+
                Text.indent("$args : " + args) + System.lineSeparator()+
                "}";
        }
        
        public boolean isName(){
            return node instanceof Name;
        }
        
        public boolean isSimpleName(){
            return node instanceof SimpleName;
        }
        
        public boolean isClassOrInterfaceType(){
            return node instanceof ClassOrInterfaceType;
        }
    }
}