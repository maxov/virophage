package virophage;

import virophage.gui.GameClient;

import java.util.logging.Logger;

/**
 * Main Class - Creates a GameClient
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class Start {

    public static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        log.info("Constructing frame");
        System.setProperty("sun.java2d.opengl","True");
        GameClient gameClient = new GameClient();
    }

}