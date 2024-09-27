package backend.academy.game.states;

import backend.academy.game.logic.Dictionary;
import backend.academy.game.logic.GameContext;
import backend.academy.game.logic.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameCategorySettingStateTest {

    Dictionary dict = new Dictionary();
    GameContext context = new GameContext();
    GameState gameState = new GameCategorySettingState(dict,context);

    @Nested
    class ChangingStateTests {

        @Test
        public void correctCategoryInput() {
            gameState = gameState.changeState(true);
            assertSame(gameState.getClass(), GameDifficultySettingState.class);
        }

        @Test
        public void incorrectCategoryInput() {
            gameState = gameState.changeState(false);
            assertSame(gameState.getClass(), GameCategorySettingState.class);
        }
    }

    @Nested
    class ProcessingInputTests {

        @BeforeEach
        void setUp() {
            dict.addWord(new Word("banana",1,"fruits","hint"));
        }

        @Test
        public void notValidatingInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput(null));
            assertFalse(processed);
        }

        @Test
        public void emptyInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput(""));
            assertTrue(processed);
            assertNotEquals("",context.wordCategory());
        }

        @Test
        public void blankInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput("  "));
            assertTrue(processed);
            assertNotEquals("",context.wordCategory());
        }

        @Test
        public void presentCategoryInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput("fruits"));
            assertTrue(processed);
            assertEquals("fruits",context.wordCategory());
        }

        @Test
        public void notPresentCategoryInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput("toys"));
            assertFalse(processed);
            assertEquals("",context.wordCategory());
        }
    }

}
