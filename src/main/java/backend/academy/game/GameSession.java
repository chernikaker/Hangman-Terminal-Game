package backend.academy.game;

import backend.academy.game.states.GameEndState;
import backend.academy.game.states.GameStartState;
import backend.academy.game.states.GameState;
import backend.academy.game.visualizers.PlayerInterface;
import lombok.Getter;
import java.io.InputStream;
import java.io.PrintStream;


@Getter
public class GameSession {

    private static long NEXT_ID = 0L;
    private final long id;
    private final GameContext context = new GameContext();
    private final Dictionary dictionary = new Dictionary();
    private final PlayerInterface playerInterface;
    private GameState state;

    public GameSession(InputStream in, PrintStream out) {
        id = NEXT_ID++;
        dictionary.generateDefaultDictionary();
        playerInterface = new PlayerInterface(out, in);
        state = new GameStartState(dictionary, context);
    }

    public void start(){
        while(state.getClass()!= GameEndState.class){

            String userInput = state.viewScreen(playerInterface);
            boolean inputProcessed = state.processInput(userInput);
            state = state.changeState(inputProcessed);
        }
        state.viewScreen(playerInterface);
    }

}
