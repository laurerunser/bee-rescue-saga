package model.bonus;

import model.board.Board;
import model.board.piece.Piece;

/**
 * Erases an entire column of the Board. The trapped Pieces get freed but not deleted (you don't get points from them).
 * Gives points : basePoints * the number of points won from deleting the Pieces
 */
public class EraseColumn extends Bonus {

    public EraseColumn(String iconPath, int basePoints) {
        super(iconPath, basePoints);
    }

    @Override
    public int use(Board b, int x, int y) {
        if (!b.isInsideBoard(x, y)) return -1;
        int points = 0;
        Piece[][] p = b.getBoard();
        for (int i = 0; i < p.length; i++) {
            if (p[i][y].isFree()) {
                points += p[i][y].getPoints();
                p[i][y] = null;
            } else {
                p[i][y].setFree();
            }
        }
        return points * basePoints;
    }

    @Override
    public void unlock() {
        if (isUnlocked()) return;
        unlocked[libre] = "EraseColumn";
        libre += 1;
    }

    @Override
    public boolean isUnlocked() {
        for (int i = 0; i < libre; i++) {
            if (unlocked[i].equals("EraseColumn")) return true;
        }
        return false;
    }
}
