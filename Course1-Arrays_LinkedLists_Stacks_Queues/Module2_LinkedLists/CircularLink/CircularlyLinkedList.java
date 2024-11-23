package Module2_LinkedLists.CircularLink;
import java.util.Iterator;

public class CircularlyLinkedList<T> implements Iterable<T> {
    public CircularlyLinkedList(T data) {
        head = new Node<T>(data);
        head.next = head;
        size = 1;
    }

    public Iterator<T> iterator() {
        return new LLIterator();
    }

    private class LLIterator implements Iterator<T> {
        private Node<T> curr;

        LLIterator() { curr = head; }

        public boolean hasNext() {return curr.next != null; }

        public T next() {
            if (hasNext()) {
                T temp = curr.data;
                curr = curr.next;
                return temp;
            }
            return null;
        }
    }

    private static class Node<T> {
        // Node data
        private T data;

        // Pointer to the next node
        private Node<T> next;

        private Node(T data, Node<T> next, Node<T> prev) {
            this.data = data;
            this.next = next;
        }

        private Node(T data) {this(data, null, null);}

        private Node() { this(null, null, null); }
    }

    // Used for optimization, can check size in O(1) to make size checking faster
    private int size;

    // Head pointer
    private Node<T> head;

    public void addToBack(T data) {
        if (head == null) {
            head = new Node<T>(data);
            head.next = head;
        } else {
            Node<T> newNode = new Node<>();
            newNode.next = head.next;
            head.next = newNode;
            newNode.data = head.data;
            head.data = data;
            head = newNode;
        }
        size++;
    }

    public void addToFront(T data) {
        if (head == null) {
            head = new Node<T>(data);
            head.next = head;
        } else {
            Node<T> newNode = new Node<>();
            newNode.next = head.next;
            head.next = newNode;
            newNode.data = head.data;
            head.data = data;
        }
        size++;
    }

    public T getHead(T data) {
        if (head != null) {
            return head.data;
        } else {
            return null;
        }
    }

    public void removeFromBack() {
        if (head == null) { return; }
        else if (size == 1) { 
            head = null;
        } else {
            Node<T> current = head;
            // Look ahead
            while (current.next.next != head) {
                current = current.next;
            }
            current.next = head;
        }
        size--;
    }

    public void removeFromFront() {
        // Java has garbage collection
        if (head == null) { return; }
        else if (size == 1) {
            head = null;
        } else {
            head.data = head.next.data;
            head.next = head.next.next;
        }
        size--;
    }

    public String toString() {
        String answer = "[";
        Node<T> current = head;
        answer += current.data + " ";
        current = current.next;
        while (current.next != head) {
            answer += current.data + " ";
            current = current.next;
        }
        answer += "]";

        return answer;
    }

    private void pruneDuplicatesImpl(Node<T> curr) {
        if (curr == null) { return; }
        else if (curr.next == head) { return; }
        else if (curr.data == curr.next.data) {
            // Duplicate, prune
            if (curr.next.next == head) { curr.next = head; }
            else {
                curr.next = curr.next.next;
            }
            // Recheck for this node
            pruneDuplicatesImpl(curr);
        } else {
            pruneDuplicatesImpl(curr.next);
        }
    }

    private Node<T> pruneWithReturnImpl(Node<T> curr) {
        if (curr == null) { return null; }
        curr.next = pruneWithReturnImpl(curr.next);
        if (curr.next != head && curr.data == curr.next.data) {
            return curr.next;
        }
        return curr;
    }

    public void pruneDuplicates() {
        pruneDuplicatesImpl(head);
    }

    public void removeDuplicates() {
        head = pruneWithReturnImpl(head);
    }

    public static void main(String[] args)
    {
        CircularlyLinkedList<Integer> list = new CircularlyLinkedList<>(10);
        list.addToFront(10);
        list.addToFront(9);
        list.addToFront(9);
        list.addToFront(8);
        list.addToFront(7);
        list.addToFront(6);
        list.addToFront(5);
        list.addToFront(5);
        list.addToFront(5);
        list.addToFront(5);
        list.addToFront(4);
        list.addToFront(3);
        list.addToFront(3);
        list.addToFront(3);
        list.addToFront(2);
        list.addToFront(1);
        System.out.println(list.toString());

        list.pruneDuplicates();
        System.out.println(list.toString());
    }
}
