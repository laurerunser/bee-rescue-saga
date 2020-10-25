package model.level;

import model.board.piece.Piece;

public class LevelLimitedTime extends Level {
    private int seconds;
    private int pointsPerSecondLeft;

    @Override
    public void updateBoard() {

    }

    @Override
    public boolean hasWon() {
        return false;
    }

    @Override
    public boolean hasLost() {
        return false;
    }

    public Piece getNewBlock() {

    }
}
