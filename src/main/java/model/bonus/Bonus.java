package model.bonus;

import model.board.Board;

public abstract class Bonus {
    private String iconPath;

    private int basePoints;
    private boolean unlocked;

    public abstract boolean use(Board board);
}
