import java.awt.event.*;
import java.util.HashSet;
import java.util.Set;

public class InputHandler implements KeyListener, MouseListener, MouseMotionListener {
    private final Set<Integer> keysDown = new HashSet<>();
    private final Set<Integer> keysJust = new HashSet<>();
    private final Set<Integer> mouseDown = new HashSet<>();
    private final Set<Integer> mouseJust = new HashSet<>();
    private int mouseX, mouseY;

    public void keyPressed(KeyEvent e){ int k = e.getKeyCode(); if (!keysDown.contains(k)) keysJust.add(k); keysDown.add(k); }
    public void keyReleased(KeyEvent e){ keysDown.remove(e.getKeyCode()); }
    public void keyTyped(KeyEvent e){}
    public boolean isKeyDown(int k){ return keysDown.contains(k); }
    public boolean isKeyJustPressed(int k){ if (keysJust.contains(k)){ keysJust.remove(k); return true; } return false; }

    public void mousePressed(MouseEvent e){ int b = e.getButton(); if (!mouseDown.contains(b)) mouseJust.add(b); mouseDown.add(b); }
    public void mouseReleased(MouseEvent e){ mouseDown.remove(e.getButton()); }
    public void mouseClicked(MouseEvent e){} public void mouseEntered(MouseEvent e){} public void mouseExited(MouseEvent e){}
    public boolean isMousePressed(int b){ return mouseDown.contains(b); }
    public boolean isMouseJustPressed(int b){ if (mouseJust.contains(b)){ mouseJust.remove(b); return true; } return false; }
    public void consumeMousePress(int b){ mouseJust.remove(b); mouseDown.remove(b); }

    public void mouseMoved(MouseEvent e){ mouseX = e.getX(); mouseY = e.getY(); }
    public void mouseDragged(MouseEvent e){ mouseMoved(e); }
    public int getMouseX(){ return mouseX; }
    public int getMouseY(){ return mouseY; }
}


