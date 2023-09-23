package manager;

public class GameSizeManager {
    public static final int WINDOW_BORDER = 16;
    public static final int WINDOW_MANAGER_HEIGHT = 39;
    public static final GameSize DEFAULT_GAME_SIZE = GameSize.MEDIUM;

    public enum GameSize {
        SMALL(0, 300, 400, 20, 100, 200), MEDIUM(1, 600, 800, 40, 200, 400), LARGE(2, 750, 1000, 50, 250, 500);

        private final int sizeId;
        private final int width;
        private final int height;
        private final int blockCellSize;
        private final int infoAreaWidth;
        private final int gameAreaWidth;

        GameSize(int sizeId, int width, int height, int blockCellSize, int infoAreaWidth, int gameAreaWidth) {
            this.sizeId = sizeId;
            this.width = width;
            this.height = height;
            this.blockCellSize = blockCellSize;
            this.infoAreaWidth = infoAreaWidth;
            this.gameAreaWidth = gameAreaWidth;
        }

        public int getSizeId() {
            return sizeId;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getBlockCellSize() {
            return blockCellSize;
        }

        public int getInfoAreaWidth() {
            return infoAreaWidth;
        }

        public int getGameAreaWidth() {
            return gameAreaWidth;
        }
    }

    public static GameSize GAME_SIZE = GameSize.MEDIUM;

    public static void setGameSize(GameSize gameSize) {
        GAME_SIZE = gameSize;
    }
}
