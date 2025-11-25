import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class GameWindow extends Canvas implements Runnable {
    private Thread thread;
    private boolean running = false;
    private Game game;

    public GameWindow() {
        // Default size; Game will auto-adjust preferred size after detection
        setPreferredSize(new Dimension(16 * 20 * 3, 16 * 15 * 3));
        JFrame frame = new JFrame("Farming - AutoTile");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        game = new Game(this);
        addKeyListener(game.getInput());
        addMouseListener(game.getInput());
        addMouseMotionListener(game.getInput());
        setFocusable(true);
    }

    public synchronized void start() {
        if (running) return;
        running = true;
        thread = new Thread(this, "GameLoop");
        thread.start();
    }

    public void run() {
        createBufferStrategy(3);
        BufferStrategy bs = getBufferStrategy();
        final double TPS = 60.0;
        final double NS_PER_TICK = 1_000_000_000.0 / TPS;
        long last = System.nanoTime();
        double delta = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - last) / NS_PER_TICK;
            last = now;
            while (delta >= 1) {
                game.update();
                delta--;
            }
            Graphics g = bs.getDrawGraphics();
            game.render((Graphics2D) g, getWidth(), getHeight());
            g.dispose();
            bs.show();
            try { Thread.sleep(2); } catch (InterruptedException ignored) {}
        }
    }
}

