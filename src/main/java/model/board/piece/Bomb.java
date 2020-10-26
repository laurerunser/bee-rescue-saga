package model.board.piece;


import model.board.Board;

public class Bomb extends Piece implements PieceBonus {
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
     *
     * @param x     The x-coordinate of the Bomb
     * @param y     The y-coordinate of the Bomb
     * @param board The board
     * @return the number of Points won
     */
    @Override
    public int delete(Board board, int x, int y) {
        int pointsWon = 0;
        Piece[] toDelete = new Piece[9];
        int[][] coordinates = new int[9][];
        if (board.isInsideBoard(x - 1, y - 1)) {
            toDelete[0] = board.getBoard()[x - 1][y - 1];
            coordinates[0] = new int[]{x - 1, y - 1};
        }
        if (board.isInsideBoard(x - 1, y)) {
            toDelete[1] = board.getBoard()[x - 1][y];
            coordinates[1] = new int[]{x - 1, y};
        }
        if (board.isInsideBoard(x - 1, y + 1)) {
            toDelete[2] = board.getBoard()[x - 1][y + 1];
            coordinates[2] = new int[]{x - 1, y + 1};
        }
        if (board.isInsideBoard(x, y - 1)) {
            toDelete[3] = board.getBoard()[x][y - 1];
            coordinates[3] = new int[]{x, y - 1};
        }
        if (board.isInsideBoard(x, y + 1)) {
            toDelete[4] = board.getBoard()[x][y + 1];
            coordinates[4] = new int[]{x, y + 1};
        }
        if (board.isInsideBoard(x + 1, y - 1)) {
            toDelete[5] = board.getBoard()[x + 1][y - 1];
            coordinates[5] = new int[]{x + 1, y - 1};
        }
        if (board.isInsideBoard(x + 1, y)) {
            toDelete[6] = board.getBoard()[x + 1][y];
            coordinates[6] = new int[]{x + 1, y};
        }
        if (board.isInsideBoard(x + 1, y + 1)) {
            toDelete[7] = board.getBoard()[x + 1][y + 1];
            coordinates[7] = new int[]{x + 1, y + 1};
        }

        for (int i = 0; i < toDelete.length; i++) { // try to delete or free each Piece
            if (toDelete[i] != null) {
                pointsWon += deletePiece(toDelete[i], board, coordinates[i][0], coordinates[i][1]);
            }
        }
        board.getBoard()[x][y] = null; // delete this from the board
        return pointsWon * getPoints(); // apply the coefficient from this
    }

    /**
     * Deletes a Piece or sets if free, or makes it explode if it is a Bomb
     *
     * @param p     The Piece to delete
     * @param board The board
     * @param x     The x-coordinate of the Piece to delete
     * @param y     The y-coordinate of the Piece to delete
     * @return the number of points won from deleting the Piece
     */
    private int deletePiece(Piece p, Board board, int x, int y) {
        int pointsWon = 0;
        if (p == null || p instanceof Decor) {
            return 0;
        } else if (!p.isFree()) {
            p.setFree();
            return 0;
        } else if (p instanceof Bomb) {
            pointsWon = p.delete(board, x, y);
        } else {
            pointsWon = board.getBoard()[x][y].getPoints();
            board.getBoard()[x][y] = null;
        }
        return pointsWon;
    }
}
