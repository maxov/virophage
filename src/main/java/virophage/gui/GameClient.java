package virophage.gui;

import virophage.Start;
import virophage.render.HexagonNode;
import virophage.render.RenderNode;
import virophage.render.RenderTree;

import javax.swing.*;
import java.awt.*;

public class GameClient extends JFrame {

    public static final Dimension SIZE = new Dimension(1280, 720);

    public GameClient() {

        setTitle("virophage");
        setSize(SIZE);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        RenderTree tree = new RenderTree();

        int N = 10;
        
        for(int x = -N; x <= N; x++) {
            for(int y = -N; y <= N; y++) {
                for(int z = -N; z <= N; z++) {
                    if(x + y + z == 0)
                        tree.add(new HexagonNode(x, y));
                }
            }
        }

        tree.updateNodes();

        add(tree, BorderLayout.CENTER);

        Start.log.info("Setting frame visible");
        setVisible(true);
        
        //create tissue here?
    }

}
