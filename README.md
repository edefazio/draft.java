# draft.java
<P>draft.java is a developer-friendly API to <A HREF="#build">build</A>, <A HREF="#access">access</A>, <A HREF="#change">change</A>, <A HREF="#add">add</A>, and <A HREF="#remove">remove</A>, <A HREF="#query">query</A>, elements from code; then <A HREF="#compile">compile</A>, <A HREF="#load">load</A> and <A HREF="#use">use/run</A> the code from within a program. It's a "code generator" and much much more.  <SMALL>(it's like having a <A HREF=https://www.w3.org/TR/DOM-Level-1/introduction.html">DOM</A> for Java source code)</SMALL>

<H3>requirements</H3>
draft.java requires Java 8 or later, and the <A HREF="http://javaparser.org/">JavaParser</A> core (3.12.0 or later) for transforming Java source code into ASTs. draft.java can generate code for any version of Java (Java 1.0 to Java 12).  

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

<H4><A name="build">build a draft.java model</A></H3>
<P>the most convenient way to <B>build</B> a DOM-like draft.java model (_class) is by pass in an 
  existing Class (the .java source of the class is modeled)</A></P>

```java
class Point { @Deprecated int x, y; }
_class _c = _class.of(Point.class);
```

<P>alternatively you may "manually" build a draft.java model (_class) via the simple API</P>

```java  
assertEquals(_c, _class.of("Point").fields("@Deprecated int x,y;"));
```

<H4><A name="access">access</A></H4>
<A name="access">draft gives <B>access</B> to individual sub-elements (fields, methods,...)</A>

```java
_field _x = _c.getField("x");
```  

<H4><A name="change">change</A></H4>
<B>change</B>s to elements are reflected in the _class</A>

```java  
_x.init(0); //initialize value of field x to be 0
_c.getField("y").init(0); //initialize field y to be 0
```

draft.java integrates lambdas to simplify <B>access</B> & <B>change</B>s on elements easily

```java  
_c.forFields(f->f.setPrivate()); //set ALL _fields to be private
_c.forFields(f->f.removeAnnos(Deprecated.class)); //remove Deprecated from ALL fields
```

<H4><A name="add">add</H4>
   draft.java lets you directly <B>add</B> elements to the _class</A>

```java  
_c.field("public static final int ID = 1023;");
_c.method("public int getX(){ return this.x; }");

_c.field("/** temp field */ public String temp;");
_c.method("public String getTemp() { return temp; }");
```

draft can "pass code around" via lambda bodies and anonymous objects. 
(easier for a developer to read, modify and debug in an IDE than dreaded escaped plain text.)

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

<H4><A name="remove">remove</A></H4>
<P>draft also lets you manually remove elements to the _class</P>

```java
_c.removeField("temp"); //remove field named "temp"
_c.removeMethods(m->m.getName().equals("getTemp")); //remove all methods with name "getTemp"
```

<H4>macros</H4>
<B>macros</B> can replace repetitive manual coding; <A HREF="https://github.com/edefazio/draft.java/tree/master/src/main/java/draft/java/macro">use the built in ones</A> -or- easily build your own

```java
_c.apply(_autoSet.$); //adds set methods for all non-static non-final fields (setX(), setY())
_c.apply(_autoEquals.$,_autoHashCode.$); //build equals() and hashCode() methods
```

<H4>print the .java source</H4>

```java
/* the _class can always be toString()ed and written out to .java source code */
System.out.println(_c);

//PRINTS:
public class Point {

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

<h3>instant feedback</H3>
<P>draft.java doesn't stop at <I>just</i> building or modifying source code, it also provides
a means of giving the developer "instant feedback" by compiling & loading the dynamically built
code, and also allowing the code to be loaded and used. <I>(This gives developers a tight feedback loop, to generate
build, compile and use or test code in a single program)</I></P>

<H4><A NAME="#compile">compile</A> via _javac</H4>
<P>to <B>compile</B> the .java code represented by one or more _class, _enum, _interface _types:</P>

```java
_classFiles _cfs = _javac.of(_c);
```

<H4><A NAME="#load">compile & load</A> via _project</H4>
<P>to compile and <B>load</B> (in a new ClassLoader) the .java code represented by one or more _class, _enum, _interface... _types,
   use _project:</P>

```java
_project _p = _project.of(_c);
List<Class> loadedClasses = _pr.listClasses(); //list all loaded classes
assertEquals(1023, _pr.get(_c, "ID"));//get a static field value from a class
Object aPoint = _pr._new(_c); //create a new instance of the Point.class
```

<H4><A NAME="#proxy>_proxy instances</A> for using code</H4>
   
<P>a _proxy helps you simplify the construction of a new instance of a 
dynamically built _class.</P>

```java
_proxy _p = _pr._proxy(_c );
```
<P>...a _proxy simplifies access to fields or properties on the instance with ```.get(...)```.</P>

```java
/* _proxy simplifies accessing fields or getters on the proxied instance */
assertEquals(1023,_p.get("ID")); //get static field value
assertEquals(0,_p.get("x")); //call get method (note: x is private field)
```

<P>...a _proxy simplifies updating fields or calling setters on an instance with .set(...).</P>

```java
/* _proxy can set fields or call set methods on the object (note: x & y are private fields) */
_p.set("x",100).set("y",200);
```

<P>...a _proxy simplifies calling instance methods on a new instance with .call(...);</P>

```java
assertEquals(200.0d / 100.0d, _p.call("riseRun"));
```
<P>...the underlying instance wrapped by a _proxy is always available via .instance field</P>
   
```java
assertEquals("Point", _p.instance.getClass().getCanonicalName());
```

/* we can create another instance */
_proxy _p2 = _p._new().set("x",100).set("y",200);

/* verify the macro generated equals() and hashcode() methods work */
assertEquals(_p, _p2); //proxies delegate equals method to instance method
assertEquals(_p.hashCode(), _p2.hashCode()); //proxies delegate hashCode method to instance method
assertEquals(_p.instance, _p2.instance); //instance equality check
assertEquals(_p.instance.hashCode(),_p2.instance.hashCode()); //instance hashcode call

/* export & verify the file "Point.java" was written */
assertTrue(_io.out("C:\\temp\\",_c).contains("C:\\temp\\Point.java"));

/* export & verify the file "Point.class" was written */
assertTrue(_io.out("C:\\temp\\",_p._classFile()).contains("C:\\temp\\Point.class"));
```


<TABLE>
<TR><TH>draft.java code</TH><TH>source code</TH></TR>
<TR><TD>_class.of(<span style="color: #a31515">&quot;Circle&quot;</span>).field(<span style="color: #a31515">&quot;int rad;&quot;</span>);
</TD><TD><span style="color: #0000ff">public</span> <span style="color: #0000ff">class</span> <span style="color: #2b91af">Circle</span>{<span style="color: #2b91af">int</span> rad;}
</TD></TR>     
<TR><TD>_field.of(<span style="color: #a31515">&quot;private static String ID;&quot;</span>);</TD><TD><span style="color: #0000ff">private</span> <span style="color: #0000ff">static</span> String ID;</TD></TR> 
<TR><TD>_method.of(<span style="color: #a31515">&quot;public String toString(){return ID;}&quot;</span>);</TD><TD><span style="color: #0000ff">public</span> String toString(){<span style="color: #0000ff">return</span> ID;}</TD></TR>
<TR><TD>_constructor.of(<span style="color: #a31515">&quot;Circle(int rad){this.rad=rad;}&quot;</span>);</TD><TD>Circle(<span style="color: #2b91af">int</span> rad){<span style="color: #0000ff">this</span>.rad=rad;}</TD></TR>                            
<TR><TD>_staticBlock.of(<span style="color: #a31515">&quot;ID = UUID.randomUUID.toString();&quot;</span>);</TD>
<TD><span style="color: #0000ff">static</span>{ID = UUID.randomUUID.toString();}</TD></TR>
<TR><TD>_interface.of(<span style="color: #a31515">&quot;Drawable&quot;</span>).method(<span style="color: #a31515">&quot;void draw();&quot;</span>);</TD><TD><span style="color: #0000ff">public</span> <span style="color: #0000ff">interface</span> <span style="color: #2b91af">Drawable</span>{<span style="color: #2b91af">void</span> draw();}
</TD></TR>           
<TR><TD>_annotation.of(<span style="color: #a31515">&quot;Refresh&quot;</span>).element(<span style="color: #a31515">&quot;int value();&quot;</span>)</TD><TD><span style="color: #0000ff">public</span> @interface Refresh{<span style="color: #2b91af">int</span> value();}</TD></TR>
<TR><TD>_enum.of(<span style="color: #a31515">&quot;State&quot;</span>).constants(<span style="color: #a31515">&quot;STABLE&quot;</span>,<span style="color: #a31515">&quot;REDRAW&quot;</span>);</TD><TD><span style="color: #0000ff">public</span> <span style="color: #0000ff">enum</span> State{STABLE,REDRAW;}</TD></TR>
</TABLE>
