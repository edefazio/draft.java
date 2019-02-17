# draft.java
<P>draft.java is a developer-friendly application programming interface (API) to <A HREF="#access">access</A>, <A HREF="#change">change</A>, 
<A HREF="#compile">compile</A>, <A HREF="#load">load</A>, and <A HREF="#run">run</A> Java source code.

<P>Think of draft.java as having a <A HREF=https://www.w3.org/TR/DOM-Level-1/introduction.html">DOM</A> for Java
code, you can build, navigate, add, change, and delete elements from code; then compile and use/run the code from
within a program.</P>

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
