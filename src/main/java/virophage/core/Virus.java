package virophage.core;

/**
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class Virus {

    private Player player;
    private int energy;

    public Virus(Player player, int energy) {
        this.player = player;
        this.energy = energy;
        player.addVirus(this);
    }

    public Player getPlayer() {
        return player;
    }

    public int getEnergy() {
        return energy;
    }
}
