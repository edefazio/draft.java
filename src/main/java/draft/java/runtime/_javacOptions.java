package draft.java.runtime;

import java.nio.file.Path;
import java.util.*;

/**
 * javac compiler options API (formalized) in an API for more intuitive
 * documentation given it is being called at runtime (not from the command line)
 *
 * @author M. Eric DeFazio
 */
public final class _javacOptions
        implements Iterable<String>{

    /** convention (static of method rather than new constructor)
     * @return a new _javacOptions
     */
    public static _javacOptions of(){
        return new _javacOptions();
    }

    @Override
    public Iterator<String> iterator() {
        return toOptions().iterator();
    }

    /**
     * when compiling using javac a successful TYPE search may produce a class file,
     * a source file, or both.
     *
     * If both are found, then you can use the -Xprefer option to instruct
     * the compiler which to use. If "newer" is specified, then the compiler
     * uses the "newer" of the two files. If "source" is specified, the compiler
     * uses the "source" file. The default is "newer".
     *
     * If a TYPE search finds a source file for a required TYPE, either by
     * itself, or as a result of the setting for the -Xprefer option,
     * then the compiler reads the source file to getAt the information it needs.
     * By default the compiler also compiles the source file. You can use the
     * -implicit option to specify the behavior.
     *
     * If "none" is specified, then no class files are generated for the source
     * file. If class is specified, then class files are generated for the source file.
     *
     * The compiler might not discover the need for some TYPE information until
     * after annotation processing completes.
     *
     * When the TYPE information is found in a source file and no -implicit
     * option is specified, the compiler gives a warning that the file is being
     * compiled without being subject to annotation processing.
     *
     * To disable the warning, either specify the file on the command line
     * (so that it will be subject to annotation processing) or use the
     * -implicit option to specify whether or not class files should be
     * generated for such source files.
     */
    public enum ClassResolvePrefer {
        NEWER,
        SOURCE,
        CLASS;

        @Override
        public String toString() {
            switch( this ) {
                case NEWER:
                    return "-Xprefer:newer"; //use whichever is newer (DEFAULT)
                case SOURCE:
                    return "-Xprefer:source"; //prefer the source when possible
                default:
                    return "-Xprefer:class"; //prefer the class
            }
        }
    }

    /**
     * Stores the Data about the options for the compiler options
     */
    public static class _data {

        /**
         * Specifies options to pass to annotation processors. These options are
         * not interpreted by javac directly, but are made available for use by
         * individual processors. The key VALUE should be one or more
         * identifiers separated by a dot (.).
         *
         * <PRE>FORM: "-Akey[=VALUE]"</PRE>
         */
        public Map<String, String> annotationKeyValues = new HashMap<>();

        /**
         * any -X??? javac options
         */
        public Map<String, String>dashXOptionKeyValues = new HashMap<>();

        /**
         * Contains individual paths (and when "printing" separates them with ;)
         */
        public List<String> classPaths = new ArrayList<>();

        public String extensionDirs;

        public String endorsedDirs;

        public String classDestinationDirectory;

        /**
         * -deprecation Shows a description of each use or override of a
         * deprecated member or class. Without the -deprecation option, javac
         * shows a summary of the source files that use or override deprecated
         * BODY or classes. The -deprecation option is shorthand for
         * -Xlint:deprecation
         */
        public Boolean deprecationShown;

        public Set<DebugInformationType> debugInformation = null;

        /**
         * Null means dont use this property false means -implicit:none true
         * means -implicit:class
         *
         */
        public Boolean implicitClassGeneration;

        public List<String> launcherOptions = new ArrayList<>();

        public String encoding;

        public Boolean noWarn;

        public Boolean parameters;

        public AnnotationProcessingDirective annotationProcessingDirective;

        public List<String> annotationProcessorNames
                = new ArrayList<>();

        public String annotationProcessorPath;

        public String generatedSourcePath;

        public String sourceVersion;

        public String sourcePath;

        public Boolean verbose;

        /**
         * -XprintProcessorInfo
         * Print information about which ANNOTATIONS a processor is asked to process.
         */
        public Boolean annotationsProcessedPrinted;

        /** -XprintRounds
         * Print information about initial and subsequent annotation processing rounds.
         */
        public Boolean annotationRoundsPrinted;

        public Boolean terminateOnWarning;

        /** Targets a specific bytecode version of the JVM for class files*/
        public String targetClassVersion;

        /**
         * When the Compiler Searches for Types and resolves multiple potential
         * types, (i.e. a ".class" file on the ClassPath, and a ".java" file
         * on the sourcePath) this option will decide which to prefer
         * (either the source, the class, or the "newest"
         */
        public ClassResolvePrefer prefer;

        /** Allows cross-compilation with a set of classes (i.e. rt.jar)*/
        public String bootstrapPath;

        /** Adds a prefix to the bootstrap path */
        public String bootstrapPathPrefix;

        /** Adds a suffix to the bootstrap path */
        public String bootstrapPathSuffix;


        public List<String> toOptions() {
            ArrayList<String> opts = new ArrayList<String>();

            if( this.classPaths.size() > 0 ) {
                StringBuilder sb = new StringBuilder();
                for( int i = 0; i < classPaths.size(); i++ ) {
                    if( i > 0 ) {
                        sb.append( ";" );
                    }
                    sb.append( classPaths.get( i ) );
                }
                opts.add( "-cp"); opts.add( sb.toString() );
            }
            if( this.sourceVersion != null ) {
                //opts.accept( "-source " + this.sourceVersion );
                opts.add( "-source"); opts.add( this.sourceVersion );
            }
            if( this.targetClassVersion != null ) {
                opts.add( "-target"); opts.add( this.targetClassVersion );
            }

            if( this.sourcePath != null ) {
                opts.add( "-sourcepath");opts.add( this.sourcePath );
            }

            if( generatedSourcePath != null ) {
                opts.add( "-s"); opts.add( this.generatedSourcePath );
            }
            if( this.annotationProcessorPath != null ) {
                opts.add( "-processorpath"); opts.add(this.annotationProcessorPath );
            }
            if( this.annotationProcessorNames != null && this.annotationProcessorNames.size() > 0 ) {
                StringBuilder sb = new StringBuilder();
                for( int i = 0; i < this.annotationProcessorNames.size(); i++ ) {
                    if( i > 0 ) {
                        sb.append( "," );
                    }
                    sb.append( this.annotationProcessorNames.get( i ) );
                }
                opts.add( "-processor"); opts.add( sb.toString() );
            }
            if( this.annotationProcessingDirective != null ) {
                opts.add( this.annotationProcessingDirective.name );
            }
            if( Boolean.TRUE.equals( this.terminateOnWarning ) ) {
                opts.add( "-Werror" );
            }
            if( Boolean.TRUE.equals( this.verbose ) ) {
                opts.add( "-verbose" );
            }
            if( Boolean.TRUE.equals( this.parameters )) {
                opts.add( "-parameters" );
            }
            if( Boolean.TRUE.equals( this.noWarn ) ) {
                opts.add( "-nowarn" );
            }
            if( this.encoding != null ) {
                opts.add( "-encoding"); opts.add( encoding );
            }
            if( this.launcherOptions != null && this.launcherOptions.size() > 0 ) {
                for( int i = 0; i < launcherOptions.size(); i++ ) {
                    opts.add( "-J"); opts.add( launcherOptions.get( i ) );
                }
            }
            if( this.implicitClassGeneration != null ) {
                if( this.implicitClassGeneration ) {
                    opts.add( "-implicit:class" );
                }
                else {
                    opts.add( "-implicit:none" );
                }
            }

            if( Boolean.TRUE.equals( this.deprecationShown ) ) {
                opts.add( "-deprecation" );
            }
            if( this.debugInformation != null ) {
                if( this.debugInformation.size() == 0 ) {
                    opts.add( "-g:none" );
                }
                else if( this.debugInformation.size()
                        == DebugInformationType.values().length ) {
                    opts.add( "-g" );
                }
                else {
                    DebugInformationType[] dt = this.debugInformation.toArray( new DebugInformationType[ 0 ] );
                    StringBuilder sb = new StringBuilder();
                    for( int i = 0; i < dt.length; i++ ) {
                        if( i > 0 ) {
                            sb.append( "," );
                        }
                        sb.append( dt[ i ] );
                    }
                    opts.add( "-g:" + sb.toString() );
                }
            }
            if( this.classDestinationDirectory != null ) {
                opts.add( "-d"); opts.add( classDestinationDirectory );
            }
            if( this.endorsedDirs != null ) {
                opts.add( "-Djava.endorsed.dirs=" + endorsedDirs );
            }
            if( this.extensionDirs != null ) {
                opts.add( "-Djava.ext.dirs=" + extensionDirs );
            }

            String[] keys = annotationKeyValues.keySet().toArray( new String[ 0 ] );
            for( int i = 0; i < keys.length; i++ ) {
                Object value = annotationKeyValues.get( keys[ i ] );
                if( value == null ) {
                    opts.add( "-A"+ keys[ i ] );
                }
                else {
                    opts.add( "-A"+ keys[ i ]+ "=" + value );
                }
            }

            if( this.prefer != null ) {
                opts.add( this.prefer.toString() );
            }
            if( this.bootstrapPath != null ){
                opts.add("-Xbootclasspath/:"+this.bootstrapPath);
            }
            if( this.bootstrapPathPrefix != null ){
                opts.add("-Xbootclasspath/a:"+this.bootstrapPathPrefix);
            }
            if( this.bootstrapPathSuffix != null ){
                opts.add("-Xbootclasspath/p:"+this.bootstrapPathSuffix);
            }
            if( this.annotationRoundsPrinted != null && this.annotationRoundsPrinted.equals( Boolean.TRUE)){
                opts.add("-XprintRounds");
            }
            if(this.annotationsProcessedPrinted != null && this.annotationRoundsPrinted.equals( Boolean.TRUE)){
                opts.add("-XprintProcessorInfo");
            }
            String[] xoptKeys = this.dashXOptionKeyValues.keySet().toArray( new String[0]);
            for(int i=0;i<xoptKeys.length;i++){
                String val = this.dashXOptionKeyValues.get( xoptKeys[i] );
                if( val == null ){
                    opts.add("-X"+xoptKeys[i] );
                }else{
                    opts.add("-X"+xoptKeys[i]+":"+val );
                }
            }
            return opts;
        }
    }

    private _data data = new _data();

    /**
     * Add a key to be passed to the AnnotationProcessors attached to the
     * compiler
     */
    public _javacOptions annotationKey(String key ) {
        data.annotationKeyValues.put( key, null );
        return this;
    }

    /**
     * Add a key and VALUE to be passed to the AnnotationProcessors attached to
     * the compiler
     */
    public _javacOptions annotationKeyValue(String key, String value ) {
        data.annotationKeyValues.put( key, value );
        return this;
    }

    /**
     * Specify where to find user class files, and (optionally) annotation
     * processors and source files. This class path overrides the user class
     * path in the CLASSPATH environment variable. If neither CLASSPATH, -cp nor
     * -classpath is specified, the user class path consists of the current
     * directory.
     */
    public _javacOptions classPath(String classPath ) {
        classPathAdd( classPath );
        return this;
    }

    /**
     * Appends Path(s) to the END of the existing classpath
     */
    public _javacOptions classPathAdd(String... manyPaths ) {
        for( int i = 0; i < manyPaths.length; i++ ) {
            String[] paths = manyPaths[ i ].split( ";" );
            for( int j = 0; j < paths.length; j++ ) {
                data.classPaths.add( paths[ j ] );
            }
        }
        return this;
    }

    /** add the following paths to the classpath for the javac compiler */
    public _javacOptions classPathAdd(Path...paths){
        String[] strPaths = new String[ paths.length];
        for(int i=0;i<paths.length;i++){
            strPaths[i] = paths[i].toString();
        }
        return classPathAdd(strPaths);
    }

    /**
     * -Djava.ext.dirs=directories Override the location of installed
     * extensions.
     */
    public _javacOptions extensionDirs(String extDirs ) {
        data.extensionDirs = extDirs;
        return this;
    }

    /**
     * -Djava.ext.dirs=directories Override the location of installed
     * extensions.
     */
    public _javacOptions extensionDirs(Path extDirs ) {
        data.extensionDirs = extDirs.toString();
        return this;
    }

    /**
     * -Djava.ext.dirs=directories Override the location of installed
     * extensions.
     */
    public _javacOptions endorsedDirs(String endorsedDirs ) {
        data.endorsedDirs = endorsedDirs;
        return this;
    }

    /**
     * -Djava.ext.dirs=directories Override the location of installed
     * extensions.
     */
    public _javacOptions endorsedDirs(Path endorsedDirs ) {
        data.endorsedDirs = endorsedDirs.toString();
        return this;
    }

    /**
     * Sets the destination directory for class files. The directory must
     * already exist because javac does not create it. If a class is part of a
     * package, then javac puts the class file in a subdirectory that reflects
     * the package NAME and creates directories as needed. If you specify -d
     * C:\myclasses and the class is called com.mypackage.MyClass, then the
     * class file is C:\myclasses\com\mypackage\MyClass.class.
     *
     * If the -d option is not specified, then javac puts each class file in the
     * same directory as the source file from which it was generated.
     *
     * Note: The directory specified by the -d option is not automatically added
     * to your user class path.
     */
    public _javacOptions classDestinationDirectory(
            String classDestinationDirectory ) {
        data.classDestinationDirectory = classDestinationDirectory;
        return this;
    }

    public _javacOptions deprecationShown() {
        return deprecationShown( Boolean.TRUE );
    }

    public _javacOptions deprecationShown(Boolean showDeprecation ) {
        data.deprecationShown = showDeprecation;
        return this;
    }

    public enum DebugInformationType {
        SOURCE( "source" ),
        LINES( "lines" ),
        VARS( "vars" );

        private final String name;

        DebugInformationType(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    /**
     * -g Generate all debugging information, including local variables. By
     * default, only line number and source file information is generated.
     */
    public _javacOptions debugInformationFull() {
        return debugInformation(
                DebugInformationType.LINES,
                DebugInformationType.SOURCE,
                DebugInformationType.VARS );
    }

    /**
     * -g:{keyword listAt} Generate only some kinds of debugging information,
     * specified by a comma separated listAt of keywords. Valid keywords are:
     * Lines, source, vars
     *
     * @param types
     * @return
     */
    public _javacOptions debugInformation(DebugInformationType... types ) {
        data.debugInformation = new HashSet<DebugInformationType>();
        for( int i = 0; i < types.length; i++ ) {
            data.debugInformation.add( types[ i ] );
        }
        return this;
    }

    /**
     * Turn off debug information about lines, vars and source
     */
    public _javacOptions debugInformationOff() {
        data.debugInformation = new HashSet<DebugInformationType>();
        return this;
    }

    /**
     * -implicit:[class, none] Controls the generation of class files for
     * implicitly loaded source files. To automatically generate class files,
     * use -implicit:class. To suppress class file generation, use
     * -implicit:none. If this option is not specified, then the default is to
     * automatically generate class files. In this case, the compiler issues a
     * warning if any such class files are generated when also doing annotation
     * processing.
     *
     * The warning is not issued when the -implicit option is set explicitly.
     * See
     * <A HREF="https://docs.oracle.com/javase/8/docs/technotes/tools/windows/javac.html#BHCJJJAJ">Searching
     * for Types</a>.
     *
     * @return
     */
    public _javacOptions generateImplicitClasses() {
        return generateImplicitClasses( Boolean.TRUE );
    }

    /**
     * Controls the generation of class files for implicitly loaded source
     * files. To automatically generate class files, use -implicit:class. To
     * suppress class file generation, use -implicit:none. If this option is not
     * specified, the default is to automatically generate class files. In this
     * case, the compiler will issue a warning if any such class files are
     * generated when also doing annotation processing. The warning will not be
     * issued if this option is set explicitly
     *
     * Null means dont use this property false means -implicit:none true means
     * -implicit:class
     */
    public _javacOptions generateImplicitClasses(Boolean implicit ) {
        data.implicitClassGeneration = implicit;
        return this;
    }

    /**
     * Pass option to the java launcher called by javac. For example, -J-Xms48m
     * sets the startup memory to 48 megabytes. It is a common convention for -J
     * to pass options to the underlying VM executing applications written in
     * Java.
     *
     * Note: CLASSPATH, -classpath, -bootclasspath, and -extdirs do not specify
     * the classes used to adhoc javac. Fiddling with the implementation of the
     * compiler in this way is usually pointless and always risky. If you do
     * need to do this, use the -J option to pass through options to the
     * underlying java launcher.
     *
     * @param options individual options to pass to the Java Launcher -J
     * @return the optionBuilder
     */
    public _javacOptions javaLauncherOptions(String... options ) {
        for( int i = 0; i < options.length; i++ ) {
            data.launcherOptions.add( options[ i ] );
        }
        return this;
    }

    /**
     * Sets the source file encoding NAME, such as EUC-JP and UTF-8. If the
     * -encoding option is not specified, then the platform default converter is
     * used.
     */
    public _javacOptions encoding(String encoding ) {
        data.encoding = encoding;
        return this;
    }

    /**
     * Disables warning messages. This option operates the same as the
     * -Xlint:none option.
     */
    public _javacOptions warningsOff() {
        data.noWarn = Boolean.TRUE;
        return this;
    }

    /**
     * Stores formal parameter names of CONSTRUCTORS and METHODS in the
     * generated class file so that the method java.lang.reflect.Executable.getParameters from the Reflection API can
     * retrieve them.
     * i.e. IF we pass set this to be true (when we compile)
     *
     * class C{
     *     void aMethod(int daysPerWeek)
     * }
     *
     * //the parameter NAME "daysPerWeek" is stored in the .class file and can be accessed via reflection
     * assertEquals("daysPerWeek", C.class.getMethod("aMethod", new Class[]{int.class}).getParameters()[0].getName());
     *
     */
    public _javacOptions parameterNamesStoredForRuntimeReflection() {
        data.parameters = Boolean.TRUE;
        return this;
    }

    public enum AnnotationProcessingDirective {
        ONLY_PROCESS_ANNOTATIONS( "-proc:only" ),
        SKIP_ANNOTATION_PROCESSING( "-proc:none" );

        private final String name;

        AnnotationProcessingDirective(String name) {
            this.name = name;
        }

        public String toString() {
            return name;
        }
    }

    /**
     * Dont do a full compilation, rather just process the ANNOTATIONS
     * -proc:only
     *
     * @return
     */
    public _javacOptions annotationProcessOnly() {
        data.annotationProcessingDirective
                = AnnotationProcessingDirective.ONLY_PROCESS_ANNOTATIONS;
        return this;
    }

    /**
     * Do a full compilation, but AVOID processing ANNOTATIONS -proc:none means
     * that compilation takes place without annotation processing.
     *
     * @return
     */
    public _javacOptions annotationProcessSkip() {
        data.annotationProcessingDirective
                = AnnotationProcessingDirective.SKIP_ANNOTATION_PROCESSING;
        return this;
    }

    /**
     * Process any provided annotation processors
     */
    public _javacOptions annotationProcessNormally() {
        data.annotationProcessingDirective = null;
        return this;
    }

    /**
     * Provide a List of ClassNames used for Annotation Processing
     */
    public _javacOptions annotationProcessorNames(String... classNames ) {
        for( int i = 0; i < classNames.length; i++ ) {
            data.annotationProcessorNames.add( classNames[ i ] );
        }
        return this;
    }

    /**
     * Specifies where to find annotation processors. If this option is not
     * used, then the class path is searched for processors.
     */
    public _javacOptions annotationProcessorPath(String pathToAnnotationProcessors ) {
        data.annotationProcessorPath = pathToAnnotationProcessors;
        return this;
    }

    /**
     * When compiling, print which annotation rounds are taking place
     * @return the updated javac options
     */
    public _javacOptions annotationRoundsPrinted(){
        data.annotationRoundsPrinted = Boolean.TRUE;
        return this;
    }

    /**
     * When compiling, print which ANNOTATIONS that are asked to be printed
     * @return the updated javac options
     */
    public _javacOptions annotationProcessedPrinted(){
        data.annotationsProcessedPrinted = Boolean.TRUE;
        return this;
    }


    /**
     * -s Specifies the directory where to place the generated source files. The
     * directory must already exist because javac does not create it. If a class
     * is part of a package, then the compiler puts the source file in a
     * subdirectory that reflects the package NAME and creates directories as
     * needed. If you specify -s C:\mysrc and the class is called
     * com.mypackage.MyClass, then the source file is put in in
     * C:\mysrc\com\mypackage\MyClass.java.
     *
     * @param path
     * @return
     */
    public _javacOptions generatedSourcePath(String path ) {
        data.generatedSourcePath = path;
        return this;
    }

    /**
     * Expects .java source files for Java version 1.3 The compiler does not
     * support assertions, generics, or other language features introduced after
     * Java SE 1.3.
     */
    public _javacOptions sourceVersion_java1_3() {
        data.sourceVersion = "1.3";
        return this;
    }

    /**
     * Expects .java source files for Java version 1.4 The compiler accepts
     * source code containing assertions, which were introduced in Java SE 1.4.
     */
    public _javacOptions sourceVersion_java1_4() {
        data.sourceVersion = "1.4";
        return this;
    }

    /**
     * Expects .java source files for Java version 5 (1.5) The compiler accepts
     * source code containing generics and other language features introduced in
     * Java SE 5.
     */
    public _javacOptions sourceVersion_java5() {
        data.sourceVersion = "5";
        return this;
    }

    /**
     * Expects .java source files for Java version 6 (1.6) No language changes
     * were introduced in Java SE 6. However, encoding errors in source files
     * are now reported as errors instead of warnings as in earlier releases of
     * Java Platform, Standard Edition.
     */
    public _javacOptions sourceVersion_java6() {
        data.sourceVersion = "6";
        return this;
    }

    /**
     * Expects .java source files for Java version 7 (1.7)
     *
     * @return
     */
    public _javacOptions sourceVersion_java7() {
        data.sourceVersion = "1.7";
        return this;
    }

    /**
     * Expects .java source files for Java version 8 (1.8)
     *
     * @return
     */
    public _javacOptions sourceVersion_java8() {
        data.sourceVersion = "8";
        return this;
    }

    /**
     * Expects .java source files for Java version 9 (1.9)
     *
     * @return
     */
    public _javacOptions sourceVersion_java9() {
        data.sourceVersion = "9";
        return this;
    }

    /**
     * Expects .java source files for Java version 10
     *
     * @return
     */
    public _javacOptions sourceVersion_java_10() {
        data.sourceVersion = "10";
        return this;
    }

    /**
     * Expects .java source files for Java version 11
     *
     * @return
     */
    public _javacOptions sourceVersion_java_11() {
        data.sourceVersion = "11";
        return this;
    }
    
    /**
     * Expects .java source files for Java version 11
     *
     * @return
     */
    public _javacOptions sourceVersion_java_12() {
        data.sourceVersion = "12";
        return this;
    }
    
    /**
     * Expects .java source files for Java version 13
     *
     * @return
     */
    public _javacOptions sourceVersion_java_13() {
        data.sourceVersion = "13";
        return this;
    }
    
    /**
     * Specifies the source code path to search for class or interface
     * definitions. As with the user class path, source path entries are
     * separated by colons (:) on Oracle Solaris and semicolons on Windows and
     * can be directories, JAR archives, or ZIP archives. If packages are used,
     * then the local path NAME within the directory or archive must reflect the
     * package NAME.
     *
     * Note: Classes found through the class path might be recompiled when their
     * source files are also found. See Searching for Types.
     *
     * @param sourcePath
     * @return
     */
    public _javacOptions sourcePath(String sourcePath ) {
        data.sourcePath = sourcePath;
        return this;
    }

    /**
     * Uses verbose output, which includes information about each class loaded
     * and each source file compiled.
     *
     * @return
     */
    public _javacOptions verbose() {
        data.verbose = Boolean.TRUE;
        return this;
    }

    /**
     * Decide whether to set verbose output: verbose output, includes
     * information about each class loaded and each source file compiled.
     *
     * @param verbose
     * @return
     */
    public _javacOptions verbose(Boolean verbose ) {
        data.verbose = verbose;
        return this;
    }

    /**  */
    public _javacOptions X(String key ){
        data.dashXOptionKeyValues.put( key, null );
        return this;
    }

    /**
     * i.e.
     * _javacOptions _o = new _javacOptions().X("plugin", "MyPlugin")

     adds the Java Compiler Plugin option
     "-Xplugin:MyPlugin"
     */
    public _javacOptions X(String key, String value ){
        data.dashXOptionKeyValues.put( key, value );
        return this;
    }

    /**
     * -werror Terminates compilation when warnings occur.
     */
    public _javacOptions terminateOnWarning() {
        return terminateOnWarning( Boolean.FALSE );
    }

    /**
     * -werror Terminates compilation when warnings occur.
     */
    public _javacOptions terminateOnWarning(Boolean terminateOnWarning ) {
        data.terminateOnWarning = terminateOnWarning;
        return this;
    }

    /**
     * Writes target class bytecode for the Java 1.3 JVM The compiler does not
     * support assertions, generics, or other language features introduced after
     * Java SE 1.3.
     */
    public _javacOptions targetClassVersion_java1_3() {
        data.targetClassVersion = "1.3";
        return this;
    }

    /**
     * Writes target class bytecode for the Java 1.4 JVM The compiler accepts
     * code containing assertions, which were introduced in Java SE 1.4.
     */
    public _javacOptions targetClassVersion_java1_4() {
        data.targetClassVersion = "1.4";
        return this;
    }

    /**
     * Writes target class bytecode for the Java 5 JVM The compiler accepts code
     * containing generics and other language features introduced in Java SE 5.
     */
    public _javacOptions targetClassVersion_java5() {
        data.targetClassVersion = "5";
        return this;
    }

    /**
     * Writes target class bytecode targeting the Java 6 JVM No language changes
     * were introduced in Java SE 6. However, encoding errors in source files
     * are now reported as errors instead of warnings as in earlier releases of
     * Java Platform, Standard Edition.
     */
    public _javacOptions targetClassVersion_java6() {
        data.targetClassVersion = "6";
        return this;
    }

    /**
     * Writes target class bytecode for the Java 7(1.7) JVM
     *
     * @return
     */
    public _javacOptions targetClassVersion_java7() {
        data.targetClassVersion = "1.7";
        return this;
    }

    /**
     * Writes target class bytecode for the Java 8(1.8) JVM
     *
     * @return
     */
    public _javacOptions targetClassVersion_java8() {
        data.targetClassVersion = "8";
        return this;
    }

    /**
     * Writes target class bytecode for the Java 9(1.9) JVM
     *
     * @return
     */
    public _javacOptions targetClassVersion_java9() {
        data.targetClassVersion = "9";
        return this;
    }

    /**
     * Writes target class bytecode for the Java 10  JVM
     *
     * @return
     */
    public _javacOptions targetClassVersion_java10() {
        data.targetClassVersion = "10";
        return this;
    }
    
    
    /**
     * Writes target class bytecode for the Java 11 JVM
     *
     * @return
     */
    public _javacOptions targetClassVersion_java11() {
        data.targetClassVersion = "11";
        return this;
    }
    
    /**
     * Writes target class bytecode for the Java 12 JVM
     *
     * @return
     */
    public _javacOptions targetClassVersion_java12() {
        data.targetClassVersion = "12";
        return this;
    }

    /**
     * Writes target class bytecode for the Java 12 JVM
     *
     * @return
     */
    public _javacOptions targetClassVersion_java13() {
        data.targetClassVersion = "13";
        return this;
    }
    
    /** -Xprefer=source
     * When the compiler resolves a ".java" source file AND a ".class" file
     * that could be resolved as the class, choose the source file
     * @return
     */
    public _javacOptions preferSourceOnResolveConflict(){
        data.prefer = ClassResolvePrefer.SOURCE;
        return this;
    }

    /** -Xprefer=class
     * When the compiler resolves a ".java" source file AND a ".class" file
     * that could be resolved as the class, choose the class
     * @return
     */
    public _javacOptions preferClassOnResolveConflict(){
        data.prefer = ClassResolvePrefer.CLASS;
        return this;
    }

    /** -Xprefer=newest
     * When the compiler resolves a ".java" source file AND a ".class" file
     * that could be resolved as the class, choose the newer of the two
     * @return
     */
    public _javacOptions preferNewerOnResolveConflict(){
        data.prefer = ClassResolvePrefer.NEWER;
        return this;
    }

    public _javacOptions bootstrapPath(Path bootstrapPath ){
        data.bootstrapPath = bootstrapPath.toString();
        return this;
    }

    public _javacOptions bootstrapPath(String bootstrapPath ){
        data.bootstrapPath = bootstrapPath;
        return this;
    }

    public _javacOptions bootstrapPathSuffix(String bootstrapPathSuffix ){
        data.bootstrapPathSuffix = bootstrapPathSuffix;
        return this;
    }

    public _javacOptions bootstrapPathPrefix(String bootstrapPathPrefix ){
        data.bootstrapPathPrefix = bootstrapPathPrefix;
        return this;
    }

    public List<String> toOptions() {
        return this.data.toOptions();
    }

    public String toString(){
        StringBuilder sb = new StringBuilder();
        toOptions().forEach(o -> sb.append( o ).append(" ") );
        return sb.toString();
    }
}
