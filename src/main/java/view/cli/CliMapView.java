package view.cli;

import controller.listeners.MapNavigationListeners;
import model.Player;
import model.level.Level;
import model.level.LevelMap;
import view.MapView;

import java.util.Scanner;

public class CliMapView implements MapView {
    private final MapNavigationListeners mapNavigationListeners;
    /**
     * The current Player
     */
    private Player player;
    /**
     * The LevelMap of the Player
     */
    private LevelMap map;

    public CliMapView(Player p, MapNavigationListeners mapNavigationListeners) {
        player = p;
        map = p.getMap();
        this.mapNavigationListeners = mapNavigationListeners;
    }

    /**
     * Draws the player's stats, the map and asks the player for their choice.
     */
    public void drawMap() {
        System.out.println("This is the map of all the levels of the game.");
        System.out.println("Play the different levels and save all the Bees from a certain death !");
        drawPlayerStats();
        int lastVisible = map.getLastLevelVisible();
        drawVisible(map.getLevelsCompleted(), lastVisible);
        if (lastVisible + 1 == map.getLevels().length) {
            System.out.println("You have unlocked all the levels in the game !");
        } else {
            System.out.println("There are many more levels to unlock. Win all the previous levels to get access to the rest of the game");
        }
        System.out.println();
        System.out.println();
        System.out.println("Possible actions : ");
        System.out.println("To see more informations on a Level and play it, type I, then the number of the level");
        System.out.println("To go in the shop to buy lives or bonuses, type S");
        System.out.println("To play the raffle, type R");
        boolean ok = false;
        do { // ask the player for their choice until it can be interpreted correctly.
            ok = askPlayerMapChoice();
        } while (!ok);
    }

    /**
     * Redraws the Map.
     */
    public void onGoBackToMap() { drawMap(); }


    /**
     * Prints all of the player's stats
     */
    private void drawPlayerStats() {
        System.out.println("Here are your statistics : ");
        System.out.println("  - you have " + player.getNbLives() + " / " + player.getMaxLives() + " lives");
        System.out.println("  - you have " + player.getScore() + " points");
        System.out.println("  - you have " + player.getGold() + " gold");
        System.out.println("  - you have " + player.getRaffleTurns() + " raffle tickets to use");
        System.out.println("  - here is the list of bonus you possess : ");
        for (var b : player.getBonus().entrySet()) {
            System.out.println("  - " + b.getValue() + " " + b.getKey().toString());
        }
        System.out.println();
    }

    /**
     * Draws all the visible levels with the number of stars and points the player got.
     *
     * @param completion The array containing the number of stars and points for each level
     * @param visible    The number of the last visible level
     */
    private void drawVisible(int[][] completion, int visible) {
        for (int i = 0; i <= visible; i++) {
            System.out.print("Level " + i + " ");
            if (completion[i][0] == 0) {
                System.out.println("You haven't won this level yet.");
            } else {
                System.out.println("Your best score is " + completion[i][0] + " stars and " + completion[i][1] + " points !");
            }
        }
        System.out.println();
    }

    /**
     * Interprets the player's choice.
     *
     * @return true if the choice was interpreted, false if it didn't make sense.
     */
    private boolean askPlayerMapChoice() {
        Scanner sc = new Scanner(System.in);
        String choice = sc.nextLine().toUpperCase();
        sc.close();
        if (choice.equals("R")) {
            mapNavigationListeners.onGoToRaffle();
        } else if (choice.equals("S")) {
            mapNavigationListeners.onGoToShop();
        } else if (choice.startsWith("I")) {
            choice = choice.substring(1);
            int level = Integer.parseInt(choice);
            if (level >= 0 && level <= map.getLastLevelVisible()) {
                mapNavigationListeners.onShowLevelDetails(level);
            } else {
                return false;
            }
        } else {
            return false;
        }
        return true;
    }


    /**
     * Prints the level's details and prompts the user for their choice : playing the level or going back to the map.
     *
     * @param n The number of the level to display.
     */
    public void showLevelDetails(int n) {
        Level l = map.getLevels()[n];
        int[] objScore = l.getObjScore();
        System.out.println("Here are the details of level number " + n);
        System.out.println("To win, you must save " + l.getObjBees() + " bees");
        System.out.println("To get one star, you must get " + objScore[0] + " points");
        System.out.println("To get two stars, you must get " + objScore[1] + " points");
        System.out.println("To get three stars, you must get " + objScore[2] + " points");
        System.out.println();
        System.out.println("If you want to play the level, type P");
        System.out.println("If you want to go back to the map, type B");
        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            choice = sc.nextLine().toUpperCase();
        } while (!choice.equals("P") && !choice.equals("B"));
        if (choice.equals("B")) {
            mapNavigationListeners.onGoBackToMap();
        } else {
            mapNavigationListeners.onPlayLevel(n);
        }
        sc.close();
    }

    public void onGoToShop() {
        // TODO implement the shop
    }

    public void onGoToRaffle() {
        // TODO implement the raffle
    }

}
