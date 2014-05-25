package virophage.gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;

public class ConnectionDialog extends JDialog {

    private JPanel content;
    private JButton buttonJoin = new JButton("Join");
    private JButton buttonCancel = new JButton("Cancel");
    private JTextField name = new JTextField("SuperPlayer", 15);
    private JTextField host = new JTextField("192.168.1.1", 10);
    private JTextField port = new JTextField("4444", 5);
    private GameClient gameClient;

    public ConnectionDialog(GameClient gameClient) {
        this.gameClient = gameClient;
        setModal(true);

        content = new JPanel();
        content.setBorder(new EmptyBorder(15, 15, 15, 15));
        content.setLayout(new BoxLayout(content, BoxLayout.Y_AXIS));
        setContentPane(content);

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

        content.add(namePanel);
        content.add(hostPort);
        content.add(buttons);

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

   private void onOK() {

        dispose();
   }

   private void onCancel() {
        dispose();
   }

}
