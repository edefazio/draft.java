# draft.java
<P>draft.java is a developer-friendly API to <A HREF="#build">build</A>, <A HREF="#access">access</A>, <A HREF="#change">change</A>, <A HREF="#add">add</A>, <A HREF="#remove">remove</A>, and <A HREF="#analyze">analyze</A> .java source code; then optionally <A HREF="#compile">compile</A>, <A HREF="#load">load</A> and <A HREF="#use">use/run</A> the code from within a program. it's a "code generator", and much much more.  <SMALL>(it's like having a <A HREF=https://www.w3.org/TR/DOM-Level-1/introduction.html">DOM</A> for Java source code)</SMALL></P>

<H3>requirements</H3>
draft.java runs on Java 8 or later. 
draft.java depends only on the <A HREF="http://javaparser.org/">JavaParser</A> core (3.12.0 or later) library for transforming Java source code into ASTs. draft.java can generate source code for any version of Java (Java 1.0 - 12).  

```xml
<dependency>
   <groupId>draft</groupId>
   <artifactId>draft.java</artifactId>
   <version>1.0</version>   
</dependency>
<dependency>
    <groupId>com.github.javaparser</groupId>
    <artifactId>javaparser-core</artifactId>
    <version>3.12.0</version>
</dependency>
```          

<H3>how to...</H3>

<H4><A name="build">building a _type (_class, _enum, _interface, _annotation)</A></H3>
<P>the most convenient way to <B>build</B> a DOM-like _type is by pass in an 
  existing Class to the _type.of(Class) method (the .java source of the Class is modeled)</A></P>

```java
public class Point { @Deprecated int x, y; }
_class _c = _class.of(Point.class); //_class _c represents the source of Point.java

public interface Drawable{ public void draw(); }
_interface _i = _interface.of(Drawable.class); //_interface _i represents the source of Drawable.java

public enum State{ STABLE, REDRAW; }
_enum _e = _enum.of(State.class); //_enum _e represents the source of State.java

public @interface Refresh{ int value() default 0; }
 _annotation _a = _annotation.of(Refresh.class); //_annotation _a represents the source of Refresh.java
```

<P>alternatively you may "manually" build a _type (_class, _enum, _interface, _annotation) via the simple API</P>

```java  
//verify that building _types via Class is equivalent to building via component / Strings
assertEquals(_c, _class.of("Point").fields("@Deprecated int x,y;"));
assertEquals(_i, _interface.of("Drawable").method("public void draw();"));
assertEquals(_e, _enum.of("State").constants("STABLE", "REDRAW"));
assertEquals(_a, _annotation.of("Refresh").element("int value() default 0;"));
```

<H4><A name="access">access</A>ing members of _types</H4>
<A name="access">each _type gives <B>access</B> to individual members via <B>.getXXX(...)</B></A>

```java
_field _x = _c.getField("x");
_method _m = _i.getMethod("draw");
_enum._constant _ec = _e.getConstant("STABLE");
_annotation._element _ae = _a.getElement("value");
```  

<P><B>accessing</B> lists of like members of each _type via <B>.listXXX()</B></P>

```java
List<_field> _fs = _c.listFields();                  //list all fields on _c
List<_method> _ms = _i.listMethods();                //list all methods on _i 
List<_enum._constant> _ecs = _e.listConstants();     //list all constants on _e
List<_annotation._element> _aes = _a.listElements(); //list all elements on _a
```

<P>each _type can <B>selectively list</B> members based on a lambda with <B>.listXXX(Predicate)</B></P>
   
```java   
_fs = _c.listFields(f -> f.isPrivate());        //list all private fields on _c   
_ms = _i.listMethods(m -> m.isDefault());       //list all default methods on _i
_ecs = _e.listConstants(c -> c.hasArguments()); //list all constants with constructor arguments on _e
_aes = _a.listElements(e -> e.hasDefault());    //list all elements with defaults on _a
```

<P>we can also list all (generic) members with <B>.listMembers(Predicate)</B></P>

```java
_annot = _c.listMembers(m -> m.hasAnnos());     //list all annotated members 
```

<H4><A name="change">change</A> _types & members</H4>
<P><B>change</B>s can be applied to the _type or members are reflected in the source of the _class</P>

```java  
//apply changes to top level _type
_c.implements(Serializable.class); //add Serializable import & implement Serializable to _c
_i.packageName("graphics.draw");   //set Package Name on _i
_e.annotate(Deprecated.class);     //add Deprecated import & add the @Deprecated annotation to _e
_a.imports(Target.class,ElementType.class); //import (2) classes to _a
_a.setTargetRuntime()
  .setE

//apply changes to members
_x.init(0);               //initialize value of field x to be 0
_c.getField("y").init(0); //initialize field y to be 0
```

<P>_types allow lambdas to simplify <B>iteration</B> over members with <B>forXXX(Consumer)</B></P>

```java  
_c.forFields(f->f.setPrivate()); //set ALL _fields to be private on _c
_c.forFields(f->f.removeAnnos(Deprecated.class)); //remove Deprecated annotation from ALL fields of _c
_e.forConstants(c->c.addArgument(100)); //add constructor argument 100 for ALL constants of _e

_i.forMembers(m -> m.annotate(Deprecated.class)); //apply @Deprecated to all member fields, methods, of _i 
```

<P>_types can also <B>selectively change</B> members easily with <B>forXXX(Predicate, Consumer)</B></P>

```java
_c.forFields(f->f.isStatic() && f.hasInit(), f->f.setFinal()); //set all static initialized fields as final
_i.forMethods(m->m.isStatic(), m->m.setPublic()); //select all static methods & make them public
```

<H4><A name="add">adding members</A> to _types</H4>
<P>each _type provides methods for <B>adding</B> members appropriate for the underlying _type</P>

```java  
_c.field("public static final int ID = 1023;");
_c.method("public int getX(){ return this.x; }");

_i.field("public static final int VERSION = 12;");
_e.constant("INVALID(100);"); //add a new constant to _e
_a.element("String name() default "";"); //add a new annotation element to _a

_c.field("/** temp field */ public String temp;");
_c.method("public String getTemp() { return temp; }");
```

<P>_types provide the ability to "pass code around" via lambda bodies and anonymous objects. 
(this technique allows source code to be parsed / checked and presented/colorized in real time by the IDE, 
and not treated like escaped plain text... much easier to read, debug, and modify.)</P>

```java
/* this method body is defined by a lambda body */
_c.method("public int getY()", (Integer y)->{ return y; });

/* the riseRun method is defined on an anonymous object & added to the _class */
_c.method( new Object(){ int x, y; //these exist to avoid compiler errors
   public double riseRun(){
       return y * 1.0d / x * 1.0d;
   }
});
```

<H4><A name="remove">removing</A> members from _types</H4>
<P>remove members to the _class with <B>`removeXXX(...)`</B></P>

```java
_c.removeField("temp");                             //remove field named "temp" from _c
_c.removeMethods(m->m.getName().equals("getTemp")); //remove all methods with name "getTemp" from _c
_e.removeConstant("INVALID");                       //remove constant named "INVALID" from _e
_i.removeFields(f-> f.isStatic());                  //remove all static fields on _i
```

<H4>automate with macros</H4>
<P><B>macros</B> can automate repetitive manual coding tasks when building _types. <A HREF="https://github.com/edefazio/draft.java/tree/master/src/main/java/draft/java/macro">use the built in ones</A> -or- easily build your own</P>

```java
_c.apply(_autoSet.$,_autoEquals.$,_autoHashCode.$); //adds set methods & equals, hashCode methods
```

<H4>the common _type interface</h4>
<P>to uniformly collect or operate on _types, we use the _type interface.</P> 

```java 
List<_type> _ts = new ArrayList<>();
_ts.add(_c);
_ts.add(_i);
_ts.add(_e);
_ts.add(_a);

//here we can operate on all types (_class _c, _enum _e, _interface _i, and _annotation _a) 
_ts.forEach( t-> t.packageName("graphics.draw") ); //change the package name for ALL _types
```

<H4>print the .java source</H4>
<P>for testing and debugging support, you can always use `toString()` to get the .java source code for a `_type`</P>

```java
System.out.println(_c);

//PRINTS:
package graphics.draw;

import java.io.Serializable;

public class Point implements Serializable{

    private int x = 0, y = 0;

    public int getX() {
        return this.x;
    }

    public int getY() {
        return y;
    }

    public double riseRun() {
        return y * 1.0d / x * 1.0d;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (this == o) {
            return true;
        }
        if (getClass() != o.getClass()) {
            return false;
        }
        Point test = (Point) o;
        boolean eq = true;
        eq = eq && this.x == test.x;
        eq = eq && this.y == test.y;
        return eq;
    }

    public int hashCode() {
        int hash = 5;
        int prime = 3;
        hash = hash * prime + x;
        hash = hash * prime + y;
        return hash;
    }
}
```

<H3>instant feedback</H3>
<P>draft.java doesn't stop at <I>just</i> building or modifying source code, it also provides
"instant feedback" by compiling & loading the dynamically built
code, and allowing the code to be loaded and used. <I>(This gives developers a tight feedback loop, to generate
build, compile and use or test code in a single program without a restart)</I></P>

<H4><A NAME="compile">compiling</A> _types with _javac</H4>
<P>to <B>compile</B> the .java code represented by one or more _type (_class, _enum, _interface, _annotation) use _javac.of(...):</P>

```java
//we can verify the generated code is valid Java, call the javac compiler
_classFiles _cfs = _javac.of(_c); //compile & return the _classFiles (bytecode) for _c _class
_classFiles _allCfs = _javac.of(_c, _i, _a, _e); //compile & return _classFiles for all _types
```

<P> we can pass in options to the javac compiler via a builder <B>_javac.options().XXX</B></P>

```java
_classFiles _allCfs = _javac.of( 
    _javac.options()
        //https://stackoverflow.com/questions/44067477/drawbacks-of-javac-parameters-flag
        .parameterNamesStoredForRuntimeReflection() 
        .terminateOnWarning(), //strict compile: fail if any warning is found
    _c, _i, _a, _e); //compile & return the _classFiles (bytecode) for the (_c, _i, _a, and _e) _types
```

<H4><A NAME="load">compile & load</A> in one step via _project</H4>
<P>to compile and <B>load</B> (in a new ClassLoader) the .java code represented by one or more _class, _enum, _interface... _types,
   use <B>_project</B>:</P>

```java
// we can create a _project of a single _type
_project _proj = _project.of(_c);

List<Class> loadedClasses = _proj.listClasses(); //list all loaded classes
// _project constructor optionally accepts _javac options
_proj = _project.of( _javac.options().terminateOnWarning(), _c, _a, _i, _e);
```

<P>_project gives you rudimentary access to individual Classes</P>

```java
assertEquals(1023, _proj.get(_c, "ID")); //get the value of a static field value from the Point.class
Object aPoint = _proj._new(_c); //create a new instance of the Point.class
```

<H4><A NAME="use">using a _proxy</A> to interact with dynamic instances</H4>   
<P>a <B>_proxy</B> simplifies the using a _class instances.</P>

```java
_proxy _p = _proj._proxy(_c ); //build a proxy from the Point.class created by _class _c
```
<P>field or property values on an instance can be retrieved with <B>.get(...)</B>.</P>

```java
/* _proxy simplifies accessing fields or getters on the proxied instance */
assertEquals(1023,_p.get("ID")); //get static field value
assertEquals(0,_p.get("x")); //call get method (note: x is private field)
```

<P>field or property values on an instance can be updated with <B>.set(...)</B>.</P>

```java
_p.set("x",100).set("y",200); //set(...) will call set methods since x, y are private
```

<P>static or instance methods can be invoked with <B>.call(...)</B></P>

```java
assertEquals(200.0d / 100.0d, _p.call("riseRun"));
```

<P>the object instance wrapped by a _proxy is available via the <B>.instance</B> field</P>
   
```java
assertEquals("Point", _p.instance.getClass().getCanonicalName());
```

<P>a new instance of the _class can be created with <B>_new(...)</B> on the proxy, 
   or we can create from the _project._proxy(...)</P>

```java
_proxy _p2 = _p._new().set("x",100).set("y",200);
assertEquals(_p2, _proj._proxy(_c).set("x",100).set("y",200));
```

<P>we can use these (2) _proxy instances (_p, _p2) to test the macro generated equals() and hashcode() methods</P>

```java
assertEquals(_p.instance, _p2.instance); //instance equality check
assertEquals(_p.instance.hashCode(),_p2.instance.hashCode()); //instance hashCode equality check

/* _proxy instances try to operate as "transparently" as possible */
assertEquals(_p, _p2); // _proxy delegates equals() method to the .instance method
assertEquals(_p.hashCode(), _p2.hashCode()); //_proxy delegates the hashCode() method to instance hashCode() method
```

<P>when we are satisfied with the .java code and .class that was generated, we can export it</P>

```java
/* export Point.java to a file & verify where it was written to */
assertTrue(_io.out("C:\\temp\\",_c).contains("C:\\temp\\Point.java"));

/* export Point.class to a file & verify where it was written to */
assertTrue(_io.out("C:\\temp\\",_proj.classFiles()).contains("C:\\temp\\Point.class"));

/* export Point.java & Point.class to files & verify where both files were written to */
assertTrue(_io.out("C:\\temp\\",_proj).containsAll("C:\\temp\\Point.class", "C:\\temp\\Point.java"));
```
