package virophage.core;

import java.awt.Color;
import java.io.Serializable;

/**
 * Represents and AI player
 * @author Leon
 *
 */
public class MachinePlayer extends Player implements Serializable {

	public MachinePlayer() {
		super();
	}
	/**
	 * Contructs a new MachinePlayer with a given color in a given tissue.
	 * @param color - the color of this MachinePlayer
	 * @param tissue - the gameboard that will contain this MachinePlayer
	 */
	public MachinePlayer(Color color, Tissue tissue) {
		super(color, tissue);
	}

}
