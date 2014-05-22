package virophage.core;

import java.awt.Color;
import java.io.Serializable;

public class MachinePlayer extends Player implements Serializable {

	public MachinePlayer() {
		super();
	}
	public MachinePlayer(Color color, Tissue tissue) {
		super(color, tissue);
	}

}
