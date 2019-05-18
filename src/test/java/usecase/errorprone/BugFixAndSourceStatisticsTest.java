package usecase.errorprone;

import com.github.javaparser.ast.expr.AssignExpr;
import com.github.javaparser.ast.expr.BinaryExpr;
import com.github.javaparser.ast.stmt.BreakStmt;
import com.github.javaparser.ast.stmt.ContinueStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.ReturnStmt;
import com.github.javaparser.ast.stmt.Statement;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.ThrowStmt;
import draft.java._class;
import draft.java.proto.$case;
import draft.java.proto.$expr;
import draft.java.proto.$stmt;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import junit.framework.TestCase;

/**
 * <P>These $prototypes were inspired by the Talk 
 * <A HREF="https://youtu.be/sPW2Pz2dI9E?list=PLX8CzqL3ArzVnxC6PYxMlngEMv3W1pIkn">
 * Codebase Research with Louis Wasserman and Kevin Bourrillion</A>
 * given at the JVMLS in October 2018.
 * 
 * <P>Although draft and $proto were not designed to do this specific job, I wanted 
 * to build some real world scenarios (that weren't contrived), so this
 * fit the bill quite nicely.
 * 
 * <P>FWIW this is more about collecting "code metrics" than really about 
 * the "Error Prone" compiler which has Fix Suggestions for refactoring 
 * error prone code.
 *
 * @author Eric
 */
public class BugFixAndSourceStatisticsTest extends TestCase {
    
    /**
     * Here's the discussion of errorprone (this specific case matches when someone throws null)
     * https://youtu.be/sPW2Pz2dI9E?list=PLX8CzqL3ArzVnxC6PYxMlngEMv3W1pIkn&t=998
     */
    //public static final $stmt $THROW_NULL = $stmt.of("throw null;");
    public static final $stmt<ThrowStmt> $THROW_NULL = $stmt.throwStmt("throw null;");
    
    /** This is the suggested "fix" instead of throwing null*/
    public static final $stmt<ThrowStmt> $THROW_NPE = $stmt.throwStmt("throw new NullPointerException();");
    
    public void testCheckMatchForThrowNull(){
        class C{
            void m() throws Exception {
                throw null;
            }
        }
        assertEquals(1, $THROW_NULL.count(C.class));
    }
    
    public void testAutoFixForThrowNull(){
        class D{
            void m() throws Exception {
                throw null;
            }
        }
        _class _c = (_class)$THROW_NULL.replaceIn(D.class, $THROW_NPE);        
        assertEquals(1, $THROW_NPE.count(_c));
    }
    
    
    /** 
     * 
     * Source Metrics 
     * 
     * what percent of our break and continue statements are labeled ?
     * i.e. break v break outer;
     * continue v continue outer;
     * https://youtu.be/sPW2Pz2dI9E?list=PLX8CzqL3ArzVnxC6PYxMlngEMv3W1pIkn&t=1093
     */ 
    public void testCountBreakAndContinueLabeledVsUnlabeled(){        
        //EXAMPLE CODE
        class BreakAndContinue{
            public void m(){
                int j = 1000;
                outer:
                for(int i=0;i<j; i++){
                    if( i % 2 ==0 ){ continue outer; }
                    switch( i ){
                        case 1 : break;
                        case 2 : continue;
                        case 3: break outer;
                        case 4: continue outer;
                    }                    
                }
            }
        }
        
         //this prototype means ANY break statement
        $stmt<BreakStmt> $anyBreak = $stmt.breakStmt();
        
        AtomicInteger unlabeledBreak = new AtomicInteger(0);
        AtomicInteger labeledBreak = new AtomicInteger(0);
        
        
        $anyBreak.forEachIn(BreakAndContinue.class, b -> {
            if(b.getValue().isPresent()){
              unlabeledBreak.incrementAndGet();
            } 
            else{
              labeledBreak.incrementAndGet();
            } 
        });
        assertEquals(1, unlabeledBreak.get());
        assertEquals(1, labeledBreak.get());
        
        
        $stmt<ContinueStmt> $anyContinue = $stmt.continueStmt();
        AtomicInteger unlabeledContinue = new AtomicInteger(0);
        AtomicInteger labeledContinue = new AtomicInteger(0);
        
        $anyContinue.forEachIn(BreakAndContinue.class, b -> {
            if(b.getLabel().isPresent()){
              labeledContinue.incrementAndGet();
            } 
            else{
              unlabeledContinue.incrementAndGet();
            } 
        });
        assertEquals(2, labeledContinue.get());
        assertEquals(1, unlabeledContinue.get());
                
    }    
    
    /**
     * do developers put the default statement ANYWHERE but at the end?
     * https://youtu.be/sPW2Pz2dI9E?list=PLX8CzqL3ArzVnxC6PYxMlngEMv3W1pIkn&t=1300
     */
    public void testWhereDefaultClauseInSwitchStmt(){
        
        class INT{
            void m(int i){
                switch(i){ //default end
                    case 1 :  break;
                    case 2:   break;
                    default : System.out.println("default");
                }
            }
            void v(int i){
                switch(i){ //default middle
                    case 1 : break;
                    default : System.out.println("default");
                    case 3 : System.out.println("3");
                }
                
                switch(i){ //default first
                    default : System.out.println("First");
                    case 0 : assert(true);
                }
            }
        }
        
        AtomicInteger defaultAtEnd = new AtomicInteger(0);
        AtomicInteger defaultFirst = new AtomicInteger(0);
        AtomicInteger defaultNotAtEnd = new AtomicInteger(0);
        
        
        $stmt<SwitchStmt> $defaultFirst = 
            $stmt.switchStmt(ss -> ss.getEntries().isNonEmpty() && ss.getEntries().get(0).getLabels().isEmpty() );
        
        $stmt<SwitchStmt> $defaultNotAtEnd = 
            $stmt.switchStmt(ss -> ss.getEntries().isNonEmpty() && ss.getEntries().get(ss.getEntries().size() -1).getLabels().isEmpty() );
        
        $stmt.switchStmt().forEachIn(INT.class, s->{
            if( $defaultFirst.matches(s)){
                defaultFirst.incrementAndGet();
            }
            else if( $defaultNotAtEnd.matches(s)){
                defaultNotAtEnd.incrementAndGet();
            } else{
                defaultAtEnd.incrementAndGet();
            }
        });
        
        assertEquals(1, defaultFirst.get());
        assertEquals(1, defaultAtEnd.get());        
        assertEquals(1, defaultNotAtEnd.get());        
    }
    
    
    //hmm not incredibly specified, but 
    public void testSwitchWithNullTest(){
        //this is how we could check to see if it's a case null 
        $expr<BinaryExpr> isNull = $expr.binary("$var$ == null");
    }
    
    /**
     * Find the times where a user emplys 
     * https://youtu.be/sPW2Pz2dI9E?list=PLX8CzqL3ArzVnxC6PYxMlngEMv3W1pIkn&t=1475
     * 
     */
    public void testSwitchStatementsWithFallThrough(){
        //what this "means" if cases that have labels AND contain statements 
        //but don't end with a breakStmt OR ReturnStmt
        class CaseStmtWithFallThrough{
            void m(int i){
                switch(i){
                    case 1: System.out.println("FallsThrough");  
                    case 2: System.out.println("FallsThrough");
                    case 3: System.out.println( "Falls Through");
                    case 4: System.out.println(4); break;
                    default : System.out.println("Breaks");
                }
            }
        }
        $case $c = $case.of(c-> {
            if( c.getLabels().isNonEmpty()  //can't be the default statement must have label
                && c.getStatements().isNonEmpty() ){ //MUST have statements                
                //get the last statement
                Statement lastStmt = c.getStatements().get(c.getStatements().size() -1);
                //if it's not a BreakStmt/ReturnStmt, then it falls though
                return !((lastStmt instanceof BreakStmt) || (lastStmt instanceof ReturnStmt));
            }
            return false;            
        });
        
        assertEquals(3, $c.count(CaseStmtWithFallThrough.class));
    }
    
    /**
     *
     * https://youtu.be/sPW2Pz2dI9E?list=PLX8CzqL3ArzVnxC6PYxMlngEMv3W1pIkn&t=1587 
     */
    public void testWhatAreSwitchCasesDoingCategorically(){
        class GG{
            int m(int i){
                int ff =23;
                switch( i){
                    case 1 : ff = 1; //assign
                    case 2 : return 23; //return
                    case 3 : throw new RuntimeException(); //throw
                    case 4 : if( ff == 3){ //mixed
                        ff = 12;
                        return 45;
                    }
                    default : System.out.println( "Hello"); //other
                }
                return 45;
            }
        }
        //here are the categories
        AtomicInteger assignCount = new AtomicInteger(0);
        AtomicInteger returnCount = new AtomicInteger(0);
        AtomicInteger throwCount = new AtomicInteger(0);
        AtomicInteger otherCount = new AtomicInteger(0);
        AtomicInteger mixedCount = new AtomicInteger(0);
        
        //these are how we look "into" the statements to find the category
        $expr<AssignExpr> $anyAssign = $expr.assign();
        $stmt<ReturnStmt> $anyReturn = $stmt.returnStmt();
        $stmt<ThrowStmt> $anyThrow = $stmt.throwStmt();
        
        //look at all case s, 
        $case.any().forEachIn(GG.class, c -> {
            boolean isAssign = false;
            boolean isReturn = false;
            boolean isThrow = false;
            int matchCount = 0;
            if( c.getStatements().isEmpty() ){ //empty cases are "other"
                otherCount.incrementAndGet();
            } else if( $anyAssign.firstIn(c) != null ){
                isAssign = true;
                matchCount++;
            } if( $anyReturn.firstIn(c) != null ){
                isReturn = true;
                matchCount++;
            } if( $anyThrow.firstIn(c) != null){
                isThrow = true;
                matchCount++;
            }
            if( matchCount == 1 ){ //if its a "simple" match assign/return throw
                if( isAssign ){
                    assignCount.incrementAndGet();
                }
                else if( isThrow ){
                    throwCount.incrementAndGet();
                }
                else{
                    returnCount.incrementAndGet();
                }
            } else{ //either NONE of assign/return/throw occurrec
                if( matchCount == 0){
                    otherCount.incrementAndGet();
                }
                if( matchCount > 1 ){ //or more than one (a mixed result)
                    mixedCount.incrementAndGet();
                }
            }
        });
        
        assertEquals(1, assignCount.get());
        assertEquals(1, throwCount.get());
        assertEquals(1, returnCount.get());
        assertEquals(1, otherCount.get());
        assertEquals(1, mixedCount.get());
    }
    
    //It would be great to allow certain statements to be constructed by
    // other expressions
    
    // if proto builder
    // while proto builder
    // 
    // i.e. if statements with condition
    
    //test check null
    //$stmt<IfStmt> ifNull = 
    //    $stmt.ifStmt(i-> i.i.getCondition().isBinaryExpr() 
    //        && i.getCondition().asBinaryCondition() )
    
    //test SwitchWithFallThrough 
}
