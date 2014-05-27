package virophage.util;

import java.awt.*;

public class GameConstants {

	public static final int DEAD_CELL_NUM = 120;
	public static final int N = 10; // max location coordinate
    public static final int PLAYER_DISTANCE = 5;
	public static final int MAX_ENERGY = 8;
	public final static int MAX_PLAYERS = 6;
	public final static int TOTAL_NUM_PLAYERS = 12;

    public static final Color[] PLAYER_COLORS = new Color[] {
    	new Color(255, 167, 163),
    	new Color(124, 226, 136),
    	new Color(178, 189, 255),
    	new Color(255, 190, 147),
    	new Color(218, 154, 255),
    	new Color(165, 255, 253)
        //, Color.YELLOW
    };

    public static final int VIRUS_GROW_TICKS = 100;
    public static final int UPGRADED_VIRUS_GROW_TICKS = 50;
    public static final int UPGRADED_CHANNEL_MOVE_TICKS = 10;
    public static final int CHANNEL_MOVE_TICKS = 20;

}
