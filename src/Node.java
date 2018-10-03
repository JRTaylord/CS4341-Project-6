import java.util.ArrayList;

public class Node {
    ArrayList<Double> probabilities;
    String name;
    boolean evidence;
    boolean observed;
    boolean query;

    public Node(String name, ArrayList<Double> probabilities) {
        this.name = name;
        this.probabilities = probabilities;
        boolean evidence = false;
        boolean observed = false;
        boolean query = false;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Node) {
            Node node = ((Node) obj);
            return this.name.equals(node.name) && this.probabilities.equals(node.probabilities) &&
                    this.evidence == node.evidence && this.observed == node.observed && this.query == node.query;
        }
        return false;
    }
}
