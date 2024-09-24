package backend.academy.game;

import backend.academy.game.logic.Dictionary;
import backend.academy.game.logic.Word;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DictionaryTest {

    private static final Dictionary dict = new Dictionary();

    @BeforeEach
     void setUpDict()  {
        dict.generateDefaultDictionary();
    }


    @Nested
    class AddWordTests {
        @Test
        public void NewDifficultyAndCategory() {

            Word word = new Word("pizza", 1, "food");
            dict.addWord(word);
            assertTrue(dict.containsWord(word));
        }

        @Test
        public void NewDifficultyPresentCategory() {

            Word word = new Word("dragonfruit", 3, "fruits");
            dict.addWord(word);
            assertTrue(dict.containsWord(word));
        }

        @Test
        public void PresentDifficultyAndCategory() {

            Word word = new Word("lemon", 1, "fruits");
            dict.addWord(word);
            assertTrue(dict.containsWord(word));
        }
    }

    @Nested
    class GetWordTests {

        @Test
        public void CorrectCategoryAndDifficulty() {
            assertDoesNotThrow(()->dict.getWord("fruits",1));
            Word word = dict.getWord("fruits",1);
            assertTrue(dict.containsWord(word));
        }

        @Test
        public void IncorrectCategory() {
            assertThatThrownBy(()->dict.getWord("wrong category",2))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Category wrong category does not exist");
        }

        @Test
        public void IncorrectDifficulty() {
            assertThatThrownBy(()->dict.getWord("fruits",5))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Difficulty 5 does not exist in category fruits");
        }

    }

    @RepeatedTest(10)
    public void GenerateCategoryAndDifficulty() {
        String category = assertDoesNotThrow(dict::generateCategory);
        int difficulty = assertDoesNotThrow(()-> dict.generateDifficulty(category));
        Word word = assertDoesNotThrow(()->dict.getWord(category,difficulty));
        assertTrue(dict.containsWord(word));
    }

}
