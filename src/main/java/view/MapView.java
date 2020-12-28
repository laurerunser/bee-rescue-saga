package view;

public interface MapView extends Vue {

    default void draw() {
        drawMap();
    }

    void drawMap();

    void showLevelDetails(int n);
}
