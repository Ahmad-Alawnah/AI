import java.util.Arrays;
import java.util.Random;

public class Main {

    public static void main(String[] args) {

        Letter.target = new int[][] {{0,1,0,1,0},{0,1,0,1,0},{0,1,1,1,0},{0,1,0,1,0},{0,1,0,1,0}};

        Letter[] population = new Letter[6];

        int[][] generator = new int[5][5];

        for(int i =0;i<population.length;i++){
            Letter.generateLetter(generator);
            population[i] = new Letter(generator);
        }
        Random random = new Random();
        Letter result = new Letter(new int[5][5]);
        Letter test = new Letter(new int[][]{{1,0,0,1,0},{0,1,0,0,1},{0,1,1,0,1},{0,0,1,1,1},{1,1,1,0,1}});
        //crossover probability: 0.7, mutation probability: 0.001
        for(int i =0;i<100000;i++){
            int probCrossOver = 70;
            int probCrossOverResult = random.nextInt()%100 + 1;
            int probMutation = 5;
            int probMutationResult = random.nextInt()%1000 + 1;
            population[4] = new Letter(new int[5][5]);
            population[5] = new Letter(new int[5][5]);
            if (probCrossOverResult <= probCrossOver) { //crossover happens
                int first = (random.nextInt() &Integer.MAX_VALUE) %4;
                int second = (random.nextInt() &Integer.MAX_VALUE) %4;
                while(first==second) {
                    second = (random.nextInt() &Integer.MAX_VALUE) % 4;
                }

                population[5].setChromosome(population[first].crossOver(population[second].getChromosome()));

            }

            if (probMutation == probMutationResult) { //mutation happens
                int lucky = (random.nextInt() &Integer.MAX_VALUE) % 4;
                population[5].setChromosome(population[lucky].mutate());


            }
            Arrays.sort(population); //only the first 4 will be considered next time

            if (population[0].getFitness()<result.getFitness()){
                result.setChromosome(population[0].getChromosome());
            }
        }

        result.print();
        System.out.println(result.getFitness());

    }
}
