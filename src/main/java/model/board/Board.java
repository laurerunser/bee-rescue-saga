package model.board;

import model.board.piece.*;


public class Board {
    /** The array representing the board of the level */
    private Piece[][] board;
    /** The array representing the visible portion of the board */
    private boolean[][] visible;

    /** @return the board */
    public Piece[][] getBoard() {
        return board;
    }

    /**
     * Constructs a Board
     * @param board the Piece[][] representing the board
     */
    public Board(Piece[][] board) {
        this.board = board;
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
     * @param x The x-coordinate
     * @param y The y-coordinate
     * @return true if the place is empty, false otherwise
     */
    public boolean isEmpty(int x, int y) {
        return board[x][y] == null;
    }

    /**
     * Looks at each Piece and sees if there is a possible move left
     *
     * @return true if there is a move left, false otherwise
     */
    public boolean hasMoveLeft() {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; i++) {
                if (board[i][j] instanceof Decor) continue; // not a Piece you can play
                if (board[i][j] instanceof PieceBonus) return true; // a PieceBonus can always be played
                if (board[i][j] instanceof ColorBlock) {
                    if (isAColorMove(i, j))
                        return true; // the block is part of a set of the same color that can be deleted
                }
            }
        }
        return false;
    }

    /**
     * Checks if the ColorBlock is part of a set of the same color that can be deleted
     *
     * @param x The x-coordinate of the current ColorBlock
     * @param y The y-coordinate of the current ColorBlock
     * @return true if there is a move, false otherwise
     */
    private boolean isAColorMove(int x, int y) {
        ColorBlock current = isAColorBlock(x, y);
        if (current == null) return false; // the block is not a ColorBlock
        ColorBlock up = isAColorBlock(x, y + 1);
        if (up != null && current.getColor().equals(up.getColor())) return true;
        ColorBlock down = isAColorBlock(x, y - 1);
        if (down != null && current.getColor().equals(down.getColor())) return true;
        ColorBlock left = isAColorBlock(x - 1, y);
        if (left != null && current.getColor().equals(left.getColor())) return true;
        ColorBlock right = isAColorBlock(x + 1, y);
        if (right != null && current.getColor().equals(right.getColor())) return true;
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
     * @return {a, b, c} :  a = the nb of Bee saved,
     * b = 1 if change was made, 0 otherwise
     * c = the nb of empty fields in the top row
     */
    public int[] updateBoard() {
        int[] result = new int[3];
        result[0] = deleteBees();
        boolean change = fillEmptySpaces();
        if (change) result[1] = 1;
        else result[1] = 0;
        // count how many empty fields in the top row
        for (int j = 0; j < board[0].length; j++) {
            if (isEmpty(0, j)) result[2] += 1;
        }
        return result;
    }

    /**
     * Deletes all the Bees that are on the lowest row if they are free
     *
     * @return the number of deleted bees
     */
    private int deleteBees() {
        int nbDeleted = 0;
        for (int j = 0; j < board[board.length - 1].length; j++) {
            if (board[board.length - 1][j] instanceof Bee && board[board.length - 1][j].isFree()) {
                board[board.length - 1][j] = null;
                nbDeleted += 1;
            }
        }
        return nbDeleted;
    }

    /**
     * From the bottom, moves Pieces to fill the empty spaces.
     * An empty space can be filled with a Piece that is just above or in the above right corner.
     * Decor elements can't move.
     * @return true if a change was made, false otherwise
     */
    private boolean fillEmptySpaces() {
        boolean change = false;
        for (int i = board.length - 1; i >= 0; i--) {
            for (int j = board[board.length - 1].length; j >= 0; j--) {
                if (isEmpty(i, j)) {
                    if (isInsideBoard(i - 1, j)  // the Piece just above
                            && !isEmpty(i - 1, j)
                            && board[i - 1][j].isFree()
                            && !(board[i - 1][j] instanceof Decor)) {
                        board[i][j] = board[i - 1][j];
                        board[i - 1][j] = null;
                        change = true;
                    } else if (isInsideBoard(i - 1, j + 1) // the Piece above and right
                            && !isEmpty(i - 1, j + 1)
                            && board[i - 1][j + 1].isFree()
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
