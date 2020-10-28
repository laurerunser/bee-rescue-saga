package model.board.piece;


import model.board.Board;

public class Bomb extends Piece {
    private final static String iconPath = "";
    private final static String trappedIconPath = "";

    /**
     * Constructs a Bomb
     *
     * @param points The coefficient applied to the points won from deleting the surrounding Pieces
     * @param isFree True if the Piece is free, false otherwise
     */
    public Bomb(int points, boolean isFree) {
        super(points, isFree);
    }

    /**
     * A Bomb deletes all 8 Pieces around it if there are free.
     * Can't delete a Bee but can free it.
     * If there is another Bomb, makes it explode too. In this case, the method first deletes/frees/does nothing on all
     * the other Pieces around, and then explodes the other Bombs in order from left to right, from top to bottom.
     *
     * @param x     The x-coordinate of the Bomb
     * @param y     The y-coordinate of the Bomb
     * @param board The board
     * @return the number of Points won
     */
    @Override
    public int delete(Board board, int x, int y) {
        int coefficient = getPoints(); // get the coefficient before deleting this
        board.getBoard()[x][y] = null; // delete this from the board
        int pointsWon = 0;
        int[][] coordinates = new int[9][];
        getPiecesToDelete(board, x, y, coordinates);

        int[][] otherBombsCoordinates = new int[9][];
        for (int i = 0; i < coordinates.length; i++) { // try to delete or free each Piece
            if (coordinates[i] != null) {
                int pointsPiece = deletePiece(board.getBoard(), coordinates[i][0], coordinates[i][1]);
                if (pointsPiece == -1) {
                    otherBombsCoordinates[i] = new int[]{coordinates[i][0], coordinates[i][1]};
                } else {
                    pointsWon += pointsPiece;
                }
            }
        }
        for (int[] bCoordinates : otherBombsCoordinates) { // explode any extra bomb
            if (bCoordinates != null) {
                int bombX = bCoordinates[0];
                int bombY = bCoordinates[1];
                pointsWon += board.getBoard()[bombX][bombY].delete(board, bombX, bombY);
            }
        }
        return pointsWon * coefficient; // apply the coefficient from this
    }

    /**
     * Gets all 8 Pieces surrounding the Bomb.
     * Makes sure the Pieces are within the limits of the Board.
     * Be careful, toDelete can contain null.
     *
     * @param board       The board
     * @param x           The x-coordinate of the Bomb
     * @param y           The y-coordinate of the Bomb
     * @param coordinates The array in which to put the coordinates of the Pieces to delete
     */
    private void getPiecesToDelete(Board board, int x, int y, int[][] coordinates) {
        if (board.isInsideBoard(x - 1, y - 1)) {
            coordinates[0] = new int[]{x - 1, y - 1};
        }
        if (board.isInsideBoard(x - 1, y)) {
            coordinates[1] = new int[]{x - 1, y};
        }
        if (board.isInsideBoard(x - 1, y + 1)) {
            coordinates[2] = new int[]{x - 1, y + 1};
        }
        if (board.isInsideBoard(x, y - 1)) {
            coordinates[3] = new int[]{x, y - 1};
        }
        if (board.isInsideBoard(x, y + 1)) {
            coordinates[4] = new int[]{x, y + 1};
        }
        if (board.isInsideBoard(x + 1, y - 1)) {
            coordinates[5] = new int[]{x + 1, y - 1};
        }
        if (board.isInsideBoard(x + 1, y)) {
            coordinates[6] = new int[]{x + 1, y};
        }
        if (board.isInsideBoard(x + 1, y + 1)) {
            coordinates[7] = new int[]{x + 1, y + 1};
        }
    }

    /**
     * Deletes a Piece or sets if free, or makes it explode if it is a Bomb
     *
     * @param board The board
     * @param x     The x-coordinate of the Piece to delete
     * @param y     The y-coordinate of the Piece to delete
     * @return the number of points won from deleting the Piece, or -1 if it is a Bomb to explode
     */
    private int deletePiece(Piece[][] board, int x, int y) {
        int pointsPiece = 0;
        if (board[x][y] == null || board[x][y] instanceof Decor) {
            return 0; // can't delete if it doesn't exist or if it is Decor
        } else if (!board[x][y].isFree()) {
            board[x][y].setFree();
            return 0; // set the Piece free doesn't give any points
        } else if (board[x][y] instanceof Bomb) {
            return -1; // there is a Bomb to explode
        } else if (board[x][y] instanceof Bee) {
            return 0; // can't delete a Bee; needs to be here because a trapped Bee should be set free
        }
        pointsPiece = board[x][y].getPoints();
        board[x][y] = null;
        return pointsPiece;
    }
}
