package controller;

import controller.listeners.*;
import model.Player;
import model.level.Level;
import view.LevelView;
import view.MapView;
import view.Vue;
import view.cli.CliLevelView;
import view.cli.CliMapView;
import view.gui.GuiLevelView;
import view.gui.GuiMapView;


public class LevelMapController implements MapNavigationListener, LevelListener {
    /**
     * The current player
     */
    private final Player player;
    /**
     * True if the views should be GUI, false for CLI
     */
    private final boolean gui;

    /**
     * The current view
     */
    private Vue currentView;

    /**
     * The MapView
     */
    private MapView mapView;

    private final MapNavigationListeners mapNavigationListeners;

    /**
     * Constructs a LevelMapController, the appropriate views and makes them visible
     *
     * @param player The player
     * @param gui    True for GUI, false for CLI
     */
    public LevelMapController(Player player, boolean gui, MapNavigationListeners mapNavigationListeners) {
        this.player = player;
        this.gui = gui;
        this.mapNavigationListeners = mapNavigationListeners;
        mapNavigationListeners.add(this);
        init();
    }

    public void init() {
        if (gui) {
            mapView = new GuiMapView(player, mapNavigationListeners);
        } else {
            mapView = new CliMapView(player, mapNavigationListeners);
        }
        currentView = mapView;
        currentView.draw();
    }

    @Override
    public void onShowLevelDetails(int i) {
        boolean canPlay = canPlay(i);
        mapView.showLevelDetails(i);
        if (canPlay) {
            mapNavigationListeners.onPlayLevel(i);
        } else {
            // TODO : implement
            System.out.println("Can't play the level yet !");
        }
    }

    public void onGoBackToMap() {
        currentView = mapView;
        currentView.draw();
    }

    public void onGoToShop() {
        // TODO : switch current view to ShopView
    }

    public void onGoToRaffle() {
        // TODO : switch current view to RaffleView
    }

    /**
     * If the Player can play, switches the view to a LevelView of the correct Level, then draws the view.
     * Otherwise ??
     *
     * @param n The number of the Level to play
     */
    public void onPlayLevel(int n) {
        Level toPlay = player.getMap().getLevels()[n];
        if (player.getNbLives() < 0 || n > player.getMap().getLastLevelVisible()) { // can't play
            // todo define what to do otherwise
        } else { // can play
            PlayerMovesListeners playerMovesListeners = new PlayerMovesListeners();
            if (gui) {
                currentView = new GuiLevelView(toPlay, playerMovesListeners);
            } else {
                currentView = new CliLevelView(toPlay, playerMovesListeners);
            }
            LevelListeners levelListeners = new LevelListeners();
            levelListeners.add(this);
            levelListeners.add(player.getMap());
            LevelController levelController = new LevelController(toPlay, (LevelView) currentView, levelListeners, playerMovesListeners);
            currentView.draw();
        }
    }

    /**
     * Tests if the player can play and parses the level if they can
     *
     * @param n The number of the level
     */
    private boolean canPlay(int n) {
        return n - 1 < 0 || player.getMap().getLevelsCompleted()[n - 1][0] != 0;
    }

    @Override
    public void onHasWon(int stars, int score, int level) {
        player.addToScore(score);
        player.addGold(10);
    }

    public void onHasLost() {
        player.decreaseLives();
    }

}
