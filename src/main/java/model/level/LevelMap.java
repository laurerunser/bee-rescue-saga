package model.level;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

public class LevelMap implements Serializable {
    private static final long serialVersionUID = 123L;

    /**
     * The number of the current Level
     */
    private int currentLevel;
    /**
     * All of the levels
     */
    private final Level[] levels;
    /**
     * For each level, levelsCompleted[i][0] = the number of stars won
     * levelsCompleted[i][1] = the max score made playing the Level
     */
    private final int[][] levelsCompleted;
    /**
     * The number of the last Level visible on the Map
     */
    private int levelVisible;

    public LevelMap() {
        currentLevel = 0;
        levels = new Level[4];
        levelsCompleted = new int[4][2];
        levelVisible = 0;

        // fill the map with the serialized levels
        for (int i = 0; i <= 3; i++) {
            //TODO : find a way to automatically input the number of levels (3) so that I don't have to go back to it if I add levels
            Level l;
            try {
                String name = "src/main/resources/levelsSER/level" + i + ".ser";
                FileInputStream fileIn = new FileInputStream(name);
                ObjectInputStream in = new ObjectInputStream(fileIn);
                l = (Level) in.readObject();
                in.close();
                fileIn.close();
                levels[i] = l;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    public Level[] getLevels() { return levels; }

    public int[][] getLevelsCompleted() { return levelsCompleted; }

    public int getCurrentLevel() { return currentLevel; }

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

    public void hasWon(int stars, int score, int level) {
        currentLevel += 1;
        if (stars > levelsCompleted[level][0]) {
            levelsCompleted[level][0] = stars;
        }
        if (score > levelsCompleted[level][1]) {
            levelsCompleted[level][1] = score;
        }
        if (level == levelVisible) {
            uncoverMap();
            // TODO : launch animations & reward for uncovering levels
        }
    }
}
