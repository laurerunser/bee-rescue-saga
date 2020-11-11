package utils.parsers;

import model.board.Board;
import model.board.NewPieceBuilder;
import model.board.piece.*;
import model.bonus.*;
import model.level.*;

import java.io.InputStream;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parses a Level from a file in resources.
 * See level-structure.md in resources directory to see how such a file is written.
 */
public class LevelParser {
    //TODO : test the parser !!

    /**
     * Parses the level in a file named "levelN"
     *
     * @param map The LevelMap to put the Levels into
     * @param N   The number of the level to parse
     */
    public void parseLevel(LevelMap map, int N, Map<Bonus, Integer> availableBonus) {
        String name = "levels/level" + N;
        InputStream is = getClass().getClassLoader().getResourceAsStream(name);
        if (is == null) {
            System.out.println("Erreur lors de l'ouverture du fichier");
            System.exit(1);
        }
        Scanner sc = new Scanner(is);
        map.addLevel(Objects.requireNonNull(createLevel(sc, availableBonus)));
        sc.close();
    }

    /**
     * Parses the file and makes a Level
     *
     * @param sc             The Scanner containing the file to parse
     * @param availableBonus A Map of the Bonus the Player has
     */
    private Level createLevel(Scanner sc, Map<Bonus, Integer> availableBonus) {
        int[] levelAndDimensions = readDim(sc.next());
        int[] typeAndValueLeft = readType(sc.next());
        int amountLimited = readAmount(sc.next());
        // parse freeBonus
        String[] tmp = sc.nextLine().split(" ");
        Bonus freeBonus = readBonus(tmp[0]);
        int[] freeBonusAmount = {0, 0};
        if (freeBonus != null) freeBonusAmount = readFreeBonusAmount(tmp);
        int[] objectives = readObjectives(sc);
        int[] scoreObj = {objectives[1], objectives[2], objectives[3]};
        Board board = readBoard(sc, levelAndDimensions);
        NewPieceBuilder builder = readPieceBuilder(sc.next(), sc.next(), sc.next());

        switch (typeAndValueLeft[0]) {
            case 1:
                return new LevelLimitedMoves(levelAndDimensions[0], board, availableBonus, freeBonus, freeBonusAmount[0], freeBonusAmount[1],
                                             builder, objectives[0], scoreObj, amountLimited, typeAndValueLeft[1]);
            case 2:
                return new LevelLimitedPieces(levelAndDimensions[0], board, availableBonus, freeBonus, freeBonusAmount[0], freeBonusAmount[1],
                                              builder, objectives[0], scoreObj, amountLimited);
            case 3:
                return new LevelLimitedTime(levelAndDimensions[0], board, availableBonus, freeBonus, freeBonusAmount[0], freeBonusAmount[1],
                                            builder, objectives[0], scoreObj, amountLimited, typeAndValueLeft[1]);
            case 4:
                // TODO : constructor for unlimited Level
        }
        return null; // unreachable
    }

    //TODO : make parsing exceptions and catch them instead of ending the program like a brute
    //TODO : check that the line is not null before trying to split it

    /**
     * Reads and returns the dimensions of the Board. Ends the program if can't parse the line
     *
     * @param line The line to read
     * @return - {l, x, y} where l = the nb of the Level and (x,y) = the dimensions of the Board , or
     * - {l, x, y, a, b} where l = the nb of the Level, (x,y) = dimensions of the Board and (a, b) = dimensions of the visible part of the Board
     */
    private int[] readDim(String line) {
        String[] str = line.split(" ");

        if (str.length == 3) {
            return new int[]{Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2])};
        } else if (str.length == 5) {
            return new int[]{Integer.parseInt(str[0]), Integer.parseInt(str[1]), Integer.parseInt(str[2]), Integer.parseInt(str[3]), Integer.parseInt(str[4])};
        } else {
            System.out.println("ERROR WHILE PARSING THE FILE");
            System.exit(1);
            return null;
        }
    }

    /**
     * Reads the type of the Level and the value associated with the leftover pieces/moves/seconds.
     * The type of the Level is :
     * - 1 : moves
     * - 2 : pieces
     * - 3 : time
     * - 4 : unlimited
     *
     * @param line The line to read
     * @return {the type of the level, the nb of points per move/piece left}
     */
    private int[] readType(String line) {
        String[] str = line.split(" ");
        int[] result = new int[2];
        switch (str[0]) {
            case "moves" -> result[0] = 1;
            case "pieces" -> result[0] = 2;
            case "time" -> result[0] = 3;
            case "unlimited" -> result[0] = 4;
        }
        if (result[0] == 0 || (str.length < 2 && result[0] != 4)) {
            System.out.println("ERROR WHILE PARSING THE FILE");
            System.exit(1);
        }
        if (result[0] != 4) { // 4 is unlimited so doesn't have a number of points for leftover stuff
            result[1] = Integer.parseInt(str[1]);
        }
        return result;
    }

    /**
     * @param line The line to read
     * @return the amount of the limited quantity (moves or time, pieces is treated in another way)
     */
    private int readAmount(String line) {
        if (line.equals("none")) return 0;
        else return Integer.parseInt(line);
    }

    /**
     * @param value The String to evaluate
     * @return a Bonus corresponding to the name in the String
     */
    private Bonus readBonus(String value) {
        return switch (value) {
            case "ChangeBlockColor" -> new ChangeBlockColor();
            case "EraseBlock" -> new EraseBlock();
            case "EraseColor" -> new EraseColor();
            case "EraseColumn" -> new EraseColumn();
            case "FreeBee" -> new FreeBee();
            case "FreeBlock" -> new FreeBlock();
            default -> null;
        };
    }

    /**
     * If the free bonus is infinite, the nb of free bonus will be -1.
     * If the free bonus is not infinite, the nb of moves will be 0.
     *
     * @param values The String[] to evaluate
     * @return {nb of free bonus, nb of moves to replenish it}
     */
    private int[] readFreeBonusAmount(String[] values) {
        if (values[1].equals("infinite")) {
            return new int[]{-1, Integer.parseInt(values[2])};
        } else {
            return new int[]{Integer.parseInt(values[1]), 0};
        }
    }

    /**
     * Parses 3 lines to read the objectives of the Level
     *
     * @param sc The scanner to read the lines
     * @return {nb of Bees, score for 1 star, score for 2, score for 3}
     */
    private int[] readObjectives(Scanner sc) {
        int[] objectives = new int[4];
        String[] tmp = sc.next().split(" ");
        objectives[0] = Integer.parseInt(tmp[0]);
        objectives[1] = Integer.parseInt(tmp[1]);
        objectives[2] = Integer.parseInt(sc.next());
        objectives[3] = Integer.parseInt(sc.next());
        return objectives;
    }

    /**
     * Constructs a Board from the file
     *
     * @param sc         The Scanner to read the lines from
     * @param dimensions The dimensions. See readDim() for the contents of this array
     * @return a Board
     */
    private Board readBoard(Scanner sc, int[] dimensions) {
        int width = dimensions[1];
        int height = dimensions[2];
        if (!sc.next().equals("[")) {
            System.out.println("ERROR WHILE PARSING");
            System.exit(1);
        }
        int i = 0;
        String line = sc.nextLine();
        Piece[][] pieces = new Piece[width][height];
        boolean[][] visible = new boolean[width][height];
        while (!line.equals("]")) {
            String[] strPieces = line.split(" ");
            int v = (dimensions.length == 4) ? dimensions[3] : -1; // part until when it is visible
            for (int j = 0; j < strPieces.length; j++) {
                String p = strPieces[j];
                if (p.equals("none")) {
                    pieces[i][j] = null;
                    visible[i][j] = false;
                } else if (p.equals("visible")) {
                    pieces[i][j] = null;
                    visible[i][j] = true;
                } else {
                    p = p.substring(1, p.length() - 1); // remove ()
                    pieces[i][j] = readPiece(p);
                    visible[i][j] = j > v; // visible only if not under the limit
                }
            }
            line = sc.nextLine();
            i++;
        }
        return new Board(pieces, visible);
    }

    /**
     * Constructs a NewPieceBuilder for the Level
     *
     * @param piecesStr The String containing the name of the available pieces and their probability
     * @param pointsStr The String containing the points corresponding to the available pieces
     * @param colorsStr The String containing the available colors and their probabilities
     * @return a NewPieceBuilder
     */
    public NewPieceBuilder readPieceBuilder(String piecesStr, String pointsStr, String colorsStr) {
        if (piecesStr.equals("none") || pointsStr.equals("none") || colorsStr.equals("none")) return null;

        String[] tmp = piecesStr.split(" ");
        String[] tmpPoints = pointsStr.split(" ");
        Pattern p = Pattern.compile("[0-9]"); // numbers
        Pattern p2 = Pattern.compile("^[0-9]"); // everything but numbers
        // parse pieces
        String[][] pieces = new String[tmp.length][2];
        int[] piecesProba = new int[tmp.length];
        for (int i = 0; i < tmp.length; i++) {
            Matcher m = p.matcher(tmp[i]);
            piecesProba[i] = Integer.parseInt(m.group());
            Matcher m2 = p2.matcher(tmp[i]);
            pieces[i][0] = m2.group();
            pieces[i][1] = tmpPoints[i];
        }
        // parse colors
        String[] tmp2 = colorsStr.split(" ");
        String[] colors = new String[tmp2.length];
        int[] colorsProba = new int[tmp2.length];
        for (int i = 0; i < tmp2.length; i++) {
            Matcher m = p.matcher(tmp2[i]);
            colorsProba[i] = Integer.parseInt(m.group());
            Matcher m2 = p2.matcher(tmp2[i]);
            colors[i] = m2.group();
        }
        return new NewPieceBuilder(colors, pieces, colorsProba, piecesProba);
    }

    /**
     * Constructs a Piece from the String. See the level-structure.me file in resources.
     *
     * @param piece The String to read
     * @return a Piece of the right type filled with the correct options
     */
    private Piece readPiece(String piece) {
        String[] args = piece.split(" ");
        switch (args[0]) {
            case "Bee":
                if (args[2].equals("visible")) { // free Bee
                    return new Bee(Integer.parseInt(args[1]));
                } else { // trapped Bee
                    return new Bee(Integer.parseInt(args[1]), args[2]);
                }
            case "Bomb":
                return new Bomb(Integer.parseInt(args[1]), args[2].equals("true"));
            case "ColorBlock":
                return new ColorBlock(Integer.parseInt(args[1]), args[2].equals("true"), args[3]);
            case "Decor":
                return new Decor();
            case "EraseColorBlock":
                return new EraseColorBlocks(Integer.parseInt(args[1]), args[2].equals("true"), args[3]);
            default:
                System.out.println("ERROR");
                System.exit(1);
        }
        return null; // unreachable
    }

}

