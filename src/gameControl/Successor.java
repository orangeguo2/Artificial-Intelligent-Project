package gameControl;

import java.util.LinkedList;
import java.util.List;

import GUI.Player;
import gameControl.dashBoard.SquareHolder;


public class Successor implements SuccessorInterface{
	enum Direction{
		RIGHT_FORWARD,
		LEFT_FORWARD,
		RIGHT_BOTTOM,
		LEFT_BOTTOM
	}
	
	private final static Direction[] kingDirections = Direction.values();
	private final static Direction[] lightDirections = new Direction[]{Direction.RIGHT_FORWARD, Direction.LEFT_FORWARD};
	private final static Direction[] darkDirections = new Direction[]{Direction.RIGHT_BOTTOM, Direction.LEFT_BOTTOM};
	
	
	
	public boolean handleList(LinkedList<Movement> list) {
		boolean hasMust = false;
		for (Movement move : list) {
			if(move.MandaryMove){
				hasMust = true;
				break;
			}
		}
		if(hasMust){
			LinkedList<Movement> linkedList = new LinkedList<Movement>(list);
			for (Movement move : linkedList) {
				if(!move.MandaryMove){
					list.remove(move);
				}
			}
		}
		return hasMust;
		
		
	}
	
	private Direction[] getDirections(SquareHolder squareHolder) {
		if(squareHolder.holder == null)
			return null;
		if(squareHolder.isKing()){
			return kingDirections;
		}
		if(squareHolder.holder == Player.LIGHT){
			return lightDirections;
		}
		if(squareHolder.holder == Player.DARK){
			return darkDirections;
		}
		throw new IllegalStateException("the state is illegal");
	}
	
	private boolean bottomCheck(SquareHolder[][] state, int i) {
		return i-1 < 0;
	}
	
	private SquareHolder RightBottom2Step(SquareHolder[][] state, int i , int j){
		if(bottomCheck(state, i)) {
			return null;
		}
		if( i-2 >= 0 && j+2 < state[i].length){
			return state[i-2][j+2];
		}
		return null;
	}
	private SquareHolder LeftBottom2Step(SquareHolder[][] state, int i, int j) {
		if(i-2>=0 &&  j-2 >= 0){
			return state[i-2][j-2];
		}
		return null;
	}
	private SquareHolder leftBottom(SquareHolder[][] state, int i, int j) {
		if(i-1>=0 &&  j-1 >= 0){
			return state[i-1][j-1];
		}
		return null;
	}
	private SquareHolder rightBottom(SquareHolder[][] state, int i, int j) {
		if(bottomCheck(state, i)){
			return null;
		}
		if(i-1>=0 &&  j+1 < state[i].length){
			return state[i-1][j+1];
		}
		return null;
	}
	private SquareHolder RightForward2Step(SquareHolder[][] state, int i, int j) {
		if( i+2 < state.length && j+2 < state[i].length){
			return state[i+2][j+2];
		}
		return null;
	}
	
	private SquareHolder LeftForward2Step(SquareHolder[][] state, int i, int j) {
		if( i+2 < state.length && j-2 >= 0){
			return state[i+2][j-2];
		}
		return null;
	}


	private SquareHolder leftForward(SquareHolder[][] state, int i, int j) {
		if(forwardCheck(state, i)){
			return null;
		}
		if(j-1 >= 0){
			return state[i+1][j-1];
		}
		return null;
	}
	
	private SquareHolder rightForward(SquareHolder[][] state, int i, int j) {
		if(forwardCheck(state, i)){
			return null;
		}			
		if(j+1 < state[i].length){
			return state[i+1][j+1];
		}
		return null;
	}

	private boolean forwardCheck(SquareHolder[][] state, int i) {
		return i+1 >= state.length;
	}
	
	private Movement createEatMove(int i, int j, int desX, int desY) {
		Movement move = createMove(i, j, desX, desY);
		move.MandaryMove = true;
		return move;
	}

	private Movement createMove(int i, int j, int desX, int desY) {
		Movement move = new Movement();
		move.srcX = i;
		move.srcY = j;
		move.desX = desX;
		move.desY = desY;
		return move;
	}
	
	
	private List<Movement> eatMoveList(Helper helper, int i, int j) {
		LinkedList<Movement> eatList = new LinkedList<Movement>();
		SquareHolder[][] state = helper.holders;
		SquareHolder currentSquare = state[i][j];
		Direction[] directions = getDirections(currentSquare);
		for (Direction direction : directions) {
			SquareHolder twoNext = null, next = null;
			int desX = -1, desY = -1;
			switch (direction) {
				case RIGHT_BOTTOM:
					twoNext = RightBottom2Step(state, i, j);
					next = rightBottom(state, i, j);
					desX = i-2;
					desY = j+2;
					break;
				case RIGHT_FORWARD:
					twoNext = RightForward2Step(state, i, j);
					next = rightForward(state, i, j);
					desX = i+2;
					desY = j+2;
					break;
				case LEFT_BOTTOM:
					twoNext = LeftBottom2Step(state, i, j);
					next = leftBottom(state, i, j);
					desX = i-2;
					desY = j-2;
					break;
				case LEFT_FORWARD:
					twoNext = LeftForward2Step(state, i, j);
					next = leftForward(state, i, j);
					desX = i+2;
					desY = j-2;
					break;
				default:
					throw new IllegalStateException("No default value");
			}
			if(twoNext != null && next != null){
				boolean canEat = next.holder != null && 
						next.holder != currentSquare.holder && 
						twoNext == SquareHolder.NONE;
				if(canEat){
					Movement move = createEatMove(i, j, desX, desY);
					helper.tryMovement(move);
					List<Movement> nextList = eatMoveList(helper, desX, desY);
					if(nextList != null && nextList.size()>0){
						for (Movement nextMove : nextList) {
							Movement currentMove = createEatMove(i, j, desX, desY);
							currentMove.nextMandaryMove = nextMove;
							eatList.add(currentMove);
						}
					}else{
						Movement currentMove = createEatMove(i, j, desX, desY);
						currentMove.nextMandaryMove = null;
						eatList.add(currentMove);						
					}
					helper.cancelTryMovement(move);
				}
			}
		}
		return eatList;
	}
	
	private Movement createNormalMove(int i, int j, int desX, int desY) {
		Movement move = createMove(i, j, desX, desY);
		move.MandaryMove = false;
		return move;
	}
	
	private List<Movement> normalMovementList(Helper helper, int i, int j) {
		LinkedList<Movement> moveList = new LinkedList<Movement>();
		SquareHolder[][] state = helper.holders;
		SquareHolder currentSquare = state[i][j];
		Direction[] directions = getDirections(currentSquare);
		for (Direction direction : directions) {
			SquareHolder next = null;
			int desX = -1, desY = -1;
			switch (direction) {
				case RIGHT_BOTTOM:
					next = rightBottom(state, i, j);
					desX = i-1;
					desY = j+1;
					break;
				case RIGHT_FORWARD:
					next = rightForward(state, i, j);
					desX = i+1;
					desY = j+1;
					break;
				case LEFT_BOTTOM:
					next = leftBottom(state, i, j);
					desX = i-1;
					desY = j-1;
					break;
				case LEFT_FORWARD:
					next = leftForward(state, i, j);
					desX = i+1;
					desY = j-1;
					break;
				default: 
					throw new IllegalStateException("No default value");
			}
			boolean canGo = next==SquareHolder.NONE;
			if(canGo){
				Movement move = createNormalMove(i, j, desX, desY);
				moveList.add(move);
			}
		}
		
		return moveList;
	}
	
	public void handleStone(LinkedList<Movement> list, Helper helper, int i, int j){
		SquareHolder[][] state = helper.holders;
		if(state[i][j] == SquareHolder.NONE)
			return;
		List<Movement> eatList = eatMoveList(helper, i, j);
		if(eatList != null && eatList.size()>0){
			list.addAll(eatList);
		}else{
			List<Movement> moveList = normalMovementList(helper, i, j);
			list.addAll(moveList);
		}
	}
	@Override
	public List<Movement> getSuccessor(Helper helper, Player player) {
		// TODO Auto-generated method stub
		LinkedList<Movement> list = new LinkedList<Movement>();
		SquareHolder[][] state = helper.holders;
		for (int i=0; i<state.length; i++){
			for (int j=0; j<state[i].length; j++){
				if(state[i][j].holder == player)
					handleStone(list, helper, i, j);
			}
		}
		handleList(list);
		return list;

	}
	
	
}

