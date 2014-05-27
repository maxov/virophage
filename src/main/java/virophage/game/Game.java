package virophage.game;

import java.util.*;

import virophage.core.*;
import virophage.gui.GameScreen;
import virophage.util.GameConstants;
import virophage.util.Location;

/**
 * Represents an active game that is going on.
 *
 * @author Max Ovsiankin, Leon Ren
 * @since 2014-05-16
 */
public class Game {

    protected Tissue tissue;
    private boolean gameStarted = false;
    private boolean gameEnded = false;
    private Player activePlayer;
    private int ticks;

    /**
     * Construct this game given a tissue.
     *
     * @param tissue the tissue
     */
    public Game(Tissue tissue) {
        this.tissue = tissue;
        this.activePlayer = null;

    }

    public Game() {
        this(null);
    }

    public Tissue getTissue() {
        return tissue;
    }
    public Tissue setTissue(Tissue tissue) {
        return tissue;
    }

    /**
     * Constructs the gameBoard.
     */
    public void constructTissue() {
        Cell[][] cells = new Cell[2 * GameConstants.N + 1][2 * GameConstants.N + 1];
        Tissue t = new Tissue(cells, this);
        for (int i = -GameConstants.N; i <= GameConstants.N; i++) {
            for (int j = -GameConstants.N; j <= GameConstants.N; j++) {
                for (int k = -GameConstants.N; k <= GameConstants.N; k++) {
                    if (i + j + k == 0) {
                        Location loc = new Location(i, j);
                        t.setCell(loc, new Cell(t, loc));
                    }
                }
            }
        }
        this.tissue = t;
    }

    private boolean canInfect(Channel c) {
        Cell from = tissue.getCell(c.from);
        Virus virus = from.getOccupant();
        return virus != null && virus.getEnergy() > 1 &&
                (isUpgraded(virus.getPlayer()) ?
                        ((ticks - c.getCreationTime()) % GameConstants.UPGRADED_CHANNEL_MOVE_TICKS == 0) :
                        ((ticks - c.getCreationTime()) % GameConstants.CHANNEL_MOVE_TICKS == 0)) &&
                (getOutgoing(from, c).size() + 1) < virus.getEnergy();
    }

    private boolean canGrow(Cell c) {
        Virus virus = c.getOccupant();
        return virus != null &&
                (isUpgraded(virus.getPlayer()) ?
                        ((ticks - virus.getCreationTime()) % GameConstants.UPGRADED_VIRUS_GROW_TICKS == 0) :
                        ((ticks - virus.getCreationTime()) % GameConstants.VIRUS_GROW_TICKS == 0));
    }

    private boolean isUpgraded(Player player) {
        for(BonusCell cell: getTissue().getBonuses()) {
            if(cell.getPlayer() == null || !cell.getPlayer().equals(player)) return false;
        }
        return true;
    }

    private ArrayList<Channel> getOutgoing(Cell c, Channel cha) {
        ArrayList<Channel> channels = new ArrayList<Channel>();
        if(c.hasOccupant()) {
            Player player = c.getOccupant().getPlayer();
            for(Channel channel: player.getChannels()) {
                if(channel != cha && channel.from.equals(c.location) && canInfect(channel)) channels.add(channel);
            }
        }
        return channels;
    }

    private ArrayList<Channel> getOutgoing(Cell c) {
        ArrayList<Channel> channels = new ArrayList<Channel>();
        if(c.hasOccupant()) {
            Player player = c.getOccupant().getPlayer();
            for(Channel channel: player.getChannels()) {
                if(channel.from.equals(c.location) && canInfect(channel)) channels.add(channel);
            }
        }
        return channels;
    }

    private ArrayList<Channel> getInfectors(Cell c) {
        ArrayList<Channel> channels = new ArrayList<Channel>();
        for(Channel channel: tissue.getChannels())
            if(channel.to.equals(c.location) &&
                    canInfect(channel) &&
                    !(c.hasOccupant() && c.getOccupant().getPlayer().equals(channel.player)))
                channels.add(channel);
        return channels;
    }

    private ArrayList<Channel> getSupporters(Cell c) {
        ArrayList<Channel> channels = new ArrayList<Channel>();
        if(c.hasOccupant()) {
            Player player = c.getOccupant().getPlayer();
            for(Channel channel: player.getChannels()) if(channel.to.equals(c.location) &&
                    canInfect(channel))
                channels.add(channel);

        }
        return channels;
    }

    private void grow(Cell c) {
        Virus virus = c.getOccupant();
        int energy = virus.getEnergy();
        if(energy < GameConstants.MAX_ENERGY) {
            virus.setEnergy(energy + 1);
        }
    }

    private Map<Player, List<Channel>> groupChannels(ArrayList<Channel> channels) {
        Map<Player, List<Channel>> map = new HashMap<Player, List<Channel>>();
        for(Channel channel: channels) {
            Player player = channel.player;
            if (!map.containsKey(player)) {
                map.put(player, new ArrayList<Channel>());
            }
            map.get(player).add(channel);
        }
        return map;
    }

    private Map<Player, Integer> countChannels(Map<Player, List<Channel>> channels) {
        Map<Player, Integer> map = new HashMap<Player, Integer>();
        for(Map.Entry<Player, List<Channel>> entry: channels.entrySet())
            map.put(entry.getKey(), entry.getValue().size());
        return map;
    }

    private Map.Entry<Player, Integer> highestEntry(Map<Player, Integer> channels) {
        Map.Entry<Player, Integer> highest = null;
        for(Map.Entry<Player, Integer> entry: channels.entrySet())
            if(highest == null || entry.getValue() > highest.getValue())
                highest = entry;
        return highest;
    }

    private Map<Player, Integer> otherEntries(Map<Player, Integer> channels, Player player) {
        Map<Player, Integer> newMap = new HashMap<Player, Integer>(channels);
        newMap.remove(player);
        return newMap;
    }

    private int sumEntries(Map<Player, Integer> channels) {
        int sum = 0;
        for(Map.Entry<Player, Integer> entry: channels.entrySet()) {
            sum += entry.getValue();
        }
        return sum;
    }

    public void tick(int tick) {
        this.ticks = tick;
        Tissue tissue = getTissue();

        // grow mechanics
        for(Cell cell: tissue.flatCells()) {
            if(canGrow(cell)) grow(cell);
        }

        // channel transfer mechanics
        for(Cell cell: tissue.flatCells()) {
            if(cell.hasOccupant()) {
                ArrayList<Channel> supporters = getSupporters(cell);
                int supportersNum = supporters.size();
                ArrayList<Channel> infectors = getInfectors(cell);
                Map<Player, Integer> count = countChannels(groupChannels(infectors));
                int infectNum = sumEntries(count);
                int delta = supportersNum - infectNum;
                if(delta > 0) {
                    Virus occupant = cell.getOccupant();
                    occupant.setEnergy(occupant.getEnergy() + delta);
                    if(occupant.getEnergy() > GameConstants.MAX_ENERGY)
                        occupant.setEnergy(GameConstants.MAX_ENERGY);
                } else {
                    Map.Entry<Player, Integer> highest = highestEntry(count);
                    if(highest != null) {
                        int highestVal = highest.getValue();
                        int rest = sumEntries(otherEntries(count, highest.getKey()));
                        int delta2 = rest - highestVal;
                        if(delta2 > 0) {
                            cell.setOccupant(new Virus(highest.getKey(), delta2));
                            Virus occupant = cell.getOccupant();
                            if(occupant.getEnergy() > GameConstants.MAX_ENERGY)
                                occupant.setEnergy(GameConstants.MAX_ENERGY);
                        }
                    }
                }
                ArrayList<Channel> channels = new ArrayList<Channel>();
                channels.addAll(supporters);
                channels.addAll(infectors);
                for(Channel channel: channels) {
                    Virus occupant = tissue.getCell(channel.from).getOccupant();
                    occupant.setEnergy(occupant.getEnergy() - 1);
                }
            } else {
                ArrayList<Channel> channels = getInfectors(cell);
                Map<Player, Integer> count = countChannels(groupChannels(channels));
                Map.Entry<Player, Integer> highest = highestEntry(count);
                if(highest != null) {
                    int highestVal = highest.getValue();
                    int rest = sumEntries(otherEntries(count, highest.getKey()));
                    int delta = rest - highestVal;
                    if(delta > 0) {
                        cell.setOccupant(new Virus(highest.getKey(), delta));
                        if(cell.getOccupant().getEnergy() > GameConstants.MAX_ENERGY)
                            cell.getOccupant().setEnergy(GameConstants.MAX_ENERGY);
                    }
                }
                for(Channel channel: channels) {
                    Virus occupant = tissue.getCell(channel.from).getOccupant();
                    occupant.setEnergy(occupant.getEnergy() - 1);
                }
            }
        }

    }


    public boolean isGameStarted() {
    	return gameStarted;
    }

    public void setGameStarted(boolean g) {
    	gameStarted = g;
    }


    public void checkGame() {
    	List<Player> players = tissue.getPlayers();

        if (isGameStarted()) {
	        for (Player p: players){
                if (p.getViruses().size() == 0){
                    gameEnded = true;
                }
	        }
        }
    }

    public Player getActivePlayer() {
        return activePlayer;
    }

    public void setActivePlayer(Player activePlayer) {
        this.activePlayer = activePlayer;
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

}
