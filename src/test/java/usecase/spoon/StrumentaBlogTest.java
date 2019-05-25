package usecase.spoon;

import draft.java.io._bulk;
import junit.framework.TestCase;
import draft.java._class;
import draft.java._method;
import draft.java._method._hasMethods;
import draft.java._type;
import draft.java.io._bulk._receipt;
import draft.java.macro._package;
import draft.java.proto.$typeUse;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import org.junit.Test;

/**
 * 
 * https://tomassetti.me/analyze-generate-and-transform-java-code/
 * @author Eric
 */
public class StrumentaBlogTest extends TestCase  {
    
    public void testTypesWithMoreThan20Methods(){
        
        _bulk.consume(
            "C:\\Users\\Eric\\.m2\\repository\\com\\github\\javaparser\\javaparser-core\\3.14.2\\javaparser-core-3.14.2-sources.jar", //"C:\\temp", 
            t-> { 
            if( t instanceof _hasMethods ){                 
                _hasMethods _hm = (_hasMethods)t;
                if( _hm.listMethods().size() > 20 ){
                    System.out.println( "("+ _hm.listMethods().size()+") methods in "+ t.getFullName() );
                }
            } 
        });        
    }
    
    /**
     * https://tomassetti.me/analyze-generate-and-transform-java-code/
     * Let’s try to find all test classes and ensure their names ends with “Test”. 
     * A test class will be a class with at least a method annotated with org.unit.Test.
     */    
    public void testTestClassesNotNamed_Test(){
        
        @_package("aaaa.bbbb")
        class ATest extends TestCase {
            
        }
        
        @_package("aaaa.bbbb")
        class B{
            @Test
            public void testR(){
                
            }                        
        }
        List<String> listOfBadlyNamedTests = new ArrayList<>();
        List<String> listOfProperlyNamedTests = new ArrayList<>();
        
        _type _t = _class.of(B.class);
        //_t.listMethods( (_method m) -> m.hasAnno(Test.class) );
        //_t.countMembers(_method.class, (_method m)-> m.hasAnno(Test.class));
        //_t.hasMethod( m-> m.hasAnno(String.class) );
        /*        
        Consumer<_type> v = t-> {
            if( _t.isExtends(TestCase.class) 
                || _t.listMethods( (_method _m)-> _m.hasAnno(Test.class) ).size() > 0 ){
                    if (_t.getName().endsWith("Test")){
                        listOfProperlyNamedTests.add( t.getFullName());                        
                    } else{                    
                        listOfBadlyNamedTests.add(t.getFullName());
                    }
                }            
            };
        */
        Consumer<_type> verifyTestClassesHaveProperName = t-> {
            if( t instanceof _class ){
                _class _c = (_class)t;
                if( _c.isExtends(TestCase.class) 
                    || _c.listMethods(_m -> _m.hasAnno(Test.class)).size() > 0 ){
                    if (_c.getName().endsWith("Test")){
                        listOfProperlyNamedTests.add( t.getFullName());                        
                    } else{                    
                        listOfBadlyNamedTests.add(t.getFullName());
                    }
                }            
            }   
        };
        
        List<_type> types = new ArrayList<>();
        types.add(_class.of(ATest.class));
        types.add(_class.of(B.class));
        
        types.forEach(verifyTestClassesHaveProperName);
        assertEquals(1, listOfBadlyNamedTests.size() );
        assertEquals(1, listOfProperlyNamedTests.size() );
        listOfBadlyNamedTests.clear();
        listOfProperlyNamedTests.clear();
        
        _bulk.consume("C:\\draft\\project\\draft-java\\src\\test", 
            verifyTestClassesHaveProperName);
        
        listOfBadlyNamedTests.forEach( t -> System.out.println( "badly named test \""+ t+"\""));        
        
    }
}
