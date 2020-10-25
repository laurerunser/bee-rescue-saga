package model.level;

import model.board.Board;
import model.bonus.Bonus;

import java.util.Map;

public abstract class Level {
    private int beeSaved;
    private int score;
    private Board board;
    private Map<Bonus, Integer> availableBonus;
    private Bonus freeBonus;

    private int objBees;
    private int objScore1;
    private int objScore2;
    private int objScore3;

    public abstract void updateBoard();

    public abstract boolean hasWon();

    public abstract boolean hasLost();

    public int getStars() {

    }


}
