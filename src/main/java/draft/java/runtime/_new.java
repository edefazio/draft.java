package draft.java.runtime;

import draft.DraftException;
import draft.java._class;
import draft.java._type;
import draft.Translator;

import javax.annotation.processing.Processor;
import java.lang.reflect.*;
import java.util.*;

/**
 * Creates new instances by compiling /loading (into a new {@link _classLoader})
 * and calling CONSTRUCTORS on compiled classes
 *
 * @author Eric
 */
public class _new {

    /**
     * <UL>
     *  <LI>create a new _class given the code
     *  <LI>compile the _class into a new Class in a new _project/ClassLoader
     *  <LI>call the constructor on the new Class passing in the ctorArgs
     *  <LI>returns the constructed instance of the Class
     * </UL>
     * @param classDef
     * @param ctorArgs
     * @return
     */
    public static Object of( String classDef, Object...ctorArgs ){
        return of( _class.of(classDef), ctorArgs );
    }


    public static Object of(Processor annotationProcessor, _type _t, Object...ctorArgs ){
        return of( null, null, annotationProcessor, _t, ctorArgs );
    }

    /**
     * Compiles / Loads (into a new ClassLoader) & returns a new instance of a
     * <UL>
     *  <LI>compile the _class into a new Class in a new _project/ClassLoader
     *  <LI>call the constructor on the new Class passing in the ctorArgs
     *  <LI>returns the constructed instance of the Class
     * </UL>
     * @param _t the TYPE to _1_build
     * @param ctorArgs
     * @return
     */
    public static Object of(_type _t, Object...ctorArgs ){
        _project _p = _project.of( _t );
        return _p._new( _t.getFullName(), ctorArgs );
    }

    /**
     * Build a new instance using the parent classes as the foundation
     * (the class will be loaded into a child ClassLoader under the
     * parent's ClassLoader
     *
     * @param parent the Base Project (may contain classes needed to compile
     * @param _t the TYPE to be _1_build (likely a _class)
     * @param ctorArgs the constructor args to create a new instance (after the
     * class is compiled and loaded)
     * @return the new instance (loaded in a new child ClassLoader off of baseProject)
     */
    public static Object of(_project parent, _type _t, Object...ctorArgs){
        return of(parent, new _javacOptions(), _t, ctorArgs );
    }

    /**
     * Build a new instance using the _parentProject classes as the foundation
     * (the class will be loaded into a child ClassLoader under the
     * _parentProject's ClassLoader
     *
     * @param _compilerOpts javac compiler options to pass to the compiler
     * @param _t the TYPE to be _1_build (likely a _class)
     * @param ctorArgs the constructor args to create a new instance (after the
     * class is compiled and loaded)
     * @return the new instance (loaded in a new child ClassLoader off of baseProject)
     */
    public static Object of(_javacOptions _compilerOpts, _type _t, Object...ctorArgs){
        return of(null, _compilerOpts, _t, ctorArgs );
    }

    /**
     * Build a new instance using the parent classes as the foundation
     (the class will be loaded into a child ClassLoader under the parent's
     ClassLoader)
     *
     * @param _compilerOpts javac compiler options to pass to the compiler
     * @param parent the Base Project (may contain classes needed to compile
     * @param annotationProcessor an annotation Processor
     * @param _t the TYPE to be _1_build (likely a _class)
     * @param ctorArgs the constructor args to create a new instance (after the
     * class is compiled and loaded)
     * @return the new instance (loaded in a new child ClassLoader off of baseProject)
     */
    public static Object of(_project parent, _javacOptions _compilerOpts,
                            Processor annotationProcessor, _type _t, Object... ctorArgs){

        List<Processor>annProc = new ArrayList<Processor>();
        annProc.add( annotationProcessor);
        return of(parent, _compilerOpts, annProc, _t, ctorArgs );
    }

    /**
     * Build a new instance using the parent classes as the foundation
     (the class will be loaded into a child ClassLoader under the parent's
     ClassLoader)
     *
     * @param _compilerOpts javac compiler options to pass to the compiler
     * @param parent the Base Project (may contain classes needed to compile
     * @param _t the TYPE to be _1_build (likely a _class)
     * @param ctorArgs the constructor args to create a new instance (after the
     * class is compiled and loaded)
     * @return the new instance (loaded in a new child ClassLoader off of baseProject)
     */
    public static Object of(
            _project parent,
            _javacOptions _compilerOpts,
            _type _t,
            Object... ctorArgs){

        List<Processor>p = new ArrayList<Processor>();

        return of(parent, _compilerOpts, p, _t, ctorArgs );
    }

    /**
     * Build a new instance using the parent classes as the foundation
     (the class will be loaded into a child ClassLoader under the parent's
     ClassLoader)
     *
     * @param _compilerOpts javac compiler options to pass to the compiler
     * @param parent the Base Project (may contain classes needed to compile
     * @param annotationProcessors a listAt of annotation processors
     * @param _t the TYPE to be _1_build (likely a _class)
     * @param ctorArgs the constructor args to create a new instance (after the
     * class is compiled and loaded)
     * @return the new instance (loaded in a new child ClassLoader off of baseProject)
     */
    public static Object of(
            _project parent,
            _javacOptions _compilerOpts,
            List<Processor>annotationProcessors,
            _type _t,
            Object... ctorArgs){

        //_1_build/compile the adHocClass
        Class adHocClass = _project.of(parent,
                _compilerOpts,
                annotationProcessors,
                _t )
                .getClass( _t.getFullName() );

        //call constructor with args
        if( ctorArgs.length == 0 ) {
            try {
                //uhh this seems wrong... no its a no-arg constructor
                Constructor ctor = adHocClass.getConstructor( new Class[ 0 ] );
                return construct( ctor, ctorArgs );
            }
            catch( Exception e ) {
                throw new DraftException("unable to find constructor for " + _t.getFullName(), e );
            }
        }
        return tryAllCtors( adHocClass.getConstructors(), ctorArgs );
    }


    public static Object construct( Constructor ctor, Object... ctorArgs ) {
        try {
            return ctor.newInstance(ctorArgs );
        }
        catch( InstantiationException ex ) {
            throw new DraftException( "Instantiation Exception ", ex );
        }
        catch( IllegalAccessException ex ) {
            throw new DraftException( "Illegal Access ", ex );
        }
        catch( IllegalArgumentException ex ) {
            throw new DraftException( "Illegal Argument ", ex );
        }
        catch( InvocationTargetException ex ) {
            throw new DraftException( "Exception calling constructor", ex.getCause() );
        }
    }

    /**
     * Brute force, just try of ctors that accept the number compile
     * PARAMETERS (silently fail and return on success)
     * @param ctors
     * @param ctorArgs
     * @return
     */
    public static Object tryAllCtors( Constructor[] ctors, Object... ctorArgs ) {
        //should I organize the CONSTRUCTORS by largest number compile parameterrs first

        //(just in case I have multiple VarArg variants and I dont choose the wrong one
        //(with fewer ARGUMENTS?)
        // i.e
        //First organize CONSTRUCTORS by parameter count:
        //List<Constructor> ctorsByParams = new ArrayList<Constructor>();
        PriorityQueue<Constructor> pq
                = new PriorityQueue<Constructor>( new Comparator() {
            //Organize the CONSTRUCTORS largest # compile PARAMETERS first
            public int compare( Object o1, Object o2 ) {
                Constructor c1 = (Constructor)o1;
                Constructor c2 = (Constructor)o2;
                return c2.getParameterCount() - c1.getParameterCount();
            }
        } );

        //accept of the CONSTRUCTORS export the priorityQueue ordering them
        for( int i = 0; i < ctors.length; i++ ) {
            pq.add( ctors[ i ] );
        }
        //now that the ctors are organized...
        ctors = pq.toArray( new Constructor[ 0 ] );

        for( int i = 0; i < ctors.length; i++ ) {
            if( ctors[ i ].getParameterCount() == ctorArgs.length
                    || (ctors[ i ].isVarArgs() && ctors[ i ].getParameterCount() <= ctorArgs.length) ) {
                try {
                    return ctors[ i ].newInstance(ctorArgs );
                }
                catch( InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException ex ) {
                    //dont catch
                }
            }
        }
        throw new DraftException( "Unable to find a public Constructor to accept \""
                + ctors[ 0 ].getName() + "\"\n"
                + Translator.DEFAULT_TRANSLATOR.translate(ctorArgs ) );
    }

    public static Object invokeStatic(Class targetClass, Method m, int[] args ) {
        try {
            //System.out.println( "METHOD " + m + " "+m.isVarArgs());
            if( m.isVarArgs() ){
                //System.out.println( "VA METHOD " + m + " "+m.isVarArgs());
                Class[] pts = m.getParameterTypes();
                if( pts[pts.length-1].isPrimitive() ){
                    //need to convert
                }
                return m.invoke( null, (Object)args );
            }
            return m.invoke( null, args );
        }
        catch( Exception ex ) {
            throw new DraftException("error invoking \"" + m.getName() + "\" on " + targetClass, ex );
        }
    }

    public static Object invokeStatic(Class targetClass, Method m, Object... args ) {
        try {
            return m.invoke( null, args );
        }
        catch( Exception ex ) {
            throw new DraftException("error invoking \"" + m.getName() + "\" on " + targetClass, ex );
        }
    }

    public static Object invokeStatic( Class targetClass, String methodName, int[] args ) {
        Method[] ms = targetClass.getMethods();
        if( ms.length == 0 ) {
            throw new DraftException("No method with NAME \"" + methodName + "\" on " + targetClass );
        }
        if( ms.length == 1 ) {
            if( ms[0].getName() != methodName ){
                throw new DraftException("No method with NAME \"" + methodName + "\" on " + targetClass );
            }
            return invokeStatic( targetClass, ms[ 0 ], args );
        }
        //more than one...
        DraftException exception = null;
        for( int i = 0; i < ms.length; i++ ) {
            Method m = ms[ i ];

            if( m.getName().equals( methodName )
                    && (m.getParameterCount() == args.length
                    || (args.length > m.getParameterCount() && m.isVarArgs())) ) {
                try {
                    return invokeStatic( targetClass, m, args );
                }
                catch( DraftException de ) {
                    exception = de;
                }
            }
        }
        if( exception == null ){
            throw new DraftException("could not find method \""+methodName +"\"");
        }
        throw exception;
    }

    public static Object invokeStatic( Class targetClass, String methodName, Object... args ) {
        int indexOfP = methodName.indexOf('(');
        if( indexOfP > 0 ){
            methodName = methodName.substring(0, indexOfP );
        }
        Method[] ms = targetClass.getMethods();
        if( ms.length == 0 ) {
            throw new DraftException("No method with NAME \"" + methodName + "\" on " + targetClass );
        }
        if( ms.length == 1 ) {
            if( ms[0].getName() != methodName ){
                throw new DraftException("No method with NAME \"" + methodName + "\" on " + targetClass );
            }
            return invokeStatic( targetClass, ms[ 0 ], args );
        }
        //more than one...
        DraftException exception = null;
        for( int i = 0; i < ms.length; i++ ) {
            Method m = ms[ i ];

            if( m.getName().equals( methodName )
                    && (m.getParameterCount() == args.length
                    || (args.length > m.getParameterCount() && m.isVarArgs())) ) {
                try {
                    return invokeStatic( targetClass, m, args );
                }
                catch( DraftException de ) {
                    exception = de;
                }
            }
        }
        if( exception == null ){
            throw new DraftException("could not find method \""+methodName +"\"");
        }
        throw exception;
    }
}
