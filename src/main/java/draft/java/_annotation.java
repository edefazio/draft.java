package draft.java;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.type.Type;
import draft.DraftException;
import draft.java._anno.*;
import draft.java._inspect._diff;
import draft.java._java._path;
import draft.java._java.Component;
import draft.java.io._in;
import draft.java.macro._macro;

import java.io.InputStream;
import java.lang.annotation.*;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Logical Mutable Model of the source code representing a Java annotation.<BR>
 *
 * Implemented as a "Facade" on top of an AST ({@link AnnotationDeclaration})
 * All state is stored within the AST, this facade supplies access to
 *
 * @author Eric
 */
public final class _annotation
        implements _type<AnnotationDeclaration, _annotation> {

    public static _annotation of( Class<? extends Annotation> clazz ){
        Node n = Ast.type( clazz );
        if( n instanceof CompilationUnit ){
            return _macro.to(clazz, of( (CompilationUnit)n));
        }
        //not a compuilation
        Set<Class> imps = _type.inferImportsFrom(clazz);
        _annotation _a = of( (AnnotationDeclaration)n);
        imps.forEach(i -> _a.imports(i) );
        return _macro.to(clazz, _a);        
    }

    /**
     *
     * NOTE: default values are set to initial values
     *
     * NOTE: macros are not run on the Anonymous Object BODY
     * @param signature
     * @param anonymousObjectBody
     * @return
     */
    public static _annotation of( String signature, Object anonymousObjectBody ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        _annotation _a = of( signature );
        ObjectCreationExpr oce = Expr.anonymousObject(ste);
        
        NodeList<BodyDeclaration<?>> bds = oce.getAnonymousClassBody().get();

        //each field REALLY represents
        bds.stream().filter( bd-> bd instanceof FieldDeclaration ).forEach( f-> {
            VariableDeclarator vd = ((FieldDeclaration) f).getVariable(0);
            if( vd.getInitializer().isPresent()){
                _a.element(_element.of(vd.getType(), vd.getNameAsString(), vd.getInitializer().get()) );
            } else {
                _a.element(_annotation._element.of(vd.getType(), vd.getNameAsString()));
            }
        });
        return _a;
    }

    public _annotation retentionPolicyRuntime(){
        this.imports(Retention.class, RetentionPolicy.class);
        this.removeAnnos(Retention.class); //remove if one already exists
        annotate( "Retention(RetentionPolicy.RUNTIME)");
        return this;
    }

    public _annotation retentionPolicyClass(){
        this.imports(Retention.class, RetentionPolicy.class);
        this.removeAnnos(Retention.class);
        annotate( "Retention(RetentionPolicy.CLASS)");
        return this;
    }

    public _annotation retentionPolicySource(){
        this.imports(Retention.class, RetentionPolicy.class);
        this.removeAnnos(Retention.class);
        annotate( "Retention(RetentionPolicy.SOURCE)");
        return this;
    }

    /**
     * Set the target element types for this annotation
     * (with an annotation)
     * @param elementTypes the element types this annotation should target
     * @return the modified annotation
     */
    public _annotation targets( ElementType...elementTypes ){        
        if( elementTypes.length == 0 ){
            this.removeAnnos( Target.class);
        }
        this.imports(Target.class, ElementType.class); 
        if( elementTypes.length == 1 ){
            this.removeAnnos( Target.class);
            return annotate("Target(ElementType."+elementTypes[0].name()+")" );
        }                
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<elementTypes.length; i++){
            if( i > 0 ){
                sb.append(",");
            }
            sb.append("ElementType.").append(elementTypes[i].name() );
        }
        return annotate("Target({"+sb.toString()+"})");        
    }
    
    public _annotation targetMethod(){
        this.imports( Target.class, ElementType.class );        
        removeAnnos(Target.class);
        annotate("Target(ElementType.METHOD)");
        return this;
    }

    public _annotation targetParameter(){
        this.imports( Target.class, ElementType.class );
        removeAnnos(Target.class);
        annotate("Target(ElementType.PARAMETER)");
        return this;
    }

    public _annotation targetTypeUse(){
        this.imports( Target.class, ElementType.class );
        removeAnnos(Target.class);
        annotate("Target(ElementType.TYPE_USE)");
        return this;
    }

    public _annotation targetType(){
        this.imports( Target.class, ElementType.class );
        removeAnnos(Target.class);
        annotate("Target(ElementType.TYPE)");
        return this;
    }

    public _annotation targetTypeParameter(){
        this.imports( Target.class, ElementType.class );
        removeAnnos(Target.class);
        annotate("Target(ElementType.TYPE_PARAMETER)");
        return this;
    }

    public _annotation targetLocalVariable(){
        this.imports( Target.class, ElementType.class );
        removeAnnos(Target.class);
        annotate("Target(ElementType.LOCAL_VARIABLE)");
        return this;
    }

    public _annotation targetAnnotationType(){
        this.imports( Target.class, ElementType.class );
        removeAnnos(Target.class);
        annotate("Target(ElementType.ANNOTATION_TYPE)");
        return this;
    }
    public _annotation targetPackage(){
        this.imports( Target.class, ElementType.class );
        removeAnnos(Target.class);
        annotate("Target(ElementType.PACKAGE)");
        return this;
    }

    public _annotation targetConstructor(){
        this.imports( Target.class, ElementType.class );
        removeAnnos(Target.class);
        annotate("Target(ElementType.CONSTRUCTOR)");
        return this;
    }

    public _annotation targetField(){
        this.imports( Target.class, ElementType.class );
        removeAnnos(Target.class);
        annotate("Target(ElementType.FIELD)");        
        return this;
    }

    public static _annotation of( String...classDef ){
        if( classDef.length == 1){
            String[] strs = classDef[0].split(" ");
            if( strs.length == 1 ){
                //shortcut classes
                String shortcutClass = strs[0];
                String packageName = null;
                int lastDotIndex = shortcutClass.lastIndexOf('.');
                if( lastDotIndex > 0 ){
                    packageName = shortcutClass.substring(0, lastDotIndex );
                    shortcutClass = shortcutClass.substring(lastDotIndex + 1);
                    if(!shortcutClass.endsWith("}")){
                        shortcutClass = shortcutClass + "{}";
                    }
                    return of( Ast.compilationUnit( "package "+packageName+";"+System.lineSeparator()+
                            "public @interface "+shortcutClass));
                }
                if(!shortcutClass.endsWith("}")){
                    shortcutClass = shortcutClass + "{}";
                }
                return of( Ast.compilationUnit("public @interface "+shortcutClass));
            }
        }
        return of( Ast.compilationUnit( classDef ));
    }

    public static _annotation of( CompilationUnit cu ){
        if( cu.getPrimaryTypeName().isPresent() ){
            return of( cu.getAnnotationDeclarationByName(cu.getPrimaryTypeName().get() ).get() );
        }
        NodeList<TypeDeclaration<?>> tds = cu.getTypes();
        if( tds.size() == 1 ){
            return of( (AnnotationDeclaration)tds.get(0) );
        }
        throw new DraftException("Unable to locate primary TYPE in "+ cu);
    }

    public static _annotation of( AnnotationDeclaration astClass ){
        return new _annotation( astClass );
    }

    public static _annotation of(InputStream is){
        return of( StaticJavaParser.parse(is) );
    }

    public static _annotation of( _in in ){
        return of( in.getInputStream());
    }
    public _annotation( AnnotationDeclaration astClass ){
        this.astAnnotation = astClass;
    }

    /**
     * the AST storing the state of the _class
     * the _class is simply a facade into the state astClass
     *
     * the _class facade is a "Logical View" of the _class state stored in the
     * AST and can interpret or manipulate the AST without:
     * having to deal with syntax issues
     */
    private final AnnotationDeclaration astAnnotation;

    @Override
    public boolean isTopClass(){
        return ast().isTopLevelType();
    }

    @Override
    public CompilationUnit findCompilationUnit(){
        if( this.ast().findCompilationUnit().isPresent() ){
            return this.ast().findCompilationUnit().get();
        }
        return null;
    }

    @Override
    public _annos getAnnos() {
        return _annos.of(this.astAnnotation );
    }

    @Override
    public String toString(){
        if( this.ast().isTopLevelType() ){
            return this.ast().findCompilationUnit().get().toString();
        }
        return this.astAnnotation.toString();
    }

    public boolean hasElements(){
        return !listElements().isEmpty();
    }

    public _annotation element( String... elementDeclaration ){
        return element( Ast.annotationMember( elementDeclaration ));
    }

    public _annotation element( _element _p){
        this.astAnnotation.addMember( _p.astAnnMember );
        return this;
    }

    public _annotation element( AnnotationMemberDeclaration annotationProperty){
        this.astAnnotation.addMember( annotationProperty );
        return this;
    }

    public _element getElement( String name ){
        List<_element> lps = listElements(p -> p.getName().equals( name ) );
        if( lps.isEmpty() ){
            return null;
        }
        return lps.get(0);
    }

    public List<_element> listElements(){
        NodeList<BodyDeclaration<?>> nb  = this.astAnnotation.getMembers();
        List<_element> ps = new ArrayList<>();
        nb.stream().filter( b -> b instanceof AnnotationMemberDeclaration )
                .forEach( am -> ps.add( _element.of( (AnnotationMemberDeclaration)am) ));
        return ps;
    }

    public List<_element> listElements(Predicate<_element> _elementMatchFn ){
        return listElements().stream().filter( _elementMatchFn ).collect(Collectors.toList());
    }

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
        final _annotation other = (_annotation)obj;
        if( this.astAnnotation == other.astAnnotation){
            return true; //short circuit... two _annotations refer to the same AnnotationDeclaration
        }
        if( !Objects.equals( this.getPackage(), other.getPackage())){
            return false;
        }
        if( !Ast.modifiersEqual(astAnnotation, astAnnotation) ){
            return false;
        }
        if( !Objects.equals( this.getJavadoc(), other.getJavadoc()) ){
            return false;
        }
        if( !Ast.annotationsEqual( astAnnotation, other.astAnnotation)){
            return false;
        }
        if( !Objects.equals( this.getName(), other.getName()) ){
            return false;
        }
        if( this.isTopClass() ){
            if( ! other.isTopClass() ){
                return false;
            }
            if( ! Ast.importsEqual( this.findCompilationUnit(), other.findCompilationUnit() ) ){
                return false;
            }
        }

        Set<_field> tf = new HashSet<>();
        Set<_field> of = new HashSet<>();
        tf.addAll( this.listFields() );
        tf.addAll( other.listFields() );

        if( !Objects.equals( tf, of)){
            return false;
        }

        Set<_element> tp = new HashSet<>();
        Set<_element> op = new HashSet<>();
        tp.addAll(this.listElements() );
        op.addAll(other.listElements() );

        if( !Objects.equals( tp, op)){
            return false;
        }

        Set<_type> tn = new HashSet<>();
        Set<_type> on = new HashSet<>();
        tn.addAll( this.listNests() );
        on.addAll( other.listNests() );

        if( !Objects.equals( tn, on)){
            return false;
        }
        return true;
    }

    public _annotation removeElement( String elementName ){
        _element _e = this.getElement(elementName );
        if( _e != null ) {
            this.astAnnotation.remove(_e.astAnnMember);
        }
        return this;
    }

    public _annotation removeElement( _element _e ){
        listElements(e -> e.equals(_e)).forEach(e-> e.ast().removeForced() );
        return this;
    }

    public _annotation removeElements( Predicate<_element> _pe ){
        listElements(_pe).forEach( e -> removeElement(e));
        return this;
    }

    public _annotation forElements( Consumer<_element> elementConsumer ){
        listElements().forEach(elementConsumer);
        return this;
    }

    public _annotation forElements( Predicate<_element> elementMatchFn, Consumer<_element> elementConsumer ){
        listElements(elementMatchFn).forEach(elementConsumer);
        return this;
    }

    @Override
    public List<_member> listMembers(){
        List<_member> _mems = new ArrayList<>();
        forFields( f-> _mems.add( f));
        forElements(e -> _mems.add(e));
        forNests(n -> _mems.add(n));
        return _mems;
    }

    @Override
    public _annotation forMembers( Predicate<_member>_memberMatchFn, Consumer<_member> _memberActionFn){
        listMembers(_memberMatchFn).forEach(m -> _memberActionFn.accept(m) );
        return this;
    }

    @Override
    public Map<_java.Component, Object> componentsMap( ) {
        Map<_java.Component, Object> parts = new HashMap<>();
        parts.put( _java.Component.PACKAGE_NAME, this.getPackage() );
        parts.put( _java.Component.IMPORTS, this.listImports() );
        parts.put( _java.Component.ANNOS, this.listAnnos() );
        parts.put( _java.Component.JAVADOC, this.getJavadoc() );
        parts.put( _java.Component.NAME, this.getName() );
        parts.put( _java.Component.MODIFIERS, this.getModifiers() );
        parts.put( _java.Component.ELEMENTS, this.listElements() );
        parts.put( _java.Component.FIELDS, this.listFields() );
        parts.put( _java.Component.NESTS, this.listNests() );
        return parts;
    }

    @Override
    public int hashCode() {
        int hash = 5;

        hash = 13 * hash + Objects.hashCode( this.getPackage() );
        hash = 13 * hash + Ast.importsHash( astAnnotation  );

        hash = 13 * hash + Objects.hashCode( this.getEffectiveModifiers() );

        hash = 13 * hash + Objects.hashCode( this.getJavadoc() );
        hash = 13 * hash + Ast.annotationsHash( astAnnotation  );

        hash = 13 * hash + Objects.hashCode( this.getName() );

        //organize
        Set<_field> fields = new HashSet<>();
        fields.addAll( this.listFields() );
        hash = 13 * hash + Objects.hashCode( fields );

        Set<_element> elements = new HashSet<>();
        elements.addAll(this.listElements() );
        hash = 13 * hash + Objects.hashCode( elements );

        Set<_type> nests = new HashSet<>();
        nests.addAll(  this.listNests() );
        hash = 13 * hash + Objects.hashCode( nests );

        return hash;
    }

    @Override
    public _annotation field( VariableDeclarator field ) {
        if(! field.getParentNode().isPresent()){
            throw new DraftException("cannot add Var without parent FieldDeclaration");
        }
        FieldDeclaration fd = (FieldDeclaration)field.getParentNode().get();
        //we already added it to the parent
        if( this.astAnnotation.getFields().contains( fd ) ){
            if( !fd.containsWithin( field ) ){
                fd.addVariable( field );
            }
            return this;
        }
        this.astAnnotation.addMember( fd );
        return this;
    }

    public boolean is( String...annotationDeclaration){
        try {
            return is(Ast.annotationDeclaration(annotationDeclaration));
        }catch(Exception e){
            return false;
        }
    }

    /** 
     * is the AnnotationDeclaration equal this class
     * @param ad 
     * @return true if these annotations are equivalent
     */
    public boolean is( AnnotationDeclaration ad ){
        try{
            _annotation _a = of( ad);
            return Objects.equals(this, _a);
        } catch(Exception e){
            return false;
        }
    }

    /**
     * Diff this _annotation against the _right _annotation 
     * and return a diff-tree containing the changes (add, remove, edit)s that
     * would be applied to this in order to transform it to the _right _annotation
     * 
     * @param right the "right" / or target annotation to diff / transform to
     * @return the changes required to make this to the _right _annotation  
     */
    public _diff diff( _annotation right ){
        return INSPECT_ANNOTATION.diff(this, right);
    }
        
    /**
     * Diff left _annotation against the right _annotation 
     * and return a diff-tree containing the changes (add, remove, edit)s that
     * would be applied to this in order to transform it to the right _annotation
     * 
     * @param left the starting annotation
     * @param right the target annotation to diff / transform to
     * @return the changes required to make this left to the right _annotation  
     */
    public static _diff diff( _annotation left, _annotation right){
        return INSPECT_ANNOTATION.diff(left, right);
    }

    @Override
    public AnnotationDeclaration ast() {
        return this.astAnnotation;
    }
    
    /**
     * a property element added to an annotation
     * <PRE>
     * // VALUE is an element of the Speed annotation
     * @interface Speed{
     *     int VALUE() default 0;
     * }
     * </PRE>
     *
     */
    public static class _element implements _javadoc._hasJavadoc<_element>,
            _anno._hasAnnos<_element>, _namedType<_element>,
            _member<AnnotationMemberDeclaration, _element> {

        public static _element of( AnnotationMemberDeclaration am){
            return new _element( am );
        }

        public static _element of( String...code ){
            return new _element( Ast.annotationMember( code ) );
        }

        public static _element of( Type type, String name ){
            AnnotationMemberDeclaration amd = new AnnotationMemberDeclaration();
            amd.setName(name);
            amd.setType(type);
            return of( amd );
        }

        public static _element of( Type type, String name, Expression defaultValue ){
            AnnotationMemberDeclaration amd = new AnnotationMemberDeclaration();
            amd.setName(name);
            amd.setType(type);
            amd.setDefaultValue(defaultValue);
            return of( amd );
        }
        
        private final AnnotationMemberDeclaration astAnnMember;

        public _element( AnnotationMemberDeclaration astAnnMember ){
            this.astAnnMember = astAnnMember;
        }

        @Override
        public _element name(String name){
            this.astAnnMember.setName( name );
            return this;
        }

        @Override
        public AnnotationMemberDeclaration ast(){
            return this.astAnnMember;
        }
        
        @Override
        public _element type( Type t){
            this.astAnnMember.setType( t );
            return this;
        }

        @Override
        public String getName(){
            return this.astAnnMember.getNameAsString();
        }

        @Override
        public _typeRef getType(){
            return _typeRef.of(this.astAnnMember.getType());
        }

        public boolean hasDefaultValue(){
            return this.astAnnMember.getDefaultValue().isPresent();
        }

        public _element removeDefaultValue(){
            this.astAnnMember.removeDefaultValue();
            return this;
        }
        
        public _element setDefaultValue(int intValue ){
            this.astAnnMember.setDefaultValue( Expr.of( intValue ) );
            return this;
        }
        
        public _element setDefaultValue(long longValue ){
            this.astAnnMember.setDefaultValue( Expr.of( longValue ) );
            return this;
        }
        
        public _element setDefaultValue(char charValue ){
            this.astAnnMember.setDefaultValue( Expr.of( charValue ) );
            return this;
        }
        
        public _element setDefaultValue(boolean booleanValue ){
            this.astAnnMember.setDefaultValue( Expr.of( booleanValue ) );
            return this;
        }
        
        public _element setDefaultValueNull(){
            this.astAnnMember.setDefaultValue( Expr.nullExpr() );
            return this;
        }
        
        public _element setDefaultValue(float floatValue ){
            this.astAnnMember.setDefaultValue( Expr.of( floatValue ) );
            return this;
        }
        
        public _element setDefaultValue(double doubleValue ){
            this.astAnnMember.setDefaultValue( Expr.of( doubleValue ) );
            return this;
        }
        
        public _element setDefaultValue( String defaultValueExpression){
            this.astAnnMember.setDefaultValue( Ast.expr( defaultValueExpression) );
            return this;
        }

        public _element setDefaultValue( Expression e){
            this.astAnnMember.setDefaultValue( e );
            return this;
        }

        public Expression getDefaultValue(){
            if( this.astAnnMember.getDefaultValue().isPresent()){
                return this.astAnnMember.getDefaultValue().get();
            }
            return null;
        }

        /*
        @Override
        public _javadoc getJavadoc() {
            return _javadoc.of(this.ast());
        }

        @Override
        public _element removeJavadoc(){
            this.ast().removeJavaDocComment();
            return this;
        }

        @Override
        public boolean hasJavadoc(){
            return this.ast().getJavadoc().isPresent();
        }

        @Override
        public _element javadoc( String... content ) {
            this.ast().setJavadocComment( Text.combine(content));
            return this;
        }
        
        @Override
        public _element javadoc( JavadocComment astJavadocComment ){
            this.ast().setJavadocComment( astJavadocComment );
            return this;
        }
        */

        @Override
        public _annos getAnnos() {
            return _annos.of(this.astAnnMember );
        }

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
            final _element other = (_element)obj;
            if( this.astAnnMember == other.astAnnMember){
                return true; //two _element instances pointing to same AstMemberDeclaration
            }
            if( !Ast.annotationsEqual( this.astAnnMember, other.astAnnMember)){
                return false;
            }
            if( !Objects.equals( this.getJavadoc(), other.getJavadoc() ) ) {
                return false;
            }
            if( !Objects.equals( this.getName(), other.getName() ) ) {
                return false;
            }
            if( !Ast.typesEqual( astAnnMember.getType(), other.astAnnMember.getType())){
                return false;
            }
            if( !Objects.equals( this.getDefaultValue(), other.getDefaultValue() ) ) {
                return false;
            }
            return true;
        }

        @Override
        public Map<_java.Component, Object> componentsMap( ) {
            Map<_java.Component, Object> parts = new HashMap<>();
            parts.put( _java.Component.ANNOS, this.listAnnos() );
            parts.put( _java.Component.JAVADOC, this.getJavadoc() );
            parts.put( _java.Component.NAME, this.getName() );
            parts.put( _java.Component.TYPE, this.getType() );
            parts.put( _java.Component.DEFAULT, this.getDefaultValue() );
            return parts;
        }
        
        @Override
        public int hashCode() {
            int hash = 7;
            hash = 97 * hash + Objects.hash(
                    Ast.annotationsHash(this.astAnnMember),
                    this.getJavadoc(),
                    this.getName(),
                    Ast.typeHash(this.astAnnMember.getType()),
                    this.getDefaultValue() );
            return hash;
        }

        @Override
        public String toString(){
            return this.astAnnMember.toString();
        }
        
        /**
         * Diff this _element against the _right _element 
         * and return a diff-tree containing the changes (add, remove, edit)s that
         * would be applied to this in order to transform it to the right _element
         * 
         * @param right the "right" / or target _element to diff / transform to
         * @return the changes required to make this to the right _element  
         */
        public _diff diff( _element right ){
            return INSPECT_ANNOTATION_ELEMENT.diff(this, right);
        }
        
        /**
         * Diff left _element against the right _element
         * and return a diff-tree containing the changes (add, remove, edit)s that
         * would be applied to this in order to transform it to the right _element
         * 
         * @param left the starting _element
         * @param right the target _element to diff / transform to
         * @return the changes required to make this left to the right _annotation  
         */
        public static _diff diff( _element left, _element right){
            return INSPECT_ANNOTATION_ELEMENT.diff(left, right);
        }
        
        /**
         * Diff left _elements against the right _elements
         * and return a diff-tree containing the changes (add, remove, edit)s that
         * would be applied to this in order to transform it to the right _elements
         * 
         * @param left the starting _elements
         * @param right the target _elements to diff / transform to
         * @return the changes required to make this left to the right _elements 
         */
        public static _diff diff( List<_element> left, List<_element> right){
            return INSPECT_ANNOTATION_ELEMENTS.diff(left, right);
        }        
    }
    
    public static _annotationInspect INSPECT_ANNOTATION = new _annotationInspect();
    
    public static class _annotationInspect implements _inspect<_annotation>, 
            _differ<_annotation, _node> {

        @Override
        public boolean equivalent(_annotation left,_annotation right) {
            return Objects.equals(left,right);
        }

        @Override
        public _inspect._diff diff( _java._inspector _ins, _path path, _inspect._diff dt, _annotation left, _annotation right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                return dt.add( path.in(_java.Component.ANNOTATION, right.getName()), null, right);                
            }
            if( right == null){
                return dt.add( path.in(_java.Component.ANNOTATION, left.getName()), left, null);
            }
            _ins.INSPECT_PACKAGE_NAME.diff(_ins, path,dt, left.getPackage(), right.getPackage() );
            _ins.INSPECT_IMPORTS.diff(_ins,path,dt, left.listImports(), right.listImports() );
            _ins.INSPECT_ANNOS.diff(_ins,path, dt, left.getAnnos(), right.getAnnos());                     
            _ins.INSPECT_JAVADOC.diff(_ins,path, dt, left.getJavadoc(), right.getJavadoc());              
            _ins.INSPECT_NAME.diff(_ins,path, dt, left.getName(), right.getName());
            _ins.INSPECT_MODIFIERS.diff(_ins,path, dt, left.getModifiers(), right.getModifiers());            
            _ins.INSPECT_FIELDS.diff(_ins,path, dt, left.listFields(), right.listFields() );
            _ins.INSPECT_ANNOTATION_ELEMENTS.diff(_ins,path, dt, left.listElements(), right.listElements() );
            _ins.INSPECT_NESTS.diff(_ins,path, dt, left.listNests(), right.listNests());
            return dt;
        }        

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, _annotation left, _annotation right) {
            _java.INSPECT_PACKAGE.diff(path, dt, leftRoot, rightRoot, left.getPackage(), right.getPackage());
            _type.INSPECT_IMPORTS.diff(path, dt, leftRoot, rightRoot, left.listImports(), right.listImports());
            _anno.INSPECT_ANNOS.diff(path, dt, leftRoot, rightRoot, left.getAnnos(), right.getAnnos());
            _javadoc.INSPECT_JAVADOC.diff(path, dt, leftRoot, rightRoot, left.getJavadoc(), right.getJavadoc());
            _java.INSPECT_NAME.diff(path, dt, leftRoot, rightRoot, left.getName(), right.getName());
            _modifiers.INSPECT_MODIFIERS.diff(path, dt, leftRoot, rightRoot, left.getEffectiveModifiers(), right.getEffectiveModifiers());
            _field.INSPECT_FIELDS.diff(path, dt, leftRoot, rightRoot, left.listFields(), right.listFields());
            INSPECT_ANNOTATION_ELEMENTS.diff(path, dt, leftRoot, rightRoot, left.listElements(), right.listElements());            
            _type.INSPECT_NESTS.diff(path, dt, leftRoot, rightRoot, left.listNests(), right.listNests());
            
            return (_dif)dt;
        }
    }
    
    public static _annotationElementsInspect INSPECT_ANNOTATION_ELEMENTS = new _annotationElementsInspect();
    
    public static class _annotationElementsInspect implements _inspect<List<_annotation._element>>, 
            _differ<List<_annotation._element>,_node>{

        @Override
        public boolean equivalent( List<_annotation._element> left, List<_annotation._element> right) {
            Set<_annotation._element>ls = new HashSet<>();
            Set<_annotation._element>rs = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            return Objects.equals(ls, rs);
        }

        public _annotation._element sameName( _annotation._element target, Set<_annotation._element> source ){
            Optional<_annotation._element> ec = 
                    source.stream().filter(c-> c.getName().equals(target.getName())).findFirst();
            if( ec.isPresent()){
                return ec.get();
            }
            return null;
        }
        
        @Override
        public _inspect._diff diff( _java._inspector _ins, _path path, _inspect._diff dt, List<_annotation._element> left, List<_annotation._element> right) {
            Set<_annotation._element>ls = new HashSet<>();
            Set<_annotation._element>rs = new HashSet<>();
            Set<_annotation._element>both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            both.addAll(ls);
            both.retainAll(rs);
            
            ls.removeAll(both);
            ls.forEach(f -> {
                _annotation._element cc = sameName( f, rs );
                if( cc != null ){
                    rs.remove( cc );
                    dt.add(path.in(_java.Component.ELEMENT, f.getName()), f, cc);
                } else{
                    dt.add(path.in( _java.Component.ELEMENT,f.getName()), f, null);
                }
            });
            
            rs.forEach(f -> {
                dt.add(path.in(_java.Component.ELEMENT, f.getName()), null,f);                
            });
            return dt;
        }        

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, List<_element> left, List<_element> right) {
            Set<_annotation._element>ls = new HashSet<>();
            Set<_annotation._element>rs = new HashSet<>();
            Set<_annotation._element>both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            both.addAll(ls);
            both.retainAll(rs);
            
            ls.removeAll(both);
            ls.forEach(f -> {
                _annotation._element cc = sameName( f, rs );
                if( cc != null ){
                    rs.remove( cc );
                    INSPECT_ANNOTATION_ELEMENT.diff(path.in(_java.Component.ELEMENT, f.getName()), dt, leftRoot, rightRoot, f, cc);
                } else{
                    dt.node( new remove_element( path.in(Component.ELEMENT, f.getName()), (_annotation)leftRoot,(_annotation)rightRoot, f));
                }
            });
            
            rs.forEach(f -> {
                dt.node( new add_element( path.in(Component.ELEMENT, f.getName()), (_annotation)leftRoot,(_annotation)rightRoot, f));           
            });
            return (_dif)dt;
        }
        
        public static class add_element implements _delta<_annotation>, _add<_element>{

            public _path path;
            public _annotation leftRoot;
            public _annotation rightRoot;
            public _element toAdd;
            
            public add_element( _path path, _annotation leftRoot, _annotation rightRoot, _element toAdd ){
                this.path = path;
                this.leftRoot = leftRoot;
                this.rightRoot = rightRoot;
                this.toAdd = toAdd;
            }
            
            @Override
            public _annotation leftRoot() {
                return leftRoot;
            }

            @Override
            public _annotation rightRoot() {
                return rightRoot;
            }

            @Override
            public void keepLeft() {
                leftRoot.removeElement(toAdd);
                rightRoot.removeElement(toAdd);
            }

            @Override
            public void keepRight() {
                leftRoot.removeElement(toAdd);
                rightRoot.removeElement(toAdd);
                leftRoot.element(toAdd);
                rightRoot.element(toAdd);
            }

            @Override
            public _path path() {
                return path;
            }

            @Override
            public _element added() {
                return toAdd;
            }
            
            @Override
            public String toString(){
                return "   + "+path;
            }            
        }
        
        public static class remove_element implements _delta<_annotation>, _remove<_element>{

            public _path path;
            public _annotation leftRoot;
            public _annotation rightRoot;
            public _element toRemove;
            
            public remove_element( _path path, _annotation leftRoot, _annotation rightRoot, _element toRemove ){
                this.path = path;
                this.leftRoot = leftRoot;
                this.rightRoot = rightRoot;
                this.toRemove = _element.of( toRemove.toString());
            }
            
            @Override
            public _annotation leftRoot() {
                return leftRoot;
            }

            @Override
            public _annotation rightRoot() {
                return rightRoot;
            }

            @Override
            public void keepLeft() {
                leftRoot.removeElement(toRemove);
                rightRoot.removeElement(toRemove);
            }

            @Override
            public void keepRight() {
                leftRoot.removeElement(toRemove);
                rightRoot.removeElement(toRemove);
                leftRoot.element(toRemove);
                rightRoot.element(toRemove);
            }

            @Override
            public _path path() {
                return path;
            }

            @Override
            public _element removed() {
                return toRemove;
            }
            
            @Override
            public String toString(){
                return "   - "+path;
            }            
        }
    }
    
    public static _annotationElementInspect INSPECT_ANNOTATION_ELEMENT = new _annotationElementInspect();
    
    public static class _annotationElementInspect implements _inspect<_annotation._element>, 
            _differ<_annotation._element,_node>{
        

        @Override
        public boolean equivalent(_annotation._element left, _annotation._element right) {
            return Objects.equals(left,right);
        }

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, _element left, _element right) {
            _javadoc.INSPECT_JAVADOC.diff(path, dt, left, right, left.getJavadoc(), right.getJavadoc());
            _anno.INSPECT_ANNOS.diff(path, dt, left, right, left.getAnnos(), right.getAnnos() );
            _typeRef.INSPECT_TYPE_REF.diff(path, dt, left, right, left.getType(), right.getType());            
            _java.INSPECT_NAME.diff(path, dt, left, right, left.getName(), right.getName());            
            INSPECT_DEFAULT_VALUE.diff(path, dt, left, right, left.getDefaultValue(), right.getDefaultValue());            
            return (_dif)dt;            
        }
        
        @Override
        public _inspect._diff diff( _java._inspector _ins, _path path, _inspect._diff dt, _annotation._element left, _annotation._element right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                return dt.add( path.in( _java.Component.ELEMENT, right.getName()), null, right);
                
            }
            if( right == null){
                return dt.add( path.in( _java.Component.ELEMENT, left.getName()), left, null);                
            }
            _ins.INSPECT_NAME.diff(_ins, path, dt, left.getName(), right.getName());
            _ins.INSPECT_TYPE_REF.diff(_ins,path, dt, left.getType(), right.getType());
            _ins.INSPECT_DEFAULT.diff(_ins,path, dt, left.getDefaultValue(), right.getDefaultValue());
            _ins.INSPECT_JAVADOC.diff(_ins,path, dt, left.getJavadoc(), right.getJavadoc());
            _ins.INSPECT_ANNOS.diff(_ins,path, dt, left.getAnnos(), right.getAnnos());            
            return dt;
        }        
    }
    
    public static final inspectDefaultValue INSPECT_DEFAULT_VALUE = new inspectDefaultValue();
    
    public static class inspectDefaultValue implements _differ<Expression,_element>{

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, Expression left, Expression right) {
            if( !Objects.equals(left, right ) ){
                dt.node(new _changeDefault( path.in(Component.DEFAULT), (_element)leftRoot, (_element)rightRoot) );
            }
            return (_dif)dt;
        }        
    }
    
    /**
     * Both signifies a delta and provides a means to 
     * commit (via right()) 
     * or rollback( via left())
     */
    public static class _changeDefault 
            implements _differ._delta, _differ._change<Expression>{
        _path path;
        _element left;
        _element right;
        Expression leftExpression;
        Expression rightExpression;
        
        public _changeDefault(_path _p, _element left, _element right ){
            this.path = _p;
            this.left = left;
            if( left.hasDefaultValue() ){
                this.leftExpression = left.getDefaultValue().clone();
            }
            this.right = right;
            if( right.hasDefaultValue()){
                this.rightExpression = right.getDefaultValue().clone();            
            }
        }
        
        @Override
        public void keepLeft(){
            left.setDefaultValue(leftExpression);
            right.setDefaultValue(leftExpression);
        }
        
        @Override
        public void keepRight(){
            left.setDefaultValue(rightExpression);
            right.setDefaultValue(rightExpression);
        }
        
        @Override
        public Expression left(){
            return leftExpression;
        }
        
        @Override
        public Expression right(){
            return rightExpression;
        }
        
        @Override
        public _model leftRoot() {
            return left;
        }

        @Override
        public _model rightRoot() {
            return right;
        }

        @Override
        public _path path() {
            return path;
        }
    }
    
    public static final _java.ExpressionInspect INSPECT_DEFAULT = new _java.ExpressionInspect(_java.Component.DEFAULT);
}
