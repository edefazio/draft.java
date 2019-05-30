/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java;

import com.github.javaparser.ast.body.TypeDeclaration;
import draft.java.proto.$var;
import java.util.List;
import junit.framework.TestCase;

import test.*;

/**
 *
 * @author Eric
 */
public class _compilationUnitTest extends TestCase {
    
    public void testGetType(){
        _type _t = _type.of( PackagePrivateClass.class );
        List<TypeDeclaration<?>> astTypes = _t.astCompilationUnit().getTypes();
        
        //System.out.println( "STORAGE" + _t.astCompilationUnit().getStorage().get().getFileName() );
        //System.out.println ( "PRIMARY TYPE" +_t.astCompilationUnit().getPrimaryType().get() );
        assertNotNull(_t.astCompilationUnit().getPrimaryType().get() );
        
        //System.out.println ( astTypes.get(0).isTopLevelType() );
        //System.out.println ( astTypes.get(1).isTopLevelType() );
        
        //System.out.println( _t.getFullName() );
        //System.out.println( _type.of(astTypes.get(1)).getFullName() );
        
        _t = _type.of( PackagePrivateMultiClass.class );
        assertEquals( PackagePrivateMultiClass.class.getSimpleName(), _t.getName());
        assertEquals( PackagePrivateMultiClass.class.getCanonicalName(), _t.getFullName());
        
        //verify that I can find the 
        assertEquals( 5, $var.of("int a").count(PackagePrivateMultiClass.class));
        assertEquals( 5, $var.of("int a = 100").count(PackagePrivateMultiClass.class));
        
    }
    
    public void testPublicClassWithPackagePrivateTypes(){
        _type _t = _type.of(test.PublicTypeWithPackagePrivateTypes.class);
        
        System.out.println( _t );
        List<_type> ppts = null;
        //_t.getPackagePrivateTypes();
    }
    /*
    public void testGetCompilationUnit(){
        _compilationUnit()
    }
    */
    
}
