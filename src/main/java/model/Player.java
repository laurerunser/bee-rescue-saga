package model;

import model.bonus.Bonus;
import model.level.LevelMap;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private final LevelMap map;
    private int nbLives = 0;
    private int maxLives = 3;
    private long score = 0;
    private long gold = 0;
    private Map<Bonus, Integer> bonus = new HashMap<>();
    private int raffleTurns = 0;
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

    public void decreaseLives() { nbLives--; }

}
