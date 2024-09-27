package backend.academy.game.logic.dictionary;

import backend.academy.game.exceptions.InvalidWordException;
import backend.academy.game.logic.Dictionary;
import backend.academy.game.logic.Word;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DictionaryAddWordTest {

    private static final Dictionary dict = new Dictionary();

    @Test
    public void NewDifficultyAndCategory() {

        Word word = new Word("pizza", 1, "food", "hint");
        dict.addWord(word);
        assertTrue(dict.containsWord(word));
    }

    @Test
    public void NewDifficultyPresentCategory() {

        Word word1 = new Word("banana", 1, "fruits", "hint");
        dict.addWord(word1);
        Word word = new Word("dragonfruit", 5, "fruits", "hint");
        dict.addWord(word);
        assertTrue(dict.containsWord(word));
    }

    @Test
    public void PresentDifficultyAndCategory() {

        Word word1 = new Word("banana", 1, "fruits","hint");
        dict.addWord(word1);
        Word word = new Word("lemon", 1, "fruits","hint");
        dict.addWord(word);
        assertTrue(dict.containsWord(word));
    }

    private static Stream<Word> provideDataWrongWord() {
        return Stream.of(
            new Word("",1,"fruits","hint"),
            new Word(null,1,"fruits","hint"),
            new Word("    ",1,"fruits","hint")
        );
    }

    private static Stream<Word> provideDataWrongCategory() {
        return Stream.of(
            new Word("banana",1,"","hint"),
            new Word("banana",1,null,"hint"),
            new Word("banana",1,"   ","hint")
        );
    }

    private static Stream<Word> provideDataWrongHint() {
        return Stream.of(
            new Word("banana",1,"fruits",""),
            new Word("banana",1,"fruits",null),
            new Word("banana",1,"fruits","   ")
        );
    }

    @ParameterizedTest
    @CsvSource({"0","6"})
    public void InvalidDifficulty(int difficulty) {

        Word word = new Word("lemon", difficulty, "fruits","hint");
        assertThatThrownBy(()->dict.addWord(word))
            .isInstanceOf(InvalidWordException.class)
            .hasMessage("Error while adding word: word difficulty must be at least 1 and at most 5");
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

    @ParameterizedTest
    @MethodSource("provideDataWrongHint")
    public void InvalidHint(Word word) {

        assertThatThrownBy(()->dict.addWord(word))
            .isInstanceOf(InvalidWordException.class)
            .hasMessage("Error while adding word: hint is null or empty");
    }
}
