import java.util.Arrays;

public class Fast {
    public static void main(String[] args) {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        StdDraw.show(0);

        // read in the input
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();

        Point points[] = new Point[N];
        Point copies[] = new Point[N];

        for (int i = 0; i < N; i++) {
            points[i] = new Point(in.readInt(), in.readInt());
            points[i].draw();
        }

        // Sort points in natural order described in specification
        Arrays.sort(points);

        for (int i = 0; i < N; i++) {
            copies[i] = points[i];
        }

        for (int i = 0; i < N; i++) {
            // Sort copy array by slope order from current point
            Arrays.sort(copies, points[i].SLOPE_ORDER);

            int start = 1;
            int count = 1;

            while (start < N) {
                double slope = points[i].slopeTo(copies[start]);
                int stop = start + count;

                while (stop < N && points[i].slopeTo(copies[stop]) == slope)
                    stop = start + ++count;

                // More than 2 points found with same slope to current point
                // This is our line case
                if (count > 2) {
                    Point tmp[] = new Point[count];

                    for (int j = start, k = 0; k < count;)
                        tmp[k++] = copies[j++];

                    // Order tmp array by natural order, which allows
                    // us to detect if segment has already been printed
                    Arrays.sort(tmp);

                    if (points[i].compareTo(tmp[0]) < 0) {
                        System.out.print(points[i]);
                        for (int j = 0; j < tmp.length; j++) {
                            System.out.print(" -> ");
                            System.out.print(tmp[j]);
                        }
                        System.out.println();
                        points[i].drawTo(tmp[count - 1]);
                    }
                }

                start = stop;
                count = 1;
            }

            Arrays.sort(copies);
        }

        StdDraw.show(0);
    }
}
