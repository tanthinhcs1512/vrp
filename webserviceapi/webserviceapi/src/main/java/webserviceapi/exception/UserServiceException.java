package webserviceapi.exception;

public class UserServiceException extends RuntimeException {
    private static final long serialVersionUID = 12344638834L;

    public UserServiceException(String message) {
        super(message);
    }
}
