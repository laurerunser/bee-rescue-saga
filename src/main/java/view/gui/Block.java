package view.gui;

import controller.listeners.PlayerMovesListeners;
import model.board.piece.Piece;

import javax.swing.*;
import java.awt.*;


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
        this.addActionListener(actionEvent -> {
            playerMovesListeners.onPieceClicked(x, y);
        });

        initIcon(piece);
        this.setText(x + " " + y);

        //TODO add animation for the Bee (from Bee-happy.png to Bee-sad).png

        // other cosmetic stuff
        this.setPreferredSize(new Dimension(60, 60));
    }

    private void initIcon(Piece piece) {
        if (piece != null) {
            String path = piece.getCurrentIconPath();
            System.out.println(piece.getCurrentIconPath());
            ImageIcon i = new ImageIcon(getClass().getClassLoader().getResource(path));
            this.setIcon(i);
        }
    }

}
