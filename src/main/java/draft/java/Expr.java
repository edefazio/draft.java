package draft.java;

import com.github.javaparser.*;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.Statement;
import draft.DraftException;
import draft.Text;
import draft.java.io._in;
import draft.java.io._io;
import java.util.*;
import java.util.function.*;

/**
 * Utility for converting free form Strings and Runtime entities (lambdas)
 * into JavaParser {@link Expression} implementations and accepting runtime entities
 * (Lambda Expressions, and Anonymous Objects) and retrieving the source code for them
 *
 */
public enum Expr {
    ;

    /**
     * Functional interface for no input, no return lambda function
     * (Used when we pass in Lambdas to the {@link Expr#lambda(Command)} operation
     */
    @FunctionalInterface
    public interface Command{
        void consume();
    }


    /**
     * Functional interface for (3) input, (1) return lambda function
     * (Used when we pass in Lambdas to the {@link Expr#lambda(TriFunction)}  operation
     */
    @FunctionalInterface
    public interface TriFunction<A,B,C,D>{
        D apply(A a, B b, C c);
    }

    /**
     * Functional interface for (4) input PARAMETERS, (1) return lambda function
     * (Used when we pass in Lambdas to the {@link Expr#lambda(QuadFunction)} operation)
     */
    @FunctionalInterface
    public interface QuadFunction<A,B,C,D,E>{
        E apply(A a, B b, C c, D d);
    }

    /**
     * Functional interface for (3) input PARAMETERS, no return lambda function
     * (Used when we pass in Lambdas to the {@link Expr#lambda(TriConsumer)} operation)
     */
    @FunctionalInterface
    public interface TriConsumer<T,U,V>{
        void consume(T t, U u, V v);
    }

     /**
     * Functional interface for (4) input PARAMETERS, no return lambda function
     * (Used when we pass in Lambdas to the {@link Expr#lambda(QuadConsumer)} operation
     */
    @FunctionalInterface
    public interface QuadConsumer<T,U,V,Z>{
        void consume(T t, U u, V v, Z z);
    }

    public static <T extends Object> LambdaExpr of( Expr.Command c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    public static <T extends Object> LambdaExpr of( Consumer<T> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    public static <T extends Object, U extends Object> LambdaExpr of( BiConsumer<T, U> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    public static <T extends Object, U extends Object, V extends Object> LambdaExpr of( TriConsumer<T, U, V> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    public static <T extends Object, U extends Object> LambdaExpr of( Function<T, U> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    public static <T extends Object, U extends Object, V extends Object> LambdaExpr of( BiFunction<T, U, V> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    public static <T extends Object, U extends Object, V extends Object, Z extends Object> LambdaExpr of( TriFunction<T, U, V, Z> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    public static <A extends Object, B extends Object, C extends Object, D extends Object> LambdaExpr of( QuadConsumer<A,B,C,D> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Given the stack trace, find the source code, and extract the AST LambdaExpr
     * from the AST that was passed in and return the AST LambdaExpr
     * (NOTE: this only works if the Lambda expression is INLINED, and not referenced
     * as a Variable in the source)
     *
     * this works if there is ONE and ONLY ONE lambda expression on the line
     *
     * @param ste
     * @return

    public static LambdaExpr oldlambda(StackTraceElement ste ){
        try {
            //System.out.println( ste.toString() );
            Class clazz = Class.forName(ste.getClassName());
            _type _t = _type.of(clazz);
            int lineNumber = ste.getLineNumber();
            List<LambdaExpr> le = new ArrayList<>();
            //TokenRange tr = _t.ast().getTokenRange().get();

            _t.ast().walk(LambdaExpr.class, l -> {
                if (l.getBegin().get().line == lineNumber) {
                    le.add(l);
                }
                if (l.getBegin().get().line == lineNumber +1) { //sometimes they start on the NEXT line
                    le.add(l);
                }
            });
            if (!le.isEmpty()) {
                return le.get(0);
            }
        }catch(Exception e){
            throw new DraftException("no .java source for Runtime Class \""+ste.getClassName()+"\" "+System.lineSeparator()+
                    _io.describe(), e ); //print out the input config to help
        }
        throw new DraftException("unable to find in lambda at ("+ste.getFileName()+":"+ste.getLineNumber()+")");
    }
    */


    public static LambdaExpr lambda( StackTraceElement ste ) {
        return lambda(ste, _io.IN_DEFAULT );
    }

    /**
     *
     * @param ste
     * @param resolver
     * @return
     */
    public static LambdaExpr lambda(StackTraceElement ste, _in._resolver resolver ){
        _type _t = null;
        try {
            //System.out.println( ste.toString() );
            Class clazz = Class.forName(ste.getClassName());
            _t = _type.of(clazz, resolver);
        } catch (Exception e) {
            throw new DraftException("no .java source for Runtime Class \"" + ste.getClassName() + "\" " + System.lineSeparator() +
                    _io.describe(), e); //print out the input config to help
        }
        int lineNumber = ste.getLineNumber();

        //What I need to do is to find the MethodCallExpr that is nested inside another MethodCallExpr
        //check if it begins before the stack trace line and ends aftrer the stack trace line
        List<MethodCallExpr> ln = Ast.listAll(
                Ast.WALK_BREADTH_FIRST,
                _t.astType(),
                Ast.METHOD_CALL_EXPR,
                mce->
                    mce.getRange().isPresent() && mce.getRange().get().begin.line <= lineNumber
                            && mce.getRange().get().end.line >= lineNumber
                            && mce.getArguments().stream().filter(a-> a instanceof LambdaExpr).findFirst().isPresent()

                        //&& Ast.first(Ast.WALK_DIRECT_CHILDREN, mce.get, Ast.LAMBDA_EXPR, t-> true) != null
        );

        // we always want to start with the LAST one, since we could have a nested
        // grouping of statements
        for(int i=ln.size()-1;i>=0;i--){
            //System.out.println("TRYING ("+i+" of "+ln.size()+") "+ ln.get(i).getClass().getSimpleName()+ " " + ln.get(i) );
            Optional<Node> on = ln.get(i).stream().filter(n -> n instanceof LambdaExpr
                    //&& n.getRange().get().begin.line <= ste.getLineNumber()
                    //&& n.getRange().get().end.line >= ste.getLineNumber()
            ).findFirst();
            if( on.isPresent() ){
                return (LambdaExpr)on.get();
            }
        }
        throw new DraftException("unable to find in lambda at (" + ste.getFileName() + ":" + ste.getLineNumber() + ")"+System.lineSeparator()+ _io.describe());
    }

    /**
     *
     * @param oce
     * @return
     */
    public static ObjectCreationExpr anonymousClass ( Object oce ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return anonymousClass(ste, _io.IN_DEFAULT);
    }

    /**
     *
     * @param oce
     * @param resolver the resolver
     * @return
     */
    public static ObjectCreationExpr anonymousClass ( Object oce, _in._resolver resolver ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return anonymousClass(ste, resolver);
    }

    /**
     *
     * @param ste
     * @return
     */
    public static ObjectCreationExpr anonymousClass( StackTraceElement ste){
         return anonymousClass(ste, _io.IN_DEFAULT );
    }

    /**
     * Given a StackTraceElement for a specific line
     * find the corresponding Anonymous class definition CODE
     * (which is an ObjectCreationExpr with an anonymousClassBody)
     * and return the Expression
     *
     * /// NEED TO REVISE
     * 1) find the Statement at the stackTraceElement line number
     * 2) Walk it until I find the ObjectCreationExpr I am looking for
     *
     * @param ste the stackTraceElement containing the location of the anonymousClass declaration
     * @param resolver  resolver for resolving the .java source for the Class containing the code
     * @return an ObjectCreationExpr of an anonymous Object created
     */
    public static ObjectCreationExpr anonymousClass( StackTraceElement ste, _in._resolver resolver ){
        _type _t = null;
        try {
            //System.out.println( ste.toString() );
            Class clazz = Class.forName(ste.getClassName());
            _t = _type.of(clazz, resolver);
        } catch (Exception e) {
            throw new DraftException("no .java source for Runtime Class \"" + ste.getClassName() + "\" " + System.lineSeparator() +
                    resolver.describe(), e); //print out the input config to help
        }

        List<MethodCallExpr> mces = Walk.list(
                Ast.WALK_POST_ORDER,
                _t.astType(),
                Ast.METHOD_CALL_EXPR,
                (MethodCallExpr mce) -> ((MethodCallExpr)mce).getRange().get().begin.line <= ste.getLineNumber() &&
                        ((MethodCallExpr)mce).getRange().get().end.line >= ste.getLineNumber() &&
                        mce.getArguments().stream().filter( e-> e.isObjectCreationExpr() && e.asObjectCreationExpr().getAnonymousClassBody().isPresent() ).findFirst().isPresent()
                        //Ast.first(Ast.WALK_DIRECT_CHILDREN, mce, Ast.OBJECT_CREATION_EXPR, oce-> oce.getAnonymousClassBody().isPresent()) != null
        );

        /*
        if( mces.size() == 1 ){
            List<ObjectCreationExpr> ocs = Ast.listAll( mces.get(0),
                     Ast.OBJECT_CREATION_EXPR, oce-> oce.getAnonymousClassBody().isPresent() );
            if( ocs.size() == 1 ){
                return ocs.get(0);
            }
        }
        */

        //for(int i=mces.size()-1;i>=0;i--){
        for(int i=0; i<mces.size();i++ ){
            // System.out.println("TRYING ("+i+" of "+mces.size()+") "+ mces.get(i).getClass().getSimpleName()+ " " + mces.get(i) +" AT LINE : "+ ste.getLineNumber() );
            //mces.get(i).stream().filter(n -> n instanceof ObjectCreationExpr).forEach(oce -> System.out.println( oce.getClass() +" "+ oce+ " from: "+oce.getRange().get().begin+ " to: "+oce.getRange().get().end+ " "));

            Optional<Expression> on =
                    mces.get(i).getArguments().stream().filter( a -> a instanceof ObjectCreationExpr
                            && a.asObjectCreationExpr().getAnonymousClassBody().isPresent()).findFirst();
            /*
            Optional<Node> on = mces.get(i).stream().filter(n -> n instanceof ObjectCreationExpr &&
                    ((ObjectCreationExpr)n).getAnonymousClassBody().isPresent()
                            //&& Math.abs(n.getRange().get().begin.line - ste.getLineNumber()) < 1 &&
                    //n.getRange().get().end.line >= ste.getLineNumber()
            ).findFirst();
            */
            if( on.isPresent() ){
                return (ObjectCreationExpr)on.get();
            }
        }

        throw new DraftException("unable to find in anonymous class at (" + ste.getFileName() + ":"
                + ste.getLineNumber() + ")" + System.lineSeparator() + resolver.describe());

        //List<ObjectCreationExpr> le = Walk.list( _t, ObjectCreationExpr.class, oce -> oce.getAnonymousClassBody().isPresent()
        //        && oce.getBegin().get().line == ste.getLineNumber()
        //        || oce.getBegin().get().line == ste.getLineNumber() + 1
        //        || oce.getBegin().get().line == ste.getLineNumber() + 2);

    }

    /**
     * Given a StackTraceElement for a specific line
     * find the corresponding Anonymous class definition CODE
     * (which is an ObjectCreationExpr with an anonymousClassBody)
     * and return the Expression
     *
     * /// NEED TO REVISE
     * 1) find the Statement at the stackTraceElement line number
     * 2) Walk it until I find the ObjectCreationExpr I am looking for
     *
     * @param ste the stackTraceElement containing the location of the anonymousClass declaration
     * @param resolver  resolver for resolving the .java source for the Class containing the code
     * @return an ObjectCreationExpr of an anonymous Object created

    public static ObjectCreationExpr oldanonymousClass( StackTraceElement ste, _in._resolver resolver ){
        try {
            //System.out.println( "IN ANONY " + ste +" "+ste.getClassName() +" "+ ste.getFileName()+" ");

            String tfn = ste.getFileName().substring(0, ste.getFileName().length() - ".java".length() );
            String pn = ste.getClassName().substring(0, ste.getClassName().lastIndexOf('.') );
            //try {

            //}catch (Exception e){
            //    System.out.println( e );
            //}
            Class clazz = Class.forName(pn + "."+ tfn );
            _type _t = _type.of(clazz, resolver);
            //System.out.println( "Got TYPE of "+ _t.getName() );
            //Walk.in( _t, ObjectCreationExpr.class, oce -> System.out.println( oce.getBegin().get().line + " :: "+ ste.getLineNumber()) );
            List<ObjectCreationExpr> le = Walk.list( _t, ObjectCreationExpr.class, oce -> oce.getAnonymousClassBody().isPresent()
                    && oce.getBegin().get().line == ste.getLineNumber()
                    || oce.getBegin().get().line == ste.getLineNumber() + 1
                    || oce.getBegin().get().line == ste.getLineNumber() + 2);
            if (!le.isEmpty()) {
                return le.get(0);
            }
            //return null;
            throw new DraftException("unable to find anonymous class in : ("+ste.getFileName()+ ":"+ ste.getLineNumber()+")"
                    + System.lineSeparator()+ resolver.describe());
        }catch(Exception e){
            //return null;
            if( e instanceof DraftException ){
                throw (DraftException)e;
            }
            throw new DraftException("unable to in .java source for \""+ste.getClassName()+"\""+ System.lineSeparator()
                    + resolver.describe(), e );
        }
    }
    */

    /**
     * convert the String code into a single AST Expression nod
     *
     * @param code
     * @return
     */
    public static Expression of(String... code ) {

        String str = Text.combine( code ).trim();
        if( str.equals("super") ){
            return new SuperExpr();
        }
        //a frequent mistake I make is to end expressions with ";"
        // this will fix it...(no expressions end with ;, those are ExpressionStmt
        if( str.endsWith( ";" ) ) {
            str = str.substring( 0, str.length() - 1 );
        }
        //we need to intercept ArrayInitializers,
        if( str.startsWith("{") && (str.endsWith( "}" ) ) ){

            //it could be an arrayInitialationExpresssion
            Statement st = Stmt.of("Object[] arr = "+str+";");
            ArrayInitializerExpr aie = (ArrayInitializerExpr)
                    st.asExpressionStmt().getExpression().asVariableDeclarationExpr().getVariable(0).getInitializer().get();
            aie.removeForced();
            return aie;
        }
        try{
            return JavaParser.parseExpression( str );
        }
        catch(ParseProblemException ppe){
            try {
                //normal parsing of Variable Declarations fails, we need to call a special parse method
                return JavaParser.parseVariableDeclarationExpr(str);
            } catch(Exception e ) {
                throw new DraftException("Unable to parse Expression \"" + str + "\" ", ppe);
            }
        }
    }

    public static final Class<Expression> EXPRESSION = Expression.class;

    /**
     * i.e. "arr[3]"
     */
    public static final Class<ArrayAccessExpr> ARRAY_ACCESS = ArrayAccessExpr.class;

    /**
     * i.e. "arr[3]"
     * @param code
     * @return
     */
    public static ArrayAccessExpr arrayAccess( String... code ) {
        return of( code ).asArrayAccessExpr();
    }

    /**
     * i.e. "int[][] matrix "
     */
    public static final Class<ArrayCreationExpr> ARRAY_CREATION = ArrayCreationExpr.class;

    public static ArrayCreationExpr arrayCreation( String... code ) {
        return of( code ).asArrayCreationExpr();
    }

    /**
     * i.e. "{1,2,3,4,5}"
     */
    public static final Class<ArrayInitializerExpr> ARRAY_INITIALIZER = ArrayInitializerExpr.class;

    /**
     * i.e. "{1,2,3,4,5}"
     */
    public static ArrayInitializerExpr arrayInitializer( String... code ) {
        String ai = Text.combine(code);
        ai = "new Object[] "+ai;
        ArrayInitializerExpr aie = of(ai).asArrayCreationExpr().getInitializer().get();
        aie.removeForced();
        return aie;
        //return AST.expr( code ).asArrayInitializerExpr();
    }

    /** i.e. "a = 1", "a = 4" */
    public static final Class<AssignExpr> ASSIGN = AssignExpr.class;

    /** i.e. "a = 1", "a = 4" */
    public static AssignExpr assign( String... code ) {
        return of( code ).asAssignExpr();
    }

    /** i.e. "a || b"*/
    public static final Class<BinaryExpr> BINARY = BinaryExpr.class;

    public static BinaryExpr binary( String... code ) {
        return of( code ).asBinaryExpr();
    }

    public static final Class<BooleanLiteralExpr> BOOLEAN_LITERAL = BooleanLiteralExpr.class;

    public static BooleanLiteralExpr of( boolean b ){
        return new BooleanLiteralExpr( b );
    }

    /** "true" / "false" */
    public static BooleanLiteralExpr booleanLiteral( boolean b) {
        if( b ){
            return new BooleanLiteralExpr(true);
        }
        return new BooleanLiteralExpr(false);
    }

    /** "true" / "false" */
    public static BooleanLiteralExpr booleanLiteral( String... code ) {
        return of( code ).asBooleanLiteralExpr();
    }

    public static final Class<CastExpr> CAST = CastExpr.class;

    /** (String)o */
    public static CastExpr cast( String... code ) {
        return of( code ).asCastExpr();
    }

    /** 'c' */
    public static final Class<CharLiteralExpr> CHAR_LITERAL = CharLiteralExpr.class;

    public static CharLiteralExpr of( char c ){
        return new CharLiteralExpr( c );
    }

    /** 'c' */
    public static CharLiteralExpr charLiteral( char c ) {
        return new CharLiteralExpr(c);
    }

    /** 'c' */
    public static CharLiteralExpr charLiteral( String... code ) {
        return of( code ).asCharLiteralExpr();
    }

    /** String.class */
    public static final Class<ClassExpr> CLASS = ClassExpr.class;

    /** i.e. "String.class" */
    public static ClassExpr classExpr(String... code ) {
        return of( code ).asClassExpr();
    }

    /**
     * saved ? return true;
     */
    public static final Class<ConditionalExpr> CONDITIONAL = ConditionalExpr.class;

    public static ConditionalExpr conditional( String... code ) {
        return of( code ).asConditionalExpr();
    }

    /** 3.14d */
    public static final Class<DoubleLiteralExpr> DOUBLE_LITERAL = DoubleLiteralExpr.class;

    public static DoubleLiteralExpr of( double d ){
        return new DoubleLiteralExpr( d );
    }

    public static DoubleLiteralExpr doubleLiteral( double d ) {
        return new DoubleLiteralExpr( d );
    }

    /** i.e. "3.14d" */
    public static DoubleLiteralExpr doubleLiteral( String... code ) {
        return of( code ).asDoubleLiteralExpr();
    }

    public static DoubleLiteralExpr of( float d ){
        return new DoubleLiteralExpr( d );
    }

    public static DoubleLiteralExpr doubleLiteral( float d ) {
        return new DoubleLiteralExpr( d );
    }

    /** i.e. "3.14f" */
    public static DoubleLiteralExpr floatLiteral( String... code ) {
        return of( code ).asDoubleLiteralExpr();
    }

    public static final Class<EnclosedExpr> ENCLOSED = EnclosedExpr.class;

    /** ( 4 + 5 ) */
    public static EnclosedExpr enclosedExpr( String... code ) {
        return of( code ).asEnclosedExpr();
    }

    /**
     * person.NAME
     */
    public static final Class<FieldAccessExpr> FIELD_ACCESS = FieldAccessExpr.class;

    public static FieldAccessExpr fieldAccess(String... code ) {
        return of( code ).asFieldAccessExpr();
    }

    /**
     * <PRE>"v instanceof Serializable"</PRE>
     */
    public static final Class<InstanceOfExpr> INSTANCEOF = InstanceOfExpr.class;

    public static InstanceOfExpr instanceOf(String... code ) {
        return of( code ).asInstanceOfExpr();
    }

    public static final Class<IntegerLiteralExpr> INT_LITERAL = IntegerLiteralExpr.class;


    public static IntegerLiteralExpr of(int i) {
        return new IntegerLiteralExpr(i);
    }

    public static IntegerLiteralExpr intLiteral(int i) {
        return new IntegerLiteralExpr(i);
    }

    public static IntegerLiteralExpr intLiteral(String... code ) {
        return of( code ).asIntegerLiteralExpr();
    }

    public static final Class<LambdaExpr> LAMBDA = LambdaExpr.class;

    /**
     * parses and returns a lambda expression from the code
     * @param code
     * @return
     */
    public static LambdaExpr lambda(String... code ) {
        return of( code ).asLambdaExpr();
    }

    /**
     * Builds a lambda expression from the *CODE* passed in... i,.e.
     * <PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param c a "command" or no arg, no return lambda implementation
     * @return the LambdaExpr instance
     */
    public static LambdaExpr lambda( Expr.Command c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in... i,.e.
     * <PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <T extends Object,U extends Object>  LambdaExpr lambda( Function<T, U> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in... i,.e.
     * <PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <T extends Object,U extends Object, V extends Object> LambdaExpr lambda( BiFunction<T, U, V> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in... i,.e.
     * <PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <T extends Object,U extends Object, V extends Object, W extends Object> LambdaExpr lambda( TriFunction<T, U, V, W> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in... i,.e.
     * <PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <T extends Object,U extends Object, V extends Object, W extends Object, X extends Object> LambdaExpr lambda( QuadFunction<T, U, V, W, X> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in... i,.e.
     * <PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <T extends Object>  LambdaExpr lambda( Consumer<T> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in... i,.e.
     * <PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <T extends Object, U extends Object>  LambdaExpr lambda( BiConsumer<T, U> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in... i,.e.
     * <PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <T extends Object, U extends Object, V extends Object> LambdaExpr lambda( Expr.TriConsumer<T, U, V> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in... i,.e.
     * <PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <A extends Object, B extends Object, C extends Object, D extends Object> LambdaExpr lambda( Expr.QuadConsumer<A,B,C,D> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    //Throw, Scychronized
    public static final Class<LongLiteralExpr> LONG_LITERAL = LongLiteralExpr.class;

    public static LongLiteralExpr of(long l) {
        return new LongLiteralExpr(l);
    }

    public static LongLiteralExpr longLiteral( long l ) {
        return new LongLiteralExpr(l);
    }

    public static LongLiteralExpr longLiteral( String... code ) {
        return new LongLiteralExpr(Text.combine(code));
        //return of( Expr.longLiteral( code) ).asLongLiteralExpr();
    }

    public static final Class<MethodCallExpr> METHOD_CALL = MethodCallExpr.class;

    public static MethodCallExpr methodCall( String... code ) {
        return of( code ).asMethodCallExpr();
    }

    /** i.e. "String:toString" */
    public static final Class<MethodReferenceExpr> METHOD_REFERENCE = MethodReferenceExpr.class;

    /** i.e. "String:toString" */
    public static MethodReferenceExpr methodReference( String... code ) {
        String r = Text.combine(code);
        r = "o -> "+ r;
        //System.out.println( r );
        LambdaExpr rs = lambda(r);
        MethodReferenceExpr mre = rs.getExpressionBody().get().asMethodReferenceExpr();
        mre.removeForced(); //DISCONNECT
        return mre;
        //return AST.expr( code ).asMethodReferenceExpr();
    }

    public static final Class<NameExpr> NAME = NameExpr.class;

    public static NameExpr name( String... code ) {
        return of( code ).asNameExpr();
    }

    /**
     * Any implementation {@link AnnotationExpr}
     * ({@link MarkerAnnotationExpr}, {@link SingleMemberAnnotationExpr}, {@link NormalAnnotationExpr})
     */
    public static final Class<AnnotationExpr> ANNOTATION_ANY = AnnotationExpr.class;

    /**
     * i.e. {@code @Deprecated }
     */
    public static final Class<MarkerAnnotationExpr> ANNOTATION_MARKER = MarkerAnnotationExpr.class;
    /**
     * @Generated("2/14/1985")
     */
    public static final Class<SingleMemberAnnotationExpr> ANNOTATION_SINGLE_MEMBER = SingleMemberAnnotationExpr.class;
    /**
     * @KeyValue(key1="string",key2=12)
     */
    public static final Class<NormalAnnotationExpr> ANNOTATION_NORMAL = NormalAnnotationExpr.class;

    public static AnnotationExpr annotation( String... code ) {
        return of( code ).asAnnotationExpr();
    }

    public static final Class<NullLiteralExpr> NULL = NullLiteralExpr.class;

    public static NullLiteralExpr nullExpr(){
        return new NullLiteralExpr();
    }

    public static final Class<ObjectCreationExpr> OBJECT_CREATION = ObjectCreationExpr.class;

    public static ObjectCreationExpr objectCreation(String... code ) {
        return of( code ).asObjectCreationExpr();
    }

    public static final Class<StringLiteralExpr> STRING_LITERAL = StringLiteralExpr.class;

    public static StringLiteralExpr stringLiteral( String... code ) {
        String str = Text.combine( code );
        if(! str.startsWith("\"") ){
            str = "\""+str;
        }
        if(! str.endsWith("\"") ){
            str = str+"\"";
        }
        return of( str ).asStringLiteralExpr();
    }
    /** "super" */
    public static final Class<SuperExpr> SUPER = SuperExpr.class;

    /** "super" */
    public static SuperExpr superExpr(  ) {
        return new SuperExpr();
    }
    public static final Class<ThisExpr> THIS = ThisExpr.class;

    public static ThisExpr thisExpr(  ) {
        return new ThisExpr();
    }

    public static final Class<TypeExpr> TYPE = TypeExpr.class;

    public static TypeExpr typeExpr(String... code ) {
        //World::greet
        String str = Text.combine(code);
        str = str + "::method";
        TypeExpr te =  of( str ).asMethodReferenceExpr().getScope().asTypeExpr();
        te.removeForced(); //DISCONNECT
        return te;
    }

    public static final Class<UnaryExpr> UNARY = UnaryExpr.class;

    public static UnaryExpr unary( String... code ) {
        return of( code ).asUnaryExpr();
    }

    /**
     * int i = 1
     */
    public static final Class<VariableDeclarationExpr> VARIABLE_DECLARATION = VariableDeclarationExpr.class;

    public static VariableDeclarationExpr varDecl( String... code ) {
        return JavaParser.parseVariableDeclarationExpr( Text.combine( code ));
    }
}
