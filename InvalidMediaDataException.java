/**
 * Custom exception for handling invalid or malformed media data
 * This exception is thrown when media data from files contains
 * missing fields, invalid formats, or corrupted information
 */
public class InvalidMediaDataException extends Exception {
    
    /**
     * Default constructor with no message
     */
    public InvalidMediaDataException() {
        super();
    }
    
    /**
     * Constructor with custom error message
     * @param message The error message describing what went wrong
     */
    public InvalidMediaDataException(String message) {
        super(message);
    }
    
    /**
     * Constructor with custom message and cause
     * @param message The error message
     * @param cause The underlying cause of the exception
     */
    public InvalidMediaDataException(String message, Throwable cause) {
        super(message, cause);
    }
    
    /**
     * Constructor with just the cause
     * @param cause The underlying cause of the exception
     */
    public InvalidMediaDataException(Throwable cause) {
        super(cause);
    }
}