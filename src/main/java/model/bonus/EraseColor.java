package model.bonus;

import model.board.Board;
import model.board.piece.ColorBlock;
import model.board.piece.Piece;

/**
 * Deletes all the ColorBlocks of that color.
 * Gives points : basePoints * the number of points won from deleting the Pieces
 */
public class EraseColor extends Bonus {
    private static final long serialVersionUID = 123L;

    private String color;

    public EraseColor(int basePoints) {
        super("EraseColor", basePoints);
    }

    @Override
    public int use(Board b, int x, int y) {
        if (!b.isInsideBoard(x, y)) return -1;
        int points = 0;
        Piece[][] p = b.getBoard();
        if (!(p[x][y] instanceof ColorBlock)) return -1;
        color = ((ColorBlock) p[x][y]).getColor();
        for (Piece[] line : p) {
            for (Piece piece : line) {
                if (piece instanceof ColorBlock && ((ColorBlock) piece).getColor().equals(color)) {
                    if (!piece.isFree()) {
                        piece.setFree();
                    } else {
                        points += piece.delete(b, x, y);
                    }
                }
            }
        }
        return points * basePoints;
    }

    @Override
    public void unlock() {
        if (isUnlocked()) return;
        unlocked[libre] = "EraseColor";
        libre += 1;
    }

    @Override
    public boolean isUnlocked() {
        for (int i = 0; i < libre; i++) {
            if (unlocked[i].equals("EraseColor")) return true;
        }
        return false;
    }


    @Override
    public String getToolTipText() {
        return "Deletes or frees all the blocks of the chosen color";
    }
}
