package backend.academy.game.logic.dictionary;

import backend.academy.game.exceptions.InvalidWordException;
import backend.academy.game.exceptions.WordNotFoundException;
import backend.academy.game.logic.Dictionary;
import backend.academy.game.logic.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DictionaryGeneratingAndGettingTest {


        private static final Dictionary dict = new Dictionary();

        @BeforeEach
        void setUpDict()  {
            dict.fillDictionaryFromFile("src/test/resources/dict/defaultTestData.txt");
        }

        @Test
        public void CorrectCategoryAndDifficulty() {
            assertDoesNotThrow(()->dict.getWord("fruits",1));
            Word word = dict.getWord("fruits",1);
            assertTrue(dict.containsWord(word));
        }

        @Test
        public void IncorrectCategory() {
            assertThatThrownBy(()->dict.getWord("wrong category",2))
                .isInstanceOf(WordNotFoundException.class)
                .hasMessage("Word can not be found: category wrong category does not exist");
        }

        @Test
        public void IncorrectDifficulty() {
            assertThatThrownBy(()->dict.getWord("fruits",5))
                .isInstanceOf(WordNotFoundException.class)
                .hasMessage("Word can not be found: difficulty 5 does not exist");
        }

        @Test
        public void GenerateDifficultyCorrectCategory() {
            int difficulty = assertDoesNotThrow(()->dict.generateDifficulty("fruits"));
            assertTrue(dict.getDifficulties("fruits").contains(difficulty));
        }

        @Test
        public void GenerateDifficultyWrongCategory() {
            assertThatThrownBy(()->dict.generateDifficulty("wrong category"))
                .isInstanceOf(WordNotFoundException.class)
                .hasMessage("Word can not be found: category wrong category does not exist");
        }

        @Test
        public void GetDifficultiesCorrectCategory() {
            assertDoesNotThrow(()->dict.getDifficulties("fruits"));
        }

        @Test
        public void GetDifficultiesWrongCategory() {
            assertThatThrownBy(()->dict.getDifficulties("wrong category"))
                .isInstanceOf(WordNotFoundException.class)
                .hasMessage("Word can not be found: category wrong category does not exist");
        }

        @RepeatedTest(10)
        public void GenerateCategoryAndDifficulty() {
            String category = assertDoesNotThrow(dict::generateCategory);
            int difficulty = assertDoesNotThrow(()-> dict.generateDifficulty(category));
            Word word = assertDoesNotThrow(()->dict.getWord(category,difficulty));
            assertTrue(dict.containsWord(word));
        }

}
