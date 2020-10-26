package model.level;

public class LevelLimitedMoves extends Level {
    /** The number of moves available to the Player */
    private int moves;
    /** The amount of points the Player gets for each move left at the end*/
    private int pointsPerMoveLeft;

    /** {@inheritDoc} */
    @Override
    public boolean hasWon() {
        return (moves >= 0) && allBeesSaved();
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasLost() {
        return !allBeesSaved() && (moves == 0);
    }
}
