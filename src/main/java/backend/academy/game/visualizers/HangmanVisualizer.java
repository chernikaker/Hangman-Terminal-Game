package backend.academy.game.visualizers;

import java.util.List;

public class HangmanVisualizer {

    public static final int MIN_ATTEMPTS = 7;
    public static final int HANGMAN_STEPS = 6;
    public static final int STEPS_NOT_CHANGING_HEIGHT = 3;
    private static final List<String> PARTS = List.of(
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
        if (part < 0 || part >= PARTS.size()) {
            throw new IllegalArgumentException("Part number is out of range");
        }
        return PARTS.get(part);
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
