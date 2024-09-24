package backend.academy.game.validators;

@FunctionalInterface
public interface InputValidator {
    boolean validate(String input);
}
