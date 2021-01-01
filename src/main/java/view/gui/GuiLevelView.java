package view.gui;

import controller.listeners.PlayerMovesListeners;
import model.level.Level;
import view.LevelView;

import javax.swing.*;

public class GuiLevelView extends JPanel implements LevelView {

    /**
     * Constructs the view of the level
     *
     * @param level                The level to display
     * @param playerMovesListeners The list of listeners to listen on the player's actions
     * @param frame                The JFrame of the app
     */
    public GuiLevelView(Level level, PlayerMovesListeners playerMovesListeners, JFrame frame) {

    }

    @Override
    public void drawLevel() {

    }

    @Override
    public void updateBoard() {

    }

    @Override
    public void updateScore() {

    }

    @Override
    public void updateBees() {

    }

    @Override
    public void updateAvailableBonus() {

    }

    @Override
    public void updateFreeBonus() {

    }
}
