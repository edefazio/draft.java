package usecase.spoon;

import com.github.javaparser.ast.expr.ObjectCreationExpr;
import draft.java._class;
import draft.java._method;
import draft.java.io._bulk;
import draft.java.io._bulk._load;
import draft.java.io._bulk._receipt;
import draft.java.macro._ctor;
import draft.java.proto.$anno;
import draft.java.proto.$catch;
import draft.java.proto.$expr;
import draft.java.proto.$method;
import draft.java.proto.$node;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TreeSet;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class SpoonAnalysisTest extends TestCase {
    
    /** Use case from :
     * <A HREF="http://spoon.gforge.inria.fr/first_analysis_processor.html">First Analysis Processor</A> 
     */
    public void testCodeAnalysisEmptyCatchClauses(){
        //heres how I define an empty catch clause (in a $proto)
        $catch $emptyCatch = $catch.of(c -> c.getBody().isEmpty());
        
        // heres some class to test
        class D{
            void m(){
                try{}
                catch(Exception e){}
            }
            void g(){
                try{}
                catch( Exception e){ System.out.println( e );}
            }
        }
        //here I can count the ones above (we can quickly test that it works)
        assertEquals(1, $emptyCatch.count(D.class));
        
        //com.github.javaparser.GeneratedJavaParserTokenManager
        
        //analyze the source of each type in an entire jar file or directory:
        _receipt _l = // "C:\\Users\\Eric\\.m2\\repository\\com\\github\\javaparser\\javaparser-core\\3.14.2\\javaparser-core-3.14.2-sources.jar",
            _bulk.consume("C:\\Users\\Eric\\.m2\\repository\\com\\github\\javaparser\\javaparser-core\\3.14.2\\javaparser-core-3.14.2-sources.jar", //"C:\\temp",
                t-> $emptyCatch.forEachIn(t, 
                    c-> System.err.println("Empty catch at : "+
                        t.getFullName()+":"+ c.getBegin().get().line)));
        
        System.out.println( _l ); //lets print out the statistics 
        
        /**
         * Here's an example output (running against javaparser-core-3.14.2):
         * Empty catch at : com.github.javaparser.GeneratedJavaParserTokenManager:2765
         * Empty catch at : com.github.javaparser.JavaParser:131
         * Empty catch at : com.github.javaparser.GeneratedJavaParser:10658
         * Read and Processed (461) .java files from "C:\Users\Eric\.m2\repository\com\github\javaparser\javaparser-core\3.14.2\javaparser-core-3.14.2-sources.jar" in : 2597ms
         */        
    }    
    
    /**
     * <A HREF="http://spoon.gforge.inria.fr/first_transformation.html">First Transformation Processor</A>
     * 
     */
    public void testTransformationProcessor(){
        _class _c = _class.of("Tacos", new Object(){
            private List<Date> dates;
            @_ctor public void Tacos(List<Date> dates){
                this.dates = dates;
            }
        });        
    }
    
    /**
     * <A HREF="http://spoon.gforge.inria.fr/architecture_test.html">Using Spoon for Architecture enforcement</A>
     */
    public void testArchitectureCantUseTreeSetConstructor(){
        //never allow the TreeSet Constructor
        class C{
            TreeSet ts = new TreeSet();
            TreeSet<Integer> ts2 = new TreeSet<Integer>();
        }
        $expr<ObjectCreationExpr> $newTreeSet = $expr.objectCreation("new TreeSet$any$()");        
        assertEquals(2, $newTreeSet.count(C.class));
        
        _receipt _l = //"C:\\Users\\Eric\\.m2\\repository\\com\\github\\javaparser\\javaparser-core\\3.14.2\\javaparser-core-3.14.2-sources.jar",
            _bulk.consume("C:\\temp",
            t-> $newTreeSet.forEachIn(t, 
                c-> System.err.println("Constructed TreeSet " + c + "@ : "+
                        t.getFullName()+":"+ c.getBegin().get().line)));
        System.out.println( _l );
        //$node.of(n->true).forEachIn(C.class, n-> System.out.println(n+" "+n.getClass() ) );
        //$expr.objectCreation( oce -> oce.g)
    }
    
    /**
     * http://spoon.gforge.inria.fr/architecture_test.html
     * Example rule: all test classes must start or end with "Test"
     */
    public void testArchitectureTestClassesMustStartOrEndWithTest(){        
        _receipt _l = //  "C:\\Users\\Eric\\.m2\\repository\\com\\github\\javaparser\\javaparser-core\\3.14.2\\javaparser-core-3.14.2-sources.jar",
            _bulk.consume("C:\\temp",
            t-> {
                if( t instanceof _class && t.hasImport(TestCase.class) && ((_class)t).isExtends(TestCase.class) ){
                    if(! t.getName().startsWith("Test") || (t.getName().endsWith("Test"))){
                        System.out.println( "Found a class "+ t.getFullName()+" that implements TestCase but isn'nt named \"Test*\" or \"*Test\" ");
                    }                           
                } });
    }
    
    /**
     * Example rule: all public methods must be documented
     * http://spoon.gforge.inria.fr/architecture_test.html
     */
    public void testAllPublicMethodsShouldBeDocumented(){
        $method $m = $method.of(_m -> _m.isPublic() && 
            (!_m.hasJavadoc() || _m.getJavadoc().getContent().length() < 20) );
        List<_method> publicMethodsWithoutJavadoc = new ArrayList<>();   
        //lets just collect all of the methods
        _bulk.consume("C:\\temp",
            //"C:\\Users\\Eric\\.m2\\repository\\com\\github\\javaparser\\javaparser-core\\3.14.2\\javaparser-core-3.14.2-sources.jar", //
            t-> publicMethodsWithoutJavadoc.addAll($m.listIn(t)));
        System.out.println( publicMethodsWithoutJavadoc.size());
        //System.out.println( publicMethodsWithoutJavadoc.get(0) );
        
        //publicMethodsWithoutJavadoc.forEach(m -> m.toString());
    }
}
