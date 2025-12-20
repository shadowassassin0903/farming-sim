import javax.swing.*;
import java.awt.*;

public class GameClient extends Canvas implements Runnable {

    private boolean running = false;
    private Thread gameThread;
    private GameState gameState;

    public GameClient() {
        gameState = new GameState();
        JFrame frame = new JFrame("Farming Client");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(this);
        frame.setVisible(true);
        setFocusable(true);
    }

    public void start() {
        running = true;
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        createBufferStrategy(2); // DOUBLE BUFFER
        BufferStrategy bs = getBufferStrategy();

        long lastTime = System.nanoTime();
        double nsPerTick = 1_000_000_000.0 / 60.0;
        double delta = 0;

        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / nsPerTick;
            lastTime = now;

            while (delta >= 1) {
                gameState.update();
                delta--;
            }

            Graphics g = bs.getDrawGraphics();
            Renderer.render(g, gameState);
            g.dispose();
            bs.show();
        }
    }

    public static void main(String[] args) {
        new GameClient().start();
    }
}
