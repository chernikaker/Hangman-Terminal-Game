package backend.academy.game.visualizers;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class HangmanVisualizerTest {

    private final HangmanVisualizer hangmanVisualizer = new HangmanVisualizer();

    @ParameterizedTest
    @CsvSource({"0", "1", "2", "3", "4", "5"})
    public void getPartCorrectPartTest(int partNum) {
        String part = assertDoesNotThrow(() -> hangmanVisualizer.getPart(partNum));
        assertFalse(part.isEmpty());
    }

    @ParameterizedTest
    @CsvSource({"-1", "7"})
    public void getPartIncorrectPartTest(int partNum) {
        assertThatThrownBy(() -> hangmanVisualizer.getPart(partNum))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid part number");
    }

    @ParameterizedTest
    @CsvSource({"0", "1"})
    public void getHeightCorrectPartTest1(int partNum) {
        int height = assertDoesNotThrow(() -> hangmanVisualizer.getHeight(partNum));
        assertEquals(1, height);
    }

    @ParameterizedTest
    @CsvSource({"2", "3", "4"})
    public void getHeightCorrectPartTest2(int partNum) {
        int height = assertDoesNotThrow(() -> hangmanVisualizer.getHeight(partNum));
        assertEquals(2, height);
    }

    @ParameterizedTest
    @CsvSource({"5", "6"})
    public void getHeightCorrectPartTest3(int partNum) {
        int height = assertDoesNotThrow(() -> hangmanVisualizer.getHeight(partNum));
        assertEquals(3, height);
    }

    @ParameterizedTest
    @CsvSource({"-1", "7"})
    public void getHeightIncorrectPartTest(int partNum) {
        assertThatThrownBy(() -> hangmanVisualizer.getPart(partNum))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessage("Invalid part number");
    }
}
