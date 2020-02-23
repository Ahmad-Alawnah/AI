import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        long attempt = 1;
        while(true) {
            System.out.println("Attempt #"+attempt++);
            Letter.target = new int[][]{{0, 1, 0, 1, 0}, {0, 1, 0, 1, 0}, {0, 1, 1, 1, 0}, {0, 1, 0, 1, 0}, {0, 1, 0, 1, 0}};

            Letter[] population = new Letter[6];

            int[][] generator = new int[5][5];

            for (int i = 0; i < population.length; i++) {
                Letter.generateLetter(generator);
                population[i] = new Letter(generator);
            }
            Random random = new Random();
            Letter result = new Letter(new int[5][5]);
            long genResult = 0;

            //crossover probability: 0.7, mutation probability: 0.001
            for (long i = 0; i < 100000; i++) {
                int probCrossOver = 70;
                int probCrossOverResult = (random.nextInt() & Integer.MAX_VALUE) % 100 + 1;
                int probMutation = 5;
                int probMutationResult = (random.nextInt() & Integer.MAX_VALUE) % 1000 + 1;

                population[4] = new Letter(new int[5][5]);
                population[5] = new Letter(new int[5][5]);
                boolean crossOverHappened = false;
                boolean mutationHappened = false;

                if (probCrossOverResult <= probCrossOver) { //crossover happens
                    int first = (random.nextInt() & Integer.MAX_VALUE) % 4;
                    int second = (random.nextInt() & Integer.MAX_VALUE) % 4;
                    while (first == second) {
                        second = (random.nextInt() & Integer.MAX_VALUE) % 4;
                    }

                    population[4].setChromosome(population[first].crossOver(population[second].getChromosome()));
                    crossOverHappened = true;
                }

                if (probMutation == probMutationResult) { //mutation happens
                    int lucky = (random.nextInt() & Integer.MAX_VALUE) % 4;
                    population[5].setChromosome(population[lucky].mutate());
                    mutationHappened = true;
                }
                int newPopSize = 4;
                if (crossOverHappened) newPopSize++;
                if (mutationHappened) newPopSize++;

                Arrays.sort(population, 0, newPopSize); //only the first 4 will be considered next time
                if (population[0].getFitness() < result.getFitness()) {
                    result.setChromosome(population[0].getChromosome());
                    genResult = i;
                }
            }

            result.print();
            System.out.println("Fitness: " + result.getFitness());
            System.out.println("Generation: " + genResult);
        }

    }
}
