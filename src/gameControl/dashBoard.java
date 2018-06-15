package gameControl;

import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import Algorithm.MinimaxAlgorithm;
import GUI.ConfigurationModel;
import GUI.ConfigurationPanel;
import GUI.gameBoard;
import GUI.Player;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class dashBoard extends JFrame {

	//All data Definition here
	private static final long serialVersionUID = 1L;
	private gameController gameController;
	private JFrame configurationFrame;
	
	
	// constructor
	public dashBoard(String str) {
		//create a JFrame window titled str
		super(str);
		//when open the window, a new game is started already
		initChecker();
		//init the GUI
		init();
	}
	
	//Start a new Game
	void initChecker() {
		gameController = new gameController();
		try {
			gameBoard board = new gameBoard();
			gameController.setBoard(board);
			gameController.setCallback(this);
			this.add(board);
		} catch (IOException e) {
			System.err.println(e.getMessage());
			System.exit(-1);
		}
	}
	
	public void gameFinished(boolean amI) {
		String msg = amI ? "AI win!" : "You Win!";
		JOptionPane.showMessageDialog(this, msg , "Game Finished!", JOptionPane.INFORMATION_MESSAGE);
	}

	
	// the state of each square
	public enum SquareHolder {
		// use number to represent the holder
		NONE(null), // = 0
		LIGHT(Player.LIGHT), // = 1
		DARK(Player.DARK), // = 2
		KING_LIGHT(Player.LIGHT), // = 3
		KING_DARK(Player.DARK); // = 4

		public final Player holder;

		// constructor of each square
		private SquareHolder(Player holder) {
			this.holder = holder;
		}

		// whether King
		public boolean isKing() {
			return this == KING_DARK || this == KING_LIGHT;
		}

		// Nomal or King, all piece to King
		public SquareHolder HolderToKing() {
			if (this == LIGHT || this == DARK) {
				return values()[this.ordinal() + 2];
			}
			return this;
		}

		// Nomal or King, all piece to Normal
		public SquareHolder HolderToNormal() {
			if (this == KING_LIGHT || this == KING_DARK) {
				return values()[this.ordinal() - 2];
			}
			return this;
		}
	}// end of each square enum




	
	private class ConfigurationItemListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent action) {
			ConfigurationPanel panel = new ConfigurationPanel(dashBoard.this);
			panel.setConfigurationModel(new ConfigurationModel());
			configurationFrame = new JFrame("Select Algorithm");
			configurationFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			configurationFrame.setContentPane(panel);
			configurationFrame.pack();
			configurationFrame.setResizable(false);
			configurationFrame.setVisible(true);
		}
	}
	
	public void configurationUpdated(ConfigurationModel newConfigurationModel) {
		gameController.configurationChanged(newConfigurationModel);
		if(configurationFrame != null)
			configurationFrame.dispose();
	}
	
	private void init() {
		JMenu jmenu = new JMenu("Select Algorithm Here");
		
		JMenuItem jMenuItem0 = new JMenuItem("New Game with the algorithm selected");
		jMenuItem0.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent event) {
				gameController.initNewGame();
			}			
		});
		jmenu.add(jMenuItem0);
		
		JMenuItem jMenuItem1 = new JMenuItem("Select Algorithm Here");
		jMenuItem1.addActionListener(new ConfigurationItemListener());
		jmenu.add(jMenuItem1);

		
		
		JMenuBar jmenubar = new JMenuBar();
		jmenubar.add(jmenu);
		
		this.setJMenuBar(jmenubar);
	}
	

}
