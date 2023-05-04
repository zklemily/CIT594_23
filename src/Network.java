import java.util.ArrayList;
import java.util.List;

/**
 *
 *
 */
public class Network {

    private List<User> nodeArray;

    // Initialize the graph with n vertices
    public void init(int n)
    {
        nodeArray = new ArrayList<>();
    }

    public List<User> getNodeArray() {
        return nodeArray;
    }

    // Return the number of vertices
    public int userCount()
    {
        return nodeArray.size();
    }


    // Get the user at index v
    public User getUser(int v)
    {
        return nodeArray.get(v);
    }

    // Set the user at index v
    public void setUser(int v, User user)
    {
        nodeArray.set(v, user);
    }

}
