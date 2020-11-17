package model.bonus;

import model.board.Board;
import model.board.piece.Piece;

/**
 * Erases a block if it is free.
 * Gives points : basePoints * the points of the Piece
 */
public class EraseBlock extends Bonus {

    public EraseBlock(String iconPath, int basePoints) {
        super(iconPath, basePoints);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int use(Board b, int x, int y) {
        if (!b.isInsideBoard(x, y)) return -1;
        Piece[][] p = b.getBoard();
        if (p[x][y].isFree()) {
            int points = p[x][y].getPoints();
            b.delete(x, y);
            return points * basePoints;
        } else {
            return -1;
        }
    }

    @Override
    public void unlock() {
        if (isUnlocked()) return;
        unlocked[libre] = "EraseBlock";
        libre += 1;
    }

    @Override
    public boolean isUnlocked() {
        for (int i = 0; i < libre; i++) {
            if (unlocked[i].equals("EraseBlock")) return true;
        }
        return false;
    }
}
