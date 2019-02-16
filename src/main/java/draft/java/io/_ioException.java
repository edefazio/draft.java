
package draft.java.io;

import draft.DraftException;

/**
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
