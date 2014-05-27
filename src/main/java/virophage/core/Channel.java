package virophage.core;

import java.io.Serializable;
import java.util.Date;
import java.util.Iterator;
import java.util.TimerTask;

import virophage.Start;
import virophage.gui.GameScreen;
import virophage.util.GameConstants;
import virophage.util.Location;

/**
 * A <code>Channel</code> represents a bridge between two cells.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-6
 */
public class Channel implements Serializable {

    public Tissue tissue;
    public Location from;
    public Location to;
    public Player player;
    public Virus virus;
    private int creationTime;

    private transient TimerTask task = new TimerTask() {
        /**
         * Timer - Timeout Method.
         * Transfers energy and removes channel when "conquered" by opponent.
         * @post the energy of the "from" cell will be reduced by one. The energy of the "to" cell will be increased by one.
         */
        @Override
        public void run() {
            synchronized(tissue) {
                Cell f = tissue.getCell(from);
                Cell t = tissue.getCell(to);
                Virus v = f.occupant;
                Virus v1 = t.occupant;
                if (v != null && v.getEnergy() > 1) {
                    if (v1 != null) {
                        if (v.getPlayer().equals(v1.getPlayer())) {
                            if (v1.getEnergy() < GameConstants.MAX_ENERGY) {
                                v1.setEnergy(v1.getEnergy() + 1);
                                v.setEnergy(v.getEnergy() - 1);
                            }
                        } else {
                            if (!hasVirus()) {
                                createVirus();
                            }
                            Virus my = getVirus();
                            if (my.getEnergy() < GameConstants.MAX_ENERGY) {
                                my.setEnergy(my.getEnergy() + 1);
                                v.setEnergy(v.getEnergy() - 1);
                                if (my.getEnergy() >= v1.getEnergy()) {
                                    Player p = t.occupant.getPlayer();
                                    Iterator<Channel> channels = p.getChannels().iterator();
                                    while (channels.hasNext()) {
                                        Channel c = channels.next();
                                        if (c.from.equals(to) || c.to.equals(to)) {
                                            channels.remove();
                                            p.removeChannel(c);
                                            c.destroy();
                                        }
                                    }
                                    p.removeVirus(t.occupant);
                                    t.occupant.destroy();
                                    t.setOccupant(my);
                                    my.setCell(t);
                                    my.getPlayer().addVirus(my);
                                    my.schedule();
                                    setVirus(null);
                                }
                            }

                        }
                    } else {
                        if (t instanceof BonusCell) {
                            ((BonusCell) t).setPlayer(v.getPlayer());
                            //tissue.getTree().getGame().removeBonusCell((BonusCell)t);
                            Player p = null;
                            int numTaken = 0;
                            for (BonusCell c : tissue.getBonuses()) {
                                if (p == null && c.getPlayer() != null) {
                                    p = c.getPlayer();
                                    numTaken++;
                                } else if (c.getPlayer() != null) {
                                    if (!p.equals(c.getPlayer())) {
                                        break;
                                    } else {
                                        numTaken++;
                                    }
                                }
                            }
                            if (numTaken == 7) {
                                Start.log.info("ALL BONUSES TAKEN");
                                for (Virus vx : p.getViruses()) {
                                    if (vx.getUpdateTime() == 5000) {
                                        continue;
                                    }
                                    // half the time to update
                                    vx.setTimeToUpdate(5000);
                                    vx.reschedule();
                                }
                            }
                        }

                        t.occupant = new Virus(v.getPlayer(), 0);
                        t.occupant.setCell(t);
                        v.getPlayer().addVirus(t.occupant);
                        t.occupant.schedule();
                        v.setEnergy(v.getEnergy() - 1);
                    }
                }
            }
        }
    };

    /**
     * Constructs a Channel for a player between two locations.
     *
     * @param from   The location the bridge starts from
     * @param to     The location the bridge goes to
     * @param player The current player making this bridge
     */
    public Channel(final Tissue tissue, final Location from, final Location to, final Player player) {
        this.tissue = tissue;
        this.from = from;
        this.to = to;
        this.player = player;
        virus = null;
        GameScreen.timer.schedule(task, 2000, 2000);
    }

    public Channel() {

    }

    public boolean canInfectAt(int time) {
        return (time - creationTime) % GameConstants.CHANNEL_MOVE_TICKS == 0;
    }

    /**
     * Destroys this Channel.
     * @post timer is canceled
     */
    public void destroy() {
        task.cancel();
    }

    /**
     * Creates a new virus, within this channel.
     */
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

    public void setCreationTime(int creationTime) {
        this.creationTime = creationTime;
    }

    public int getCreationTime() {
        return creationTime;
    }

}
