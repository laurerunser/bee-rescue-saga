package model.level;

public class LevelLimitedPieces extends Level {
    /**
     * The number of Pieces on that Level excluding Bees
     */
    private int nbPieces;

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasWon() {
        return allBeesSaved() && nbPieces >= 0;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean hasLost() {
        if (freeBonus != null) return true; // you can always play the free Bonus
        return !board.hasMoveLeft();
    }

    /**
     * Only updates the board, without filling the empty spaces
     */
    @Override
    public void updateBoard() {
        updateBoardNoFill();
    }
}
