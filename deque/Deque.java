import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null, last = null;
    private int count;

    // construct an empty deque
    public Deque() {
    }

    private class Node {
        Item item;
        Node next;
        Node previous;
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return count;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null can't be the input");
        }
        Node oldfirst = first;
        first = new Node();
        if (oldfirst == null) {
            last = first;
        }
        else {
            oldfirst.previous = first;
            first.next = oldfirst;
        }
        first.item = item;
        count++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("null can't be the input");
        }
        if (last == null) {
            addFirst(item);
            return;
        }
        Node oldlast = last;
        last = new Node();
        oldlast.next = last;
        last.previous = oldlast;
        last.item = item;
        count++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        if (first.next == null) {
            Item boom = first.item;
            first = null;
            last = null;
            count--;
            return boom;
        }
        Item item = first.item;
        first = first.next;
        first.previous = null;
        count--;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        Node oldlast = last;
        if (oldlast.previous == null) {
            Item lastItem = oldlast.item;
            last = null;
            first = null;
            count--;
            return lastItem;
        }
        Item item = oldlast.item;
        last = oldlast.previous;
        last.next = null;
        oldlast.previous = null;
        count--;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public boolean hasNext() {
            return current != null;
        }

        public Item next() {
            if (current == null) {
                throw new java.util.NoSuchElementException();
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        testCourseraCase1();
    }


    private static void testCourseraCase1() {
        System.out.println("\ntestCase1");
        Deque<Integer> deque = new Deque<>();
        compare("deque.isEmpty()", deque.isEmpty(), true);
        deque.addLast(2);
        compare("deque.removeFirst()", deque.removeFirst(), 2);
        compare("deque.isEmpty()", deque.isEmpty(), true);
        deque.addLast(5);
        compare("deque.removeFirst()", deque.removeFirst(), 5);
    }

    private static void compare(String command, Object result, Object expected) {
        System.out.println(command + " -> " + result + " - " + expected);
        String errorMessage =
                "ERROR!!! Result and expected dont match." +
                        " Result: " + result + " Expected: " + expected;
        if (result != expected) System.out.println(errorMessage);
    }
}