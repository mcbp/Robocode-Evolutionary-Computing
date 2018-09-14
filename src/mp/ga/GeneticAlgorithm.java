package mp.ga;

public class GeneticAlgorithm {

	public static final int GENERATIONS = 15;
	public static final int POPULATION_SIZE = 30;
	public static final int CHROMO_STRUCTURE_LENGTH = 10;
	public static final double MUTATION_RATE = 0.05;
	public static final int NO_ELITE_CHROMOSOMES = 2;
	public static final int TOURNAMENT_SELECTION_SIZE = 30;
	public static int mutations = 0;
	
	//First performs crossover, then mutation
	public Population evolvePopulation(Population population) {
		return mutatePopulation(crossoverPopulation(population));
	}
	
	private Population crossoverPopulation(Population population) {
		Population crossoverPopulation = new Population(population.getChromosomes().length);
		//Do not crossover elite chromosomes
		for (int i = 0; i < NO_ELITE_CHROMOSOMES; i++) {
			crossoverPopulation.getChromosomes()[i] = population.getChromosomes()[i];
		}
		//Crossover the tournament winners selected from the rest
		for (int i = NO_ELITE_CHROMOSOMES; i < population.getChromosomes().length; i++) {
			Chromosome c1 = selectTournamentPopulation(population).getChromosomes()[0];
			Chromosome c2 = selectTournamentPopulation(population).getChromosomes()[0];
			crossoverPopulation.getChromosomes()[i] = crossoverChromosome(c1, c2);
		}
		return crossoverPopulation;
	}
	
	private Population mutatePopulation(Population population) {
		Population mutatePopulation = new Population(population.getChromosomes().length);
		//Do not mutate elite chromosomes
		for (int i = 0; i < NO_ELITE_CHROMOSOMES; i++) {
			mutatePopulation.getChromosomes()[i] = population.getChromosomes()[i];
		}
		//Call mutate on the rest
		for (int i = NO_ELITE_CHROMOSOMES; i < population.getChromosomes().length; i++) {
			mutatePopulation.getChromosomes()[i] = mutateChromosome(population.getChromosomes()[i]);
		}
		return mutatePopulation;
	}
	
	//50/50 chance of getting a gene from either parent
	private Chromosome crossoverChromosome(Chromosome c1, Chromosome c2) {
		Chromosome crossoverChromosome = new Chromosome(CHROMO_STRUCTURE_LENGTH);
		for (int i = 0; i < c1.getGenes().length; i++) {
			if (Math.random() < 0.5) {
				crossoverChromosome.getGenes()[i] = c1.getGenes()[i];
				mutations++;
			} else {
				crossoverChromosome.getGenes()[i] = c2.getGenes()[i];
			}
		}
		return crossoverChromosome;
	}
	
	//Mutation rate defines chance for each gene to be mutated
	private Chromosome mutateChromosome(Chromosome chromosome) {
		Chromosome mutateChromosome = new Chromosome(CHROMO_STRUCTURE_LENGTH);
		for (int i = 0; i < chromosome.getGenes().length; i++) {
			if (Math.random() < MUTATION_RATE) {
				//Mutation algorithm
				//could improve
				mutateChromosome.getGenes()[i] = Math.random();
			} else {
				mutateChromosome.getGenes()[i] = chromosome.getGenes()[i];
			}
		}
		return mutateChromosome;
	}
	
	//Choose x random individuals
	//Select one with highest fitness (tournament winner)
	private Population selectTournamentPopulation(Population population) {
		Population tournamentPopulation = new Population(TOURNAMENT_SELECTION_SIZE);
		for (int i = 0; i < TOURNAMENT_SELECTION_SIZE; i++) {
			tournamentPopulation.getChromosomes()[i] = 
					population.getChromosomes()[(int)(Math.random()*population.getChromosomes().length)];
		}
		tournamentPopulation.sortByFitness();
		return tournamentPopulation;
	}
}
