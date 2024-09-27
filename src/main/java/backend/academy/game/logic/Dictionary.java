package backend.academy.game.logic;

import backend.academy.game.exceptions.GameException;
import backend.academy.game.exceptions.InvalidWordException;
import backend.academy.game.exceptions.WordNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Dictionary {

    public static final int MAX_DIFFICULTY = 5;
    private static final int FILE_DATA_PARTS = 3;
    public static final String DICTIONARY_PATH = "src/main/resources/dict/data.txt";

    private final Map<String, WordsByDifficultyList> dictionary = new HashMap<>();
    private final SecureRandom random = new SecureRandom();

    public void addWord(Word word) {

        if (word.category() == null || word.category().isBlank()) {
            throw new InvalidWordException("category is null or empty");
        }

        if (word.difficulty() < 1 || word.difficulty() > MAX_DIFFICULTY) {
            throw new InvalidWordException(
                "word difficulty must be at least 1 and at most " + MAX_DIFFICULTY
            );
        }

        if (word.word() == null || word.word().isBlank()) {
            throw new InvalidWordException("word is null or empty");
        }

        dictionary
            .computeIfAbsent(word.category(), k -> new WordsByDifficultyList())
            .addWord(word);
    }

    public void fillDictionaryFromFile(String filename) {
        try (BufferedReader reader = new BufferedReader(new FileReader(filename, StandardCharsets.UTF_8))) {
            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split(" ");
                if (parts.length == FILE_DATA_PARTS) {
                    String word = parts[0].trim();
                    try {
                        int difficulty = Integer.parseInt(parts[1].trim());
                        String category = parts[2].trim();
                        addWord(new Word(word, difficulty, category));
                    } catch (NumberFormatException e) {
                        log.warn("Error while parsing difficulty in line: {} Current word skipped", line);
                    } catch (InvalidWordException e) {
                        log.warn("Error while making word in line: {}. {}", line, e.getMessage());
                    }
                } else {
                    log.warn("Error while parsing line (wrong params): {} Current line skipped", line);
                }
            }
        } catch (IOException e) {
            throw new GameException("Error while working with file: " + filename, e);
        }
    }

    public String generateCategory() {
        int categoryIndex = random.nextInt(dictionary.size());
        return dictionary.keySet().toArray()[categoryIndex].toString();
    }

    public int generateDifficulty(String category) {

        if (!dictionary.containsKey(category)) {
            throwNotFoundCategory(category);
        }
        int difficultyIndex = random.nextInt(dictionary.get(category).getDifficulties().size());
        return (int) dictionary.get(category).getDifficulties().toArray()[difficultyIndex];
    }

    public Word getWord(String category, int difficulty) {

        if (!dictionary.containsKey(category)) {
            throwNotFoundCategory(category);
        }

        int wordIndex = random.nextInt(dictionary.get(category).getWords(difficulty).size());
        return dictionary.get(category).getWords(difficulty).get(wordIndex);
    }

    public List<String> getCategories() {
        return new ArrayList<>(dictionary.keySet());
    }

    public List<Integer> getDifficulties(String category) {
        if (!dictionary.containsKey(category)) {
            throwNotFoundCategory(category);
        }
        return dictionary.get(category).getDifficulties();
    }

    public boolean containsWord(Word word) {
        return dictionary.containsKey(word.category())
            && dictionary.get(word.category()).containsWord(word);
    }

    private void throwNotFoundCategory(String category) {
        throw new WordNotFoundException("category " + category + " does not exist");
    }


}
