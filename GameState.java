public class GameState {

    public int playerX = 5;
    public int playerY = 5;
    public int coins = 20;

    public void update() {
        // placeholder logic
        playerX++;
        if (playerX > 10) playerX = 0;
    }
}
