package model.board;

import junit.framework.TestCase;
import model.board.piece.Bee;
import model.board.piece.ColorBlock;
import model.board.piece.Piece;

public class NewPieceBuilderTest extends TestCase {

    public void testGetNewPiece() {
        String[] colors = {"blue", "red"};
        int[] probabilitiesColors = {20, 80};
        String[][] pieces = {{"Bee", "2"}, {"ColorBlock", "4"}};
        int[] probabilitiesPieces = {30, 70};
        NewPieceBuilder n = new NewPieceBuilder(colors, pieces, probabilitiesColors, probabilitiesPieces);

        Piece p = n.getNewPiece();
        assertTrue(p instanceof Bee || p instanceof ColorBlock);
        String color;
        if (p instanceof Bee) {
            color = ((Bee) p).getColor();
        } else {
            color = ((ColorBlock) p).getColor();
        }
        assertTrue(color.equals("red") || color.equals("blue"));
        assertTrue(p.getPoints() == 2 || p.getPoints() == 4);
    }
}