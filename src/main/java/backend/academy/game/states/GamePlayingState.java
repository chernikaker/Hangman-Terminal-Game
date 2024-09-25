package backend.academy.game.states;

import backend.academy.game.logic.GameContext;
import backend.academy.game.validators.InputValidator;
import backend.academy.game.visualizers.PlayerInterface;

public class GamePlayingState implements GameState {

    private final GameContext context;
    private boolean error = false;

    private final InputValidator letterValidator = ((input)->
        input != null
            && !input.isBlank()
            && input.length() == 1
            && Character.isAlphabetic(input.charAt(0)));


    public GamePlayingState(GameContext ctx) {
        context = ctx;
    }

    @Override
    public String viewScreen(PlayerInterface playerInterface) {
        playerInterface.viewHangmanScreen(context, error);
        return playerInterface.getInput();
    }

    @Override
    public GameState changeState(boolean inputProcessed) {

        if (context.mistakes() == context.maxMistakes()
            || context.answer().equals(new String(context.getCurrentAnswer()))) {
            return new GameEndState(context);
        }
        error = !inputProcessed;
        return this;
    }

    @Override
    public boolean processInput(String input) {
        if(!letterValidator.validate(input)) return false;

        return context.processLetter(input.charAt(0));
    }

}
