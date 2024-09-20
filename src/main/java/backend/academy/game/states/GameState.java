package backend.academy.game.states;

public interface GameState {

    void viewScreen();

    GameState changeInterface();

    boolean validateInput(String input);

    boolean changeContext();
}
