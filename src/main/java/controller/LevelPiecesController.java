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
