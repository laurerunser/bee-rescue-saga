import controller.LevelMapController;
import controller.listeners.MapNavigationListeners;
import model.Player;
import view.WelcomeView;
import view.cli.CliWelcomeView;
import view.gui.GuiWelcomeView;

/**
 * Launches the game.
 * Deals with loading savegames, asking the player's name,...
 * Constructs the corresponding Player and LevelMapController to launch the game.
 */
public class Main {
    /**
     * True if the view must be the GUI, false for the CLI
     */
    private static boolean gui;

    /**
     * The current view
     */
    private static WelcomeView view;

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("You need to specify whether you want a [CLI] or [GUI] view.");
            System.exit(1);
        } else {
            gui = args[0].toUpperCase().equals("CLI");
        }
        welcome();
    }

    /**
     * Displays the welcome screen.
     * Asks the user if they want to resume a savegame, if so which one.
     * Otherwise asks the player for their name and starts a new Game
     */
    public static void welcome() {
        if (gui) {
            view = new GuiWelcomeView();
        } else {
            view = new CliWelcomeView();
        }
        view.welcome();
        String name = view.askName();

        Player p = new Player(name);

        MapNavigationListeners mapNavigationListeners = new MapNavigationListeners();
        LevelMapController controller = new LevelMapController(p, gui, mapNavigationListeners);
        mapNavigationListeners.add(controller);
    }
}
