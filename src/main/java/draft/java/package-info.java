/**
 * <P>draft.java provides an alternate API (code as a data structure) 
 * to representing, analyzing and modifying a program as discrete .java source 
 * files in plain text. draft.java mediates between different representations: </P>
 * <UL>
 *   <LI>plain text .java source code i.e. ("int square(int x){ return x*x; }")
 *   <LI>AST (i.e. {@link com.github.javaparser.ast.body.MethodDeclaration} )
 *   <LI>draft Object (i.e. {@link draft.java._method}
 * </UL>
 * <PRE> 
 * you__________
 *   |          |
 *   |    ----draft
 *   |   /      |
 *   |  /------AST
 *   | /        |     
 *   |---------->---------->
 * .java      javac     .class
 * </PRE>
 * 
 * <P>...normally, the programmer interacts directly with the plain text .java
 * source files (directly or through an IDE).  Periodically the source code is 
 * compiled using the javac tool, which converts the .java source to Java bytecodes
 * in .class files.</P>
 * 
 * <PRE> 
 * you
 *   |---------->---------->
 * .java      javac     .class
 * </PRE>
 * 
 * 
 * <P>when programs are small (only tens of .java source files) this works well. 
 * however, when programs get larger (hundreds or thousands of .java 
 * source files) <B>changes to the program source becomes harder to manage</B>.</P>
 * 
 * <h3>the problem of scaling up to large programs</h3>
 * 
 * <P>the problem with managing the individual/discrete files is that, as the 
 * program starts to scale up to more and more files, it acts like a complex 
 * system and is interconnected in complex ways (a class in one file is depended 
 * on by many other classes, and itself depends on many classes to do its job 
 * within the context of the program.</P>  
 * 
 * <P>when we do a compile and build the program, it is NOT like building 
 * a wall of bricks, laying one discrete unit on top of another, but rather a 
 * complex graph of dependencies needs to be resolved, and it is often very opaque
 * whether the changes made to one unit/file don't cause a bad side effect within 
 * the context of the program as a whole.</P>
 * 
 * <P>although it makes our lives as developers more convenient when we can treat 
 * each (source file) as a separate "unit"; the program as a whole is a 
 * complex system, and wholistic understanding requires understanding the units
 * and most importantly the relationships between the units (Classes / etc).</P>
 * 
 * <P>draft.java allows the programmer to load, inspect, query, analyze and 
 * manipulate the source code as a wholistic Domain Object (data structure) at 
 * a higher level of abstraction than code as plain-text within discrete files.</P>  
 */
package draft.java;
