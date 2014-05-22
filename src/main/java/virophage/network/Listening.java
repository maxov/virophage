package virophage.network;

public class Listening {

    private boolean listening = false;

    public boolean isListening() {
        return listening;
    }

    public void startListening() {
        this.listening = true;
    }

    public void stopListening() {
        this.listening = false;
    }

}
