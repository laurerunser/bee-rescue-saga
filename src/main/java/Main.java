import controller.LevelMapController;
import controller.listeners.MapNavigationListeners;
import model.Player;
import view.WelcomeView;
import view.cli.CliWelcomeView;
import view.gui.GuiWelcomeView;

import javax.swing.*;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

/**
 * Launches the game.
 * Deals with loading savegames, asking the player's name, and starting the LevelMap.
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
        if (args.length == 0) {
            Install.main(args);
            System.exit(0);
        } else if (args.length == 1) {
            gui = args[0].toUpperCase().equals("GUI");
            EventQueue.invokeLater(Main::welcome); // to make it thread-safe
        } else {
            System.out.println("If you haven't already, don't forget to install the game by typing : ./gradlew run");
            System.out.println();
            System.out.println("You need to specify whether you want a [CLI] or [GUI] view.");
            System.out.println("For a [CLI] view : ./gradlew run --args='cli'");
            System.out.println("For a [GUI] view : ./gradlew run --args='gui'");
            System.exit(1);
        }
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
        ArrayList<String> savedNames = getSavedNames();

        savedNames.forEach(System.out::println);
        System.out.println();

        view.welcome();

        savedNames.forEach(System.out::println);
        System.out.println();

        String name = view.askName(savedNames);

        if (view instanceof CliWelcomeView) {
            startGame(name); // Cli answers the name of the player directly
        } else {
            GuiWelcomeView g = (GuiWelcomeView) view;
            startGame(g.getName());
        }
    }

    /**
     * Parses the resources/savedGames directory and returns the names of the players who have a saved game
     *
     * @return the names of the players with a game saved on the disk
     */
    private static ArrayList<String> getSavedNames() {
        ArrayList<String> names = new ArrayList<>();
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream("savedGames");
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        try {
            String resource;
            while ((resource = br.readLine()) != null) {
                names.add(resource);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        names.replaceAll(n -> n.substring(0, n.length() - 4));
        return names;
    }

    /**
     * Starts the game once the player's name has been acquired
     *
     * @param name The name of the player
     */
    private static void startGame(String name) {
        ArrayList<String> savedNames = getSavedNames();
        Player p;
        if (savedNames.contains(name)) {
            p = Player.deserialize(name);
            if (p == null) {
                // TODO : make a nice exception instead of a lame error message
                System.out.println("The saved game couldn't be opened");
                System.exit(0);
            }
        } else {
            p = new Player(name);
        }

        MapNavigationListeners mapNavigationListeners = new MapNavigationListeners();
        LevelMapController controller = new LevelMapController(p, gui, mapNavigationListeners, (JFrame) view);
    }

}
