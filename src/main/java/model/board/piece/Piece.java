package model.board.piece;

import model.board.Board;

import java.io.Serializable;

//TODO : fill all the iconPath and trappedIconPath
public abstract class Piece implements Serializable {
    private static final long serialVersionUID = 123L;

    /**
     * The path to the icon
     */
    private String iconPath = "";

    /**
     * The path to the icon when the Piece is trapped/not free
     */
    private String trappedIconPath = "";

    /**
     * The path to the current icon
     */
    private String currentIconPath;

    /**
     * The number of points the Player gets hen deleting the Piece, without the multiplicators or extra points
     */
    private final int points;
    /**
     * True if the Piece is free, false otherwise
     */
    private boolean isFree;

    /**
     * Constructs a Piece
     *
     * @param points The number of points the Player gets when deleting the Piece
     * @param isFree True if the Piece is free, false if it is trapped
     */
    public Piece(int points, boolean isFree) {
        this.points = points;
        this.isFree = isFree;
        if (isFree) {
            currentIconPath = iconPath;
        } else {
            currentIconPath = trappedIconPath;
        }
    }

    /** Sets the Piece free. Changes the currentIconPath to the default iconPath. */
    public void setFree() {
        isFree = true;
        currentIconPath = iconPath;
    }

    /** @return true if the Piece is free, false otherwise */
    public boolean isFree() {
        return isFree;
    }

    /** @return the number of points the Player gets when deleting the Piece */
    public int getPoints() {
        return points;
    }

    /**
     * Deletes the Piece from the Board if possible.
     * Delete the surrounding Pieces from the board if needed.
     *
     * @param board The board
     * @param x     The x-coordinate of the Piece
     * @param y     The y-coordinate of the Piece
     * @return the number of points won
     */
    public abstract int delete(Board board, int x, int y);

    public abstract String toString();

    /**
     * @return a String of length 6 that represents the Piece
     */
    public abstract String charForCli();

    public String getIconPath() { return iconPath; }

    public void setIconPath(String path) { iconPath = path; }

    public String getTrappedIconPath() { return trappedIconPath; }

    public void setTrappedIconPath(String path) { trappedIconPath = path; }

    public String getCurrentIconPath() { return currentIconPath; }

    public void setCurrentIconPath(String path) { currentIconPath = path; }


}
