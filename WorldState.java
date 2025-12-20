public class WorldState {

    public int day = 1;

    public void updatePlayer(PlayerData p) {
        // validate movement, update economy, etc.
        System.out.println("Player moved to " + p.x + "," + p.y);
    }
}
