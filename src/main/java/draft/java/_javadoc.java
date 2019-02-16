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
        //if( jdnode.getJavadocComment().isPresent() ) {
        //    ((JavadocComment)jdnode.getJavadocComment().get())
        //            .setContent( getContent( Text.combine( content ) ) );
       // }
        //else {
        jdnode.setJavadocComment( Text.combine( content ) );
        //}
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
            //return getContent( ((JavadocComment)jdnode.getJavadocComment().get()) );
        }
        return null;
    }

    public String toString(){
        if( jdnode.getJavadocComment().isPresent() ) {
            return jdnode.getJavadocComment().get().toString();
        }
        return null;
    }

    /*
    public static String getContent( JavadocComment astBc ) {
        String content = astBc.toString().trim();
        return getContent( content );
    }

    public static String getContent( Comment anyComment ){
        if( anyComment instanceof JavadocComment ){
            return getContent( (JavadocComment)anyComment );
        }
        return anyComment.getContent();
    }
    */

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

    /*
    public static String getContent( String content ) {
        if( content.startsWith( "/**" + System.lineSeparator() ) ) {
            content = content.replace( "/**" + System.lineSeparator(), "" );
        }
        else {
            content = content.substring( 2 );
            /* inline block
        }
        if( content.endsWith( "*"+"/" + System.lineSeparator() ) ) {
            content = content.replace( "*"+"/" + System.lineSeparator(), "" );
        }
        else {
            content = content.substring( 0, content.length() - 2 );
        }
        //now go line where line, trim, the "*" and tabs
        String lines[] = content.split( "\\r?\\n" );
        StringBuilder sb = new StringBuilder();
        boolean started = false;
        for( int i = 0; i < lines.length; i++ ) {
            lines[ i ] = lines[ i ].trim();
            if( lines[ i ].startsWith( "* " ) ) {
                lines[ i ] = lines[ i ].substring( 2 );
            }
            else if( lines[ i ].startsWith( "*" ) ) {
                lines[ i ] = lines[ i ].substring( 1 );
            }
            if( lines[ i ].length() == 0 && !started ) {

            }
            else {
                started = true;
                sb.append( lines[ i ] );
                sb.append( System.lineSeparator() );
            }
        }
        return sb.toString().trim();
    }
    */

    /**
     * Model entity that optionally has a Javadoc Comment attributed to it
     *
     * @author Eric
     * @param <T>
     */
    public interface _hasJavadoc<T extends _hasJavadoc>
            extends _model {

        /** gets the JAVADOC for this element (or returns null) */
        _javadoc getJavadoc();

        T javadoc( String... content );

        boolean hasJavadoc();

        T removeJavadoc();
    }
}
