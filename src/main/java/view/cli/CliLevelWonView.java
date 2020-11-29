package view.cli;

import model.level.Level;
import view.LevelWonView;

public class CliLevelWonView implements LevelWonView {
    /**
     * The number of stars won
     */
    private final int stars;

    /**
     * The score that was made
     */
    private final int score;

    /**
     * The level that was played
     */
    private final Level level;

    public CliLevelWonView(int stars, int score, Level level) {
        this.stars = stars;
        this.score = score;
        this.level = level;
    }

    @Override
    public void draw() {
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
    }
}
