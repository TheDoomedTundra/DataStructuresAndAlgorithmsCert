package Module12_PatternMatching;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import static org.junit.Assert.*;

public class PatternMatchingTest {
    
    @Test
    public void testBoyerMooreLastTable() {
        CharSequence pattern = "ab";
        Map<Character, Integer> act = PatternMatching.buildLastTable(pattern);
        Map<Character, Integer> exp = Map.of(
            'a', 0,
            'b', 1
        );
        assertEquals(act, exp);

        pattern = "bba";
        act = PatternMatching.buildLastTable(pattern);
        exp = Map.of(
            'b', 1,
            'a', 2
        );
        assertEquals(act, exp);
    }

    public static void bmHelper(CharSequence pattern, CharSequence text, List<Integer> exp, int comparisons) {
        CharacterComparator comparator = new CharacterComparator();

        List<Integer> act = PatternMatching.boyerMoore(pattern, text, comparator);
        assertEquals(act, exp);
        assertEquals(comparator.getComparisonCount(), comparisons);
    }

    @Test
    public void testBoyerMoore() {
        bmHelper("aaaa","aaaaaaaaaaaaa", Arrays.asList(0,1,2,3,4,5,6,7,8,9), 40);
        bmHelper("aaab","aaaaaaaaaaaaa", Arrays.asList(), 10);
        bmHelper("baaa","aaaaaaaaaaaaa", Arrays.asList(), 40);
        bmHelper("aaaa","aaabaaabaaaba", Arrays.asList(), 3);
        bmHelper("aaab","aaabaaabaaaba", Arrays.asList(0, 4, 8), 19);
        bmHelper("baaa","aaabaaabaaaba", Arrays.asList(3, 7), 11);
        bmHelper("abab","abacabacababa", Arrays.asList(8), 7);
        bmHelper("lack","sphinxofblackquartz", Arrays.asList(9), 9);
    }

    public static void galilHelper(CharSequence pattern, CharSequence text, List<Integer> exp, int comparisons) {
        CharacterComparator comparator = new CharacterComparator();

        List<Integer> act = PatternMatching.boyerMooreGalil(pattern, text, comparator);
        assertEquals(act, exp);
        assertEquals(comparator.getComparisonCount(), comparisons);
    }

    @Test
    public void testBoyerMooreGalil() {
        galilHelper("caa","aabbabaabacaaaa", Arrays.asList(10), 10);
    }

    @Test
    public void testFailureTable() {
        CharSequence pattern = "caa";
        CharacterComparator comparator = new CharacterComparator();
        List<Integer> exp = Arrays.asList(0, 0, 0);
        List<Integer> act = PatternMatching.buildFailureTable(pattern, comparator);
        assertEquals(act, exp);
        assertEquals(comparator.getComparisonCount(), 2);

        pattern = "abab";
        comparator = new CharacterComparator();
        exp = Arrays.asList(0, 0, 1, 2);
        act = PatternMatching.buildFailureTable(pattern, comparator);
        assertEquals(act, exp);
        assertEquals(comparator.getComparisonCount(), 3);
    }

    public static void kmpHelper(CharSequence pattern, CharSequence text, List<Integer> exp, int comparisons) {
        CharacterComparator comparator = new CharacterComparator();

        List<Integer> act = PatternMatching.knuthMorrisPratt(pattern, text, comparator);
        assertEquals(act, exp);
        assertEquals(comparator.getComparisonCount(), comparisons);
    }

    @Test
    public void testKnuthMorrisPratt() {
        kmpHelper("aaaa","aaaaaaaaaaaaa", Arrays.asList(0,1,2,3,4,5,6,7,8,9), 16);
        kmpHelper("aaab","aaaaaaaaaaaaa", Arrays.asList(), 27);
        kmpHelper("baaa","aaaaaaaaaaaaa", Arrays.asList(), 13);
        kmpHelper("aaaa","aaabaaabaaaba", Arrays.asList(), 22);
        kmpHelper("aaab","aaabaaabaaaba", Arrays.asList(0, 4, 8), 17);
        kmpHelper("baaa","aaabaaabaaaba", Arrays.asList(3, 7), 14);
        kmpHelper("abab","abacabacababa", Arrays.asList(8), 19);
        kmpHelper("lack","sphinxofblackquartz", Arrays.asList(9), 19);
    }

    @Test
    public void testBaseHash() {
        CharSequence pattern = "a";
        int base = 199;
        int act = PatternMatching.baseHash(pattern, base);
        int exp = 97;
        assertEquals(act, exp);

        pattern = "abc";
        act = PatternMatching.baseHash(pattern, base);
        exp = (int) 'a' * base * base + (int) 'b' * base + (int) 'c';
        assertEquals(act, exp);
    }

    @Test
    public void testUpdateHash() {
        CharSequence pattern = "abc";
        int base = 199;
        int oldHash = PatternMatching.baseHash(pattern, base);
        
        int act = PatternMatching.updateHash(oldHash, 'a', 'd', 3, base);
        int exp = (int) 'b' * base * base + (int) 'c' * base + (int) 'd';
        assertEquals(act, exp);
    }

    public static void rabinKarpHelper(CharSequence pattern, CharSequence text, List<Integer> exp, int comparisons) {
        CharacterComparator comparator = new CharacterComparator();

        List<Integer> act = PatternMatching.rabinKarp(pattern, text, comparator);
        assertEquals(act, exp);
        assertEquals(comparator.getComparisonCount(), comparisons);
    }

    @Test
    public void testRabinKarp() {
        rabinKarpHelper("aaaa","aaaaaaaaaaaaa", Arrays.asList(0,1,2,3,4,5,6,7,8,9), 40);
        rabinKarpHelper("aaab","aaaaaaaaaaaaa", Arrays.asList(), 0);
        rabinKarpHelper("baaa","aaaaaaaaaaaaa", Arrays.asList(), 0);
        rabinKarpHelper("aaaa","aaabaaabaaaba", Arrays.asList(), 0);
        rabinKarpHelper("aaab","aaabaaabaaaba", Arrays.asList(0, 4, 8), 12);
        rabinKarpHelper("baaa","aaabaaabaaaba", Arrays.asList(3, 7), 8);
        rabinKarpHelper("abab","abacabacababa", Arrays.asList(8), 4);
        rabinKarpHelper("lack","sphinxofblackquartz", Arrays.asList(9), 4);
    }
}
