/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package usecase.errorprone;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.type.*;
import com.github.javaparser.ast.*;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.printer.PrettyPrintVisitor;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import draft.java.Ast;
import java.util.concurrent.atomic.AtomicInteger;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class UseCasePrettyPrintTest extends TestCase {

    public void testPP() {
        com.github.javaparser.printer.PrettyPrintVisitor ppv = new PrettyPrintVisitor(new PrettyPrinterConfiguration());
        PrettyPrinterConfiguration ppc = new PrettyPrinterConfiguration();
        CompilationUnit cu = Ast.of("public class C{",
                "    /** comment */",
                "    public void m(){",
                "    }",
                "}");
        System.out.println(cu.toString());
        System.out.println(cu.toString(ppc));

        MethodDeclaration md = cu.getType(0).getMethodsByName("m").get(0);
        System.out.println(md.toString());
        System.out.println(md.toString(ppc));

        PrettyPrinterConfiguration ppc2 = ppc.setIndentSize(12).setEndOfLineCharacter("<EOL>");

        System.out.println(md.toString(ppc2));
        System.out.println(cu.toString(ppc2));

    }

    

    public void testGetIndentLevel() {
        class C {
            class D {
                class E {
                    /**
                    * javadoc
                    */
                    public void m() {
                    }
                }
            }
        }
        CompilationUnit cu = Ast.of(C.class);
        System.out.println(cu);
        JavadocComment jdc = (JavadocComment) cu.getComments().get(0);
        Node commentedNode = jdc.getCommentedNode().get();
        AtomicInteger indentLevel = new AtomicInteger(0);
        System.out.println( commentedNode );
        commentedNode.walk(Node.TreeTraversal.PARENTS, p -> indentLevel.incrementAndGet());

        assertEquals(4, indentLevel.get());

        //ClassOrInterfaceDeclaration c = (ClassOrInterfaceDeclaration)cu.getType(0);
        //.getMembers().get(0);        
    }

    public void testAG() {
        PrettyPrintVisitor ppv = new PrettyPrintVisitor(new PrettyPrinterConfiguration());
        PrettyPrinterConfiguration ppc = new PrettyPrinterConfiguration();
        CompilationUnit cu = StaticJavaParser.parse("public class C\n"
                + "{\n"
                + "    /** comment */\n"
                + "    public void m()\n"
                + "    {\n"
                + "    }\n"
                + "}");
        System.out.println("CompilationUnit.toString()");
        System.out.println("--------------------------");
        System.out.println(cu.toString());
        System.out.println(cu.toString(ppc));

        System.out.println("MethodDeclaration.toString()");
        System.out.println("---------------------------");
        MethodDeclaration md = cu.getType(0).getMethodsByName("m").get(0);
        System.out.println(md.toString());
        System.out.println();

        System.out.println("MethodDeclaration.toString(ppc)");
        System.out.println("-------------------------------");
        System.out.println(md.toString(ppc));
        System.out.println();

        System.out.println("MethodDeclaration.toString(ppc2)");
        System.out.println("--------------------------------");
        PrettyPrinterConfiguration ppc2 = ppc.setIndentSize(12).setEndOfLineCharacter("<EOL>");
        System.out.println(md.toString(ppc2));
        System.out.println();

        System.out.println("CompilationUnit.toString(ppc2)");
        System.out.println("------------------------------");
        System.out.println(cu.toString(ppc2));
        System.out.println();

        System.out.println("JavadocDeclaration.toString(ppc2)");
        System.out.println("---------------------------------");
        JavadocComment jdc = md.getJavadocComment().get();
        System.out.println(jdc.toString(ppc2));

        //JavadocComment jdc = md.getJavadocComment().get();
        System.out.println(jdc.toString(ppc2));
    }
}
