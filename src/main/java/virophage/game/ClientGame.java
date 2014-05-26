package virophage.game;

import virophage.Start;
import virophage.core.*;
import virophage.gui.ClientLobbyScreen;
import virophage.gui.ConnectionDialog;
import virophage.network.PacketStream;
import virophage.network.packet.*;
import virophage.util.GameConstants;
import virophage.util.Location;

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
                Object packet = in.readObject();
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
                            Start.gameClient.getGameScreen().gameStart(this);
                            Start.gameClient.changePanel("menuScreen");
                            inLobbyMode = false;
                        }
                    } else {

                    }
                } else {
                    if(packet instanceof PlayerError) {
                        if(packet instanceof TooManyPlayersError) {
                            JOptionPane.showMessageDialog(
                                    Start.gameClient,
                                    "Disconnected",
                                    "Too many players",
                                    JOptionPane.WARNING_MESSAGE);
                            Start.gameClient.changePanel("menuScreen");
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
            JOptionPane.showMessageDialog(
                    Start.gameClient,
                    "Error: Disconnected",
                    "Disconnected",
                    JOptionPane.ERROR_MESSAGE);
            Start.gameClient.changePanel("menuScreen");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

}
