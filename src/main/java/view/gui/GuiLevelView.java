package view.gui;

import controller.listeners.PlayerMovesListeners;
import model.board.piece.Piece;
import model.level.Level;
import view.LevelView;

import javax.swing.*;
import java.awt.*;

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
        initFreeBonus();
        initAvailableBonus();
    }

    private void initFreeBonus() {

    }

    private void initAvailableBonus() {

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
        board = new JPanel();
        int x = level.getBoard().getBoard().length;
        int y = level.getBoard().getBoard()[0].length;
        board.setLayout(new GridLayout(x, y));

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
                    panel.setPreferredSize(new Dimension(60, 60));
                    board.add(panel, p.length - 1 - i, p[i].length - 1 - j);
                }
            }
        }
        this.add(board, BorderLayout.CENTER);
    }


    @Override
    public void updateAvailableBonus() {

    }

    @Override
    public void updateFreeBonus() {

    }
}
