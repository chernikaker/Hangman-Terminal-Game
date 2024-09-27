package backend.academy.game.logic;

import backend.academy.game.exceptions.FieldAlreadySetException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class GameContextTest {

    GameContext gameContext;

    @BeforeEach
    void setUpContext() {
        gameContext = new GameContext();
    }

    @Nested
    class SettersTests {

        @Test
        void setMistakesValidArg() {
            assertDoesNotThrow(()->gameContext.setMaxMistakes(10));
            assertEquals(10, gameContext.maxMistakes());
        }

        @Test
        void setMistakesInvalidArg(){
            assertThatThrownBy(()->gameContext.setMaxMistakes(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Amount of mistakes must be at least 1");
        }

        @Test
        void setMistakesWhenAlreadySet(){
            gameContext.setMaxMistakes(10);
            assertThatThrownBy(()->gameContext.setMaxMistakes(1))
                .isInstanceOf(FieldAlreadySetException.class)
                .hasMessage("Can't change context: max mistakes number has already been set");
        }

        @Test
        void setDifficultyValidArg() {
            assertDoesNotThrow(()->gameContext.setWordDifficulty(1));
            assertEquals(1, gameContext.wordDifficulty());
        }

        @Test
        void setDifficultyInvalidArg(){
            assertThatThrownBy(()->gameContext.setWordDifficulty(0))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Word difficulty level must be at least 1");
        }

        @Test
        void setDifficultyWhenAlreadySet(){
            gameContext.setWordDifficulty(3);
            assertThatThrownBy(()->gameContext.setWordDifficulty(1))
                .isInstanceOf(FieldAlreadySetException.class)
                .hasMessage("Can't change context: word difficulty level has already been set");
        }

        @Test
        void setCategoryValidArg() {
            assertDoesNotThrow(()->gameContext.setWordCategory("fruits"));
            assertEquals("fruits", gameContext.wordCategory());
        }

        @Test
        void setCategoryInvalidArg(){
            assertThatThrownBy(()->gameContext.setWordCategory(""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Word category cannot be empty");
        }

        @Test
        void setCategoryWhenAlreadySet(){
            gameContext.setWordCategory("fruits");
            assertThatThrownBy(()->gameContext.setWordCategory("vegetables"))
                .isInstanceOf(FieldAlreadySetException.class)
                .hasMessage("Can't change context: word category has already been set");
        }

        @Test
        void setAnswerValidArg() {
            assertDoesNotThrow(()->gameContext.setAnswer("banana","Long yellow fruit"));
            assertEquals("banana", gameContext.answer());
            assertEquals("Long yellow fruit", gameContext.hint());
            assertEquals(6,gameContext.getCurrentAnswer().length);
        }

        @Test
        void setAnswerInvalidWord(){
            assertThatThrownBy(()->gameContext.setAnswer("","hint"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Word can not be empty");
        }

        @Test
        void setAnswerInvalidHint(){
            assertThatThrownBy(()->gameContext.setAnswer("word",""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Hint can not be empty");
        }

        @Test
        void setAnswerWhenAlreadySet(){
            gameContext.setAnswer("banana","Long yellow fruit");
            assertThatThrownBy(()->gameContext.setAnswer("apple","A round fruit that keeps the doctor away when eaten daily."))
                .isInstanceOf(FieldAlreadySetException.class)
                .hasMessage("Can't change context: answer has already been set");
        }

    }

    @Nested
    class ProcessLetterTests {

        @ParameterizedTest
        @CsvSource({"b, b_____","B, b_____","a, _a_a_a","A, _a_a_a"})
        public void wordContainsLetter(char letter, String expectedAns) {

            gameContext.setAnswer("banana","Long yellow fruit");
            gameContext.setMaxMistakes(10);
           boolean letterProcessed = gameContext.processLetter(letter);
           assertTrue(letterProcessed);

           assertThat(gameContext.getProcessedLetters().size()).isEqualTo(1);
           assertTrue(gameContext.getProcessedLetters().contains(Character.toLowerCase(letter)));

           assertThat(String.valueOf(gameContext.getCurrentAnswer())).isEqualTo(expectedAns);

           assertThat(gameContext.maxMistakes()-gameContext.mistakes()).isEqualTo(10);
        }

        @Test
        public void wordDoesNotContainLetter() {

            gameContext.setAnswer("banana","Long yellow fruit");
            gameContext.setMaxMistakes(10);
            boolean letterProcessed = gameContext.processLetter('i');
            assertTrue(letterProcessed);

            assertThat(gameContext.getProcessedLetters().size()).isEqualTo(1);
            assertTrue(gameContext.getProcessedLetters().contains('i'));

            assertThat(String.valueOf(gameContext.getCurrentAnswer())).isEqualTo("______");

            assertThat(gameContext.maxMistakes()-gameContext.mistakes()).isEqualTo(9);
        }

        @Test
        public void letterIsAlreadyProcessed() {
            gameContext.setAnswer("banana","Long yellow fruit");
            gameContext.setMaxMistakes(10);
            gameContext.processLetter('a');
            boolean letterProcessed = gameContext.processLetter('a');
            assertFalse(letterProcessed);
        }
    }

    @Nested
    class GettersTests{

        @BeforeEach
        void fillContext(){
            gameContext.setAnswer("answer","Long yellow fruit");
            gameContext.setMaxMistakes(10);
        }

        @Test
        public void changeCurrentAnswer(){
            char[] currentAnswer = gameContext.getCurrentAnswer();
            currentAnswer[0] ='q';
            char[] currentAnswer2 = gameContext.getCurrentAnswer();
            assertThat(currentAnswer2[0]).isEqualTo('_');
        }

    }
}
