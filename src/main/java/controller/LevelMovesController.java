package controller;

import model.bonus.Bonus;
import model.level.LevelLimitedMoves;
import view.LevelView;

public class LevelMovesController extends LevelController {
    LevelLimitedMoves level;

    public LevelMovesController(LevelLimitedMoves level, LevelView view) {
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
    public void onUseFreeBonus(Bonus bonus, int x, int y) {
        super.onUseFreeBonus(bonus, x, y);
        level.updateBoard();
        testHasWon();
    }

    @Override
    public void onUseAvailableBonus(char bonus, int x, int y) {
        super.onUseAvailableBonus(bonus, x, y);
        level.updateBoard();
        testHasWon();
    }

    @Override
    public void testHasWon() {
        if (level.hasWon()) {
            hasWon();
        } else if (level.hasLost()) {
            hasLost();
        }
    }
}