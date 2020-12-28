package controller.listeners;

import utils.ListOfListeners;

public class MapNavigationListeners extends ListOfListeners<MapNavigationListener> implements MapNavigationListener {

    private final Function<MapNavigationListener> onSave = new Function<MapNavigationListener>() {
        @Override
        public void call(MapNavigationListener listener) { listener.onSave(); }
    };

    private final Function1<MapNavigationListener, Integer> onShowLevelDetails = new Function1<MapNavigationListener, Integer>() {
        @Override
        public void call(MapNavigationListener listener, Integer i) { listener.onShowLevelDetails(i); }
    };

    private final Function<MapNavigationListener> onGoBackToMap = new Function<MapNavigationListener>() {
        @Override
        public void call(MapNavigationListener listener) { listener.onGoBackToMap(); }
    };

    private final Function<MapNavigationListener> onGoToShop = new Function<MapNavigationListener>() {
        @Override
        public void call(MapNavigationListener listener) { listener.onGoToShop(); }
    };

    private final Function<MapNavigationListener> onGoToRaffle = new Function<MapNavigationListener>() {
        @Override
        public void call(MapNavigationListener listener) { listener.onGoToRaffle(); }
    };

    private final Function1<MapNavigationListener, Integer> onPlayLevel = new Function1<MapNavigationListener, Integer>() {
        @Override
        public void call(MapNavigationListener listener, Integer i) { listener.onPlayLevel(i); }
    };

    @Override
    public void onShowLevelDetails(int i) {
        callAllListeners(this.onShowLevelDetails, i);
    }

    @Override
    public void onGoBackToMap() {
        callAllListeners(this.onGoBackToMap);
    }

    @Override
    public void onGoToShop() {
        callAllListeners(this.onGoToShop);
    }

    @Override
    public void onGoToRaffle() {
        callAllListeners(this.onGoToRaffle);
    }

    @Override
    public void onPlayLevel(int n) {
        callAllListeners(this.onPlayLevel, n);
    }

    @Override
    public void onSave() {
        callAllListeners(this.onSave);
    }
}
