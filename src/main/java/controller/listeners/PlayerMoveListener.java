package controller.listeners;

import model.bonus.Bonus;

public interface PlayerMoveListener {
    void onPieceClicked(int x, int y);

    void onFreeBonusUsed(Bonus bonus, int x, int y);

    void onAvailableBonusUsed(Bonus bonus, int x, int y);
}
