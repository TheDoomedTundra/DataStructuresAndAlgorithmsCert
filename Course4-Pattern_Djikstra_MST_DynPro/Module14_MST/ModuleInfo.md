# Minimum Spanning Trees (MSTs)
An MST is a tree subgraph of undirected graphs that has a minimum cost while connecting all vertices. That is, the fewest and shortest edges needed to connect all interesting vertices.

Since the point is to connected all vertices, if a graph is not connected, we get a **minimum spanning forest (MSF)** instead.

* **Tree**: An acyclic, connected, undirected graph
* **Spanning Tree**: A tree that connected every vertex in the graph
* **Minimum Spanning Tree**: A spanning tree with the smallest total edge weight
* **Subgraph**: A subsgraph whose set of vertices and edges are all subsets of the graph

Key Observations:
* **Graph Cut**: A graph cut takes a subset of the vertices and all edges connecting them -> visualization tool
* Any MST must include the minimume edge connecting the two subgraphs left by any graph cut

## Prim's
### Background
* Greedy algorithm, optimizes the problem of finding a MST
* Focuses on connected graphs, as in general MSTs do not exists for unconnected graphs
* Builds the MST outwards from a single component, one vertex at a time
* Similar to Dijkstra's as it looks for shortest path

### Main Ideas
* Visited set
* PriorityQueue of edges with shortest distance
* Shortest  edge from the PR represents shortest path found in new graph cut
* Edges of minimum weight that are traversed are stpred

**Cut Property**: States that **any** MST **must** include the minimum cost edge over **any** graph cut
* The visted set is essentially a graph cut between visited and unvisited vertices, where edges in the PQ (or frontier) are the edges cut by the graph cut

### Requirements
1. Priority Queue for edges
2. Visited Set for vertices
3. MST Edge Set
4. Source Vertex

### Algorithm
```
initialize VS, MST, PQ
for each edge(s, v) in G, PQ.enqueue(edge(s, v))
mark s as visited
while !PQ.isEmpty and !VS.full
    edge(u,w) = PQ.dequeue()
    if w not in VS
        mark w in VS
        add edge(u,w) to MST
        for each edge(w, x) where x not visited
            PQ.enqueue(edge(w, x))
```

### Time Complexity
Due to the algorithmic similarity, the same as Dijkstra's

## The Greedy Paradigm
**Greedy Algorithm**: Decisions are made by considering local options and selecting the option that is locally optimal.
* Often simple, for example Dijkstra's simply looking at optimal distance, and Prim's looking at optimal edge

### Traveling Salesman: Greed can be Bad
* Local optimality is not always a good tihng.
    * Optimal solutions from greedy algorithms concept is **matroids**

**Traveling Salesman Problem (TSP)**
A notoriously difficult graph problem in CS where greedy algorithms do not give the optimal solution.

The problem: Suppose we have a graph of cities (vertices) and distances between them (weighted edges). There is a salesman that needs to visit each city once for sales and then return home. What route has the shortest distance?

Giving local optimums does not account for global optimum.

### Benefits of Greedy Algorithms
1. Often simple concepts that can be built upon for more complex strategies
2. Usually somewhat efficienct with polynomial time
3. Often give good approximations in practice
    * For example, while it will likely not get the best solution to TSP, the solution will be comparable despite being a linear time algorithm

## Kruskal's
### High Level Introcution
Simple greedy algorithm for comuting the MST of an undirected, connected graph.
* If unconnected, it will discover this and compute a MSF instead

The algorithm:
1. Add every edge of the graph into the PQ
2. While the PQ is not empty AND the MST size is not reached, dequeue an edge from the PQ. 
    * If the dequeued edge **does not form a cycle**, add it to the MST.

#### Motivation: The Cycle Property
Prim's is motivated by the cut property, Kruskal's is motivated by the cut property.

**Cycle Property of MSTs**: If we consider *any* cycle in the graph and look at the edge of maximum weight in the cycle, then that edge will not be contained in *any* MST. ---> slightly more difficult with duplicate edge weights, this concerns unique edge weights.
    * When dequeueing an edge, the previously dequeued edges would be smaller, and this edge gets excluded

#### Motivation for Disjoint Sets: The Visited Set Fails
Visited sets rely on being built out from a single source, which Kruskal's does not do. As such, the edges in the priority queue can be connected vertices that are already visited. In this case the visited set fails, but Kruskal's still needs to identify the smallest edges which do not form a cycle.

### The Disjoint Set (Union-Find) Data Structure
The MST cycle problem can be generalized to the problem of partitioning a universe set, and efficiently updating the partitions as we merge the subsets.
* **Partition**: A collection of subsets that are disjoint (no overlapping data).

In Kruskal's, the universe set is $V$, and the initial partition is each vertex in its own subset. As edges are added to the MST, vertices get connected, in effect forming a *union* of clusters in the partition.

#### A Tree Based Solution
What this data structure needs to accomplish:
1. Efficiently detect if two vertices are connected to each other
2. Merge/union connected components together efficiently so that all vertices belong to the same component, and have the same membership.

For each connected component, selected a **representative vertex**.
* To check if two vertices are in the same component, check if the representatives are the same.
    * Retrieving the rep is called `find`.
        * To implement, follow ancestors until getting to the root. To get efficient access to where the vertex is in the disjoint set forest we use a HashMap from vertex in the graph to nodes in the forest
    * Merging two clusters is called `union`.
        * Make one root the child of the other root

In the worst casem both of these operations are $O(|V|)$ when the tree degrades. An optimization for this is **path compression**.

#### Path Compression and Rank
Path compression is an optimization of `find`, which is a subroutine of `union`.
* As we unravel the recursive stack, we point the node at the root so future operations are $O(1)$.
* Other strategies like **path splitting** and **path halving** can do away with recursion

**Rank** of a node is similar to height, and is used to optimize `union` by having the higher rank node be the new root for the lower rank node.
* If ranks are equal, we update the rank of the new top node to be its rank + 1

### Efficiency of Kruskal's
The efficiency uses the inverse Ackermann function, which is extremely slow growing such that the number of atoms in the universe is less than or equal to 5 passed into it.

`find` and `union` are both $O(\alpha(|V|)) \approx O(1)$ amortized cost.

1. Adding all edges to the PQ  (using buildheap) is $O(|E|)$
2. If we end up dequeueing evert edge, we get $O(|E|\log(|E|))$ for dequeues

The efficiency is thus $O(|E|\log(|E|))$, or when simple it is $O(|E|\log(|V|))$ since $|E| = O(|V|^2)$

### Algorithm
```
initialize DS with all vertices inn G
initialize MST
initialize PQ with all edges in G
while PQ not empty and MST has < n - 1 edges
    edge(u,v) = PQ.dequeue()
    if u and v not in the same cluster
        add edge(u, v) to MST
        union clusters
```

## Prim's vs Kruskal's
Time complexity:
1. Prim's is $O((|V| + |E|)\log(|V|))$
    * If dense, $O(|E|\log(|V|))$
    * If sparse, $O(|V|\log(|V|))$
2. Kruskal's is $O(|E|\log(|E|))$
    * If dense, $O(|E|\log(|E|))$
    * If sparse, $O(|E|\log(|E|))$
3. The same when dense and simple, Prim's is better for dense non-simple. Prim's is generally better when dense so we don't dequeue so many edges
4. If sparse, Kruskal's will tend to perform better, but Prim's will perform better if disconnected
