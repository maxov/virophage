package virophage;

public class GameLoop extends Thread {

	public GameLoop() {
		
	}
	
	public void run() {
		while(true) {
			long start = System.nanoTime();
			
			long end = System.nanoTime();

			try {
				Thread.sleep(1000 / 60);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
	
}
