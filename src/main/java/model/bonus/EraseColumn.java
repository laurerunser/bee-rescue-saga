package model.bonus;

import model.board.Board;

public class EraseColumn extends Bonus {

    @Override
    public boolean use(Board board) {
        return false;
    }
}
