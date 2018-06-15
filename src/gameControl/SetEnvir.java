package gameControl;

import Algorithm.AlgorithmInterface;
import GUI.Player;

public class SetEnvir {
	private int depth = 4;
	private Player player;

	private AlgorithmInterface algorithm;
	private CalculateFunction calcuationFunction = new EnhancedWeightenedMenCountEvaluation();
	private SuccessorInterface successorFunction;
	
	public Player getPlayer() {
		return player;
	}
	
	public void setPlayer(Player player) {
		this.player=player;
	}
	
	public int getDepth() {
		return depth;
	}		

	public CalculateFunction getEvaluationFunction() {
		return this.calcuationFunction;
	}
	
	public void setAlgorithm(AlgorithmInterface algorithm) {
		this.algorithm = algorithm;
	}

	public AlgorithmInterface getAlgorithm() {
		return algorithm;
	}
	
	public void setSuccessorFunction(SuccessorInterface successor) {
		this.successorFunction = successor;
	}

	public SuccessorInterface getSuccessorFunction() {
		return successorFunction;
	}
	
}
