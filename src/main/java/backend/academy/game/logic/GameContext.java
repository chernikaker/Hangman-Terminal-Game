package backend.academy.game.logic;

import backend.academy.game.exceptions.FieldAlreadySetException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.Getter;

public class GameContext {

    @Getter
    private String wordCategory = "";
    @Getter
    private int wordDifficulty = 0;
    @Getter
    private String answer = "";
    @Getter
    private int maxMistakes = 0;
    @Getter
    private int mistakes = 0;
    private char[] currentAnswer = new char[maxMistakes];
    private final Set<Character> processedLetters = new HashSet<>();

    public void setMaxMistakes(int mistakes) {
        if (mistakes < 1) {
            throw new IllegalArgumentException("Amount of mistakes must be at least 1");
        }
        if (maxMistakes >= 1) {
            throw new FieldAlreadySetException("max mistakes number has already been set");
        }
        this.maxMistakes = mistakes;
    }

    public void setAnswer(String word) {
        if (word.isEmpty()) {
            throw new IllegalArgumentException("Word can not be empty");
        }
        if (!answer.isEmpty()) {
            throw new FieldAlreadySetException("answer has already been set");
        }
        answer = word;
        currentAnswer = new char[word.length()];
        Arrays.fill(currentAnswer, '_');
    }

    public void setWordCategory(String category) {
        if (category.isEmpty()) {
            throw new IllegalArgumentException("Word category cannot be empty");
        }
        if (!wordCategory.isEmpty()) {
            throw new FieldAlreadySetException("word category has already been set");
        }
        this.wordCategory = category;
    }

    public void setWordDifficulty(int difficulty) {
        if (difficulty < 1) {
            throw new IllegalArgumentException("Word difficulty level must be at least 1");
        }
        if (wordDifficulty >= 1) {
            throw new FieldAlreadySetException("word difficulty level has already been set");
        }
        this.wordDifficulty = difficulty;
    }

    public char[] getCurrentAnswer() {
        return Arrays.copyOf(currentAnswer, currentAnswer.length);
    }

    public List<Character> getProcessedLetters() {
        return List.copyOf(processedLetters);
    }

    public boolean processLetter(char letter) {
        String ans = String.valueOf(answer);
        char exactLetter = Character.toLowerCase(letter);
        if (processedLetters.contains(exactLetter)) {
            return false;
        }
        processedLetters.add(exactLetter);
        if (ans.indexOf(exactLetter) == -1) {
            mistakes++;
        } else {
            for (int i = 0; i < answer.length(); i++) {
                if (answer.charAt(i) == exactLetter) {
                    currentAnswer[i] = exactLetter;
                }
            }
        }
        return true;
    }
}
