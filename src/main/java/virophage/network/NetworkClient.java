package virophage.network;

public class NetworkClient extends Listening implements Runnable {

	private int port;
	private String host;
	
	public NetworkClient(int port, String host) {
		this.port = port;
		this.host = host;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	

}
