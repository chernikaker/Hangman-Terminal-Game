package backend.academy.game.viewers;

import backend.academy.game.GameContext;
import java.io.InputStream;
import java.io.PrintStream;

public class GameStartScreen extends GameInterface{

    public GameStartScreen(PrintStream out, InputStream in) {
        super(out, in);
    }

    @Override
    public void viewScreen(GameContext context) {}

    @Override
    public String getInput() {
        return scanner.nextLine();
    }

    // CHANGE INTERFACE
    @Override
    public GameInterface changeInterface() {
        return new GameStartScreen(out, in);
    }

    @Override
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
}
