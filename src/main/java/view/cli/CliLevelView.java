package view.cli;

import controller.listeners.MapNavigationListeners;
import controller.listeners.PlayerMovesListeners;
import model.board.piece.Piece;
import model.bonus.Bonus;
import model.level.Level;
import model.level.LevelLimitedMoves;
import model.level.LevelLimitedPieces;
import view.LevelView;

import java.util.Map;
import java.util.Scanner;

public class CliLevelView implements LevelView {
    private Level level;

    protected PlayerMovesListeners playerMovesListeners;

    public CliLevelView(Level level, PlayerMovesListeners playerMovesListeners) {
        this.level = level;
        this.playerMovesListeners = playerMovesListeners;
    }

    public void updateBoard() { drawLevel(); }

    public void updateScore() { drawLevel(); }

    public void updateBees() { drawLevel(); }

    public void updateAvailableBonus() { drawLevel(); }

    public void updateFreeBonus() { drawLevel(); }

    /**
     * Prints the entire Level
     */
    public void drawLevel() {
        drawHeader();
        drawEndHeader();
        drawBonus();
        drawBoard();
        boolean freeBonus = false;
        boolean availableBonus = false;
        if (level.getFreeBonus() != null) freeBonus = true;
        if (level.getAvailableBonus() != null) availableBonus = true;
        promptNextMove(freeBonus, availableBonus);
    }

    /**
     * Asks the user which piece they want to click on, then fires the appropriate event.
     *
     * @param sc
     */
    public void askWhichPiece(Scanner sc) {
        int[] coordinates = askCoordinates(sc);
        playerMovesListeners.onPieceClicked(coordinates[1], coordinates[0]);
    }

    /**
     * Asks the user for coordinates and returns them
     * Note : the letter (=y) is first, and the the number (=x)
     *
     * @param sc The Scanner to asks the user with
     * @return an array containing the two coordinates {y , x}
     */
    private int[] askCoordinates(Scanner sc) {
        int[] coordinates = {-1, -1};
        String a;
        while (coordinates[0] < 0 && coordinates[1] < 0) {
            System.out.print("Please give your first coordinate (the letter) :");
            coordinates[1] = sc.next().toLowerCase().charAt(0) - 97; // 'a' is 97
            try {
                System.out.print("Please give your second coordinate (the number) :");
                coordinates[0] = Integer.parseInt(sc.next());
            } catch (NumberFormatException e) {
                int[] c = askCoordinates(sc); // if the user doesn't input a number, try again
                coordinates[0] = c[0];
            }
        }
        System.out.println();
        return coordinates;
    }

    /**
     * Asks the user on which coordinates they want to use their free Bonus, then fires the appropriate event.
     *
     * @param sc The Scanner to asks the user with
     */
    public void askFreeBonusCoordinates(Scanner sc) {
        int[] coordinates = askCoordinates(sc);
        playerMovesListeners.onUseFreeBonus(level.getFreeBonus(), coordinates[1], coordinates[0]);
    }

    /**
     * Asks the user which Bonus they want to use, and on which coordinates. Then fires the appropriate event.
     *
     * @param sc The Scanner to ask to user with
     */
    public void askWhichBonus(Scanner sc) {
        char b;
        do {
            System.out.print("Which bonus do you want to use : [C]hangeBlockColor, Erase[B], Er[A]seColor, [D]eleteColumn, [F]reeBee, FreeBloc[K] ? ");
            b = sc.nextLine().toLowerCase().charAt(0);
            System.out.println();
        } while (b != 'C' && b != 'B' && b != 'A' && b != 'D' & b != 'F' && b != 'K');
        int[] coordinates = askCoordinates(sc);
        playerMovesListeners.onUseAvailableBonus(b, coordinates[1], coordinates[0]);
    }


    ////////////////////////////////////////////// DRAWING METHODS /////////////////////////////////////////////////////

    /**
     * Prompt the user for their next move and fires the appropriate events.
     */
    public void promptNextMove(boolean freeBonus, boolean availableBonus) {
        String message = "Do you want to [C]lick on a piece on the board, ";
        if (freeBonus) message = message + "use your [F]ree bonus, ";
        if (availableBonus) message = message + "use a [B]onus, ";
        message = message + "or save and [R]eturn to the map ? ";
        Scanner sc = new Scanner(System.in);
        String move = "";
        do {
            System.out.print(message);
            move = sc.nextLine();
            System.out.println();
        } while (!nextMoveOK(move, freeBonus, availableBonus));
        switch (move) {
            case "C" -> askWhichPiece(sc);
            case "F" -> askFreeBonusCoordinates(sc);
            case "B" -> askWhichBonus(sc);
            case "R" -> playerMovesListeners.onReturnToMap();
        }
    }

    /**
     * Determines if the move is ok, depending on whether the player possesses a bonus or not.
     * Eg : the player can't try to use a free bonus if there is none on the level
     *
     * @param move           2    The String to asses
     * @param freeBonus      True if there is a freeBonus, false otherwise
     * @param availableBonus True if the player has a bonus, false otherwise
     * @return true if the move is ok, false otherwise
     */
    private boolean nextMoveOK(String move, boolean freeBonus, boolean availableBonus) {
        if (freeBonus && availableBonus) {
            return !move.toUpperCase().equals("C") && !move.toUpperCase().equals("F")
                    && !move.toUpperCase().equals("B") && !move.toUpperCase().equals("R");
        } else if (freeBonus) {
            return !move.toUpperCase().equals("C") && !move.toUpperCase().equals("F") && !move.toUpperCase().equals("R");
        } else if (availableBonus) {
            return !move.toUpperCase().equals("C") && !move.toUpperCase().equals("B") && !move.toUpperCase().equals("R");
        } else {
            return !move.toUpperCase().equals("C") && !move.toUpperCase().equals("R");
        }
    }

    /**
     * Prints the header with general level info and the player's current score and number of Bees saved
     */
    public void drawHeader() {
        System.out.println("=================================");
        System.out.println("===== Bee Rescue Saga =====");
        System.out.println("===== Level " + level.getLevel() + " =====");
        System.out.println("=================================");
        System.out.println();

        System.out.println("Number of bees to save : " + level.getObjBees());
        System.out.println("Number of bee saved : " + level.getBeeSaved());
        System.out.println();

        int[] objScore = level.getObjScore();
        System.out.println("Your score : " + level.getScore());
        System.out.println("Scores needed to get : ");
        System.out.println("1 star  : " + objScore[0]);
        System.out.println("2 stars : " + objScore[1]);
        System.out.println("3 stars : " + objScore[2]);
        System.out.println();
    }

    /**
     * Prints the goal of the Level depending on what type of Level it is.
     */
    public void drawEndHeader() {
        if (level instanceof LevelLimitedPieces) {
            System.out.println("To win the level, you must erase all the pieces on the board");
        } else if (level instanceof LevelLimitedMoves) {
            System.out.println("To win the level, you must save" + level.getObjBees() + " Bees before you run out of moves");
            System.out.println("You have " + ((LevelLimitedMoves) level).getMoves() + " left");
        }
    }

    /**
     * Prints the number of Bonus available, and their effects
     */
    public void drawBonus() {
        Map<Bonus, Integer> availableBonus = level.getAvailableBonus();
        System.out.println("You currently have the following bonus : ");
        if (availableBonus == null) {
            System.out.println("You don't have any bonuses at the moment : get some in the shop !");
        } else {
            for (Map.Entry<Bonus, Integer> bonus : availableBonus.entrySet()) {
                System.out.println(bonus.getValue() + " " + bonus.getKey().toString());
            }
        }
        Bonus freeBonus = level.getFreeBonus();
        if (freeBonus != null) {
            System.out.println("Here is your free bonus for the level : " + freeBonus.toString());
            int[] conditions = level.getFreeBonusConditions();
            if (conditions[0] != -1) {
                System.out.println("You have " + conditions[0] + " free bonus to use");
            } else {
                System.out.println("You have an infinite amount to free bonus to use");
            }
            if (conditions[1] == -1) {
                System.out.println("You can use your free bonus now");
            } else {
                System.out.println("You have to wait " + conditions[1] + " move before you can use your next free bonus");
            }
        } else {
            System.out.println("There is no free bonus for this level");
        }
    }

    /**
     * Prints the board
     */
    public void drawBoard() {
        Piece[][] board = level.getBoard().getBoard();
        System.out.print("  ");
        for (int i = 0; i < board.length; i++) {
            // print the numbers at the top
            System.out.print("     " + i + "      ");
        }
        System.out.println();
        char letter = 'A';
        for (Piece[] pieces : board) {
            // print the letter on the left
            System.out.print(letter);
            letter++;

            // print the Pieces
            for (int i = 0; i < pieces.length; i++) {
                if (pieces[i] == null) {
                    System.out.print("         ");
                } else {
                    System.out.print("   " + pieces[i].charForCli());
                }
            }
            System.out.println();
        }
        System.out.println();
    }

    public void noAvailableBonus() {
        System.out.println();
        System.out.println();
        System.out.println("You don't have any of this bonus !");
        System.out.println("Go to the shop to buy more !");
    }

    public void noFreeBonus() {
        System.out.println();
        System.out.println();
        System.out.println("There is no free bonus for this level !");
    }

    @Override
    public void drawWon(int score, int stars, MapNavigationListeners mapNavigationListeners) {
        System.out.println("CONGRATULATIONS !");
        System.out.println("You have won level " + level.getLevel() + " and saved all the bees from a terrible death !");
        System.out.println("You got a score of " + score + " points");
        System.out.println("This gives you " + stars + " stars");

        int[] obj = level.getObjScore();
        if (stars == 1) {
            System.out.println("Try again to get a second star : you only need to get " + obj[1]);
        } else if (stars == 2) {
            System.out.println("Try again to get a third star : you only need to get " + obj[2]);
        } else {
            System.out.println("Congratulations, you got all the stars for this level !");
            System.out.println("Play it again to best your score !");
        }
        drawBackToMap(mapNavigationListeners);
    }

    @Override
    public void drawLost(MapNavigationListeners mapNavigationListeners) {
        System.out.println("Sorry you've lost !");
        drawBackToMap(mapNavigationListeners);
    }

    private void drawBackToMap(MapNavigationListeners mapNavigationListeners) {
        // TODO : let the user choose to go back to the map OR to replay the level
        System.out.println("Type [OK] to go back to the map !");
        Scanner sc = new Scanner(System.in);
        String choice = "";
        while (choice.equals("")) {
            choice = sc.nextLine();
        }
        mapNavigationListeners.onGoBackToMap();
    }


}
