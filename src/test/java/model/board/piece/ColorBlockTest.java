package model.board.piece;

import junit.framework.TestCase;
import model.board.Board;

public class ColorBlockTest extends TestCase {

    // test it deletes other blocks and that the correct number of points is returned
    public void testDelete() {
        Piece[][] p = new Piece[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                p[i][j] = new ColorBlock(2, true, "red");
            }
        }
        Board board = new Board(p);
        int result = p[1][1].delete(board, 1, 1);
        assertEquals(result, 18);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertNull(p[i][j]);
            }
        }
    }

    // test it doesn't delete on the diagonal
    public void testDelete2() {
        Piece[][] p = new Piece[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                p[i][j] = new ColorBlock(2, true, "red");
            }
        }
        p[1][2] = new ColorBlock(2, true, "blue");
        p[0][1] = new ColorBlock(2, true, "blue");
        Board board = new Board(p);
        int result = p[1][1].delete(board, 1, 1);
        assertEquals(result, 12);
        assertNotNull(p[0][2]);
        assertNotNull(p[0][1]);
        assertNotNull(p[1][2]);
    }


    // test that trapped blocks of the same color are set free, but not deleted
    public void testDelete3() {
        Piece[][] p = new Piece[3][3];
        p[0][0] = new ColorBlock(2, true, "blue");
        p[0][1] = new ColorBlock(2, false, "red");
        p[0][2] = new ColorBlock(2, true, "red");
        p[1][0] = new ColorBlock(2, true, "red");
        p[1][1] = new ColorBlock(2, true, "red");
        p[1][2] = new ColorBlock(2, false, "red");
        p[2][0] = new ColorBlock(2, true, "red");
        p[2][1] = new ColorBlock(2, true, "blue");
        p[2][2] = new ColorBlock(2, true, "red");
        Board board = new Board(p);
        int result = p[1][1].delete(board, 1, 1);
        assertEquals(result, 6);
        assertNotNull(p[0][2]);
        assertNotNull(p[0][1]);
        assertNotNull(p[1][2]);
        assertTrue(p[1][2].isFree());
        assertTrue(p[0][1].isFree());
    }

    // test the Bees don't die and the trapped Bees of the same color are set free, but not the ones of the wrong color
    // also the Bees don't recursively free each other : they have to be in direct contact with the deleted ColorBlock
    public void testDelete4() {
        Piece[][] p = new Piece[3][3];
        p[0][1] = new Bee(2, "red");
        p[0][2] = new Bee(2, "red");
        p[1][0] = new Bee(2, "blue");
        p[1][1] = new ColorBlock(2, true, "red");
        p[2][1] = new Bee(2);
        p[2][2] = new Bee(2);
        Board board = new Board(p);
        int result = p[1][1].delete(board, 1, 1);
        assertEquals(result, 2);
        assertTrue(p[0][1].isFree());
        assertFalse(p[0][2].isFree());
        assertTrue(p[2][1].isFree());
        assertFalse(p[1][0].isFree());

    }
}