import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private Item[] items;
    private int size;

    public RandomizedQueue() {
        items = (Item[]) new Object[2];
    }

    public boolean isEmpty() { return size == 0; }
    public int size()        { return size; }

    public Iterator<Item> iterator() { return new RandomQueueIterator(); }

    public Item sample() {
        checkEmpty();
        return items[sampleIndex()];
    }

    public Item dequeue() {
        checkEmpty();

        int index = sampleIndex();
        Item item = items[index];
        items[index] = items[size - 1];
        items[--size] = null;

        if (size > 0 && size == items.length / 4) resize(items.length / 2);
        return item;
    }

    public void enqueue(Item item) {
        checkNull(item);

        if (size == items.length) resize( 2 * items.length);
        items[size++] = item;
    }

    private int sampleIndex() {
        return StdRandom.uniform(size);
    }

    private void resize(int capacity) {
        assert capacity >= size;
        Item[] tmp = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++) { tmp[i] = items[i]; }
        items = tmp;
    }

    private void checkNull(Item item) {
        if(item == null)
            throw new NullPointerException("Item is null");
    }

    private void checkEmpty() {
        if(isEmpty())
            throw new NoSuchElementException("Queue is empty");
    }

    private class RandomQueueIterator implements Iterator<Item> {
        private int count;
        private int[] indexes;

        public RandomQueueIterator() {
            indexes = new int[size];
            for (int i = 0; i < size; i++) { indexes[i] = i; }
            StdRandom.shuffle(indexes);
            count = size;
        }

        public void remove() { throw new UnsupportedOperationException(); }
        public boolean hasNext() { return count > 0; }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return items[indexes[--count]];
        }
    }
}
