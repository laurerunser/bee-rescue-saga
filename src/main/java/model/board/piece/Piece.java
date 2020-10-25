package model.board.piece;

import model.board.Board;

public abstract class Piece {
    private String iconPath;
    private String trappedIconPath;
    private int points;
    private boolean isFree;

    public void setFree() {
        isFree = true;
    }

    public boolean isFree() {
        return isFree;
    }

    /**
     * Delete the surrounding Pieces from the board if needed
     *
     * @param board The board
     * @param x     The x-coordinate of the Piece
     * @param y     The y-coordinate of the Piece
     */
    public abstract void delete(Board board, int x, int y);


    // TODO: fix the constructors

}
