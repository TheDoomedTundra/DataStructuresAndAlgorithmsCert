# Dynamic Programming
* Similar to recursion, solves a problem by solving smaller, overlapping repeated subproblems.
    * Differes by storing solutions to subproblems to avoid recomputing them, called **memoization**.
* Components:
    * Identify the optimal structure of subproblems
    * Establish an order of solving
    * Solve subproblems and combine solutions to solve the larger problem
    * Commonly, recursive solution found first and is then made iterative to reduce overhead with memoization.
* **Overlapping Subproblems**: When subproblems share dependencies, resulting in repeated calculations in the recursive solution. For example, calculating Fibonacci recursively vs iteratively
* **Top-down DP**: Having a memoization array to check during recursion
* **Bottom-up DP**: Computing solutions to subproblems in increasing order of complexity
* **Optimal Substructure**: Compute optimal solution to subproblem to compute optimal solution of the larger problem
* Con: Increased space from storing answers
* Identifying: If you can make a DAG of solutions, where an edge says a solution requires another solution, then DP can be of use

## Longest Common Subsequence (LCS)
Solved with dynamic programming in $O(mn)$ time, where $m$ and $n$ are the two strings.

**Subsequence**: Sequence of characters that occur in a string in the same relative order, but not necessarily
contiguous like a substring.

### How do we find LCS?
* Brute Force: Compare every subsequence of one string to every subsequence opf another string. -> $2^n$ subsequences
* Dynamic Programming: Store the length of the LCS for substrings of each string and try to build off the LCS by adding
more and more characters to the end.

### Algorithm
* Create an `n+1` by `m+1` array (`L`)
* Fill row and column 0 with 0s -> the empty column which correspons to ""
* For each cell `L[i][j]`, where i is 1 to n and j is 1 to m, store the length of the longest common subsequence
of the substrings of the two strings we are looking at, up to the index of that cell:
    * If characters equal in both strings, set the value to be 1 + the diagonal value (extension of previous LCS of 
    the diagnoal calc by one character, and adding the same character to the end of both previous substrings).
    ```
    L[i][j] = 1 + L[i - 1][j - 1] // above left diagnoal value
    ```
    * Else, set to max of value in cells above and to the left
    ``` 
    L[i][j] = max(L[i - 1][j], L[i][j - 1])
    ```
    * If above and left are the same, take from the left.
* When the array is filled, the bottom right corner is the LCS
    * To get the value, we trace back up the array. Any value that came from a diagonal calculation (incremented along the diagonal), that is a member of the LCS and should be recorded.

### Multiple LCS
Equal values can also come from above, not just the diagonal, so we may need to backtrack up before adding along the
diagonal.

### The Knapsack Problem: A DP Approach
Given items and their values, find the maximum value we can obtain. Similar solution structure where we build out a 
m x n table and increase on the diagonals.

## Bellman-Ford Algorithm
A dynamic programming algorithm that solves the single source shortest path algorithm with negative edge weights.

Principle: Every shortest path between two vertices will use at most $|V| - 1$ edges.

```
def bellmanFord(source):
init dist, map from vertices to distances
for each vertex u
    dist[v] = inf
dist[source] = 0

for i = 1 : |V| - 1
    for each directed edge (u, v, w):
        if dist[v] > dist[u] + w
            dist[v] = dist[u] + w
return dist
```

Goes through all E edges V - 1 times, updating the shortest path found up to that point in the algorithm. The subproblem
is defined by the destination and the max edge length of the path.

### Efficiency
* Time Complexity: $O(|V|*|E|)$
* Space Complexity: $O(|V|)$ for the distance map

### Negative Cycles
If we run it for |V| iterations instead, and the distance map is updated, it means there are negative cycles present
as there is a shortest path that relies on repeating a path to use |V| edges

## Floyd-Warshall Algorithm
Dynamic programming algorithm that solves the all-pairs shortest paths problem and works with negative edge weights.
This is an expensive problem, which takes $\Omega(|V|^2)$ just to write an answer.

### The Algorithm
Assumes the graph is representedn by an adjacency matrix.

Principle: Any shortest path can be written as the sum of two shortest paths between some intermediate vertex. If not,
then we could improve the shortest path by splitting that path along the intermediate vertex and use the shorter one. If
the shortest path is a direct edge $(u, v, w)$, we can decompose it as the shortest path from u to u of length 0, then the shortest path from u to v of length w. 

```
def floydWarshall(adjMatrix):
init dist, a |V| x |V| matrix initialized to take the values of adjMatrix
for each vertex vj:
    for each vertex vi:
        for each vertex vk:
            if dist[vi][vk] > dist[vi][vj] + dist[vj][vk]
                dist[vi][vk] = dist[vi][vj] + dist[vj][vk] 
```

It goes through all pairs of vertices |V| times, and there are $O(|V|^2)$ pairs, getting a $O(|V|^3)$ time compleixty.
* Space compleixty is $O(|V|^2)$ for the matrix

### Negative Cycles
Detected by any changing or becoming negative of the values on the diagonal of the distance matrix