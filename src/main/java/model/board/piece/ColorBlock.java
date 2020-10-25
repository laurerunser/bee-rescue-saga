package model.board.piece;

import model.board.Board;

public class ColorBlock extends Piece {
    /**
     * The color of the block : yellow, red, green, blue, orange, purple
     */
    private final String color;

    public ColorBlock(String color, boolean free) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    /**
     * Delete recursively the surrounding Pieces if they are ColorBlocks of the same color.
     *
     * @param board The board
     * @param x     The x-coordinate of the Piece
     * @param y     The y-coordinate of the Piece
     */
    @Override
    public void delete(Board board, int x, int y) {
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
                    c.delete(board, coordinates[i][0], coordinates[i][1]);
                }
            }
        }
        board.getBoard()[x][y] = null;
    }

}
