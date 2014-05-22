package virophage.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import virophage.Start;
import virophage.core.Virus;
import virophage.gui.GameClient;
import virophage.math.Location;
import virophage.render.RenderTree;

/**
 * A <code>Cell</code> is an area that can contain a virus.
 *
 * @author Leon Ren
 * @since 2014-05-6
 */
public class Cell {

	public Tissue tissue;
    public Virus occupant;
    public Location location;

    /**
     * Constructs a cell with a <code>Virus</code> occupant
     * @param occupant
     */
    public Cell(Tissue tissue, Location loc, Virus occupant) {
    	this.tissue = tissue;
    	this.location = loc;
        this.occupant = occupant;
    }
    
    public Cell(Tissue tissue, Location loc) {
    	this(tissue, loc, null);
    }
  	
    public Virus getOccupant() {
        return occupant;
    }

    public void setOccupant(Virus occupant) {
        this.occupant = occupant;
    }
    
    public Tissue getTissue() {
    	return tissue;
    }
}