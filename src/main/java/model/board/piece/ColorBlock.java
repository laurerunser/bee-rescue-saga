package model.board.piece;

public class ColorBlock extends Piece {
    /**
     * The color of the block : yellow, red, green, blue, orange, purple
     */
    private final String color;

    public ColorBlock(String color, boolean free) {
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    @Override
    public void delete(Piece[][] board) {
        // TODO : write the delete method
    }
}
