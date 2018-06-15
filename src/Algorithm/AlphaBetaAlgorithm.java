package Algorithm;

import java.util.List;

import GUI.Player;
import gameControl.CalculateFunction;
import gameControl.Helper;
import gameControl.Movement;
import gameControl.SetEnvir;
import gameControl.SuccessorInterface;


public class AlphaBetaAlgorithm implements AlgorithmInterface{

	public Movement algorithm(SetEnvir context, Helper helper, Player whosTurn) {
		if(context == null || helper == null) {
			throw new IllegalArgumentException();
		}
		//set initial values to start recursion:
		return alphaBeta(context, helper, whosTurn, 0, 0, 0, false, false);
	}

	Movement alphaBeta(SetEnvir context, Helper helper, Player whosTurn, int currentDepth, 
				double prunMin, double prunMax, boolean isPrunMinUsed, boolean isPrunMaxUsed) {
		
		//check if reached target-depth
		if(context.getDepth()==currentDepth){
			Movement move = new Movement();
			move.value=evaluateHelper(context, helper);
			return move;
		}
		
		//get all possible moves:
		List<Movement> successors = getSuccessors(context, helper, whosTurn);
		//if there is no possible move, set min or max value
		if(successors.isEmpty()){
			Movement move = new Movement();
			move.value = evaluateHelper(context, helper);
			return move;
		}
		
		
		double selectedValue = 0;
		Movement selectedMovement = null;
		for (Movement move : successors) {
			helper.tryMovement(move);// make move
			
			//to calculate pruning values:
			if(selectedMovement!=null){
				if(context.getPlayer() == whosTurn){
					if(isPrunMinUsed){
						prunMin = prunMin > selectedValue ? selectedValue : prunMin;
					}else{
						prunMin = selectedValue;
						isPrunMinUsed = true;
					}
				}else{
					if(isPrunMaxUsed){
						prunMax = prunMax < selectedValue ? selectedValue : prunMax;
					}else{
						prunMax = selectedValue;
						isPrunMaxUsed = true;
					}
				}
			}
			
			//recursion:
			Movement minimax = alphaBeta(context, helper, whosTurn.opposite(), currentDepth+1, prunMin, prunMax, isPrunMinUsed, isPrunMaxUsed);
			if(selectedMovement==null                                                               //to set initial move
					|| (context.getPlayer() == whosTurn && minimax.value> selectedValue)   //max value is desired for computer
					|| (context.getPlayer() != whosTurn && minimax.value< selectedValue)){ //min value is desired for opponent
				
				selectedValue = minimax.value;
				move.next = minimax;
				selectedMovement = move;
			}
			
			helper.cancelTryMovement(move); // remove move back
			
			//to make pruning:
			if(context.getPlayer() == whosTurn && isPrunMinUsed){
				if(prunMin >= selectedValue){
					selectedMovement.value = selectedValue;
					return selectedMovement;
				}
			}
			if(context.getPlayer() != whosTurn && isPrunMaxUsed){
				if(prunMax <= selectedValue){
					selectedMovement.value = selectedValue;
					return selectedMovement;
				}
			}
		}
		selectedMovement.value = selectedValue;
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
		return "Alpha Beta Algorithm";
	}

}
