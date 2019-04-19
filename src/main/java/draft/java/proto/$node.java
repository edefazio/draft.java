package draft.java.proto;

import com.github.javaparser.ast.Node;
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
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Any Ast Node that can be matched, extracted
 * @author Eric
 */
public class $node implements $proto<Node> {
    
    public static <N extends Node> N replace(  N astRootNode, String source, String target ){
        return $node.of(source).replaceIn(astRootNode, target);
    }
    
    public static $node of( String name ){
        return new $node( name );
    }
    
    /**
     * the string pattern
     */
    public Stencil pattern;
    
    public Predicate<Node> constraint = t -> true;
    
    public $node( String pattern ){
        this( Stencil.of(pattern) );
    }
    
    public $node( Stencil pattern ){
        this.pattern = pattern;
        this.constraint = t-> true;
    }
    
    public $node constraint( Predicate<Node> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    /**
     * ADDS an additional matching constraint to the prototype
     * @param constraint a constraint to be added
     * @return the modified prototype
     */
    public $node addConstraint( Predicate<Node>constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    public $node $(String target, String $Name) {
        this.pattern = this.pattern.$(target, $Name);
        return this;
    }
    
    public List<String> list$() {
        return this.pattern.list$();
    }

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
    
    @Override
    public List<Node> listIn(_node _n) {
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            return listIn( ((_type)_n).findCompilationUnit() );
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
    public List<Select> selectListIn(Node astRootNode) {
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
    public List<Select> selectListIn(_node _n) {
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            return selectListIn( ((_type)_n).findCompilationUnit() );
        }
        return selectListIn(_n.ast());
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
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            return selectListIn( ((_type)_n).findCompilationUnit(), selectConstraint );
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
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            removeIn( ((_type)_n).findCompilationUnit() );
            return _n;
        }
        removeIn(_n.ast());
        return _n;
    }
    
    private static boolean replaceNode( Node target, Node replacement ){
        boolean isRep = false;
        
        //System.out.println( "replacing "+target+" with "+ replacement);
        if( target.getClass() != replacement.getClass() ){
            
            //System.out.println( "replacing "+target.getClass()+" with "+ replacement.getClass());
            if( target instanceof ClassOrInterfaceType ){
                //System.out.println("   <amual replace");
                isRep = target.replace( Ast.typeDecl( replacement.toString() ) );                
            }else if( target instanceof Name) {
                //System.out.println("   <amual replace");
                isRep = target.replace( new Name( replacement.toString()) );                
            } else if( target instanceof SimpleName) {
                //System.out.println("   <amual replace");
                isRep = target.replace( new SimpleName( replacement.toString()) );
            }
        }
        //
        if( ! isRep ){      
            //System.out.println( " NEITHER ");
            isRep = target.replace(replacement);                                
        }
        //System.out.println( isRep + " "+ target+" "+replacement );
        return isRep;        
    }
    
    private static boolean replaceNode( Node target, String replacement ){
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
        if( _n instanceof _type && ((_type)_n).isTopClass()){            
            replaceIn( ((_type)_n).findCompilationUnit(), $replacement);
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
            //System.out.println( "+++++ ON NODE "+ n );
            if( this.constraint.test(n) ) {
                //System.out.println( "   -> Passed Constraint "+ n );
                Tokens ts = this.pattern.deconstruct( n.toString(Ast.PRINT_NO_COMMENTS) );
                if( ts != null ){
                    //System.out.println( "   -> Tokens "+ ts );
                    //specialize the replacement node
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
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            replaceIn( ((_type)_n).findCompilationUnit(), replacement);
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
        //System.out.println( "IN WALK PATTERN "+ this.pattern );
        
        astRootNode.walk(n -> {
               //System.out.println( "   !>>>" + n.getClass() );
            if( this.constraint.test(n)) {
                String st = n.toString(Ast.PRINT_NO_COMMENTS);
                //System.out.println( "   >>>"+st);
                Tokens ts = this.pattern.deconstruct( st );
                if( ts != null ){
                    //System.out.println( " ***** FOUND "+this.pattern);   
                    
                    boolean isRep = replaceNode( n, replacement );                    
                    if( !isRep ){
                        //System.out.println( "NOT REPALCED");
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
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            replaceIn( ((_type)_n).findCompilationUnit(), replacement);
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
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            forSelectedIn( ((_type)_n).findCompilationUnit(), nodeActionFn);
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
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            forSelectedIn( ((_type)_n).findCompilationUnit(), selectConstraint, nodeActionFn);
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
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            forEachIn( ((_type)_n).findCompilationUnit(), nodeActionFn);
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
        public $args getArgs(){
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
            return "$node.Selected{"+ System.lineSeparator()+
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