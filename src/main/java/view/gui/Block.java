package view.gui;

import controller.listeners.PlayerMovesListeners;
import model.board.piece.*;

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
            this.setVisible(false);
            playerMovesListeners.onPieceClicked(x, y);
        });

        initIcon(piece);

        //TODO add animation for the Bee (from Bee-happy.png to Bee-sad).png

        // other cosmetic stuff
        this.setPreferredSize(new Dimension(65, 65));
        this.setBackground(new Color(237, 198, 63));
        this.setBorder(null);// empty border
    }

    private void initIcon(Piece piece) {
        // TODO : clean it up and go back to using the currentIconPath field in the class of the different pieces
        // The problem is that currentIconPath seems to be null, no matter how I initialize it...
//        if (piece != null) {
//            String path = piece.getCurrentIconPath();
//            ImageIcon i = new ImageIcon(getClass().getClassLoader().getResource("pictures/r1.png"));
//            this.setIcon(i);
//        }
        String path = "pictures/";
        if (piece instanceof ColorBlock) {
            if (piece.isFree()) {
                path = path + ((ColorBlock) piece).getColor().charAt(0) + random() + ".png";
            } else {
                path = path + "t" + ((ColorBlock) piece).getColor().charAt(0) + random() + ".png";
            }
        } else if (piece instanceof Bee) {
            if (piece.isFree()) {
                path = path + "Bee-happy.png";
            } else {
                path = path + "Bee-happy-" + ((Bee) piece).getColor().charAt(0) + ".png";
            }
        } else if (piece instanceof Bomb) {
            if (piece.isFree()) {
                path = path + "Bomb.png";
            } else {
                path = path + "tBomb.png";
            }
        } else if (piece instanceof Decor) {
            path = path + "Decor.png";
        } else if (piece instanceof EraseColorBlocks) {
            if (piece.isFree()) {
                path = path + "Ballon" + ((EraseColorBlocks) piece).getColor().charAt(0) + ".png";
            } else {
                path = path + "tBallon" + ((EraseColorBlocks) piece).getColor().charAt(0) + ".png";
            }
        } else {
            path = null;
        }

        if (path != null) {
            ImageIcon i = new ImageIcon(getClass().getClassLoader().getResource(path));
            this.setIcon(i);
        }
    }

    /**
     * Returns a number between 1 and 3 (both included)
     *
     * @return an int between 1 and 3 (both included)
     */
    private String random() {
        int a = (int) (Math.random() * (3 - 1 + 1) + 1);
        return String.valueOf(a);
    }

}
