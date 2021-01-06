package model.board.piece;

import model.board.Board;

public class Decor extends Piece {
    private static final long serialVersionUID = 123L;

    private final String currentIconPath;

    /**
     * Constructs a Decor : can't be deleted so doesn't have points
     */
    public Decor() {
        super(0, true);
        currentIconPath = "pictures/Decor.png";
    }
    // TODO if several different pictures for Decor, do a random selection (like in ColorBlock)


    @Override
    public String getCurrentIconPath() {
        return currentIconPath;
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

    public String toString() {
        return "Decor";
    }

    public String charForCli() {
        return "___D__";
    }
}
