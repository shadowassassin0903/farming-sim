import java.awt.*;

public class Renderer {

    public static void render(Graphics g, GameState state) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, 800, 600);

        g.setColor(Color.GREEN);
        g.fillRect(state.playerX * 40, state.playerY * 40, 32, 32);

        g.setColor(Color.WHITE);
        g.drawString("Coins: " + state.coins, 20, 20);
    }
}
