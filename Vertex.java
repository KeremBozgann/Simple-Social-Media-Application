import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

/**
 * Implementation of Vertex interface.
 * 
 * * @param <T>
 * data type of the label of the vertex
 * 
 * @author Kerem Bozgan kerembozgan
 * @version 2022-11-11
 */
class Vertex<T> implements VertexInterface<T> {
    public static void main(String[] args) { 
        Profile us1 = new Profile();
        us1.setName("kerem", "bozgan");
        us1.setStatus("my name is Kerem Bozgan");
        
        Profile us2 = new Profile();
        us2.setName("ahmet", "demir");
        us2.setStatus("my name is Ahmet Demir");
        
        Profile us3 = new Profile();
        us3.setName("muhammed", "ali");
        us3.setStatus("my name is Muhammed Ali");
        
        Vertex<Profile> vertex1 = new Vertex<Profile>(us1); 
        Vertex<Profile> vertex2 = new Vertex<Profile>(us2); 
        Vertex<Profile> vertex3 = new Vertex<Profile>(us3);
        
        System.out.println( vertex1.connect(vertex1, 10));
        System.out.println( vertex2.connect(vertex1, 20));
        System.out.println( vertex2.connect(vertex1, 30));
        System.out.println( vertex2.connect(vertex1, 20));
        System.out.println( vertex1.connect(vertex3, 30));
        System.out.println( vertex3.connect(vertex1, 30));
        System.out.println( vertex1.connect(vertex2, 100));
        System.out.println( );
        Iterator<VertexInterface<Profile>> itr = 
            vertex1.getNeighborIterator();
        while(itr.hasNext()) {
            Profile friend = itr.next().getLabel();
            System.out.println(friend);
        }
        System.out.println( );
        System.out.println( vertex1.disconnect(vertex1, 10));
        System.out.println( vertex2.disconnect(vertex1, 40));
        System.out.println( );

        
        System.out.println("Vertex weighted disconnect:" );
        System.out.println( vertex1.disconnect(vertex3, 10));
        System.out.println( vertex1.disconnect(vertex3, 30));
        System.out.println( vertex1.disconnect(vertex2, 100));
        System.out.println( vertex2.disconnect(vertex1, 50));
        System.out.println( vertex2.disconnect(vertex1, 20));
        System.out.println( vertex2.disconnect(vertex1, 30));
        System.out.println( vertex3.disconnect(vertex1, 30));
        System.out.println( );
        
        System.out.println("Vertex unweighted connect:" );
        System.out.println( vertex1.connect(vertex1));
        System.out.println( vertex1.connect(vertex2));
        System.out.println( vertex1.connect(vertex3));
        System.out.println( vertex3.connect(vertex2));
        System.out.println("Vertex unweighted disconnect:" );
        System.out.println( vertex1.disconnect(vertex1));
        System.out.println( vertex2.disconnect(vertex1));
//        System.out.println( vertex1.disconnect(vertex2));
//        System.out.println( vertex1.disconnect(vertex3));
        System.out.println( vertex3.disconnect(vertex2));
        itr = 
            vertex1.getNeighborIterator();
        while(itr.hasNext()) {
            Profile friend = itr.next().getLabel();
            System.out.println(friend);
        }
        System.out.println( );
        System.out.println( vertex2.hasNeighbor());
        System.out.println( vertex1.getUnvisitedNeighbor().getLabel());
        vertex2.visit();
        System.out.println( );
        System.out.println( vertex1.getUnvisitedNeighbor().getLabel());

        
        
    }
    /**
     * Represents the label of the vertex.
     */
    private T label;

    /**
     * Stores if the vertex is visited or not, true if visited.
     */
    private boolean visited;

    /**
     * Predecessor of this vertex vertex on path to this vertex
     */
    private VertexInterface<T> previousVertex;

    /**
     * The cost of a path to this vertex.
     */
    private double cost;

    /**
     * List of edges that start at this node
     */
    protected List<Edge> edgeList;

    /**
     * 
     * Inner Edge class that simply
     * stores the index for the vertex
     * pointed to by the edge and the weight
     * of the edge
     * 
     * Reference: Taken from the lecture notes: Graphs.pdf
     * 
     * @author Kerem Bozgan kerembozgan
     *
     */
    private class Edge {
        private VertexInterface<T> vertex;
        private double weight;

        private Edge(VertexInterface<T> endVertex) {
            vertex = endVertex;
        }


        private Edge(VertexInterface<T> endVertex, double edgeWeight) {
            vertex = endVertex;
            weight = edgeWeight;
        }

    }

    /**
     * initialize a vertex with given label
     * 
     * @param vertexLabel
     *            label of the vertex
     */
    public Vertex(T vertexLabel) {
        label = vertexLabel;
        visited = false;
        cost = 0.0;
        previousVertex = null;
        edgeList = new ArrayList<Edge>();
    }


    /**
     * getter method for the vertex label
     * 
     * @return label of the vertex
     */
    public T getLabel() {
        return label;
    }


    /**
     * Marks the vertex as visited
     */
    public void visit() {
        visited = true;
    }


    /**
     * Returns number of vertices connected to this vertex
     * by an edge
     * 
     * @return number of neighbors
     */
    public int getNumberOfNeighbors() {
        return edgeList.size();
    }


    /**
     * Remove the visited mark of this vertex
     */
    public void unvisit() {
        visited = false;
    }


    /**
     * Returns true if vertex is marked as visited,
     * false otherwise
     * 
     * @return true if vertex is visited
     */
    public boolean isVisited() {
        return visited;
    }

//
//    /**
//     * Returns true if label of the two vertices are the same,
//     * false otherwise
//     * 
//     * @param vertex1
//     *            first vertex
//     * @param vertex2
//     *            second vertex
//     * @return true if both have same labels
//     */
//    private boolean equals(
//        VertexInterface<T> vertex1,
//        VertexInterface<T> vertex2) {
//        if (vertex1.getLabel().compareTo(vertex2.getLabel()) == 0) {
//            return true;
//        }
//        else {
//            return false;
//        }
//    }


    /**
     * Connects this vertex
     * and endVertex with a unweighted edge.
     * Abort if two vertices are the same or
     * an edge connecting the vertices already exits.
     * 
     * @param endVertex
     *            vertex to be connected
     * @return true if connection is successful
     */
    public boolean connect(VertexInterface<T> endVertex) {

        // First, check if labels are the same.
        if (this.equals(endVertex)) {
            return false;
        }

        // Next, check if an edge to endVertex already exists.
        Iterator<Edge> itr = edgeList.iterator();
        Edge currentEdge;
        while (itr.hasNext()) {
            currentEdge = itr.next();
            if (endVertex.equals(currentEdge.vertex)) {
                return false;
            }
        }

        // If not, add the edge to list.
        Edge newEdge = new Edge(endVertex);
        edgeList.add(newEdge);
        return true;

    }


    /**
     * Connects this vertex
     * and endVertex with a weighted edge.
     * Abort if two vertices are the same or
     * an edge connecting the vertices already exits.
     * 
     * @param endVertex
     *            vertex to be connected
     * @param edgeWeight
     *            weight of the edge
     * @return true if connection is successful
     */
    public boolean connect(VertexInterface<T> endVertex, double edgeWeight) {

        // First, check if labels are the same.
        if (this.equals( endVertex)) {
            return false;
        }

        // Next, check if an edge with the same weight
        // to endVertex already exists.
        Iterator<Edge> itr = edgeList.iterator();
        Edge currentEdge;
        while (itr.hasNext()) {
            currentEdge = itr.next();
            if (endVertex.equals( currentEdge.vertex)
                && edgeWeight == currentEdge.weight) {
                return false;
            }
        }

        // If not, add the edge to list.
        Edge newEdge = new Edge(endVertex, edgeWeight);
        edgeList.add(newEdge);
        return true;

    }


    /**
     * Method to remove an unweighted edge between two vertices
     * 
     * @param endVertex
     *            target vertex
     * @return true if disconnection is successful
     */
    public boolean disconnect(VertexInterface<T> endVertex) {

        // Search for the edge and remove it if found:
        Edge currentEdge;

        for (int i = 0; i < edgeList.size(); i++) {
            currentEdge = edgeList.get(i);
            if (endVertex.equals( currentEdge.vertex)) {
                edgeList.remove(i);
                return true;
            }
        }
        // Otherwise, return false:
        return false;
    }


    /**
     * Method to remove a weighted edge between two vertices
     * 
     * @param endVertex
     *            target vertex
     * @return true if disconnection is successful
     */
    public boolean disconnect(VertexInterface<T> endVertex, double edgeWeight) {

        // Search for the edge and return true if found:
        Edge currentEdge;

        for (int i = 0; i < edgeList.size(); i++) {
            currentEdge = edgeList.get(i);
            if (endVertex.equals( currentEdge.vertex)
                && edgeWeight == currentEdge.weight) {
                edgeList.remove(i);
                return true;
            }
        }
        // Otherwise, return false:
        return false;
    }


    /**
     * Returns iterator for list of neighbor vertices.
     * 
     * @return iterator for list of neighbors
     */
    public Iterator<VertexInterface<T>> getNeighborIterator() {

        List<VertexInterface<T>> vertexList =
            new ArrayList<VertexInterface<T>>();

        for (int i = 0; i < edgeList.size(); i++) {
            vertexList.add(edgeList.get(i).vertex);
        }
        return vertexList.iterator();
    }


    /**
     * Returns true if vertex has a neighbor,
     * false otherwise.
     */
    public boolean hasNeighbor() {
        return !edgeList.isEmpty();
    }


    /**
     * Gets an unvisited neighbor, if any, returns null
     * otherwise
     * 
     * @return an unvisited neighbor vertex
     */
    public VertexInterface<T> getUnvisitedNeighbor() {

        // Search for the edge and return true if found:
        VertexInterface<T> currentVertex;

        for (int i = 0; i < edgeList.size(); i++) {
            currentVertex = edgeList.get(i).vertex;
            if (!currentVertex.isVisited()) {
                return currentVertex;
            }
        }
        return null;
    }


    /**
     * Records the
     * previous vertex on a path to this vertex.
     * 
     * @param predecessor
     */
    public void setPredecessor(VertexInterface<T> predecessor) {
        previousVertex = predecessor;
    }


    /**
     * Gets the recorded predecessor
     * of this vertex.
     * 
     * @return predecessor vertex
     */
    public VertexInterface<T> getPredecessor() {
        return previousVertex;
    }


    /**
     * Sees whether a predecessor was recorded for
     * this vertex.
     * 
     * @return true if a predecessor was recorded, false if null
     */
    public boolean hasPredecessor() {
        return !(previousVertex == null);
    }


    /**
     * Records the cost of a path to this
     * vertex.
     * 
     * @param newCost
     *            set cost of a path to this vertex
     */
    public void setCost(double newCost) {
        cost = newCost;
    }


    /**
     * Returns the cost of a path to this vertex.
     * 
     * @return cost
     */
    public double getCost() {
        return cost;
    }
}
