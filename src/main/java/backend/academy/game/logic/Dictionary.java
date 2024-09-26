package backend.academy.game.logic;

import backend.academy.game.exceptions.InvalidWordException;
import backend.academy.game.exceptions.WordNotFoundException;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Stream;
import static java.util.function.Predicate.not;

public class Dictionary {

    public static final int maxDifficulty = 5;
    private final Map<String, Map<Integer, List<Word>>> dictionary = new HashMap<>();
    private final SecureRandom random = new SecureRandom();
    Logger log = Logger.getLogger("backend.academy.game.logic");


    public void addWord(Word word) {
        if(word.difficulty()<1 || word.difficulty()>maxDifficulty) {
            throw new InvalidWordException
                ("word difficulty must be at least 1 and at most " + maxDifficulty);
        }

        if(word.word() == null || word.word().isBlank()) {
            throw new InvalidWordException("word is null or empty");
        }

        if(word.category() == null || word.category().isBlank()) {
            throw new InvalidWordException("category is null or empty");
        }

        dictionary
            .computeIfAbsent(word.category(), k -> new HashMap<>())
            .computeIfAbsent(word.difficulty(), k -> new ArrayList<>())
            .add(word);
    }

    public void fillDictionaryFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(" ");
                if (parts.length == 3) {
                    String word = parts[0].trim();
                    try {
                        int difficulty = Integer.parseInt(parts[1].trim());
                        String category = parts[2].trim();
                        addWord(new Word(word, difficulty, category));
                    } catch (NumberFormatException e) {
                        log.warning("Error while parsing difficulty in line: " + line);
                        log.warning("Current word skipped");
                    } catch (InvalidWordException e) {
                        log.warning("Error while making word in line: " + line + ". " + e.getMessage());
                    }
                } else {
                    log.warning("Error while parsing line (wrong params): " + line);
                    log.warning("Current line skipped");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Error while working with file: " + filename);
        }
    }

    public String generateCategory() {
        int categoryIndex = random.nextInt(dictionary.size());
        return dictionary.keySet().toArray()[categoryIndex].toString();
    }

    public int generateDifficulty(String category) {

        if (!dictionary.containsKey(category)) {
            throw new WordNotFoundException("category " + category + " does not exist");
        }
        int difficultyIndex = random.nextInt(dictionary.get(category).size());
        return (int) dictionary.get(category).keySet().toArray()[difficultyIndex];
    }

    public Word getWord(String category, int difficulty) {

        if (!dictionary.containsKey(category)) {
            throw new WordNotFoundException("category " + category + " does not exist");
        }
        if (!dictionary.get(category).containsKey(difficulty)) {
            throw new WordNotFoundException(
                "difficulty " + difficulty + " does not exist in category " + category);
        }
        int wordIndex = random.nextInt(dictionary.get(category).get(difficulty).size());
        return dictionary.get(category).get(difficulty).get(wordIndex);
    }

    public List<String> getCategories() {
        return new ArrayList<>(dictionary.keySet());
    }

    public List<Integer> getDifficulties(String category) {
        if (!dictionary.containsKey(category)) {
            throw new WordNotFoundException("category " + category + " does not exist");
        }
        return new ArrayList<>(dictionary.get(category).keySet());
    }

    public boolean containsWord(Word word) {
        return dictionary.containsKey(word.category())
            && dictionary.get(word.category()).containsKey(word.difficulty())
            && dictionary.get(word.category()).get(word.difficulty()).contains(word);
    }

}
