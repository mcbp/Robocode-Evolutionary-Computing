package mp.ga;

import java.util.Arrays;

public class Chromosome {
	
	private double[] genes;
	private double fitness = 0;
	
	public Chromosome(int length) {
		genes = new double[length];
	}
	
	//Initialise with random gene values
	public Chromosome initialiseChromosome() {
		for (int i = 0; i < genes.length; i++) {
			genes[i] = Math.random();
		}
		return this;
	}
	
	//Get array of genes
	public double[] getGenes() {
		return genes;
	}
	
	//Get fitness of the chromosome
	public double getFitness() {
		return fitness;
	}
	
	public void setFitness(double fitness) {
		this.fitness = fitness;
	}
	
	//Not used anymore, BattleRunner returns fitness and
	//setFitness is called to set it
	public double calculateFitness() {
		double chromosomeFitness = 0;
		
		return chromosomeFitness;
	}
	
	//Get text of chromosome
	public String toString() {
		return Arrays.toString(this.genes);
	}

	
}
