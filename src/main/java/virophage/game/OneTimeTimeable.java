package virophage.game;

/**
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

    @Override
    public void act(int tick) {
        scheduler.cancelTask(this);
    }

    @Override
    public boolean shouldAct(int tick) {
        return tick == time;
    }

}
