package virophage.game;

import virophage.Start;
import virophage.core.*;
import virophage.gui.LobbyScreen;
import virophage.network.SocketBundle;
import virophage.network.packet.*;
import virophage.util.GameConstants;
import virophage.util.Location;

import java.awt.*;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class ServerGame extends Game implements Runnable {

    private int port;
    private final ArrayList<SocketBundle> socketBundles = new ArrayList<SocketBundle>();
    private boolean listening;
    private boolean inLobbyMode = true;

    private ServerSocket serverSocket;
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
        new Thread(this).start();
    }

    /**
     * Starts a new serverGame that listens to the client.
     */
    public void run() {
        startListening();

        try {
            serverSocket = new ServerSocket(port);

            while (isListening()) {
                Socket socket = serverSocket.accept();
                new Thread(new Handler(socket)).start();
            }

            serverSocket.close();
            serverSocket = null;

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Adds a new AI player.
     * @param name the name of the player
     * @return if succesfully added
     */
    public boolean addAI(String name) {
        if(getTissue().getPlayers().size() < GameConstants.MAX_PLAYERS) {
            getTissue().addPlayer(new AIPlayer(name));
            return true;
        } else
            return false;
    }

    /**
     * 
     * @return returns the player Color
     */
    public Color requestPlayerColor() {
        ArrayList<Color> colorList = new ArrayList<Color>(Arrays.asList(GameConstants.PLAYER_COLORS));
        playerFor: for(Player p: getTissue().getPlayers()) {
            Color pColor = p.getColor();
            Iterator<Color> colorIterator = colorList.iterator();
            while(colorIterator.hasNext()) {
                Color c = colorIterator.next();
                if(c.equals(pColor)) {
                    colorIterator.remove();
                    continue playerFor;
                }
            }
        }
        return colorList.get(0);
    }

    public boolean isListening() {
        return listening;
    }

    public void startListening() {
        this.listening = true;
    }

    public void stopListening() {
        this.listening = false;
        try {
            if(serverSocket != null)
                serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Iterator<SocketBundle> socketIterator = socketBundles.iterator();
        while(socketIterator.hasNext()) {
            SocketBundle socketBundle = socketIterator.next();
            getTissue().getPlayers().remove(socketBundle.getPlayer());
            try {
                socketBundle.getSocket().close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            socketIterator.remove();
        }
        lobbyScreen.resetPlayers();
    }

    public boolean isInLobbyMode() {
        return inLobbyMode;
    }

    private void writeToAll(Packet packet) {
        for(SocketBundle bundle: socketBundles) {
            ObjectOutputStream out = bundle.getOut();
            if(out != null) {
                try {
                    out.writeObject(packet);
                    out.flush();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void setInLobbyMode(boolean inLobbyMode) {
        this.inLobbyMode = inLobbyMode;
    }

    public void updateLobby(java.util.List<Player> playerList) {
        writeToAll(new LobbyPacket(playerList));
    }

    public void removePlayer(String name) {
        Iterator<Player> playerIterator = getTissue().getPlayers().iterator();
        while(playerIterator.hasNext()) {
            Player player = playerIterator.next();
            if(player.getName().equals(name)) {
                if (!(player instanceof AIPlayer)) {
                    Iterator<SocketBundle> socketBundleIterator = socketBundles.iterator();
                    while(socketBundleIterator.hasNext()) {
                        SocketBundle socketBundle = socketBundleIterator.next();
                        if(socketBundle.getPlayer().getName().equals(player.getName())) {
                            try {
                                socketBundle.getSocket().close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            socketBundleIterator.remove();
                            break;
                        }
                    }
                }
                playerIterator.remove();
                break;
            }
        }
    }

    public LobbyScreen getLobbyScreen() {
        return lobbyScreen;
    }

    public void setLobbyScreen(LobbyScreen lobbyScreen) {
        this.lobbyScreen = lobbyScreen;
    }

    public void beginGame() {
        java.util.List<Player> players = getTissue().getPlayers();
        Tissue t = getTissue();
        // initialize player locations
        for(int i = 0; i < players.size(); i++) {
            Player p = players.get(i);
            p.setTissue(t);
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
        writeToAll(new StartGamePacket(getTissue()));
        setInLobbyMode(false);
        setGameStarted(true);
    }

    private class Handler implements Runnable{

        private SocketBundle socketBundle;
        private ObjectInputStream in;
        private ObjectOutputStream out;

        public Handler(Socket socket) {
            this.socketBundle = new SocketBundle(socket, null, null, null);
            socketBundles.add(socketBundle);
        }

        @Override
        public void run() {
            try {
                Socket socket = socketBundle.getSocket();
                out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
                out.flush();
                in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
                socketBundle.setIn(in);
                socketBundle.setOut(out);

                // There are already too many players
                if(getTissue().getPlayers().size() >= GameConstants.MAX_PLAYERS) {
                    out.writeObject(new TooManyPlayersError("already " + GameConstants.MAX_PLAYERS + " players"));
                    out.flush();
                    return;
                }

                String name= null;

                waitForPlayerName: while (true) {
                    try {
                        RequestPlayerName requestPlayerName = (RequestPlayerName) in.readObject();
                        name = requestPlayerName.getName();
                        int len = requestPlayerName.getName().length();
                        // invalid length
                        if(len >= 4 && len <= 12) {
                            // name already taken
                            boolean taken = false;
                            for(Player p: getTissue().getPlayers()) {
                                String n = p.getName();
                                if(n.equals(name)) {
                                    out.writeObject(new PlayerNameError("that name is already taken"));
                                    out.flush();
                                    taken = true;
                                }
                            }
                            if(!taken) break waitForPlayerName;
                        } else {
                            out.writeObject(new PlayerNameError("name not between 4 and 12 chars"));
                            out.flush();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        return;
                    }
                }

                Color c = requestPlayerColor();
                Player player = new Player(name);
                player.setTissue(getTissue());
                player.setColor(c);
                getTissue().addPlayer(player);
                socketBundle.setPlayer(player);
                out.writeObject(new AssignPlayer(player));
                out.flush();
                updateLobby(getTissue().getPlayers());
                lobbyScreen.resetPlayers();

                while (socketBundles.contains(socketBundle) && isListening()) {
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (socketBundle != null) {
                        synchronized(socketBundles) {
                            socketBundles.remove(socketBundle);
                        }
                }
                try {
                    socketBundle.getSocket().close();
                } catch (IOException ignored) {
                }
                if(inLobbyMode) {
                    if(socketBundle.getPlayer() != null) {
                        Iterator<Player> playerIterator = getTissue().getPlayers().iterator();
                        while(playerIterator.hasNext()) {
                            Player player = playerIterator.next();
                            if(player.getName().equals(socketBundle.getPlayer().getName())) {
                                playerIterator.remove();
                            }
                        }
                        lobbyScreen.resetPlayers();
                    }
                }
            }
        }

    }



}
