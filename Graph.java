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
class Graph<T extends Comparable<T>> implements GraphInterface<T> {

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
            if (neighbor.getLabel().compareTo(targetVertex.getLabel()) == 0) {
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
                if (nextNeighbor.getLabel().compareTo(destVertex
                    .getLabel()) == 0) {
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
