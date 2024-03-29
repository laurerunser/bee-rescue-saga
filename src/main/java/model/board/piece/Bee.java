package model.board.piece;

import model.board.Board;


public class Bee extends Piece {
    private static final long serialVersionUID = 123L;

    /**
     * The color of the box the Bee can be trapped in : yellow, red, green, blue, orange, purple
     * If the Bee is not trapped, color = "" (empty String)
     */
    private final String color;

    private final String freeIconPath;
    private String currentIconPath;

    /**
     * Constructs a Bee that is trapped in a box of that color
     *
     * @param points The number of points the Players gets when deleting (=saving) the Bee
     * @param color  The color of the box the bee is trapped in
     */
    public Bee(int points, String color) {
        super(points, false);
        this.color = color;

        freeIconPath = "pictures/Bee-happy.png";
        currentIconPath = "pictures/Bee-happy-" + color.charAt(0) + ".png";
    }

    /**
     * Constructs a free Bee
     *
     * @param points The number of points the Player gets when deleting (=saving) the Bee
     */
    public Bee(int points) {
        super(points, true);
        color = "";
        freeIconPath = "pictures/Bee-happy.png";
        currentIconPath = freeIconPath;
    }

    @Override
    public void setFree() {
        super.setFree();
        currentIconPath = freeIconPath;
    }

    @Override
    public String getCurrentIconPath() {
        return currentIconPath;
    }

    /**
     * @return the color of the box the Bee is trapped in
     */
    public String getColor() { return color; }

    /**
     * A Bee can only be deleted when the Board updates itself and finds a Bee in the bottom row.
     * So this method does nothing
     *
     * @param board The board
     * @param x     The x-coordinate of the Bee
     * @param y     The y-coordinate of the Bee
     * @return the number of points won (here 0 because you can't delete the Bee through this method)
     */
    @Override
    public int delete(Board board, int x, int y) {
        return 0;
    }

    public String toString() {
        return "Bee";
    }

    public String charForCli() {
        if (isFree()) {
            return "___B__";
        } else {
            return "_x_" + color.toLowerCase().charAt(0) + "B_";
        }
    }
}
