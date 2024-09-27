package backend.academy.game;

import backend.academy.game.logic.Dictionary;
import backend.academy.game.logic.GameContext;
import backend.academy.game.states.GameEndState;
import backend.academy.game.states.GameStartState;
import backend.academy.game.states.GameState;
import backend.academy.game.visualizers.PlayerInterface;
import java.io.InputStream;
import java.io.PrintStream;
import lombok.Getter;

@Getter
public class GameSession {

    private static long nextId = 0L;
    private final long id;
    private final GameContext context = new GameContext();
    private final Dictionary dictionary = new Dictionary();
    private final PlayerInterface playerInterface;
    private GameState state;

    public GameSession(InputStream in, PrintStream out) {
        id = getNextId();
        dictionary.fillDictionaryFromFile(Dictionary.DICTIONARY_PATH);
        playerInterface = new PlayerInterface(out, in);
        playerInterface.createTerminal();
        state = new GameStartState(dictionary, context);
    }

    public void start() {
        while (state.getClass() != GameEndState.class) {
            String userInput = state.viewScreen(playerInterface);
            boolean inputProcessed = state.processInput(userInput);
            state = state.changeState(inputProcessed);
        }
        state.viewScreen(playerInterface);
    }

    private static synchronized long getNextId() {
        return nextId++;
    }
}
