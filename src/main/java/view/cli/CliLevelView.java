package view.cli;

import controller.listeners.PlayerMovesListeners;
import model.board.piece.Piece;
import model.bonus.Bonus;
import model.level.Level;

import java.util.Map;
import java.util.Scanner;

public class CliLevelView {
    private Level level;

    protected PlayerMovesListeners playerMovesListeners = new PlayerMovesListeners();

    public CliLevelView(Level level) {
        this.level = level;
    }

    public void updateBoard() { drawLevel(); }

    public void updateScore() { drawLevel(); }

    public void updateBees() { drawLevel(); }

    public void updateAvailableBonus() { drawLevel(); }

    public void updateFreeBonus() { drawLevel(); }

    public void promptNextMove() {
        Scanner sc = new Scanner(System.in);
        String move = "";
        do {
            System.out.print("Do you want to [C]lick on a piece on the board, use your [F]ree bonus, or use one of your [B]onuses ? ");
            move = sc.nextLine();
            System.out.println();
        } while (!move.toUpperCase().equals("C") && !move.toUpperCase().equals("F") && !move.toUpperCase().equals("B"));
        switch (move) {
            case "C" -> askWhichPiece(sc);
            case "F" -> askFreeBonusCoordinates(sc);
            case "B" -> askWhichBonus(sc);
        }
        sc.close();
    }

    public void askWhichPiece(Scanner sc) {
        int[] coordinates = askCoordinates(sc);
        playerMovesListeners.onPieceClicked(coordinates[0], coordinates[1]);
    }

    public void askFreeBonusCoordinates(Scanner sc) {
        int[] coordinates = askCoordinates(sc);
        playerMovesListeners.onUseFreeBonus(level.getFreeBonus(), coordinates[0], coordinates[1]);
    }

    public void askWhichBonus(Scanner sc) {
        char b;
        do {
            System.out.print("Which bonus do you want to use : [C]hangeBlockColor, Erase[B], Er[A]seColor, [D]eleteColumn, [F]reeBee, FreeBloc[K] ? ");
            b = sc.nextLine().toLowerCase().charAt(0);
            System.out.println();
        } while (b != 'C' && b != 'B' && b != 'A' && b != 'D' & b != 'F' && b != 'K');
        int[] coordinates = askCoordinates(sc);
        playerMovesListeners.onUseAvailableBonus(b, coordinates[0], coordinates[1]);
    }

    public int[] askCoordinates(Scanner sc) {
        int[] coordinates = {-1, -1};
        String a;
        while (coordinates[0] < 0 && coordinates[1] < 0) {
            System.out.print("Please give your first coordinate (the number) :");
            coordinates[0] = Integer.parseInt(sc.next());
            System.out.print("Please give your second coordinate (the letter) :");
            coordinates[1] = sc.next().toLowerCase().charAt(0) - 97; // 'a' is 97
        }
        return coordinates;
    }


    ////////////////////////////////////////////// DRAWING METHODS /////////////////////////////////////////////////////

    /**
     * Prints the entire Level
     */
    public void drawLevel() {
        drawHeader();
        drawEndHeader();
        drawBonus();
        drawBoard();
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
     * Prints the rest of the header depending on what type of Level it is
     */
    public void drawEndHeader() {}

    /**
     * Prints the number of Bonus available, and their effects
     */
    public void drawBonus() {
        Map<Bonus, Integer> availableBonus = level.getAvailableBonus();
        System.out.println("You currently have the following bonus : ");
        for (Map.Entry<Bonus, Integer> bonus : availableBonus.entrySet()) {
            System.out.println(bonus.getValue() + " " + bonus.getKey().toString());
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
        for (int i = 0; i < board.length; i++) {
            // print the numbers at the top
            System.out.println("     " + i + "  ");
        }
        char letter = 'A';
        for (Piece[] pieces : board) {
            // print the letter at the left
            System.out.println(letter);
            letter++;

            // print the Pieces
            for (Piece p : pieces) {
                if (p == null) {
                    System.out.println("      ");
                } else {
                    System.out.print("   " + p.charForCli());
                }
            }
            System.out.println();
        }
        System.out.println();
    }


}
