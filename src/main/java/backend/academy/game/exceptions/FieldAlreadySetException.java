package backend.academy.game.exceptions;

public class FieldAlreadySetException extends GameException {

    private final static String MESSAGE_PART = "Can't change context: ";

    public FieldAlreadySetException(String message) {
        super(MESSAGE_PART + message);
    }
}
