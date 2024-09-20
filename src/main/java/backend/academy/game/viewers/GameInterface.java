package backend.academy.game.viewers;

import backend.academy.game.Dictionary;
import backend.academy.game.GameContext;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
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

    public String getInput() {
        return scanner.nextLine();
    }

    public void clearScreen(){
        try {
            Terminal terminal = TerminalBuilder.terminal();
            terminal.puts(org.jline.utils.InfoCmp.Capability.clear_screen);
            terminal.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public abstract void viewScreen(GameContext context);
    public abstract GameInterface changeInterface(boolean validated, boolean processed);
    public abstract void viewStartScreen();
    public abstract void viewSettingsScreen(Dictionary dictionary);
}
