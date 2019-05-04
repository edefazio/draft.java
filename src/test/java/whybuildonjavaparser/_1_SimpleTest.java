package whybuildonjavaparser;

import com.github.javaparser.Position;
import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.expr.IntegerLiteralExpr;
import draft.java.Ast;
import draft.java.Walk;
import draft.java._anno;
import draft.java._anno._hasAnnos;
import draft.java._class;
import draft.java._field;
import draft.java._interface;
import draft.java._method;
import draft.java._model._node;
import draft.java.io._io;
import draft.java.macro.*;
import draft.java.runtime._project;
import draft.java.runtime._proxy;
import java.util.List;

import static java.lang.System.*;
import java.util.Map;
import junit.framework.TestCase;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 
 * NOTE: we use the  annotation to cache the AST after parsing this
 * class the first time, and return copies (rahter than re-reading/reparsing 
 * the same file, over and over each time we build a _class from a local or
 * nested class... it's much faster
 * @author Eric
 */
public class _1_SimpleTest extends TestCase {
    
    /**
     * An old saw is to "make the common case fast", and, in this case, I'm 
     * assuming the common case is Parsing a String into a CompilationUnit
     * class file.  
     * 
     * then also verifying that the field has an init value we expect
     */
    public void testCommonCase_JavaParser(){
        CompilationUnit cu = StaticJavaParser.parse(
            "public class Point{ int x=1, y=1;}");
        ClassOrInterfaceDeclaration astC = cu.getClassByName("Point").get();
        
        //verify tht the initilizer for "x" is  literal int 1
        assertTrue( astC.getFieldByName("x").get().getVariable(0)
            .getInitializer().get().equals(new IntegerLiteralExpr(1)) );
    }
   
      /**
     * draft tries an integrated approach to build the AST from existing
     * code within the editor, here we use all of the affordances the editor has to 
     * offer to build a local class, and then we let draft build an in memory 
     * AST from it.  
     * 
     * To be clear, here "fast" means "fast" in developer time.
     * (There is extra parsing overhead incurred at runtime because the entire
     * file "_1_SurfaceSyntax.java" is parsed at runtime in order to extract the
     * local class).
     * 
     * (WE think it's a valuable trade off as "developer time" reading 
     * and understanding the code (seconds, minutes) 
     * is balanced against the parsing time costs much more than "computer time" 
     * (in a few extra milliseconds)
     */
    public void testCommonCase_draft(){
        class Point{ int x=1, y=1; } //here we have "non-stringified" actual code
        _class _c = _class.of(Point.class);//build a _class/AST from local Class
        
        //verify the field x is == to (the integer literal expression 1)
        assertTrue( _c.getField("x").isInit(1)); 
        
        // Note: draft has some "extras" tht make it easy to for testing
        // verify that the field "x" is defined by the pre parsed String below
        assertTrue( _c.getField("x").is("int x=1;")); 
        
        //alternative way of building an AST/_class from code w/ body:
        _class _c2 = _class.of("Point", new Object(){ int x=1, y=1; });
        
        //we can test equality & verify they are the same _class
        assertEquals(_c, _c2);
        
        //you can always just toString() to get the String representation
        String codeString = _c.toString();
        
        //or provide your own pretty printer
        System.out.println( _c.toString(Ast.PRINT_NO_COMMENTS));
        
        // behind the _class facade, is just a JavaParser 
        // CompiltionUnit / ClassOrInterfaceDeclaration, 
        // you can get to them easily:
        CompilationUnit astCu = _c.findCompilationUnit();
        
        ClassOrInterfaceDeclaration astCl = _c.ast();        
        
        // this gives you access to the lower level operations and functions, 
        // tokens,visitors, positions, and all of the other detail information         
        System.out.println( "from line : "+astCl.getBegin().get().line+
                " to : " +astCl.getEnd().get().line );
        
        /** 
         * you get back the real mutable reference, (not a copy or anything) 
         * so you can change the AST directly through the API you are already
         * familiar with if you wish.
         */
        astCl.tryAddImportToParentCompilationUnit(Map.class);
        
    }
    
    
    public void testCRUD(){
        System.out.println( _io.config() );
        
        _class _pt = _class.of("Point", new Object(){
            public static final int ID = 123456;
            public int x;
            public int y;
        });        
        //Simple (one shot) CRUD operations
        _pt.constructor("public Point(){}");// Create & add a constructor
        _pt.getField("ID").isInit(123456);  // Read & verify the field
        _pt.setPackage("aaa.bbbb");         // Update package (on CompilationUnit)
        _pt.removeField("ID");              // Delete a field (by name)
        
        //Single Reading
        _pt.getExtends();                            //Read class extends
        _pt.getAnno(Deprecated.class);               //Read annos of this type   
        _pt.getConstructor(c -> !c.hasParameters()); //Read first no-arg ctor 
                
        //Bulk Reading                 
        List<_field> _fs = _pt.listFields();   //Read fields 
        List<_anno> _as = _pt.listAnnos();     //Read annotations on class        
        List<_method> _ms = _pt.listMethods(); //Read methods
        
        //Lambda operations        
        _pt.listImports(i->i.isStatic());       // Read all static imports
        _pt.listFields(f-> f.isPrimitive());    // Read all primitive fields  
        _pt.forFields(f-> f.init(0));           // Update init for ALL fields to 0
        _pt.removeFields(f-> f.isStatic());     // Delete ALL static fields 
        //print ALL member names(fields,methods,etc.)
        _pt.forMembers(m->out.print(m.getName())); 
        
        //Bi-Lambda Operations
        
        // Update all NON static fields to 0
        _pt.forFields(f->!f.isStatic(), f-> f.init(0)); 
        
        //set all parameters for all public constructors to be final
        _pt.forConstructors(c-> c.isPublic(), c->c.forParameters(p-> p.setFinal()));                
    }
    
     /**
     * draft does macros, because writing boiler plate code and repeating 
     * yourself is tedious
     */
    public void testMacros(){
        //Here we "apply" macros as annotations
        @_autoGet @_autoSet @_autoConstructor @_autoHashCode @_autoEquals
        class Point{
            int x,y;
            
            public String toString(){
                return "("+x+","+y+")";                        
            }
        }
        
        // NOTE: there is a single annotation @_autoDto that accomplishes all of 
        // the above (plus adds a @_autoToString) which only requires the one 
        // annotation too...we just annotated each for illustration purposes
        
        //when the Point class is parsed 
        _class _pt = _class.of(Point.class);
        assertEquals( 1, _pt.listConstructors().size() ); //generated a constructor
        assertTrue(_pt.getMethod("getX").isType(int.class));
        assertTrue(_pt.getMethod("getY").isType(int.class));
        assertTrue(_pt.getMethod("setX").getParameter(0).isType(int.class));
        assertTrue(_pt.getMethod("setY").getParameter(0).isType(int.class));
        assertTrue(_pt.getMethod("equals").isType(boolean.class));
        assertTrue(_pt.getMethod("hashCode").isType(int.class));
        assertTrue(_pt.getMethod("toString").isType(String.class));        
        
    }
    
     /**
     * What good is code that doesn't run?
     * ...instead of kicking the can down the road, (after creating the AST)
     * or having to write numerous test cases searching into the (generated) code
     * to attempt to validate the code is "correct"... Lets just compile and use
     * the code  
     */
    public void testCompileAndRunDraftedCode(){
        @_autoGet @_autoSet @_autoConstructor @_autoHashCode @_autoEquals
        class Point{
            int x,y;
            
            public String toString(){
                return "("+x+","+y+")";                        
            }
        }
        //lets verify the 
        _class _c = _class.of( Point.class);
        //for simplicity, lets add a new initilizing constructor
        _c.constructor("public Point(int x, int y){ this.x = x; this.y = y; }");
        
        System.out.println( _c );
        //compile & load the class in a _project
        _project _p = _project.of(_c);
        
        //build a wrapper proxy instance
        _proxy _pt1 = _p._proxy(_c, 1,2);
        _proxy _pt2 = _p._proxy(_c, 1,2);
        assertEquals("(1,2)", _pt1.toString());
        assertEquals("(1,2)", _pt2.toString());
        
        assertEquals(_pt1.toString(), _pt2.toString());
        assertEquals(_pt1.instance, _pt2.instance); //verify that equals works
        assertEquals(_pt1, _pt2); //verify that equals works (on proxies)
        
        assertEquals(_pt1.hashCode(), _pt2.hashCode()); //verify hashcode works
        assertEquals(_pt1.call("hashCode"), _pt2.call("hashCode")); //check hashcode
        
        //now change & verify equals and hashcode not equal
        _pt2.set("x", 100);
        
        assertNotSame(_pt1.toString(), _pt2.toString());
        assertNotSame(_pt1.instance, _pt2.instance);
        assertNotSame(_pt1.hashCode(), _pt2.hashCode());
    }
    
     /** 
     * Below is an example of Syntactically different and Semantically the same 
     */
    @interface Ann{}
    
    interface ToExtend1{}
    interface ToExtend2{}
    
    interface A extends ToExtend1, ToExtend2{
        @Ann
        Map a = null;
    }
    
    interface B extends _1_SimpleTest.ToExtend2, 
            _1_SimpleTest.ToExtend1 {
        
        @_1_SimpleTest.Ann
        public static final Map a = null;
    }
    
    /**
     * Illustrate situations where entities are NOT syntactically 
     * equals, but ARE semantically equals (to put it another way, 
     * the effect of the code is the same, but the syntax is different)
     * @throws java.lang.NoSuchFieldException
     */
    public void testSemanticsAndDiff() throws NoSuchFieldException{
        // the runtime instances of A, and B above each have a field "a"
        // and they are "semantically" the same, but not syntactically the same
        
        // the Runtime modifiers are the same, 
        // A.a has "implicit modifiers" public static final
        // B.a has "explicit modifiers" public static final 
        assertEquals( A.class.getModifiers(), B.class.getModifiers() );
        //The Runtime Annotations are the same
        Ann ann1 = A.class.getField("a").getAnnotation(Ann.class);
        Ann ann2 = B.class.getField("a").getAnnotation(Ann.class);
        assertEquals( ann1, ann2 );
        
        //alright, lets create JavaParser ASTs 
        _interface _i1 = _interface.of(A.class);
        _interface _i2 = _interface.of(B.class).name("A"); //change the name
        
        CompilationUnit astI1 = _i1.findCompilationUnit();
        CompilationUnit astI2 = _i2.findCompilationUnit();
        
        assertNotSame( astI1, astI2 ); //we know the ASTs are not syntactically the same
        
        //Testing equality for draft entities understands more about semantics
        assertEquals( _i1, _i2);//..in draft they ARE semantically ==
        assertEquals( _i1.hashCode(), _i2.hashCode()); //hashcodes are also equal
        
        // so some of the syntactical differences (in the source code)
        // do NOT translate to semantical differences (in the bytecode)
        // For example:
        // fully qualified vs not           ( Map -v- java.util.Map)
        // order (of extends or implements) ( extends A,B -v- extends B, A)
        // order (of throws)                ( throws A,B  -v- throws B,A)
        // implied vs explicit modifiers    ( interface A{int a=1;} -v- interface A{public static final int a=1;}      
        
        //Often times we might make change and want to know what is "semantically"
        //different... so we have a java.diff project specifically for this:        
        //assertTrue( _diff.of(_i1, _i2).isEmpty() );        
    }    
    
}
