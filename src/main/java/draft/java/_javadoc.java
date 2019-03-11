package draft.java;

import com.github.javaparser.ast.comments.Comment;
import com.github.javaparser.ast.comments.JavadocComment;
import com.github.javaparser.ast.nodeTypes.NodeWithJavadoc;
import draft.Text;
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
}
