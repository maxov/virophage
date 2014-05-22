package virophage.game;

import java.util.ArrayList;

/**
*
* @author Max Ovsiankin
* @since 2014-05-16
*/
public class Scheduler {

    private int tick;
    private ArrayList<Timeable> tasks = new ArrayList<Timeable>();
    private ArrayList<Timeable> tasksToCancel = new ArrayList<Timeable>();

    public Scheduler() {
        this(0);
    }

    public Scheduler(int tick) {
        this.tick = tick;
    }

    public void addTask(Timeable task) {
        tasks.add(task);
    }

    public void cancelTask(Timeable task) {
       tasksToCancel.add(task);
    }

    public void tick() {
        ArrayList<Timeable> cancel = tasksToCancel;
        tasksToCancel = new ArrayList<Timeable>();

        tasks.removeAll(cancel);

        for(Timeable task: tasks) {
            if(task.shouldAct(tick)) {
                task.act(tick);
            }
        }

        tick++;
    }

}
