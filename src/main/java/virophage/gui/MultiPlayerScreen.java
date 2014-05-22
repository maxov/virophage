package virophage.gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import virophage.Start;
import virophage.core.MachinePlayer;
import virophage.core.Player;

/**
 * @author Leon Ren
 * @since 2014-05-6
 */
public class MultiPlayerScreen extends JPanel implements ActionListener, ListSelectionListener{
	
	private JButton b1;
	private JButton b2;
	private JButton b3;
	private JCheckBox c1;
	private JList list;
	private JTextField playerName;
	private DefaultListModel listModel;
	private JButton buttonBack, buttonContinue;
    private GameClient w;
    private ArrayList<Player> humanPlayers;
    private boolean isNetworkedGame;
    
	/**
	 * Constructs a screen in which the user can add or remove players.
	 * @param g
	 */
	public MultiPlayerScreen(GameClient g) {
		w = g;
		isNetworkedGame = false;
		listModel = new DefaultListModel();
		humanPlayers = new ArrayList<Player>();
        this.setLayout(null);
        
		buttonBack = new JButton("Back");
		Font f = new Font("Verdana", Font.ITALIC|Font.BOLD, 16);
        buttonBack.setFont(f);
        buttonBack.addActionListener(this);    
        add(buttonBack);
        
        buttonContinue = new JButton("Start");
		buttonContinue.setFont(f);
		buttonContinue.addActionListener(this);    
        add(buttonContinue);
        		
        f = new Font("Arial", Font.PLAIN, 20);
		b2 = new JButton("Add Human Player");
		b2.addActionListener(this);
		b2.setFont(f);

		b3 = new JButton("Remove");
		b3.addActionListener(this);
		b3.setFont(f);

		add(b2);
		add(b3);
		
		
        c1 = new JCheckBox(" Networked Game");
        c1.setFont(f);
        c1.addActionListener(this);
        add (c1);
        
        playerName = new JTextField("Player Name ", 20);
        add(playerName);
       
        list = new JList(listModel); 
        list.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        list.setLayoutOrientation(JList.VERTICAL_WRAP);
        list.setVisibleRowCount(-1);
        add(list);
    }
	
	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        int x = this.getWidth();
        int y = this.getHeight();
        
        Font f = new Font("calibri", Font.PLAIN, 18);
        g.setFont(f);
        g.drawString("Player List:", x/12, y/18);
        list.setBounds(x/ 12, y / 15, x/3, y/2);
        
        b2.setBounds(x -x/3, y *2/5, 300, 30);
        b3.setBounds(x -x/3, y *3/5, 300, 30);
        buttonBack.setBounds(x/15, y - y/10, 100, 30);
        buttonContinue.setBounds(x- x/7, y - y/10, 150, 30);
        c1.setBounds(x - x/3, y / 12, 300, 30);
        
        playerName.setBounds(x/12, y *10/15, 300, 30);
   }
	
	
		private boolean alreadyInList(String name) {
	        return listModel.contains(name);
	    }
		
		@Override
		public void actionPerformed(ActionEvent e) {
			Object x = e.getSource();
			
			
			if(x == buttonBack){
				w.changePanel("menuScreen");
			} else if (x == buttonContinue) {
				// multiplayer game starts
				Start.log.info("Multiplayer Game will start now!");
				if (isNetworkedGame) {
					
				} else {
					w.changePanel("renderTree");
					w.gameStart(humanPlayers);
				}
			}else if (x == b2) {
				if (humanPlayers.size() >= w.TOTAL_NUM_PLAYERS) {
					return;
				}
				String name = playerName.getText();

	            //User didn't type in a unique name...
	            if (name.equals("") || alreadyInList(name)) {
	                playerName.requestFocusInWindow();
	                playerName.selectAll();
	                return;
	            }
	
	            int index = list.getSelectedIndex(); //get selected index
	            if (index == -1) { //no selection, so insert at beginning
	                index = 0;
	            } else {           //add after the selected item
	                index++;
	            }
	
	            listModel.insertElementAt(playerName.getText(), index);
	            //If we just wanted to add to the end, we'd do this:
	            //listModel.addElement(employeeName.getText());
	
	            //Reset the text field.
	            playerName.requestFocusInWindow();
	            playerName.setText("");
	
	            //Select the new item and make it visible.
	            list.setSelectedIndex(index);
	            list.ensureIndexIsVisible(index);
	            Player another = new Player(new Color(200 + index * 50, 250 - index * 50, 200), w.getTree().getTissue());
	            another.setName(name);
	            humanPlayers.add(another);
			} else if (x == b3) {
				int index = list.getSelectedIndex();
				boolean found = false;
				String name = (String)listModel.getElementAt(index);
				int i;
				for (i =0 ; i<humanPlayers.size(); i++) {
					if (humanPlayers.get(i).getName().equals(name)) {
						found = true;
						break;
					}
				}
				if (found == false) {
					return;
				}
				humanPlayers.remove(i);
	            listModel.remove(index);

	            int size = listModel.getSize();

	            if (size == 0) { //Nobody's left, disable firing.
	                b3.setEnabled(false);

	            } else { 
	            	
	            	//Select an index.
	                if (index == listModel.getSize()) {
	                    //removed item in last position
	                    index--;
	                }

	                list.setSelectedIndex(index);
	                list.ensureIndexIsVisible(index);
	            }
			}
			else if (x == c1){
				if (c1.isSelected()){
					isNetworkedGame = true;
					Start.log.info("Network Game selected");
				}
				else{
					isNetworkedGame = false;
					Start.log.info("Network Game deselected");
				}
				
			}
		}

		@Override
		public void valueChanged(ListSelectionEvent arg0) {
			// TODO Auto-generated method stub
			
		}


}