package draft.java;

import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.modules.ModuleDeclaration;
import draft.DraftException;
import draft.Text;
import draft.java._java.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * "module-info.java" file describing the module dependencies
 * 
 */
public class _moduleInfo implements _code<_moduleInfo>, _node<CompilationUnit> {

    public CompilationUnit astCompUnit;
    private final _javadoc.JavadocHolderAdapter javadocHolder;

    @Override
    public CompilationUnit astCompilationUnit() {
        return astCompUnit;
    }

    /**
     * is the String representation equal to the _node entity (i.e. if we parse
     * the string, does it create an AST entity that is equal to the node?)
     *
     * @param stringRep the string representation of the node (parsed as an AST
     * and compared to this entity to see if equal)
     * @return true if the Parsed String represents the entity
     */
    @Override
    public boolean is(String... stringRep) {
        try {
            return is(Ast.of(stringRep));
        } catch (DraftException e) {
            return false;
        }
    }

    @Override
    public String getSimpleName(){
        return "module-info";
    }
    
    @Override
    public String getFullName(){
        return "module-info";
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
        final _moduleInfo other = (_moduleInfo) obj;
        if (!Objects.equals(this.astCompUnit, other.astCompUnit)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return this.astCompUnit.hashCode();
    }

    /**
     * Is the AST node representation equal to the underlying entity
     *
     * @param astCu the astNode to compare against
     * @return true if they represent the same _node, false otherwise
     */
    @Override
    public boolean is(CompilationUnit astCu) {
        return false;
    }

    /**
     * Decompose the entity into key-VALUE pairs
     *
     * @return a map of key values
     */
    @Override
    public Map<_java.Component, Object> componentsMap() {

        Map m = new HashMap();
        m.put(_java.Component.HEADER_COMMENT, getHeaderComment());
        m.put(_java.Component.NAME, getModuleAst().getNameAsString());
        m.put(_java.Component.MODULE_DECLARATION, getModuleAst());
        m.put(_java.Component.ANNOS, _anno._annos.of(getModuleAst()));
        m.put(_java.Component.IMPORTS, _import._imports.of(astCompUnit));
        m.put(_java.Component.JAVADOC, this.javadocHolder.getJavadoc());

        return m;
    }

    @Override
    public String toString() {
        return this.astCompUnit.toString();
    }

    @Override
    public CompilationUnit ast() {
        return astCompUnit;
    }

    public static _moduleInfo of(String... input) {
        return new _moduleInfo(Ast.parse(Text.combine(input)));
    }

    public static _moduleInfo of(CompilationUnit astCu) {
        return new _moduleInfo(astCu);
    }

    public _moduleInfo(CompilationUnit cu) {
        this.astCompUnit = cu;
        this.javadocHolder = new _javadoc.JavadocHolderAdapter(cu);
    }

    @Override
    public boolean isTopLevel() {
        return true;
    }

    public ModuleDeclaration getModuleAst() {
        if (this.astCompUnit.getModule().isPresent()) {
            return this.astCompUnit.getModule().get();
        }
        return null;
    }
}
