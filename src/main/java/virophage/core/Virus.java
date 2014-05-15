package virophage.core;

/**
 * A <code>Virus</code> represents an occupant of a cell that belongs to a player and has a given amount of energy.
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class Virus {

    private Player player;
    private int energy;
    
    /**
     * Constructs a virus belonging to a certain player containing a given amount of energy.
     * @param player The player this virus belongs to.
     * @param energy The intial energy of this virus
     */
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
