/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usecase.javapoet;


import draft.java.*;
import draft.java.macro.*;
import draft.java.runtime.*;
import draft.java.proto.$method;
import java.io.IOException;
import java.util.*;
import javax.lang.model.element.Modifier;
import junit.framework.TestCase;
import java.util.logging.LogRecord;

/**
 *
 * @author Eric
 */
public class JavaPoetUseCasesTest extends TestCase {
    
    public void test1Draft(){
        _class _c = _class.of("com.example.helloworld.HelloWorld")
                .main( () ->System.out.println("Hello JavaPoet!") );
        System.out.println( _c ); 
    }
    
    public void test2Draft(){
        _method _main = _method.of( new Object(){
            public void main(){
                int total = 0;
                for(int i=0;i<10;i++){
                    total += i;
                }
            }
        });
        //_method _main = _method.of("main", ()-> {
        //    int total = 0;
        //    for(int i=0;i<10;i++){
        //        total += i;
        //    }
        //});        
        System.out.println( _main );
    }
    
    public void test3Draft(){
        _method _main = _method.of(new Object(){
           void main(){
               int total = 0;
               for(int i = 0; i < 10; i++){
                   total += i;
               }
           } 
        });
        System.out.println( _main );
    }
    
    private $method $computeRange = $method.of(new Object(){
        public int $name$(){
            int result = 1;
            for(int i=$from$;i<$to$;i++){
                result = result * i;
            }
            return result;
        }        
        int $from$, $to$;
    }).$("*", "op");        
    
    public void test4Draft(){
        System.out.println($computeRange.construct("name", "multiply10to20","from", 10,"to",20,"op", "*"));
        //TODO fix fill
        //  System.out.println($computeRange.fill("multiply10to20",10,20,"*"));
    }
    
    public void test5Draft(){
        _method _m = _method.of(new Object(){
            void main() {
                long now = System.currentTimeMillis();
                if(System.currentTimeMillis() < now){
                    System.out.println("Time travelling, woo hoo!");
                } else if (System.currentTimeMillis() == now) {
                    System.out.println("Time stood still!");
                } else {
                    System.out.println("Ok, time still moving forward");
                }
            }
        });
        System.out.println( _m );
    }
    
    public void test6Draft(){
        _method _main = _method.of( new Object(){
            void main() {
                try {
                    throw new Exception("Failed");
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        });
        System.out.println( _main );
    }
    
    public void test7Draft(){
        $method $whatsMyName = $method.of( new Object(){
            String $name$(){
                return "$name$";
            }
        });
        _class _h = _class.of("com.example.helloworld.HelloWorld")
            .setPublic().setFinal()
            .add($whatsMyName.fill("slimShady"), 
                $whatsMyName.fill("eminem"), 
                $whatsMyName.fill("marshallMathers"));
        System.out.println( _h );
    }
    
    
    // NOTE: because we define the body of the Class via 
    // an anonymous Object, we inferred the imports (specifically Date.class)
    public void test8Draft(){
        _class _c = _class.of("com.example.helloworld.HelloWorld", new Object(){
           public Date today(){
               return new Date();
           } 
        });
        System.out.println(_c );
        _javac.of(_c);
    }    
    
    
    _class _hoverboard = _class.of("com.mattel.Hoverboard", new Object(){            
            public List<Hoverboard> beyond(){
                List<Hoverboard> result = new ArrayList<>();
                result.add(new Hoverboard());
                result.add(new Hoverboard());
                result.add(new Hoverboard());                
                return result;
            }            
            @_remove class Hoverboard{}            
        }).imports(ArrayList.class);
    
    public void test9Draft(){        
        //build, compile & load, create new instance & call beyond method        
        assertEquals(3, ((List)_proxy.of( _hoverboard ).call("beyond")).size());
        //verify we created and return (3) new Hoverboards
    }
    
    // Implicit Type 
    public void test10Draft(){
        _class _c = _class.of("com.example.helloworld.HelloWorld", new Object(){           
            @_remove class Hoverboard{}
            
            List<Hoverboard> beyond() {
                List<Hoverboard> result = new ArrayList<>();
                result.add(new Hoverboard());
                result.add(new Hoverboard());
                result.add(new Hoverboard());
                return result;
            }            
        }).imports(_hoverboard).imports(ArrayList.class);
        
        _javac.of(_hoverboard, _c);
    }
    /*
    public void test11Draft(){        
        _class _c = _class.of("com.example.helloworld.HelloWorld")
                .method( 
                    "List<Hoverboard> beyond() {",
                    "List<Hoverboard> result = new ArrayList<>();",
                    "result.add(createNimbus(2000));",
                    "result.add(createNimbus(\"2001\"));",
                    "result.add(createNimbus(THUNDERBOLT));",
                    "sort(result);",
                    "return result.isEmpty() ? emptyList() : result;",
                    "}")
                .importStatic(Collections.class.getCanonicalName()+".*",
                    "com.mattel.Hoverboard.Boards.*",
                    "com.mattel.Hoverboard.createNimbus.*");
        System.out.println( _c);
    }
    */
    
    public void test11Draft(){        
        _class _c = _class.of("com.example.helloworld.HelloWorld", new Object(){
            List<Hoverboard> beyond() {
                List<Hoverboard> result = new ArrayList<>();
                result.add(createNimbus(2000));
                result.add(createNimbus("2001"));
                result.add(createNimbus(THUNDERBOLT));
                Collections.sort(result);
                return result.isEmpty() ? Collections.emptyList() : result;
            }
            
            @_remove class Hoverboard implements Comparable {
                public int compareTo(Object o){ return 1;}
            }
            @_remove public Hoverboard createNimbus( int val ){ return null; }
            @_remove public Hoverboard createNimbus( String val ){ return null; }
            @_remove public Hoverboard createNimbus( Hoverboard board ){ return null; }
            @_remove Hoverboard THUNDERBOLT = new Hoverboard();
        }).importStatic(Collections.class.getCanonicalName()+".*",
                    "com.mattel.Hoverboard.Boards.*",
                    "com.mattel.Hoverboard.createNimbus.*");
        System.out.println( _c);
    }    
    
    public void test12Draft(){
        _method _hexDigit = _method.of(new Object(){
            public char hexDigit(int i) {
                return (char) (i < 10 ? i + '0' : i - 10 + 'a');
            }
        });
        _method _byteToHex = _method.of(new Object(){
            public String byteToHex(int b) {
                char[] result = new char[2];
                result[0] = hexDigit((b >>> 4) & 0xf);
                result[1] = hexDigit(b & 0xf);
                return new String(result);
            }
            @_remove char hexDigit(int i){ return 0; }
        });
        _class _c = _class.of( "C" ).add( _hexDigit, _byteToHex );
        assertEquals("0f", (String)_proxy.of(_c).call("byteToHex", 15 ) );
    }
    
    public void test13Draft(){
        _class _c = _class.of("public abstract class HelloWorld")
                .method("protected abstract void flux();");
        System.out.println( _c );
    }
    
    public void test14Draft(){
        _class _c = _class.of( "HelloWorld", new Object(){
            private @_final String greeting;
            @_ctor public void HelloWorld(String greeting) {
                this.greeting = greeting;
            }
        });
        
        System.out.println( _c );
    }
    
    
    public void test15Draft(){
        _parameter _p = _parameter.of(String.class, "android").setFinal()
                .annotate("@Nullable");
        
        _method _welcomeOverlords = _method.of("welcomeOverlords")
                .addParameter(_p)                
                .addParameter("final String robot");
        
        System.out.println( _welcomeOverlords );
    }
    
    /**
     * https://github.com/square/javapoet#fields
     */    
    public void test16Draft(){
        _field _android = _field.of("private final String android;")
                .init(()-> "Lollipop v." + 5.0d );
        System.out.println( _android );
    }
    
    /* https://github.com/square/javapoet#interfaces */
    public void test17Draft(){
        _interface _helloWorld = _interface.of("HelloWorld", new Object(){
           public @_static @_final String ONLY_THING_THAT_IS_CONSTANT = "change";
           @_abstract void beep(){}
        });
        System.out.println( _helloWorld );
        _javac.of( _helloWorld );
    }
    
    /* https://github.com/square/javapoet#enums */    
    public void test18Draft(){
        _enum _e = _enum.of("Roshambo")
                .constants("ROCK","PAPER","SCISSORS");
        System.out.println( _e );
    }
    
    public void test19Draft(){
        _enum _e = _enum.of("Roshambo")
            .field("private final String handSign;")
            .apply(_autoConstructor.$)
            .constant("ROCK(\"fist\")", new Object(){
                @Override
                public String toString(){
                   return "avalanche!";
                }        
            })
            .constant("SCISSORS(\"peace\")")
            .constant("PAPER(\"flat\")");
        
       System.out.println( _e );
       _javac.of(_e);
    }
    
    /* https://github.com/square/javapoet#anonymous-inner-classes */    
    public void test20Draft(){
        _class _c = _class.of("class HelloWorld", new Object() {
            public void sortByLength(List<String> strings) {
                Collections.sort(strings, new Comparator<String>() {
                    @Override
                    public int compare(String a, String b) {
                        return a.length() - b.length();
                    }
                });
            }
        }).imports(Collections.class, Comparator.class, List.class);
        
        System.out.println(_c);
        _javac.of( _c );
    }
    
    /* https://github.com/square/javapoet#annotations */    
    public void test21Draft(){
        _method _m = _method.of(new Object(){
           @Override
           public String toString(){
               return "Hoverboard";
           }        
        });
        //or
        _m.annotate(Override.class);
    }
    
    class LogReceipt{
        
    }
    @interface Headers{
        
    }    
    public void test22Draft(){
        _anno _a = _anno.of(Headers.class)
                .addAttr("accept", "application/json; charset=utf-8")
                .addAttr("userAgent","Square Cash");
        _method _m = _method.of("public abstract LogReceipt recordEvent(LogRecord logRecord);")
                .annotate(_a);       
        System.out.println( _m );
    }
    
   
    public @interface HeaderList{
        Header[] value();
    }
    public @interface Header{
        String name();
        String value();
    }
    
    public void test23Draft(){       
        _anno _a = _anno.of(new Object(){
            @HeaderList({
                @Header(name="Accept", value="application/json; charset=utf-8"),
                @Header(name="User-Agent", value="Square Cash"),
            })    
            class C{}
        });
        
        _method _m = _method.of("public abstract LogReceipt recordEvent(LogRecord logRecord);")
            .annotate(_a);
        System.out.println( _m );                
    }   
}
