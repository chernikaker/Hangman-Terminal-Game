package backend.academy.game.exceptions;

public class InvalidWordException extends GameException {

    private final static String MESSAGE_PART = "Error while adding word: ";

    public InvalidWordException(String message) {
        super(MESSAGE_PART + message);
    }
}
