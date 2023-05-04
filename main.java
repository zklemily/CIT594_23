import java.io.*;
import java.util.*;

public class main {
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
            int numVertices = Integer.parseInt(br.readLine().split(" ")[0]);
            this.numVertices = numVertices;
            graph.init(numVertices + 1);
            //try to read the file line by line
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(" ");
                if (parts.length < 3) {
                    continue;
                }
                int v1 = Integer.parseInt(parts[0].trim());
                int v2 = Integer.parseInt(parts[1].trim());
                double weight = Double.parseDouble(parts[2].trim());
                if (weight < tau) {
                    continue;
                }
                graph.addEdge(v1, v2, (int) (weight * 100));
                graph.addEdge(v2, v1, (int) (weight * 100));
                set.add(v1);
                set.add(v2);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.actualVertices = set.size();
        return actualVertices;
    }

    public void connect(int v, int w, int wt) {

    }

    public Set<User> findCommonFollowers(User a, User b) {
        Set<User> intersection = new HashSet<>(a.getFollowers()); // use the copy constructor
        intersection.retainAll(b.getFollowers());
        return intersection;
    }

    public Set<User> findCommonFollowings(User a, User b) {
        Set<User> intersection = new HashSet<>(a.getFollowings()); // use the copy constructor
        intersection.retainAll(b.getFollowings());
        return intersection;
    }


    /**
     * Return the ids of the neighbors of a specific vertex
     *
     * @param id the id of the vertex
     * @return the array of neighbor(s)
     */
    public int[] getNeighbors(int id) {
        return graph.neighbors(id);
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
            for (int neighbor : graph.neighbors(curr)) {
                double weight = graph.weight(curr, neighbor);
                weight /= 100;
                if (dist[curr] - Math.log(weight) < dist[neighbor]) {
                    dist[neighbor] = dist[curr] - Math.log(weight);
                    parent[neighbor] = curr;
                    queue.offer(neighbor);
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
    public double avgDegree() {
        return (double) graph.edgeCount() / numVertices;
    }

    /**


     */

    public int degree (int n) {
        if (graph.neighbors(n) == null) {
            return -1;
        }
        return graph.neighbors(n).length;
    }

    /**
     * @param d the degree
     * @return all the node with degree d
     */
    public Collection<Integer> degreeNodes(int d) {
        Set<Integer> nodes = new HashSet<>();
        for (int i = 1; i <= numVertices; i++) {
            if (graph.neighbors(i).length == d) {
                nodes.add(i);
            }
        }

        return nodes;
    }

    public static void main(String[] args) {
        // Initialize your social network and other necessary objects

        // Start the main loop for the terminal-based interaction
        Scanner scanner = new Scanner(System.in);
        boolean exit = false;

        while (!exit) {
            // Display the menu options
            displayMenu();

            // Get user input
            String choice = scanner.nextLine();

            // Process the user's choice
            switch (choice.toLowerCase()) {
                case "1":
                    // Perform action 1
                    break;
                case "2":
                    // Perform action 2
                    break;
                case "3":
                    // Perform action 3
                    break;
                case "4":
                    // Perform action 4
                    break;
                case "5":
                    // Perform action 5
                    break;
                case "q":
                    exit = true;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
        }

        // Clean up and exit the application
        scanner.close();
        System.out.println("Exiting the social network application.");
    }

    private static void displayMenu() {
        System.out.println("=== Social Network Menu ===");
        System.out.println("1. Option 1");
        System.out.println("2. Option 2");
        System.out.println("3. Option 3");
        System.out.println("4. Option 4");
        System.out.println("5. Option 5");
        System.out.println("Q. Quit");
        System.out.print("Enter your choice: ");
    }
}