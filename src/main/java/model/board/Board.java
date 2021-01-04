package model.board;

import model.board.piece.*;

import java.io.Serializable;

/**
 * Represents the board that the Player is playing with
 */
public class Board implements Serializable {
    private static final long serialVersionUID = 123L;

    /**
     * The array representing the board of the level
     */
    private final Piece[][] board;
    /**
     * The array representing the visible portion of the board
     */
    private final boolean[][] visible;

    /**
     * Constructs a Board
     *
     * @param board   the Piece[][] representing the board
     * @param visible a boolean[][] representing the visible portion of the board
     */
    public Board(Piece[][] board, boolean[][] visible) {
        this.board = board;
        this.visible = visible;
    }

    /**
     * Getter for the board
     *
     * @return the board
     */
    public Piece[][] getBoard() {
        return board;
    }

    /**
     * Deletes the Piece at coordinates [x,y] and those around it if necessary.
     * The method assumes the coordinate point to a valid place on the board
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return the number of points won from deleting that Piece
     */
    public int delete(int x, int y) {
        return board[x][y].delete(this, x, y);
    }

    /**
     * Adds the Piece in the correct place on the board if there is nothing else there
     *
     * @param p The Piece to add
     * @param x The x-coordinate
     * @param y The y-coordinate
     */
    public void add(Piece p, int x, int y) {
        if (isEmpty(x, y)) board[x][y] = p;
    }

    /**
     * Check if the field is empty
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return true if the place is empty, false otherwise
     */
    public boolean isEmpty(int x, int y) {
        if (!isInsideBoard(x, y)) return false;
        return board[x][y] == null;
    }

    /**
     * Looks at each Piece and sees if there is a possible move left
     *
     * @return true if there is a move left, false otherwise
     */
    public boolean hasMoveLeft() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (isAValidMove(i, j)) return true;
            }
        }
        return false;
    }

    /**
     * Returns true if the Piece at (x,y) is a valid move
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return true if the Piece is a valid move, false otherwise
     */
    public boolean isAValidMove(int x, int y) {
        if (board[x][y] == null) return false;
        else if (board[x][y] instanceof Decor) return false;
        else if (board[x][y] instanceof Bomb || board[x][y] instanceof EraseColorBlocks)
            return true; //they can always be played
        else if (board[x][y] instanceof ColorBlock && isAColorMove(x, y)) {
            return true; // the block is part of a set of the same color that can be deleted
        } else {
            return false;
        }
    }

    /**
     * Checks if the ColorBlock is part of a set of the same color that can be deleted
     *
     * @param x The x-coordinate of the current ColorBlock
     * @param y The y-coordinate of the current ColorBlock
     * @return true if there is a move, false otherwise
     */
    public boolean isAColorMove(int x, int y) {
        ColorBlock current = isAColorBlock(x, y);
        if (current == null) {
            return false; // the block is not a ColorBlock
        }
        ColorBlock up = isAColorBlock(x - 1, y);
        if (up != null && up.isFree() && current.getColor().equals(up.getColor())) {
            return true;
        }
        ColorBlock down = isAColorBlock(x + 1, y);
        if (down != null && down.isFree() && current.getColor().equals(down.getColor())) {
            return true;
        }
        ColorBlock left = isAColorBlock(x, y - 1);
        if (left != null && left.isFree() && current.getColor().equals(left.getColor())) {
            return true;
        }
        ColorBlock right = isAColorBlock(x, y + 1);
        if (right != null && right.isFree() && current.getColor().equals(right.getColor())) {
            return true;
        }
        return false;
    }

    /**
     * Checks if a block is a ColorBlock
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return the Piece casted into a ColorBlock, or null if the space is empty or not castable
     */
    public ColorBlock isAColorBlock(int x, int y) {
        if (isInsideBoard(x, y) && !isEmpty(x, y)) {
            try {
                return (ColorBlock) board[x][y];
            } catch (ClassCastException e) {
                return null;
            }
        }
        return null;
    }

    /**
     * Checks if a set of coordinates are on the board or outside
     *
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return true if the coordinates are on the board, false otherwise
     */
    public boolean isInsideBoard(int x, int y) {
        return !(x < 0 || x >= board.length || y < 0 || y >= board[x].length);
    }

    /**
     * Updates the board and moves the Pieces
     *
     * @return {a, b, c, d} where :
     * a = the nb of Bee saved,
     * b = the number of points won from the Bees
     * c = 1 if change was made, 0 otherwise
     * d = the nb of empty fields in the top row
     */
    public int[] updateBoard() {
        int[] result = new int[4];
        int[] tmp = deleteBees();
        result[0] = tmp[0];
        result[1] = tmp[1];
        boolean change = fillEmptySpaces();
        if (change) {
            result[2] = 1; // otherwise stays 0
        }
        while (change) {
            int[] tmp2 = deleteBees();
            result[0] += tmp2[0];
            result[1] += tmp2[1];
            change = fillEmptySpaces();
        } // do it over again until there is no more changes to do
        for (int j = 0; j < board[0].length; j++) { // count how many empty fields in the top row
            if (isEmpty(0, j)) result[3] += 1;
        }
        return result;
    }

    /**
     * Deletes all the Bees that are on the lowest row if they are free
     *
     * @return {the number of deleted bees, the number of points won}
     */
    private int[] deleteBees() {
        int nbDeleted = 0;
        int points = 0;
        for (int j = 0; j < board[board.length - 1].length; j++) {
            if (board[board.length - 1][j] instanceof Bee && board[board.length - 1][j].isFree()) {
                points += board[board.length - 1][j].getPoints();
                board[board.length - 1][j] = null;
                nbDeleted += 1;
            }
        }
        return new int[]{nbDeleted, points};
    }

    /**
     * From the bottom, moves Pieces to fill the empty spaces.
     * An empty space can be filled with a Piece that is just above or in the above right corner.
     * Decor elements can't move. Trapped Bee can move, but other trapped Pieces can't.
     *
     * @return true if a change was made, false otherwise
     */
    private boolean fillEmptySpaces() {
        // TODO : fix bug => when a column is empty, infinite loop
        boolean change = false;

        // move the empty columns
        for (int j = 0; j < board[0].length - 1; j++) {
            if (emptyColumn(j) && !emptyColumn(j + 1)) { // column empty
                moveColumnsLeft(j);
                change = true;
            }
        }

        // move the pieces
        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = 0; j < board[i].length; j++) {
                if (isEmpty(i, j)) {
                    if (isInsideBoard(i - 1, j)  // the Piece just above
                            && !isEmpty(i - 1, j)
                            && !(board[i - 1][j] instanceof Decor)
                            && (board[i - 1][j].isFree() || board[i - 1][j] instanceof Bee || board[i - 1][j] == null)) { // either a free Piece or a trapped Bee or null
                        board[i][j] = board[i - 1][j];
                        board[i - 1][j] = null;
                        change = true;
                    } else if (isInsideBoard(i - 1, j + 1) // the Piece above and right
                            && !isEmpty(i - 1, j + 1)
                            && (board[i - 1][j + 1].isFree() || board[i - 1][j + 1] instanceof Bee || board[i - 1][j + 1] == null) // either a free Piece or a trapped Bee or null
                            && !(board[i - 1][j + 1] instanceof Decor)) {
                        board[i][j] = board[i - 1][j + 1];
                        board[i - 1][j + 1] = null;
                        change = true;
                    }
                }
            }
        }

        return change;
    }

    /**
     * @param y The y-coordinate of the column
     * @return true if the column is empty, false otherwise
     */
    private boolean emptyColumn(int y) {
        for (int i = 0; i < board.length; i++) {
            if (!isEmpty(i, y)) return false;
        }
        return true;
    }

    /**
     * Move all the columns at the right of y one column to the left
     *
     * @param y The y-coordinate of the empty column to move to
     */
    private void moveColumnsLeft(int y) {
        for (int i = 0; i < board.length - 1; i++) {
            for (int j = y; j < board[i].length - 1; j++) {
                board[i][j] = board[i][j + 1];
                board[i][j + 1] = null;
            }
        }
    }

    /**
     * Tests that not all the columns to the right of column y are empty
     *
     * @param y The column to start at
     * @return false if all columns to the right are empty, true otherwise
     */
    private boolean notAllColumnsToTheRightEmpty(int y) {
        for (int i = y; i < board[0].length; i++) {
            if (!emptyColumn(i)) return true;
        }
        return false;
    }

    /**
     * Adds the new Piece on the upper-left empty space.
     * If no empty space, does nothing
     *
     * @param p The Piece to add
     */
    public void addOnTop(Piece p) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (isEmpty(i, j)) {
                    board[i][j] = p;
                    return;
                }
            }
        }
    }


}
