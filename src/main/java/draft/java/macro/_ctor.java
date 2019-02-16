package draft.java.macro;

//import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.TypeDeclaration;
import draft.DraftException;
import draft.java.Walk;
import draft.java._constructor;
import draft.java._method;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

/**
 * Annotation _macro to convert a _method to a _constructor (removing the
 * _method from its container and adding a _constructor)
 *
 * useful when the BODY of a _class or _enum is defined using an anonymous object
 * and (since anonymous objects don't have CONSTRUCTORS) for example:
 * <PRE>
 *     _class _c = _class.of("aaaa.bbbb.C", new Object(){
 *         public @_final int x;
 *
 *         public @_ctor void C( int x){
 *             this.x = x;
 *         }
 *     }
 *     // _c will be:
 *     package aaaa.bbbb;
 *     public class C{
 *         public final int x;
 *
 *         public C( int x){
 *             this.x = x;
 *         }
 *     }
 * </PRE>
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface _ctor {

    class Macro implements _macro<_method>{

        @Override
        public _method apply(_method _m) {
            _m.removeAnnos(_ctor.class);
            List<TypeDeclaration>tds = new ArrayList<>();
            Walk.parents( _m, TypeDeclaration.class, t-> tds.add(t) );
            if( ! (tds.size() > 0 )){
                throw new DraftException("no TypeDeclaration parent for "+_m+" to convert to constructor ");
            }
            TypeDeclaration astParentType = tds.get(0);

            _constructor _ct = _constructor.of( _m.getModifiers()+" "+ astParentType.getName()+"(){}");
            _m.forParameters( p-> _ct.addParameter(p) );
            if( _m.hasTypeParameters()){
                _ct.setTypeParameters( _m.getTypeParameters() );
            }
            if( _m.hasAnnos() ) {
                _ct.annotate(_m.ast().getAnnotations() );
            }
            _ct.setBody( _m.getBody() );
            if( _m.hasJavadoc() ){
                _ct.ast().setJavadocComment(_m.ast().getJavadocComment().get());
            }
            astParentType.addMember( _ct.ast() );
            boolean removed = astParentType.remove( _m.ast() );
            if( ! removed ){
                throw new DraftException("Unable to remove "+_m+" from parent TYPE");
            }
            //note this is dangerous, seeing as _m is removed... but we'll return it
            return _m;
        }
    }
}
