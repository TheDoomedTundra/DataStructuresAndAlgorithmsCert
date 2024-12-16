package Module7_HashMaps;

import java.util.NoSuchElementException;
import java.lang.Math;

/**
 * Your implementation of a ExternalChainingHashMap.
 */
public class ExternalChainingHashMap<K, V> {

    /*
     * The initial capacity of the ExternalChainingHashMap when created with the
     * default constructor.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final int INITIAL_CAPACITY = 13;

    /*
     * The max load factor of the ExternalChainingHashMap.
     *
     * DO NOT MODIFY THIS VARIABLE!
     */
    public static final double MAX_LOAD_FACTOR = 0.67;

    /*
     * Do not add new instance variables or modify existing ones.
     */
    private ExternalChainingMapEntry<K, V>[] table;
    private int size;

    /**
     * Constructs a new ExternalChainingHashMap with an initial capacity of INITIAL_CAPACITY.
     */
    @SuppressWarnings("unchecked")
    public ExternalChainingHashMap() {
        //DO NOT MODIFY THIS METHOD!
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[INITIAL_CAPACITY];
    }

    /**
     * Gets the value associated with the given key.
     *
     * @param key The key to search for in the map. You may assume that the
     *            key is never null.
     * @return The value associated with the given key.
     * @throws java.util.NoSuchElementException If the key is not in the map.
     */
    public V get(K key) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        int idx = compress(key);
        
        ExternalChainingMapEntry<K, V> curr = table[idx];
        while (curr != null) {
             if (key.equals(curr.getKey())) {
                  return curr.getValue();   
             } else {
                  curr = curr.getNext();   
             }
        }
        throw new NoSuchElementException();
    }

    /**
     * Adds the given key-value pair to the map. If an entry in the map
     * already has this key, replace the entry's value with the new one
     * passed in.
     *
     * In the case of a collision, use external chaining as your resolution
     * strategy. Add new entries to the front of an existing chain, but don't
     * forget to check the entire chain for duplicate keys first.
     *
     * If you find a duplicate key, then replace the entry's value with the new
     * one passed in. When replacing the old value, replace it at that position
     * in the chain, not by creating a new entry and adding it to the front.
     *
     * Before actually adding any data to the HashMap, you should check to
     * see if the table would violate the max load factor if the data was
     * added. Resize if the load factor (LF) is greater than max LF (it is
     * okay if the load factor is equal to max LF). For example, let's say
     * the table is of length 5 and the current size is 3 (LF = 0.6). For
     * this example, assume that no elements are removed in between steps.
     * If another entry is attempted to be added, before doing anything else,
     * you should check whether (3 + 1) / 5 = 0.8 is larger than the max LF.
     * It is, so you would trigger a resize before you even attempt to add
     * the data or figure out if it's a duplicate. Be careful to consider the
     * differences between integer and double division when calculating load
     * factor.
     *
     * When regrowing, resize the length of the backing table to
     * (2 * old length) + 1. You should use the resizeBackingTable method to do so.
     *
     * @param key   The key to add.
     * @param value The value to add.
     * @return null if the key was not already in the map. If it was in the
     *         map, return the old value associated with it.
     * @throws java.lang.IllegalArgumentException If key or value is null.
     */
    public V put(K key, V value) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        assertNullKey(key);
        assertNullValue(value);

        if (needsResize()) {
            resizeBackingTable(2*table.length + 1);
        }

        int idx = compress(key);
        V rVal = setValInChain(table[idx], key, value);

        // Didn't find the key, add it to the front of the chain
        if (rVal == null) {
            ExternalChainingMapEntry<K, V> newHead = new ExternalChainingMapEntry<>(key, value);
            newHead.setNext(table[idx]);
            table[idx] = newHead;
            size++;
        }

        return rVal;
    }

    // Search for the key in the chain, if it is found update the entry value and return the old value
    private V setValInChain(ExternalChainingMapEntry<K, V> curr, K key, V value) {
        if (curr == null) {
            return null;
        } else if (curr.getKey().equals(key)) {
            V rVal = curr.getValue();
            curr.setValue(value);
            return rVal;
        } else {
            return setValInChain(curr.getNext(), key, value);
        }
    }

    private V removeEntry(ExternalChainingMapEntry<K, V> curr, K key) {
        if (curr == null) {
            return null;
        } else if (curr.getNext() == null) {
            return null;
        } else if (curr.getNext().getKey().equals(key)) {
            V rVal = curr.getNext().getValue();
            curr.setNext(curr.getNext().getNext());
            return rVal;
        } else {
            return removeEntry(curr.getNext(), key);
        }
    }

    /**
     * Removes the entry with a matching key from the map.
     *
     * @param key The key to remove.
     * @return The value associated with the key.
     * @throws java.lang.IllegalArgumentException If key is null.
     * @throws java.util.NoSuchElementException   If the key is not in the map.
     */
    public V remove(K key) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        assertNullKey(key);
        int idx = compress(key);
        V rVal = null;
        if (table[idx] == null) {
        } else if (table[idx].getKey().equals(key)) {
            // Handle case where it is the head node
            rVal = table[idx].getValue();
            table[idx] = table[idx].getNext();
        } else {
            rVal = removeEntry(table[idx], key);
        }
        if (rVal == null) {
            throw new NoSuchElementException("Key not in the map!");
        }
        size--;
        return rVal;
    }

    /**
     * Helper method stub for resizing the backing table to length.
     *
     * This method should be called in put() if the backing table needs to
     * be resized.
     *
     * You should iterate over the old table in order of increasing index and
     * add entries to the new table in the order in which they are traversed.
     *
     * Since resizing the backing table is working with the non-duplicate
     * data already in the table, you won't need to explicitly check for
     * duplicates.
     *
     * Hint: You cannot just simply copy the entries over to the new table.
     *
     * @param Length The new length of the backing table.
     */
    @SuppressWarnings("unchecked")
    private void resizeBackingTable(int length) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        ExternalChainingMapEntry<K, V>[] tmpTable = table;
        table = (ExternalChainingMapEntry<K, V>[]) new ExternalChainingMapEntry[length];

        // Reset the size since we're starting from 0 and using put to re-add
        size = 0;

        ExternalChainingMapEntry<K, V> curr = null;
        for (int i = 0; i < tmpTable.length; i++) {
            curr = tmpTable[i];
            while (curr != null) {
                put(curr.getKey(), curr.getValue());
                curr = curr.getNext();
            }
        }
    }

    // Check if we need to resize
    private boolean needsResize() {
        return ((double) (size + 1) / table.length) > MAX_LOAD_FACTOR; 
    }

    private int compress(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    /**
     * Returns whether or not the key is in the map.
     *
     * @param key The key to search for in the map. You may assume that the
     *            key is never null.
     * @return true if the key is contained within the map, false otherwise.
     */
    public boolean containsKey(K key) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        int idx = compress(key);
        
        ExternalChainingMapEntry<K, V> curr = table[idx];
        while (curr != null) {
             if (key.equals(curr.getKey())) {
                  return true;   
             } else {
                  curr = curr.getNext();   
             }
        }
        return false;
    }


    /**
     * Returns the table of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The table of the map.
     */
    public ExternalChainingMapEntry<K, V>[] getTable() {
        // DO NOT MODIFY THIS METHOD!
        return table;
    }

    /**
     * Returns the size of the map.
     *
     * For grading purposes only. You shouldn't need to use this method since
     * you have direct access to the variable.
     *
     * @return The size of the map.
     */
    public int size() {
        // DO NOT MODIFY THIS METHOD!
        return size;
    }

    private void assertNullKey(K key) {
        if (key == null) {
            throw new IllegalArgumentException("Null key detected!");
        }
    }

    private void assertNullValue(V value) {
        if (value == null) {
            throw new IllegalArgumentException("Null value detected!");
        }
    }
}