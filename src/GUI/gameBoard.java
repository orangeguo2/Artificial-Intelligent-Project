package GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import gameControl.Movement;
import gameControl.Successor;
import gameControl.dashBoard.SquareHolder;
import gameControl.Helper;


@SuppressWarnings("serial")
public class gameBoard extends JPanel {
	
	private Image board = null;
	private Image light = null;
	private Image dark = null;
	private Image kingLight = null;
	private Image kingDark = null;
	
	private Helper helper;

	public Image getBoard() {
		return board;
	}

	public void setBoard(Image board) {
		this.board = board;
	}

	public gameBoard() throws IOException {
		File f = new File("./img/board.jpg");
		board = ImageIO.read(f);
		
		f = new File("./img/light.gif");
		dark = ImageIO.read(f);
		
		f = new File("./img/dark.gif");
		light = ImageIO.read(f);
		
		f = new File("./img/kingLight.gif");
		kingDark = ImageIO.read(f);
		
		f = new File("./img/kingDark.gif");
		kingLight = ImageIO.read(f);
		
		addMouseListener(new BoardMouseListener());
		addMouseMotionListener(new BoardMouseMotionListener());
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.drawImage(board, 0, 0, getHeight(), getWidth(), null);
		
		SquareHolder [][] matrix = helper.holders;
		
		for (int i = matrix.length-1; i >=0; i--) {
			for (int j = 0; j < matrix[i].length; j++) {
				Image img = null;
				switch(matrix[i][j]){
					case DARK:
						img = dark;
						break;
					case LIGHT:
						img = light;
						break;
					case KING_DARK:
						img = kingDark;
						break;
					case KING_LIGHT:
						img = kingLight;
						break;
				}
				if(img != null){
					double p = getHeight() / 8;
					g.drawImage(img, (int)(p*j+p*0.15), (int)(p*(7-i)+p*0.15), (int)(p*0.7), (int)(p*0.7), null);
				}
			}
		}
	}
	
	private class BoardMouseListener implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent event) {
			// TODO Auto-generated method stub
		}

		@Override
		public void mouseEntered(MouseEvent event) {
			// TODO Auto-generated method stub			
		}

		@Override
		public void mouseExited(MouseEvent event) {
			// TODO Auto-generated method stub			
		}
		
		private int _x,_y;
		private LinkedList<Movement> possibleMoves;

		@Override
		public void mousePressed(MouseEvent event) {
			double width = gameBoard.this.getWidth();
			double height = gameBoard.this.getHeight();
			
			int x = (int)(event.getX() / (width / 8)) + 1;
			int y = (int)((height - event.getY()) / (height/ 8)) + 1;
						
			_x=x;
			_y=y;
			
			Successor successors = new Successor();
			LinkedList<Movement> moves = new LinkedList<Movement>();
			successors.handleStone(moves, helper, _y-1, _x-1);
			possibleMoves = moves;
		}

		@Override
		public void mouseReleased(MouseEvent event) {
			if(possibleMoves.size()==0)
				return;
			double width = gameBoard.this.getWidth();
			double height = gameBoard.this.getHeight();
			
			int x = (int)(event.getX() / (width / 8)) + 1;
			int y = (int)((height - event.getY()) / (height/ 8)) + 1;
						Movement currentMove = null;
			for (Movement move : possibleMoves) {
				if(move.desX == y-1 && move.desY == x-1){
					currentMove = move;
					break;
				}
			}

			if(currentMove==null)
				return;
			helper.doMovement(currentMove);
		}
		
	}
	
	private class BoardMouseMotionListener implements MouseMotionListener{

		@Override
		public void mouseDragged(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void mouseMoved(MouseEvent arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}

	public void setHelper(Helper helper) {
		this.helper = helper;
	}
}
