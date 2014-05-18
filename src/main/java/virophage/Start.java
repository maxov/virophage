package virophage;

//import org.lwjgl.LWJGLException;
//import org.lwjgl.opengl.Display;
//import org.lwjgl.opengl.DisplayMode;
//import static org.lwjgl.opengl.GL11.*;
//
//import org.lwjgl.opengl.GL15;
//import org.lwjgl.opengl.GL40;
//import org.lwjgl.util.glu.GLU;
//import org.newdawn.slick.opengl.Texture;
//import org.newdawn.slick.opengl.TextureLoader;
import virophage.gui.GameClient;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Main Class - Starts the game, and also hosts the logger.
 *
 * @author      Max Ovsiankin, Leon Ren
 * @since       2014-05-6
 */
public class Start {

    static {
        String pathsep = System.getProperty("file.separator");
        String dir = System.getProperty("user.dir");
        System.setProperty("org.lwjgl.librarypath", dir + pathsep + "lib" + pathsep + "natives");
    }

    public static Logger log = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

    public static void main(String[] args) {

        log.info("Constructing frame");
        GameClient gameClient = new GameClient();

        /*try {
            Display.setDisplayMode(new DisplayMode(1280, 720));
            Display.setTitle("virophage");
            Display.create();
        } catch (LWJGLException e) {
            e.printStackTrace();
        }

        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        GLU.gluPerspective((float) 90, 1280f / 720f, 0.01f, 10000f);

        //glOrtho(0, 1280, 0, 720, 100, -100);
        glMatrixMode(GL_MODELVIEW);

        glTranslatef(0, 0, -50f);

        Random rand = new Random();

        while(!Display.isCloseRequested()) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glColor3f(0.5f, 0.5f, 1.0f);

            float vertices[] = {
                    0.0f,  0.5f, // Vertex 1 (X, Y)
                    0.5f, -0.5f, // Vertex 2 (X, Y)
                    -0.5f, -0.5f  // Vertex 3 (X, Y)
            };

            glBegin(GL_QUADS);
                glVertex3f(-3, -3, 0);
                glVertex3f(-3, 3, 0);
                glVertex3f(3, 3, 0);
                glVertex3f(3, -3, 0);
            glEnd();
            Display.update();
        }

        Display.destroy();*/
    }

}