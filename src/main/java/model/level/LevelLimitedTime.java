package model.level;

public class LevelLimitedTime extends Level {
    /** The number of seconds available to do the Level */
    private int seconds;
    /** The number of points the Player gets per second left at the end */
    private int pointsPerSecondLeft;

    /** {@inheritDoc} */
    @Override
    public boolean hasWon() {
        return allBeesSaved() && (seconds >= 0);
    }

    /** {@inheritDoc} */
    @Override
    public boolean hasLost() {
        return !allBeesSaved() && (seconds == 0);
    }

}
