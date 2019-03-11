package draft.java;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import draft.DraftException;
import draft.java._anno.*;
import draft.java._inspect._diff;
import draft.java._java.Component;
import draft.java._java._path;

import static draft.java._java.Component.CONSTANT;
import draft.java.io._in;
import draft.java.macro._macro;

import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Logical Mutable Model of the source code representing a Java enum.<BR>
 *
 * Implemented as a "Logical Facade" on top of an AST
 * ({@link EnumDeclaration}) for logical manipulation
 *
 * @author Eric
 */
public final class _enum implements _type<EnumDeclaration, _enum>,_method._hasMethods<_enum>,
        _constructor._hasConstructors<_enum, EnumDeclaration>, _staticBlock._hasStaticBlock<_enum>,
        _type._hasImplements<_enum>{

    public static _enum of( Class<? extends Enum> clazz ){
        Node n = Ast.type( clazz );
        if( n instanceof CompilationUnit ){
            return _macro.to(clazz, of( (CompilationUnit)n));
        }
        _enum _e = of( (EnumDeclaration)n);
        Set<Class> importClasses = _type.inferImportsFrom(clazz);
        _e.imports(importClasses.toArray(new Class[0]));
        return _macro.to(clazz, _e);        
    }

    public static _enum of( String...classDef ){
        if( classDef.length == 1){
            String[] strs = classDef[0].split(" ");
            if( strs.length == 1 ){
                //definately shortcut classes
                String shortcutClass = strs[0];
                String packageName = null;
                int lastDotIndex = shortcutClass.lastIndexOf('.');
                if( lastDotIndex >0 ){
                    packageName = shortcutClass.substring(0, lastDotIndex );
                    shortcutClass = shortcutClass.substring(lastDotIndex + 1);
                    if(!shortcutClass.endsWith("}")){
                        shortcutClass = shortcutClass + "{}";
                    }
                    return of( Ast.compilationUnit( "package "+packageName+";"+System.lineSeparator()+
                            "public enum "+shortcutClass));
                }
                if(!shortcutClass.endsWith("}")){
                    shortcutClass = shortcutClass + "{}";
                }
                return of( Ast.compilationUnit("public enum "+shortcutClass));
            }
        }
        return of( Ast.compilationUnit( classDef ));
    }

    public static _enum of( CompilationUnit cu ){
        if( cu.getPrimaryTypeName().isPresent() ){
            return of(cu.getEnumByName( cu.getPrimaryTypeName().get() ).get() );
        }
        NodeList<TypeDeclaration<?>> tds = cu.getTypes();
        if( tds.size() == 1 ){
            return of( (EnumDeclaration)tds.get(0) );
        }
        throw new DraftException("Unable to locate EnumDeclaration in "+ cu);
    }

    public static _enum of( EnumDeclaration astClass ){
        return new _enum( astClass );
    }

    public static _enum of(InputStream is){
        return of( StaticJavaParser.parse(is) );
    }

    public static _enum of( _in in ){
        return of( in.getInputStream());
    }

    public static _enum of(String signature, Object anonymousBody, Function<_type,_type>... typeFns ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        _enum _e = _enum.of(signature);
        ObjectCreationExpr oce = Expr.anonymousObject( ste );
        if( oce.getAnonymousClassBody().isPresent()) {
            NodeList<BodyDeclaration<?>> bds = oce.getAnonymousClassBody().get();
            for(int i=0; i<bds.size(); i++) {
                _e.ast().addMember(bds.get(i));
            }
        }
        Set<Class> importClasses = _type.inferImportsFrom(anonymousBody);
        _e.imports(importClasses.toArray(new Class[0]));
        
        _e = _macro.to(anonymousBody.getClass(), _e);
        for(int i=0;i<typeFns.length; i++){
            _e = (_enum)typeFns[i].apply(_e);
        }
        return _e;
    }

    public _enum( EnumDeclaration astClass ){
        this.astEnum = astClass;
    }

    /**
     * the AST storing the state of the _class
     * the _class is simply a facade into the state astClass
     *
     * the _class facade is a "Logical View" of the _class state stored in the
     * AST and can interpret or manipulate the AST without:
     * having to deal with syntax issues
     */
    private final EnumDeclaration astEnum;

    @Override
    public EnumDeclaration ast(){
        return this.astEnum;
    }
    
    @Override
    public boolean isTopClass(){
        return astEnum.isTopLevelType();
    }

    @Override
    public CompilationUnit findCompilationUnit(){
        //it might be a member class
        if( this.astEnum.findCompilationUnit().isPresent()){
            return this.astEnum.findCompilationUnit().get();
        }
        return null; //its an orphan
    }

    @Override
    public _annos getAnnos() {
        return _annos.of(this.astEnum );
    }
   
    @Override
    public List<_method> listMethods() {
        List<_method> _ms = new ArrayList<>();
        astEnum.getMethods().forEach( m-> _ms.add(_method.of( m ) ) );
        return _ms;
    }

    @Override
    public _enum method( MethodDeclaration method ) {
        astEnum.addMember( method );
        return this;
    }

    @Override
    public List<_constructor> listConstructors() {
        List<_constructor> _cs = new ArrayList<>();
        astEnum.getConstructors().forEach( c-> _cs.add( _constructor.of(c) ));
        return _cs;
    }

    @Override
    public _constructor getConstructor(int index){
        return _constructor.of(astEnum.getConstructors().get( index ));
    }

    @Override
    public _enum constructor( ConstructorDeclaration constructor ) {
        constructor.setName(this.getName()); //set the constructor NAME
        constructor.setPrivate(true);
        constructor.setPublic(false);
        constructor.setProtected(false);
        this.astEnum.addMember( constructor );
        return this;
    }

    public _enum constants( String...onePerConstant ){
        Arrays.stream(onePerConstant).forEach( c-> constant(c) );
        return this;
    }

    public List<_constant> listConstants() {
        List<_constant> _cs = new ArrayList<>();
        astEnum.getEntries().forEach( c-> _cs.add( _constant.of(c) ));
        return _cs;
    }

    public List<_constant> listConstants( Predicate<_constant> constantMatchFn ){
        return listConstants().stream().filter( constantMatchFn ).collect( Collectors.toList());
    }

    public _enum forConstants( Consumer<_constant> constantConsumer ){
        listConstants().forEach( constantConsumer );
        return this;
    }

    public _enum forConstants( Predicate<_constant>constantMatchFn, Consumer<_constant> constantConsumer ){
        listConstants( constantMatchFn).forEach( constantConsumer );
        return this;
    }

    public _constant getConstant(String name){
        Optional<EnumConstantDeclaration> ed =
                astEnum.getEntries().stream().filter( e-> e.getNameAsString().equals( name ) ).findFirst();
        if( ed.isPresent()){
            return _constant.of(ed.get());
        }
        return null;
    }

    @Override
    public _enum field( VariableDeclarator field ) {
        if(! field.getParentNode().isPresent()){
            throw new DraftException("cannot add Var without parent FieldDeclaration");
        }
        FieldDeclaration fd = (FieldDeclaration)field.getParentNode().get();
        //we already added it to the parent
        if( this.astEnum.getFields().contains( fd ) ){
            if( !fd.containsWithin( field ) ){
                fd.addVariable( field );
            }
            return this;
        }
        this.astEnum.addMember( fd );
        return this;
    }

    public _enum constant( _constant _c ){
        this.astEnum.addEntry( _c.ast() );
        return this;
    }
    
    public _enum constant ( String...constantDecl ) {
        return constant( Ast.constant( constantDecl ));
    }

    /**
     * Build a constant with the signature and add an anonymous BODY
     * (And process any Annotation Macros that are within the anonymous BODY)
     * <PRE>
     *  _enum.of("E", new Object() { @_final int i; }, _autoConstructor.$)
     *         .constant("G(2)", new Object(){
     *                     @_final int ff =100;
     *                     public String toString(){
     *                         return ff+"";
     *                     }
     *                 });
     *  //will create
     *  public enum E{
     *      G(2){
     *          final int ff = 100;
     *
     *          public String toString(){
     *              return ff+"";
     *          }
     *      }
     *  }
     * </PRE>
     * @param signature
     * @param anonymousBody
     * @return
     */
    public _enum constant (String signature, Object anonymousBody ){
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        ObjectCreationExpr oce = Expr.anonymousObject(ste);
        _constant _ct = _constant.of( Ast.constant(signature));
        if( oce.getAnonymousClassBody().isPresent()){
            // here, I'm putting the BODY into a temp _class, so that I can apply
            // annotation macros to it
            _class _c = _class.of("C");
            NodeList<BodyDeclaration<?>> bds = oce.getAnonymousClassBody().get();
            for(int i=0; i<bds.size();i++){
                _c.ast().addMember(bds.get(i));
            }
            //apply macros to the constant BODY (here stored in a class)
            _c = _macro.to(anonymousBody.getClass(), _c);
            //the potentially modified BODY members are added
            _ct.ast().setClassBody(_c.ast().getMembers());
            //_ct.astConstant.setClassBody( oce.getAnonymousClassBody().get());
        }
        return constant(_ct.ast());
    }

    public _enum constant ( EnumConstantDeclaration constant ) {
        this.astEnum.addEntry( constant );
        return this;
    }

    @Override
    public String toString(){
        if( this.isTopClass() ){
            return this.astEnum.findCompilationUnit().get().toString();
        }
        return this.astEnum.toString();
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
        final _enum other = (_enum)obj;

        if( this.astEnum == other.astEnum ){
            return true; //two _enum instances pointing to the same EnumDeclaration instance
        }
        if( !Objects.equals( this.getPackage(), other.getPackage() ) ) {
            return false;
        }
        if( !Objects.equals( this.getAnnos(), other.getAnnos() ) ) {
            return false;
        }
        if( !Objects.equals( this.getJavadoc(), other.getJavadoc() ) ) {
            return false;
        }
        if( !Ast.modifiersEqual(astEnum, other.astEnum)){
            return false;
        }
        if( !Objects.equals( this.getName(), other.getName() ) ) {
            return false;
        }
        Set<_staticBlock>tsb = new HashSet<>();
        Set<_staticBlock>osb = new HashSet<>();
        tsb.addAll(listStaticBlocks());
        osb.addAll(other.listStaticBlocks());
        if( !Objects.equals( tsb, osb ) ) {
            return false;
        }

        if( !Ast.typesEqual( this.listImplements(), other.listImplements())){
            return false;
        }

        if( ! Ast.importsEqual( astEnum, other.astEnum )){
            return false;
        }
        Set<_constant> tc = new HashSet<>();
        Set<_constant> oc = new HashSet<>();
        tc.addAll( this.listConstants() );
        oc.addAll( other.listConstants() );

        if( !Objects.equals( tc, oc ) ) {
            return false;
        }

        Set<_constructor> tct = new HashSet<>();
        Set<_constructor> oct = new HashSet<>();
        tct.addAll( this.listConstructors() );
        oct.addAll( other.listConstructors() );

        if( !Objects.equals( tct, oct ) ) {
            return false;
        }

        Set<_field> tf = new HashSet<>();
        Set<_field> of = new HashSet<>();
        tf.addAll( this.listFields() );
        of.addAll( other.listFields() );

        if( !Objects.equals( tf, of ) ) {
            return false;
        }

        Set<_method> tm = new HashSet<>();
        Set<_method> om = new HashSet<>();
        tm.addAll( this.listMethods() );
        om.addAll( other.listMethods() );

        if( !Objects.equals( tm, om ) ) {
            return false;
        }

        Set<_type> tn = new HashSet<>();
        Set<_type> on = new HashSet<>();
        tn.addAll( this.listNests() );
        on.addAll( other.listNests() );

        if( !Objects.equals( tn, on ) ) {
            return false;
        }
        return true;
    }

    @Override
    public List<_member> listMembers(){
        List<_member> _mems = new ArrayList<>();
        forFields( f-> _mems.add( f));
        forMethods(m -> _mems.add(m));
        forConstructors(c -> _mems.add(c));
        forConstants(c-> _mems.add(c));
        forNests(n -> _mems.add(n));
        return _mems;
    }

    @Override
    public _enum forMembers( Predicate<_member>_memberMatchFn, Consumer<_member> _memberActionFn){
        listMembers(_memberMatchFn).forEach(m -> _memberActionFn.accept(m) );
        return this;
    }

    @Override
    public _enum removeImplements( ClassOrInterfaceType toRemove ){
        this.astEnum.getImplementedTypes().remove( toRemove );
        return this;
    }

    @Override
    public _enum removeImplements( Class toRemove ){
        this.astEnum.getImplementedTypes().removeIf( im -> im.getNameAsString().equals( toRemove.getSimpleName() ) ||
                im.getNameAsString().equals(toRemove.getCanonicalName()) );
        return this;
    }

    public _enum removeConstant( _constant _c ){
        if( listConstants().contains(_c) ){
            _c.ast().removeForced();
        }
        return this;
    }

    public _enum removeConstant( String name ){
        _constant c = getConstant(name);
        if( c != null ){
            removeConstant( c );
        }
        return this;
    }
    
    /**
     * Remove all constants that match the constantMatchFn
     * @param _constantMatchFn
     * @return  the modified _enum
     */
    public _enum removeConstants( Predicate<_constant> _constantMatchFn ){
        listConstants(_constantMatchFn).forEach(c -> removeConstant(c) );
        return this;
    }

    @Override
    public Map<_java.Component, Object> componentsMap( ) {
        Map<_java.Component, Object> parts = new HashMap<>();
        parts.put( _java.Component.PACKAGE_NAME, this.getPackage() );
        parts.put( _java.Component.IMPORTS, this.listImports() );
        parts.put( _java.Component.ANNOS, this.listAnnos() );
        parts.put( _java.Component.IMPLEMENTS, this.listImplements() );
        parts.put( _java.Component.JAVADOC, this.getJavadoc() );
        parts.put( _java.Component.CONSTANTS, this.listConstants());
        parts.put( _java.Component.STATIC_BLOCKS, this.listStaticBlocks());
        parts.put( _java.Component.NAME, this.getName() );
        parts.put( _java.Component.MODIFIERS, this.getModifiers() );
        parts.put( _java.Component.CONSTRUCTORS, this.listConstructors() );
        parts.put( _java.Component.METHODS, this.listMethods() );
        parts.put( _java.Component.FIELDS, this.listFields() );
        parts.put( _java.Component.NESTS, this.listNests() );
        return parts;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        Set<ImportDeclaration> ti = new HashSet<>();
        ti.addAll( this.listImports() );

        Set<_constant> tc = new HashSet<>();
        tc.addAll( this.listConstants() );

        Set<_constructor> tct = new HashSet<>();
        tct.addAll( this.listConstructors() );

        Set<_field> tf = new HashSet<>();
        tf.addAll( this.listFields() );

        Set<_method> tm = new HashSet<>();
        tm.addAll( this.listMethods() );

        Set<_type> tn = new HashSet<>();
        tn.addAll( this.listNests() );

        Set<_staticBlock> sbs = new HashSet<>();
        sbs.addAll( this.listStaticBlocks() );

        hash = 53 * hash + Objects.hash( this.getPackage(),
                Ast.annotationsHash( astEnum),
                this.getJavadoc(), 
                this.getEffectiveModifiers(),
                this.getName(), 
                sbs,
                Ast.importsHash( astEnum ),
                Ast.typesHashCode( astEnum.getImplementedTypes()),
                tc, tct, tf, tm, tn);

        return hash;
    }

    /**
     * individual constant within an _enum
     * <PRE>
     * //A, and B are CONSTANTS of _enum E
     * enum E{
     *    A(),
     *    B('a');
     * }
     * </PRE>
     */
    public static final class _constant implements _javadoc._hasJavadoc<_constant>,
            _anno._hasAnnos<_constant>,_method._hasMethods<_constant>, _field._hasFields<_constant>,
            _member<EnumConstantDeclaration, _constant> {

        public static _constant of( String... ecd ){
            return of(Ast.constant(ecd));
        }
        
        public static _constant of( EnumConstantDeclaration ecd ){
            return new _constant( ecd);
        }

        public _constant( EnumConstantDeclaration ecd ){
            this.astConstant = ecd;
        }

        public final EnumConstantDeclaration astConstant;

        @Override
        public EnumConstantDeclaration ast(){
            return astConstant;
        }        
        
        public boolean hasArguments(){
            return this.astConstant.getArguments().size() > 0;
        }

        @Override
        public _constant name( String name ){
            this.astConstant.setName( name );
            return this;
        }

        public _constant addArgument( int i){
            return addArgument( Expr.of(i) );
        }
        
        public _constant addArgument( boolean b){
            return addArgument( Expr.of(b) );
        }
        
        public _constant addArgument( float f){
            return addArgument( Expr.of(f) );
        }
        
        public _constant addArgument( long l){
            return addArgument( Expr.of(l) );
        }
        
        public _constant addArgument( double d){
            return addArgument( Expr.of(d) );
        }
        
        public _constant addArgument( char c){
            return addArgument( Expr.of(c) );
        }
        
        public _constant addArgument( Expression e ){
            this.astConstant.addArgument( e );
            return this;
        }

        public _constant addArgument( String str ){
            this.astConstant.addArgument( str );
            return this;
        }

        public _constant clearArguments(){
            this.astConstant.getArguments().clear();
            return this;
        }

        public _constant setArgument( int index, Expression e){
            this.astConstant.getArguments().set( index, e );
            return this;
        }
        
        public _constant setArguments( NodeList<Expression> arguments ){
            this.astConstant.setArguments(arguments);
            return this;
        }
        
        public _constant setArguments( List<Expression> arguments ){
            NodeList<Expression> nles = new NodeList<>();
            nles.addAll(arguments);
            this.astConstant.setArguments(nles);
            return this;
        }
        
        public _constant setArgument( int index, boolean b){
            return setArgument(index, Expr.of(b));
        }
        
        public _constant setArgument( int index, int i){
            return setArgument(index, Expr.of(i));
        }
        
        public _constant setArgument( int index, char c){
            return setArgument(index, Expr.of(c));
        }

        public _constant setArgument( int index, float f){
            return setArgument(index, Expr.of(f));
        }
        
        public _constant setArgument( int index, long l){
            return setArgument(index, Expr.of(l));
        }
        
        public _constant setArgument(int index, double d){
            return setArgument(index, Expr.of(d));
        }
        
        public _constant forArguments( Consumer<Expression> expressionAction ){
            this.listArguments().forEach(expressionAction);
            return this;
        }
        
        public _constant forArguments( Predicate<Expression> expressionMatchFn, Consumer<Expression> expressionAction ){
            this.listArguments(expressionMatchFn).forEach(expressionAction);
            return this;
        }
        
        public _constant removeArgument( int index ){
            this.astConstant.getArguments().remove(index);
            return this;
        }
        
        public _constant removeArguments( Predicate<Expression> argumentMatchFn ){
            listArguments(argumentMatchFn).forEach(e -> e.removeForced() );
            return this;
        }
        
        public List<Expression> listArguments(){
            return this.astConstant.getArguments();
        }
        
        public List<Expression>listArguments(Predicate<Expression> expressionMatchFn){
            return this.astConstant.getArguments().stream().filter( expressionMatchFn ).collect(Collectors.toList() );
        }

        public Expression getArgument( int index ){
            return listArguments().get( index );
        }

        @Override
        public _annos getAnnos() {
            return _annos.of(this.astConstant );
        }

        @Override
        public List<_method> listMethods() {
            List<_method> ms = new ArrayList<>();
            this.astConstant.getClassBody().stream().filter(b -> b instanceof MethodDeclaration).forEach( m -> ms.add(_method.of( (MethodDeclaration)m )) );
            return ms;
        }

        @Override
        public _constant method( MethodDeclaration method ) {
            this.astConstant.getClassBody().add( method );
            return this;
        }

        @Override
        public _constant field( VariableDeclarator field ) {
            if(! field.getParentNode().isPresent()){
                throw new DraftException("cannot add Var without parent FieldDeclaration");
            }
            FieldDeclaration fd = (FieldDeclaration)field.getParentNode().get();
            //we already added it to the parent
            if( this.astConstant.getClassBody().contains( fd ) ){
                if( !fd.containsWithin( field ) ){
                    fd.addVariable( field );
                }
                return this;
            }
            this.astConstant.getClassBody().add( fd );
            return this;
        }

        @Override
        public List<_field> listFields() {
            List<_field> ms = new ArrayList<>();
            this.astConstant.getClassBody().stream().filter(b -> b instanceof FieldDeclaration)
                    .forEach( f->{
                        if( ((FieldDeclaration)f).getVariables().size() == 1 ){
                            ms.add(_field.of( ((FieldDeclaration)f).getVariable( 0 ) ) );
                        } else{
                            for(int i=0;i<((FieldDeclaration)f).getVariables().size();i++){
                                ms.add(_field.of( ((FieldDeclaration)f).getVariable( i ) ) );
                            }
                        }
                    });
            return ms;
        }

        @Override
        public String getName(){
            return this.astConstant.getNameAsString();
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
            final _constant other = (_constant)obj;
            if( this.astConstant == other.astConstant){
                return true; //two _constant pointing to the same AstEnumDeclaration
            }
            if( !Objects.equals( this.getAnnos(), other.getAnnos() ) ) {
                return false;
            }
            if( !Objects.equals( this.getJavadoc(), other.getJavadoc() ) ) {
                return false;
            }
            if( !Objects.equals( this.getName(), other.getName() ) ) {
                return false;
            }
            if( !Objects.equals(this.listArguments(), other.listArguments() ) ) {
                return false;
            }
            Set<_method> tms = new HashSet<>();
            Set<_method> oms = new HashSet<>();
            tms.addAll( this.listMethods());
            oms.addAll( other.listMethods());

            if( !Objects.equals( tms, oms ) ) {
                return false;
            }
            Set<_field> tfs = new HashSet<>();
            Set<_field> ofs = new HashSet<>();
            tfs.addAll( this.listFields());
            ofs.addAll( other.listFields());
            if( !Objects.equals( tfs, ofs ) ) {
                return false;
            }
            return true;
        }

        @Override
        public Map<_java.Component, Object> componentsMap( ) {
            Map<_java.Component, Object> parts = new HashMap<>();
            parts.put( _java.Component.ANNOS, this.listAnnos() );
            parts.put( _java.Component.JAVADOC, this.getJavadoc() );
            parts.put( _java.Component.NAME, this.getName());
            parts.put( _java.Component.ARGUMENTS, this.listArguments());
            parts.put( _java.Component.NAME, this.getName() );
            parts.put( _java.Component.METHODS, this.listMethods() );
            parts.put( _java.Component.FIELDS, this.listFields() );
            return parts;
        }

        @Override
        public String toString(){
            return this.astConstant.toString();
        }
        
        @Override
        public String toString( PrettyPrinterConfiguration ppv ){
            return this.astConstant.toString(ppv);
        }
        
        @Override
        public int hashCode() {
            int hash = 7;
            Set<_method> tms = new HashSet<>();
            tms.addAll( this.listMethods());

            Set<_field> tfs = new HashSet<>();
            tfs.addAll( this.listFields());

            hash = 13 * hash + Objects.hash( tms, tfs, getAnnos(), getJavadoc(),
                    getName(), listArguments() );
            return hash;
        }
        
        public _diff diff(_constant right ){
            return INSPECT_ENUM_CONSTANT.diff(this, right);
        }
        
        public static _diff diff( _constant left, _constant right){
            return INSPECT_ENUM_CONSTANT.diff(left, right);
        }
        
        public static _diff diff( List<_constant> left, List<_constant> right){
            return INSPECT_ENUM_CONSTANTS.diff(left, right);
        }
    }
    
    public _diff diff( _enum right){
        return INSPECT_ENUM.diff(this, right);
    }
    
    public static _diff diff( _enum left, _enum right ){
        return INSPECT_ENUM.diff(left, right);
    }
    
    public static _enumConstantInspect INSPECT_ENUM_CONSTANT = new _enumConstantInspect();
    
    public static class _enumConstantInspect implements _inspect<_enum._constant>,
            _differ<_enum._constant, _enum> {        
        
        @Override
        public boolean equivalent(_enum._constant left, _enum._constant right) {
            return Objects.equals(left,right);
        }
        
        @Override
        public _inspect._diff diff( _java._inspector _ins, _path path, _inspect._diff dt, _enum._constant left, _enum._constant right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                dt.add( path.in(_java.Component.CONSTANT, right.getName()), null, right);
                return dt;
            }
            if( right == null){
                dt.add( path.in(_java.Component.CONSTANT, left.getName()), left, null );
                return dt;
            }
            _ins.INSPECT_ANNOS.diff(_ins, path, dt, left.getAnnos(), right.getAnnos());
            _ins.INSPECT_JAVADOC.diff(_ins, path, dt, left.getJavadoc(), right.getJavadoc());
            _ins.INSPECT_NAME.diff(_ins, path, dt, left.getName(), right.getName());
            _ins.INSPECT_ARGUMENTS.diff(_ins, path, dt, left.listArguments(), right.listArguments());
            _ins.INSPECT_METHODS.diff(_ins, path, dt, left.listMethods(), right.listMethods());
            _ins.INSPECT_FIELDS.diff(_ins, path, dt, left.listFields(), right.listFields());            
            return dt;
        }        

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, _constant left, _constant right) {
            _path _p = path.in(Component.CONSTANT, left.getName());
            _anno.INSPECT_ANNOS.diff( _p, dt, left, right, left.getAnnos(), right.getAnnos());
            _javadoc.INSPECT_JAVADOC.diff(_p, dt, left, right, left.getJavadoc(), right.getJavadoc());
            if(!Objects.equals( left.getName(), right.getName())){
                dt.node(new _differ._changeName( _p, left, right) );
            }
            INSPECT_ARGUMENTS.diff(_p, dt, left, right, left.listArguments(), right.listArguments());
            _method.INSPECT_METHODS.diff(_p, dt, left, right, left.listMethods(), right.listMethods());
            _field.INSPECT_FIELDS.diff(_p, dt, left, right, left.listFields(), right.listFields()); 
            return (_dif)dt;
        }
    }
    
    public static _enumConstantsInspect INSPECT_ENUM_CONSTANTS = new _enumConstantsInspect();
    
    public static class _enumConstantsInspect 
            implements _inspect<List<_enum._constant>>, 
                _differ<List<_enum._constant>, _node> {

        @Override
        public boolean equivalent( List<_enum._constant> left, List<_enum._constant> right) {
            Set<_enum._constant>ls = new HashSet<>();
            Set<_enum._constant>rs = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            return Objects.equals(ls, rs);
        }

        public _enum._constant sameName( _enum._constant target, Set<_enum._constant> source ){
            Optional<_enum._constant> ec = 
                    source.stream().filter(c-> c.getName().equals(target.getName())).findFirst();
            if( ec.isPresent()){
                return ec.get();
            }
            return null;
        }
        
        @Override
        public _inspect._diff diff( _java._inspector _ins, _path path, _inspect._diff dt, List<_enum._constant> left, List<_enum._constant> right) {
            Set<_enum._constant>ls = new HashSet<>();
            Set<_enum._constant>rs = new HashSet<>();
            Set<_enum._constant>both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            both.addAll(ls);
            both.retainAll(rs);
            
            ls.removeAll(both);
            ls.forEach(f -> {
                _enum._constant cc = sameName( f, rs );
                if( cc != null ){
                    rs.remove( cc );
                    dt.add(path.in(_java.Component.CONSTANT, f.getName()), f, cc);
                } else{
                    dt.add(path.in(_java.Component.CONSTANT, f.getName()), f, null);                    
                }
            });            
            rs.forEach(f -> {
                dt.add( path.in(_java.Component.CONSTANT, f.getName()), null, f);                                    
            });
            return dt;
        }        

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, List<_constant> left, List<_constant> right) {
            Set<_enum._constant>ls = new HashSet<>();
            Set<_enum._constant>rs = new HashSet<>();
            Set<_enum._constant>both = new HashSet<>();
            ls.addAll(left);
            rs.addAll(right);
            both.addAll(ls);
            both.retainAll(rs);
            
            ls.removeAll(both);
            rs.removeAll(both);
            ls.forEach(f -> {
                _enum._constant cc = sameName( f, rs );
                if( cc != null ){
                    rs.remove( cc );
                    INSPECT_ENUM_CONSTANT.diff(path, dt, leftRoot, rightRoot, f, cc);
                } else{
                    dt.node( new remove_enum_constant(path.in(CONSTANT, f.getName()), (_enum)leftRoot, (_enum)rightRoot, f) );                    
                }
            });            
            rs.forEach(f -> {
                dt.node( new add_enum_constant(path.in(CONSTANT, f.getName()), (_enum)leftRoot, (_enum)rightRoot, f) );                             
            });
            return (_dif)dt;
        }
        
        public static class add_enum_constant implements _delta<_enum>, _add<_enum._constant>{

            public _path path;
            public _enum leftRoot;
            public _enum rightRoot;
            public _enum._constant toAdd;
            
            public add_enum_constant(_path path, _enum leftRoot, _enum rightRoot, _constant added ){
                this.path = path;
                this.leftRoot = leftRoot;
                this.rightRoot = rightRoot;
                this.toAdd = _constant.of(added.toString());
            }
             @Override
            public _enum leftRoot() {
                return leftRoot;
            }

            @Override
            public _enum rightRoot() {
                return rightRoot;
            }

            @Override
            public void keepLeft() {
                leftRoot.remove(toAdd);
                rightRoot.remove(toAdd);               
            }

            @Override
            public void keepRight() {                                
                leftRoot.remove(toAdd);
                leftRoot.add(toAdd);
                
                rightRoot.remove(toAdd);
                rightRoot.add(toAdd); 
            }

            @Override
            public _path path() {
                return path;
            }

            @Override
            public _constant added() {
                return this.toAdd;
            }            
            
            @Override
            public String toString(){
                return "   + "+path;
            }
        }
        
        public static class remove_enum_constant implements _delta<_enum>, _remove<_enum._constant>{

            public _path path;
            public _enum leftRoot;
            public _enum rightRoot;
            public _enum._constant toRemove;
            
            public remove_enum_constant(_path path, _enum leftRoot, _enum rightRoot, _constant removed ){
                this.path = path;
                this.leftRoot = leftRoot;
                this.rightRoot = rightRoot;
                this.toRemove = _constant.of(removed.toString());
            }
            
            @Override
            public _enum leftRoot() {
                return leftRoot;
            }

            @Override
            public _enum rightRoot() {
                return rightRoot;
            }

            @Override
            public void keepLeft() {
                leftRoot.remove(toRemove);
                leftRoot.add(toRemove);
                
                rightRoot.remove(toRemove);
                rightRoot.add(toRemove);                
            }

            @Override
            public void keepRight() {                
                leftRoot.remove(toRemove);
                rightRoot.remove(toRemove);
            }

            @Override
            public _path path() {
                return path;
            }

            @Override
            public _constant removed() {
                return this.toRemove;
            }            
            
            @Override
            public String toString(){
                return "   - "+path;
            }
        }
    }
    
    public static _enumInspect INSPECT_ENUM = new _enumInspect();
    
    public static class _enumInspect implements _inspect<_enum>, _differ<_enum, _node> {

        @Override
        public boolean equivalent(_enum left, _enum right) {
            return Objects.equals(left,right);
        }

        @Override
        public _inspect._diff diff( _java._inspector _ins, _path path, _inspect._diff dt, _enum left, _enum right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                return dt.add( path.in(_java.Component.ENUM, right.getName()), null, right);                
            }
            if( right == null){
                return dt.add( path.in(_java.Component.ENUM, left.getName()), left, null );
            }
            _ins.INSPECT_PACKAGE_NAME.diff(_ins, path,dt, left.getPackage(), right.getPackage() );
            _ins.INSPECT_IMPORTS.diff(_ins, path,dt, left.listImports(), right.listImports() );
            _ins.INSPECT_ANNOS.diff(_ins, path, dt, left.getAnnos(), right.getAnnos());                               
            _ins.INSPECT_IMPLEMENTS.diff(_ins, path, dt, left.listImplements(), right.listImplements());  
            _ins.INSPECT_JAVADOC.diff(_ins, path, dt, left.getJavadoc(), right.getJavadoc());  
            _ins.INSPECT_STATIC_BLOCKS.diff(_ins, path, dt, left.listStaticBlocks(), right.listStaticBlocks());            
            _ins.INSPECT_NAME.diff(_ins, path, dt, left.getName(), right.getName());
            _ins.INSPECT_MODIFIERS.diff(_ins, path, dt, left.getModifiers(), right.getModifiers());
            _ins.INSPECT_CONSTRUCTORS.diff(_ins, path, dt, left.listConstructors(), right.listConstructors());
            _ins.INSPECT_METHODS.diff(_ins, path, dt, left.listMethods(), right.listMethods() );
            _ins.INSPECT_FIELDS.diff(_ins, path, dt, left.listFields(), right.listFields() );
            _ins.INSPECT_ENUM_CONSTANTS.diff(_ins, path, dt, left.listConstants(), right.listConstants());            
            _ins.INSPECT_NESTS.diff(_ins, path, dt, left.listNests(), right.listNests());  
            return dt;
        }

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, _enum left, _enum right) {
            _java.INSPECT_PACKAGE.diff(path, dt, leftRoot, rightRoot, left.getPackage(), right.getPackage() );
            _type.INSPECT_IMPORTS.diff(path, dt, leftRoot, rightRoot, left.listImports(), right.listImports() );
            _anno.INSPECT_ANNOS.diff(path, dt, leftRoot, rightRoot, left.getAnnos(), right.getAnnos() );
            
            _type.INSPECT_IMPLEMENTS.diff(path, dt, leftRoot, rightRoot,left.listImplements(), right.listImplements());  
            _javadoc.INSPECT_JAVADOC.diff(path, dt, leftRoot, rightRoot, left.getJavadoc(), right.getJavadoc());  
            _staticBlock.INSPECT_STATIC_BLOCKS.diff(path, dt, leftRoot, rightRoot,left.listStaticBlocks(), right.listStaticBlocks());  
            _java.INSPECT_NAME.diff(path, dt, leftRoot, rightRoot, left.getName(), right.getName());
            _modifiers.INSPECT_MODIFIERS.diff(path, dt, leftRoot, rightRoot, left.getEffectiveModifiers(), right.getEffectiveModifiers());
            _constructor.INSPECT_CONSTRUCTORS.diff(path, dt, leftRoot, rightRoot, left.listConstructors(), right.listConstructors());
            _method.INSPECT_METHODS.diff(path, dt, leftRoot, rightRoot, left.listMethods(), right.listMethods() );
            _field.INSPECT_FIELDS.diff(path, dt, leftRoot, rightRoot, left.listFields(), right.listFields() );
            INSPECT_ENUM_CONSTANTS.diff(path, dt, leftRoot, rightRoot, left.listConstants(), right.listConstants());            
            _type.INSPECT_NESTS.diff(path, dt, leftRoot, rightRoot, left.listNests(), right.listNests());
            
            return (_dif)dt;            
        }
    }
    
    public static final ArgsInspect INSPECT_ARGUMENTS = new ArgsInspect();
    
    public static class ArgsInspect implements _inspect<List<Expression>>, 
            _differ<List<Expression>, _node> {

        @Override
        public boolean equivalent(List<Expression> left, List<Expression> right) {
            return Objects.equals(left, right);
        }

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, List<Expression> left, List<Expression> right) {
            if( ! Objects.equals(left, right)){
                dt.node( new _changeArguments(path.in(Component.ARGUMENTS), (_constant)leftRoot, (_constant)rightRoot) );
            }
            return (_dif)dt;            
        }
        
        public static final _java.ExpressionInspect INSPECT_ARG = new _java.ExpressionInspect(Component.ARGUMENT);
        
        @Override
        public _inspect._diff diff( _java._inspector _ins, _path path, _inspect._diff dt, List<Expression> left, List<Expression> right) {
            
            if(left == null ){
                if(right == null){
                    return dt;
                }
                for(int i=0;i<right.size();i++){
                    dt.add( path.in(_java.Component.ARGUMENT, i+""), null, right);
                }
                return dt;
            }
            if( right == null ){
                for(int i=0;i<left.size();i++){
                    dt.add(path.in(_java.Component.ARGUMENT, i+""), left, null);
                }
                return dt;
            }
            int max = Math.max( left.size(), right.size() );
            for(int i=0;i<max;i++){
                Expression l = left.size() > i ? left.get(i) : null;
                Expression r = right.size() > i ? right.get(i) : null;
                _ins.INSPECT_ARGUMENT.diff(_ins, path.in(_java.Component.ARGUMENT,""+i), dt, l, r);
            }
            return dt;
        }

        public static class _changeArguments
                implements _differ._delta<_constant>, _differ._change<List<Expression>>{

            _path path; 
            _constant leftRoot;
            _constant rightRoot;
            NodeList<Expression> leftArguments;
            NodeList<Expression> rightArguments;
            
            public _changeArguments(_path path, _constant left, _constant right){
                this.path = path;
                this.leftRoot = left;
                this.rightRoot = right;
                
                //these are effectively clones
                leftArguments = new NodeList<>();
                rightArguments = new NodeList<>();
                leftArguments.addAll(leftRoot.astConstant.getArguments());
                rightArguments.addAll(rightRoot.astConstant.getArguments());                
            }
            
            @Override
            public _constant leftRoot() {
                return leftRoot;
            }

            @Override
            public _constant rightRoot() {
                return rightRoot;
            }

            @Override
            public _path path() {
                return path;
            }

            @Override
            public List<Expression> left() {
                return leftArguments;
            }
            
            @Override
            public List<Expression> right() {
                return rightArguments;
            }

            @Override
            public void keepRight() {
                this.leftRoot.setArguments(rightArguments);
                this.rightRoot.setArguments(rightArguments);                
            }

            @Override
            public void keepLeft() {
                this.leftRoot.setArguments(leftArguments);
                this.rightRoot.setArguments(leftArguments);
            }        
            
            @Override
            public String toString(){
                return "   ~ "+path;
            }
        }
    }
}
