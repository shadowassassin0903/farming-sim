import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class SpriteSheet {
    protected String path;
    protected transient BufferedImage image;
    protected int tileW, tileH;

    public SpriteSheet(String path, int tileW, int tileH) {
        this.path = path; this.tileW = tileW; this.tileH = tileH;
        load();
    }

    public void load() {
        try { image = ImageIO.read(new File(path)); }
        catch (Exception e) { image = new BufferedImage(tileW, tileH, BufferedImage.TYPE_INT_ARGB); }
    }

    public BufferedImage get(int col, int row) {
        if (image == null) load();
        int x = col * tileW, y = row * tileH;
        if (x + tileW > image.getWidth() || y + tileH > image.getHeight())
            return new BufferedImage(tileW, tileH, BufferedImage.TYPE_INT_ARGB);
        return image.getSubimage(x, y, tileW, tileH);
    }
}


