package virophage.core;

import virophage.gui.GameScreen;
import virophage.util.Location;

import java.awt.Color;
import java.util.TimerTask;

/**
 * Represents an AI playing as a player.
 *
 * @author Leon Ren,  Max Ovsiankin
 * @since 2014-05-19
 */
public class AIPlayer extends Player {

    /**
     * Contructs a new MachinePlayer with a given color in a given tissue.
     *
     * @param color  - the color of this MachinePlayer
     * @param tissue - the gameboard that will contain this MachinePlayer
     */
    public AIPlayer(Color color, Tissue tissue) {
        super(color, tissue);
    }
    
    public void schedule() {
    	GameScreen.timer.schedule(task, 100, 300);
    }

    private TimerTask task = new TimerTask() {
        @Override
        public void run() {
            for (Virus v : getViruses()) {
                try {
                    Tissue tissue = getTissue();
                    if (v.getCell() == null) {
                        continue;
                    }
                    Location from = v.getCell().location;
                    for (Location to : from.getNeighbors()) {
                        Cell cellTo = tissue.getCell(to);
                        if (cellTo != null && !(cellTo instanceof DeadCell) &&
                                !hasChannelBetween(from, to) && !hasChannelBetween(to, from) &&
                                v.getEnergy() >= 4
                                && Math.random() < 0.1) {
                        /*if (cellTo != null && !(cellTo instanceof DeadCell) &&
                                !hasChannelBetween(from, to) && !hasChannelBetween(to, from)) {*/
                            Channel c = new Channel(tissue, from, to, AIPlayer.this);
                            addChannel(c);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        }
    };

}
