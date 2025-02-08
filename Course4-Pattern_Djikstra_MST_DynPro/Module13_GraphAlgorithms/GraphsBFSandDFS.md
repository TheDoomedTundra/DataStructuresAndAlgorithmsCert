# Graphs
**Graph**: A non-linear data structure, which consists of a set of vertices or nodes connected by a collection of edges or arcs where the edges may be directed or undirected.
* Represent relationships
* A very diverse, abstract data type with many computing applications

## Basic Terminology
* Graph: $G = (V, E)$
    * A tuplem of two sets, $V$ for vertices and $E$ for edges
* $Order(G) = |V|$
* $Size(G) = |E|$
* Degree of a Vertex: $deg(v)$ -> number of edges incident or directly connected to that vertex
* Edge: $e = (u, v)$, where $u$, $v$ are vertices
    * Vertices are adjacent when connected by a single edge
    * An edge connect to a vertex is incident to that vertex
* Directed Graph (Digraph): A graph whose edges are all directed (can only be followed in one direction)
    * Max number of edges: $\frac{n(n-1)}{2}$
* Undirected Graph: A graph where all edges are undirected
    * Max number of edges: $n(n-1)$
* Path: A set of edges that connect a pair of edges
    * Length of the path: The number of edges traversed
    * Simple Path: Does not repeat vertices
    * Trail: A non-simple path
    * Walk: A trail where edges can repeat
* Weight of an Edge: Cross of traversing a given edge (for example, if the edge is a distance, has a cost to an op)
    * Graphs can be weighted or unweighted
* Dense Graph: Number of edges close to the max, $\Omega(|V|^2)$
* Sparse Graph: Minimal number of edges, $O(|V|)$
* Disconnected Graph: If there are two nodes for which there is no path in the graph
    * If a graph just has one vertex, it is connected even though there are no "paths"
* Connected Graph: For every pair of vertices, there exists a path between the two. \
    * $\Omega(|V|)$ at minimum
    * Weakly-connected Graph: When a digraph is connected in the undirected sense
    * Strongly-connected Graph: If the graph is connected in a sense that every pair of vertices has a path between them.
* Self-loop: Edge with the same origin and destination vertex
* Parallel Edge: Edges connecting the same vertices with the same direction
* Simple Graph: A graph without self-loops or parallel edges, usually what we talk about when saying "graph" 
    * $O(|V|^2)$ without self-loops or parallel edges
    * Multigraph: A graph that specifically allows self-loops and parallel edges
* Cyclic Graph: A cycle is when vertices are repeated in a path
    * Cycle: A path where the first and last vertices are adjacent
    * Circuit: A trail where the first and last vertices are adjacent
* Acyclic Graph: A graph with no cycles
    * When an acyclic graph is connected, it is a tree.

## Common Graph Families
* Complete Graph (clique): Every edge is present in an undirected graph
    * Tournament: Completed graph with directions
* Cycle Graph
* Tree: Formally, a treee with the minimum number of edges, for $n$ vertices we get $m = n - 1$
* Forest: Connected acyclic graph
* Petersen Graph

## Graph ADT
Methods:
* `vertices()` returns iteration of all vertices
* `edges()` returns iteration of all edges
* `numVertices()`
* `numEdges()`

Vertex Procedures:
* `endVertices(e)` return endpoint vertices
* `getEdge(u, v)` returns an edge from vertex $u$ to vertex $v$ or null
* `numEdges(v)` returns iteration of all outgoing edges to $v$
* `opposite(v, e)` returns the other vertex $u$ that is incident to $v$ for edge $e$

Graph Mutator Procedures:
* `insertVertex(d)` create and return a new vertex storing $d$
* `insertEdge(u, v, e)` create and return new Edge $e$ from $u$ to $v$
* `removeVertex(v)` remove vertex and all incident edges
* `removeEdge(e)` remove edge
* These require auxiliary data structures, which depend on the procuedure being used

### Graph Representation Considerations
* Information stored efficiently
* Adding/removing vertices
* Adding/removing edges
* How will the information be stored?
* Adjacency Matrix, Adjacency List, Edge List 

#### Adjacency Matrix
A $V x V$ matrix
* Row A, Column B is edge from A to B
    * Uses a delimiter when no edge
* Space Complexity is $O(|V|^2)$
* Works well when we have a very dense graph
* Not efficient for adding a vertex, we need to create a new matrix

#### Adjacency List
* Map from each vertex to incident edges
* Isolated vertices are an empty list
```
A: e, g
B: e, f
...
```
* Space Complexity: $O(|V| + |E|)$
* Works well when the graph is sparse

#### Edge List
* Stores only the edges
* No way to represent isolated vertices
* Space Complexity: $O(|E|)$
* Works well when getting all edges at once, but can't represent isolated vertices
```
E = {e, f, g, h}
```

### Vertex Class
* Needs equals, get data, toString, etc
```
public class Vertex<T> {
    private T data;
    public Vertex(T data) {
        if (data == null) {
            throw new IllegalArgumentException
        }
        this.data = data
    }
    
    public int hashCode() {
        return data.hashCode();
    }
}
```

### Edge Class
* Needs compareTo, or it forces the worst case by not constraining it
* Needs getters, toString etc.
```
public class Edge<T> implements Comparable<Edge<? super T>> {
    private Vertex<T> u;
    private Vertex<T> v;
    private int weight;
    public Edge(Vertex<T> u, Vertex<T> v, int weight) {
        throwIfNull();
        this.u = u;
        this.v = v;
        this.weight = weight;
    }
}
```


### Graph Class
* Needs getters, toString, etc.
```
public class Graph<T> implements Comparable<Edge<? super T>> {
    private Set<Vertex<T>> vertices;
    private Set<Edge<T>> edges;
    private Map<Vertex<T>, List<Edge<T>>> adjList; // one option
    public Graph(Set<Vertex<T>> vertices, Set<Edge<T>> edges) {
        nullCheck();
        this.vertices = new HashSet<>(vertices);
        this.edges = new HashSet<>(edges);
        adjList = new HashMap();

        // Add V and E to adjList
    }
}
```

## Graph Search Algorithms
### Depth-First Search (DFS)
Explores a branch as far down as it possibly can before moving back up and exploring other branches.
* Highly suited for recursion and stacks
* Takes one path as deep as possible before back tracking to look for other paths until all vertices have been reached

Two Implementations
1. Non-recursive (similar to BFS)
    * Requires:
        - A stack
        - Visited set
        - Starting Vertex

```
initialize VisitedSet, VS
initialize Stack, S
S.push(u)
while S is not empty
    v = S.pop()
    if v not visited in VS
        mark v as visited in VS
        for all w adjacent to v
            if w is not visited in VS
                S.push(w)
```
2. Recursive

```
mark u as visited in VS
for all w adjacent to u
    if w is not visited in VS
        DFS(G, w)
```

#### Applications of DFS
Time complexity:
* Worst Case: $O(|V| + |E|) = O(n + m)$ due to the graph size

1. DFS can be used to detect if a graph is connected. If connected, it can find if there exists a path from one vertex
to another
2. DFS can be used for cycle detection. Mixed with the first application, it can be used  to detect if the graph is a
tree
3. Can be used to obtain a **spanning tree**, a subgraph for which all vertices are connected with the smallest number
of edges
4. Can be used to detect if a graph is **bipartite**, that is if we can partition the vertices into two sets where 
there are no edges between vertices in the same set. Equivalent to a graph with an odd cycle.
5. Can be used to simulate decisions for AI, particularly in games with structure.
6. Can be used to do topological sorted on DAGs. That is, an ordering of the vertices in sorted order based on the 
edge orientations.
7. Can be used to obtain a meta-graph of strongly connected components in a digraph. If we find components in a weakly-connected
graph that are strongly connected, and treat each of those components as a single vertex of a new meta-graph, we get
an all together new graph.

### Breadth-First Search (BFS)
Similar to level-order traversal in a BST. First visits all vertices that are one edge away from the starting vertex, then two edges, etc. Requires:
* Queue
* Visited Set
* Starting Vertex

```
initialize VS
initialize Queue, Q
Q.enqueue(s)
while Q not empty
    v = Q.dequeue
    mark v as visited in VS
    for all w adjacent to v
        if w is not visited in VS
        Q.enqueue(w)
```

Worst case time linear time complexity again of $O(|V| + |E|)$

### Things to Consider Choosing DFS vs BFS
1. Do we have any prior knowledge of what vertex that is being searched for and its location in the graph relative to the starting point? If it is known that the vertex is relatively close by compared to the size of the graph, then we may prefer to use BFS.
2. How "deep" is the graph? For example, if we are doing a traversal of a tree from some root node and the depth of the tree is large, then we may want to use BFS to avoid staying on a single path for a long time. Or we could impose some sort of depth limit on DFS traversal if the graph is infinitely large.
3. How "wide" is the graph / how large is the branch factor? If each node has many neighbors, then a BFS will quickly blow up in space usage since we need to store all of these neighbors in a queue. This is particularly important in areas like AI where the graph we're considering is essentially infinitely large because our agent's decision space is very large.
4. Do we have any heuristics or any information about the structure of the graph we can take advantage of? The heuristics we have, may tell us where to search, in which case we may switch between the two strategies depending on the graph's structure.

## Time Complexities
Assumptions:
1. Hashing data structures with $O(1)$ operations
2. Graph uses an adjacency list
3. Priority Queue has a binary heap with `decreaseKey()`

Considerations
1. Set $|V|$
2. Set $|E|$
3. Backing structures
4. Connected or disconnected
5. Directed or undirected

Main ideas for traversal algorithms:
1. Visited Set
2. Queue/stack of vertices
3. Starting vertex

Main idea for BFS;
1. The goal is to visit all the vertices in a graph reachable through edges from some indicated start vertex
2. For this, only considering connected, undirected graphs, but does not have to be either
3. Implemented with nested loops, only looking at unvisited adjacent loops

**Time Complexity**: $O(|V| + |E|)$
* $O(|V|)$: Visit each vertex
* $O(|E|)$: Visit each edge

Main idea for DFS (recursive):
1. The goal is to visit all the vertices in a graph reachable through edges from some indicated start vertex
2. For this, only considering connected, undirected graphs, but does not have to be either
3. Looks at next adjacent unvisited vertex until return from the last unvisited vertex

**Time Complexity**: $O(|V| + |E|)$
* $O(|V|)$: Visit each vertex
* $O(|E|)$: Visit each edge

Main idea for Shortest Path Algorithms:
1. Priority Queue to pull shortest path
2. Visited set
3. Map of graph
4. Starting vertex

Main ideas for Dijkstra's:
1. Visited set
2. Priority queue of cumulative distances
3. Next vertex represents shortest path found
4. Shortest distances stored in distance map
5. Computes the shortest path from a source vertex, and then distances to all other reachable vertices in a graph

**Time Complexity**: $O((|V| + |E|)\log(|V|))$
* $O(|V|\log(|V|))$: Visit each vertex, perform $|V|$ calls to remove from PQ which is log operation
* $O(|E|\log(|V|))$: Conder each path, perform $|E|$ calls to add to PQ