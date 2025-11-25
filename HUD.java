import java.awt.*;

public class HUD {
    private final Game game;
    public HUD(Game game){ this.game = game; }

    public void render(Graphics2D g, int screenW, int screenH, int day) {
        g.setColor(new Color(0,0,0,200)); g.fillRect(0,0,screenW,36);
        g.setColor(Color.WHITE);
        Player p = game.getPlayer();
        g.drawString("Coins: " + p.getCoins(), 8, 14);
        g.drawString("Day: " + day, 8, 30);
        int x = 140;
        for (var e : p.getSeeds().entrySet()) {
            g.drawString(e.getKey() + ":" + e.getValue(), x, 14); x += 80;
        }
        g.drawString("WASD move | LClick: till/plant/harvest | RClick: water | 1:buy seed(5c)", 8, screenH - 8);
    }
}



