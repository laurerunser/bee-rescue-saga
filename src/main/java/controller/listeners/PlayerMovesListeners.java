package controller.listeners;

import model.bonus.Bonus;
import utils.ListOfListeners;

public class PlayerMovesListeners extends ListOfListeners<PlayerMovesListener> implements PlayerMovesListener {
    private final Function2<PlayerMovesListener, Integer, Integer> onPieceClicked = new Function2<PlayerMovesListener, Integer, Integer>() {
        @Override
        public void call(PlayerMovesListener listener, Integer x, Integer y) { listener.onPieceClicked(x, y); }
    };
    private final Function3<PlayerMovesListener, Bonus, Integer, Integer> onUseFreeBonus = new Function3<PlayerMovesListener, Bonus, Integer, Integer>() {
        @Override
        public void call(PlayerMovesListener listener, Bonus bonus, Integer x, Integer y) { listener.onUseFreeBonus(bonus, x, y); }
    };
    private final Function3<PlayerMovesListener, Character, Integer, Integer> onUseAvailableBonus = new Function3<PlayerMovesListener, Character, Integer, Integer>() {
        @Override
        public void call(PlayerMovesListener listener, Character bonus, Integer x, Integer y) { listener.onUseAvailableBonus(bonus, x, y); }
    };

    @Override
    public void onPieceClicked(int x, int y) {
        callAllListeners(this.onPieceClicked, x, y);
    }

    @Override
    public void onUseFreeBonus(Bonus bonus, int x, int y) {
        callAllListeners(this.onUseFreeBonus, bonus, x, y);
    }

    @Override
    public void onUseAvailableBonus(char bonus, int x, int y) {
        callAllListeners(this.onUseAvailableBonus, bonus, x, y);
    }
}
