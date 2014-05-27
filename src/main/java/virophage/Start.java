package virophage;

import virophage.gui.ChatList;
import virophage.gui.GameClient;
import java.util.logging.Logger;

/**
 * Main Class - Starts the game, and also hosts the logger.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-21
 */
public class Start {

    static {
        String pathsep = System.getProperty("file.separator");
        String dir = System.getProperty("user.dir");
        System.setProperty("org.lwjgl.librarypath", dir + pathsep + "lib" + pathsep + "natives");
        //System.setProperty("sun.java2d.opengl","True");
    }

    public static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
    public static GameClient gameClient;
    public static ChatList chatList = new ChatList();

    /**
     * Starts the game.
     *
     * @param args String arguments passed on the command line
     */
    public static void main(String[] args) {

        log.info("Constructing frame");
        gameClient = new GameClient();
    }

}