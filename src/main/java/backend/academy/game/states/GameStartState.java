package backend.academy.game.states;

import backend.academy.game.logic.Dictionary;
import backend.academy.game.logic.GameContext;
import backend.academy.game.visualizers.PlayerInterface;

public class GameStartState implements GameState {

    private final Dictionary dictionary;
    private final GameContext context;

    public GameStartState(Dictionary d, GameContext ctx) {
        dictionary = d;
        context = ctx;
    }

    @Override
    public String viewScreen(PlayerInterface playerInterface) {
        playerInterface.viewStartScreen();
        return playerInterface.getInput();
    }

    @Override
    public GameState changeState(boolean inputProcessed) {
        return new GameCategorySettingState(dictionary, context);
    }

    @Override
    public boolean processInput(String input) {
        return true;
    }
}
