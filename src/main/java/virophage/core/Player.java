package virophage.core;

import java.awt.Color;
import java.util.ArrayList;

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
}
