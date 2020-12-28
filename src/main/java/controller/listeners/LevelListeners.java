package controller.listeners;

import utils.ListOfListeners;

public class LevelListeners extends ListOfListeners<LevelListener> implements LevelListener {
    private final Function<LevelListener> onReturnToMap = new Function<LevelListener>() {
        @Override
        public void call(LevelListener listener) { listener.onReturnToMap(); }
    };
    private final Function3<LevelListener, Integer, Integer, Integer> onHasWon = new Function3<LevelListener, Integer, Integer, Integer>() {
        @Override
        public void call(LevelListener listener, Integer stars, Integer score, Integer level) { listener.onHasWon(stars, score, level); }
    };

    private final Function<LevelListener> onHasLost = new Function<LevelListener>() {
        @Override
        public void call(LevelListener listener) { listener.onHasLost(); }
    };

    @Override
    public void onHasWon(int stars, int score, int level) {
        callAllListeners(this.onHasWon, stars, score, level);
    }

    @Override
    public void onHasLost() {
        callAllListeners(this.onHasLost);
    }

    public void onReturnToMap() {
        callAllListeners(this.onReturnToMap);
    }
}
