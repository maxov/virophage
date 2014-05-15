package virophage.core;

import java.awt.Color;
import java.util.ArrayList;

/**
 * @author      Max Ovsiankin and Leon Ren
 * @version     1.0 (Alpha)
 * @since       2014-05-6
 */
public class Player {

    private Color color;
    public ArrayList<Channel> channels = new ArrayList<Channel>();
    public ArrayList<Virus> viruses = new ArrayList<Virus>();

    public Player(Color c) {
        color = c;
    }

    public Color getColor() {
        return color;
    }

    public void addVirus(Virus v) {
        viruses.add(v);
    }

    public void addChannel(Channel c) {
        channels.add(c);
    }
}
