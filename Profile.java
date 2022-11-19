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
class Profile {

    /** Unique, unmutable identifier for profile */
    protected Integer id;
    /** A String value that represents the full name of the user */
    protected String name;

    /** A String that the user uses to specify their status. */
    protected String status;

    /** An arraylist of profiles that stores friends of the user. */
    protected ArrayList<Profile> friendProfiles;

    /**
     * Initializes all the String attributes to
     * empty strings and a default arraylist.
     */
    public Profile() {
        name = "";
        status = "";
        friendProfiles = new ArrayList<Profile>();
    }


    /**
     * Initializes attributes to the given values.
     * 
     * @param name
     *            user name
     * @param status
     *            user status
     * @param friendProfiles
     *            list of friend profiles
     */
    public Profile(
        String name,
        String status,
        ArrayList<Profile> friendProfiles) {
        this.name = name;
        this.status = status;
        this.friendProfiles = friendProfiles;

    }


    /**
     * Initializes the attributes with
     * the accepted valued and the last attribute with a default arraylist
     * object.
     * 
     * @param name
     *            user name
     * @param status
     *            user status
     */
    public Profile(String name, String status) {
        this.name = name;
        this.status = status;
        this.friendProfiles = new ArrayList<Profile>();
    }


    /**
     * The setter method for the name attribute
     * that accepts the first and last name of the user and set the name
     * attribute with firstName +” “ +lastName
     * 
     * @param firstName
     *            firstname
     * @param lastName
     *            lastname
     */
    public void setName(String firstName, String lastName) {
        name = firstName + " " + lastName;
    }


    /**
     * The getter method for the name attribute.
     * 
     * @return user name
     */
    public String getName() {
        return name;
    }


    /**
     * The setter method for status
     * 
     * @param status
     *            status
     */
    public void setStatus(String status) {
        this.status = status;
    }


    /**
     * Getter method for status
     * 
     * @return status
     */
    public String getStatus() {
        return status;
    }


    /**
     * Returns the a string that represents the profile
     * of the user.
     * 
     * @return user info
     */
    public String toString() {
        return "Name: " + name + "\n\tStatus: " + status
            + "\n\tNumber of friend profiles: " + friendProfiles.size() + "\n";
    }


    /**
     * Displays the profile and the friends profiles.
     */
    public void display() {

        System.out.println("Name: " + name + "\n\tStatus: " + status
            + "\n\tNumber of friend profiles: " + friendProfiles.size());

        System.out.println("Friends:");
        Profile friend;
        for (int i = 0; i < friendProfiles.size(); i++) {
            friend = friendProfiles.get(i);
            System.out.println("\t" + friend.name);
        }
    }


    /**
     * Getter method for attribute friendProfiles
     * 
     * @return friendProfiles
     */
    public ArrayList<Profile> getFriendProfiles() {
        return friendProfiles;
    }


    /**
     * Add a new friend to the friends list.
     * 
     * @param user
     *            user name to add as friend
     */
    public void addFriend(Profile user) {
        // check if user is trying to friend itself
        if (name.compareTo(user.name) == 0) {
            return;
        }
        if (!friendProfiles.contains(user)) {
            friendProfiles.add(user);
        }
    }


    /**
     * Removes an existing friend from the
     * list of friends.
     * 
     * @param user
     *            user to unfriend
     * @return true if removed successfully
     */
    public boolean unFriend(Profile user) {

        Profile friend;
        for (int i = 0; i < friendProfiles.size(); i++) {
            friend = friendProfiles.get(i);
            if (friend.name.compareTo(user.name) == 0) {
                friendProfiles.remove(i);
                return true;
            }
        }
        return false;

    }
}
