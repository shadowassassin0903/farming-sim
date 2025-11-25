import java.awt.*;
import java.awt.image.BufferedImage;

public class Tile {
    private int groundCol=0, groundRow=0;
    private boolean tilled=false;
    private Crop crop=null;

    public void setGroundTile(int col,int row){ groundCol=col; groundRow=row; }
    public boolean isTilled(){ return tilled; }
    public void till(){ tilled=true; }
    public void plant(Crop c){ crop=c; }
    public Crop getCrop(){ return crop; }
    public int harvest(){ if (crop==null) return 0; int y = crop.getYieldCoins(); crop=null; tilled=true; return y; }

    public void render(java.awt.Graphics2D g2d, int x, int y, int tileSize, int scale, Tileset tileset, CropSheet cropSheet) {
        BufferedImage ground = tileset.get(groundCol, groundRow);
        g2d.drawImage(ground, x, y, tileSize*scale, tileSize*scale, null);
        if (tilled) {
            BufferedImage tilledImg = tileset.get(1,0);
            g2d.drawImage(tilledImg, x, y, tileSize*scale, tileSize*scale, null);
        }
        if (crop!=null) crop.render(g2d, x, y, tileSize, scale);
    }
}


