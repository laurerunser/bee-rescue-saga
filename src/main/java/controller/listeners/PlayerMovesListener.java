package controller.listeners;

import model.bonus.Bonus;

public interface PlayerMovesListener {
    void onPieceClicked(int x, int y);

    void onUseFreeBonus(Bonus bonus, int x, int y);

    void onUseAvailableBonus(char bonus, int x, int y);


}
