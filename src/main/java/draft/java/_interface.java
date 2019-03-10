package draft.java;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.expr.ObjectCreationExpr;
import com.github.javaparser.ast.type.*;
import draft.DraftException;
import draft.java._anno.*;
import draft.java._inspect._diff;
import draft.java._java._path;
import draft.java.io._in;
import draft.java.macro._macro;

import java.io.InputStream;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 * Model of a Java interface.<BR>
 *
 * Implemented as a "Logical Facade" on top of an AST
 * ({@link ClassOrInterfaceDeclaration}) for logical manipulation.
 */
public final class _interface implements _type<ClassOrInterfaceDeclaration, _interface>,
        _method._hasMethods<_interface>, _typeParameter._hasTypeParameters<_interface>,
        _type._hasExtends<_interface>{

    public static _interface of( Class clazz ){
        Node n = Ast.type( clazz );
        if( n instanceof CompilationUnit ){
            //_interface _i = ;
            return _macro.to(clazz, of( (CompilationUnit)n));
        } 
        
        return _macro.to(clazz, of((ClassOrInterfaceDeclaration)n));        
    }

    /**
     * Build and return the interface based on the String definition passed in
     * this also handles "shortcut" declarations:
     * <PRE>
     *     _interface.of("I")
     *     // is:
     *     public interface I{}
     *
     *     _interface.of("aaaa.vvvv.I")
     *     // is:
     *     package aaaa.vvvv;
     *     public interface I{}
     *
     * </PRE>
     * @param interfaceDef String definition of the interface, (each element represents a line)
     * @return the _interface declaration
     */
    public static _interface of( String...interfaceDef ){
        //Handle shortcut interfaces (i.e. _interface.of("I"), _interface.of("aaaa.bbbb.I")
        if( interfaceDef.length == 1){
            String[] strs = interfaceDef[0].split(" ");
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
                            "public interface "+shortcutClass));
                }
                if(!shortcutClass.endsWith("}")){
                    shortcutClass = shortcutClass + "{}";
                }
                return of( Ast.compilationUnit("public interface "+shortcutClass));
            }
        }
        return of( Ast.compilationUnit( interfaceDef ));
    }

    public static _interface of( String signature, Object anonymousBody, _macro<_type>...typeMacros) {
        StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
        final _interface _i = of( signature );

        //interfaces to be extended
        if(anonymousBody.getClass().getInterfaces().length > 0){
            Arrays.stream(anonymousBody.getClass().getInterfaces()).forEach( i -> {
                _i.imports(i);
                _i.extend(i);
            });
        }

        //need to process these ANNOTATIONS
        //@_abstract
        //@_default
        //@_static
        ObjectCreationExpr oce = Expr.anonymousObject(ste);
        if( oce.getAnonymousClassBody().isPresent() ){
            oce.getAnonymousClassBody().get().forEach( e -> _i.astInterface.addMember(e) );
        }
        _macro.to(anonymousBody.getClass(), _i);

        //lastly... apply the passed in macros
        for(int i=0;i<typeMacros.length;i++){
            typeMacros[i].apply(_i);
        }
        return _i;
    }

    public static _interface of( CompilationUnit cu ){
        if( cu.getPrimaryTypeName().isPresent() ){
            return of( cu.getClassByName( cu.getPrimaryTypeName().get() ).get() );
        }
        NodeList<TypeDeclaration<?>> tds = cu.getTypes();
        if( tds.size() == 1 ){
            return of( (ClassOrInterfaceDeclaration)tds.get(0) );
        }
        throw new DraftException("Unable to locate primary TYPE in "+ cu);
    }

    public static _interface of( ClassOrInterfaceDeclaration astClass ){
        return new _interface( astClass );
    }


    public static _interface of(InputStream is){
        return of( StaticJavaParser.parse(is) );
    }

    public static _interface of( _in in ){
        return of( in.getInputStream());
    }

    public _interface( ClassOrInterfaceDeclaration astClass ){
        this.astInterface = astClass;
    }

    /**
     * the AST storing the state of the _class
     * the _class is simply a facade into the state astClass
     *
     * the _class facade is a "Logical View" of the _class state stored in the
     * AST and can interpret or manipulate the AST without:
     * having to deal with syntax issues
     */
    private final ClassOrInterfaceDeclaration astInterface;

    @Override
    public ClassOrInterfaceDeclaration ast(){
        return this.astInterface;
    }
    
    @Override
    public boolean isTopClass(){
        return ast().isTopLevelType();
    }

    @Override
    public CompilationUnit findCompilationUnit(){
        if( this.ast().isTopLevelType()){
            return ast().findCompilationUnit().get();
        }
        //it might be a member class
        if( this.astInterface.findCompilationUnit().isPresent()){
            return this.astInterface.findCompilationUnit().get();
        }
        return null; //its an orphan
    }

    @Override
    public boolean hasExtends(){
        return !this.astInterface.getExtendedTypes().isEmpty();
    }

    @Override
    public NodeList<ClassOrInterfaceType> listExtends(){
        return astInterface.getExtendedTypes();
    }


    @Override
    public _interface removeExtends( Class toRemove ){
        this.astInterface.getExtendedTypes().removeIf( im -> im.getNameAsString().equals( toRemove.getSimpleName() ) ||
                im.getNameAsString().equals(toRemove.getCanonicalName()) );
        return this;
    }

    @Override
    public _interface removeExtends( ClassOrInterfaceType toRemove ){
        this.astInterface.getExtendedTypes().remove( toRemove );
        return this;
    }

    @Override
    public _interface extend( ClassOrInterfaceType toExtend ){
        this.astInterface.addExtendedType( toExtend );
        return this;
    }

    @Override
    public _interface extend( Class toExtend ){
        this.astInterface.addExtendedType( toExtend );
        this.astInterface.tryAddImportToParentCompilationUnit(toExtend);
        return this;
    }

    @Override
    public _interface extend( String  toExtend ){
        this.astInterface.addExtendedType( toExtend);
        return this;
    }

    @Override
    public List<_method> listMethods() {
        List<_method> _ms = new ArrayList<>();
        astInterface.getMethods().forEach( m-> _ms.add(_method.of( m ) ) );
        return _ms;
    }

    @Override
    public _interface method( MethodDeclaration method ) {
        astInterface.addMember( method );
        return this;
    }

    @Override
    public _interface field( VariableDeclarator field ) {
        if(! field.getParentNode().isPresent()){
            throw new DraftException("cannot add Var without parent FieldDeclaration");
        }
        FieldDeclaration fd = (FieldDeclaration)field.getParentNode().get();
        //we already added it to the parent
        if( this.astInterface.getFields().contains( fd ) ){
            if( !fd.containsWithin( field ) ){
                fd.addVariable( field );
            }
            return this;
        }
        this.astInterface.addMember( fd );
        return this;
    }

    @Override
    public _annos getAnnos() {
        return _annos.of(this.astInterface);
    }

    @Override
    public boolean isExtends( ClassOrInterfaceType ct ){
        return this.astInterface.getExtendedTypes().contains( ct ) ||
                this.astInterface.getExtendedTypes().contains( (ClassOrInterfaceType)Ast.typeRef(ct.getNameAsString()) );
    }

    @Override
    public boolean isExtends( String str ){
        try{
            return isExtends( (ClassOrInterfaceType)Ast.typeRef( str ) );
        }catch( Exception e){}
        return false;
    }

    @Override
    public boolean isExtends( Class clazz ){
        try{
            return isExtends( (ClassOrInterfaceType)Ast.typeRef( clazz ) );
        }catch( Exception e){}
        return false;
    }

    @Override
    public String toString(){
        if( this.ast().isTopLevelType()){
            return this.ast().findCompilationUnit().get().toString();
        }
        return this.astInterface.toString();
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
        final _interface other = (_interface)obj;
        if( this.astInterface == other.astInterface ){
            return true; //two _interfaces pointing to the same InterfaceDeclaration
        }
        if( !Objects.equals( this.getPackage(), other.getPackage())){
            return false;
        }
        if( !Objects.equals( this.getAnnos(), other.getAnnos())){
            return false;
        }
        if( this.hasJavadoc() != other.hasJavadoc() ){
            return false;
        }
        if( this.hasJavadoc() && !Objects.equals( this.getJavadoc().getContent(), other.getJavadoc().getContent())){
            return false;
        }
        if( !Objects.equals( this.getModifiers(), other.getModifiers())){
            return false;
        }
        if( !Objects.equals( this.getTypeParameters(), other.getTypeParameters())){
            return false;
        }
        Set<_method> tm = new HashSet<>();
        Set<_method> om = new HashSet<>();
        tm.addAll(  this.listMethods());
        om.addAll(  other.listMethods());

        if( !Objects.equals( tm, om)){
            return false;
        }
        Set<_field> tf = new HashSet<>();
        Set<_field> of = new HashSet<>();
        tf.addAll(  this.listFields());
        of.addAll(  other.listFields());

        if( !Objects.equals( tf, of)){
            return false;
        }

        if( !Ast.typesEqual( astInterface.getExtendedTypes(), other.astInterface.getExtendedTypes() )){
            return false;
        }

        if( !Ast.importsEqual( astInterface, other.astInterface)){
            return false;
        }
        Set<_type> tn = new HashSet<>();
        Set<_type> on = new HashSet<>();
        tn.addAll( this.listNests() );
        on.addAll( this.listNests() );
        if( !Objects.equals( tn, on)){
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 7;

        //we want to have consistent order for METHODS,fileds, extends, imports
        //and NESTS, for the hash code so we put the
        Set<_method> tm = new HashSet<>();
        tm.addAll(  this.listMethods());

        Set<_field> tf = new HashSet<>();
        tf.addAll( this.listFields());

        Set<Integer> te = new HashSet<>();
        this.listExtends().forEach(e-> te.add( Ast.typeHash(e)));
        //Set<ClassOrInterfaceType> te = new HashSet<>();
        //te.addAll(this.listExtends() );

        Set<ImportDeclaration> tis = new HashSet<>();
        tis.addAll(  this.listImports() );

        Set<_type> nests = new HashSet<>();
        nests.addAll(  this.listNests() );

        hash = 53 * Objects.hash( this.getPackage(), Ast.annotationsHash(astInterface),
                this.getJavadoc(),this.getModifiers(), this.getTypeParameters(),
                tm, tf,
                //te, tis,
                Ast.importsHash(astInterface),
                Ast.typesHashCode(astInterface.getExtendedTypes()),
                nests);

        return hash;
    }

    @Override
    public List<_member> listMembers(){
        List<_member> _members = new ArrayList<>();
        forFields( f-> _members.add( f));
        forMethods(m -> _members.add(m));
        forNests(n -> _members.add(n));
        return _members;
    }

    @Override
    public _interface forMembers( Predicate<_member>_memberMatchFn, Consumer<_member> _memberActionFn){
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
        parts.put( _java.Component.EXTENDS, this.listExtends() );
        parts.put( _java.Component.NAME, this.getName() );

        parts.put( _java.Component.MODIFIERS, this.getModifiers() );
        parts.put( _java.Component.TYPE_PARAMETERS, this.getTypeParameters() );
        parts.put( _java.Component.FIELDS, this.listFields() );
        parts.put( _java.Component.METHODS, this.listMethods() );
        parts.put( _java.Component.NESTS, this.listNests() );
        return parts;
    }

    public boolean is( String...interfaceDeclaration ){
        try{
            return of(interfaceDeclaration).equals(this);
        }catch(Exception e){
            return false;
        }
    }

    public boolean is( ClassOrInterfaceDeclaration astI){
        try{
            return of(astI).equals(this);
        }catch(Exception e){
            return false;
        }
    }
    
    public _diff diff( _interface right ){
        return INSPECT_INTERFACE.diff(this, right);
    }
    
    public _diff diff( _interface left, _interface right ){
        return INSPECT_INTERFACE.diff(left, right);
    }
    
    public static _interfaceInspect INSPECT_INTERFACE = new _interfaceInspect();
    
    public static class _interfaceInspect implements _inspect<_interface>, 
            _differ<_interface, _node> {

        @Override
        public boolean equivalent(_interface left, _interface right) {
            return Objects.equals(left,right);
        }

        @Override
        public _inspect._diff diff( _java._inspector _ins, _path path, _inspect._diff dt, _interface left, _interface right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                return dt.add( path.in(_java.Component.INTERFACE, right.getName()), null, right);
            }
            if( right == null){
                return dt.add( path.in(_java.Component.INTERFACE, left.getName()), left, null);
            }
            _ins.INSPECT_PACKAGE_NAME.diff(_ins, path,dt, left.getPackage(), right.getPackage() );
            _ins.INSPECT_IMPORTS.diff(_ins, path,dt, left.listImports(), right.listImports() );
            _ins.INSPECT_ANNOS.diff(_ins, path, dt, left.getAnnos(), right.getAnnos());          
            _ins.INSPECT_EXTENDS.diff(_ins, path, dt, left.listExtends(), right.listExtends());          
            _ins.INSPECT_JAVADOC.diff(_ins, path, dt, left.getJavadoc(), right.getJavadoc());  
            _ins.INSPECT_TYPE_PARAMETERS.diff(_ins, path, dt, left.getTypeParameters(), right.getTypeParameters());  
            _ins.INSPECT_NAME.diff(_ins, path, dt, left.getName(), right.getName());
            _ins.INSPECT_MODIFIERS.diff(_ins, path, dt, left.getModifiers(), right.getModifiers());
            _ins.INSPECT_METHODS.diff(_ins, path, dt, left.listMethods(), right.listMethods() );
            _ins.INSPECT_FIELDS.diff(_ins, path, dt, left.listFields(), right.listFields() );
            _ins.INSPECT_NESTS.diff(_ins, path, dt, left.listNests(), right.listNests());
            //INSPECT_NESTS            
            return dt;
        }    

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, _interface left, _interface right) {
            _type.INSPECT_PACKAGE_NAME.diff(path,dt, leftRoot, rightRoot,left.getPackage(), right.getPackage() );
            _type.INSPECT_IMPORTS.diff(path,dt, leftRoot, rightRoot,left.listImports(), right.listImports() );
            _anno.INSPECT_ANNOS.diff(path, dt, leftRoot, rightRoot,left.getAnnos(), right.getAnnos());          
            _type.INSPECT_EXTENDS.diff(path, dt, leftRoot, rightRoot, left.listExtends(), right.listExtends());          
            _javadoc.INSPECT_JAVADOC.diff(path, dt, leftRoot, rightRoot, left.getJavadoc(), right.getJavadoc());  
            _typeParameter.INSPECT_TYPE_PARAMETERS.diff(path, dt, leftRoot, rightRoot, left.getTypeParameters(), right.getTypeParameters());  
            _java.INSPECT_NAME.diff(path, dt, leftRoot, rightRoot, left.getName(), right.getName());
            _modifiers.INSPECT_MODIFIERS.diff(path, dt, leftRoot, rightRoot, left.getEffectiveModifiers(), right.getEffectiveModifiers());
            _method.INSPECT_METHODS.diff(path, dt, leftRoot, rightRoot, left.listMethods(), right.listMethods() );
            _field.INSPECT_FIELDS.diff(path, dt, leftRoot, rightRoot, left.listFields(), right.listFields() );
            _type.INSPECT_NESTS.diff(path, dt, leftRoot, rightRoot, left.listNests(), right.listNests());
            return (_dif)dt;
        }
    }
}
