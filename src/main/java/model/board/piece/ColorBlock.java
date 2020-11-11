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
     * Be careful, it doesn't check if the move is possible : even if there is no other ColorBlock of the same color
     * around, this will be deleted
     *
     * @param board The board
     * @param x     The x-coordinate of the Piece
     * @param y     The y-coordinate of the Piece
     * @return the number of points won
     */
    @Override
    public int delete(Board board, int x, int y) {
        int pointsWon = getPoints(); // add the points from this
        board.getBoard()[x][y] = null; // delete this from the board
        int[][] coordinates = new int[4][2];
        coordinates[0] = new int[]{x - 1, y};
        coordinates[1] = new int[]{x + 1, y};
        coordinates[2] = new int[]{x, y - 1};
        coordinates[3] = new int[]{x, y + 1};
        for (int i = 0; i < 4; i++) {
            if (board.isInsideBoard(coordinates[i][0], coordinates[i][1]) && !board.isEmpty(coordinates[i][0], coordinates[i][1])) {
                ColorBlock c = board.isAColorBlock(coordinates[i][0], coordinates[i][1]);
                if (c != null && c.getColor().equals(this.color)) { // test if it is a ColorBlock
                    if (c.isFree()) {
                        pointsWon += c.delete(board, coordinates[i][0], coordinates[i][1]);
                    } else {
                        c.setFree();
                    }
                } else { // check if the Piece is a Bee of the same color
                    Piece toTest = board.getBoard()[coordinates[i][0]][coordinates[i][1]];
                    if (toTest instanceof Bee && !toTest.isFree() && ((Bee) toTest).getColor().equals(this.color)) {
                        toTest.setFree(); // no points won from setting a Bee free
                        // Bees don't recursively set each other free : they have to be in direct contact with the Piece
                    } else if (toTest instanceof EraseColorBlocks && !toTest.isFree() && ((EraseColorBlocks) toTest).getColor().equals(this.color)) {
                        toTest.setFree(); // set free the EraseColorBlock
                    }
                }

            }
        }
        return pointsWon;
    }

    public String toString() {
        return color + " block";
    }

    public String charForCli() {
        if (isFree()) {
            return " " + color.toLowerCase().charAt(0) + "Blk ";
        } else {
            return " x" + color.toLowerCase().charAt(0) + "Blk";
        }
    }

}
