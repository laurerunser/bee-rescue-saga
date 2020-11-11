package view.cli;

import model.level.LevelLimitedMoves;

public class CliLevelMovesView extends CliLevelView {

    private LevelLimitedMoves level;

    public CliLevelMovesView(LevelLimitedMoves level) {
        super(level);
        this.level = level;
    }

    @Override
    public void drawEndHeader() {
        System.out.println("To win the level, you must erase all the pieces on the board before you run out of moves");
        System.out.println("You have " + level.getMoves() + " left");
    }

}
