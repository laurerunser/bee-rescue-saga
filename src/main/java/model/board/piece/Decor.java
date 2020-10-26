package model.board.piece;

import model.board.Board;

public class Decor extends Piece {
    private final static String iconPath = "";
    private final static String trappedIconPath = "";

    public Decor() {
        super(0, true);
    }

    /**
     * Does nothing because it can't be deleted
     *
     * @param board The board
     * @param x     The x-coordinate of the Piece
     * @param y     The y-coordinate of the Piece
     * @return 0 because the Piece can't be deleted (so no points won)
     */
    @Override
    public int delete(Board board, int x, int y) {
        return 0;
    }
}
