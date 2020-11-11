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
        super.onPieceClicked(x, y);
        level.updateBoard();
    }

    @Override
    public void onFreeBonusUsed(Bonus bonus, int x, int y) {
        super.onFreeBonusUsed(bonus, x, y);
        level.updateBoard();
    }

    @Override
    public void onAvailableBonusUsed(Bonus bonus, int x, int y) {
        super.onAvailableBonusUsed(bonus, x, y);
        level.updateBoard();
    }
}