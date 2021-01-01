package model.board.piece;

import model.board.Board;

public class ColorBlock extends Piece {
    private static final long serialVersionUID = 123L;

    /**
     * The color of the block : yellow, red, green, blue, orange, purple
     */
    private String color;

    /**
     * Constructs a ColorBlock
     *
     * @param points The number of points the Player gets for deleting the Piece
     * @param isFree True if the Piece is free, false if it is trapped
     * @param color  The color of the block
     */
    public ColorBlock(int points, boolean isFree, String color) {
        super(points, isFree);
        this.color = color;
        setIconPath("pictures/" + color.charAt(0) + random() + ".png");
        setTrappedIconPath("pictures/t" + color.charAt(0) + random() + ".png");
        if (isFree) {
            setCurrentIconPath(getIconPath());
        } else {
            setCurrentIconPath(getTrappedIconPath());
        }
    }

    /**
     * Returns a number between 1 and 3 (both included)
     *
     * @return an int between 1 and 3 (both included)
     */
    private String random() {
        int a = (int) (Math.random() * (4 - 1 + 1) + 1);
        return String.valueOf(a);
    }

    /**
     * @return the color
     */
    public String getColor() {
        return color;
    }

    /**
     * Sets the color to the new value
     *
     * @param color The new color
     */
    public void setColor(String color) {
        this.color = color;
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
            return "_" + color.toLowerCase().charAt(0) + "Blk_";
        } else {
            return "_x" + color.toLowerCase().charAt(0) + "Blk";
        }
    }


}
