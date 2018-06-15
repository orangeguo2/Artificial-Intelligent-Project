package gameControl;

import java.util.List;
import GUI.Player;


public interface SuccessorInterface {
List<Movement> getSuccessor(Helper helper, Player player) ;
}
