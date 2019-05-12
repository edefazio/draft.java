package draft.java.proto;

import com.github.javaparser.ast.expr.ArrayAccessExpr;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.expr.BooleanLiteralExpr;
import com.github.javaparser.ast.expr.CastExpr;
import com.github.javaparser.ast.expr.CharLiteralExpr;
import com.github.javaparser.ast.expr.ClassExpr;
import com.github.javaparser.ast.expr.ConditionalExpr;
import com.github.javaparser.ast.expr.DoubleLiteralExpr;
import com.github.javaparser.ast.expr.EnclosedExpr;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.FieldAccessExpr;
import com.github.javaparser.ast.expr.InstanceOfExpr;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.LongLiteralExpr;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.expr.MethodReferenceExpr;
import com.github.javaparser.ast.expr.NameExpr;
import com.github.javaparser.ast.expr.NullLiteralExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.expr.StringLiteralExpr;
import com.github.javaparser.ast.expr.SuperExpr;
import com.github.javaparser.ast.expr.ThisExpr;
import com.github.javaparser.ast.expr.UnaryExpr;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithBlockStmt;
import com.github.javaparser.ast.nodeTypes.NodeWithOptionalBlockStmt;
import com.github.javaparser.ast.stmt.AssertStmt;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.DoStmt;
import com.github.javaparser.ast.stmt.EmptyStmt;
import com.github.javaparser.ast.stmt.ExplicitConstructorInvocationStmt;
import com.github.javaparser.ast.stmt.ExpressionStmt;
import com.github.javaparser.ast.stmt.ForEachStmt;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.LabeledStmt;
import com.github.javaparser.ast.stmt.LocalClassDeclarationStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import com.github.javaparser.ast.stmt.TryStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import draft.java.Expr;
import draft.java._anno;
import draft.java._anno._annos;
import draft.java._body;
import draft.java._constructor;
import draft.java._field;
import draft.java._import;
import draft.java._method;
import draft.java._modifiers;
import draft.java._parameter;
import draft.java._parameter._parameters;
import java.lang.annotation.Annotation;
import java.util.function.*;

/**
 * This abstraction is a shortcut to unify all of the $prototypes in a single
 * API that is easy / convenient to use (since modern IDES will autocomplete, this
 * will help simplify getting up to speed/ using the protyotype API)
 * 
 * @author Eric
 */
public final class $ {
    /** cant construct one of these */
    private $(){}
    
    public static $anno anno(){
        return $anno.any();
    }
    
    public static $anno anno( $id name, $anno.$memberValue...memberValues ){
        return $anno.of(name, memberValues);
    }
    
    public static $anno anno(Predicate<_anno> constraint){
        return $anno.any().constraint(constraint);
    }
    
    public static $anno anno(String pattern){
        return $anno.of(pattern);
    }
    
    public static $anno anno(String pattern, Predicate<_anno> constraint){
        return $anno.of(pattern).constraint(constraint);
    }
    
    public static $anno anno( Class<? extends Annotation> clazz ){
        return $anno.of(clazz);
    }
    
    public static $anno anno( Class<? extends Annotation> clazz, Predicate<_anno> constraint){
        return $anno.of(clazz).constraint(constraint);
    }
    
    public static $annos annos(){
        return $annos.any();
    }
    
    public static $annos annos( Predicate<_annos> constraint ){
        return $annos.of().constraint(constraint);
    }
    
    public static $annos annos( _annos _anns ){
        return $annos.of(_anns);
    }
    
    public static $annos annos( _annos _anns, Predicate<_annos> constraint){
        return $annos.of(_anns).constraint(constraint);
    }
    
    public static $annos annos($anno... annos){
        return $annos.of(annos);
    }
    
    public static $body body(){
        return $body.any();
    }
    
    public static $body body( Predicate<_body> constraint){
        return $body.of().constraint(constraint);
    }
    
    public static $body body(String...body){
        return $body.of(body);
    }
    
    public static $body body( _body _bd ){
        return $body.of(_bd);
    }
    
    public static $body body( _body _bd, Predicate<_body> constraint){
        return $body.of(_bd);
    }
    
    public static $body body( NodeWithBlockStmt astNodeWithBlock ){
        return $body.of(_body.of(astNodeWithBlock));
    }
    
    public static $body body( NodeWithOptionalBlockStmt astNodeWithBlock ){
        return $body.of(_body.of(astNodeWithBlock));
    }
    
    public static $body body(Expr.Command commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return new $body( le );
    }
    
    public static $body body(Consumer commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return new $body( le );
    }
    
    public static $body body(BiConsumer commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return new $body( le );
    }
    
    public static $body body(Expr.TriConsumer commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return new $body( le );
    }
    
    public static $body body(Expr.QuadConsumer commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return new $body( le );
    }
    
    public static <A extends Object, B extends Object> $body body(Function<A,B> commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return new $body( le );
    }
    
    public static <A extends Object, B extends Object, C extends Object>  $body body(BiFunction<A,B,C> commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return new $body( le );
    }
    
    public static <A extends Object, B extends Object, C extends Object, D extends Object> $body body(Expr.TriFunction<A,B,C,D> commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return new $body( le );
    }
    
    public static <A extends Object, B extends Object, C extends Object, D extends Object, E extends Object>  $body body(Expr.QuadFunction<A,B,C,D,E> commandLambda ){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        return new $body ( le );
    }
    
    public static $constructor constructor(){
        return $constructor.any();
    }

    public static $constructor constructor( Predicate<_constructor> constraint){
        return $constructor.of().constraint(constraint);
    }
    
    public static $constructor constructor( _constructor _proto ){
        return $constructor.of(_proto);
    }
    
    public static $constructor constructor( _constructor _proto, Predicate<_constructor> constraint ){
        return $constructor.of(_proto).constraint(constraint);
    }
    
    public static $constructor constructor( String pattern){
        return $constructor.of(pattern);
    }
    
    public static $constructor constructor( String pattern, Predicate<_constructor> constraint){
        return $constructor.of(pattern).constraint(constraint);
    }
    
    public static $constructor constructor( String...pattern){
        return $constructor.of(pattern);
    }
    
    public static $constructor constructor($constructor.$part part){
        return $constructor.of(part);
    }
    
    public static $constructor constructor($constructor.$part...parts){
        return $constructor.of(parts);
    }
    
    public static $constructor constructor(Object anonymousObjectContainingMethod){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return $constructor.of( ste, anonymousObjectContainingMethod);
    }
    
    public static $expr expr(){
        return $expr.any();
    }
    
    public static $expr expr(Predicate<Expression> constraint){
        return $expr.any().constraint(constraint);
    }
    
    public static $expr expr(String... pattern){
        return $expr.of(pattern);
    }
    
    public static <T extends Expression> $expr<T> of(T protoExpr ){
        return $expr.of(protoExpr);
    }
    
    public static <T extends Expression> $expr<T> of(T protoExpr, Predicate<T> constraint){
        return $expr.of(protoExpr).constraint(constraint);
    }
   
    public static $expr<StringLiteralExpr> of( String stringLiteral ){
        return $expr.stringLiteral(stringLiteral);
    }
    
    public static $expr<CharLiteralExpr> of(char c){
        return $expr.of(c);
    }
    
    public static $expr<IntegerLiteralExpr> of(int i){
        return $expr.of(i);
    }
    
    /**
     * Note: this can be finniky
     * @param f
     * @return 
     */
    public static $expr<DoubleLiteralExpr> of(float f){
        return $expr.of(f);
    }
    
    public static $expr<DoubleLiteralExpr> of(double d){
        return $expr.of(d);
    }
    
    public static $expr<LongLiteralExpr> of(long l){
        return $expr.of(l);
    }
    
    public static $expr<BooleanLiteralExpr> of(boolean b){        
        return $expr.of(b);
    }
    
    public static $expr<ArrayAccessExpr> arrayAccessExpr(){
        return $expr.arrayAccess();
    }
    
    public static $expr<ArrayCreationExpr> arrayCreationExpr(){
        return $expr.arrayCreation();    
    }
    
    public static $expr<ArrayInitializerExpr> arrayInitExpr(){
        return $expr.arrayInitializer();
    }
    
    public static $expr<AssignExpr> assignExpr(){
        return $expr.assign();
    }
    
    public static $expr<BinaryExpr> binaryExpr(){
        return $expr.binary();
    }
    
    public static $expr<BooleanLiteralExpr> booleanExpr(){
        return $expr.booleanLiteral();
    }
    
    public static $expr<CastExpr> castExpr(){
        return $expr.cast();
    }
    
    public static $expr<CharLiteralExpr> charExpr(){
        return $expr.charLiteral();
    }
    
    public static $expr<ClassExpr> classExpr(){
        return $expr.classExpr();
    }
    
    public static $expr<ConditionalExpr> conditionalExpr(){
        return $expr.conditional();
    }
    
    public static $expr<DoubleLiteralExpr> doubleLiteralExpr(){
        return $expr.doubleLiteral();
    }
    
    public static $expr<DoubleLiteralExpr> doubleLiteralExpr(String pattern){
        return $expr.doubleLiteral(pattern);
    }
    
    public static $expr<EnclosedExpr> enclosedExpr(){
        return $expr.enclosedExpr();
    }
    
    public static $expr<FieldAccessExpr> fieldAccessExpr(){
        return $expr.fieldAccess();
    }
    
    public static $expr<InstanceOfExpr> instanceOfExpr(){
        return $expr.instanceOf();
    }
    
    public static $expr<IntegerLiteralExpr> intLiteralExpr(){
        return $expr.intLiteral();
    }
    
    public static $expr<LambdaExpr> lambdaExpr(){
        return $expr.lambda();
    }
    
    public static $expr<LongLiteralExpr> longLiteral(){
        return $expr.longLiteral();
    }
    
    public static $expr<MethodCallExpr> methodCallExpr(){
        return $expr.methodCall();
    }
    
    public static $expr<MethodReferenceExpr> methodReferenceExpr(){
        return $expr.methodReference();
    }
    
    public static $expr<NameExpr> nameExpr(){
        return $expr.name();
    }
    
    public static $expr<NullLiteralExpr> nullExpr(){
        return $expr.nullExpr();
    }
    
    public static $expr<ObjectCreationExpr> objectCreationExpr(){
        return $expr.objectCreation();
    }
    
    public static $expr<StringLiteralExpr> stringLiteralExpr(){
        return $expr.stringLiteral();
    }
    
    public static $expr<SuperExpr> superExpr(){
        return $expr.superExpr();
    }
    
    public static $expr<ThisExpr> thisExpr(){
        return $expr.thisExpr();
    }
    
    public static $expr<UnaryExpr> unaryExpr(){
        return $expr.unary();
    }
    
    public static $expr<VariableDeclarationExpr> varDeclarationExpr(){
        return $expr.varDecl();
    }
    
    public static $field field(){
        return $field.any();
    }
    
    public static $field field(Predicate<_field> constraint){
        return $field.any().addConstraint(constraint);
    }
    
    public static $import importing(){
        return $import.any();
    }
    
    public static $import importing(Predicate<_import> constraint){
        return $import.any().addConstraint(constraint);
    }
    
    public static $method method(){
        return $method.any();
    }
    
    public static $method method(Predicate<_method> constraint){
        return $method.any().addConstraint(constraint);
    }
    
    public static $modifiers modifiers(){
        return $modifiers.any();
    }
    
    public static $modifiers modifiers(Predicate<_modifiers> constraint){
        return $modifiers.any().addConstraint(constraint);
    }
    
    public static $parameters parameters(){
        return $parameters.any();
    }
    
    public static $parameters parameters(Predicate<_parameters> constraint){
        return $parameters.any().addConstraint(constraint);
    }
    
    public static $parameter parameter(){
        return $parameter.any();
    }
    
    public static $parameter parameter(Predicate<_parameter> constraint){
        return $parameter.any().addConstraint(constraint);
    }
    
    public static $snip snip(){
        return $snip.any();
    }
    
    public static $stmt stmt(){
        return $stmt.any();
    }
    
    public static $stmt<AssertStmt> assertStmt(){
        return $stmt.assertStmt();
    }
    
    public static $stmt<BlockStmt> blockStmt(){
        return $stmt.blockStmt();
    }
    
    public static $stmt<BreakStmt> breakStmt(){
        return $stmt.breakStmt();
    }
    
    public static $stmt<ExplicitConstructorInvocationStmt> constructorInvocationStmt(){
        return $stmt.ctorInvocationStmt();
    }
    
    public static $stmt<ContinueStmt> continueStmt(){
        return $stmt.continueStmt();
    }
    
    public static $stmt<DoStmt> doStmt(){
        return $stmt.doStmt();
    }
    
    public static $stmt<EmptyStmt> emptyStmt(){
        return $stmt.emptyStmt();
    }
    
    public static $stmt<ExpressionStmt> expressionStmt(){
        return $stmt.expressionStmt();
    }
    public static $stmt<ForStmt> forStmt(){
        return $stmt.forStmt();
    }
    public static $stmt<ForEachStmt> forEachStmt(){
        return $stmt.forEachStmt();
    }
    public static $stmt<IfStmt> ifStmt(){
        return $stmt.ifStmt();
    }
    public static $stmt<LabeledStmt> labeledStmt(){
        return $stmt.labeledStmt();
    }
    public static $stmt<LocalClassDeclarationStmt> localClassStmt(){
        return $stmt.localClassStmt();
    }
    public static $stmt<SwitchStmt> switchStmt(){
        return $stmt.switchStmt();
    }
    public static $stmt<ThrowStmt> throwStmt(){
        return $stmt.throwStmt();
    }
    public static $stmt<TryStmt> tryStmt(){
        return $stmt.tryStmt();
    }
    public static $stmt<WhileStmt> whileStmt(){
        return $stmt.whileStmt();
    }
    
    
    /*
    //stmt
    static Class<Statement>[] ST = new Class<Statement>[] {
        Stmt.ASSERT,
        Stmt.BLOCK,
        Stmt.BREAK,
        Stmt.CONSTRUCTOR_INVOCATION,
        Stmt.CONTINUE,
        Stmt.DO,
        Stmt.EMPTY,
        Stmt.EXPRESSION,
        Stmt.FOR,
        Stmt.FOR_EACH,
        Stmt.IF,
        Stmt.LABELED,
        Stmt.LOCAL_CLASS,
        Stmt.RETURN,
        Stmt.SWITCH,
        Stmt.SYNCHRONIZED,
        Stmt.THROW,
        Stmt.TRY,
        Stmt.WHILE,        
    };
    */
    
    
    public static $throws thrown(){
        return $throws.any();
    }
    
    public static $typeParameter typeParameter(){
        return $typeParameter.any();
    }
    
    public static $typeParameters typeParameters(){
        return $typeParameters.any();
    }
    
    public static $typeRef typeRef(){
        return $typeRef.any();
    }
    
    public static $var var(){
        return $var.any();
    }
}
