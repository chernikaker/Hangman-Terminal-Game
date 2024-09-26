package backend.academy.game.exceptions;

public class FieldAlreadySetException extends GameException {

    private final static String MESSAGE_PART = "Can't change context: ";

    public FieldAlreadySetException(String message) {
        super(MESSAGE_PART + message);
    }

    public FieldAlreadySetException(String message, Throwable cause) {
        super(MESSAGE_PART + message, cause);
    }

    public FieldAlreadySetException(Throwable cause) {
        super(cause);
    }

    public FieldAlreadySetException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace
    ) {
        super(MESSAGE_PART + message, cause, enableSuppression, writableStackTrace);
    }
}
