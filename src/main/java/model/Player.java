package model;

import model.bonus.Bonus;
import model.level.LevelMap;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Player implements Serializable {
    private static final long serialVersionUID = 123L;

    private final LevelMap map;
    private int nbLives;
    private int maxLives = 3;
    private long score = 0;
    private long gold;
    private Map<Bonus, Integer> bonus = new HashMap<>();
    private int raffleTurns;
    String name;


    public Player(String name) {
        nbLives = maxLives;
        this.name = name;
        this.map = new LevelMap();
        this.gold = 10;
        this.raffleTurns = 3;
    }

    public int getNbLives() { return nbLives; }

    public int getMaxLives() { return maxLives; }

    public long getScore() { return score; }

    public long getGold() { return gold; }

    public Map<Bonus, Integer> getBonus() { return bonus; }

    public int getRaffleTurns() { return raffleTurns; }

    public LevelMap getMap() { return map; }

    public void addToScore(int s) {
        this.score += s;
    }

    public void addGold(int s) { this.gold += s; }

    public void decreaseLives() { nbLives--; }


    /**
     * Deserializes the saved game from the player : it opens the name.ser file in the resources/savedGames directory
     *
     * @param n The name of the player
     * @return a Player containing all of their session info
     */
    public static Player deserialize(String n) {
        n = "savedGames/" + n + ".ser";
        try {
            System.out.println(n);
            File f = new File(Thread.currentThread().getContextClassLoader().getResource(n).getPath().toString());
            FileInputStream file = new FileInputStream(f);
            ObjectInputStream in = new ObjectInputStream(file);
            return (Player) in.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Creates a new file in resources/savedGames named NAME.ser and serializes this.
     */
    public void save() {
        String n = "src/main/resources/savedGames/" + name + ".ser";
        try {
            FileOutputStream fileOut = new FileOutputStream(n);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(this);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
