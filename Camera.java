public class Camera {
    private final int viewX, viewY, worldW, worldH;
    private Player player;
    private int x, y;

    public Camera(int viewX, int viewY, int worldW, int worldH, Player player) {
        this.viewX = viewX; this.viewY = viewY; this.worldW = worldW; this.worldH = worldH;
        this.player = player; update();
    }

    public void setPlayer(Player p){ this.player = p; update(); }

    public void update(){
        if (player == null) return;
        x = player.getX() - viewX/2; y = player.getY() - viewY/2;
        if (x < 0) x = 0; if (y < 0) y = 0;
        if (x + viewX > worldW) x = worldW - viewX;
        if (y + viewY > worldH) y = worldH - viewY;
    }

    public int getX(){ return x; } public int getY(){ return y; }
}



