package model.board;

import junit.framework.TestCase;
import model.board.piece.*;

public class BoardTest extends TestCase {

    // test a Bomb or an EraseColorBlocks is there
    public void testHasMoveLeft1() {
        Piece[][] p = new Piece[2][2];
        p[0][0] = new ColorBlock(2, true, "red");
        p[0][1] = new Bee(2);
        p[1][0] = new EraseColorBlocks(2, true, "red");
        p[1][1] = new Bomb(4, true);
        Board b = new Board(p);
        assertTrue(b.hasMoveLeft());
    }

    // test ColorBlocks can be deleted
    public void testHasMoveLeft2() {
        Piece[][] p = new Piece[2][2];
        p[0][0] = new ColorBlock(2, true, "red");
        p[0][1] = new ColorBlock(2, true, "red");
        Board b = new Board(p);
        assertTrue(b.hasMoveLeft());
    }

    // test no move left with trapped ColorBlocks
    public void testHasMoveLeft3() {
        Piece[][] p = new Piece[2][2];
        p[0][0] = new ColorBlock(2, false, "red");
        p[0][1] = new ColorBlock(2, false, "red");
        Board b = new Board(p);
        assertFalse(b.hasMoveLeft());
    }

    // test no move left
    public void testHasMoveLeft4() {
        Piece[][] p = new Piece[2][2];
        p[0][0] = new ColorBlock(2, true, "red");
        p[0][1] = new Bee(2);
        p[1][0] = new Decor();
        p[1][1] = new Bee(2);
        Board b = new Board(p);
        assertFalse(b.isAColorMove(0, 0));
        assertFalse(b.hasMoveLeft());
    }

    public void testIsAColorBlock() {
        Piece[][] p = new Piece[2][2];
        p[0][0] = new ColorBlock(2, true, "red");
        p[0][1] = new Bee(2);
        p[1][0] = new EraseColorBlocks(2, true, "red");
        p[1][1] = new Bomb(4, true);
        Board b = new Board(p);
        assertNotNull(b.isAColorBlock(0, 0));
        assertNull(b.isAColorBlock(0, 1));
        assertNull(b.isAColorBlock(1, 0));
        assertNull(b.isAColorBlock(1, 1));
    }

    public void testIsInsideBoard() {
        Piece[][] p = new Piece[4][5];
        Board b = new Board(p);
        assertTrue(b.isInsideBoard(1, 1));
        assertFalse(b.isInsideBoard(6, 6));
    }

    public void testAddOnTop() {
        // add to (0,0)
        Piece[][] p = new Piece[2][2];
        Bee bee = new Bee(2);
        p[0][1] = new Bee(2);
        p[1][0] = new EraseColorBlocks(2, true, "red");
        p[1][1] = new Bomb(4, true);
        Board b = new Board(p);
        b.addOnTop(bee);
        assertSame(b.getBoard()[0][0], bee);

        // add to (2,2)
        Piece[][] p2 = new Piece[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                p2[i][j] = new Bee(2);
            }
        }
        p2[2][2] = null;
        Bomb bomb = new Bomb(2, true);
        Board b2 = new Board(p2);
        b2.addOnTop(bomb);
        assertSame(b2.getBoard()[2][2], bomb);

        // don't add, board already full
        Piece[][] p3 = new Piece[2][2];
        Bee bee3 = new Bee(2);
        Bomb bomb3 = new Bomb(2, true);
        p3[0][0] = bomb3;
        p3[0][1] = new Bee(2);
        p3[1][0] = new EraseColorBlocks(2, true, "red");
        p3[1][1] = new Bomb(4, true);
        Board b3 = new Board(p3);
        b3.addOnTop(bee3);
        assertSame(b3.getBoard()[0][0], bomb3);
    }

    public void testIsAColorMove() {
        Piece[][] p = new Piece[2][2];
        p[0][0] = new ColorBlock(2, true, "red");
        p[0][1] = new ColorBlock(2, true, "red");
        Board b = new Board(p);
        assertTrue(b.isAColorMove(0, 0));

        p[0][0] = new ColorBlock(2, false, "red");
        p[0][1] = new ColorBlock(2, false, "red");
        Board b2 = new Board(p);
        assertFalse(b2.isAColorMove(0, 0));
    }

    // test that the method returns the right values when no Piece has to be moved
    public void testUpdateBoard1() {
        Piece[][] p = new Piece[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                p[i][j] = new ColorBlock(2, true, "red");
            }
        }
        Board b = new Board(p);
        int[] result = b.updateBoard();
        int[] expected = {0, 0, 0, 0};
        assertTrue(result[0] == expected[0] && result[1] == expected[1] && result[2] == expected[2] && result[3] == expected[3]);

        p[0][0] = null;
        p[0][2] = null;
        expected = new int[]{0, 0, 0, 2};
        result = b.updateBoard();
        assertTrue(result[0] == expected[0] && result[1] == expected[1] && result[2] == expected[2] && result[3] == expected[3]);
    }

    // test deleting bees and moving Pieces down
    public void testUpdateBoard2() {
        Piece[][] p = new Piece[3][3];
        Bee a = new Bee(2);
        ColorBlock r = new ColorBlock(2, true, "red");
        ColorBlock b = new ColorBlock(3, true, "blue");
        p[0][0] = b;
        p[0][1] = r;
        p[1][0] = r;
        p[1][1] = a;
        p[1][2] = b;
        p[2][0] = a;
        p[2][1] = r;
        p[2][2] = a;
        Board board = new Board(p);
        int[] result = board.updateBoard();
        int[] expected = {2, 4, 1, 2};
        assertTrue(result[0] == expected[0] && result[1] == expected[1] && result[2] == expected[2] && result[3] == expected[3]);
        assertSame(r, p[2][0]);
        assertSame(b, p[1][0]);
        assertNull(p[0][0]);
        assertSame(r, p[2][1]);
        assertSame(a, p[1][1]);
        assertSame(r, p[0][1]);
        assertSame(b, p[2][2]);
        assertNull(p[1][2]);
        assertNull(p[0][2]);
    }

    // test moving a column to the left
    public void testUpdateBoard3() {
        Piece[][] p = new Piece[3][3];
        Bee a = new Bee(2);
        ColorBlock r = new ColorBlock(2, true, "red");
        ColorBlock b = new ColorBlock(3, true, "blue");
        p[0][0] = b;
        p[1][0] = r;
        p[1][2] = b;
        p[2][0] = a;
        p[2][2] = a;
        Board board = new Board(p);
        int[] result = board.updateBoard();
        int[] expected = {2, 4, 1, 3};
        assertEquals(expected[0], result[0]);
        assertEquals(expected[1], result[1]);
        assertEquals(expected[2], result[2]);
        assertEquals(expected[3], result[3]);
        assertSame(r, p[2][0]);
        assertSame(b, p[1][0]);
        assertNull(p[0][0]);
        assertSame(b, p[2][1]);
        for (int i = 0; i < 3; i++) {
            assertNull(p[i][2]);
        }
    }

    // doesn't move trapped Pieces, but takes the upper-right Piece instead if possible
    public void testUpdateBoard4() {
        Piece[][] p = new Piece[2][3];
        Bee a = new Bee(2);
        ColorBlock r = new ColorBlock(2, true, "red");
        ColorBlock rt = new ColorBlock(2, false, "red");
        ColorBlock b = new ColorBlock(3, true, "blue");
        ColorBlock y = new ColorBlock(3, true, "yellow");
        p[0][0] = r;
        p[0][1] = rt;
        p[0][2] = b;
        p[1][0] = a;
        p[1][1] = a;
        p[1][2] = y;
        Board board = new Board(p);
        int[] result = board.updateBoard();
        int[] expected = {2, 4, 1, 2};
        assertEquals(expected[0], result[0]);
        assertEquals(expected[1], result[1]);
        assertEquals(expected[2], result[2]);
        assertEquals(expected[3], result[3]);
        assertNull(p[0][0]);
        assertNull(p[0][2]);
        assertSame(r, p[1][0]);
        assertSame(rt, p[0][1]);
        assertSame(b, p[1][1]);
        assertSame(y, p[1][2]);
    }

    // test on a bigger board
    public void testUpdateBoard5() {
        Piece[][] p = new Piece[5][5];
        // the Bees
        Bee bee1 = new Bee(2);
        Bee bee2 = new Bee(3, "red");
        Bee bee3 = new Bee(3);
        p[4][0] = bee1;
        p[4][1] = bee2;
        p[4][3] = bee3;
        // the Bombs
        Bomb bomb1 = new Bomb(3, true);
        Bomb bomb2 = new Bomb(4, false);
        p[2][1] = bomb1;
        p[1][3] = bomb2;
        // the Decor
        Decor decor1 = new Decor();
        Decor decor2 = new Decor();
        Decor decor3 = new Decor();
        Decor decor4 = new Decor();
        p[2][0] = decor1;
        p[1][2] = decor2;
        p[4][2] = decor3;
        p[3][4] = decor4;
        // the ColorBlocks
        ColorBlock r1 = new ColorBlock(2, false, "red");
        ColorBlock r2 = new ColorBlock(2, true, "red");
        ColorBlock b1 = new ColorBlock(2, true, "blue");
        ColorBlock b2 = new ColorBlock(2, false, "blue");
        ColorBlock b3 = new ColorBlock(2, true, "blue");
        ColorBlock y1 = new ColorBlock(2, true, "yellow");
        ColorBlock y2 = new ColorBlock(2, true, "yellow");
        ColorBlock y3 = new ColorBlock(2, true, "yellow");
        ColorBlock g1 = new ColorBlock(2, true, "green");
        ColorBlock g2 = new ColorBlock(2, true, "green");
        p[3][0] = r1;
        p[4][4] = r2;
        p[3][1] = b1;
        p[2][3] = b2;
        p[2][4] = b3;
        p[1][0] = y1;
        p[2][2] = y2;
        p[0][2] = y3;
        p[0][0] = g1;
        p[0][4] = g2;
        Board board = new Board(p);
        board.updateBoard();

        // didn't move
        assertSame(decor1, p[2][0]);
        assertSame(decor2, p[1][2]);
        assertSame(decor3, p[4][2]);
        assertSame(decor4, p[3][4]);
        assertSame(bee2, p[4][1]);
        assertSame(bomb2, p[1][3]);
        assertSame(g1, p[0][0]);
        assertSame(y1, p[1][0]);
        assertSame(r2, p[4][4]);
        assertSame(y2, p[3][2]);
        // moved
        assertSame(b1, p[4][0]);
        assertSame(bomb1, p[3][1]);
        assertSame(b3, p[4][3]);
        assertSame(g2, p[3][3]);
        assertSame(y3, p[2][1]);
    }


}