package model.bonus;

import model.board.Board;

import java.io.Serializable;

public abstract class Bonus implements Serializable {
    /**
     * If the name of the Bonus is in the array, then the Bonus is unlocked for the Player
     */
    protected static String[] unlocked = new String[6];
    /**
     * the next free place in the unlocked array
     */
    protected static int libre;
    protected String iconPath;
    protected int basePoints;

    /**
     * Constructs a Bonus
     *
     * @param iconPath   The path to the icon
     * @param basePoints The number of points
     */
    public Bonus(String iconPath, int basePoints) {
        this.iconPath = iconPath;
        this.basePoints = basePoints;
    }

    /**
     * Use the Bonus on the Board
     *
     * @param b The board
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return -1 if the Bonus can't be used, otherwise the number of points won
     */
    public abstract int use(Board b, int x, int y);

    /**
     * Unlocks the Bonus
     */
    public abstract void unlock();

    /**
     * @return true if the Bonus is unlocked, false otherwise
     */
    public abstract boolean isUnlocked();
}
