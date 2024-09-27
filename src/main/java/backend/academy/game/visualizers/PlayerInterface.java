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
import static backend.academy.game.visualizers.HangmanVisualizer.HANGMAN_STEPS;

@Slf4j
public class PlayerInterface {

    private final static int DIFFICULTY_COLUMNS = 5;
    private final static int CATEGORY_COLUMNS = 5;

    private final PrintStream out;
    private final Scanner scanner;
    private final HangmanVisualizer visualizer = new HangmanVisualizer();
    private Terminal terminal = null;

    public PlayerInterface(PrintStream out, InputStream in) {
        this.out = out;
        scanner = new Scanner(in, StandardCharsets.UTF_8);
    }

    public void createTerminal() {
        try {
            terminal = TerminalBuilder.terminal();
        } catch (Exception e) {
            throw new RuntimeException("Terminal can't be created: ", e);
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
                         ██████╗  █████╗ ███╗   ███╗███████╗
                        ██╔════╝ ██╔══██╗████╗ ████║██╔════╝
                        ██║  ███╗███████║██╔████╔██║█████╗
                        ██║   ██║██╔══██║██║╚██╔╝██║██╔══╝
                        ╚██████╔╝██║  ██║██║ ╚═╝ ██║███████╗
                         ╚═════╝ ╚═╝  ╚═╝╚═╝     ╚═╝╚══════╝""");
        out.println("               [Press Enter to continue]");
    }

    public void viewSettingCategoryScreen(List<String> categories, boolean previousInvalidInput) {
        clearScreen();
        out.println("""
            ╔═══════════════════════════════════════════════════════════╗
            ║ █▀▀ █ █ █▀█ █▀█ █▀▀ █▀▀   █▀▀ █▀█ ▀█▀ █▀▀ █▀▀ █▀█ █▀▄ █ █ ║
            ║ █   █▀█ █ █ █ █ ▀▀█ █▀▀   █   █▀█  █  █▀▀ █ █ █ █ █▀▄  █  ║
            ║ ▀▀▀ ▀ ▀ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀   ▀▀▀ ▀ ▀  ▀  ▀▀▀ ▀▀▀ ▀▀▀ ▀ ▀  ▀  ║
            ╚═══════════════════════════════════════════════════════════╝
            """);
        for (int i = 0; i < categories.size(); i++) {
            out.printf("%-25s", categories.get(i).toUpperCase());
            if ((i + 1) % CATEGORY_COLUMNS == 0) {
                out.println();
            }
        }
        if (categories.size() % CATEGORY_COLUMNS != 0) {
            out.println();
        }
        out.println("═══════════════════════════════════════════════════════════");
        out.println();
        if (previousInvalidInput) {
            out.println("Error: no such category. Try again!");
        }
        out.print("Enter one of the categories [leave empty to choose random]: ");
    }

    public void viewSettingDifficultyScreen(List<Integer> difficulties, boolean previousInvalidInput) {
        clearScreen();
        out.println("""
            ╔═══════════════════════════════════════════════════════════════════╗
            ║ █▀▀ █ █ █▀█ █▀█ █▀▀ █▀▀   █▀▄ ▀█▀ █▀▀ █▀▀ ▀█▀ █▀▀ █ █ █   ▀█▀ █ █ ║
            ║ █   █▀█ █ █ █ █ ▀▀█ █▀▀   █ █  █  █▀▀ █▀▀  █  █   █ █ █    █   █  ║
            ║ ▀▀▀ ▀ ▀ ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀   ▀▀  ▀▀▀ ▀   ▀   ▀▀▀ ▀▀▀ ▀▀▀ ▀▀▀  ▀   ▀  ║
            ║                       █   █▀▀ █ █ █▀▀ █                           ║
            ║                       █   █▀▀ ▀▄▀ █▀▀ █                           ║
            ║                       ▀▀▀ ▀▀▀  ▀  ▀▀▀ ▀▀▀                         ║
            ╚═══════════════════════════════════════════════════════════════════╝
            """);
        for (int i = 0; i < difficulties.size(); i++) {
            out.printf("%-15s", difficulties.get(i));
            if ((i + 1) % DIFFICULTY_COLUMNS == 0) {
                out.println();
            }
        }
        if (difficulties.size() % DIFFICULTY_COLUMNS != 0) {
            out.println();
        }
        out.println("═════════════════════════════════════════════════════════════════════");
        out.println();
        if (previousInvalidInput) {
            out.println("Error: no such difficulty level");
        }
        out.print("Enter one of the levels [leave empty to choose random]: ");
    }

    public void viewHangmanScreen(GameContext context, boolean previousInvalidInput, boolean withHint) {
        clearScreen();
        drawHangman(context);
        out.print("Checked letters: ");
        for (char c : context.getProcessedLetters()) {
            out.print(c + " ");
        }
        out.println();
        out.println("Remaining attempts: " + (context.maxMistakes() - context.mistakes()));
        out.println();
        out.println("CURRENT ANSWER");
        for (char c : context.getCurrentAnswer()) {
            out.print(c);
        }
        out.println('\n');
        if (previousInvalidInput) {
            out.println("Error: invalid input, enter one letter, which was not checked. Try again!");
        }
        if (!withHint) {
            out.print("Enter next letter [or enter /hint to get hint]: ");
        } else {
            out.println("HINT: "+context.hint());
            out.print("Enter next letter: ");
        }
    }

    public void viewEndScreen(GameContext context) {
        clearScreen();
        if (context.mistakes() == context.maxMistakes()) {
            drawHangman(context);
            out.println("YOU LOST!\nAnswer was: " + context.answer());
        } else {
            out.println("""
                ██╗    ██╗██╗███╗   ██╗
                ██║    ██║██║████╗  ██║
                ██║ █╗ ██║██║██╔██╗ ██║
                ██║███╗██║██║██║╚██╗██║
                ╚███╔███╔╝██║██║ ╚████║
                 ╚══╝╚══╝ ╚═╝╚═╝  ╚═══╝
                   \\O/ \\O/ \\O/ \\O/
                    |   |   |   |
                   / \\ / \\ / \\ / \\""");
            out.println("\nYOU WON!\nAnswer: " + context.answer());
        }
    }

    public void drawHangman(GameContext context) {
        out.println("█▀▀▀▀▀█");
        int ropeCount = context.maxMistakes() - context.mistakes() > HANGMAN_STEPS
            ? context.mistakes() : context.maxMistakes() - HANGMAN_STEPS;
        int hangmanCount = context.maxMistakes() - context.mistakes() > HANGMAN_STEPS
            ? 0 : HANGMAN_STEPS - context.maxMistakes() + context.mistakes();
        for (int i = 0; i < ropeCount; i++) {
            out.println("█     |");
        }
        out.println(visualizer.getPart(hangmanCount));
        int remainingHeight = context.maxMistakes()
            - HangmanVisualizer.STEPS_NOT_CHANGING_HEIGHT
            - ropeCount
            - visualizer.getHeight(hangmanCount);
        for (int i = 0; i < remainingHeight + 1; i++) {
            out.println('█');
        }
        out.println("═════════════");
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
