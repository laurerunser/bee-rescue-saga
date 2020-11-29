package view;

public interface MapView extends Vue {

    default void draw() {
        drawMap();
    }

    void drawMap();

    void onGoBackToMap();

    void onShowLevelDetails(int n);

    void onGoToShop();

    void onGoToRaffle();

}
