package model.board.piece;

public abstract class Piece {
    private String iconPath;
    private String trappedIconPath;
    private int points;
    private boolean isFree;

    public void free() {
        isFree = true;
    }

    public boolean isFree() {
        return isFree;
    }
}
