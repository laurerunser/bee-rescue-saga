package model.board.piece;

import junit.framework.TestCase;
import model.board.Board;

public class EraseColorBlocksTest extends TestCase {

    // check that it deletes or frees the ColorBlocks of the right color but not the Bees trapped in the same color
    public void testDelete() {
        Piece[][] p = new Piece[4][4];
        p[0][0] = new EraseColorBlocks(3, true, "red");
        p[2][2] = new ColorBlock(3, true, "red");
        p[3][1] = new ColorBlock(3, false, "red");
        p[1][3] = new Bee(3, "red");
        p[3][0] = new ColorBlock(2, true, "blue");
        boolean[][] visible = new boolean[4][4];
        Board b = new Board(p, visible);
        int result = p[0][0].delete(b, 0, 0);
        assertEquals(result, 9);
        assertNull(p[2][2]);
        assertNotNull(p[3][0]);
        assertTrue(p[3][1].isFree());
        assertFalse(p[1][3].isFree());
    }
}