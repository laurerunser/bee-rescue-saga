package model.board.piece;

import model.board.Board;

public class EraseColorBlocks extends Piece {
    private static final long serialVersionUID = 123L;

    /**
     * The color of the blocks this Piece deletes. Can be : yellow, red, green, blue, orange, purple
     */
    private final String color;

    /**
     * Constructs a EraseColorBlocks
     *
     * @param points The coefficient applied to the points the Player gets from deleting the ColorBlocks
     * @param isFree True if the Piece is free, false if it is trapped
     * @param color  The color of ColorBlocks that this Piece deletes
     */
    public EraseColorBlocks(int points, boolean isFree, String color) {
        super(points, isFree);
        this.color = color;
        setCurrentIconPath("pictures/EraseColor" + color.charAt(0) + ".png");
        setTrappedIconPath("pictures/tEraseColor" + color.charAt(0) + ".png");
        if (isFree) {
            setCurrentIconPath(getIconPath());
        } else {
            setCurrentIconPath(getTrappedIconPath());
        }
    }

    public String getColor() { return color; }

    /**
     * Delete the surrounding Pieces from the board if needed
     *
     * @param board The board
     * @param x     The x-coordinate of the Piece
     * @param y     The y-coordinate of the Piece
     */
    @Override
    public int delete(Board board, int x, int y) {
        int coefficient = getPoints();
        board.getBoard()[x][y] = null; // delete this from the board
        int pointsWon = 0;
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (board.isInsideBoard(i, j) && !board.isEmpty(i, j)) {
                    ColorBlock c = board.isAColorBlock(i, j); // null if not a ColorBlock
                    if (c != null && c.getColor().equals(this.color)) {
                        if (c.isFree()) {
                            pointsWon += c.delete(board, i, j);
                        } else {
                            c.setFree();
                        }
                    }
                }
            }
        }
        return pointsWon * coefficient; // multiply by the coefficient of the EraseColorBlocks
    }

    public String toString() {
        return color + " color balloun : pop it to delete all the color blocks of that color";
    }

    public String charForCli() {
        if (isFree()) {
            return "_" + color.toLowerCase().charAt(0) + "ECB_";
        } else {
            return "x" + color.toLowerCase().charAt(0) + "ECB_";
        }
    }
}
