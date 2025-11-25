import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Detects tile width/height and grid layout in a sprite image.
 * Returns a Result with suggestions.
 */
public class AutoTileDetector {
    public static class Result {
        public final int tileW, tileH, cols, rows;
        public final String info;
        public Result(int tileW, int tileH, int cols, int rows, String info) {
            this.tileW = tileW; this.tileH = tileH; this.cols = cols; this.rows = rows; this.info = info;
        }
        @Override public String toString() {
            return "Result{tileW=" + tileW + ", tileH=" + tileH + ", cols=" + cols + ", rows=" + rows + ", info=" + info + "}";
        }
    }

    public static Result detect(String path) {
        try {
            BufferedImage img = ImageIO.read(new File(path));
            if (img == null) return new Result(16,16,0,0,"empty");
            int w = img.getWidth(), h = img.getHeight();
            int[] colCount = new int[w];
            int[] rowCount = new int[h];
            for (int y=0;y<h;y++){
                for (int x=0;x<w;x++){
                    int px = img.getRGB(x,y);
                    int a = (px >>> 24) & 0xFF;
                    int r = (px >>> 16) & 0xFF;
                    int g = (px >>> 8) & 0xFF;
                    int b = (px) & 0xFF;
                    boolean visible = a > 16 || (r+g+b) > 32;
                    if (visible) { colCount[x]++; rowCount[y]++; }
                }
            }
            List<Integer> sigCols = findSignificant(colCount);
            List<Integer> sigRows = findSignificant(rowCount);
            int tileW = computeGcdDiff(sigCols);
            int tileH = computeGcdDiff(sigRows);
            if (tileW <= 0) tileW = fallback(w,h);
            if (tileH <= 0) tileH = fallback(h,w);
            int cols = Math.max(1, w / tileW);
            int rows = Math.max(1, h / tileH);
            String info = String.format("img %dx%d sigCols=%d sigRows=%d", w,h,sigCols.size(),sigRows.size());
            return new Result(tileW,tileH,cols,rows,info);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(16,16,0,0,"exception");
        }
    }

    private static List<Integer> findSignificant(int[] counts) {
        List<Integer> list = new ArrayList<>();
        int max=0; for(int v:counts) if (v>max) max=v;
        int thr = Math.max(1, max/10);
        boolean run=false; int rs=0;
        for (int i=0;i<counts.length;i++){
            boolean above = counts[i] > thr;
            if (above && !run) { run=true; rs=i; }
            if (!above && run) { list.add((rs + i -1)/2); run=false; }
        }
        if (run) list.add((rs + counts.length -1)/2);
        if (list.isEmpty()) { for (int i=0;i<counts.length;i++) if (counts[i]>0) { list.add(i); break; } }
        return list;
    }

    private static int computeGcdDiff(List<Integer> idx) {
        if (idx.size() < 2) return -1;
        int gcd = -1;
        for (int i=1;i<idx.size();i++){
            int d = Math.abs(idx.get(i)-idx.get(i-1));
            if (d<=0) continue;
            gcd = gcd == -1 ? d : gcd(gcd,d);
            if (gcd == 1) break;
        }
        return gcd;
    }

    private static int gcd(int a,int b){ return b==0? a : gcd(b, a%b); }

    private static int fallback(int primary, int secondary) {
        int[] cand = {64,48,32,24,16,8};
        for (int c : cand) if (primary % c == 0 && secondary % c == 0) return c;
        for (int c : cand) if (primary % c == 0) return c;
        int g = gcd(primary, secondary);
        if (g >= 8 && g <= 64) return g;
        return 16;
    }

    // quick CLI test
    public static void main(String[] args) throws Exception {
        if (args.length==0) { System.out.println("Usage: java AutoTileDetector <imagePath>"); return; }
        Result r = detect(args[0]);
        System.out.println("AutoTileDetector -> " + r);
    }
}


