/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java;

import com.github.javaparser.ast.Modifier;
import com.github.javaparser.ast.body.FieldDeclaration;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class _modifiersTest extends TestCase {


    public enum E{
        ;
        public static final int a = 1;
    }
    public enum F{
        ;
        public int a = 1;
    }

    public abstract class C{
        int a;
    }
    public abstract class C2{
        int a;
    }
    public interface I{
        int a = 1;
        public static int m(){
            return 1;
        }

        public default int d(){
            return 1;
        }
    }
    public interface I2{
        public static final int a = 1;

        static int m(){
            return 1;
        }
        default int d(){ //default methods are public
            return 1;
        }
    }

    public void testM()
            throws NoSuchFieldException, NoSuchMethodException {

        System.out.println( I.class.getDeclaredField("a").getModifiers()+ " " +
                I2.class.getDeclaredField("a").getModifiers());

        //a static method on an interface has public
        System.out.println( I.class.getDeclaredMethod("m").getModifiers()+ "  " +
                I2.class.getDeclaredMethod("m").getModifiers());

        System.out.println( I.class.getDeclaredMethod("d").getModifiers()+ "  " +
                I2.class.getDeclaredMethod("d").getModifiers());

        //System.out.println( E.class.getDeclaredField("a").getModifiers()+ " " +
        //        F.class.getDeclaredField("a").getModifiers());
    }

    /*
    public void testImpliedModifiers(){
        // field on interface
        _interface _i = _interface.of(I.class);
        _interface _i2 = _interface.of(B.class).name("I");

        assertEquals( _i, _i2);

        // method on interface
        // default method on interface
        // _field _f1 = _field.of( new Object(){ int x; } );
    }
    */

    public void testAccess(){
        FieldDeclaration fd = Ast.field("public static final int E = 1002;");
        assertTrue( fd.isPublic() );
        fd.setPrivate( true );
        
        assertFalse( fd.getModifiers().contains( Modifier.abstractModifier() ));
        assertTrue( fd.getModifiers().contains( Modifier.publicModifier() ));
        
        _modifiers _m = _modifiers.of( fd );
        assertTrue( _m.isPublic() && _m.isStatic() && _m.isFinal());
        assertFalse( _m.isAbstract() );
        
        //make sure when I set Private, I UNSET public  or protected
        _m.set( Modifier.privateModifier() );
        assertTrue( _m.isPrivate() && _m.isStatic() && _m.isFinal());
        assertFalse( _m.isPublic() );
        
        //will it allow me to do this? YES (although no field can be native)
        _m.set( Modifier.nativeModifier() );
        
        assertTrue( _m.isPrivate() && _m.isStatic() && _m.isFinal() && _m.isNative() ); 
        
        System.out.println( fd );
    }


    public void testSetUnset(){
        _modifiers _ms = _modifiers.of();
        _ms.set( Modifier.abstractModifier() );
        assertTrue( _ms.isAbstract() );
        _ms.unset( Modifier.abstractModifier());
        assertTrue( !_ms.isAbstract() );

        _ms.set( Modifier.finalModifier() );
        assertTrue( _ms.isFinal() );
        _ms.unset( Modifier.finalModifier() );
        assertTrue( !_ms.isFinal() );

        _ms.set( Modifier.nativeModifier() );
        assertTrue( _ms.isNative());
        _ms.unset( Modifier.nativeModifier() );
        assertTrue( !_ms.isNative() );

        _ms.set( Modifier.privateModifier() );
        assertTrue( _ms.isPrivate() );
        _ms.unset( Modifier.privateModifier() );
        assertTrue( !_ms.isPrivate() );

        _ms.set( Modifier.protectedModifier() );
        assertTrue( _ms.isProtected() );
        _ms.unset( Modifier.protectedModifier() );
        assertTrue( !_ms.isProtected() );

        _ms.set( Modifier.publicModifier() );
        assertTrue( _ms.isPublic() );
        _ms.unset( Modifier.publicModifier() );
        assertTrue( !_ms.isPublic() );

        _ms.set( Modifier.staticModifier() );
        assertTrue( _ms.isStatic() );
        _ms.unset( Modifier.staticModifier() );
        assertTrue( !_ms.isStatic() );

        _ms.set( Modifier.strictfpModifier() );
        assertTrue( _ms.isStrict());
        _ms.unset( Modifier.strictfpModifier() );
        assertTrue( !_ms.isStrict() );

        _ms.set( Modifier.synchronizedModifier() );
        assertTrue( _ms.isSynchronized());
        _ms.unset( Modifier.synchronizedModifier() );
        assertTrue( !_ms.isSynchronized() );

        _ms.set( Modifier.transientModifier() );
        assertTrue( _ms.isTransient() );
        _ms.unset( Modifier.transientModifier() );
        assertTrue( !_ms.isTransient() );

        _ms.set( Modifier.volatileModifier() );
        assertTrue( _ms.isVolatile() );
        _ms.unset( Modifier.volatileModifier() );
        assertTrue( !_ms.isVolatile() );

    }

    public void testIs(){
        _modifiers _ms = _modifiers.of();
        assertTrue( _ms.is());
    }
    public void testSetOrUnset(){
        _modifiers _mods = _modifiers.of("public");
        assertFalse( _mods.isAbstract() );
        _mods.setAbstract();
        assertTrue( _mods.isAbstract() );
        _mods.setAbstract(false);
        assertFalse( _mods.isAbstract() );
    }


    public void testSetDefault() {
        _field _f = _field.of("public static int F = 100;");
        _f.setDefaultAccess(); //.getModifiers().setDefaultAccess();
        assertEquals( _field.of("static int F = 100;"), _f );

        _modifiers _mods = _modifiers.of();

    }

    public void testN(){
        _modifiers _ms  = _modifiers.of( "public", "static", "final");
        assertEquals("public static final", _ms.toString() );

        _ms  = _modifiers.of( "public static final");
        assertEquals("public static final", _ms.toString() );

        _ms  = _modifiers.of( Modifier.publicModifier(), Modifier.abstractModifier() );
        assertEquals("public abstract", _ms.toString() );
    }
    
}
