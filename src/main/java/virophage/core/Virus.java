package virophage.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.TimerTask;

import virophage.Start;
import virophage.gui.GameScreen;
import virophage.util.GameConstants;
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
    private int timeToUpdate;
    private int creationTime;

    private TimerTask task = recreateTask();

    /**
     * Recreates a TimerTask;
     * @return the newly createdTimerTask
     */
    private TimerTask recreateTask() {
        return new TimerTask() {
            /**
             * Spawns new cells.
             */
            @Override
            public void run() {
                if (getEnergy() < GameConstants.MAX_ENERGY) {
                    setEnergy(getEnergy() + 1);
                }
            }
        };
    }

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
        this.timeToUpdate = 10000;
    }

    /**
     * Schedules this task.
     */
    public void schedule() {
        GameScreen.timer.schedule(task, timeToUpdate, timeToUpdate);
    }
    
    /**
     * Reschedules the timer according to the new TimeoutTime.
     */
    public void reschedule() {
        task.cancel();
        task = recreateTask();
    	GameScreen.timer.schedule(task, timeToUpdate, timeToUpdate);
    }
    public void setTimeToUpdate(int t){
    	timeToUpdate = t;
    }
    
    public int getUpdateTime(){
    	return timeToUpdate;
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

    /**
     * Sets the cell the virus belongs to.
     * @pre c is not null
     * @param c the cell (parent)
     */
    public void setCell(Cell c) {
        if (c == null) {
            Start.log.info("whoah");
        }
        cell = c;
    }

    public Cell getCell() {
        return cell;
    }

    public int getCreationTime() {
        return creationTime;
    }

    public void setCreationTime(int creationTime) {
        this.creationTime = creationTime;
    }
}
