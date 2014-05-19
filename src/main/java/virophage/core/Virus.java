package virophage.core;

import java.util.Date;
import java.util.TimerTask;

import virophage.gui.GameClient;
import virophage.render.RenderTree;

/**
 * A <code>Virus</code> represents an occupant of a cell that belongs to a player and has a given amount of energy.
 *
 * @author Max Ovsiankin, Leon Ren
 * @version 1.0 (Alpha)
 * @since 2014-05-6
 */
public class Virus implements Cloneable {

    private Player player;
    private int energy;

    public void setEnergy(int energy) {
        this.energy = energy;
    }

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
    
    public void schedule() {
    	RenderTree.timer.schedule(new TimerTask() {
				
				@Override
				public void run() {
					if(getEnergy() < GameClient.MAX_ENERGY) {
						setEnergy(getEnergy() + 1);
					}
				}
			}, new Date(System.currentTimeMillis()), 10000);
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

    public Virus clone() throws CloneNotSupportedException {
        Virus v = (Virus) super.clone();
        return v;
    }

}
