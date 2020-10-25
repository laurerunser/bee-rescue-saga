package model.bonus;

import model.board.Board;

public class EraseBlock extends Bonus {
    @Override
    public boolean use(Board board) {
        return false;
    }
}
