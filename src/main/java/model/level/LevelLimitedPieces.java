package model.level;

import model.board.Board;
import model.board.NewPieceBuilder;
import model.bonus.Bonus;

import java.util.Map;

public class LevelLimitedPieces extends Level {
    /**
     * The number of Pieces on that Level excluding Bees
     */
    private int nbPieces;

    /**
     * Constructs a Level with a limited amount of pieces
     *
     * @param level                   The number of the Level
     * @param board                   The initial Board
     * @param availableBonus          The number and types of available bonus
     * @param freeBonus               The free Bonus
     * @param nbFreeBonus             The number of freeBonus available. -1 is infinite.
     * @param movesReplenishFreeBonus The number of moves necessary to replenish a freeBonus. 0 is can't be replenished, -1 is immediately accessible.
     * @param builder                 The builder to make new Pieces
     * @param objBees                 The number of Bees necessary to win the level
     * @param objScore                An int array of size 3 that contains the amounts of points to get 1, 2 or 3 stars
     * @param nbPieces                The number of Pieces available in the Level
     */
    public LevelLimitedPieces(int level, Board board, Map<Bonus, Integer> availableBonus, Bonus freeBonus, int nbFreeBonus, int movesReplenishFreeBonus, NewPieceBuilder builder, int objBees, int[] objScore, int nbPieces) {
        super(level, board, availableBonus, freeBonus, nbFreeBonus, movesReplenishFreeBonus, builder, objBees, objScore);
        this.nbPieces = nbPieces;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasWon() {
        return allBeesSaved() && nbPieces >= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasLost() {
        if (freeBonus != null) return true; // you can always play the free Bonus
        return !board.hasMoveLeft();
    }

    /** Only updates the board, without filling the empty spaces */
    @Override
    public void updateBoard() {
        updateBoardNoFill();
    }
}
