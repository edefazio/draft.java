package draft.java;

import com.github.javaparser.ast.body.InitializerDeclaration;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.stmt.BlockStmt;
import com.github.javaparser.ast.stmt.Statement;
import draft.Text;
import draft.java._model.*;
import draft.java._java._path;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Model of a Java static initializer block
 * static { ... }
 * 
 * There can be one or more static initializer block in each {@link _class} or 
 * {@link _enum}
 * 
 * @author Eric
 */
public final class _staticBlock
        implements _body._hasBody<_staticBlock>, _javadoc._hasJavadoc<_staticBlock>,
        _node<InitializerDeclaration> {

    public static _staticBlock of( String... body ) {
        InitializerDeclaration id = Ast.staticBlock( body );
        return of( id );
    }

    public static _staticBlock of( InitializerDeclaration id ) {
        return new _staticBlock( id );
    }

    public _staticBlock( InitializerDeclaration id ) {
        this.astStaticInit = id;
    }

    @Override
    public InitializerDeclaration ast() {
        return this.astStaticInit;
    }

    private final InitializerDeclaration astStaticInit;

    @Override
    public _body getBody() {
        return _body.of( astStaticInit );
    }

    public boolean is( String... code ) {
        try {
            return of(code).equals(this);
        }catch(Exception e){
            return false;
        }
    }


    @Override
    public _staticBlock setBody( BlockStmt body ) {
        this.astStaticInit.setBody( body );
        return this;
    }

    @Override
    public _staticBlock clearBody() {

        this.astStaticInit.getBody().setStatements( Ast.blockStmt( "{}" ).getStatements() );
        return this;
    }

    @Override
    public _staticBlock add( Statement... statements ) {
        for( int i = 0; i < statements.length; i++ ) {
            this.astStaticInit.getBody().addStatement( statements[ i ] );
        }
        return this;
    }

    @Override
    public _staticBlock add( int startStatementIndex, Statement...statements ){

        for( int i=0;i<statements.length;i++) {
            this.astStaticInit.getBody().addStatement( i+ startStatementIndex, statements[i] );
        }
        return this;
    }

    @Override
    public String toString() {
        return this.astStaticInit.toString();
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
        final _staticBlock other = (_staticBlock)obj;
        if( this.astStaticInit == other.astStaticInit ) {
            return true; //two _staticBlocks pointing to the same InitializerDeclaration
        }
        if( this.hasJavadoc() != other.hasJavadoc() ) {
            return false;
        }
        if( this.hasJavadoc() && !this.getJavadoc().equals( other.getJavadoc() ) ) {
            return false;
        }
        if( !Objects.equals( _body.of( this.astStaticInit ), _body.of( other.astStaticInit ) ) ) {
            return false;
        }
        return true;
    }

    @Override
    public Map<_java.Component, Object> componentsMap( ) {
        Map<_java.Component, Object> parts = new HashMap<>();
        parts.put( _java.Component.BODY, getBody() );
        return parts;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 83 * hash + Objects.hashCode( _body.of( this.astStaticInit ) );
        return hash;
    }

    @Override
    public _javadoc getJavadoc() {
        return _javadoc.of( this.astStaticInit );
    }

    @Override
    public _staticBlock javadoc( JavadocComment astJavadocComment ){
        this.astStaticInit.setJavadocComment( astJavadocComment );
        return this;
    }
        
    @Override
    public _staticBlock javadoc( String... content ) {
        if( this.astStaticInit.getJavadocComment().isPresent() ) {
            this.astStaticInit.getJavadocComment().get().setContent( Text.combine( content ) );
            return this;
        }
        this.astStaticInit.setJavadocComment( new JavadocComment( Text.combine( content ) ) );
        return this;
    }

    @Override
    public boolean hasJavadoc() {
        return this.astStaticInit.getJavadocComment().isPresent();
    }

    @Override
    public _staticBlock removeJavadoc() {
        this.astStaticInit.removeJavaDocComment();
        return this;
    }

    /**
     * {@_type}s that may contain one or more static initializer blocks
     * @author Eric
     * @param <T>
     */
    public interface _hasStaticBlock<T extends _hasStaticBlock & _type>
            extends _model {

        /** returns the static Blocks on the _type (ordered by when they are declared) */
        List<_staticBlock> listStaticBlocks();

        /** returns the static Blocks on the _type matching the matchFn */
        default List<_staticBlock> listStaticBlocks( Predicate<_staticBlock>_staticBlockMatchFn){
            return listStaticBlocks().stream().filter(_staticBlockMatchFn).collect(Collectors.toList());
        }

        /** @return the index<SUP>th</SUP> static block declared in the _type */
        _staticBlock getStaticBlock( int index );

        /** adds a Static block based on th body of the lambda */
        default T staticBlock( Expr.Command command ){
            StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
            return staticBlock( Stmt.block(ste));
        }

        /**
         * Apply an "action" function to all static blocks
         * @param _staticBlockAction action to take on static blocks
         * @return the modified T
         */
        default T forStaticBlocks( Consumer<_staticBlock> _staticBlockAction ){
            listStaticBlocks().forEach(_staticBlockAction );
            return (T)this;
        }

        /**
         * Apply an action function to all static blocks that match the function
         * @param _staticBlockMatchFn matches Static blocks to act on
         * @param _staticBlockAction the action to take on matching _staticBlocks
         * @return the modified T
         */
        default T forStaticBlocks(Predicate<_staticBlock> _staticBlockMatchFn, Consumer<_staticBlock> _staticBlockAction ){
            listStaticBlocks(_staticBlockMatchFn).forEach(_staticBlockAction );
            return (T)this;
        }

        /**
         * Build a static Block based on the Lambda Command Body
         * @param command the lambda command body (to get the source of the Static Block)
         * @param <A> the command type
         * @return the modified T
         */
        default <A extends Object> T staticBlock( Consumer<A> command ){
            StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
            return staticBlock( Stmt.block(ste));
        }

        /**
         * Build a static Block based on the Lambda Body
         * @param command the lambda command body (to get the source of the Static Block)
         * @param <A> the command type
         * @return the modified T
         */
        default <A extends Object, B extends Object> T staticBlock( BiConsumer<A, B> command ){
            StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
            return staticBlock( Stmt.block(ste));
        }

        /**
         * Build a static Block based on the Lambda Body
         * @param <A> the command type
         * @param <B>
         * @param <C>
         * @param command the lambda command body (to get the source of the Static Block)         
         * @return the modified T
         */
        default <A extends Object, B extends Object, C extends Object> T staticBlock( Expr.TriConsumer<A, B, C> command ){
            StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
            return staticBlock( Stmt.block(ste));
        }

        /**
         * Build a static Block based on the Lambda Body
         * @param <A> the command type
         * @param <B>
         * @param <C>
         * @param <D>
         * @param command the lambda command body (to get the source of the Static Block)         
         * @return the modified T
         */
        default <A extends Object, B extends Object, C extends Object, D extends Object> T staticBlock( Expr.QuadConsumer<A, B, C, D> command ){
            StackTraceElement ste = Thread.currentThread().getStackTrace()[2];
            return staticBlock( Stmt.block(ste));
        }

        default boolean hasStaticBlock(){
            return ((_type)this).astType().getMembers().stream().anyMatch( m -> m instanceof InitializerDeclaration );
        }

        default T staticBlock(BlockStmt block){
            BlockStmt bs = ((_type)this).astType().addStaticInitializer();
            bs.setStatements( block.getStatements());
            return (T)this;
        }

        default T staticBlock(String... content){
            //reserve the static initializer on the _type
            BlockStmt bs = ((_type)this).astType().addStaticInitializer();

            bs.setStatements( Ast.blockStmt( content ).getStatements());
            return (T)this;
        }

        default T staticBlock( _staticBlock sb){
             BlockStmt bs = ((_type)this).astType().addStaticInitializer();
             bs.setStatements(sb.astStaticInit.getBody().getStatements());
             return (T)this;
        }
        
        /** 
         * remove the _staticBlock from the _type and return the _type 
         * @param _sb the staticBlock
         * @return the modified T
         */
        T removeStaticBlock( _staticBlock _sb );

        /** 
         * remove the static block from the _type and return the _type
         * @param astInitializerDeclaration
         * @return the modified T
         */
        T removeStaticBlock( InitializerDeclaration astInitializerDeclaration );
        
    }
    
    public static _staticBlocksInspect INSPECT_STATIC_BLOCKS 
            = new _staticBlocksInspect();
    
    public static class _staticBlocksInspect 
            implements _inspect<List<_staticBlock>>, _differ<List<_staticBlock>, _node> {

        @Override
        public boolean equivalent(List<_staticBlock> left, List<_staticBlock> right) {
            Set<_staticBlock> ls = new HashSet<>();
            Set<_staticBlock> rs = new HashSet<>();
            ls.addAll( left);
            rs.addAll(right);
            
            return Objects.equals(ls, rs);
        }

        @Override
        public _inspect._diff diff( _java._inspector _ins, _path path, _inspect._diff dt, List<_staticBlock> left, List<_staticBlock> right) {
            Set<_staticBlock> ls = new HashSet<>();
            Set<_staticBlock> rs = new HashSet<>();
            Set<_staticBlock> both = new HashSet<>();
            ls.addAll( left);
            rs.addAll(right);
            
            both.addAll(left);
            both.retainAll(right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            
            ls.forEach(s -> dt.add(path.in(_java.Component.STATIC_BLOCK), s, null));
            rs.forEach(s -> dt.add(path.in(_java.Component.STATIC_BLOCK), null, s));
            return dt;
        }

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, List<_staticBlock> left, List<_staticBlock> right) {
            Set<_staticBlock> ls = new HashSet<>();
            Set<_staticBlock> rs = new HashSet<>();
            Set<_staticBlock> both = new HashSet<>();
            ls.addAll( left);
            rs.addAll(right);
            
            both.addAll(left);
            both.retainAll(right);
            
            ls.removeAll(both);
            rs.removeAll(both);
            
            ls.forEach(s -> dt.node( new remove_staticBlock(
                    path.in(_java.Component.STATIC_BLOCK), 
                    (_hasStaticBlock)leftRoot, (_hasStaticBlock)rightRoot, s) ) );
            rs.forEach(s -> dt.node( new add_staticBlock(
                    path.in(_java.Component.STATIC_BLOCK), 
                    (_hasStaticBlock)leftRoot, (_hasStaticBlock)rightRoot, s) ) );
            return (_dif)dt;
        }
        
        public static class add_staticBlock implements _delta<_hasStaticBlock>, _add<_staticBlock> {

            public _path path;
            public _hasStaticBlock leftRoot;
            public _hasStaticBlock rightRoot;
            public _staticBlock toAdd;
            //TODO? leftMemberIndex, rightMemberIndex so I add the static Block in the right place???
            
            public add_staticBlock(_path path, _hasStaticBlock leftRoot, _hasStaticBlock rightRoot, _staticBlock toAdd){
                this.path = path;
                this.leftRoot = leftRoot;
                this.rightRoot = rightRoot;
                this.toAdd = _staticBlock.of(toAdd.astStaticInit.clone());
            }
            
            @Override
            public _hasStaticBlock leftRoot() {
                return leftRoot;
            }

            @Override
            public _hasStaticBlock rightRoot() {
                return rightRoot;
            }

            @Override
            public void keepLeft() {
                this.leftRoot.removeStaticBlock(toAdd);
                this.rightRoot.removeStaticBlock(toAdd);                            
            }

            @Override
            public void keepRight() {                
                this.leftRoot.removeStaticBlock(toAdd);
                this.leftRoot.staticBlock(toAdd);
                
                this.rightRoot.removeStaticBlock(toAdd);
                this.rightRoot.staticBlock(toAdd);    
            }

            @Override
            public _path path() {
                return this.path;
            }

            @Override
            public _staticBlock added() {
                return this.toAdd;
            }
            
            @Override
            public String toString(){
                return "   + "+path;
            }
        }
        
        public static class remove_staticBlock implements _delta<_hasStaticBlock>, _remove<_staticBlock> {

            public _path path;
            public _hasStaticBlock leftRoot;
            public _hasStaticBlock rightRoot;
            public _staticBlock toRemove;
            
            public remove_staticBlock(_path path, _hasStaticBlock leftRoot, _hasStaticBlock rightRoot, _staticBlock toRemove){
                this.path = path;
                this.leftRoot = leftRoot;
                this.rightRoot = rightRoot;
                this.toRemove = _staticBlock.of(toRemove.astStaticInit.clone());
            }
            
            @Override
            public _hasStaticBlock leftRoot() {
                return leftRoot;
            }

            @Override
            public _hasStaticBlock rightRoot() {
                return rightRoot;
            }

            @Override
            public void keepLeft() {
                this.leftRoot.removeStaticBlock(toRemove);
                this.leftRoot.staticBlock(toRemove);
                
                this.rightRoot.removeStaticBlock(toRemove);
                this.rightRoot.staticBlock(toRemove);
            }

            @Override
            public void keepRight() {
                
                this.leftRoot.removeStaticBlock(toRemove);
                this.rightRoot.removeStaticBlock(toRemove);
                
            }

            @Override
            public _path path() {
                return this.path;
            }

            @Override
            public _staticBlock removed() {
                return this.toRemove;
            }
            
            @Override
            public String toString(){
                return "   - "+path;
            }
        }
    }
    
    public static _staticBlockInspect INSPECT_STATIC_BLOCK 
            = new _staticBlockInspect();
            
    public static class _staticBlockInspect 
            implements _inspect<_staticBlock> {

        @Override
        public boolean equivalent(_staticBlock left, _staticBlock right) {
            return Objects.equals(left, right);
        }

        @Override
        public _inspect._diff diff( _java._inspector _ins, _path path, _inspect._diff dt, _staticBlock left, _staticBlock right) {
            if( left == null){
                if( right == null){
                    return dt;
                }
                return dt.add( path.in( _java.Component.STATIC_BLOCK), null, right);
            }
            if( right == null){
                return dt.add( path.in(_java.Component.STATIC_BLOCK), left, null);
            }
            return _ins.INSPECT_BODY.diff(_ins, path, dt, left.getBody(), right.getBody());            
        }
    }
}
