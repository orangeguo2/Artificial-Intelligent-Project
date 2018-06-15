package gameControl;

import java.util.List;

import Algorithm.MinimaxAlgorithm;
import GUI.ConfigurationModel;
import GUI.Player;
import GUI.gameBoard;



//support controller
	public class gameController {
		private Helper helper;
		private boolean gameover, lock=true;
		private Successor successor;
		private dashBoard callback;
		private gameBoard board;
		private Operation darkPlayer;

		public gameController() {
			helper = new Helper();
			helper.setCallback(this);
			initNewGame();
		}
		
		public void initNewGame() {
			helper.startHolders();
			darkPlayer = new Operation(Player.DARK);
			SetEnvir envir = new SetEnvir();
			envir.setAlgorithm(new MinimaxAlgorithm());
			envir.setPlayer(Player.DARK);
			successor = new Successor();
			envir.setSuccessorFunction(successor);
			darkPlayer.setSetEnvir(envir);
			if(board!=null)
				board.updateUI();
			gameover = false;
		}
		
	
		
		public void update() {
			if(gameover)
				return;
			else {
				lock = ! lock;
				if(!lock) {
					List<Movement> listDark = successor.getSuccessor(helper, Player.DARK);
					if(listDark == null|| listDark.size()==0) {
						System.err.println("here is error!");
						callback.gameFinished(false);
						gameover = true;
						board.updateUI();
						return;
					}
					
					darkPlayer.playTurn(helper);
					List<Movement> listLight = successor.getSuccessor(helper, Player.LIGHT);
					if(listLight==null || listLight.size()==0){
						System.err.println("error!");
						callback.gameFinished(true);
						gameover = true;
					}
				}
				board.updateUI();
				}
		}
			
			public void configurationChanged(ConfigurationModel newConfigurationModel){
			SetEnvir envir = new SetEnvir();
				envir.setAlgorithm(newConfigurationModel.getSelectedAlgorithm());
				envir.setPlayer(Player.DARK);
				envir.setSuccessorFunction(successor);
				darkPlayer.setSetEnvir(envir);
			}
			
			public Helper getHelper() {
				return helper;
			}
			
			public void setBoard(gameBoard board) {
				this.board=board;
				board.setHelper(helper);
			}
			
			public void setCallback(dashBoard dashBoard) {
				this.callback = dashBoard;
			}
			}