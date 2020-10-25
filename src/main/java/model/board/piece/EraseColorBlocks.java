package model.board.piece;

import model.board.Board;

public class EraseColorBlocks extends Piece implements PieceBonus {
    private String color;

    /**
     * Delete the surrounding Pieces from the board if needed
     *
     * @param board The board
     * @param x     The x-coordinate of the Piece
     * @param y     The y-coordinate of the Piece
     */
    @Override
    public void delete(Board board, int x, int y) {
        for (int i = 0; i < board.getBoard().length; i++) {
            for (int j = 0; j < board.getBoard()[i].length; j++) {
                if (board.isInsideBoard(i, j) && !board.isEmpty(i, j)) {
                    ColorBlock c = board.isAColorBlock(i, j); // null if not a ColorBlock
                    if (c != null && c.getColor().equals(this.color)) {
                        c.delete(board, i, j);
                    }
                }
            }
        }
        board.getBoard()[x][y] = null;
    }
}
