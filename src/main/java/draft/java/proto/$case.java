package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.stmt.*;
import draft.*;
import draft.java.*;
import draft.java._java._node;
import draft.java.proto.$proto.$args;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * case within a Switch Statement
 * @author Eric
 */
public class $case 
    implements $proto<SwitchEntry>, Template<SwitchEntry> {

    public static $case of( Predicate<SwitchEntry> constraint ){
        return any().addConstraint(constraint);
    }
    
    public static $case of( String...acase ){
        return of( Ast.switchEntry(acase ));
    }
    
    public static $case any(){
        return new $case( $expr.any() );
    }
    
    public static $case of( SwitchEntry astSwitchEntry ){
        return new $case(astSwitchEntry );
    }
    
    public static $case of( SwitchEntry astSwitchEntry, Predicate<SwitchEntry> constraint){
        return new $case(astSwitchEntry).addConstraint(constraint);
    }
    
    public static $case of( $expr expr, $stmt...stmts ){
        return new $case(expr, stmts);
    }
    
    public Predicate<SwitchEntry> constraint = t-> true;
    
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
    
    public $case $label(){
        this.label = $expr.any();
        return this;
    }
    
    public boolean matches( String... switchCase ){
        return select(switchCase) != null;
    }
    
    public boolean matches(SwitchEntry switchEntry ){
        return select(switchEntry) != null;
    }
    
    public $case addConstraint(Predicate<SwitchEntry> constraint ){
        this.constraint = constraint;
        return this;
    }
    
    private Select selectStatements(SwitchEntry astSwitchEntry, Tokens tokens){
        //System.out.println( "In selectStatements");
        if( statements.isEmpty()){
            //System.out.println("Statments empty");
            return new Select( astSwitchEntry, $proto.$args.of(tokens) );
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
        return new Select(astSwitchEntry, $args.of(ts));
    }
    
    public Select select( String... switchCase ){
        return select( Ast.caseStmt(switchCase));
    }
    
    public Select select( SwitchEntry astSwitchEntry ){
        //System.out.println( "KKKKLLL ");
        if( ! constraint.test(astSwitchEntry)){
            return null;
        }
        if( astSwitchEntry.getLabels().isEmpty() ){
            //System.out.println( "test label is null");  
            if( this.label == null ){
                //System.out.println( "$case label is null");                
                return selectStatements( astSwitchEntry, new Tokens());
            }
            if( this.label.isMatchAny() ){
                //System.out.println( "isMatchAny");                
                return selectStatements( astSwitchEntry, new Tokens());
            }
            return null;
        }
        Expression label = astSwitchEntry.getLabels().get(0);
        //System.out.println("the label is "+ label);
        if( this.label == null ){
            //System.out.println( "$case label is null");
            return null;
        }
        //System.out.println("Selecting "+ this.label);
        $expr.Select sel = this.label.select(label);
        
        if( sel != null ){
            //System.out.println("Matched the label "+ label+" to "+this.label );
            return selectStatements(astSwitchEntry, sel.args.asTokens());            
        }
        return null;
    }

    @Override
    public SwitchEntry firstIn(Node astRootNode, Predicate<SwitchEntry> caseMatchFn ) {
        Optional<SwitchEntry> ose = 
            astRootNode.findFirst(SwitchEntry.class, se ->{
                Select sel = select(se);
                return sel != null && caseMatchFn.test(se);
            });
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
    
    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @return 
     */
    public Select selectFirstIn(Class clazz, Predicate<Select>selectConstraint ){
        return selectFirstIn( _java.type(clazz), selectConstraint);
    }
    
    /**
     * 
     * @param _n
     * @param selectConstraint
     * @return 
     */
    public Select selectFirstIn(_java _n, Predicate<Select>selectConstraint ){
        if( _n instanceof _code ){
            _code _c = (_code)_n;
            if( _c.isTopLevel() ){
                return selectFirstIn(_c.astCompilationUnit(), selectConstraint);
            }
            _type _t = (_type)_n; //only possible 
            return selectFirstIn(_t.ast(), selectConstraint);
        }
        return selectFirstIn((_node)_n, selectConstraint);        
    }
    
    /**
     * 
     * @param n
     * @param selectConstraint
     * @return 
     */
    public Select selectFirstIn(Node n, Predicate<Select>selectConstraint ){
        Optional<SwitchEntry> ose = 
            n.findFirst(SwitchEntry.class, se -> {
                Select sel = select(se);
                return sel != null && selectConstraint.test(sel);
            });
        if( ose.isPresent() ){
            return select(ose.get());
        }
        return null;                
    }

    @Override
    public List<Select> listSelectedIn(Node astRootNode) {
        List<Select> found = new ArrayList<>();
        forEachIn( astRootNode, s-> found.add(select(s)));
        return found;        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param selectConstraint
     * @return 
     */
    public <N extends _java> List<Select> listSelectedIn(N _n, Predicate<Select> selectConstraint) {
        if( _n instanceof _code ){
            _code _c = (_code)_n;
            if( _c.isTopLevel() ){
                return listSelectedIn(_c.astCompilationUnit(), selectConstraint);
            }
            _type _t = (_type)_n; //only possible 
            return listSelectedIn(_t.ast(), selectConstraint);
        }
        return listSelectedIn((_node)_n, selectConstraint);
    }
    
    /**
     * 
     * @param astRootNode
     * @param selectConstraint
     * @return 
     */
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
    public <N extends Node> N forEachIn(N astRootNode, Predicate<SwitchEntry> _caseMatchFn, Consumer<SwitchEntry> _nodeActionFn) {
        astRootNode.walk(SwitchEntry.class, se-> {
            Select sel = select(se);
            if( sel != null && _caseMatchFn.test(se) ) {
                _nodeActionFn.accept(se);
            }
        });
        return astRootNode;
    }

    /**
     * 
     * @param clazz
     * @param selectActionFn
     * @return 
     */
    public _type forSelectedIn(Class clazz, Consumer<Select> selectActionFn) {
        return forSelectedIn(_java.type(clazz), selectActionFn);
    }
    
    /**
     * 
     * @param clazz
     * @param selectConstraint
     * @param selectActionFn
     * @return 
     */
    public _type forSelectedIn(Class clazz, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn) {
        return forSelectedIn(_java.type(clazz), selectConstraint, selectActionFn);
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param selectActionFn
     * @return 
     */
    public <N extends _java> N forSelectedIn(N _n, Consumer<Select> selectActionFn) {
        if( _n instanceof _code ){
            _code _c = (_code)_n;
            if( _c.isTopLevel() ){
                forSelectedIn(_c.astCompilationUnit(), selectActionFn);
                return _n;
            }
            _type _t = (_type)_n; //only possible 
            forSelectedIn(_t.ast(), selectActionFn); //return the TypeDeclaration, not the CompilationUnit            
            return _n;
        }
        forSelectedIn(((_node)_n).ast(), selectActionFn);
        return _n;        
    }
    
    /**
     * 
     * @param <N>
     * @param _n
     * @param selectMatchFn
     * @param selectActionFn
     * @return 
     */
    public <N extends _java> N forSelectedIn(N _n, Predicate<Select> selectMatchFn, Consumer<Select> selectActionFn) {
        if( _n instanceof _code ){
            _code _c = (_code)_n;
            if( _c.isTopLevel() ){
                forSelectedIn(_c.astCompilationUnit(), selectMatchFn, selectActionFn);
                return _n;
            }
            _type _t = (_type)_n; //only possible 
            forSelectedIn(_t.ast(), selectMatchFn, selectActionFn); //return the TypeDeclaration, not the CompilationUnit            
            return _n;
        }
        forSelectedIn(((_node)_n).ast(), selectMatchFn, selectActionFn);
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
        astRootNode.walk(SwitchEntry.class, se-> {
            Select sel = select(se);
            if( sel != null ) {
                selectActionFn.accept(sel);
            }
        });
        return astRootNode;
    }
    
    /**
     * 
     * @param <N>
     * @param astRootNode
     * @param selectMatchFn
     * @param selectActionFn
     * @return 
     */
    public <N extends Node> N forSelectedIn(N astRootNode, Predicate<Select> selectMatchFn, Consumer<Select> selectActionFn) {
        astRootNode.walk(SwitchEntry.class, se-> {
            Select sel = select(se);
            if( sel != null && selectMatchFn.test(sel) ) {
                selectActionFn.accept(sel);
            }
        });
        return astRootNode;
    }
    
    @Override
    public SwitchEntry construct(Translator translator, Map<String, Object> keyValues) {
        SwitchEntry se = new SwitchEntry();
        //Parameteric override
        if( keyValues.get("$case") != null ){
            $case $a = $case.of( keyValues.get("$case").toString() );
            Map<String,Object> kvs = new HashMap<>();
            kvs.putAll(keyValues);
            kvs.remove("$case"); //remove to avoid stackOverflow
            return $a.construct(translator, kvs);        
        }
        //parameteric override of the label
        if( keyValues.get("$label") != null ){
            Object ll = keyValues.get("$label" );
            if( ll instanceof $expr ){
                NodeList<Expression> labels = new NodeList<>();
                labels.add( (($expr) ll).construct(translator, keyValues) );
                se.setLabels(labels);                 
            } else{
                NodeList<Expression> labels = new NodeList<>();
                labels.add( $expr.of(ll.toString()).construct( translator, keyValues) );
                se.setLabels(labels);                                 
            }                
        } 
        else if( this.label != null ){
            NodeList<Expression> labels = new NodeList<>();
            labels.add( this.label.construct(translator, keyValues) );
            se.setLabels(labels); 
        }        
        if( keyValues.get("$statements") != null ){
            System.out.println( "has Statements");
            Object ll = keyValues.get("$statements" );
            if( ll instanceof Statement ){
                
                se.addStatement( $stmt.of((Statement) ll).construct(translator, keyValues) );                
            } else if( ll instanceof $stmt ){
                se.addStatement( (($stmt) ll).construct(translator, keyValues) );                
            } else if( ll instanceof $stmt[]) {
                $stmt[] sts = ($stmt[])ll;
                Arrays.stream(sts).forEach( s-> se.addStatement(s.construct(translator, keyValues)) );                
            } else if( ll instanceof Statement[]) {
                Arrays.stream( (Statement[])ll).forEach( s-> se.addStatement($stmt.of(s).construct(translator, keyValues)) );                
            } else {
                //just toString the thing 
                BlockStmt bs = Ast.blockStmt( (String)(ll.toString()) );
                bs.getStatements().forEach(s -> se.addStatement( $stmt.of(s).construct(translator, keyValues)));                        
            }                     
        } else{ 
        //List<Statement> composedStatements = new ArrayList<>();
            //System.out.println( "COMPLETE NORM");
            this.statements.forEach(st -> se.addStatement( st.construct(translator, keyValues) ) );
        }
        return se;
    }

    @Override
    public Template<SwitchEntry> $(String target, String $Name) {
        if( this.label != null ){
            this.label.$(target, $Name);
        }
        this.statements.forEach(s -> s.$(target, $Name));
        return this;
    }

    @Override
    public List<String> list$() {
        List<String> all = new ArrayList<>();
        if( this.label != null ){
            all.addAll( this.label.list$() );
        }
        this.statements.forEach(s -> all.addAll(s.list$()));
        return all;
    }

    @Override
    public List<String> list$Normalized() {
        List<String> allN = new ArrayList<>();
        if( this.label != null ){
            allN.addAll( this.label.list$Normalized() );
        }
        this.statements.forEach(s -> allN.addAll(s.list$Normalized()));
        return allN.stream().distinct().collect(Collectors.toList());
        //return all;
    }
    
    
    public static class Select 
        implements $proto.selected<SwitchEntry>, $proto.selectedAstNode<SwitchEntry>{
        
        public SwitchEntry astCase;
        public $proto.$args args;
        
        public Select( SwitchEntry astCase, $args $nv){
            this.astCase = astCase;
            this.args = $nv;
        }

        @Override
        public $proto.$args args() {
            return args;
        }

        @Override
        public SwitchEntry ast() {
            return astCase;
        }   
        
        /**
         * The default case has empty labels
         * @return 
         */
        public boolean isDefaultCase(){
            return astCase.getLabels().isEmpty();
        }
    }     
}
