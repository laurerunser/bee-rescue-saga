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
     * Plays the Level
     *
     * @return true if won, false if lost
     */
    public boolean play() {


        return true;
    }

    public void onPieceClicked(int x, int y) {
        if (level.getBoard().getBoard()[x][y] instanceof ColorBlock && !level.getBoard().isAColorMove(x, y)) {
            return;    //TODO : launch the view's animations for the different moves

        } else {
            //TODO : launch the view's animations for the different moves

            int pointsWon = level.getBoard().delete(x, y);
            level.addToScore(pointsWon);
            view.drawLevel();
        }
    }

    public void onFreeBonusUsed(Bonus bonus, int x, int y) {
        if (level.getFreeBonusConditions()[0] == 0 || bonus != level.getFreeBonus()) {
            return;    //TODO : launch the view's animations for the different moves

        } else {
            //TODO : launch the view's animations for the different moves

            //TODO : use the Bonus
            int pointsWon = 0;
            level.addToScore(pointsWon);
            view.drawLevel();
        }
    }

    public void onAvailableBonusUsed(Bonus bonus, int x, int y) {
        // test if the bonus is part of the available ones
        Map<Bonus, Integer> availableBonus = level.getAvailableBonus();
        if (availableBonus.getOrDefault(bonus, 0) == 0) {
            return;    //TODO : launch the view's animations for the different moves

        } else {
            //TODO : launch the view's animations for the different moves

            //TODO use the Bonus
            int pointsWon = 0;
            level.addToScore(pointsWon);
            view.drawLevel();
        }
    }

}
