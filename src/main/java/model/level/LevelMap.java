package model.level;

public class LevelMap {
    private int currentLevel;
    private Level[] levels;
    private int[][] levelsCompleted;
    private int levelVisible;

    public void initLevels() {

    }

    /**
     * Uncovers the next 10 levels on the map
     */
    public void uncoverMap() {
        levelVisible += 10;
    }

}
