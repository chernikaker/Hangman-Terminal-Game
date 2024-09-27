package backend.academy.game.exceptions;

public class WordNotFoundException extends GameException {

    private final static String MESSAGE_PART = "Word can not be found: ";

    public WordNotFoundException(String message) {
        super(MESSAGE_PART + message);
    }
}
