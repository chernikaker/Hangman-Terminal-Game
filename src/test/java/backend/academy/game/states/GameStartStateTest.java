package backend.academy.game.states;

import backend.academy.game.logic.Dictionary;
import backend.academy.game.logic.GameContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertSame;

public class GameStartStateTest {

     GameState gameState = new GameStartState(new Dictionary(),new GameContext());

     @ParameterizedTest
     @CsvSource({"true","false"})
    public void stateChanging(boolean processed){
         gameState = gameState.changeState(processed);
         assertSame(gameState.getClass(), GameCategorySettingState.class);
     }

}
