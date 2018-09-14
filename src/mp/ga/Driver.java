package mp.ga;

import java.util.Arrays;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Driver {

	public static void main(String[] args) throws FileNotFoundException {

		int currentGen = 0;
		
		//Set up file writers
		int[] averageFitness = new int[GeneticAlgorithm.GENERATIONS];
		int[] bestFitness = new int[GeneticAlgorithm.GENERATIONS];
		PrintWriter averageFitnessCSV = new PrintWriter(new File("averageFitness.csv"));
		PrintWriter bestFitnessCSV = new PrintWriter(new File("bestFitness.csv"));
        StringBuilder ab = new StringBuilder();
        StringBuilder bb = new StringBuilder();
				
        //Set up population
		Population population = new Population(GeneticAlgorithm.POPULATION_SIZE).initialisePopulation();
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm();
				
		while (currentGen < GeneticAlgorithm.GENERATIONS) {
		
			double[] fitness = new double[GeneticAlgorithm.POPULATION_SIZE];
			
			//Create generation of bots and run battles to fin fitness
			for (int i = 0; i < GeneticAlgorithm.POPULATION_SIZE; i++) {
				
				double[] phenome = population.getChromosomes()[i].getGenes();
				Bot bot = new Bot("GA_"+currentGen+"_"+i, phenome);
				bot.writeCode();
				bot.compileCode();
				
				BattleRunner br = new BattleRunner();
				fitness[i] = br.runBattle("sampleex.GA_"+currentGen+"_"+i, 5);
				//System.out.println(fitness[i]);
				population.getChromosomes()[i].setFitness(fitness[i]);
			}
			
			//Sort fitness
			population.sortByFitness();
			ab.append(currentGen);ab.append(",");ab.append(population.getAverageFitness());ab.append("\n");
			bb.append(currentGen);bb.append(",");bb.append(population.getChromosomes()[0].getFitness());bb.append("\n");
			printPopulation(population, "Fitness gen #" + currentGen);
			
			//Evolve
			population = geneticAlgorithm.evolvePopulation(population);
		
			//Clear files
			if (currentGen != GeneticAlgorithm.GENERATIONS-1) {
				Bot.clearBots(currentGen);
			}
			
			currentGen++;

		}
		
		averageFitnessCSV.write(ab.toString());
		averageFitnessCSV.close();
		bestFitnessCSV.write(bb.toString());
		bestFitnessCSV.close();
		
		System.out.println("Populations total: " + GeneticAlgorithm.POPULATION_SIZE*GeneticAlgorithm.GENERATIONS);
		System.out.println("Genes total: " + GeneticAlgorithm.POPULATION_SIZE*GeneticAlgorithm.GENERATIONS*GeneticAlgorithm.CHROMO_STRUCTURE_LENGTH);
		System.out.println("Mutations: " + GeneticAlgorithm.mutations);
		System.out.println("COMPELTE");
		
	}
	
	public static void printPopulation(Population population, String heading) {
		System.out.println(heading);
		System.out.println("------------------------------------------------");
		for (int i = 0; i < population.getChromosomes().length; i++) {
			System.out.println("Chromosome #" + i + " : " + Arrays.toString(population.getChromosomes()[i].getGenes()) +
								" | Fitness: " + population.getChromosomes()[i].getFitness());
		}
	}
	
	
}
