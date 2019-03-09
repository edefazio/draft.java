package draft.java;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.ast.type.*;
import draft.DraftException;
import draft.java._model.*;
import draft.Text;
import draft.java._parameter.*;
import draft.java._anno.*;
import draft.java._java._path;
import draft.java._inspect._diff;
import draft.java._typeParameter._typeParameters;
import draft.java.macro._macro;
import draft.java.macro._remove;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Model of a Java method (wraps a {@link MethodDeclaration} AST node
 *
 * @author Eric
 */
public final class _method
        implements _javadoc._hasJavadoc<_method>, _anno._hasAnnos<_method>,
        _namedType<_method>, _body._hasBody<_method>, _throws._hasThrows<_method>,
        _modifiers._hasModifiers<_method>, _parameter._hasParameters<_method>,
        _typeParameter._hasTypeParameters<_method>, _receiverParameter._hasReceiverParameter<_method>,
        _modifiers._hasStatic<_method>, _modifiers._hasNative<_method>, _modifiers._hasFinal<_method>,
        _modifiers._hasAbstract<_method>, _modifiers._hasSynchronized<_method>,
        _modifiers._hasStrictFp<_method>, _member<MethodDeclaration, _method> {

    public static _method of(String methodDecl) {
        return of(new String[]{methodDecl});
    }

    public static _method of(String... methodDecl) {

        //check for shortcut method
        String m = Text.combine(methodDecl);
        String[] ml = m.split(" ");
        if (ml.length == 1) {
            m = ml[0].trim();
            //"id"
            //"id<T>"
            //"id()"
            //"id();"
            //"id(){}"
            if (!m.endsWith(";") && !m.endsWith("}")) {
                //its a shortcut method (only providing NAME, no return TYPE)
                if (!m.endsWith(")")) {
                    m = m + "()";
                }
                return new _method(Ast.method("public void " + m + ";"));
            } else {
                return new _method(Ast.method("public void " + m));
            }
        }
        return new _method(Ast.method(methodDecl));
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
     * </PRE> NOTE: the method should be the only method declared in the BODY
     * -or- the only method that does NOT contain the @_remove annotation
     *
     * @param anonymousObjectBody
     * @return
     */
    public static _method of(Object anonymousObjectBody) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject(ste);
        NodeList<BodyDeclaration<?>> bds = oce.getAnonymousClassBody().get();
        //removeIn all things that aren't METHODS or METHODS WITH @_remove
        bds.removeIf(b -> b.isAnnotationPresent(_remove.class) || (!(b instanceof MethodDeclaration)));
        //there should be only (1) method left, if > 1 take the first method
        MethodDeclaration md = (MethodDeclaration) bds.get(0);
        return _macro.to(anonymousObjectBody.getClass(), of(md));
    }

    public static <T extends Object> _method of(String signature, Supplier<T> body) {
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = fromSignature(signature);
        return updateBody(_m, le);
    }

    public static _method of(String signature, Expr.Command body) {
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = fromSignature(signature);
        return updateBody(_m, le);
    }

    public static <T extends Object> _method of(String signature, Consumer<T> body) {
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = fromSignature(signature);
        return updateBody(_m, le);
    }

    /**
     * _method.of( "public static final void print", ()->{
     * System.out.println(1); });
     *
     * @return
     */
    public static <T extends Object, U extends Object> _method of(String signature, Function<T, U> parametersAndBody) {
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = fromSignature(signature);
        return updateBody(_m, le);
    }

    /**
     * _method.of( "public static final void print", ()->{
     * System.out.println(1); });
     *
     * @return
     */
    public static <T extends Object, U extends Object, V extends Object> _method of(String signature, BiFunction<T, U, V> parametersAndBody) {
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = fromSignature(signature);
        return updateBody(_m, le);
    }

    public static <A extends Object, B extends Object, C extends Object, D extends Object> _method of(String signature, Expr.TriFunction<A, B, C, D> parametersAndBody) {
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = fromSignature(signature);
        return updateBody(_m, le);
    }

    public static <A extends Object, B extends Object, C extends Object, D extends Object, E extends Object> _method of(String signature, Expr.QuadFunction<A, B, C, D, E> parametersAndBody) {
        LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
        _method _m = fromSignature(signature);
        return updateBody(_m, le);
    }

    public static _method updateBody(_method _m, LambdaExpr le) {
        //set the BODY of the method
        if (le.getBody().isBlockStmt()) {
            _m.setBody(le.getBody().asBlockStmt());
        } else {
            _m.add(le.getBody());
        }
        return _m;
    }

    public static _method fromSignature(String signature) {
        String[] toks = signature.split(" ");
        if (toks.length == 1) {
            //single token shortcut... make it "public void"
            signature = "public void " + signature;
        }
        if (!signature.contains("(")) {
            signature = signature + "()";
        }
        signature = signature + "{}";
        return of(signature);
    }

    public static _method of(MethodDeclaration methodDecl) {
        return new _method(methodDecl);
    }

    private final MethodDeclaration astMethod;

    public _method(MethodDeclaration md) {
        this.astMethod = md;
    }

    @Override
    public MethodDeclaration ast() {
        return astMethod;
    }

    @Override
    public _method javadoc(String... javadoc) {
        astMethod.setJavadocComment(Text.combine(javadoc));
        return this;
    }

    @Override
    public _method javadoc(JavadocComment astJavadocComment) {
        this.astMethod.setJavadocComment(astJavadocComment);
        return this;
    }

    @Override
    public _method removeJavadoc() {
        this.astMethod.removeJavaDocComment();
        return this;
    }

    @Override
    public boolean isVarArg() {
        if (!this.astMethod.getParameters().isEmpty()) {
            return astMethod.getParameter(astMethod.getParameters().size() - 1).isVarArgs();
        }
        return false;
    }

    public boolean is(String... methodDecl) {
        try {
            _method _mm = of(methodDecl);

            //because we DONT know the context of the method (on interface, etc.)
            // lets add all of the implied modifiers to the _mm temp model
            NodeList<Modifier> mms = Ast.getImpliedModifiers(this.astMethod);
            mms.forEach(mmm -> {
                _mm.ast().addModifier(mmm.getKeyword());
            });
            return _mm.equals(this);
        } catch (Exception e) {
        }
        return false;
    }

    public boolean isMain() {
        return IS_MAIN.test(this);
    }

    public _method copy() {
        return new _method(this.astMethod.clone());
    }

    /**
     * Predicate
     */
    public static final Predicate<_method> IS_MAIN = m
            -> m.isPublic() && m.isStatic() && m.getName().equals("main") && m.isVoid()
            && m.getParameters().count() == 1 && m.getParameter(0).isType(String[].class);

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final _method other = (_method) obj;
        if (this.astMethod == other.astMethod) {
            return true; //two _method s pointing to the same MethodDeclaration
        }        
        if (!Ast.annotationsEqual(astMethod, other.astMethod)) {
            return false;
        }
        if (!Objects.equals(this.getBody(), other.getBody())) {
            return false;
        }
        if (this.hasJavadoc() != other.hasJavadoc()) {
            return false;
        }
        if (this.hasJavadoc() && !Objects.equals(this.getJavadoc().getContent().trim(), other.getJavadoc().getContent().trim())) {
            return false;
        }
        if (!Ast.modifiersEqual(this.astMethod, other.astMethod)) {
            return false;
        }
        if (!Objects.equals(this.getName(), other.getName())) {
            return false;
        }
        if (!Objects.equals(this.getParameters(), other.getParameters())) {
            return false;
        }
        if (!Ast.typesEqual(astMethod.getThrownExceptions(), other.astMethod.getThrownExceptions())) {
            return false;
        }        
        if (!Objects.equals(this.getTypeParameters(), other.getTypeParameters())) {
            return false;
        }
        if (!Ast.typesEqual(astMethod.getType(), other.astMethod.getType())) {
            return false;
        }
        if (!Objects.equals(this.getReceiverParameter(), other.getReceiverParameter())) {
            return false;
        }
        return true;
    }

    @Override
    public Map<_java.Component, Object> componentsMap() {
        Map<_java.Component, Object> parts = new HashMap<>();
        parts.put(_java.Component.ANNOS, getAnnos());
        parts.put(_java.Component.BODY, getBody());
        parts.put(_java.Component.TYPE, getType());
        parts.put(_java.Component.PARAMETERS, getParameters());
        parts.put(_java.Component.MODIFIERS, getEffectiveModifiers());
        parts.put(_java.Component.JAVADOC, getJavadoc());
        parts.put(_java.Component.RECEIVER_PARAMETER, getReceiverParameter());
        parts.put(_java.Component.TYPE_PARAMETERS, getTypeParameters());
        parts.put(_java.Component.THROWS, getThrows());
        parts.put(_java.Component.NAME, getName());
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
                Ast.typesHashCode(astMethod.getThrownExceptions()),
                this.getTypeParameters(),
                this.getReceiverParameter(),
                Ast.typeHash(astMethod.getType()));
        return hash;
    }

    @Override
    public boolean hasJavadoc() {
        return this.astMethod.getJavadocComment().isPresent();
    }

    @Override
    public _javadoc getJavadoc() {
        return _javadoc.of(this.astMethod);
    }

    @Override
    public _method type(Type type) {
        this.astMethod.setType(type);
        return this;
    }

    @Override
    public _typeRef getType() {
        return _typeRef.of(this.astMethod.getType());
    }

    @Override
    public _method name(String name) {
        this.astMethod.setName(name);
        return this;
    }

    @Override
    public _throws getThrows() {
        return _throws.of(astMethod);
    }

    @Override
    public NodeList<TypeParameter> listAstTypeParameters() {
        return this.astMethod.getTypeParameters();
    }

    @Override
    public _method typeParameters(String typeParameters) {
        this.astMethod.setTypeParameters(Ast.typeParameters(typeParameters));
        return this;
    }

    @Override
    public _typeParameters getTypeParameters() {
        return _typeParameters.of(this.astMethod);
    }

    @Override
    public boolean hasTypeParameters() {
        return this.astMethod.getTypeParameters().isNonEmpty();
    }

    @Override
    public boolean hasParameters() {
        return this.astMethod.getParameters().isNonEmpty();
    }

    public _diff diff(_method right) {
        return INSPECT_METHOD.diff(this, right);
    }

    public static _diff diff(_method left, _method right) {
        return INSPECT_METHOD.diff(left, right);
    }

    public static _diff diff(List<_method> left, List<_method> right) {
        return INSPECT_METHODS.diff(left, right);
    }

    /**
     * Match the reflective java.lang.reflect.Method to this _method's
     * parameters
     *
     * @param m the method
     * @return
     */
    public boolean hasParametersOf(java.lang.reflect.Method m) {
        java.lang.reflect.Type[] genericParameterTypes = m.getGenericParameterTypes();
        List<_parameter> pl = this.listParameters();
        if (genericParameterTypes.length != pl.size()) {
            return false;
        }
        for (int i = 0; i < genericParameterTypes.length; i++) {
            //System.out.println( "PARAM "+ genericParameterTypes[i]);
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
    
    @Override
    public _method addParameters(Parameter... parameters) {
        Arrays.stream(parameters).forEach(p -> addParameter(p));
        return this;
    }

    @Override
    public _method addParameter(Parameter parameter) {
        this.astMethod.addParameter(parameter);
        return this;
    }

    @Override
    public _body getBody() {
        return _body.of(this.astMethod);
    }

    @Override
    public boolean hasThrows() {
        return this.astMethod.getThrownExceptions().isNonEmpty();
    }

    @Override
    public _modifiers getModifiers() {
        return _modifiers.of(this.astMethod);
    }

    @Override
    public NodeList<Modifier> getEffectiveModifiers() {
        NodeList<Modifier> ims = Ast.getImpliedModifiers(this.astMethod);

        if (ims == null) {
            return this.astMethod.getModifiers();
        }
        NodeList<Modifier> mms = this.astMethod.getModifiers();
        mms.forEach(m -> {
            if (!ims.contains(m)) {
                ims.add(m);
            }
        });
        //ims.addAll(this.astMethod.getModifiers());
        return ims;
    }

    @Override
    public _annos getAnnos() {
        return _annos.of(astMethod);
    }

    @Override
    public String getName() {
        return astMethod.getNameAsString();
    }

    @Override
    public _method setParameters(NodeList<Parameter> astPs){
        this.astMethod.setParameters(astPs);
        return this;        
    }
    
    @Override
    public _parameters getParameters() {
        return _parameters.of(astMethod);
    }

    public _parameter getParameter(String parameterName) {
        Optional<Parameter> op = this.astMethod.getParameterByName(parameterName);
        if (op.isPresent()) {
            return _parameter.of(op.get());
        }
        return null;
    }

    public _parameter getParameter(Class type) {
        Optional<Parameter> op = this.astMethod.getParameterByType(type);
        if (op.isPresent()) {
            return _parameter.of(op.get());
        }
        return null;
    }

    public _parameter getParameter(_typeRef _type) {
        Optional<Parameter> op = this.astMethod.getParameterByType(_type.toString());
        if (op.isPresent()) {
            return _parameter.of(op.get());
        }
        return null;
    }

    @Override
    public _parameter getParameter(int index) {
        return _parameter.of(this.astMethod.getParameter(index));
    }

    public boolean is(MethodDeclaration methodDeclaration) {
        try {
            return of(methodDeclaration).equals(this);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public String toString() {
        return this.astMethod.toString();
    }

    public boolean isPublic() {
        return this.astMethod.isPublic() || getEffectiveModifiers().contains(Modifier.publicModifier());
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
        if (!this.astMethod.getBody().isPresent()) {
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
        return setStatic(true);
    }

    @Override
    public _method setAbstract() {
        this.astMethod.removeBody();
        return setAbstract(true);
    }

    @Override
    public _method setSynchronized() {
        return setSynchronized(true);
    }

    @Override
    public _method setFinal() {
        return setFinal(true);
    }

    @Override
    public _method setNative() {
        return setNative(true);
    }

    @Override
    public _method setStrictFp() {
        return setStrictFp(true);
    }

    @Override
    public _method setNative(boolean toSet) {
        this.astMethod.setNative(toSet);
        return this;
    }

    @Override
    public _method setStatic(boolean toSet) {
        this.astMethod.setStatic(toSet);
        return this;
    }

    @Override
    public _method setAbstract(boolean toSet) {
        this.astMethod.setAbstract(toSet);
        if (toSet) {
            this.astMethod.removeBody();
        } else {
            this.astMethod.createBody();
        }
        return this;
    }

    @Override
    public _method setSynchronized(boolean toSet) {
        this.astMethod.setSynchronized(toSet);
        return this;
    }

    @Override
    public _method setStrictFp(boolean toSet) {
        this.astMethod.setStrictfp(toSet);
        return this;
    }

    @Override
    public _method setFinal(boolean toSet) {
        this.astMethod.setFinal(toSet);
        return this;
    }

    @Override
    public _method typeParameters(NodeList<TypeParameter> typeParams) {
        this.astMethod.setTypeParameters(typeParams);
        return this;
    }

    @Override
    public _method typeParameters(String... typeParameters) {
        this.astMethod.setTypeParameters(Ast.typeParameters(Text.combine(typeParameters)));
        return this;
    }

    @Override
    public _method typeParameters(_typeParameters _tps) {
        this.astMethod.setTypeParameters(_tps.ast());
        return this;
    }

    @Override
    public boolean hasReceiverParameter() {
        return this.astMethod.getReceiverParameter().isPresent();
    }

    @Override
    public _receiverParameter getReceiverParameter() {
        if (this.astMethod.getReceiverParameter().isPresent()) {
            return _receiverParameter.of(this.astMethod.getReceiverParameter().get());
        }
        return null;
    }

    @Override
    public _method removeReceiverParameter() {
        this.astMethod.removeReceiverParameter();
        return this;
    }

    @Override
    public _method receiverParameter(String receiverParameter) {
        return receiverParameter(Ast.receiverParameter(receiverParameter));
    }

    @Override
    public _method receiverParameter(_receiverParameter _rp) {
        return receiverParameter(_rp.ast());
    }

    @Override
    public _method receiverParameter(ReceiverParameter rp) {
        this.astMethod.setReceiverParameter(rp);
        return this;
    }

    @Override
    public _method removeTypeParameters() {
        this.astMethod.getTypeParameters().clear();
        return this;
    }

    @Override
    public _method setBody(BlockStmt body) {
        if (body == null) {
            this.astMethod.removeBody();
        } else {
            this.astMethod.setBody(body);
        }
        return this;
    }

    /**
     * _method.of( "public static final void print", ()->{
     * System.out.println(1); });
     *
     * @param <T>
     * @param <U>
     * @param <V>
     * @param parametersAndBody
     * @return
     */
    public <T extends Object, U extends Object, V extends Object> _method setBody(BiFunction<T, U, V> parametersAndBody) {
        return setBody(Stmt.block(Thread.currentThread().getStackTrace()[2]));
    }

    public <A extends Object, B extends Object, C extends Object, D extends Object> _method setBody(Expr.TriFunction<A, B, C, D> parametersAndBody) {
        return setBody(Stmt.block(Thread.currentThread().getStackTrace()[2]));
    }

    public <A extends Object, B extends Object, C extends Object, D extends Object, E extends Object> _method setBody(Expr.QuadFunction<A, B, C, D, E> parametersAndBody) {
        return setBody(Stmt.block(Thread.currentThread().getStackTrace()[2]));
    }

    @Override
    public _method clearBody() {
        if (this.astMethod.getBody().isPresent()) {
            this.astMethod.getBody().get().remove();
        }
        return this;
    }

    @Override
    public _method add(Statement... statements) {
        if (!this.astMethod.getBody().isPresent()) {
            this.astMethod.createBody();
        }
        for (Statement statement : statements) {
            this.astMethod.getBody().get().addStatement(statement);
        }
        return this;
    }

    @Override
    public _method add(int startStatementIndex, Statement... statements) {
        if (!this.astMethod.getBody().isPresent()) {
            this.astMethod.createBody();
        }
        for (int i = 0; i < statements.length; i++) {
            this.astMethod.getBody().get().addStatement(i + startStatementIndex, statements[i]);
        }
        return this;
    }

    /**
     * Are these (2) collections of methods equivalent ?
     *
     * @param left
     * @param right
     * @return true if these collections are semantically equivalent
     *
     * public static boolean equivalent( Collection<_method> left,
     * Collection<_method> right ){ return EQIVALENT_METHODS.equivalent(left,
     * right); }
     */
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

        default _method getMethod(String name) {
            List<_method> lm = listMethods(name);
            if (lm.isEmpty()) {
                return null;
            }
            return lm.get(0);
        }

        default List<_method> listMethods(String name) {
            return listMethods().stream().filter(m -> m.getName().equals(name)).collect(Collectors.toList());
        }

        default List<_method> listMethods(Predicate<_method> _methodMatchFn) {
            return listMethods().stream().filter(_methodMatchFn).collect(Collectors.toList());
        }

        default T forMethods(Consumer<_method> methodConsumer) {
            return forMethods(m -> true, methodConsumer);
        }

        default T forMethods(Predicate<_method> methodMatchFn,
                Consumer<_method> methodConsumer) {
            listMethods(methodMatchFn).forEach(methodConsumer);
            return (T) this;
        }

        default T removeMethod(_method _m) {
            this.forMethods(m -> m.equals(_m), m-> m.astMethod.removeForced() );
            return removeMethod( _m.astMethod );
        }

        default T removeMethod(MethodDeclaration astM) {
            this.forMethods(m -> m.equals(_method.of(astM)), m-> m.astMethod.removeForced() );
            return (T) this;
        }

        default T removeMethods(Predicate<_method> methodPredicate) {
            listMethods(methodPredicate).forEach(_m -> removeMethod(_m));
            return (T) this;
        }

        T method(MethodDeclaration method);

        default T method(String... method) {
            return method(Ast.method(method));
        }

        default T method(_method _m) {
            return method(_m.ast());
        }

        default T method(String signature, Expr.Command parametersAndBody) {
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = fromSignature(signature);
            _m = updateBody(_m, le);
            return method(_m);
        }

        default T methods(_method... ms) {
            Arrays.stream(ms).forEach(m -> method(m));
            return (T) this;
        }

        /**
         * Pass in the BODY of a main method and _1_build/add a
         *
         * public static void main(String[] args) {...} method
         *
         * @param mainMethodBody the BODY of the main method
         * @return the modified T
         */
        default T main(String... mainMethodBody) {
            _method _m = _method.of("public static void main(String[] args){ }");
            _m.add(mainMethodBody);
            return method(_m);
        }

        /**
         * Build a public static void main(String[] args) {...} method with the
         * contents of the lambda
         *
         * @param body
         * @return
         */
        default T main(Expr.Command body) {
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = _method.of("public static void main(String[] args){ }");
            if (le.getBody().isBlockStmt()) {
                _m.setBody(le.getBody().asBlockStmt());
            } else {
                _m.add(le.getBody());
            }
            //TODO? should I removeIn / replaceIn the main method if one is already found??
            return method(_m);
        }

        /**
         * Build a public static void main(String[] args) {...} method with the
         * contents of the lambda
         *
         * @param body
         * @return
         */
        default T main(Consumer<String[]> body) {
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = _method.of("public static void main(String[] args){ }");
            if (le.getBody().isBlockStmt()) {
                _m.setBody(le.getBody().asBlockStmt());
            } else {
                _m.add(le.getBody());
            }
            //TODO? should I removeIn / replaceIn the main method if one is already found??
            return method(_m);
        }

        default T method(String methodDef) {
            return method(new String[]{methodDef});
        }

        default T method(Object anonymousObjectContainingMethod) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
            ObjectCreationExpr oce = Expr.anonymousObject(ste);
            if (oce == null || !oce.getAnonymousClassBody().isPresent()) {
                throw new DraftException("No anonymous Object containing a method provided ");
            }
            Optional<BodyDeclaration<?>> obd = oce.getAnonymousClassBody().get().stream()
                    .filter(bd -> bd instanceof MethodDeclaration
                    && !bd.asMethodDeclaration().getAnnotationByClass(_remove.class).isPresent()).findFirst();
            if (!obd.isPresent()) {
                throw new DraftException("Could not find Method in anonymous object body");
            }
            MethodDeclaration md = (MethodDeclaration) obd.get();
            return method(md);
        }

        /**
         * Builds a method with the signature and
         *
         * @return
         */
        default <A extends Object> T method(String signature, Consumer<A> parametersAndBody) {
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = fromSignature(signature);
            _m = updateBody(_m, le);
            return method(_m);
        }

        /**
         * _method.of( "public static final void print", ()->{
         * System.out.println(1); });
         *
         * @return
         */
        default <A extends Object, B extends Object> T method(String signature, BiConsumer<A, B> parametersAndBody) {
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = fromSignature(signature);
            _m = updateBody(_m, le);
            return method(_m);
        }

        /**
         *
         * _method.of( "public static final void print", ()->{
         * System.out.println(1); });
         *
         * @return
         */
        default <A extends Object, B extends Object> T method(String signature, Function<A, B> parametersAndBody) {
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = fromSignature(signature);
            return method(updateBody(_m, le));
        }

        /**
         * ( "public static final void print", ()->{ System.out.println(1); });
         *
         * @return
         */
        default <A extends Object, B extends Object, C extends Object> T method(String signature, BiFunction<A, B, C> parametersAndBody) {
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = fromSignature(signature);
            return method(updateBody(_m, le));
        }

        default <A extends Object, B extends Object, C extends Object, D extends Object> T method(String signature, Expr.TriFunction<A, B, C, D> parametersAndBody) {
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = fromSignature(signature);
            return method(updateBody(_m, le));
        }

        default <A extends Object, B extends Object, C extends Object, D extends Object, E extends Object> T method(String signature, Expr.QuadFunction<A, B, C, D, E> parametersAndBody) {
            LambdaExpr le = Expr.lambda(Thread.currentThread().getStackTrace()[2]);
            _method _m = fromSignature(signature);
            return method(updateBody(_m, le));
        }
    }

    public static String describeMethodSignature(_method _m) {
        StringBuilder sb = new StringBuilder();
        sb.append(_m.getName());
        sb.append("(");
        for (int i = 0; i < _m.getParameters().count(); i++) {
            if (i > 0) {
                sb.append(", ");
            }
            sb.append(_m.getParameter(i).getType());
            if (_m.getParameter(i).isVarArg()) {
                sb.append("...");
            }
        }
        sb.append(")");
        return sb.toString();
    }

    public static final _methodsInspect INSPECT_METHODS = new _methodsInspect();

    public static class _methodsInspect implements _inspect<List<_method>>,
            _differ<List<_method>, _node> {

        static class _matchedMethods {

            _method left;
            _method right;

            public _matchedMethods(_method left, _method right) {
                this.left = left;
                this.right = right;
            }
        }

        @Override
        public boolean equivalent(List<_method> left, List<_method> right) {
            Set<_method> ls = new HashSet<>();
            ls.addAll(left);
            Set<_method> rs = new HashSet<>();
            rs.addAll(right);
            return Objects.equals(ls, rs);
        }

        public static _method findSameNameAndParameters(_method tm, Set<_method> targets) {
            Optional<_method> om = targets.stream().filter(m -> m.getName().equals(tm.getName())
                    //&& m.getType().equals(tm.getType())
                    && m.getParameters().hasParametersOfType(tm.getParameters().types())).findFirst();
            if( om.isPresent() ) {
                return om.get();
            }
            return null;
        }

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, List<_method> left, List<_method> right) {
            Set<_method> ls = new HashSet<>();
            ls.addAll(left);
            Set<_method> rs = new HashSet<>();
            rs.addAll(right);

            Set<_method> both = new HashSet<>();
            both.addAll(left);
            both.retainAll(right);

            ls.removeAll(right);
            rs.removeAll(left);

            ls.forEach(m -> {
                _method fm = findSameNameAndParameters(m, rs);
                //System.out.println(" LEFT NOT MATCHED "+m+" "+rs);
                if(fm != null) {
                    rs.remove(fm);
                    INSPECT_METHOD.diff(
                            path,
                            dt,
                            leftRoot,
                            rightRoot,
                            m,
                            fm);
                } else {
                    //System.out.println("METHOD"+ m );
                    dt.node(new _removeMethod(
                            path.in(_java.Component.METHOD, describeMethodSignature(m)),
                            (_hasMethods) leftRoot, (_hasMethods) rightRoot, m) );
                }
            });
            rs.forEach(m -> {
                //System.out.println(" RIGHT NOT MATCHED "+m);
                //dt.add( path.in(_java.Component.METHOD, describeMethodSignature(m)), null, m );                
                dt.node(new _addMethod(
                        path.in(_java.Component.METHOD, describeMethodSignature(m)),
                        (_hasMethods) leftRoot, (_hasMethods) rightRoot, m));
            });
            return (_dif) dt;
        }

        public static class _removeMethod implements _delta<_hasMethods>, _remove<_method> {

            public _path path;
            public _hasMethods rightRoot;
            public _hasMethods leftRoot;
            public _method toRemove;

            public _removeMethod(_path path, _hasMethods leftRoot, _hasMethods rightRoot, _method _toRemove) {
                this.path = path;
                this.leftRoot = leftRoot;
                this.rightRoot = rightRoot;                
                this.toRemove = _method.of( _toRemove.toString());
            }

            @Override
            public _hasMethods leftRoot() {
                return leftRoot;
            }

            @Override
            public _hasMethods rightRoot() {
                return rightRoot;
            }

            @Override
            public _path path() {
                return path;
            }

            @Override
            public _method removed() {
                return toRemove;
            }

            @Override
            public void keepRight() {
                this.leftRoot.removeMethod(toRemove);
                this.rightRoot.removeMethod(toRemove);
            }

            @Override
            public void keepLeft() {
                this.leftRoot.removeMethod(toRemove); //dont double add
                this.leftRoot.method(toRemove);                
                this.rightRoot.removeMethod(toRemove);
                this.rightRoot.method(toRemove);               
            }

            @Override
            public String toString(){
                return "   - "+path;
            }
        }

        public static class _addMethod implements _delta<_hasMethods>, _add<_method> {

            public _path path;
            public _hasMethods rightRoot;
            public _hasMethods leftRoot;
            public _method toAdd;

            public _addMethod(_path path, _hasMethods leftRoot, _hasMethods rightRoot, _method _toAdd) {
                this.path = path;
                this.leftRoot = leftRoot;
                this.rightRoot = rightRoot;
                this.toAdd = _method.of( _toAdd.toString());
            }

            @Override
            public _hasMethods leftRoot() {
                return leftRoot;
            }

            @Override
            public _hasMethods rightRoot() {
                return rightRoot;
            }

            @Override
            public _path path() {
                return path;
            }

            @Override
            public _method added() {
                return toAdd;
            }

            @Override
            public void keepRight() {
                this.leftRoot.removeMethod(toAdd);
                this.leftRoot.method(toAdd);

                this.rightRoot.removeMethod(toAdd);
                this.rightRoot.method(toAdd);
            }

            @Override
            public void keepLeft() {
                this.leftRoot.removeMethod(toAdd);
                this.rightRoot.removeMethod(toAdd);                
            }
            
            @Override
            public String toString(){
                return "   + "+path;
            }
        }

        @Override
        public _inspect._diff diff(_java._inspector _ins, _path path, _inspect._diff dt, List<_method> left, List<_method> right) {
            Set<_method> ls = new HashSet<>();
            ls.addAll(left);
            Set<_method> rs = new HashSet<>();
            rs.addAll(right);

            Set<_method> both = new HashSet<>();
            both.addAll(left);
            both.retainAll(right);

            ls.removeAll(right);
            rs.removeAll(left);

            ls.forEach(m -> {

                _method fm = findSameNameAndParameters(m, rs);
                if (fm != null) {
                    rs.remove(fm);
                    _ins.INSPECT_METHOD.diff(_ins, path.in(_java.Component.METHOD, describeMethodSignature(m)), dt, m, fm);
                } else {
                    dt.add(path.in(_java.Component.METHOD, describeMethodSignature(m)), m, null);
                }
            });
            rs.forEach(m -> {
                dt.add(path.in(_java.Component.METHOD, describeMethodSignature(m)), null, m);
            });
            return dt;
        }
    }

    public static final _methodInspect INSPECT_METHOD = new _methodInspect();

    public static class _methodInspect implements _inspect<_method>,
            _differ<_method, _node> {

        @Override
        public boolean equivalent(_method left, _method right) {
            return Objects.equals(left, right);
        }

        @Override
        public _inspect._diff diff(_java._inspector _ins, _path path, _inspect._diff dt, _method left, _method right) {
            if (left == null) {
                if (right == null) {
                    return dt;
                }
                return dt.add(path.in(_java.Component.METHOD, describeMethodSignature(right)), null, right);
            }
            if (right == null) {
                return dt.add(path.in(_java.Component.METHOD, describeMethodSignature(left)), left, null);
            }
            _ins.INSPECT_JAVADOC.diff(_ins, path, dt, left.getJavadoc(), right.getJavadoc());
            _ins.INSPECT_ANNOS.diff(_ins, path, dt, left.getAnnos(), right.getAnnos());
            _ins.INSPECT_MODIFIERS.diff(_ins, path, dt, left.getModifiers(), right.getModifiers());
            _ins.INSPECT_TYPE_REF.diff(_ins, path, dt, left.getType(), right.getType());
            _ins.INSPECT_NAME.diff(_ins, path, dt, left.getName(), right.getName());
            _ins.INSPECT_RECEIVER_PARAMETER.diff(_ins, path, dt, left.getReceiverParameter(), right.getReceiverParameter());
            _ins.INSPECT_PARAMETERS.diff(_ins, path, dt, left.getParameters(), right.getParameters());
            _ins.INSPECT_TYPE_PARAMETERS.diff(_ins, path, dt, left.getTypeParameters(), right.getTypeParameters());
            _ins.INSPECT_THROWS.diff(_ins, path, dt, left.getThrows(), right.getThrows());
            _ins.INSPECT_BODY.diff(_ins, path, dt, left.getBody(), right.getBody());
            return dt;
        }

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, _method left, _method right) {
            _path p = path.in(_java.Component.METHOD, describeMethodSignature(left));
            
            _javadoc.INSPECT_JAVADOC.diff(p, dt, left, right, left.getJavadoc(), right.getJavadoc());
            _anno.INSPECT_ANNOS.diff(p, dt, left, right, left.getAnnos(), right.getAnnos());
            _typeRef.INSPECT_TYPE_REF.diff(p, dt, left, right, left.getType(), right.getType());
            _modifiers.INSPECT_MODIFIERS.diff(p, dt, left, right, left.getEffectiveModifiers(), right.getEffectiveModifiers());
            _java.INSPECT_NAME.diff(p, dt, left, right, left.getName(), right.getName());
            _typeParameter.INSPECT_TYPE_PARAMETERS.diff(p, dt, left, right, left.getTypeParameters(), right.getTypeParameters());
            _receiverParameter.INSPECT_RECEIVER_PARAMETER.diff(p, dt, left, right, left.getReceiverParameter(), right.getReceiverParameter());
            _parameter.INSPECT_PARAMETERS.diff(p, dt, left, right, left.getParameters(),right.getParameters() );
            _throws.INSPECT_THROWS.diff(p, dt, left, right, left.getThrows(),right.getThrows() );
            _body.INSPECT_BODY.diff(p, dt, left, right, left.getBody(),right.getBody() );            
            return (_dif)dt;
        }
    }
}
