package model.board.piece;

import junit.framework.TestCase;
import model.board.Board;

public class BombTest extends TestCase {

    // test that all 9 Pieces are deleted and that the method returns the correct amount of points
    public void testDelete() {
        Bomb a = new Bomb(2, true);
        Piece[][] p = new Piece[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                p[i][j] = new ColorBlock(1, true, "red");
            }
        }
        p[3][3] = a;
        Board board = new Board(p);
        int result = p[3][3].delete(board, 3, 3);
        assertEquals(8 * 2, result); // 8 ColorBlock, 1 point each, multiplied by 2 (the Bomb points)
        for (int i = 2; i < 5; i++) {
            for (int j = 2; j < 5; j++) {
                assertNull(p[i][j]); // all the 9 Piece were deleted
            }
        }
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 5; j++) {
                assertNotNull(p[i][j]); // there weren't deleted
            }
        }
        for (int i = 2; i < 3; i++) {
            for (int j = 0; j < 2; j++) {
                assertNotNull(p[i][j]); // there weren't deleted
            }
        }
    }

    // test that the other Bomb also explodes
    public void testDelete2() {
        Bomb a = new Bomb(2, true);
        Bomb b = new Bomb(2, true);
        Piece[][] p = new Piece[5][5];
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                p[i][j] = new ColorBlock(1, true, "red");
            }
        }
        p[3][3] = a;
        p[2][2] = b;
        Board board = new Board(p);
        int result = p[3][3].delete(board, 3, 3);
        assertEquals((7 + 5 * 2) * 2, result);
        for (int i = 2; i < 5; i++) {
            for (int j = 2; j < 5; j++) {
                assertNull(p[i][j]); // all the 9 Pieces around the original bomb were deleted
            }
        }
        for (int i = 1; i < 4; i++) {
            for (int j = 1; j < 4; j++) {
                assertNull(p[i][j]); // the 9 Pieces around the other bomb were deleted
            }
        }
        assertNotNull(p[1][4]); // the other Pieces were not deleted
        assertNotNull(p[4][1]);
        for (int j = 0; j < 5; j++) {
            assertNotNull(p[0][j]);
        }
    }


    // test that elements of Decor are not deleted
    public void testDelete3() {
        Bomb a = new Bomb(2, true);
        Piece[][] p = new Piece[3][3];
        for (int i = 0; i < 3; i++) {
            p[0][i] = new ColorBlock(1, true, "red");
            p[2][i] = new ColorBlock(1, true, "yellow");
        }
        p[1][1] = a;
        p[1][0] = new Decor();
        p[1][2] = new Decor();
        Board board = new Board(p);
        p[1][1].delete(board, 1, 1);
        assertNotNull(p[1][0]);
        assertNotNull(p[1][2]);
    }

    // test that trapped Pieces are now free but not deleted
    public void testDelete4() {
        Bomb a = new Bomb(2, true);
        Piece[][] p = new Piece[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                p[i][j] = new ColorBlock(1, false, "red");
            }
        }
        p[1][1] = a;
        Board board = new Board(p);
        p[1][1].delete(board, 1, 1);
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i != 1 && j != 1) {
                    assertNotNull(p[i][j]);
                    assertTrue(p[i][j].isFree());
                }
            }
        }
    }

    // test that Bees are not deleted, and that a trapped Bee is set free
    public void testDelete5() {
        Bomb a = new Bomb(2, true);
        Piece[][] p = new Piece[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                p[i][j] = new ColorBlock(1, true, "red");
            }
        }
        p[1][1] = a;
        p[0][1] = new Bee(12, false);
        p[0][2] = new Bee(12, true);
        Board board = new Board(p);
        p[1][1].delete(board, 1, 1);
        assertNotNull(p[0][1]);
        assertNotNull(p[0][2]);
        assertTrue(p[0][1].isFree());
    }

    // test it works on the side of the board
    public void testDelete6() {
        Bomb a = new Bomb(2, true);
        Piece[][] p = new Piece[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                p[i][j] = new ColorBlock(1, true, "red");
            }
        }
        p[0][0] = a;
        Board board = new Board(p);
        p[0][0].delete(board, 0, 0);
        assertNull(p[0][0]);
        assertNull(p[0][1]);
        assertNull(p[1][0]);
        assertNull(p[1][1]);
        assertNotNull(p[1][2]);
        assertNotNull(p[0][2]);
        for (int i = 0; i < 3; i++) {
            assertNotNull(p[2][i]);
        }
    }

    // test 2 bombs placed correctly free and then delete a non-free Piece
    // test that trapped Pieces are now free but not deleted
    public void testDelete7() {
        Bomb a = new Bomb(2, true);
        Bomb b = new Bomb(2, true);
        Piece[][] p = new Piece[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                p[i][j] = new ColorBlock(1, false, "red");
            }
        }
        p[1][1] = a;
        p[2][2] = b;
        Board board = new Board(p);
        p[1][1].delete(board, 1, 1);
        // the Pieces not touched by the bombs stay trapped
        assertFalse(p[0][3].isFree());
        assertFalse(p[3][0].isFree());
        // the Pieces touched by one bomb are free
        for (int i = 1; i < 3; i++) {
            assertTrue(p[i][3].isFree());
            assertTrue(p[3][i].isFree());
        }
        for (int i = 0; i < 3; i++) {
            assertTrue(p[i][0].isFree());
            assertTrue(p[0][i].isFree());
        }
        // the Pieces touched by both bombs are gone, and the bombs too
        assertNull(p[1][1]);
        assertNull(p[1][2]);
        assertNull(p[2][1]);
        assertNull(p[2][2]);

    }
}