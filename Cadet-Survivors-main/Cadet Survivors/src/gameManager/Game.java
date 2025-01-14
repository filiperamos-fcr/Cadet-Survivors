package gameManager;

public class Game {
    private GameWorld gameWorld;
    public static int ticks;

    public Game() {
        gameWorld = new GameWorld();
    }

    public void start() throws InterruptedException {
        while (true) {
            gameWorld.update();
            if (ticks < 30) {
                ticks++;
            } else {
                ticks = 0;
            }

            try {
                Thread.sleep(16);// Aproximadamente 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Game game = new Game();
        game.start();
    }
}
