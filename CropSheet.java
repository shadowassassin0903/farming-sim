public class CropSheet extends SpriteSheet {
    public CropSheet(String path, int tileW, int tileH) { super(path, tileW, tileH); }
    public Crop createCrop(String id) {
        int col = 0;
        if ("carrot".equals(id)) col = 0;
        // extend mapping for additional crops
        return new Crop(id, col, this);
    }
}

