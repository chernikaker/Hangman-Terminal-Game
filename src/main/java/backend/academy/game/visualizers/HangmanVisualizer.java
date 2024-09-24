package backend.academy.game.visualizers;

import java.util.List;

public class HangmanVisualizer {

    private final List<String> parts = List.of(
        "█",

        """
            █     O""",

        """
            █     O
            █     |""",

        """
            █     O
            █    /|""",

        """
            █     O
            █    /|\\""",

        """
            █     O
            █    /|\\
            █    /""",

        """
            █     O
            █    /|\\
            █    / \\"""
    );

    public String getPart(int part) {

        if (part < 0 || part > parts.size()) {
            throw new IndexOutOfBoundsException("Invalid part number");
        }
        return parts.get(part);
    }
    public int getHeight(int part){
        return switch (part) {
            case 0,1 -> 1;
            case 2, 3, 4 -> 2;
            case 5, 6 -> 3;
            default -> throw new IndexOutOfBoundsException("Invalid part number");
        };
    }

}
