import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * Campus network Graph implemented with an ADJACENCY LIST.
 *
 *   Vertices  = campus locations (Main Gate, Library, ...)
 *   Edges     = roads / paths / direct connections between locations
 *
 * Supports adding/removing locations and connections, displaying the
 * network, and BFS / DFS traversals.
 *
 * A LinkedHashMap keeps insertion order so output is predictable, while
 * the adjacency structure itself (each vertex owning a neighbour list)
 * demonstrates the classic adjacency-list graph representation.
 */
public class Graph {

    // Map of location name -> its adjacency node
    private LinkedHashMap<String, GraphNode> vertices;

    public Graph() {
        vertices = new LinkedHashMap<>();
    }

    // ---------------- Vertex operations ----------------

    /** Add a new campus location (vertex). False when it already exists. */
    public boolean addLocation(String location) {
        if (containsLocation(location)) {
            return false; // duplicate location
        }
        vertices.put(normalize(location), new GraphNode(normalize(location)));
        return true;
    }

    /**
     * Remove a campus location and all of its associated connections.
     * False when the location does not exist.
     */
    public boolean removeLocation(String location) {
        String key = normalize(location);
        if (!vertices.containsKey(key)) {
            return false;
        }
        // Remove this vertex from every other vertex's neighbour list
        for (GraphNode node : vertices.values()) {
            node.removeNeighbour(key);
        }
        vertices.remove(key);
        return true;
    }

    public boolean containsLocation(String location) {
        return vertices.containsKey(normalize(location));
    }

    public List<String> getAllLocations() {
        return new ArrayList<>(vertices.keySet());
    }

    public int getVertexCount() { return vertices.size(); }

    // ---------------- Edge operations ----------------

    /**
     * Add an undirected connection (road/path) between two locations.
     * Returns: 1 = added, 0 = one/both locations missing or self-loop,
     *         -1 = connection already exists.
     */
    public int addConnection(String from, String to) {
        String a = normalize(from);
        String b = normalize(to);
        if (a.equals(b)) {
            return 0; // self-connection is not meaningful on a campus map
        }
        if (!vertices.containsKey(a) || !vertices.containsKey(b)) {
            return 0; // one or both locations do not exist
        }
        if (vertices.get(a).isConnectedTo(b)) {
            return -1; // already connected
        }
        vertices.get(a).addNeighbour(b);
        vertices.get(b).addNeighbour(a);  // undirected graph: add both ways
        return 1;
    }

    /**
     * Remove an existing connection between two locations.
     * Returns true when the edge existed and was removed.
     */
    public boolean removeConnection(String from, String to) {
        String a = normalize(from);
        String b = normalize(to);
        if (!vertices.containsKey(a) || !vertices.containsKey(b)) {
            return false;
        }
        if (!vertices.get(a).isConnectedTo(b)) {
            return false; // connection does not exist
        }
        vertices.get(a).removeNeighbour(b);
        vertices.get(b).removeNeighbour(a);
        return true;
    }

    // ---------------- Display ----------------

    /** Display each location and its direct neighbours. */
    public void displayConnections() {
        System.out.println("==================================================");
        System.out.println("               CAMPUS NETWORK GRAPH");
        System.out.println("==================================================");
        if (vertices.isEmpty()) {
            System.out.println("The campus graph is empty. No locations added.");
            return;
        }
        for (GraphNode node : vertices.values()) {
            System.out.print(node.getLocation());
            if (node.getNeighbours().isEmpty()) {
                System.out.println(" -> (no connections)");
            } else {
                System.out.println(" -> " + String.join(", ", node.getNeighbours()));
            }
        }
        System.out.println("Locations: " + vertices.size());
    }

    // ---------------- Traversals ----------------

    /**
     * Breadth-First Search from a starting location using a Queue.
     * Visits all nearest locations first, level by level.
     * Returns null when the start location does not exist.
     */
    public List<String> bfs(String start) {
        String s = normalize(start);
        if (!vertices.containsKey(s)) {
            return null;
        }
        List<String> order = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        Queue<String> queue = new ArrayDeque<>();

        queue.offer(s);
        visited.add(s);

        while (!queue.isEmpty()) {
            String current = queue.poll();
            order.add(current);
            for (String neighbour : vertices.get(current).getNeighbours()) {
                if (!visited.contains(neighbour)) {
                    visited.add(neighbour);
                    queue.offer(neighbour);
                }
            }
        }
        return order;
    }

    /**
     * Depth-First Search from a starting location using an explicit Stack
     * (iterative DFS). Explores as far as possible along each path first.
     * Returns null when the start location does not exist.
     */
    public List<String> dfs(String start) {
        String s = normalize(start);
        if (!vertices.containsKey(s)) {
            return null;
        }
        List<String> order = new ArrayList<>();
        Set<String> visited = new HashSet<>();
        java.util.Deque<String> stack = new java.util.ArrayDeque<>();

        stack.push(s);

        while (!stack.isEmpty()) {
            String current = stack.pop();
            if (visited.contains(current)) {
                continue;
            }
            visited.add(current);
            order.add(current);
            List<String> neighbours = vertices.get(current).getNeighbours();
            // Push in reverse so neighbours are explored in listed order
            for (int i = neighbours.size() - 1; i >= 0; i--) {
                if (!visited.contains(neighbours.get(i))) {
                    stack.push(neighbours.get(i));
                }
            }
        }
        return order;
    }

    // ---------------- Helpers ----------------

    /** Trim whitespace so location names are stored consistently. */
    private String normalize(String location) {
        return location == null ? "" : location.trim();
    }
}
