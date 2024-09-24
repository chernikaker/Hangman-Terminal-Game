package backend.academy.game.visualizers;

import backend.academy.game.logic.GameContext;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;
import lombok.extern.slf4j.Slf4j;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

@Slf4j
@SuppressWarnings("magicnumber")
public class PlayerInterface {

    private final PrintStream out;

    private final Scanner scanner;
    private final HangmanVisualizer visualizer = new HangmanVisualizer();
    private final Terminal terminal;


    public PlayerInterface(PrintStream out, InputStream in) {
        this.out = out;
        scanner = new Scanner(in, StandardCharsets.UTF_8);

        try {
            terminal = TerminalBuilder.terminal();
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public String getInput() {
        return scanner.nextLine();
    }

    public void viewStartScreen() {
        clearScreen();
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

    public void viewSettingCategoryScreen(List<String> categories, boolean previousInvalidInput) {
        clearScreen();
        out.println("""
             █▀▀ █ █ █▀█ █▀█ █▀▀ █▀▀   █▀▀ █▀█ ▀█▀ █▀▀ █▀▀ █▀█ █▀▄ █ █
             █   █▀█ █ █ █ █ ▀▀█ █▀▀   █   █▀█  █  █▀▀ █ █ █ █ █▀▄  █
             ▀▀▀ ▀ ▀ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀   ▀▀▀ ▀ ▀  ▀  ▀▀▀ ▀▀▀ ▀▀▀ ▀ ▀  ▀
            """);

        int categoryColumns = 3;
        for (int i = 0; i < categories.size(); i++) {
            out.printf("%-25s", categories.get(i));
            if ((i + 1) % categoryColumns == 0) {
                out.println();
            }
        }
        if (categories.size() % categoryColumns != 0) {
            out.println();
        }
        if (previousInvalidInput) {
            out.println("Error: no such category");
        }
        out.print("Enter one of the categories(leave empty to choose random): ");

    }

    public void viewSettingDifficultyScreen(List<Integer> difficulties, boolean previousInvalidInput) {
        clearScreen();
        out.println("""
              █▀▀ █ █ █▀█ █▀█ █▀▀ █▀▀   █▀▄ ▀█▀ █▀▀ █▀▀ ▀█▀ █▀▀ █ █ █   ▀█▀ █ █
              █   █▀█ █ █ █ █ ▀▀█ █▀▀   █ █  █  █▀▀ █▀▀  █  █   █ █ █    █   █
              ▀▀▀ ▀ ▀ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀   ▀▀  ▀▀▀ ▀   ▀   ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀  ▀   ▀
              █   █▀▀ █ █ █▀▀ █
              █   █▀▀ ▀▄▀ █▀▀ █
              ▀▀▀ ▀▀▀  ▀  ▀▀▀ ▀▀▀
            """);

        int difficultyColumns = 2;
        for (int i = 0; i < difficulties.size(); i++) {
            out.printf("%-15s", difficulties.get(i));
            if ((i + 1) % difficultyColumns == 0) {
                out.println();
            }
        }
        if (difficulties.size() % difficultyColumns != 0) {
            out.println();
        }
        if (previousInvalidInput) {
            out.println("Error: no such difficulty level");
        }
        out.print("Enter one of the levels(leave empty to choose random): ");

    }


    public void viewHangmanScreen(GameContext context, boolean previousInvalidInput) {
        clearScreen();
        drawHangman(context);
        if (previousInvalidInput) {
            out.println("Error: invalid input, enter one letter, which was not checked");
        }
        out.print("Checked letters: ");
        for (char c : context.getProcessedLetters()) {
            out.print(c + " ");
        }
        out.println();
        out.println("Remaining attempts: " + (context.MAX_MISTAKES() - context.mistakes()));
        out.println("CURRENT ANSWER");
        for (char c : context.getCurrentAnswer()) {
            out.print(c);
        }
        out.println();
        out.print("Enter next letter: ");
    }

    public void viewEndScreen(GameContext context) {
        clearScreen();
        if (context.mistakes() == context.MAX_MISTAKES()) {
            drawHangman(context);
            out.println("You lost! Answer was:" + context.answer());
        } else {
            out.println("""
               \\O/
                |
               / \\""");
            out.println("You won! Answer: " + context.answer());
        }
    }

    public void drawHangman(GameContext context) {
        out.println("█▀▀▀▀▀█");
        int ropeCount = context.MAX_MISTAKES() - context.mistakes() > 6
            ? context.mistakes() : context.MAX_MISTAKES() - 6;
        int hangmanCount = context.MAX_MISTAKES() - context.mistakes() > 6
            ? 0 : 6 - context.MAX_MISTAKES() + context.mistakes();
        for (int i = 0; i < ropeCount; i++) {
            out.println("█     |");
        }
        out.println(visualizer.getPart(hangmanCount));
        int remainingHeight = context.MAX_MISTAKES()-3-ropeCount- visualizer.getHeight(hangmanCount);
        for (int i = 0; i < remainingHeight+1; i++) {
            out.println("█");
        }
        out.println("----------");
    }

    public void clearScreen() {
        try {
            if (terminal != null) {
                terminal.puts(org.jline.utils.InfoCmp.Capability.clear_screen);
            } else {
                log.warn("Terminal is not available, unable to clear screen.");
            }
        } catch (Exception e) {
            log.error("An error occurred while clearing the screen: {}", e.getMessage());
        }
    }
}
