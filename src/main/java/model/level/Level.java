package model.level;

import model.board.Board;
import model.board.NewPieceBuilder;
import model.board.piece.Piece;
import model.bonus.Bonus;

import java.util.HashMap;
import java.util.Map;

public abstract class Level {
    /** The number of Bees that were saved */
    private int beeSaved;
    /** The score of the Player on that Level */
    private int score;
    /** The Board associated with the Level */
    protected Board board;
    /** The Bonus the Player already had (can be null) */
    protected Map<Bonus, Integer> availableBonus;
    /** The free Bonus given to the Player for that Level (can be null) */
    protected Bonus freeBonus;
    /** The builder used to create new Pieces, based on the Level restrictions */
    private NewPieceBuilder builder;

    /** The number of Bees the Player must save to win the Level */
    private int objBees;
    /** The number of points the Player must have to get 1 star */
    private int objScore1;
    /** The number of points the Player must have to get 2 stars */
    private int objScore2;
    /** The number of points the Player must have to get 3 stars */
    private int objScore3;

    /** @return true of the Player won, false otherwise */
    public abstract boolean hasWon();

    /** @return true if the Player lost, false otherwise */
    public abstract boolean hasLost();

    /** Computes how many stars the Player got in the Level, depending on the 3 score objectives
     *
     * @return the number of stars the Player got in that level
     */
    public int getStars() {
        if (score >= objScore3) return 3;
        else if (score >= objScore2) return 2;
        else if (score >= objScore1) return 1;
        else return 0;
    }

    /** Updates the board and fills all empty spaces at the top with new Pieces */
    public void updateBoard() {
        int empty = updateBoardNoFill();
        for (int i = 0; i < empty; i++) {
            Piece newPiece = builder.getNewPiece();
            board.addOnTop(newPiece);
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
        int[] result = new int[3];
        while (keepUpdating == 1) {
            result = board.updateBoard();
            beeSaved += result[0];
            keepUpdating = result[1];
        }
        return result[2];
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


}
