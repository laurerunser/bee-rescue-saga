package model;

import model.bonus.Bonus;
import model.level.LevelMap;

import java.util.HashMap;
import java.util.Map;

public class Player {
    private int nbLives = 0;
    private int maxLives = 3;
    private long score = 0;
    private long gold = 0;
    private Map<Bonus, Integer> bonus = new HashMap<>();
    private int raffleTurns = 0;
    private final LevelMap map = new LevelMap();
}
