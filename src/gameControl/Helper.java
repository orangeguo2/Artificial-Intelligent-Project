package gameControl;

import gameControl.dashBoard.SquareHolder;

public class Helper {
	public SquareHolder holders[][];
	private gameController callBack;

	/*
	 * Class GameBoard The GameBoard is represented by a 2D array with dimensions
	 * 8x8. The top-left corner is assigned (0,0), and the bottom right corner is
	 * assigned (7,7). Player one's pieces start at the top of the board (rows 0, 1,
	 * 2) and the forward direction of this player is indicated by successively
	 * increasing row numbers. In contrast, player two's pieces start at the bottom
	 * (rows 5, 6, 7) and the forward direction of this player is indicated by
	 * successively decreasing row numbers.
	 * 
	 */

	public void startHolders() {
		SquareHolder startColor[][] = {
				{ SquareHolder.LIGHT, SquareHolder.NONE, SquareHolder.LIGHT, SquareHolder.NONE, SquareHolder.LIGHT,
						SquareHolder.NONE, SquareHolder.LIGHT, SquareHolder.NONE },
				{ SquareHolder.NONE, SquareHolder.LIGHT, SquareHolder.NONE, SquareHolder.LIGHT, SquareHolder.NONE,
						SquareHolder.LIGHT, SquareHolder.NONE, SquareHolder.LIGHT },
				{ SquareHolder.LIGHT, SquareHolder.NONE, SquareHolder.LIGHT, SquareHolder.NONE, SquareHolder.LIGHT,
						SquareHolder.NONE, SquareHolder.LIGHT, SquareHolder.NONE },
				{ SquareHolder.NONE, SquareHolder.NONE, SquareHolder.NONE, SquareHolder.NONE, SquareHolder.NONE,
						SquareHolder.NONE, SquareHolder.NONE, SquareHolder.NONE },
				{ SquareHolder.NONE, SquareHolder.NONE, SquareHolder.NONE, SquareHolder.NONE, SquareHolder.NONE,
						SquareHolder.NONE, SquareHolder.NONE, SquareHolder.NONE },
				{ SquareHolder.NONE, SquareHolder.DARK, SquareHolder.NONE, SquareHolder.DARK, SquareHolder.NONE,
						SquareHolder.DARK, SquareHolder.NONE, SquareHolder.DARK },
				{ SquareHolder.DARK, SquareHolder.NONE, SquareHolder.DARK, SquareHolder.NONE, SquareHolder.DARK,
						SquareHolder.NONE, SquareHolder.DARK, SquareHolder.NONE },
				{ SquareHolder.NONE, SquareHolder.DARK, SquareHolder.NONE, SquareHolder.DARK, SquareHolder.NONE,
						SquareHolder.DARK, SquareHolder.NONE, SquareHolder.DARK }, };
		holders = startColor;
	}

	public void clearHolder() {
		SquareHolder[][] empty = new SquareHolder[8][8];
		for (int i = 0; i < empty.length; i++)
			for (int j = 0; j < empty[i].length; j++) {
				empty[i][j] = SquareHolder.NONE;
			}
		holders = empty;
	}

	public void tryMovement(Movement move) {
		if (move.MandaryMove) {
			move.eat = holders[(move.desX + move.srcX) / 2][(move.srcY + move.desY) / 2];
			holders[(move.desX + move.srcX) / 2][(move.srcY + move.desY) / 2] = SquareHolder.NONE;
		}
		holders[move.desX][move.desY] = holders[move.srcX][move.srcY];
		holders[move.srcX][move.srcY] = SquareHolder.NONE;
		// King
		if (move.desX > move.srcX) {
			if (move.desX == 7 && (holders[move.desX][move.desY] == SquareHolder.DARK
					|| holders[move.desX][move.desY] == SquareHolder.LIGHT)) {
				holders[move.desX][move.desY] = holders[move.desX][move.desY].HolderToKing();
				move.convert = true;

			}
		}

		if (move.desX < move.srcX) {
			if (move.desX == 0 && (holders[move.desX][move.desY] == SquareHolder.DARK
					|| holders[move.desX][move.desY] == SquareHolder.LIGHT)) {
				holders[move.desX][move.desY] = holders[move.desX][move.desY].HolderToKing();
				move.convert = true;

			}
		}

		if (move.nextMandaryMove != null)
			tryMovement(move.nextMandaryMove);

	}

	public void doMovement(Movement move) {
		holders[move.desX][move.desY] = holders[move.srcX][move.srcY];
		holders[move.srcX][move.srcY] = SquareHolder.NONE;
		// eat
		if (Math.abs(move.desX - move.srcX) > 1) {
			move.eat = holders[(move.desX + move.srcX) / 2][(move.desY + move.srcY) / 2];
			holders[(move.desX + move.srcX) / 2][(move.desY + move.srcY) / 2] = SquareHolder.NONE;
		}
		// king
		if (move.desX > move.srcX)
			if (move.desX == 7)
				holders[move.desX][move.desY] = holders[move.desX][move.desY].HolderToKing();
		if (move.desX < move.srcX)
			if (move.desX == 0)
				holders[move.desX][move.desY] = holders[move.desX][move.desY].HolderToKing();
		// continuous eat
		if (move.nextMandaryMove != null)
			doMovement(move.nextMandaryMove);
		else
			callBack.update();
	}

	public void cancelTryMovementHelper(Movement move) {
		holders[move.srcX][move.srcY] = holders[move.desX][move.desY];
		holders[move.desX][move.desY] = SquareHolder.NONE;
		if (move.eat != null)
			holders[(move.desX + move.srcX) / 2][(move.desY + move.srcY) / 2] = move.eat;
		if (move.desX > move.srcX) {
			if (move.desX == 7 && move.convert) {
				holders[move.srcX][move.srcY] = holders[move.srcX][move.srcY].HolderToNormal();
			}
		}

		if (move.desX < move.srcX) {
			if (move.desX == 0 && move.convert) {
				holders[move.srcX][move.srcY] = holders[move.srcX][move.srcY].HolderToNormal();

			}
		}
	}

	public void cancelTryMovement(Movement move) {
		if (move.nextMandaryMove != null)
			cancelTryMovement(move.nextMandaryMove);
		cancelTryMovementHelper(move);
	}

	public void setCallback(gameController gamecontroller) {
		this.callBack = gamecontroller;
	}
}
