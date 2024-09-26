package backend.academy.game.visualizers;

import java.util.List;

public class HangmanVisualizer {

    public static final int MIN_ATTEMPTS = 7;
    public static final int HANGMAN_STEPS = 6;
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

        if (part < 0 || part >= parts.size()) {
            throw new IllegalArgumentException("Part number is out of range");
        }
        return parts.get(part);
    }

    @SuppressWarnings("magicnumber")
    public int getHeight(int part) {
        return switch (part) {
            case 0, 1 -> 1;
            case 2, 3, 4 -> 2;
            case 5, 6 -> 3;
            default -> throw new IllegalArgumentException("Invalid part number");
        };
    }

}
