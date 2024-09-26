package backend.academy.game.exceptions;

public class InvalidWordException extends GameException {

    private final static String MESSAGE_PART = "Error while adding word: ";

    public InvalidWordException(String message) {
        super(MESSAGE_PART + message);
    }

    public InvalidWordException(String message, Throwable cause) {
        super(MESSAGE_PART + message, cause);
    }

    public InvalidWordException(Throwable cause) {
        super(cause);
    }

    public InvalidWordException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace
    ) {
        super(MESSAGE_PART + message, cause, enableSuppression, writableStackTrace);
    }
}
