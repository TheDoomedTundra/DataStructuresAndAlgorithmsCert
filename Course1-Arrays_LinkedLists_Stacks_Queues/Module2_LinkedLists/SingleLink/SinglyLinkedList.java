package Module2_LinkedLists.SingleLink;

import java.util.Iterator;

public class SinglyLinkedList<T> implements Iterable<T> {
    public SinglyLinkedList(T data) {
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

        private Node(T data, Node<T> next) {
            this.data = data;
            this.next = next;
        }

        private Node(T data) {this(data, null);}
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
            // Code for when no tail:
            // Node<T> current = head;
            // while (current.next != null) {
            //     current = current.next;
            // }
            Node<T> newNode = new Node<>(data);
            tail.next = newNode;
            tail = newNode;
        }
        size++;
    }

    // Doesn't use tail, not implementing it since this is an example
    public void addToFront(T data) {
        Node<T> newNode = new Node<>(data);
        newNode.next = head;
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
            Node<T> current = head;
            // Look ahead
            while (current.next.next != null) {
                tail = current;
                current = current.next;
            }
            current.next = null;
        }
        size--;
    }

    public void removeFromFront() {
        // Java has garbage collection
        if (head == tail) {
            tail = null;
        }
        head = head.next;
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
        SinglyLinkedList<Integer> list = new SinglyLinkedList<>(10);
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
