package controller;

import controller.listeners.PlayerMoveListeners;
import model.board.piece.ColorBlock;
import model.bonus.Bonus;
import model.level.Level;
import view.LevelView;

import java.util.Map;

public class LevelController {
    private final Level level;
    private final LevelView view;
    private final PlayerMoveListeners playerMoveListeners = new PlayerMoveListeners();

    public LevelController(Level level, LevelView view) {
        this.level = level;
        this.view = view;
    }

    /**
     * When a Piece is clicked, checks if a move is possible or not, executes it if it is.
     * Also update the view and launches the appropriate animations
     *
     * @param x The x-coordinate the Player chose
     * @param y The y-coordiante the Player chose
     */
    public void onPieceClicked(int x, int y) {
        if (level.getBoard().getBoard()[x][y] instanceof ColorBlock && !level.getBoard().isAColorMove(x, y)) {
            return;    //TODO : launch the view's animations for the different moves

        } else {
            //TODO : launch the view's animations for the different moves

            // TODO : in level update freeBonus replenishing
            int pointsWon = level.getBoard().delete(x, y);
            level.addToScore(pointsWon);
            view.updateScore();
            view.updateBoard();
            view.updateBees();
        }
    }

    /**
     * When a freeBonus is chosen by the use, applies it on the Board then updates the view and launches animations.
     * Also updates in Level the number of freeBonus available
     *
     * @param bonus The Bonus used
     * @param x     The x-coordinate chosen by the user
     * @param y     The y-coordinate chosen by the user
     */
    public void onFreeBonusUsed(Bonus bonus, int x, int y) {
        if (level.getFreeBonusConditions()[0] == 0 || bonus != level.getFreeBonus()) {
            return;    //TODO : launch the view's animations for the different moves

        } else {
            //TODO : launch the view's animations for the different moves

            //TODO : use the Bonus
            // TODO : in level freeBonus -1 && update replenishing
            int pointsWon = 0;
            level.addToScore(pointsWon);
            view.updateBoard();
            view.updateBees();
            view.updateFreeBonus();
            view.updateScore();
        }
    }

    /**
     * When the user uses a Bonus they possess : applies it on the Board and update the level and the view. Launches animations as necessary.
     *
     * @param bonus The Bonus used
     * @param x     The x-coordinate chosen by the user
     * @param y     The y-coordinate chosen by the user
     */
    public void onAvailableBonusUsed(Bonus bonus, int x, int y) {
        // test if the bonus is part of the available ones
        Map<Bonus, Integer> availableBonus = level.getAvailableBonus();
        if (availableBonus.getOrDefault(bonus, 0) == 0) {
            return;    //TODO : launch the view's animations for the different moves

        } else {
            //TODO : launch the view's animations for the different moves

            //TODO use the Bonus
            //TODO in level update available bonus + freeBonus replenishing -1
            int pointsWon = 0;
            level.addToScore(pointsWon);
            view.updateBees();
            view.updateBoard();
            view.updateScore();
            view.updateAvailableBonus();
        }
    }

    /**
     * When the player has won the Level
     */
    public void hasWon() {

    }

    /**
     * When the Player has lost the Level
     */
    public void hasLost() {

    }

}
