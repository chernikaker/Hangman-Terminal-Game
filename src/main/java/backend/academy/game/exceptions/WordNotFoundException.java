package backend.academy.game.exceptions;

public class WordNotFoundException extends GameException{

    private final static String messagePart = "Word can not be found: ";

    public WordNotFoundException(String message) {
        super(messagePart + message);
    }

    public WordNotFoundException(String message, Throwable cause) {
        super(messagePart + message, cause);
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
        super(messagePart + message, cause, enableSuppression, writableStackTrace);
    }
}
