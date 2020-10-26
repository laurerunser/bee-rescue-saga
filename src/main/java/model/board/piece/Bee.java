package model.board.piece;

import model.board.Board;


public class Bee extends Piece {
    private final static String iconPath = "";
    private final static String trappedIconPath = "";

    /**
     * Constructs a Bee
     *
     * @param points The number of points the Players gets when deleting (=saving) the Bee
     * @param isFree True if the Bee is free, false if it is trapped
     */
    public Bee(int points, boolean isFree) {
        super(points, isFree);
    }

    /**
     * A Bee can only be deleted when the Board updates itself and finds a Bee in the bottom row.
     * So this method does nothing
     *
     * @param board The board
     * @param x     The x-coordinate of the Bee
     * @param y     The y-coordinate of the Bee
     * @return the number of points won (here 0 because you can't delete the Bee through this method)
     */
    @Override
    public int delete(Board board, int x, int y) {
        return 0;
    }
}
