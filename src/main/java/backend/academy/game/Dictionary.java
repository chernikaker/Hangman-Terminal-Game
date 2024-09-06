package backend.academy.game;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Dictionary {

    private final Map<String, Map<Integer, List<Word>>> dictionary = new HashMap<>();
    private final SecureRandom random = new SecureRandom();

    public Dictionary() {

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

    private void addWord(Word word) {
        dictionary
            .computeIfAbsent(word.category(), k -> new HashMap<>())
            .computeIfAbsent(word.difficulty(), k -> new ArrayList<>())
            .add(word);
    }

    private String generateCategory(){
        int categoryIndex = random.nextInt(dictionary.size());
        return dictionary.keySet().toArray()[categoryIndex].toString();
    }

    private int generateDifficulty(String category){

        int difficultyIndex = random.nextInt(dictionary.get(category).size());
        return (int) dictionary.get(category).keySet().toArray()[difficultyIndex];
    }

    public Word getWord(String category, int difficulty){

        if(category.isEmpty()) category = generateCategory();
        if(difficulty==0) difficulty = generateDifficulty(category);
        int wordIndex = random.nextInt(dictionary.get(category).get(difficulty).size());
        return dictionary.get(category).get(difficulty).get(wordIndex);
    }


}
