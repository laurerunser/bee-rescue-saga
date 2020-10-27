package model.board.piece;

import junit.framework.TestCase;
import model.board.Board;

public class BeeTest extends TestCase {

    // test that the Bee is not deleted and that the method returns 0
    public void testDelete() {
        Piece[][] p = new Piece[1][2];
        Bee a = new Bee(4, "blue");
        p[0][0] = a;
        p[0][1] = new Bee(5, "blue");
        Board b = new Board(p);
        int res = p[0][0].delete(b, 0, 0);
        assertEquals(0, res);
        assertEquals(p[0][0], a);
    }
}