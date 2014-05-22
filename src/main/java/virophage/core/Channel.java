package virophage.core;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.TimerTask;

import virophage.gui.GameClient;
import virophage.util.Location;
import virophage.render.RenderTree;

/**
 * A <code>Channel</code> represents a bridge between two cells.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-6
 * 
 */
public class Channel implements Serializable {

	public Tissue tissue;
    public Location from;
    public Location to;
    public Player player;
    public Virus virus;
    
    private TimerTask task = new TimerTask() {
		
		@Override
		public void run() {
			Cell f = tissue.getCell(from);
			Cell t = tissue.getCell(to);
			Virus v = f.occupant;
			Virus v1 = t.occupant;
			if(v != null && v.getEnergy() > 1) {
				if(v1 != null) {
					if(v.getPlayer().equals(v1.getPlayer())) {
						if(v1.getEnergy() < GameClient.MAX_ENERGY) {
							v1.setEnergy(v1.getEnergy() + 1);
							v.setEnergy(v.getEnergy() - 1);
						}
					} else {
						if(!hasVirus()) {
							createVirus();
						}
						Virus my = getVirus();
						if(my.getEnergy() < GameClient.MAX_ENERGY) {
							my.setEnergy(my.getEnergy() + 1);
							v.setEnergy(v.getEnergy() - 1);
							if(my.getEnergy() >= v1.getEnergy()) {
								Player p = t.occupant.getPlayer();
								Iterator<Channel> channels = p.getChannels().iterator();
								while(channels.hasNext()) {
									Channel c = channels.next();
									if(c.from.equals(to) || c.to.equals(to)) {
										channels.remove();
										p.removeChannel(c);
										c.destroy();
									}
								}
								t.occupant.destroy();
								p.removeVirus(t.occupant);
								t.occupant = my;
								my.schedule();
								
								setVirus(null);
							}
						}
						
					}
				} else {
					t.occupant = new Virus(v.getPlayer(), 0);
					t.occupant.schedule();
					v.setEnergy(v.getEnergy() - 1);
				}
			}
		}
	};
    
	/**
	 * Constructs a Channel for a player between two locations. 
	 * @param from - The location the bridge starts from
	 * @param to - The location the bridge goes to
	 * @param player - The current player making this bridge
	 */
    public Channel(final Tissue tissue, final Location from, final Location to, final Player player) {
    	this.tissue = tissue;
        this.from = from;
        this.to = to;
        this.player = player;
        virus = null;
        RenderTree.timer.schedule(task, new Date(System.currentTimeMillis()), 2000);
    }
    
    public void destroy() {
    	task.cancel();
    }

    public void createVirus() {
        this.virus = new Virus(player, 0);
    }

    public boolean hasVirus() {
        return virus != null;
    }
    
    public Virus getVirus() {
    	return this.virus;
    }
    
    public void setVirus(Virus virus) {
    	this.virus = virus;
    }

}
