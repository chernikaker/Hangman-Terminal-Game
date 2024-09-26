package backend.academy.game.exceptions;

public class WordNotFoundException extends GameException {

    private final static String MESSAGE_PART = "Word can not be found: ";

    public WordNotFoundException(String message) {
        super(MESSAGE_PART + message);
    }

    public WordNotFoundException(String message, Throwable cause) {
        super(MESSAGE_PART + message, cause);
    }

    public WordNotFoundException(Throwable cause) {
        super(cause);
    }

    public WordNotFoundException(
        String message,
        Throwable cause,
        boolean enableSuppression,
        boolean writableStackTrace
    ) {
        super(MESSAGE_PART + message, cause, enableSuppression, writableStackTrace);
    }
}
