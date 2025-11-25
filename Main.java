public class Main {
    public static void main(String[] args) {
        javax.swing.SwingUtilities.invokeLater(() -> {
            GameWindow gw = new GameWindow();
            gw.start();
        });
    }
}
