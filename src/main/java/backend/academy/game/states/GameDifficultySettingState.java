package backend.academy.game.states;

import backend.academy.game.Dictionary;
import backend.academy.game.GameContext;
import backend.academy.game.Word;
import backend.academy.game.visualizers.PlayerInterface;

public class GameDifficultySettingState implements GameState {

    private final Dictionary dictionary;
    private final GameContext context;
    private boolean error = false;

    public GameDifficultySettingState(Dictionary d, GameContext ctx) {
        dictionary = d;
        context = ctx;
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
        try {
            int difficulty = Integer.parseInt(input);
            if (dictionary.getDifficulties(context.wordCategory()).contains(difficulty)) {
                context.setWordDifficulty(difficulty);
                setContext();
                return true;
            }
            return false;
        } catch (NumberFormatException e) {
            if (input.isBlank()) {
                context.setWordDifficulty(dictionary.generateDifficulty(context.wordCategory()));
                setContext();
                return true;
            }
            return false;
        }
    }

    @SuppressWarnings("magicnumber")
    private void setContext() {
        Word answer = dictionary.getWord(context.wordCategory(), context.wordDifficulty());
        context.setAnswer(answer.word());
        // TODO: add constraints to word and difficulty in dictionary
        int maxMistakes = 7 + 4 / context.wordDifficulty();
        context.setMaxMistakes(maxMistakes);
    }
}
