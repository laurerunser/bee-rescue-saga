package model.board.piece;

import model.board.Board;

public class EraseColorBlocks extends Piece implements PieceBonus {
    /** The color of the blocks this Piece deletes. Can be : yellow, red, green, blue, orange, purple */
    private final String color;

    private final static String iconPath = "";
    private final static String trappedIconPath = "";

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
                            System.out.println("ok");
                        }
                    }
                }
            }
        }
        return pointsWon * coefficient; // multiply by the coefficient of the EraseColorBlocks
    }
}
