package backend.academy.game;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Word {

    private String word;
    private int difficulty;
    private String category;

}
