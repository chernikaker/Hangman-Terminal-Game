package backend.academy.game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameContext {

    @SuppressWarnings("membername")
    private final int MAX_MISTAKES;
    private int mistakes = 0;

    private final String answer;
    private final char[] currentAnswer;
    private final Set<Character> processedLetters = new HashSet<>();


    public String getAnswer() {
        return answer;
    }

    public GameContext(int maxMistakes, String word) {
        MAX_MISTAKES = maxMistakes;
        answer = word;
        currentAnswer = new char[word.length()];
        Arrays.fill(currentAnswer, '_');
    }

    public char[] getCurrentAnswer() {
        return Arrays.copyOf(currentAnswer, currentAnswer.length);
    }

    public List<Character> getProcessedLetters() {
        return List.copyOf(processedLetters);
    }

    public int getRemainingAttempts() {
        return MAX_MISTAKES-mistakes;
    }

    public boolean processLetter(char letter) {

        char exactLetter = Character.toLowerCase(letter);
        if (processedLetters.contains(exactLetter)) {
            return false;
        }
        processedLetters.add(exactLetter);
        if (answer.indexOf(exactLetter) == -1) {
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
