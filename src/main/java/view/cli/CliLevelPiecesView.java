package view.cli;

import model.level.LevelLimitedPieces;

/**
 * This is the cli View for a LevelLimitedPieces
 */
public class CliLevelPiecesView extends CliLevelView {

    public CliLevelPiecesView(LevelLimitedPieces level) {
        super(level);
    }

    @Override
    public void drawEndHeader() {
        System.out.println("To win the level, you must erase all the pieces on the board");
    }

}
