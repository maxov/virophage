package virophage.gui;

import virophage.core.Player;
import virophage.game.ClientGame;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class ConnectionDialog extends JDialog {

    private CardLayout layout;

    private JPanel content;
    private JButton buttonJoin = new JButton("Join");
    private JButton buttonCancel = new JButton("Cancel");
    private JTextField name = new JTextField("SuperPlayer", 15);
    private JTextField host = new JTextField("localhost", 10);
    private JTextField port = new JTextField("4444", 5);
    private JLabel errorLabel = new JLabel("No error",
            UIManager.getIcon("OptionPane.errorIcon"),
            SwingConstants.LEFT);
    private GameClient gameClient;

    public ConnectionDialog(GameClient gameClient) {
        this.gameClient = gameClient;
        setModal(true);
        setName("Join Game");
        //setResizable(false);
        setLocationRelativeTo(gameClient);

        content = new JPanel();
        layout = new CardLayout();
        content.setLayout(layout);
        setContentPane(content);

        JPanel main = new JPanel();
        main.setBorder(new EmptyBorder(15, 15, 15, 15));
        main.setLayout(new BoxLayout(main, BoxLayout.Y_AXIS));

        JPanel buttons = new JPanel();
        buttons.add(buttonJoin);
        buttons.add(buttonCancel);

        JPanel namePanel = new JPanel();
        namePanel.setLayout(new BorderLayout(10, 10));
        namePanel.add(new JLabel("Name:"), BorderLayout.WEST);
        name.setBorder(new EmptyBorder(7, 7, 7, 7));
        namePanel.add(name, BorderLayout.CENTER);

        JPanel hostPort = new JPanel();
        hostPort.add(new JLabel("Host:"));
        host.setBorder(new EmptyBorder(7, 7, 7, 7));
        hostPort.add(host);
        hostPort.add(new JLabel("Port:"));
        port.setBorder(new EmptyBorder(7, 7, 7, 7));
        hostPort.add(port);

        main.add(namePanel);
        main.add(hostPort);
        main.add(buttons);

        JPanel progress = new JPanel();
        progress.setLayout(new BoxLayout(progress, BoxLayout.Y_AXIS));
        progress.setBorder(new EmptyBorder(50, 50, 50, 50));
        JProgressBar progressBar = new JProgressBar();
        progressBar.setIndeterminate(true);
        JLabel connecting = new JLabel("Connecting...");
        connecting.setAlignmentX(CENTER_ALIGNMENT);
        progress.add(connecting);
        progress.add(progressBar);

        JPanel error = new JPanel();
        error.setLayout(new BoxLayout(error, BoxLayout.Y_AXIS));
        error.setBorder(new EmptyBorder(30, 30, 30, 30));
        errorLabel.setAlignmentX(CENTER_ALIGNMENT);
        error.add(errorLabel);

        JPanel errorButtons = new JPanel();
        JButton retry = new JButton("Retry");
        retry.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });
        JButton back = new JButton("Back");
        back.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                changePanel("main");
            }
        });
        errorButtons.add(retry);
        errorButtons.add(back);
        error.add(errorButtons);

        content.add(main, "main");
        content.add(progress, "progress");
        content.add(error, "error");

        getRootPane().setDefaultButton(buttonJoin);

        buttonJoin.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        content.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        pack();
        setVisible(true);
    }

    public void changePanel(String s) {
        layout.show(content, s);
        requestFocus();
    }

    private void onOK() {
        changePanel("progress");
        new Thread(new Runnable() {
            @Override
            public void run() {
                ClientGame clientGame = new ClientGame(null, host.getText(), Integer.parseInt(port.getText()));
                clientGame.setPlayer(new Player(name.getText()));
                boolean connect = clientGame.connect();
                if(connect) {
                    onCancel();
                    new Thread(clientGame).start();
                } else {
                    error("Cannot connect");
                }
            }
        }).start();
    }

    private void error(String text) {
        errorLabel.setText("Error! " + text);
        changePanel("error");
    }

    private void onCancel() {
        dispose();
   }

}
