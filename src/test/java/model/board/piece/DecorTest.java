package model.board.piece;

import junit.framework.TestCase;
import model.board.Board;

public class DecorTest extends TestCase {
    public void testDelete() {
        Decor d = new Decor();
        Decor d2 = new Decor();
        Piece[][] p = {{d}, {d2}};
        boolean[][] visible = new boolean[1][2];
        Board b = new Board(p, visible);
        int result = d.delete(b, 0, 0);
        assertNotNull(p[0][0]);
        assertEquals(result, 0);
    }

}