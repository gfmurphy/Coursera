import java.util.Arrays;

public class Brute {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();

        Point points[] = new Point[N];

        for (int i = 0; i < N; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
            points[i].draw();
        }

        Arrays.sort(points);

        for (int w = 0; w < N; w++) {
            for (int x = w + 1; x < N; x++) {
                double slopeWX = points[w].slopeTo(points[x]);
                for (int y = x + 1; y < N; y++) {
                    double slopeWY = points[w].slopeTo(points[y]);
                    for (int z = y + 1; z < N; z++) {
                        double slopeWZ = points[w].slopeTo(points[z]);

                        if (slopeWX == slopeWY && slopeWX == slopeWZ &&
                            slopeWY == slopeWZ) {
                            System.out.print(points[w]);
                            System.out.print(" -> ");
                            System.out.print(points[x]);
                            System.out.print(" -> ");
                            System.out.print(points[y]);
                            System.out.print(" -> ");
                            System.out.println(points[z]);

                            points[w].drawTo(points[z]);
                        }
                    }
                }
            }
        }

        StdDraw.show(0);
    }
}
