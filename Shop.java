import java.awt.*;
import java.io.Serializable;

public class Shop implements Serializable {
    private transient Player player;
    private Player storedPlayerRef;

    public Shop(Player p) {
        this.player = p;
        this.storedPlayerRef = p;
    }

    public void render(Graphics2D g, int screenW, int screenH) {
        // small shop UI top-right
        int w = 200, h = 80;
        int x = screenW - w - 8, y = 8;
        g.setColor(new Color(0,0,0,160));
        g.fillRect(x,y,w,h);
        g.setColor(Color.WHITE);
        g.drawString("Shop (press 1 to buy seed)", x+8, y+16);
        g.drawString("1) Carrot seed - 5 coins", x+8, y+32);
        g.drawString("2) Change tool (H/W)", x+8, y+48);

        // input: quick keys (polled elsewhere if you want)
    }

    public void buyCarrot(Player p) {
        if (p.spendCoins(5)) p.addSeed("carrot", 1);
    }

    // getters/setters
    public void setPlayer(Player p) { this.player = p; storedPlayerRef = p; }
}
