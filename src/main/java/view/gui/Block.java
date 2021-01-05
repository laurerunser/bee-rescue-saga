package view.gui;

import controller.listeners.PlayerMovesListeners;
import model.board.piece.*;
import model.bonus.*;

import javax.swing.*;
import java.awt.*;


public class Block extends JButton {
    /**
     * The Bonus currently selected (if any)
     */
    private static Bonus bonus;
    /**
     * True if the selected Bonus is the freeBonus, false otherwise
     */
    private static boolean freeBonus;

    /**
     * Constructs a Block for a Bonus
     *
     * @param bonus     The Bonus
     * @param freeBonus True if this is a free bonus, false otherwise
     */
    public Block(Bonus bonus, boolean freeBonus) {
        this.addActionListener(actionEvent -> {
            this.setVisible(false); // TODO : make it grey if none left, and decrease the amount
            Block.bonus = bonus;
            Block.freeBonus = freeBonus;
        });
        initCosmetic();
        this.setToolTipText(bonus.getToolTipText());
    }

    /**
     * Constructs a Block for a Piece
     *
     * @param x                    The x-coordinate on the board
     * @param y                    The y-coordinate on the board
     * @param playerMovesListeners The list of listeners to fire the event from
     * @param piece                The piece that is being represented by the Block
     */
    public Block(int x, int y, PlayerMovesListeners playerMovesListeners, Piece piece) {
        this.addActionListener(actionEvent -> {
            decideMovement(x, y, playerMovesListeners);
        });

        initIcon(piece);

        //TODO add animation for the Bee (from Bee-happy.png to Bee-sad).png

        initCosmetic();
        if (piece != null && !piece.getToolTipText().equals("")) {
            this.setToolTipText(piece.getToolTipText());
        }
        // TODO : make the button go grey or smth when mouse hovers
    }

    /**
     * Init some cosmetic stuff that both a Piece and a Bonus need
     */
    private void initCosmetic() {
        this.setPreferredSize(new Dimension(65, 65));
        this.setBackground(new Color(237, 198, 63));
        this.setBorder(null);// empty border
        this.setContentAreaFilled(false);
    }

    /**
     * Decides, based on the value of bonusChosen, whether the piece is simply clicked, or if a bonus is used.
     * Then fires the appropriate events
     *
     * @param x                    The x-coordinate of the Block clicked
     * @param y                    The y-coordinate of the Block clicked
     * @param playerMovesListeners The list of listeners to fire events with
     */
    private void decideMovement(int x, int y, PlayerMovesListeners playerMovesListeners) {
        if (bonus == null) { // no bonus selected
            playerMovesListeners.onPieceClicked(x, y);
        } else if (freeBonus) {
            playerMovesListeners.onUseFreeBonus(bonus, x, y);
        } else {
            playerMovesListeners.onUseAvailableBonus(getBonusChar(bonus), x, y);
        }
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

    /**
     * Returns the char representing the bonus to be used in useAvailableBonus
     *
     * @param bonus the Bonus
     * @return the char representing the Bonus
     */
    // TODO : refactor to have a method in each Bonus class that returns the right char
    private char getBonusChar(Bonus bonus) {
        if (bonus instanceof ChangeBlockColor) {
            return 'C';
        } else if (bonus instanceof EraseBlock) {
            return 'B';
        } else if (bonus instanceof EraseColor) {
            return 'A';
        } else if (bonus instanceof EraseColumn) {
            return 'D';
        } else if (bonus instanceof FreeBee) {
            return 'F';
        } else if (bonus instanceof FreeBlock) {
            return 'K';
        } else {
            return '0';
        }
    }


}
