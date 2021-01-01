package view.gui;

import controller.listeners.PlayerMovesListeners;
import model.board.piece.Bee;
import model.board.piece.Piece;

import javax.swing.*;


public class Block extends JButton {

    /**
     * Constructs a Block
     *
     * @param x                    The x-coordinate on the board
     * @param y                    The y-coordinate on the board
     * @param playerMovesListeners The list of listeners to fire the event from
     * @param piece                The piece that is being represented by the Block
     */
    public Block(int x, int y, PlayerMovesListeners playerMovesListeners, Piece piece) {
        this.addActionListener(actionEvent -> playerMovesListeners.onPieceClicked(x, y));

        if (piece instanceof Bee) {
            initBee();
        } else {
            initBlock(piece);
        }
    }

    /**
     * Initializes the Block if the represented Piece is a Bee.
     * Creates an animation where the picture of the Bee moves.
     */
    private void initBee() {

    }

    /**
     * Normal initialization without any animation
     */
    private void initBlock(Piece piece) {

    }


}
