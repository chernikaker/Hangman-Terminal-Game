package backend.academy.game.exceptions;

public class FieldAlreadySetException extends GameException {

    private final static String messagePart = "Can't change context: ";

    public FieldAlreadySetException(String message) {
        super(messagePart + message);
    }

    public FieldAlreadySetException(String message, Throwable cause) {
        super(messagePart + message, cause);
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
        super(messagePart + message, cause, enableSuppression, writableStackTrace);
    }
}
