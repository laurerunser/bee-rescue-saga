package controller;

import model.level.Level;
import view.LevelView;

public class LevelController {
    private final Level level;
    private final LevelView view;

    public LevelController(Level level, LevelView view) {
        this.level = level;
        this.view = view;
    }
}
