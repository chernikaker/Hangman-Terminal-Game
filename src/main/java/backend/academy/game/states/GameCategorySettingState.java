package backend.academy.game.states;

import backend.academy.game.logic.Dictionary;
import backend.academy.game.logic.GameContext;
import backend.academy.game.validators.InputValidator;
import backend.academy.game.visualizers.PlayerInterface;
import java.util.Objects;

public class GameCategorySettingState implements GameState {

    private final Dictionary dictionary;
    private final GameContext context;
    private boolean error = false;
    private final InputValidator categoryValidator = (Objects::nonNull);

    public GameCategorySettingState(Dictionary d, GameContext ctx) {
        dictionary = d;
        context = ctx;
    }

    @Override
    public String viewScreen(PlayerInterface playerInterface) {
        playerInterface.viewSettingCategoryScreen(dictionary.getCategories(), error);
        return playerInterface.getInput();
    }

    @Override
    public GameState changeState(boolean inputProcessed) {
        if (inputProcessed) {
            return new GameDifficultySettingState(dictionary, context);
        } else {
            error = true;
            return this;
        }
    }

    @Override
    public boolean processInput(String input) {
       if (!categoryValidator.validate(input)) {
           return false;
       }
       if (input.isBlank()) {
            context.setWordCategory(dictionary.generateCategory());
            return true;
       }
       if (dictionary.getCategories().contains(input.toLowerCase().trim())) {
           context.setWordCategory(input.toLowerCase().trim());
           return true;
       }
       return false;
    }
}
