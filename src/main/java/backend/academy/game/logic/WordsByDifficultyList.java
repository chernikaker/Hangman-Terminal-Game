package backend.academy.game.logic;

import backend.academy.game.exceptions.WordNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordsByDifficultyList {

    private final Map<Integer, List<Word>> wordsByDifficulty = new HashMap<>();

    public void addWord(Word word) {

        wordsByDifficulty
            .computeIfAbsent(word.difficulty(), k -> new ArrayList<>())
            .add(word);
    }

    public List<Integer> getDifficulties() {
        return new ArrayList<>(wordsByDifficulty.keySet());
    }

    public boolean containsDifficulty(int difficulty) {
        return wordsByDifficulty.containsKey(difficulty);
    }

    public boolean containsWord(Word word) {
        return containsDifficulty(word.difficulty())
            && wordsByDifficulty.get(word.difficulty()).contains(word);
    }

    public List<Word> getWords(int difficulty) {
        if (!wordsByDifficulty.containsKey(difficulty)) {
            throw new WordNotFoundException(
                "difficulty " + difficulty + " does not exist");
        }
        return wordsByDifficulty.get(difficulty);
    }
}
