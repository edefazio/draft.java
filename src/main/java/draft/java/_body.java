package draft.java;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.expr.LambdaExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithBlockStmt;
import com.github.javaparser.ast.nodeTypes.NodeWithOptionalBlockStmt;
import com.github.javaparser.ast.nodeTypes.NodeWithStatements;
import com.github.javaparser.ast.stmt.*;
import com.github.javaparser.printer.PrettyPrinterConfiguration;
import draft.DraftException;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * The "body" part of methods, constructors, and static blocks (i.e.
 * {@link Statement}s getting stuff done)
 *
 * @author Eric
 */
public final class _body implements _model {

    private final Object parentNode;

    public static _body of(NodeWithBlockStmt nwbs) {
        return new _body(nwbs);
    }

    public static _body of(BlockStmt bs) {
        if (bs.getParentNode().isPresent()) {
            Node n = bs.getParentNode().get();
            return new _body(n);
        }
        throw new DraftException("No parent for _body");
    }

    public static _body of(NodeWithOptionalBlockStmt nwobs) {
        return new _body(nwobs);
    }

    private _body(Object parentNode) {
        this.parentNode = parentNode;
    }

    boolean isPresent() {
        if (parentNode instanceof NodeWithOptionalBlockStmt) {
            NodeWithOptionalBlockStmt nobs = (NodeWithOptionalBlockStmt) parentNode;
            return nobs.getBody().isPresent();
        }
        return true;
    }

    /**
     * NOTE this could be null
     *
     * @return
     */
    public BlockStmt ast() {
        if (parentNode instanceof NodeWithOptionalBlockStmt) {
            NodeWithOptionalBlockStmt nobs = (NodeWithOptionalBlockStmt) parentNode;
            if (nobs.getBody().isPresent()) {
                return (BlockStmt) nobs.getBody().get();
            }
            return null;
        }
        NodeWithBlockStmt nbs = (NodeWithBlockStmt) parentNode;
        return nbs.getBody();
    }

    public Statement getStatement(int index) {
        return ast().getStatement(index);
    }

    public NodeList<Statement> getStatements() {
        if (isPresent()) {
            return ast().getStatements();
        }
        return new NodeList<>();
    }

    public boolean is(String... body) {
        if (body == null) {
            return !this.isPresent();
        }
        BlockStmt bs = Ast.blockStmt(body);
        return Objects.equals(this.ast(), bs);
    }

    public boolean is(BlockStmt bs) {
        return Objects.equals(this.ast(), bs);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final _body other = (_body) obj;
        if (this.parentNode == other.parentNode) {
            return true; //two different _body referring to the same NodeWithBody (short curcuit)
        }
        if (isPresent() != other.isPresent()) {
            return false;
        }
        if (!isPresent()) {
            return true;
        }

        BlockStmt t = this.ast();
        BlockStmt o = other.ast();

        NodeList<Statement> ts = t.getStatements();
        NodeList<Statement> os = o.getStatements();

        if (ts.size() != os.size()) {
            //if we can avoid printing, we should
            return false;
        }
        String tnc = t.toString(Ast.PRINT_NO_COMMENTS);
        String onc = o.toString(Ast.PRINT_NO_COMMENTS);
        return tnc.equals(onc);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        if (!isPresent()) {
            return 0;
        }
        hash = 53 * hash + Objects.hashCode(ast().toString(Ast.PRINT_NO_COMMENTS));
        return hash;
    }

    /**
     * Prints out the body using the pretty printer configuration provided
     *
     * For an EXAMPLE on how to create a PreetyPrinterConfiguration, see:
     * {@link draft.java.Ast#PRINT_NO_COMMENTS}
     *
     * @param ppc
     * @return
     */
    public String toString(PrettyPrinterConfiguration ppc) {
        if (!isPresent()) {
            return "";
        }
        return ast().toString(ppc);
    }

    @Override
    public String toString() {
        if (!isPresent()) {
            return "";
        }
        return ast().toString();
    }

    public boolean isEmpty() {
        return !isPresent() || ast().isEmpty();
    }

    /**
     * Examples {@link _staticBlock}, {@link _method}, {@link _constructor}
     *
     * @author Eric
     * @param <T> enclosing TYPE (to access this)
     */
    public interface _hasBody<T extends _hasBody>
            extends _model {
        
        /**
         * @return gets the body
         */
        _body getBody();

        /**
         * Replace the entire _body with the body passed in
         *
         * @param body the new BODY
         * @return
         */
        T setBody(BlockStmt body);

        /**
         * @return return true if the member has a body
         */
        default boolean hasBody() {
            return getBody().isPresent();
        }

        /**
         * Returns a list of statements
         * NOTE: this lists the "top statements" 
         * (this DOES NOT WALK INTO statements, like compound statements, 
         * statements in for loops and nested blocks)
         * 
         * @return 
         */
        default List<Statement> listStatements(){
            if( this.hasBody() ){
                return getBody().getStatements();
            }
            return Collections.EMPTY_LIST;
        }
        
        /**
         * Returns the statements that are of the StmtClass
         * <PRE>
         * //list all return statements from the class
         * List<ReturnStmt> retSts = _m.listStatements( Ast.RETURN_STMT );
         * List<ThrowStmt> throwSts = _m.listStatements( Ast.THROW_STMT );
         * </PRE>
         * 
         * NOTE: this lists the "top statements" 
         * (this DOES NOT WALK INTO statements, like compound statements, 
         * statements in for loops and nested blocks)
         * @param <S> the statement class
         * @param stmtClass the statement class
         * @return the list of statements of the class
         */
        default <S extends Statement> List<S> listStatements(Class<S> stmtClass ){
            if( this.hasBody() ){
                List<S> stmts = new ArrayList<>();
                getBody().getStatements().stream()
                    .filter(s-> stmtClass.isAssignableFrom(s.getClass()))
                    .forEach(s -> stmts.add( (S)s ));
                return stmts;
            }
            return Collections.EMPTY_LIST;
        }
        
        /**
         *
         * @param body
         * @return
         */
        default T setBody(_body body) {
            return setBody(body.ast());
        }

        /**
         *
         * @param body
         * @return
         */
        default T setBody(String... body) {
            return setBody(Ast.blockStmt(body));
        }

        default T setBody(Statement st) {
            if (st.isBlockStmt()) {
                return setBody(st.asBlockStmt());
            }
            BlockStmt bs = new BlockStmt();
            bs.addStatement(st);
            return setBody(bs);
        }

        /**
         * Sets the body to be the body of the Lambda
         *
         * @param c the lambda containing the lambda body to set as the body of
         * the member
         * @return the modified member
         */
        default T setBody(Expr.Command c) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
            LambdaExpr le = Expr.lambda(ste);
            return setBody(le.getBody());
        }

        default T setBody(Function c) {
            StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
            LambdaExpr le = Expr.lambda(ste);
            return setBody(le.getBody());
        }

        /**
         * clear the contents of the BODY (the statements)
         *
         * @return the modified T
         */
        T clearBody();        

        /**
         * Add Statements to the end of the BODY
         *
         * @param statements the statements to add to the tail of the BODY
         * @return the modified T (_method, _constructor, _staticBlock)
         */
        T add(Statement... statements);

        /**
         * Add Statements to the BODY starting at startStatementIndex (0-based)
         *
         * @param startStatementIndex index of the position to add the
         * statements
         * @param statements the statements to add to the tail of the BODY
         * @return the modified T (_method, _constructor, _staticBlock)
         */
        T add(int startStatementIndex, Statement... statements);

        /**
         * Add Statements to the end of the BODY
         *
         * @param statements the statements as an Array of Strings
         * @return the modified T (_method, _constructor, _staticBlock)
         */
        default T add(String... statements) {
            BlockStmt bs = Ast.blockStmt(statements);
            //organize orphan comments
            List<Comment> coms = bs.getOrphanComments();
            Comment c = null;
            if (coms.size() > 0) {
                c = coms.get(0);
            }
            Collections.sort(coms, new Walk.CommentPositionComparator());

            for (int i = 0; i < bs.getStatements().size(); i++) {
                Statement st = bs.getStatement(i);
                if (c != null && c.getBegin().get().isBefore(st.getBegin().get())) {
                    getBody().ast().addOrphanComment(c);
                    coms.remove(0);
                    if (!coms.isEmpty()) {
                        c = coms.get(0);
                    } else {
                        c = null;
                    }
                }
                getBody().ast().addStatement(st);
                //add( bs.getStatements().toArray( new Statement[ 0 ] ) );
            }
            coms.forEach(co -> getBody().ast().addOrphanComment(co));
            return (T) this;
        }

        /**
         * Add Statements to the BODY starting at the startStatementIndex
         * (0-based)
         *
         * @param statementStartIndex the statement index (0 is the first
         * statement)
         * @param statements a Block of Strings representing statements (this is
         * first parsed before added
         * @return the modified T
         */
        default T add(int statementStartIndex, String... statements) {
            BlockStmt bs = Ast.blockStmt(statements);
            return add(statementStartIndex, bs.getStatements().toArray(new Statement[0]));
        }

        /**
         * Adds Statements to the end of the labeled Statement with NAME
         * "labelName"
         *
         * @param labelName the label to populate with the statements
         * @param statements the statements to add
         * @return the modified T
         */
        default T addAt(String labelName, String... statements) {
            Optional<LabeledStmt> ols
                    = this.getBody().ast().findFirst(LabeledStmt.class, ls -> ls.getLabel().toString().equals(labelName));
            if (!ols.isPresent()) {
                throw new DraftException("cannot find labeled Statement \"" + labelName + "\"");
            }
            Statement st = ols.get().getStatement();
            if (st.isBlockStmt()) {
                BlockStmt bs = st.asBlockStmt();
                NodeList<Statement> stmts = Ast.blockStmt(statements).getStatements();
                stmts.forEach(s -> bs.addStatement(s));
            } else {
                BlockStmt bs = Ast.blockStmt(statements);
                bs.addStatement(0, st);
                st.replace(bs);
            }
            return (T) this;
        }

        default boolean hasLabel(String labelName) {
            return this.getBody().ast().findFirst(LabeledStmt.class, ls -> ls.getLabel().toString().equals(labelName)).isPresent();
        }

        default T addAt(String labelName, Statement stmt) {
            List<Statement> stmts = new ArrayList<>();
            stmts.add(stmt);
            return addAt(labelName, stmts);
        }

        default T addAt(String labelName, List<Statement> stmts) {
            Optional<LabeledStmt> ols
                    = this.getBody().ast().findFirst(LabeledStmt.class, ls -> ls.getLabel().toString().equals(labelName));
            if (!ols.isPresent()) {
                throw new DraftException("cannot find labeled Statement \"" + labelName + "\"");
            }
            Statement st = ols.get().getStatement();
            if (st.isBlockStmt()) {
                BlockStmt bs = st.asBlockStmt();
                stmts.forEach(s -> bs.addStatement(s));
            } else {
                BlockStmt bs = Ast.blockStmt(stmts.toArray(new Statement[0]));
                bs.addStatement(0, st);
                st.replace(bs);
            }
            return (T) this;
        }

        /**
         * Gets the Labeled statement based on the label
         *
         * @param labelName
         * @return the Labeled Statement or THROWS EXCEPTION if not found
         */
        default LabeledStmt getAt(String labelName) {
            Optional<LabeledStmt> ols
                    = this.getBody().ast().findFirst(LabeledStmt.class, ls -> ls.getLabel().toString().equals(labelName));
            if (!ols.isPresent()) {
                throw new DraftException("cannot find labeled Statement \"" + labelName + "\"");
            }
            return ols.get();
        }

        /**
         * Replaces the Labeled Statement/Block with sequential code w/o a label
         * and without a block for instance:
         * <PRE>
         *
         * //FLATTEN WILL REPLACE A LABELED STATEMENT WITH A SINGLE STATEMENT
         * public int method(){
         *     label: System.out.println(1);
         * }
         * //...
         * method.flattenLabel("label");
         * //...(PRODUCES)
         * public int method(){
         *      System.out.println(1);
         * }
         *
         * //FLATTEN WILL REPLACE THE LABELED BLOCK WITH SEQUENTIAL CODE:
         *
         * public int method(){
         *     label: {
         *         System.out.println(1);
         *         System.out.println(2);
         *     }
         * }
         * //...
         * method.flattenLabel("label");
         * //...(PRODUCES)
         * public int method(){
         *     System.out.println(1);
         *     System.out.println(2);
         * }
         *
         * </PRE>
         *
         * @param labelName the NAME of the label to be flattened
         * @return the modified codeBlock
         * @throws DraftException is the code does not contain a label with the
         * LabelName
         */
        default T flattenLabel(String labelName) {

            Optional<LabeledStmt> ols
                    = this.getBody().ast().findFirst(LabeledStmt.class, ls -> ls.getLabel().toString().equals(labelName));
            while (ols.isPresent()) {
                LabeledStmt ls = ols.get();
                if (ls.getStatement().isBlockStmt()) {
                    BlockStmt bs = ls.getStatement().asBlockStmt();
                    NodeList<Statement> stmts = bs.getStatements();
                    if (stmts.isEmpty()) {
                        (ls.getParentNode().get()).replace(ls, new EmptyStmt());
                    } else if (stmts.size() == 1) {
                        //System.out.println("Single Statement");
                        ls.getParentNode().get().replace(ls, stmts.get(0));
                    } else {
                        Node parent = ls.getParentNode().get();
                        //BlockStmt parentNode = (BlockStmt)ls.getParentNode().get();
                        NodeWithStatements parentNode = (NodeWithStatements) parent;
                        //System.out.println("Block Statement" + parentNode.getClass());
                        //int si = parentNode.getStatements().indexOf(ls);
                        int stmtIndex = parentNode.getStatements().indexOf(ls);
                        //parentNode.getChildNodes().indexOf( ls );
                        for (int i = 0; i < stmts.size(); i++) {
                            //System.out.println( "Adding "+ stmts.get(i)+" at "+ stmtIndex +i );
                            parentNode.addStatement(stmtIndex + i, stmts.get(i));
                        }
                        parent.remove(ls);
                        //parentNode.removeIn(ls);
                    }
                } else {
                    //System.out.println("Single Statement");
                    ls.getParentNode().get().replace(ls, ls.getStatement().clone());
                }
                //check if there is another to be flattened
                ols = this.getBody().ast().findFirst(LabeledStmt.class, lbs -> lbs.getLabel().toString().equals(labelName));
            }
            return (T) this;
        }

        /**
         * Searches through the entity (in Preorder order) to find the first
         * instance of a nodeClass
         *
         * @param nodeClass
         * @param <N>
         * @return the first instance found or null
         */
        default <N extends Node> N findFirst(Class<N> nodeClass) {
            Optional<N> of = getBody().ast().findFirst(nodeClass);
            if (of.isPresent()) {
                return of.get();
            }
            return null;
        }

        /**
         * Searches through the entity (in Preorder order) to find the first
         * instance of a nodeClass that matches the nodeMatchFn
         *
         * @param nodeClass
         * @param nodeMatchFn
         * @param <N>
         * @return the first instance found or null
         */
        default <N extends Node> N findFirst(Class<N> nodeClass, Predicate<N> nodeMatchFn) {
            Optional<N> of = getBody().ast().findFirst(nodeClass, nodeMatchFn);
            if (of.isPresent()) {
                return of.get();
            }
            return null;
        }
    }    
}
