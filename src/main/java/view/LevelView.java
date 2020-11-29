package view;

public interface LevelView extends Vue {

    default void draw() {
        drawLevel();
    }

    void drawLevel();

    void updateBoard();

    void updateScore();

    void updateBees();

    void updateAvailableBonus();

    void updateFreeBonus();
}
