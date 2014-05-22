package virophage.game;

/**
 * A <code>Timeable</code> is an action that happens on one tick, or a number of ticks.
 * For an explanation of ticks, look at {@link virophage.game.Scheduler}.
 *
 * @author Max Ovsiankin
 * @since 2014-05-16
 */
public interface Timeable {

    /**
     * Performs this Timeable.
     *
     * @param tick the current tick.
     */
    public abstract void act(int tick);

    /**
     * Checks whether this timeable should preform in a certain tick.
     *
     * @param tick the current tick
     * @return true if this Timeable should act in this tick
     */
    public abstract boolean shouldAct(int tick);

}
