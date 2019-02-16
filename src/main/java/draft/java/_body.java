package draft.java;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.Node.TreeTraversal;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.nodeTypes.NodeWithBlockStmt;
import com.github.javaparser.ast.nodeTypes.NodeWithOptionalBlockStmt;
import com.github.javaparser.ast.nodeTypes.NodeWithStatements;
import com.github.javaparser.ast.stmt.*;
import draft.DraftException;

import java.util.*;
import java.util.function.Predicate;

/**
 * container for code( AST {@link Statement}s made from {@link com.github.javaparser.ast.expr.Expression}s
 * @author Eric
 */
public final class _body implements _model {

    private final Object parentNode;

    public static _body of( NodeWithBlockStmt nwbs ) {
        return new _body( nwbs );
    }

    public static _body of( BlockStmt bs ) {
        if( bs.getParentNode().isPresent() ) {
            Node n = bs.getParentNode().get();
            return new _body( n );
        }
        throw new DraftException( "No parent for _body" );
    }

    public static _body of( NodeWithOptionalBlockStmt nwobs ) {
        return new _body( nwobs );
    }

    private _body( Object parentNode ) {
        this.parentNode = parentNode;
    }

    boolean isPresent() {
        if( parentNode instanceof NodeWithOptionalBlockStmt ) {
            NodeWithOptionalBlockStmt nobs = (NodeWithOptionalBlockStmt)parentNode;
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
        if( parentNode instanceof NodeWithOptionalBlockStmt ) {
            NodeWithOptionalBlockStmt nobs = (NodeWithOptionalBlockStmt)parentNode;
            if( nobs.getBody().isPresent() ) {
                return (BlockStmt)nobs.getBody().get();
            }
            return null;
        }
        NodeWithBlockStmt nbs = (NodeWithBlockStmt)parentNode;
        return nbs.getBody();
    }

    public Statement getStatement( int index ) {
        return ast().getStatement( index );
    }

    public NodeList<Statement> getStatements() {
        if( isPresent() ) {
            return ast().getStatements();
        }
        return new NodeList<>();
    }

    public boolean is( String... body ) {
        if( body == null ) {
            return !this.isPresent();
        }
        BlockStmt bs = Ast.blockStmt( body );
        return Objects.equals( this.ast(), bs );
    }

    public boolean is (BlockStmt bs){
        return Objects.equals( this.ast(), bs );
    }

    @Override
    public boolean equals( Object obj ) {
        if( this == obj ) {
            return true;
        }
        if( obj == null ) {
            return false;
        }
        if( getClass() != obj.getClass() ) {
            return false;
        }
        final _body other = (_body)obj;
        if( this.parentNode == other.parentNode ) {
            return true; //two different _body referring to the same NodeWithBody (short curcuit)
        }
        if( isPresent() != other.isPresent() ) {
            return false;
        }
        if( !isPresent() ) {
            return true;
        }

        BlockStmt t = this.ast();
        BlockStmt o = other.ast();

        NodeList<Statement> ts = t.getStatements();
        NodeList<Statement> os = o.getStatements();

        //TODO I SHOULD SIMULTANEOUSLY WALK EACH STATEMENT INSTEAD
        //THIS IS BAD ONLY WALKS THE TOP LEVEL STATEMENTS
        if( ts.size() != os.size() ) {
            //System.out.println( "STMT COUNT");
            return false;
        }

        for( int i = 0; i < ts.size(); i++ ) {
            if( !ts.get( i ).equals( os.get( i ) ) ) {
                //things get tricky with comments
                if( ts.get( i ).getComment().isPresent() || os.get( i ).getComment().isPresent() ) {
                    String tss = ts.toString();
                    String oss = os.toString();
                    if( !tss.equals( oss ) ) {
                        return false;
                    }
                }
                else {
                    //System.out.println( ts.get(i ) + " =/= "+ os.get(i) );
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int hashCode() {

        /**
         * TODO THIS IS WRONG need to account for statements with comments
         */
        int hash = 7;
        hash = 53 * hash + Objects.hashCode( ast() );
        return hash;
    }

    @Override
    public String toString() {
        if( !isPresent() ) {
            return "";
        }
        return ast().toString();
    }

    public boolean isEmpty() {
        return !isPresent() || ast().isEmpty();
    }

    /**
     * This is a
     *
     * @param t
     * @param o
     * @return
     */
    public static boolean isEqual( BlockStmt t, BlockStmt o ) {

        //statements without comments
        List<Statement> ts = new ArrayList<>();
        List<Statement> os = new ArrayList<>();
        t.walk( TreeTraversal.POSTORDER,
                s -> {
                    if( s instanceof Statement && !s.getComment().isPresent() ) {
                        ts.add( (Statement)s );
                    }
                } );
        o.walk( TreeTraversal.POSTORDER,
                s -> {
                    if( s instanceof Statement && !s.getComment().isPresent() ) {
                        os.add( (Statement)s );
                    }
                } );
        if( !Objects.equals( ts, os ) ) {
            return false;
        }
        //now clear
        ts.clear();
        os.clear();

        //now walk all statements that HAVE comments
        //and check for equality
        t.walk( TreeTraversal.POSTORDER,
                s -> {
                    if( s instanceof Statement && s.getComment().isPresent() ) {
                        ts.add( (Statement)s );
                    }
                } );
        o.walk( TreeTraversal.POSTORDER,
                s -> {
                    if( s instanceof Statement && s.getComment().isPresent() ) {
                        os.add( (Statement)s );
                    }
                } );
        //we have

        //NodeList<Statement> ts = t.getStatements();
        //NodeList<Statement> os = o.getStatements();
        //TODO I SHOULD SIMULTANEOUSLY WALK EACH STATEMENT INSTEAD
        //THIS IS BAD ONLY WALKS THE TOP LEVEL STATEMENTS
        if( ts.size() != os.size() ) {
            return false;
        }

        for( int i = 0; i < ts.size(); i++ ) {
            if( !ts.get( i ).equals( os.get( i ) ) ) {
                //things get tricky with comments
                if( ts.get( i ).getComment().isPresent() || os.get( i ).getComment().isPresent() ) {
                    String tss = ts.toString();
                    String oss = os.toString();
                    if( !tss.equals( oss ) ) {
                        return false;
                    }
                }
                else {
                    //System.out.println( ts.get(i ) + " =/= "+ os.get(i) );
                    return false;
                }
            }
        }
        return true;
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
         * @return gets the code block
         */
        _body getBody();

        /**
         *
         * @param body the new BODY
         * @return
         */
        T setBody( BlockStmt body );

        default boolean hasBody() {
            return getBody().isPresent();
        }

        /**
         *
         * @param body
         * @return
         */
        default T setBody( _body body ) {
            return setBody( body.ast() );
        }

        /**
         *
         * @param body
         * @return
         */
        default T setBody( String... body ) {
            return setBody( Ast.blockStmt( body ) );
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
        T add( Statement... statements );

        /**
         * Add Statements to the BODY starting at startStatementIndex (0-based)
         * @param startStatementIndex index of the position to add the statements
         * @param statements the statements to add to the tail of the BODY
         * @return the modified T (_method, _constructor, _staticBlock)
         */
        T add( int startStatementIndex, Statement...statements );

        /**
         * Add Statements to the end of the BODY
         *
         * @param statements the statements as an Array of Strings
         * @return the modified T (_method, _constructor, _staticBlock)
         */
        default T add( String... statements ) {
            BlockStmt bs = Ast.blockStmt( statements );
            //organize orphan comments
            List<Comment> coms = bs.getOrphanComments();
            Comment c = null;
            if( coms.size() > 0 ){
                c = coms.get(0);
            }
            Collections.sort( coms, new Walk.CommentPositionComparator());

            for(int i=0;i< bs.getStatements().size(); i++){
                Statement st = bs.getStatement(i);
                if( c != null && c.getBegin().get().isBefore(st.getBegin().get())){
                    getBody().ast().addOrphanComment( c );
                    coms.remove(0);
                    if( !coms.isEmpty()){
                        c = coms.get(0);
                    } else{
                        c = null;
                    }
                }
                getBody().ast().addStatement(st);
                //add( bs.getStatements().toArray( new Statement[ 0 ] ) );
            }
            coms.forEach(co -> getBody().ast().addOrphanComment(co));
            return (T)this;
        }

        /**
         * Add Statements to the BODY starting at the startStatementIndex (0-based)
         * @param statementStartIndex the statement index (0 is the first statement)
         * @param statements a Block of Strings representing statements (this is first parsed before added
         * @return the modified T
         */
        default T add( int statementStartIndex, String...statements ){
            BlockStmt bs = Ast.blockStmt( statements );
            return add( statementStartIndex, bs.getStatements().toArray( new Statement[ 0 ] ) );
        }

        /** Adds Statements to the end of the labeled Statement with NAME "labelName" */
        default T addAt( String labelName, String...statements ){
            Optional<LabeledStmt> ols =
                    this.getBody().ast().findFirst(LabeledStmt.class, ls-> ls.getLabel().toString().equals(labelName));
            if( !ols.isPresent() ){
                throw new DraftException("cannot find labeled Statement \""+labelName+"\"");
            }
            Statement st = ols.get().getStatement();
            if( st.isBlockStmt() ){
                BlockStmt bs = st.asBlockStmt();
                NodeList<Statement> stmts = Ast.blockStmt( statements ).getStatements();
                stmts.forEach( s-> bs.addStatement(s));
            } else{
                BlockStmt bs = Ast.blockStmt( statements );
                bs.addStatement(0, st );
                st.replace(bs);
            }
            return (T)this;
        }

        default boolean hasLabel( String labelName ){
            return this.getBody().ast().findFirst(LabeledStmt.class, ls-> ls.getLabel().toString().equals(labelName)).isPresent();
        }

        default T addAt( String labelName, Statement stmt){
            List<Statement> stmts = new ArrayList<>();
            stmts.add( stmt );
            return addAt( labelName, stmts );
        }

        default T addAt( String labelName, List<Statement> stmts){
            Optional<LabeledStmt> ols =
                    this.getBody().ast().findFirst(LabeledStmt.class, ls-> ls.getLabel().toString().equals(labelName));
            if( !ols.isPresent() ){
                throw new DraftException("cannot find labeled Statement \""+labelName+"\"");
            }
            Statement st = ols.get().getStatement();
            if( st.isBlockStmt() ){
                BlockStmt bs = st.asBlockStmt();
                stmts.forEach( s-> bs.addStatement(s));
            } else{
                BlockStmt bs = Ast.blockStmt( stmts.toArray(new Statement[0]) );
                bs.addStatement(0, st );
                st.replace(bs);
            }
            return (T)this;
        }

        /**
         * Gets the Labeled statement based on the label
         * @param labelName
         * @return the Labeled Statement or THROWS EXCEPTION if not found
         */
        default LabeledStmt getAt(String labelName){
            Optional<LabeledStmt> ols =
                    this.getBody().ast().findFirst(LabeledStmt.class, ls-> ls.getLabel().toString().equals(labelName));
            if( !ols.isPresent() ){
                throw new DraftException("cannot find labeled Statement \""+labelName+"\"");
            }
            return ols.get();
        }

        /**
         * Replaces the Labeled Statement/Block with sequential code w/o a label and without a block
         * for instance:
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
         * @param labelName the NAME of the label to be flattened
         * @return the modified codeBlock
         * @throws DraftException is the code does not contain a label with the LabelName
         */
        default T flattenLabel(String labelName) {

            Optional<LabeledStmt> ols =
                    this.getBody().ast().findFirst(LabeledStmt.class, ls-> ls.getLabel().toString().equals(labelName));
            while( ols.isPresent()){
                LabeledStmt ls = ols.get();
                if( ls.getStatement().isBlockStmt() ){
                    BlockStmt bs = ls.getStatement().asBlockStmt();
                    NodeList<Statement> stmts = bs.getStatements();
                    if( stmts.size() == 0 ){
                        (ls.getParentNode().get()).replace(ls, new EmptyStmt() );
                    }
                    else if( stmts.size() == 1 ){
                        //System.out.println("Single Statement");
                        ls.getParentNode().get().replace( ls, stmts.get(0));
                    }
                    else {
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
                ols = this.getBody().ast().findFirst(LabeledStmt.class, lbs-> lbs.getLabel().toString().equals(labelName));
            }
            return (T)this;
        }

        /**
         * Searches through the entity (in Preorder order) to find the first
         * instance of a nodeClass
         * @param nodeClass
         * @param <N>
         * @return the first instance found or null
         */
        default <N extends Node> N findFirst(Class<N> nodeClass ){
            Optional<N> of = getBody().ast().findFirst(nodeClass);
            if( of.isPresent() ){
                return of.get();
            }
            return null;
        }

        /**
         * Searches through the entity (in Preorder order) to find the first
         * instance of a nodeClass that matches the nodeMatchFn
         * @param nodeClass
         * @param nodeMatchFn
         * @param <N>
         * @return the first instance found or null
         */
        default <N extends Node> N findFirst(Class<N> nodeClass, Predicate<N>nodeMatchFn ){
            Optional<N> of = getBody().ast().findFirst(nodeClass, nodeMatchFn);
            if( of.isPresent() ){
                return of.get();
            }
            return null;
        }
    }
}
