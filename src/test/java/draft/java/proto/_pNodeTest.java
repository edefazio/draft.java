/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package draft.java.proto;

import com.github.javaparser.ast.expr.AnnotationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import draft.java.Ast;
import draft.java._class;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class _pNodeTest extends TestCase {
    
    public interface Inter{
        public static final int VAL = 1;
        public static void tell(){            
        }
        public static Integer blah(){ return 1;}
        public static Integer mr(Object o){ return 1;}
    }
    
    public static class Base{
        public static final int VAL = 1;
        public static void tell(){            
        }
        
        
        public static Integer blah(){ return 1;}
        public static Integer mr(Object o){ return 1;}
    }
    
    public @interface Ann{
        int value() default 1;
        int a() default 2;
    }
    
    
    /** */
    public void testAstParseAnnotationOnMethod(){
        AnnotationExpr as = Ast.anno("@$dollar");
        as = Ast.anno("@$dollar.bills");
        
    }
    
    public void testN(){
        _class _c = _class.of("C",new Object(){
            @Ann
            Inter ifield = null;
            
            @_pNodeTest.Ann
            Base bField = null;
            
            @draft.java.proto._pNodeTest.Ann
            Map<Inter,Base> m = new HashMap<>();
            
            //@draft.java.proto.$nodeTest.Ann
            Inter getInter(){
                Inter.tell();
                System.out.println( Inter.VAL );
                Class II = Inter.class; //verify classExpr                
                Consumer<Object> C = Inter::mr; //verify method references
                
                BiConsumer<Inter, Base> f = ( Inter i, Base b) -> System.out.println();
                
                Inter[] arr = new Inter[0];
                if( arr[0] instanceof Inter ){
                    
                }
                @_pNodeTest.Ann
                class INN implements Inter{ //check inner nest
                    
                }
                
                return ifield;
            }
            
            Base getBase(){
                Base.tell();
                System.out.println( Base.VAL );
                System.out.println( (Base)bField ); //verify case
                Class CC = Base.class;
                //verify method references
                Consumer<Object> C = Base::mr;
                Base[] arr = new Base[0];
                if( arr[0] instanceof Base ){
                    
                }
                class INN extends Base{ //check inner nest
                    
                }
                return bField;
            }
            
            @_pNodeTest.Ann
            public void setInter( Inter ifld ){
                this.ifield = ifld;
            }
            //@draft.java.proto.$nodeTest.Ann
            public <I extends Inter> void g( I in ) {}
            
            //@$nodeTest.Ann
            public <B extends Base> void gg( B in ) {}                        
        }).implement(Inter.class)
                .extend(Base.class);
         
        //the first thing is to replace fully qualified references
        _pNode $n = new _pNode(Inter.class.getCanonicalName());
        $n.replaceIn(_c, Inter.class.getCanonicalName().replace("Inter", "Other") );
        $n = new _pNode("Inter");
        $n.replaceIn(_c, "Other");
        
        
        $n = new _pNode(Ann.class.getCanonicalName());
        $n.replaceIn(_c, Ann.class.getCanonicalName().replace("Ann", "Stan") );
        
        $n = new _pNode("$nodeTest.Ann");
        $n.replaceIn(_c, "$nodeTest.Stan");
        
        $n = new _pNode("Ann");
        $n.replaceIn(_c, "Stan");
        
        
        $n = new _pNode(Base.class.getCanonicalName());
        $n.replaceIn(_c, Base.class.getCanonicalName().replace("Base", "Replace") );
        $n = new _pNode("Base");
        $n.replaceIn(_c, "Replace");
        
        System.out.println( _c );
        
    }
    
}
