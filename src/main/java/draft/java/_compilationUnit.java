package draft.java;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import draft.Text;
import draft.java._anno._annos;
import draft.java._import._imports;
import java.util.Objects;
import java.util.function.*;
import draft.*;
import java.util.stream.*;
import java.util.*;

/**
 * Top level Java compilation Unit (an object model we need this because we have
 * {@link moduleInfo}.java and {@link packageInfo} files that need to be read
 * along with the standard java {@link _type}s like
 * {@link _class}, {@link _interface}, {@link _enum}, {@link annotation}
 *
 * @author Eric
 * @param <T> the implementation type
 */
public interface _compilationUnit<T extends _compilationUnit> extends _model {

    /**
     *
     * @return
     */
    public CompilationUnit astCompilationUnit();

    /**
     * Gets the package
     *
     * @return
     
    public String getPackage();
    */ 

    default boolean isTopLevel() {
        return true;
    }

    /**
     * Gets the "Header comment" (usually the License) from the compilationUnit
     * (NOTE: returns null if there are no header comments)
     *
     * @return the Comment implementation of the Header comment
     */
    default Comment getHeaderComment() {
        if (astCompilationUnit().getComment().isPresent()) {
            return astCompilationUnit().getComment().get();
        }
        return null;
    }

    /**
     * sets the header comment (i.e.the license copyright, etc.) at the top of
     * the _compilationUnit
     *
     * @param astBlockComment
     * @return the Comment a JavaDoc comment or BlockComment or null
     */
    default T setHeaderComment(BlockComment astBlockComment) {
        astCompilationUnit().setComment(astBlockComment);
        return (T) this;
    }

    /**
     * Sets the header comment (i.e. the copywrite)
     *
     * @param commentLines the lines in the header comment
     * @return the modified T
     */
    default T setHeaderComment(String... commentLines) {
        return setHeaderComment(Ast.blockComment(commentLines));
    }

    /**
     * gets the imports from the CompilationUnit
     * @return the _imports
     */
    default _imports getImports() {
        CompilationUnit cu = astCompilationUnit();
        if (cu != null) {
            return _imports.of(cu);
        }
        return new _imports(new CompilationUnit()); //better of all the evils
    }
    
    /**
     * remove imports based on predicate
     * @param _importMatchFn filter for deciding which imports to removeIn
     * @return the modified TYPE
     */
    default T removeImports( Predicate<_import> _importMatchFn ){
        getImports().remove(_importMatchFn );
        return (T)this;
    }
    
    /**
     * removeIn imports by classes
     * @param clazzes
     * @return
     */
    default T removeImports( Class...clazzes ){
        _imports.of(astCompilationUnit()).remove(clazzes);
        return (T)this;        
    }

    /**
     * 
     * @param toRemove
     * @return 
     */
    default T removeImports( _import...toRemove ){
        _imports.of(astCompilationUnit()).remove(toRemove);
        return (T)this;
    }
    
    /**
     * 
     * @param _typesToRemove
     * @return 
     */
    default T removeImports( _type..._typesToRemove ){
        getImports().remove(_typesToRemove);        
        return (T)this;
    }
    
    /**
     *
     * @param toRemove the ImportDeclarations to removeIn
     * @return the modified _type
     */
    default T removeImports( ImportDeclaration...toRemove ){
        CompilationUnit cu = astCompilationUnit();
        if( cu != null ){
            for(int i=0;i<toRemove.length;i++){
                cu.getImports().remove( toRemove[i] );
            }
        }
        return (T)this;
    }

    /**
     * 
     * @param toRemove
     * @return 
     */
    default T removeImports( List<ImportDeclaration> toRemove ){
        CompilationUnit cu = astCompilationUnit();
        if( cu != null ){
            for(int i=0;i<toRemove.size();i++){
                cu.getImports().remove( toRemove.get(i) );
            }
        }
        return (T)this;
    }
    
    /**
     * Select some imports based on the astImportPredicate and apply the 
     * astImportActionFn on the selected Imports
     * @param _importActionFn function to apply to the imports
     * @return the T
     */
    default T forImports( Consumer<_import> _importActionFn ){
        getImports().forEach(_importActionFn);
        //_imports.of(astCompilationUnit()).forEach(_importActionFn);
        //listImports(_importMatchFn).forEach(astImportActionFn);
        return (T)this;
    }
    
    /**
     * Select some imports based on the astImportPredicate and apply the 
     * astImportActionFn on the selected Imports
     * @param _importMatchFn selects the Imports to act on
     * @param _importActionFn function to apply to the imports
     * @return the T
     */
    default T forImports( Predicate<_import> _importMatchFn, Consumer<_import> _importActionFn ){
        _imports.of(astCompilationUnit()).forEach(_importMatchFn, _importActionFn);
        //listImports(_importMatchFn).forEach(astImportActionFn);
        return (T)this;
    }
    
    //TODO get rid of this in place of _imports, or getImports()    
    default List<ImportDeclaration> listAstImports(){
        CompilationUnit cu = astCompilationUnit();
        if( cu != null ){
            return cu.getImports();
        }
        return new ArrayList<>();
    }
    
    default List<_import> listImports(){
        return getImports().list();       
    }
    
    default boolean hasImport( _type _t ){
        return hasImport( _t.getFullName() );
    }
    
    /**
     * Does this class statically import this class
     * i.e.
     * <PRE>
     * _class _c = _class.of("A").imports("import static draft.java.Ast.*;");
     * assertTrue( _c.hasImportStatic(Ast.class));
     * </PRE>
     * 
     * @param clazz
     * @return 
     */
    default boolean hasImportStatic( Class clazz ){
        return !listImports( i -> i.isStatic() && i.isWildcard() && i.hasImport(clazz)).isEmpty();        
    }

    /**
     * class or method name
     * <PRE>
     * 
     * </PRE>
     * @param className
     * @return 
     */
    default boolean hasImport(String className){
        return _imports.of(astCompilationUnit()).hasImport(className);        
    }

    default boolean hasImports( Class...clazzes ){
        return _imports.of(astCompilationUnit()).hasImports(clazzes);
    }
    
    default boolean hasImport(Class clazz ){        
        return _imports.of(astCompilationUnit()).hasImport(clazz);        
    }
    
    default boolean hasImport( Predicate<_import> _importMatchFn ){
        return !listImports( _importMatchFn ).isEmpty();
    }
    
    default boolean hasImport( _import _i){
        return listImports( i -> i.equals(_i )).size() > 0;
    }

    default List<_import> listImports( Predicate<_import> _importMatchFn ){
        return this.getImports().list().stream().filter( _importMatchFn ).collect(Collectors.toList());
    }

    /**
     * Adds static wildcard imports for all Classes
     * @param wildcardStaticImports a list of classes that will WildcardImports
     * @return the T
     */
    default T importStatic( Class ...wildcardStaticImports ){
        CompilationUnit cu = astCompilationUnit();
        if( cu != null ){
            Arrays.stream(wildcardStaticImports).forEach( i -> {
                ImportDeclaration id = Ast.importDeclaration(i);
                id.setAsterisk(true);
                id.setStatic(true);
                cu.addImport( id );
                /*
                if( i.isArray() ){
                    cu.addImport(new ImportDeclaration((i.getComponentType().getCanonicalName()), true, true));
                } else {
                    cu.addImport(new ImportDeclaration(i.getCanonicalName(), true, true));
                }
                */
            });
        }
        return (T)this;
    }

    /**
     *
     * @param staticWildcardImports
     * @return
     */
    default T importStatic( String...staticWildcardImports){
        CompilationUnit cu = astCompilationUnit();
        if( cu != null ){
            Arrays.stream(staticWildcardImports).forEach( i -> {
                ImportDeclaration id = Ast.importDeclaration(i);
                id.setStatic(true);
                id.setAsterisk(true);
                cu.addImport(id);
                /*
                if( i.endsWith(".*")) {
                    ImportDeclaration id = Ast.importDeclaration(i);
                    cu.addImport(new ImportDeclaration(i.substring(0, i.length() - 2), true, true));
                } else{
                    cu.addImport(new ImportDeclaration(i, true, false));
                }
                */
            });
        }
        return (T)this;
    }
    

    /**
     * Statically import all of the
     * @param wildcardTypeStaticImport
     * @return
     */
    default T importStatic( _type...wildcardTypeStaticImport){
        CompilationUnit cu = astCompilationUnit();
        if( cu != null ){
            Arrays.stream(wildcardTypeStaticImport).forEach( i -> {
                cu.addImport(new ImportDeclaration(i.getFullName(), true, true));
            });
        }
        return (T)this;
    }

    /**
     *
     * @param _ts
     * @return
     */
    default T imports( _type... _ts ){
        Arrays.stream(_ts).forEach( _t -> imports(_t.getFullName()));
        return (T)this;
    }

    
    default T imports( Class singleClass ){
        return imports( new Class[]{singleClass});
    }
    
    /**
     * Regularly import a class
     * @param classesToImport
     * @return
     */
    default T imports( Class...classesToImport ){
        CompilationUnit cu = astCompilationUnit();
        if( cu != null ){
            for(int i=0;i<classesToImport.length; i++){
                if( classesToImport[i].isArray() ){
                    //System.out.println("CT " + classesToImport[i].getComponentType() );
                    imports(classesToImport[i].getComponentType());
                }else{
                    //dont import primitives or primitive arrays
                    if( classesToImport[i] == null 
                        || classesToImport[i].isPrimitive() 
                        || classesToImport[i].isArray() && classesToImport[i].getComponentType().isPrimitive() 
                        || classesToImport[i].getPackageName().equals("java.lang") ) {
                        break;
                    }
                    String cn = classesToImport[i].getCanonicalName();
                    //fix a minor bug in JavaParser API where anything in "java.lang.**.*" is not imported
                    // so java.lang.annotation.* classes are not imported when they should be
                    if( classesToImport[i].getPackage() != Integer.class.getPackage()
                        && classesToImport[i].getCanonicalName().startsWith("java.lang") ) {
                        //System.out.println( "manually adding "+ classesToImport[i].getCanonicalName());
                        cu.addImport(classesToImport[i].getCanonicalName());
                    } else {
                        cu.addImport(cn);
                    }
                }
            }
            return (T)this;
        }
        throw new DraftException("No AST CompilationUnit to add imports");
    }

    default T imports( ImportDeclaration...importDecls){
        CompilationUnit cu = astCompilationUnit();
        if( cu != null ){
            Arrays.stream( importDecls ).forEach( c-> cu.addImport( c ) );
            return (T)this;
        }
        //return (T) this;
        throw new DraftException("No AST CompilationUnit of class to add imports");
    }

    default T imports( String singleStatement ){
        return imports( new String[]{singleStatement});
    }
    
    default T imports( String...importStatements ){
        CompilationUnit cu = astCompilationUnit();
        if( cu != null ){
            Arrays.stream( importStatements ).forEach( c-> cu.addImport( Ast.importDeclaration( c ) ) );
            return (T)this;
        }
        //return (T) this;
        throw new DraftException("No AST CompilationUnit of  to add imports");
    }

    /**
     *
     */
    public static class _packageInfo implements _compilationUnit<_packageInfo>, _anno._hasAnnos<_packageInfo> {

        public static _packageInfo of(String... pkgInfo) {
            return new _packageInfo(StaticJavaParser.parse(Text.combine(pkgInfo)));
        }

        public static _packageInfo of(CompilationUnit astCu) {
            return new _packageInfo(astCu);
        }

        public CompilationUnit astCompUnit;

        @Override
        public CompilationUnit astCompilationUnit() {
            return astCompUnit;
        }

        public _packageInfo(CompilationUnit astCu) {
            this.astCompUnit = astCu;
        }

        public String getPackage() {
            if (astCompilationUnit().getPackageDeclaration().isPresent()) {
                return astCompilationUnit().getPackageDeclaration().get().getNameAsString();
            }
            return "";
        }

        /**
         * Sets the package this TYPE is in
         *
         * @param packageName
         * @return the modified TYPE
         */
        public _packageInfo setPackage(String packageName) {
            CompilationUnit cu = astCompilationUnit();
            //System.out.println("Setting package name to \""+ packageName+"\" in "+ cu );
            //TODO I need to make sure it's a valid name
            cu.setPackageDeclaration(packageName);
            return this;
        }

        /**
         * Returns true if this compilationUnit is located directly within this
         * package
         *
         * @param packageName
         * @return
         */
        public boolean isInPackage(String packageName) {
            String pn = getPackage();
            if (pn == null) {
                return packageName == null || packageName.length() == 0;
            }
            if (Objects.equals(pn, packageName)) {
                return true;
            }
            if (packageName != null) {
                return packageName.indexOf(pn) == 0;
            }
            return false;
        }

        @Override
        public _annos getAnnos() {
            if (astCompilationUnit().getPackageDeclaration().isPresent()) {
                //annos are on the packageDeclaration
                return _annos.of(astCompilationUnit().getPackageDeclaration().get());
            }
            return _annos.of(); //dont like this... but
        }
    }

    /**
     * a module-info.java file it is it's own
     */
    public static class _moduleInfo implements _compilationUnit<_moduleInfo> {

        public CompilationUnit astCompUnit;

        @Override
        public CompilationUnit astCompilationUnit() {
            return astCompUnit;
        }

        public static _moduleInfo of(String... input) {
            return new _moduleInfo(StaticJavaParser.parse(Text.combine(input)));
        }

        public _moduleInfo(CompilationUnit cu) {
            this.astCompUnit = cu;
        }

        public ModuleDeclaration getModuleAst() {
            if (this.astCompUnit.getModule().isPresent()) {
                return this.astCompUnit.getModule().get();
            }
            return null;
        }

        @Override
        public Comment getHeaderComment() {
            if (this.astCompUnit.getComment().isPresent()) {
                return this.astCompUnit.getComment().get();
            }
            return null;
        }

        @Override
        public _moduleInfo setHeaderComment(BlockComment astBlockComment) {
            this.astCompUnit.setComment(astBlockComment);
            return this;
        }

        @Override
        public _moduleInfo setHeaderComment(String... commentLines) {
            return setHeaderComment(Ast.blockComment(commentLines));
        }
    }

}
