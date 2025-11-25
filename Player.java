import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;

public class Player {
    public enum Tool { HOE, HAND }

    private int x,y;
    private transient SpriteSheet idleSheet, walkSheet;
    private int animIndex=0;
    private long lastAnim=0;
    private final int animDelay=150;
    private Tool selectedTool = Tool.HOE;
    private String selectedSeed = "carrot";
    private int coins = 20;
    private Map<String,Integer> seeds = new HashMap<>();
    private TileMap world;

    public Player(int x,int y, SpriteSheet idle, SpriteSheet walk) {
        this.x = x; this.y = y; this.idleSheet = idle; this.walkSheet = walk;
        seeds.put("carrot", 5);
    }

    public void setWorld(TileMap w){ this.world = w; }
    public void update(){ long now = System.currentTimeMillis(); if (now - lastAnim >= animDelay){ animIndex++; lastAnim = now; } }

    public void moveDirectional(boolean up, boolean down, boolean left, boolean right, TileMap world) {
        if (up && !down) moveTo(x, y-1, world);
        if (down && !up) moveTo(x, y+1, world);
        if (left && !right) moveTo(x-1, y, world);
        if (right && !left) moveTo(x+1, y, world);
    }

    private void moveTo(int nx,int ny, TileMap world) {
        if (nx<0||ny<0||nx>=world.getWidth()||ny>=world.getHeight()) return;
        x = nx; y = ny;
    }

    public void render(Graphics2D g2d, int sx, int sy, int tileSize, int scale) {
        if (idleSheet == null || idleSheet.image == null) { g2d.setColor(Color.RED); g2d.fillRect(sx, sy, tileSize*scale, tileSize*scale); return; }
        int frames = Math.max(1, idleSheet.image.getWidth() / tileSize);
        int frame = animIndex % frames;
        BufferedImage img = idleSheet.get(frame, 0);
        g2d.drawImage(img, sx, sy, tileSize*scale, tileSize*scale, null);
    }

    public void addCoins(int c){ coins += c; }
    public boolean spendCoins(int c){ if (coins >= c){ coins -= c; return true; } return false; }
    public void addSeed(String id,int amt){ seeds.put(id, seeds.getOrDefault(id,0) + amt); }
    public boolean removeSeed(String id,int amt){ int have = seeds.getOrDefault(id,0); if (have>=amt){ seeds.put(id, have-amt); return true;} return false; }
    public int getSeedCount(String id){ return seeds.getOrDefault(id,0); }
    public int getX(){ return x; } public int getY(){ return y; }
    public Tool getSelectedTool(){ return selectedTool; }
    public String getSelectedSeed(){ return selectedSeed; }
    public void setSelectedTool(Tool t){ selectedTool = t; }
    public void setSelectedSeed(String s){ selectedSeed = s; }
    public int getCoins(){ return coins; }
    public Map<String,Integer> getSeeds(){ return seeds; }
}

