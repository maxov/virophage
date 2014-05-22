package virophage.game;

import virophage.core.Tissue;
import virophage.util.Listening;

public class Game extends Listening implements Runnable {

    private Scheduler scheduler;
    private Tissue tissue;

    public Game(Tissue tissue) {
        this.tissue = tissue;
        scheduler = new Scheduler(0);
    }

    @Override
    public void run() {
        while(isListening()) {
            long t = System.nanoTime();
            scheduler.tick();
            int delta = (int) (System.nanoTime() - t / 1000000d);
            if (delta < 10) {
                try {
                    Thread.sleep((long) (10 - delta));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public Scheduler getScheduler() {
        return scheduler;
    }

    public Tissue getTissue() {
        return tissue;
    }

}
