package virophage;

import virophage.gui.VFrame;

import java.util.logging.Logger;

public class Start {

    public static final Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {
        log.info("Constructing frame");
        GameLoop gameLoop = new GameLoop();
        gameLoop.start();
        VFrame frame = new VFrame();
    }

}
