package backend.academy;

import backend.academy.game.GameSession;
import lombok.experimental.UtilityClass;

@UtilityClass
public class Main {
    public static void main(String[] args) {
        GameSession game = new GameSession(System.in, System.out);
        game.start();
    }
}
