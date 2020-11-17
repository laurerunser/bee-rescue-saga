package controller;

import model.bonus.Bonus;
import model.level.LevelLimitedPieces;
import view.LevelView;

public class LevelPiecesController extends LevelController {
    LevelLimitedPieces level;

    public LevelPiecesController(LevelLimitedPieces level, LevelView view) {
        super(level, view);
        this.level = level;
    }

    @Override
    public void onPieceClicked(int x, int y) {
        if (level.getBoard().isAValidMove(x, y)) {
            super.onPieceClicked(x, y);
            level.updateBoard();
            testHasWon();
        }
    }

    @Override
    public void onFreeBonusUsed(Bonus bonus, int x, int y) {
        super.onFreeBonusUsed(bonus, x, y);
        level.updateBoard();
        testHasWon();
    }

    @Override
    public void onAvailableBonusUsed(Bonus bonus, int x, int y) {
        super.onAvailableBonusUsed(bonus, x, y);
        level.updateBoard();
        testHasWon();
    }

    /**
     * Tests if the Player has won or lost the Level. If so, launches the appropriate actions.
     * If not, does nothing.
     */
    public void testHasWon() {
        if (level.hasWon()) {
            hasWon();
        } else if (level.hasLost()) {
            hasLost();
        }
    }
}
