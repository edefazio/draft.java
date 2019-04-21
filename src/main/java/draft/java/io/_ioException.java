
package draft.java.io;

import draft.DraftException;

/**
 * base exception for doing i/o typically reading in .java source or
 * writing .java source of .class files
 * 
 * @author Eric
 */
public final class _ioException
    extends DraftException {

    public _ioException(String message, Throwable throwable ){
        super( message, throwable );
    }

    public _ioException(String message ){
        super( message );
    }

    public _ioException(Throwable throwable ){
        super( throwable );
    }
}
