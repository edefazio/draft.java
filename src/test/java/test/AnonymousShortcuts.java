package test;

import draft.java._class;
import draft.java._constructor;
import draft.java._field;
import draft.java._method;
import draft.java.macro._ctor;
import draft.java.macro._final;
import draft.java.macro._remove;
import draft.java.macro._static;
import draft.java.proto.$constructor;
import draft.java.proto.$field;
import draft.java.proto.$method;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Map;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class AnonymousShortcuts extends TestCase {
    
    /**
     * 
     */
    public void testAnonymousMethod(){
        
        //create a method via an anonymous class
        _method _m = _method.of( new Object(){
            /** Javadoc*/
            @Deprecated
            public void m(){
                System.out.println( "Hello");
            }
        });
        
        System.out.println( _m );
        
        //create a method prototype via Anonymous class
        $method $m = $method.of( new Object(){
            /** Javadoc */
            @Deprecated
            public void m(){
                System.out.println("Hello");
            }
        });
        //todo create a toString for $method
        System.out.println( $m );
        System.out.println( $m.construct() );        
    }
    
    public void testAnonymousField(){
        _field _f = _field.of( new Object(){
            /** Javadoc */
            @Deprecated
            public static final int a = 100 + 20;
        });
        //ensure the field has the anno, the javadoc and the init expression
        assertEquals( _f, _field.of("/** Javadoc */ @Deprecated public static final int a = 100 + 20;" ) );
        
        $field $f = $field.of( new Object(){
            /** Javadoc */
            @Deprecated
            public static final int a = 100 + 20;
        }); 
        
        assertEquals( $f.construct(), _field.of("/** Javadoc */ @Deprecated public static final int a = 100 + 20;" ) );
    }
    
    public void testAnonymousConstructor(){
        _constructor _c = _constructor.of( new Object(){
            private int a, b;
            
            /**
             * Some Javadoc 
             * @param a
             * @param b
             */
            @Deprecated
            public void C(int a, final int b){
                this.a = a;
                this.b = b;
            }
        });        
        System.out.println( _c );
        $constructor $c = $constructor.of( new Object(){
            private int a, b;
            
            /**
             * Some Javadoc 
             * @param a
             * @param b
             */
            @Deprecated
            public void C(int a, final int b){
                this.a = a;
                this.b = b;
            }
        });        
        System.out.println( $c.construct() );        
    }
    
    /**
     * Instead of "one at a time" you can create the body and members
     * of a _class as 
     */
    public void testAnonymousClassBodyMembers(){
        _class _c = _class.of("Anon", new Object(){
            private int a;
            private String name = "Default";
            
            public @_static @_final Map someStatic;
            
            /** a constructor */
            @Deprecated
            @_ctor public void m( int a, String name) throws IOException{
                this.a = a;
                this.name = name;
            }
            
            /** a static method */
            @Deprecated
            public @_static void g() throws FileNotFoundException {
                System.out.println("hello");
            }            
        });
        System.out.println( _c.getField("someStatic"));
        assertTrue( _c.getField("someStatic").is("public static final Map someStatic;") );
        assertTrue( _c.hasImport(Map.class)); //ohh... also, we read the public API and "auto import" the appropriate classes
        assertTrue( _c.getMethod("g").isStatic());
        assertTrue( _c.getMethod("g").hasThrow(FileNotFoundException.class));
        
        System.out.println(_c);
    }
    
    public interface ToImplement{
        public boolean implementedThisMethod();
    }
    
    public void testAnonymousImplementer(){
        _class _c = _class.of("AnonymousImplementer", new ToImplement(){
            public static final boolean implemented = true;
            
            @_remove private int someValueIDontWant;
                    
            /** The implementation method will be transposed */
            @Override
            public boolean implementedThisMethod() {
                return this.implemented;
            }            
            
            public @_static void anotherIncludedMethod(){
                System.out.println( "It's transposed");
            }
        });
        
        assertTrue( _c.isImplementer(ToImplement.class));
        assertTrue( _c.getField("implemented").is("public static final boolean implemented = true;") );
        assertTrue( _c.getMethod("implementedThisMethod").hasAnno(Override.class) );
        assertTrue( _c.getMethod("anotherIncludedMethod").isStatic());
        assertNull( _c.getField("someValueIDontWant")); 
    }
}
