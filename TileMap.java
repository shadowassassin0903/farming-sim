public class TileMap {
    private final int width, height;
    private final Tile[][] tiles;
    private final Tileset tileset;
    private final int tileSize;

    public TileMap(int width, int height, Tileset tileset, int tileSize) {
        this.width = width; this.height = height; this.tileset = tileset; this.tileSize = tileSize;
        tiles = new Tile[width][height];
        generate();
    }

    private void generate() {
        for (int x=0;x<width;x++) for (int y=0;y<height;y++) {
            Tile t = new Tile();
            t.setGroundTile(0,0);
            tiles[x][y] = t;
        }
    }

    public Tile getTile(int x,int y) {
        if (x<0||y<0||x>=width||y>=height) return new Tile();
        return tiles[x][y];
    }

    public void advanceDay() {
        for (int x=0;x<width;x++) for (int y=0;y<height;y++) {
            Tile t = tiles[x][y];
            if (t.getCrop()!=null) t.getCrop().newDay();
        }
    }

    public int getWidth(){ return width; }
    public int getHeight(){ return height; }
}
