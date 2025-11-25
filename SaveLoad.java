import java.io.*;

public class SaveLoad {
    public static void saveGame(String filename, Game game) {
        System.out.println("Save stub: wrote marker to " + filename);
        try (PrintWriter out = new PrintWriter(new FileWriter(filename))) { out.println("SAVE_STUB"); }
        catch (IOException e) { System.err.println("Save failed: " + e.getMessage()); }
    }

    public static Game loadGame(String filename) {
        System.out.println("Load stub: not implemented.");
        return null;
    }
}
