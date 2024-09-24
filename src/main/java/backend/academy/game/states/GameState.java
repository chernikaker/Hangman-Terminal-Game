package backend.academy.game.states;

import backend.academy.game.visualizers.PlayerInterface;

public interface GameState {

    String viewScreen(PlayerInterface playerInterface);

    GameState changeState(boolean inputProcessed);

    boolean processInput(String input);


}
