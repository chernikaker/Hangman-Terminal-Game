package backend.academy.game.logic;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {

    private final Map<String, Map<Integer, List<Word>>> dictionary = new HashMap<>();
    private final SecureRandom random = new SecureRandom();

    @SuppressWarnings({"MagicNumber", "MultipleStringLiterals"})
    public void generateDefaultDictionary() {

        addWord(new Word("apple", 1, "fruits"));
        addWord(new Word("orange", 1, "fruits"));
        addWord(new Word("mango", 1, "fruits"));
        addWord(new Word("pear", 1, "fruits"));

        addWord(new Word("grapefruit", 2, "fruits"));
        addWord(new Word("pineapple", 2, "fruits"));
        addWord(new Word("watermelon", 2, "fruits"));

        addWord(new Word("tiger", 1, "animals"));
        addWord(new Word("horse", 1, "animals"));
        addWord(new Word("whale", 1, "animals"));
        addWord(new Word("rabbit", 1, "animals"));

        addWord(new Word("tortoise", 2, "animals"));
        addWord(new Word("dolphin", 2, "animals"));
        addWord(new Word("elephant", 2, "animals"));

        addWord(new Word("armadillo", 3, "animals"));
        addWord(new Word("hippopotamus", 3, "animals"));
        addWord(new Word("squirrel", 3, "animals"));
    }

    public void addWord(Word word) {
        dictionary
            .computeIfAbsent(word.category(), k -> new HashMap<>())
            .computeIfAbsent(word.difficulty(), k -> new ArrayList<>())
            .add(word);
    }

    public String generateCategory() {
        int categoryIndex = random.nextInt(dictionary.size());
        return dictionary.keySet().toArray()[categoryIndex].toString();
    }

    public int generateDifficulty(String category) {

        int difficultyIndex = random.nextInt(dictionary.get(category).size());
        return (int) dictionary.get(category).keySet().toArray()[difficultyIndex];
    }

    public Word getWord(String category, int difficulty) {

        if (!dictionary.containsKey(category)) {
            throw new IllegalArgumentException("Category " + category + " does not exist");
        }
        if (!dictionary.get(category).containsKey(difficulty)) {
            throw new IllegalArgumentException(
                "Difficulty " + difficulty + " does not exist in category " + category);
        }
        int wordIndex = random.nextInt(dictionary.get(category).get(difficulty).size());
        return dictionary.get(category).get(difficulty).get(wordIndex);
    }

    public List<String> getCategories() {
        return new ArrayList<>(dictionary.keySet());
    }

    public List<Integer> getDifficulties(String category) {
        return new ArrayList<>(dictionary.get(category).keySet());
    }

    public boolean containsWord(Word word) {
        return dictionary.containsKey(word.category())
            && dictionary.get(word.category()).containsKey(word.difficulty())
            && dictionary.get(word.category()).get(word.difficulty()).contains(word);
    }

}
