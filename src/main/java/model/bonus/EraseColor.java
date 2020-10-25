package model.bonus;

import model.board.Board;

public class EraseColor extends Bonus {
    private String color;

    @Override
    public boolean use(Board board) {
        return false;
    }
}
