package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.Name;
import com.github.javaparser.ast.expr.SimpleName;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import draft.Stencil;
import draft.Text;
import draft.Tokens;
import draft.java.Ast;
import draft.java.Walk;
import draft.java._model._node;
import draft.java._type;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Any Ast Node that can be matched, extracted
 * @author Eric
 */
public class _pNode implements _pQuery<Node> {
    
    public static <N extends Node> N replace(  N astRootNode, String source, String target ){
        return _pNode.of(source).replaceIn(astRootNode, target);
    }
    
    public static _pNode of( String name ){
        return new _pNode( name );
    }
    public Stencil pattern;
    
    public Predicate<Node> constraint = t -> true;
    
    public _pNode( String pattern ){
        this( Stencil.of(pattern) );
    }
    
    public _pNode( Stencil pattern ){
        this.pattern = pattern;
        this.constraint = t-> true;
    }
    
    public _pNode constraint( Predicate<Node> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    public _pNode $(String target, String $Name) {
        this.pattern = this.pattern.$(target, $Name);
        return this;
    }
    
    public List<String> list$() {
        return this.pattern.list$();
    }

    public List<String> list$Normalized() {
        return this.pattern.list$Normalized();
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
            if( this.constraint.test(n)) {
                Tokens ts = this.pattern.deconstruct( n.toString(Ast.PRINT_NO_COMMENTS) );
                if( ts != null ){
                    //System.out.println( "ADDING "+ n.getClass()+ "  "+n);
                    found.add(n);
                }
            }
        });
        return found;
    }

    @Override
    public List<Select> selectListIn(Node astRootNode) {
        List<Select> found = new ArrayList<>();
        astRootNode.walk(n -> {
            if( this.constraint.test(n)) {
                Tokens ts = this.pattern.deconstruct( n.toString(Ast.PRINT_NO_COMMENTS) );
                if( ts != null ){
                    found.add(new Select( n, ts) );
                }
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

    @Override
    public <N extends Node> N removeIn(N astRootNode) {
        astRootNode.walk(n -> {
            if( this.constraint.test(n)) {
                Tokens ts = this.pattern.deconstruct( n.toString(Ast.PRINT_NO_COMMENTS) );
                if( ts != null ){
                    n.removeForced();
                }                
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
                isRep = target.replace( Ast.typeRef( replacement.toString() ) );                
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
        //if( target.toString().equals( "draft.java.proto.$nodeTest.Ann" ) ){            
        //}                
        Node n = Ast.of(target.getClass(), replacement);
        
        return replaceNode( target, n);
        //target.replace( Ast.of(target.getClass(), replacement ));        
    }
     
    
    public <N extends _node> N replaceIn(N _n, _pNode $replacement) {
        if( _n instanceof _type && ((_type)_n).isTopClass()){            
            replaceIn( ((_type)_n).findCompilationUnit(), $replacement);
            return _n;
        }
        replaceIn(_n.ast(), $replacement);
        return _n;
    }
    
    public <N extends Node> N replaceIn(N astRootNode, _pNode $replacement) {
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
    
    public <N extends _node> N replaceIn(N _n, String replacement) {
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            replaceIn( ((_type)_n).findCompilationUnit(), replacement);
            return _n;
        }
        replaceIn(_n.ast(), replacement);
        return _n;
    }
    
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

    public <N extends _node> N replaceIn(N _n, Node replacement) {
        if( _n instanceof _type && ((_type)_n).isTopClass()){
            replaceIn( ((_type)_n).findCompilationUnit(), replacement);
            return _n;
        }
        replaceIn(_n.ast(), replacement);
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
     * A Matched Selection result returned from matching a prototype $field
     * inside of some Node or _node
     */
    public static class Select implements _pQuery.selected, _pQuery.selectedAstNode<Node> {
        public Node node;
        public args args;

        @Override
        public args getArgs(){
            return args;
        }
        
        public Select( Node node, Tokens tokens){
            this.node = node;
            this.args = args.of(tokens);
        }

        
        @Override
        public Node ast() {
            return node;
        }
        
        @Override
        public String toString(){
            return "$snip.Selected{"+ System.lineSeparator()+
                Text.indent( node.toString() )+ System.lineSeparator()+
                Text.indent("args : " + args) + System.lineSeparator()+
                "}";
        }

    }
}