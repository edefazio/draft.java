package draft.java;

import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.expr.*;
import com.github.javaparser.ast.type.*;

import draft.java._model.*;
import draft.DraftException;
import draft.Text;
import draft.java._anno.*;
import draft.java._inspect._diff;
import draft.java._java._path;
import draft.java._java.Component;
import draft.java.macro._macro;
import draft.java.macro._remove;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Model of a Java Field (A Facade linked to a {@link VariableDeclarator} and
 * its parent {@link FieldDeclaration} )
 *
 * NOTE: many variables can be declared in a single {@link FieldDeclaration}:
 * <PRE>
 * int x,y,z;
 * </PRE> each of the above will be (3) separate {@link _field} (s) each with
 * the same {@link FieldDeclaration} root, and with (3) separate
 * {@link VariableDeclarator}
 *
 * thus, each {@link _field} is a reference to the {@link VariableDeclarator}
 * and it can "lookup" it's parent {@link FieldDeclaration}
 *
 */
public final class _field
        implements _javadoc._hasJavadoc<_field>, _anno._hasAnnos<_field>, _modifiers._hasModifiers<_field>,
        _modifiers._hasFinal<_field>, _modifiers._hasStatic<_field>, _modifiers._hasTransient<_field>,
        _modifiers._hasVolatile<_field>, _namedType<_field>,
        _member<VariableDeclarator, _field> {

    private final VariableDeclarator astVar;

    public static _field of(String singleString) {
        return of(new String[]{singleString});
    }

    public static _field of(Object anonymousObjectWithField) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject(ste);
        FieldDeclaration fd = (FieldDeclaration) oce.getAnonymousClassBody().get().stream().filter(bd -> bd instanceof FieldDeclaration
                && !bd.getAnnotationByClass(_remove.class).isPresent()).findFirst().get();

        //add the field to a class so I can run
        _class _c = _class.of("Temp").add(_field.of(fd.clone().getVariable(0)));
        _macro.to(anonymousObjectWithField.getClass(), _c);

        //I NEED TO DISSOCIATE THE FIELD FROM THE OTHER ??? (I think clone does that)
        return _c.getField(0);
    }

    /**
     * We can define a {@link FieldDeclaration} that represents multiple
     * distinct {@link _field}s:
     *
     * FieldDeclaration fd = Ast.field("int x,y,z;"); List<_field> _fs =
     * _fields.of(fd); //_fs is a list of (3) _fields
     *
     * we need to return a list
     *
     * @param f
     * @return
     */
    public static List<_field> of(FieldDeclaration f) {
        List<_field> fs = new ArrayList<>();
        for (int i = 0; i < f.getVariables().size(); i++) {
            fs.add(_field.of(f.getVariable(i)));
        }
        return fs;
    }

    public static _field of(VariableDeclarator v) {
        return new _field(v);
    }

    public static _field of(String... fieldDecl) {
        String str = Text.combine(fieldDecl);
        FieldDeclaration fd = Ast.field(str);
        if (fd.getVariables().size() != 1) {
            throw new DraftException("unable to create a singular field from " + str);
        }
        return new _field(fd.getVariable(0));
    }

    /**
     * Gets the (effective modifiers) which is the union of the explicit
     * modifiers (returned from {@link #getModifiers()} and the implied
     * modifiers, that are derived from the context
     *
     * @return an enumSet of the modifiers that are effective on this _field
     */
    @Override
    public NodeList<Modifier> getEffectiveModifiers() {
        if (this.getFieldDeclaration() == null) {
            return new NodeList<>();
        }

        NodeList<Modifier> implied = Ast.getImpliedModifiers(getFieldDeclaration());
        if (implied == null) {
            return getFieldDeclaration().getModifiers();
        }
        //implied.addAll(getFieldDeclaration().getModifiers());
        implied = Ast.merge(implied, getFieldDeclaration().getModifiers());
        return implied;
    }

    public boolean is(VariableDeclarator vd) {
        return of(vd).equals(this);
    }

    public boolean is(String... fieldDeclaration) {
        try {
            return of(fieldDeclaration).equals(this);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean is(FieldDeclaration fd) {
        return of(fd.getVariable(0))
                .equals(this);
    }

    public boolean initIs(Predicate<Expression> expressionPredicate) {
        return this.hasInit() && expressionPredicate.test(this.getInit());
    }

    public boolean initIs(String... initExpression) {
        Expression e = Expr.of(initExpression);
        return this.getInit().equals(e);
    }

    /**
     * remove the initial value declaration of the field
     */
    public _field removeInit() {
        this.astVar.removeInitializer();
        return this;
    }

    public boolean initIs(Expression e) {
        return Objects.equals(this.getInit(), e);
    }

    public boolean hasInit() {
        return this.astVar.getInitializer().isPresent();
    }

    public _field(VariableDeclarator astVar) {
        this.astVar = astVar;
    }

    @Override
    public VariableDeclarator ast() {
        //return this.getFieldDeclaration();
        return astVar;
    }
    
    @Override
    public VariableDeclarator astMember(){
        return astVar;
    }

    public Expression getInit() {
        if (astVar.getInitializer().isPresent()) {
            return astVar.getInitializer().get();
        }
        return null;
    }

    //it IS possible to set the parents FieldDeclaration to be null
    // in this case, the FieldDeclaration is null
    // it happens often enough  to want to shoot yourself
    public FieldDeclaration getFieldDeclaration() {
        if (astVar.getParentNode().isPresent()) {
            return (FieldDeclaration) astVar.getParentNode().get();
        }
        return null;
    }

    @Override
    public _annos getAnnos() {

        if (this.astVar != null && this.astVar.getParentNode().isPresent()) {

            return _annos.of(getFieldDeclaration());
        }
        //FIELDS are a pain this avoids issues if the FieldDeclaration if removed and the errant VarDeclarator
        //exists (not knowing it has been effectively deleted /removed from the model)
        // you SHOULDNT EVER HAVE a VarDeclarator w/o a FieldDeclaration, but (in practice) this
        // saves trying to double removeIn when the parent was removed
        return _annos.of();
    }

    @Override
    public String getName() {
        return astVar.getNameAsString();
    }

    @Override
    public boolean hasJavadoc() {
        return getFieldDeclaration().getJavadocComment().isPresent();
    }

    @Override
    public _field javadoc(String... contents) {
        getFieldDeclaration().setJavadocComment(Text.combine(contents));
        return this;
    }

    @Override
    public _field javadoc(JavadocComment astJavadocComment) {
        if (this.getFieldDeclaration() != null) {
            this.getFieldDeclaration().setJavadocComment(astJavadocComment);
        }
        return this;
    }

    @Override
    public _javadoc getJavadoc() {
        return _javadoc.of(getFieldDeclaration());
    }

    @Override
    public _field removeJavadoc() {
        getFieldDeclaration().removeJavaDocComment();
        return this;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        if (this.hasJavadoc()) {
            sb.append(this.getJavadoc());
        }
        if (this.hasAnnos()) {
            sb.append(this.getAnnos());
            //sb.append( System.lineSeparator() );
        }
        String mods = this.getModifiers().toString();
        if (mods.trim().length() > 0) {
            sb.append(mods);
            sb.append(" ");
        }
        sb.append(this.getType());
        sb.append(" ");
        sb.append(this.getName());
        if (this.hasInit()) {
            sb.append(" = ");
            sb.append(this.getInit());
        }
        sb.append(";");
        return sb.toString();
    }

    @Override
    public _field name(String name) {
        this.astVar.setName(name);
        return this;
    }

    @Override
    public _field type(Type t) {
        this.astVar.setType(t);
        return this;
    }

    @Override
    public _typeRef getType() {
        return _typeRef.of(astVar.getType());
    }

    @Override
    public boolean isType(Class clazz) {
        try {
            return this.astVar.getType().equals(Ast.typeRef(clazz.getSimpleName()))
                    || this.astVar.getType().equals(Ast.typeRef(clazz.getCanonicalName()));
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public boolean isType(Type type) {
        return this.astVar.getType().equals(type);
    }

    @Override
    public boolean isType(String type) {
        try {
            return this.astVar.getType().equals(Ast.typeRef(type));
        } catch (Exception e) {
        }
        return false;
    }

    @Override
    public _modifiers getModifiers() {
        return _modifiers.of(getFieldDeclaration());
    }

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
        final _field other = (_field) obj;
        if (this.astVar == other.astVar) {
            return true; //two _field s pointing to the same VariableDeclarator
        }
        if (!Objects.equals(getName(), other.getName())) {
            return false;
        }
        if (!Ast.typesEqual(this.astVar.getType(), other.astVar.getType())) {
            return false;
        }
        if (!Ast.modifiersEqual(getFieldDeclaration(), other.getFieldDeclaration())) {
            return false;
        }
        if (!Ast.annotationsEqual(getFieldDeclaration(), other.getFieldDeclaration())) {
            return false;
        }
        if (!Objects.equals(getJavadoc(), other.getJavadoc())) {
            return false;
        }
        if (!Objects.equals(getInit(), other.getInit())) {
            return false;
        }
        return true;
    }

    @Override
    public Map<_java.Component, Object> componentsMap() {
        Map<_java.Component, Object> parts = new HashMap<>();
        parts.put(_java.Component.NAME, getName());
        parts.put(_java.Component.TYPE, getType());
        parts.put(_java.Component.MODIFIERS, getModifiers());
        parts.put(_java.Component.JAVADOC, getJavadoc());
        parts.put(_java.Component.ANNOS, getAnnos());
        parts.put(_java.Component.INIT, getInit());
        return parts;
    }

    @Override
    public int hashCode() {
        Set<Modifier> ms = new HashSet<>();
        ms.addAll(getEffectiveModifiers());
        return Objects.hash(getName(), Ast.typeHash(astVar.getType()),
                ms, //getModifiers(),
                Ast.annotationsHash(getFieldDeclaration()),
                getJavadoc(), getInit());
    }

    public _field copy() {
        FieldDeclaration fd = getFieldDeclaration().clone();
        return new _field(fd.getVariable(fd.getVariables().indexOf(this.astVar)));
    }

    public boolean isPublic() {
        return getFieldDeclaration().isPublic() || getEffectiveModifiers().contains(Modifier.publicModifier());
    }

    public boolean isDefaultAccess() {
        return !this.getFieldDeclaration().isPublic()
                && !this.getFieldDeclaration().isPrivate()
                && !this.getFieldDeclaration().isProtected();
    }

    public boolean isProtected() {
        return this.getFieldDeclaration().isProtected();
    }

    public boolean isPrivate() {
        return this.getFieldDeclaration().isPrivate();
    }

    @Override
    public boolean isStatic() {
        return this.getFieldDeclaration().isStatic() || getEffectiveModifiers().contains(Modifier.staticModifier());
    }

    @Override
    public boolean isFinal() {
        return this.getFieldDeclaration().isFinal() || getEffectiveModifiers().contains(Modifier.finalModifier());
    }

    @Override
    public boolean isVolatile() {
        return this.getFieldDeclaration().isVolatile();
    }

    @Override
    public boolean isTransient() {
        return this.getFieldDeclaration().isTransient();
    }

    public _field setPublic() {
        if (this.getFieldDeclaration() != null) {
            this.getFieldDeclaration().setPrivate(false);
            this.getFieldDeclaration().setProtected(false);
            this.getFieldDeclaration().setPublic(true);
        }
        return this;
    }

    public _field setProtected() {
        if (this.getFieldDeclaration() != null) {
            this.getFieldDeclaration().setPrivate(false);
            this.getFieldDeclaration().setProtected(true);
            this.getFieldDeclaration().setPublic(false);
        }
        return this;
    }

    public _field setPrivate() {
        if (this.getFieldDeclaration() != null) {
            this.getFieldDeclaration().setPrivate(true);
            this.getFieldDeclaration().setProtected(false);
            this.getFieldDeclaration().setPublic(false);
        }
        return this;
    }

    public _field setDefaultAccess() {
        if (this.getFieldDeclaration() != null) {
            this.getFieldDeclaration().setPrivate(false);
            this.getFieldDeclaration().setProtected(false);
            this.getFieldDeclaration().setPublic(false);
        }
        return this;
    }

    @Override
    public _field setStatic() {
        return setStatic(true);
    }

    @Override
    public _field setFinal() {
        return setFinal(true);
    }

    @Override
    public _field setVolatile() {
        return setVolatile(true);
    }

    @Override
    public _field setTransient() {
        return setTransient(true);
    }

    @Override
    public _field setTransient(boolean toSet) {
        if (this.getFieldDeclaration() != null) {
            this.getFieldDeclaration().setTransient(toSet);
        }
        return this;
    }

    @Override
    public _field setVolatile(boolean toSet) {
        if (this.getFieldDeclaration() != null) {
            this.getFieldDeclaration().setVolatile(toSet);
        }
        return this;
    }

    @Override
    public _field setStatic(boolean toSet) {
        if (this.getFieldDeclaration() != null) {
            this.getFieldDeclaration().setStatic(toSet);
        }
        return this;
    }

    @Override
    public _field setFinal(boolean toSet) {
        if (this.getFieldDeclaration() != null) {
            this.getFieldDeclaration().setFinal(toSet);
        }
        return this;
    }

    public boolean isPrimitive() {
        return this.astVar.getType().isPrimitiveType();
    }

    public boolean isArray() {
        return this.astVar.getType().isArrayType();
    }

    public boolean isReferenceType() {
        return this.astVar.getType().isReferenceType();
    }

    public _field init(boolean b) {
        this.astVar.setInitializer(Expr.of(b));
        return this;
    }

    public _field init(byte b) {
        this.astVar.setInitializer(Expr.of(b));
        return this;
    }

    public _field init(short s) {
        this.astVar.setInitializer(Expr.of(s));
        return this;
    }

    public _field init(int i) {
        this.astVar.setInitializer(Expr.of(i));
        return this;
    }

    public _field init(char c) {
        this.astVar.setInitializer(Expr.of(c));
        return this;
    }

    public _field init(float f) {
        this.astVar.setInitializer(Expr.of(f));
        return this;
    }

    public _field init(double d) {
        this.astVar.setInitializer(Expr.of(d));
        return this;
    }

    public _field init(long l) {
        this.astVar.setInitializer(Expr.of(l));
        return this;
    }

    public _field init(String init) {
        this.astVar.setInitializer(init);
        return this;
    }

    public _field init(Supplier supplier) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        LambdaExpr sup = Expr.lambda(ste);
        return init(sup.getExpressionBody().get());
    }

    public _field init(Expression expr) {
        this.astVar.setInitializer(expr);
        return this;
    }

    /**
     * Verify that one list of _fields is equivalent to another list of _fields
     * Here we put the _fields in a HashSet and verify the HashSets are equal
     *
     * public static Semantic<Collection<_field>> EQIVALENT_FIELDS_LIST =
     * (Collection<_field> o1, Collection<_field> o2) -> { if( o1 == null ){
     * return o2 == null; } if( o1.size() != o2.size()){ return false; }
     * Set<_field> tm = new HashSet<>(); Set<_field> om = new HashSet<>();
     * tm.addAll(o1); om.addAll(o2); return Objects.equals(tm, om); };
     *
     * public static boolean equivalent( Collection<_field> left,
     * Collection<_field> right){ return EQIVALENT_FIELDS_LIST.equivalent(left,
     * right); }
     */
    /**
     *
     * @param right
     * @return
     */
    public _diff diff(_field right) {
        return INSPECT_FIELD.diff(this, right);
    }

    public static _diff diff(_field left, _field right) {
        return INSPECT_FIELD.diff(left, right);
    }

    public static _diff diff(List<_field> left, List<_field> right) {
        return INSPECT_FIELDS.diff(left, right);
    }

    /**
     * Components that have fields (_class, _interface, _enum, _annotation, _enum._constant)
     * 
     * @author Eric
     * @param <T>
     */
    public interface _hasFields<T extends _hasFields>
            extends _model {

        List<_field> listFields();

        default boolean hasFields() {
            return !listFields().isEmpty();
        }

        default List<_field> listFields(Predicate<_field> _fieldMatchFn) {
            return listFields().stream().filter(_fieldMatchFn).collect(Collectors.toList());
        }

        default T forFields(Consumer<_field> fieldConsumer) {
            return forFields(f -> true, fieldConsumer);
        }

        default T forFields(Predicate<_field> fieldMatchFn,
                Consumer<_field> fieldConsumer) {
            listFields(fieldMatchFn).forEach(fieldConsumer);
            return (T) this;
        }

        default T removeField(String fieldName){
            this.listFields(f-> f.getName().equals(fieldName)).forEach(f-> {
                if(f.getFieldDeclaration().getVariables().size() == 1){
                    f.getFieldDeclaration().removeForced();
                } else {
                    f.astVar.removeForced();
                }            
                });
            return (T)this;
        }

        default T removeField(_field _f){
            this.listFields(f-> f.equals(_f)).forEach(f-> {
                if(f.getFieldDeclaration().getVariables().size() == 1){
                    f.getFieldDeclaration().removeForced();
                } else {
                    f.astVar.removeForced();
                }            
                });
            return (T)this;
        }

        default T removeFields(Predicate<_field> fieldPredicate){
            this.listFields(fieldPredicate).forEach(f-> {
                if(f.getFieldDeclaration().getVariables().size() == 1){
                    f.getFieldDeclaration().removeForced();
                } else {
                    f.astVar.removeForced();
                }            
                });
            return (T)this;
        }

        default _field getField(String name) {
            return getField(f -> f.getName().equals(name));
        }

        default _field getField(int index) {
            return listFields().get(index);
        }

        default _field getField(Predicate<_field> _fieldMatchFn) {
            Optional<_field> of = listFields().stream().filter(_fieldMatchFn).findFirst();
            if (of.isPresent()) {
                return of.get();
            }
            return null;
        }

        T field(VariableDeclarator field);

        default T field(String... field) {
            return field(Ast.field(field).getVariable(0));
        }

        default T field(_field _f) {

            return field(_f.astVar);
        }

        default T fields(FieldDeclaration fds) {
            fds.getVariables().forEach(v -> field(v));
            return (T) this;
        }

        default T fields(String... fieldDeclarations) {
            List<FieldDeclaration> fs = Ast.fields(fieldDeclarations);
            fs.forEach(f -> fields(f));
            return (T) this;
        }

        default T fields(_field... fs) {
            Arrays.stream(fs).forEach(f -> field(f));
            return (T) this;
        }
    }

    public static _fieldsInspect INSPECT_FIELDS = new _fieldsInspect();

    public static class remove_field //extends remove_node<_hasAnnos, _anno>{
            implements _differ._delta<_hasFields>, _differ._remove<_field> {

        public _path path;
        public _hasFields leftRoot;
        public _hasFields rightRoot;
        public _field toRemove;

        public remove_field( _path p, _hasFields left, _hasFields right, _field toRemove) {
            this.path = p;
            this.leftRoot = left;
            this.rightRoot = right;
            this.toRemove = _field.of( toRemove.toString());
        }

        @Override
        public _hasFields leftRoot() {
            return leftRoot;
        }

        @Override
        public _hasFields rightRoot() {
            return rightRoot;
        }

        @Override
        public _path path() {
            return path;
        }

        @Override
        public _field removed() {
            return toRemove;
        }

        @Override
        public void keepRight() {
            //remove the anno
            leftRoot.removeField(toRemove);
            rightRoot.removeField(toRemove);
        }

        @Override
        public void keepLeft() {
            //remove it IN CASE so we dont mistakenly add it twice
            leftRoot.removeField(toRemove);
            leftRoot.field(toRemove);
            rightRoot.removeField(toRemove);
            rightRoot.field(toRemove);
        }

        @Override
        public String toString() {
            return "   - " + path;
        }
    }

    public static class add_field implements _differ._delta<_hasFields>, _differ._add<_field> {

        public _path path;
        public _hasFields leftRoot;
        public _hasFields rightRoot;
        public _field toAdd;

        public add_field(_path p, _hasFields left, _hasFields right, _field toAdd) {
            this.path = p;
            this.leftRoot = left;
            this.rightRoot = right;
            this.toAdd = _field.of( toAdd.toString() );
        }

        @Override
        public _hasFields leftRoot() {
            return leftRoot;
        }

        @Override
        public _hasFields rightRoot() {
            return rightRoot;
        }

        @Override
        public _path path() {
            return path;
        }

        @Override
        public _field added() {
            return toAdd;
        }

        @Override
        public void keepRight() {
            //remove it before just so we dont mistakenly add it twice
            leftRoot.removeField(toAdd);
            leftRoot.field(toAdd);
            rightRoot.removeField(toAdd);
            rightRoot.field(toAdd);
        }

        @Override
        public void keepLeft() {
            //remove it before just so we dont mistakenly add it twice
            leftRoot.removeField(toAdd);
            rightRoot.removeField(toAdd);
        }

        @Override
        public String toString() {
            return "   + " + path;
        }
    }

    public static class _fieldsInspect implements _inspect<List<_field>>,
            _differ<List<_field>, _node> {

        @Override
        public boolean equivalent(List<_field> left, List<_field> right) {
            Set<_field> lf = new HashSet<>();
            Set<_field> rf = new HashSet<>();
            lf.addAll(left);
            rf.addAll(right);
            return Objects.equals(lf, rf);
        }

        public _field getFieldNamed(Set<_field> _fs, String name) {
            Optional<_field> _f
                    = _fs.stream().filter(f -> f.getName().equals(name)).findFirst();
            if (_f.isPresent()) {
                return _f.get();
            }
            return null;
        }

        @Override
        public _inspect._diff diff(_java._inspector _ins, _path path, _inspect._diff dt, List<_field> left, List<_field> right) {
            Set<_field> lf = new HashSet<>();
            Set<_field> rf = new HashSet<>();
            lf.addAll(left);
            rf.addAll(right);
            Set<_field> both = new HashSet<>();
            both.addAll(left);
            both.retainAll(right);

            //organize fields that have the same name as edits
            lf.removeAll(both);
            rf.removeAll(both);

            lf.forEach(f -> {
                _field match = getFieldNamed(rf, f.getName());
                if (match != null) {
                    //dt.add(path.in( _java.Component.FIELD, f.getName()), f, match);
                    _ins.INSPECT_FIELD.diff(_ins, path.in(_java.Component.FIELD), dt, f, match);
                    rf.remove(match);
                } else {
                    dt.add(path.in(_java.Component.FIELD, f.getName()), f, null);
                }
            });

            rf.forEach(f -> {
                dt.add(path.in(_java.Component.FIELD, f.getName()), null, f);
            });
            return dt;
        }

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, List<_field> left, List<_field> right) {
            Set<_field> lf = new HashSet<>();
            Set<_field> rf = new HashSet<>();
            lf.addAll(left);
            rf.addAll(right);
            Set<_field> both = new HashSet<>();
            both.addAll(left);
            both.retainAll(right);

            //organize fields that have the same name as edits
            lf.removeAll(both);
            rf.removeAll(both);

            lf.forEach(f -> {
                _field match = getFieldNamed(rf, f.getName());
                if (match != null) {
                    INSPECT_FIELD.diff(path, dt, leftRoot, rightRoot, f, match);
                    rf.remove(match);
                } else {
                    dt.node(new remove_field(path.in(_java.Component.FIELD, f.getName()), (_hasFields) leftRoot, (_hasFields) rightRoot, f));
                }
            });
            rf.forEach(f -> {
                dt.node(new add_field(path.in(_java.Component.FIELD, f.getName()), (_hasFields) leftRoot, (_hasFields)rightRoot, f));
            });

            return (_dif)dt;
        }
    }

    public static _fieldInspect INSPECT_FIELD = new _fieldInspect();

    public static class _fieldInspect implements _inspect<_field>,
            _differ<_field, _node> {

        @Override
        public boolean equivalent(_field left, _field right) {
            return Objects.equals(left, right);
        }

        @Override
        public _inspect._diff diff(_java._inspector _ins, _path path, _inspect._diff dt, _field left, _field right) {
            if (left == null) {
                if (right == null) {
                    return dt;
                }
                return dt.add(path.in(_java.Component.FIELD, right.getName()), null, right);
            }
            if (right == null) {
                return dt.add(path.in(_java.Component.FIELD, left.getName()), left, null);
            }
            _ins.INSPECT_NAME.diff(_ins, path, dt, left.getName(), right.getName());
            _ins.INSPECT_TYPE_REF.diff(_ins, path, dt, left.getType(), right.getType());
            _ins.INSPECT_MODIFIERS.diff(_ins, path, dt, left.getModifiers(), right.getModifiers());
            _ins.INSPECT_JAVADOC.diff(_ins, path, dt, left.getJavadoc(), right.getJavadoc());
            _ins.INSPECT_ANNOS.diff(_ins, path, dt, left.getAnnos(), right.getAnnos());
            _ins.INSPECT_INIT.diff(_ins, path, dt, left.getInit(), right.getInit());
            return dt;
        }

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, _field left, _field right) {

            _path p = path.in(Component.FIELD, left != null ? left.getName() : right.getName());
            _java.INSPECT_NAME.diff(p, dt, left, right, left.getName(), right.getName());
            _typeRef.INSPECT_TYPE_REF.diff(p, dt, left, right, left.getType(), right.getType());
            _modifiers.INSPECT_MODIFIERS.diff(p, dt, left, right, left.getEffectiveModifiers(), right.getEffectiveModifiers());
            _javadoc.INSPECT_JAVADOC.diff(path, dt, left, right, left.getJavadoc(), right.getJavadoc());
            _anno.INSPECT_ANNOS.diff(path, dt, left, right, left.getAnnos(), right.getAnnos());

            if (!Objects.equals(left.getInit(), right.getInit())) {
                dt.node(new _changeInit(p.in(Component.INIT), left, right));
            }
            return (_dif) dt;
        }
    }

    /**
     * Both signifies a delta and provides a means to commit (via right()) or
     * rollback( via left())
     */
    public static class _changeInit
            implements _differ._delta<_field>, _differ._change<Expression> {

        _path path;
        _field left;
        _field right;
        Expression leftExpression;
        Expression rightExpression;

        public _changeInit(_path _p, _field left, _field right) {
            this.path = _p;
            this.left = left;
            if (left.hasInit()) {
                this.leftExpression = left.getInit().clone();
            }
            this.right = right;
            if (right.hasInit()) {
                this.rightExpression = right.getInit().clone();
            }
        }

        @Override
        public void keepLeft() {
            left.init(leftExpression);
            right.init(leftExpression);
        }

        @Override
        public void keepRight() {
            left.init(rightExpression);
            right.init(rightExpression);
        }

        @Override
        public Expression left() {
            return leftExpression;
        }

        @Override
        public Expression right() {
            return rightExpression;
        }

        @Override
        public _field leftRoot() {
            return left;
        }

        @Override
        public _field rightRoot() {
            return right;
        }

        @Override
        public _path path() {
            return path;
        }
    }
    public static final _java.ExpressionInspect INSPECT_INIT = new _java.ExpressionInspect(_java.Component.INIT);
}
