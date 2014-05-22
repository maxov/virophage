package virophage.game;

/**
 * An <code>IntervalTimeable</code> is a Timeable that should act multiple times.
 *
 * @author Max Ovsiankin
 * @since 2014-05-16
 */
public abstract class IntervalTimeable implements Timeable {

    private int interval;
    private int start;

    /**
     * Create an IntervalTimeable.
     *
     * @param interval the interval tick
     * @param start    the starting tick
     */
    public IntervalTimeable(int interval, int start) {
        this.interval = interval;
        this.start = start;
    }

    /**
     * Perform this IntervalTimeable.
     *
     * @param tick the current tick
     */
    @Override
    public abstract void act(int tick);

    /**
     * Check whether this IntervalTimeable should be performed.
     *
     * @param tick the current tick
     * @return true if this timeable should act in this tick
     */
    @Override
    public boolean shouldAct(int tick) {
        return (tick - start) % interval == 0;
    }

}
