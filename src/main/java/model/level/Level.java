package model.level;

import model.board.Board;
import model.board.NewPieceBuilder;
import model.board.piece.Piece;
import model.bonus.Bonus;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Level implements Serializable {
    private static final long serialVersionUID = 123L;

    /**
     * The number of the Level
     */
    private final int level;
    /**
     * The number of Bees that were saved
     */
    private int beeSaved;
    /**
     * The score of the Player on that Level
     */
    private int score;
    /**
     * The Board associated with the Level
     */
    protected Board board;
    /**
     * The Bonus the Player already had (can be null)
     */
    protected HashMap<Bonus, Integer> availableBonus;
    /**
     * The free Bonus given to the Player for that Level (can be null)
     */
    protected Bonus freeBonus;
    /**
     * The number of freeBonus available.
     * -1 is infinite.
     */
    protected int nbFreeBonus;
    /**
     * The number of moves it takes to replenish the freeBonus.
     * 0 : the freeBonus can't be replenished.
     * -1 : another freeBonus is immediately accessible.
     */
    protected int movesReplenishFreeBonus;
    /**
     * The number of moves left before the next freeBonus is accessible (0 if always accessible)
     */
    protected int currentCountReplenishingBonus;

    /**
     * The builder used to create new Pieces, based on the Level restrictions
     */
    private final NewPieceBuilder builder;

    /**
     * The number of Bees the Player must save to win the Level
     */
    private final int objBees;
    /**
     * The number of points the Player must have to get 1 star
     */
    private final int objScore1;
    /**
     * The number of points the Player must have to get 2 stars
     */
    private final int objScore2;
    /**
     * The number of points the Player must have to get 3 stars
     */
    private final int objScore3;

    public static String goal = "You need to save all the bees !";

    /**
     * Constructs a Level
     *
     * @param level          The number of the Level
     * @param board          The initial Board
     * @param availableBonus The number and types of available bonus
     * @param freeBonus      The free Bonus
     * @param builder        The builder to make new Pieces
     * @param objBees        The number of Bees necessary to win the level
     * @param objScore       An int array of size 3 that contains the amounts of points to get 1, 2 or 3 stars
     */
    public Level(int level, Board board, HashMap<Bonus, Integer> availableBonus, Bonus freeBonus, int nbFreeBonus, int movesReplenishFreeBonus, NewPieceBuilder builder, int objBees, int[] objScore) {
        this.level = level;
        this.board = board;
        this.availableBonus = availableBonus;
        this.freeBonus = freeBonus;
        this.nbFreeBonus = nbFreeBonus;
        this.movesReplenishFreeBonus = movesReplenishFreeBonus;
        this.currentCountReplenishingBonus = movesReplenishFreeBonus;
        this.builder = builder;
        this.objBees = objBees;
        this.objScore1 = objScore[0];
        this.objScore2 = objScore[1];
        this.objScore3 = objScore[2];
    }

    public int getLevel() { return level; }

    public int getBeeSaved() { return beeSaved; }

    public int getScore() { return score; }

    public int getObjBees() { return objBees; }

    public int[] getObjScore() {
        int[] obj = new int[3];
        obj[0] = objScore1;
        obj[1] = objScore2;
        obj[2] = objScore3;
        return obj;
    }

    public Map<Bonus, Integer> getAvailableBonus() { return availableBonus; }

    public Bonus getFreeBonus() { return freeBonus; }

    public Board getBoard() { return board; }

    /**
     * Getter for the conditions of the freeBonus
     *
     * @return an array of {the number of freeBonus, the number of moves to replenish the freeBonus}
     */
    public int[] getFreeBonusConditions() {
        return new int[]{nbFreeBonus, movesReplenishFreeBonus};
    }

    /**
     * @return true of the Player won, false otherwise
     */
    public boolean hasWon() {
        return allBeesSaved();
    }

    /**
     * @return true if the Player lost, false otherwise
     */
    public boolean hasLost() {
        return false; // can't lose an unlimited level
    }

    /**
     * Computes how many stars the Player got in the Level, depending on the 3 score objectives
     *
     * @return the number of stars the Player got in that level
     */
    public int getStars() {
        if (score >= objScore3) return 3;
        else if (score >= objScore2) return 2;
        else if (score >= objScore1) return 1;
        else return 0;
    }

    /**
     * Deletes the piece at coordinates {x,y}, and other if possible. Then updates the board.
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return the number of points won from deleting the piece(s)
     */
    public int delete(int x, int y) {
        int points = board.delete(x, y);
        updateBoard();
        return points;
    }

    /**
     * Updates the board and fills all empty spaces at the top with new Pieces
     * Also update the replenishing count for the free bonus
     */
    public void updateBoard() {
        int empty = updateBoardNoFill();
        for (int i = 0; i < empty; i++) {
            Piece newPiece = builder.getNewPiece();
            board.addOnTop(newPiece);
        }
        if (currentCountReplenishingBonus >= 1) {
            currentCountReplenishingBonus -= 1;
        }
    }

    /**
     * Updates the board without filling the empty fields.
     * Deletes all the Bees from the lowest row, and adds them to the beeSaved variable.
     * Then fills all the empty fields if possible.
     *
     * @return the number of null fields on the upper line
     */
    public int updateBoardNoFill() {
        int keepUpdating = 1;
        int[] result = new int[4];
        while (keepUpdating == 1) {
            result = board.updateBoard();
            beeSaved += result[0];
            score += result[1];
            keepUpdating = result[2];
        }
        return result[3];
    }

    /**
     * Uses the Bonus on the Board
     *
     * @param bonus The bonus to use
     * @param x     The x-coordinate
     * @param y     The y-coordinate
     * @return the number of points won, or -1 if the Bonus can't be used
     */
    public int useBonus(Bonus bonus, int x, int y) {
        if (bonus != freeBonus) { // -1 in the available bonus map
            availableBonus.put(bonus, availableBonus.getOrDefault(bonus, 1) - 1);
        }
        return bonus.use(getBoard(), x, y);
    }


    /**
     * Sets the bonus that the Player owns
     *
     * @param bonus The HashMap of the Bonus the Player owns
     */
    public void setBonus(HashMap<Bonus, Integer> bonus) {
        this.availableBonus = bonus;
    }

    /**
     * Adds v to the score
     *
     * @param v The value to add to the score
     */
    public void addToScore(int v) {
        score += v;
    }

    /**
     * @return true if all the Bees were saved, false otherwise
     */
    public boolean allBeesSaved() {
        return beeSaved == objBees;
    }

    /**
     * Resets the number of moves to replenish the freeBonus to the default value.
     */
    public void resetFreeBonusMoves() {
        if (nbFreeBonus != -1) {
            nbFreeBonus -= 1;
            currentCountReplenishingBonus = movesReplenishFreeBonus;
        }
        currentCountReplenishingBonus = movesReplenishFreeBonus;
    }

}
