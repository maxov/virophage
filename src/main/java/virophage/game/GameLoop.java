package virophage.game;


public class GameLoop implements Runnable {

    private ServerGame game;
    private boolean running = true;

    public GameLoop(ServerGame game) {
        this.game = game;
    }

    public void start() {
        new Thread(this).start();
    }

    public boolean isRunning() {
        return running;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    private long getTime() {
        return (long) ((double) System.nanoTime() / 1000000);
    }

    @Override
    public void run() {
        Thread.currentThread().setName("GameLoop");
        int tick = 1;
        while(running) {
            long t1 = getTime();
            game.updateGameState();
            long delta = getTime() - t1;
            if(delta < 100) {
                try {
                    Thread.sleep(100 - delta);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            tick++;
        }
    }

}
