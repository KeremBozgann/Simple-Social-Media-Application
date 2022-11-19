// On my honor:
//
// - I have not used source code obtained from another student,
// or any other unauthorized source, either modified or
// unmodified.
//
// - All source code and documentation used in my program is
// either my original work, or was derived by me from the
// source code published in the textbook for this course.
//
// - I have not discussed coding details about this project with
// anyone other than my partner (in the case of a joint
// submission), instructor, ACM/UPE tutors or the TAs assigned
// to this course. I understand that I may discuss the concepts
// of this program with other students, and that another student
// may help me debug my program so long as neither of us writes
// anything during the discussion or modifies any computer file
// during the discussion. I have violated neither the spirit nor
// letter of this restriction.

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Stack;

/**
 * Implementation of Vertex interface.
 * 
 * * @param <T>
 * data type of the label of the vertex
 * 
 * @author Kerem Bozgan kerembozgan
 * @version 2022-11-11
 */

class VTConnect {

    /** Dictionary of (id, vertex) pairs */
    private Graph<Integer> grph;
    /** Dictionary of (id, profile) pairs */
    private HashMap<Integer, Profile> profileDictionary;
    /**
     * Counts added profiles to
     * assign unique id to each profile
     */
    private Integer idCounter = 0;

    /**
     * Initializes social network with empty
     * dictionaries
     */
    public VTConnect() {
        grph = new Graph<Integer>();
        profileDictionary = new HashMap<Integer, Profile>(101);
    }


    /**
     * Adds a new user to the social network.
     * Checks if new profile is a duplicate
     * of any other profile in the graph.
     * 
     * @param p
     *            profile to be added
     */
    public void addUser(Profile p) {

        // check if p matches any other profile in the
        // graph, in terms of name, status and friends
        List<Profile> profileList = new ArrayList<Profile>(profileDictionary
            .values());

        Profile currentProfile;
        for (Integer i = 0; i < profileList.size(); i++) {
            currentProfile = profileList.get(i);

            // if duplicate profile, return:
            if (currentProfile.toString().compareTo(p.toString()) == 0) {
                return;
            }
        }

        Integer newId = idCounter++;
        grph.addVertex(newId);
        p.id = newId;
        profileDictionary.put(newId, p);
    }


    /**
     * Removes an existing user from the
     * social network. If the user does not exist, it returns null.
     * 
     * @param p
     *            profile to be removed
     * @return removed profile
     */
    public Profile removeUser(Profile p) {
        if (!profileDictionary.containsKey(p.id)) {
            return null;
        }
        else {
            grph.removeVertex(p.id);
            Profile profileRemoved = profileDictionary.remove(p.id);
            return profileRemoved;
        }

    }


    /**
     * Creates a
     * friendship between two users on VTConnect. If the friendship is created
     * successfully, it returns true, false otherwise.
     * 
     * @param a
     *            first profile
     * @param b
     *            second profile
     * @return true if successful
     */
    public boolean createFriendship(Profile a, Profile b) {
        // check if profiles exists in the graph and
        // these profiles are not the same
        if (!profileDictionary.containsKey(a.id) || !profileDictionary
            .containsKey(b.id) || a.id == b.id) {
            return false;
        }
        boolean res = grph.addEdge(a.id, b.id);
        if (res) {
            a.friendProfiles.add(b);
            b.friendProfiles.add(a);
        }
        return res;
    }


    /**
     * removes a friendship
     * between two users on VTConnect. If the friendship is discontinued
     * successfully, it returns true, false otherwise
     * 
     * @param a
     *            first profile
     * @param b
     *            second profile
     * @return true if successful
     */
    public boolean removeFriendship(Profile a, Profile b) {
        boolean res = grph.removeEdge(a.id, b.id);
        if (res) {
            a.friendProfiles.remove(b);
            b.friendProfiles.remove(a);
        }
        return res;
    }


    /**
     * Returns true if there is
     * friendship between Profiles a and b, false otherwise.
     * 
     * @param a
     *            first profile
     * @param b
     *            second profile
     * @return true if has friendship
     */
    public boolean hasFriendship(Profile a, Profile b) {
        return grph.hasEdge(a.id, b.id);
    }


    /**
     * this method displays each
     * profile's information and friends, starting from the startPoint
     * profile.
     * 
     * @param startPoint
     *            start of traversal
     */
    public void traverse(Profile startPoint) {
        Queue<Integer> traversalOrder = grph.getBreadthFirstTraversal(
            startPoint.id);
        int sizeList = traversalOrder.size();
        Profile currentProfile;
        Integer currentId;
        for (int i = 0; i < sizeList; i++) {
            currentId = traversalOrder.remove();
            currentProfile = profileDictionary.get(currentId);
            currentProfile.display();
            System.out.println();
        }

    }


    /**
     * this returns true if a user with the
     * given profile exists in VTConnect, false otherwise.
     * 
     * @param user
     *            user profile to query
     * @return true if profile exists
     */
    public boolean exists(Profile user) {
        return profileDictionary.containsKey(user.id);
    }


    /**
     * Returns a list of
     * Profiles, who are friends with one or more of the profile's friends
     * (but not currently the profile's friend). It returns null, if the user
     * does not exist or if it does not have any friend suggestions.
     * 
     * @param user
     *            origin profile
     * @return friend suggestionList for user
     */
    public List<Profile> friendSuggestion(Profile user) {
        // check if given vertex exists in the graph:
        if (!profileDictionary.containsKey(user.id)) {
            return null;
        }
        resetVertices();

        VertexInterface<Integer> originVertex = grph.vertices.get(user.id);
        originVertex.visit();
        Iterator<VertexInterface<Integer>> itrNeigh = originVertex
            .getNeighborIterator();
        Iterator<VertexInterface<Integer>> itrNeigh2;
        List<Profile> suggestionList = new ArrayList<Profile>();
        VertexInterface<Integer> currentVertex;
        VertexInterface<Integer> currentVertex2;

        // First, mark all the neighbors as visited.
        while (itrNeigh.hasNext()) {
            currentVertex = itrNeigh.next();
            currentVertex.visit();
        }
        // reset iterator
        itrNeigh = originVertex.getNeighborIterator();
        // Next, traverse all the neighbors of the neighbors.
        // Add them to suggestionList if they are not visited.
        while (itrNeigh.hasNext()) {
            currentVertex = itrNeigh.next();
            itrNeigh2 = currentVertex.getNeighborIterator();
            while (itrNeigh2.hasNext()) {
                currentVertex2 = itrNeigh2.next();
                if (!currentVertex2.isVisited()) {
                    Integer id = currentVertex2.getLabel();
                    currentVertex2.visit();
                    Profile prof = profileDictionary.get(id);
                    suggestionList.add(prof);
                }
            }
        }
        if (suggestionList.size() == 0) {
            return null;
        }
        else {
            return suggestionList;
        }
    }


    /**
     * Returns the friendship
     * distance between two profiles.
     * 
     * @param a
     *            first profile
     * @param b
     *            second profile
     * @return distance between profiles
     */
    public int friendshipDistance(Profile a, Profile b) {
        // check if given vertices exist in the graph:
        if (!profileDictionary.containsKey(a.id) || !profileDictionary
            .containsKey(b.id)) {
            return -1;
        }
        Stack<Integer> path = new Stack<Integer>();
        return grph.getShortestPath(a.id, b.id, path);
    }


    /**
     * Reset vertices before traversals
     */
    private void resetVertices() {
        List<VertexInterface<Integer>> vertexList = grph.getVertices();

        VertexInterface<Integer> current;
        for (int i = 0; i < vertexList.size(); i++) {
            current = vertexList.get(i);
            current.unvisit();
            current.setCost(0.0);
            current.setPredecessor(null);
        }
    }
}
