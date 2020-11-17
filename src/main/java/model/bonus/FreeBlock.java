package model.bonus;

import model.board.Board;
import model.board.piece.Bee;
import model.board.piece.Piece;

/**
 * Frees a Piece, except if it is a Bee. Doesn't give any points.
 */
public class FreeBlock extends Bonus {

    public FreeBlock(String iconPath) {
        super(iconPath, 0);
    }

    @Override
    public int use(Board b, int x, int y) {
        Piece[][] p = b.getBoard();
        if (!b.isInsideBoard(x, y) || (p[x][y] instanceof Bee) || p[x][y].isFree()) return -1;
        p[x][y].setFree();
        return 0;
    }

    @Override
    public void unlock() {
        if (isUnlocked()) return;
        unlocked[libre] = "FreeBlock";
        libre += 1;
    }

    @Override
    public boolean isUnlocked() {
        for (int i = 0; i < libre; i++) {
            if (unlocked[i].equals("FreeBlock")) return true;
        }
        return false;
    }
}
