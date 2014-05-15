package virophage.core;

/**
 * A <code>InfectingVirus</code> is a virus that can infect another virus.
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class InfectingVirus extends Virus {
	
	/**
	 * Constructs an Infecting Virus.
	 * @param player - The player that has this virus.
	 * @param energy - The energy of this virus.
	 */
    public InfectingVirus(Player player, int energy) {
        super(player, energy);
    }

}
