package gameControl;

import gameControl.dashBoard.SquareHolder;

public class Movement {
	public double value = Double.NEGATIVE_INFINITY;
	public int srcX, srcY, desX, desY;
	public boolean MandaryMove = false, convert;
	public SquareHolder eat;
	public Movement next, nextMandaryMove;

}