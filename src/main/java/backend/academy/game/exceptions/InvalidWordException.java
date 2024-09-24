package backend.academy.game.exceptions;

public class InvalidWordException extends GameException{

    private final static String messagePart = "Error while adding word: ";

    public InvalidWordException(String message) {
        super(messagePart + message);
    }

    public InvalidWordException(String message, Throwable cause) {
        super(messagePart + message, cause);
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
        super(messagePart + message, cause, enableSuppression, writableStackTrace);
    }
}
