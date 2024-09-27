package backend.academy.game.logic.dictionary;

import backend.academy.game.logic.Dictionary;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FillDictionaryFromFileTest {

    private static Dictionary dict;

    @BeforeEach
    public void setUp() {
        dict = new Dictionary();
    }

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
