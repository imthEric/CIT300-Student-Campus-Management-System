import java.util.ArrayList;
import java.util.List;

/**
 * Vertex (location) node used by the campus Graph adjacency list.
 * Each GraphNode stores one campus location name and the list of
 * neighbouring locations it is directly connected to.
 */
public class GraphNode {

    private String location;                 // vertex name, e.g. "Library"
    private List<String> neighbours;         // adjacent vertices (roads/paths)

    public GraphNode(String location) {
        this.location = location;
        this.neighbours = new ArrayList<>();
    }

    public String getLocation() { return location; }

    public List<String> getNeighbours() { return neighbours; }

    /** Add an undirected neighbour if not already present. Returns true if added. */
    public boolean addNeighbour(String other) {
        if (!neighbours.contains(other)) {
            neighbours.add(other);
            return true;
        }
        return false;
    }

    /** Remove a neighbour. Returns true if it existed and was removed. */
    public boolean removeNeighbour(String other) {
        return neighbours.remove(other);
    }

    public boolean isConnectedTo(String other) {
        return neighbours.contains(other);
    }
}
