package view.gui;

import controller.listeners.PlayerMovesListeners;
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

    private final JLabel petsToSave = new JLabel();

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
        System.out.println("OK");
    }

    @Override
    public void drawLevel() {
        // remove the previous content of the ContentPane
        frame.getContentPane().removeAll();
        frame.setContentPane(this);

        // add elements
        this.setLayout(new BorderLayout());
        initStats();

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
        int[] obj = level.getObjScore();
        int o1 = obj[0] - level.getScore();
        int o2 = obj[1] - level.getScore();
        int o3 = obj[2] - level.getScore();
        pointsLeftTo1star.setText("Points lefts to 1 star : " + o1);
        pointsLeftTo2stars.setText("Points lefts to 2 stars : " + o2);
        pointsLeftTo3stars.setText("Points lefts to 3 stars : " + o3);
        JPanel pObjScore = new JPanel();
        pObjScore.setLayout(new BoxLayout(pObjScore, BoxLayout.PAGE_AXIS));
        pObjScore.add(pointsLeftTo1star);
        pObjScore.add(pointsLeftTo2stars);
        pObjScore.add(pointsLeftTo3stars);
        panel.add(pObjScore);

        // nb of pets to save
        petsToSave.setText(level.getBeeSaved() + " / " + level.getObjBees() + "bees saved !");
        panel.add(petsToSave);

        this.add(panel, BorderLayout.NORTH);
    }

    @Override
    public void updateScore() {
        score.setText("SCORE : " + level.getScore());
    }

    private void initBoard() {

    }

    private void initBonus() {

    }


    @Override
    public void updateBoard() {

    }

    private void initFreeBonus() {

    }

    @Override
    public void updateBees() {

    }

    @Override
    public void updateAvailableBonus() {

    }

    @Override
    public void updateFreeBonus() {

    }
}
