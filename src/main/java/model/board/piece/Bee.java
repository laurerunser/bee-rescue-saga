package model.board.piece;

import model.board.Board;

public class Bee extends Piece {

    /**
     * A Bee can only be deleted when the Board updates itself and finds a Bee in the bottom row.
     * So this method does nothing
     *
     * @param board The board
     * @param x     The x-coordinate of the Bee
     * @param y     The y-coordinate of the Bee
     */
    @Override
    public void delete(Board board, int x, int y) {
    }
}
