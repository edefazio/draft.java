package draft.java.proto;

import draft.java.Ast;
import draft.java.Stmt;
import draft.java._field;
import junit.framework.TestCase;

@Ast.cache
public class Ssnip_fieldsTest extends TestCase {

    public void testSimple(){
        Ssnip_fields $sfs = new Ssnip_fields("System.out.println($name$);");

        //$sfs.TYPE(int.class,
        //        ($name$)-> System.out.println("int $name$"));
        //assertEquals( $sfs.compose(_field.of("int a;")).get(0),
        //        Stmt.of( (a)-> System.out.println(a) ));
    }

    /*
    public void testSF(){
        $snip_fields $sfs = new $snip_fields();
        //$sfs.TYPE( int[].class,
        //        $snip.of( ()-> System.out.println("IsPrimitive Array")) );

        assertEquals( 0, $sfs.compose( _field.of("int f;") ).size());
        assertEquals( Stmt.of(()-> System.out.println("IsPrimitive Array")),
                $sfs.compose( _field.of("int[] f;") ).get(0));

        //$sfs.TYPE( int.class,
        //        $snip.of( ()-> System.out.println("$name$ IsPrimitive")) );

        assertEquals( Stmt.of(()-> System.out.println("f IsPrimitive")),
                $sfs.compose( _field.of("int f;") ).get(0));
        //System.out.println( $sfs.draft( _field.of("int[] f;"), new Tokens() ));

    }
    */
}
