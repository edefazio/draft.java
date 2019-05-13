package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.SwitchEntry;
import draft.Tokens;
import draft.java.Ast;
import draft.java._model._node;
import draft.java.proto.$proto.$nameValues;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * case within a Switch Statement
 * @author Eric
 */
public class $case 
    implements $proto<SwitchEntry> {
    
    //there seems to only ever be STATEMENT GROUPS
    //public static final SwitchEntry.Type STATEMENT_GROUP = SwitchEntry.Type.STATEMENT_GROUP;
    //public static final SwitchEntry.Type EXPRESSION = SwitchEntry.Type.EXPRESSION;
    //public static final SwitchEntry.Type BLOCK = SwitchEntry.Type.BLOCK;
    //public static final SwitchEntry.Type THROWS_STATEMENT = SwitchEntry.Type.THROWS_STATEMENT;

    public static $case of( String...acase ){
        return of( Ast.switchEntry(acase ));
    }
    
    public static $case any(){
        return new $case( $expr.any() );
    }
    
    public static $case of( SwitchEntry astSwitchEntry ){
        return new $case(astSwitchEntry );
    }
    
    public static $case of( $expr expr, $stmt...stmts ){
        return new $case(expr, stmts);
    }
    
    public $expr label = $expr.any();
    
    public List<$stmt> statements = new ArrayList<>();
       
    private $case( $expr $labelExpr ){
        this.label = $labelExpr; 
    }
    
    private $case($expr $labelExpr, $stmt...stmts){
        this.label = $labelExpr;
        Arrays.stream(stmts).forEach(s -> this.statements.add(s));        
    }
    
    
    public $case(SwitchEntry se){   
        if( se.getLabels().isNonEmpty() ){
            this.label = $expr.of( se.getLabels().get( 0 ) );
        } 
        for(int i=0;i<se.getStatements().size(); i++){
            this.statements.add($stmt.of(se.getStatements().get(i))); 
        }
    }
    
    public boolean matches( String... switchCase ){
        return select(switchCase) != null;
    }
    
    public boolean matches(SwitchEntry switchEntry ){
        return select(switchEntry) != null;
    }
    
    
    
    private Select selectStatements(SwitchEntry astSwitchEntry, Tokens tokens){
        //System.out.println( "In selectStatements");
        if( statements.isEmpty()){
            //System.out.println("Statments empty");
            return new Select( astSwitchEntry, $proto.$nameValues.of(tokens) );
        }
        if( statements.size() != astSwitchEntry.getStatements().size() ){
            //System.out.println( "Statement size mismatch");
            return null;
        }
        Tokens ts = Tokens.of(tokens);
            
        for(int i=0;i< this.statements.size();i++){
            $stmt.Select ss = 
                statements.get(i).select(astSwitchEntry.getStatement(i));
            if( ss == null || !ts.isConsistent(ss.args.asTokens()) ){
                //System.out.println( "NOT consistent with "+i+" " );
                return null;
            }
            ts.putAll(ss.args.asTokens());                
        }
        return new Select(astSwitchEntry, $nameValues.of(ts));
    }
    
    public Select select( String... switchCase ){
        return select( Ast.switchCase(switchCase));
    }
    
    public Select select( SwitchEntry astSwitchEntry ){
        if( astSwitchEntry.getLabels().isEmpty() ){
            System.out.println( "test label is null");  
            if( this.label == null ){
                System.out.println( "$case label is null");                
                return selectStatements( astSwitchEntry, new Tokens());
            }
            if( this.label.isMatchAny() ){
                System.out.println( "isMatchAny");                
                return selectStatements( astSwitchEntry, new Tokens());
            }
            return null;
        }
        Expression label = astSwitchEntry.getLabels().get(0);
        System.out.println("the label is "+ label);
        if( this.label == null ){
            System.out.println( "$case label is null");                
        }
        $expr.Select sel = this.label.select(label);
        
        if( sel != null ){
            System.out.println("Matced the label "+ label+" to "+this.label );
            return selectStatements(astSwitchEntry, sel.args.asTokens());            
        }
        return null;
    }

    @Override
    public SwitchEntry firstIn(Node astRootNode) {
        Optional<SwitchEntry> ose = 
            astRootNode.findFirst(SwitchEntry.class, se -> select(se) != null );
        if( ose.isPresent() ){
            return ose.get();
        }
        return null;                
    }

    @Override
    public Select selectFirstIn(Node n) {
        Optional<SwitchEntry> ose = 
            n.findFirst(SwitchEntry.class, se -> select(se) != null );
        if( ose.isPresent() ){
            return select(ose.get());
        }
        return null;                
    }

    @Override
    public List<SwitchEntry> listIn(Node astRootNode) {
        List<SwitchEntry> found = new ArrayList<>();
        forEachIn( astRootNode, s-> found.add(s));
        return found;        
    }

    @Override
    public List<Select> listSelectedIn(Node astRootNode) {
        List<Select> found = new ArrayList<>();
        forEachIn( astRootNode, s-> found.add(select(s)));
        return found;        
    }
    
    public <N extends _node> List<Select> listSelectedIn(N _n, Predicate<Select> selectConstraint) {
        return listSelectedIn( _n.ast() );        
    }
    
    public List<Select> listSelectedIn(Node astRootNode, Predicate<Select> selectConstraint) {
        List<Select> found = new ArrayList<>();
        forEachIn( astRootNode, s-> {
            Select sel = select(s);
            if( selectConstraint.test(sel ) ){
                found.add( sel );
            }
        });
        return found;        
    }

    @Override
    public <N extends Node> N forEachIn(N astRootNode, Consumer<SwitchEntry> _nodeActionFn) {
        astRootNode.walk(SwitchEntry.class, se-> {
            if( matches(se)) {
                _nodeActionFn.accept(se);
            }
        });
        return astRootNode;
    }

    @Override
    public <N extends Node> N removeIn(N astRootNode) {
        return forEachIn(astRootNode, n -> n.remove() );
    }
    
    
    public static class Select 
        implements $proto.selected<SwitchEntry>, $proto.selectedAstNode<SwitchEntry>{
        
        public SwitchEntry astCase;
        public $proto.$nameValues args;
        
        public Select( SwitchEntry astCase, $nameValues $nv){
            this.astCase = astCase;
            this.args = $nv;
        }

        @Override
        public $proto.$nameValues args() {
            return args;
        }

        @Override
        public SwitchEntry ast() {
            return astCase;
        }        
    }     
}
