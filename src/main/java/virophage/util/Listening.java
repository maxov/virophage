package virophage.util;

/**
*
* @author Max Ovsiankin
* @since 2014-05-16
*/
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
