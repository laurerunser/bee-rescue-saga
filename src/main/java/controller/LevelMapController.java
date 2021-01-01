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

import javax.swing.*;


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

    /**
     * The list of listeners to listen on the player's actions
     */
    private final MapNavigationListeners mapNavigationListeners;

    /**
     * The JFrame to pass on to the GUI views
     */
    private final JFrame frame;

    /**
     * Constructs a LevelMapController, the appropriate views and makes them visible
     *
     * @param player                 The player
     * @param gui                    True for GUI, false for CLI
     * @param mapNavigationListeners The listeners for the player's choices
     * @param frame                  The JFrame to pass on to the GUI views
     */
    public LevelMapController(Player player, boolean gui, MapNavigationListeners mapNavigationListeners, JFrame frame) {
        this.player = player;
        this.gui = gui;
        this.mapNavigationListeners = mapNavigationListeners;
        this.frame = frame;
        mapNavigationListeners.add(this);
        init();
    }

    /**
     * Creates the CLI or GUI view
     */
    public void init() {
        if (gui) {
            mapView = new GuiMapView(player, mapNavigationListeners, frame);
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
        if (mapView instanceof CliMapView) {
            if (canPlay) {
                mapNavigationListeners.onPlayLevel(i);
            } else {
                // TODO : implement
                System.out.println("Can't play the level yet !");
            }
        }
        // the GUI view launches the level itself

        //TODO : clean up and make the CLI also launch the level itself if possible
        // or better : don't let it ask the player if they want to play the level when they can't
    }

    @Override
    public void onGoBackToMap() {
        // fired from the MapNavigationListeners in the Views
        currentView = mapView;
        currentView.draw();
    }

    @Override
    public void onReturnToMap() {
        // fired from the LevelListeners in LevelController
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
                currentView = new GuiLevelView(toPlay, playerMovesListeners, frame);
            } else {
                currentView = new CliLevelView(toPlay, playerMovesListeners);
            }
            LevelListeners levelListeners = new LevelListeners();
            levelListeners.add(this);
            LevelController levelController = new LevelController(toPlay, (LevelView) currentView, levelListeners, playerMovesListeners);
            currentView.draw();
        }
    }

    /**
     * Tests if the player can play the level (= they have completed all the previous ones)
     *
     * @param n The number of the level
     * @return true if the level can be played, false otherwise
     */
    public boolean canPlay(int n) {
        return player.getMap().canPlay(n);
    }

    @Override
    public void onHasWon(int stars, int score, int level) {
        player.addToScore(score);
        player.addGold(10);
        player.getMap().hasWon(stars, score, level);
    }

    @Override
    public void onHasLost() {
        player.decreaseLives();
    }

    @Override
    public void onSave() {
        player.save();
        //TODO a screen/message that says everything has been saved correctly
        currentView.draw();
    }

}
