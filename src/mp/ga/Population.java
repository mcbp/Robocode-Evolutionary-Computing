package mp.ga;

import java.util.Arrays;

public class Population {

	private Chromosome[] chromosomes;
	
	public Population(int length) {
		chromosomes = new Chromosome[length];
	}
	
	//Create population, CHROMO_STRUCTURE_LENGTH determines the amount of genes in each chromosome
	public Population initialisePopulation() {
		for (int i = 0; i < chromosomes.length; i++) {
			chromosomes[i] = new Chromosome(GeneticAlgorithm.CHROMO_STRUCTURE_LENGTH).initialiseChromosome();
		}
		return this;
	}
	
	//Get array of chromosomes
	public Chromosome[] getChromosomes() {
		return chromosomes;
	}
	
	//Calculate the average fitness of all members in the population
	public double getAverageFitness() {
		double average = 0;
		for (int i = 0; i < GeneticAlgorithm.POPULATION_SIZE; i++) {
			average += this.getChromosomes()[i].getFitness();
		}
		return (average/GeneticAlgorithm.POPULATION_SIZE);
	}
	
	//Most fit = first, least fit = last
	public void sortByFitness() {
		Arrays.sort(chromosomes, (c1, c2) -> {
			int flag = 0;
			if (c1.getFitness() > c2.getFitness()) {
				flag = -1;
			} else if (c1.getFitness() < c2.getFitness()) {
				flag = 1;
			}
			return flag;
		});
	}
	
}
