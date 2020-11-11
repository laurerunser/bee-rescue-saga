package controller.listeners;

import model.bonus.Bonus;
import utils.ListOfListeners;

public class PlayerMoveListeners extends ListOfListeners<PlayerMoveListener> implements PlayerMoveListener {

    private final Function2<PlayerMoveListener, Integer, Integer> onPieceClicked = new Function2<PlayerMoveListener, Integer, Integer>() {
        @Override
        public void call(PlayerMoveListener listener, Integer x, Integer y) { listener.onPieceClicked(x, y); }
    };
    private final Function3<PlayerMoveListener, Bonus, Integer, Integer> onFreeBonusUsed = new Function3<PlayerMoveListener, Bonus, Integer, Integer>() {
        @Override
        public void call(PlayerMoveListener listener, Bonus bonus, Integer x, Integer y) { listener.onAvailableBonusUsed(bonus, x, y); }
    };
    private final Function3<PlayerMoveListener, Bonus, Integer, Integer> onAvailableBonusUsed = new Function3<PlayerMoveListener, Bonus, Integer, Integer>() {
        @Override
        public void call(PlayerMoveListener listener, Bonus bonus, Integer x, Integer y) { listener.onAvailableBonusUsed(bonus, x, y); }
    };

    @Override
    public void onPieceClicked(int x, int y) {
        callAllListeners(this.onPieceClicked, x, y);
    }

    @Override
    public void onFreeBonusUsed(Bonus bonus, int x, int y) {
        callAllListeners(this.onFreeBonusUsed, bonus, x, y);
    }

    @Override
    public void onAvailableBonusUsed(Bonus bonus, int x, int y) {
        callAllListeners(this.onAvailableBonusUsed, bonus, x, y);
    }


}
