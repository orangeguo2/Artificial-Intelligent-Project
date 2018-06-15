package gameControl;

import GUI.Player;
import gameControl.dashBoard.SquareHolder;

public class EnhancedWeightenedMenCountEvaluation implements CalculateFunction{
	
	/*friendly*/ final static int MEN_WEIGHT = 8;
	/*friendly*/ final static int KING_WEIGHT = 12;
	/*friendly*/ final static int WMEN_WEIGHT = 7;
	/*friendly*/ final static int WKING_WEIGHT = 11;

	@Override
	public double evaluate(Helper helper, Player player) {
		int count = 0;
		SquareHolder[][] state = helper.holders;
		for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				SquareHolder currentSquare = state[i][j];
				if(currentSquare == null){
					String msg = String.format("Cell [%d,%d] is null", i,j);
					throw new IllegalStateException(msg);
				}
				switch (currentSquare) {
				case DARK:
					count += MEN_WEIGHT;
					break;
				case KING_DARK:
					count += KING_WEIGHT;
					break;
				case LIGHT:
					count -= WMEN_WEIGHT;
					break;
				case KING_LIGHT:
					count -= WKING_WEIGHT;
					break;
				default:
					break;
				}
			}
		}
		return count * (player== Player.DARK ? 1:-1);
	}

}
