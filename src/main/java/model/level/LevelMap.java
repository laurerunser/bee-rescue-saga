package model.level;

import controller.listeners.LevelListener;

public class LevelMap implements LevelListener {
    /**
     * The number of the current Level
     */
    private int currentLevel;
    /**
     * All of the levels
     */
    private Level[] levels;
    /**
     * For each level, levelsCompleted[i][0] = the number of stars won
     * levelsCompleted[i][1] = the max score made playing the Level
     */
    private int[][] levelsCompleted;
    /**
     * The number of the last Level visible on the Map
     */
    private int levelVisible;


    // TODO : initialize the levels

    public Level[] getLevels() { return levels; }

    public int currentLevel() { return currentLevel; }

    public int[][] getLevelsCompleted() { return levelsCompleted; }

    public int getLastLevelVisible() { return levelVisible; }

    /**
     * Uncovers the next 10 levels on the map
     */
    public void uncoverMap() {
        levelVisible += 10;
    }

    /**
     * Adds the Level to the array. It is put in the position nb of level -1
     * ex : level 1 is in levels[0], level 10 is in levels[9]
     *
     * @param level The Level to add
     */
    public void addLevel(Level level) {
        levels[level.getLevel() - 1] = level;
    }

    @Override
    public void onHasWon(int stars, int score, int level) {
        if (stars > levelsCompleted[level][0]) {
            levelsCompleted[level][0] = stars;
        }
        if (score > levelsCompleted[level][1]) {
            levelsCompleted[level][1] = score;
        }
    }

    @Override
    public void onHasLost() {
        // TODO : on has lost
    }
}
