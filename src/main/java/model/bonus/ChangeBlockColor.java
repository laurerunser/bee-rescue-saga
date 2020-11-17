package model.bonus;

import model.board.Board;
import model.board.piece.ColorBlock;
import model.board.piece.Piece;

/**
 * All the blocks in color colorFrom are changed in the color colorTo (even if they are not free).
 * Doesn't give any points.
 */
public class ChangeBlockColor extends Bonus {
    /**
     * The old color
     */
    private String colorFrom;
    /**
     * The new color
     */
    private String colorTo;

    public ChangeBlockColor(String iconPath) {
        super(iconPath, 0);
    }


    @Override
    public int use(Board b, int x, int y) {
        if (!b.isInsideBoard(x, y)) return -1;
        Piece[][] p = b.getBoard();
        if (!(p[x][y] instanceof ColorBlock)) return -1;
        colorFrom = ((ColorBlock) p[x][y]).getColor();
        for (Piece[] line : p) {
            for (Piece piece : line) {
                if (piece instanceof ColorBlock && ((ColorBlock) piece).getColor().equals(colorFrom)) {
                    ((ColorBlock) piece).setColor(colorTo);
                }
            }
        }
        return 0;
    }

    @Override
    public void unlock() {
        if (isUnlocked()) return;
        unlocked[libre] = "ChangeColorBlock";
        libre += 1;
    }

    @Override
    public boolean isUnlocked() {
        for (int i = 0; i < libre; i++) {
            if (unlocked[i].equals("ChangeColorBlock")) return true;
        }
        return false;
    }
}
