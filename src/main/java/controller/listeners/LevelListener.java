package controller.listeners;

public interface LevelListener {
    void onHasWon(int stars, int score, int level);

    void onHasLost();
}
