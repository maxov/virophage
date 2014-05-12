package virophage;

import virophage.gui.GameClient;

import java.util.logging.Logger;

public class Start {

    public static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        log.info("Constructing frame");
        GameClient gameClient = new GameClient();
    }

}
