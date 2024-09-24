package backend.academy.game.states;

import backend.academy.game.Dictionary;
import backend.academy.game.GameContext;
import backend.academy.game.visualizers.PlayerInterface;

public class GameCategorySettingState implements GameState{

    private final Dictionary dictionary;
    private final GameContext context;
    private boolean error = false;

    public GameCategorySettingState(Dictionary d, GameContext ctx) {
        dictionary = d;
        context = ctx;
    }

    @Override
    public String viewScreen(PlayerInterface playerInterface) {
        playerInterface.viewSettingCategoryScreen(dictionary.getCategories(),error);
        return playerInterface.getInput();
    }

    @Override
    public GameState changeState(boolean inputProcessed) {

        if(inputProcessed) return new GameDifficultySettingState(dictionary,context);
        else {
            error = true;
            return this;
        }
    }

    @Override
    public boolean processInput(String input) {
        if(input==null) return false;
        String processedInput = input.toLowerCase().trim();
        if(dictionary.getCategories().contains(processedInput)){
            context.setWordCategory(processedInput);
            return true;
        }
        if(processedInput.isEmpty()) {
            context.setWordCategory(dictionary.generateCategory());
            return true;
        }
        return false;
    }


}
