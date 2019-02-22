package draft.java;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;
import draft.DraftException;
import draft.java._model.*;
import draft.Text;
import draft.java._parameter.*;
import draft.java._anno.*;
import draft.java._typeParameter._typeParameters;
import draft.java.macro._macro;
import draft.java.macro._remove;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Model of a Java method
 * (wraps a {@link MethodDeclaration} AST node
 *
 * @author Eric
 */
public final class _method
        implements _javadoc._hasJavadoc<_method>, _anno._hasAnnos<_method>,
        _namedType<_method>, _body._hasBody<_method>, _throws._hasThrows<_method>,
        _modifiers._hasModifiers<_method>, _parameter._hasParameters<_method>,
        _typeParameter._hasTypeParameters<_method>,_receiverParameter._hasReceiverParameter<_method>,
        _modifiers._hasStatic<_method>,_modifiers._hasNative<_method>, _modifiers._hasFinal<_method>,
        _modifiers._hasAbstract<_method>, _modifiers._hasSynchronized<_method>,
        _modifiers._hasStrictFp<_method>, _member<MethodDeclaration, _method> {

    public static _method of( String methodDecl ){
        return of( new String[]{methodDecl});
    }

    public static _method of( String... methodDecl ) {

        //check for shortcut method
        String m = Text.combine( methodDecl );
        String[] ml = m.split(" ");
        if( ml.length == 1 ){
            m = ml[0].trim();
            //"id"
            //"id<T>"
            //"id()"
            //"id();"
            //"id(){}"
            if( !m.endsWith(";") && !m.endsWith("}")){
                //its a shortcut method (only providing NAME, no return TYPE)
                if( !m.endsWith(")") ){
                    m = m + "()";
                }
                return new _method( Ast.method("public void "+ m + ";"));
            } else{
                return new _method( Ast.method("public void "+ m ));
            }
        }
        return new _method( Ast.method( methodDecl ) );
    }

    /**
     * Builds a _method from an anonymous Object BODY
     * <PRE>
     * _method _m = _method.of( new Object() {
     *    int x;
     *    public int getDiff(int x){
     *        return this.x - x;
     *    }
     * });
     *
     * //
     * assertEquals(_m,
     * _method.of("public int getDiff(int x){",
     *     "return this.x - x;",
     *     "}");
     * </PRE>
     * NOTE: the method should be the only method declared in the
     * BODY
     * -or-
     * the only method that does NOT contain the @_remove annotation
     *
     * @param anonymousObjectBody
     * @return
     */
    public static _method of( Object anonymousObjectBody ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject( ste );
        NodeList<BodyDeclaration<?>> bds = oce.getAnonymousClassBody().get();
        //removeIn all things that aren't METHODS or METHODS WITH @_remove
        bds.removeIf( b -> b.isAnnotationPresent(_remove.class) || (! (b instanceof MethodDeclaration)) );
        //there should be only (1) method left, if > 1 take the first method
        MethodDeclaration md = (MethodDeclaration)bds.get(0);
        return _macro.to(anonymousObjectBody.getClass(), of( md ));        
    }

    public static <T extends Object> _method of( String signature, Supplier<T> body){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = fromSignature( signature );
        return updateBody(_m, le);
    }

    public static _method of( String signature, Expr.Command body){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = fromSignature( signature );
        return updateBody(_m, le);
    }

    public static <T extends Object> _method of( String signature, Consumer<T> body){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = fromSignature( signature );
        return updateBody(_m, le);
    }

    /**
     * _method.of( "public static final void print", ()->{ System.out.println(1); });
     *
     * @return
     */
    public static <T extends Object, U extends Object> _method of( String signature, Function<T,U> parametersAndBody){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = fromSignature( signature );
        return updateBody(_m, le);
    }

    /**
     * _method.of( "public static final void print", ()->{ System.out.println(1); });
     *
     * @return
     */
    public static <T extends Object, U extends Object, V extends Object> _method of( String signature, BiFunction<T,U,V> parametersAndBody){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = fromSignature( signature );
        return updateBody(_m, le);
    }

    public static <A extends Object, B extends Object, C extends Object, D extends Object> _method of( String signature, Expr.TriFunction<A,B,C,D> parametersAndBody){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = fromSignature( signature );
        return updateBody(_m, le);
    }

    public static <A extends Object, B extends Object, C extends Object, D extends Object, E extends Object> _method of( String signature, Expr.QuadFunction<A,B,C,D,E> parametersAndBody){
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = fromSignature( signature );
        return updateBody(_m, le);
    }

    public static _method updateBody( _method _m, LambdaExpr le){
        //set the BODY of the method
        if( le.getBody().isBlockStmt()){
            _m.setBody(le.getBody().asBlockStmt());
        } else{
            _m.add(le.getBody());
        }
        return _m;
    }

    public static _method fromSignature( String signature ){
        String[] toks = signature.split(" ");
        if( toks.length == 1 ){
            //single token shortcut... make it "public void"
            signature = "public void "+signature;
        }
        if( !signature.contains("(")){
            signature = signature+"()";
        }
        signature = signature+"{}";
        return of(signature);
    }

    public static _method of( MethodDeclaration methodDecl ) {
        return new _method( methodDecl );
    }

    private final MethodDeclaration astMethod;

    public _method( MethodDeclaration md ) {
        this.astMethod = md;
    }

    @Override
    public MethodDeclaration ast() {
        return astMethod;
    }

    @Override
    public _method javadoc( String... javadoc ) {
        astMethod.setJavadocComment( Text.combine( javadoc ) );
        return this;
    }

    @Override
    public _method removeJavadoc() {
        this.astMethod.removeJavaDocComment();
        return this;
    }

    @Override
    public boolean isVarArg() {
        if( !this.astMethod.getParameters().isEmpty() ) {
            return astMethod.getParameter( astMethod.getParameters().size() - 1 ).isVarArgs();
        }
        return false;
    }

    public boolean is( String... methodDecl ) {
        try {
            return of( methodDecl ).equals( this );
        }
        catch( Exception e ) {
        }
        return false;
    }

    public boolean isMain(){
        return IS_MAIN.test(this);
    }

    public _method copy(){
        return new _method(this.astMethod.clone());
    }

    /** Predicate */
    public static final Predicate<_method> IS_MAIN = m->
            m.isPublic() && m.isStatic() && m.getName().equals("main") && m.isVoid()
            && m.getParameters().count() == 1 && m.getParameter( 0 ).isType(String[].class);

    @Override
    public boolean equals( Object obj ) {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        final _method other = (_method)obj;
        if( this.astMethod == other.astMethod ) {
            return true; //two _method s pointing to the same MethodDeclaration
        }
        //this.getAnnos(), this.getBody(), this.getJavadoc(), this.getModifiers(), this.getName(), this.getParameters(), this.getThrownExeptions(), this.getTypeParameters(), this.getType()
        if( !Ast.annotationsEqual( astMethod, other.astMethod)){
            return false;
        }
        //if( !Objects.equals( this.getAnnos(), other.getAnnos() ) ) {
            //System.out.println("annos");
        //    return false;
        //}
        if( !Objects.equals( this.getBody(), other.getBody() ) ) {
            //System.out.println("BODY");
            return false;
        }
        if( this.hasJavadoc() != other.hasJavadoc() ) {
            return false;
        }
        if( this.hasJavadoc() && !Objects.equals( this.getJavadoc().getContent().trim(), other.getJavadoc().getContent().trim() ) ) {
            //System.out.println("JAVADOC >" + this.getJavadoc().getContent() +"< >"+other.getJavadoc().getContent());
            return false;
        }
        if( !Ast.modifiersEqual(this.astMethod, other.astMethod)){
            return false;
        }
        //if( !Objects.equals( this.getModifiers(), other.getModifiers() ) ) {
            //System.out.println("MODIFIERS");
        //    return false;
        //}
        if( !Objects.equals( this.getName(), other.getName() ) ) {
            //System.out.println("NAME");
            return false;
        }
        if( !Objects.equals( this.getParameters(), other.getParameters() ) ) {
            //System.out.println("PARAMETERS");
            return false;
        }
        if( !Ast.typesEqual(astMethod.getThrownExceptions(), other.astMethod.getThrownExceptions())){
            return false;
        }
        //if( !Objects.equals( this.getThrows(), other.getThrows() ) ) {
            //System.out.println("thrownExpcet");
        //    return false;
        //}
        if( !Objects.equals( this.getTypeParameters(), other.getTypeParameters() ) ) {
            //System.out.println("TYPE params");
            return false;
        }
        if( !Ast.typesEqual( astMethod.getType(), other.astMethod.getType())){
            return false;
        }
        //if( !Objects.equals( this.getType(), other.getType() ) ) {
            //System.out.println("TYPE");
        //    return false;
        //}
        if( !Objects.equals( this.getReceiverParameter(), other.getReceiverParameter() ) ) {
            //System.out.println("TYPE");
            return false;
        }
        return true;
    }

    @Override
    public Map<_java.Component, Object> componentsMap() {
        Map<_java.Component, Object> parts = new HashMap<>();
        parts.put(_java.Component.ANNOS, getAnnos() );
        parts.put( _java.Component.BODY, getBody() );
        parts.put( _java.Component.TYPE, getType() );
        parts.put( _java.Component.PARAMETERS, getParameters() );
        parts.put( _java.Component.MODIFIERS, getModifiers() );
        parts.put( _java.Component.JAVADOC, getJavadoc() );
        parts.put( _java.Component.RECEIVER_PARAMETER, getReceiverParameter() );
        parts.put( _java.Component.TYPE_PARAMETERS, getTypeParameters() );
        parts.put( _java.Component.THROWS, getThrows() );
        parts.put( _java.Component.NAME, getName() );
        return parts;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 23 * hash + Objects.hash(
                Ast.annotationsHash(astMethod),
                this.getBody(),
                this.getJavadoc(),
                this.getEffectiveModifiers(), //this.getModifiers(),
                this.getName(),
                this.getParameters(),
                Ast.typesHashCode( astMethod.getThrownExceptions()), this.getTypeParameters(),
                this.getReceiverParameter(),
                Ast.typeHash(astMethod.getType()) );
        return hash;
    }

    @Override
    public boolean hasJavadoc() {
        return this.astMethod.getJavadocComment().isPresent();
    }

    @Override
    public _javadoc getJavadoc() {
        return _javadoc.of( this.astMethod );
    }

    @Override
    public _method type( Type type ) {
        this.astMethod.setType( type );
        return this;
    }

    @Override
    public _typeRef getType() {
        return _typeRef.of( this.astMethod.getType() );
    }

    @Override
    public _method name( String name ) {
        this.astMethod.setName( name );
        return this;
    }

    @Override
    public _throws getThrows() {
        return _throws.of( astMethod );
    }

    @Override
    public boolean isThrown( Class<? extends Throwable> clazz ) {
        return this.astMethod.isThrown( clazz )
                || this.astMethod.isThrown( clazz.getCanonicalName() );
    }

    @Override
    public boolean isThrown( String name ) {
        return this.astMethod.isThrown( name );
    }

    @Override
    public boolean isThrown( ReferenceType rt ) {
        return this.getThrows().contains( rt );
    }

    public _method typeParameters( String typeParameters ) {
        this.astMethod.setTypeParameters( Ast.typeParameters( typeParameters ) );
        return this;
    }

    @Override
    public _typeParameters getTypeParameters() {
        return _typeParameters.of( this.astMethod );
    }

    @Override
    public boolean hasTypeParameters() {
        return this.astMethod.getTypeParameters().isNonEmpty();
    }

    @Override
    public boolean hasParameters() {
        return this.astMethod.getParameters().isNonEmpty();
    }

    /*
    NOTE: We're removing this because it's giving the wrong results for
    generic parameters i.e. List<String>... use hashParametersOfType(Type....)
    instead

    @Override
    public boolean hasParametersOfType(Class<?>... paramTypes) {
        return this.ast().hasParametersOfType(paramTypes);
    }
    */

    
    
    public boolean hasParametersOf(java.lang.reflect.Method m){
        java.lang.reflect.Type[] genericParameterTypes = m.getGenericParameterTypes();
        List<_parameter> pl = this.listParameters();
        if (genericParameterTypes.length != pl.size()) {
            return false;
        }
        for (int i = 0; i < genericParameterTypes.length; i++) {
            System.out.println( "PARAM "+ genericParameterTypes[i]);
            _typeRef _t = _typeRef.of(genericParameterTypes[i]);
            if (!pl.get(i).isType(_t)) {
                if (m.isVarArgs()
                        && //if last parameter and varargs
                        Ast.typesEqual(pl.get(i).getType().getElementType(),
                                _t.getElementType())) {

                } else {
                    System.out.println("Failed at " + _t + " =/= " + pl.get(i).getType());
                    return false;
                }
            }
        }
        return true;
    }
    
    /** TODO REMOVE THIS IT DOESNT WORK (ESPECIALLY FOR VARARGS
     * 
     * @param genericParameterTypes
     * @return 
     */
    public boolean hasParametersOfType(java.lang.reflect.Type...genericParameterTypes){
        //receiver parameters?
        if( genericParameterTypes.length != this.listParameters().size() ){
            return false;
        }
        List<_parameter> pl = this.listParameters();
        for(int i=0;i<genericParameterTypes.length; i++){

            _typeRef _t = _typeRef.of( genericParameterTypes[i].getTypeName() );
            if( !pl.get(i).isType( _t ) ){
                //System.out.println( "Failed at "+ _t+" =/= "+ pl.get(i).getType() );
                //if the last one is a varargs
                
                return false;
            }
        }
        return true;
        /*
        List<String>paramTypes = new ArrayList<String>();
        Arrays.stream(genericParameterTypes).forEach(t -> paramTypes.add(t.toString()));
        System.out.println( paramTypes );
        System.out.println( this.getParameters() );

        return this.ast().hasParametersOfType(paramTypes.toArray(new String[0]));
        */
    }

    @Override
    public _method addThrows( String... throwExceptions ) {
        Arrays.stream( throwExceptions ).forEach( t -> addThrows( t ) );
        return this;
    }

    @Override
    public _method addThrows( String throwException ) {
        this.astMethod.addThrownException( (ReferenceType)Ast.typeRef( throwException ) );
        return this;
    }

    @Override
    public _method addThrows( Class<? extends Throwable>... throwExceptions ) {
        Arrays.stream( throwExceptions ).forEach( t -> addThrows( t ) );
        return this;
    }

    @Override
    public _method addThrows( Class<? extends Throwable> throwException ) {
        this.astMethod.addThrownException( (ReferenceType)Ast.typeRef( throwException ) );
        return this;
    }

    @Override
    public _method addParameters( Parameter... parameters ) {
        Arrays.stream( parameters ).forEach( p -> addParameter( p ) );
        return this;
    }

    @Override
    public _method addParameter( Parameter parameter ) {
        this.astMethod.addParameter( parameter );
        return this;
    }

    @Override
    public _body getBody() {
        return _body.of( this.astMethod );
    }

    @Override
    public boolean hasThrows() {
        return this.astMethod.getThrownExceptions().isNonEmpty();
    }

    @Override
    public _modifiers getModifiers() {

        return _modifiers.of( this.astMethod );
    }



    @Override
    public NodeList<Modifier> getEffectiveModifiers(){
        NodeList<Modifier> ims = Ast.getImpliedModifiers( this.astMethod );
        if( ims == null ){
            return this.astMethod.getModifiers();
        }
        ims.addAll(this.astMethod.getModifiers());
        return ims;
    }

    @Override
    public _annos getAnnos() {
        return _annos.of( astMethod );
    }

    @Override
    public String getName() {
        return astMethod.getNameAsString();
    }

    @Override
    public _parameters getParameters() {
        return _parameters.of( astMethod );
    }

    public _parameter getParameter( String parameterName ){
        Optional<Parameter> op = this.astMethod.getParameterByName(parameterName);
        if( op.isPresent() ){
            return _parameter.of(op.get());
        }
        return null;
    }

    public _parameter getParameter( Class type ){
        Optional<Parameter> op = this.astMethod.getParameterByType(type);
        if( op.isPresent() ){
            return _parameter.of(op.get());
        }
        return null;
    }

    public _parameter getParameter( _typeRef _type ){
        Optional<Parameter> op = this.astMethod.getParameterByType(_type.toString());
        if( op.isPresent() ){
            return _parameter.of(op.get());
        }
        return null;
    }

    @Override
    public _parameter getParameter( int index ) {
        return _parameter.of( this.astMethod.getParameter( index ) );
    }

    public boolean is( MethodDeclaration methodDeclaration ){
        try{
            return of(methodDeclaration).equals(this);
        }catch(Exception e){
            return false;
        }
    }

    @Override
    public String toString() {
        return this.astMethod.toString();
    }

    public boolean isPublic() {
        return this.astMethod.isPublic();
    }

    public boolean isProtected() {
        return this.astMethod.isProtected();
    }

    public boolean isPrivate() {
        return this.astMethod.isPrivate();
    }

    public boolean isDefault() {
        return this.astMethod.isDefault();
    }

    @Override
    public boolean isStatic() {
        return this.astMethod.isStatic();
    }

    @Override
    public boolean isAbstract() {
        /**
         * Cant just look at the MODIFIERS, I also have to check if this method
         * lacks a BODY
         */
        if( !this.astMethod.getBody().isPresent() ){
            return true;
            //I dont think I need to check if it's on an interface,
            //
        }
        return this.astMethod.isAbstract();
    }

    @Override
    public boolean isNative() {
        return this.astMethod.isNative();
    }

    @Override
    public boolean isSynchronized() {
        return this.astMethod.isSynchronized();
    }

    @Override
    public boolean isStrictFp() {
        return this.astMethod.isStrictfp();
    }

    @Override
    public boolean isFinal() {
        return this.astMethod.isFinal();
    }

    public _method setPublic() {
        this.astMethod.setPrivate(false);
        this.astMethod.setProtected(false);
        this.astMethod.setPublic(true);
        return this;
    }

    public _method setProtected() {
        this.astMethod.setPrivate(false);
        this.astMethod.setProtected(true);
        this.astMethod.setPublic(false);
        return this;
    }

    public _method setPrivate() {
        this.astMethod.setPrivate(true);
        this.astMethod.setProtected(false);
        this.astMethod.setPublic(false);
        return this;
    }

    public _method setDefaultAccess() {
        this.astMethod.setPrivate(false);
        this.astMethod.setProtected(false);
        this.astMethod.setPublic(false);
        return this;
    }

    @Override
    public _method setStatic() {
        return setStatic( true );
    }

    @Override
    public _method setAbstract() {
        this.astMethod.removeBody();
        return setAbstract( true );
    }

    @Override
    public _method setSynchronized() {
        return setSynchronized( true );
    }

    @Override
    public _method setFinal() {
        return setFinal( true );
    }

    @Override
    public _method setNative() {
        return setNative( true );
    }

    @Override
    public _method setStrictFp() {
        return setStrictFp( true );
    }

    @Override
    public _method setNative( boolean toSet ) {
        this.astMethod.setNative(toSet);
        return this;
    }

    @Override
    public _method setStatic( boolean toSet ) {
        this.astMethod.setStatic(toSet);
        return this;
    }

    @Override
    public _method setAbstract( boolean toSet ) {
        this.astMethod.setAbstract(toSet);
        if( toSet ) {
            this.astMethod.removeBody();
        }
        else {
            this.astMethod.createBody();
        }
        return this;
    }

    @Override
    public _method setSynchronized( boolean toSet ) {
        this.astMethod.setSynchronized(toSet);
        return this;
    }

    @Override
    public _method setStrictFp( boolean toSet ) {
        this.astMethod.setStrictfp(toSet);
        return this;
    }

    @Override
    public _method setFinal( boolean toSet ) {
        this.astMethod.setFinal(toSet);
        return this;
    }

    @Override
    public _method typeParameters( NodeList<TypeParameter> typeParams ) {
        this.astMethod.setTypeParameters( typeParams );
        return this;
    }

    @Override
    public _method typeParameters( String... typeParameters ) {
        this.astMethod.setTypeParameters( Ast.typeParameters( Text.combine( typeParameters ) ) );
        return this;
    }

    @Override
    public _method typeParameters( _typeParameters _tps ) {
        this.astMethod.setTypeParameters( _tps.ast() );
        return this;
    }

    @Override
    public boolean hasReceiverParameter() {
        return this.astMethod.getReceiverParameter().isPresent();
    }

    @Override
    public _receiverParameter getReceiverParameter() {
        if( this.astMethod.getReceiverParameter().isPresent() ) {
            return _receiverParameter.of( this.astMethod.getReceiverParameter().get() );
        }
        return null;
    }

    @Override
    public _method removeReceiverParameter() {
        this.astMethod.removeReceiverParameter();
        return this;
    }

    @Override
    public _method setReceiverParameter( String receiverParameter ) {
        return setReceiverParameter( Ast.receiverParameter( receiverParameter ) );
    }

    @Override
    public _method setReceiverParameter( _receiverParameter _rp ) {
        return setReceiverParameter( _rp.ast() );
    }

    @Override
    public _method setReceiverParameter( ReceiverParameter rp ) {
        this.astMethod.setReceiverParameter( rp );
        return this;
    }

    @Override
    public _method removeTypeParameters() {
        this.astMethod.getTypeParameters().clear();
        return this;
    }

    @Override
    public _method setBody( BlockStmt body ) {
        if( body == null ) {
            this.astMethod.removeBody();
        }
        else {
            this.astMethod.setBody( body );
        }
        return this;
    }

    /**
     * _method.of( "public static final void print", ()->{ System.out.println(1); });
     *
     * @return
     */
    public <T extends Object, U extends Object> _method setBody( Function<T,U> parametersAndBody){
        return setBody( Stmt.block(Thread.currentThread().getStackTrace()[2]));
    }

    /**
     * _method.of( "public static final void print", ()->{ System.out.println(1); });
     *
     * @return
     */
    public <T extends Object, U extends Object, V extends Object> _method setBody( BiFunction<T,U,V> parametersAndBody){
        return setBody( Stmt.block(Thread.currentThread().getStackTrace()[2]));
    }

    public <A extends Object, B extends Object, C extends Object, D extends Object> _method setBody( Expr.TriFunction<A,B,C,D> parametersAndBody){
        return setBody( Stmt.block(Thread.currentThread().getStackTrace()[2]));
    }

    public <A extends Object, B extends Object, C extends Object, D extends Object, E extends Object> _method setBody( Expr.QuadFunction<A,B,C,D,E> parametersAndBody){
        return setBody( Stmt.block(Thread.currentThread().getStackTrace()[2]));
    }

    @Override
    public _method clearBody() {
        if( this.astMethod.getBody().isPresent() ) {
            this.astMethod.getBody().get().remove();
        }
        return this;
    }

    @Override
    public _method add( Statement... statements ) {
        if( !this.astMethod.getBody().isPresent() ) {
            this.astMethod.createBody();
        }
        for( Statement statement : statements ) {
            this.astMethod.getBody().get().addStatement( statement );
        }
        return this;
    }

    @Override
    public _method add( int startStatementIndex, Statement...statements ){
        if( !this.astMethod.getBody().isPresent() ) {
            this.astMethod.createBody();
        }
        for( int i=0;i<statements.length;i++) {
            this.astMethod.getBody().get().addStatement( i+ startStatementIndex, statements[i] );
        }
        return this;
    }

    public static final _java.Semantic<Collection<_method>> EQIVALENT_METHODS = (o1, o2)->{
         if( o1 == null){
                return o2 == null;
            }
            if( o2 == null ){
                return false;
            }
            if( o1.size() != o2.size()){
                return false;
            }
            Set<_method> tm = new HashSet<>();
            Set<_method> om = new HashSet<>();
            tm.addAll(o1);
            om.addAll(o2);
            return Objects.equals(tm, om);        
    };
    
    /** 
     * Are these (2) collections of methods equivalent ?
     * @param left
     * @param right
     * @return true if these collections are semantically equivalent
     */
    public static boolean equivalent( Collection<_method> left, Collection<_method> right ){
        return EQIVALENT_METHODS.equivalent(left, right);
    }
    
    /**
     *
     * @author Eric
     * @param <T>
     */
    public interface _hasMethods<T extends _hasMethods>
            extends _model {

        List<_method> listMethods();

        default boolean hasMethods() {
            return !listMethods().isEmpty();
        }

        default _method getMethod( String name ) {
            List<_method> lm = listMethods( name );
            if( lm.isEmpty() ) {
                return null;
            }
            return lm.get( 0 );
        }

        default List<_method> listMethods( String name ) {
            return listMethods().stream().filter( m -> m.getName().equals( name ) ).collect( Collectors.toList() );
        }

        default List<_method> listMethods( Predicate<_method> _methodMatchFn ) {
            return listMethods().stream().filter( _methodMatchFn ).collect( Collectors.toList() );
        }

        default T forMethods( Consumer<_method> methodConsumer ) {
            return forMethods( m -> true, methodConsumer );
        }

        default T forMethods( Predicate<_method> methodMatchFn,
                              Consumer<_method> methodConsumer ) {
            listMethods( methodMatchFn ).forEach( methodConsumer );
            return (T)this;
        }

        default T removeMethod( _method _m ){
            if( listMethods().contains(_m)){
                _m.ast().removeForced();
            }
            return (T)this;
        }

        default T removeMethod( MethodDeclaration m ) {
            if( listMethods().stream().filter((_m -> _m.ast().equals(m))).findFirst().isPresent()){
                m.removeForced();
            }
            return (T)this;
        }

        default T removeMethods ( Predicate<_method> methodPredicate ){
            listMethods(methodPredicate).forEach( _m -> removeMethod(_m));
            return (T) this;
        }

        T method( MethodDeclaration method );

        default T method( String... method ) {
            return method( Ast.method( method ) );
        }

        default T method( _method _m ) {
            return method( _m.ast() );
        }

        default T method(String signature, Expr.Command parametersAndBody){
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = fromSignature( signature );
            _m = updateBody(_m, le);
            return method( _m);
        }

        default T methods(_method...ms){
            Arrays.stream(ms).forEach( m -> method(m));
            return (T)this;
        }

        /**
         * Pass in the BODY of a main method and _1_build/add a
         *
         * public static void main(String[] args) {...} method
         *
         * @param mainMethodBody the BODY of the main method
         * @return the modified T
         */
        default T main( String...mainMethodBody ){
            _method _m = _method.of("public static void main(String[] args){ }");
            _m.add(mainMethodBody);
            return method( _m );
        }

        /**
         * Build a
         * public static void main(String[] args) {...} method
         * with the contents of the lambda
         *
         * @param body
         * @return
         */
        default T main( Expr.Command body ){
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = _method.of("public static void main(String[] args){ }");
            if( le.getBody().isBlockStmt() ) {
                _m.setBody(le.getBody().asBlockStmt());
            }else{
                _m.add(le.getBody());
            }
            //TODO? should I removeIn / replaceIn the main method if one is already found??
            return method( _m );
        }

        /**
         * Build a
         * public static void main(String[] args) {...} method
         * with the contents of the lambda
         *
         * @param body
         * @return
         */
        default T main( Consumer<String[]> body ){
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = _method.of("public static void main(String[] args){ }");
            if( le.getBody().isBlockStmt() ) {
                _m.setBody(le.getBody().asBlockStmt());
            }else{
                _m.add(le.getBody());
            }
            //TODO? should I removeIn / replaceIn the main method if one is already found??
            return method( _m );
        }

        default T method( String methodDef ){
            return method(new String[]{methodDef});
        }

        default T method( Object anonymousObjectContainingMethod ){
            StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
            ObjectCreationExpr oce = Expr.anonymousObject( ste );
            if( oce == null || !oce.getAnonymousClassBody().isPresent() ){
                throw new DraftException("No anonymous Object containing a method provided ");
            }
            Optional<BodyDeclaration<?>> obd = oce.getAnonymousClassBody().get().stream()
                    .filter(bd -> bd instanceof MethodDeclaration &&
                            !bd.asMethodDeclaration().getAnnotationByClass(_remove.class).isPresent()).findFirst();
            if( ! obd.isPresent() ){
                throw new DraftException("Could not find Method in anonymous object body");
            }
            MethodDeclaration md = (MethodDeclaration)obd.get();
            return method( md );
        }
        /**
         * Builds a method with the signature and
         *
         * @return
         */
        default <A extends Object> T method(String signature, Consumer<A> parametersAndBody){
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = fromSignature( signature );
            _m = updateBody(_m, le);
            return method(_m);
        }

        /**
         * _method.of( "public static final void print", ()->{ System.out.println(1); });
         *
         * @return
         */
        default <A extends Object, B extends Object> T method(String signature, BiConsumer<A,B> parametersAndBody){
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = fromSignature( signature );
            _m = updateBody(_m, le);
            return method(_m);
        }

        /**
         *
         * _method.of( "public static final void print", ()->{ System.out.println(1); });
         *
         * @return
         */
        default <A extends Object, B extends Object> T method( String signature, Function<A,B> parametersAndBody){
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = fromSignature( signature );
            return method(updateBody(_m, le));
        }

        /**
         * ( "public static final void print", ()->{ System.out.println(1); });
         *
         * @return
         */
        default <A extends Object, B extends Object, C extends Object> T method( String signature, BiFunction<A,B,C> parametersAndBody){
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = fromSignature( signature );
            return method(updateBody(_m, le));
        }

        default <A extends Object, B extends Object, C extends Object, D extends Object> T method( String signature, Expr.TriFunction<A,B,C,D> parametersAndBody){
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = fromSignature( signature );
            return method(updateBody(_m, le));
        }

        default <A extends Object, B extends Object, C extends Object, D extends Object, E extends Object> T method( String signature, Expr.QuadFunction<A,B,C,D,E> parametersAndBody){
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = fromSignature( signature );
            return method(updateBody(_m, le));
        }
    }
}
