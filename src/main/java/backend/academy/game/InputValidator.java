package backend.academy.game;

@FunctionalInterface
public interface InputValidator {
    boolean validate(String input);
}
