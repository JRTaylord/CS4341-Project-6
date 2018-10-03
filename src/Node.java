import java.util.ArrayList;

public class Node {
    ArrayList<Double> probabilities;
    String name;
    boolean evidence;
    boolean query;
    boolean neither;

    public Node(String name, ArrayList<Double> probabilities) {
        this.name = name;
        this.probabilities = probabilities;
        this.evidence = false;
        this.query = false;
        this.neither = false;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node node = ((Node) obj);
            return this.name.equals(node.name) && this.probabilities.equals(node.probabilities) &&
                    this.evidence == node.evidence && this.query == node.query;
        }
        return false;
    }
}
