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

public class GameDifficultySettingStateTest {

    Dictionary dict = new Dictionary();
    GameContext context = new GameContext();
    GameState gameState = new GameDifficultySettingState(dict,context);

    @Nested
    class ChangingStateTests {

        @Test
        public void correctCategoryInput() {
            gameState = gameState.changeState(true);
            assertSame(gameState.getClass(), GamePlayingStateTest.class);
        }

        @Test
        public void incorrectCategoryInput() {
            gameState = gameState.changeState(false);
            assertSame(gameState.getClass(), GameDifficultySettingState.class);
        }
    }

    @Nested
    class ProcessingInputTests {

        @BeforeEach
        void setUp() {
            dict.addWord(new Word("banana",1,"fruits"));
            context.setWordCategory("fruits");
        }

        @Test
        public void notParsingInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput("incorrect"));
            assertFalse(processed);
        }

        @Test
        public void emptyInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput(""));
            assertTrue(processed);
            assertNotEquals(0,context.wordDifficulty());
        }

        @Test
        public void blankInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput("  "));
            assertTrue(processed);
            assertNotEquals(0,context.wordDifficulty());
        }

        @Test
        public void presentDifficultyInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput("1"));
            assertTrue(processed);
            assertEquals(1,context.wordDifficulty());
            assertEquals(context.answer(),"banana");
            assertEquals(context.maxMistakes(),11);
        }

        @Test
        public void notPresentDifficultyInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput("2"));
            assertFalse(processed);
            assertEquals(0,context.wordDifficulty());
        }
    }


}
