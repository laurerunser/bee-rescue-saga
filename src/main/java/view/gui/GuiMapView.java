package view.gui;

import controller.listeners.MapNavigationListeners;
import model.Player;
import model.level.Level;
import model.level.LevelMap;
import view.MapView;

import javax.swing.*;
import java.awt.*;

public class GuiMapView extends JPanel implements MapView {
    private final Player player;
    private final LevelMap map;
    private final MapNavigationListeners mapNavigationListeners;
    private final JFrame frame;

    /**
     * Constructs a Map View
     *
     * @param p                      The player
     * @param mapNavigationListeners The list of listeners to listen on the player's actions
     * @param frame                  The JFrame of the app
     */
    public GuiMapView(Player p, MapNavigationListeners mapNavigationListeners, JFrame frame) {
        this.player = p;
        this.map = p.getMap();
        this.mapNavigationListeners = mapNavigationListeners;
        this.frame = frame;
    }

    @Override
    public void drawMap() {
        // remove the previous content of the ContentPane
        frame.getContentPane().removeAll();
        frame.setContentPane(this);

        // add elements
        initMenu();
        this.setLayout(new BorderLayout());
        initStats();
        initMap();
        initSideBar();

        // update the view and redraw it
        frame.getContentPane().revalidate();
        frame.repaint();
    }

    /**
     * Initializes the menu bar
     */
    private void initMenu() {
        // TODO : add action listeners for the menu items
        JMenuBar menuBar = new JMenuBar();
        JMenu exit = new JMenu("Exit");
        JMenu save = new JMenu("Save");
        JMenu changeUser = new JMenu("Change user");
        JMenu about = new JMenu("About");
        menuBar.add(exit);
        menuBar.add(save);
        menuBar.add(changeUser);
        menuBar.add(about);

        frame.setJMenuBar(menuBar);
    }

    /**
     * Initializes the stats about the player at the top of the screen
     */
    private void initStats() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.PAGE_AXIS));
        panel.add(new JLabel("Hello " + player.getName()));
        panel.add(new JLabel(player.getNbLives() + " / " + player.getMaxLives() + " lives"));
        panel.add(new JLabel(player.getGold() + " gold"));
        panel.add(new JLabel(player.getRaffleTurns() + " raffle turns available"));
        panel.add(new JLabel(player.getScore() + " points"));

        this.add(panel, BorderLayout.NORTH);
    }

    /**
     * Initializes the Map of the levels
     */
    private void initMap() {
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        int nbLevels = map.getLastLevelVisible();
        for (int i = 0; i <= nbLevels; i++) {
            JButton b = new JButton("Level " + i + 1);
            b.addActionListener(actionEvent -> showLevelDetails(getButtonLevel(b)));
            panel.add(b);
        }

        this.add(panel, BorderLayout.CENTER);
    }

    private void initSideBar() {
        // TODO : put the raffle and shop there
    }

    /**
     * Parses the text on the JButton and returns the number of the level
     *
     * @param b The JButton
     * @return The number of the level
     */
    private int getButtonLevel(JButton b) {
        String text = b.getText();
        String[] t = text.split(" ");
        return Integer.parseInt(t[t.length - 1]);
    }

    /**
     * Returns a String with the statistics to put in the pop-up
     *
     * @param n The number of the level
     * @return the description of the level
     */
    private String getLevelStats(int n) {
        Level l = map.getLevels()[n];
        StringBuilder str = new StringBuilder();
        str.append("Level : " + l.getLevel() + "\n");
        str.append("You need to save " + l.getObjBees() + " bees to win the level.\n");
        str.append(l.getGoal() + "\n");


        int[] s = map.getLevelsCompleted()[n];
        int[] obj = l.getObjScore();

        if (s[0] == 0) { // never played the level
            str.append("You have never played this level yet, good luck !\n");
            str.append("You need " + obj[0] + " points to get your first star.\n");
        } else if (s[0] == 3) {
            str.append("You already have 3 stars for this level. You can still play it again if you want to better your score !\n");
        } else {
            str.append("You have " + s[0] + " stars on this level. ");
            str.append("You need " + obj[s[0] + 1] + " points to get " + s[0] + 1 + " stars.\n");
        }

        return str.toString();
    }

    @Override
    public void showLevelDetails(int n) {
        String stats = getLevelStats(n);
        String[] options = {"Play !", "Go back to the map"};

        if (!map.canPlay(n)) { // just show the details, can't play the level
            JOptionPane.showMessageDialog(this, stats +
                    "\nYou can't play this level yet.\nWin the previous ones to unlock it !");
        } else {
            int result = JOptionPane.showOptionDialog(this, stats, "Details of level" + n,
                                                      JOptionPane.YES_NO_OPTION, JOptionPane.PLAIN_MESSAGE, null, options,
                                                      options[0]);

            if (result == JOptionPane.YES_OPTION) {
                mapNavigationListeners.onPlayLevel(n);
            } else {
                mapNavigationListeners.onGoBackToMap();
            }
        }
    }
}
