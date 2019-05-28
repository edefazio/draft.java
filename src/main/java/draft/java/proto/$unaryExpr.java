package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.UnaryExpr;
import draft.Stencil;
import draft.Template;
import draft.Tokens;
import draft.Translator;
import draft.java._model;
import draft.java._model._node;
import draft.java._type;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * NOTE: still a WIP... for the time being... just use
 * $expr<unaryExpr> ue = $expr.unary(...);
 * 
 * i.e. "!a", "-position", "a++"
 * 
 * TODO?
 * 1) extract interface for $expr to $exprProto
 * 2) move all methods there
 * 3) $expr implement $exprProto
 * 4) $unaryExpr implement $exprProto
 * 
 * @author Eric
 */
public class $unaryExpr 
    implements $exprProto<UnaryExpr>, $proto<UnaryExpr>, Template<UnaryExpr> {
    
    public static final UnaryExpr.Operator PLUS = UnaryExpr.Operator.PLUS;
    public static final UnaryExpr.Operator MINUS = UnaryExpr.Operator.MINUS;
    public static final UnaryExpr.Operator PREFIX_INCREMENT = UnaryExpr.Operator.PREFIX_INCREMENT;
    public static final UnaryExpr.Operator PREFIX_DECREMENT = UnaryExpr.Operator.PREFIX_DECREMENT;
    public static final UnaryExpr.Operator LOGICAL_COMPLEMENT = UnaryExpr.Operator.LOGICAL_COMPLEMENT;
    public static final UnaryExpr.Operator BITWISE_COMPLEMENT = UnaryExpr.Operator.BITWISE_COMPLEMENT;
    public static final UnaryExpr.Operator POSTFIX_INCREMENT = UnaryExpr.Operator.POSTFIX_INCREMENT;
    public static final UnaryExpr.Operator POSTFIX_DECREMENT = UnaryExpr.Operator.POSTFIX_DECREMENT;
    
    public static final $unaryExpr any(){
        return new $unaryExpr($expr.any(), UnaryExpr.Operator.values() );
    }

    public static final $unaryExpr of(){
        return any();
    }
    
    public Predicate<UnaryExpr> constraint = t-> true;
    public Stencil operator = Stencil.of("$operator$");
    //
    public $expr expr = $expr.any();
    
    public $unaryExpr(UnaryExpr astExpressionProto) {        
        $operators(astExpressionProto.getOperator());
        $expression(astExpressionProto.getExpression());
    }
    
    public $unaryExpr(UnaryExpr astExpressionProto, Predicate<UnaryExpr> constraint) {        
        $operators(astExpressionProto.getOperator());
        $expression(astExpressionProto.getExpression());
        this.constraint = constraint;
    }
    
    public $unaryExpr( $expr expression, UnaryExpr.Operator...operators){
        $operators(operators);
        $expression(expression);
    }
    
    public $unaryExpr $operator(UnaryExpr.Operator op){
        this.operator = Stencil.of(op.asString());
        return this;
    }
    
    public $unaryExpr $operators(){
        operator = Stencil.of("$operator$");
        return this;
    }
    
    /**
     * set which operators are expected
     * (converts this into a lambda)
     * @param ops
     * @return 
     */
    public $unaryExpr $operators( UnaryExpr.Operator...ops ){
        Set<UnaryExpr.Operator> operators = new HashSet<>();
        Arrays.stream(ops).forEach( o-> operators.add(o) );        
        Predicate<UnaryExpr> pu = ue-> operators.contains( ue.getOperator() );
        addConstraint(pu);
        return this;
    }
    
    public $unaryExpr $operators( String...ops ){
        Set<UnaryExpr.Operator> operators = new HashSet<>();
        Arrays.stream(ops).forEach( o-> operators.add( UnaryExpr.Operator.valueOf(o) ));
        Predicate<UnaryExpr> pu = ue-> operators.contains( ue.getOperator() );
        addConstraint(pu);
        return this;
    }
    
    public $unaryExpr $expression(){
        return $expression($expr.any());
    }
    
    /**
     * Sets the expression for the unary operator
     * @param e
     * @return 
     */
    public $unaryExpr $expression( Expression e){
        return $expression($expr.of(e) );
    }
    
    public $unaryExpr $expression( $expr $e){
        this.expr = $e;
        return this;
    } 
    
    @Override
    public boolean matches(Expression e){
        return select(e) != null;
    }
    
    @Override
    public Select select( Expression e ){
        if( !(e instanceof UnaryExpr) ){
            return null;
        }
        UnaryExpr ue = (UnaryExpr)e;
        if( !this.constraint.test(ue)){
            return null;
        }       
        $expr.Select sel = this.expr.select(ue);
        if( sel == null ){
            return null;
        }
        return new Select( ue, sel.args );
    }

    @Override
    public $unaryExpr $(String target, String $name) {
        this.expr.$(target, $name);
        return this;
    }

    @Override
    public $unaryExpr $(Expression astExpr, String $name) {
        this.expr.$(astExpr, $name);
        return this;
    }

    @Override
    public $unaryExpr addConstraint(Predicate<UnaryExpr> constraint) {
        this.constraint = this.constraint.and(constraint);
        return this;
    }

    @Override
    public UnaryExpr construct(_node _n) {
        return construct(_n.deconstruct() );
    }

    @Override
    public UnaryExpr construct(Translator t, Map<String, Object> tokens) {
        UnaryExpr ue = new UnaryExpr();
        Expression ex = this.expr.construct(t, tokens);
        ue.setExpression(ex);
        if( this.operator.isFixedText() ){            
            UnaryExpr.Operator op = UnaryExpr.Operator.valueOf( this.operator.getTextBlanks().getFixedText() );                        
            ue.setOperator(op);            
        }
        else {
            String operator = this.operator.construct(t, tokens);
            //tokens.get("operator").toString();            
            ue.setOperator(UnaryExpr.Operator.valueOf( operator ));
        }
        return ue;
    }

    @Override
    public UnaryExpr fill(Object... values) {
        
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UnaryExpr firstIn(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UnaryExpr firstIn(_model._node _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public UnaryExpr firstIn(Node astNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E extends Expression> _type forEach(Class clazz, Class<E> exprClass, Consumer<E> expressionActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node, E extends Expression> N forEach(N _n, Class<E> exprClass, Consumer<E> expressionActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <E extends Expression> _type forEach(Class clazz, E exprProto, Consumer<E> expressionActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node, E extends Expression> N forEach(N _n, E exprProto, Consumer<E> expressionActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node, E extends Expression> N forEach(N _n, E exprProto, Predicate<E> constraint, Consumer<E> expressionActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends Node> N forEachIn(N astNode, Consumer<UnaryExpr> expressionActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node> N forEachIn(N _n, Consumer<UnaryExpr> expressionActionFn) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public _type forSelectedIn(Class clazz, Consumer<$expr.Select<UnaryExpr>> selectConsumer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node> N forSelectedIn(N _n, Consumer<$expr.Select<UnaryExpr>> selectConsumer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends Node> N forSelectedIn(N astNode, Consumer<$expr.Select<UnaryExpr>> selectConsumer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public _type forSelectedIn(Class clazz, Predicate<$expr.Select<UnaryExpr>> selectConstraint, Consumer<$expr.Select<UnaryExpr>> selectConsumer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node> N forSelectedIn(N _n, Predicate<$expr.Select<UnaryExpr>> selectConstraint, Consumer<$expr.Select<UnaryExpr>> selectConsumer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends Node> N forSelectedIn(N astNode, Predicate<$expr.Select<UnaryExpr>> selectConstraint, Consumer<$expr.Select<UnaryExpr>> selectConsumer) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public $expr hardcode$(Tokens kvs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public $expr hardcode$(Object... keyValues) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public $expr hardcode$(Translator translator, Object... keyValues) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public $expr hardcode$(Translator translator, Tokens kvs) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isMatchAny() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> list$() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<String> list$Normalized() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UnaryExpr> listIn(_model._node _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<UnaryExpr> listIn(Node astNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<$expr.Select<UnaryExpr>> listSelectedIn(Node astNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<$expr.Select<UnaryExpr>> listSelectedIn(_model._node _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<$expr.Select<UnaryExpr>> listSelectedIn(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<$expr.Select<UnaryExpr>> listSelectedIn(Class clazz, Predicate<$expr.Select<UnaryExpr>> selectConstraint) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<$expr.Select<UnaryExpr>> listSelectedIn(Node astNode, Predicate<$expr.Select<UnaryExpr>> selectConstraint) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<$expr.Select<UnaryExpr>> listSelectedIn(_model._node _n, Predicate<$expr.Select<UnaryExpr>> selectConstraint) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean matches(String... expression) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends Node> N removeIn(N astNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node> N removeIn(N _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public _type replaceIn(Class clazz, Node astExprReplace) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node> N replaceIn(N _n, Node astExprReplace) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node> N replaceIn(N _n, String protoReplaceExpr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public _type replaceIn(Class clazz, $expr $repl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public <N extends _model._node> N replaceIn(N _n, $expr $repl) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public $expr.Select<UnaryExpr> select(String... expr) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public $expr.Select<UnaryExpr> selectFirstIn(Class clazz) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public $expr.Select<UnaryExpr> selectFirstIn(_model._node _n) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public $expr.Select<UnaryExpr> selectFirstIn(Node astNode) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public $expr.Select<UnaryExpr> selectFirstIn(Class clazz, Predicate<$expr.Select<UnaryExpr>> selectConstraint) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public $expr.Select<UnaryExpr> selectFirstIn(_model._node _n, Predicate<$expr.Select<UnaryExpr>> selectConstraint) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public $expr.Select<UnaryExpr> selectFirstIn(Node astNode, Predicate<$expr.Select<UnaryExpr>> selectConstraint) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public static class Select extends $expr.Select<UnaryExpr>{
        
        public Select(UnaryExpr astExpr, Tokens tokens) {
            super(astExpr, tokens);
        }
        
        public Select(UnaryExpr astExpr, $args args) {
            super(astExpr, args);
        }
        
        public UnaryExpr.Operator getOperator(){
            return this.astExpression.getOperator();            
        }
        
        public Expression getExpression(){
            return this.astExpression.getExpression();
        }
    }
    
}
