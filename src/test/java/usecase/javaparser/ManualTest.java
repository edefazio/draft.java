/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usecase.javaparser;

import draft.java.Walk;
import draft.java._class;
import draft.java._method;
import draft.java.io._io;
import draft.java.macro._static;
import draft.java.proto.pStmt;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.List;
import junit.framework.TestCase;

/**
 * conversion of the Manual Tests found in the JavaParser Manual at:
 * https://github.com/javaparser/javaparser/wiki/Manual
 * 
 * @author Eric
 */
public class ManualTest extends TestCase {
    
    @Override
    public void setUp(){
        _class _c = _class.of("test", new Object(){
           int f = 0;
           void m(){
               System.out.println( "called method");
           }
           class Inner{
               void innerM(){
                   System.out.println( "inner class method");
               }
           }
        });
        _io.out("C:\\temp", _c);
    }
    /*
    <A HREF="https://github.com/javaparser/javaparser/wiki/Manual#printing-the-compilationunit-to-system-output">
    */
    public void testPrintingOut() throws FileNotFoundException{
        System.out.println( 
            _class.of( new FileInputStream("C:\\temp\\test.java") )
        );        
    }
    
    /* 
    <A HREF="https://github.com/javaparser/javaparser/wiki/Manual#visiting-class-methods">Visiting Class methods</A>
    */
    public void testVisitingClassMethods() throws FileNotFoundException{
        
        _class _c = _class.of( new FileInputStream("C:\\temp\\test.java") );
        
        /* this method will be called for all methods in this 
           _class, including inner class methods */
        Walk.in( _c, 
            _method.class, 
            m->System.out.println(m) /* here you can access the attributes of the method m. */
        );
        
        /* Alternatively; to iterate over the methods on _class _c
           (i.e. NOT INNER CLASS METHODS of class _c) : */
        _c.forMethods( m -> System.out.println( m ) );
        
        
    }
    
    /*
    <A HREF="https://github.com/javaparser/javaparser/wiki/Manual#changing-methods-from-a-class-with-a-visitor">Changing Methods with a visitor</A>    
    <A HREF="https://github.com/javaparser/javaparser/wiki/Manual#changing-methods-from-a-class-without-a-visitor">Changing methods without a visitor</A>
    */
    public void testChangingMethods() throws FileNotFoundException{
        _class _c = _class.of( new FileInputStream("C:\\temp\\test.java") );
        
        /** to modify BOTH methods on _c AND methods on Inner */
        Walk.in( 
            _c,
            _method.class,
            m -> m.name(m.getName().toUpperCase())
                .addParameter("int value") );
        
        /** to modify only methods on test.java */
        _c.forMethods(m-> m.name(m.getName()+"1")
            .addParameter("int anotherValue") );
        
        //BONUS: VERIFY the changes happend like we wanted
        assertTrue( _c.getMethod("M1") //we uppercased name... then added 1
            .getParameters().is("int value, int anotherValue") ); //added 2 parameters
        
        assertTrue( _c.getNestedClass("Inner")
            .getMethod("INNERM") //we ONLY uppercased it
                .getParameters().is("int value")); //we added only 1 parameter
     
        System.out.println( _c );
    }
       
    /*
    <A HREF="   
    */
    public void testCreateACompilationUnitFromScratch(){
        _class _c = _class.of("java.parser.test.GeneratedClass", new Object(){
            
            public @_static void main(String... args){
                System.out.println( "Hello World!");
            }
            
            public @_static void main2(String...args){}
        });
        System.out.println( _c );
    }
 
    
    public void testRemoveDeleteNodeFromAST(){
        class D{
            public void foo(int e){
                int a = 20;
            }
        }
        _class _c = _class.of(D.class);
        
        pStmt.remove(_c, "int a = 20;");
        
        //BONUS: lets verify the code was removed
        assertTrue( _c.getMethod("foo").getBody().isEmpty());
        
        //BONUS (2) : lets add the code back in to understand $stmt proto
        
        //here create a proto - $stmt for initializing a variable a
        pStmt $a = pStmt.of("int a = $init$;");
        
        //construct/add a statement to the method using the prototype $a 
        //    "int a = 100;"
        _c.getMethod("foo").add($a.construct("init", 100));
        
        //use the $a prototype to select the statement "int a = 100;"
        assertNotNull($a.selectFirstIn(_c).astStatement );
        
        //a select contains the "read/extract" the init parameterized value
        assertNotNull( $a.selectFirstIn(_c).args.is("init", "100") );
        
        //find/match and statement assignment of int a to any value
        assertNotNull($a.firstIn(_c));
        
        $a = pStmt.of("$type$ a = $init$;");
        
        // remove any assignment statements of variable a to any type 
        assertNotNull($a.removeIn(_c));
                
        //find any assignment of the variable a to any value
        assertNull(pStmt.first(_c, "$type$ a = $init$;"));        
    }
}
