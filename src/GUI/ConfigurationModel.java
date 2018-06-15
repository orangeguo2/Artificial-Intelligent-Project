package GUI;

import java.util.LinkedList;
import java.util.List;

import Algorithm.AlgorithmInterface;
import Algorithm.AlphaBetaAlgorithm;
import Algorithm.MinimaxAlgorithm;
import gameControl.CalculateFunction;
import gameControl.EnhancedWeightenedMenCountEvaluation;



public class ConfigurationModel {

	private List<AlgorithmInterface> algorithms = new LinkedList<AlgorithmInterface>();
	{
		algorithms.add(new MinimaxAlgorithm());
		algorithms.add(new AlphaBetaAlgorithm());
	}
	
	private AlgorithmInterface selectedAlgorithm = null;
	
	

	public List<AlgorithmInterface> getAlgorithms() {
		return algorithms;
	}

	public void setAlgorithms(List<AlgorithmInterface> algorithms) {
		this.algorithms = algorithms;
	}

	public AlgorithmInterface getSelectedAlgorithm() {
		return selectedAlgorithm;
	}

	public void setSelectedAlgorithm(AlgorithmInterface selectedAlgorithm) {
		this.selectedAlgorithm = selectedAlgorithm;
	}
	
	
	public String[] getAlgorithmNames(){
		String[] array = new String[algorithms.size()];
		for (int i = 0; i < algorithms.size(); i++) {
			array[i] = algorithms.get(i).getName();
		}
		return array;
	}


	public void setSelectedAlgorithmName(String selectedItem) {
		for (int i = 0; i < algorithms.size(); i++) {
			if(algorithms.get(i).getName().equals(selectedItem)){
				selectedAlgorithm = algorithms.get(i);
				break;
			}
		}
	}

		
	
}
