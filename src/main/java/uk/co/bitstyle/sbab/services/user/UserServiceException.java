package uk.co.bitstyle.sbab.services.user;

/**
 * @author cspiking
 */
public class UserServiceException extends RuntimeException {

    public UserServiceException(String message) {
        super(message);
    }

    public UserServiceException(String message, Throwable t) {
        super(message, t);
    }

}
