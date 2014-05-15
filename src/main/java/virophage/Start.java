package virophage;

import virophage.gui.GameClient;

import java.util.logging.Logger;

/**
 * Main Method - Creates a GameClient
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class Start {

    public static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        log.info("Constructing frame");
        GameClient gameClient = new GameClient();
    }

}