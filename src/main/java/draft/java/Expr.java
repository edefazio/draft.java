package draft.java;

import com.github.javaparser.*;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.stmt.Statement;
import draft.DraftException;
import draft.Text;
import draft.java.io._in;
import draft.java.io._io;
import draft.java.io._ioException;
import java.util.*;
import java.util.function.*;

/**
 * Utility for converting free form Strings and Runtime entities (lambdas, Anonymous Objects)
 * into JavaParser AST {@link Expression} implementations.
 * 
 * Simplify the mediation between different representations of the same thing.
 * @author M. Eric DeFazio
 */
public enum Expr {
    ;

    /**
     * Functional interface for no input, no return lambda function
     * (Used when we pass in Lambdas to the {@link Expr#lambda(Command)} operation
     * in order to get the LamdbaExpr AST from the Runtime to a 
     * {@link com.github.javaparser.ast.expr.LambdaExpr}
     */
    @FunctionalInterface
    public interface Command{
        void consume();
    }

    /**
     * Functional interface for (3) input, (1) return lambda function
     * (Used when we pass in Lambdas to the {@link Expr#lambda(TriFunction)}  
     * operation
     * {@link com.github.javaparser.ast.expr.LambdaExpr}
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <D>
     */
    @FunctionalInterface
    public interface TriFunction<A,B,C,D>{
        D apply(A a, B b, C c);
    }

    /**
     * Functional interface for (4) input PARAMETERS, (1) return lambda function
     * (Used when we pass in Lambdas to the {@link Expr#lambda(QuadFunction)} 
     * operation)
     * {@link com.github.javaparser.ast.expr.LambdaExpr}
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <D>
     * @param <E>
     */
    @FunctionalInterface
    public interface QuadFunction<A,B,C,D,E>{
        E apply(A a, B b, C c, D d);
    }

    /**
     * Functional interface for (3) input PARAMETERS, no return lambda function
     * (Used when we pass in Lambdas to the {@link Expr#lambda(TriConsumer)} 
     * operation)
     * {@link com.github.javaparser.ast.expr.LambdaExpr}
     * @param <T>
     * @param <U>
     * @param <V>
     */
    @FunctionalInterface
    public interface TriConsumer<T,U,V>{
        void consume(T t, U u, V v);
    }

    /**
     * Functional interface for (4) input PARAMETERS, no return lambda function
     * (Used when we pass in Lambdas to the {@link Expr#lambda(QuadConsumer)} 
     * operation
     * {@link com.github.javaparser.ast.expr.LambdaExpr}
     * @param <T>
     * @param <V>
     * @param <U>
     * @param <Z>
     */
    @FunctionalInterface
    public interface QuadConsumer<T,U,V,Z>{
        void consume(T t, U u, V v, Z z);
    }

    /**
     * Resolves and returns the AST LambdaExpr representing the Runtime Lambda passed in
     * for example:
     * <PRE>
     * LambdaExpr le = Expr.of( ()-> System.out.println(1) );
     * assertEquals( Stmt.of("System.out.println(1);"), le.getBody().getStatement(0) );
     * </PRE>
     * NOTE: the source of the calling method must be resolveable via draft
     * @see draft.java.io._io#addInFilePath(java.lang.String) 
     * @see draft.java.io._io#addInProject(java.lang.String) 
     * @param c the runtime Lambda Expression
     * @return the AST LambdaExpr representation for the runtime Command
     */
    public static LambdaExpr of( Expr.Command c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Resolves and returns the AST LambdaExpr representing the Runtime Lambda passed in
     * for example:
     * <PRE>
     * LambdaExpr le = Expr.of( ()-> System.out.println(1) );
     * assertEquals( Stmt.of("System.out.println(1);"), le.getBody().getStatement(0) );
     * </PRE>
     * NOTE: the source of the calling method must be resolveable via draft
     * @param <T>
     * @see draft.java.io._io#addInFilePath(java.lang.String) 
     * @see draft.java.io._io#addInProject(java.lang.String) 
     * @param c the runtime Lambda Expression
     * @return the AST LambdaExpr representation for the runtime Command
     */
    public static <T extends Object> LambdaExpr of( Consumer<T> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Resolves and returns the AST LambdaExpr representing the Runtime Lambda passed in
     * for example:
     * <PRE>
     * LambdaExpr le = Expr.of( ()-> System.out.println(1) );
     * assertEquals( Stmt.of("System.out.println(1);"), le.getBody().getStatement(0) );
     * </PRE>
     * NOTE: the source of the calling method must be resolveable via draft     
     * @see draft.java.io._io#addInFilePath(java.lang.String) 
     * @see draft.java.io._io#addInProject(java.lang.String) 
     * @param <T>
     * @param <U>
     * @param c the runtime Lambda Expression
     * @return the AST LambdaExpr representation for the runtime Command
     */
    public static <T extends Object, U extends Object> LambdaExpr of( BiConsumer<T, U> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Resolves and returns the AST LambdaExpr representing the Runtime Lambda passed in
     * for example:
     * <PRE>
     * LambdaExpr le = Expr.of( ()-> System.out.println(1) );
     * assertEquals( Stmt.of("System.out.println(1);"), le.getBody().getStatement(0) );
     * </PRE>
     * NOTE: the source of the calling method must be resolveable via draft
     * @see draft.java.io._io#addInFilePath(java.lang.String) 
     * @see draft.java.io._io#addInProject(java.lang.String) 
     * @param <T>
     * @param <U>
     * @param <V>
     * @param c the runtime Lambda Expression
     * @return the AST LambdaExpr representation for the runtime Command
     */
    public static <T extends Object, U extends Object, V extends Object> LambdaExpr of( TriConsumer<T, U, V> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Resolves and returns the AST LambdaExpr representing the Runtime Lambda passed in
     * for example:
     * <PRE>
     * LambdaExpr le = Expr.of( ()-> System.out.println(1) );
     * assertEquals( Stmt.of("System.out.println(1);"), le.getBody().getStatement(0) );
     * </PRE>
     * NOTE: the source of the calling method must be resolveable via draft
     * @see draft.java.io._io#addInFilePath(java.lang.String) 
     * @see draft.java.io._io#addInProject(java.lang.String) 
     * @param <U>
     * @param <T>
     * @param c the runtime Lambda Expression
     * @return the AST LambdaExpr representation for the runtime Command
     */
    public static <T extends Object, U extends Object> LambdaExpr of( Function<T, U> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Resolves and returns the AST LambdaExpr representing the Runtime Lambda passed in
     * for example:
     * <PRE>
     * LambdaExpr le = Expr.of( ()-> System.out.println(1) );
     * assertEquals( Stmt.of("System.out.println(1);"), le.getBody().getStatement(0) );
     * </PRE>
     * NOTE: the source of the calling method must be resolveable via draft
     * @see draft.java.io._io#addInFilePath(java.lang.String) 
     * @see draft.java.io._io#addInProject(java.lang.String) 
     * @param <T>
     * @param <U>
     * @param <V>
     * @param c the runtime Lambda Expression
     * @return the AST LambdaExpr representation for the runtime Command
     */
    public static <T extends Object, U extends Object, V extends Object> LambdaExpr of( BiFunction<T, U, V> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Resolves and returns the AST LambdaExpr representing the Runtime Lambda passed in
     * for example:
     * <PRE>
     * LambdaExpr le = Expr.of( ()-> System.out.println(1) );
     * assertEquals( Stmt.of("System.out.println(1);"), le.getBody().getStatement(0) );
     * </PRE>
     * NOTE: the source of the calling method must be resolveable via draft
     * @see draft.java.io._io#addInFilePath(java.lang.String) 
     * @see draft.java.io._io#addInProject(java.lang.String) 
     * @param <T>
     * @param <U>
     * @param <V>
     * @param <Z>
     * @param c the runtime Lambda Expression
     * @return the AST LambdaExpr representation for the runtime Command
     */
    public static <T extends Object, U extends Object, V extends Object, Z extends Object> LambdaExpr of( TriFunction<T, U, V, Z> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Resolves and returns the AST LambdaExpr representing the Runtime Lambda passed in
     * for example:
     * <PRE>
     * LambdaExpr le = Expr.of( ()-> System.out.println(1) );
     * assertEquals( Stmt.of("System.out.println(1);"), le.getBody().getStatement(0) );
     * </PRE>
     * NOTE: the source of the calling method must be resolveable via draft
     * @see draft.java.io._io#addInFilePath(java.lang.String) 
     * @see draft.java.io._io#addInProject(java.lang.String) 
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <D>
     * @param c the runtime Lambda Expression
     * @return the AST LambdaExpr representation for the runtime Command
     */
    public static <A extends Object, B extends Object, C extends Object, D extends Object> LambdaExpr of( QuadConsumer<A,B,C,D> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Resolves and returns the AST LambdaExpr representing lambda expression 
     * that is referenced from this stackTraceElement line
     * for example:
     * <PRE>
     * //call the method f with a lambdaExpression
     * f( (String s) -> System.out.println(s) );
     * //...
     * 
     * //the method f() accepts a Lambda expression, but we WANT the actual
     * // AST (we dont "use" the runtime lambda expression for anything other
     * // than to represent the AST
     * void f( Consumer c ){
     *      StackTraceElement ste = Thread.currentThread().getStackTrace[2];
     *      LambdaExpr le = Expr.lambda( ste );
     *      assertEquals( Stmt.of("System.out.println(1);"), le.getBody().getStatement(0) );
     * }
     * </PRE>
     * NOTE: the source of the calling method StackTraceELement 
     * must be resolveable via draft
     * @see draft.java.io._io#addInFilePath(java.lang.String) 
     * @see draft.java.io._io#addInProject(java.lang.String) 
     * 
     * @param ste the stack trace Element line containing the code to draw from  
     * @return the AST LambdaExpr representation for the runtime Command
     */
    public static LambdaExpr lambda( StackTraceElement ste ) {
        return lambda(ste, _io.IN_DEFAULT );
    }

    /**
     * Resolves and returns the AST LambdaExpr representing lambda expression 
     * that is referenced from this stackTraceElement line
     * for example:
     * <PRE>
     * //call the method f with a lambdaExpression
     * f( (String s) -> System.out.println(s) );
     * //...
     * 
     * //the method f() accepts a Lambda expression, but we WANT the actual
     * // AST (we dont "use" the runtime lambda expression for anything other
     * // than to represent the AST
     * void f( Consumer c ){
     *      StackTraceElement ste = Thread.currentThread().getStackTrace[2];
     *      LambdaExpr le = Expr.lambda( ste );
     *      assertEquals( Stmt.of("System.out.println(1);"), le.getBody().getStatement(0) );
     * }
     * </PRE>
     * NOTE: the source of the calling method StackTraceELement 
     * must be resolveable via draft
     * @see draft.java.io._io#addInFilePath(java.lang.String) 
     * @see draft.java.io._io#addInProject(java.lang.String) 
     * 
     * @param ste the stack trace Element line containing the code to draw from  
     * @param resolver the resolver for finding the source referenced in the StackTraceElement line
     * @return the AST LambdaExpr representation for the runtime Command
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
            _t.ast(),
            Ast.METHOD_CALL_EXPR,
            mce-> mce.getRange().isPresent() && mce.getRange().get().begin.line <= lineNumber
                && mce.getRange().get().end.line >= lineNumber
                && mce.getArguments().stream().filter(a-> a instanceof LambdaExpr).findFirst().isPresent()                
        );

        // we always want to start with the LAST one, since we could have a nested
        // grouping of statements
        for(int i=ln.size()-1;i>=0;i--){
            Optional<Node> on = ln.get(i).stream().filter(n -> n instanceof LambdaExpr).findFirst();
            if( on.isPresent() ){
                return (LambdaExpr)on.get();
            }
        }
        throw new _ioException("unable to find in lambda at (" + ste.getFileName() + ":" + ste.getLineNumber() + ")"+System.lineSeparator()+ _io.describe());
    }

    /**
     * Return the AST (ObjectCreationExpr) for the .java SOURCE code 
     * of the Anonymous Runtime Object passed in.
     * 
     * for instance:
     * <PRE>
     * //return the ObjectCreationExpr (the AST for the Runtime Object passed in)
     * ObjectCreationExpr oce = Expr.anonymousObject( new Object(){ int x,y;} );
     * NodeList<BodyExpression<?>> body =  oce.getBody().get();
     * //do something with the AST
     * </PRE>
     * 
     * NOTE: there are important implications here: 
     * 
     * 1) THE Object passed in MUST BE an anonymous Object
     * 2) The .java Source for the code calling this method MUST be locateable by draft 
     * either
     * <UL>
     *  <LI>on the classpath
     *  <LI>in user.dir System property ( this is by default for most IDES like
     *      Eclipse, NetBeans, IntelliJ
     *  <LI>on one of the manually configured in.paths 
     * {@link draft.java.io._io._config#inFilesPath(java.lang.String)} 
     * {@link draft.java.io._io._config#inProjectsPath(java.lang.String)}    
     * </UL>
     * 
     * alternatively, you can use the {@link #anonymousObject(java.lang.Object, draft.java.io._in._resolver) }
     * method and pass in an _in.resolver to locate the code manually
     * 
     * @see draft.java.io._io._config#inFilesPath(java.lang.String)
     * @see draft.java.io._io._config#inProjectsPath(java.lang.String)
     * @param anonymousObject an anonymous Object
     * @return the ObjectCreationExpr AST representation of the anonymousObject passed in
     */
    public static ObjectCreationExpr anonymousObject ( Object anonymousObject ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return anonymousObject(ste, _io.IN_DEFAULT);
    }

    /**
     * Return the AST (ObjectCreationExpr) for the .java SOURCE code 
     * of the Runtime Anonymous Object passed in.
     * 
     * for instance:
     * <PRE>
     * //return the ObjectCreationExpr (the AST for the Runtime Object passed in)
     * ObjectCreationExpr oce = Expr.anonymousObject( new Object(){ int x,y;} );
     * NodeList<BodyExpression<?>> body =  oce.getBody().get();
     * //do something with the AST
     * </PRE>
     * 
     * NOTE: there are important implications here: 
     * 
     * 1) THE Object passed in MUST BE an anonymous Object
     * 2) The .java Source for the code calling this method MUST be locateable by draft 
     * either
     * <UL>
     *  <LI>on the classpath
     *  <LI>in user.dir System property ( this is by default for most IDES like
     *      Eclipse, NetBeans, IntelliJ
     *  <LI>on one of the manually configured in.paths 
     * {@link draft.java.io._io._config#inFilesPath(java.lang.String)} 
     * {@link draft.java.io._io._config#inProjectsPath(java.lang.String)}    
     * </UL>
     * 
     * 
     * @see draft.java.io._io._config#inFilesPath(java.lang.String)
     * @see draft.java.io._io._config#inProjectsPath(java.lang.String)
     * @param anonymousObject an anonymous Object of which we want the AST ObjectCreationExpr
     * representation of the source
     * @param resolver a resolver to use for looking up the source for the calling
     * code
     * @return the ObjectCreationExpr AST representation of the anonymousObject passed in
     */
    public static ObjectCreationExpr anonymousObject(Object anonymousObject, _in._resolver resolver){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return anonymousObject(ste, resolver);
    }

    /**
     * Return the AST (ObjectCreationExpr) for the .java SOURCE code 
     * of the Runtime Anonymous Object passed in.
     * 
     * for instance:
     * <PRE>
     * //return the ObjectCreationExpr (the AST for the Runtime Object passed in)
     * ObjectCreationExpr oce = Expr.anonymousObject( new Object(){ int x,y;} );
     * NodeList<BodyExpression<?>> body =  oce.getBody().get();
     * //do something with the AST
     * </PRE>
     * 
     * NOTE: there are important implications here: 
     * 
     * 1) THE Object passed in MUST BE an anonymous Object
     * 2) The .java Source for the code calling this method MUST be locateable by draft 
     * either
     * <UL>
     *  <LI>on the classpath
     *  <LI>in user.dir System property ( this is by default for most IDES like
     *      Eclipse, NetBeans, IntelliJ
     *  <LI>on one of the manually configured in.paths 
     * {@link draft.java.io._io._config#inFilesPath(java.lang.String)} 
     * {@link draft.java.io._io._config#inProjectsPath(java.lang.String)}    
     * </UL>
     * 
     * @see draft.java.io._io._config#inFilesPath(java.lang.String)
     * @see draft.java.io._io._config#inProjectsPath(java.lang.String)
     * @param ste the stackTraceElement line referring to the lone of code where the
     * 
     * @return the ObjectCreationExpr AST representation of the anonymousObject passed in
     * @throws _ioException if unable to resolve the Source of the anonymousObject
     */
    public static ObjectCreationExpr anonymousObject( StackTraceElement ste ){
         return anonymousObject(ste, _io.IN_DEFAULT );
    }

    /**
     * Return the AST (ObjectCreationExpr) for the .java SOURCE code 
     * of the Runtime Anonymous Object passed in.for instance:
     * <PRE>
     * //return the ObjectCreationExpr (the AST for the Runtime Object passed in)
     * ObjectCreationExpr oce = Expr.anonymousObject( new Object(){ int x,y;} );
     * NodeList<BodyExpression<?>> body =  oce.getBody().get();
     * //do something with the AST
     * </PRE>
     * 
     * NOTE: there are important implications here: 
     * 
     * 1) THE Object passed in MUST BE an anonymous Object
     * 2) The .java Source for the code calling this method MUST be locateable by draft 
     * either
     * <UL>
     *  <LI>on the classpath
     *  <LI>in user.dir System property ( this is by default for most IDES like
     *      Eclipse, NetBeans, IntelliJ
     *  <LI>on one of the manually configured in.paths 
     * {@link draft.java.io._io._config#inFilesPath(java.lang.String)} 
     * {@link draft.java.io._io._config#inProjectsPath(java.lang.String)}    
     * </UL>
     * 
     * @param resolver the resolver for the source code
     * @see draft.java.io._io._config#inFilesPath(java.lang.String)
     * @see draft.java.io._io._config#inProjectsPath(java.lang.String)
     * @param ste the stackTraceElement line referring to the lone of code where the
     * 
     * @return the ObjectCreationExpr AST representation of the anonymousObject 
     * referred to from the StackTraceElement passed in
     * @throws _ioException if unable to resolve the Source of the anonymousObject
     */
    public static ObjectCreationExpr anonymousObject( StackTraceElement ste, _in._resolver resolver ){
        _type _t = null;
        try {
            Class clazz = Class.forName(ste.getClassName());
            _t = _type.of(clazz, resolver);
        } catch (Exception e) {
            throw new _ioException("no .java source for Runtime Class \"" + ste.getClassName() + "\" " + System.lineSeparator() +
                resolver.describe(), e); //print out the input config to help
        }

        //find all of the potential method calls that could be the call 
        //mentioned in the stack trace based on the line numbers 
        List<MethodCallExpr> mces = Walk.list(
                Ast.WALK_POST_ORDER,
                _t.ast(),
                Ast.METHOD_CALL_EXPR,
                (MethodCallExpr mce) -> ((MethodCallExpr)mce).getRange().get().begin.line <= ste.getLineNumber() &&
                        ((MethodCallExpr)mce).getRange().get().end.line >= ste.getLineNumber() &&
                        mce.getArguments().stream().filter( e-> e.isObjectCreationExpr() && e.asObjectCreationExpr().getAnonymousClassBody().isPresent() ).findFirst().isPresent()
        );
        for(int i=0; i<mces.size();i++ ){
            //find the particular methodCall containing the anonymous Object being created
            //
            Optional<Expression> on =
                mces.get(i).getArguments().stream().filter( a -> a instanceof ObjectCreationExpr
                    && a.asObjectCreationExpr().getAnonymousClassBody().isPresent()).findFirst();
            if( on.isPresent() ){
                return (ObjectCreationExpr)on.get();
            }
        }
        throw new _ioException("unable to find in anonymous object at (" + ste.getFileName() + ":"
            + ste.getLineNumber() + ")" + System.lineSeparator() + resolver.describe());

    }

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
        String comment = null;
        int endComment = str.indexOf("*/");
        if( str.startsWith("/*") && endComment > 0 ) {
            //we need to manually "save" the comment
            comment = str.substring(0, endComment + 2);
            str = str.substring(endComment+2);
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
            Expression e = StaticJavaParser.parseExpression( str );
            if( comment != null ){
                if( comment.startsWith("/**") ){
                    JavadocComment jdc = new JavadocComment( comment.replace("/**", "" ).replace("*/", ""));    
                    e.setComment( jdc);
                } else{
                    BlockComment bc = new BlockComment(comment.replace("/*", "" ).replace("*/", ""));                       
                    e.setComment(bc);
                }                
            }
            return e;
        }
        catch(ParseProblemException ppe){
            try {
                //normal parsing of Variable Declarations fails, we need to call a special parse method
                return StaticJavaParser.parseVariableDeclarationExpr(str);
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
    
    public static ArrayInitializerExpr arrayInitializer( int[] intArray ){
        return of( intArray);
    }
    
    public static ArrayInitializerExpr of( int[] intArray ){
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for(int i=0;i<intArray.length;i++){
            if( i > 0 ){
                sb.append(",");
            }
            sb.append( intArray[i] );
        }
        sb.append(" }");
        return arrayInitializer( sb.toString() );
    }
    
    public static ArrayInitializerExpr arrayInitializer( float[] floatArray ){
        return of( floatArray);
    }
    
    public static ArrayInitializerExpr of( float[] array ){
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for(int i=0;i<array.length;i++){
            if( i > 0 ){
                sb.append(",");
            }
            sb.append( array[i] ).append("f");
        }
        sb.append(" }");
        return arrayInitializer( sb.toString() );
    }
    
    public static ArrayInitializerExpr arrayInitializer( double[] array ){
        return of( array);
    }
    
    public static ArrayInitializerExpr of( double[] array ){
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for(int i=0;i<array.length;i++){
            if( i > 0 ){
                sb.append(",");
            }
            sb.append( array[i] ).append("d");
        }
        sb.append(" }");
        return arrayInitializer( sb.toString() );
    }

    public static ArrayInitializerExpr arrayInitializer( boolean[] array ){
        return of( array);
    }
    
    public static ArrayInitializerExpr of( boolean[] array ){
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for(int i=0;i<array.length;i++){
            if( i > 0 ){
                sb.append(",");
            }
            sb.append( array[i] );
        }
        sb.append(" }");
        return arrayInitializer( sb.toString() );
    }
    
    public static ArrayInitializerExpr arrayInitializer( char[] array ){
        return of( array );
    }
    
    public static ArrayInitializerExpr of( char[] array ){
        StringBuilder sb = new StringBuilder();
        sb.append("{ ");
        for(int i=0;i<array.length;i++){
            if( i > 0 ){
                sb.append(",");
            }
            sb.append("'").append( array[i] ).append("'");
        }
        sb.append(" }");
        return arrayInitializer( sb.toString() );
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
     * Builds & returns a EnclosedExpr based on the FIRST method call inside
     * the body of the lambda passed in
     * 
     * (SOURCE PASSING)
     * 
     * @param lambdaWithFieldAccessInSource a lambda expression containing the 
     * source with a METHOD CALL that will be converted into a EnclosedExpr
     * Ast node
     * @return the EnclosedExpr Ast Node representing the first method call
     * in the lambda body
     */
    public static EnclosedExpr enclosedExpr( Function<? extends Object,? extends Object> lambdaWithFieldAccessInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return astLambda.getBody().findFirst(EnclosedExpr.class).get();
    }

    /**
     * person.NAME
     */
    public static final Class<FieldAccessExpr> FIELD_ACCESS = FieldAccessExpr.class;

    public static FieldAccessExpr fieldAccess(String... code ) {
        return of( code ).asFieldAccessExpr();
    }

    /**
     * Builds & returns a MethodCallExpr based on the FIRST method call inside
     * the body of the lambda passed in
     * 
     * (SOURCE PASSING)
     * 
     * @param lambdaWithFieldAccessInSource a lambda expression containing the 
     * source with a METHOD CALL that will be converted into a MethodCallExpr
     * Ast node
     * @return the FieldAccessExpr Ast Node representing the first method call
     * in the lambda body
     */
    public static FieldAccessExpr fieldAccess( Function<? extends Object,? extends Object> lambdaWithFieldAccessInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return astLambda.getBody().findFirst(FieldAccessExpr.class).get();
    }
    
    /**
     * <PRE>"v instanceof Serializable"</PRE>
     */
    public static final Class<InstanceOfExpr> INSTANCEOF = InstanceOfExpr.class;

    public static InstanceOfExpr instanceOf(String... code ) {
        return of( code ).asInstanceOfExpr();
    }
    
    public static InstanceOfExpr instanceOf( Function<? extends Object, ? extends Object> fun ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr le = lambda(ste);
        return le.getBody().findFirst(InstanceOfExpr.class).get();
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
     * Builds a lambda expression from the *CODE* passed in...i,.e.<PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param <T>
     * @param <U>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <T extends Object,U extends Object>  LambdaExpr lambda( Function<T, U> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in...i,.e.<PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param <T>
     * @param <U>
     * @param <V>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <T extends Object,U extends Object, V extends Object> LambdaExpr lambda( BiFunction<T, U, V> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in...i,.e.<PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param <T>
     * @param <U>
     * @param <V>
     * @param <W>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <T extends Object,U extends Object, V extends Object, W extends Object> LambdaExpr lambda( TriFunction<T, U, V, W> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in...i,.e.<PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param <T>
     * @param <U>
     * @param <V>
     * @param <W>
     * @param <X>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <T extends Object,U extends Object, V extends Object, W extends Object, X extends Object> LambdaExpr lambda( QuadFunction<T, U, V, W, X> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in...i,.e.<PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param <T>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <T extends Object>  LambdaExpr lambda( Consumer<T> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in...i,.e.<PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param <T>
     * @param <U>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <T extends Object, U extends Object>  LambdaExpr lambda( BiConsumer<T, U> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in...i,.e.<PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param <T>
     * @param <U>
     * @param <V>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <T extends Object, U extends Object, V extends Object> LambdaExpr lambda( Expr.TriConsumer<T, U, V> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }

    /**
     * Builds a lambda expression from the *CODE* passed in...i,.e.<PRE>
     * Expr.lamdba( ()-> assert(true) );  will return the same as
     * Expr.lambda("()->assert(true)");
     * </PRE>
     * @param <A>
     * @param <B>
     * @param <C>
     * @param <D>
     * @param c a lambda
     * @return the LambdaExpr instance
     */
    public static <A extends Object, B extends Object, C extends Object, D extends Object> LambdaExpr lambda( Expr.QuadConsumer<A,B,C,D> c ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        return lambda( ste );
    }
    
    public static final Class<LongLiteralExpr> LONG_LITERAL = LongLiteralExpr.class;

    public static LongLiteralExpr of(long l) {
        return new LongLiteralExpr(l);
    }

    public static LongLiteralExpr longLiteral( long l ) {
        return new LongLiteralExpr(l);
    }

    public static LongLiteralExpr longLiteral( String... code ) {
        return new LongLiteralExpr(Text.combine(code));
    }

    public static final Class<MethodCallExpr> METHOD_CALL = MethodCallExpr.class;

    public static MethodCallExpr methodCall( String... code ) {
        return of( code ).asMethodCallExpr();
    }

    /**
     * Builds & returns a MethodCallExpr based on the FIRST method call inside
     * the body of the lambda passed in
     * 
     * (SOURCE PASSING)
     * 
     * @param lambdaWithMethodCallInSource a lambda expression containing the 
     * source with a METHOD CALL that will be converted into a MethodCallExpr
     * Ast node
     * @return the MethodCallExpr Ast Node representing the first method call
     * in the lambda body
     */
    public static MethodCallExpr methodCall( Expr.Command lambdaWithMethodCallInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return astLambda.getBody().findFirst(MethodCallExpr.class).get();
    }

    /**
     * Builds & returns a MethodCallExpr based on the FIRST method call inside
     * the body of the lambda passed in
     * 
     * (SOURCE PASSING)
     * 
     * @param lambdaWithMethodCallInSource a lambda expression containing the 
     * source with a METHOD CALL that will be converted into a MethodCallExpr
     * Ast node
     * @return the MethodCallExpr Ast Node representing the first method call
     * in the lambda body
     */
    public static MethodCallExpr methodCall( Consumer<? extends Object> lambdaWithMethodCallInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return astLambda.getBody().findFirst(MethodCallExpr.class).get();
    }    
    
    /**
     * Builds & returns a MethodCallExpr based on the FIRST method call inside
     * the body of the lambda passed in
     * 
     * (SOURCE PASSING)
     * 
     * @param lambdaWithMethodCallInSource a lambda expression containing the 
     * source with a METHOD CALL that will be converted into a MethodCallExpr
     * Ast node
     * @return the MethodCallExpr Ast Node representing the first method call
     * in the lambda body
     */
    public static MethodCallExpr methodCall( BiConsumer<? extends Object,? extends Object> lambdaWithMethodCallInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return astLambda.getBody().findFirst(MethodCallExpr.class).get();
    }  
    
    /**
     * Builds & returns a MethodCallExpr based on the FIRST method call inside
     * the body of the lambda passed in
     * 
     * (SOURCE PASSING)
     * 
     * @param lambdaWithMethodCallInSource a lambda expression containing the 
     * source with a METHOD CALL that will be converted into a MethodCallExpr
     * Ast node
     * @return the MethodCallExpr Ast Node representing the first method call
     * in the lambda body
     */
    public static MethodCallExpr methodCall( TriConsumer<? extends Object,? extends Object, ? extends Object> lambdaWithMethodCallInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return astLambda.getBody().findFirst(MethodCallExpr.class).get();
    }
    
    /**
     * Builds & returns a MethodCallExpr based on the FIRST method call inside
     * the body of the lambda passed in
     * 
     * (SOURCE PASSING)
     * 
     * @param lambdaWithMethodCallInSource a lambda expression containing the 
     * source with a METHOD CALL that will be converted into a MethodCallExpr
     * Ast node
     * @return the MethodCallExpr Ast Node representing the first method call
     * in the lambda body
     */
    public static MethodCallExpr methodCall( QuadConsumer<? extends Object,? extends Object, ? extends Object, ? extends Object> lambdaWithMethodCallInSource ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr astLambda = Expr.lambda(ste);
        return astLambda.getBody().findFirst(MethodCallExpr.class).get();
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
    
    /**
     * accepts a lambda expression and (DOES NOT RUN IT) but rather
     * reads the body code and returns 
     * 
     * i.e.
     * <PRE>
     * ObjectCreationExpression oce = Ast.objectCreation( ()-> new HashMap());
     * </PRE>
     * @param lambdaThatCreatesObject a supplier lambda expression that must contain a "new"
     * @return the AST ObjectCreation Expression representing the new
     */
    public static ObjectCreationExpr objectCreation( Supplier<? extends Object> lambdaThatCreatesObject ) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr le = lambda(ste);
        return le.getBody().findFirst(ObjectCreationExpr.class).get();        
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
    
    public static ThisExpr superExpr( String... expr ){
        return (ThisExpr)StaticJavaParser.parseExpression(Text.combine(expr));
    }
    
    public static final Class<ThisExpr> THIS = ThisExpr.class;

    public static ThisExpr thisExpr(  ) {
        return new ThisExpr();
    }
    
    public static ThisExpr thisExpr( String... expr ){
        return (ThisExpr)StaticJavaParser.parseExpression(Text.combine(expr));
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

    /**
     * accepts a lambda expression and (DOES NOT RUN IT) but rather
     * reads the body code and returns 
     * 
     * i.e.
     * <PRE>
     * UnaryExpr oce = Ast.objectCreation( ()-> !true);
     * </PRE>
     * @param lambdaThatCreatesObject a supplier lambda expression that must contain a "new"
     * @return the AST ObjectCreation Expression representing the new
     */
    public static UnaryExpr unary( Supplier<? extends Object> lambdaThatCreatesObject ) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr le = lambda(ste);
        return le.getBody().findFirst(UnaryExpr.class).get();        
    }
    
    public static UnaryExpr unary( String... code ) {
        return of( code ).asUnaryExpr();
    }
    
    /**
     * int i = 1
     */
    public static final Class<VariableDeclarationExpr> VARIABLE_DECLARATION = VariableDeclarationExpr.class;

    public static VariableDeclarationExpr varDecl( String... code ) {
        return StaticJavaParser.parseVariableDeclarationExpr( Text.combine( code ));
    }    
    
    /**
     * 
     * @param exp
     * @param o could be another expression, a String, or a value (integer, Float, array, etc.)
     * @return 
     */
    public static boolean equatesTo (Expression exp, Object o) {
        if( o == null || o instanceof NullLiteralExpr || o.equals("null") ) {
            return exp.equals( new NullLiteralExpr() );
        }
        else if( o instanceof Expression ){
            return Objects.equals( exp, o );
        }   
        else if( o instanceof String ){
            try{
                Expression e = Expr.of( (String)o);
                return exp.equals(e);
            }catch(Exception e){
                if( exp instanceof StringLiteralExpr ){
                    return Objects.equals( exp, Expr.stringLiteral(o.toString()) );
                }
            }
        }
        //handle All Wrapper types
        else if( o instanceof Number ||  o instanceof Boolean ){ //Int Float, etc.
            return Objects.equals( Expr.of(o.toString()), exp );
        }
        else if(o instanceof Character ){
            return Objects.equals( Expr.charLiteral( (Character)o), exp );
        }
        //arrays?
        else if( o.getClass().isArray() ){
            if( o.getClass().getComponentType().isPrimitive() ){
                Class ct = o.getClass().getComponentType();
                if( ct == int.class ){
                    return Objects.equals(exp, Expr.of( (int[])o) );
                }
                if( ct == float.class ){
                    return Objects.equals(exp, Expr.of( (float[])o) );
                }
                if( ct == double.class ){
                    return Objects.equals(exp, Expr.of( (double[])o) );
                }
                if( ct == boolean.class ){
                    return Objects.equals(exp, Expr.of( (boolean[])o) );
                }
                if( ct == char.class ){
                    return Objects.equals(exp, Expr.of( (char[])o) );
                }
                throw new DraftException("Only simple primitive types supported");                
            } 
        }
        return false;
    }    
}
