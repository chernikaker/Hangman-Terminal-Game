package backend.academy.game;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class GameContext {

    @SuppressWarnings("membername")
    final int MAX_MISTAKES;
    int mistakes = 0;
    final String answer;
    char[] currentAnswer;
    Set<Character> processedLetters = new HashSet<>();

    public GameContext(int maxMistakes, String word) {
        MAX_MISTAKES = maxMistakes;
        answer = word;
        currentAnswer = new char[word.length()];
        Arrays.fill(currentAnswer, '_');
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
