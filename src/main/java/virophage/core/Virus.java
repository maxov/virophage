package virophage.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import virophage.Start;
import virophage.gui.GameClient;
import virophage.gui.GameScreen;
import virophage.util.Location;

/**
 * A <code>Virus</code> represents an occupant of a cell that belongs to a player and has a given amount of energy.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-6
 */
public class Virus implements Serializable {

    private Player player;
    private int energy;
    private Cell cell;

    private TimerTask task = new TimerTask() {
        /**
         * Spawns new cells.
         */
        @Override
        public void run() {
            if (getEnergy() < GameClient.MAX_ENERGY) {
                setEnergy(getEnergy() + 1);
            }
        }
    };

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    /**
     * Constructs a virus belonging to a certain player containing a given amount of energy.
     *
     * @param player The player this virus belongs to.
     * @param energy The initial energy of this virus
     */
    public Virus(Player player, int energy) {
        this.player = player;
        this.energy = energy;
        player.addVirus(this);
    }

    /**
     * Schedules this task.
     */
    public void schedule() {
        GameScreen.timer.schedule(task, 10000, 10000);
    }

    /**
     * Cancels this task.
     */
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
        if (c == null) {
            Start.log.info("whoah");
        }
        cell = c;
    }

    public Cell getCell() {
        return cell;
    }
}
