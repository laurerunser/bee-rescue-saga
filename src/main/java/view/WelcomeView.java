package view;

import java.util.ArrayList;

/**
 * The first view that appears when the game is launched.
 * Allows to choose the player's name, which savegame to use (if any), the langage, ...
 */
public interface WelcomeView {
    /**
     * Prints a welcome screen and introduces the goal of the game
     */
    void welcome();

    /**
     * @return the name of the Player
     */
    String askName(ArrayList<String> savedNames);
}
