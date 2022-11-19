import java.util.Iterator;
/**
 * Vertex interface. Represents all the 
 * functionalities of a vertex in a graph. 
 * 
 *  * @param <T>
 *            data type of the label of the vertex
 * @author Kerem Bozgan kerembozgan
 * @version 2022-11-11
 */

public interface VertexInterface<T> { 
  
   /**
    * Getter method for the vertex label. 
    * @return vertex label
    */
  public T getLabel();
  
  /**
   * Returns number of neighbors of this vertex 
   * @return number of neighbors
   */
  public int getNumberOfNeighbors();
  
  /**
   * Marks this vertex as visited
   */
  public void visit();
  
  /**
   * Removes this vertex's visited mark
   */
  public void unvisit();
  
  /**
   * Returns true if the vertex is visited, otherwise returns false.
   * @return true if vertex marked as visited
   */
  public boolean isVisited();
  
  /**
Connects this vertex and endVertex with a weighted edge. The two
vertices cannot be the same, and must not already have this edge between
them. Two vertices are equal (same)if their labels are equal (same).
Returns true if the connection is successful, false otherwise. 
   * @param endVertex The vertex to connect to
   * @return true if connection is successful
   */
  public boolean connect(VertexInterface<T> endVertex, 
      double  edgeWeight);
  
  
  /**
   * Connects this vertex
and endVertex with a unweighted edge. The two vertices cannot be the
same, and must not already have this edge between them. Two vertices
are equal (same)if their labels are equal (same). Returns true if the
connection is successful, false otherwise.
   * @param endVertex The vertex to connect to
   * @return true if connection is successful
   */
  public boolean connect(VertexInterface<T> endVertex);
  

  /**
   * Disconnects this vertex from a given vertex with a weighted edge, i.e.,
removes the edge. The Edge should exist in order to be disconnected.
Returns true if the disconnection is successful, false otherwise.
   * @param endVertex The vertex to disconnect from
   * @return true if successfully disconnected
   */
  public boolean disconnect(VertexInterface<T> endVertex, 
                  double edgeWeight);
  
  
  /**
   * Disconnects this
vertex from a given vertex with an unweighted edge. The Edge should
exist in order to be disconnected. Returns true if the disconnection is
successful, false otherwise.
   * @param endVertex The vertex to disconnect from
   * @return true if successfully disconnected
   */
  public boolean disconnect(VertexInterface<T> endVertex);
  
  /**
   * creates an
iterator of this vertex's neighbors by following all edges that begin
at this vertex.
   * @return iterator of neighbors 
   */
  public Iterator<VertexInterface<T>> getNeighborIterator();
  
  /**
   * Sees whether this vertex has at least one
neighbor.
   * @return true if vertex has neighbor, false otherwise
   */
  public boolean hasNeighbor();
  
  /**
   * Gets an unvisited
neighbor, if any, of this vertex.
   * @return an unvisited neighbor vertex, if there are any.
   */
  public VertexInterface<T> getUnvisitedNeighbor();
  
  /**
   * Records the
previous vertex on a path to this vertex.
   * @param predecessor predecessor of this vertex on a path to this vertex
   */
  public void setPredecessor(VertexInterface<T> predecessor);
  
  /**
   * Gets the recorded predecessor
of this vertex.
   * @return predecessor vertex
   */
  public VertexInterface<T> getPredecessor();
  
  /**
   * Sees whether a predecessor was recorded for
this vertex.
   * @return true if a predecessor was recorded, false otherwise
   */
  public boolean hasPredecessor();
  
  /**
   * Records the cost of a path to this
vertex.
   * @param newCost set cost of a path to this vertex
   */
  public void setCost(double newCost);
  
  /**
   * Returns the cost of a path to this vertex.
   * @return cost
   */
  public double getCost();
}