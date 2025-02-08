# Dijkstra's Algorithm
Solves the shortest paths problem in a graph.

There are variations to **shortest paths** based on:
1. Are there negative edge weights present in the graph?
2. Are we trying to find the shortest path from a single vertex to another known vertex? (Single source, single 
destination)
3. Are we trying to find the shortest path from a single vertex to all other reachable vertices? (Single source)
4. Do we want the shortest path for all pairs of vertices? (All pairs)

The problem is in the context of a weighted graph. For an unweighted graph, BFS solves the problem. Dijkstra's 
solves the problem for non-negative weights with a single source. It can be modified to work for a single destination,
 or all other vertices.

## Operation
* Operates on weighted graphs
* Assumes distance to all vertices is infinite
* Enters through a given source vertex
    * As it moves through the graph choosing the shortest path each time
    * Updates the cumulative distance
    * Ends when it reaches the last vertedx

## Requirements
* Priority Queue of vertices with cumulative distances
* Next vertex represents shortest path found
* Visited Set
* Map
    * Shorted distances are stored in a distance map

### Main Ideas for Search Algorithms
* Visited Set
* Queue/stack of vertices
* Next vertex represents visit order

## The Algorithm
The shortest path between a pair of vertices must be composed of the shortest path to a neighbor plus the incident 
edge.
```
initialize VisitedSet, VS
initialize DistanceMap, DM
initialize PriorityQueue, PQ
for all v in G, initialize distance of v to inf
PQ.enqueue((s, 0)) // source vertex
while PQ !empty and VS !full
    (u, d) = PQ.dequeue()
    if u is not visited in VS
        mark u as visited in VS
        update DM for u with new shorted path d
        for all (w, d2) adjacent to u and not visited in VS
            PQ.enqueue((w, d + d2))
```

## Efficiency
Heavily dependent on the data structures used and the design of the implementation. Assume a HashSet and HashMap
are used, as well as a binary heap for the PQ.

* The PQ could contain up to $O(|E|)$ entries
    * Thus, $O(|E|)$ potential removals, with worst case of $O(\log(|E|))$

Therefore, given $O(1)$ ops for the set and map, overall efficiency for this design is $O(|E|\log(|E|))$

Can get to $O(|E|\log(|V|))$ by updating the priorities directly

## Optimizations
1. Decouple the visited set from updates to the distance map.
    * Instead of only updating the distance map when we visit a vertex, we can update the distance map as we find new 
    paths and add them to the priority queue.
    * Then, can query the ditance map as a criteria for adding to the PQ and reduce the number of paths we add
    * Can then do away with the visited set if desired and reduce space by a constant factor
2. Make the priority queue update entries with a different priority, then will never exceed $O(|V|)$ size for the PQ

## Negative Edge Weights and Cycles
The algorithm can work for *some* graphs with negative edge weights. You need to account for negative cycles that make
the weighted path shorter and shorter, as well as cases of visiting a vertex before a negative edge weight can find 
a shorter path.

Two algorithms account for this:
1. Bellman-Ford algorithm for single source shortest path
2. Floyd-Warshall for all-pairs shortest path

However, these algorithms are less efficient due to the need to detect negative cycles 

## Food for Thought: Heuristics and Search Algorithms
Dijkstra's laid the ground work for other algorithms like **uniform cost search (UCS)** and **A\***

**Heuristics** are used to provide information to help improve the efficiency of the algorithm.