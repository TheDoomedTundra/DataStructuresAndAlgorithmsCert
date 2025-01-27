# Pattern Matching Algorithms
These opearate by finding a specified **pattern** on a given input **text** string. Aka **string searching algorithms**. There are two main variations:
1. **Single Occurrence**: Trying to detect if there is an occurrence
2. **All Occurrences**: Trying to find all occurrences of the pattern, even if they overlap.

## Introduction to Pattern Matching
Basic problem structure for pattern matching:

**Input**: A small string that is called the **pattern** and a longer string called the **text**. The pattern is of length *m*, and the text is of length *n*. May also receive the alphabet from which the characters are drawn of size *s*.
<br>**Output**: For **single occurrence**, output the location (text index) of any occurence of the pattern as a substring of the text. For **all occurrences**, output a list of all locations of occurrences. These patterns can overlap.

### The naive solution, the **brute force algorithm**
* Check every possible substring of the text for a match. Align the pattern and the text, and then increment by one character until you find a match.

```
for t = 0 : n - m
    i = 0
    while i <= m - 1
        if pattern[i] matches text[i + t]
            if i < m - 1
                continue
            else
                return index of match
        else
            break
```

#### Time Complexity
* Best Case: $O(m)$
* Best Case (no occurrence): $O(n)$ when on first character every tie
* Average Case: $O(mn)$
* Worst  Case: $O(mn)$

In general, average case is not as useful for pattern matching since for the most part we cannot assume we are uniformly sampling the input. The structure of syntax and alphabets makes there be a very diverse scope of possibilities, that requires more context to scope down the average case.

## Boyer-Moore (BM)
An algorithm that performs best when there are many characters in the text that do not occur in the pattern. 
**Bad Character Rule**: The algorithm recognizes when this occurs and skips the characters completely since they cannot be in the pattern.

The algorithm presented is simplified because it does not use the **good suffix rule**, which was shown not to be very helpful in terms of performance, while making the algorithm more complex.

It improves on brute force by preprocessing the pattern to create a **last occurrence table**, which allows for optimizing the shift when we hit a bad character.

### Last Occurrence Table
* Records the index of the last occurrence of the letter
* Stores the pair `<letter, index>` in a HashMap
* Letters not in the alphebet of the pattern are marked as `*`
    * Java has a `getOrDefault()` when a key is not in a map
* The algorithm:
```
m = pattern.length
last = HashMap<character, index>
for all i from 0 to m-1
    last.put(pattern[i], i)
end
return last
```

### Boyer-Moore Concept:
* Create the Last Occurrence Table to optimize shifts past mismatches
* Move right to left in the pattern
* If there is a match, continue comparing text and pattern
* If there is a mismatch, check to see if text character is in the LOT
    * If in the alphabet, shift to right to align the pattern with the text
    * If not in the alphabet, then shift past mismatch entirely.

Algorithm for single occurrence:
```
last = BoyerMooreLastTable(pattern)
i = 0;
while i <= text.length - pattern.length
    j = pattern.length - 1
    while j>=0 and text[i+j] = pattern[j]
        j--
    if j = -1
        return i // pattern found
        // If doing multi search, record and continue
    else // text and pattern do not match
        shift = last[text[i+j]]
        if shift < j
            i = i + j - shift
        else
            i = i + 1
return pattern not found
```

**3 Pattern Shift Cases**:
1. Pattern can be shifted to align the last occurrence
2. Shifting to align alst occurrence causes pattern to go backwards, so shift by one
3. Character is not in pattern, so shift past the character
### Time Complexity and Efficiency
Last Occurrence Table Creation:
* Best, Average, Worst: $O(m)$

**Efficiency**:
* Works best for large alphabets, increases the likelihood of shifting over bad matches
* Best Case (Single Occurrence): $O(m)$
    * In some cases $O(n/m + m)$ could be faster than $O(m)$
* Best Case (All Occurrences): $O(n/m + m)$
* Worst Case: $O(mn)$ -> degenerates to brute force

### Foor for Thought: BM Variations
* Good suffix improves no occurrence worst case to $O(m + n)$
    * Uses the matched suffix to further optimize the shift, as the bad character rule does not keep track of the data that has already been matched. This shifts to the first no-suffix occurence of the suffix in the pattern
* Boyer-Moore-Horspool
    * When found a mismatch, do not shift on it, but on the last occurence of the last character in the pattern that has been matched (the text window). In these, we omit the final character in the pattern's true last occurrence in the LOT. Average case of $O(m+n)$
* Galil Rule
    * Modification that makes the worst case $O(m + n)$

## Knuth-Morris-Pratt (KMP)
Relies on preprocessing the pattern to make strong inferences on how to shift 
based on what has already been encountered in the text while searching.

It is well suited for text and patterns that have similar alphabets and repetition of
characters throughout.

### The Failure Table
* Integer array of length `m`
* `f[i]` represents the length of the longest prefix that is also a proper suffix of
`pattern[0, ..., i]`

Vars:
* `p`: pattern
* `f`: failure table
* `i`: prefix index
* `j`: query index
* `m`: pattern length
```
f[0] = 0, i = 0;, j = 1;
while j < m
    if p[i] = p[j] // match, increment
        f[j] = i + 1
        i++; j++;
    else if p[i] != p[j] and i = 0 // no match, no prefix
        f[j] = 0
        j++;
    else // no match, built up a prefix, decrement size of the pair and try again
        i = f[i - 1] // since we store the length, will grab the right index
return f
```

#### Efficiency
(looking at the movement of j)
* Algorithm ends when `j` reaches end of table
* `j` never moves backwards
* If `j` moves forward each time its $O(m)$, so what if it doesn't?

* `j` doesn't move if and only if `i` moves backwards

(looking at movement of i)
* `i` moves backwards **at most** until it raches 0
* `i` can only move forward by 1

* `i` can only move backwards at most **m** times
* `j` stays in place at most **m** times

(overall)
* Construction is $O(m+m) \rightarrow O(m)$

### Food for Thought: The Galil Rule
Reduces the worst case of Boyer-Moore from $O(mn)$ to $O(m + n)$

How it works:
* works on the periodicity of a pattern
* A string is **periodic** if it is the prefix of a repeating string. For example, the
string *ababa* is a periodic prefix of *ababababab...*
* When a pattern is aperiodic, the worst case of BM is $O(m+n)$, so it works with periodic
strings.

The Rule:
1. Once we completely find a match, shift forward by `p`, the period of the periodic prefix.
    * Inspects at most `p` new characters
    * If mismatch, revert to normal algorithm
2. Compute the period using the failure table!

### KMP: The Algorithm
* Upon mismatch, query the pattern index in the Failure Table
* Realigning causes all characters before mismatch to still match
* Begin comparing from beginning of pattern, unlike BM which goes right to left

Vars:
* `p`: pattern
* `t`: text
* `f`: failure table
* `j`: pattern index
* `k`: text index
* `m`: pattern length
* `n`: text length
    j = pattern.length - 1
    while j>=0 and text[i+j] = pattern[j]
        j--
    if j = -1
        return i // pattern found
        // If doing multi search, record and continue
    else // text and pattern do not match
        shift = last[text[i+j]]
        if shift < j
            i = i + j - shift
        else
            i = i + 1
```
j = 0; k = 0
while k <= n - m
    while j < m && t[j+k] == p[j]
        j++
    if j == 0
        k++;
    else 
        if j == m
            // match found
        shift = f[j - 1]
        i = i + j - shift
        j = shift
```

### Time Complexity
* Worst Case: $O(m+n)$
    * Failure Table: $O(m)$
    * Main Algorithm: $O(n)$
* Best Case (All): $O(m + n)$
* Best Case (single occurrence): $O(m)$

## Rabin-Karp (RK)
Instead of improving the shift, it improves brute force by adding a "screening" step 
to decide whether or not to do a brute force comparison.

It uses the **Rabin Fingerprint rolling hash** to efficiently update the hash along
the test as the window moves, and compare the hash of the pattern and text

Side noteL: widely used in detecting plagiarism.

Steps:
1. Calculate the initial hash for the pattern, and first length-m substring of text
2. Compare text and pattern hash
    * If hashes match, compare character by character
    * If no match, don't compare
3. Update text hash to next substring and repeat

Hash Calc: $H(p) = H(p[0])*BASE^{m-1}+...+h(p[m-1])*BASE^0$

### Rolling Hash:
* Select a BASE number, usually a large prime number to eliminate hash collisions
    * Weights characters by their position
* Select a hash function `h(x)` which maps single character to integer (usually ASCII)
* Raise BASE to an exponent based on its index in the string to multiply by `h(x)` and
add the characters
* This is the decimal system if BASE = 10
* Start calculations from right to left to efficiently keep track of the power of BASE

Updating the hash:
* Substract the contribution of the old character from the hash, add the new character
    * $NewHash = (OldHash - h(OldChar)*BASE^{m-1})*BASE + h(newChar)$

    
```
i = 0
while i <= n-m
    if patternHash == textHash
        j = 0
        while (j < m and text[i+j] == pattern[j])
            j++
        if j == m
            // Match at if
            return i or record and i++
    if i < n-m
        roll textHash forward one
    i++
```

### Efficiency
* Hash is updated $n - m$ times, needs to be $O(1)$ to not have worst case of brute force
    * Efficiency heavily depends on this, no comparisons are made when there is a mismatch
* Worst Case: $O(mn)$
    * Average case is linear, but because there can be collisions the worst case degenerates
* Best Case (All): $O(m + n)$
    * Initial hash: $O(m)$
    * Searching: $O(n)$
* Best Case (single): $O(m)$
* Space: $O(m\log(b))$ to store the two hashes