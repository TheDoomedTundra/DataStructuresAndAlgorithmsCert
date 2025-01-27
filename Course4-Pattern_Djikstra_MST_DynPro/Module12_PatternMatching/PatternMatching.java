package Module12_PatternMatching;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Your implementations of the Boyer Moore string searching algorithm.
 */
public class PatternMatching {
    /**
     * Boyer Moore algorithm that relies on a last occurrence table.
     * This algorithm Works better with large alphabets.
     *
     * Make sure to implement the buildLastTable() method, which will be
     * used in this boyerMoore() method.
     *
     * Note: You may find the getOrDefault() method from Java's Map class useful.
     *
     * You may assume that the passed in pattern, text, and comparator will not be null.
     *
     * @param pattern    The pattern you are searching for in a body of text.
     * @param text       The body of text where you search for the pattern.
     * @param comparator You MUST use this to check if characters are equal.
     * @return           List containing the starting index for each match found.
     */
    public static List<Integer> boyerMoore(CharSequence pattern, CharSequence text, CharacterComparator comparator) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        Map<Character, Integer> last = buildLastTable(pattern);
        List<Integer> matches = new LinkedList<>();

        if (last.isEmpty()) {
            return matches;
        }

        int i = 0;
        while (i <= text.length() - pattern.length()) {
            int j = pattern.length() - 1;
            while (j >= 0 && comparator.compare(text.charAt(i+j), pattern.charAt(j)) == 0) {
                j--;
            }

            if (j == -1) {
                matches.addLast((Integer) i);
                i++;
            } else {
                int shift = last.getOrDefault(text.charAt(i+j), -1);
                if (shift < j) {
                    i = i + j - shift;
                } else {
                    i++;
                }
            }
        }
        return matches;
    }

    /**
     * Builds the last occurrence table that will be used to run the Boyer Moore algorithm.
     *
     * Note that each char x will have an entry at table.get(x).
     * Each entry should be the last index of x where x is a particular
     * character in your pattern.
     * If x is not in the pattern, then the table will not contain the key x,
     * and you will have to check for that in your Boyer Moore implementation.
     *
     * Ex. pattern = octocat
     *
     * table.get(o) = 3
     * table.get(c) = 4
     * table.get(t) = 6
     * table.get(a) = 5
     * table.get(everything else) = null, which you will interpret in
     * Boyer-Moore as -1
     *
     * If the pattern is empty, return an empty map.
     * You may assume that the passed in pattern will not be null.
     *
     * @param pattern A pattern you are building last table for.
     * @return A Map with keys of all of the characters in the pattern mapping
     *         to their last occurrence in the pattern.
     */
    public static Map<Character, Integer> buildLastTable(CharSequence pattern) {
        // WRITE YOUR CODE HERE (DO NOT MODIFY METHOD HEADER)!
        Map<Character, Integer> lastTable = new HashMap<>();
        int m = pattern.length();
        for (int i = 0; i < m; i++) {
            lastTable.put(pattern.charAt(i), i);
        }
        return lastTable;
    }

    public static List<Integer> knuthMorrisPratt(CharSequence pattern, CharSequence text, CharacterComparator comp) {
        List<Integer> matches = new ArrayList<>();
        List<Integer> failureTable = buildFailureTable(pattern, comp);

        int j = 0, i = 0;
        int m = pattern.length();
        int n = text.length();

        while (i <= (n - m)) {
            while (j < m && comp.compare(pattern.charAt(j), text.charAt(j+i)) == 0) {
                j++;
            }
            if (j == 0) {
                i++;
            } else {
                if (j == m) {
                    matches.add(i);
                }
                int shift = failureTable.get(j - 1);
                i = i + j - shift;
                j = shift;
            }
        }

        return matches;
    }

    public static List<Integer> buildFailureTable(CharSequence pattern, CharacterComparator comp) {
        int i = 0, j = 1;
        int m = pattern.length();
        Integer[] failureTable = new Integer[m];
        failureTable[0] = 0;
        while (j < m) {
            if (comp.compare(pattern.charAt(i), pattern.charAt(j)) == 0) {
                failureTable[j] = i + 1;
                i++;
                j++;
            } else if (i == 0) {
                failureTable[j] = 0;
                j++;
            } else {
                i = failureTable[i - 1];
            }
        }
        return Arrays.asList(failureTable);
    }
    
    public static List<Integer> rabinKarp(CharSequence pattern, CharSequence text, CharacterComparator comp) {
        List<Integer> matches = new ArrayList<>();
        int i = 0;
        int m = pattern.length();
        int n = text.length();

        int base = 199;
        int patternHash = baseHash(pattern, base);
        int textHash = baseHash(text.subSequence(0, m), base);

        while (i <= n - m) {
            if (patternHash == textHash) {
                int j = 0;
                while (j < m && comp.compare(pattern.charAt(j), text.charAt(i + j)) == 0) {
                    j++;
                }
                if (j == m) {
                    matches.add(i);
                }
            }
            if (i < n - m) {
                textHash = updateHash(textHash, text.charAt(i), text.charAt(i+m), m, base);
            }
            i++;
        }

        return matches;
    }

    public static int baseHash(CharSequence pattern, int base) {
        int m = pattern.length();
        int hash = 0;
        for (int i = 0; i < m; i++) {
            hash = hash + (int) pattern.charAt(i) * (int) Math.pow(base, m - i - 1);
        }
        return hash;
    }

    public static int updateHash(int oldHash, Character oldChar, Character newChar, int m, int base) {
        int hash = (oldHash - (int) oldChar * (int) Math.pow(base, m - 1)) * base + (int) newChar;
        return hash;
    }
}