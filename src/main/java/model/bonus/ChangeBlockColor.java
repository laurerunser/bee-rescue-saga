package model.bonus;

import model.board.Board;

public class ChangeBlockColor extends Bonus {
    private String colorFrom;
    private String colorTo;

    @Override
    public boolean use(Board board) {
        return false;
    }
}
