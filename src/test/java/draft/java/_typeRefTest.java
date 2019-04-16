package draft.java;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.Type;
import junit.framework.TestCase;

import java.io.Closeable;
import java.io.DataInput;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;

/**
 *
 * @author Eric
 */
public class _typeRefTest extends TestCase {

    public void testSimplify(){
        _typeDecl _tr = _typeDecl.of("java.util.List<java.util.Map>");
        assertTrue( _tr.is(Ast.typeRef("List<Map>")));
        assertEquals( "List<Map>", _tr.normalized());
        
    }
    
    public void testLocalAnonymousTypeRefEquality(){
       String className = "draft.java._classTest$1$Hoverboard";
       _typeDecl _tr = _typeDecl.of(className);
       assertTrue( _tr.is("Hoverboard"));
    }

    public void testEqHashCode(){
        _typeDecl tr = _typeDecl.of("List<Integer>");
        _typeDecl tr2 = _typeDecl.of("List<java.lang.Integer>");
        assertEquals( tr, tr2);        
        assertEquals( tr.hashCode(), tr2.hashCode() );                
        tr2 = _typeDecl.of("java.util.List<java.lang.Integer>");
        assertEquals( tr, tr2);        
        assertEquals( tr.hashCode(), tr2.hashCode() );        
        tr2 = _typeDecl.of("java.util.List<Integer>");
        assertEquals( tr, tr2);        
        assertEquals( tr.hashCode(), tr2.hashCode() );                
    }

    /*
    public void testSwap(){
      _typeRef _tr = _typeRef.of( "Map<Integer, ? super OriginalName>" );
      _typeRef _tr2 = _typeRef.swap( _tr, "OriginalName", "NewName" );
      assertEquals( "Map<Integer,? super NewName>", _tr2.toSourceCode() );
    }
    */

    /*
   public void testTypeArgs(){
      _typeRef _t = _typeRef.of(String.class);
      assertEquals("String", _t.getRootTypeName());
      assertEquals("String", _t.toString() );

      _typeRef _t2 = _typeRef.of("java.util.Map<java.lang.Integer,java.lang.String>");
      System.out.println( AST.normalize( _t2.getTypeArgs() ));
      _t2 = _typeRef.of("java.util.Map<Integer,String>");
      System.out.println( AST.normalize( _t2.getTypeArgs() ));

      _typeRef b = _typeRef.of( "SomeClass<? super com.localthing.T, Integer>" );
      System.out.println( "RRR" + AST.normalize( b.getTypeArgs() ));

        /*
        assertEquals( _t2.getRootTypeName(), "Map" ) ;
        NodeList<Type> _as = _t2.getTypeArgs();
        assertEquals( 2, _as.size() );
        //System.out.println( _as.toString() );
        //assertEquals( "<java.lang.Integer,java.lang.String>", _as.toString());
        assertEquals( "Map<Integer,String>", _t2.normalize() );

   }
   */

    /*
   public void testIsSame(){

      _typeRef a = _typeRef.of( "Class<? super T>" );
      _typeRef b = _typeRef.of( "Class<? super com.localthing.T>" );
      //assertTrue( a.typesEqual(b));
      assertTrue( _typeRef.isSame( _typeRef.of("draft.java.Macro.$mockTest$Order"), _typeRef.of("Order") ));
   }
   */



   public static class MyGen {
      public Map<Integer, String> myMap = new HashMap<Integer, String>();
   }
   /*
   public void testFullyQualifiedEqualsNot() throws NoSuchFieldException{
      assertEquals( _typeRef.of(String.class), _typeRef.of("String" ) );
      assertEquals( _typeRef.of("String"), _typeRef.of(String.class ) );

      _typeRef t = _typeRef.of( MyGen.class.getField("myMap").getGenericType() );
      assertEquals( "Map<Integer,String>", t.normalize() );

        assertEquals( _typeRef.of("HashMap<String,Integer>"),
            _typeRef.of(
                new HashMap<String, Integer>() )
            );

   }
   */
    /*
    public void testResolveable(){
        _typeRef _tr = _typeRef.of( Object.class );

        ResolvedType rt = _tr.getType().in();
        Type t = JavaParser.parseType( "Object");
        assertTrue( rt.isAssignableBy( t.in() ) );
    }
    */

   public static class GenericTypes{

      private static <I extends DataInput & Closeable> GenericTypes intersection(I source){
         return null;
      }
      private static List<? extends GenericTypes> wildcard(){
         return null;
      }
      public static void unboundedWildcard(List<?> list) {}

      public static void lowerBounded(List<? super Integer> list) {}

   }

   public void testGenericTypes(){
      _class _c = _class.of( GenericTypes.class ); //make sure we can read thesein
      _typeParameter._typeParameters _args = _c.getMethod( "intersection" ).getTypeParameters();
      //Type t = mds.getAt( 0 ).getType();
      //System.out.println( _typeParams );
      _typeDecl wc = _c.getMethod("wildcard").getType();
      //assertTrue( wc.is( "List<? extends GenericTypes>") );
      //NodeList<Type> args = wc.getTypeArgs();
      //assertEquals( "List", wc.getRootTypeName() );
      //assertEquals( args.toString(), "<? extends GenericTypes>" );



      //assertTrue( wc.isGenericType() );
      Type t = wc.ast();
      ClassOrInterfaceType astT = (ClassOrInterfaceType)t;
      NodeList<Type> nt = astT.getTypeArguments().get();
        /*
        System.out.println( nt );
        for(int i=0;i<nt.size();i++ ){
            System.out.println( nt.getAt( i ));
        }
        */
      //_typeParams.
        /*
        //Type t = JavaParser.parseType(  "I extends DataInput & Closeable" );
        assertTrue( t.isIntersectionType() );
        IntersectionType it = t.asIntersectionType();
        NodeList<ReferenceType>rts = it.getElements();
        for(int i=0;i<rts.size();i++){
            System.out.println( "RT "+ rts.getAt( i ) );
        }
        */
   }
   public void testGeneric(){
      _typeDecl _tr = _typeDecl.of( "Map<K,V>" );
      //assertTrue( _tr.isGenericType() );

      //_typeRef _n = _typeRef.swap(new _typeRef("String"), "String", "Integer" );
      //assertEquals( "Integer", _n.toString() );

      //_n = _typeRef.swap(new _typeRef("String[]"), "String", "Integer" );
      //assertEquals( "Integer[]", _n.toString() );

      //_n = swap(new _typeRef("Map<K, V>"), "Map", "HashMap" );
      //assertEquals( "HashMap<K, V>", _n.toString() );

        /*
        Type t = JavaParser.parseType( "Map<K,V>" );
        assertEquals("Map<K, V>", t.toString());
        System.out.println( t.getClass() );

        if(t.isClassOrInterfaceType()){
            ClassOrInterfaceType cit = t.asClassOrInterfaceType();
            System.out.println( cit.getTypeArguments().getAt().getAt( 0 ) );
            System.out.println( cit.getTypeArguments().getAt().getAt( 1 ) );
        }
        */
      //t.asWildcardType()



      //t = JavaParser.parseType( "so.Typed" );
      //System.out.println( t.getClass() );

   }
   public void testArr(){

      _typeDecl _t = _typeDecl.of("int");
      assertTrue( _t.isPrimitive());

      _t = _typeDecl.of("int[]");
      assertTrue( _t.isArray());
      assertEquals(1, _t.getArrayDimensions() );
      assertEquals( "int[]", _t.toString() );

      _t = _typeDecl.of("int[][]");
      assertTrue( _t.isArray());
      assertEquals(2, _t.getArrayDimensions() );
      assertEquals( "int[][]", _t.toString() );

   }

    /*
    public void testInter(){
        String inter = "<I extends DataInput & Closeable>I";
        _typeRef _t = _typeRef.of( inter );
        assertTrue( _t.isIntersectionType() );
    }
    */

   public void testTypeName(){
      //make sure I can
      String[] primitiveNames = {"int", "char", "float"};
      for(int i=0;i<primitiveNames.length;i++){
         _typeDecl.of(primitiveNames[i]);
      }
      _typeDecl sn = _typeDecl.of("_A");
      sn = _typeDecl.of("$A");
      sn = _typeDecl.of("$A8");

      _typeDecl.of( "java.util.Set<String>" ); //fully qualified

      //generics
      sn = _typeDecl.of( "List<?>" );
      // System.out.println( sn );
      //assertTrue( sn.isClass() );


      //assertTrue( sn.isGenericType());

      _typeDecl.of( "java.util.Set<String>" ); //fully qualified
      _typeDecl.of( "Map<String,Integer<K,V>>" );
      sn = _typeDecl.of( "Map<String,Integer< K , V > >" ); //nested spaces
      // System.out.println( sn );
      _typeDecl.of( "List<? extends Foo>" ); //wildcard
      _typeDecl.of( "Box<>" );

      _typeDecl.of( "Pair<K, V>");

      _typeDecl aa = _typeDecl.of( "@yolo Pair<K, V>");

      // System.out.println( aa.getName() );

      aa = _typeDecl.of( "/** jd */ Pair<K, V>");

      // System.out.println( aa.getName() );

      sn = _typeDecl.of("to$A");
      //sn = _typeRef.of("<T extends B1 & B2 & B3>");

      _typeDecl.of( "List<? extends Number>");
      _typeDecl.of( "List<? super Integer>" );
      _typeDecl.of( "Pair<Integer, Character>" );
   }

   public void testGenericParams(){
      _typeParameter._typeParameters.of( "<T extends B1 & B2 & B3>" );
   }

   public void testFailType(){
      try{ _typeDecl.of( "3542" );fail("expected excep"); }catch( Exception e ){ }
   }

   public static final Map<Integer,String> aValue = null;

   public static final List<? extends Number> aNum = null;
   public static final List<? super Integer> aInt = null;


   public void testEquals() throws NoSuchFieldException{
      assertEquals( _typeDecl.of( Map.class), _typeDecl.of("java.util.Map"));
      assertEquals( _typeDecl.of( "Map<String,Integer>"),
              _typeDecl.of("Map < String, Integer >"));

      assertEquals(
              _typeDecl.of(
                      _typeRefTest.class.getField( "aNum" ).getAnnotatedType() ),
              _typeDecl.of( "java.util.List<? extends java.lang.Number>" ) );

      assertEquals(
              _typeDecl.of(
                      _typeRefTest.class.getField( "aInt" ).getAnnotatedType() ),
              _typeDecl.of( "java.util.List<? super java.lang.Integer>" ) );


      //verify that the Type is equal
      assertEquals(
              _typeDecl.of(_typeRefTest.class.getField("aValue").getGenericType() ),
              _typeDecl.of( " Map < Integer , String >" ) );

   }
}
