package virophage.gui;

import virophage.Start;
import virophage.render.RenderTree;

import javax.swing.*;
import java.awt.*;

public class VFrame extends JFrame {

    public static final Dimension SIZE = new Dimension(1280, 720);

    public VFrame() {

        setTitle("virophage");
        setSize(SIZE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        add(new RenderTree(), BorderLayout.CENTER);

        Start.log.info("Setting frame visible");
        setVisible(true);
    }

}