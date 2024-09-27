package backend.academy.game.states;

import backend.academy.game.logic.Dictionary;
import backend.academy.game.logic.GameContext;
import backend.academy.game.logic.Word;
import backend.academy.game.validators.InputValidator;
import backend.academy.game.visualizers.HangmanVisualizer;
import backend.academy.game.visualizers.PlayerInterface;

public class GameDifficultySettingState implements GameState {

    private final Dictionary dictionary;
    private final GameContext context;
    private boolean error = false;
    private final InputValidator difficultyValidator;

    public GameDifficultySettingState(Dictionary d, GameContext ctx) {
        dictionary = d;
        context = ctx;
        difficultyValidator = (input -> {
            try {
                Integer.parseInt(input);
                return true;
            } catch (NumberFormatException e) {
                return input.isBlank();
            }
        });
    }

    @Override
    public String viewScreen(PlayerInterface playerInterface) {
        playerInterface.viewSettingDifficultyScreen(dictionary.getDifficulties(context.wordCategory()), error);
        return playerInterface.getInput();
    }

    @Override
    public GameState changeState(boolean inputProcessed) {
        if (inputProcessed) {
            return new GamePlayingState(context);
        } else {
            error = true;
            return this;
        }
    }

    @Override
    public boolean processInput(String input) {
        if (!difficultyValidator.validate(input)) {
            return false;
        }
        int difficulty = input.isBlank()
            ? dictionary.generateDifficulty(context.wordCategory())
            : Integer.parseInt(input);
        if (dictionary.getDifficulties(context.wordCategory()).contains(difficulty)) {
            context.setWordDifficulty(difficulty);
            setContext();
            return true;
        }
        return false;
    }

    private void setContext() {
        Word answer = dictionary.getWord(context.wordCategory(), context.wordDifficulty());
        context.setAnswer(answer.word());
        int maxMistakes = HangmanVisualizer.MIN_ATTEMPTS + Dictionary.MAX_DIFFICULTY - context.wordDifficulty();
        context.setMaxMistakes(maxMistakes);
    }
}
