package view.gui;

import controller.listeners.MapNavigationListeners;
import controller.listeners.PlayerMovesListeners;
import model.board.piece.Piece;
import model.bonus.Bonus;
import model.level.Level;
import view.LevelView;

import javax.swing.*;
import java.awt.*;
import java.util.Map;
import java.util.Objects;

public class GuiLevelView extends JPanel implements LevelView {
    private final Level level;
    private final PlayerMovesListeners playerMovesListeners;
    private final JFrame frame;

    private final JLabel score = new JLabel();
    private final JLabel pointsLeftTo1star = new JLabel();
    private final JLabel pointsLeftTo2stars = new JLabel();
    private final JLabel pointsLeftTo3stars = new JLabel();
    private final JLabel beesToSave = new JLabel();

    private JPanel board = new JPanel();
    private final JPanel freeBonus = new JPanel();
    private final JPanel availableBonus = new JPanel();


    /**
     * Constructs the view of the level
     *
     * @param level                The level to display
     * @param playerMovesListeners The list of listeners to listen on the player's actions
     * @param frame                The JFrame of the app
     */
    public GuiLevelView(Level level, PlayerMovesListeners playerMovesListeners, JFrame frame) {
        this.level = level;
        this.playerMovesListeners = playerMovesListeners;
        this.frame = frame;
    }

    @Override
    public void drawLevel() {
        // remove the previous content of the ContentPane
        frame.getContentPane().removeAll();
        frame.setContentPane(this);

        // add elements
        this.setLayout(new BorderLayout());
        initStats();
        updateBoard();
        initBonus();
        initGoal();

        // update the view and redraw it
        this.setVisible(true);
        frame.getContentPane().revalidate();
        frame.repaint();
    }

    private void initStats() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        // banner
        ImageIcon banner = new ImageIcon(Objects.requireNonNull(getClass().getClassLoader().getResource("pictures/Banner.png")));
        panel.add(new JLabel(banner));

        // return to map button
        JButton backToMap = new JButton("Back");
        backToMap.addActionListener(actionEvent -> playerMovesListeners.onReturnToMap());
        panel.add(backToMap);

        // score
        score.setText("SCORE : " + 0);
        panel.add(score);

        // score objectives to get stars
        updateScore();
        JPanel pObjScore = new JPanel();
        pObjScore.setLayout(new BoxLayout(pObjScore, BoxLayout.PAGE_AXIS));
        pObjScore.add(pointsLeftTo1star);
        pObjScore.add(pointsLeftTo2stars);
        pObjScore.add(pointsLeftTo3stars);
        panel.add(pObjScore);

        // nb of pets to save
        updateBees();
        panel.add(beesToSave);

        this.add(panel, BorderLayout.NORTH);
    }

    /**
     * Add a sentence saying the goal of the level at the bottom of the screen
     */
    private void initGoal() {
        JLabel goal = new JLabel(level.getGoal());
        this.add(goal, BorderLayout.SOUTH);
    }

    private void initBonus() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 120, 63));
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        initFreeBonus();
        initAvailableBonus();
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        panel.add(freeBonus);
        panel.add(Box.createRigidArea(new Dimension(10, 10)));
        panel.add(availableBonus);
        this.add(panel, BorderLayout.EAST);
    }

    private void initFreeBonus() {
        freeBonus.setBackground(new Color(255, 120, 63));
        freeBonus.setLayout(new BoxLayout(freeBonus, BoxLayout.PAGE_AXIS));
        updateFreeBonus();
    }

    private void initAvailableBonus() {
        availableBonus.setBackground(new Color(255, 120, 63));
        availableBonus.setLayout(new BoxLayout(availableBonus, BoxLayout.PAGE_AXIS));
        updateAvailableBonus();
    }

    @Override
    public void updateScore() {
        int[] obj = level.getObjScore();
        int o1 = obj[0] - level.getScore();
        int o2 = obj[1] - level.getScore();
        int o3 = obj[2] - level.getScore();
        score.setText("SCORE : " + level.getScore());
        pointsLeftTo1star.setText("Points lefts to 1 star : " + o1);
        pointsLeftTo2stars.setText("Points lefts to 2 stars : " + o2);
        pointsLeftTo3stars.setText("Points lefts to 3 stars : " + o3);
    }

    @Override
    public void updateBees() {
        beesToSave.setText(level.getBeeSaved() + " / " + level.getObjBees() + " bees saved !");
    }


    @Override
    public void updateBoard() {
        board.removeAll();
        board.setVisible(false);
        board = new JPanel();
        int x = level.getBoard().getBoard().length;
        int y = level.getBoard().getBoard()[0].length;
        board.setLayout(new GridLayout(x, y));
        board.setPreferredSize(new Dimension(x * 65, y * 65));
        board.setBackground(new Color(237, 198, 63));

        Piece[][] p = level.getBoard().getBoard();
        for (int i = p.length - 1; i >= 0; i--) {
            for (int j = p[i].length - 1; j >= 0; j--) {
                Block b = new Block(i, j, playerMovesListeners, p[i][j]);
                if (b.getIcon() != null) {
                    board.add(b, p.length - 1 - i, p[i].length - 1 - j);
                } else {
                    // if the icon is null, there is no Piece to display
                    // so I add an invisible JPanel to fill the space
                    JPanel panel = new JPanel();
                    panel.setPreferredSize(new Dimension(65, 65));
                    board.add(panel, p.length - 1 - i, p[i].length - 1 - j);
                }
            }
        }
        this.add(board, BorderLayout.CENTER);
        board.setVisible(true);
    }


    @Override
    public void updateAvailableBonus() {
        availableBonus.removeAll();
        var bonus = level.getAvailableBonus();
        if (bonus == null) {
            JLabel label = new JLabel("You currently possess no bonus.\n Go to the shop to buy some !");
            availableBonus.add(label);
        } else {
            for (Map.Entry<Bonus, Integer> bonusEntry : bonus.entrySet()) {
                if (bonusEntry.getValue() != 0) {
                    JLabel label = new JLabel("Quantity : " + bonusEntry.getValue().toString());
                    Block block = new Block(bonusEntry.getKey(), false);
                    JPanel panel = new JPanel();
                    panel.add(block);
                    panel.add(label);
                    availableBonus.add(panel);
                }
            }
        }
    }

    @Override
    public void updateFreeBonus() {
        freeBonus.removeAll();

        if (level.getFreeBonus() != null) { // if null there is no free bonus on the level
            Block b = new Block(level.getFreeBonus(), true);
            freeBonus.add(b);
            int[] c = level.getFreeBonusConditions();
            JLabel condition = new JLabel("You have " + c[0] + " free bonus in stock.");
            JLabel movesReplenish = new JLabel("You have to wait at least " + c[1] + " moves before you get your next free bonus");
            freeBonus.add(condition);
            freeBonus.add(movesReplenish);
        } else {
            JLabel noBonus = new JLabel("There is no free bonus on this level.");
            freeBonus.add(noBonus);
        }
    }

    @Override
    public void drawWon(int score, int stars, MapNavigationListeners mapNavigationListeners) {
        String message = "CONGRATULATIONS you've won with a score of " + score + ".\nYou also have " + stars + " stars !";
        drawEndMessage(message, mapNavigationListeners);
    }


    @Override
    public void drawLost(MapNavigationListeners mapNavigationListeners) {
        String message = "Sorry, you've lost !";
        drawEndMessage(message, mapNavigationListeners);
    }

    private void drawEndMessage(String message, MapNavigationListeners mapNavigationListeners) {
        String[] options = {"Play again !", "Go back to the map"};
        message += "\n don't click on \"Play again!\", it doesn't work right now";
        int result = JOptionPane.showOptionDialog(this, message,
                                                  "You've won level " + level.getLevel(),
                                                  JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                                                  options[0]);

        if (result == JOptionPane.YES_OPTION) {
            mapNavigationListeners.onPlayLevel(level.getLevel() + 1);
            // I got confused with the numbers of the levels. It has to be +1 bc I wanted the user to start at level 1
            // but arrays start at 0...
        } else {
            mapNavigationListeners.onGoBackToMap();
        }

        //TODO implement restarting a level
        // this code works and fires the right events
        // but the level needs tobe "restarted" : right now it starts back where we left it,
        // that is to say in a winning state
        // Don't forget to delete the part of the message that says it doesn't work
    }

}
