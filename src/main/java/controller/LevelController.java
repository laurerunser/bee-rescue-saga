package controller;

import controller.listeners.LevelListeners;
import controller.listeners.PlayerMovesListener;
import controller.listeners.PlayerMovesListeners;
import model.board.piece.ColorBlock;
import model.bonus.*;
import model.level.Level;
import view.LevelView;
import view.cli.CliLevelView;

import java.util.Map;

public class LevelController implements PlayerMovesListener {
    protected final Level level;
    protected final LevelView view;

    private final LevelListeners levelListeners;

    public LevelController(Level level, LevelView view, LevelListeners levelListeners, PlayerMovesListeners playerMovesListeners) {
        this.level = level;
        this.view = view;
        this.levelListeners = levelListeners;
        playerMovesListeners.add(this);
    }

    /**
     * When a Piece is clicked, checks if a move is possible or not, executes it if it is.
     * Also update the view and launches the appropriate animations
     *
     * @param x The x-coordinate the Player chose
     * @param y The y-coordinate the Player chose
     */
    public void onPieceClicked(int x, int y) {
        if (level.getBoard().getBoard()[x][y] instanceof ColorBlock && !level.getBoard().isAColorMove(x, y)) {
            return;    //TODO : launch the view's animations for the different moves
        } else {
            //TODO : launch the view's animations for the different moves
            int pointsWon = level.delete(x, y);
            level.addToScore(pointsWon);
            view.updateScore();
            view.updateBoard();
            view.updateBees();
        }
        testHasWon();
    }

    /**
     * When a freeBonus is chosen by the use, applies it on the Board then updates the view and launches animations.
     * Also updates in Level the number of freeBonus available
     *
     * @param bonus The Bonus used
     * @param x     The x-coordinate chosen by the user
     * @param y     The y-coordinate chosen by the user
     */
    public void onUseFreeBonus(Bonus bonus, int x, int y) {
        if (level.getFreeBonusConditions()[0] == 0 || level.getFreeBonusConditions()[1] != 0 || bonus != level.getFreeBonus()) {
            // can't use the bonus
            if (view instanceof CliLevelView) {
                ((CliLevelView) view).noFreeBonus();
            }
            // TODO animations
        } else {
            // TODO : launch the view's animations for the different moves
            // TODO : if a ChangeColorBlock : set the colorTo attribute
            int pointsWon = level.useBonus(bonus, x, y);
            if (pointsWon != -1) { // the Bonus can be used
                level.updateBoard();
                level.resetFreeBonusMoves();
                level.addToScore(pointsWon);
                view.updateBoard();
                view.updateBees();
                view.updateFreeBonus();
                view.updateScore();
            }
        }
        testHasWon();
    }

    /**
     * When the user uses a Bonus they possess : applies it on the Board and update the level and the view. Launches animations as necessary.
     *
     * @param b The char corresponding to the Bonus (see <code>getBonusFromChar(char b)</code>)
     * @param x The x-coordinate chosen by the user
     * @param y The y-coordinate chosen by the user
     */
    public void onUseAvailableBonus(char b, int x, int y) {
        Bonus bonus = getBonusFromChar(b);
        // test if the bonus is part of the available ones
        Map<Bonus, Integer> availableBonus = level.getAvailableBonus();
        if (availableBonus.getOrDefault(bonus, 0) == 0) {
            if (view instanceof CliLevelView) {
                ((CliLevelView) view).noAvailableBonus();
            }
            return;
        } else {
            //TODO : launch the view's animations for the different moves
            int pointsWon = level.useBonus(bonus, x, y);
            if (pointsWon == -1) return; // the bonus can't be used
            level.addToScore(pointsWon);
            view.updateBees();
            view.updateBoard();
            view.updateScore();
            view.updateAvailableBonus();
        }
        testHasWon();
    }

    @Override
    public void onReturnToMap() {
        levelListeners.onReturnToMap();
    }

    /**
     * Tests if the Player has won or lost the Level. If so, launches the appropriate actions.
     * If the Player has neither won nor lost, does nothing.
     */
    public void testHasWon() {
        if (level.hasWon()) {
            levelListeners.onHasWon(level.getStars(), level.getScore(), level.getLevel());
        } else if (level.hasLost()) {
            levelListeners.onHasLost();
        }
    }

    /**
     * Finds the Bonus corresponding to the char in the availableBonus map
     *
     * @param b The char to look up
     * @return the corresponding Bonus if it is in the Map and the Player has at least one of it, otherwise null
     */
    private Bonus getBonusFromChar(char b) {
        Map<Bonus, Integer> availableBonus = level.getAvailableBonus();
        for (Map.Entry<Bonus, Integer> m : availableBonus.entrySet()) {
            if ((m.getKey() instanceof ChangeBlockColor && b == 'C' && m.getValue() > 0)
                    || (m.getKey() instanceof EraseBlock && b == 'B' && m.getValue() > 0)
                    || (m.getKey() instanceof EraseColor && b == 'A' && m.getValue() > 0)
                    || (m.getKey() instanceof EraseColumn && b == 'D' && m.getValue() > 0)
                    || (m.getKey() instanceof FreeBee && b == 'F' && m.getValue() > 0)
                    || (m.getKey() instanceof FreeBlock && b == 'K' && m.getValue() > 0)
            ) return m.getKey();
        }
        return null;
    }

}
