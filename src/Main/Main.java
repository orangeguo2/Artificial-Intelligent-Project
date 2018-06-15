package Main;

import javax.swing.JFrame;

import gameControl.dashBoard;

public class Main {
	public static void main(String args[]){
		 dashBoard dashBoard = new dashBoard("Tunnel Checkers");
		 dashBoard.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		 dashBoard.setResizable(false);
		 dashBoard.setVisible(true);
		 dashBoard.setSize(602,652);
	}
	
}
