package main;

import model.board.Board;
import model.board.piece.Bee;
import model.board.piece.ColorBlock;
import model.board.piece.Piece;
import model.level.Level;
import model.level.LevelLimitedPieces;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.Arrays;

/**
 * Serializes the default levels in the ressources/levelsSER directory
 * Each file is named levelN where N is the number of the level, starting from 0
 */

// TODO : the first level is not appearing ???
// TODO : adjust the score objectives so that the levels can be won
public class Install {
    public static void main(String[] args) {
        Level[] levels = new Level[5];
        levels[0] = level0();
        levels[1] = level1();
        levels[2] = level2();
        levels[3] = level3();
        levels[4] = level4();

        for (Level l : levels) {
            writeOut(l, l.getLevel());
        }
    }

    /**
     * Serialize the level. Places the file in the resources/levelsSER from main.
     * Each file will be named levelN.ser where N is the number of the level.
     *
     * @param l The level
     * @param n The number of the level
     */
    public static void writeOut(Level l, int n) {
        String name = "src/main/resources/levelsSER/level" + n + ".ser";
        try {
            FileOutputStream fileOut = new FileOutputStream(name);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(l);
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static LevelLimitedPieces level0() {
        Piece[][] p = new Piece[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                p[i][j] = new ColorBlock(10, true, "blue");
            }
        }
        p[0][2] = new Bee(10);

        boolean[][] visible = new boolean[4][4];
        for (boolean[] line : visible) {
            Arrays.fill(line, true);
        }
        Board b = new Board(p, visible);
        int[] obj = {10, 30, 50};
        return new LevelLimitedPieces(0, b, null, null, 0, 0, null, 2, obj, 42);
    }

    public static LevelLimitedPieces level1() {
        Piece[][] p = new Piece[7][7];
        p[0][1] = new Bee(1000);
        p[0][5] = new Bee(1000);
        p[1][0] = new ColorBlock(20, true, "yellow");
        p[1][1] = new ColorBlock(20, true, "yellow");
        p[1][2] = new ColorBlock(20, true, "yellow");
        p[2][0] = new ColorBlock(20, true, "yellow");
        p[2][1] = new ColorBlock(20, true, "yellow");
        p[5][0] = new ColorBlock(20, true, "yellow");
        p[5][4] = new ColorBlock(20, true, "yellow");
        p[5][5] = new ColorBlock(20, true, "yellow");
        p[6][0] = new ColorBlock(20, true, "yellow");
        p[6][4] = new ColorBlock(20, true, "yellow");
        p[6][5] = new ColorBlock(20, true, "yellow");

        p[3][0] = new ColorBlock(20, true, "blue");
        p[4][0] = new ColorBlock(20, true, "blue");
        p[4][1] = new ColorBlock(20, true, "blue");
        p[1][2] = new ColorBlock(20, true, "blue");
        p[1][3] = new ColorBlock(20, true, "blue");
        p[1][4] = new ColorBlock(20, true, "blue");
        p[5][3] = new ColorBlock(20, true, "blue");
        p[5][6] = new ColorBlock(20, true, "blue");
        p[6][3] = new ColorBlock(20, true, "blue");
        p[6][6] = new ColorBlock(20, true, "blue");

        p[1][5] = new ColorBlock(20, true, "green");
        p[1][6] = new ColorBlock(20, true, "green");
        p[2][5] = new ColorBlock(20, true, "green");
        p[2][6] = new ColorBlock(20, true, "green");
        p[3][1] = new ColorBlock(20, true, "green");
        p[3][2] = new ColorBlock(20, true, "green");
        p[5][1] = new ColorBlock(20, true, "green");
        p[5][2] = new ColorBlock(20, true, "green");
        p[6][1] = new ColorBlock(20, true, "green");
        p[6][2] = new ColorBlock(20, true, "green");

        p[3][5] = new ColorBlock(20, true, "purple");
        p[3][6] = new ColorBlock(20, true, "purple");
        p[4][5] = new ColorBlock(20, true, "purple");
        p[4][6] = new ColorBlock(20, true, "purple");

        p[2][2] = new ColorBlock(20, true, "red");
        p[2][3] = new ColorBlock(20, true, "red");
        p[2][4] = new ColorBlock(20, true, "red");
        p[3][2] = new ColorBlock(20, true, "red");
        p[3][3] = new ColorBlock(20, true, "red");
        p[3][4] = new ColorBlock(20, true, "red");
        p[4][2] = new ColorBlock(20, true, "red");
        p[4][3] = new ColorBlock(20, true, "red");
        p[4][4] = new ColorBlock(20, true, "red");

        boolean[][] visible = new boolean[7][7];
        for (boolean[] line : visible) {
            Arrays.fill(line, true);
        }
        Board b = new Board(p, visible);
        int[] obj = {2000, 2700, 3000};
        return new LevelLimitedPieces(0, b, null, null, 0, 0, null, 2, obj, 42);
    }

    public static LevelLimitedPieces level2() {
        Piece[][] p = new Piece[7][5];
        p[1][0] = new Bee(1000);
        p[1][2] = new Bee(1000);
        p[0][2] = new Bee(1000);
        p[2][2] = new Bee(1000);
        p[1][4] = new Bee(1000);

        p[2][0] = new ColorBlock(20, true, "red");
        p[2][4] = new ColorBlock(20, true, "red");
        p[3][0] = new ColorBlock(20, true, "red");
        p[3][4] = new ColorBlock(20, true, "red");
        p[3][2] = new ColorBlock(20, true, "red");
        p[4][2] = new ColorBlock(20, true, "red");
        p[5][2] = new ColorBlock(20, true, "red");
        p[6][2] = new ColorBlock(20, true, "red");

        p[2][1] = new ColorBlock(20, true, "purple");
        p[2][3] = new ColorBlock(20, true, "purple");
        p[3][1] = new ColorBlock(20, true, "purple");
        p[3][3] = new ColorBlock(20, true, "purple");
        p[4][0] = new ColorBlock(20, true, "purple");
        p[4][4] = new ColorBlock(20, true, "purple");
        p[5][0] = new ColorBlock(20, true, "purple");
        p[5][4] = new ColorBlock(20, true, "purple");
        p[6][1] = new ColorBlock(20, true, "purple");
        p[6][3] = new ColorBlock(20, true, "purple");

        p[0][1] = new ColorBlock(20, true, "blue");
        p[0][3] = new ColorBlock(20, true, "blue");
        p[1][1] = new ColorBlock(20, true, "blue");
        p[1][3] = new ColorBlock(20, true, "blue");
        p[4][1] = new ColorBlock(20, true, "blue");
        p[4][3] = new ColorBlock(20, true, "blue");
        p[5][1] = new ColorBlock(20, true, "blue");
        p[5][3] = new ColorBlock(20, true, "blue");
        p[6][0] = new ColorBlock(20, true, "blue");
        p[6][4] = new ColorBlock(20, true, "blue");

        boolean[][] visible = new boolean[5][8];
        for (boolean[] line : visible) {
            Arrays.fill(line, true);
        }
        Board b = new Board(p, visible);
        int[] obj = {27000, 30000, 35000};
        return new LevelLimitedPieces(1, b, null, null, 0, 0, null, 5, obj, 28);
    }

    public static LevelLimitedPieces level3() {
        Piece[][] p = new Piece[9][7];

        p[0][2] = new Bee(1000);
        p[0][3] = new Bee(1000);
        p[0][4] = new Bee(1000);

        p[1][4] = new ColorBlock(20, true, "yellow");
        p[1][6] = new ColorBlock(20, true, "yellow");
        p[2][4] = new ColorBlock(20, true, "yellow");
        p[2][6] = new ColorBlock(20, true, "yellow");
        p[3][3] = new ColorBlock(20, true, "yellow");
        p[4][0] = new ColorBlock(20, true, "yellow");
        p[4][3] = new ColorBlock(20, true, "yellow");
        p[5][2] = new ColorBlock(20, true, "yellow");
        p[5][4] = new ColorBlock(20, true, "yellow");
        p[5][6] = new ColorBlock(20, true, "yellow");
        p[6][2] = new ColorBlock(20, true, "yellow");
        p[6][4] = new ColorBlock(20, true, "yellow");
        p[6][6] = new ColorBlock(20, true, "yellow");
        p[7][0] = new ColorBlock(20, true, "yellow");
        p[7][1] = new ColorBlock(20, true, "yellow");
        p[7][3] = new ColorBlock(20, true, "yellow");
        p[8][0] = new ColorBlock(20, true, "yellow");
        p[8][3] = new ColorBlock(20, true, "yellow");

        p[1][2] = new ColorBlock(20, true, "purple");
        p[2][2] = new ColorBlock(20, true, "purple");
        p[3][4] = new ColorBlock(20, true, "purple");
        p[3][6] = new ColorBlock(20, true, "purple");
        p[4][4] = new ColorBlock(20, true, "purple");
        p[4][6] = new ColorBlock(20, true, "purple");
        p[5][0] = new ColorBlock(20, true, "purple");
        p[5][3] = new ColorBlock(20, true, "purple");
        p[5][5] = new ColorBlock(20, true, "purple");
        p[6][0] = new ColorBlock(20, true, "purple");
        p[6][1] = new ColorBlock(20, true, "purple");
        p[6][3] = new ColorBlock(20, true, "purple");
        p[6][5] = new ColorBlock(20, true, "purple");

        p[1][3] = new ColorBlock(20, true, "green");
        p[1][5] = new ColorBlock(20, true, "green");
        p[2][3] = new ColorBlock(20, true, "green");
        p[2][5] = new ColorBlock(20, true, "green");
        p[3][2] = new ColorBlock(20, true, "green");
        p[3][5] = new ColorBlock(20, true, "green");
        p[4][1] = new ColorBlock(20, true, "green");
        p[4][2] = new ColorBlock(20, true, "green");
        p[4][5] = new ColorBlock(20, true, "green");
        p[5][1] = new ColorBlock(20, true, "green");
        p[7][2] = new ColorBlock(20, true, "green");
        p[7][4] = new ColorBlock(20, true, "green");
        p[8][1] = new ColorBlock(20, true, "green");
        p[8][2] = new ColorBlock(20, true, "green");
        p[8][4] = new ColorBlock(20, true, "green");

        boolean[][] visible = new boolean[9][7];
        for (boolean[] line : visible) {
            Arrays.fill(line, true);
        }
        Board b = new Board(p, visible);
        int[] obj = {24000, 30000, 35000};
        return new LevelLimitedPieces(2, b, null, null, 0, 0, null, 3, obj, 46);
    }

    public static LevelLimitedPieces level4() {
        Piece[][] p = new Piece[8][8];

        p[0][4] = new Bee(1000);
        p[0][5] = new Bee(1000);
        p[0][6] = new Bee(1000);
        p[0][7] = new Bee(1000);

        p[1][3] = new ColorBlock(20, true, "blue");
        p[1][4] = new ColorBlock(20, true, "blue");
        p[1][6] = new ColorBlock(20, true, "blue");
        p[2][2] = new ColorBlock(20, true, "blue");
        p[2][4] = new ColorBlock(20, true, "blue");
        p[2][6] = new ColorBlock(20, true, "blue");
        p[3][1] = new ColorBlock(20, true, "blue");
        p[3][2] = new ColorBlock(20, true, "blue");
        p[5][4] = new ColorBlock(20, true, "blue");
        p[6][2] = new ColorBlock(20, true, "blue");
        p[6][4] = new ColorBlock(20, true, "blue");
        p[7][0] = new ColorBlock(20, true, "blue");
        p[7][2] = new ColorBlock(20, true, "blue");

        p[1][7] = new ColorBlock(20, true, "yellow");
        p[2][7] = new ColorBlock(20, true, "yellow");
        p[3][3] = new ColorBlock(20, true, "yellow");
        p[3][5] = new ColorBlock(20, true, "yellow");
        p[4][0] = new ColorBlock(20, true, "yellow");
        p[4][2] = new ColorBlock(20, true, "yellow");
        p[4][5] = new ColorBlock(20, true, "yellow");
        p[5][2] = new ColorBlock(20, true, "yellow");
        p[5][5] = new ColorBlock(20, true, "yellow");
        p[6][0] = new ColorBlock(20, true, "yellow");
        p[6][1] = new ColorBlock(20, true, "yellow");
        p[6][2] = new ColorBlock(20, true, "yellow");
        p[7][1] = new ColorBlock(20, true, "yellow");
        p[7][3] = new ColorBlock(20, true, "yellow");

        p[1][5] = new ColorBlock(20, true, "purple");
        p[2][5] = new ColorBlock(20, true, "purple");
        p[3][4] = new ColorBlock(20, true, "purple");
        p[3][6] = new ColorBlock(20, true, "purple");
        p[3][7] = new ColorBlock(20, true, "purple");
        p[4][1] = new ColorBlock(20, true, "purple");
        p[4][3] = new ColorBlock(20, true, "purple");
        p[4][4] = new ColorBlock(20, true, "purple");
        p[4][6] = new ColorBlock(20, true, "purple");
        p[5][0] = new ColorBlock(20, true, "purple");
        p[5][1] = new ColorBlock(20, true, "purple");
        p[5][3] = new ColorBlock(20, true, "purple");

        boolean[][] visible = new boolean[8][8];
        for (boolean[] line : visible) {
            Arrays.fill(line, true);
        }
        Board b = new Board(p, visible);
        int[] obj = {25000, 30000, 350000};
        return new LevelLimitedPieces(3, b, null, null, 0, 0, null, 4, obj, 39);
    }

}
