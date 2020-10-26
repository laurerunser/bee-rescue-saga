package model.board.piece;

import model.board.Board;

public class ColorBlock extends Piece {
    /** The color of the block : yellow, red, green, blue, orange, purple */
    private final String color;

    private final static String iconPath = "";
    private final static String trappedIconPath = "";

    /**
     * Constructs a ColorBlock
     * @param points    The number of points the Player gets for deleting the Piece
     * @param isFree    True if the Piece is free, false if it is trapped
     * @param color     The color of the block
     */
    public ColorBlock(int points, boolean isFree, String color) {
        super(points, isFree);
        this.color = color;
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Delete recursively the surrounding Pieces if they are ColorBlocks of the same color.
     *
     * @param board The board
     * @param x     The x-coordinate of the Piece
     * @param y     The y-coordinate of the Piece
     * @return the number of points won
     */
    @Override
    public int delete(Board board, int x, int y) {
        int pointsWon = 0;
        int[][] coordinates = new int[4][2];
        coordinates[0] = new int[]{x - 1, y};
        coordinates[1] = new int[]{x + 1, y};
        coordinates[2] = new int[]{x, y - 1};
        coordinates[3] = new int[]{x, y + 1};
        for (int i = 0; i < 4; i++) {
            if (board.isInsideBoard(coordinates[i][0], coordinates[i][1])
                    && !board.isEmpty(coordinates[i][0], coordinates[i][1])) {
                ColorBlock c = board.isAColorBlock(coordinates[i][0], coordinates[i][1]); // null if not a ColorBlock
                if (c != null && c.getColor().equals(this.color)) {
                    pointsWon += c.delete(board, coordinates[i][0], coordinates[i][1]);
                }
            }
        }
        pointsWon += getPoints(); // add the points from this
        board.getBoard()[x][y] = null; // delete this from the board
        return pointsWon;
    }

}
