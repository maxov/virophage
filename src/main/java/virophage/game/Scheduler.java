package virophage.game;

import java.util.ArrayList;

/**
 * A <code>Scheduler</code> is a class that schedules a bunch of Timeables.
 * It splits game time into seperate ticks, and excecutes the timeables.
 *
 * @author Max Ovsiankin
 * @since 2014-05-16
 */
public class Scheduler {

    private int tick;
    private ArrayList<Timeable> tasks = new ArrayList<Timeable>();
    private ArrayList<Timeable> tasksToCancel = new ArrayList<Timeable>();

    /**
     * Create a new scheduler.
     */
    public Scheduler() {
        this(0);
    }

    /**
     * Create a scheduler starting on a certain tick.
     *
     * @param tick the starting tick
     */
    public Scheduler(int tick) {
        this.tick = tick;
    }

    /**
     * Add a Timeable to this scheduler
     *
     * @param task a Timeable
     */
    public void addTask(Timeable task) {
        tasks.add(task);
    }

    /**
     * Cancel this Timeable.
     *
     * @param task a Timeable
     */
    public void cancelTask(Timeable task) {
        tasksToCancel.add(task);
    }

    /**
     * Do a tick. This first removes Timeables scheduled for deletion, then acts out the remaining ones.
     */
    public void tick() {
        ArrayList<Timeable> cancel = tasksToCancel;
        tasksToCancel = new ArrayList<Timeable>();

        tasks.removeAll(cancel);

        for (Timeable task : tasks) {
            if (task.shouldAct(tick)) {
                task.act(tick);
            }
        }

        tick++;
    }

}
