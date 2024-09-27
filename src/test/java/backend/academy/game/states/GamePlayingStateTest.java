package backend.academy.game.states;

import backend.academy.game.logic.GameContext;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GamePlayingStateTest {

    GameContext context = new GameContext();
    GameState gameState = new GamePlayingState(context);

    @Nested
    class ChangingStateTests {

        @BeforeEach
        void setUp() {
            context.setAnswer("a","hint");
            context.setMaxMistakes(1);
        }
        @Test
        public void correctInputGameLost() {
            context.processLetter('b');
            gameState = gameState.changeState(true);
            assertSame(gameState.getClass(), GameEndState.class);
        }

        @Test
        public void correctInputGameWon() {
            context.processLetter('a');
            gameState = gameState.changeState(true);
            assertSame(gameState.getClass(), GameEndState.class);
        }

        @Test
        public void correctInput() {
            gameState = gameState.changeState(true);
            assertSame(gameState.getClass(), GamePlayingState.class);
        }

        @Test
        public void incorrectInput() {
            gameState = gameState.changeState(false);
            assertSame(gameState.getClass(), GamePlayingState.class);
        }
    }

    @Nested
    class ProcessingInputTests {

        @BeforeEach
        void setUp() {
            context.setAnswer("a","hint");
            context.setMaxMistakes(1);
        }

        @Test
        public void nullInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput(null));
            assertFalse(processed);
        }

        @Test
        public void emptyInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput(""));
            assertFalse(processed);
        }

        @Test
        public void blankInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput("  "));
            assertFalse(processed);
        }

        @ParameterizedTest
        @CsvSource({"!","1",";","@"})
        public void notLetterInput(String input) {
            boolean processed = assertDoesNotThrow(()->gameState.processInput(input));
            assertFalse(processed);
        }


        @Test
        public void presentLetterInput() {
            context.processLetter('b');
            boolean processed = assertDoesNotThrow(()->gameState.processInput("b"));
            assertFalse(processed);
        }

        @Test
        public void correctGuessLetterInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput("a"));
            assertTrue(processed);
        }

        @Test
        public void incorrectGuessLetterInput() {
            boolean processed = assertDoesNotThrow(()->gameState.processInput("b"));
            assertTrue(processed);
        }
    }


}
