package backend.academy.game;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameContextTest {

    @Nested
    class ConstructorTests {

        @Test
        void validArgs() {
            String word = "hello";
            int mistakes = 10;
            assertDoesNotThrow(()->new GameContext(mistakes, word));
        }

        @Test
        void invalidWord(){
            String word = "";
            int mistakes = 10;
            assertThatThrownBy(()->new GameContext(mistakes,word))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Word cannot be empty");
        }

        @Test
        void invalidMistakesAmount(){
            String word = "hello";
            int mistakes = 0;
            assertThatThrownBy(()->new GameContext(mistakes,word))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount of mistakes must be at least 1");
        }

    }

    @Nested
    class ProcessLetterTests {

        @ParameterizedTest
        @CsvSource({"b, b_____","B, b_____","a, _a_a_a","A, _a_a_a"})
        public void wordContainsLetter(char letter, String expectedAns) {

           GameContext gameContext = new GameContext(10,"banana");
           boolean letterProcessed = gameContext.processLetter(letter);
           assertTrue(letterProcessed);

           assertThat(gameContext.getProcessedLetters().size()).isEqualTo(1);
           assertTrue(gameContext.getProcessedLetters().contains(Character.toLowerCase(letter)));

           assertThat(String.valueOf(gameContext.getCurrentAnswer())).isEqualTo(expectedAns);

           assertThat(gameContext.getRemainingAttempts()).isEqualTo(10);
        }

        @Test
        public void wordDoesNotContainLetter() {
            GameContext gameContext = new GameContext(10,"banana");
            boolean letterProcessed = gameContext.processLetter('i');
            assertTrue(letterProcessed);

            assertThat(gameContext.getProcessedLetters().size()).isEqualTo(1);
            assertTrue(gameContext.getProcessedLetters().contains('i'));

            assertThat(String.valueOf(gameContext.getCurrentAnswer())).isEqualTo("______");

            assertThat(gameContext.getRemainingAttempts()).isEqualTo(9);
        }

        @Test
        public void letterIsAlreadyProcessed() {
            GameContext gameContext = new GameContext(10,"banana");
            gameContext.processLetter('a');
            boolean letterProcessed = gameContext.processLetter('a');
            assertFalse(letterProcessed);
        }
    }


}
