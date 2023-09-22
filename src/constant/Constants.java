package constant;

public class Constants {
    public static final int WINDOW_BORDER = 16;
    public static final int WINDOW_MANAGER_HEIGHT = 39;
    public static int CLIENT_WIDTH = 300; // 300 / 600 / 750
    public static int CLIENT_HEIGHT = 400; // 400 / 800 / 1000

    public static int BLOCK_CELL_SIZE = 20; // 20 / 40 / 50
    public static int INFO_AREA_WIDTH = 100; // 100 / 200 / 250
    public static int GAME_AREA_WIDTH = CLIENT_WIDTH - INFO_AREA_WIDTH;

    public static void setClientSmall() {
        CLIENT_WIDTH = 300;
        CLIENT_HEIGHT = 400;
        BLOCK_CELL_SIZE = 20;
        INFO_AREA_WIDTH = 100;
        GAME_AREA_WIDTH = CLIENT_WIDTH - INFO_AREA_WIDTH;
    }

    public static void setClientMedium() {
        CLIENT_WIDTH = 600;
        CLIENT_HEIGHT = 800;
        BLOCK_CELL_SIZE = 40;
        INFO_AREA_WIDTH = 200;
        GAME_AREA_WIDTH = CLIENT_WIDTH - INFO_AREA_WIDTH;
    }

    public static void setClientLarge() {
        CLIENT_WIDTH = 750;
        CLIENT_HEIGHT = 1000;
        BLOCK_CELL_SIZE = 50;
        INFO_AREA_WIDTH = 250;
        GAME_AREA_WIDTH = CLIENT_WIDTH - INFO_AREA_WIDTH;
    }
}
