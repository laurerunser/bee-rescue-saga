package model.bonus;

import model.board.Board;

public class FreeBlock extends Bonus {
    @Override
    public boolean use(Board board) {
        return false;
    }
}
