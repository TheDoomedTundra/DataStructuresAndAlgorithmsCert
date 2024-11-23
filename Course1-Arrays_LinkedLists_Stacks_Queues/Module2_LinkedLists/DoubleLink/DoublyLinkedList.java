package Module2_LinkedLists.DoubleLink;

import java.util.Iterator;

public class DoublyLinkedList<T> implements Iterable<T> {
    public DoublyLinkedList(T data) {
        head = new Node<T>(data);
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

        // Pointer to the previous node
        private Node<T> prev;

        private Node(T data, Node<T> next, Node<T> prev) {
            this.data = data;
            this.next = next;
            this.prev = prev;
        }

        private Node(T data) {this(data, null, null);}
    }

    // Used for optimization, can check size in O(1) to make size checking faster
    private int size;

    // Tail pointer makes addToBack O(1)
    private Node<T> tail;

    // Head pointer
    private Node<T> head;

    public void addToBack(T data) {
        if (head == null) {
            head = new Node<>(data);
            tail = head;
        } else {
            Node<T> newNode = new Node<>(data);
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        size++;
    }

    public void addToFront(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
        head.prev = newNode;
        head = newNode;
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
        else if (head.next == null) { 
            head = null; 
            tail = null;
        } else {
            // Look ahead
            tail = tail.prev;
            tail.next = null;
        }
        size--;
    }

    public void removeFromFront() {
        // Java has garbage collection
        if (head == null) { return; }
        else if (head.next == null) { 
            head = null; 
            tail = null;
        } else {
            head = head.next;
            head.prev = null;
        }
        size--;
    }

    public String toString() {
        String answer = "[";
        Node<T> current = head;
        while (current != null) {
            answer += current.data + " ";
            current = current.next;
        }
        answer += "]";

        return answer;
    }

    private void pruneDuplicatesImpl(Node<T> curr) {
        if (curr == null) { return; }
        else if (curr.next == null) { return; }
        else if (curr.data == curr.next.data) {
            // Duplicate, prune
            if (curr.next.next == null) { curr.next = null; }
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
        if (curr.next != null && curr.data == curr.next.data) {
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
        DoublyLinkedList<Integer> list = new DoublyLinkedList<>(10);
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
