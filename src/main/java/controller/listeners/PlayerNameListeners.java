package controller.listeners;

import utils.ListOfListeners;

public class PlayerNameListeners extends ListOfListeners<PlayerNameListener> implements PlayerNameListener {
    private final Function1<PlayerNameListener, String> onPlayerName = new Function1<PlayerNameListener, String>() {
        @Override
        public void call(PlayerNameListener listener, String n) { listener.onPlayerName(n); }
    };

    @Override
    public void onPlayerName(String n) {
        callAllListeners(this.onPlayerName, n);
    }
}
