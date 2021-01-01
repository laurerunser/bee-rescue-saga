package model.level;

import model.board.Board;
import model.board.NewPieceBuilder;
import model.bonus.Bonus;

import java.util.HashMap;

public class LevelLimitedMoves extends Level {
    private static final long serialVersionUID = 123L;

    /**
     * The number of moves available to the Player
     */
    private int moves;
    /**
     * The amount of points the Player gets for each move left at the end
     */
    private int pointsPerMoveLeft;

    public static String goal = "You need to save the Bees before you run out of moves !";


    /**
     * Constructs a Level with a limited amount of moves
     *
     * @param level                   The number of the Level
     * @param board                   The initial Board
     * @param availableBonus          The number and types of available bonus
     * @param freeBonus               The free Bonus
     * @param nbFreeBonus             The number of freeBonus available. -1 is infinite.
     * @param movesReplenishFreeBonus The number of moves necessary to replenish a freeBonus. 0 is can't be replenished, -1 is immediately accessible.     * @param builder           The builder to make new Pieces
     * @param objBees                 The number of Bees necessary to win the level
     * @param objScore                An int array of size 3 that contains the amounts of points to get 1, 2 or 3 stars
     * @param moves                   The number of moves available to play this Level
     * @param pointsPerMoveLeft       The number of points the Player gets for each move left when they have won
     */
    public LevelLimitedMoves(int level, Board board, HashMap<Bonus, Integer> availableBonus, Bonus freeBonus, int nbFreeBonus, int movesReplenishFreeBonus, NewPieceBuilder builder, int objBees, int[] objScore, int moves, int pointsPerMoveLeft) {
        super(level, board, availableBonus, freeBonus, nbFreeBonus, movesReplenishFreeBonus, builder, objBees, objScore);
        this.moves = moves;
        this.pointsPerMoveLeft = pointsPerMoveLeft;
    }

    public int getMoves() { return moves; }

    /**
     * {@inheritDoc}
     * Updates the Board and decreases the amount of moves left.
     */
    @Override
    public void updateBoard() {
        super.updateBoard();
        moves -= 1;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasWon() {
        return (moves >= 0) && allBeesSaved();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasLost() {
        return !allBeesSaved() && (moves == 0);
    }


}
