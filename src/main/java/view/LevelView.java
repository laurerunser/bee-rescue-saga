package view;

import controller.listeners.MapNavigationListeners;

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

    void drawWon(int score, int stars, MapNavigationListeners mapNavigationListeners);

    void drawLost(MapNavigationListeners mapNavigationListeners);

}

