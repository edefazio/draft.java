/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package whybuildonjavaparser;

import draft.java.Walk;
import draft.java._anno;
import draft.java._class;
import draft.java._java;
import draft.java.macro._macro;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import junit.framework.TestCase;
import static junit.framework.TestCase.assertFalse;
import static junit.framework.TestCase.assertTrue;

/**
 *
 * @author Eric
 */
public class _2_MacroUseTest extends TestCase {
    
      /** How hard is it to build and use your own annotation macro?? */
    
    /** 1) MAKE SURE it's RetentionPolicy Runtime! */    
    @Retention(RetentionPolicy.RUNTIME)
    /** 1a) you can (OPTIONALLY) specify where this annotation applies*/
    @Target({ElementType.METHOD, ElementType.CONSTRUCTOR, ElementType.FIELD, ElementType.TYPE})
    public @interface _removeAllDeprecated{
    
        
        public static final macro $ = new macro();
        
        /** 2) HAVE a nested class under the annotation that implements _macro
         * This particular macro can be applied (via an annotation) to any "target"
         * (_class, _method, _field, _constructor,...)
         */
        public static class macro implements _macro<_anno._hasAnnos>{
            
            /** 
             * 2a) this method will remove all @Deprecated annotations at (or below)
             * the t node instance passed in
             * @param t
             * @return the modified t
             */
            @Override
            public _anno._hasAnnos apply(_anno._hasAnnos t) {                
                // Walk does a (preorder) AST.walk including walking via the
                // _node draft classes, here we intercept all _hasAnnos 
                // (nodes which may have _annos) and remove the Deprecated _anno
                return (_anno._hasAnnos)Walk.in( 
                    (_java._node)t, _anno._hasAnnos.class, ha->ha.removeAnnos(Deprecated.class));
            }            
        }
    }
    
    //
    public void testMyMacroAndWalk(){
        
        /** Heres an example class that has many Deprecated annotations */
        @_removeAllDeprecated class A{             
            /** A field */ @Deprecated int a=1;
            /** a constructor */ @Deprecated A(){}
            /** a method*/ @Deprecated void m(){}
            /** Nested class */
            @Deprecated class N{
                @Deprecated public static final int ID=102;
            }
        }        
        // 3) when we convert from a runtime Class to a _class, we located/run 
        //    and remove macro annotations
        _class _c = _class.of(A.class); //(on creation, macros remove their @annos)
        assertFalse(_c.hasAnnos());//verify @_removeAllDeprecated is removed
        
        //Here manually verify that all Deprecated annotations are removed
        assertFalse( _c.getField("a").hasAnno(Deprecated.class));
        assertFalse( _c.getConstructor(0).hasAnno(Deprecated.class));
        assertFalse( _c.getMethod("m").hasAnno(Deprecated.class));        
        assertFalse( _c.getNest("N").hasAnno(Deprecated.class));
        assertFalse( _c.getNest("N").getField("ID").hasAnno(Deprecated.class));
        
        // the above code is boring, let's use Walk INSTEAD to verify there are
        // NO annotated elements within _c that have the Deprecated annotation
        assertTrue(Walk.list(_c,_anno._hasAnnos.class,ha-> ha.hasAnno(Deprecated.class)).isEmpty());
    }
    
}
