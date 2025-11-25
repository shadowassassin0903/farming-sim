import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Game {
    private final Canvas owner;
    private final InputHandler input;
    private Camera camera;
    private Tileset tileset;
    private CropSheet cropSheet;
    private SpriteSheet idle, walk;
    private TileMap world;
    private Player player;
    private HUD hud;

    private int tileSize = 16;
    private final int scale = 3;
    private final int viewTilesX = 20;
    private final int viewTilesY = 15;

    private long lastDayTime;
    private long dayLengthMs = 20_000;
    private int day = 1;

    public Game(Canvas owner) {
        this.owner = owner;
        input = new InputHandler();

        // Auto-detect tile size using spritesheet if available
        try {
            AutoTileDetector.Result det = AutoTileDetector.detect("assets/Tileset Spring.png");
            System.out.println("AutoTileDetector: " + det);
            tileSize = Math.max(8, det.tileW);
        } catch (Exception e) {
            System.out.println("AutoTile detect failed: using default 16");
            tileSize = 16;
        }

        // Set window preferred size based on tileSize
        int pxW = tileSize * viewTilesX * scale;
        int pxH = tileSize * viewTilesY * scale;
        owner.setPreferredSize(new Dimension(pxW, pxH));
        owner.getParent(); // may be null; ok

        tileset = new Tileset("assets/Tileset Spring.png", tileSize, tileSize);
        cropSheet = new CropSheet("assets/Spring Crops.png", tileSize, tileSize);
        idle = new SpriteSheet("assets/Idle.png", tileSize, tileSize);
        walk = new SpriteSheet("assets/Walk.png", tileSize, tileSize);

        world = new TileMap(80, 60, tileset, tileSize);
        player = new Player(world.getWidth()/2, world.getHeight()/2, idle, walk);
        player.setWorld(world);
        camera = new Camera(viewTilesX, viewTilesY, world.getWidth(), world.getHeight(), player);
        hud = new HUD(this);

        lastDayTime = System.currentTimeMillis();
    }

    public InputHandler getInput() { return input; }

    public void update() {
        handleInput();
        player.update();
        camera.update();
        if (System.currentTimeMillis() - lastDayTime >= dayLengthMs) {
            day++;
            world.advanceDay();
            lastDayTime = System.currentTimeMillis();
            System.out.println("Day -> " + day);
        }
    }

    private void handleInput() {
        boolean up = input.isKeyDown(java.awt.event.KeyEvent.VK_W);
        boolean down = input.isKeyDown(java.awt.event.KeyEvent.VK_S);
        boolean left = input.isKeyDown(java.awt.event.KeyEvent.VK_A);
        boolean right = input.isKeyDown(java.awt.event.KeyEvent.VK_D);
        player.moveDirectional(up, down, left, right, world);

        if (input.isMousePressed(1)) {
            Point t = screenToTile(input.getMouseX(), input.getMouseY());
            if (t != null) {
                Tile tile = world.getTile(t.x, t.y);
                if (!tile.isTilled()) tile.till();
                else if (player.getSelectedSeed() != null && player.getSeedCount(player.getSelectedSeed()) > 0 && tile.getCrop() == null) {
                    Crop c = cropSheet.createCrop(player.getSelectedSeed());
                    tile.plant(c);
                    player.removeSeed(player.getSelectedSeed(), 1);
                } else if (tile.getCrop() != null && tile.getCrop().isHarvestable()) {
                    int coins = tile.harvest();
                    player.addCoins(coins);
                }
            }
            input.consumeMousePress(1);
        }

        if (input.isMousePressed(3)) {
            Point t = screenToTile(input.getMouseX(), input.getMouseY());
            if (t != null) {
                Tile tile = world.getTile(t.x, t.y);
                if (tile.getCrop() != null) tile.getCrop().setWatered(true);
            }
            input.consumeMousePress(3);
        }

        if (input.isKeyJustPressed(java.awt.event.KeyEvent.VK_1)) {
            if (player.spendCoins(5)) player.addSeed("carrot", 1);
        }
        if (input.isKeyJustPressed(java.awt.event.KeyEvent.VK_2)) player.setSelectedSeed("carrot");
        if (input.isKeyJustPressed(java.awt.event.KeyEvent.VK_H)) player.setSelectedTool(Player.Tool.HOE);
    }

    private Point screenToTile(int mx, int my) {
        int worldX = camera.getX() + (mx / (tileSize * scale));
        int worldY = camera.getY() + (my / (tileSize * scale));
        if (worldX < 0 || worldY < 0 || worldX >= world.getWidth() || worldY >= world.getHeight()) return null;
        return new Point(worldX, worldY);
    }

    public void render(Graphics2D g2d, int screenW, int screenH) {
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0,0,screenW,screenH);

        // Debug: draw raw sheets top-left for quick inspection (small)
        try {
            BufferedImage tImg = ImageIO.read(new File("assets/Tileset Spring.png"));
            BufferedImage cImg = ImageIO.read(new File("assets/Spring Crops.png"));
            if (tImg != null) g2d.drawImage(tImg, 4, 36, Math.min(256, tImg.getWidth()), Math.min(256, tImg.getHeight()), null);
            if (cImg != null) g2d.drawImage(cImg, 8 + Math.min(256, (tImg==null?0:tImg.getWidth())), 36, Math.min(256, cImg.getWidth()), Math.min(256, cImg.getHeight()), null);
        } catch (Exception ignored) {}

        int startX = camera.getX();
        int startY = camera.getY();
        for (int y = startY; y < startY + viewTilesY; y++) {
            for (int x = startX; x < startX + viewTilesX; x++) {
                Tile t = world.getTile(x, y);
                int dx = (x - startX) * tileSize * scale;
                int dy = (y - startY) * tileSize * scale;
                t.render(g2d, dx, dy, tileSize, scale, tileset, cropSheet);
            }
        }

        int px = (player.getX() - startX) * tileSize * scale;
        int py = (player.getY() - startY) * tileSize * scale;
        player.render(g2d, px, py, tileSize, scale);

        hud.render(g2d, screenW, screenH, day);
    }

    // getters for HUD or save systems
    public Player getPlayer() { return player; }
    public TileMap getWorld() { return world; }
}

