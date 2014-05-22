package virophage.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import virophage.Start;
import virophage.gui.GameClient;
import virophage.util.Location;
import virophage.render.RenderTree;

/**
 * A <code>Virus</code> represents an occupant of a cell that belongs to a player and has a given amount of energy.
 *
 * @author Max Ovsiankin, Leon Ren
 * @version 1.0 (Alpha)
 * @since 2014-05-6
 */
public class Virus implements Serializable {

    private Player player;
    private int energy;
    private Cell cell;
    
    private TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			if(getEnergy() < GameClient.MAX_ENERGY) {
				setEnergy(getEnergy() + 1);
			}
			
			if (cell != null && !(cell instanceof DeadCell) && player instanceof MachinePlayer) {
				Tissue tissue = cell.getTissue();
  				// spawn a virus in empty neighbor
  				ArrayList<Location> locations = cell.location.getNeighbors();
  				Start.log.info("size:" + locations.size());
  				for (Location loc: locations) {
  					if (tissue.getCell(loc).getOccupant() == null && !(cell instanceof DeadCell)) {
  					
  						Virus virus = new Virus(player, 1);
                        
                        tissue.getCell(loc).setOccupant(virus);
                        virus.schedule();
                        
                        break;
  					}
  				}
  			}
		}
	};

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * Constructs a virus belonging to a certain player containing a given amount of energy.
     * @param player The player this virus belongs to.
     * @param energy The initial energy of this virus
     */
    public Virus(Player player, int energy) {
        this.player = player;
        this.energy = energy;
        player.addVirus(this);
    }
    
    public void schedule() {
    	RenderTree.timer.schedule(task, new Date(System.currentTimeMillis()), 10000);
    }
    
    public void destroy() {
    	task.cancel();
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public int getEnergy() {
        return energy;
    }

    public void setCell(Cell c) {
    	cell = c;
    }
    
    public Cell getCell() {
    	return cell;
    }
}
