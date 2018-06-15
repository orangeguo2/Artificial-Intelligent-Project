package gameControl;

import Algorithm.AlgorithmInterface;
import GUI.Player;


public class Operation implements HelperListener{
	public Player player;
	public SetEnvir envir;
	

	public void setSetEnvir(SetEnvir envir) {
		this.envir = envir;
	}

	public Operation(Player player) {
		this.player = player;
	}

	public double playTurn(Helper helper) {
		AlgorithmInterface algorithm = envir.getAlgorithm();
		Movement move = algorithm.algorithm(envir, helper, player);
		if(move.value!=Integer.MIN_VALUE || move.value!= Integer.MAX_VALUE)
			helper.doMovement(move);
		return move.value;
	}
	
	@Override
	public void helperUpdated(Helper helper, Operation updater) {
		if(player == updater.player)
			return;
		playTurn(helper);
	}


}
