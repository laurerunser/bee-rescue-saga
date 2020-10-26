package model.board;

import junit.framework.TestCase;
import model.board.piece.Piece;

public class BoardTest extends TestCase {

    public void testHasMoveLeft() {
    }

    public void testIsAColorBlock() {
    }

    public void testIsInsideBoard() {
        Piece[][] p = new Piece[4][5];
        Board b = new Board(p);
        assertTrue(b.isInsideBoard(1, 1));
        assertFalse(b.isInsideBoard(6, 6));
    }

    public void testUpdateBoard() {
    }

    public void testAddOnTop() {
    }
}