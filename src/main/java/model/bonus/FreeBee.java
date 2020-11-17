package model.bonus;

import model.board.Board;
import model.board.piece.Bee;
import model.board.piece.Piece;

/**
 * Frees a Bee. Doesn't give any points.
 */
public class FreeBee extends Bonus {

    public FreeBee(String iconPath) {
        super(iconPath, 0);
    }

    @Override
    public int use(Board b, int x, int y) {
        Piece[][] p = b.getBoard();
        if (!b.isInsideBoard(x, y) || !(p[x][y] instanceof Bee) || p[x][y].isFree()) return -1;
        p[x][y].setFree();
        return 0;
    }

    @Override
    public void unlock() {
        if (isUnlocked()) return;
        unlocked[libre] = "FreeBee";
        libre += 1;
    }

    @Override
    public boolean isUnlocked() {
        for (int i = 0; i < libre; i++) {
            if (unlocked[i].equals("FreeBee")) return true;
        }
        return false;
    }
}
