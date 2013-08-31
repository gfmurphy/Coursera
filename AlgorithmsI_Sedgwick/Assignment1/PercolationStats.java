/**
 * Name: George Murphy
 * Login: gfmurphy@gmail.com
 * Date: Aug 20th, 2013
 *
 * Runs T MonteCarlo experiments on grid of N size
 */
public class PercolationStats {
    private double[] runs;
    private int times;

    public PercolationStats(int N, int T) {
        if (N <= 0 || T <= 0)
            throw new IllegalArgumentException("Arguments must be > 0");

        times = T;
        runs  = new double[times];

        for (int i = 0; i < times; i++) {
            Percolation perc = new Percolation(N);
            int count = 0;

            while (!perc.percolates()) {
                int row = 0;
                int col = 0;

                do {
                    row = StdRandom.uniform(N) + 1;
                    col = StdRandom.uniform(N) + 1;
                } while (perc.isOpen(row, col));

                perc.open(row, col);
                count++;
            }

            runs[i] = count / ((double) N * N);
        }
    }

    /**
     * returns double the mean probability for perculation
     */
    public double mean() {
        return StdStats.mean(runs);
    }

    /**
     * returns double the standard deviation from the mean probability
     */
    public double stddev() {
        return StdStats.stddev(runs);
    }

    /**
     * returns lower bound of 95% confidence interval
     */
    public double confidenceLo() {
        return mean() - (1.96 * stddev()) / Math.sqrt(times);
    }

    /**
     * returns upper bound of 95% confidence interval
     */
    public double confidenceHi() {
        return mean() + (1.96 * stddev()) / Math.sqrt(times);
    }

    /**
     * main method
     */
    public static void main(String[] args) {
        int size = Integer.parseInt(args[0]);
        int times = Integer.parseInt(args[1]);

        PercolationStats stats = new PercolationStats(size, times);
        double mean = stats.mean();
        double stddev = stats.stddev();
        System.out.println("mean\t\t\t= " + mean);
        System.out.println("stddev\t\t\t= " + stddev);
        double conf0 = stats.confidenceLo();
        double conf1 = stats.confidenceHi();
        String msg = "95% confidence interval\t= " + conf0 + ", " + conf1;
        System.out.println(msg);
    }
}
