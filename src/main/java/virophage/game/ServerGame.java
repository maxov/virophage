package virophage.game;

import virophage.core.*;
import virophage.gui.GameClient;
import virophage.gui.LobbyScreen;
import virophage.network.PacketBundle;
import virophage.network.PacketStream;
import virophage.network.PacketStreamListener;
import virophage.network.packet.AssignPlayer;
import virophage.network.packet.ErrorPacket;
import virophage.network.packet.RequestPlayer;
import virophage.util.GameConstants;
import virophage.util.Location;

import java.awt.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Random;

public class ServerGame extends Game {

    private int port;
    private ArrayList<PacketBundle> packetBundles = new ArrayList<PacketBundle>();
    private boolean accepting = false;
    private boolean listening;

    private LobbyScreen lobbyScreen;

    /**
     * Construct this game given a tissue.
     *
     * @param tissue the tissue
    */
    public ServerGame(Tissue tissue, int port) {
        super(tissue);
        this.port = port;
    }

    /**
     * Start this serverGame.
     */
    public void start() {
        startListening();

        try {
            ServerSocket serverSocket = new ServerSocket(port);

            while (isListening()) {
                Socket socket = serverSocket.accept();
                handleSocket(socket, new PacketStream(socket));
            }

            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void beginGame() {
        java.util.List<Player> players = getTissue().getPlayers();
        Tissue t = getTissue();
        // initialize player locations
        for(int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            p.setTissue(t);
            t.addPlayer(p);
            Location playerCenterLoc = null;
            switch(i) {
                case 0:
                    playerCenterLoc = new Location(0, -GameConstants.PLAYER_DISTANCE);
                    break;
                case 1:
                    playerCenterLoc = new Location(GameConstants.PLAYER_DISTANCE, -GameConstants.PLAYER_DISTANCE);
                    break;
                case 2:
                    playerCenterLoc = new Location(GameConstants.PLAYER_DISTANCE, 0);
                    break;
                case 3:
                    playerCenterLoc = new Location(0, GameConstants.PLAYER_DISTANCE);
                    break;
                case 4:
                    playerCenterLoc = new Location(-GameConstants.PLAYER_DISTANCE, GameConstants.PLAYER_DISTANCE);
                    break;
                case 5:
                    playerCenterLoc = new Location(-GameConstants.PLAYER_DISTANCE, 0);
                    break;
            }
            for(int x = -1; x <= 1; x++) {
                for(int y = -1; y <= 1; y++) {
                    for(int z = -1; z <= 1; z++) {
                        if(x + y + z == 0) {
                            Virus v = new Virus(p, 4);
                            p.addVirus(v);
                            v.schedule();
                            Cell c = t.getCell(new Location(playerCenterLoc.x + x, playerCenterLoc.y + y));
                            c.setOccupant(v);
                            v.setCell(c);
                        }
                    }
                }
            }
        }
//        Start.log.info("f of Cells: " + count);
        //set bonus cells
        Location loc = new Location(0, 0);
        if (t.getCell(loc) != null){
            ArrayList<Location> centerLocs = loc.getNeighbors();
            BonusCell b = null;
            for (Location l : centerLocs){
                b = new BonusCell(t, l);
                t.setCell(l, b);
                t.addBonusCell(b);
            }
            b = new BonusCell(t, loc);
            t.setCell(loc, b);
            t.addBonusCell(b);
        }


        //place dead cells in the renderTree
        int dead = 0;
        while (dead < GameConstants.DEAD_CELL_NUM) {
            Random rand = new Random();
            int xPos = rand.nextInt(GameConstants.N * 2 + 1) - GameConstants.N;
            int yPos = rand.nextInt(GameConstants.N * 2 + 1) - GameConstants.N;

            loc = new Location(xPos, yPos);
            if (t.getCell(loc) != null &&
                    t.getCell(loc).occupant == null && !(t.getCell(loc) instanceof BonusCell)) {
                t.setCell(loc, new DeadCell(t, loc));
                dead++;
            }
        }

        for(Player p: players) {
            if(p instanceof AIPlayer) {
                ((AIPlayer) p).schedule();
            }
        }
        setGameStarted(true);
    }

    public void handleSocket(final Socket socket, final PacketStream ps) {
        ps.addListener(new PacketStreamListener() {

            @Override
            public void onDisconnect(Socket socket) {
                Iterator<PacketBundle> packetBundleIterator = packetBundles.iterator();
                while(packetBundleIterator.hasNext()) {
                    PacketBundle bundle = packetBundleIterator.next();
                    if(bundle.getSocket().equals(socket)) {
                        packetBundleIterator.remove();
                        break;
                    }
                }

            }
            @Override
            public void onEvent(Object event) {
                if(event instanceof RequestPlayer ) {
                    if(accepting && !isGameStarted() &&
                            getTissue().getPlayers().size() < GameConstants.MAX_PLAYERS) {
                        String name = ((RequestPlayer) event).getName();
                        for(Player p: getTissue().getPlayers()) {
                            if(p.getName().equals(name)) {
                                ps.write(new ErrorPacket("that name is already taken"));
                                return;
                            }
                        }
                        Player p = new Player(name);
                        RemotePlayer remotePlayer = new RemotePlayer(name);
                        remotePlayer.setTissue(getTissue());
                        remotePlayer.setColor(requestPlayerColor());
                        packetBundles.add(new PacketBundle(remotePlayer, ps, socket));
                        ps.write(new AssignPlayer(p));
                    } else {
                        ps.write(new ErrorPacket("cannot join at this time"));
                    }
                }
            }

        });
    }

    public boolean addAI(String name) {
        if(getTissue().getPlayers().size() < GameConstants.MAX_PLAYERS) {
            getTissue().addPlayer(new AIPlayer(name));
            return true;
        } else {
            return false;
        }
    }

    private Color requestPlayerColor() {
        ArrayList<Color> colorList = new ArrayList<Color>(Arrays.asList(GameConstants.PLAYER_COLORS));
        playerFor: for(Player p: getTissue().getPlayers()) {
            Color pColor = p.getColor();
            Iterator<Color> colorIterator = colorList.iterator();
            while(colorIterator.hasNext()) {
                Color c = colorIterator.next().darker();
                if(c.equals(pColor)) {
                    colorIterator.remove();
                    continue playerFor;
                }
            }
        }
        return colorList.get(0);
    }

    public boolean isAccepting() {
        return accepting;
    }

    public void setAccepting(boolean accepting) {
        this.accepting = accepting;
    }

    public boolean isListening() {
        return listening;
    }

    public void startListening() {
        this.listening = true;
    }

    public void stopListening() {
        this.listening = false;
        packetBundles = new ArrayList<PacketBundle>();
    }

    public LobbyScreen getLobbyScreen() {
        return lobbyScreen;
    }

    public void setLobbyScreen(LobbyScreen lobbyScreen) {
        this.lobbyScreen = lobbyScreen;
    }
}
