package virophage.game;

import virophage.SerializeTest;
import virophage.Start;
import virophage.core.*;
import virophage.gui.ClientLobbyScreen;
import virophage.gui.ConnectionDialog;
import virophage.network.PacketStream;
import virophage.network.packet.*;
import virophage.network.packet.Action;
import virophage.util.GameConstants;
import virophage.util.Location;

import javax.sql.rowset.serial.SerialException;
import javax.swing.*;
import java.io.*;
import java.net.Socket;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Max Ovsiankin
 * @since 2014-05-6
 */

public class ClientGame extends Game implements Runnable {

    private int port;
    private String host;
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private Player player;
    private boolean inLobbyMode = true;
    private boolean loud = true;
    private ClientLobbyScreen clientLobbyScreen;

    public PacketStream stream;

    public ClientGame(Tissue tissue, String host, int port) {
        super(tissue);
        this.host = host;
        this.port = port;
    }

    /**
     * Attempt to connect with the given socket information.
     *
     * @return whether the connection attempt was successful
     */
    public boolean connect() {
        try {
            socket = new Socket(host, port);
            socket.setKeepAlive(true);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private void writeName(String name) throws IOException {
        out.writeObject(new RequestPlayerName(name));
        out.flush();
    }

    private void writeAction(Action action) throws IOException {
        out.writeObject(action);
        out.flush();
    }

    /**
     * Begin listening on this socket.
     */
    @Override
    public void run() {
        try {
            boolean accepted = false;
            out = new ObjectOutputStream(new BufferedOutputStream(socket.getOutputStream()));
            out.flush();
            in = new ObjectInputStream(new BufferedInputStream(socket.getInputStream()));
            writeName(player.getName());
            while(true) {
                Object packet = null;
                try {
                    packet = in.readObject();
                } catch (Throwable e) {
                    e.printStackTrace();
                }
                if(accepted) {
                    if(inLobbyMode) {
                        if(packet instanceof LobbyPacket) {
                            Player[] players = ((LobbyPacket) packet).players;
                            getTissue().removeAllPlayers();
                            for(Player p: players) {
                                getTissue().addPlayer(p);
                            }
                            clientLobbyScreen.resetPlayers();
                        } else if(packet instanceof StartGamePacket) {
                            setTissue(((StartGamePacket) packet).getTissue());
                            Start.gameClient.changePanel("renderTree");
                            Start.gameClient.getGameScreen().gameStart(this);
                            inLobbyMode = false;
                        }
                    } else {
                        if(packet instanceof TissueUpdate) {
                            setTissue(((TissueUpdate) packet).getTissue());
                            SerializeTest.serialize(((TissueUpdate) packet).getTissue());
                            Start.log.info("recv" + ((TissueUpdate) packet).getTissue() +"occupied cells= " + getTissue().getOccupiedCells());
                        } else if(packet instanceof BroadcastPacket) {
                            if(packet instanceof ChatPacket) {

                            } else {

                            }
                        }
                    }
                } else {
                    if(packet instanceof PlayerError) {
                        if(packet instanceof TooManyPlayersError) {
                            JOptionPane.showMessageDialog(
                                    Start.gameClient,
                                    ((TooManyPlayersError) packet).getError(),
                                    "Disconnected",
                                    JOptionPane.WARNING_MESSAGE);
                            Start.gameClient.changePanel("menuScreen");
                            loud = false;
                            new Thread(new Runnable() {
                                @Override
                                public void run() {
                                    new ConnectionDialog(Start.gameClient);
                                }
                            }).start();
                        } else if(packet instanceof PlayerNameError) {
                            String name = JOptionPane.showInputDialog(
                                    Start.gameClient,
                                    ((PlayerError) packet).getError() + ", re-enter name: ",
                                    "Re-Enter Name",
                                    JOptionPane.INFORMATION_MESSAGE);
                            writeName(name);
                        }
                    } else if(packet instanceof AssignPlayer) {
                        player = ((AssignPlayer) packet).getPlayer();
                        accepted = true;
                        constructTissue();
                        Start.gameClient.changePanel("clientLobbyScreen");
                        clientLobbyScreen = Start.gameClient.getClientLobbyScreen();
                        clientLobbyScreen.setClientGame(this);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            if(loud) {
                JOptionPane.showMessageDialog(
                        Start.gameClient,
                        "Error: Disconnected",
                        "Disconnected",
                        JOptionPane.ERROR_MESSAGE);
            }
            Start.gameClient.changePanel("menuScreen");
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
