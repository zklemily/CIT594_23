import java.io.*;
import java.util.*;

public class Main {
    /**
     * Create a graph representation of the dataset. The first line of the file
     * contains the number of nodes. Keep in mind that the vertex with id 0 is
     * not actually considered present in your final graph!
     *
     * @param filePath the path of the data
     * @param tau      the minimum edge weight required for an edge to be added to the graph.
     * Will be a value between 0 and 1. Save this as an instance variable!
     * @return the number of entries (nodes) in the dataset (graph)
     */
    private int numVertices;    //all vertices in the doc
    private int actualVertices; //actual vertices without junk edges
    private double tau;
    Network graph = new Network();

    public int loadGraphFromDataSet(String filePath, double tau) {
        this.tau = tau;
        Set<Integer> set = new HashSet<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            int numVertices = Integer.parseInt(br.readLine().split(",")[0]);
            this.numVertices = numVertices;
            graph.init(numVertices);
            //try to read the file line by line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length < 3) {
                    continue;
                }
                int v1 = Integer.parseInt(parts[0].trim());
                int v2 = Integer.parseInt(parts[1].trim());
                double weight = Double.parseDouble(parts[2].trim());
                if (weight < tau) {
                    continue;
                }
                graph.getUser(v1).follow(graph.getUser(v2), weight);
                set.add(v1);
                set.add(v2);
            }
            br.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.actualVertices = set.size();
        return actualVertices;
    }

    public void loadData(String path, BiUser<User, String> function) {
        try {
            BufferedReader br = new BufferedReader(new FileReader(path));
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                int userIndex = Integer.parseInt(data[0].trim());
                String value = data[1];
                User currUser = graph.getUser(userIndex);
                function.accept(currUser, value);
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
//
//        // Load user interests
//        loadData("userInterests.txt", (user, interest) -> user.addInterest(interest));
//
//        // Load usernames
//        loadData("username1000.txt", (user, username) -> user.setUsername(username));
//
//        // Load passwords
//        loadData("userPassword1000.txt", (user, password) -> user.setPassword(password));
    }

    public void connect(int v, int w, int wt) {

    }


    /**
     * return the shortest path between two vertices
     * include the source and destination vertices in your collection
     *
     * @param source      - the id of the origin node
     * @param destination - the id of the destination node
     * @return collection of nodes to follow to go from source to destination
     */
    public List<Integer> path(int source, int destination) {
        double[] dist = new double[numVertices + 1];
        Arrays.fill(dist, Double.POSITIVE_INFINITY);
        dist[source] = 0;
        boolean[] visited = new boolean[numVertices + 1];

        // Initialize the priority queue with the source vertex
        PriorityQueue<Integer> queue =
                new PriorityQueue<>(Comparator.comparingDouble(v -> dist[v]));
        queue.offer(source);

        // Initialize the parent array to keep track of the shortest path
        int[] parent = new int[numVertices + 1];
        Arrays.fill(parent, -1);

        // Run Dijkstra's algorithm
        while (!queue.isEmpty()) {
            int curr = queue.poll();
            if (visited[curr]) {
                continue;
            }
            visited[curr] = true;
            User currUser = graph.getUser(curr);
            for (User neighbor : currUser.getFollowings().keySet()) {
                int n = neighbor.getUserId();
                double weight = currUser.getFollowings().get(neighbor);
                if (dist[curr] + weight < dist[n]) {
                    dist[n] = dist[curr] + weight;
                    parent[n] = curr;
                    queue.offer(n);
                }
            }
        }

        // Construct the path from the parent array
        List<Integer> path = new ArrayList<>();
        int curr = destination;
        while (curr != -1) {
            path.add(curr);
            curr = parent[curr];
        }
        if (path.size() == 1) {
            return new ArrayList<>();
        }
        Collections.reverse(path);

        return path;
    }

    /**
     * Compute the average degree of the graph
     */
    public double avgInDegree() {
        int edgeCount = 0;
        for (User i : graph.getNodeArray()) {
            edgeCount += i.getFollowers().size();
        }
        return (double) edgeCount / numVertices;
    }

    public double avgOutDegree() {
        int edgeCount = 0;
        for (User i : graph.getNodeArray()) {
            edgeCount += i.getFollowings().size();
        }
        return (double) edgeCount / numVertices;
    }

    public int inDegree(int n) {
        if (graph.getUser(n).getFollowers().size() == 0) {
            return -1;
        }
        return graph.getUser(n).getFollowers().size();
    }

    public int outDegree(int n) {
        if (graph.getUser(n).getFollowings().size() == 0) {
            return -1;
        }
        return graph.getUser(n).getFollowings().size();
    }

    /**
     * @param d the degree
     * @return all the node with degree d
     */
    public Collection<User> inDegreeNodes(int d) {
        Set<User> nodes = new HashSet<>();
        for (User i : graph.getNodeArray()) {
            if (i.getFollowers().size() == d) {
                nodes.add(i);
            }
        }

        return nodes;
    }

    /**
     * @return list of <user, shared number of interests> pairs
     * ranked by shared number of interests
     *
     */
    public List<AbstractMap.SimpleEntry<User, Integer>> findCommonInterestUsers (User user) {
        List<AbstractMap.SimpleEntry<User, Integer>> ret = new ArrayList<>();

        for (User x : graph.getNodeArray()) {
            Set<String> intersection = new HashSet<>(user.getInterestSet());
            intersection.retainAll(x.getInterestSet());
            if (intersection.size() != 0) {
                ret.add(new AbstractMap.SimpleEntry<>(x, intersection.size()));
            }
        }

        ret.sort(Map.Entry.comparingByValue());

        return ret;
    }

    /**
     * @return list of users ranked by number of followers
     *
     */
    public List<User> kMostInfluentialUsers (int k) {
        PriorityQueue<User> pq = new PriorityQueue<>(Comparator.comparingInt(user -> -user.getFollowers().size()));

        for (User x : graph.getNodeArray()) {
            pq.offer(x);
            if (pq.size() > k) {
                pq.poll();
            }
        }

        List<User> ret = new ArrayList<>();
        while (!pq.isEmpty()) {
            ret.add(pq.poll());
        }

        return ret;
    }


    public User login() {
        Scanner scanner = new Scanner(System.in);
        Map<String, AbstractMap.SimpleEntry<User, String>> credentials = graph.getCredentials();
        System.out.println("Please enter your username: ");
        String username = scanner.nextLine().trim();
        while (!credentials.containsKey(username)) {
            System.out.println("Invalid username! Please try again, or type 'q' to exit.");
            String userInput = scanner.nextLine().trim();
            if (userInput.equals("q")) {
                return graph.getUser(0);
            }
        }

        String password = scanner.nextLine().trim();
        AbstractMap.SimpleEntry<User, String> value = credentials.get(username);
        while (!value.getValue().equals(password)) {
            System.out.println("Invalid password! Please try again, or type 'q' to exit.");
            password = scanner.nextLine().trim();
            if (password.equals("q")) {
                return graph.getUser(0);
            }
        }
        return value.getKey();
    }

    public User register() {
        Map<String, AbstractMap.SimpleEntry<User, String>> credentials = graph.getCredentials();
        Scanner scanner = new Scanner(System.in);

        System.out.println("Please enter your username: ");
        String userInput = scanner.nextLine().trim();
        while (credentials.containsKey(userInput)) {
            System.out.println("The username already exists. Please enter another one. Or type 'q' to exit");
            userInput = scanner.nextLine().trim();
            if (userInput.equals("q")) {
                return graph.getUser(0);
            }
        }

        System.out.println("Please enter your password: ");
        String userPassword = scanner.nextLine().trim();
        int userId = graph.getNodeArray().size();
        User newUser = new User(userId);
        graph.addUser(newUser);

        newUser.setUsername(userInput);
        newUser.setPassword(userPassword);

        credentials.put(userInput, new AbstractMap.SimpleEntry<>(newUser, userPassword));

        return newUser;
    }

    public void main(String[] args) {
        // Initialize your social network and other necessary objects

        // Start the main loop for the terminal-based interaction
        Scanner scanner = new Scanner(System.in);

        boolean flag = true;
        while (flag) {
            // Display the menu options
            displayMainMenu();

            // Get user input
            String choice = scanner.nextLine();
            String userInput;

            switch (choice) {
                case "1": {
                    if (this.login().getUserId() == 0) {
                        continue;
                    }
                    User currUser = this.login();
                    break;
                }
                case "2": {
                    if (this.register().getUserId() == 0) {
                        continue;
                    }
                    User currUser = this.register();
                    break;
                }
                case "3":
                    flag = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    flag = false;
                    break;
            }

        }

        // Clean up and exit the application
        scanner.close();
        System.out.println("Exiting the social network application.");
    }

    private static void displayMainMenu() {
        System.out.println("=== Social Network Menu ===");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("3. Quit");
        System.out.print("Please enter your option: eg. '1'");
    }
}
