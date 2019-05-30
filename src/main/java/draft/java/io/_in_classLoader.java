package draft.java.io;

import draft.java._annotation;
import draft.java._class;
import draft.java._enum;
import draft.java._type;
import java.io.ByteArrayInputStream;
import java.lang.annotation.Annotation;

/**
 * Since we maintain the {@link _type}s in memory when we compile and load a new
 * {@link _typeCacheClassLoader} (i.e. a {@link draft.java.runtime._classLoader} the
 * .java source code is not compiled from a file,
 * but rather from the serialized version of the {@link _type}), we can return
 * the cached version of the _type
 *
 *
 * <PRE>
 * //we can MANUALLY lookup the _type by getting it from the _classLoader
 * //create a new Project with a _type (compile/load)
 * _project _p = _project.of( _class.of("aaaa.bbbb.cc.D") );
 *
 * //get a handle to the Class
 * Class clazz = _p.getClass( "aaaa.bbbb.cc.D");
 *
 * //we KNOW the class is loaded via a _classLoader
 * _classLoader _cl = (_classLoader) clazz.getClassLoader();
 *
 * _type _t = _cl.get_type(clazz);
 *
 *
 * //in( this will chekc clazz's _classLoader, which contains a reference
 * // to _type "aaaa.bbbb.cc.D"
 *
 * _in _javaSrc = _in_classLoader.in( clazz );
 * assertNotNull( _javaSrc );
 * </PRE>
 * @author Eric
 */
public final class _in_classLoader implements _in._resolver {

    public static final _in_classLoader INSTANCE = new _in_classLoader();

    @Override
    public _in resolve( String sourceId ) {
        return null; /*can't help you if you don't have the Class*/
    }

    @Override
    public _in resolve( Class clazz ) {
        if( clazz.getClassLoader() instanceof _typeCacheClassLoader ){
            _typeCacheClassLoader _cl = (_typeCacheClassLoader)clazz.getClassLoader();
            _type _t = _cl.get_type( clazz );
            if( _t != null ){
                return _in._source.of(
                    //hmm we dont have a Path... it's a cached file    
                    clazz.getCanonicalName()+".java",
                    "_classLoader cached .java file : \""+clazz.getCanonicalName()+".java\"",
                    new ByteArrayInputStream( _t.toString().getBytes() ) );
            }
        }
        return null;
    }

    @Override
    public String describe() {
        return "[_classLoader in-memory]";
    }

    /**
     * Interface for a ClassLoader instance that is also a cache for _type
     * (an example is {@link draft.java.runtime._classLoader}
     * instances ( i.e. it stores a Class AND it's _class model)
     */
    public interface _typeCacheClassLoader {

        <T extends _type> T get_type( Class clazz );

        _class get_class(Class clazz );

        _annotation get_annotation(Class<? extends Annotation> clazz );

        _enum get_enum(Class<? extends Enum> clazz );
    }
}