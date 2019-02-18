# draft.java
<P>draft.java is a developer-friendly API to <A HREF="#build">build</A>, <A HREF="#access">access</A>, <A HREF="#change">change</A>, <A HREF="#add">add</A>, and <A HREF="#delete">delete</A>, <A HREF="#query">query</A>, elements from code; then <A HREF="#compile">compile</A>, <A HREF="#load">load</A> and <A HREF="#use">use/run</A> the code from within a program. It's a "code generator" and much much more.  <SMALL>(it's like having a <A HREF=https://www.w3.org/TR/DOM-Level-1/introduction.html">DOM</A> for Java source code)</SMALL>

<H3>Requirements</H3>
draft.java requires Java 8 or later, and the <A HREF="http://javaparser.org/">JavaParser</A> core (3.12.0 or later) for transforming Java source code into ASTs (Abstract Syntax Trees). draft.java can generate code for ANY version of Java (Java 1.0 to Java 12).  

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

<H3>How to...</H3>

<H4><A name="build">build</A></H3>
<P>the most convenient way to <B>build</B> a DOM-like draft.java model is by pass in an 
  existing Class (the .java source of the class is modeled)</A></P>

```java
class Point { @Deprecated int x, y; }
_class _c = _class.of(Point.class);
```

<P>alternatively you may "manually" build a _draft model via the simple API</P>

```java  
assertEquals(_c, _class.of("Point").fields("@Deprecated int x,y;"));
```

<H4><A name="access">Access</A></H4>
<A name="access">draft gives <B>access</B> to individual sub-elements (fields, methods,...)</A>

```java
_field _x = _c.getField("x");
```  

<H4><A name="change">Change</A></H4>
<B>change</B>s to elements are reflected in the _class</A>

```java  
_x.init(0); //initialize value of field x to be 0
_c.getField("y").init(0); //initialize field y to be 0
```

draft integrates lambdas to simplify <B>access</B> & <B>change</B>s on elements easily

```java  
_c.forFields(f->f.setPrivate()); //set ALL _fields to be private
_c.forFields(f->f.removeAnnos(Deprecated.class)); //remove Deprecated from ALL fields
```

<H4><A name="add">Add</H4>
draft lets you directly add elements to the _class</A>

```java  
_c.field("public static final int ID = 1023;");
_c.method("public int getX(){ return this.x; }");
```

draft can "pass code around" via lambda bodies and anonymous objects. 
(easier for a developer to read, modify and debug in an IDE than escaped plain text.)

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

<H4><A name="delete">Delete</H4>
draft also lets you manually remove elements to the _class</A>

```java
_c.removeField("ID");
```

<H4>macro</H4>
macros replace manual coding; <A HREF="https://github.com/edefazio/draft.java/tree/master/src/main/java/draft/java/macro">use the built in ones</A> -or- easily build your own

```java
_c.apply(_autoSet.$); //adds setter methods for all non-static non-final fields (setX(), setY())
_c.apply(_autoEquals.$,_autoHashCode.$); //build equals() and hashCode() methods
```

/* the _class can always be toString()ed and written out to .java source code */
System.out.println(_c);

/** INSTANT FEEDBACK **/

/* compile, load & create a new proxied instance based on the Java code at runtime */
_proxy _p = _proxy.of(_c);

/* _proxy simplifies accessing fields or getters on the proxied instance */
assertEquals(1023,_p.get("ID")); //get static field value
assertEquals(0,_p.get("x")); //call get method (note: x is private field)

/* _proxy can call set methods on the object (note: x & y are private fields) */
_p.set("x",100).set("y",200);

/* _proxy can simplify calling instance methods on a new instance */
assertEquals(200.0d / 100.0d,_p.call("riseRun"));

/* the underlying instance is a public field on the _proxy */
assertEquals("Point",_p.instance.getClass().getCanonicalName());

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
