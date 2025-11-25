import java.awt.*;
import java.awt.image.BufferedImage;

public class Crop {
    private final String id;
    private final int colIndex;
    private int stage = 0;
    private final int[] daysPerStage = new int[]{1,2,1};
    private int daysInStage = 0;
    private boolean watered = false;
    private final CropSheet sheet;

    public Crop(String id, int colIndex, CropSheet sheet) {
        this.id = id; this.colIndex = colIndex; this.sheet = sheet;
    }

    public void newDay() {
        if (watered) {
            daysInStage++;
            if (daysInStage >= daysPerStage[Math.min(stage, daysPerStage.length-1)]) {
                stage = Math.min(stage+1, daysPerStage.length-1);
                daysInStage = 0;
            }
        }
        watered = false;
    }

    public boolean isHarvestable(){ return stage >= daysPerStage.length-1; }
    public void setWatered(boolean v){ watered = v; }
    public int getYieldCoins(){ return 5; }

    public void render(Graphics2D g2d, int x, int y, int tileSize, int scale) {
        BufferedImage img = sheet.get(colIndex, stage);
        g2d.drawImage(img, x, y, tileSize*scale, tileSize*scale, null);
    }
}

