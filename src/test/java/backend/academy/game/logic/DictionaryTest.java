package backend.academy.game.logic;

import backend.academy.game.exceptions.InvalidWordException;
import backend.academy.game.exceptions.WordNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DictionaryTest {

    @Nested
    class FillDictionaryFromFileTest{

        private static final Dictionary dict = new Dictionary();

        @Test
        public void allCorrectData(){
            assertDoesNotThrow(()->dict.fillDictionaryFromFile("src/test/resources/dict/defaultTestData.txt"));
            assertEquals(dict.getCategories().size(),3);
            assertDoesNotThrow(()->dict.getDifficulties("fruits"));
            assertDoesNotThrow(()->dict.getDifficulties("animals"));
            assertDoesNotThrow(()->dict.getDifficulties("professions"));
            assertEquals(dict.getDifficulties("fruits").size(),3);
            assertEquals(dict.getDifficulties("animals").size(),3);
            assertEquals(dict.getDifficulties("professions").size(),3);
        }

        @Test
        public void wrongPath(){
            assertThatThrownBy(()->dict.fillDictionaryFromFile("wrongFile.txt"))
                .isInstanceOf(RuntimeException.class)
                .hasMessage("Error while working with file: wrongFile.txt");

        }

        @Test
        public void wrongNumberOfParamsInLine(){
            assertDoesNotThrow(()
                ->dict.fillDictionaryFromFile("src/test/resources/dict/wrongWordsFormatData.txt"));
            assertEquals(dict.getCategories().size(),0);
        }

        @Test
        public void invalidDifficulty(){
            assertDoesNotThrow(()
                ->dict.fillDictionaryFromFile("src/test/resources/dict/cantParseDifficultyData.txt"));
            assertEquals(dict.getCategories().size(),0);
        }

        @Test
        public void addWordException(){
            assertDoesNotThrow(()
                ->dict.fillDictionaryFromFile("src/test/resources/dict/addWordExceptionData.txt"));
            assertEquals(dict.getCategories().size(),0);
        }
    }


    @Nested
    class AddWordTests {

        private static final Dictionary dict = new Dictionary();

        @Test
        public void NewDifficultyAndCategory() {

            Word word = new Word("pizza", 1, "food");
            dict.addWord(word);
            assertTrue(dict.containsWord(word));
        }

        @Test
        public void NewDifficultyPresentCategory() {

            Word word1 = new Word("banana", 1, "fruits");
            dict.addWord(word1);
            Word word = new Word("dragonfruit", 5, "fruits");
            dict.addWord(word);
            assertTrue(dict.containsWord(word));
        }

        @Test
        public void PresentDifficultyAndCategory() {

            Word word1 = new Word("banana", 1, "fruits");
            dict.addWord(word1);
            Word word = new Word("lemon", 1, "fruits");
            dict.addWord(word);
            assertTrue(dict.containsWord(word));
        }

        private static Stream<Word> provideDataWrongWord() {
            return Stream.of(
                new Word("",1,"fruits"),
                new Word(null,1,"fruits"),
                new Word("    ",1,"fruits")
            );
        }

        private static Stream<Word> provideDataWrongCategory() {
            return Stream.of(
                new Word("banana",1,""),
                new Word("banana",1,null),
                new Word("banana",1,"   ")
            );
        }

        @Test
        public void InvalidDifficulty() {

            Word word = new Word("lemon", 0, "fruits");
            assertThatThrownBy(()->dict.addWord(word))
                .isInstanceOf(InvalidWordException.class)
                .hasMessage("Error while adding word: word difficulty must be at least 1");
        }

        @ParameterizedTest
        @MethodSource("provideDataWrongWord")
        public void InvalidWord(Word word) {

            assertThatThrownBy(()->dict.addWord(word))
                .isInstanceOf(InvalidWordException.class)
                .hasMessage("Error while adding word: word is null or empty");
        }

        @ParameterizedTest
        @MethodSource("provideDataWrongCategory")
        public void InvalidCategory(Word word) {

            assertThatThrownBy(()->dict.addWord(word))
                .isInstanceOf(InvalidWordException.class)
                .hasMessage("Error while adding word: category is null or empty");
        }
    }

    @Nested
    class GetWordGeneratingTests {

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
                .hasMessage("Word can not be found: difficulty 5 does not exist in category fruits");
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


}
