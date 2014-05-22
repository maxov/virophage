package virophage.util;

/**
 * A class that represents something that can listen and stop listening(e.g. network things)/
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
