package Algorithm;



import java.util.List;

import GUI.Player;
import gameControl.CalculateFunction;
import gameControl.Helper;
import gameControl.Movement;
import gameControl.SetEnvir;
import gameControl.SuccessorInterface;



public class MinimaxAlgorithm implements AlgorithmInterface{

	@Override
	public Movement algorithm (SetEnvir context, Helper helper, Player whosTurn) {
		if(context == null || helper == null) {
			throw new IllegalArgumentException();
		}
		//set initial values to start recursion:
		return minimax(context, helper, whosTurn, 0);
	}

	Movement minimax(SetEnvir context, Helper helper, Player whosTurn, int currentDepth) {
		//check if reached target-depth
		if(context.getDepth()==currentDepth){
			Movement move = new Movement();
			move.value= evaluateHelper(context, helper);
			return move;
		}
		
		//get all possible moves:
		List<Movement> successors = getSuccessors(context, helper, whosTurn);
		//if there is no possible move, set min or max value
		if(successors.isEmpty()){
			Movement move = new Movement();
			int value = context.getPlayer() == whosTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE; 
			move.value= value;
			return move;
		}
		
		double value = 0;
		Movement selectedMovement = null;
		for (Movement move : successors) {
			helper.tryMovement(move); // make move
			
			//recursion:
			Movement minimax = minimax(context, helper,whosTurn.opposite(), currentDepth+1);
			if(selectedMovement == null                                                 //to set initial move
				|| (context.getPlayer() == whosTurn && minimax.value> value)   //max value is desired for computer
				|| (context.getPlayer() != whosTurn && minimax.value< value)){ //min value is desired for opponent
				
				value = minimax.value;
				move.next = minimax;
				selectedMovement = move;
			}
			
			helper.cancelTryMovement(move); // remove move back
		}
		selectedMovement.value = value;
		return selectedMovement;
	}

	private double evaluateHelper(SetEnvir context, Helper helper) {
		CalculateFunction evaluationFunction = context.getEvaluationFunction();
		return evaluationFunction.evaluate(helper,context.getPlayer());
	}
	
	private List<Movement> getSuccessors(SetEnvir context, Helper helper, Player whosTurn) {
		SuccessorInterface successor = context.getSuccessorFunction();
		List<Movement> successors = successor.getSuccessor(helper, whosTurn);
		return successors;
	}

	@Override
	public String getName() {
		return "Min-Max Algorithm";
	}

	

}
