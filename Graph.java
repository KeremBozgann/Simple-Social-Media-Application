import java.util.List;
import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Implementation of Vertex interface.
 * 
 * * @param <T>
 * data type of the label of the vertex
 * 
 * @author Kerem Bozgan kerembozgan
 * @version 2022-11-11
 */
class Graph<T> implements GraphInterface<T> {
    
  public static void main(String[] args) {
  Graph<Profile> grph = new Graph<Profile>();
  Profile p1 = new Profile("p1", "Hello I am p1."); 
  Profile p2 = new Profile("p2", "Hello I am p2."); 
  Profile p5 = new Profile("p5", "Hello I am p5.");
  Profile p10 = new Profile("p10", "Hello I am p10.");
  
  grph.addVertex(p1);
  grph.addVertex(p2);
  grph.addVertex(p5);
  System.out.println(grph.getNumberOfEdges());
  System.out.println(grph.getNumberOfVertices());
  grph.addEdge(p1, p2);
  System.out.println(grph.getNumberOfEdges());
  System.out.println(grph.addEdge(p1, p2));
  System.out.println(grph.getNumberOfEdges());
  grph.addEdge(p2,p5);
  System.out.println(grph.getNumberOfEdges());
  System.out.println(grph.addEdge(p1, p10));
  System.out.println(grph.getNumberOfEdges());
  System.out.println(grph.addEdge(p10, p2));
  System.out.println(grph.getNumberOfEdges());
  grph.addEdge(p1, p5);
  System.out.println(grph.getNumberOfEdges());
 
  grph.removeVertex(p1);
  System.out.println(grph.getNumberOfEdges());
  grph.removeVertex(p2);
  System.out.println(grph.getNumberOfEdges());
  System.out.println(grph.getNumberOfVertices());
  grph.removeVertex(p5);
  System.out.println(grph.getNumberOfVertices());
 
  grph.clear();
  System.out.println(grph.getBreadthFirstTraversal(p1));
  System.out.println(grph.getBreadthFirstTraversal(p10));
 
 
  Profile A = new Profile("A", "Hello I am A."); 
  Profile B = new Profile("B", "Hello I am B."); 
  Profile C = new Profile("C", "Hello I am C.");
  Profile D= new Profile("D", "Hello I am D.");
  Profile E= new Profile("E", "Hello I am E.");
  Profile F= new Profile("F", "Hello I am F.");
  Profile G= new Profile("G", "Hello I am G.");
  Profile H= new Profile("H", "Hello I am H.");
  Profile I= new Profile("I", "Hello I am I.");
  Profile unreach= new Profile("Unreachable", "Hello I am Unreachable.");
  
  grph.addVertex(A);
  grph.addVertex(B);
  grph.addVertex(C);
  grph.addVertex(D);
  grph.addVertex(E);
  grph.addVertex(F);
  grph.addVertex(G);
  grph.addVertex(H);
  grph.addVertex(I);
  grph.addVertex(unreach);
 
 
  grph.addEdge(A, B);
  grph.addEdge(A, D);
  grph.addEdge(A, E);
  grph.addEdge(B, C);
  grph.addEdge(B, E);
  grph.addEdge(C, F);
  grph.addEdge(D, G);
  grph.addEdge(E, H);
  grph.addEdge(E, F);
  grph.addEdge(F, I);
  grph.addEdge(F, H);
  grph.addEdge(G, H);
  grph.addEdge(H, I);
 
  System.out.println(grph.getNumberOfEdges());
  System.out.println(grph.getNumberOfVertices());
 
 
  Stack<Profile> emptyStack = new Stack<Profile>();
 
  int dist = grph.getShortestPath(A, I, emptyStack);
  System.out.println(dist);
  System.out.println(emptyStack.toString());
 
  Queue<Profile> traversalOrder =
  grph.getBreadthFirstTraversal(A);
  System.out.println(traversalOrder.toString());
  }
    
    /** A dictionary of key (Vertex label), value (Vertex) pair */
    protected HashMap<T, Vertex<T>> vertices;
    /** Number of edges */
    private int numEdges;

    /**
     * Initializes the graph with an empty graph
     * structure.
     */
    Graph() {
        // instantiate hashMap with a prime number size
        vertices = new HashMap<T, Vertex<T>>(101);
        numEdges = 0;
    }


    /**
     * Adds a given vertex to the graph.
     * If vertexLabel is null, it returns false.
     * 
     * @param vertexLabel
     *            label of the vertex
     * @return true if operation is successful
     */
    public boolean addVertex(T vertexLabel) {
        if (!vertices.containsKey(vertexLabel)) {
            Vertex<T> newVertex = new Vertex<T>(vertexLabel);
            vertices.put(vertexLabel, newVertex);
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Removes given vertex from the Graph. Disconnects any
     * edge that connects another vertex to the removed vertex.
     * 
     * @param vertexLabel
     *            label of the vertex to remove
     * @return vertex that is removed
     */
    public VertexInterface<T> removeVertex(T vertexLabel) {
        if (vertices.containsKey(vertexLabel)) {
            VertexInterface<T> vertexRemoved = vertices.remove(vertexLabel);
            Iterator<VertexInterface<T>> itr = vertexRemoved
                .getNeighborIterator();

            VertexInterface<T> neighbor;

            while (itr.hasNext()) {
                neighbor = itr.next();
                boolean res = neighbor.disconnect(vertexRemoved);
                if (res) {
                    numEdges--;
                }
            }
            return vertexRemoved;
        }
        else {
            return null;
        }
    }


    /**
     * Adds a weighted
     * edge between two given distinct vertices that are currently in this
     * graph. "Begin" vertex and "end" vertex
     * cannot be the same. Also,
     * the desired edge must not already be in the graph.
     * Since graph is undirected, adds an edge in the edge lists
     * of both begin and end vertices.
     * 
     * @param begin
     *            beginning vertex
     * @param end
     *            end vertex
     * @param edgeWeight
     *            weight of the edge to be added
     * @return true if added
     */
    public boolean addEdge(T begin, T end, double edgeWeight) {
        // check if given vertices exists in the graph:
        if (!vertices.containsKey(begin) || !vertices.containsKey(end)) {
            return false;
        }
        VertexInterface<T> originVertex = vertices.get(begin);
        VertexInterface<T> targetVertex = vertices.get(end);

        boolean res1 = originVertex.connect(targetVertex, edgeWeight);
        boolean res2 = targetVertex.connect(originVertex, edgeWeight);

        if (res1 && res2) {
            numEdges++;
            return true;
        }
        else {
            return false;
        }

    }


    /**
     * Adds an unweighted edge between two
     * given distinct vertices that are currently in this graph.
     * Since graph is undirected, adds an edge in the edge lists
     * of both begin and end vertices.
     * 
     * @param begin
     *            beginning vertex
     * @param end
     *            end vertex
     * @return true if successfull
     */
    public boolean addEdge(T begin, T end) {
        // check if given vertices exists in the graph:
        if (!vertices.containsKey(begin) || !vertices.containsKey(end)) {
            return false;
        }

        VertexInterface<T> originVertex = vertices.get(begin);
        VertexInterface<T> targetVertex = vertices.get(end);

        boolean res1 = originVertex.connect(targetVertex);

        boolean res2 = targetVertex.connect(originVertex);

        if (res1 && res2) {
            numEdges++;
            return true;
        }
        else {
            return false;
        }

    }


    /**
     * Removes a
     * weighted edge between two given distinct vertices that are currently in
     * this graph.
     * 
     * @param begin
     *            beginning vertex label
     * @param end
     *            end vertex label
     * @param edgeWeight
     *            weight of the edge to be removed
     * @return true if successful
     */
    public boolean removeEdge(T begin, T end, double edgeWeight) {
        // check if given vertices exists in the graph:
        if (!vertices.containsKey(begin) || !vertices.containsKey(end)) {
            return false;
        }
        VertexInterface<T> originVertex = vertices.get(begin);
        VertexInterface<T> targetVertex = vertices.get(end);

        // remove connection in both directions since this is an
        // undirected graph
        boolean res1 = originVertex.disconnect(targetVertex, edgeWeight);
        boolean res2 = targetVertex.disconnect(originVertex, edgeWeight);

        if (res1 && res2) {
            numEdges--;
            return true;
        }
        else {
            return false;
        }
    }


    /**
     * Removes an unweighted edge
     * between two given distinct vertices that are currently in this graph.
     * The desired edge must already be in the graph
     * 
     * @param begin
     *            beginning vertex
     * @param end
     *            end vertex
     * @return true if successfully removed
     */
    public boolean removeEdge(T begin, T end) {
        // check if given vertices exists in the graph:
        if (!vertices.containsKey(begin) || !vertices.containsKey(end)) {
            return false;
        }
        VertexInterface<T> originVertex = vertices.get(begin);
        VertexInterface<T> targetVertex = vertices.get(end);

        // remove connection in both directions since this is an
        // undirected graph
        boolean res1 = originVertex.disconnect(targetVertex);

        boolean res2 = targetVertex.disconnect(originVertex);

        if (res1 && res2) {
            numEdges--;
            return true;
        }
        else {
            return false;
        }

    }


    /**
     * Sees whether an undirected edge
     * exists between two given vertices.
     * 
     * @param begin
     *            beginning vertex
     * @param end
     *            end vertex
     * @return true if there is an edge
     */
    public boolean hasEdge(T begin, T end) {
        // check if given vertices exists in the graph:
        if (!vertices.containsKey(begin) || !vertices.containsKey(end)) {
            return false;
        }
        VertexInterface<T> originVertex = vertices.get(begin);
        VertexInterface<T> targetVertex = vertices.get(end);

        Iterator<VertexInterface<T>> itr = originVertex.getNeighborIterator();

        VertexInterface<T> neighbor;

        while (itr.hasNext()) {
            neighbor = itr.next();
            if (neighbor.equals(targetVertex)) {
                return true;
            }
        }
        return false;
    }


    /**
     * Returns the number of
     * vertices in this graph.
     * 
     * @return number of vertices
     */
    public int getNumberOfVertices() {
        // Note that hashMap.size() method is
        // O(1)
        return vertices.size();
    }


    /**
     * Returns the number of
     * undirected Edges in this graph.
     * 
     * @return number of edges in the graph
     */
    public int getNumberOfEdges() {
        return numEdges;
    }


    /**
     * Returns true, if this graph is
     * empty, false otherwise.
     * 
     * @return true if the graph is empty
     */
    public boolean isEmpty() {
        return vertices.isEmpty();
    }


    /**
     * This method returns the
     * list of all vertices in the graph. If the graph is empty
     * returns null.
     * 
     * @return list of vertices in the graph
     */
    public List<VertexInterface<T>> getVertices() {

        if (vertices.isEmpty()) {
            return null;
        }
        else {
            List<VertexInterface<T>> vertexList =
                new ArrayList<VertexInterface<T>>(vertices.values());
            return vertexList;
        }
    }


    /**
     * Clears the graph.
     */
    public void clear() {
        vertices.clear();
        numEdges = 0;
    }


    /**
     * Performs a breadthfirst traversal of a graph and returns the queue that
     * contains the
     * result. Empty queue can be returned.
     * 
     * @param origin
     *            starting node of the traversal
     * @return a queue of vertices, according to order visitation
     */
    public Queue<T> getBreadthFirstTraversal(T origin) {
        // check if given vertices exists in the graph:
        if (!vertices.containsKey(origin)) {
            return null;
        }
        resetVertices();
        VertexInterface<T> originVertex = vertices.get(origin);

        Queue<T> traversalOrder = new LinkedList<T>();
        Queue<VertexInterface<T>> vertexQueue =
            new LinkedList<VertexInterface<T>>();

        originVertex.visit();
        traversalOrder.add(origin);
        vertexQueue.add(originVertex);

        while (!vertexQueue.isEmpty()) {
            VertexInterface<T> frontVertex = vertexQueue.remove();
            Iterator<VertexInterface<T>> itr = frontVertex
                .getNeighborIterator();
            VertexInterface<T> nextNeighbor;

            while (itr.hasNext()) {
                nextNeighbor = itr.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit();
                    traversalOrder.add(nextNeighbor.getLabel());
                    vertexQueue.add(nextNeighbor);
                }
            }
        }
        return traversalOrder;
    }


    /**
     * Returns the shortest distance between the origin and destination. If a
     * path does not exist, it returns the maximum integer (to simulate
     * infinity
     * 
     * @param origin
     *            origin vertex
     * @param destination
     *            target vertex
     * @param path
     *            shortest path between origin and destination
     * @return length of the shortest path
     */
    public int getShortestPath(T origin, T destination, Stack<T> path) {
        // check if given vertices exists in the graph:
        if (!vertices.containsKey(origin) || !vertices.containsKey(
            destination)) {
            return -1;
        }
        resetVertices();
        VertexInterface<T> originVertex = vertices.get(origin);
        VertexInterface<T> destVertex = vertices.get(destination);
        int pathLength;

        boolean done = false;
        Queue<VertexInterface<T>> vertexQueue =
            new LinkedList<VertexInterface<T>>();

        originVertex.visit();
        vertexQueue.add(originVertex);
        VertexInterface<T> frontVertex;
        Iterator<VertexInterface<T>> itrFront;
        VertexInterface<T> nextNeighbor;
        while (!done && !vertexQueue.isEmpty()) {
            frontVertex = vertexQueue.remove();

            itrFront = frontVertex.getNeighborIterator();
            while (!done && itrFront.hasNext()) {
                nextNeighbor = itrFront.next();
                if (!nextNeighbor.isVisited()) {
                    nextNeighbor.visit();
                    nextNeighbor.setCost(frontVertex.getCost() + 1);
                    nextNeighbor.setPredecessor(frontVertex);
                    vertexQueue.add(nextNeighbor);
                }
                if (nextNeighbor.equals(destVertex ) ) 
                  {
                    done = true;
                }
            }
        }

        if (destVertex.getPredecessor() == null) {
            pathLength = -1;

        }
        else {
            pathLength = (int)destVertex.getCost();
            path.push(destination);
            VertexInterface<T> vertex = destVertex;
            while (vertex.getPredecessor() != null) {
                vertex = vertex.getPredecessor();
                path.push(vertex.getLabel());
            }
        }
        return pathLength;

    }


    private void resetVertices() {
        List<VertexInterface<T>> vertexList = getVertices();

        VertexInterface<T> current;
        for (int i = 0; i < vertexList.size(); i++) {
            current = vertexList.get(i);
            current.unvisit();
            current.setCost(0.0);
            current.setPredecessor(null);
        }
    }

}
