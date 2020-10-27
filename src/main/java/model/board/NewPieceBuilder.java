package model.board;

import model.board.piece.*;

/** Builds a Piece based on the Level's restrictions */
public class NewPieceBuilder {
    /** The available colors for the ColorBlock in the Level */
    private final String[] availableColors;
    /** The probabilities for each color : must be natural integers that add up to 100 */
    private final int[] probabilitiesColors;
    /**
     * The available Pieces in this Level
     * availablePieces[i][0] = the name of the Piece
     * availablePieces[i][1] = the number of points for that Piece
     */
    private final String[][] availablePieces;
    /** The probability of getting each Piece in the Level : must be natural integers that add up to 100*/
    private final int[] probabilitiesPiece;

    /**
     * Constructor for NewPieceBuilder
     * @param availableColors       A String array that contains all the colors available for the Level
     * @param availablePieces       A String array that contains the names of all the Pieces available on the Level,
     *                              and the number of points associated with each
     * @param probabilitiesColors   An integer array that contains the probabilities associated with each color
     * @param probabilitiesPiece    An integer array that contains the probabilities associated with each Piece
     */
    public NewPieceBuilder(String[] availableColors, String[][] availablePieces,
                           int[] probabilitiesColors, int[] probabilitiesPiece) {
        this.probabilitiesColors = probabilitiesColors;
        this.availablePieces = availablePieces;
        this.availableColors = availableColors;
        this.probabilitiesPiece = probabilitiesPiece;
    }

    /** @return a Piece based on the Level's restrictions and probabilities */
    public Piece getNewPiece() {
        String color = availableColors[getColor()];
        int indexPiece = getPiece();
        String piece = availablePieces[indexPiece][0];
        int points = Integer.parseInt(availablePieces[indexPiece][1]);
        return switch (piece) {
            case "Bee" -> new Bee(points, "blue");
            case "Bomb" -> new Bomb(points, false);
            case "ColorBlock" -> new ColorBlock(points, false, color);
            case "EraseColorBlocks" -> new EraseColorBlocks(points, false, color);
            default -> null;
        };
    }

    /** @return the index of a randomly chosen color (weighted with the probabilities) in the availableColors array */
    public int getColor() {
        int random = getRandom();
        int sum = 0;
        for (int i = 0; i < probabilitiesColors.length; i++) {
            sum += probabilitiesColors[i];
            if (random <= sum) return i;
        }
        return -1; // should never be reached
    }

    /** @return the index of a randomly chosen color (weighted with the probabilities) in the availablePiece array */
    public int getPiece() {
        int random = getRandom();
        int sum = 0;
        for (int i = 0; i < probabilitiesPiece.length; i++) {
            sum += probabilitiesPiece[i];
            if (random <= sum) return i;
        }
        return -1; // should never be reached
    }

    /** @return a random number between 1 (included) and 100 (excluded) */
    private int getRandom() {
        return (int) (Math.random() + 1) * 100;
    }

}
