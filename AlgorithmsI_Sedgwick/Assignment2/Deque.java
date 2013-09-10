import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
    public static void main(String[] args) {
        Deque<Integer> d = new Deque<Integer>();
        for (int i = 0; i < 10; i++) d.addLast(i);
        for (int i = 0; i < 10; i++) System.out.println(d.removeFirst());

        assert d.isEmpty();

        for (int i = 0; i < 10; i++) d.addFirst(i);
        for (int i = 0; i < 10; i++) System.out.println(d.removeLast());
    }

    private Node<Item> head;
    private Node<Item> tail;
    private int size;

    public Deque() {}

    public boolean isEmpty()         { return size == 0; }

    public int size()                { return size; }

    public Iterator<Item> iterator() { return new DequeIterator(head); }

    public void addFirst(Item item) {
        checkNull(item);

        Node<Item> oldHead = head;
        head = new Node<Item>(item);
        head.setNext(oldHead);

        if (oldHead != null) oldHead.setPrev(head);
        else tail = head;

        size++;
        assert valid();
    }

    public void addLast(Item item) {
        checkNull(item);

        Node<Item> oldTail = tail;
        tail = new Node<Item>(item);
        tail.setPrev(oldTail);

        if (oldTail != null) oldTail.setNext(tail);
        else head = tail;

        size++;
        assert valid();
    }

    public Item removeFirst() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Node<Item> tmp = head;
        head = head.getNext();

        if (head != null) head.setPrev(null);
        else tail = null;

        size--;

        assert valid();
        return tmp.getItem();
    }

    public Item removeLast() {
        if (isEmpty()) throw new NoSuchElementException("Deque is empty");
        Node<Item> tmp = tail;
        tail = tail.getPrev();

        if (tail != null) tail.setNext(null);
        else head = null;

        size--;

        assert valid();
        return tmp.getItem();
    }

    private void checkNull(Item item) {
        if (item == null)
            throw new NullPointerException("Item is null");
    }

    private boolean valid() {
        if (size == 0)
            return head == tail && head == null;
        else if (size == 1) {
            return head == tail && head != null && head.prev == null &&
                tail.next == null;
        }
        else {
            return head != null
                && head.next != null
                && head.prev == null
                && tail != null
                && tail.next == null
                && tail.prev != null;
        }
    }

    private class DequeIterator implements Iterator<Item> {
        private Node<Item> current;

        public DequeIterator(Node<Item> start) { this.current = start; }
        public DequeIterator()                 { this.current = head; }

        public boolean hasNext() { return current != null; }
        public void remove()     { throw new UnsupportedOperationException(); }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            Item item = current.getItem();
            this.current = current.getNext();
            return item;
        }
    }

    private class Node<Item> {
        Item item;
        Node<Item> next;
        Node<Item> prev;

        public Node(Item item) { this.item = item; }
        public Item getItem()  { return item; }
        public Node<Item> getNext() { return next; }
        public Node<Item> getPrev() { return prev; }

        public void setNext(Node<Item> next) { this.next = next; }
        public void setPrev(Node<Item> prev) { this.prev = prev; }
    }
}
