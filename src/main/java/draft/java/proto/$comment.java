package draft.java.proto;

import com.github.javaparser.ast.Node;
import com.github.javaparser.ast.comments.BlockComment;
import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.comments.LineComment;
import draft.Stencil;
import draft.Template;
import draft.Tokens;
import draft.Translator;
import draft.java.Ast;
import draft.java.Walk;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;

/**
 *
 * @author Eric
 */
public class $comment 
    implements $proto<Comment>, Template<Comment> {
    
    public static $comment any(){
        return new $comment();
    }
    
    public static $comment of( String pattern, Predicate<Comment> constraint ){
        return new $comment(Ast.comment(pattern)).addConstraint(constraint);
    }
    
    public static $comment of( Predicate<Comment> constraint ){
        return any().addConstraint(constraint);
    }
    
    public static $comment javadocComment(){
        return new $comment().omitBlockComments().omitLineComments();
    }

    public static $comment javadocComment(String...comment){
        return new $comment(Ast.javadocComment(comment)).omitBlockComments().omitLineComments();
    }
        
    public static $comment javadocComment(String pattern, Predicate<Comment> constraint){
        return new $comment(Ast.javadocComment(pattern)).omitBlockComments().omitLineComments()
            .addConstraint(constraint);
    }
    
    public static $comment javadocComment(Predicate<Comment> constraint){
        return new $comment().omitBlockComments().omitLineComments()
            .addConstraint(constraint);
    }
        
    public static $comment blockComment(){
        return new $comment().omitJavadocComments().omitLineComments();
    }

    public static $comment blockComment(String...blockComment){
        return new $comment(Ast.blockComment(blockComment)).omitJavadocComments().omitLineComments();
    }
    
    public static $comment blockComment(String pattern, Predicate<Comment> constraint){
        return new $comment(Ast.blockComment(pattern)).omitJavadocComments().omitLineComments()
            .addConstraint(constraint);
    }
    
    public static $comment blockComment(Predicate<Comment> constraint){
        return new $comment().omitJavadocComments().omitLineComments()
            .addConstraint(constraint);
    }
    
    public static $comment lineComment(){
        return new $comment().omitBlockComments().omitJavadocComments();
    }

    public static $comment lineComment(String lineComment){
        return new $comment(Ast.lineComment(lineComment)).omitBlockComments().omitJavadocComments();
    }
    
    public static $comment lineComment(String pattern, Predicate<Comment> constraint){
        return new $comment(Ast.lineComment( pattern))
                .omitBlockComments().omitJavadocComments()
                .addConstraint(constraint);
    }
    
    public static $comment lineComment(Predicate<Comment> constraint){
        return new $comment().omitBlockComments().omitJavadocComments()
            .addConstraint(constraint);
    }
    
    public static $comment of(Comment comment){
        return new $comment(comment);
    }
    
    public static $comment of( String...comment ){
        return new $comment( Ast.comment(comment));
    }
    
    public static final Set<Class<? extends Comment>> ALL_COMMENT_CLASSES = new HashSet<>();
    
    static {
        ALL_COMMENT_CLASSES.add(BlockComment.class);
        ALL_COMMENT_CLASSES.add(LineComment.class);
        ALL_COMMENT_CLASSES.add(JavadocComment.class);
    }    
    
    /** the classes of comment classes that are matched against */
    public Set<Class<? extends Comment>> commentClasses = new HashSet<>();
    
    /** The pattern for the contents of the Comment */
    public Stencil contentsPattern = Stencil.of("$comment$");
    
    public Predicate<Comment> constraint = t -> true;
    
    /** this is the any() constructor private intentionally*/
    private $comment(){
        commentClasses.addAll(ALL_COMMENT_CLASSES);
        contentsPattern = Stencil.of("$comment$");        
    }
    
    /**
     * A $proto based on a particular kind of comment
     * @param astComment 
     */
    public $comment( Comment astComment ){
        this.commentClasses.add(astComment.getClass() );
        this.contentsPattern = Stencil.of(Ast.getContent(astComment) );        
    }
    
    public $comment addConstraint( Predicate<Comment> constraint ){
        this.constraint = this.constraint.and(constraint);
        return this;
    }
    
    public $comment matchBlockComments(){
        this.commentClasses.add(BlockComment.class);
        return this;
    }
    
    public $comment matchLineComments(){
        this.commentClasses.add(LineComment.class);
        return this;
    }
    
    public $comment matchJavadocComments(){
        this.commentClasses.add(JavadocComment.class );
        return this;
    }
    
    public $comment omitBlockComments(){
        this.commentClasses.remove(BlockComment.class);
        return this;
    }
    
    public $comment omitLineComments(){
        this.commentClasses.remove(LineComment.class);
        return this;
    }
    
    public $comment omitJavadocComments(){
        this.commentClasses.remove(JavadocComment.class );
        return this;
    }
    
    public boolean matches( String...comment ){
        return matches( Ast.comment(comment));
    }
    
    public boolean matches( Comment astComment ){
        return select(astComment) != null;
    }
    
    public Select select( String...comment ){
        return select( Ast.comment(comment));
    }
    
    public Select select( Comment astComment ){
        if( !this.constraint.test(astComment) ){
            return null;
        }
        if(! this.commentClasses.contains(astComment.getClass())){
            return null;
        }
        Tokens ts = this.contentsPattern.deconstruct(Ast.getContent(astComment));
        if( ts == null ){
            return null;
        }
        return new Select( astComment, $args.of( ts ));
    }

    @Override
    public Comment firstIn(Node astRootNode) {
        
        //this is extra work, but it "acts" like we want it to
        List<Comment> found = listIn(astRootNode );
        Collections.sort( found, Ast.COMPARE_NODE_BY_LOCATION);
        if( found.isEmpty() ){
            return null;
        }
        return found.get(0);
        
        /** this wont work for orphan comments
        Optional<Comment> oc = 
            astRootNode.findFirst(Comment.class, c-> matches(c) );
        if( oc.isPresent() ){
            return oc.get();
        }
        return null;
        */ 
    }

    @Override
    public Select selectFirstIn(Node n) {
        List<Comment> found = listIn(n );
        Collections.sort( found, Ast.COMPARE_NODE_BY_LOCATION);
        if( found.isEmpty() ){
            return null;
        }
        return select(found.get(0));
        /*
        Optional<Comment> oc = 
            n.findFirst(Comment.class, c-> matches(c) );
        if( oc.isPresent() ){
            return select(oc.get());
        }
        return null;
        */
    }

    public Select selectFirstIn(Node n, Predicate<Select> selectConstraint) {
        List<Select> found = listSelectedIn(n, selectConstraint);
        Collections.sort( found, (s1, s2)-> Ast.COMPARE_NODE_BY_LOCATION.compare(s1.comment, s2.comment));
        if( found.isEmpty() ){
            return null;
        }
        return found.get(0);
        /*
        Optional<Comment> oc = 
            n.findFirst(Comment.class, c->{
                Select sel = select(c);
                return sel != null && selectConstraint.test(sel );
            });
        if( oc.isPresent() ){
            return select(oc.get());
        }
        return null;
        */
    }
        
    @Override
    public List<Comment> listIn(Node astRootNode) {
        List<Comment> found = new ArrayList<>();
        forEachIn(astRootNode, c -> found.add(c));
        return found;
    }

    @Override
    public List<Select> listSelectedIn(Node astRootNode) {
        List<Select> found = new ArrayList<>();
        forSelectedIn( astRootNode, s -> found.add(s));
        return found;
    }
    
    public List<Select> listSelectedIn(Node astRootNode, Predicate<Select> selectConstraint) {
        List<Select> found = new ArrayList<>();
        forSelectedIn( astRootNode, selectConstraint, s -> found.add(s));
        return found;
    }

    @Override
    public <N extends Node> N forEachIn(N astRootNode, Consumer<Comment> _nodeActionFn) {
        Walk.comments(astRootNode, c->{
            Select s = select(c);
            if( s != null ){
                _nodeActionFn.accept(c);
            }
        });
        return astRootNode;
    }

    public <N extends Node> N forSelectedIn(N astRootNode, Consumer<Select> selectActionFn) {
        //Walk will organize by order
        Walk.comments(astRootNode, c->{
            Select s = select(c);
            if( s != null ){
                selectActionFn.accept(s);
            }
        });
        return astRootNode;
    }
    
    public <N extends Node> N forSelectedIn(N astRootNode, Predicate<Select> selectConstraint, Consumer<Select> selectActionFn) {
        Walk.comments(astRootNode, c->{
            Select s = select(c);
            if( s != null && selectConstraint.test(s)){
                selectActionFn.accept(s);
            }
        });
        return astRootNode;
    }
        
    @Override
    public <N extends Node> N removeIn(N astRootNode) {
        return forEachIn( astRootNode, n-> n.remove() );
    }

    public JavadocComment constructJavadocComment(Translator translator, Map<String, Object> keyValues) {
        String contents = this.contentsPattern.construct(translator, keyValues);
        return new JavadocComment( contents );
    }
    
    public BlockComment constructBlockComment(Translator translator, Map<String, Object> keyValues) {
        String contents = this.contentsPattern.construct(translator, keyValues);
        return new BlockComment( contents );
    }
    
    public LineComment constructLineComment( Translator translator,  Map<String,Object> keyValues){
        String contents = this.contentsPattern.construct(translator, keyValues);
        return new LineComment( contents );
    }
    
    @Override
    public Comment construct(Translator translator, Map<String, Object> keyValues) {
        if( this.commentClasses.isEmpty()){
            return constructBlockComment( translator, keyValues );
        }
        if( this.commentClasses.size() == 1 ){
            Class cc = this.commentClasses.toArray(new Class[0])[0];
            if( cc == JavadocComment.class){
                return constructJavadocComment(translator, keyValues);
            }
            if( cc == BlockComment.class){
                return constructBlockComment( translator, keyValues );
            }
            return constructLineComment(translator, keyValues );            
        }
        if( this.commentClasses.contains(JavadocComment.class)){
            return constructJavadocComment(translator, keyValues);
        }
        if( this.commentClasses.contains(BlockComment.class)){
            return constructJavadocComment(translator, keyValues);
        }
        return constructLineComment(translator, keyValues);
    }

    @Override
    public $comment $(String target, String $Name) {
        this.contentsPattern = this.contentsPattern.$(target, $Name);
        return this;
    }

    @Override
    public List<String> list$() {
        return this.contentsPattern.list$();
    }

    @Override
    public List<String> list$Normalized() {
        return this.contentsPattern.list$Normalized();
    }
    
    /**
     * 
     * @param <C> 
     */
    public static class Select <C extends Comment> 
        implements $proto.selected<C>, 
            $proto.selectedAstNode<C> {

        public C comment;
        public $args args;
        
        public Select( C comment, $args args){
            this.comment = comment;
            this.args = args;
        }
        
        @Override
        public $args args() {
            return args;
        }

        @Override
        public C ast() {
            return comment;
        }
        
        public boolean isOrphan(){
            return comment.isOrphan();
        }
        
        public boolean isBlockComment(){
            return comment instanceof BlockComment;
        }
        
        public boolean isLineComment(){
            return comment instanceof LineComment;
        }
        
        public boolean isJavadocComment(){
            return comment instanceof JavadocComment;
        }
        
        public String getContent(){
            return Ast.getContent(comment);
        }
    }
    
}
