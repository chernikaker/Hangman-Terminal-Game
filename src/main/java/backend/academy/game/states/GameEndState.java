package backend.academy.game.states;

import backend.academy.game.GameContext;
import backend.academy.game.visualizers.PlayerInterface;

public class GameEndState implements GameState{

    private final GameContext context;

    public GameEndState(GameContext ctx) {
        context = ctx;
    }

    @Override
    public String viewScreen(PlayerInterface playerInterface) {
        playerInterface.viewEndScreen(context);
        return "";
    }

    @Override
    public GameState changeState(boolean inputProcessed) {
        return this;
    }

    @Override
    public boolean processInput(String input) {
        return true;
    }
}
