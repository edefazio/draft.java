package usecase.gitter;

import com.github.javaparser.ast.expr.Expression;
import draft.java.Ast;
import draft.java.proto.$expr;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import junit.framework.TestCase;

/**
 *
 * @author Eric
 */
public class FindAllOperandsTest extends TestCase {
    
    public void testFindOperands(){
        class L{
            int a = 1+2;
            int b = 2-3;
            int d = 2*4;
            int f = 6/3;
            int g = 3%2;
            int h = a++;
            int j = b--;
            boolean k = (a==b);
            boolean l = (a!=b);
            boolean m = (a>b);
            boolean n = (a<b);
            boolean o = (a<=b);
            boolean p = (a>=b);
            
            int q = a&b;
            int r = a|b;
            int s = a^b;
            int t = ~a;            
        }
        String[] expect = {"+", "-", "*","/", "%","++", "--", "==", "!=", ">", "<", "<=", ">=", "&", "|", "^", "~"};
        
        //If we want unique operands
        Set<String> operators = new HashSet<>();
        $expr.binary().forEachIn(L.class, b-> operators.add(b.getOperator().toString()));
        $expr.unary().forEachIn(L.class, b-> operators.add(b.getOperator().toString()));
        assertEquals(17, operators.size());    
        
        //If we want the ordered operands
        List<Expression> exprs = new ArrayList<>();
        exprs.addAll($expr.binary().listIn(L.class));
        exprs.addAll($expr.unary().listIn(L.class));
        
        List<String> orderedOperators = new ArrayList<>();
        Ast.sortNodesByPosition(exprs).forEach(e -> {
            e.ifBinaryExpr(b-> orderedOperators.add(b.getOperator().asString()));
            e.ifUnaryExpr(u-> orderedOperators.add(u.getOperator().asString()));
        });
        assertEquals(17, orderedOperators.size());  
        for(int i=0;i<expect.length; i++){
            assertEquals( expect[i], orderedOperators.get(i));
        }
        
    }
    
}
