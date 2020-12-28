package view.gui;

import controller.listeners.MapNavigationListeners;
import model.Player;
import view.MapView;

public class GuiMapView implements MapView {
    private Player player;
    private MapNavigationListeners mapNavigationListeners;

    public GuiMapView(Player p, MapNavigationListeners mapNavigationListeners) {
        this.player = p;
        this.mapNavigationListeners = mapNavigationListeners;
    }

    @Override
    public void drawMap() {

    }

    @Override
    public void showLevelDetails(int n) {

    }

}
