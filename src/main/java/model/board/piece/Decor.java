package model.board.piece;

import model.board.Board;

public class Decor extends Piece {

    /**
     * Does nothing because it can't be deleted
     *
     * @param board The board
     * @param x     The x-coordinate of the Piece
     * @param y     The y-coordinate of the Piece
     */
    @Override
    public void delete(Board board, int x, int y) {

    }
}
