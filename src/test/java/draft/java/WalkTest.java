package draft.java;

import com.github.javaparser.ast.ImportDeclaration;
import com.github.javaparser.ast.body.TypeDeclaration;
import junit.framework.TestCase;


public class WalkTest extends TestCase {

    public void testWalkIn(){
        _class _c = _class.of("C")
                .fields("int x=1;", "int y=2;", "String z;");


        Walk.in(_c, _class.class, c-> System.out.println(c));



        Walk.in(_c, TypeDeclaration.class, td->System.out.println(td) );
        Walk.in(_c, ImportDeclaration.class, td->System.out.println(td) );

    }
}
