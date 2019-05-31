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
 * Top level Java 
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

}
