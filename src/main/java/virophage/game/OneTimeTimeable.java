package virophage.game;

/**
 * A <code>OneTimeTimeable</code> is a Timeable that should only act one time.
 *
 * @author Max Ovsiankin
 * @since 2014-05-16
 */
public abstract class OneTimeTimeable implements Timeable {

    private int time;
    private Scheduler scheduler;

    public OneTimeTimeable(int time, Scheduler scheduler) {
        this.time = time;
        this.scheduler = scheduler;
    }

    /**
     * Perform this OneTimeTimeable.
     *
     * @param tick the current tick
     */
    @Override
    public void act(int tick) {
        scheduler.cancelTask(this);
    }

    /**
     * Check if this Timeable should act in a certain tick.
     *
     * @param tick the current tick
     * @return true if this timeable should act in this tick
     */
    @Override
    public boolean shouldAct(int tick) {
        return tick == time;
    }

}
