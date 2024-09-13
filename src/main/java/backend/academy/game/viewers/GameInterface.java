package backend.academy.game.viewers;

import backend.academy.game.GameContext;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

public abstract class GameInterface {

    PrintStream out;
    InputStream in;

    final Scanner scanner;

    GameInterface(PrintStream out, InputStream in) {
        this.out = out;
        this.in = in;
        scanner = new Scanner(in);
    }

    public abstract void viewScreen(GameContext context);
    public abstract String getInput();
    public abstract GameInterface changeInterface();
    public abstract void viewStartScreen();
}
