import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] leo;
    private int N = 0;

    // construct an empty randomized queue
    public RandomizedQueue() {
        leo = (Item[]) new Object[2];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        else if (N == leo.length) {
            resize(leo.length * 2);
        }
        leo[N++] = item;
    }

    private void resize(int capacity) {
        Item[] copy;
        copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = leo[i];
        }
        leo = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        if (N == leo.length / 4 && N > 0) {
            resize(leo.length / 2);
        }
        int obj = StdRandom.uniformInt(N);
        Item item = leo[obj];
        leo[obj] = leo[--N];
        leo[N] = null;
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (this.isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        return leo[StdRandom.uniformInt(N)];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private class RandomizedQueueIterator implements Iterator<Item> {
        int[] randomIndex = new int[N];
        int totalSelected = 0;

        public RandomizedQueueIterator() {
            for (int i = 0; i < N; i++) {
                randomIndex[i] = i;
            }
            StdRandom.shuffle(randomIndex);
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return totalSelected < N && N != 0;
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            int selectedIndex = randomIndex[totalSelected++];
            return leo[selectedIndex];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

    }

}