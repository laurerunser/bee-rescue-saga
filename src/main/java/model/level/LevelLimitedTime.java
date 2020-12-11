package model.level;

import model.board.Board;
import model.board.NewPieceBuilder;
import model.bonus.Bonus;

import java.util.HashMap;

public class LevelLimitedTime extends Level {
    /**
     * The number of seconds available to do the Level
     */
    private int seconds;
    /**
     * The number of points the Player gets per second left at the end
     */
    private int pointsPerSecondLeft;

    /**
     * Constructs a Level with a limited amount of time
     *
     * @param level                   The number of the Level
     * @param board                   The initial Board
     * @param availableBonus          The number and types of available bonus
     * @param freeBonus               The free Bonus
     * @param nbFreeBonus             The number of freeBonus available. -1 is infinite.
     * @param movesReplenishFreeBonus The number of moves necessary to replenish a freeBonus. 0 is can't be replenished, -1 is immediately accessible.     * @param builder             The builder to make new Pieces
     * @param objBees                 The number of Bees necessary to win the level
     * @param objScore                An int array of size 3 that contains the amounts of points to get 1, 2 or 3 stars
     * @param seconds                 The number of seconds available to play the Level
     * @param pointsPerSecondLeft     The amount of points the Player gets for each second left when they have won the Level
     */
    public LevelLimitedTime(int level, Board board, HashMap<Bonus, Integer> availableBonus, Bonus freeBonus, int nbFreeBonus,
                            int movesReplenishFreeBonus, NewPieceBuilder builder, int objBees, int[] objScore, int seconds,
                            int pointsPerSecondLeft) {
        super(level, board, availableBonus, freeBonus, nbFreeBonus, movesReplenishFreeBonus, builder, objBees, objScore);
        this.seconds = seconds;
        this.pointsPerSecondLeft = pointsPerSecondLeft;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasWon() {
        return allBeesSaved() && (seconds >= 0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasLost() {
        return !allBeesSaved() && (seconds == 0);
    }

}
