package draft.java.runtime;

import draft.DraftException;
import draft.java._type;
import draft.java.file._classFiles;
import draft.java.file._javaFiles;

import javax.annotation.processing.Processor;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.ToolProvider;
import java.io.IOException;
import java.io.Writer;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Handles the input to the Runtime "javac" Tool for compiling Java source files
 * into Classes
 *
 * @author M. Eric DeFazio
 */
public final class _javac {

    /**
     * Compile of {@link _type}s returning the {@link _classFiles}
     * containing the compiled {@link draft.java.file._classFile}s
     *
     * @param _types all _project to be compiled
     * @return directory of classes
     */
    public static _classFiles of(_type..._types ){
        return _javac.of(_javaFiles.of(_types ) );
    }

    public static _classFiles of( Processor annotationProcessor, _type...types ){
        return _javac.of(_javaFiles.of( types), annotationProcessor );
    }

    /**
     * Compile of {@link _type}s returning the {@link _classFiles}
     * containing the compiled {@link draft.java.file._classFile}s
     *
     * @param javaFiles
     * @return directory of classes
     */
    public static _classFiles of(_javaFiles javaFiles ){
        return _javac.of(new _javacOptions(), null, javaFiles, null, null, null );
    }

    /**
     * Return an options object that can configure "command line PARAMETERS"
     * that can be passed in
     * @return a new _javacOptions
     */
    public static _javacOptions options(){
        return new _javacOptions();
    }

    /**
     * Compile of {@link _type}s returning the {@link _classFiles}
     * containing the compiled {@link draft.java.file._classFile}s
     *
     * @param javaFiles source code to be compiled
     * @param annotationProcessors processors for ANNOTATIONS within java files
     * @return directory of classes
     */
    public static _classFiles of(_javaFiles javaFiles, Processor...annotationProcessors ){
        List<Processor> l = Arrays.asList( annotationProcessors );
        return _javac.of( new _javacOptions(), null, javaFiles, l, null, null );
    }

    /**
     * Compile of {@link _type}s returning the {@link _classFiles}
     * containing the compiled {@link draft.java.file._classFile}s
     *
     * @param parent an optional parent classLoader (containing dependent classes)
     * @param _types all _project to be compiled
     * @return directory of classes
     */
    public static _classFiles of(_classLoader parent, _type..._types ){
        return _javac.of(parent, _javaFiles.of(_types) );
    }

    /**
     * Compile of {@link _type}s returning the {@link _classFiles}
     * containing the compiled {@link draft.java.file._classFile}s
     *
     * @param parent an optional parent classLoader (containing dependent classes)
     * @param javaFiles all types to be compiled
     * @return directory of classes
     */
    public static _classFiles of(_classLoader parent, _javaFiles javaFiles ){

        return _javac.of(null, parent, javaFiles, null, null, null );
    }

    /**
     * Compile of {@link _type}s returning the {@link _classFiles}
     * containing the compiled {@link draft.java.file._classFile}s and also perform all
     * annotation processing provided by the annotation processors
     *
     * @param parent an optional parent classLoader (containing dependent classes)
     * @param javaFiles all types to be compiled
     * @param annotationProcessors
     * @return directory of classes
     */
    public static _classFiles of(
            _classLoader parent, _javaFiles javaFiles, Processor...annotationProcessors ){

        List<Processor>annProcessors = Arrays.asList(annotationProcessors);

        return _javac.of(null, parent, javaFiles, annProcessors, null, null );
    }

    /**
     * Compile of {@link _type}s returning the {@link _classFiles}
     * containing the compiled {@link draft.java.file._classFile}s
     *
     * @param compilerOpts compiler options for of
     * @param parent an optional parent classLoader (containing dependent classes)
     * @param _types all _project to be compiled
     * @return directory of classes
     */
    public static _classFiles of(
            _javacOptions compilerOpts,
            _classLoader parent,
            _type..._types ){

        return _javac.of(compilerOpts, parent, _javaFiles.of(_types), null, null, null );
    }

    /**
     * Compile of {@link _type}s returning the {@link _classFiles}
     * containing the compiled {@link draft.java.file._classFile}s
     *
     * @param compilerOpts compiler options for of
     * @param parent an optional parent classLoader (containing dependent classes)
     * @param javaFiles directory all types to be compiled
     * @param annotationProcessors processors to perform annotation processing on
     *
     * @return directory of classes
     */
    public static _classFiles of(
            _javacOptions compilerOpts,
            _classLoader parent,
            _javaFiles javaFiles,
            Processor...annotationProcessors ){

        List<Processor> annProcessors = Arrays.asList(annotationProcessors);
        return _javac.of(compilerOpts, parent, javaFiles, annProcessors, null, null );
    }

    /**
     * Compile of {@link _type}s returning the {@link _classFiles}
     * containing the compiled {@link draft.java.file._classFile}s
     *
     * @param compilerOpts options for the compiler
     * @param _types all _project to be compiled
     * @return directory of classes
     */
    public static _classFiles of(_javacOptions compilerOpts, _type..._types ){
        return _javac.of(compilerOpts, _javaFiles.of(_types) );
    }

    /**
     * Compile of {@link _type}s returning the {@link _classFiles}
     * containing the compiled {@link draft.java.file._classFile}s
     *
     * @param compilerOpts options for the compiler
     * @param javaFiles a directory containing the Java types to be compiled
     * @return directory of classes
     */
    public static _classFiles of(_javacOptions compilerOpts,
                                 _javaFiles javaFiles  ){

        return _javac.of(compilerOpts, null, javaFiles, null, null, null );
    }

    /**
     * Sometimes you only want to compile to create some bytecode,
     and you don't want to of and use the classes, this method will
     compile and return the _classFiles of classes, that can be returned
     packaged into a jar, etc.
     *
     * @param compilerOpts options for the compiler
     * @param _parent a classLoader containing other
     * @param javaFiles the source java files to be compiled
     * @param annotationProcessors annotation processors
     * @param diagnostics diagnostics used when the compiler compiles (compilation failures written here)
     * @param errOutput where the output is written to
     * @return a _classFiles containing the compiled .class files (in bytecode format)
     */
    public static _classFiles of(
            _javacOptions compilerOpts, //optional options for the of compiler
            _classLoader _parent, //optional "parent" classLoader (if you want to use pre-built ad-hoc classes, or include other source folders etc.)
            _javaFiles javaFiles, //REQUIRED source files to be compiled
            List<Processor>annotationProcessors, //optional annotation processors to adhoc on the code
            DiagnosticCollector<JavaFileObject> diagnostics, //optional where JAVAC diagnostics are written to
            Writer errOutput ) {//optional where to write Javac errors (defaults to System.err)

        _classLoader _targetClassLoader = of(
                _parent,
                compilerOpts,
                javaFiles,
                annotationProcessors,
                diagnostics,
                errOutput
        );
        return _targetClassLoader._fileMan.classFiles;
    }

    /**
     * <P>
     * NOTE: if you pass in a _classLoader EXPECT IT WILL be modified and returned
     * we HAVE to do this for situations where (when we compile a given class and
     * register it with the runtime, we no longer control the class (i.e. it has
     * exposure beyond the classLoader) and therefore if another class expresses
     * a dependency on this class, we May attempt to redefine it (which is invalid)
     * </P>
     *
     * <P>
     * so compiling into a classLoader (across time) or "staggered" compilation
     * should always have to compile into the same classLoader
     * (i.e. if you want to create a class A, then a class B where B depends on
     * A, you can:
     * <UL>
     * <LI>compile A & B at the same time into _classLoader _cl
     * <LI>compile A into _cl, THEN (later) compile B into _cl (passing the _cl into of)
     * <LI>compile A(version1) into _cl, then compile A (version2) into _cl
     * then compile B(version1) into _cl, then compile B(version2) into _cl
     * </UL>
     * The point is, we do NOT create multiple _cl (_classLoaders) and try to compile
     * versions of A and B across time  in DIFFERENT _classLoaders... that creates
     * an unholy mess and "breaks things", since the runtime (above the classLoader)
     * can have a handle to an old version of A or B, (related to an OLD _classLoader
     * that we are not in scope) and we have
     * </P>
     *
     * @param parent the parent classLoader
     * @param compilerOpts options for the compiler
     * @param javaFiles the source java files to be compiled
     * @param annotationProcessors annotation processors
     * @param diagnostics diagnostics used when the compiler compiles (compilation failures written here)
     * @param errOutput where the output is written to
     * @return a populated _classLoader
     */
    public static _classLoader of(
            _classLoader parent, _javacOptions compilerOpts, _javaFiles javaFiles, List<Processor> annotationProcessors,
            DiagnosticCollector<JavaFileObject> diagnostics, Writer errOutput) {//optional where to write Javac errors (defaults to System.err)

        /**..._1_build appropriate defaults for optional PARAMETERS the API is finiky */
        if( compilerOpts == null ){
            compilerOpts = new _javacOptions(); //_1_build me an empty _javacOptions
        }

        if( javaFiles == null || !javaFiles.iterator().hasNext() ) {
            throw new _javacException( "No Java Source Files to compile" );
        }
        _classLoader _targetClassLoader = null;
        if( parent == null ){ //they didnt provide an existing _classLoader
            _targetClassLoader = new _classLoader(); //create a new default _classLoader
        } else{
            if( parent.isEmpty() ){
                _targetClassLoader = parent;
            }else{
                _targetClassLoader = new _classLoader( parent );
            }
        }
        if( annotationProcessors == null ){
            annotationProcessors = Collections.EMPTY_LIST;
        }
        if( diagnostics == null ){
            diagnostics = new DiagnosticCollector<JavaFileObject>();
        }

        /** TODO I need to determine if isJava8 or later */

        JavaCompiler.CompilationTask task =
                ToolProvider.getSystemJavaCompiler()
                        .getTask( errOutput,
                                _targetClassLoader._fileMan, //the file manager will create .class files (in memory in this case)
                                diagnostics,
                                compilerOpts,
                                null, //annotationPros, //this is the "string-way" of specifying annotation Processors
                                javaFiles );

        task.setProcessors( annotationProcessors );

        boolean compiledNoErrors = task.call();


        if( !compiledNoErrors ) {
            throw new _javacException( diagnostics );
        }
        try{
            _targetClassLoader._fileMan.flush();
            _targetClassLoader._fileMan.close();
        } catch( IOException ioe ){
            throw new DraftException("Error writing Class bytecodes in _classLoader ", ioe);
        }
        return _targetClassLoader;
    }
}

