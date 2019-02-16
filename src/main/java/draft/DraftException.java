package draft;

/**
 * Base runtime exception for the draft tool
 *
 * @author M. Eric DeFazio
 */
public class DraftException extends RuntimeException{

    public DraftException(String message, Throwable throwable){
        super(message, throwable);
    }

    public DraftException(String message){
        super(message);
    }

    public DraftException(Throwable throwable){
        super(throwable);
    }
}
