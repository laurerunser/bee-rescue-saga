package model.level;

import model.board.piece.Piece;

public class LevelLimitedMoves extends Level {
    private int moves;
    private int pointsPerMoveLeft;

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
