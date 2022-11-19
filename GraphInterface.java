import java.util.List;
import java.util.Queue;
import java.util.Stack;
/**
 * Graph interface. Contains the methods that represents all the 
 * functionalities of the graph data structure. 
 * 
 *  * @param <T>
 *            data type of the labels of vertices to be stored in the Graph
 * @author Kerem Bozgan kerembozgan
 * @version 2022-11-11
 */

public interface GraphInterface<T> { 
  
    /** 
     * Adds a given vertex to the graph. 
     * If vertexLabel is null, it returns false. 
     * @param vertexLabel label of the vertex
     * @return true if operation is successful
     */
  public boolean addVertex(T vertexLabel);
   
  /**
   * Removes a vertex
   * with the given vertexLabel from this graph and returns the removed
   * vertex. If vertex does not exist, it will return null.
   * @param vertexLabel label of the vertex to remove
   * @return removed vertex
   */
  public VertexInterface<T> removeVertex(T vertexLabel);
  
  /**
   * Adds a weighted
   * edge between two given distinct vertices that are currently in this
   * graph. The desired edge must not already be in the graph. Note that the
   * graph is undirected graph.
   * @param begin beginning vertex 
   * @param end end vertex
   * @param edgeWeight weight of the edge to be added
   * @return true if added
   */
  public boolean addEdge(T begin, T end, double edgeWeight);
  
  /** 
   * Adds an unweighted edge between two
   * given distinct vertices that are currently in this graph. 
   * @param begin beginning vertex
   * @param end end vertex
   * @return true if successfull
   */
  public boolean addEdge(T begin, T end);
  
  /** 
   * Removes a
   * weighted edge between two given distinct vertices that are currently in
   * this graph.
   * @param begin beginning vertex
   * @param end end vertex
   * @param edgeWeight weight of the edge to be removed
   * @return true if successful
   */
  public boolean removeEdge(T begin, T end, double edgeWeight);
  
  /**
   * Removes an unweighted edge
   * between two given distinct vertices that are currently in this graph.
   * The desired edge must already be in the graph
   * @param begin beginning vertex
   * @param end end vertex
   * @return true if successfully removed
   */ 
  public boolean removeEdge(T begin, T end);
  
  /** 
   * Sees whether an undirected edge
   * exists between two given vertices.
   * @param begin beginning vertex
   * @param end end vertex
   * @return true if there is an edge
   */
  public boolean hasEdge(T begin, T end);
  
  /**
   * Returns the number of
   * vertices in this graph.
   * @return number of vertices
   */
  public int getNumberOfVertices();
  
  /**
   * Returns the number of
   * undirected Edges in this graph.
   * @return number of edges in the graph
   */
  public int getNumberOfEdges();
  
  /**
   * Returns true, if this graph is
   * empty, false otherwise.
   * @return true if the graph is empty 
   */
  public boolean isEmpty();
  
  /**
   * This method returns the
   * list of all vertices in the graph. If the graph is empty
   * returns null. 
   * @return list of vertices in the graph
   */
  public List<VertexInterface<T>> getVertices();
  
  /**
   * Clears the graph.
   */
  public void clear();
  
  /**
   * Performs a breadthfirst traversal of a graph and returns the queue that contains the
   * result. Empty queue can be returned.
   * @param origin starting node of the traversal
   * @return a queue of vertices, according to order visitation
   */
  public  Queue<T> getBreadthFirstTraversal(T origin);
  
  /**
    * Returns the shortest distance between the origin and destination. If a
    * path does not exist, it returns the maximum integer (to simulate
    * infinity
   * @param origin origin vertex
   * @param destination target vertex
   * @param path shortest path between origin and destination
   * @return length of the shortest path
   */
  public int getShortestPath(T origin, T destination, Stack<T> path);
  
}