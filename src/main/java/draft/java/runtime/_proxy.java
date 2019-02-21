package draft.java.runtime;

import draft.DraftException;
import draft.java.*;
import draft.java.file._classFile;
import draft.java.macro._macro;

import javax.annotation.processing.Processor;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

/**
 * A mechanism and wrapper around a dynamically loaded Class instance
 *
 * Compile a {@link _class} model into an Class into a new {@link _classLoader} then create a
 * new instance (by reflectively calling the constructor) and return a proxy wrapper
 * with simple access for get, set and running METHODS
 *
 * @author M. Eric DeFazio
 */
public final class _proxy extends _new {

    /** the proxied instance of a class */
    public Object instance;

    /**
     * i.e.
     *
     * _proxy _noArgs = _proxy.of( "public class M{ public static int v = 1001; }" )
     * _proxy _arg = _proxy.of(
     "public class A{ "+
     "    public final int v;" +
     "    public A(int i){" +
     "        this.v = i;" +
     "    }" +
     "}",
     102 ); //constructor argument
     *
     * @param classDef the class definition
     * @param ctorArgs constructor ARGUMENTS
     * @return an _proxy containing a new _new of the class
     */
    public static _proxy of(String classDef, Object... ctorArgs ) {
        return of(_class.of( classDef ), ctorArgs );
    }

    public static _proxy of( Class clazz, Object...ctorArgs ){
        //return of( _type._class( clazz ), ctorArgs );
        return of( _class.of( clazz ), ctorArgs );
    }

    public static _proxy of(Processor annotationProcessor, _type _c, Object...ctorArgs ){
        return of(null,
                new _javacOptions(),
                annotationProcessor,
                _c,
                ctorArgs );
    }

    public static _proxy of(Processor annotationProcessor, _class _c, Object...ctorArgs ){
        return of( null, null, annotationProcessor, _c, ctorArgs );
    }

    public static _proxy of( Class clazz ){
        //_class _c = _class.of( clazz );
        //return of(_type.ProcessRuntimeAnnotations.process(clazz, _c));
        return of(_class.of(clazz ));
    }

    public static _proxy of(_type _c, Object... ctorArgs ) {
        return new _proxy( _new.of(_c, ctorArgs ) );
    }

    public static _proxy of(_project parent, _type _c, Object...ctorArgs ){
        return new _proxy( _new.of(parent, _c, ctorArgs ) );
    }

    public static _proxy of(_javacOptions compilerOpts, _type _c, Object...ctorArgs ){
        return new _proxy( _new.of(compilerOpts, _c, ctorArgs ));
    }

    public static _proxy of(_project parent, _javacOptions compilerOpts, _type _c, Object...ctorArgs ){
        return new _proxy( _new.of(parent, compilerOpts, _c, ctorArgs ));
    }

    public static _proxy of(_project parent, _javacOptions compilerOpts,
                            Processor annotationProcessor, _type _c, Object...ctorArgs ){
        return new _proxy( _new.of(parent, compilerOpts, annotationProcessor, _c, ctorArgs ));
    }

    public static _proxy of(_project parent, _javacOptions compilerOpts,
                            List<Processor> annotationProcessors, _type _c, Object...ctorArgs ){
        return new _proxy( _new.of(parent, compilerOpts, annotationProcessors, _c, ctorArgs ));
    }

    @Override
    public boolean equals( Object o ){
        if( !( o instanceof _proxy)){
            return this.instance.equals(o);
        }
        _proxy test = (_proxy)o;
        return this.instance.equals( test.instance );
    }

    @Override
    public int hashCode(){
        return instance.hashCode();
    }

    @Override
    public String toString(){
        return instance.toString();
    }

    /**
     * Build and return another new _proxy _new of the class
     * given the constructor args
     *
     * @param ctorArgs constructor args
     * @return a new _proxy
     */
    public _proxy _new(Object...ctorArgs ){
        Class c = instance.getClass();

        if( ctorArgs.length == 0 ) {
            try {
                Constructor ctor = c.getConstructor( new Class[ 0 ] );
                return new _proxy( _new.construct(ctor, ctorArgs )  );
            }
            catch( Exception e ) {
                throw new DraftException("unable to find constructor for " + c.getCanonicalName(), e );
            }
        }
        return new _proxy(
                tryAllCtors(c.getConstructors(), ctorArgs ) );
    }

    public _proxy( Object obj ) {
        this.instance = obj;
    }

    private static Object call(Object instance, Method m, Object... args ) {
        try {
            return m.invoke( instance, args );
        }
        catch( Exception ex ) {
            throw new DraftException("error invoking \"" + m.getName() + "\" on " + instance, ex );
        }
    }

    public Object get( Field F ) {
        try {
            return F.get( instance );
        }
        catch( IllegalArgumentException ex ) {
            throw new DraftException( "Illegal Arg", ex );
        }
        catch( IllegalAccessException ex ) {
            throw new DraftException( "Illegal Access", ex );
        }
    }

    /** return the _class of this _new*/
    public _class _class() {
        _classLoader _cl = (_classLoader)instance.getClass().getClassLoader();
        return (_class)_cl.get_type( instance.getClass().getCanonicalName() );
    }

    /** gets all classFiles that are created by this class and all nested classes */
    public _classFile _classFile(){
        _classLoader _cl = (_classLoader)instance.getClass().getClassLoader();
        return _cl._fileMan.classFiles.list( cf->  cf.getFullName().equals(this._class().getFullName()) ).get(0);
    }

    public Object set( Field F, Object value ){
        try {
            F.set( instance, value );
            return null;
        }
        catch( IllegalArgumentException ex ) {
            throw new DraftException( "Illegal Arg", ex );
        }
        catch( IllegalAccessException ex ) {
            throw new DraftException( "Illegal Access", ex );
        }
    }

    /**
     * calls the set(x) method or sets the VALUE of a field with the NAME
     * and return the _proxy
     * @param propertyName
     * @param value
     * @return
     */
    public _proxy set(String propertyName, Object value ){
        try {
            Field F = this.instance.getClass().getField( propertyName );

            if( F != null && (Modifier.isPublic( F.getModifiers() )) ) {
                set( F, value );
                return this;
            }
        }
        catch( DraftException e ) {
            //it's OK if this fails, we MIGHT need to call set...() method
        }
        catch( NoSuchFieldException ex ) {
            //it's OK if this fails, we MIGHT need to call set...() method
        }
        catch( SecurityException ex ) {
            //it's OK if this fails, we MIGHT need to call set...() method
        }

        Method[] ms = this.instance.getClass().getMethods();
        for( int i = 0; i < ms.length; i++ ) {
            if( Modifier.isPublic( ms[ i ].getModifiers() )
                    && ms[ i ].getParameterCount() == 1
                    && (ms[ i ].getName().equalsIgnoreCase( "set" + propertyName )) ) {
                call( instance, ms[ i ], new Object[ ]{value} );
                return this;
            }
        }
        throw new DraftException( "Could not find \"" + propertyName + "\"" );
    }

    /**
     * gets (via a reflection on a Field, OR by a get_type()...is()...method)
     * the VALUE of a property on the _new
     *
     * @param propertyName
     * @return
     */
    public Object get( String propertyName ) {
        try{
            Field F = this.instance.getClass().getField( propertyName );

            if( F != null && (Modifier.isPublic( F.getModifiers() )) ) {
                return get( F );
            }
        }catch( NoSuchFieldException e ) {
            //throw new DraftException(e);
        }
        //
        Method[] ms = this.instance.getClass().getMethods();
        for( int i = 0; i < ms.length; i++ ) {
            if( Modifier.isPublic( ms[ i ].getModifiers() )
                    && ms[ i ].getParameterCount() == 0
                    && (ms[ i ].getName().equalsIgnoreCase( "get" + propertyName )
                    || ms[ i ].getName().equalsIgnoreCase( "is" + propertyName )) ) {
                return call( instance, ms[ i ], new Object[ 0 ] );
            }
        }
        throw new DraftException( "Could not find \"" + propertyName + "\"" );
    }

    /** call the static main method on the _new with the (optional)
     string ARGUMENTS*/
    public void main( String...args ){
        if( args.length == 0 ){
            call("main", (Object)new String[0] );
        }
        else{
            call("main", (Object)args );
        }
    }

    /** call a method (_new or static) on this _new
     * <PRE>
     * note: the following works:
     * call( "hashCode");
     * call("hashCode;");
     * call("hashCode()");
     * call("hashCode();");
     *
     * call("withParams", 1, "e");
     * call("withParams()", 1, "e");
     * call("withParams(x, y)", 1, "e");
     * </PRE>
     * @param methodName
     * @param args
     * @return
     */
    public Object call(String methodName, Object... args ) {
        methodName = methodName.trim();
        if( methodName.endsWith(";") ){
            methodName = methodName.substring(0, methodName.length() -1);
        }
        int openIndex = methodName.indexOf('(');
        if( openIndex > 0){
            methodName = methodName.substring(0, openIndex );
        }
        Method[] ms = instance.getClass().getDeclaredMethods();
        if( ms.length == 0 ) {
            throw new DraftException(
                    "Could not find method with NAME \"" + methodName + "\" on " + instance );
        }
        if( ms.length == 1 ) {
            if( ms[0].getName() != methodName ){
                throw new DraftException( "Could not find method \""+methodName+"\"" );
            }
            return call( instance, ms[ 0 ], args );
        }
        //more than one...
        DraftException exception = null;
        for( int i = 0; i < ms.length; i++ ) {
            Method m = ms[ i ];

            if( m.getName().equals( methodName )
                    && (m.getParameterCount() == args.length
                    || (args.length > m.getParameterCount() && m.isVarArgs())) ) {
                try {
                    return call( instance, m, args );
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
