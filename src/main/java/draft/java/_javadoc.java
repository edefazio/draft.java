package draft.java;

import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;
import draft.Text;
import draft.java._inspect._diff;
import draft.java._java._path;
import java.util.Objects;

/**
 * Model of a Javadoc Comment
 * @author Eric
 */
public final class _javadoc
        implements _model {

    public static _javadoc of( NodeWithJavadoc jdnode ) {
        return new _javadoc( jdnode );
    }

    private final NodeWithJavadoc jdnode;

    public _javadoc( NodeWithJavadoc jdnode ) {
        this.jdnode = jdnode;
    }

    public NodeWithJavadoc astHolder() {
        return this.jdnode;
    }

    public _javadoc setContent( String... content ) {
        jdnode.setJavadocComment( Text.combine( content ) );
        return this;
    }

    public boolean isEmpty(){
        return !jdnode.hasJavaDocComment();
    }

    public JavadocComment ast() {
        if( this.jdnode.getJavadocComment().isPresent() ) {
            return (JavadocComment)this.jdnode.getJavadocComment().get();
        }
        return null;
    }

    public String getContent() {
        if( jdnode.getJavadocComment().isPresent() ) {
            return Ast.getContent( (Comment)jdnode.getJavadocComment().get() );
        }
        return null;
    }

    @Override
    public String toString(){
        if( jdnode.getJavadocComment().isPresent() ) {
            return jdnode.getJavadocComment().get().toString();
        }
        return null;
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
        final _javadoc other = (_javadoc)obj;
        if( this.jdnode == other.jdnode ) {
            return true; //two _javadoc instances pointing to the same NodeWithJavadoc
        }
        if( !Objects.equals( this.getContent(), other.getContent() ) ) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 41 * hash + Objects.hashCode( this.getContent() );
        return hash;
    }

    public _diff diff( _javadoc right ){
        return INSPECT_JAVADOC.diff(this, right);
    }
    
    public static _diff diff( _javadoc left, _javadoc right){
        return INSPECT_JAVADOC.diff(left, right);
    }
    
    /**
     * Model entity that optionally has a Javadoc Comment attributed to it
     *
     * @author Eric
     * @param <T>
     */
    public interface _hasJavadoc<T extends _hasJavadoc>
            extends _model {

        /** @return the JAVADOC for this element (or returns null) */
        _javadoc getJavadoc();

        /** 
         * Add a javadoc to the entity and return the modified entity
         * @param content the javadoc content
         * @return 
         */
        T javadoc( String... content );

        /**
         * set the javadoc comment with this JavadocComment
         * @param astJavadocComment the
         * @return the modified T
         */
        T javadoc( JavadocComment astJavadocComment );
        
        /**
         * Does this component have a Javadoc entry?
         * @return true if there is a javadoc, false otherwise
         */
        boolean hasJavadoc();

        /**
         * Remove the javadoc entry from the entity and return the modified entity
         * @return the modified entity
         */
        T removeJavadoc();
    }
    
    public static final _javadocInspect INSPECT_JAVADOC = 
        new _javadocInspect();
    
    public static class _javadocInspect implements _inspect<_javadoc>, 
            _differ<_javadoc, _node>{
       
        @Override
        public boolean equivalent(_javadoc left, _javadoc right) {
            return Objects.equals(left, right);
        }

        @Override
        public _inspect._diff diff( _java._inspector _ins,_path path, _inspect._diff dt,  _javadoc left, _javadoc right) {
            if( !equivalent( left, right)){
                dt.add(path.in( _java.Component.JAVADOC ), left, right);
            }
            return dt;
        }        

        @Override
        public <R extends _node> _dif diff(_path path, build dt, R leftRoot, R rightRoot, _javadoc left, _javadoc right) {
            if( !equivalent( left, right)){
                dt.node( new _changeJavadoc( path.in( _java.Component.JAVADOC ), (_hasJavadoc)leftRoot, (_hasJavadoc)rightRoot) );
            }
            return (_dif)dt;
        }
    }
    
    /**
     * Both signifies a delta and provides a means to 
     * commit (via right()) 
     * or rollback( via left())
     */
    public static class _changeJavadoc 
            implements _differ._delta, _differ._change<JavadocComment>{
        public _path path;
        public _hasJavadoc left;
        public _hasJavadoc right;
        public JavadocComment leftJavadoc;
        public JavadocComment rightJavadoc;
        
        public _changeJavadoc(_path _p, _hasJavadoc left, _hasJavadoc right ){
            this.path = _p;
            this.left = left;
            if( left.hasJavadoc() ){
                this.leftJavadoc = left.getJavadoc().ast().clone();
            }
            this.right = right;
            if( right.hasJavadoc()){
                this.rightJavadoc = right.getJavadoc().ast().clone();            
            }
        }
        
        public void keepLeft(){
            left.javadoc(leftJavadoc);
            right.javadoc(leftJavadoc);
        }
        
        public void keepRight(){
            left.javadoc(rightJavadoc);
            right.javadoc(rightJavadoc);
        }
        
        public JavadocComment left(){
            return leftJavadoc;
        }
        
        public JavadocComment right(){
            return rightJavadoc;
        }
        
        @Override
        public _model leftRoot() {
            return left;
        }

        @Override
        public _model rightRoot() {
            return right;
        }

        @Override
        public _path path() {
            return path;
        }
        
        @Override
        public String toString(){
            return "   ~ "+path;
        }
    }
}
