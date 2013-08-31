/**
 * Name: George Murphy
 * Login: gfmurphy@gmail.com
 * Date: Aug 20th, 2013
 * 
 * Percolation Grid
 */
public class Percolation {
    private int dimension;
    private int top;
    private int bottom;
    private boolean[] sites;
    private WeightedQuickUnionUF connections;
    private WeightedQuickUnionUF topConnections;

    public Percolation(int N) {
        dimension = N;
        top    = 0;
        bottom = gridSize() + 1;
        sites = new boolean[gridSize() + 1];
        connections = new WeightedQuickUnionUF(gridSize() + 2);
        topConnections = new WeightedQuickUnionUF(gridSize() + 1);
        connectVirtualSites();
    }

    /**
     * Open site at given coordinates
     */
    public void open(int i, int j) {
        int index = convertDimensions(i, j);
        sites[index] = true;

        // bugs here
        if (i - 1 > 0)
            connectNeighbor(index, index - dimension); // Top
        if (j + 1 <= dimension)
            connectNeighbor(index, index + 1); // Right
        if (i + 1 <= dimension)
            connectNeighbor(index, index + dimension); // Bottom
        if (j - 1 > 0)
            connectNeighbor(index, index - 1); // Left
    }

    /**
     * Determine if site is open at given coordinates
     */
    public boolean isOpen(int i, int j) {
        return isOpen(convertDimensions(i, j));
    }

    /**
     * Determine if site is full at given coordinates
     */
    public boolean isFull(int i, int j) {
        return isFull(convertDimensions(i, j));
    }

    /**
     * Does the grid percolate in its current state
     */
    public boolean percolates() {
        boolean opened = true;
        if (dimension == 1)
            opened = isOpen(1);
        return connections.connected(top, bottom) && opened;
    }

    private void connectVirtualSites() {
        int size = gridSize();

        for (int i = 1; i <= dimension; i++) {
            connections.union(top, i);
            topConnections.union(top, i);
        }

        for (int i = size; i > size - dimension; i--)
            connections.union(bottom, i);
    }

    private int gridSize() { return dimension * dimension; }

    private int convertDimensions(int i, int j) {
        if (!isValidDimension(i))
            throw new IndexOutOfBoundsException("bad index: " + i);
        if (!isValidDimension(j))
            throw new IndexOutOfBoundsException("bad index: " + j);

        int index = (i - 1) * dimension + j;
        return index;
    }

    private boolean isFull(int index) {
        return isOpen(index) && topConnections.connected(top, index);
    }

    private boolean isOpen(int index) {
        return sites[index];
    }

    private boolean isValidDimension(int dim) {
        return dim > 0 && dim <= dimension;
    }

    private void connectNeighbor(int index, int neighbor) {
        if (neighbor > 0 && neighbor <= gridSize() && isOpen(neighbor)) {
            topConnections.union(index, neighbor);
            connections.union(index, neighbor);
        }
    }

    public static void main(String[] args) {
        Percolation perc = new Percolation(2);
        perc.open(1, 2);
        perc.open(1, 1);
        System.out.println(perc.percolates());
    }
}
