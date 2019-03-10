/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java;

import draft.java.macro.*;
import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.nodeTypes.NodeWithSimpleName;
import junit.framework.TestCase;
import test.ComplexClass;

import java.io.Serializable;
import java.util.*;

/**
 * Resolveable
 * Addressable
 * Identifyable
 * getIdentifier()
 * @author Eric
 */
public class _javaTest extends TestCase {


    class Base{

    }

    class RException extends RuntimeException{

    }

    public void testWalkAnonymousObjectWithReflection(){
        Set<Class> classes = walkAnnon( new Serializable(){});
        assertTrue( classes.contains(Serializable.class));

        classes = walkAnnon( new Base(){});
        assertTrue( classes.contains(Base.class));

        classes = walkAnnon( new Object(){ //field types
            List<String> field;
        });
        assertTrue( classes.contains(List.class));

        classes = walkAnnon( new Object(){ //method return type
            Map m(){ return null; }
        });
        assertTrue( classes.contains(Map.class));

        classes = walkAnnon( new Object(){ //method parameter type
            void m(Map m){  }
        });
        assertTrue( classes.contains(Map.class));

        classes = walkAnnon( new Object(){ //method parameter type
            void m(Map m, List l){  }
        });
        assertTrue( classes.contains(Map.class));
        assertTrue( classes.contains(List.class));

        classes = walkAnnon( new Object(){ //method parameter type
            void m(Map m, List l) throws RException {  }
        });
        assertTrue( classes.contains(RException.class));
    }



    public static Set<Class> walkAnnon(Object o){
        Set<Class>classes = new HashSet<>();
        //implemented interfaces
        Arrays.stream(o.getClass().getInterfaces()).forEach(c-> classes.add(c));
        //super class if not Object
        if( !o.getClass().getSuperclass().equals(Object.class )){
            classes.add(o.getClass().getSuperclass() );
        }
        // field types
        Arrays.stream(o.getClass().getDeclaredFields()).forEach( f-> {
            if( f.getAnnotation(_remove.class) == null ) {
                classes.add(f.getType());
            }
        } );

        //constructors
        Arrays.stream(o.getClass().getDeclaredConstructors()).forEach( c -> {
            if( c.getAnnotation(_remove.class) == null ) {
                Arrays.stream(c.getParameterTypes()).forEach(p -> classes.add(p));
                Arrays.stream(c.getExceptionTypes()).forEach(p -> classes.add(p));
            }
        });
        //methods
        Arrays.stream(o.getClass().getDeclaredMethods()).forEach( m -> {
            if( m.getAnnotation(_remove.class) == null ) {
                classes.add(m.getReturnType());
                Arrays.stream(m.getParameterTypes()).forEach(p -> classes.add(p));
                Arrays.stream(m.getExceptionTypes()).forEach(p -> classes.add(p));
            }
        });
        return classes;
    }

    public class BaseClass{

    }

    public void testG( ) {

        @_promote("aaaa.bbbb")
        @_final
        //@_static
        @_implement(Serializable.class)
        @_extend(BaseClass.class)
        @_replace({" OldName ", " NewName "})
        class OldName{
            @_static
            @_final
            @_replace({"Hello", "Goodbye"})
            public String Message = "Hello";
        }
        //creating a class this way DOES NOT process the ANNOTATIONS
        _class _c = _class.of(OldName.class);
        
        assertFalse( _c.getAnnos().contains(_promote.class));
        assertFalse( _c.getAnnos().contains(_final.class));
        
        _class _c2 = _class.of( Ast.type( OldName.class) );

        assertTrue( _c2.getAnnos().contains(_promote.class));
        assertTrue( _c2.getAnnos().contains(_final.class));
        
        //assertTrue( _c.getAnnos().contains(_static.class));

        _c.getField("Message").getAnnos().contains(_final.class);

        //calling this way WILL PROCESS the ANNOTATIONS
        _c = _class.of(OldName.class);

        System.out.println( _c );

        assertTrue( _c.getModifiers().is("public", "final"));
        _c.hasImplements(Serializable.class);
        _c.isExtends(BaseClass.class);
        assertEquals( "aaaa.bbbb", _c.getPackage());

        _c.getField("Message").getModifiers().is("public", "static", "final");
        assertEquals( "Goodbye", _c.getField("Message").getInit().asStringLiteralExpr().asString()) ;
    }



    public void testAutoCtor(){
        @_promote("aaaa")
        @_autoConstructor
        class C{
            //@_final
            int x,y,z;
        }
        _class _c = _class.of(C.class);

        System.out.println(_c);
    }

    public void testFinal(){
        @_promote
        @_final
        class C{
            int x,y,z;
        }
        _class _c = _class.of(C.class);
        System.out.println(_c);
    }

    /**
     * this is an issue with the framework dealing with VarDecl and FieldDecl
     *  so I need to write some one off code that deals with it
     */
    public void testAnnoOnMultipleFields(){
        class C{
            @_public int x,y,z;
        }
        _class _c = _class.of(C.class);
        System.out.println(_c);
    }

    public void testF( ) {

        @_promote("aaaa.bbbb")
        @_final
        @_implement(Serializable.class)
        @_extend(BaseClass.class)
        @_replace({"C", "D"})
        class C{
            @_static
            @_final
            @_replace({"Hello", "Goodbye"})
            public String Message = "Hello";
        }
        //creating a class this way DOES process the ANNOTATIONS
        _class _c = _class.of(C.class);
        
        //alternatively to NOT process the annotations, build the AST first
        TypeDeclaration td = Ast.type(C.class);
        System.out.println("GOT THE TYPE" + td );
        
        _class _c2 = _class.of( Ast.type(C.class) );

        assertTrue( _c2.hasAnno(_promote.class));
        assertTrue( _c2.hasAnno(_final.class));
        
        //I Process the annotations
        assertFalse( _c.hasAnno(_promote.class));
        assertFalse( _c.hasAnno(_final.class));
        //assertTrue( _c.getAnnos().contains(_static.class));

        _c.getField("Message").getAnnos().contains(_final.class);

        //calling this way WILL PROCESS the ANNOTATIONS
        _c = _class.of(C.class);

        assertTrue( _c.getModifiers().is("public",  "final"));
        _c.hasImplements(Serializable.class);
        _c.isExtends(BaseClass.class);
        assertEquals( "aaaa.bbbb", _c.getPackage());

        _c.getField("Message").getModifiers().is("public", "static", "final");

        //System.out.println( "REFINED " + _c +" <<<< ");
    }

    public void testH(){
        _class _c = _class.of(ComplexClass.class);

        //_c.walk( Node.class,
        Walk.in(_c,
                Node.class,
            n-> n instanceof MethodDeclaration 
            || n instanceof TypeDeclaration
            || n instanceof VariableDeclarator
            || n instanceof ConstructorDeclaration 
            || n instanceof EnumConstantDeclaration
            || n instanceof AnnotationMemberDeclaration, 
            
            n-> System.out.println( ((NodeWithSimpleName)n).getNameAsString() ) );
            
            
        //_java.walk( _c.astType(), Ast.NODE_WITH_NAME, n-> System.out.println(n) );
        //_java.walk( _c.astType(), Ast.NODE_WITH_SIMPLE_NAME, n-> System.out.println(n.getNameAsString()) );
        //    Ast.NODE_WITH_NAME, 
        //    n-> System.out.println( n ) );
        
        //_java.walk( )
        
        
    }
}