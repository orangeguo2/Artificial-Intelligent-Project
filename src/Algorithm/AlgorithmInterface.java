package Algorithm;

import GUI.Player;
import gameControl.Helper;
import gameControl.Movement;
import gameControl.SetEnvir;


public interface AlgorithmInterface {
	Movement algorithm(SetEnvir envir, Helper helper, Player whosTurn);
	String getName();
}
