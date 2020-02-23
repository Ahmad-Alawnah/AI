import java.util.Random;
public class Letter implements Comparable<Letter>{
    public static int[][] target;
    private int[][] chromosome;
    private int fitness;


    public static int toDecimal(int[] number){
        int res = 0;
        int exp = 1;
        for(int i =number.length-1;i>=0;i--){
            res += number[i] * exp;
            exp*=2;
        }
        return res;
    }

    public Letter(int[][] c){
        this.chromosome = new int[5][5];
        for(int i =0;i<c.length;i++){
            for(int j =0;j<c[i].length;j++){
                this.chromosome[i][j] = c[i][j];
            }
        }
        calculateFitness();
    }

    public void setChromosome(int [][] a){
        for(int i =0;i<a.length;i++){
            for(int j =0;j<a.length;j++){
                this.chromosome[i][j] = a[i][j];
            }
        }
        this.calculateFitness();
    }

    public int[][] getChromosome(){
        return this.chromosome;
    }

    public int getFitness(){
        return fitness;
    }

    public void calculateFitness(){
        int sum = 0;

        for(int i =0;i<this.chromosome.length;i++) {
            sum += Math.abs(toDecimal(chromosome[i]) - toDecimal(target[i]));
        }
        this.fitness = sum;
    }

    public int[][] crossOver(int[][] parent){
        int[][] kid = new int[5][5];
        Random random = new Random();
        for(int i=0;i<this.chromosome.length;i++){
            int r = (random.nextInt() &Integer.MAX_VALUE) % 5;
            for(int j = 0;j<r;j++){
                kid[i][j] = this.chromosome[i][j];
            }
            for(int j = r;j<parent.length;j++){
                kid[i][j] = parent[i][j];
            }
        }
        return kid;
    }

    public int[][] mutate(){
        int[][] newChromosome = this.chromosome;
        Random random = new Random();
        int i, j;
        for(int x =0;x<5;x++){
            i = (random.nextInt() &Integer.MAX_VALUE) %5;
            j = (random.nextInt() &Integer.MAX_VALUE) %5;
            newChromosome[i][j] =  (this.chromosome[i][j] + 1) % 2;
        }
        return newChromosome;
    }

    public void print(){
        for(int i =0;i<this.chromosome.length;i++) {
            for (int j = 0; j < this.chromosome[i].length; j++) {
                System.out.print(this.chromosome[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void printTarget(){
        for(int i =0;i<target.length;i++) {
            for (int j = 0; j < target[i].length; j++) {
                System.out.print(target[i][j] + " ");
            }
            System.out.println();
        }
    }

    public static void generateLetter(int[][] a){
        Random random = new Random();
        for(int i =0;i<a.length;i++){
            for(int j = 0;j<a[i].length;j++){
                int n = (random.nextInt() &Integer.MAX_VALUE) % 2;
                a[i][j] = n;
            }
        }
    }

    public int compareTo(Letter letter){

        int otherFitness = letter.getFitness();
        return this.getFitness() - otherFitness;

    }
}
