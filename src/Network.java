import java.util.*;

/**
 *
 *
 */
public class Network {

    private List<User> nodeArray;
    private Map<String, AbstractMap.SimpleEntry<User, String>> credentials;

    // Initialize the graph with n vertices
    public void init(int n)
    {
        nodeArray = new ArrayList<>();
        for (int i = 0; i <= n; i++) {
            nodeArray.add(new User(i));
        }
        credentials = new HashMap<>();
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

    public int getUserId(User u) {
        return nodeArray.indexOf(u);
    }

    // Set the user at index v
    public void setUser(int v, User user)
    {
        nodeArray.set(v, user);

    }

    public void setUsername(int v, String username) {
        if (!credentials.containsKey(username)) {
            credentials.put(username, new AbstractMap.SimpleEntry<>(getUser(v), ""));
        }
    }

    public void setPassword(int v, String password) {
        for (AbstractMap.SimpleEntry values : credentials.values()) {
            if (getUserId((User) values.getKey()) == v) {
                values.setValue(password);
            }
        }
    }

    public Map<String, AbstractMap.SimpleEntry<User, String>> getCredentials() {
        return credentials;
    }

}
