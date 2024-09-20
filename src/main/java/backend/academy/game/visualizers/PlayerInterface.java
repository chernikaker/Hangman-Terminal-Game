package backend.academy.game.visualizers;

import backend.academy.game.GameContext;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import java.util.List;
import java.util.Scanner;
import java.io.InputStream;
import java.io.PrintStream;

public class PlayerInterface {

    PrintStream out;
    InputStream in;

    final Scanner scanner;

    public PlayerInterface(PrintStream out, InputStream in) {
        this.out = out;
        this.in = in;
        scanner = new Scanner(in);
    }

    public String getInput() {
        return scanner.nextLine();
    }

    public void viewStartScreen() {
        out.println("""
            ██╗  ██╗ █████╗ ███╗   ██╗ ██████╗ ███╗   ███╗ █████╗ ███╗   ██╗
            ██║  ██║██╔══██╗████╗  ██║██╔════╝ ████╗ ████║██╔══██╗████╗  ██║
            ███████║███████║██╔██╗ ██║██║  ███╗██╔████╔██║███████║██╔██╗ ██║
            ██╔══██║██╔══██║██║╚██╗██║██║   ██║██║╚██╔╝██║██╔══██║██║╚██╗██║
            ██║  ██║██║  ██║██║ ╚████║╚██████╔╝██║ ╚═╝ ██║██║  ██║██║ ╚████║
            ╚═╝  ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝ ╚═════╝ ╚═╝     ╚═╝╚═╝  ╚═╝╚═╝  ╚═══╝
                         ██████╗  █████╗ ███╗   ███╗███████╗               \s
                        ██╔════╝ ██╔══██╗████╗ ████║██╔════╝               \s
                        ██║  ███╗███████║██╔████╔██║█████╗                 \s
                        ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝                 \s
                        ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗               \s
                         ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝               \s""");
        out.println("               [Press Enter to continue]");

    }

    public void viewSettingCategoryScreen(List<String> categories) {}

    public void viewSettingDifficultyScreen(List<String> categories) {}

    public void viewHangmanScreen(GameContext context, boolean previousInvalidInput) {}

    public void clearScreen(){
        try {
            Terminal terminal = TerminalBuilder.terminal();
            terminal.puts(org.jline.utils.InfoCmp.Capability.clear_screen);
            terminal.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
