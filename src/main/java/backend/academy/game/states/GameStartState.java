package backend.academy.game.states;

import backend.academy.game.Dictionary;
import backend.academy.game.GameContext;
import backend.academy.game.visualizers.PlayerInterface;

public class GameStartState implements GameState {

    private final Dictionary dictionary;
    private final GameContext context;
    private final PlayerInterface playerInterface;


    GameStartState(Dictionary d, GameContext ctx, PlayerInterface pi) {
        dictionary = d;
        context = ctx;
        playerInterface = pi;
    }

    @Override
    public void viewScreen() {
        playerInterface.viewStartScreen();
    }

    @Override
    public GameState changeInterface() {
        // CHANGE
        return new GameStartState(dictionary, context, playerInterface);
    }

    @Override
    public boolean validateInput(String input) {
        return true;
    }

    @Override
    public boolean changeContext() {
        return true;
    }
}
