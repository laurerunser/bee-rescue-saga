package model.level;

public class LevelMap {
    /** The number of the current Level */
    private int currentLevel;
    /** All of the levels */
    private Level[] levels;
    /** For each level, levelsCompleted[i][0] = the number of stars won
     *                  levelsCompleted[i][1] = the max score made playing the Level */
    private int[][] levelsCompleted;
    /** The number of the last Level visible on the Map */
    private int levelVisible;

    /** Initializes the Levels */
    public void initLevels() {

    }

    /** Uncovers the next 10 levels on the map */
    public void uncoverMap() {
        levelVisible += 10;
    }

}
